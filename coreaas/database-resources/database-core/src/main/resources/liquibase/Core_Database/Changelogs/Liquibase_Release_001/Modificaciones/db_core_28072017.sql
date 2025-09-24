--liquibase formatted sql

--changeset alopez:01
--comment: Se eliminan registros en la tabla parametro
DELETE FROM Parametro WHERE prmNombre in('IDM_EMPLEADORES_WEB_CLIENT_SECRET','IDM_EMPLEADORES_WEB_DOMAIN_NAME','IDM_EMPLEADORES_WEB_CLIENT_ID');

--changeset silopez:02
--comment: Se agrega campo a la tabla ParametrizacionMetodoAsignacion
ALTER TABLE ParametrizacionMetodoAsignacion ADD pmaSedeCajaDestino BIGINT NULL;

--changeset flopez:03
--comment: Se actualizan registros de la tabla ParametrizacionNovedad y se eliminan registros de la tabla ValidacionProceso
--Actualización de Novedades de Medios de Pago a TIPO 'GRUPO_FAMILIAR'
UPDATE ParametrizacionNovedad SET novTipoNovedad = 'GRUPO_FAMILIAR' WHERE novTipoTransaccion IN ('CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_PRESENCIAL','CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_WEB','CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_DEPWEB','CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_PRESENCIAL','CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_WEB','CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_DEPWEB','CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE','CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE_WEB','CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_ADMINISTRADOR_SUBSIDIO','CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_ADMINISTRADOR_SUBSIDIO_WEB','CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_PRESENCIAL','CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_WEB','CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_DEPWEB','CAMBIO_DATOS_DE_CUENTA_ADMINISTRADOR_SUBSIDIO');

--Eliminar validaciones de RNs 64 y 65 a las novedades anteriores.
DELETE FROM ValidacionProceso WHERE vapBloque IN ('CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_PRESENCIAL','CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_WEB','CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_DEPWEB','CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_PRESENCIAL','CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_WEB','CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_DEPWEB') AND vapValidacion = 'VALIDACION_DEPENDIENTE_ADMIN_SUBSIDIO';
DELETE FROM ValidacionProceso WHERE vapBloque IN ('CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_PRESENCIAL','CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_WEB','CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_DEPWEB','CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE','CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE_WEB','CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_ADMINISTRADOR_SUBSIDIO','CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_ADMINISTRADOR_SUBSIDIO_WEB','CAMBIO_DATOS_DE_CUENTA_ADMINISTRADOR_SUBSIDIO') AND vapValidacion = 'VALIDACION_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO';

