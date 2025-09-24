-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Update:      Juan Diego Ocampos Q
-- Update date: 2020/09/15
-- Description:	Procedimiento almacenado encargado  de cargar las tablas del esquema Staging  
-- por Id de Transaccion
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ExecuteBloqueStaging]
       @IdTransaccion Bigint,
       @bSuccess Bit = 0 OUTPUT
AS
BEGIN

SET NOCOUNT ON;

DECLARE @ErrorMessage NVARCHAR(4000);
DECLARE @ErrorSeverity INT;
DECLARE @ErrorState INT;

--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
BEGIN TRY

	declare @regId bigInt = -5
	set @regId = (select regId from staging.RegistroGeneral where regTransaccion = @IdTransaccion)

	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate();

	-- Crear información realacionada a partir de la DB de core
	EXECUTE USP_ExecuteIntegrationResource @IdTransaccion = @IdTransaccion;

	DECLARE @SMMLV NUMERIC(19,5);
	SELECT @SMMLV = stpValorParametro FROM staging.StagingParametros WHERE stpNombreParametro = 'SMMLV';
	
	DECLARE @TarifaBaseEmpleador NUMERIC(19,5);
	SELECT @TarifaBaseEmpleador = stpValorParametro FROM staging.StagingParametros WHERE stpNombreParametro = 'TARIFA_BASE_EMPLEADOR';

	DECLARE @RegistroGeneralTmp AS TABLE (
		regIdTmp bigint not null primary key,
		regOUTTarifaEmpleadorTmp numeric(5, 5) NULL,
		regOUTEsEmpleadorTmp bit NULL,
		regOUTEstadoEmpleadorTmp varchar(50) NULL,
		regOUTTipoBeneficioTmp varchar(10) NULL,
		regOUTBeneficioActivoTmp bit NULL,
		regOUTEsEmpleadorReintegrableTmp bit NULL,
		regOUTTarifaBaseEmpleadorTmp numeric(5, 5) NULL,
		regOUTSMMLVTmp numeric(19, 5) NULL,
		regOUTMarcaSucursalPILATmp bit NULL,
		regOUTCodSucursalPrincipalTmp varchar(10) NULL,
		regOUTNomSucursalPrincipalTmp varchar(100) NULL
	);
	
	CREATE TABLE #RegistroDetalladoTmp (
		redIdTmp bigint NULL,
		redOUTClaseTrabajadorTmp varchar(50) NULL,
		redOUTPorcentajePagoAportesTmp numeric(5, 5) NULL,
		redOUTEstadoSolicitanteTmp varchar(50) NULL,
		redOUTEsTrabajadorReintegrableTmp bit NULL,
		redOUTFechaIngresoCotizanteTmp date NULL,
		redOUTFechaUltimaNovedadTmp date NULL,
		redOUTFechaFallecimientoTmp date NULL,
		redOUTCodSucursalTmp varchar(10) NULL,
		redOUTNomSucursalTmp varchar(100) NULL,
		redOUTDiasCotizadosPlanillasTmp smallint NULL,
		redOUTDiasCotizadosBDTmp smallint NULL,
		redOUTDiasCotizadosNovedadesTmp smallint NULL,
		redOUTTipoAfiliadoTmp varchar(50) NULL,
		redOUTValorMoraCotizanteTmp numeric(19, 5) NULL,
		redOUTTipoNovedadSituacionPrimariaTmp varchar(15) NULL,
		redOUTFechaInicioNovedadSituacionPrimariaTmp date NULL,
		redOUTFechaFinNovedadSituacionPrimariaTmp date NULL,
		redOUTFechaRetiroCotizanteTmp date NULL,
		redOUTDiasCotizadosNovedadesBDTmp smallint NULL,
		redOUTGrupoFamiliarReintegrableTmp bit NULL,
		redOUTPeriodicidadTmp varchar(11) NULL
	);
	
	-- PASO 1
	INSERT INTO @RegistroGeneralTMP (
		regIdTmp 
		,regOUTEsEmpleadorTmp
		,regOUTEstadoEmpleadorTmp 
		,regOUTTipoBeneficioTmp
		,regOUTBeneficioActivoTmp 
		,regOUTTarifaEmpleadorTmp
		,regOUTEsEmpleadorReintegrableTmp
		,regOUTTarifaBaseEmpleadorTmp
		,regOUTSMMLVTmp
		,regOUTMarcaSucursalPILATmp 
		,regOUTCodSucursalPrincipalTmp
		,regOUTNomSucursalPrincipalTmp
		)
	SELECT
		regId 
		,apoEsEmpleador 
		,apoEstadoEmpleador  
		,bepTipoBeneficio 
		,bepBeneficioActivo  
		,ISNULL(bep.bepTarifaEmpleador, @TarifaBaseEmpleador) 
		,ISNULL(apoEsEmpleadorReintegrable, 0) 
		,@TarifaBaseEmpleador 
		,@SMMLV 
		,apo.apoMarcaSucursalPILA  
		,sue.sueCodigoSucursal 
		,sue.sueNombreSucursal 
	FROM staging.RegistroGeneral reg
	LEFT JOIN staging.Aportante apo ON 
		reg.regTipoIdentificacionAportante = apo.apoTipoIdentificacion 
		AND reg.regNumeroIdentificacionAportante = apo.apoNumeroIdentificacion 
		AND apo.apoTransaccion = reg.regTransaccion
	LEFT JOIN staging.BeneficioEmpresaPeriodo bep ON  
		reg.regTipoIdentificacionAportante = bep.bepTipoIdentificacion 
		AND reg.regNumeroIdentificacionAportante = bep.bepNumeroIdentificacion 
		AND reg.regPeriodoAporte = bep.bepPeriodoAporte 
		AND bep.bepTransaccion = reg.regTransaccion
	LEFT JOIN staging.SucursalEmpresa sue ON 
		reg.regTipoIdentificacionAportante = sue.sueTipoIdentificacion 
		AND reg.regNumeroIdentificacionAportante = sue.sueNumeroIdentificacion 
		AND sue.sueEsSucursalPrincipal = 1 
		AND sue.sueTransaccion = reg.regTransaccion
		AND apo.apoTransaccion = reg.regTransaccion
	--WHERE regTransaccion = @IdTransaccion;
	where reg.regId = @regId

	UPDATE reg
	SET
		regOUTEsEmpleador = regOUTEsEmpleadorTmp
		,regOUTEstadoEmpleador = regOUTEstadoEmpleadorTmp
		,regOUTTipoBeneficio = regOUTTipoBeneficioTmp
		,regOUTBeneficioActivo = regOUTBeneficioActivoTmp
		,regOUTTarifaEmpleador = regOUTTarifaEmpleadorTmp
		,regOUTEsEmpleadorReintegrable = regOUTEsEmpleadorReintegrableTmp
		,regOUTTarifaBaseEmpleador = regOUTTarifaBaseEmpleadorTmp
		,regOUTSMMLV = regOUTSMMLVTmp
		,regOUTMarcaSucursalPILA = regOUTMarcaSucursalPILATmp
		,regOUTCodSucursalPrincipal = regOUTCodSucursalPrincipalTmp
		,regOUTNomSucursalPrincipal = regOUTNomSucursalPrincipalTmp
		,regDateTimeUpdate = dbo.getLocalDate()
	FROM staging.RegistroGeneral reg
	INNER JOIN @RegistroGeneralTMP ON regId = regIdTmp  ;
	
	DELETE FROM @RegistroGeneralTMP;
	-- FIN PASO 1
	
	DECLARE @TablaIndependientes AS TABLE (tipoCotizante smallInt);

	INSERT INTO @TablaIndependientes
	SELECT Data FROM dbo.Split( (
		SELECT stpValorParametro FROM staging.StagingParametros 
		WHERE stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE') ,',');

	DECLARE @EstadosArchivoPostAportes AS TABLE (estado VARCHAR(60));

	INSERT INTO @EstadosArchivoPostAportes (estado)
	VALUES	('REGISTRADO_O_RELACIONADO_LOS_APORTES'),
			('PROCESADO_NOVEDADES'),
			('PROCESADO_SIN_NOVEDADES'),
			('RECAUDO_NOTIFICADO'),
			('RECAUDO_NOTIFICADO_MANUAL'),
			('NOTIFICACION_RECAUDO_FALLIDO');

	-- PASO 2
	--Actualizacion Flujo Dependientes
	INSERT INTO #RegistroDetalladoTmp (
		redIdTmp
		,redOUTClaseTrabajadorTmp
		,redOUTPorcentajePagoAportesTmp
		,redOUTEstadoSolicitanteTmp
		,redOUTEsTrabajadorReintegrableTmp
		,redOUTGrupoFamiliarReintegrableTmp
		,redOUTFechaIngresoCotizanteTmp
		,redOUTFechaRetiroCotizanteTmp
		,redOUTFechaUltimaNovedadTmp
		,redOUTFechaFallecimientoTmp
		,redOUTCodSucursalTmp
		,redOUTNomSucursalTmp
		,redOUTDiasCotizadosPlanillasTmp
		,redOUTDiasCotizadosBDTmp
		,redOUTDiasCotizadosNovedadesTmp
		,redOUTTipoAfiliadoTmp
		,redOUTValorMoraCotizanteTmp
		,redOUTDiasCotizadosNovedadesBDTmp 
		,redOUTTipoNovedadSituacionPrimariaTmp
		,redOUTFechaInicioNovedadSituacionPrimariaTmp
		,redOUTFechaFinNovedadSituacionPrimariaTmp
		,redOUTPeriodicidadTmp
		)
	SELECT 
		redId
		,ct.cotClaseTrabajador
		,ct.cotPorcentajePagoAportes
		,ct.cotEstadoAfiliado
		,ISNULL(ct.cotEsTrabajadorReintegrable, 0)
		,CASE WHEN ISNULL(ct.cotEsTrabajadorReintegrable, 0) = 0 THEN 0 ELSE ISNULL(ct.cotGrupoFamiliarReintegrable, 0) END
		,ct.cotFechaIngreso
		,ct.cotFechaRetiro
		,ct.cotFechaUltimaNovedad
		,ct.cotFechaFallecido
		,ct.cotCodigoSucursal
		,ct.cotNombreSucursal
		,0
		,ISNULL(appDiasCotizados,0)
		,ISNULL(redOUTDiasCotizadosNovedades,0) --Se calcula en HU396
		,ISNULL(ct.cotTipoAfiliado, 'TRABAJADOR_DEPENDIENTE')
		,CASE WHEN redOUTValorMoraCotizante IS NOT NULL THEN redOUTValorMoraCotizante
			ELSE (
				CASE WHEN regValTotalApoObligatorio = 0 THEN 0 ELSE
					(redAporteObligatorio * regValorIntMora) / regValTotalApoObligatorio
				END
			)
		END
		,diasNov.dncDiasNovedad 
		,nspTipoNovedad
		,nspFechaInicio
		,nspFechaFin
		,cotPeriodicidad
	FROM staging.RegistroGeneral reg with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral
	LEFT JOIN staging.Cotizante ct ON red.redTipoIdentificacionCotizante = ct.cotTipoIdentificacion
								AND red.redNumeroIdentificacionCotizante = ct.cotNumeroIdentificacion 
								AND reg.regTipoIdentificacionAportante = ct.cotTipoIdentificacionEmpleador
								AND reg.regNumeroIdentificacionAportante = ct.cotNumeroIdentificacionEmpleador
								AND ct.cotPeriodoAporte = reg.regPeriodoAporte
								AND ct.cotTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
								AND ct.cotTransaccion = reg.regTransaccion
	LEFT JOIN [staging].[AportePeriodo] app ON	reg.regPeriodoAporte = app.appPeriodoAporte
												AND reg.regTipoIdentificacionAportante = app.appTipoIdentificacionAportante
												AND reg.regNumeroIdentificacionAportante = app.appNumeroIdentificacionAportante
												AND red.redTipoIdentificacionCotizante = app.appTipoIdentificacionCotizante
												AND red.redNumeroIdentificacionCotizante = app.appNumeroIdentificacionCotizante
												AND reg.regTransaccion = app.appTransaccion
	LEFT JOIN Staging.NovedadSituacionPrimaria nsp ON reg.regPeriodoAporte = nsp.nspPeriodoRegular
												AND reg.regTipoIdentificacionAportante = nsp.nspTipoIdentificacionAportante
												AND reg.regNumeroIdentificacionAportante = nsp.nspNumeroIdentificacionAportante
												AND red.redTipoIdentificacionCotizante = nsp.nspTipoIdentificacionCotizante
												AND red.redNumeroIdentificacionCotizante = nsp.nspNumeroIdentificacionCotizante
												AND reg.regTransaccion = nsp.nspTransaccion
	LEFT JOIN (
				SELECT dnc.dncTipoIdCotizante, dnc.dncNumeroIdCotizante, dnc.dncTransaccion, dnc.dncPeriodo, MAX(dnc.dncDiasNovedad) dncDiasNovedad
				FROM Staging.DiasNovedadCore dnc 
				GROUP BY dnc.dncTipoIdCotizante, dnc.dncNumeroIdCotizante, dnc.dncTransaccion, dnc.dncPeriodo) diasNov 
											ON red.redTipoIdentificacionCotizante = diasNov.dncTipoIdCotizante
												AND red.redNumeroIdentificacionCotizante = diasNov.dncNumeroIdCotizante
												AND reg.regTransaccion = diasNov.dncTransaccion
												AND reg.regPeriodoAporte = diasNov.dncPeriodo
	WHERE --regTransaccion = @IdTransaccion
	reg.regId = @regId
	and reg.regEsAportePensionados = 0
	AND ISNULL(red.redTipoCotizante,0) NOT IN (SELECT tipoCotizante FROM @TablaIndependientes);
	
	create clustered index IXNC_tempredId on #RegistroDetalladoTmp (redIdTmp)

	declare @contPaso1 int = (select count(*) from #RegistroDetalladoTmp)

	UPDATE top (@contPaso1) red
	SET
		redDateTimeUpdate = @redDateTimeUpdate
		,redOUTClaseTrabajador = redOUTClaseTrabajadorTmp
		,redOUTPorcentajePagoAportes = redOUTPorcentajePagoAportesTmp 
		,redOUTEstadoSolicitante = redOUTEstadoSolicitanteTmp
		,redOUTEsTrabajadorReintegrable = redOUTEsTrabajadorReintegrableTmp
		,redOUTGrupoFamiliarReintegrable = redOUTGrupoFamiliarReintegrableTmp
		,redOUTFechaIngresoCotizante = redOUTFechaIngresoCotizanteTmp
		,redOUTFechaRetiroCotizante = redOUTFechaRetiroCotizanteTmp
		,redOUTFechaUltimaNovedad = redOUTFechaUltimaNovedadTmp
		,redOUTFechaFallecimiento = redOUTFechaFallecimientoTmp
		,redOUTCodSucursal = redOUTCodSucursalTmp
		,redOUTNomSucursal = redOUTNomSucursalTmp
		,redOUTDiasCotizadosPlanillas = redOUTDiasCotizadosPlanillasTmp
		,redOUTDiasCotizadosBD = redOUTDiasCotizadosBDTmp
		,redOUTDiasCotizadosNovedades = redOUTDiasCotizadosNovedadesTmp
		,redOUTTipoAfiliado = redOUTTipoAfiliadoTmp
		,redOUTValorMoraCotizante = redOUTValorMoraCotizanteTmp
		,redOUTDiasCotizadosNovedadesBD = redOUTDiasCotizadosNovedadesBDTmp 
		,redOUTTipoNovedadSituacionPrimaria = redOUTTipoNovedadSituacionPrimariaTmp
		,redOUTFechaInicioNovedadSituacionPrimaria = redOUTFechaInicioNovedadSituacionPrimariaTmp
		,redOUTFechaFinNovedadSituacionPrimaria = redOUTFechaFinNovedadSituacionPrimariaTmp
		,redOUTPeriodicidad = redOUTPeriodicidadTmp
	FROM staging.RegistroDetallado red with (nolock)
	INNER JOIN #RegistroDetalladoTmp ON redIdTmp = redId ;
		
	DELETE FROM #RegistroDetalladoTmp;
	-- FIN PASO 2
	
	--Actualizacion Flujo Dependientes - Dias Cotizados planillas para NO Simulacion

	-- PASO 3
	SELECT	regPeriodoAporte,
				regTipoIdentificacionAportante, regNumeroIdentificacionAportante,
				redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,  SUM(ISNULL(redDiasCotizados,0)) redDiasCotizados
	INTO #sumReg
	FROM staging.RegistroGeneral reg with (nolock)
	INNER JOIN staging.RegistroDetallado red (nolock) ON reg.regId = red.redRegistroGeneral
	WHERE --regTransaccion = @IdTransaccion
	reg.regId = @regId
	AND reg.regEsSimulado = 0
	AND ISNULL(red.redCorrecciones,'') NOT IN ('A', 'C') 
	GROUP BY regPeriodoAporte, regTipoIdentificacionAportante, regNumeroIdentificacionAportante, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante
				
	CREATE CLUSTERED INDEX IX_sumReg ON #sumReg (regPeriodoAporte,
											regTipoIdentificacionAportante,
											regNumeroIdentificacionAportante,
											redTipoIdentificacionCotizante,
											redNumeroIdentificacionCotizante);

	INSERT INTO #RegistroDetalladoTmp (
		redIdTmp
		,redOUTDiasCotizadosPlanillasTmp
		)
	SELECT 
		redId
		,CASE WHEN ISNULL(redOUTDiasCotizadosPlanillas, 0) + ISNULL(sumReg.redDiasCotizados,0) > 30 THEN 30
			ELSE ISNULL(redOUTDiasCotizadosPlanillas, 0) + ISNULL(sumReg.redDiasCotizados,0) END
	FROM staging.RegistroGeneral reg with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral	
	LEFT JOIN #sumReg 
						AS sumReg ON sumReg.regPeriodoAporte = reg.regPeriodoAporte
				AND sumReg.regTipoIdentificacionAportante = reg.regTipoIdentificacionAportante 
				AND sumReg.regNumeroIdentificacionAportante = reg.regNumeroIdentificacionAportante
				AND sumReg.redTipoIdentificacionCotizante = red.redTipoIdentificacionCotizante 
				AND sumReg.redNumeroIdentificacionCotizante = red.redNumeroIdentificacionCotizante	
	WHERE --regTransaccion = @IdTransaccion
	reg.regId = @regId
	and reg.regEsAportePensionados = 0
	AND ISNULL(red.redTipoCotizante,0) NOT IN (SELECT tipoCotizante FROM @TablaIndependientes)
	AND regOUTEstadoArchivo NOT IN (SELECT estado FROM @EstadosArchivoPostAportes)
	AND reg.regEsSimulado = 0;

	declare @contPaso3 int = (select count(*) from #RegistroDetalladoTmp)
	
	UPDATE top (@contPaso3) red
	SET 
		redDateTimeUpdate = @redDateTimeUpdate,
		redOUTDiasCotizadosPlanillas = redOUTDiasCotizadosPlanillasTmp
	FROM staging.RegistroDetallado red with (nolock)
	INNER JOIN #RegistroDetalladoTmp ON redIdTmp = redId; 
		
	DELETE FROM #RegistroDetalladoTmp;
	-- FIN PASO 3
	
	-- PASO 4

	declare @periodoAct varchar(7) = (select regPeriodoAporte from staging.RegistroGeneral where regTransaccion = @IdTransaccion)

	SELECT	regPeriodoAporte,
				regTipoIdentificacionAportante, regNumeroIdentificacionAportante,
				redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,  SUM(ISNULL(redDiasCotizados,0)) redDiasCotizados
	into #sumRegI
	FROM staging.RegistroGeneral reg with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral 
	WHERE reg.regPeriodoAporte = @periodoAct
	AND regTransaccion IS NULL
	and redOUTEstadoValidacionV0 = 'CUMPLE' AND redOUTEstadoValidacionV2 = 'OK'
	and regOUTEstadoArchivo NOT IN (SELECT estado FROM @EstadosArchivoPostAportes)
	AND ISNULL(red.redCorrecciones,'') NOT IN ('A', 'C') 
	GROUP BY regPeriodoAporte, regTipoIdentificacionAportante, regNumeroIdentificacionAportante, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante
		

	CREATE CLUSTERED INDEX IX_sumRegI ON #sumRegI (regPeriodoAporte,
											regTipoIdentificacionAportante,
											regNumeroIdentificacionAportante,
											redTipoIdentificacionCotizante,
											redNumeroIdentificacionCotizante);


	INSERT INTO #RegistroDetalladoTmp (
		redIdTmp
		,redOUTDiasCotizadosPlanillasTmp
		)
	SELECT 
		redId
		,CASE WHEN ISNULL(redOUTDiasCotizadosPlanillas, 0) + ISNULL(sumRegI.redDiasCotizados,0) > 30 THEN 30
			ELSE ISNULL(redOUTDiasCotizadosPlanillas, 0) + ISNULL(sumRegI.redDiasCotizados,0) END
	FROM staging.RegistroGeneral reg with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral	
	LEFT JOIN #sumRegI AS sumRegI ON sumRegI.regPeriodoAporte = reg.regPeriodoAporte
				AND sumRegI.regTipoIdentificacionAportante = reg.regTipoIdentificacionAportante 
				AND sumRegI.regNumeroIdentificacionAportante = reg.regNumeroIdentificacionAportante
				AND sumRegI.redTipoIdentificacionCotizante = red.redTipoIdentificacionCotizante 
				AND sumRegI.redNumeroIdentificacionCotizante = red.redNumeroIdentificacionCotizante	
	WHERE --regTransaccion = @IdTransaccion
	reg.regId = @regId
	and reg.regEsAportePensionados = 0
	AND ISNULL(red.redTipoCotizante,0) NOT IN (SELECT tipoCotizante FROM @TablaIndependientes)
	AND regOUTEstadoArchivo NOT IN (SELECT estado FROM @EstadosArchivoPostAportes)
	AND reg.regEsSimulado = 0;
	
	declare @contPaso4 int = (select count(*) from #RegistroDetalladoTmp)

	UPDATE top (@contPaso4) red
	SET 
		redDateTimeUpdate = @redDateTimeUpdate,
		redOUTDiasCotizadosPlanillas = redOUTDiasCotizadosPlanillasTmp
	FROM staging.RegistroDetallado red with (nolock)
	INNER JOIN #RegistroDetalladoTmp ON redIdTmp = redId; 
		
	DELETE FROM #RegistroDetalladoTmp;
	-- FIN PASO 4
	
	
	-- PASO 5
	--Actualizacion Flujo Dependientes - Dias Cotizados planillas para Simulacion
	INSERT INTO #RegistroDetalladoTmp (
		redIdTmp
		,redOUTDiasCotizadosPlanillasTmp
		)
	SELECT 
		redId
		,CASE WHEN ISNULL(redOUTDiasCotizadosPlanillas, 0) + ISNULL(sumReg.redDiasCotizados,0) > 30 THEN 30
			ELSE ISNULL(redOUTDiasCotizadosPlanillas, 0) + ISNULL(sumReg.redDiasCotizados,0) END
	FROM staging.RegistroGeneral reg with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral	
	LEFT JOIN (	SELECT	regPeriodoAporte,
							regTipoIdentificacionAportante, regNumeroIdentificacionAportante,
							redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,  SUM(ISNULL(redDiasCotizados,0)) redDiasCotizados
				FROM staging.RegistroDetallado red with (nolock)
				INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
				WHERE regTransaccion = @IdTransaccion
				AND ISNULL(red.redCorrecciones,'') <> 'A'
				AND reg.regEsSimulado = 1
				GROUP BY regPeriodoAporte, regTipoIdentificacionAportante, regNumeroIdentificacionAportante, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante
				) AS sumReg ON sumReg.regPeriodoAporte = reg.regPeriodoAporte
				AND sumReg.regTipoIdentificacionAportante = reg.regTipoIdentificacionAportante 
				AND sumReg.regNumeroIdentificacionAportante = reg.regNumeroIdentificacionAportante
				AND sumReg.redTipoIdentificacionCotizante = red.redTipoIdentificacionCotizante 
				AND sumReg.redNumeroIdentificacionCotizante = red.redNumeroIdentificacionCotizante	
	WHERE -- regTransaccion = @IdTransaccion
	reg.regId = @regId
	and reg.regEsAportePensionados = 0
	AND ISNULL(red.redTipoCotizante,0) NOT IN (SELECT tipoCotizante FROM @TablaIndependientes)
	AND regOUTEstadoArchivo NOT IN (SELECT estado FROM @EstadosArchivoPostAportes)
	AND reg.regEsSimulado = 1;
	
	declare @contPaso5 int = (select count(*) from #RegistroDetalladoTmp)

	UPDATE top (@contPaso5) red
	SET 
		redDateTimeUpdate = @redDateTimeUpdate,
		redOUTDiasCotizadosPlanillas = redOUTDiasCotizadosPlanillasTmp
	FROM staging.RegistroDetallado red with (nolock)
	INNER JOIN #RegistroDetalladoTmp ON redIdTmp = redId;
		
	DELETE FROM #RegistroDetalladoTmp;
	-- FIN PASO 5
				
	-- PASO 6
	INSERT INTO #RegistroDetalladoTmp (
		redIdTmp
		,redOUTDiasCotizadosPlanillasTmp
		)
	SELECT 
		redId
		,CASE WHEN ISNULL(redOUTDiasCotizadosPlanillas, 0) + ISNULL(sumRegI.redDiasCotizados,0) > 30 THEN 30
			ELSE ISNULL(redOUTDiasCotizadosPlanillas, 0) + ISNULL(sumRegI.redDiasCotizados,0) END
	FROM staging.RegistroGeneral reg with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral		
	LEFT JOIN (	SELECT	regPeriodoAporte,
							regTipoIdentificacionAportante, regNumeroIdentificacionAportante,
							redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,  SUM(ISNULL(redDiasCotizados,0)) redDiasCotizados
				FROM staging.RegistroDetallado red with (nolock)
				INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
				WHERE regOUTEstadoArchivo NOT IN (SELECT estado FROM @EstadosArchivoPostAportes)
				AND redOUTEstadoValidacionV0 = 'CUMPLE' AND redOUTEstadoValidacionV2 = 'OK'
				AND ISNULL(red.redCorrecciones,'') <> 'A'
				AND regTransaccion IS NULL
				GROUP BY regPeriodoAporte, regTipoIdentificacionAportante, regNumeroIdentificacionAportante, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante
				) AS sumRegI ON sumRegI.regPeriodoAporte = reg.regPeriodoAporte
				AND sumRegI.regTipoIdentificacionAportante = reg.regTipoIdentificacionAportante 
				AND sumRegI.regNumeroIdentificacionAportante = reg.regNumeroIdentificacionAportante
				AND sumRegI.redTipoIdentificacionCotizante = red.redTipoIdentificacionCotizante 
				AND sumRegI.redNumeroIdentificacionCotizante = red.redNumeroIdentificacionCotizante	
	WHERE --regTransaccion = @IdTransaccion
	reg.regId = @regId
	and reg.regEsAportePensionados = 0
	AND ISNULL(red.redTipoCotizante,0) NOT IN (SELECT tipoCotizante FROM @TablaIndependientes)
	AND regOUTEstadoArchivo NOT IN (SELECT estado FROM @EstadosArchivoPostAportes)
	AND reg.regEsSimulado = 1;

	declare @contPaso6 int = (select count(*) from #RegistroDetalladoTmp)

	UPDATE top (@contPaso6) red
	SET 
		redDateTimeUpdate = @redDateTimeUpdate,
		redOUTDiasCotizadosPlanillas = redOUTDiasCotizadosPlanillasTmp
	FROM staging.RegistroDetallado red with (nolock)
	INNER JOIN #RegistroDetalladoTmp ON redIdTmp = redId;
		
	DELETE FROM #RegistroDetalladoTmp;
	-- FIN PASO 6

	-- PASO 7
	--Actualizacion Flujo Independientes
	INSERT INTO #RegistroDetalladoTmp (
		redIdTmp
		,redOUTClaseTrabajadorTmp
		,redOUTPorcentajePagoAportesTmp
		,redOUTEstadoSolicitanteTmp
		,redOUTEsTrabajadorReintegrableTmp
		,redOUTGrupoFamiliarReintegrableTmp
		,redOUTFechaIngresoCotizanteTmp
		,redOUTFechaRetiroCotizanteTmp
		,redOUTFechaUltimaNovedadTmp
		,redOUTFechaFallecimientoTmp
		,redOUTCodSucursalTmp
		,redOUTNomSucursalTmp
		,redOUTTipoAfiliadoTmp
		,redOUTValorMoraCotizanteTmp
		,redOUTPeriodicidadTmp
		)
	SELECT 
		redId
		,ct.cotClaseTrabajador
		,ct.cotPorcentajePagoAportes
		,ct.cotEstadoAfiliado
		,ISNULL(ct.cotEsTrabajadorReintegrable, 0)
		,CASE WHEN ISNULL(ct.cotEsTrabajadorReintegrable, 0) = 0 THEN 0 ELSE ISNULL(ct.cotGrupoFamiliarReintegrable, 0) END
		,ct.cotFechaIngreso
		,ct.cotFechaRetiro
		,ct.cotFechaUltimaNovedad
		,ct.cotFechaFallecido
		,ct.cotCodigoSucursal
		,ct.cotNombreSucursal
		,ISNULL(ct.cotTipoAfiliado, 'TRABAJADOR_INDEPENDIENTE')
		,CASE WHEN redOUTValorMoraCotizante IS NOT NULL THEN redOUTValorMoraCotizante
			ELSE (
				CASE WHEN regValTotalApoObligatorio = 0 THEN 0 ELSE
					(redAporteObligatorio * regValorIntMora) / regValTotalApoObligatorio
				END
			)
		END
		,cotPeriodicidad
	FROM staging.RegistroGeneral reg with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral
	JOIN staging.Cotizante ct ON red.redTipoIdentificacionCotizante = ct.cotTipoIdentificacion
								AND red.redNumeroIdentificacionCotizante = ct.cotNumeroIdentificacion 
								AND reg.regTipoIdentificacionAportante = ct.cotTipoIdentificacionEmpleador
								AND reg.regNumeroIdentificacionAportante = ct.cotNumeroIdentificacionEmpleador
								AND ct.cotPeriodoAporte = reg.regPeriodoAporte
								AND ct.cotTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
								AND ct.cotTransaccion = reg.regTransaccion
	WHERE --regTransaccion = @IdTransaccion
	reg.regId = @regId
	and reg.regEsAportePensionados = 0
	AND red.redTipoCotizante IN (SELECT tipoCotizante FROM @TablaIndependientes)

	declare @contPaso7 int = (select count(*) from #RegistroDetalladoTmp)
	
	UPDATE top (@contPaso7) red
	SET
		redDateTimeUpdate = @redDateTimeUpdate
		,redOUTClaseTrabajador = redOUTClaseTrabajadorTmp
		,redOUTPorcentajePagoAportes = redOUTPorcentajePagoAportesTmp
		,redOUTEstadoSolicitante = redOUTEstadoSolicitanteTmp
		,redOUTEsTrabajadorReintegrable = redOUTEsTrabajadorReintegrableTmp
		,redOUTGrupoFamiliarReintegrable = redOUTGrupoFamiliarReintegrableTmp
		,redOUTFechaIngresoCotizante = redOUTFechaIngresoCotizanteTmp
		,redOUTFechaRetiroCotizante = redOUTFechaRetiroCotizanteTmp
		,redOUTFechaUltimaNovedad = redOUTFechaUltimaNovedadTmp
		,redOUTFechaFallecimiento = redOUTFechaFallecimientoTmp
		,redOUTCodSucursal = redOUTCodSucursalTmp
		,redOUTNomSucursal = redOUTNomSucursalTmp
		,redOUTTipoAfiliado = redOUTTipoAfiliadoTmp
		,redOUTValorMoraCotizante = redOUTValorMoraCotizanteTmp
		,redOUTPeriodicidad = redOUTPeriodicidadTmp
	FROM staging.RegistroDetallado red with (nolock)
	INNER JOIN #RegistroDetalladoTmp ON redIdTmp = redId;
	
	DELETE FROM #RegistroDetalladoTmp;
	-- FIN PASO 7

	-- PASO 8
	--Actualizacion Flujo Pensionados
	INSERT INTO #RegistroDetalladoTmp (
		redIdTmp
		,redOUTClaseTrabajadorTmp
		,redOUTPorcentajePagoAportesTmp
		,redOUTEstadoSolicitanteTmp
		,redOUTEsTrabajadorReintegrableTmp
		,redOUTGrupoFamiliarReintegrableTmp
		,redOUTFechaIngresoCotizanteTmp
		,redOUTFechaRetiroCotizanteTmp
		,redOUTFechaUltimaNovedadTmp
		,redOUTFechaFallecimientoTmp
		,redOUTCodSucursalTmp
		,redOUTNomSucursalTmp
		,redOUTTipoAfiliadoTmp
		,redOUTValorMoraCotizanteTmp
		,redOUTPeriodicidadTmp
		)
	SELECT 
		redId
		,ct.cotClaseTrabajador
		,ct.cotPorcentajePagoAportes
		,ct.cotEstadoAfiliado
		,ISNULL(ct.cotEsTrabajadorReintegrable, 0)
		,CASE WHEN ISNULL(ct.cotEsTrabajadorReintegrable, 0) = 0 THEN 0 ELSE ISNULL(ct.cotGrupoFamiliarReintegrable, 0) END
		,ct.cotFechaIngreso
		,ct.cotFechaRetiro
		,ct.cotFechaUltimaNovedad
		,ct.cotFechaFallecido
		,ct.cotCodigoSucursal
		,ct.cotNombreSucursal
		,ISNULL(ct.cotTipoAfiliado, 'PENSIONADO')
		,CASE WHEN redOUTValorMoraCotizante IS NOT NULL THEN redOUTValorMoraCotizante
			ELSE (
				CASE WHEN regValTotalApoObligatorio = 0 THEN 0 ELSE
					(redAporteObligatorio * regValorIntMora) / regValTotalApoObligatorio
				END
			)
		END
		,cotPeriodicidad
	FROM staging.RegistroGeneral reg with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral
	LEFT JOIN staging.Cotizante ct ON red.redTipoIdentificacionCotizante = ct.cotTipoIdentificacion
								AND red.redNumeroIdentificacionCotizante = ct.cotNumeroIdentificacion 
								AND reg.regTipoIdentificacionAportante = ct.cotTipoIdentificacionEmpleador
								AND reg.regNumeroIdentificacionAportante = ct.cotNumeroIdentificacionEmpleador
								AND ct.cotPeriodoAporte = reg.regPeriodoAporte
								AND ct.cotTipoAfiliado = 'PENSIONADO'
								AND ct.cotTransaccion = reg.regTransaccion
	WHERE --regTransaccion = @IdTransaccion
	reg.regId = @regId
	and reg.regEsAportePensionados = 1;
	
	declare @contPaso8 int = (select count(*) from #RegistroDetalladoTmp)

	UPDATE top (@contPaso8) red
	SET
		redDateTimeUpdate = @redDateTimeUpdate
		,redOUTClaseTrabajador = redOUTClaseTrabajadorTmp
		,redOUTPorcentajePagoAportes = redOUTPorcentajePagoAportesTmp
		,redOUTEstadoSolicitante = redOUTEstadoSolicitanteTmp
		,redOUTEsTrabajadorReintegrable = redOUTEsTrabajadorReintegrableTmp
		,redOUTGrupoFamiliarReintegrable = redOUTGrupoFamiliarReintegrableTmp
		,redOUTFechaIngresoCotizante = redOUTFechaIngresoCotizanteTmp
		,redOUTFechaRetiroCotizante = redOUTFechaRetiroCotizanteTmp
		,redOUTFechaUltimaNovedad = redOUTFechaUltimaNovedadTmp
		,redOUTFechaFallecimiento = redOUTFechaFallecimientoTmp
		,redOUTCodSucursal = redOUTCodSucursalTmp
		,redOUTNomSucursal = redOUTNomSucursalTmp
		,redOUTTipoAfiliado = redOUTTipoAfiliadoTmp
		,redOUTValorMoraCotizante = redOUTValorMoraCotizanteTmp
		,redOUTPeriodicidad = redOUTPeriodicidadTmp
	FROM staging.RegistroDetallado red with (nolock)
	INNER JOIN #RegistroDetalladoTmp ON redIdTmp = redId;
	
	DELETE FROM #RegistroDetalladoTmp;
	-- FIN PASO 8

    SET @bSuccess = 1;
END TRY
BEGIN CATCH
	IF XACT_STATE() <> 0 AND @@TRANCOUNT > 0
	BEGIN
		ROLLBACK
	END

    SELECT @ErrorMessage = '[dbo].[USP_ExecuteBloqueStaging]|' + ERROR_MESSAGE(),
           @ErrorSeverity = ERROR_SEVERITY(),
           @ErrorState = ERROR_STATE();

	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.GetLocalDate(), 
		'@IdTransaccion = ' + CAST(@IdTransaccion AS VARCHAR), @ErrorMessage);

    RAISERROR (@ErrorMessage, -- Message text.
               --@ErrorSeverity, -- Severity.
               14, 
               @ErrorState -- State.
               );  
    
END CATCH;
END;