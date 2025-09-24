--liquibase formatted sql

--changeset arocha:01
--CREATE SCHEMA aud;

--changeset arocha:02

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='MedioDePago_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.MedioDePago_aud;
CREATE TABLE aud.MedioDePago_aud (
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  mdpTipo varchar(100) NOT NULL ,
  mdpId bigint NOT NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='MedioEfectivo_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.MedioEfectivo_aud;
CREATE TABLE aud.MedioEfectivo_aud (
  mdpId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  mefEfectivo bit NOT NULL ,
  mefSitioPago bigint NULL ,
  mefSedeCajaCompensacion bigint NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='MedioTarjeta_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.MedioTarjeta_aud;
CREATE TABLE aud.MedioTarjeta_aud (
  mdpId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  mtrNumeroTarjeta varchar(50) NOT NULL ,
  mtrDisponeTarjeta bit NOT NULL ,
  mtrEstadoTarjetaMultiservicios varchar(30) NOT NULL ,
  mtrSolicitudTarjeta varchar(30) NOT NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='MedioTransferencia_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.MedioTransferencia_aud;
CREATE TABLE aud.MedioTransferencia_aud (
  mdpId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  metBanco bigint NOT NULL ,
  metTipoCuenta varchar(30) NOT NULL ,
  metNumeroCuenta varchar(30) NOT NULL ,
  metTipoIdentificacionTitular varchar(20) NULL ,
  metNumeroIdentificacionTitular varchar(16) NULL ,
  metDigitoVerificacionTitular smallint NULL ,
  metNombreTitularCuenta varchar(200) NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Persona_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.Persona_aud;
CREATE TABLE aud.Persona_aud (
  perId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  perDigitoVerificacion smallint NULL ,
  perNumeroIdentificacion varchar(16) NULL ,
  perRazonSocial varchar(250) NULL ,
  perTipoIdentificacion varchar(20) NULL ,
  perUbicacionPrincipal bigint NULL ,
  perPrimerNombre varchar(50) NULL ,
  perSegundoNombre varchar(50) NULL ,
  perPrimerApellido varchar(50) NULL ,
  perSegundoApellido varchar(50) NULL ,
  perCreadoPorPila bit NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Afiliado_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.Afiliado_aud;
CREATE TABLE aud.Afiliado_aud (
  afiId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  afiPersona bigint NULL ,
  afiPignoracionSubsidio bit NULL ,
  afiCesionSubsidio bit NULL ,
  afiRetencionSubsidio bit NULL ,
  afiServicioSinAfiliacion bit NULL ,
  afiCausaSinAfiliacion varchar(20) NULL ,
  afiFechaInicioServiciosSinAfiliacion date NULL ,
  afifechaFinServicioSinAfiliacion date NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='GrupoFamiliar_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.GrupoFamiliar_aud;
CREATE TABLE aud.GrupoFamiliar_aud (
  grfId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  grfNumero smallint NOT NULL ,
  grfObservaciones varchar(500) NULL ,
  grfAfiliado bigint NOT NULL ,
  grfUbicacion bigint NULL ,
  grfInembargable bit NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CodigoCIIU_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.CodigoCIIU_aud;
CREATE TABLE aud.CodigoCIIU_aud (
  ciiId smallint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  ciiCodigo varchar(4) NOT NULL ,
  ciiDescripcion varchar(255) NOT NULL ,
  ciiCodigoSeccion varchar(1) NULL ,
  ciiDescripcionSeccion varchar(200) NULL ,
  ciiCodigoDivision varchar(2) NULL ,
  ciiDescripcionDivision varchar(250) NULL ,
  ciiCodigoGrupo varchar(3) NULL ,
  ciiDescripcionGrupo varchar(200) NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Empresa_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.Empresa_aud;
CREATE TABLE aud.Empresa_aud (
  empId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  empPersona bigint NULL ,
  empNombreComercial varchar(250) NULL ,
  empFechaConstitucion date NULL ,
  empNaturalezaJuridica varchar(100) NULL ,
  empCodigoCIIU smallint NULL ,
  empArl smallint NULL ,
  empUltimaCajaCompensacion int NULL ,
  empPaginaWeb varchar(256) NULL ,
  empRepresentanteLegal bigint NULL ,
  empRepresentanteLegalSuplente bigint NULL ,
  empEspecialRevision bit NULL ,
  empUbicacionRepresentanteLegal bigint NULL ,
  empUbicacionRepresentanteLegalSuplente bigint NULL ,
  empCreadoPorPila bit NULL ,
  empEnviadoAFiscalizacion bit NULL ,
  empMotivoFiscalizacion varchar(30) NULL ,
  empFechaFiscalizacion date NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SucursalEmpresa_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SucursalEmpresa_aud;
CREATE TABLE aud.SucursalEmpresa_aud (
  sueId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  sueCodigo varchar(10) NULL ,
  sueNombre varchar(100) NULL ,
  sueCodigoCIIU smallint NULL ,
  sueEmpresa bigint NULL ,
  sueUbicacion bigint NULL ,
  sueEstadoSucursal varchar(25) NULL ,
  sueCoindicirCodigoPila bit NULL ,
  sueMedioPagoSubsidioMonetario varchar(30) NULL ,
  sueSucursalPrincipal bit NULL ,
  sueRetencionSubsidioActiva bit NULL ,
  sueMotivoRetencionSubsidio varchar(24) NULL ,
  sueMotivoInactivaRetencionSubsidio varchar(26) NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Empleador_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.Empleador_aud;
CREATE TABLE aud.Empleador_aud (
  empId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  empEstadoEmpleador varchar(8) NULL ,
  empExpulsionSubsanada bit NULL ,
  empFechaCambioEstadoAfiliacion datetime2 NULL ,
  empMotivoDesafiliacion varchar(100) NULL ,
  empNumeroTotalTrabajadores int NULL ,
  empPeriodoUltimaNomina date NULL ,
  empValorTotalUltimaNomina numeric NULL ,
  empEmpresa bigint NULL ,
  empFechaRetiro datetime NULL ,
  empFechaSubsanacionExpulsion date NULL ,
  empMotivoSubsanacionExpulsion varchar(200) NULL ,
  empCantIngresoBandejaCeroTrabajadores smallint NULL ,
  empFechaRetiroTotalTrabajadores date NULL ,
  empFechaGestionDesafiliacion date NULL ,
  empMedioPagoSubsidioMonetario varchar(30) NULL ,
  empValidarSucursalPila bit NULL ,
  empDiaHabilVencimientoAporte smallint NULL ,
  empMarcaExpulsion varchar(22) NULL ,
  empRetencionSubsidioActiva bit NULL ,
  empMotivoRetencionSubsidio varchar(24) NULL ,
  empMotivoInactivaRetencionSubsidio varchar(26) NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='RolAfiliado_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.RolAfiliado_aud;
CREATE TABLE aud.RolAfiliado_aud (
  roaId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  roaCargo varchar(200) NULL ,
  roaClaseIndependiente varchar(50) NULL ,
  roaClaseTrabajador varchar(20) NULL ,
  roaEstadoAfiliado varchar(8) NULL ,
  roaEstadoEnEntidadPagadora varchar(20) NULL ,
  roaFechaIngreso date NULL ,
  roaFechaRetiro datetime NULL ,
  roaHorasLaboradasMes smallint NULL ,
  roaIdentificadorAnteEntidadPagadora varchar(15) NULL ,
  roaPorcentajePagoAportes numeric NULL ,
  roaTipoAfiliado varchar(30) NOT NULL ,
  roaTipoContrato varchar(20) NULL ,
  roaTipoSalario varchar(10) NULL ,
  roaValorSalarioMesadaIngresos numeric NULL ,
  roaAfiliado bigint NOT NULL ,
  roaEmpleador bigint NULL ,
  roaPagadorAportes bigint NULL ,
  roaPagadorPension smallint NULL ,
  roaSucursalEmpleador bigint NULL ,
  roaFechaAfiliacion date NULL ,
  roaMotivoDesafiliacion varchar(50) NULL ,
  roaSustitucionPatronal bit NULL ,
  roaFechaFinPagadorAportes date NULL ,
  roaFechaFinPagadorPension date NULL ,
  roaEstadoEnEntidadPagadoraPension varchar(20) NULL ,
  roaDiaHabilVencimientoAporte smallint NULL ,
  roaMarcaExpulsion varchar(22) NULL ,
  roaEnviadoAFiscalizacion bit NULL ,
  roaMotivoFiscalizacion varchar(30) NULL ,
  roaFechaFiscalizacion date NULL ,
  roaOportunidadPago varchar(11) NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='AdministradorSubsidio_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.AdministradorSubsidio_aud;
CREATE TABLE aud.AdministradorSubsidio_aud (
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  asuPersona bigint NOT NULL ,
  asuId bigint NOT NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='AdminSubsidioGrupo_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.AdminSubsidioGrupo_aud;
CREATE TABLE aud.AdminSubsidioGrupo_aud (
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  asgGrupoFamiliar bigint NOT NULL ,
  asgAdministradorSubsidio bigint NOT NULL ,
  asgMedioDePago bigint NULL ,
  asgAfiliadoEsAdminSubsidio bit NOT NULL ,
  asgMedioPagoActivo bit NOT NULL ,
  asgRelacionGrupoFamiliar smallint NULL ,
  asgId bigint NOT NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='IntentoNovedad_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.IntentoNovedad_aud;
CREATE TABLE aud.IntentoNovedad_aud (
  inoId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  inoCausaIntentoFallido varchar(255) NULL ,
  inoFechaInicioProceso datetime2 NULL ,
  inoSolicitud bigint NULL ,
  inoTipoTransaccion varchar(100) NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='IntentoAfiliacion_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.IntentoAfiliacion_aud;
CREATE TABLE aud.IntentoAfiliacion_aud (
  iafId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  iafCausaIntentoFallido varchar(50) NULL ,
  iafFechaCreacion datetime2 NULL ,
  iafFechaInicioProceso datetime2 NULL ,
  iafSedeCajaCompensacion varchar(2) NULL ,
  iafTipoTransaccion varchar(100) NULL ,
  iafUsuarioCreacion varchar(255) NULL ,
  iafSolicitud bigint NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SedeCajaCompensacion_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SedeCajaCompensacion_aud;
CREATE TABLE aud.SedeCajaCompensacion_aud (
  sccfId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  sccfNombre varchar(100) NULL ,
  sccfVirtual bit NULL ,
  sccCodigo varchar(2) NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Solicitud_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.Solicitud_aud;
CREATE TABLE aud.Solicitud_aud (
  solId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  solCanalRecepcion varchar(21) NULL ,
  solFechaRadicacion datetime2 NULL ,
  solInstanciaProceso varchar(255) NULL ,
  solNumeroRadicacion varchar(12) NULL ,
  solUsuarioRadicacion varchar(255) NULL ,
  solCajaCorrespondencia bigint NULL ,
  solTipoTransaccion varchar(100) NULL ,
  solCiudadUsuarioRadicacion varchar(255) NULL ,
  solEstadoDocumentacion varchar(50) NULL ,
  solMetodoEnvio varchar(20) NULL ,
  solClasificacion varchar(48) NULL ,
  solTipoRadicacion varchar(20) NULL ,
  solFechaCreacion datetime2 NULL ,
  solDestinatario varchar(255) NULL ,
  solSedeDestinatario varchar(2) NULL ,
  solObservacion varchar(500) NULL ,
  solCargaAfiliacionMultipleEmpleador bigint NULL ,
  solResultadoProceso varchar(22) NULL ,
  solDiferenciasCargueActualizacion bigint NULL ,
  solAnulada bit NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudAfiliaciEmpleador_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudAfiliaciEmpleador_aud;
CREATE TABLE aud.SolicitudAfiliaciEmpleador_aud (
  saeId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  saeCodigoEtiquetaPreimpresa varchar(12) NULL ,
  saeEstadoSolicitud varchar(50) NULL ,
  saeFechaAprobacionConsejo datetime2 NULL ,
  saeNumeroActoAdministrativo varchar(50) NULL ,
  saeNumeroCustodiaFisica varchar(12) NULL ,
  saeEmpleador bigint NULL ,
  saeSolicitudGlobal bigint NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudAfiliacionPersona_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudAfiliacionPersona_aud;
CREATE TABLE aud.SolicitudAfiliacionPersona_aud (
  sapId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  sapEstadoSolicitud varchar(50) NULL ,
  sapRolAfiliado bigint NULL ,
  sapSolicitudGlobal bigint NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Comunicado_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.Comunicado_aud;
CREATE TABLE aud.Comunicado_aud (
  comId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  comEmail varchar(255) NULL ,
  comIdentificaArchivoComunicado varchar(255) NULL ,
  comTextoAdicionar varchar(500) NULL ,
  comPlantillaComunicado bigint NULL ,
  comFechaComunicado datetime2 NULL ,
  comRemitente varchar(255) NULL ,
  comSedeCajaCompensacion varchar(2) NULL ,
  comNumeroCorreoMasivo bigint NULL ,
  comDestinatario varchar(255) NULL ,
  comEstadoEnvio varchar(20) NULL ,
  comMensajeEnvio varchar(max) NULL ,
  comMedioComunicado varchar(10) NULL ,
  comSolicitud bigint NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='ParametrizacionNovedad_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.ParametrizacionNovedad_aud;
CREATE TABLE aud.ParametrizacionNovedad_aud (
  novId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  novTipoTransaccion varchar(100) NULL ,
  novPuntoResolucion varchar(255) NULL ,
  novRutaCualificada varchar(255) NULL ,
  novTipoNovedad varchar(255) NULL ,
  novProceso varchar(50) NULL ,
  novAplicaTodosRoles bit NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudNovedad_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudNovedad_aud;
CREATE TABLE aud.SolicitudNovedad_aud (
  snoId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  snoEstadoSolicitud varchar(50) NULL ,
  snoNovedad bigint NULL ,
  snoSolicitudGlobal bigint NULL ,
  snoObservaciones varchar(200) NULL ,
  snoCargaMultiple bit NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudNovedadEmpleador_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudNovedadEmpleador_aud;
CREATE TABLE aud.SolicitudNovedadEmpleador_aud (
  sneId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  sneIdEmpleador bigint NULL ,
  sneIdSolicitudNovedad bigint NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudNovedadPersona_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SolicitudNovedadPersona_aud;
CREATE TABLE aud.SolicitudNovedadPersona_aud (
  snpId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  snpPersona bigint NOT NULL ,
  snpSolicitudNovedad bigint NOT NULL ,
  snpRolAfiliado bigint NULL ,
  snpBeneficiario bigint NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='NovedadDetalle_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.NovedadDetalle_aud;
CREATE TABLE aud.NovedadDetalle_aud (
  nopId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  nopFechaInicio date NULL ,
  nopFechaFin date NULL ,
  nopVigente bit NULL ,
  nopSolicitudNovedad bigint NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='AporteGeneral_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.AporteGeneral_aud;
CREATE TABLE aud.AporteGeneral_aud (
  apgId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  apgPeriodoAporte varchar(7) NULL ,
  apgValTotalApoObligatorio numeric NULL ,
  apgValorIntMora numeric NULL ,
  apgFechaRecaudo date NULL ,
  apgFechaProcesamiento datetime NULL ,
  apgCodEntidadFinanciera smallint NULL ,
  apgOperadorInformacion bigint NULL ,
  apgModalidadPlanilla varchar(40) NULL ,
  apgModalidadRecaudoAporte varchar(40) NULL ,
  apgApoConDetalle bit NULL ,
  apgNumeroCuenta varchar(17) NULL ,
  apgRegistroGeneral bigint NOT NULL ,
  apgPersona bigint NULL ,
  apgEmpresa bigint NULL ,
  apgSucursalEmpresa bigint NULL ,
  apgEstadoAportante varchar(50) NULL ,
  apgEstadoAporteAportante varchar(40) NULL ,
  apgEstadoRegistroAporteAportante varchar(30) NULL ,
  apgPagadorPorTerceros bit NULL ,
  apgOrigenAporte varchar(26) NULL ,
  apgCajaCompensacion int NULL ,
  apgTipoSolicitante varchar(13) NULL ,
  apgEmailAportante varchar(255) NULL ,
  apgEmpresaTramitadoraAporte bigint NULL ,
  apgFechaReconocimiento datetime NULL ,
  apgFormaReconocimientoAporte varchar(75) NULL ,
  apgMarcaPeriodo varchar(19) NULL ,
  apgMarcaActualizacionCartera bit NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='AporteDetallado_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.AporteDetallado_aud;
CREATE TABLE aud.AporteDetallado_aud (
  apdId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  apdAporteGeneral bigint NULL ,
  apdDiasCotizados smallint NULL ,
  apdHorasLaboradas smallint NULL ,
  apdSalarioBasico numeric NULL ,
  apdValorIBC numeric NULL ,
  apdValorIntMora numeric NULL ,
  apdTarifa numeric NULL ,
  apdAporteObligatorio numeric NULL ,
  apdValorSaldoAporte numeric NULL ,
  apdCorrecciones varchar(400) NULL ,
  apdEstadoAporteRecaudo varchar(50) NULL ,
  apdEstadoAporteAjuste varchar(50) NULL ,
  apdEstadoRegistroAporte varchar(50) NULL ,
  apdSalarioIntegral bit NULL ,
  apdMunicipioLaboral smallint NULL ,
  apdDepartamentoLaboral smallint NULL ,
  apdRegistroDetallado bigint NOT NULL ,
  apdTipoCotizante varchar(100) NULL ,
  apdEstadoCotizante varchar(60) NULL ,
  apdEstadoAporteCotizante varchar(50) NULL ,
  apdEstadoRegistroAporteCotizante varchar(40) NULL ,
  apdPersona bigint NULL ,
  apdUsuarioAprobadorAporte varchar(50) NOT NULL ,
  apdEstadoRegistroAporteArchivo varchar(60) NOT NULL ,
  apdCodSucursal varchar(10) NULL ,
  apdNomSucursal varchar(100) NULL ,
  apdFechaMovimiento date NULL ,
  apdFechaCreacion date NULL ,
  apdFormaReconocimientoAporte varchar(75) NULL ,
  apdMarcaPeriodo varchar(19) NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SocioEmpleador_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.SocioEmpleador_aud;
CREATE TABLE aud.SocioEmpleador_aud (
  semId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  semExistenCapitulaciones bit NULL ,
  semIdentifiDocumCapitulaciones varchar(255) NULL ,
  semConyugue bigint NULL ,
  semEmpleador bigint NULL ,
  semPersona bigint NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='PersonaDetalle_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.PersonaDetalle_aud;
CREATE TABLE aud.PersonaDetalle_aud (
  pedId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  pedPersona bigint NOT NULL ,
  pedFechaNacimiento date NULL ,
  pedFechaExpedicionDocumento date NULL ,
  pedGenero varchar(10) NULL ,
  pedOcupacionProfesion int NULL ,
  pedNivelEducativo varchar(100) NULL ,
  pedGradoAcademico smallint NULL ,
  pedCabezaHogar bit NULL ,
  pedAutorizaUsoDatosPersonales bit NULL ,
  pedResideSectorRural bit NULL ,
  pedEstadoCivil varchar(20) NULL ,
  pedHabitaCasaPropia bit NULL ,
  pedFallecido bit NULL ,
  pedFechaFallecido date NULL ,
  pedBeneficiarioSubsidio bit NULL ,
  pedEstudianteTrabajoDesarrolloHumano bit NULL ,
  pedFechaDefuncion date NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='BeneficiarioDetalle_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.BeneficiarioDetalle_aud;
CREATE TABLE aud.BeneficiarioDetalle_aud (
  bedId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  bedSalarioMensual numeric NULL ,
  bedLabora bit NULL ,
  bedPersonaDetalle bigint NOT NULL ,
  bedCertificadoEscolaridad bit NULL ,
  bedFechaRecepcionCertificadoEscolar date NULL ,
  bedFechaVencimientoCertificadoEscolar date NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Beneficiario_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.Beneficiario_aud;
CREATE TABLE aud.Beneficiario_aud (
  benId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  benCertificadoEscolaridad bit NULL ,
  benEstadoBeneficiarioAfiliado varchar(20) NULL ,
  benEstadoBeneficiarioCaja varchar(20) NULL ,
  benEstudianteTrabajoDesarrolloHumano bit NULL ,
  benFechaAfiliacion date NULL ,
  benFechaRecepcionCertificadoEscolar date NULL ,
  benFechaVencimientoCertificadoEscolar date NULL ,
  benLabora bit NULL ,
  benTipoBeneficiario varchar(30) NOT NULL ,
  benGrupoFamiliar bigint NULL ,
  benPersona bigint NOT NULL ,
  benAfiliado bigint NOT NULL ,
  benSalarioMensualBeneficiario numeric NULL ,
  benGradoAcademico smallint NULL ,
  benMotivoDesafiliacion varchar(70) NULL ,
  benFechaRetiro date NULL ,
  benFechaInicioSociedadConyugal date NULL ,
  benFechaFinSociedadConyugal date NULL ,
  benRolAfiliado bigint NULL ,
  benBeneficiarioDetalle bigint NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='BeneficioEmpleador_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.BeneficioEmpleador_aud;
CREATE TABLE aud.BeneficioEmpleador_aud (
  bemId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  bemBeneficioActivo bit NULL ,
  bemFechaVinculacion date NULL ,
  bemFechaDesvinculacion date NULL ,
  bemEmpleador bigint NOT NULL ,
  bemBeneficio bigint NOT NULL ,
  bemMotivoInactivacion varchar(50) NULL ,
  revTime DATETIME NOT NULL
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CondicionInvalidez_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.CondicionInvalidez_aud;
CREATE TABLE aud.CondicionInvalidez_aud (
  coiId bigint NOT NULL ,
  REV bigint NOT NULL ,
  REVTYPE smallint NULL ,
  coiPersona bigint NOT NULL ,
  coiInvalidez bit NULL ,
  coiFechaReporteInvalidez date NULL ,
  coiComentarioInvalidez varchar(500) NULL ,
  revTime DATETIME NOT NULL
);

--changeset arocha:03

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DiasFestivos_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.DiasFestivos_aud;
CREATE TABLE aud.DiasFestivos_aud(
	pifId bigint NOT NULL,
    REV bigint NOT NULL ,
    REVTYPE smallint NULL ,
	pifConcepto varchar(150) NOT NULL,
	pifFecha date NOT NULL ,
	revTime DATETIME NOT NULL
);

--changeset dsuesca:04
--comment: Se crean nuevas tablas Departamento_aud, Municipio_aud, Ubicacion_aud
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Departamento_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.Departamento_aud;

CREATE TABLE aud.Departamento_aud (
  depId smallint NOT NULL,
  REV bigint NOT NULL,
  REVTYPE smallint,
  depCodigo varchar(2) NOT NULL,
  depIndicativoTelefoniaFija varchar(2) NOT NULL,
  depNombre varchar(100) NOT NULL,
  depExcepcionAplicaFOVIS bit,
  revTime DATETIME NOT NULL
);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Municipio_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.Municipio_aud;
CREATE TABLE aud.Municipio_aud (
  munId smallint,
  REV bigint NOT NULL,
  REVTYPE smallint,
  munCodigo varchar(5) NOT NULL,
  munNombre varchar(50) NOT NULL,
  munDepartamento smallint NOT NULL,
  revTime DATETIME NOT NULL
);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Ubicacion_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.Ubicacion_aud;
CREATE TABLE aud.Ubicacion_aud (
  ubiId bigint,
  REV bigint NOT NULL,
  REVTYPE smallint,
  ubiAutorizacionEnvioEmail bit,
  ubiCodigoPostal varchar(10),
  ubiDireccionFisica varchar(300),
  ubiEmail varchar(255),
  ubiIndicativoTelFijo varchar(2),
  ubiTelefonoCelular varchar(10),
  ubiTelefonoFijo varchar(7),
  ubiMunicipio smallint,
  ubiDescripcionIndicacion varchar(100),
  revTime DATETIME NOT NULL
);

--changeset dsuesca:05
--comment: Se crean nueva tabla DetalleSubsidioAsignado_aud
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DetalleSubsidioAsignado_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.DetalleSubsidioAsignado_aud;

CREATE TABLE aud.DetalleSubsidioAsignado_aud (
  dsaId bigint NOT NULL,
  REV bigint NOT NULL,
  REVTYPE smallint,
  dsaUsuarioCreador varchar(200) NOT NULL,
  dsaFechaHoraCreacion datetime NOT NULL,
  dsaEstado varchar(20) NOT NULL,
  dsaMotivoAnulacion varchar(40),
  dsaDetalleAnulacion varchar(250),
  dsaOrigenRegistroSubsidio varchar(30) NOT NULL,
  dsaTipoliquidacionSubsidio varchar(60) NOT NULL,
  dsaTipoCuotaSubsidio varchar(80) NOT NULL,
  dsaValorSubsidioMonetario numeric(19,5) NOT NULL,
  dsaValorDescuento numeric(19,5) NOT NULL,
  dsaValorOriginalAbonado numeric(19,5) NOT NULL,
  dsaValorTotal numeric(19,5) NOT NULL,
  dsaFechaTransaccionRetiro date,
  dsaUsuarioTransaccionRetiro varchar(200),
  dsaFechaTransaccionAnulacion date,
  dsaUsuarioTransaccionAnulacion varchar(200),
  dsaFechaHoraUltimaModificacion datetime,
  dsaUsuarioUltimaModificacion varchar(200),
  dsaSolicitudLiquidacionSubsidio bigint NOT NULL,
  dsaEmpleador bigint NOT NULL,
  dsaAfiliadoPrincipal bigint NOT NULL,
  dsaGrupoFamiliar bigint NOT NULL,
  dsaAdministradorSubsidio bigint NOT NULL,
  dsaIdRegistroOriginalRelacionado bigint,
  dsaCuentaAdministradorSubsidio bigint NOT NULL,
  dsaBeneficiarioDetalle bigint NOT NULL,
  dsaPeriodoLiquidado date NOT NULL,
  dsaResultadoValidacionLiquidacion bigint NOT NULL,
  dsaCondicionPersonaBeneficiario bigint,
  dsaCondicionPersonaAfiliado bigint,
  dsaCondicionPersonaEmpleador bigint,
  revTime DATETIME NOT NULL 
);

--changeset abaquero:01
--comment: Adición de campos de canal de recepción de reintegro por aportes o afiliación
ALTER TABLE aud.RolAfiliado_aud ADD roaCanalReingreso varchar(21)
ALTER TABLE aud.RolAfiliado_aud ADD roaReferenciaAporteReingreso bigint
ALTER TABLE aud.RolAfiliado_aud ADD roaReferenciaSolicitudReingreso bigint

--changeset abaquero:02
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='RolContactoEmpleador_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.RolContactoEmpleador_aud;--comment: Se añade la tabla RolContactoEmpleador_aud

CREATE TABLE aud.RolContactoEmpleador_aud (
	rceId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint,
	rceTipoRolContactoEmpleador varchar(50),
	rceEmpleador bigint,
	rcePersona bigint,
	rceCargo varchar(100),
	rcetoken varchar(50),
	rceCorreoEnviado bit,
	rceUbicacion bigint NOT NULL,
  	revTime DATETIME NOT NULL 
)

--changeset abaquero:03
--comment: Adición de marcas de trazabilidad para reintegro por aportes
ALTER TABLE aud.Empleador_aud ADD empCanalReingreso varchar(21)
ALTER TABLE aud.Empleador_aud ADD empReferenciaAporteReingreso bigint

--changeset dsuesca:06
--comment: Se cambia tipo de dato columna apdMunicipioLaboral
ALTER TABLE aud.AporteDetallado_aud ALTER COLUMN apdMunicipioLaboral VARCHAR(5);

--changeset dsuesca:07
--comment: Cambio de tipo de dato
ALTER TABLE aud.Solicitud_aud ALTER COLUMN solResultadoProceso VARCHAR(30);

--changeset abaquero:04
--comment: Se amplia el tamaño del campo de teléfono fijo en la tabla Ubicación
ALTER TABLE aud.Ubicacion_aud ALTER COLUMN ubiTelefonoFijo VARCHAR(10) null

--changeset jocorrea:05
--comment: Eliminacion de campos
ALTER TABLE aud.BeneficiarioDetalle_aud DROP COLUMN bedFechaRecepcionCertificadoEscolar;
ALTER TABLE aud.BeneficiarioDetalle_aud DROP COLUMN bedFechaVencimientoCertificadoEscolar;

--changeset abaquero:05
--comment: Se incluye el dato del número de planilla diligenciado en el formulario de aporte manual
ALTER TABLE aud.AporteGeneral_aud ADD apgNumeroPlanillaManual varchar(10)

--changeset abaquero:06
--comment: Se añade la tabla Cartera_aud
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Cartera_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.Cartera_aud;

CREATE TABLE aud.Cartera_aud (
	carId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint,
	carDeudaPresunta numeric(19,5),
	carEstadoCartera varchar(6) NOT NULL,
	carEstadoOperacion varchar(10) NOT NULL,
	carFechaCreacion datetime NOT NULL,
	carPersona bigint NOT NULL,
	carMetodo varchar(8),
	carPeriodoDeuda date NOT NULL,
	carRiesgoIncobrabilidad varchar(48),
	carTipoAccionCobro varchar(4),
	carTipoDeuda varchar(11),
	carTipoLineaCobro varchar(3),
	carTipoSolicitante varchar(13),
	carFechaAsignacionAccion datetime,
	carUsuarioTraspaso varchar(255),
  	revTime DATETIME NOT NULL 
)

--changeset dsuesca:08
--comment: Se añade la tabla Cartera_aud
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CertificadoEscolarBeneficiario_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.CertificadoEscolarBeneficiario_aud;

CREATE TABLE aud.CertificadoEscolarBeneficiario_aud (
cebId bigint NOT NULL,
REV bigint NOT NULL,
REVTYPE smallint,
cebBeneficiarioDetalle bigint NOT NULL,
cebFechaRecepcion date NULL,
cebFechaVencimiento date NULL
);

--changeset dsuesca:09
--comment: Se crea tabla Constante
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Constante_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.Constante_aud;

CREATE TABLE aud.Constante_aud (
  cnsId int NOT NULL,
  REV bigint NOT NULL,
  REVTYPE smallint,
  cnsNombre varchar(100),
  cnsValor varchar(150),
  cnsDescripcion varchar(250)  
) 

--changeset dsuesca:10
--comment: Se agregam campos revTime
ALTER TABLE aud.CertificadoEscolarBeneficiario_aud ADD revTime DATETIME NOT NULL;
ALTER TABLE aud.Constante_aud ADD revTime DATETIME NOT NULL;

--changeset abaquero:07
--comment: Se añade la tabla CarteraDetalle_aud
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CarteraDependiente_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.CarteraDependiente_aud;
CREATE TABLE aud.CarteraDependiente_aud (
	cadId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint,
	cadDeudaPresunta numeric(19,5),
	cadEstadoOperacion varchar(10) NOT NULL,
	cadCartera bigint NOT NULL,
	cadPersona bigint NOT NULL,
	cadDeudaReal numeric(19,5),
	cadAgregadoManual bigint,
  	revTime DATETIME NOT NULL 
)

--changeset abaquero:08
--comment: Se añaden las tablas ActividadCartera_aud y AgendaCartera_aud
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='ActividadCartera_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.ActividadCartera_aud;
CREATE TABLE aud.ActividadCartera_aud (
	acrId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint,
	acrActividadCartera varchar(42) NOT NULL,
	acrResultadoCartera varchar(33),
	acrComentarios varchar(500),
	acrCicloAportante bigint,
	acrFecha datetime NOT NULL,
	acrFechaCompromiso date,
	acrCartera bigint,
  	revTime DATETIME NOT NULL 
)

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='AgendaCartera_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.AgendaCartera_aud;
CREATE TABLE aud.AgendaCartera_aud (
	agrId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint,
	agrVisitaAgenda varchar(13),
	agrFecha date NOT NULL,
	agrHorario datetime NOT NULL,
	agrContacto varchar(255) NOT NULL,
	agrTelefono varchar(255),
	agrCicloAportante bigint,
	agrCartera bigint,
  	revTime DATETIME NOT NULL 
)

--changeset abaquero:09
--comment: Se añaden la tabla CajaCompensacion_aud

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CajaCompensacion_aud' AND TABLE_SCHEMA = 'aud')
DROP TABLE aud.CajaCompensacion_aud;

CREATE TABLE aud.CajaCompensacion_aud (
	ccfId int NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint,
	ccfHabilitado bit NOT NULL,
	ccfMetodoGeneracionEtiquetas varchar(150) NOT NULL,
	ccfNombre varchar(100) NOT NULL,
	ccfSocioAsopagos bit NOT NULL,
	ccfDepartamento smallint NOT NULL,
	ccfCodigo varchar(5) NOT NULL,
	ccfCodigoRedeban int,
  	revTime DATETIME NOT NULL 
)

--changeset jocorrea:10
--comment: 
ALTER TABLE aud.PersonaDetalle_aud ADD pedPersonaPadre BIGINT;
ALTER TABLE aud.PersonaDetalle_aud ADD pedPersonaMadre BIGINT;