--changeset mosanchez:04
--comment: Se eliminan tablas asociadas con pila
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaPasoValores_ppvTipoPlanilla')) ALTER TABLE PilaPasoValores DROP CONSTRAINT CK_PilaPasoValores_ppvTipoPlanilla;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaPasoValores_ppvBloque')) ALTER TABLE PilaPasoValores DROP CONSTRAINT CK_PilaPasoValores_ppvBloque;
DROP TABLE dbo.PilaPasoValores;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaProceso_pprTipoProceso')) ALTER TABLE PilaProceso DROP CONSTRAINT CK_PilaProceso_pprTipoProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaProceso_pprEstadoProceso')) ALTER TABLE PilaProceso DROP CONSTRAINT CK_PilaProceso_pprEstadoProceso;
DROP TABLE dbo.PilaProceso;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaOportunidadPresentacionPlanilla_popOportunidad')) ALTER TABLE PilaOportunidadPresentacionPlanilla DROP CONSTRAINT CK_PilaOportunidadPresentacionPlanilla_popOportunidad;
DROP TABLE dbo.PilaOportunidadPresentacionPlanilla;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneralSimulado_agsModalidadPlanilla')) ALTER TABLE AporteGeneralSimulado DROP CONSTRAINT CK_AporteGeneralSimulado_agsModalidadPlanilla;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneralSimulado_agsModalidadRecaudoAporte')) ALTER TABLE AporteGeneralSimulado DROP CONSTRAINT CK_AporteGeneralSimulado_agsModalidadRecaudoAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneralSimulado_agsFormaReconocimientoAporte')) ALTER TABLE AporteGeneralSimulado DROP CONSTRAINT CK_AporteGeneralSimulado_agsFormaReconocimientoAporte;
DROP TABLE dbo.AporteGeneralSimulado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoAporteRecaudo')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoAporteRecaudo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoAporteAjuste')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoAporteAjuste;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoRegistroAporte')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoRegistroAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoValidacionV0')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoValidacionV0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoValidacionV1')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoValidacionV1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoValidacionV2')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoValidacionV2;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoValidacionV3')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoValidacionV3;
DROP TABLE dbo.AporteDetalladoSimulado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaErrorValidacionLog_pevTipoArchivo')) ALTER TABLE PilaErrorValidacionLog DROP CONSTRAINT CK_PilaErrorValidacionLog_pevTipoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaErrorValidacionLog_pevTipoError')) ALTER TABLE PilaErrorValidacionLog DROP CONSTRAINT CK_PilaErrorValidacionLog_pevTipoError;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaErrorValidacionLog_pevBloqueValidacion')) ALTER TABLE PilaErrorValidacionLog DROP CONSTRAINT CK_PilaErrorValidacionLog_pevBloqueValidacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaErrorValidacionLog_pevEstadoInconsistencia')) ALTER TABLE PilaErrorValidacionLog DROP CONSTRAINT CK_PilaErrorValidacionLog_pevEstadoInconsistencia;
ALTER TABLE dbo.PilaErrorValidacionLog DROP CONSTRAINT FK_PilaErrorValidacionLog_pevIndicePlanillaOF;
ALTER TABLE dbo.PilaErrorValidacionLog DROP CONSTRAINT FK_PilaErrorValidacionLog_pevIndicePlanilla;
DROP TABLE dbo.PilaErrorValidacionLog;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Aportante_apoEstadoAportante')) ALTER TABLE Aportante DROP CONSTRAINT CK_Aportante_apoEstadoAportante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Aportante_apoEstadoAporteAportante')) ALTER TABLE Aportante DROP CONSTRAINT CK_Aportante_apoEstadoAporteAportante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Aportante_apoEstadoRegistroAporteAportante')) ALTER TABLE Aportante DROP CONSTRAINT CK_Aportante_apoEstadoRegistroAporteAportante;
ALTER TABLE dbo.Aportante DROP CONSTRAINT FK_Aportante_apoPersona;
ALTER TABLE dbo.Aportante DROP CONSTRAINT FK_Aportante_apoSucursalEmpresa;
DROP TABLE dbo.Aportante;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroGeneral_regTipoIdentificacionAportante')) ALTER TABLE RegistroGeneral DROP CONSTRAINT CK_RegistroGeneral_regTipoIdentificacionAportante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroGeneral_regOUTEstadoEmpleador')) ALTER TABLE RegistroGeneral DROP CONSTRAINT CK_RegistroGeneral_regOUTEstadoEmpleador;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroGeneral_regOUTTipoBeneficio')) ALTER TABLE RegistroGeneral DROP CONSTRAINT CK_RegistroGeneral_regOUTTipoBeneficio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroGeneral_regOUTEstadoArchivo')) ALTER TABLE RegistroGeneral DROP CONSTRAINT CK_RegistroGeneral_regOUTEstadoArchivo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroDetallado_redOUTMarcaValRegistroAporte')) ALTER TABLE RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTMarcaValRegistroAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroDetallado_redOUTEstadoRegistroAporte')) ALTER TABLE RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTEstadoRegistroAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroDetallado_redOUTEstadoValidacionV0')) ALTER TABLE RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTEstadoValidacionV0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroDetallado_redOUTEstadoValidacionV1')) ALTER TABLE RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTEstadoValidacionV1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroDetallado_redOUTEstadoValidacionV2')) ALTER TABLE RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTEstadoValidacionV2;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroDetallado_redOUTEstadoValidacionV3')) ALTER TABLE RegistroDetallado DROP CONSTRAINT CK_RegistroDetallado_redOUTEstadoValidacionV3;


IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaNormatividadFechaVencimiento_pfvTipoFecha')) ALTER TABLE PilaNormatividadFechaVencimiento DROP CONSTRAINT CK_PilaNormatividadFechaVencimiento_pfvTipoFecha;
ALTER TABLE dbo.PilaNormatividadFechaVencimiento DROP CONSTRAINT FK_PilaNormatividadFechaVencimiento_pfvClasificacionAportante;
DROP TABLE dbo.PilaNormatividadFechaVencimiento;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndiceCorreccionPlanilla_pscAccion')) ALTER TABLE PilaIndiceCorreccionPlanilla DROP CONSTRAINT CK_PilaIndiceCorreccionPlanilla_pscAccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndiceCorreccionPlanilla_pscEstadoArchivoAfectado')) ALTER TABLE PilaIndiceCorreccionPlanilla DROP CONSTRAINT CK_PilaIndiceCorreccionPlanilla_pscEstadoArchivoAfectado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndiceCorreccionPlanilla_pscRazonRechazo')) ALTER TABLE PilaIndiceCorreccionPlanilla DROP CONSTRAINT CK_PilaIndiceCorreccionPlanilla_pscRazonRechazo;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla DROP CONSTRAINT FK_PilaIndiceCorreccionPlanilla_picPilaIndicePlanilla;
DROP TABLE dbo.PilaIndiceCorreccionPlanilla;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoEstadoBloque0')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoEstadoBloque0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoAccionBloque0')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoAccionBloque0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoEstadoBloque1')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoEstadoBloque1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoAccionBloque1')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoAccionBloque1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoEstadoBloque6')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoEstadoBloque6;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoAccionBloque6')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoAccionBloque6;
ALTER TABLE dbo.PilaEstadoBloqueOF DROP CONSTRAINT FK_PilaEstadoBloqueOF_peoIndicePlanillaOF;
DROP TABLE dbo.PilaEstadoBloqueOF;

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
ALTER TABLE dbo.PilaEstadoBloque DROP CONSTRAINT FK_PilaEstadoBloque_pebIndicePlanilla;
DROP TABLE dbo.PilaEstadoBloque;

ALTER TABLE dbo.PilaArchivoIRegistro3 DROP CONSTRAINT FK_PilaArchivoIRegistro3_pi3IndicePlanilla;
DROP TABLE dbo.PilaArchivoIRegistro3;

ALTER TABLE dbo.PilaArchivoIRegistro2 DROP CONSTRAINT FK_PilaArchivoIRegistro2_pi2IndicePlanilla;
DROP TABLE dbo.PilaArchivoIRegistro2;

ALTER TABLE dbo.PilaArchivoIRegistro1 DROP CONSTRAINT FK_PilaArchivoIRegistro1_pi1IndicePlanilla;
DROP TABLE dbo.PilaArchivoIRegistro1;

ALTER TABLE dbo.PilaArchivoIPRegistro3 DROP CONSTRAINT FK_PilaArchivoIPRegistro3_ip3IndicePlanilla;
DROP TABLE dbo.PilaArchivoIPRegistro3;

ALTER TABLE dbo.PilaArchivoIPRegistro2 DROP CONSTRAINT FK_PilaArchivoIPRegistro2_ip2IndicePlanilla;
DROP TABLE dbo.PilaArchivoIPRegistro2;

ALTER TABLE dbo.PilaArchivoIPRegistro1 DROP CONSTRAINT FK_PilaArchivoIPRegistro1_ip1IndicePlanilla;
DROP TABLE dbo.PilaArchivoIPRegistro1;

ALTER TABLE dbo.PilaArchivoFRegistro9 DROP CONSTRAINT FK_PilaArchivoFRegistro9_pf9IndicePlanillaOF;
DROP TABLE dbo.PilaArchivoFRegistro9;

ALTER TABLE dbo.PilaArchivoFRegistro8 DROP CONSTRAINT FK_PilaArchivoFRegistro8_pf8IndicePlanillaOF;
DROP TABLE dbo.PilaArchivoFRegistro8;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaArchivoFRegistro6_pf6EstadoConciliacion')) ALTER TABLE PilaArchivoFRegistro6 DROP CONSTRAINT CK_PilaArchivoFRegistro6_pf6EstadoConciliacion;
ALTER TABLE dbo.PilaArchivoFRegistro6 DROP CONSTRAINT FK_PilaArchivoFRegistro6_pf6IndicePlanillaOF;
DROP TABLE dbo.PilaArchivoFRegistro6;

ALTER TABLE dbo.PilaArchivoFRegistro5 DROP CONSTRAINT FK_PilaArchivoFRegistro5_pf5IndicePlanillaOF;
DROP TABLE dbo.PilaArchivoFRegistro5;

