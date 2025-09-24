-- =============================================
-- Author:		Mauricio Sánchez Castro / Andrés Julián Rocha
-- Create date: 2017-07-28 Modified: 2017-08-09
-- Description:	Procedimiento almacenado que calcula los día de las novedades de IGE, LMA y IRL desde su fecha inicio hasta su fecha fin. 
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidateDiasNovedadesPlanillaPILA]
	@IdRegistroGeneral BigInt,
	@IdTransaccion BigInt,
	@EsSimulado Bit,
	@periodoAporte Varchar(7),
	@TipoIdentificacionAportante Varchar(20),
	@NumeroIdentificacionAportante Varchar(16),
	@TipoIdentificacionCotizante VARCHAR(20),
	@NumeroIdentificacionCotizante VARCHAR(16),
	@DiasCotizados Smallint,
	@TotalDiasNovedades Smallint OUTPUT
AS
BEGIN
SET NOCOUNT ON;
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED

	--print 'USP_ValidateDiasNovedadesPlanillaPILA'
	--print CAST(@IdRegistroGeneral AS VARCHAR(50))
	--print CAST(@IdTransaccion AS VARCHAR(50))
	--print CAST(@periodoAporte AS VARCHAR(50))
	--print CAST(@TipoIdentificacionCotizante AS VARCHAR(50))
	--print CAST(@NumeroIdentificacionCotizante AS VARCHAR(50))

	DECLARE @NovedadesCotizante AS NovedadesTYPE

	--DECLARE @DiasPeriodo AS TABLE (Dia DATETIME, TieneNovedad BIT)
	CREATE TABLE #DiasPeriodoTMP (Dia DATETIME, TieneNovedad BIT)
	CREATE INDEX ix_dia ON #DiasPeriodoTMP(Dia)
	
	DECLARE @Dia INT = 0
	
	CREATE TABLE #EstadosArchivoPostNovedades (estado VARCHAR(60))
	
	INSERT INTO #EstadosArchivoPostNovedades (estado)
	VALUES	('PROCESADO_NOVEDADES'),
			('PROCESADO_SIN_NOVEDADES'),
			('RECAUDO_NOTIFICADO'),
			('RECAUDO_NOTIFICADO_MANUAL'),
			('NOTIFICACION_RECAUDO_FALLIDO')
	
	WHILE @Dia < DAY(EOMONTH(CONVERT(Date,@PeriodoAporte+'-01')))
	BEGIN
		SET @Dia = @Dia + 1
		INSERT INTO #DiasPeriodoTMP VALUES (CONVERT(Date,@PeriodoAporte+'-'+CAST(@Dia AS VARCHAR(2))), 0)
	END

	DECLARE @totalDiasPeriodo SMALLINT

	SELECT @totalDiasPeriodo = COUNT(*) FROM #DiasPeriodoTMP

	SET @TotalDiasNovedades = 0	
	
	DECLARE @NovSLN VARCHAR(1)
	DECLARE @NovIGE VARCHAR(1)
	DECLARE @NovLMA VARCHAR(1)
	DECLARE @DiasIRL INT
	
	DECLARE @FechaInicioSLN DATE
	DECLARE @FechaFinSLN DATE
	DECLARE @FechaInicioIGE DATE
	DECLARE @FechaFinIGE DATE
	DECLARE @FechaInicioLMA DATE
	DECLARE @FechaFinLMA DATE
	DECLARE @FechaInicioIRL DATE
	DECLARE @FechaFinIRL DATE

	DECLARE @OrigenNovedad VARCHAR(16)

	----------------------------------- se marcan los días por retiro o ingreso ---------------------------------
	
	DECLARE @fechaIngreso DATE
	DECLARE @fechaRetiro DATE

	DECLARE @periodoAporteDate DATE = CONVERT(Date,@periodoAporte+'-01')
	DECLARE @anioPeriodo SMALLINT = DATEPART(YEAR, @periodoAporteDate)
	DECLARE @mesPeriodo SMALLINT = DATEPART(MONTH, @periodoAporteDate)

	SELECT @fechaIngreso = CASE WHEN ingresoPlanilla IS NOT NULL THEN ingresoPlanilla ELSE ingresoBD END, 
		@fechaRetiro = CASE WHEN retiroPlanilla IS NOT NULL THEN retiroPlanilla ELSE retiroBD END
	FROM (
		SELECT redId, 
			CASE 
				WHEN redNovIngreso IS NOT NULL AND MAX(redFechaIngreso) IS NOT NULL THEN MAX(redFechaIngreso) 
				WHEN redNovIngreso IS NOT NULL AND MAX(redFechaIngreso) IS NULL THEN @periodoAporteDate
			END ingresoPlanilla, 
			CASE 
				WHEN redNovRetiro IS NOT NULL AND MAX(redFechaRetiro) IS NOT NULL THEN MAX(redFechaRetiro) 
				WHEN redNovRetiro IS NOT NULL AND MAX(redFechaRetiro) IS NULL THEN EOMONTH(@periodoAporteDate)
			END retiroPlanilla, 
			MAX(redOUTFechaIngresoCotizante) ingresoBD, 
			MAX(redOUTFechaRetiroCotizante) retiroBD
		FROM staging.RegistroDetallado WITH (NOLOCK)
		WHERE redRegistroGeneral = @IdRegistroGeneral
		AND redTipoIdentificacionCotizante = @tipoIdentificacionCotizante
		AND redNumeroIdentificacionCotizante = @numeroIdentificacionCotizante
		GROUP BY redId, redNovIngreso, redNovRetiro, redFechaIngreso, redFechaRetiro
	) AS fechasCotizante

	IF @fechaIngreso IS NOT NULL
	BEGIN
		DECLARE @anioIngreso SMALLINT = DATEPART(YEAR, @fechaIngreso)
		DECLARE @mesIngreso SMALLINT = DATEPART(MONTH, @fechaIngreso)

		IF @anioIngreso != @anioPeriodo OR @mesIngreso != @mesPeriodo
		BEGIN
			SET @fechaIngreso = NULL
		END
	END

	IF @fechaRetiro IS NOT NULL
	BEGIN
		DECLARE @anioRetiro SMALLINT = DATEPART(YEAR, @fechaRetiro)
		DECLARE @mesRetiro SMALLINT = DATEPART(MONTH, @fechaRetiro)

		IF @anioRetiro != @anioPeriodo OR @mesRetiro != @mesPeriodo
		BEGIN
			SET @fechaRetiro = NULL
		END
	END

	IF @fechaIngreso IS NOT NULL AND @fechaRetiro IS NULL
	BEGIN
		UPDATE #DiasPeriodoTMP SET TieneNovedad = 1
		WHERE (Dia BETWEEN @periodoAporteDate AND @fechaIngreso)
	END

	IF @fechaIngreso IS NULL AND @fechaRetiro IS NOT NULL 
	BEGIN
		SET @periodoAporteDate = CONVERT(Date,@periodoAporte+'-'+CAST(DAY(EOMONTH(@periodoAporteDate)) AS VARCHAR))

		UPDATE #DiasPeriodoTMP SET TieneNovedad = 1
		WHERE (Dia BETWEEN @fechaRetiro AND @periodoAporteDate)
	END

	IF @fechaIngreso IS NOT NULL AND @fechaRetiro IS NOT NULL 
	BEGIN
		IF @fechaIngreso <= @fechaRetiro
		BEGIN
			DECLARE @finMesPeriodo DATE = CONVERT(Date,@periodoAporte+'-'+CAST(DAY(EOMONTH(@periodoAporteDate)) AS VARCHAR))

			UPDATE #DiasPeriodoTMP SET TieneNovedad = 1
			WHERE (Dia BETWEEN @periodoAporteDate AND @fechaIngreso)

			UPDATE #DiasPeriodoTMP SET TieneNovedad = 1
			WHERE (Dia BETWEEN @fechaRetiro AND @finMesPeriodo)
		END
		ELSE
		BEGIN
			UPDATE #DiasPeriodoTMP SET TieneNovedad = 1
			WHERE (Dia BETWEEN @fechaRetiro AND @fechaIngreso)
		END
	END

	-------------------------------------------------------------------------------------------------------------

	--DECLARE @TablaNovTMP AS TABLE (
	CREATE TABLE #TablaNovTMP (
		redNovSLN VARCHAR(1), 
		redNovIGE VARCHAR(1),
		redNovLMA VARCHAR(1),
		redDiasIRL VARCHAR(2),
		redFechaInicioSLN DATE,
		redFechaFinSLN DATE,
		redFechaInicioIGE DATE,
		redFechaFinIGE DATE,
		redFechaInicioLMA DATE,
		redFechaFinLMA DATE,
		redFechaInicioIRL DATE,
		redFechaFinIRL DATE,
		origenNovedad VARCHAR(20)
	)
	
	
	-- novedades en aporte
	INSERT INTO #TablaNovTMP (
		redNovSLN, 
		redNovIGE,
		redNovLMA,
		redDiasIRL,
		redFechaInicioSLN,
		redFechaFinSLN,
		redFechaInicioIGE,
		redFechaFinIGE,
		redFechaInicioLMA,
		redFechaFinLMA,
		redFechaInicioIRL,
		redFechaFinIRL,
		origenNovedad
	)
	SELECT
		redNovSLN, 
		redNovIGE,
		redNovLMA,
		CAST(ISNULL(redDiasIRL,'0') AS INT) AS redDiasIRL,
		CASE WHEN redNovSLN IS NOT NULL AND redFechaInicioSLN IS NULL THEN @periodoAporteDate ELSE redFechaInicioSLN END redFechaInicioSLN,
		CASE WHEN redNovSLN IS NOT NULL AND redFechaFinSLN IS NULL THEN EOMONTH(@periodoAporteDate) ELSE redFechaFinSLN END redFechaFinSLN,
		CASE WHEN redNovIGE IS NOT NULL AND redFechaInicioIGE IS NULL THEN @periodoAporteDate ELSE redFechaInicioIGE END redFechaInicioIGE,
		CASE WHEN redNovIGE IS NOT NULL AND redFechaFinIGE IS NULL THEN EOMONTH(@periodoAporteDate) ELSE redFechaFinIGE END redFechaFinIGE,
		CASE WHEN redNovLMA IS NOT NULL AND redFechaInicioLMA IS NULL THEN @periodoAporteDate ELSE redFechaInicioLMA END redFechaInicioLMA,
		CASE WHEN redNovLMA IS NOT NULL AND redFechaFinLMA IS NULL THEN EOMONTH(@periodoAporteDate) ELSE redFechaFinLMA END redFechaFinLMA,
		CASE WHEN ISNULL(redDiasIRL,'0') > 0 AND redFechaInicioIRL IS NULL THEN @periodoAporteDate ELSE redFechaInicioIRL END redFechaInicioIRL,
		CASE WHEN ISNULL(redDiasIRL,'0') > 0 AND redFechaFinIRL IS NULL THEN EOMONTH(@periodoAporteDate) ELSE redFechaFinIRL END redFechaFinIRL,
		'NOVEDAD_APORTE' origenNovedad
	FROM staging.RegistroDetallado red WITH (NOLOCK)
	INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON reg.regId = red.redRegistroGeneral
	WHERE reg.regId = @IdRegistroGeneral
	AND red.redTipoIdentificacionCotizante = @TipoIdentificacionCotizante
	AND red.redNumeroIdentificacionCotizante = @NumeroIdentificacionCotizante
	AND (
		redNovSLN IS NOT NULL OR
		redNovLMA IS NOT NULL OR
		redNovIGE IS NOT NULL OR
		CAST(ISNULL(redDiasIRL,'0') AS INT) > 0
		)
	AND 
		(
			(@EsSimulado = 1 AND	ISNULL(red.redCorrecciones,'') NOT IN ('A')) OR
			(@EsSimulado = 0 AND	ISNULL(red.redCorrecciones,'') NOT IN ('A','C'))
		)

	UNION ALL
	-- novedades en otros aportes
	SELECT
		redNovSLN, 
		redNovIGE,
		redNovLMA,
		CAST(ISNULL(redDiasIRL,'0') AS INT) AS redDiasIRL,
		CASE WHEN redNovSLN IS NOT NULL AND redFechaInicioSLN IS NULL THEN @periodoAporteDate ELSE redFechaInicioSLN END redFechaInicioSLN,
		CASE WHEN redNovSLN IS NOT NULL AND redFechaFinSLN IS NULL THEN EOMONTH(@periodoAporteDate) ELSE redFechaFinSLN END redFechaFinSLN,
		CASE WHEN redNovIGE IS NOT NULL AND redFechaInicioIGE IS NULL THEN @periodoAporteDate ELSE redFechaInicioIGE END redFechaInicioIGE,
		CASE WHEN redNovIGE IS NOT NULL AND redFechaFinIGE IS NULL THEN EOMONTH(@periodoAporteDate) ELSE redFechaFinIGE END redFechaFinIGE,
		CASE WHEN redNovLMA IS NOT NULL AND redFechaInicioLMA IS NULL THEN @periodoAporteDate ELSE redFechaInicioLMA END redFechaInicioLMA,
		CASE WHEN redNovLMA IS NOT NULL AND redFechaFinLMA IS NULL THEN EOMONTH(@periodoAporteDate) ELSE redFechaFinLMA END redFechaFinLMA,
		CASE WHEN ISNULL(redDiasIRL,'0') > 0 AND redFechaInicioIRL IS NULL THEN @periodoAporteDate ELSE redFechaInicioIRL END redFechaInicioIRL,
		CASE WHEN ISNULL(redDiasIRL,'0') > 0 AND redFechaFinIRL IS NULL THEN EOMONTH(@periodoAporteDate) ELSE redFechaFinIRL END redFechaFinIRL,
		'NOVEDAD_APORTE' origenNovedad
	FROM staging.RegistroDetallado red WITH (NOLOCK)
	INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON reg.regId = red.redRegistroGeneral
	WHERE regOUTEstadoArchivo NOT IN (SELECT estado FROM #EstadosArchivoPostNovedades)
	AND reg.regId <> @IdRegistroGeneral
	AND reg.regPeriodoAporte = @periodoAporte
	AND red.redTipoIdentificacionCotizante = @TipoIdentificacionCotizante
	AND red.redNumeroIdentificacionCotizante = @NumeroIdentificacionCotizante
	AND reg.regTipoIdentificacionAportante = @TipoIdentificacionAportante
	AND reg.regNumeroIdentificacionAportante = @NumeroIdentificacionAportante
	AND red.redOUTEstadoValidacionV0 = 'CUMPLE' 
	AND red.redOUTEstadoValidacionV2 = 'OK'
	AND (
		redNovSLN IS NOT NULL OR
		redNovLMA IS NOT NULL OR
		redNovIGE IS NOT NULL OR
		CAST(ISNULL(redDiasIRL,'0') AS INT) > 0
		)
	AND 
		(
			(@EsSimulado = 1 AND	ISNULL(red.redCorrecciones,'') NOT IN ('A')) OR
			(@EsSimulado = 0 AND	ISNULL(red.redCorrecciones,'') NOT IN ('A','C'))
		)

	UNION ALL
	-- novedades SLN en core
	SELECT 
		'X' AS redNovSLN, 
		NULL AS redNovIGE,
		NULL AS redNovLMA,
		0 AS redDiasIRL,
		novFechaInicio AS redFechaInicioSLN,
		novFechaFin AS redFechaFinSLN,
		NULL AS redFechaInicioIGE,
		NULL AS redFechaFinIGE,
		NULL AS redFechaInicioLMA,
		NULL AS redFechaFinLMA,
		NULL AS redFechaInicioIRL,
		NULL AS redFechaFinIRL,
		'NOVEDAD_PERSONAS' origenNovedad
	FROM staging.Novedad nov WITH (NOLOCK)
	WHERE nov.novTransaccion = @IdTransaccion
	AND nov.novTipoIdentificacion = @TipoIdentificacionCotizante
	AND nov.novNumeroIdentificacion = @NumeroIdentificacionCotizante
	AND novTipoNovedad IN ('NOVEDAD_SLN', 'NOVEDAD_SUS')

	UNION ALL
	-- novedades IGE en core
	SELECT 
		NULL AS redNovSLN, 
		'X' AS redNovIGE,
		NULL AS redNovLMA,
		0 AS redDiasIRL,
		novFechaInicio AS redFechaInicioIGE,
		novFechaFin AS redFechaFinIGE,
		NULL AS redFechaInicioSLN,
		NULL AS redFechaFinSLN,
		NULL AS redFechaInicioLMA,
		NULL AS redFechaFinLMA,
		NULL AS redFechaInicioIRL,
		NULL AS redFechaFinIRL,
		'NOVEDAD_PERSONAS' origenNovedad
	FROM staging.Novedad nov WITH (NOLOCK)
	WHERE nov.novTransaccion = @IdTransaccion
	AND nov.novTipoIdentificacion = @TipoIdentificacionCotizante
	AND nov.novNumeroIdentificacion = @NumeroIdentificacionCotizante
	AND novTipoNovedad = 'NOVEDAD_IGE'

	UNION ALL
	-- novedades LMA en core
	SELECT 
		NULL AS redNovSLN, 
		NULL AS redNovIGE,
		'X' AS redNovLMA,
		0 AS redDiasIRL,
		NULL AS redFechaInicioSLN,
		NULL AS redFechaFinSLN,
		NULL AS redFechaInicioIGE,
		NULL AS redFechaFinIGE,
		novFechaInicio AS redFechaInicioLMA,
		novFechaFin AS redFechaFinLMA,
		NULL AS redFechaInicioIRL,
		NULL AS redFechaFinIRL,
		'NOVEDAD_PERSONAS' origenNovedad
	FROM staging.Novedad nov WITH (NOLOCK)
	WHERE nov.novTransaccion = @IdTransaccion
	AND nov.novTipoIdentificacion = @TipoIdentificacionCotizante
	AND nov.novNumeroIdentificacion = @NumeroIdentificacionCotizante
	AND novTipoNovedad = 'NOVEDAD_LMA'

	UNION ALL
	-- novedades IRL en core
	SELECT 
		NULL AS redNovSLN, 
		NULL AS redNovIGE,
		NULL AS redNovLMA,
		1 AS redDiasIRL,
		NULL AS redFechaInicioSLN,
		NULL AS redFechaFinSLN,
		NULL AS redFechaInicioIGE,
		NULL AS redFechaFinIGE,
		NULL AS redFechaInicioLMA,
		NULL AS redFechaFinLMA,
		novFechaInicio AS redFechaInicioIRL,
		novFechaFin AS redFechaFinIRL,
		'NOVEDAD_PERSONAS' origenNovedad
	FROM staging.Novedad nov WITH (NOLOCK)
	WHERE nov.novTransaccion = @IdTransaccion
	AND nov.novTipoIdentificacion = @TipoIdentificacionCotizante
	AND nov.novNumeroIdentificacion = @NumeroIdentificacionCotizante
	AND novTipoNovedad = 'NOVEDAD_IRL'



	DECLARE @TrabajadorCursor AS CURSOR

	SET @TrabajadorCursor = CURSOR FAST_FORWARD FOR
	SELECT
		redNovSLN, 
		redNovIGE,
		redNovLMA,
		redDiasIRL,
		redFechaInicioSLN,
		redFechaFinSLN,
		redFechaInicioIGE,
		redFechaFinIGE,
		redFechaInicioLMA,
		redFechaFinLMA,
		redFechaInicioIRL,
		redFechaFinIRL,
		origenNovedad
	FROM #TablaNovTMP WITH (NOLOCK)
	
	OPEN @TrabajadorCursor
	FETCH NEXT FROM @TrabajadorCursor INTO		
	@NovSLN, 
	@NovIGE,
	@NovLMA,
	@DiasIRL, 
	@FechaInicioSLN,
	@FechaFinSLN,
	@FechaInicioIGE,
	@FechaFinIGE,
	@FechaInicioLMA,
	@FechaFinLMA,
	@FechaInicioIRL,
	@FechaFinIRL, 
	@OrigenNovedad
	WHILE @@FETCH_STATUS = 0
	BEGIN	
		
		IF @NovLMA IS NOT NULL AND @FechaInicioLMA IS NOT NULL AND @FechaFinLMA IS NOT NULL 
			AND (@DiasCotizados != 0 OR @OrigenNovedad = 'NOVEDAD_PERSONAS')
		BEGIN
			UPDATE #DiasPeriodoTMP SET TieneNovedad = 1
			WHERE	(Dia BETWEEN @FechaInicioLMA AND @FechaFinLMA)
		END

		IF @NovSLN IS NOT NULL AND @FechaInicioSLN IS NOT NULL AND @FechaFinSLN IS NOT NULL
			AND (@DiasCotizados != 0 OR @OrigenNovedad = 'NOVEDAD_PERSONAS')
		BEGIN
			UPDATE #DiasPeriodoTMP SET TieneNovedad = 1
			WHERE	(Dia BETWEEN @FechaInicioSLN AND @FechaFinSLN)
		END

		IF @NovIGE IS NOT NULL AND @FechaInicioIGE IS NOT NULL AND @FechaInicioIGE IS NOT NULL
			AND (@DiasCotizados != 0 OR @OrigenNovedad = 'NOVEDAD_PERSONAS')
		BEGIN
			UPDATE #DiasPeriodoTMP SET TieneNovedad = 1
			WHERE	(Dia BETWEEN @FechaInicioIGE AND @FechaFinIGE)
		END
				
		IF @DiasIRL > 0 AND (@DiasCotizados != 0 OR @OrigenNovedad = 'NOVEDAD_PERSONAS')
		BEGIN
			IF @FechaInicioIRL IS NOT NULL AND @FechaFinIRL IS NOT NULL
			BEGIN
				UPDATE #DiasPeriodoTMP SET TieneNovedad = 1
				WHERE	(Dia BETWEEN @FechaInicioIRL AND @FechaFinIRL)
			END
			ELSE
			BEGIN
				SET @TotalDiasNovedades = @TotalDiasNovedades + @DiasIRL
			END
		END


		FETCH NEXT FROM @TrabajadorCursor INTO	
		@NovSLN, 
		@NovIGE,
		@NovLMA,
		@DiasIRL, 
		@FechaInicioSLN,
		@FechaFinSLN,
		@FechaInicioIGE,
		@FechaFinIGE,
		@FechaInicioLMA,
		@FechaFinLMA,
		@FechaInicioIRL,
		@FechaFinIRL, 
		@OrigenNovedad
	END
	CLOSE @TrabajadorCursor;
	DEALLOCATE @TrabajadorCursor;


	SELECT @TotalDiasNovedades = @TotalDiasNovedades + COUNT(1)
	FROM #DiasPeriodoTMP WITH (NOLOCK)
	WHERE TieneNovedad = 1

	IF @TotalDiasNovedades = @totalDiasPeriodo
	BEGIN
		SET @TotalDiasNovedades = 30
	END
END;
