--liquibase formatted sql
--
--changeset arocha:01 runOnChange:true

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='MovimientoAporte_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.MovimientoAporte_aud;
CREATE TABLE aud.MovimientoAporte_aud (
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  moaTipoAjuste varchar(20) NULL ,
  moaTipoMovimiento varchar(23) NULL ,
  moaEstadoAporte varchar(22) NULL ,
  moaValorAporte numeric NULL ,
  moaValorInteres numeric NULL ,
  moaFechaActualizacionEstado datetime NULL ,
  moaFechaCreacion datetime NULL ,
  moaAporteDetallado bigint NULL ,
  moaAporteGeneral bigint NOT NULL ,
  moaId bigint NOT NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CarteraAgrupadora_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.CarteraAgrupadora_aud;
CREATE TABLE aud.CarteraAgrupadora_aud (
  cagId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  cagNumeroOperacion bigint NOT NULL ,
  cagCartera bigint NOT NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudPreventiva_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudPreventiva_aud;
CREATE TABLE aud.SolicitudPreventiva_aud (
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  sprActualizacionEfectiva bit NULL ,
  sprBackActualizacion varchar(255) NULL ,
  sprContactoEfectivo bit NULL ,
  sprEstadoSolicitudPreventiva varchar(34) NULL ,
  sprPersona bigint NOT NULL ,
  sprRequiereFiscalizacion bit NULL ,
  sprTipoSolicitanteMovimientoAporte varchar(14) NOT NULL ,
  sprSolicitudGlobal bigint NOT NULL ,
  sprTipoGestionCartera varchar(10) NULL ,
  sprId bigint NOT NULL ,
  sprFechaFiscalizacion date NULL ,
  sprCantidadVecesMoroso smallint NULL ,
  sprEstadoActualCartera varchar(6) NULL ,
  sprFechaLimitePago date NULL ,
  sprSolicitudPreventivaAgrupadora bigint NULL ,
  sprTrabajadoresActivos smallint NULL ,
  sprValorPromedioAportes numeric NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudPreventivaAgrupadora_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudPreventivaAgrupadora_aud;
CREATE TABLE aud.SolicitudPreventivaAgrupadora_aud (
  spaId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  spaEstadoSolicitudPreventivaAgrupadora varchar(255) NULL ,
  spaSolicitudGlobal bigint NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudGestionCobroManual_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudGestionCobroManual_aud;
CREATE TABLE aud.SolicitudGestionCobroManual_aud (
  scmId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  scmCicloAportante bigint NOT NULL ,
  scmEstadoSolicitud varchar(25) NULL ,
  scmSolicitudGlobal bigint NOT NULL ,
  scmLineaCobro varchar(3) NOT NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudGestionCobroFisico_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudGestionCobroFisico_aud;
CREATE TABLE aud.SolicitudGestionCobroFisico_aud (
  sgfId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  sgfDocumentoSoporte bigint NULL ,
  sgfEstado varchar(52) NULL ,
  sgfFechaRemision datetime NULL ,
  sgfObservacionRemision varchar(255) NULL ,
  sgfTipoAccionCobro varchar(4) NOT NULL ,
  sgfSolicitud bigint NOT NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudGestionCobroElectronico_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudGestionCobroElectronico_aud;
CREATE TABLE aud.SolicitudGestionCobroElectronico_aud (
  sgeId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  sgeEstado varchar(52) NULL ,
  sgeCartera bigint NOT NULL ,
  sgeTipoAccionCobro varchar(4) NOT NULL ,
  sgeSolicitud bigint NOT NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudFiscalizacion_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudFiscalizacion_aud;
CREATE TABLE aud.SolicitudFiscalizacion_aud (
  sfiId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  sfiEstadoFiscalizacion varchar(11) NULL ,
  sfiSolicitudGlobal bigint NOT NULL ,
  sfiCicloAportante bigint NOT NULL ,
  revTime DATETIME NOT NULL
);

--changeset arocha:02 runOnChange:true

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CicloAsignacion_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.CicloAsignacion_aud;
CREATE TABLE aud.CicloAsignacion_aud (
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  ciaNombre varchar(50) NULL ,
  ciaFechaInicio date NULL ,
  ciaFechaFin date NULL ,
  ciaCicloPredecesor bigint NULL ,
  ciaEstadoCicloAsignacion varchar(30) NULL ,
  ciaCicloActivo bit NULL ,
  ciaId bigint NOT NULL ,
  ciaValorDisponible numeric NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudAsignacion_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudAsignacion_aud;
CREATE TABLE aud.SolicitudAsignacion_aud (
  safId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  safSolicitudGlobal bigint NOT NULL ,
  safFechaAceptacion datetime NULL ,
  safUsuario varchar(50) NULL ,
  safValorSFVAsignado numeric NULL ,
  safEstadoSolicitudAsignacion varchar(50) NULL ,
  safComentarios varchar(500) NULL ,
  safCicloAsignacion bigint NOT NULL ,
  safComentarioControlInterno varchar(500) NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='JefeHogar_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.JefeHogar_aud;
CREATE TABLE aud.JefeHogar_aud (
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  jehAfiliado bigint NOT NULL ,
  jehEstadoHogar varchar(10) NULL ,
  jehId bigint NOT NULL ,
  jehIngresoMensual numeric NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='IntegranteHogar_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.IntegranteHogar_aud;
CREATE TABLE aud.IntegranteHogar_aud (
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  inhJefeHogar bigint NOT NULL ,
  inhPersona bigint NOT NULL ,
  inhIntegranteReemplazaJefeHogar bit NULL ,
  inhTipoIntegrante varchar(32) NOT NULL ,
  inhEstadoHogar varchar(10) NULL ,
  inhIntegranteValido bit NULL ,
  inhSalarioMensual numeric NULL ,
  inhId bigint NOT NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='PostulacionFOVIS_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.PostulacionFOVIS_aud;
CREATE TABLE aud.PostulacionFOVIS_aud (
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  pofCicloAsignacion bigint NULL ,
  pofJefeHogar bigint NULL ,
  pofEstadoHogar varchar(58) NULL ,
  pofCondicionHogar varchar(44) NULL ,
  pofHogarPerdioSubsidioNoPago bit NULL ,
  pofCantidadFolios smallint NULL ,
  pofValorSFVSolicitado numeric NULL ,
  pofProyectoSolucionVivienda bigint NULL ,
  pofModalidad varchar(50) NULL ,
  pofId bigint NOT NULL ,
  pofSolicitudAsignacion bigint NULL ,
  pofResultadoAsignacion varchar(50) NULL ,
  pofValorAsignadoSFV numeric NULL ,
  pofIdentificardorDocumentoActaAsignacion varchar(255) NULL ,
  pofPuntaje numeric NULL ,
  pofFechaCalificacion datetime NULL ,
  pofPrioridadAsignacion varchar(11) NULL ,
  pofValorCalculadoSFV numeric NULL ,
  pofValorProyectoVivienda numeric NULL ,
  pofMotivoDesistimiento varchar(29) NULL ,
  pofMotivoRechazo varchar(51) NULL ,
  pofMotivoHabilitacion varchar(38) NULL ,
  pofRestituidoConSancion bit NULL ,
  pofTiempoSancion varchar(10) NULL ,
  pofMotivoRestitucion varchar(45) NULL ,
  pofMotivoEnajenacion varchar(40) NULL ,
  pofValorAjusteIPCSFV numeric NULL ,
  pofInfoAsignacion varchar(max) NULL ,
  pofCalificacionPostulacion bigint NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudPostulacion_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudPostulacion_aud;
CREATE TABLE aud.SolicitudPostulacion_aud (
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  spoSolicitudGlobal bigint NOT NULL ,
  spoPostulacionFOVIS bigint NULL ,
  spoEstadoSolicitud varchar(42) NULL ,
  spoObservaciones varchar(500) NULL ,
  spoId bigint NOT NULL ,
  spoObservacionesWeb varchar(500) NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='LegalizacionDesembolso_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.LegalizacionDesembolso_aud;
CREATE TABLE aud.LegalizacionDesembolso_aud (
  lgdId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  lgdFormaPago varchar(50) NULL ,
  lgdValorDesembolsar numeric NULL ,
  lgdFechaLimitePago datetime NULL ,
  lgdVisita bigint NULL ,
  lgdSubsidioDesembolsado bit NULL ,
  lgdTipoMedioPago varchar(30) NULL ,
  lgdDocumentoSoporte varchar(255) NULL ,
  lgdFechaTransferencia datetime2 NULL ,
  lgdObservaciones varchar(500) NULL ,
  lgdMontoDesembolsado numeric NULL ,
  lgdObservacionesBack varchar(500) NULL ,
  lgdDocumentoSoporteBack varchar(255) NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudLegalizacionDesembolso_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudLegalizacionDesembolso_aud;
CREATE TABLE aud.SolicitudLegalizacionDesembolso_aud (
  sldId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  sldSolicitudGlobal bigint NOT NULL ,
  sldPostulacionFOVIS bigint NULL ,
  sldEstadoSolicitud varchar(60) NULL ,
  sldLegalizacionDesembolso bigint NULL ,
  sldObservaciones varchar(500) NULL ,
  sldFechaOperacion datetime NULL ,
  sldJsonPostulacion text NULL ,
  sldCantidadReintentos smallint NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudNovedadFovis_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudNovedadFovis_aud;
CREATE TABLE aud.SolicitudNovedadFovis_aud (
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  snfSolicitudGlobal bigint NOT NULL ,
  snfEstadoSolicitud varchar(38) NOT NULL ,
  snfParametrizacionNovedad bigint NOT NULL ,
  snfObservaciones varchar(200) NULL ,
  snfId bigint NOT NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudNovedadPersonaFovis_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudNovedadPersonaFovis_aud;
CREATE TABLE aud.SolicitudNovedadPersonaFovis_aud (
  spfId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  spfPersona bigint NULL ,
  spfSolicitudNovedadFovis bigint NOT NULL ,
  spfPostulacionFovis bigint NOT NULL ,
  revTime DATETIME NOT NULL
);

--changeset dsuesca:11
--comment: 
ALTER TABLE aud.RolAfiliado_aud ADD roaFechaFinContrato DATE;
ALTER TABLE aud.PostulacionFovis_aud ADD pofValorAvaluoCatastral NUMERIC(19,5) NULL;

--changeset dsuesca:12
--comment: 
CREATE TABLE aud.ConvenioPago_aud (
  copId bigint NOT NULL,
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  copPersona bigint NOT NULL,
  copTipoSolicitante varchar(13) NOT NULL,
  copDeudaPresuntaRegistrada numeric(19,5) NOT NULL,
  copDeudaRealRegistrada numeric(19,5) NULL,
  copCuotasPorPagar smallint NOT NULL,
  copEstadoConvenioPago varchar(30) NOT NULL,
  copMotivoAnulacion varchar(30) NULL,
  copUsuarioAnulacion varchar(255) NULL,
  copFechaAnulacion datetime NULL,
  copFechaRegistro datetime NOT NULL,
  copUsuarioCreacion varchar(255) NULL,
  revTime DATETIME NOT NULL
);

--changeset dsuesca:13
--comment: 
CREATE TABLE aud.DetalleSolicitudGestionCobro_aud (
  dsgId bigint NOT NULL,
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  dsgEnviarPrimeraRemision bit NULL,
  dsgEnviarSegundaRemision bit NULL,
  dsgEstado varchar(52) NULL,
  dsgFechaPrimeraRemision datetime NULL,
  dsgFechaSegundaRemision datetime NULL,
  dsgCartera bigint NOT NULL,
  dsgObservacionPrimeraEntrega varchar(255) NULL,
  dsgObservacionPrimeraRemision varchar(255) NULL,
  dsgObservacionSegundaEntrega varchar(255) NULL,
  dsgObservacionSegundaRemision varchar(255) NULL,
  dsgSolicitudPrimeraRemision bigint NULL,
  dsgSolicitudSegundaRemision bigint NULL,
  dsgResultadoPrimeraEntrega varchar(18) NULL,
  dsgResultadoSegundaEntrega varchar(18) NULL,
  dsgDocumentoPrimeraRemision bigint NULL,
  dsgDocumentoSegundaRemision bigint NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE aud.BitacoraCartera_aud (
  bcaId bigint NOT NULL,
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  bcaFecha date NULL,
  bcaActividad varchar(50) NULL,
  bcaMedio varchar(20) NULL,
  bcaResultado varchar(40) NULL,
  bcaUsuario varchar(255) NULL,
  bcaPersona bigint NOT NULL,
  bcaTipoSolicitante varchar(20) NULL,
  bcaNumeroOperacion varchar(12) NULL,
  revTime DATETIME NOT NULL
)

--changeset dsuesca:14
--comment: 

CREATE TABLE aud.UbicacionEmpresa_aud (
  ubeId bigint NOT NULL,
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  ubeEmpresa bigint NULL,
  ubeUbicacion bigint NULL,
  ubeTipoUbicacion varchar(30) NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE dbo.DevolucionAporteDetalle_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  dadIncluyeAporteObligatorio bit NULL,
  dadIncluyeMoraCotizante bit NULL,
  dadComentarioHistorico varchar(255) NULL,
  dadComentarioNovedades varchar(255) NULL,
  dadComentarioAportes varchar(255) NULL,
  dadUsuario varchar(255) NULL,
  dadFechaGestion datetime NULL,
  dadDevolucionAporte bigint NOT NULL,
  dadMovimientoAporte bigint NULL,
  dadId bigint NOT NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE dbo.DevolucionAporte_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  dapFechaRecepcion datetime NULL,
  dapMotivoPeticion varchar(28) NULL,
  dapDestinatarioDevolucion varchar(13) NULL,
  dapCajaCompensacion int NULL,
  dapOtroDestinatario varchar(255) NULL,
  dapMontoAportes numeric(19,5) NULL,
  dapMontoIntereses numeric(19,5) NULL,
  dapPeriodoReclamado varchar(255) NULL,
  dapMedioPago bigint NULL,
  dapSedeCajaCompensacion bigint NULL,
  dapDescuentoGestionPagoOI numeric(19,5) NULL,
  dapDescuentoGestionFinanciera numeric(19,5) NULL,
  dapDescuentoOtro numeric(19,5) NULL,
  dapId bigint NOT NULL,
  dapOtraCaja varchar(255) NULL,
  dapOtroMotivo varchar(255) NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE dbo.SolicitudDevolucionAporte_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  sdaEstadoSolicitud varchar(25) NULL,
  sdaTipoSolicitante varchar(13) NULL,
  sdaPersona bigint NULL,
  sdaObservacionAnalista varchar(255) NULL,
  sdaObservacionSupervisor varchar(255) NULL,
  sdaResultadoAnalista varchar(10) NULL,
  sdaResultadoSupervisor varchar(10) NULL,
  sdaDevolucionAporte bigint NULL,
  sdaSolicitudGlobal bigint NULL,
  sdaId bigint NOT NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE dbo.SolicitudLiquidacionSubsidio_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  slsSolicitudGlobal bigint NOT NULL,
  slsFechaCorteAporte datetime NULL,
  slsFechaInicio datetime NULL,
  slsFechaFin datetime NULL,
  slsTipoLiquidacion varchar(33) NOT NULL,
  slsTipoLiquidacionEspecifica varchar(32) NULL,
  slsEstadoLiquidacion varchar(25) NOT NULL,
  slsTipoEjecucionProceso varchar(10) NOT NULL,
  slsFechaEjecucionProgramada datetime NULL,
  slsUsuarioEvaluacionPrimerNivel varchar(50) NULL,
  slsObservacionesPrimerNivel varchar(250) NULL,
  slsUsuarioEvaluacionSegundoNivel varchar(50) NULL,
  slsObservacionesSegundoNivel varchar(250) NULL,
  slsRazonRechazoLiquidacion varchar(250) NULL,
  slsObservacionesProceso varchar(250) NULL,
  slsId bigint NOT NULL,
  slsFechaEvaluacionPrimerNivel datetime NULL,
  slsFechaEvaluacionSegundoNivel datetime NULL,
  slsCodigoReclamo varchar(50) NULL,
  slsComentarioReclamo varchar(250) NULL,
  slsFechaDispersion datetime NULL,
  slsConsideracionAporteDesembolso bit NULL,
  slsTipoDesembolso varchar(40) NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE dbo.CuentaAdministradorSubsidio_aud (
  casId bigint NOT NULL,
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  casFechaHoraCreacionRegistro datetime NOT NULL,
  casUsuarioCreacionRegistro varchar(200) NOT NULL,
  casTipoTransaccionSubsidio varchar(40) NOT NULL,
  casEstadoTransaccionSubsidio varchar(25) NULL,
  casEstadoLiquidacionSubsidio varchar(25) NULL,
  casOrigenTransaccion varchar(30) NOT NULL,
  casMedioDePagoTransaccion varchar(13) NOT NULL,
  casNumeroTarjetaAdmonSubsidio varchar(50) NULL,
  casCodigoBanco varchar(6) NULL,
  casNombreBanco varchar(255) NULL,
  casTipoCuentaAdmonSubsidio varchar(30) NULL,
  casNumeroCuentaAdmonSubsidio varchar(30) NULL,
  casTipoIdentificacionTitularCuentaAdmonSubsidio varchar(20) NULL,
  casNumeroIdentificacionTitularCuentaAdmonSubsidio varchar(20) NULL,
  casNombreTitularCuentaAdmonSubsidio varchar(200) NULL,
  casFechaHoraTransaccion datetime NOT NULL,
  casUsuarioTransaccion varchar(200) NOT NULL,
  casValorOriginalTransaccion numeric(19,5) NOT NULL,
  casValorRealTransaccion numeric(19,5) NOT NULL,
  casIdTransaccionOriginal bigint NULL,
  casIdRemisionDatosTerceroPagador varchar(200) NULL,
  casIdTransaccionTerceroPagador varchar(200) NULL,
  casNombreTerceroPagado varchar(200) NULL,
  casIdCuentaAdmonSubsidioRelacionado bigint NULL,
  casFechaHoraUltimaModificacion datetime NULL,
  casUsuarioUltimaModificacion varchar(200) NULL,
  casAdministradorSubsidio bigint NOT NULL,
  casSitioDePago bigint NULL,
  casSitioDeCobro bigint NULL,
  casMedioDePago bigint NOT NULL,
  casCondicionPersonaAdmin bigint NULL,
  casSolicitudLiquidacionSubsidio bigint NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE dbo.ProyectoSolucionVivienda_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  psvNombreProyecto varchar(250) NULL,
  psvMatriculaInmobiliariaInmueble varchar(50) NULL,
  psvLoteUrbanizado bit NULL,
  psvFechaRegistroEscritura date NULL,
  psvNumeroEscritura varchar(20) NULL,
  psvFechaEscritura date NULL,
  psvObservaciones varchar(500) NULL,
  psvOferente bigint NULL,
  psvUbicacionProyecto bigint NULL,
  psvUbicacionIgualProyecto bit NULL,
  psvUbicacionVivienda bigint NULL,
  psvNumeroDocumentoElegibilidad varchar(50) NULL,
  psvCodigoProyectoElegibilidad varchar(50) NULL,
  psvNombreEntidadElegibilidad varchar(50) NULL,
  psvFechaElegibilidad date NULL,
  psvNumeroViviendaElegibilidad int NULL,
  psvTipoInmuebleElegibilidad varchar(50) NULL,
  psvComentariosElegibilidad varchar(500) NULL,
  psvModalidad varchar(50) NULL,
  psvPoseedorOcupanteVivienda varchar(50) NULL,
  psvId bigint NOT NULL,
  psvRegistrado bit NULL,
  psvDisponeCuentaBancaria bit NULL,
  psvComparteCuentaOferente bit NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE dbo.Oferente_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  ofePersona bigint NOT NULL,
  ofeEmpresa bigint NULL,
  ofeEstado varchar(30) NULL,
  ofeId bigint NOT NULL,
  ofeCuentaBancaria bit NULL,
  ofeBanco bigint NULL,
  ofeTipoCuenta varchar(30) NULL,
  ofeNumeroCuenta varchar(30) NULL,
  ofeTipoIdentificacionTitular varchar(20) NULL,
  ofeNumeroIdentificacionTitular varchar(16) NULL,
  ofeDigitoVerificacionTitular smallint NULL,
  ofeNombreTitularCuenta varchar(200) NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE dbo.AhorroPrevio_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  ahpNombreAhorro varchar(65) NULL,
  ahpEntidad varchar(50) NULL,
  ahpFechaInicial date NULL,
  ahpValor numeric(19,5) NULL,
  ahpFechaInmovilizacion date NULL,
  ahpFechaAdquisicion date NULL,
  ahpPostulacionFOVIS bigint NOT NULL,
  ahpId bigint NOT NULL,
  ahpAhorroMovilizado bit NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE dbo.RecursoComplementario_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  recNombre varchar(26) NULL,
  recEntidad varchar(50) NULL,
  recFecha date NULL,
  recOtroRecurso varchar(255) NULL,
  recValor numeric(19,5) NULL,
  recPostulacionFOVIS bigint NOT NULL,
  recId bigint NOT NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE dbo.ActaAsignacionFovis_aud (
  aafId bigint NOT NULL,
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  aafSolicitudAsignacion bigint NOT NULL,
  aafIdentificadorDocumentoActa varchar(255) NULL,
  aafIdentificadorDocumentoConsolidado varchar(255) NULL,
  aafNumeroResolucion varchar(20) NULL,
  aafNumeroOficio varchar(20) NULL,
  aafAnoResolucion varchar(4) NULL,
  aafFechaResolucion datetime NULL,
  aafFechaOficio datetime NULL,
  aafNombreResponsable1 varchar(50) NULL,
  aafCargoResponsable1 varchar(50) NULL,
  aafNombreResponsable2 varchar(50) NULL,
  aafCargoResponsable2 varchar(50) NULL,
  aafNombreResponsable3 varchar(50) NULL,
  aafCargoResponsable3 varchar(50) NULL,
  aafFechaConfirmacion datetime NULL,
  aafFechaCorte datetime NULL,
  aafInicioVigencia datetime NULL,
  aafFinVigencia datetime NULL,
  aafFechaActaAsignacionFovis datetime NULL,
  aafFechaPublicacion datetime NULL,
  revTime DATETIME NOT NULL
)


--changeset dsuesca:15
--comment: 
DROP TABLE dbo.DevolucionAporteDetalle_aud
DROP TABLE dbo.DevolucionAporte_aud
DROP TABLE dbo.SolicitudDevolucionAporte_aud
DROP TABLE dbo.SolicitudLiquidacionSubsidio_au
DROP TABLE dbo.CuentaAdministradorSubsidio_aud
DROP TABLE dbo.ProyectoSolucionVivienda_aud
DROP TABLE dbo.Oferente_aud
DROP TABLE dbo.AhorroPrevio_aud
DROP TABLE dbo.RecursoComplementario_aud
DROP TABLE dbo.ActaAsignacionFovis_aud

CREATE TABLE aud.DevolucionAporteDetalle_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  dadIncluyeAporteObligatorio bit NULL,
  dadIncluyeMoraCotizante bit NULL,
  dadComentarioHistorico varchar(255) NULL,
  dadComentarioNovedades varchar(255) NULL,
  dadComentarioAportes varchar(255) NULL,
  dadUsuario varchar(255) NULL,
  dadFechaGestion datetime NULL,
  dadDevolucionAporte bigint NOT NULL,
  dadMovimientoAporte bigint NULL,
  dadId bigint NOT NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE aud.DevolucionAporte_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  dapFechaRecepcion datetime NULL,
  dapMotivoPeticion varchar(28) NULL,
  dapDestinatarioDevolucion varchar(13) NULL,
  dapCajaCompensacion int NULL,
  dapOtroDestinatario varchar(255) NULL,
  dapMontoAportes numeric(19,5) NULL,
  dapMontoIntereses numeric(19,5) NULL,
  dapPeriodoReclamado varchar(255) NULL,
  dapMedioPago bigint NULL,
  dapSedeCajaCompensacion bigint NULL,
  dapDescuentoGestionPagoOI numeric(19,5) NULL,
  dapDescuentoGestionFinanciera numeric(19,5) NULL,
  dapDescuentoOtro numeric(19,5) NULL,
  dapId bigint NOT NULL,
  dapOtraCaja varchar(255) NULL,
  dapOtroMotivo varchar(255) NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE aud.SolicitudDevolucionAporte_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  sdaEstadoSolicitud varchar(25) NULL,
  sdaTipoSolicitante varchar(13) NULL,
  sdaPersona bigint NULL,
  sdaObservacionAnalista varchar(255) NULL,
  sdaObservacionSupervisor varchar(255) NULL,
  sdaResultadoAnalista varchar(10) NULL,
  sdaResultadoSupervisor varchar(10) NULL,
  sdaDevolucionAporte bigint NULL,
  sdaSolicitudGlobal bigint NULL,
  sdaId bigint NOT NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE aud.SolicitudLiquidacionSubsidio_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  slsSolicitudGlobal bigint NOT NULL,
  slsFechaCorteAporte datetime NULL,
  slsFechaInicio datetime NULL,
  slsFechaFin datetime NULL,
  slsTipoLiquidacion varchar(33) NOT NULL,
  slsTipoLiquidacionEspecifica varchar(32) NULL,
  slsEstadoLiquidacion varchar(25) NOT NULL,
  slsTipoEjecucionProceso varchar(10) NOT NULL,
  slsFechaEjecucionProgramada datetime NULL,
  slsUsuarioEvaluacionPrimerNivel varchar(50) NULL,
  slsObservacionesPrimerNivel varchar(250) NULL,
  slsUsuarioEvaluacionSegundoNivel varchar(50) NULL,
  slsObservacionesSegundoNivel varchar(250) NULL,
  slsRazonRechazoLiquidacion varchar(250) NULL,
  slsObservacionesProceso varchar(250) NULL,
  slsId bigint NOT NULL,
  slsFechaEvaluacionPrimerNivel datetime NULL,
  slsFechaEvaluacionSegundoNivel datetime NULL,
  slsCodigoReclamo varchar(50) NULL,
  slsComentarioReclamo varchar(250) NULL,
  slsFechaDispersion datetime NULL,
  slsConsideracionAporteDesembolso bit NULL,
  slsTipoDesembolso varchar(40) NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE aud.CuentaAdministradorSubsidio_aud (
  casId bigint NOT NULL,
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  casFechaHoraCreacionRegistro datetime NOT NULL,
  casUsuarioCreacionRegistro varchar(200) NOT NULL,
  casTipoTransaccionSubsidio varchar(40) NOT NULL,
  casEstadoTransaccionSubsidio varchar(25) NULL,
  casEstadoLiquidacionSubsidio varchar(25) NULL,
  casOrigenTransaccion varchar(30) NOT NULL,
  casMedioDePagoTransaccion varchar(13) NOT NULL,
  casNumeroTarjetaAdmonSubsidio varchar(50) NULL,
  casCodigoBanco varchar(6) NULL,
  casNombreBanco varchar(255) NULL,
  casTipoCuentaAdmonSubsidio varchar(30) NULL,
  casNumeroCuentaAdmonSubsidio varchar(30) NULL,
  casTipoIdentificacionTitularCuentaAdmonSubsidio varchar(20) NULL,
  casNumeroIdentificacionTitularCuentaAdmonSubsidio varchar(20) NULL,
  casNombreTitularCuentaAdmonSubsidio varchar(200) NULL,
  casFechaHoraTransaccion datetime NOT NULL,
  casUsuarioTransaccion varchar(200) NOT NULL,
  casValorOriginalTransaccion numeric(19,5) NOT NULL,
  casValorRealTransaccion numeric(19,5) NOT NULL,
  casIdTransaccionOriginal bigint NULL,
  casIdRemisionDatosTerceroPagador varchar(200) NULL,
  casIdTransaccionTerceroPagador varchar(200) NULL,
  casNombreTerceroPagado varchar(200) NULL,
  casIdCuentaAdmonSubsidioRelacionado bigint NULL,
  casFechaHoraUltimaModificacion datetime NULL,
  casUsuarioUltimaModificacion varchar(200) NULL,
  casAdministradorSubsidio bigint NOT NULL,
  casSitioDePago bigint NULL,
  casSitioDeCobro bigint NULL,
  casMedioDePago bigint NOT NULL,
  casCondicionPersonaAdmin bigint NULL,
  casSolicitudLiquidacionSubsidio bigint NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE aud.ProyectoSolucionVivienda_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  psvNombreProyecto varchar(250) NULL,
  psvMatriculaInmobiliariaInmueble varchar(50) NULL,
  psvLoteUrbanizado bit NULL,
  psvFechaRegistroEscritura date NULL,
  psvNumeroEscritura varchar(20) NULL,
  psvFechaEscritura date NULL,
  psvObservaciones varchar(500) NULL,
  psvOferente bigint NULL,
  psvUbicacionProyecto bigint NULL,
  psvUbicacionIgualProyecto bit NULL,
  psvUbicacionVivienda bigint NULL,
  psvNumeroDocumentoElegibilidad varchar(50) NULL,
  psvCodigoProyectoElegibilidad varchar(50) NULL,
  psvNombreEntidadElegibilidad varchar(50) NULL,
  psvFechaElegibilidad date NULL,
  psvNumeroViviendaElegibilidad int NULL,
  psvTipoInmuebleElegibilidad varchar(50) NULL,
  psvComentariosElegibilidad varchar(500) NULL,
  psvModalidad varchar(50) NULL,
  psvPoseedorOcupanteVivienda varchar(50) NULL,
  psvId bigint NOT NULL,
  psvRegistrado bit NULL,
  psvDisponeCuentaBancaria bit NULL,
  psvComparteCuentaOferente bit NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE aud.Oferente_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  ofePersona bigint NOT NULL,
  ofeEmpresa bigint NULL,
  ofeEstado varchar(30) NULL,
  ofeId bigint NOT NULL,
  ofeCuentaBancaria bit NULL,
  ofeBanco bigint NULL,
  ofeTipoCuenta varchar(30) NULL,
  ofeNumeroCuenta varchar(30) NULL,
  ofeTipoIdentificacionTitular varchar(20) NULL,
  ofeNumeroIdentificacionTitular varchar(16) NULL,
  ofeDigitoVerificacionTitular smallint NULL,
  ofeNombreTitularCuenta varchar(200) NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE aud.AhorroPrevio_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  ahpNombreAhorro varchar(65) NULL,
  ahpEntidad varchar(50) NULL,
  ahpFechaInicial date NULL,
  ahpValor numeric(19,5) NULL,
  ahpFechaInmovilizacion date NULL,
  ahpFechaAdquisicion date NULL,
  ahpPostulacionFOVIS bigint NOT NULL,
  ahpId bigint NOT NULL,
  ahpAhorroMovilizado bit NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE aud.RecursoComplementario_aud (
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  recNombre varchar(26) NULL,
  recEntidad varchar(50) NULL,
  recFecha date NULL,
  recOtroRecurso varchar(255) NULL,
  recValor numeric(19,5) NULL,
  recPostulacionFOVIS bigint NOT NULL,
  recId bigint NOT NULL,
  revTime DATETIME NOT NULL
);

CREATE TABLE aud.ActaAsignacionFovis_aud (
  aafId bigint NOT NULL,
  REV bigint NOT NULL,
  REVTYPE smallint NULL,
  aafSolicitudAsignacion bigint NOT NULL,
  aafIdentificadorDocumentoActa varchar(255) NULL,
  aafIdentificadorDocumentoConsolidado varchar(255) NULL,
  aafNumeroResolucion varchar(20) NULL,
  aafNumeroOficio varchar(20) NULL,
  aafAnoResolucion varchar(4) NULL,
  aafFechaResolucion datetime NULL,
  aafFechaOficio datetime NULL,
  aafNombreResponsable1 varchar(50) NULL,
  aafCargoResponsable1 varchar(50) NULL,
  aafNombreResponsable2 varchar(50) NULL,
  aafCargoResponsable2 varchar(50) NULL,
  aafNombreResponsable3 varchar(50) NULL,
  aafCargoResponsable3 varchar(50) NULL,
  aafFechaConfirmacion datetime NULL,
  aafFechaCorte datetime NULL,
  aafInicioVigencia datetime NULL,
  aafFinVigencia datetime NULL,
  aafFechaActaAsignacionFovis datetime NULL,
  aafFechaPublicacion datetime NULL,
  revTime DATETIME NOT NULL
)