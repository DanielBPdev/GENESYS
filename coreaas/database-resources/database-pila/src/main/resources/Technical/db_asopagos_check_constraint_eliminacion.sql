--liquibase formatted sql
--changeset mosanchez:01 runAlways:true runOnChange:true
--comment: Creaci�n de borrado de CKs 2018-02-22T20:51:36Z
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaArchivoFRegistro6_pf6EstadoConciliacion')) ALTER TABLE PilaArchivoFRegistro6 DROP CONSTRAINT CK_PilaArchivoFRegistro6_pf6EstadoConciliacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaCondicionAportanteVencimiento_pcaComparacion')) ALTER TABLE PilaCondicionAportanteVencimiento DROP CONSTRAINT CK_PilaCondicionAportanteVencimiento_pcaComparacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaNormatividadFechaVencimiento_pfvTipoFecha')) ALTER TABLE PilaNormatividadFechaVencimiento DROP CONSTRAINT CK_PilaNormatividadFechaVencimiento_pfvTipoFecha;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaOportunidadPresentacionPlanilla_popOportunidad')) ALTER TABLE PilaOportunidadPresentacionPlanilla DROP CONSTRAINT CK_PilaOportunidadPresentacionPlanilla_popOportunidad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaErrorValidacionLog_pevTipoArchivo')) ALTER TABLE PilaErrorValidacionLog DROP CONSTRAINT CK_PilaErrorValidacionLog_pevTipoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaErrorValidacionLog_pevTipoError')) ALTER TABLE PilaErrorValidacionLog DROP CONSTRAINT CK_PilaErrorValidacionLog_pevTipoError;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaErrorValidacionLog_pevBloqueValidacion')) ALTER TABLE PilaErrorValidacionLog DROP CONSTRAINT CK_PilaErrorValidacionLog_pevBloqueValidacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaErrorValidacionLog_pevEstadoInconsistencia')) ALTER TABLE PilaErrorValidacionLog DROP CONSTRAINT CK_PilaErrorValidacionLog_pevEstadoInconsistencia;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebTipoArchivo')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebTipoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque0')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque0')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque1')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque1')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque2')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque2;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque2')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque2;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque3')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque3;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque3')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque3;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque4')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque4;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque4')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque4;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque5')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque5;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque5')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque5;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque6')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque6;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque6')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque6;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque7')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque7;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque7')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque7;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque8')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque8;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque8')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque8;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque9')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque9;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque9')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque9;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque10')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque10;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque10')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque10;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoEstadoBloque0')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoEstadoBloque0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoAccionBloque0')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoAccionBloque0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoEstadoBloque1')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoEstadoBloque1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoAccionBloque1')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoAccionBloque1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoEstadoBloque6')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoEstadoBloque6;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoAccionBloque6')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoAccionBloque6;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanilla_pipTipoArchivo')) ALTER TABLE PilaIndicePlanilla DROP CONSTRAINT CK_PilaIndicePlanilla_pipTipoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanilla_pipEstadoArchivo')) ALTER TABLE PilaIndicePlanilla DROP CONSTRAINT CK_PilaIndicePlanilla_pipEstadoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanilla_pipTipoCargaArchivo')) ALTER TABLE PilaIndicePlanilla DROP CONSTRAINT CK_PilaIndicePlanilla_pipTipoCargaArchivo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanillaOF_pioTipoArchivo')) ALTER TABLE PilaIndicePlanillaOF DROP CONSTRAINT CK_PilaIndicePlanillaOF_pioTipoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanillaOF_pioTipoCargaArchivo')) ALTER TABLE PilaIndicePlanillaOF DROP CONSTRAINT CK_PilaIndicePlanillaOF_pioTipoCargaArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanillaOF_pioEstadoArchivo')) ALTER TABLE PilaIndicePlanillaOF DROP CONSTRAINT CK_PilaIndicePlanillaOF_pioEstadoArchivo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaSolicitudCambioNumIdentAportante_pscAccion')) ALTER TABLE PilaSolicitudCambioNumIdentAportante DROP CONSTRAINT CK_PilaSolicitudCambioNumIdentAportante_pscAccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaSolicitudCambioNumIdentAportante_pscEstadoArchivoAfectado')) ALTER TABLE PilaSolicitudCambioNumIdentAportante DROP CONSTRAINT CK_PilaSolicitudCambioNumIdentAportante_pscEstadoArchivoAfectado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaSolicitudCambioNumIdentAportante_pscRazonRechazo')) ALTER TABLE PilaSolicitudCambioNumIdentAportante DROP CONSTRAINT CK_PilaSolicitudCambioNumIdentAportante_pscRazonRechazo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaPasoValores_ppvTipoPlanilla')) ALTER TABLE PilaPasoValores DROP CONSTRAINT CK_PilaPasoValores_ppvTipoPlanilla;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaPasoValores_ppvBloque')) ALTER TABLE PilaPasoValores DROP CONSTRAINT CK_PilaPasoValores_ppvBloque;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaProceso_pprTipoProceso')) ALTER TABLE PilaProceso DROP CONSTRAINT CK_PilaProceso_pprTipoProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaProceso_pprEstadoProceso')) ALTER TABLE PilaProceso DROP CONSTRAINT CK_PilaProceso_pprEstadoProceso;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetallado_redTipoIdentificacionCotizante')) ALTER TABLE staging.RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redTipoIdentificacionCotizante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetallado_redEstadoEvaluacion')) ALTER TABLE staging.RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redEstadoEvaluacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetallado_redEstadoRegistroCorreccion')) ALTER TABLE staging.RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redEstadoRegistroCorreccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetallado_redOUTMarcaValRegistroAporte')) ALTER TABLE staging.RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTMarcaValRegistroAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetallado_redOUTEstadoRegistroAporte')) ALTER TABLE staging.RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTEstadoRegistroAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetallado_redOUTEstadoValidacionV0')) ALTER TABLE staging.RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTEstadoValidacionV0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetallado_redOUTEstadoValidacionV1')) ALTER TABLE staging.RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTEstadoValidacionV1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetallado_redOUTEstadoValidacionV2')) ALTER TABLE staging.RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTEstadoValidacionV2;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetallado_redOUTEstadoValidacionV3')) ALTER TABLE staging.RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTEstadoValidacionV3;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetallado_redOUTTipoAfiliado')) ALTER TABLE staging.RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTTipoAfiliado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetallado_redOUTEstadoRegistroRelacionAporte')) ALTER TABLE staging.RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTEstadoRegistroRelacionAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetallado_redOUTEstadoEvaluacionAporte')) ALTER TABLE staging.RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTEstadoEvaluacionAporte;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetalladoNovedad_rdnTipotransaccion')) ALTER TABLE staging.RegistroDetalladoNovedad DROP CONSTRAINT CK_RegistroDetalladoNovedad_rdnTipotransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroDetalladoNovedad_rdnAccionNovedad')) ALTER TABLE staging.RegistroDetalladoNovedad DROP CONSTRAINT CK_RegistroDetalladoNovedad_rdnAccionNovedad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroGeneral_regTipoIdentificacionAportante')) ALTER TABLE staging.RegistroGeneral DROP CONSTRAINT CK_RegistroGeneral_regTipoIdentificacionAportante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroGeneral_regOUTEstadoEmpleador')) ALTER TABLE staging.RegistroGeneral DROP CONSTRAINT CK_RegistroGeneral_regOUTEstadoEmpleador;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroGeneral_regOUTTipoBeneficio')) ALTER TABLE staging.RegistroGeneral DROP CONSTRAINT CK_RegistroGeneral_regOUTTipoBeneficio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroGeneral_regOUTEstadoArchivo')) ALTER TABLE staging.RegistroGeneral DROP CONSTRAINT CK_RegistroGeneral_regOUTEstadoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroGeneral_regEstadoEvaluacion')) ALTER TABLE staging.RegistroGeneral DROP CONSTRAINT CK_RegistroGeneral_regEstadoEvaluacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'staging.CK_RegistroGeneral_regOUTMotivoFiscalizacion')) ALTER TABLE staging.RegistroGeneral DROP CONSTRAINT CK_RegistroGeneral_regOUTMotivoFiscalizacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemAportante_tapTipoDocAportante')) ALTER TABLE TemAportante DROP CONSTRAINT CK_TemAportante_tapTipoDocAportante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemAportante_tapTipoDocTramitador')) ALTER TABLE TemAportante DROP CONSTRAINT CK_TemAportante_tapTipoDocTramitador;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemAportante_tapMotivoFiscalizacion')) ALTER TABLE TemAportante DROP CONSTRAINT CK_TemAportante_tapMotivoFiscalizacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemAporte_temTipoIdAportante')) ALTER TABLE TemAporte DROP CONSTRAINT CK_TemAporte_temTipoIdAportante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemAporte_temModalidadPlanilla')) ALTER TABLE TemAporte DROP CONSTRAINT CK_TemAporte_temModalidadPlanilla;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemAporte_temModalidadRecaudoAporte')) ALTER TABLE TemAporte DROP CONSTRAINT CK_TemAporte_temModalidadRecaudoAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemAporte_temFormaReconocimientoAporte')) ALTER TABLE TemAporte DROP CONSTRAINT CK_TemAporte_temFormaReconocimientoAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemAporte_temTipoIdCotizante')) ALTER TABLE TemAporte DROP CONSTRAINT CK_TemAporte_temTipoIdCotizante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemAporte_temEstadoAporteRecaudo')) ALTER TABLE TemAporte DROP CONSTRAINT CK_TemAporte_temEstadoAporteRecaudo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemAporte_temEstadoAporteAjuste')) ALTER TABLE TemAporte DROP CONSTRAINT CK_TemAporte_temEstadoAporteAjuste;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemAporte_temEstadoRegistroAporte')) ALTER TABLE TemAporte DROP CONSTRAINT CK_TemAporte_temEstadoRegistroAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemAporte_temMarcaValRegistroAporte')) ALTER TABLE TemAporte DROP CONSTRAINT CK_TemAporte_temMarcaValRegistroAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemAporte_temEstadoValRegistroAporte')) ALTER TABLE TemAporte DROP CONSTRAINT CK_TemAporte_temEstadoValRegistroAporte;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemCotizante_tctTipoIdCotizante')) ALTER TABLE TemCotizante DROP CONSTRAINT CK_TemCotizante_tctTipoIdCotizante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemCotizante_tctTipoIdEmpleador')) ALTER TABLE TemCotizante DROP CONSTRAINT CK_TemCotizante_tctTipoIdEmpleador;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemNovedad_tenModalidadRecaudoAporte')) ALTER TABLE TemNovedad DROP CONSTRAINT CK_TemNovedad_tenModalidadRecaudoAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TemNovedad_tenTipoTransaccion')) ALTER TABLE TemNovedad DROP CONSTRAINT CK_TemNovedad_tenTipoTransaccion;

