-- =============================================
-- Author:		Diego Suesca
-- Create date: 2021/05/18
-- Description:	
-- =============================================
create PROCEDURE [dbo].[USP_CopiarAportesDesdeTemporalPilaCorrecciones]
	@idRegistroGeneral BIGINT,
    @codigoSucursalPila VARCHAR(10)
AS
BEGIN
SET NOCOUNT ON;
BEGIN TRY
    TRUNCATE TABLE #AporteGeneralTtemporal;
    TRUNCATE TABLE #AporteDetalladoTemporal;
    TRUNCATE TABLE #MovimientoAporteTemporal;
    TRUNCATE TABLE #AporteGeneralIdPorTranaccion;

    DECLARE @iRevision BIGINT;
	DECLARE @localDate DATETIME = dbo.getLocalDate()

    ---------------------------------------------------
    ---------------------------------------------------
    -- APORTE GENERAL
    ---------------------------------------------------
    ---------------------------------------------------

    -- APORTANTE PERSONA
    UPDATE apg 
    SET apg.apgPeriodoAporte = temPeriodoAporte,
        apg.apgValTotalApoObligatorio = temValTotalApoObligatorio,
        apg.apgValorIntMora = temValorIntMoraGeneral,
        apg.apgFechaRecaudo = temFechaRecaudo,
        apg.apgFechaProcesamiento = @localDate,
        apg.apgCodEntidadFinanciera = temCodEntidadFinanciera,
        apg.apgOperadorInformacion = (SELECT oinId FROM OperadorInformacion WHERE oinCodigo = temOperadorInformacion),
        apg.apgModalidadPlanilla = temModalidadPlanilla,
        apg.apgModalidadRecaudoAporte = temModalidadRecaudoAporte,
        apg.apgApoConDetalle = temApoConDetalle,
        apg.apgNumeroCuenta = temNumeroCuenta,
        --apg.apgRegistroGeneral = temRegistroGeneral,
        apg.apgRegistroGeneralUltimo = temRegistroGeneral,
        --apg.apgPersona = CASE WHEN (tapTipoSolicitud = 'PERSONA' OR tapTipoSolicitud = 'ENTIDAD_PAGADORA') THEN perId ELSE NULL END ,
        --apg.apgEmpresa = CASE WHEN tapTipoSolicitud = 'EMPLEADOR' THEN (SELECT empId FROM Empresa WHERE empPersona = perId) ELSE NULL END ,
        apg.apgSucursalEmpresa = CASE 
                                    WHEN tapTipoSolicitud = 'EMPLEADOR' 
                                    THEN (
                                        SELECT TOP 1 sueId 
                                        FROM Empresa emp1
                                        INNER JOIN SucursalEmpresa sue1 ON sue1.sueEmpresa = emp1.empId
                                        WHERE emp1.empPersona = per.perId
                                        AND @codigoSucursalPila = sue1.sueCodigo
                                        ORDER BY sueId DESC
                                    ) 
                                    ELSE NULL
                                END, -- TODO : verificar sucursal PILA que se tiene en TemCotizante y marca tapMarcaSucursal en TemAportante,
        apg.apgEstadoAportante = CASE WHEN eacEstadoAportante IS NOT NULL THEN eacEstadoAportante ELSE 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' END, -- TODO: verificar porque ISNULL trunca el valor por defecto
        apg.apgEstadoAporteAportante = temEstadoAporteRecaudo,
        apg.apgEstadoRegistroAporteAportante = temEstadoRegistroAporte,
        apg.apgPagadorPorTerceros = (SELECT
                                    CASE WHEN (tctTipoCotizante = 'TRABAJADOR_INDEPENDIENTE') THEN
                                        CASE WHEN (temTipoIdCotizante = tapTipoDocAportante AND temNumeroIdCotizante = tapIdAportante) THEN 0 ELSE 1 END
                                    ELSE 0 END
                                FROM pila.TemCotizante WHERE tctIdTransaccion = temIdTransaccion),
        apg.apgTipoSolicitante = (SELECT CASE WHEN (tctTipoCotizante = 'TRABAJADOR_DEPENDIENTE') THEN 'EMPLEADOR' WHEN (tctTipoCotizante = 'TRABAJADOR_INDEPENDIENTE') THEN 'INDEPENDIENTE' ELSE 'PENSIONADO' END FROM pila.TemCotizante WHERE tctIdTransaccion = temIdTransaccion),
        apg.apgOrigenAporte =  NULL,-- ????
        apg.apgCajaCompensacion =  NULL,-- ????
        apg.apgEmailAportante =  tapEmail,
        apg.apgEmpresaTramitadoraAporte =  tramitadorEmpId,
        apg.apgFechaReconocimiento =  CASE WHEN (temFormaReconocimientoAporte IS NOT NULL) THEN @localDate ELSE NULL END,
        apg.apgFormaReconocimientoAporte =  temFormaReconocimientoAporte, --temEstadoRegistroAporte == 'REGISTRADO' ? RECONOCIMIENTO_AUTOMATICO_OPORTUNO else NULL
        apg.apgMarcaPeriodo =  eacMarcaPeriodo,
        apg.apgMarcaActualizacionCartera =  1, --aporte.getAporteGeneral().setMarcaActualizacionCartera(Boolean.TRUE);
        apg.apgConciliado =  NULL, -- NULL SIEMPRE?
        apg.apgNumeroPlanillaManual =  temNumeroPlanillaManual,
        apg.apgEnProcesoReconocimiento =  0 -- nunca se usa???
    OUTPUT 
        INSERTED.apgId
        ,INSERTED.apgPeriodoAporte
        ,INSERTED.apgValTotalApoObligatorio
        ,INSERTED.apgValorIntMora
        ,INSERTED.apgFechaRecaudo
        ,INSERTED.apgFechaProcesamiento
        ,INSERTED.apgCodEntidadFinanciera
        ,INSERTED.apgOperadorInformacion
        ,INSERTED.apgModalidadPlanilla
        ,INSERTED.apgModalidadRecaudoAporte
        ,INSERTED.apgApoConDetalle
        ,INSERTED.apgNumeroCuenta
        ,INSERTED.apgRegistroGeneral
        ,INSERTED.apgPersona
        ,INSERTED.apgEmpresa
        ,INSERTED.apgSucursalEmpresa
        ,INSERTED.apgEstadoAportante
        ,INSERTED.apgEstadoAporteAportante
        ,INSERTED.apgEstadoRegistroAporteAportante
        ,INSERTED.apgPagadorPorTerceros
        ,INSERTED.apgTipoSolicitante
        ,INSERTED.apgOrigenAporte
        ,INSERTED.apgCajaCompensacion
        ,INSERTED.apgEmailAportante
        ,INSERTED.apgEmpresaTramitadoraAporte
        ,INSERTED.apgFechaReconocimiento
        ,INSERTED.apgFormaReconocimientoAporte
        ,INSERTED.apgMarcaPeriodo
        ,INSERTED.apgMarcaActualizacionCartera
        ,INSERTED.apgConciliado
        ,INSERTED.apgNumeroPlanillaManual
        ,INSERTED.apgEnProcesoReconocimiento
        ,INSERTED.apgRegistroGeneralUltimo
    INTO #AporteGeneralTtemporal
    FROM pila.TemAporte tem
    JOIN pila.TemAportante ON tapIdTransaccion = tem.temIdTransaccion
    JOIN Persona per ON temTipoIdAportante = perTipoIdentificacion AND temNumeroIdAportante = perNumeroIdentificacion
    LEFT JOIN #EstadoAportanteCotizante ON temIdTransaccion = eacIdTransaccion
    LEFT JOIN #Tramitadores ON tramitadorIdTransaccion = tapIdTransaccion
    JOIN (SELECT DISTINCT redRegistroGeneral, redRegistroGeneralOri
                FROM #planillasCorreccionUpdate pcr
                ) pcr	ON tem.temRegistroGeneral = pcr.redRegistroGeneral
    JOIN AporteGeneral apg ON apg.apgRegistroGeneralUltimo = pcr.redRegistroGeneralOri
                            AND apgPersona = per.perId
    WHERE temRegistroGeneral = @idRegistroGeneral 
        AND apgPersona IS NOT NULL

        

    -- AUDITORIA APORTANTE PERSONA
    IF EXISTS (SELECT TOP 1 1 FROM #AporteGeneralTtemporal)
    BEGIN
        EXEC USP_UTIL_GET_CrearRevisionActualizar  'com.asopagos.entidades.ccf.aportes.AporteGeneral', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT;

        INSERT INTO aud.AporteGeneral_aud(
            apgId
            ,apgPeriodoAporte
            ,apgValTotalApoObligatorio
            ,apgValorIntMora
            ,apgFechaRecaudo
            ,apgFechaProcesamiento
            ,apgCodEntidadFinanciera
            ,apgOperadorInformacion
            ,apgModalidadPlanilla
            ,apgModalidadRecaudoAporte
            ,apgApoConDetalle
            ,apgNumeroCuenta
            ,apgRegistroGeneral
            ,apgPersona
            ,apgEmpresa
            ,apgSucursalEmpresa
            ,apgEstadoAportante
            ,apgEstadoAporteAportante
            ,apgEstadoRegistroAporteAportante
            ,apgPagadorPorTerceros
            ,apgTipoSolicitante
            ,apgOrigenAporte
            ,apgCajaCompensacion
            ,apgEmailAportante
            ,apgEmpresaTramitadoraAporte
            ,apgFechaReconocimiento
            ,apgFormaReconocimientoAporte
            ,apgMarcaPeriodo
            ,apgMarcaActualizacionCartera
            ,apgConciliado
            ,apgNumeroPlanillaManual
            ,apgEnProcesoReconocimiento
            ,apgRegistroGeneralUltimo
            ,REV, REVTYPE
        )
        SELECT
            apgid
            ,apgPeriodoAporte
            ,apgValTotalApoObligatorio
            ,apgValorIntMora
            ,apgFechaRecaudo
            ,apgFechaProcesamiento
            ,apgCodEntidadFinanciera
            ,apgOperadorInformacion
            ,apgModalidadPlanilla
            ,apgModalidadRecaudoAporte
            ,apgApoConDetalle
            ,apgNumeroCuenta
            ,apgRegistroGeneral
            ,apgPersona
            ,apgEmpresa
            ,apgSucursalEmpresa
            ,apgEstadoAportante
            ,apgEstadoAporteAportante
            ,apgEstadoRegistroAporteAportante
            ,apgPagadorPorTerceros
            ,apgTipoSolicitante
            ,apgOrigenAporte
            ,apgCajaCompensacion
            ,apgEmailAportante
            ,apgEmpresaTramitadoraAporte
            ,apgFechaReconocimiento
            ,apgFormaReconocimientoAporte
            ,apgMarcaPeriodo
            ,apgMarcaActualizacionCartera
            ,apgConciliado
            ,apgNumeroPlanillaManual
            ,apgEnProcesoReconocimiento
            ,apgRegistroGeneralUltimo
            ,@iRevision, 1
        FROM #AporteGeneralTtemporal;
    END;

    TRUNCATE TABLE #AporteGeneralTtemporal
        -- APORTANTE EMPLEADOR
    UPDATE apg 
    SET apg.apgPeriodoAporte = temPeriodoAporte,
        apg.apgValTotalApoObligatorio = temValTotalApoObligatorio,
        apg.apgValorIntMora = temValorIntMoraGeneral,
        apg.apgFechaRecaudo = temFechaRecaudo,
        apg.apgFechaProcesamiento = @localDate,
        apg.apgCodEntidadFinanciera = temCodEntidadFinanciera,
        apg.apgOperadorInformacion = (SELECT oinId FROM OperadorInformacion WHERE oinCodigo = temOperadorInformacion),
        apg.apgModalidadPlanilla = temModalidadPlanilla,
        apg.apgModalidadRecaudoAporte = temModalidadRecaudoAporte,
        apg.apgApoConDetalle = temApoConDetalle,
        apg.apgNumeroCuenta = temNumeroCuenta,
        --apg.apgRegistroGeneral = temRegistroGeneral,
        apg.apgRegistroGeneralUltimo = temRegistroGeneral,
        --apg.apgPersona = CASE WHEN (tapTipoSolicitud = 'PERSONA' OR tapTipoSolicitud = 'ENTIDAD_PAGADORA') THEN perId ELSE NULL END ,
        --apg.apgEmpresa = CASE WHEN tapTipoSolicitud = 'EMPLEADOR' THEN (SELECT empId FROM Empresa WHERE empPersona = perId) ELSE NULL END ,
        apg.apgSucursalEmpresa = CASE 
                                    WHEN tapTipoSolicitud = 'EMPLEADOR' 
                                    THEN (
                                        SELECT TOP 1 sueId 
                                        FROM Empresa emp1
                                        INNER JOIN SucursalEmpresa sue1 ON sue1.sueEmpresa = emp1.empId
                                        WHERE emp1.empPersona = per.perId
                                        AND @codigoSucursalPila = sue1.sueCodigo
                                        ORDER BY sueId DESC
                                    ) 
                                    ELSE NULL
                                END, -- TODO : verificar sucursal PILA que se tiene en TemCotizante y marca tapMarcaSucursal en TemAportante,
        apg.apgEstadoAportante = CASE WHEN eacEstadoAportante IS NOT NULL THEN eacEstadoAportante ELSE 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' END, -- TODO: verificar porque ISNULL trunca el valor por defecto
        apg.apgEstadoAporteAportante = temEstadoAporteRecaudo,
        apg.apgEstadoRegistroAporteAportante = temEstadoRegistroAporte,
        apg.apgPagadorPorTerceros = (SELECT
                                    CASE WHEN (tctTipoCotizante = 'TRABAJADOR_INDEPENDIENTE') THEN
                                        CASE WHEN (temTipoIdCotizante = tapTipoDocAportante AND temNumeroIdCotizante = tapIdAportante) THEN 0 ELSE 1 END
                                    ELSE 0 END
                                FROM pila.TemCotizante WHERE tctIdTransaccion = temIdTransaccion),
        apg.apgTipoSolicitante = (SELECT CASE WHEN (tctTipoCotizante = 'TRABAJADOR_DEPENDIENTE') THEN 'EMPLEADOR' WHEN (tctTipoCotizante = 'TRABAJADOR_INDEPENDIENTE') THEN 'INDEPENDIENTE' ELSE 'PENSIONADO' END FROM pila.TemCotizante WHERE tctIdTransaccion = temIdTransaccion),
        apg.apgOrigenAporte =  NULL,-- ????
        apg.apgCajaCompensacion =  NULL,-- ????
        apg.apgEmailAportante =  tapEmail,
        apg.apgEmpresaTramitadoraAporte =  tramitadorEmpId,
        apg.apgFechaReconocimiento =  CASE WHEN (temFormaReconocimientoAporte IS NOT NULL) THEN @localDate ELSE NULL END,
        apg.apgFormaReconocimientoAporte =  temFormaReconocimientoAporte, --temEstadoRegistroAporte == 'REGISTRADO' ? RECONOCIMIENTO_AUTOMATICO_OPORTUNO else NULL
        apg.apgMarcaPeriodo =  eacMarcaPeriodo,
        apg.apgMarcaActualizacionCartera =  1, --aporte.getAporteGeneral().setMarcaActualizacionCartera(Boolean.TRUE);
        apg.apgConciliado =  NULL, -- NULL SIEMPRE?
        apg.apgNumeroPlanillaManual =  temNumeroPlanillaManual,
        apg.apgEnProcesoReconocimiento =  0 -- nunca se usa???
    OUTPUT 
        INSERTED.apgId
        ,INSERTED.apgPeriodoAporte
        ,INSERTED.apgValTotalApoObligatorio
        ,INSERTED.apgValorIntMora
        ,INSERTED.apgFechaRecaudo
        ,INSERTED.apgFechaProcesamiento
        ,INSERTED.apgCodEntidadFinanciera
        ,INSERTED.apgOperadorInformacion
        ,INSERTED.apgModalidadPlanilla
        ,INSERTED.apgModalidadRecaudoAporte
        ,INSERTED.apgApoConDetalle
        ,INSERTED.apgNumeroCuenta
        ,INSERTED.apgRegistroGeneral
        ,INSERTED.apgPersona
        ,INSERTED.apgEmpresa
        ,INSERTED.apgSucursalEmpresa
        ,INSERTED.apgEstadoAportante
        ,INSERTED.apgEstadoAporteAportante
        ,INSERTED.apgEstadoRegistroAporteAportante
        ,INSERTED.apgPagadorPorTerceros
        ,INSERTED.apgTipoSolicitante
        ,INSERTED.apgOrigenAporte
        ,INSERTED.apgCajaCompensacion
        ,INSERTED.apgEmailAportante
        ,INSERTED.apgEmpresaTramitadoraAporte
        ,INSERTED.apgFechaReconocimiento
        ,INSERTED.apgFormaReconocimientoAporte
        ,INSERTED.apgMarcaPeriodo
        ,INSERTED.apgMarcaActualizacionCartera
        ,INSERTED.apgConciliado
        ,INSERTED.apgNumeroPlanillaManual
        ,INSERTED.apgEnProcesoReconocimiento
        ,INSERTED.apgRegistroGeneralUltimo
    INTO #AporteGeneralTtemporal
     FROM pila.TemAporte tem
    JOIN pila.TemAportante ON tapIdTransaccion = tem.temIdTransaccion
    JOIN Persona per ON temTipoIdAportante = perTipoIdentificacion AND temNumeroIdAportante = perNumeroIdentificacion
    LEFT JOIN #EstadoAportanteCotizante ON temIdTransaccion = eacIdTransaccion
    LEFT JOIN #Tramitadores ON tramitadorIdTransaccion = tapIdTransaccion
    JOIN (SELECT DISTINCT redRegistroGeneral, redRegistroGeneralOri
                FROM #planillasCorreccionUpdate pcr
                ) pcr	ON tem.temRegistroGeneral = pcr.redRegistroGeneral
    JOIN Empresa emp ON emp.empPersona = per.perId
    JOIN AporteGeneral apg ON apg.apgRegistroGeneralUltimo = pcr.redRegistroGeneralOri
                            AND apg.apgEmpresa = emp.empId
    WHERE temRegistroGeneral = @idRegistroGeneral 
        AND apgEmpresa IS NOT NULL       

    

    -- AUDITORIA APORTANTE EMPRESA
    IF EXISTS (SELECT TOP 1 1 FROM #AporteGeneralTtemporal)
    BEGIN
        EXEC USP_UTIL_GET_CrearRevisionActualizar  'com.asopagos.entidades.ccf.aportes.AporteGeneral', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT   

        INSERT INTO aud.AporteGeneral_aud(
            apgId
            ,apgPeriodoAporte
            ,apgValTotalApoObligatorio
            ,apgValorIntMora
            ,apgFechaRecaudo
            ,apgFechaProcesamiento
            ,apgCodEntidadFinanciera
            ,apgOperadorInformacion
            ,apgModalidadPlanilla
            ,apgModalidadRecaudoAporte
            ,apgApoConDetalle
            ,apgNumeroCuenta
            ,apgRegistroGeneral
            ,apgPersona
            ,apgEmpresa
            ,apgSucursalEmpresa
            ,apgEstadoAportante
            ,apgEstadoAporteAportante
            ,apgEstadoRegistroAporteAportante
            ,apgPagadorPorTerceros
            ,apgTipoSolicitante
            ,apgOrigenAporte
            ,apgCajaCompensacion
            ,apgEmailAportante
            ,apgEmpresaTramitadoraAporte
            ,apgFechaReconocimiento
            ,apgFormaReconocimientoAporte
            ,apgMarcaPeriodo
            ,apgMarcaActualizacionCartera
            ,apgConciliado
            ,apgNumeroPlanillaManual
            ,apgEnProcesoReconocimiento
            ,apgRegistroGeneralUltimo
            ,REV, REVTYPE
        )
        SELECT
            apgid
            ,apgPeriodoAporte
            ,apgValTotalApoObligatorio
            ,apgValorIntMora
            ,apgFechaRecaudo
            ,apgFechaProcesamiento
            ,apgCodEntidadFinanciera
            ,apgOperadorInformacion
            ,apgModalidadPlanilla
            ,apgModalidadRecaudoAporte
            ,apgApoConDetalle
            ,apgNumeroCuenta
            ,apgRegistroGeneral
            ,apgPersona
            ,apgEmpresa
            ,apgSucursalEmpresa
            ,apgEstadoAportante
            ,apgEstadoAporteAportante
            ,apgEstadoRegistroAporteAportante
            ,apgPagadorPorTerceros
            ,apgTipoSolicitante
            ,apgOrigenAporte
            ,apgCajaCompensacion
            ,apgEmailAportante
            ,apgEmpresaTramitadoraAporte
            ,apgFechaReconocimiento
            ,apgFormaReconocimientoAporte
            ,apgMarcaPeriodo
            ,apgMarcaActualizacionCartera
            ,apgConciliado
            ,apgNumeroPlanillaManual
            ,apgEnProcesoReconocimiento
            ,apgRegistroGeneralUltimo
            ,@iRevision, 1
        FROM #AporteGeneralTtemporal
    END


    INSERT INTO #AporteGeneralIdPorTranaccion (
        aptId, 
        aptIdTransaccion
    )
    SELECT apgId, eacIdTransaccion
    FROM #AporteGeneralTtemporal
    INNER JOIN #EstadoAportanteCotizante eacPer ON  apgPersona = eacPer.eacPerid
    WHERE apgEmpresa IS NULL
    GROUP BY apgId, eacIdTransaccion
    
    INSERT INTO #AporteGeneralIdPorTranaccion (
        aptId, 
        aptIdTransaccion
    )
    SELECT apgId, eacIdTransaccion
    FROM #AporteGeneralTtemporal
    INNER JOIN #EstadoAportanteCotizante ON  apgEmpresa = eacEmpId
    WHERE apgPersona IS NULL
    GROUP BY apgId, eacIdTransaccion	

    
    ---------------------------------------------------
    ---------------------------------------------------
    -- APORTE DETALLADO -------------------------------------------------------------------------------------
    ---------------------------------------------------
    ---------------------------------------------------

    UPDATE apd
    SET --apd.apdAporteGeneral = aptId,
        apd.apdDiasCotizados = temDiasCotizados,
        apd.apdHorasLaboradas = temHorasLaboradas,
        apd.apdSalarioBasico = temSalarioBasico,
        apd.apdValorIBC = temValorIBC,
        apd.apdValorIntMora = temValorIntMoraDetalle,
        apd.apdTarifa = temTarifa,
        apd.apdAporteObligatorio = temAporteObligatorio,
        apd.apdValorSaldoAporte = temValorSaldoAporte,
        apd.apdCorrecciones = temCorrecciones,
        apd.apdEstadoAporteRecaudo = temEstadoAporteRecaudo,
        apd.apdEstadoAporteAjuste = temEstadoAporteAjuste,
        apd.apdEstadoRegistroAporte = temEstadoRegistroAporte,
        apd.apdSalarioIntegral = temSalarioIntegral,
        apd.apdMunicipioLaboral = temMunicipioLaboral,
        apd.apdDepartamentoLaboral = temDepartamentoLaboral,
        --apd.apdRegistroDetallado = temIdTransaccion,
        apd.apdRegistroDetalladoUltimo = temIdTransaccion,
        apd.apdTipoCotizante = tctTipoCotizante,
        apd.apdEstadoCotizante = CASE WHEN (eacEstadoCotizante IS NOT NULL ) THEN eacEstadoCotizante ELSE 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' END,
        apd.apdEstadoAporteCotizante = temEstadoAporteRecaudo,
        apd.apdEstadoRegistroAporteCotizante = temEstadoRegistroAporte,
        --apd.apdPersona = perId,
        apd.apdUsuarioAprobadorAporte = 'SISTEMA',
        apd.apdEstadoRegistroAporteArchivo = temEstadoValRegistroAporte,
        apd.apdCodSucursal = suc.sucCodSucursal,
        apd.apdNomSucursal = suc.sucNomSucursal,
        apd.apdFechaMovimiento = @localDate,--TODO: solo si hay movimientos
        --apd.apdFechaCreacion = @localDate,
        apd.apdFormaReconocimientoAporte = temFormaReconocimientoAporte,
        apd.apdMarcaPeriodo = eacMarcaPeriodo,
        apd.apdModalidadRecaudoAporte = temModalidadRecaudoAporte,
        apd.apdMarcaCalculoCategoria = 1,
        apd.apdModificadoAportesOK = NULL -- SIEMPRE?
    OUTPUT
        INSERTED.apdId,
        INSERTED.apdAporteGeneral,
        INSERTED.apdDiasCotizados,
        INSERTED.apdHorasLaboradas,
        INSERTED.apdSalarioBasico,
        INSERTED.apdValorIBC,
        INSERTED.apdValorIntMora,
        INSERTED.apdTarifa,
        INSERTED.apdAporteObligatorio,
        INSERTED.apdValorSaldoAporte,
        INSERTED.apdCorrecciones,
        INSERTED.apdEstadoAporteRecaudo,
        INSERTED.apdEstadoAporteAjuste,
        INSERTED.apdEstadoRegistroAporte,
        INSERTED.apdSalarioIntegral,
        INSERTED.apdMunicipioLaboral,
        INSERTED.apdDepartamentoLaboral,
        INSERTED.apdRegistroDetallado,
        INSERTED.apdTipoCotizante,
        INSERTED.apdEstadoCotizante,
        INSERTED.apdEstadoAporteCotizante,
        INSERTED.apdEstadoRegistroAporteCotizante,
        INSERTED.apdPersona,
        INSERTED.apdUsuarioAprobadorAporte,
        INSERTED.apdEstadoRegistroAporteArchivo,
        INSERTED.apdCodSucursal,
        INSERTED.apdNomSucursal,
        INSERTED.apdFechaMovimiento,
        INSERTED.apdFechaCreacion,
        INSERTED.apdFormaReconocimientoAporte,
        INSERTED.apdMarcaPeriodo,
        INSERTED.apdModalidadRecaudoAporte,
        INSERTED.apdMarcaCalculoCategoria,
        INSERTED.apdModificadoAportesOK,
        INSERTED.apdRegistroDetalladoUltimo
    INTO #AporteDetalladoTemporal
   FROM pila.TemAporte tem
    JOIN pila.TemCotizante ON tctIdTransaccion = tem.temIdTransaccion
    JOIN Persona p ON temTipoIdCotizante = perTipoIdentificacion AND temNumeroIdCotizante = perNumeroIdentificacion
    JOIN #AporteGeneralIdPorTranaccion ON aptIdTransaccion = temIdTransaccion
    JOIN #Sucursal suc ON suc.sucIdTransaccion = tem.temIdTransaccion 
    LEFT JOIN #EstadoAportanteCotizante ON temIdTransaccion = eacIdTransaccion
        JOIN (SELECT DISTINCT redId, redIdOri
                FROM #planillasCorreccionUpdate pcr
                ) pcr	ON tem.temRegistroDetallado = pcr.redId
    JOIN AporteDetallado apd ON apd.apdRegistroDetalladoUltimo = pcr.redIdOri
                            AND apd.apdPersona = perId
    JOIN Persona per on per.perId = apd.apdPersona
    WHERE temRegistroGeneral = @idRegistroGeneral
  

    IF EXISTS (SELECT TOP 1 1 FROM #AporteDetalladoTemporal)
    BEGIN 
        EXEC USP_UTIL_GET_CrearRevisionActualizar 'com.asopagos.entidades.ccf.aportes.AporteDetallado', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT
    
        INSERT INTO aud.AporteDetallado_aud(
        apdId,
        apdAporteGeneral,
        apdDiasCotizados,
        apdHorasLaboradas,
        apdSalarioBasico,
        apdValorIBC,
        apdValorIntMora,
        apdTarifa,
        apdAporteObligatorio,
        apdValorSaldoAporte,
        apdCorrecciones,
        apdEstadoAporteRecaudo,
        apdEstadoAporteAjuste,
        apdEstadoRegistroAporte,
        apdSalarioIntegral,
        apdMunicipioLaboral,
        apdDepartamentoLaboral,
        apdRegistroDetallado,
        apdTipoCotizante,
        apdEstadoCotizante,
        apdEstadoAporteCotizante,
        apdEstadoRegistroAporteCotizante,
        apdPersona,
        apdUsuarioAprobadorAporte,
        apdEstadoRegistroAporteArchivo,
        apdCodSucursal,
        apdNomSucursal,
        apdFechaMovimiento,
        apdFechaCreacion,
        apdFormaReconocimientoAporte,
        apdMarcaPeriodo,
        apdModalidadRecaudoAporte,
        apdMarcaCalculoCategoria,
        apdModificadoAportesOK,
        apdRegistroDetalladoUltimo,
        REV, REVTYPE
        )
        SELECT 
        apdId,
        apdAporteGeneral,
        apdDiasCotizados,
        apdHorasLaboradas,
        apdSalarioBasico,
        apdValorIBC,
        apdValorIntMora,
        apdTarifa,
        apdAporteObligatorio,
        apdValorSaldoAporte,
        apdCorrecciones,
        apdEstadoAporteRecaudo,
        apdEstadoAporteAjuste,
        apdEstadoRegistroAporte,
        apdSalarioIntegral,
        apdMunicipioLaboral,
        apdDepartamentoLaboral,
        apdRegistroDetallado,
        apdTipoCotizante,
        apdEstadoCotizante,
        apdEstadoAporteCotizante,
        apdEstadoRegistroAporteCotizante,
        apdPersona,
        apdUsuarioAprobadorAporte,
        apdEstadoRegistroAporteArchivo,
        apdCodSucursal,
        apdNomSucursal,
        apdFechaMovimiento,
        apdFechaCreacion,
        apdFormaReconocimientoAporte,
        apdMarcaPeriodo,
        apdModalidadRecaudoAporte,
        apdMarcaCalculoCategoria,
        apdModificadoAportesOK,
        apdRegistroDetalladoUltimo,
        @iRevision, 1
        FROM #AporteDetalladoTemporal
    END
		  

    ---------------------------------------------------
    ---------------------------------------------------
    -- MOVIMIENTO APORTE
    ---------------------------------------------------
    ---------------------------------------------------

    INSERT INTO MovimientoAporte (
        moaTipoAjuste,
        moaTipoMovimiento,
        moaEstadoAporte,
        moaValorAporte,
        moaValorInteres,
        moaFechaActualizacionEstado,
        moaFechaCreacion,
        moaAporteDetallado,
        moaAporteGeneral
    )
    OUTPUT
        INSERTED.moaId,
        INSERTED.moaTipoAjuste,
        INSERTED.moaTipoMovimiento,
        INSERTED.moaEstadoAporte,
        INSERTED.moaValorAporte,
        INSERTED.moaValorInteres,
        INSERTED.moaFechaActualizacionEstado,
        INSERTED.moaFechaCreacion,
        INSERTED.moaAporteDetallado,
        INSERTED.moaAporteGeneral
    INTO #MovimientoAporteTemporal
    SELECT
        'CORRECCION_A_LA_BAJA',--this.movimiento.setTipoAjuste(null);
        'CORRECCION_APORTES',
        'VIGENTE',--moaEstadoAporte this.movimiento.setEstado(EstadoAporteEnum.VIGENTE);
        apdAporteObligatorio,--this.movimiento.setAporte(this.getAporteDetallado().getAporteObligatorio());
        apdValorIntMora,--this.movimiento.setInteres(this.getAporteDetallado().getValorMora());
        @localDate,--this.movimiento.setFechaActualizacionEstado(new Date());
        @localDate,--this.movimiento.setFechaCreacion(new Date());
        apdId,
        apdAporteGeneral
    FROM #AporteDetalladoTemporal

    IF EXISTS (SELECT TOP 1 1 FROM #MovimientoAporteTemporal)
    BEGIN
    
        EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.aportes.MovimientoAporte', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT
        
        INSERT INTO aud.MovimientoAporte_aud(
            moaId,
            moaTipoAjuste,
            moaTipoMovimiento,
            moaEstadoAporte,
            moaValorAporte,
            moaValorInteres,
            moaFechaActualizacionEstado,
            moaFechaCreacion,
            moaAporteDetallado,
            moaAporteGeneral,
            REV, REVTYPE
        )
        SELECT
            moaId,
            moaTipoAjuste,
            moaTipoMovimiento,
            moaEstadoAporte,
            moaValorAporte,
            moaValorInteres,
            moaFechaActualizacionEstado,
            moaFechaCreacion,
            moaAporteDetallado,
            moaAporteGeneral,
            @iRevision, 0
        FROM #MovimientoAporteTemporal
    END

END TRY
BEGIN CATCH    
	THROW;
END CATCH
END;