ALTER TABLE dbo.PilaArchivoFRegistro1 DROP CONSTRAINT FK_PilaArchivoFRegistro1_pf1IndicePlanillaOF;
DROP TABLE dbo.PilaArchivoFRegistro1;

ALTER TABLE dbo.PilaArchivoARegistro1 DROP CONSTRAINT FK_PilaArchivoARegistro1_pa1IndicePlanilla;
DROP TABLE dbo.PilaArchivoARegistro1;

ALTER TABLE dbo.PilaArchivoAPRegistro1 DROP CONSTRAINT FK_PilaArchivoAPRegistro1_ap1IndicePlanilla;
DROP TABLE dbo.PilaArchivoAPRegistro1;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cotizante_cotEstadoAporteCotizante')) ALTER TABLE Cotizante DROP CONSTRAINT CK_Cotizante_cotEstadoAporteCotizante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cotizante_cotEstadoCotizante')) ALTER TABLE Cotizante DROP CONSTRAINT CK_Cotizante_cotEstadoCotizante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cotizante_cotEstadoRegistroAporteCotizante')) ALTER TABLE Cotizante DROP CONSTRAINT CK_Cotizante_cotEstadoRegistroAporteCotizante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cotizante_cotTipoCotizante')) ALTER TABLE Cotizante DROP CONSTRAINT CK_Cotizante_cotTipoCotizante;
ALTER TABLE dbo.Cotizante DROP CONSTRAINT FK_Cotizante_cotPersona;
DROP TABLE dbo.Cotizante;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaCondicionAportanteVencimiento_pcaComparacion')) ALTER TABLE PilaCondicionAportanteVencimiento DROP CONSTRAINT CK_PilaCondicionAportanteVencimiento_pcaComparacion;
DROP TABLE dbo.PilaClasificacionAportante;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanillaOF_pioTipoArchivo')) ALTER TABLE PilaIndicePlanillaOF DROP CONSTRAINT CK_PilaIndicePlanillaOF_pioTipoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanillaOF_pioTipoCargaArchivo')) ALTER TABLE PilaIndicePlanillaOF DROP CONSTRAINT CK_PilaIndicePlanillaOF_pioTipoCargaArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanillaOF_pioEstadoArchivo')) ALTER TABLE PilaIndicePlanillaOF DROP CONSTRAINT CK_PilaIndicePlanillaOF_pioEstadoArchivo;
DROP TABLE dbo.PilaIndicePlanillaOF;

ALTER TABLE dbo.MovimientoAjusteAporte DROP CONSTRAINT FK_MovimientoAjusteAporte_maaIndicePlanillaOriginal;
ALTER TABLE dbo.MovimientoAjusteAporte DROP CONSTRAINT FK_MovimientoAjusteAporte_maaIndicePlanillaCorregida;
ALTER TABLE dbo.MovimientoAjusteAporte DROP COLUMN maaIndicePlanillaOriginal;
ALTER TABLE dbo.MovimientoAjusteAporte DROP COLUMN maaIndicePlanillaCorregida;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanilla_pipTipoArchivo')) ALTER TABLE PilaIndicePlanilla DROP CONSTRAINT CK_PilaIndicePlanilla_pipTipoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanilla_pipEstadoArchivo')) ALTER TABLE PilaIndicePlanilla DROP CONSTRAINT CK_PilaIndicePlanilla_pipEstadoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanilla_pipTipoCargaArchivo')) ALTER TABLE PilaIndicePlanilla DROP CONSTRAINT CK_PilaIndicePlanilla_pipTipoCargaArchivo;
DROP TABLE dbo.PilaIndicePlanilla;

DROP TABLE staging.Aportante;
DROP TABLE staging.Cotizante;
DROP TABLE staging.Novedad;
DROP TABLE staging.NovedadPila;
DROP TABLE staging.PilaArchivoIPRegistro1;
DROP TABLE staging.PilaArchivoIPRegistro2;
DROP TABLE staging.PilaArchivoIRegistro1;
DROP TABLE staging.PilaArchivoIRegistro2;
DROP TABLE staging.StagingParametros;

DROP SCHEMA staging;

--changeset mosanchez:05
--comment: Se eliminan tabla dbo.PilaTasasInteresMora
DROP TABLE dbo.PilaTasasInteresMora;

--changeset mosanchez:06
--comment: Se elimina campo de la tabla Parametro
ALTER TABLE Parametro DROP COLUMN prmEstado;