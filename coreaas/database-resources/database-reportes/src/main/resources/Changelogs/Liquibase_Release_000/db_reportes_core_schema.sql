--liquibase formatted sql

--changeset arocha:01

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='MedioDePago' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.MedioDePago;
CREATE TABLE dbo.MedioDePago (
  mdpId bigint NOT NULL ,
  mdpTipo varchar(30) NOT NULL ,
CONSTRAINT PK_MedioDePago_mdpId PRIMARY KEY  (
   mdpId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='MedioEfectivo' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.MedioEfectivo;
CREATE TABLE dbo.MedioEfectivo (
  mdpId bigint NOT NULL ,
  mefEfectivo bit NOT NULL ,
  mefSitioPago bigint NULL ,
  mefSedeCajaCompensacion bigint NULL ,
CONSTRAINT PK_MedioEfectivo_mdpId PRIMARY KEY  (
   mdpId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='MedioTarjeta' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.MedioTarjeta;
CREATE TABLE dbo.MedioTarjeta (
  mdpId bigint NOT NULL ,
  mtrNumeroTarjeta varchar(50) NOT NULL ,
  mtrDisponeTarjeta bit NOT NULL ,
  mtrEstadoTarjetaMultiservicios varchar(30) NOT NULL ,
  mtrSolicitudTarjeta varchar(30) NOT NULL ,
CONSTRAINT PK_MedioTarjeta_mdpId PRIMARY KEY  (
   mdpId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='MedioTransferencia' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.MedioTransferencia;
CREATE TABLE dbo.MedioTransferencia (
  mdpId bigint NOT NULL ,
  metBanco bigint NOT NULL ,
  metTipoCuenta varchar(30) NOT NULL ,
  metNumeroCuenta varchar(30) NOT NULL ,
  metTipoIdentificacionTitular varchar(20) NULL ,
  metNumeroIdentificacionTitular varchar(16) NULL ,
  metDigitoVerificacionTitular smallint NULL ,
  metNombreTitularCuenta varchar(200) NULL ,
CONSTRAINT PK_MedioTransferencia_mdpId PRIMARY KEY  (
   mdpId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Persona' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.Persona;
CREATE TABLE dbo.Persona (
  perId bigint NOT NULL ,
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
CONSTRAINT PK_Persona_perId PRIMARY KEY  (
   perId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Afiliado' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.Afiliado;
CREATE TABLE dbo.Afiliado (
  afiId bigint NOT NULL ,
  afiPersona bigint NULL ,
  afiPignoracionSubsidio bit NULL ,
  afiCesionSubsidio bit NULL ,
  afiRetencionSubsidio bit NULL ,
  afiServicioSinAfiliacion bit NULL ,
  afiCausaSinAfiliacion varchar(20) NULL ,
  afiFechaInicioServiciosSinAfiliacion date NULL ,
  afifechaFinServicioSinAfiliacion date NULL ,
CONSTRAINT PK_Afiliado_afiId PRIMARY KEY  (
   afiId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='GrupoFamiliar' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.GrupoFamiliar;
CREATE TABLE dbo.GrupoFamiliar (
  grfId bigint NOT NULL ,
  grfNumero smallint NOT NULL ,
  grfObservaciones varchar(500) NULL ,
  grfAfiliado bigint NOT NULL ,
  grfUbicacion bigint NULL ,
  grfInembargable bit NULL ,
CONSTRAINT PK_GrupoFamiliar_grfId PRIMARY KEY  (
   grfId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CodigoCIIU' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.CodigoCIIU;
CREATE TABLE dbo.CodigoCIIU (
  ciiId smallint NOT NULL ,
  ciiCodigo varchar(4) NOT NULL ,
  ciiDescripcion varchar(255) NOT NULL ,
  ciiCodigoSeccion varchar(1) NULL ,
  ciiDescripcionSeccion varchar(200) NULL ,
  ciiCodigoDivision varchar(2) NULL ,
  ciiDescripcionDivision varchar(250) NULL ,
  ciiCodigoGrupo varchar(3) NULL ,
  ciiDescripcionGrupo varchar(200) NULL ,
CONSTRAINT PK_CodigoCIIU_ciiId PRIMARY KEY  (
   ciiId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Empresa' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.Empresa;
CREATE TABLE dbo.Empresa (
  empId bigint NOT NULL ,
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
CONSTRAINT PK_Empresa_empId PRIMARY KEY  (
   empId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SucursalEmpresa' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SucursalEmpresa;
CREATE TABLE dbo.SucursalEmpresa (
  sueId bigint NOT NULL ,
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
CONSTRAINT PK_SucursalEmpresa_sueId PRIMARY KEY  (
   sueId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Empleador' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.Empleador;
CREATE TABLE dbo.Empleador (
  empId bigint NOT NULL ,
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
  empValidarSucursalPila bit NULL DEFAULT ((0)),
  empDiaHabilVencimientoAporte smallint NULL ,
  empMarcaExpulsion varchar(22) NULL ,
  empRetencionSubsidioActiva bit NULL ,
  empMotivoRetencionSubsidio varchar(24) NULL ,
  empMotivoInactivaRetencionSubsidio varchar(26) NULL ,
CONSTRAINT PK_Empleador_empId PRIMARY KEY  (
   empId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='RolAfiliado' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.RolAfiliado;
CREATE TABLE dbo.RolAfiliado (
  roaId bigint NOT NULL ,
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
CONSTRAINT PK_RolAfiliado_roaId PRIMARY KEY  (
   roaId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='AdministradorSubsidio' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.AdministradorSubsidio;
CREATE TABLE dbo.AdministradorSubsidio (
  asuId bigint NOT NULL ,
  asuPersona bigint NOT NULL ,
CONSTRAINT PK_AdministradorSubsidio_asuId PRIMARY KEY  (
   asuId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='AdminSubsidioGrupo' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.AdminSubsidioGrupo;
CREATE TABLE dbo.AdminSubsidioGrupo (
  asgId bigint NOT NULL ,
  asgGrupoFamiliar bigint NOT NULL ,
  asgAdministradorSubsidio bigint NOT NULL ,
  asgMedioDePago bigint NULL ,
  asgAfiliadoEsAdminSubsidio bit NOT NULL ,
  asgMedioPagoActivo bit NOT NULL ,
  asgRelacionGrupoFamiliar smallint NULL ,
CONSTRAINT PK_AdminSubsidioGrupo_asgId PRIMARY KEY  (
   asgId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DatoTemporalSolicitud' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DatoTemporalSolicitud;
CREATE TABLE dbo.DatoTemporalSolicitud (
  dtsId bigint NOT NULL ,
  dtsSolicitud bigint NULL ,
  dtsJsonPayload text NULL ,
  dtsTipoIdentificacion varchar(20) NULL ,
  dtsNumeroIdentificacion varchar(16) NULL ,
CONSTRAINT PK_DatoTemporalSolicitud_dtsId PRIMARY KEY  (
   dtsId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='IntentoNovedad' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.IntentoNovedad;
CREATE TABLE dbo.IntentoNovedad (
  inoId bigint NOT NULL ,
  inoCausaIntentoFallido varchar(255) NULL ,
  inoFechaInicioProceso datetime2 NULL ,
  inoSolicitud bigint NOT NULL ,
  inoTipoTransaccion varchar(100) NULL ,
CONSTRAINT PK_IntentoNovedad_inoId PRIMARY KEY  (
   inoId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='IntentoAfiliacion' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.IntentoAfiliacion;
CREATE TABLE dbo.IntentoAfiliacion (
  iafId bigint NOT NULL ,
  iafCausaIntentoFallido varchar(50) NULL ,
  iafFechaCreacion datetime2 NULL ,
  iafFechaInicioProceso datetime2 NULL ,
  iafSedeCajaCompensacion varchar(2) NULL ,
  iafTipoTransaccion varchar(100) NULL ,
  iafUsuarioCreacion varchar(255) NULL ,
  iafSolicitud bigint NULL ,
CONSTRAINT PK_IntentoAfiliacion_iafId PRIMARY KEY  (
   iafId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SedeCajaCompensacion' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SedeCajaCompensacion;
CREATE TABLE dbo.SedeCajaCompensacion (
  sccfId bigint NOT NULL ,
  sccfNombre varchar(100) NULL ,
  sccfVirtual bit NULL ,
  sccCodigo varchar(2) NULL ,
CONSTRAINT PK_SedeCajaCompensacion_sccfId PRIMARY KEY  (
   sccfId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Solicitud' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.Solicitud;
CREATE TABLE dbo.Solicitud (
  solId bigint NOT NULL ,
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
CONSTRAINT PK_Solicitud_solId PRIMARY KEY  (
   solId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudAfiliaciEmpleador' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudAfiliaciEmpleador;
CREATE TABLE dbo.SolicitudAfiliaciEmpleador (
  saeId bigint NOT NULL ,
  saeCodigoEtiquetaPreimpresa varchar(12) NULL ,
  saeEstadoSolicitud varchar(50) NULL ,
  saeFechaAprobacionConsejo datetime2 NULL ,
  saeNumeroActoAdministrativo varchar(50) NULL ,
  saeNumeroCustodiaFisica varchar(12) NULL ,
  saeEmpleador bigint NULL ,
  saeSolicitudGlobal bigint NULL ,
CONSTRAINT PK_SolicitudAfiliaciEmpleador_saeId PRIMARY KEY  (
   saeId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudAfiliacionPersona' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudAfiliacionPersona;
CREATE TABLE dbo.SolicitudAfiliacionPersona (
  sapId bigint NOT NULL ,
  sapEstadoSolicitud varchar(50) NULL ,
  sapRolAfiliado bigint NULL ,
  sapSolicitudGlobal bigint NULL ,
CONSTRAINT PK_SolicitudAfiliacionPersona_sapId PRIMARY KEY  (
   sapId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Comunicado' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.Comunicado;
CREATE TABLE dbo.Comunicado (
  comId bigint NOT NULL ,
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
CONSTRAINT PK_Comunicado_comId PRIMARY KEY  (
   comId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='ParametrizacionNovedad' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.ParametrizacionNovedad;
CREATE TABLE dbo.ParametrizacionNovedad (
  novId bigint NOT NULL ,
  novTipoTransaccion varchar(100) NULL ,
  novPuntoResolucion varchar(255) NULL ,
  novRutaCualificada varchar(255) NULL ,
  novTipoNovedad varchar(255) NULL ,
  novProceso varchar(50) NULL ,
  novAplicaTodosRoles bit NULL ,
CONSTRAINT PK_Novedad_novId PRIMARY KEY  (
   novId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudNovedad' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudNovedad;
CREATE TABLE dbo.SolicitudNovedad (
  snoId bigint NOT NULL ,
  snoEstadoSolicitud varchar(50) NULL ,
  snoNovedad bigint NULL ,
  snoSolicitudGlobal bigint NULL ,
  snoObservaciones varchar(200) NULL ,
  snoCargaMultiple bit NULL ,
CONSTRAINT PK_SolicitudNovedad_snoId PRIMARY KEY  (
   snoId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudNovedadEmpleador' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudNovedadEmpleador;
CREATE TABLE dbo.SolicitudNovedadEmpleador (
  sneId bigint NOT NULL ,
  sneIdEmpleador bigint NULL ,
  sneIdSolicitudNovedad bigint NULL ,
CONSTRAINT PK_SolicitudNovedadEmpleador_sneId PRIMARY KEY  (
   sneId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudNovedadPersona' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudNovedadPersona;
CREATE TABLE dbo.SolicitudNovedadPersona (
  snpId bigint NOT NULL ,
  snpPersona bigint NOT NULL ,
  snpSolicitudNovedad bigint NOT NULL ,
  snpRolAfiliado bigint NULL ,
  snpBeneficiario bigint NULL ,
CONSTRAINT PK_SolicitudNovedadPersona_snpId PRIMARY KEY  (
   snpId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='NovedadDetalle' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.NovedadDetalle;
CREATE TABLE dbo.NovedadDetalle (
  nopId bigint NOT NULL ,
  nopFechaInicio date NULL ,
  nopFechaFin date NULL ,
  nopVigente bit NULL ,
  nopSolicitudNovedad bigint NULL ,
CONSTRAINT PK_NovedadPila_nopId PRIMARY KEY  (
   nopId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='AporteGeneral' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.AporteGeneral;
CREATE TABLE dbo.AporteGeneral (
  apgId bigint NOT NULL ,
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
  apgTipoSolicitante varchar(13) NULL ,
  apgOrigenAporte varchar(26) NULL ,
  apgCajaCompensacion int NULL ,
  apgEmailAportante varchar(255) NULL ,
  apgEmpresaTramitadoraAporte bigint NULL ,
  apgFechaReconocimiento datetime NULL ,
  apgFormaReconocimientoAporte varchar(75) NULL ,
  apgMarcaPeriodo varchar(19) NULL ,
  apgMarcaActualizacionCartera bit NULL ,
CONSTRAINT PK_AporteGeneral_apgId PRIMARY KEY  (
   apgId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='AporteDetallado' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.AporteDetallado;
CREATE TABLE dbo.AporteDetallado (
  apdId bigint NOT NULL ,
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
CONSTRAINT PK_AporteDetallado_apdId PRIMARY KEY  (
   apdId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SocioEmpleador' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SocioEmpleador;
CREATE TABLE dbo.SocioEmpleador (
  semId bigint NOT NULL ,
  semExistenCapitulaciones bit NULL ,
  semIdentifiDocumCapitulaciones varchar(255) NULL ,
  semConyugue bigint NULL ,
  semEmpleador bigint NULL ,
  semPersona bigint NULL ,
CONSTRAINT PK_SocioEmpleador_semId PRIMARY KEY  (
   semId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='PersonaDetalle' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.PersonaDetalle;
CREATE TABLE dbo.PersonaDetalle (
  pedId bigint NOT NULL ,
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
CONSTRAINT PK_PersonaDetalle_pedId PRIMARY KEY  (
   pedId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='BeneficiarioDetalle' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.BeneficiarioDetalle;
CREATE TABLE dbo.BeneficiarioDetalle (
  bedId bigint NOT NULL ,
  bedSalarioMensual numeric NULL ,
  bedLabora bit NULL ,
  bedPersonaDetalle bigint NOT NULL ,
  bedCertificadoEscolaridad bit NULL ,
  bedFechaRecepcionCertificadoEscolar date NULL ,
  bedFechaVencimientoCertificadoEscolar date NULL ,
CONSTRAINT PK_BeneficiarioDetalle_bedId PRIMARY KEY  (
   bedId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Beneficiario' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.Beneficiario;
CREATE TABLE dbo.Beneficiario (
  benId bigint NOT NULL ,
  benEstadoBeneficiarioAfiliado varchar(20) NULL ,
  benEstudianteTrabajoDesarrolloHumano bit NULL ,
  benFechaAfiliacion date NULL ,
  benTipoBeneficiario varchar(30) NOT NULL ,
  benGrupoFamiliar bigint NULL ,
  benPersona bigint NOT NULL ,
  benAfiliado bigint NOT NULL ,
  benGradoAcademico smallint NULL ,
  benMotivoDesafiliacion varchar(70) NULL ,
  benFechaRetiro date NULL ,
  benFechaInicioSociedadConyugal date NULL ,
  benFechaFinSociedadConyugal date NULL ,
  benRolAfiliado bigint NULL ,
  benBeneficiarioDetalle bigint NULL ,
CONSTRAINT PK_Beneficiario_benId PRIMARY KEY  (
   benId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='BeneficioEmpleador' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.BeneficioEmpleador;
CREATE TABLE dbo.BeneficioEmpleador (
  bemId bigint NOT NULL ,
  bemBeneficioActivo bit NULL ,
  bemFechaVinculacion date NULL ,
  bemFechaDesvinculacion date NULL ,
  bemEmpleador bigint NOT NULL ,
  bemBeneficio bigint NOT NULL ,
  bemMotivoInactivacion varchar(50) NULL ,
CONSTRAINT PK_Beneficio_bemId PRIMARY KEY  (
   bemId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CondicionInvalidez' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.CondicionInvalidez;
CREATE TABLE dbo.CondicionInvalidez (
  coiId bigint NOT NULL ,
  coiPersona bigint NOT NULL ,
  coiInvalidez bit NULL ,
  coiFechaReporteInvalidez date NULL ,
  coiComentarioInvalidez varchar(500) NULL ,
CONSTRAINT PK_CondicionInvalidez_coiId PRIMARY KEY  (
   coiId
  )
);

--changeset arocha:02

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='DiasFestivos' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.DiasFestivos;
CREATE TABLE dbo.DiasFestivos(
	pifId bigint NOT NULL,
	pifConcepto varchar(150) NOT NULL,
	pifFecha date NOT NULL,
 CONSTRAINT PK_PilaDiasFestivos_pifId PRIMARY KEY CLUSTERED 
(
	pifId ASC
)
);


--changeset dsuesca:03
--comment: Se crean nuevas tablas Departamento, Municipio, Ubicacion

CREATE TABLE Departamento (
  depId smallint,
  depCodigo varchar(2) NOT NULL,
  depIndicativoTelefoniaFija varchar(2) NOT NULL,
  depNombre varchar(100) NOT NULL,
  depExcepcionAplicaFOVIS bit,
  CONSTRAINT PK_Departamento_depId PRIMARY KEY (depId)
);

CREATE TABLE Municipio (
  munId smallint,
  munCodigo varchar(5) NOT NULL,
  munNombre varchar(50) NOT NULL,
  munDepartamento smallint NOT NULL,
  CONSTRAINT PK_Municipio_munId PRIMARY KEY (munId)
);

CREATE TABLE Ubicacion (
  ubiId bigint,
  ubiAutorizacionEnvioEmail bit,
  ubiCodigoPostal varchar(10),
  ubiDireccionFisica varchar(300),
  ubiEmail varchar(255),
  ubiIndicativoTelFijo varchar(2),
  ubiTelefonoCelular varchar(10),
  ubiTelefonoFijo varchar(7),
  ubiMunicipio smallint,
  ubiDescripcionIndicacion varchar(100),
  CONSTRAINT PK_Ubicacion_ubiId PRIMARY KEY (ubiId)  
);

--changeset dsuesca:04
--comment: Se crean nuevas tablas DetalleSubsidioAsignado
CREATE TABLE DetalleSubsidioAsignado (
  dsaId bigint NOT NULL,
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
  CONSTRAINT PK_DetalleSubsidioAsignado_dsaId PRIMARY KEY (dsaId)
  );

--changeset abaquero:01
--comment: Adición de campos de canal de recepción de reintegro por aportes o afiliación
ALTER TABLE dbo.RolAfiliado ADD roaCanalReingreso varchar(21)
ALTER TABLE dbo.RolAfiliado ADD roaReferenciaAporteReingreso bigint
ALTER TABLE dbo.RolAfiliado ADD roaReferenciaSolicitudReingreso bigint

--changeset abaquero:02
--comment: Se añade la tabla RolContactoEmpleador
CREATE TABLE RolContactoEmpleador (
	rceId bigint NOT NULL IDENTITY(1,1),
	rceTipoRolContactoEmpleador varchar(50),
	rceEmpleador bigint,
	rcePersona bigint,
	rceCargo varchar(100),
	rcetoken varchar(50),
	rceCorreoEnviado bit,
	rceUbicacion bigint NOT NULL,
	CONSTRAINT PK_RolContactoEmpleador_rceId PRIMARY KEY (rceId)
);

--changeset abaquero:03
--comment: Adición de marcas de trazabilidad para reintegro por aportes
ALTER TABLE dbo.Empleador ADD empCanalReingreso varchar(21)
ALTER TABLE dbo.Empleador ADD empReferenciaAporteReingreso bigint

--changeset dsuesca:05
--comment: Se cambia tipo de dato columna apdMunicipioLaboral
ALTER TABLE AporteDetallado ALTER COLUMN apdMunicipioLaboral VARCHAR(5);

--changeset dsuesca:06
--comment: Cambio de tipo de dato
ALTER TABLE Solicitud ALTER COLUMN solResultadoProceso VARCHAR(30);

--changeset abaquero:04
--comment: Se amplia el tamaño del campo de teléfono fijo en la tabla Ubicación
ALTER TABLE Ubicacion ALTER COLUMN ubiTelefonoFijo VARCHAR(10) null

--changeset jocorrea:05
--comment: Eliminacion de campos
ALTER TABLE BeneficiarioDetalle DROP COLUMN bedFechaRecepcionCertificadoEscolar;
ALTER TABLE BeneficiarioDetalle DROP COLUMN bedFechaVencimientoCertificadoEscolar;

--changeset abaquero:05
--comment: Se incluye el dato del número de planilla diligenciado en el formulario de aporte manual
ALTER TABLE dbo.AporteGeneral ADD apgNumeroPlanillaManual varchar(10)

--changeset abaquero:06
--comment: Se añade la tabla Cartera
CREATE TABLE Cartera (
	carId bigint NOT NULL IDENTITY(1,1),
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
	CONSTRAINT PK_Cartera_carId PRIMARY KEY (carId)
)

--changeset dsuesca:07
--comment: Se añade la tabla 
CREATE TABLE CertificadoEscolarBeneficiario (
cebId bigint NOT NULL,
cebBeneficiarioDetalle bigint NOT NULL,
cebFechaRecepcion date NULL,
cebFechaVencimiento date NULL,
CONSTRAINT PK_CerficadoEscolarBeneficiario_cebId PRIMARY KEY (cebId)
);

--changeset dsuesca:08
--comment: 
DROP TABLE RolContactoEmpleador

CREATE TABLE RolContactoEmpleador (
  rceId bigint NOT NULL,
  rceTipoRolContactoEmpleador varchar(50),
  rceEmpleador bigint,
  rcePersona bigint,
  rceCargo varchar(100),
  rcetoken varchar(50),
  rceCorreoEnviado bit,
  rceUbicacion bigint NOT NULL,
  CONSTRAINT PK_RolContactoEmpleador_rceId PRIMARY KEY (rceId)
)

DROP TABLE Cartera

CREATE TABLE Cartera (
  carId bigint NOT NULL,
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
  CONSTRAINT PK_Cartera_carId PRIMARY KEY (carId)
)

--changeset dsuesca:09
--comment: Se crea tabla Constante
CREATE TABLE Constante (
  cnsId int NOT NULL,
  cnsNombre varchar(100),
  cnsValor varchar(150),
  cnsDescripcion varchar(250) NOT NULL,
  CONSTRAINT PK_Constante_cnsId PRIMARY KEY (cnsId)
);

--changeset abaquero:07
--comment: Se añade la tabla CarteraDetalle
CREATE TABLE dbo.CarteraDependiente (
	cadId bigint NOT NULL IDENTITY(1,1),
	cadDeudaPresunta numeric(19,5),
	cadEstadoOperacion varchar(10) NOT NULL,
	cadCartera bigint NOT NULL,
	cadPersona bigint NOT NULL,
	cadDeudaReal numeric(19,5),
	cadAgregadoManual bigint,
	CONSTRAINT PK_CarteraDependiente_cadId PRIMARY KEY (cadId)
)

--changeset abaquero:08
--comment: Se añaden las tablas ActividadCartera y AgendaCartera
CREATE TABLE ActividadCartera (
	acrId bigint NOT NULL,
	acrActividadCartera varchar(42) NOT NULL,
	acrResultadoCartera varchar(33),
	acrComentarios varchar(500),
	acrCicloAportante bigint,
	acrFecha datetime NOT NULL,
	acrFechaCompromiso date,
	acrCartera bigint,
	CONSTRAINT PK_ActividadCartera_acrId PRIMARY KEY (acrId)
)

CREATE TABLE AgendaCartera (
	agrId bigint NOT NULL,
	agrVisitaAgenda varchar(13),
	agrFecha date NOT NULL,
	agrHorario datetime NOT NULL,
	agrContacto varchar(255) NOT NULL,
	agrTelefono varchar(255),
	agrCicloAportante bigint,
	agrCartera bigint,
	CONSTRAINT PK_AgendaCartera_agrId PRIMARY KEY (agrId)
)

--changeset abaquero:09
--comment: Se añaden la tabla CajaCompensacion
CREATE TABLE CajaCompensacion (
	ccfId int NOT NULL,
	ccfHabilitado bit NOT NULL,
	ccfMetodoGeneracionEtiquetas varchar(150) NOT NULL,
	ccfNombre varchar(100) NOT NULL,
	ccfSocioAsopagos bit NOT NULL,
	ccfDepartamento smallint NOT NULL,
	ccfCodigo varchar(5) NOT NULL,
	ccfCodigoRedeban int
)

--changeset jocorrea:10
--comment: 
ALTER TABLE PersonaDetalle ADD pedPersonaPadre BIGINT;
ALTER TABLE PersonaDetalle ADD pedPersonaMadre BIGINT;

--changeset dsuesca:11
--comment: 
DROP TABLE CarteraDependiente

CREATE TABLE CarteraDependiente (
  cadId bigint NOT NULL,
  cadDeudaPresunta numeric(19,5),
  cadEstadoOperacion varchar(10) NOT NULL,
  cadCartera bigint NOT NULL,
  cadPersona bigint NOT NULL,
  cadDeudaReal numeric(19,5),
  cadAgregadoManual bigint,
  CONSTRAINT PK_CarteraDependiente_cadId PRIMARY KEY (cadId)
)

DROP TABLE CajaCompensacion

CREATE TABLE CajaCompensacion (
  ccfId int NOT NULL,
  ccfHabilitado bit NOT NULL,
  ccfMetodoGeneracionEtiquetas varchar(150) NOT NULL,
  ccfNombre varchar(100) NOT NULL,
  ccfSocioAsopagos bit NOT NULL,
  ccfDepartamento smallint NOT NULL,
  ccfCodigo varchar(5) NOT NULL,
  ccfCodigoRedeban int,
  CONSTRAINT PK_CajaCompensacion_ccfId PRIMARY KEY (ccfId)
)


