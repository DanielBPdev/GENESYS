-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado de calcular el estado V1 
-- para los archivos de Dependientes HU396.
-- Update: Andres Julian Rocha Cruz
-- Update date: 2021/02/16
-- Note: Se modifica procedure para que el procesamiento se haga 
-- masivo en todas las rutinas subyacentes
-- Se agrega ajuste para validaciones en V1 con respecto a la tarifa. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ValidateHU396V1Dependientes]
	@IdRegistroGeneral Bigint
AS
BEGIN
SET NOCOUNT ON;
	
	/*USP_ValidateNovedadesPILA*/
	/*USP_ValidateDiasNovedadesPlanillaPILA*/
	DECLARE @PeriodoAporte AS VARCHAR(7), @IdTransaccion BIGINT
	DECLARE @NovedadesCotizante AS NovedadesTYPE

	SELECT @PeriodoAporte = regPeriodoAporte, @IdTransaccion = regTransaccion
	FROM staging.RegistroGeneral reg WITH (NOLOCK)
	WHERE reg.regId = @IdRegistroGeneral

	--DECLARE @DiasPeriodo AS TABLE (Dia DATETIME, TieneNovedad BIT)
	CREATE TABLE #DiasPeriodoTMP (Dia DATETIME, TieneNovedad BIT)
	
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

	CREATE INDEX ix_dia ON #DiasPeriodoTMP(Dia)

	DECLARE @totalDiasPeriodo SMALLINT

	SELECT @totalDiasPeriodo = COUNT(*) FROM #DiasPeriodoTMP
	DECLARE @periodoAporteDate DATE = CONVERT(Date,@periodoAporte+'-01')
	DECLARE @anioPeriodo SMALLINT = DATEPART(YEAR, @periodoAporteDate)
	DECLARE @mesPeriodo SMALLINT = DATEPART(MONTH, @periodoAporteDate)
	DECLARE @fecUltimoDiaPeriodoAporte DATE = EOMONTH(@periodoAporteDate)


	SELECT regId,redId,regEsSimulado,regTransaccion,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regOUTEstadoEmpleador,regOUTTipoBeneficio
	,regOUTBeneficioActivo,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,regOUTTarifaEmpleador
	,redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,redTipoCotizante
	,redNovIngreso,redNovRetiro,redNovVSP,redNovVST,redNovSLN,redNovIGE,redNovLMA,redNovVACLR,redDiasIRL
	,redOUTDiasCotizadosPlanillas + redOUTDiasCotizadosBD AS DiasCotizados,
	/*
	(
		SELECT MAX(red2.redTarifa)
		FROM staging.RegistroDetallado red2 WITH (NOLOCK)
		WHERE red2.redRegistroGeneral = reg.regId
		AND red2.redTipoIdentificacionCotizante = red.redTipoIdentificacionCotizante
		AND red2.redNumeroIdentificacionCotizante = red.redNumeroIdentificacionCotizante
		and red2.redTipoCotizante = red.redTipoCotizante
	) redTarifa,
	*/
	red.redTarifa, redFechaIngreso,redFechaRetiro,redFechaInicioVSP,redFechaInicioSLN
	,redFechaFinSLN,redFechaInicioIGE,redFechaFinIGE,redFechaInicioLMA,redFechaFinLMA,redFechaInicioVACLR
	,redFechaFinVACLR,redFechaInicioVCT,redFechaFinVCT,redFechaInicioIRL,redFechaFinIRL,redOUTEstadoRegistroAporte
	,redOUTFechaProcesamientoValidRegAporte,redOUTEstadoValidacionV0,redOUTEstadoValidacionV1,redOUTEstadoValidacionV2
	,redOUTEstadoValidacionV3,redOUTClaseTrabajador,redOUTEstadoSolicitante,regOUTTarifaBaseEmpleador, redDiasCotizados
	,redOUTFechaIngresoCotizante, redOUTFechaRetiroCotizante, red.redCorrecciones, 'NO_OK' EstadoValidacionNovedadesPILA
	,redOUTDiasCotizadosNovedades, redDateTimeUpdate, redOUTAnalisisIntegral
	INTO #ValidateHU396V1DependientesTemp
	FROM staging.RegistroGeneral reg WITH (NOLOCK)
	INNER JOIN staging.RegistroDetallado red WITH (NOLOCK) ON reg.regId = red.redRegistroGeneral
	WHERE redOUTMarcaValRegistroAporte = 'VALIDAR_COMO_DEPENDIENTE' 
	AND reg.regId = @IdRegistroGeneral
	AND ISNULL(redOUTEstadoRegistroAporte,'NO_OK') = 'NO_OK'
	AND (
		redOUTEstadoValidacionV0 IS NULL OR
		redOUTEstadoValidacionV1 IS NULL OR
		redOUTEstadoValidacionV2 IS NULL OR
		redOUTEstadoValidacionV3 IS NULL
	)
	ORDER BY ISNULL(redOUTGrupoAC, 0), redId
	
	;
	WITH cteBase
	AS (
		SELECT DISTINCT	regId AS IdRegistroGeneral,
				redTipoIdentificacionCotizante AS TipoIdentificacionCotizante,
				redNumeroIdentificacionCotizante AS NumeroIdentificacionCotizante,
				regTipoIdentificacionAportante AS TipoIdentificacionAportante,
				regNumeroIdentificacionAportante AS NumeroIdentificacionAportante,
				regEsSimulado
		FROM #ValidateHU396V1DependientesTemp
	)

	SELECT
		regId,
		redId,
		regTipoIdentificacionAportante,
		regNumeroIdentificacionAportante,
		redTipoIdentificacionCotizante,
		redNumeroIdentificacionCotizante,
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
		regOUTEstadoArchivo,
		redOUTEstadoValidacionV0,
		redOUTEstadoValidacionV1,
		redOUTEstadoValidacionV2,
		redOUTEstadoValidacionV3,
		base.regEsSimulado,
		redCorrecciones
	INTO #tmpCotizantesOtrasPlanillas
	FROM 
	staging.RegistroDetallado red WITH (NOLOCK)
	INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON reg.regId = red.redRegistroGeneral
	INNER JOIN cteBase base ON	
		reg.regId <> base.IdRegistroGeneral
		AND reg.regPeriodoAporte = @PeriodoAporte
		AND red.redTipoIdentificacionCotizante = base.TipoIdentificacionCotizante
		AND red.redNumeroIdentificacionCotizante = base.NumeroIdentificacionCotizante
		AND reg.regTipoIdentificacionAportante = base.TipoIdentificacionAportante
		AND reg.regNumeroIdentificacionAportante = base.NumeroIdentificacionAportante

	;
	WITH cteINGRETCotizante
	AS (
		SELECT redId, redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante, 
			CASE 
				WHEN redNovIngreso IS NOT NULL AND redFechaIngreso IS NOT NULL THEN redFechaIngreso
				WHEN redNovIngreso IS NOT NULL AND redFechaIngreso IS NULL THEN @periodoAporteDate
			END ingresoPlanilla, 
			CASE 
				WHEN redNovRetiro IS NOT NULL AND redFechaRetiro IS NOT NULL THEN redFechaRetiro
				WHEN redNovRetiro IS NOT NULL AND redFechaRetiro IS NULL THEN EOMONTH(@periodoAporteDate)
			END retiroPlanilla, 
			redOUTFechaIngresoCotizante ingresoBD, 
			redOUTFechaRetiroCotizante retiroBD
		FROM #ValidateHU396V1DependientesTemp
	),	
	cteINGRETCotizante2
	AS (	
		SELECT redId, redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,
			CASE WHEN ingresoPlanilla IS NOT NULL THEN ingresoPlanilla ELSE ingresoBD END AS FechaIngreso, 
			CASE WHEN retiroPlanilla IS NOT NULL THEN retiroPlanilla ELSE retiroBD END AS FechaRetiro
		FROM cteINGRETCotizante
	),
	cteFechasCotizanteNULL
	AS (
		SELECT redId, redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,
			CASE 
				WHEN FechaIngreso IS NOT NULL 
				THEN
					CASE 
						WHEN DATEPART(YEAR, FechaIngreso) != @anioPeriodo OR DATEPART(MONTH, FechaIngreso) != @mesPeriodo
						THEN NULL
						ELSE FechaIngreso
					END
				ELSE FechaIngreso
			END AS FechaIngreso	,
			CASE 
				WHEN FechaRetiro IS NOT NULL 
				THEN
					CASE 
						WHEN DATEPART(YEAR, FechaRetiro) != @anioPeriodo OR DATEPART(MONTH, FechaRetiro) != @mesPeriodo
						THEN NULL
						ELSE FechaRetiro
					END
				ELSE FechaRetiro
			END AS FechaRetiro				
		FROM cteINGRETCotizante2
	),
	cteFechasCotizante
	AS (
		SELECT redId, redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante, Dia,
			CASE 
				WHEN cte.FechaIngreso IS NOT NULL AND cte.FechaRetiro IS NULL
				THEN
					CASE 
						WHEN Dia BETWEEN @periodoAporteDate AND DATEADD(dd,-1,cte.FechaIngreso)
						THEN 1 
						ELSE -1
					END
				WHEN cte.FechaIngreso IS NULL AND cte.FechaRetiro IS NOT NULL
				THEN
					CASE 
						WHEN Dia BETWEEN DATEADD(dd,1,cte.FechaRetiro) AND @fecUltimoDiaPeriodoAporte
						THEN 1 
						ELSE -1
					END
				WHEN cte.FechaIngreso IS NOT NULL AND cte.FechaRetiro IS NOT NULL
				THEN
					CASE 
						WHEN cte.FechaIngreso <= cte.FechaRetiro
						THEN 
							CASE
								WHEN Dia BETWEEN @periodoAporteDate AND DATEADD(dd,-1,cte.FechaIngreso)
								THEN 1
								WHEN Dia BETWEEN DATEADD(dd,1,cte.FechaRetiro) AND @fecUltimoDiaPeriodoAporte
								THEN 1
								ELSE -1
							END
						ELSE 
							CASE
								WHEN Dia BETWEEN DATEADD(dd,1,cte.FechaRetiro) AND DATEADD(dd,-1,cte.FechaIngreso)
								THEN 1
								ELSE -1
							END
					END
				ELSE -1
			END TieneNovedad
				
		FROM cteFechasCotizanteNULL cte
		CROSS JOIN #DiasPeriodoTMP tmpD
	),
	cteFechasCotizanteGRP
	AS (

		SELECT redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante, Dia, CASE WHEN SUM(TieneNovedad) <= 0 THEN 1 ELSE 0 END  TieneNovedad
		FROM cteFechasCotizante cte1		
		GROUP BY redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante, Dia
		
	)

	SELECT *
	INTO #tmpFechasCotizante
	FROM cteFechasCotizanteGRP

	;
	WITH cteCotizantes 
	AS (
		SELECT DISTINCT 
				redTipoIdentificacionCotizante AS TipoIdentificacionCotizante,
				redNumeroIdentificacionCotizante AS NumeroIdentificacionCotizante
		FROM #ValidateHU396V1DependientesTemp
	),
	cteNovedadesTMP
	AS (
		SELECT
			redTipoIdentificacionCotizante,
			redNumeroIdentificacionCotizante,
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
		FROM #ValidateHU396V1DependientesTemp
		WHERE 1=1
		AND (
			redNovSLN IS NOT NULL OR
			redNovLMA IS NOT NULL OR
			redNovIGE IS NOT NULL OR
			CAST(ISNULL(redDiasIRL,'0') AS INT) > 0
			)
		AND 
			(
				(regEsSimulado = 1 AND	ISNULL(redCorrecciones,'') NOT IN ('A')) OR
				(regEsSimulado = 0 AND	ISNULL(redCorrecciones,'') NOT IN ('A','C'))
			)

		UNION ALL
		-- novedades en otros aportes
		SELECT
			redTipoIdentificacionCotizante,
			redNumeroIdentificacionCotizante,
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
		FROM 
		#tmpCotizantesOtrasPlanillas red WITH (NOLOCK)		
		WHERE regOUTEstadoArchivo NOT IN (SELECT estado FROM #EstadosArchivoPostNovedades)
		
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
				(regEsSimulado = 1 AND	ISNULL(red.redCorrecciones,'') NOT IN ('A')) OR
				(regEsSimulado = 0 AND	ISNULL(red.redCorrecciones,'') NOT IN ('A','C'))
			)

		UNION ALL
		-- novedades SLN en core
		SELECT 
			nov.novTipoIdentificacion,
			nov.novNumeroIdentificacion,
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
		INNER JOIN cteCotizantes cte ON 
			nov.novTipoIdentificacion = cte.TipoIdentificacionCotizante
			AND nov.novNumeroIdentificacion = cte.NumeroIdentificacionCotizante
		WHERE nov.novTransaccion = @IdTransaccion
		AND novTipoNovedad IN ('NOVEDAD_SLN', 'NOVEDAD_SUS')

		UNION ALL
		-- novedades IGE en core
		SELECT 
			nov.novTipoIdentificacion,
			nov.novNumeroIdentificacion,
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
		INNER JOIN cteCotizantes cte ON 
			nov.novTipoIdentificacion = cte.TipoIdentificacionCotizante
			AND nov.novNumeroIdentificacion = cte.NumeroIdentificacionCotizante
		WHERE nov.novTransaccion = @IdTransaccion
		AND novTipoNovedad = 'NOVEDAD_IGE'

		UNION ALL
		-- novedades LMA en core
		SELECT 
			nov.novTipoIdentificacion,
			nov.novNumeroIdentificacion,
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
		INNER JOIN cteCotizantes cte ON 
			nov.novTipoIdentificacion = cte.TipoIdentificacionCotizante
			AND nov.novNumeroIdentificacion = cte.NumeroIdentificacionCotizante
		WHERE nov.novTransaccion = @IdTransaccion
		AND novTipoNovedad = 'NOVEDAD_LMA'

		UNION ALL
		-- novedades IRL en core
		SELECT 
			nov.novTipoIdentificacion,
			nov.novNumeroIdentificacion,
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
		INNER JOIN cteCotizantes cte ON 
			nov.novTipoIdentificacion = cte.TipoIdentificacionCotizante
			AND nov.novNumeroIdentificacion = cte.NumeroIdentificacionCotizante
		WHERE nov.novTransaccion = @IdTransaccion
		AND novTipoNovedad = 'NOVEDAD_IRL'	
	)
	
	SELECT * INTO #tmpNovedades FROM cteNovedadesTMP
	
	DROP TABLE #EstadosArchivoPostNovedades
	
	;
	WITH cteFechasCN
	AS (
		SELECT  tmpFC.redNumeroIdentificacionCotizante,
				tmpFC.redTipoIdentificacionCotizante,
				tmpFC.Dia,
			CASE
				WHEN
					(CASE
						WHEN (tmpN.redNovLMA IS NOT NULL AND tmpN.redFechaInicioLMA IS NOT NULL AND tmpN.redFechaFinLMA IS NOT NULL)
						THEN 1
						WHEN tmpN.redNovSLN IS NOT NULL AND tmpN.redFechaInicioSLN IS NOT NULL AND tmpN.redFechaFinSLN IS NOT NULL
						THEN 1
						WHEN tmpN.redNovIGE IS NOT NULL AND tmpN.redFechaInicioIGE IS NOT NULL AND tmpN.redFechaInicioIGE IS NOT NULL
						THEN 1
						WHEN ISNULL(tmpN.redDiasIRL, 0) > 0 
						THEN
							CASE
								WHEN tmpN.redFechaInicioIRL IS NOT NULL AND tmpN.redFechaFinIRL IS NOT NULL
								THEN 1
								ELSE 0
							END
						ELSE 0
					END) = 0
				THEN tmpFC.TieneNovedad
				ELSE 1
			END TieneNovedad
		FROM #tmpFechasCotizante tmpFC
		LEFT JOIN (
			#tmpNovedades tmpN 
			CROSS JOIN #DiasPeriodoTMP tmpD)	ON	tmpFC.redNumeroIdentificacionCotizante = tmpN.redNumeroIdentificacionCotizante
												AND tmpFC.redTipoIdentificacionCotizante = tmpN.redTipoIdentificacionCotizante	
												AND tmpFC.Dia = tmpD.Dia
	),
	cteFechasCNGRP 
	AS (
		SELECT redNumeroIdentificacionCotizante, redTipoIdentificacionCotizante, Dia, MAX(TieneNovedad) TieneNovedad
		FROM cteFechasCN
		GROUP BY redNumeroIdentificacionCotizante, redTipoIdentificacionCotizante, Dia
	),
	cteFechasIRL
	AS (
		SELECT redNumeroIdentificacionCotizante, redTipoIdentificacionCotizante, SUM(redDiasIRL) redDiasIRL
		FROM #tmpNovedades
		GROUP BY redNumeroIdentificacionCotizante, redTipoIdentificacionCotizante
	),
	cteDiasNovedadPre
	AS
	(
		SELECT cte1.redNumeroIdentificacionCotizante, cte1.redTipoIdentificacionCotizante, 
			SUM(cte1.TieneNovedad) + MAX(ISNULL(redDiasIRL, 0)) DiasNovedad
		FROM cteFechasCNGRP cte1
		LEFT JOIN cteFechasIRL cte2 ON	cte1.redNumeroIdentificacionCotizante = cte2.redNumeroIdentificacionCotizante
										AND cte1.redTipoIdentificacionCotizante = cte2.redTipoIdentificacionCotizante	
		GROUP BY cte1.redNumeroIdentificacionCotizante, cte1.redTipoIdentificacionCotizante
	),
	cteDiasNovedad30
	AS (
		SELECT redNumeroIdentificacionCotizante, redTipoIdentificacionCotizante,
				CASE WHEN DiasNovedad = @totalDiasPeriodo THEN 30 ELSE DiasNovedad END DiasNovedad
		FROM cteDiasNovedadPre
	),
	cteDiasNovedad
	AS (
		SELECT redNumeroIdentificacionCotizante, redTipoIdentificacionCotizante,
				CASE WHEN DiasNovedad > 30 THEN 30 ELSE DiasNovedad END DiasNovedad
		FROM cteDiasNovedad30
	)

	SELECT * INTO #tmpDiasNovedad FROM cteDiasNovedad

	DROP TABLE #DiasPeriodoTMP	
	DROP TABLE #tmpFechasCotizante
	DROP TABLE #tmpNovedades		

	UPDATE #ValidateHU396V1DependientesTemp
	SET	redOUTDiasCotizadosNovedades = tdn.DiasNovedad,
		DiasCotizados = CASE WHEN tdn.DiasNovedad + tmp.DiasCotizados <= 30 THEN tdn.DiasNovedad + tmp.DiasCotizados ELSE 30 END
	FROM #ValidateHU396V1DependientesTemp tmp	
	INNER JOIN #tmpDiasNovedad tdn ON  tmp.redTipoIdentificacionCotizante = tdn.redTipoIdentificacionCotizante
										AND tmp.redNumeroIdentificacionCotizante = tdn.redNumeroIdentificacionCotizante

	
	UPDATE tmp
	SET EstadoValidacionNovedadesPILA = 
		CASE
			WHEN (isnull(tmp.redOUTClaseTrabajador,'') = 'REGULAR' OR isnull(tmp.redOUTClaseTrabajador,'') = 'MADRE_COMUNITARIA' OR isnull(tmp.redOUTClaseTrabajador,'') = 'SERVICIO_DOMESTICO') AND tdn.DiasNovedad >= 30
			THEN 'OK'
			WHEN isnull(tmp.redOUTClaseTrabajador,'') = 'TRABAJADOR_POR_DIAS' AND tdn.DiasNovedad > 0
			THEN 'OK'
			ELSE 'NO_OK'
		END
	FROM #ValidateHU396V1DependientesTemp tmp
	INNER JOIN #tmpDiasNovedad tdn ON  tmp.redTipoIdentificacionCotizante = tdn.redTipoIdentificacionCotizante
										AND tmp.redNumeroIdentificacionCotizante = tdn.redNumeroIdentificacionCotizante

------------------**********************************										
------------------******************** CONTROL PARA VALIDAR SI VIENEN NOVEDADES CON RESPECTO AL REDID PARA LA NOVEDAD V3 CUANDO LOS DÍAS COTIZADOS SON MENORES DE 30
------------------**********************************
				select redId, Novedad, TieneNovedad, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante into #novedadesPila
				from (
				select redId, redNovIngreso as [NOVEDAD_ING],redNovRetiro as [NOVEDAD_RET],redNovVSP as [NOVEDAD_VSP] ,redNovVST as [NOVEDAD_VST],redNovSLN as [NOVEDAD_SLN],redNovIGE as [NOVEDAD_IGE],redNovLMA as [NOVEDAD_LMA]
				,redNovVACLR as [NOVEDAD_VAC_LR], case when isnumeric(redDiasIRL) = 1 and redDiasIRL > 0  then 'X' else null end as redDiasIRL, redNumeroIdentificacionCotizante, redTipoIdentificacionCotizante
				from #ValidateHU396V1DependientesTemp
				) as pvt unpivot (TieneNovedad for Novedad in ([NOVEDAD_ING], [NOVEDAD_RET], [NOVEDAD_VSP], [NOVEDAD_VST], [NOVEDAD_SLN], [NOVEDAD_IGE], [NOVEDAD_LMA], [NOVEDAD_VAC_LR], redDiasIRL)) as unpvt
------------------**********************************										
------------------********************* CONTROL PARA QUITAR DE LAS NOVEDADES LAS VST Y VSP YA QUE NO ESTAS NO AFECTAN EN EL PROCESO PARA V3
------------------**********************************						
delete from #novedadesPila where novedad in ('NOVEDAD_VST', 'NOVEDAD_VSP')
------------------**********************************
------------------**********************************	
				select pi1AcogeBeneficio, regOUTTipoBeneficio, regOUTBeneficioActivo, r.regId, regClaseAportante
				into #AcogeBeneficioPila 
				from PilaIndicePlanilla as p 
				inner join staging.RegistroGeneral as r on p.pipId = r.regRegistroControl
				inner join PilaArchivoIRegistro1 as pi1 on p.pipId = pi1.pi1IndicePlanilla
				where r.regId = @IdRegistroGeneral
------------------**********************************										
------------------***** SE AGREGA VALIDACIÓN PARA VER SI EL APORTANTE SE ACOGE A BENEFICIO DE LEY. 
------------------**********************************	
				declare @NumId varchar (50) = (select top (1) regNumeroIdentificacionAportante from #ValidateHU396V1DependientesTemp)
				drop table if exists #BenEmpleador
				create table #BenEmpleador (perNumeroIdentificacion varchar(20), perTipoIdentificacion varchar(50), befTipoBeneficio varchar(20),bemFechaVinculacion date, tarifa numeric(19,5), origen varchar(200))
				insert #BenEmpleador (perNumeroIdentificacion, perTipoIdentificacion, befTipoBeneficio, bemFechaVinculacion, tarifa, origen)
				execute sp_execute_remote coreReferenceData, N'
				select 
				p.perNumeroIdentificacion, p.perTipoIdentificacion
				,b.befTipoBeneficio
				,bemFechaVinculacion
				,case when (datediff(yyyy,bemFechaVinculacion, convert(date,dbo.GetLocalDate()))) <= 1 then 0.01000
					  when (datediff(yyyy,bemFechaVinculacion, convert(date,dbo.GetLocalDate()))) > 1  and (datediff(yyyy,bemFechaVinculacion, convert(date,dbo.GetLocalDate()))) <= 2  then 0.02000
					  when (datediff(yyyy,bemFechaVinculacion, convert(date,dbo.GetLocalDate()))) > 2  and (datediff(yyyy,bemFechaVinculacion, convert(date,dbo.GetLocalDate()))) <= 3  then 0.03000
					  when (datediff(yyyy,bemFechaVinculacion, convert(date,dbo.GetLocalDate()))) > 3  and (datediff(yyyy,bemFechaVinculacion, convert(date,dbo.GetLocalDate()))) <= 4  then 0.04000
					  else 5 end as tarifa
				from persona as p with (nolock)
				inner join Empresa as e with (nolock) on p.perId = e.empPersona
				inner join Empleador as em with (nolock) on e.empId = em.empEmpresa
				inner join BeneficioEmpleador as be with (nolock) on em.empId = be.bemEmpleador
				inner join Beneficio as b with (nolock) on be.bemBeneficio = b.befId
				where be.bemBeneficioActivo = 1 and p.perNumeroIdentificacion =  @NumId', N'@NumId varchar (50)', @NumId = @NumId
------------------**********************************	
--============= Condicion veteranos. 
------------------**********************************	
			declare @tipoIdEmpleador varchar(20), @numIdEmpleador varchar(20)
			select top (1) @tipoIdEmpleador = regTipoIdentificacionAportante, @numIdEmpleador = regNumeroIdentificacionAportante from #ValidateHU396V1DependientesTemp
			
			create table #valVeterano (perTipoIdentificacion varchar(20), perNumeroIdentificacion varchar(20),  roaFechaFinCondicionVeterano date, roaFechaInicioCondicionVeterano date, roaClaseTrabajador varchar(100), origen varchar(250))
			
			insert #valVeterano
			
			execute sp_execute_remote coreReferenceData, N'
			select p.perTipoIdentificacion, p.perNumeroIdentificacion,  roaFechaFinCondicionVeterano, roaFechaInicioCondicionVeterano, roaClaseTrabajador
			from dbo.Persona as p with (nolock)
			inner join dbo.Afiliado as a with (nolock) on p.perId = a.afiPersona
			inner join dbo.RolAfiliado as r with (nolock) on a.afiId = r.roaAfiliado
			inner join dbo.Empleador as em with (nolock) on em.empId = r.roaEmpleador
			inner join dbo.Empresa as e with (nolock) on e.empId = em.empEmpresa
			inner join dbo.persona as pe with (nolock) on pe.perId = e.empPersona
			where pe.perTipoIdentificacion = @tipoIdEmpleador and pe.perNumeroIdentificacion = @numIdEmpleador and r.roaClaseTrabajador = ''VETERANO_FUERZA_PUBLICA'' and r.roaEstadoAfiliado = ''ACTIVO''
			', N'@tipoIdEmpleador varchar(20), @numIdEmpleador varchar(20)', @tipoIdEmpleador = @tipoIdEmpleador, @numIdEmpleador = @numIdEmpleador
------------------**********************************	
------------------**********************************	
------------------**********************************	


	;
	WITH cteSolInd 
	AS (
		SELECT Data FROM dbo.Split( (
		SELECT stpValorParametro FROM staging.StagingParametros WITH (NOLOCK)
		WHERE stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE') ,',')
	),

	cteValidaciones
	AS (
		SELECT
		redId,
		--USP_ValidateHU396V1BDNormal_T1T2T3
		V1 = case when tmp.redTarifa = 0.04
					then 'OK'
				else 
					case when tmp.redTarifa >= (select tarifa from #BenEmpleador where perNumeroIdentificacion = tmp.regNumeroIdentificacionAportante)
						then 'OK'
						else 
							case when tmp.redTarifa = 0 and (select top 1 TieneNovedad from #novedadesPila  as a where a.redTipoIdentificacionCotizante = tmp.redTipoIdentificacionCotizante and 
															a.redNumeroIdentificacionCotizante = tmp.redNumeroIdentificacionCotizante and Novedad in ('NOVEDAD_IGE', 'NOVEDAD_LMA','redDiasIRL','NOVEDAD_SLN','NOVEDAD_VAC_LR')) is not null
								then 'OK'
									else case when tmp.redTarifa = 0 and tmp.redTipoCotizante = 68 and exists (select 1 from #valVeterano where perTipoIdentificacion = tmp.redTipoIdentificacionCotizante and perNumeroIdentificacion = tmp.redNumeroIdentificacionCotizante)
										then 'OK'
										else 'NO_OK'
										end
								end
						end					
				end,
		--USP_ValidateHU396V2BDNormalBD1429BD590_T1T2T3
		V2 = 
		CASE
			WHEN tmp.regOUTEstadoEmpleador='ACTIVO' OR tmp.regOUTEstadoEmpleador='INACTIVO' OR tmp.regOUTEstadoEmpleador='NO_FORMALIZADO_RETIRADO_CON_APORTES'
			THEN
				CASE
					WHEN tmp.redOUTEstadoSolicitante IS NULL OR tmp.redOUTClaseTrabajador IS NULL
					THEN
						CASE 
							WHEN tmp.redOUTEstadoSolicitante IS NULL OR tmp.redOUTEstadoSolicitante = 'NO_FORMALIZADO_CON_INFORMACION' OR  tmp.redOUTEstadoSolicitante = 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES'
							THEN 'NO_VALIDADO_BD'
							ELSE
								CASE
									WHEN tmp.redOUTClaseTrabajador IS NULL
									THEN 'NO_OK'
									ELSE redOUTEstadoValidacionV2
								END
						END
					ELSE
						CASE
							WHEN (tmp.redTipoCotizante NOT IN (SELECT Data FROM cteSolInd) AND tmp.redTipoCotizante NOT IN (51)) 
								AND (tmp.redOUTClaseTrabajador = 'REGULAR' OR tmp.redOUTClaseTrabajador = 'MADRE_COMUNITARIA' OR tmp.redOUTClaseTrabajador = 'SERVICIO_DOMESTICO' OR tmp.redOUTClaseTrabajador = 'VETERANO_FUERZA_PUBLICA')
							THEN 'OK'
							ELSE
								CASE
									WHEN tmp.redTipoCotizante = 51 AND tmp.redOUTClaseTrabajador = 'TRABAJADOR_POR_DIAS'
									THEN 'OK'
									ELSE
										CASE
											WHEN tmp.redTipoCotizante = 4 AND (tmp.regClaseAportante = 'C' OR tmp.regClaseAportante= 'D')
											THEN 'NO_OK'
											ELSE 'NO_OK'
										END
								END
						END
				END
			ELSE 'NO_APLICA'
		END,
		--USP_ValidateHU396V3BDNormalBD1429BD590_T1T2T3
		V3 =
		CASE WHEN tmp.DiasCotizados = 0 THEN tmp.EstadoValidacionNovedadesPILA
			when SUM(redDiasCotizados) over (partition by tmp.redTipoIdentificacionCotizante,tmp.redNumeroIdentificacionCotizante,tmp.redTipoCotizante) >= 30
			then 'OK'
				else
					case 
						when --isnull(tmp.redOUTClaseTrabajador,'') = 'REGULAR' OR 
						isnull(tmp.redOUTClaseTrabajador,'') = 'MADRE_COMUNITARIA' OR isnull(tmp.redOUTClaseTrabajador,'') = 'SERVICIO_DOMESTICO'
						then
							case when SUM(redDiasCotizados) over (partition by tmp.redTipoIdentificacionCotizante,tmp.redNumeroIdentificacionCotizante,tmp.redTipoCotizante) >= 30 or
												((SUM(redDiasCotizados) over (partition by tmp.redTipoIdentificacionCotizante,tmp.redNumeroIdentificacionCotizante,tmp.redTipoCotizante) < 30) 
												and exists (select top (1) redId from #novedadesPila  as a where /*a.redId = tmp.redId*/ a.redTipoIdentificacionCotizante = tmp.redTipoIdentificacionCotizante and 
															a.redNumeroIdentificacionCotizante = tmp.redNumeroIdentificacionCotizante and Novedad in ('NOVEDAD_ING', 'NOVEDAD_RET','NOVEDAD_IGE', 'NOVEDAD_LMA','redDiasIRL','NOVEDAD_SLN','NOVEDAD_VAC_LR')))
							then 'OK'
							else 'NO_OK'
							end
						when isnull(tmp.redOUTClaseTrabajador,'') = 'TRABAJADOR_POR_DIAS'
							then 'OK'
						when tmp.redOUTClaseTrabajador is null
							then 
								case when exists (select top (1) redId from #novedadesPila  as a where a.redTipoIdentificacionCotizante = tmp.redTipoIdentificacionCotizante and a.redNumeroIdentificacionCotizante = tmp.redNumeroIdentificacionCotizante and Novedad in ('NOVEDAD_ING','NOVEDAD_RET'))
								then 'OK'
                                else 'NO_VALIDADO_BD'
								end
								      ---------GAP 87825 
								when (SUM(redDiasCotizados) over (partition by tmp.redTipoIdentificacionCotizante,tmp.redNumeroIdentificacionCotizante,tmp.redTipoCotizante) < 30) 
								and tmp.redTipoCotizante=51 and isnull(tmp.redOUTClaseTrabajador,'') = 'REGULAR'
								then 'OK'
								when isnull(tmp.redOUTClaseTrabajador,'') = 'REGULAR'
						        then 
						 	    case when
								SUM(redDiasCotizados) over (partition by tmp.redTipoIdentificacionCotizante,tmp.redNumeroIdentificacionCotizante,tmp.redTipoCotizante) >= 30 or
								((SUM(redDiasCotizados) over (partition by tmp.redTipoIdentificacionCotizante,tmp.redNumeroIdentificacionCotizante,tmp.redTipoCotizante) < 30) 
								and exists (select top (1) redId from #novedadesPila  as a where /*a.redId = tmp.redId*/ a.redTipoIdentificacionCotizante = tmp.redTipoIdentificacionCotizante and 
								 a.redNumeroIdentificacionCotizante = tmp.redNumeroIdentificacionCotizante and Novedad in ('NOVEDAD_ING', 'NOVEDAD_RET','NOVEDAD_IGE', 'NOVEDAD_LMA','redDiasIRL','NOVEDAD_SLN','NOVEDAD_VAC_LR')))
							then 'OK'
							else 'NO_OK'
							end	
				end
			end,
		--USP_ValidateHU396V1BD1429BD590_T2T3
		V1_1 = 
			CASE
				WHEN tmp.redTarifa NOT IN (0.00000,0.01000,0.02000,0.03000,tmp.regOUTTarifaBaseEmpleador)
				THEN 'NO_OK'
				ELSE
					CASE
						WHEN tmp.redTarifa = tmp.regOUTTarifaBaseEmpleador
						THEN 'OK'
						ELSE
							CASE
								WHEN tmp.redTarifa = 0.01000 OR tmp.redTarifa = 0.02000 OR tmp.redTarifa = 0.03000
								THEN 
									CASE
										WHEN tmp.redTarifa >= tmp.regOUTTarifaEmpleador
										THEN 'OK'
										ELSE 'NO_OK'
									END
								ELSE
									CASE
										WHEN tmp.redTarifa = 0.00000 AND tmp.DiasCotizados = 0
										THEN tmp.EstadoValidacionNovedadesPILA
										ELSE 'NO_OK'
									END
							END
					END
			END,
		tmp.redOUTDiasCotizadosNovedades
	FROM #ValidateHU396V1DependientesTemp tmp
	INNER JOIN #tmpDiasNovedad tdn ON  tmp.redTipoIdentificacionCotizante = tdn.redTipoIdentificacionCotizante
										AND tmp.redNumeroIdentificacionCotizante = tdn.redNumeroIdentificacionCotizante
	)


	,
	cteAplicarValidaciones
	AS (
		
			SELECT tmp.redId,
			V1 = cteV.V1,
			V2 = cteV.V2,
			V3 = cteV.V3,
			V0 = 'CUMPLE'
		FROM #ValidateHU396V1DependientesTemp tmp
		INNER JOIN cteValidaciones cteV ON tmp.redId = cteV.redId
		WHERE tmp.regClaseAportante IN ('A','B','I') 
		AND (tmp.regOUTTipoBeneficio IS NULL OR tmp.regOUTBeneficioActivo =  0 ) OR (tmp.regOUTTipoBeneficio = 'LEY_1429' AND tmp.regOUTBeneficioActivo = 1 ) OR (tmp.regOUTTipoBeneficio = 'LEY_590' AND tmp.regOUTBeneficioActivo = 1)

		UNION 

		--USP_ValidateHU396BD590BD1429_T2T3
		SELECT tmp.redId,
			V1 = cteV.V1,
			V2 = cteV.V2,
			V3 = cteV.V3,
			V0 = 'CUMPLE'
		FROM #ValidateHU396V1DependientesTemp tmp
		INNER JOIN cteValidaciones cteV ON tmp.redId = cteV.redId
		WHERE (tmp.regClaseAportante IN ('D') AND (select pi1AcogeBeneficio from #AcogeBeneficioPila where regId = tmp.regId) = 'N' and regOUTTipoBeneficio is null)
		
		union 

		SELECT tmp.redId,
			V1 = cteV.V1,
			V2 = cteV.V2,
			V3 = cteV.V3,
			V0 = 'NO_CUMPLE'
		FROM #ValidateHU396V1DependientesTemp tmp
		INNER JOIN cteValidaciones cteV ON tmp.redId = cteV.redId
		WHERE (tmp.regClaseAportante IN ('D') AND (select pi1AcogeBeneficio from #AcogeBeneficioPila where regId = tmp.regId) = 'N' and regOUTTipoBeneficio = 'LEY_1429')

		union 

		SELECT tmp.redId,
			V1 = cteV.V1,
			V2 = cteV.V2,
			V3 = cteV.V3,
			V0 = 'NO_CUMPLE'
		FROM #ValidateHU396V1DependientesTemp tmp
		INNER JOIN cteValidaciones cteV ON tmp.redId = cteV.redId
		WHERE (tmp.regClaseAportante IN ('D') AND (select pi1AcogeBeneficio from #AcogeBeneficioPila where regId = tmp.regId) = 'S' and regOUTTipoBeneficio is null)

		union all

		SELECT tmp.redId,
			V1 = cteV.V1,
			V2 = cteV.V2,
			V3 = cteV.V3,
			V0 = 'CUMPLE'
		FROM #ValidateHU396V1DependientesTemp tmp
		INNER JOIN cteValidaciones cteV ON tmp.redId = cteV.redId
		WHERE (tmp.regClaseAportante IN ('D') AND (select pi1AcogeBeneficio from #AcogeBeneficioPila where regId = tmp.regId) = 'S' and regOUTTipoBeneficio = 'LEY_1429')

		UNION 

		SELECT tmp.redId,
			V1 = 
				CASE 
					WHEN tmp.regOUTTipoBeneficio IS NULL OR tmp.regOUTBeneficioActivo =  0
					THEN cteV.V1
					ELSE
						CASE
							WHEN tmp.regOUTTipoBeneficio = 'LEY_1429' AND tmp.regOUTBeneficioActivo = 1
							THEN cteV.V1_1
							ELSE tmp.redOUTEstadoValidacionV1
						END
				END,
			V2 = cteV.V2,
			V3 = cteV.V3,
			V0 = 'NO_CUMPLE'
		FROM #ValidateHU396V1DependientesTemp tmp
		INNER JOIN cteValidaciones cteV ON tmp.redId = cteV.redId
		WHERE (tmp.regClaseAportante IN ('D') AND NOT(tmp.regOUTTipoBeneficio = 'LEY_590' AND tmp.regOUTBeneficioActivo = 1))
		--- SE REALIZA AJUSTE A LA LOGICA YA QUE LOS CAMPOS PUEDEN VENIR NULL
		or (tmp.regClaseAportante IN ('D') and tmp.regOUTBeneficioActivo = 0)
		or (tmp.regClaseAportante IN ('D') and (tmp.regOUTTipoBeneficio is null or tmp.regOUTBeneficioActivo is null))

		UNION

		SELECT tmp.redId,
			V1 = 
				CASE 
					WHEN tmp.regOUTTipoBeneficio IS NULL OR tmp.regOUTBeneficioActivo =  0
					THEN cteV.V1
					ELSE
						CASE
							WHEN tmp.regOUTTipoBeneficio = 'LEY_590' AND tmp.regOUTBeneficioActivo = 1
							THEN cteV.V1_1
							ELSE tmp.redOUTEstadoValidacionV1
						END
				END,
			V2 = cteV.V2,
			V3 = cteV.V3,
			V0 = 'CUMPLE'
		FROM #ValidateHU396V1DependientesTemp tmp
		INNER JOIN cteValidaciones cteV ON tmp.redId = cteV.redId
		WHERE (tmp.regClaseAportante IN ('D') AND NOT(tmp.regOUTTipoBeneficio = 'LEY_1429' AND tmp.regOUTBeneficioActivo = 1))
		--- SE REALIZA AJUSTE A LA LOGICA YA QUE LOS CAMPOS PUEDEN VENIR NULL
		or (tmp.regClaseAportante IN ('D') and tmp.regOUTBeneficioActivo = 0)
		or (tmp.regClaseAportante IN ('D') and (tmp.regOUTTipoBeneficio is null or tmp.regOUTBeneficioActivo is null))

		
		union  ----- INICIA VALIDACIONES CON LA CLASE DE APORTANTE TIPO C


		SELECT tmp.redId,
			V1 = cteV.V1,
			V2 = cteV.V2,
			V3 = cteV.V3,
			V0 = 'CUMPLE'
		FROM #ValidateHU396V1DependientesTemp tmp
		INNER JOIN cteValidaciones cteV ON tmp.redId = cteV.redId
		WHERE (tmp.regClaseAportante IN ('C') and regOUTTarifaBaseEmpleador = 0.04)

		union

		SELECT tmp.redId,
			V1 = cteV.V1,
			V2 = cteV.V2,
			V3 = cteV.V3,
			V0 = 'NO_CUMPLE'
		FROM #ValidateHU396V1DependientesTemp tmp
		INNER JOIN cteValidaciones cteV ON tmp.redId = cteV.redId
		WHERE (tmp.regClaseAportante IN ('C') and regOUTTarifaBaseEmpleador < 0.04 and regOUTTipoBeneficio is null)

		union 

		SELECT tmp.redId,
			V1 = cteV.V1,
			V2 = cteV.V2,
			V3 = cteV.V3,
			V0 = 'CUMPLE'
		FROM #ValidateHU396V1DependientesTemp tmp
		INNER JOIN cteValidaciones cteV ON tmp.redId = cteV.redId
		WHERE (tmp.regClaseAportante IN ('C') and isnull(regOUTTipoBeneficio,'') = 'LEY_590' and (select pi1AcogeBeneficio from #AcogeBeneficioPila where regId = tmp.regId) = 'S')
		
		union 

		SELECT tmp.redId,
			V1 = cteV.V1,
			V2 = cteV.V2,
			V3 = cteV.V3,
			V0 = 'NO_CUMPLE'
		FROM #ValidateHU396V1DependientesTemp tmp
		INNER JOIN cteValidaciones cteV ON tmp.redId = cteV.redId
		WHERE (tmp.regClaseAportante IN ('C') and isnull(regOUTTipoBeneficio,'') = 'LEY_590' and (select pi1AcogeBeneficio from #AcogeBeneficioPila where regId = tmp.regId) = 'N')

		union

		SELECT tmp.redId,
			V1 = cteV.V1,
			V2 = cteV.V2,
			V3 = cteV.V3,
			V0 = 'NO_CUMPLE'
		FROM #ValidateHU396V1DependientesTemp tmp
		INNER JOIN cteValidaciones cteV ON tmp.redId = cteV.redId
		WHERE (tmp.regClaseAportante IN ('C') and isnull(regOUTTipoBeneficio,'') = 'LEY_1429')

	)

	UPDATE tmp
	SET	redOUTAnalisisIntegral = CASE WHEN V3 = 'OK' THEN 1 ELSE 0 END,
		redOUTEstadoValidacionV0 = cteV.V0,
		redOUTEstadoValidacionV1 = cteV.V1,
		redOUTEstadoValidacionV2 = cteV.V2,
		redOUTEstadoValidacionV3 = cteV.V3	
	FROM cteAplicarValidaciones cteV
	INNER JOIN #ValidateHU396V1DependientesTemp tmp ON cteV.redId = tmp.redId


	DROP TABLE #tmpDiasNovedad
	DROP TABLE #novedadesPila
	drop table #AcogeBeneficioPila
	drop table if exists #valVeterano
	
	BEGIN TRAN

		--PERSISTENCIA EN TABLAS DEFINITIVAS

		INSERT INTO staging.RegistroAfectacionAnalisisIntegral (raiRegistroGeneral, raiTipoIdentificacionCotizante, raiNumeroIdentificacionCotizante, raiRegistroDetalladoAfectado)
		SELECT @IdRegistroGeneral, red.redTipoIdentificacionCotizante, red.redNumeroIdentificacionCotizante, redId
		FROM #tmpCotizantesOtrasPlanillas red
		WHERE 
		EXISTS (
			SELECT 1
			FROM #ValidateHU396V1DependientesTemp tmp
			WHERE 1=1
			AND red.regTipoIdentificacionAportante = tmp.regTipoIdentificacionAportante 
			AND red.regNumeroIdentificacionAportante = tmp.regNumeroIdentificacionAportante
			AND red.redTipoIdentificacionCotizante = tmp.redTipoIdentificacionCotizante
			AND red.redNumeroIdentificacionCotizante = tmp.redNumeroIdentificacionCotizante
			AND tmp.redOUTEstadoValidacionV3 = 'OK'
			AND red.redOUTEstadoValidacionV0 = 'CUMPLE' AND red.redOUTEstadoValidacionV2 IN ('OK', 'NO_APLICA')
			AND red.redOUTEstadoValidacionV3 = 'NO_OK'
			AND ISNULL(red.redCorrecciones,'') NOT IN ('A','C')
			AND NOT EXISTS (
				SELECT TOP 1 1 FROM staging.RegistroAfectacionAnalisisIntegral WITH (NOLOCK) WHERE raiRegistroGeneral = @IdRegistroGeneral AND raiRegistroDetalladoAfectado = red.redId
			)
			AND 
				(
					(red.regEsSimulado = 1 AND	ISNULL(red.redCorrecciones,'') NOT IN ('A')) OR
					(red.regEsSimulado = 0 AND	ISNULL(red.redCorrecciones,'') NOT IN ('A','C'))
				)
		)
			
		DROP TABLE #tmpCotizantesOtrasPlanillas	
	
		UPDATE red 
		SET redOUTEstadoValidacionV0 = tmp.redOUTEstadoValidacionV0,
			redOUTEstadoValidacionV1 = tmp.redOUTEstadoValidacionV1,
			redOUTEstadoValidacionV2 = tmp.redOUTEstadoValidacionV2,
			redOUTEstadoValidacionV3 = tmp.redOUTEstadoValidacionV3,
			redOUTDiasCotizadosNovedades = tmp.redOUTDiasCotizadosNovedades,
			redOUTAnalisisIntegral = tmp.redOUTAnalisisIntegral,
			redDateTimeUpdate = dbo.getLocalDate() 
		FROM staging.RegistroDetallado red
		INNER JOIN #ValidateHU396V1DependientesTemp tmp ON  red.redId = tmp.redId
		WHERE redRegistroGeneral = @IdRegistroGeneral

		
		DROP TABLE #ValidateHU396V1DependientesTemp;
	
	COMMIT
END;