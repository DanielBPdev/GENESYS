-- =============================================
-- Author: Robinson Castillo
-- Create date: 2022-08-25
-- Description:	Se realiza copia del sp para registrar novedades, para que las planillas N sigan ese flujo y procesen novedades. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ExecuteRegistrarRelacionarNovedadesRegistro_N]
(
	@IdRegistroGeneral BIGINT,
	@EsSimulado BIT = 0,
	@EsRegistroManual BIT = 0,
	@FechaReferenciaNovedadFutura DATE = NULL
)
AS
BEGIN
SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	--EXEC USP_AsignarMarcaNovedadFutura @IdRegistroGeneral, @FechaReferenciaNovedadFutura
	
	DECLARE @IdTransaccion BIGINT
	DECLARE @EsAportePensionados VARCHAR(20)
	DECLARE @TiposCotizanteInd AS TABLE (Id INT)
	DECLARE @EsDependiente BIT
	DECLARE @PeriodoRegular VARCHAR(7)
	DECLARE @PeriodoRetroactivo VARCHAR(7)
	DECLARE @EsPeriodoRegular BIT
	DECLARE @EsPeriodoRetroactivo BIT
	DECLARE @EsPeriodoFuturo BIT = null
	DECLARE @EsEmpleadorActivo BIT	
	DECLARE @EsEmpleadorReintegrable BIT
	DECLARE @EsTrabajadorReintegrable BIT
	DECLARE @ActivarEmpleador BIT
	DECLARE @TipoIdentificacionAportante VARCHAR(20)
	DECLARE @NumeroIdentificacionAportante VARCHAR(16)
	DECLARE @ProcesadoSinNovedades BIT = 1

	CREATE TABLE #Novedades(
		[tenIdTransaccion] [bigint] NOT NULL,
		[tenMarcaNovedadSimulado] [bit] NULL,
		[tenMarcaNovedadManual] [bit] NULL,
		[tenRegistroGeneral] [bigint] NOT NULL,
		[tenRegistroDetallado] [bigint] NOT NULL,
		[tenTipoIdAportante] [varchar](20) COLLATE Latin1_General_CI_AI NOT NULL,
		[tenNumeroIdAportante] [varchar](16) COLLATE Latin1_General_CI_AI NOT NULL,
		[tenTipoIdCotizante] [varchar](20) COLLATE Latin1_General_CI_AI NOT NULL,
		[tenNumeroIdCotizante] [varchar](16) COLLATE Latin1_General_CI_AI NOT NULL,
		[tenTipoTransaccion] [varchar](100) COLLATE Latin1_General_CI_AI NULL,
		[tenEsIngreso] [bit] NOT NULL,
		[tenEsRetiro] [bit] NOT NULL,
		[tenFechaInicioNovedad] [date] NULL,
		[tenFechaFinNovedad] [date] NULL,
		[tenAccionNovedad] [varchar](20) COLLATE Latin1_General_CI_AI NOT NULL,
		[tenMensajeNovedad] [varchar](250) COLLATE Latin1_General_CI_AI NULL,
		[tenTipoCotizante] [varchar](50) COLLATE Latin1_General_CI_AI NULL,
		[tenPrimerApellido] [varchar](20) COLLATE Latin1_General_CI_AI NULL,
		[tenSegundoApellido] [varchar](30) COLLATE Latin1_General_CI_AI NULL,
		[tenPrimerNombre] [varchar](20) COLLATE Latin1_General_CI_AI NULL,
		[tenSegundoNombre] [varchar](30) COLLATE Latin1_General_CI_AI NULL,
		[tenCodigoDepartamento] [smallint] NULL,
		[tenCodigoMunicipio] [smallint] NULL,
		[tenRegistroDetalladoNovedad] [bigint] NULL, 
		[tenEnProceso] [bit]
	)


	SET @PeriodoRegular = CONVERT(VARCHAR(7),@FechaReferenciaNovedadFutura, 120)
	SET @PeriodoRetroactivo = CONVERT(VARCHAR(7), DATEADD(MONTH,-1,@FechaReferenciaNovedadFutura), 120)
	
	--print '@PeriodoRegular'
	--print @PeriodoRegular
	--print '@PeriodoRetroactivo'
	--print @PeriodoRetroactivo

	SELECT @EsAportePensionados = regEsAportePensionados
	FROM staging.RegistroGeneral 
	WHERE regId = @IdRegistroGeneral

	INSERT INTO @TiposCotizanteInd
	SELECT Data FROM dbo.Split(
		(SELECT TOP 1 stpValorParametro
		FROM [staging].[StagingParametros]
		WHERE stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE'), ',')

	EXEC USP_ExecuteCopiarNovedades @IdRegistroGeneral

	--Dependiente/Independiente
	IF @EsAportePensionados = 0
	BEGIN

		--print 'Calcular Si es Dependiente'
		SET @EsDependiente =
			CASE WHEN EXISTS (	SELECT TOP 1 1 
								FROM staging.RegistroDetallado with (nolock)
								WHERE redRegistroGeneral = @IdRegistroGeneral
								AND redTipoCotizante NOT IN (SELECT Id FROM @TiposCotizanteInd) ) THEN 1 ELSE 0 END
		
		--print '@EsDependiente'
		--print @EsDependiente
		
		IF @EsDependiente = 1
		BEGIN
			SET @PeriodoRegular = CONVERT(VARCHAR(7), DATEADD(MONTH,-1,@FechaReferenciaNovedadFutura), 120)
			SET @PeriodoRetroactivo = CONVERT(VARCHAR(7), DATEADD(MONTH,-2,@FechaReferenciaNovedadFutura), 120)
			
			--print 'Calcular Si es Periodo regular o retroactivo y Si es empleador Activo'
			SELECT	@EsPeriodoRegular = CASE WHEN regPeriodoAporte = @PeriodoRegular THEN 1 ELSE 0 END,
					@EsPeriodoFuturo = CASE WHEN regPeriodoAporte > @PeriodoRegular THEN 1 ELSE 0 END,
					@EsPeriodoRetroactivo = CASE WHEN regPeriodoAporte <= @PeriodoRetroactivo THEN 1 ELSE 0 END,
					@EsEmpleadorActivo = CASE WHEN regOUTEstadoEmpleador = 'ACTIVO' THEN 1 ELSE 0 END,
					@EsEmpleadorReintegrable = ISNULL(regOUTEsEmpleadorReintegrable, 0),
					@TipoIdentificacionAportante = regTipoIdentificacionAportante,
					@NumeroIdentificacionAportante = regNumeroIdentificacionAportante,
					@IdTransaccion = regTransaccion
			FROM staging.RegistroGeneral with (nolock)
			WHERE regId = @IdRegistroGeneral

			--print '@EsPeriodoRegular'
			--print @EsPeriodoRegular
			--print '@EsPeriodoRetroactivo'
			--print @EsPeriodoRetroactivo
			--print '@EsEmpleadorActivo'
			--print @EsEmpleadorActivo

		
			IF @EsPeriodoRegular = 1 --es periodo regular
			BEGIN
				-- INICIO MANTIS 0266566
				--	EXEC USP_CrearNovedadIngresoPorAportesOK @IdRegistroGeneral
				-- FIN MANTIS 0266566
					
				IF @EsEmpleadorActivo = 1 --Si es empleador Activo
				BEGIN	
					EXEC USP_ValidarNovedadesEmpleadorActivo @IdRegistroGeneral, @EsSimulado, @EsRegistroManual
				END
				ELSE
				BEGIN --Si NO es empleador Activo				
				
					--print 'Calcular si al menos un trabajador tiene como primera novedad un ingreso (ING)'

					SELECT	TipoIdentificacionCotizante, 
							NumeroIdentificacionCotizante,
							TipoNovedad,
							FechaNovedad
					INTO #NovedadesEmpleador
					FROM 
					(
						SELECT redTipoIdentificacionCotizante AS TipoIdentificacionCotizante, redNumeroIdentificacionCotizante AS NumeroIdentificacionCotizante, 
						redFechaIngreso AS NOVEDAD_ING,
						redFechaRetiro AS NOVEDAD_RET,
						redFechaInicioVSP AS NOVEDAD_VSP,
						redFechaInicioVST AS NOVEDAD_VST, 
						redFechaInicioSLN AS NOVEDAD_SLN,
						redFechaInicioIGE AS NOVEDAD_IGE,
						redFechaInicioLMA AS NOVEDAD_LMA,
						redFechaInicioVACLR AS NOVEDAD_VAC_LR,
						redFechaInicioIRL AS NOVEDAD_IRL
						FROM staging.RegistroDetallado red with (nolock)
						INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
						WHERE 1=1
						AND (red.redOUTEstadoSolicitante = 'ACTIVO' OR ISNULL(red.redOUTEsTrabajadorReintegrable, 0) = 1)
						AND reg.regId = @IdRegistroGeneral
						AND (
							redNovIngreso IS NOT NULL OR
							redNovRetiro IS NOT NULL OR
							redNovVSP IS NOT NULL OR
							redNovVST IS NOT NULL OR
							redNovSLN IS NOT NULL OR
							redNovIGE IS NOT NULL OR
							redNovLMA IS NOT NULL OR
							redNovVACLR IS NOT NULL OR
							ISNULL(redDiasIRL, 0) > 0 
							)
						AND red.redOUTTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
					) AS T 
					UNPIVOT
					(
						FechaNovedad
						FOR TipoNovedad IN (NOVEDAD_ING, NOVEDAD_RET, NOVEDAD_VSP, NOVEDAD_VST, NOVEDAD_SLN, NOVEDAD_IGE, NOVEDAD_LMA, NOVEDAD_VAC_LR, NOVEDAD_IRL)
					) UNPIV;


					IF EXISTS (
						SELECT TOP 1 1
						FROM #NovedadesEmpleador Nov1
						INNER JOIN (
									SELECT TipoIdentificacionCotizante, NumeroIdentificacionCotizante, MIN(FechaNovedad) FechaNovedad
									FROM #NovedadesEmpleador
									GROUP BY TipoIdentificacionCotizante, NumeroIdentificacionCotizante 
								) Nov2 
								ON Nov1.TipoIdentificacionCotizante = Nov2.TipoIdentificacionCotizante
								AND Nov1.NumeroIdentificacionCotizante = Nov2.NumeroIdentificacionCotizante
								AND Nov1.FechaNovedad = Nov2.FechaNovedad
						WHERE Nov1.TipoNovedad = 'NOVEDAD_ING'
					) --Si primera novedad de algun trabajador es de ING
					BEGIN
					
						IF @EsEmpleadorReintegrable = 1 AND @EsPeriodoRegular = 1 --Si el empleador es reintegrable
						BEGIN
							--print 'Reintegrar al Empleador'
							EXEC USP_ValidarNovedadesEmpleadorActivo @IdRegistroGeneral, @EsSimulado, @EsRegistroManual 
						END
						ELSE --Si el empleador NO se logro activar
						BEGIN
							--print 'marcar todas las novedades del empleador como NO APLICADA'
							EXEC USP_ExecuteRegistrarNovedadesNoAplicadas @IdRegistroGeneral, @EsSimulado, @EsRegistroManual
						END
					END
					ELSE --Si entre todos los trabajadores la primera novedad NO es ING
					BEGIN
						--print 'marcar todas las novedades de la planilla como NO APLICADA'
						EXEC USP_ExecuteRegistrarNovedadesNoAplicadas @IdRegistroGeneral, @EsSimulado, @EsRegistroManual
					END
				END

			END
			IF @EsPeriodoRetroactivo = 1
			BEGIN
				IF @EsEmpleadorActivo = 1 --Si es empleador Activo
				BEGIN	
					EXEC USP_ValidarNovedadesEmpleadorActivo @IdRegistroGeneral, @EsSimulado, @EsRegistroManual
				END
				
				-- TODO: Se deja comentareado este objeto ya que es redundante, las novedades retroactivas, se procesan en el objeto USP_ValidarNovedadesEmpleadorActivo
				--EXEC USP_ValidarNovedadesRetroactivasEmpleador @IdRegistroGeneral, @EsSimulado, @EsRegistroManual 
			END
			
		END

		--print 'proceso para Independientes'
		-- se actualiza la marca de novedad futura sí ni se tiene
		IF @esPeriodoFuturo IS NULL 
		BEGIN
			SELECT @esPeriodoFuturo = ISNULL(regNovedadFutura, 0) FROM staging.RegistroGeneral WHERE regId = @IdRegistroGeneral
		END

		EXEC USP_ValidarNovedadesRetiros @IdRegistroGeneral, @EsSimulado, @EsRegistroManual, 'TRABAJADOR_INDEPENDIENTE' 
	END
	-- Pensionado
	ELSE --'ARCHIVO_OI_IP', 'ARCHIVO_OI_IPR'
	BEGIN
		--print 'proceso para Pensionados'
		EXEC USP_ValidarNovedadesRetiros @IdRegistroGeneral, @EsSimulado, @EsRegistroManual, 'PENSIONADO'

		-- se toma el valor de novedad futura
		SELECT @esPeriodoFuturo = ISNULL(regNovedadFutura, 0) FROM staging.RegistroGeneral WHERE regId = @IdRegistroGeneral
	END

	-- se determina sí el proceso presenta novedades (aplicadas o no aplicadas)
	IF EXISTS (
		SELECT TOP 1 red.redId 
		FROM staging.RegistroDetallado red
		INNER JOIN staging.RegistroGeneral reg ON red.redRegistroGeneral = reg.regId
		WHERE reg.regId = @IdRegistroGeneral
		AND (
			redNovIngreso IS NOT NULL OR
			redNovRetiro IS NOT NULL OR
			redNovVSP IS NOT NULL OR
			redNovVST IS NOT NULL OR
			redNovSLN IS NOT NULL OR
			redNovIGE IS NOT NULL OR
			redNovLMA IS NOT NULL OR
			redNovVACLR IS NOT NULL OR
			ISNULL(redDiasIRL, 0) > 0 
		)
	)
	BEGIN
		SET @ProcesadoSinNovedades = 0
	END
	
	BEGIN
		-- se crean los registros de novedad temporal
		INSERT INTO TemNovedad (tenIdTransaccion,tenMarcaNovedadSimulado,tenMarcaNovedadManual,tenRegistroGeneral,tenRegistroDetallado,tenTipoIdAportante,tenNumeroIdAportante,tenTipoIdCotizante,tenNumeroIdCotizante,tenTipoTransaccion,tenEsIngreso,tenEsRetiro,tenFechaInicioNovedad,tenFechaFinNovedad,tenAccionNovedad,tenMensajeNovedad,
		tenTipoCotizante,
		tenPrimerApellido,tenSegundoApellido,tenPrimerNombre,tenSegundoNombre,tenCodigoDepartamento,tenCodigoMunicipio,tenModalidadRecaudoAporte,
		tenEsEmpleadorReintegrable, tenEsTrabajadorReintegrable, tenRegistroDetalladoNovedad, tenEnProceso)
		SELECT tenIdTransaccion,0 as tenMarcaNovedadSimulado,tenMarcaNovedadManual,tenRegistroGeneral,tenRegistroDetallado,tenTipoIdAportante,tenNumeroIdAportante,tenTipoIdCotizante,tenNumeroIdCotizante,tenTipoTransaccion,tenEsIngreso,tenEsRetiro,tenFechaInicioNovedad,tenFechaFinNovedad,tenAccionNovedad,tenMensajeNovedad,
		CASE WHEN reg.regEsAportePensionados = 1 THEN 'PENSIONADO' 
			 ELSE (CASE  WHEN redTipoCotizante IN (SELECT id FROM @TiposCotizanteInd) THEN 'TRABAJADOR_INDEPENDIENTE' ELSE 'TRABAJADOR_DEPENDIENTE' END) END TipoCotizante,
		tenPrimerApellido,tenSegundoApellido,tenPrimerNombre,tenSegundoNombre,tenCodigoDepartamento,tenCodigoMunicipio,
		CASE WHEN tenMarcaNovedadSimulado = 1 THEN 'PILA_MANUAL' ELSE (CASE WHEN tenMarcaNovedadManual = 1 THEN 'MANUAL' ELSE 'PILA' END) END AS tenModalidadRecaudoAporte,
		ISNULL(reg.regOUTEsEmpleadorReintegrable,0), ISNULL(red.redOUTEsTrabajadorReintegrable,0), rdnId, tenEnProceso
		FROM #Novedades ten
		INNER JOIN staging.RegistroGeneral reg with (nolock) on ten.tenRegistroGeneral = reg.regId
		INNER JOIN staging.RegistroDetallado red with (nolock) on ten.tenRegistroDetallado = red.redId AND ten.tenRegistroGeneral = red.redRegistroGeneral AND red.redRegistroGeneral = reg.regId
		INNER JOIN staging.RegistroDetalladoNovedad rdn with (nolock) on ten.tenRegistroDetallado = rdn.rdnRegistroDetallado AND rdn.rdnRegistroDetallado = red.redId
			AND ((ISNULL(ten.tenEsIngreso, 0) = 1 AND rdn.rdnTipoNovedad = 'NOVEDAD_ING')
				OR (ISNULL(ten.tenEsRetiro, 0) = 1 AND rdn.rdnTipoNovedad = 'NOVEDAD_RET')
				OR ten.tenTipoTransaccion = (case when rdn.rdnTipotransaccion = N'CAMBIO_SUCURSAL_TRABAJADOR_DEPENDIENTE' then N'CAMBIO_SUCURSA_TRABAJADOR_DEPENDIENTE_DEPWEB' else rdn.rdnTipotransaccion end)
			)
		WHERE (red.redCorrecciones = 'C' OR red.redCorrecciones IS NULL)

		UPDATE reg
		SET reg.regOUTNovedadFuturaProcesada = 1,
		regDateTimeUpdate = dbo.getLocalDate()
		FROM staging.RegistroGeneral reg with (nolock)
		WHERE reg.regId = @IdRegistroGeneral
		AND reg.regOUTNovedadFuturaProcesada = 0
		

		DECLARE @estadoArchivo VARCHAR(60)

		SELECT @estadoArchivo = regOUTEstadoArchivo
		FROM staging.RegistroGeneral
		WHERE regId = @IdRegistroGeneral

		IF ISNULL(@estadoArchivo, '') != 'RECAUDO_NOTIFICADO'
		BEGIN
			SELECT @estadoArchivo = CASE WHEN @ProcesadoSinNovedades = 1 THEN 'PROCESADO_SIN_NOVEDADES' ELSE 'PROCESADO_NOVEDADES' END
		END

		UPDATE reg
		SET regOUTEstadoArchivo = @estadoArchivo,
			regOUTFinalizadoProcesoManual = CASE WHEN @EsRegistroManual = 1 THEN 1 ELSE regOUTFinalizadoProcesoManual END,
			regDateTimeUpdate = dbo.getLocalDate()
		FROM staging.RegistroGeneral reg with (nolock)
		WHERE reg.regId = @IdRegistroGeneral


	if (select top 1 r.regId
		from staging.RegistroGeneral as r with (nolock)
		inner join staging.RegistroDetallado as rd with (nolock) on r.regId = rd.redRegistroGeneral
		inner join staging.RegistroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
		where r.regId = @IdRegistroGeneral) > 1
		begin

			update rd set rd.redOUTRegistradoNovedad = 1
			from staging.RegistroGeneral as r with (nolock)
			inner join staging.RegistroDetallado as rd with (nolock) on r.regId = rd.redRegistroGeneral
			inner join staging.RegistroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
			where r.regId = @IdRegistroGeneral

		end

	update rd set rd.redOUTRegistrado = 1, rd.redOUTRegistradoAporte = 1
	from staging.RegistroGeneral as r with (nolock)
	inner join staging.RegistroDetallado as rd with (nolock) on r.regId = rd.redRegistroGeneral
	inner join staging.RegistroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
	where r.regId = @IdRegistroGeneral

	END

END;