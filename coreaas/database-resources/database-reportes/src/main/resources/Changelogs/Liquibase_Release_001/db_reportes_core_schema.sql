--liquibase formatted sql
--
--changeset arocha:01 runOnChange:true

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='MovimientoAporte' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.MovimientoAporte;
CREATE TABLE dbo.MovimientoAporte (
  moaId bigint NOT NULL ,
  moaTipoAjuste varchar(20) NULL ,
  moaTipoMovimiento varchar(23) NULL ,
  moaEstadoAporte varchar(22) NULL ,
  moaValorAporte numeric NULL ,
  moaValorInteres numeric NULL ,
  moaFechaActualizacionEstado datetime NULL ,
  moaFechaCreacion datetime NULL ,
  moaAporteDetallado bigint NULL ,
  moaAporteGeneral bigint NOT NULL ,
CONSTRAINT PK_MovimientoAporte_moaId PRIMARY KEY  (
   moaId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CarteraAgrupadora' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.CarteraAgrupadora;
CREATE TABLE dbo.CarteraAgrupadora (
  cagId bigint NOT NULL ,
  cagNumeroOperacion bigint NOT NULL ,
  cagCartera bigint NOT NULL ,
CONSTRAINT PK_CarteraAgrupadora_cagId PRIMARY KEY  (
   cagId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudPreventiva' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudPreventiva;
CREATE TABLE dbo.SolicitudPreventiva (
  sprId bigint NOT NULL ,
  sprActualizacionEfectiva bit NULL ,
  sprBackActualizacion varchar(255) NULL ,
  sprContactoEfectivo bit NULL ,
  sprEstadoSolicitudPreventiva varchar(34) NULL ,
  sprPersona bigint NOT NULL ,
  sprRequiereFiscalizacion bit NULL ,
  sprTipoSolicitanteMovimientoAporte varchar(14) NOT NULL ,
  sprSolicitudGlobal bigint NOT NULL ,
  sprTipoGestionCartera varchar(10) NULL ,
  sprFechaFiscalizacion date NULL ,
  sprCantidadVecesMoroso smallint NULL ,
  sprEstadoActualCartera varchar(6) NULL ,
  sprFechaLimitePago date NULL ,
  sprSolicitudPreventivaAgrupadora bigint NULL ,
  sprTrabajadoresActivos smallint NULL ,
  sprValorPromedioAportes numeric NULL ,
CONSTRAINT PK_SolicitudPreventiva_sprId PRIMARY KEY  (
   sprId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudPreventivaAgrupadora' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudPreventivaAgrupadora;
CREATE TABLE dbo.SolicitudPreventivaAgrupadora (
  spaId bigint NOT NULL ,
  spaEstadoSolicitudPreventivaAgrupadora varchar(255) NULL ,
  spaSolicitudGlobal bigint NULL ,
CONSTRAINT PK_SolicitudPreventivaAgrupadora_spaId PRIMARY KEY  (
   spaId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudGestionCobroManual' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudGestionCobroManual;
CREATE TABLE dbo.SolicitudGestionCobroManual (
  scmId bigint NOT NULL ,
  scmCicloAportante bigint NOT NULL ,
  scmEstadoSolicitud varchar(25) NULL ,
  scmSolicitudGlobal bigint NOT NULL ,
  scmLineaCobro varchar(3) NOT NULL ,
CONSTRAINT PK_SolicitudGestionCobroManual_scmId PRIMARY KEY  (
   scmId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudGestionCobroFisico' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudGestionCobroFisico;
CREATE TABLE dbo.SolicitudGestionCobroFisico (
  sgfId bigint NOT NULL ,
  sgfDocumentoSoporte bigint NULL ,
  sgfEstado varchar(52) NULL ,
  sgfFechaRemision datetime NULL ,
  sgfObservacionRemision varchar(255) NULL ,
  sgfTipoAccionCobro varchar(4) NOT NULL ,
  sgfSolicitud bigint NOT NULL ,
CONSTRAINT PK_SolicitudGestionCobroFisico_sgfId PRIMARY KEY  (
   sgfId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudGestionCobroElectronico' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudGestionCobroElectronico;
CREATE TABLE dbo.SolicitudGestionCobroElectronico (
  sgeId bigint NOT NULL ,
  sgeEstado varchar(52) NOT NULL ,
  sgeCartera bigint NOT NULL ,
  sgeTipoAccionCobro varchar(4) NOT NULL ,
  sgeSolicitud bigint NOT NULL ,
CONSTRAINT PK_SolicitudGestionCobroElectronico_sgeId PRIMARY KEY  (
   sgeId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudFiscalizacion' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudFiscalizacion;
CREATE TABLE dbo.SolicitudFiscalizacion (
  sfiId bigint NOT NULL ,
  sfiEstadoFiscalizacion varchar(11) NULL ,
  sfiSolicitudGlobal bigint NOT NULL ,
  sfiCicloAportante bigint NOT NULL ,
CONSTRAINT PK_SolicitudFiscalizacion_sfiId PRIMARY KEY  (
   sfiId
  )
);

--changeset arocha:02 runOnChange:true

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CicloAsignacion' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.CicloAsignacion;
CREATE TABLE dbo.CicloAsignacion (
  ciaId bigint NOT NULL ,
  ciaNombre varchar(50) NULL ,
  ciaFechaInicio date NULL ,
  ciaFechaFin date NULL ,
  ciaCicloPredecesor bigint NULL ,
  ciaEstadoCicloAsignacion varchar(30) NULL ,
  ciaCicloActivo bit NULL ,
  ciaValorDisponible numeric NULL ,
CONSTRAINT PK_CicloAsignacion_ciaId PRIMARY KEY  (
   ciaId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudAsignacion' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudAsignacion;
CREATE TABLE dbo.SolicitudAsignacion (
  safId bigint NOT NULL ,
  safSolicitudGlobal bigint NOT NULL ,
  safFechaAceptacion datetime NULL ,
  safUsuario varchar(50) NULL ,
  safValorSFVAsignado numeric NULL ,
  safEstadoSolicitudAsignacion varchar(50) NULL ,
  safComentarios varchar(500) NULL ,
  safCicloAsignacion bigint NOT NULL ,
  safComentarioControlInterno varchar(500) NULL ,
CONSTRAINT PK_SolicitudAsignacion_safId PRIMARY KEY  (
   safId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='JefeHogar' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.JefeHogar;
CREATE TABLE dbo.JefeHogar (
  jehId bigint NOT NULL ,
  jehAfiliado bigint NOT NULL ,
  jehEstadoHogar varchar(10) NULL ,
  jehIngresoMensual numeric NULL ,
CONSTRAINT PK_JefeHogar_jehId PRIMARY KEY  (
   jehId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='IntegranteHogar' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.IntegranteHogar;
CREATE TABLE dbo.IntegranteHogar (
  inhId bigint NOT NULL ,
  inhJefeHogar bigint NOT NULL ,
  inhPersona bigint NOT NULL ,
  inhIntegranteReemplazaJefeHogar bit NULL ,
  inhTipoIntegrante varchar(32) NOT NULL ,
  inhEstadoHogar varchar(10) NULL ,
  inhIntegranteValido bit NULL ,
  inhSalarioMensual numeric NULL ,
CONSTRAINT PK_IntegranteHogar_inhId PRIMARY KEY  (
   inhId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='PostulacionFOVIS' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.PostulacionFOVIS;
CREATE TABLE dbo.PostulacionFOVIS (
  pofId bigint NOT NULL ,
  pofCicloAsignacion bigint NULL ,
  pofJefeHogar bigint NULL ,
  pofEstadoHogar varchar(58) NULL ,
  pofCondicionHogar varchar(44) NULL ,
  pofHogarPerdioSubsidioNoPago bit NULL ,
  pofCantidadFolios smallint NULL ,
  pofValorSFVSolicitado numeric NULL ,
  pofProyectoSolucionVivienda bigint NULL ,
  pofModalidad varchar(50) NULL ,
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
CONSTRAINT PK_PostulacionFOVIS_pofId PRIMARY KEY  (
   pofId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudPostulacion' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudPostulacion;
CREATE TABLE dbo.SolicitudPostulacion (
  spoId bigint NOT NULL ,
  spoSolicitudGlobal bigint NOT NULL ,
  spoPostulacionFOVIS bigint NULL ,
  spoEstadoSolicitud varchar(42) NULL ,
  spoObservaciones varchar(500) NULL ,
  spoObservacionesWeb varchar(500) NULL ,
CONSTRAINT PK_SolicitudPostulacion_spoId PRIMARY KEY  (
   spoId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='LegalizacionDesembolso' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.LegalizacionDesembolso;
CREATE TABLE dbo.LegalizacionDesembolso (
  lgdId bigint NOT NULL ,
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
CONSTRAINT PK_LegalizacionDesembolso_legId PRIMARY KEY  (
   lgdId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudLegalizacionDesembolso' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudLegalizacionDesembolso;
CREATE TABLE dbo.SolicitudLegalizacionDesembolso (
  sldId bigint NOT NULL ,
  sldSolicitudGlobal bigint NOT NULL ,
  sldPostulacionFOVIS bigint NULL ,
  sldEstadoSolicitud varchar(60) NULL ,
  sldLegalizacionDesembolso bigint NULL ,
  sldObservaciones varchar(500) NULL ,
  sldFechaOperacion datetime NULL ,
  sldJsonPostulacion text NULL ,
  sldCantidadReintentos smallint NULL DEFAULT ((0)),
CONSTRAINT PK_SolicitudLegalizacionDesembolso_sldId PRIMARY KEY  (
   sldId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudNovedadFovis' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudNovedadFovis;
CREATE TABLE dbo.SolicitudNovedadFovis (
  snfId bigint NOT NULL ,
  snfSolicitudGlobal bigint NOT NULL ,
  snfEstadoSolicitud varchar(38) NOT NULL ,
  snfParametrizacionNovedad bigint NOT NULL ,
  snfObservaciones varchar(200) NULL ,
CONSTRAINT PK_SolicitudNovedadFovis_snfId PRIMARY KEY  (
   snfId
  )
);
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='SolicitudNovedadPersonaFovis' AND TABLE_SCHEMA = 'dbo')
DROP TABLE dbo.SolicitudNovedadPersonaFovis;
CREATE TABLE dbo.SolicitudNovedadPersonaFovis (
  spfId bigint NOT NULL ,
  spfPersona bigint NULL ,
  spfSolicitudNovedadFovis bigint NOT NULL ,
  spfPostulacionFovis bigint NOT NULL ,
CONSTRAINT PK_SolicitudNovedadPersonaFovis_spfId PRIMARY KEY  (
   spfId
  )
);

--changeset dsuesca:2
--comment: 
ALTER TABLE RolAfiliado ADD roaFechaFinContrato DATE;
ALTER TABLE PostulacionFovis ADD pofValorAvaluoCatastral NUMERIC(19,5) NULL;

--changeset dsuesca:3
--comment: 
ALTER TABLE CategoriaAfiliado DROP CONSTRAINT FK_CategoriaAfiliado_ctaAfiliado;
ALTER TABLE CategoriaBeneficiario DROP CONSTRAINT FK_CategoriaBeneficiario_ctbBeneficiarioDetalle;
ALTER TABLE aud.Beneficiario_aud DROP COLUMN benCertificadoEscolaridad;



--changeset dsuesca:5
--comment: 
ALTER TABLE Cartera ADD carDeudaPresuntaUnitaria  numeric(19,5);
ALTER TABLE aud.Cartera_aud ADD carDeudaPresuntaUnitaria  numeric(19,5);

--changeset dsuesca:6
--comment: 
DROP TABLE EstadoArchivoPILA
DROP TABLE FactCondicionAportante
DROP TABLE FactCondicionBeneficiario
DROP TABLE FactCondicionCotizante
DROP TABLE FactCondicionEmpleador
DROP TABLE FactCondicionPersona
DROP TABLE FactNovedadEmpleador
DROP TABLE FactNovedadPersona
DROP TABLE FactSolicitudAfiliacionEmpleador
DROP TABLE FactSolicitudAfiliacionPersona
DROP TABLE FactArchivoPILA
DROP TABLE DimAportante
DROP TABLE DimBeneficiario
--DROP TABLE DimCanal
DROP TABLE DimCotizante
DROP TABLE DimEmpleador
DROP TABLE DimEstadoAportante
DROP TABLE DimEstadoBeneficiario
DROP TABLE DimEstadoEmpleador
DROP TABLE DimEstadoNovedad
DROP TABLE DimEstadoPersona
DROP TABLE DimEstadoRecaudo
DROP TABLE DimEstadoSolicitud
--DROP TABLE DimMetasCanalPeriodo
DROP TABLE DimNaturalezaJuridica
DROP TABLE DimNaturalezaPersona
DROP TABLE DimPagoAportes
DROP TABLE DimPersona
DROP TABLE DimRangoTiempoAporte
DROP TABLE DimRangoTiempoRespuesta
DROP TABLE DimResultadoSolicitud
DROP TABLE DimTamanioEmpleador
DROP TABLE DimTipoAfiliacion
DROP TABLE DimTipoAportante
DROP TABLE DimTipoBeneficiario
DROP TABLE DimTipoNovedad
DROP TABLE DimTipoNovedadEmpleador
DROP TABLE DimTipoNovedadPersona
DROP TABLE DimTipoPersona

--changeset dsuesca:7
--comment: 

CREATE TABLE DimTipoAfiliacion
(
  ditId TINYINT NOT NULL,
  ditDescripcion VARCHAR(20) NOT NULL, --'Nueva','Reintegro'
  CONSTRAINT PK_DimTipoAfiliacion_ditId PRIMARY KEY CLUSTERED 
  (
    ditId ASC
  )
);
INSERT INTO dbo.DimTipoAfiliacion VALUES (1,'Nueva'),(2,'Reintegro');

CREATE TABLE DimResultadoSolicitud
(
  dirId TINYINT NOT NULL,
  dirDescripcion VARCHAR(20) NOT NULL, --'Intento','Rechazo'
  CONSTRAINT PK_DimResultadoSolicitud_ditId PRIMARY KEY CLUSTERED 
  (
    dirId ASC
  )
);
INSERT INTO dbo.DimResultadoSolicitud VALUES (1,'Intento'),(2,'Rechazo');

CREATE TABLE DimTamanioEmpleador
(
  dteId TINYINT NOT NULL,
  dteDescripcion VARCHAR(30) NOT NULL, --'I (0 a 10 empleados)','II (11 a 50 empleados)','III (51 a 200 empleados)','IV (más de 200 empleados)'
  CONSTRAINT PK_DimTamanioEmpleador_dteId PRIMARY KEY CLUSTERED 
  (
    dteId ASC
  )
);
INSERT INTO dbo.DimTamanioEmpleador VALUES (1,'I (0 a 10 empleados)'),(2,'II (11 a 50 empleados)'),(3,'III (51 a 200 empleados)'),(4,'IV (más de 200 empleados)');

CREATE TABLE DimNaturalezaJuridica
(
  dinId TINYINT NOT NULL,
  dinDescripcion VARCHAR(100) NOT NULL, --'Pública','Privada','Mixta','Organismos multilaterales','Entidades de derecho público no sometidas a la legislación colombiana'
  CONSTRAINT PK_DimTipoAfiliacion_dinId PRIMARY KEY CLUSTERED 
  (
    dinId ASC
  )
);
INSERT INTO dbo.DimNaturalezaJuridica VALUES (1,'Pública'),(2,'Privada'),(3,'Mixta'),(4,'Organismos multilaterales'),(5,'Entidades de derecho público no sometidas a la legislación colombiana');

CREATE TABLE DimEstadoEmpleador
(
  deeId TINYINT NOT NULL,
  deeDescripcion VARCHAR(20) NOT NULL, --'Activo','Inactivo','No formalizada','Sin información'
  CONSTRAINT PK_DimEstadoEmpleador_deeId PRIMARY KEY CLUSTERED 
  (
    deeId ASC
  )
);
INSERT INTO dbo.DimEstadoEmpleador VALUES (1,'Activo'),(2,'Inactivo'),(3,'No formalizada'),(4,'Sin información');

CREATE TABLE DimPagoAportes
(
  dpaId TINYINT NOT NULL,
  dpaDescripcion VARCHAR(20) NOT NULL, --'Pago aportes','No pago de aportes'
  CONSTRAINT PK_DimPagoAportes_dpaId PRIMARY KEY CLUSTERED 
  (
    dpaId ASC
  )
);
INSERT INTO dbo.DimPagoAportes VALUES (1,'Pago aportes'),(2,'No pago de aportes');

CREATE TABLE DimRangoTiempoRespuesta
(
  drtId TINYINT NOT NULL,
  drtDescripcion VARCHAR(20) NOT NULL, --'0 a 1 día','1 a 2 días','2 a 3 días','3 días o más'
  CONSTRAINT PK_DimRangoTiempoRespuesta_drtId PRIMARY KEY CLUSTERED 
  (
    drtId ASC
  )
);
INSERT INTO dbo.DimRangoTiempoRespuesta VALUES (1,'0 a 1 día'),(2,'1 a 2 días'),(3,'2 a 3 días'),(4,'3 días o más');


CREATE TABLE DimEmpleador
(
  demId BIGINT IDENTITY NOT NULL,
  demTipoIdentificacion VARCHAR(20) NOT NULL,
  demNumeroIdentificacion VARCHAR(16) NOT NULL,
  demDigitoVerificacion SMALLINT NULL,
  demRazonSocial VARCHAR(250) NULL,
  demPrimerNombre VARCHAR(50) NULL,
  demSegundoNombre VARCHAR(50) NULL,
  demPrimerApellido VARCHAR(50) NULL,
  demSegundoApellido VARCHAR(50) NULL,
  CONSTRAINT PK_DimEmpleador_demId PRIMARY KEY CLUSTERED 
  (
    demId ASC
  )
);

CREATE TABLE FactCondicionEmpleador
(
  fceId BIGINT NOT NULL IDENTITY,
  fceDimEmpleador BIGINT NOT NULL,
  fceDimPeriodo INT NOT NULL,
  fceDimCanal TINYINT NULL,
  fceDimSede INT NULL,
  fceDimTipoAfiliacion TINYINT NULL,
  fceDimTamanioEmpleador TINYINT NULL,
  fceDimNaturalezaJuridica TINYINT NULL,
  fceDimEstadoEmpleador TINYINT NULL,
  fceDimPagoAportes TINYINT NULL,
  fceFechaCambioEstadoAfiliacion DATETIME NULL,
  fceFechaPrimeraRevision DATETIME NULL,
  fceFechaUltimoCanalRecepcion DATETIME NULL,
  fceFechaAportes DATETIME NULL,
  fceAccionActivacion BIT NULL,
  fceActivoPeriodoAnterior BIT NULL,
  CONSTRAINT PK_FactCondicionEmpleador_fceId PRIMARY KEY CLUSTERED 
  (
    fceId ASC
  )
);

ALTER TABLE dbo.FactCondicionEmpleador ADD CONSTRAINT FK_FactCondicionEmpleador_fceDimPeriodo FOREIGN KEY (fceDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactCondicionEmpleador ADD CONSTRAINT FK_FactCondicionEmpleador_fceDimCanal FOREIGN KEY (fceDimCanal) REFERENCES dbo.DimCanal (dicId);
ALTER TABLE dbo.FactCondicionEmpleador ADD CONSTRAINT FK_FactCondicionEmpleador_fceDimTipoAfiliacion FOREIGN KEY (fceDimTipoAfiliacion) REFERENCES dbo.DimTipoAfiliacion (ditId);
ALTER TABLE dbo.FactCondicionEmpleador ADD CONSTRAINT FK_FactCondicionEmpleador_fceDimTamanioEmpleador FOREIGN KEY (fceDimTamanioEmpleador) REFERENCES dbo.DimTamanioEmpleador (dteId);
ALTER TABLE dbo.FactCondicionEmpleador ADD CONSTRAINT FK_FactCondicionEmpleador_fceDimNaturalezaJuridica FOREIGN KEY (fceDimNaturalezaJuridica) REFERENCES dbo.DimNaturalezaJuridica (dinId);
ALTER TABLE dbo.FactCondicionEmpleador ADD CONSTRAINT FK_FactCondicionEmpleador_fceDimEstadoEmpleador FOREIGN KEY (fceDimEstadoEmpleador) REFERENCES dbo.DimEstadoEmpleador (deeId);
ALTER TABLE dbo.FactCondicionEmpleador ADD CONSTRAINT FK_FactCondicionEmpleador_fceDimPagoAportes FOREIGN KEY (fceDimPagoAportes) REFERENCES dbo.DimPagoAportes (dpaId);
ALTER TABLE dbo.FactCondicionEmpleador ADD CONSTRAINT FK_FactCondicionEmpleador_fceDimEmpleador FOREIGN KEY (fceDimEmpleador) REFERENCES dbo.DimEmpleador (demId);
ALTER TABLE dbo.FactCondicionEmpleador ADD CONSTRAINT FK_FactCondicionEmpleador_fceDimSede FOREIGN KEY (fceDimSede) REFERENCES dbo.DimSede (disId);

CREATE TABLE FactSolicitudAfiliacionEmpleador
(
  fseId BIGINT IDENTITY NOT NULL,
  fseDimEmpleador BIGINT NOT NULL,
  fseDimPeriodo INT NOT NULL,
  fseSolicitud BIGINT NOT NULL,
  fseEmpleador BIGINT NULL,
  fseFechaInicioSolicitud DATETIME NOT NULL,
  fseFechaCambioEstadoSolicitud DATETIME NOT NULL,
  fseDimRangoTiempoRespuestaNotificacion TINYINT NULL,
  fseDimResultadoSolicitud TINYINT NULL,
  fseCerrada BIT,
  CONSTRAINT PK_FactSolicitudAfiliacionEmpleador_fseId PRIMARY KEY CLUSTERED 
  (
    fseId ASC
  )
);

ALTER TABLE dbo.FactSolicitudAfiliacionEmpleador ADD CONSTRAINT FK_FactSolicitudAfiliacionEmpleador_fseDimEmpleador FOREIGN KEY (fseDimEmpleador) REFERENCES dbo.DimEmpleador (demId);
ALTER TABLE dbo.FactSolicitudAfiliacionEmpleador ADD CONSTRAINT FK_FactSolicitudAfiliacionEmpleador_fseDimPeriodo FOREIGN KEY (fseDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactSolicitudAfiliacionEmpleador ADD CONSTRAINT FK_FactSolicitudAfiliacionEmpleador_fseSolicitud FOREIGN KEY (fseSolicitud) REFERENCES dbo.Solicitud (solId);
ALTER TABLE dbo.FactSolicitudAfiliacionEmpleador ADD CONSTRAINT FK_FactSolicitudAfiliacionEmpleador_fseEmpleador FOREIGN KEY (fseEmpleador) REFERENCES dbo.Empleador (empId);
ALTER TABLE dbo.FactSolicitudAfiliacionEmpleador ADD CONSTRAINT FK_FactSolicitudAfiliacionEmpleador_fseDimRangoTiempoRespuestaNotificacion FOREIGN KEY (fseDimRangoTiempoRespuestaNotificacion) REFERENCES dbo.DimRangoTiempoRespuesta (drtId);
ALTER TABLE dbo.FactSolicitudAfiliacionEmpleador ADD CONSTRAINT FK_FactSolicitudAfiliacionEmpleador_fseDimResultadoSolicitud FOREIGN KEY (fseDimResultadoSolicitud) REFERENCES dbo.DimResultadoSolicitud (dirId);

CREATE TABLE DimPersona
(
  dpeId BIGINT IDENTITY NOT NULL,
  dpeTipoIdentificacion VARCHAR(20) NOT NULL,
  dpeNumeroIdentificacion VARCHAR(16) NOT NULL,
  dpePrimerNombre VARCHAR(50) NULL,
  dpeSegundoNombre VARCHAR(50) NULL,
  dpePrimerApellido VARCHAR(50) NULL,
  dpeSegundoApellido VARCHAR(50) NULL,
  CONSTRAINT PK_DimPersona_dpeId PRIMARY KEY CLUSTERED 
  (
    dpeId ASC
  )
);

CREATE TABLE DimEstadoPersona

(
  depId TINYINT NOT NULL,
  depDescripcion VARCHAR(20) NOT NULL, --'Activo','Inactivo','No formalizada','Sin información'
  CONSTRAINT PK_DimEstadoPersona_depId PRIMARY KEY CLUSTERED 
  (
    depId ASC
  )
);

INSERT INTO dbo.DimEstadoPersona VALUES (1,'Activo'),(2,'Inactivo'),(3,'No formalizada'),(4,'Sin información');

CREATE TABLE DimNaturalezaPersona

(
  dnpId TINYINT NOT NULL,
  dnpDescripcion VARCHAR(20) NOT NULL, --'Dependiente','Independiente','Pensionado'
  CONSTRAINT PK_DimNaturalezaPersona_dnpId PRIMARY KEY CLUSTERED 
  (
    dnpId ASC
  )
);

INSERT INTO dbo.DimNaturalezaPersona VALUES (1,'Dependiente'),(2,'Independiente'),(3,'Pensionado');


CREATE TABLE DimEstadoSolicitud

(
  desId TINYINT NOT NULL,
  desDescripcion VARCHAR(20) NOT NULL, --'Dependiente','Independiente','Pensionado'
  CONSTRAINT PK_DimEstadoSolicitud_desId PRIMARY KEY CLUSTERED 
  (
    desId ASC
  )
);

INSERT INTO dbo.DimEstadoSolicitud VALUES (1,'Rechazada'),(2,'Cerrada');

CREATE TABLE FactCondicionPersona
(
  fcpId BIGINT NOT NULL IDENTITY,
  fcpDimPersona BIGINT NOT NULL,
  fcpDimPeriodo INT NOT NULL,
  fcpDimCanal TINYINT NULL,
  fcpDimTipoAfiliacion TINYINT NULL,
  fcpDimNaturalezaPersona TINYINT NULL,
  fcpDimEstadoPersona TINYINT NULL,
  fcpDimPagoAportes TINYINT NULL,
  fcpFechaCambioEstadoAfiliacion DATETIME NULL,
  fcpFechaPrimeraRevision DATETIME NULL,
  fcpFechaUltimoCanalRecepcion DATETIME NULL,
  fcpFechaAportes DATETIME NULL,
  fcpAccionActivacion BIT NULL,
  fcpActivoPeriodoAnterior BIT NULL,
  CONSTRAINT PK_FactCondicionPersona_fcpId PRIMARY KEY CLUSTERED 
  (
    fcpId ASC
  )
);

ALTER TABLE dbo.FactCondicionPersona ADD CONSTRAINT FK_FactCondicionPersona_fcpDimPeriodo FOREIGN KEY (fcpDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactCondicionPersona ADD CONSTRAINT FK_FactCondicionPersona_fcpDimCanal FOREIGN KEY (fcpDimCanal) REFERENCES dbo.DimCanal (dicId);
ALTER TABLE dbo.FactCondicionPersona ADD CONSTRAINT FK_FactCondicionPersona_fcpDimTipoAfiliacion FOREIGN KEY (fcpDimTipoAfiliacion) REFERENCES dbo.DimTipoAfiliacion (ditId);
ALTER TABLE dbo.FactCondicionPersona ADD CONSTRAINT FK_FactCondicionPersona_fcpDimNaturalezaPersona FOREIGN KEY (fcpDimNaturalezaPersona) REFERENCES dbo.DimNaturalezaPersona (dnpId);
ALTER TABLE dbo.FactCondicionPersona ADD CONSTRAINT FK_FactCondicionPersona_fcpDimEstadoPersona FOREIGN KEY (fcpDimEstadoPersona) REFERENCES dbo.DimEstadoPersona (depId);
ALTER TABLE dbo.FactCondicionPersona ADD CONSTRAINT FK_FactCondicionPersona_fcpDimPagoAportes FOREIGN KEY (fcpDimPagoAportes) REFERENCES dbo.DimPagoAportes (dpaId);
ALTER TABLE dbo.FactCondicionPersona ADD CONSTRAINT FK_FactCondicionPersona_fcpDimPersona FOREIGN KEY (fcpDimPersona) REFERENCES dbo.DimPersona (dpeId);


CREATE TABLE FactSolicitudAfiliacionPersona
(
  fspId BIGINT IDENTITY NOT NULL,
  fspDimPersona BIGINT NOT NULL,
  fspDimPeriodo INT NOT NULL,
  fspSolicitud BIGINT NOT NULL,
  fspRolAfiliado BIGINT NULL,
  fspFechaInicioSolicitud DATETIME NOT NULL,
  fspFechaCambioEstadoSolicitud DATETIME NOT NULL,
  fspDimRangoTiempoRespuestaNotificacion TINYINT NULL,
  fspDimResultadoSolicitud TINYINT NULL,
  fspDimEstadoSolicitud TINYINT NULL,
  fspCerrada BIT,
  CONSTRAINT PK_FactSolicitudAfiliacionPersona_fspId PRIMARY KEY CLUSTERED 
  (
    fspId ASC
  )
);

ALTER TABLE dbo.FactSolicitudAfiliacionPersona ADD CONSTRAINT FK_FactSolicitudAfiliacionPersona_fspDimPersona FOREIGN KEY (fspDimPersona) REFERENCES dbo.DimPersona (dpeId);
ALTER TABLE dbo.FactSolicitudAfiliacionPersona ADD CONSTRAINT FK_FactSolicitudAfiliacionPersona_fspDimPeriodo FOREIGN KEY (fspDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactSolicitudAfiliacionPersona ADD CONSTRAINT FK_FactSolicitudAfiliacionPersona_fspSolicitud FOREIGN KEY (fspSolicitud) REFERENCES dbo.Solicitud (solId);
ALTER TABLE dbo.FactSolicitudAfiliacionPersona ADD CONSTRAINT FK_FactSolicitudAfiliacionPersona_fspRolAfiliado FOREIGN KEY (fspRolafiliado) REFERENCES dbo.rolAfiliado (roaId);
ALTER TABLE dbo.FactSolicitudAfiliacionPersona ADD CONSTRAINT FK_FactSolicitudAfiliacionPersona_fspDimRangoTiempoRespuestaNotificacion FOREIGN KEY (fspDimRangoTiempoRespuestaNotificacion) REFERENCES dbo.DimRangoTiempoRespuesta (drtId);
ALTER TABLE dbo.FactSolicitudAfiliacionPersona ADD CONSTRAINT FK_FactSolicitudAfiliacionPersona_fspDimResultadoSolicitud FOREIGN KEY (fspDimResultadoSolicitud) REFERENCES dbo.DimResultadoSolicitud (dirId);
ALTER TABLE dbo.FactSolicitudAfiliacionPersona ADD CONSTRAINT FK_FactSolicitudAfiliacionPersona_fspDimEstadoSolicitud FOREIGN KEY (fspDimEstadoSolicitud) REFERENCES dbo.DimEstadoSolicitud (desId);


CREATE TABLE DimEstadoBeneficiario

(
  debId TINYINT NOT NULL,
  debDescripcion VARCHAR(20) NOT NULL, --'Activo','Inactivo'
  CONSTRAINT PK_DimEstadoBeneficiario_debId PRIMARY KEY CLUSTERED 
  (
    debId ASC
  )
);

INSERT INTO dbo.DimEstadoBeneficiario VALUES (1,'Activo'),(2,'Inactivo');

CREATE TABLE DimTipoBeneficiario

(
  dtbId TINYINT NOT NULL,
  dtbDescripcion VARCHAR(30) NOT NULL, --'Activo','Inactivo'
  CONSTRAINT PK_DimTipoBeneficiario_dtbId PRIMARY KEY CLUSTERED 
  (
    dtbId ASC
  )
);

INSERT INTO dbo.DimTipoBeneficiario VALUES (1,'Cónyuge (compañero permanente)'),(2,'Padres'),(3,'Hermano'),(4,'Hijo'),(5,'En custodia');

CREATE TABLE DimBeneficiario
(
  dbeId BIGINT IDENTITY NOT NULL,
  dbeTipoIdentificacion VARCHAR(20) NOT NULL,
  dbeNumeroIdentificacion VARCHAR(16) NOT NULL,
  dbePrimerNombre VARCHAR(50) NULL,
  dbeSegundoNombre VARCHAR(50) NULL,
  dbePrimerApellido VARCHAR(50) NULL,
  dbeSegundoApellido VARCHAR(50) NULL,
  CONSTRAINT PK_DimBeneficiario_dbeId PRIMARY KEY CLUSTERED 
  (
    dbeId ASC
  )
);


CREATE TABLE FactCondicionBeneficiario
(
  fcbId BIGINT IDENTITY NOT NULL,
  fcbDimPeriodo INT NOT NULL,
  fcbDimPersona BIGINT NOT NULL,
  fcbDimBeneficiario BIGINT NOT NULL,
  fcbDimEstadoBeneficiario TINYINT NOT NULL,
  fcbDimTipoBeneficiario TINYINT NOT NULL,
  fcbAccionActivacion BIT NULL,
  fcbActivoPeriodoAnterior BIT NULL,
  CONSTRAINT PK_FactCondicionBeneficiario_fcbId PRIMARY KEY CLUSTERED 
  (
    fcbId ASC
  )
);

ALTER TABLE dbo.FactCondicionBeneficiario ADD CONSTRAINT FK_FactCondicionBeneficiario_fcbDimPeriodo FOREIGN KEY (fcbDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactCondicionBeneficiario ADD CONSTRAINT FK_FactCondicionBeneficiario_fcbDimPersona FOREIGN KEY (fcbDimPersona) REFERENCES dbo.DimPersona (dpeId);
ALTER TABLE dbo.FactCondicionBeneficiario ADD CONSTRAINT FK_FactCondicionBeneficiario_fcbDimBeneficiario FOREIGN KEY (fcbDimBeneficiario) REFERENCES dbo.DimBeneficiario (dbeId);
ALTER TABLE dbo.FactCondicionBeneficiario ADD CONSTRAINT FK_FactCondicionBeneficiario_fcbDimEstadoBeneficiario FOREIGN KEY (fcbDimEstadoBeneficiario) REFERENCES dbo.DimEstadoBeneficiario (debId);
ALTER TABLE dbo.FactCondicionBeneficiario ADD CONSTRAINT FK_FactCondicionBeneficiario_fcbDimTipoBeneficiario FOREIGN KEY (fcbDimTipoBeneficiario) REFERENCES dbo.DimTipoBeneficiario (dtbId);

CREATE TABLE dbo.EstadoArchivoPILA(
  eppId bigint NOT NULL IDENTITY,
  eppIndicePlanilla bigint NOT NULL,
  eppIdPlanilla bigint NOT NULL,
  eppEstadoArchivo varchar(75) NULL,
  eppFechaRecaudo date NULL,
  eppEnColaProceso BIT NULL
);

CREATE TABLE DimAportante
(
  dapId BIGINT IDENTITY NOT NULL,
  dapTipoIdentificacion VARCHAR(20) NOT NULL,
  dapNumeroIdentificacion VARCHAR(16) NOT NULL,
  dapDigitoVerificacion SMALLINT NULL,
  dapRazonSocial VARCHAR(250) NULL,
  dapPrimerNombre VARCHAR(50) NULL,
  dapSegundoNombre VARCHAR(50) NULL,
  dapPrimerApellido VARCHAR(50) NULL,
  dapSegundoApellido VARCHAR(50) NULL,
  CONSTRAINT PK_DimAportante_dapId PRIMARY KEY CLUSTERED 
  (
    dapId ASC
  )
);

CREATE TABLE DimCotizante
(
  dcoId BIGINT IDENTITY NOT NULL,
  dcoTipoIdentificacion VARCHAR(20) NOT NULL,
  dcoNumeroIdentificacion VARCHAR(16) NOT NULL,
  dcoPrimerNombre VARCHAR(50) NULL,
  dcoSegundoNombre VARCHAR(50) NULL,
  dcoPrimerApellido VARCHAR(50) NULL,
  dcoSegundoApellido VARCHAR(50) NULL,
  CONSTRAINT PK_DimCotizante_dcoId PRIMARY KEY CLUSTERED 
  (
    dcoId ASC
  )
);

CREATE TABLE DimRangoTiempoAporte
(
  draId TINYINT NOT NULL,
  draDescripcion VARCHAR(25) NOT NULL,
  CONSTRAINT PK_DimRangoTiempoAporte_draId PRIMARY KEY CLUSTERED 
  (
    draId ASC
  )
);
INSERT INTO dbo.DimRangoTiempoAporte VALUES (1,'10 o más días antes'),(2,'10 a 5 días antes'),(3,'5 a 0 días antes'),(4,'0 a 5 días después'),(5,'5 a 10 días después'),(6,'10 o más días después');

CREATE TABLE DimTipoAportante
(
  dtaId TINYINT NOT NULL,
  dtaDescripcion VARCHAR(25) NOT NULL,
  CONSTRAINT PK_DimTipoAportante_dtaId PRIMARY KEY CLUSTERED 
  (
    dtaId ASC
  )
);
INSERT INTO dbo.DimTipoAportante VALUES (1,'Independiente'),(2,'Empresa > 200 aportantes'),(3,'Empresa < 200 aportantes');

CREATE TABLE DimEstadoRecaudo
(
  derId TINYINT NOT NULL,
  derDescripcion VARCHAR(20) NOT NULL,
  CONSTRAINT PK_DimEstadoRecaudo_derId PRIMARY KEY CLUSTERED 
  (
    derId ASC
  )
);
INSERT INTO dbo.DimEstadoRecaudo VALUES (1,'Exitoso'),(2,'Inconsistente');

CREATE TABLE DimEstadoAportante
(
  deaId TINYINT NOT NULL,
  deaDescripcion VARCHAR(20) NOT NULL,
  CONSTRAINT PK_DimEstadoAportante_deaId PRIMARY KEY CLUSTERED 
  (
    deaId ASC
  )
);
INSERT INTO dbo.DimEstadoAportante VALUES (1,'Activo'),(2,'Inactivo'),(3,'No formalizada'),(4,'Sin información');

CREATE TABLE DimTipoNovedad
(
  dtnId TINYINT NOT NULL,
  dtnDescripcion VARCHAR(20) NOT NULL,
  CONSTRAINT PK_DimTipoNovedad_dtnId PRIMARY KEY CLUSTERED 
  (
    dtnId ASC
  )
);
INSERT INTO dbo.DimTipoNovedad VALUES (1,'Ingreso'),(2,'Retiro');

CREATE TABLE FactCondicionAportante
(
  fcaId BIGINT IDENTITY NOT NULL,
  fcaDimPeriodo INT NOT NULL,
  fcaDimAportante BIGINT NOT NULL,
  fcaDimEstadoAportante TINYINT NOT NULL,
  fcaDimTipoAportante TINYINT NOT NULL,
  fcaDimRangoTiempoAporte TINYINT NULL
  CONSTRAINT PK_FactCondicionAportante_fcaId PRIMARY KEY CLUSTERED 
  (
    fcaId ASC
  )
);

ALTER TABLE dbo.FactCondicionAportante ADD CONSTRAINT FK_FactCondicionAportante_fcaDimPeriodo FOREIGN KEY (fcaDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactCondicionAportante ADD CONSTRAINT FK_FactCondicionAportante_fcaDimAportante FOREIGN KEY (fcaDimAportante) REFERENCES dbo.DimAportante (dapId);
ALTER TABLE dbo.FactCondicionAportante ADD CONSTRAINT FK_FactCondicionAportante_fcaDimEstadoAportante FOREIGN KEY (fcaDimEstadoAportante) REFERENCES dbo.DimEstadoAportante (deaId);
ALTER TABLE dbo.FactCondicionAportante ADD CONSTRAINT FK_FactCondicionAportante_fcaDimTipoAportante FOREIGN KEY (fcaDimTipoAportante) REFERENCES dbo.DimTipoAportante (dtaId);
ALTER TABLE dbo.FactCondicionAportante ADD CONSTRAINT FK_FactCondicionAportante_fcaDimRangoTiempoAporte FOREIGN KEY (fcaDimRangoTiempoAporte) REFERENCES dbo.DimRangoTiempoAporte (draId);


CREATE TABLE FactCondicionCotizante
(
  fccId BIGINT IDENTITY NOT NULL,
  fccDimPeriodo INT NOT NULL,
  fccDimAportante BIGINT NOT NULL,
  fccDimCotizante BIGINT NOT NULL,
  fccDimEstadoRecaudo TINYINT NULL,
  fccDimTipoNovedad TINYINT NULL,
  CONSTRAINT PK_FactCondicionCotizante_fccId PRIMARY KEY CLUSTERED 
  (
    fccId ASC
  )
);

ALTER TABLE dbo.FactCondicionCotizante ADD CONSTRAINT FK_FactCondicionCotizante_fccDimPeriodo FOREIGN KEY (fccDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactCondicionCotizante ADD CONSTRAINT FK_FactCondicionCotizante_fccDimAportante FOREIGN KEY (fccDimAportante) REFERENCES dbo.DimAportante (dapId);
ALTER TABLE dbo.FactCondicionCotizante ADD CONSTRAINT FK_FactCondicionCotizante_fccDimCotizante FOREIGN KEY (fccDimCotizante) REFERENCES dbo.DimCotizante (dcoId);
ALTER TABLE dbo.FactCondicionCotizante ADD CONSTRAINT FK_FactCondicionCotizante_fccDimEstadoRecaudo FOREIGN KEY (fccDimEstadoRecaudo) REFERENCES dbo.DimEstadoRecaudo (derId);
ALTER TABLE dbo.FactCondicionCotizante ADD CONSTRAINT FK_FactCondicionCotizante_fccDimTipoNovedad FOREIGN KEY (fccDimTipoNovedad) REFERENCES dbo.DimTipoNovedad (dtnId);

CREATE TABLE FactArchivoPILA
(
  fapId BIGINT IDENTITY NOT NULL,
  fapDimPeriodo INT NOT NULL,
  fapDimAportante BIGINT NOT NULL,
  fapDimEstadoRecaudo TINYINT NOT NULL,
  fapDimEstadoAportante TINYINT NOT NULL,
  fapDimTipoAportante TINYINT NOT NULL,
  fapDimRangoTiempoAporte TINYINT NULL,
  fapIndicePlanilla BIGINT NOT NULL,
  fapIdPlanilla BIGINT NOT NULL,
  fapFechaRecibo DATETIME,
  fapPeriodoAporte DATE,
  fapFechaRecaudo DATETIME,
  fapValorAporte NUMERIC(19,5)
  CONSTRAINT PK_FactCondicionCotizante_fapId PRIMARY KEY CLUSTERED 
  (
    fapId ASC
  )
);

ALTER TABLE dbo.FactArchivoPILA ADD CONSTRAINT FK_FactArchivoPILA_fapDimPeriodo FOREIGN KEY (fapDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactArchivoPILA ADD CONSTRAINT FK_FactArchivoPILA_fapDimAportante FOREIGN KEY (fapDimAportante) REFERENCES dbo.DimAportante (dapId);
ALTER TABLE dbo.FactArchivoPILA ADD CONSTRAINT FK_FactArchivoPILA_fapDimEstadoRecaudo FOREIGN KEY (fapDimEstadoRecaudo) REFERENCES dbo.DimEstadoRecaudo (derId);
ALTER TABLE dbo.FactArchivoPILA ADD CONSTRAINT FK_FactArchivoPILA_fapDimEstadoAportante FOREIGN KEY (fapDimEstadoAportante) REFERENCES dbo.DimEstadoAportante (deaId);
ALTER TABLE dbo.FactArchivoPILA ADD CONSTRAINT FK_FactArchivoPILA_fapDimTipoAportante FOREIGN KEY (fapDimTipoAportante) REFERENCES dbo.DimTipoAportante (dtaId);
ALTER TABLE dbo.FactArchivoPILA ADD CONSTRAINT FK_FactArchivoPILA_fapDimRangoTiempoAporte FOREIGN KEY (fapDimRangoTiempoAporte) REFERENCES dbo.DimRangoTiempoAporte (draId);


--changeset arocha:06
--comment:Adicion DimTipoPersona y relacion FactCondicionAportante

CREATE TABLE DimTipoPersona
(
  dtpId TINYINT NOT NULL,
  dtpDescripcion VARCHAR(20) NOT NULL,
  CONSTRAINT PK_DimTipoPersona_dtpId PRIMARY KEY CLUSTERED 
  (
    dtpId ASC
  )
);
INSERT INTO dbo.DimTipoPersona VALUES (1,'Jurídica'),(2,'Natural');

ALTER TABLE dbo.FactCondicionAportante ADD fcaDimTipoPersona TINYINT;

ALTER TABLE dbo.FactCondicionAportante ADD CONSTRAINT FK_FactCondicionAportante_fcaDimTipoPersona FOREIGN KEY (fcaDimTipoPersona) REFERENCES dbo.DimTipoPersona (dtpId);

CREATE TABLE DimEstadoNovedad
(
  denId TINYINT NOT NULL,
  denDescripcion VARCHAR(20) NOT NULL,
  CONSTRAINT PK_DimEstadoNovedad_denId PRIMARY KEY CLUSTERED 
  (
    denId ASC
  )
);
INSERT INTO dbo.DimEstadoNovedad VALUES (1,'Radicada'),(2,'Aprobado'),(3,'Rechazada'),(4,'Cerrada');


CREATE TABLE DimTipoNovedadPersona
(
  dnoId TINYINT NOT NULL,
  dnoDescripcion VARCHAR(100) NOT NULL,
  CONSTRAINT PK_DimTipoNovedadPersona_dnoId PRIMARY KEY CLUSTERED 
  (
    dnoId ASC
  )
);
INSERT INTO dbo.DimTipoNovedadPersona VALUES 
(1,'Activar o inactivar beneficiarios'),
(2,'ctualización de datos básicos'),
(3,'Actualización de información laboral'),
(4,'Actualizar información relativa al pago de aportes para afiliados facultativos'),
(5,'Cambio de medio de pago'),
(6,'Fallecimiento'),
(7,'Grupo familiar'),
(8,'Invalidez'),
(9,'Medios de pago'),
(10,'Otras novedades'),
(11,'Pignoración del subsidio'),
(12,'Retiro y anulación de afiliación'),
(13,'Solicitud de activación/desactivación de cesión del subsidio monetario'),
(14,'Solicitud de activación/desactivación de retención del subsidio monetario');


CREATE TABLE DimTipoNovedadEmpleador
(
  dneId TINYINT NOT NULL,
  dneDescripcion VARCHAR(20) NOT NULL,
  CONSTRAINT PK_DimTipoNovedadEmpleador_dneId PRIMARY KEY CLUSTERED 
  (
    dneId ASC
  )
);
INSERT INTO dbo.DimTipoNovedadEmpleador VALUES 
(1,'Cambiar'),
(2,'Actualizar'),
(3,'Activar'),
(4,'Agregar'),
(5,'Inactivar'),
(6,'Trasladar'),
(7,'Sustituir'),
(8,'Desafiliar'),
(9,'Anular')


CREATE TABLE FactNovedadPersona
(
  fnpId BIGINT NOT NULL IDENTITY,
  fnpDimPersona BIGINT NOT NULL,
  fnpDimEmpleador BIGINT NULL,
  fnpDimBeneficiario BIGINT NULL,
  fnpDimPeriodo INT NOT NULL,
  fnpDimCanal TINYINT NULL,
  fnpDimSede INT NULL,
  fnpDimEstadoNovedad TINYINT NULL,
  fnpDimTipoNovedadPersona TINYINT NULL,
  fnpDimRangoTiempoRespuestaNovedad TINYINT NULL,
  fnpDimRangoTiempoRespuestaGestion TINYINT NULL,
  fnpDimEstadoPersona TINYINT NULL,
  fnpDimTipoBeneficiario TINYINT NULL,
  fnpDimEstadoEmpleador TINYINT NULL,
  fnpDimEstadoBeneficiario TINYINT NULL,
  fnpDimNaturalezaPersona TINYINT NULL,
  fnpSolicitud BIGINT NOT NULL,
  CONSTRAINT PK_FactNovedadPersona_fnpId PRIMARY KEY CLUSTERED 
  (
    fnpId ASC
  )
);

ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimPersona FOREIGN KEY (fnpDimPersona) REFERENCES dbo.DimPersona (dpeId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimEmpleador FOREIGN KEY (fnpDimEmpleador) REFERENCES dbo.DimEmpleador (demId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimBeneficiario FOREIGN KEY (fnpDimBeneficiario) REFERENCES dbo.DimBeneficiario (dbeId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimPeriodo FOREIGN KEY (fnpDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimCanal FOREIGN KEY (fnpDimCanal) REFERENCES dbo.DimCanal (dicId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimSede FOREIGN KEY (fnpDimSede) REFERENCES dbo.DimSede (disId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimEstadoNovedad FOREIGN KEY (fnpDimEstadoNovedad) REFERENCES dbo.DimEstadoNovedad (denId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimTipoNovedadPersona FOREIGN KEY (fnpDimTipoNovedadPersona) REFERENCES dbo.DimTipoNovedadPersona (dnoId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimRangoTiempoRespuestaNovedad FOREIGN KEY (fnpDimRangoTiempoRespuestaNovedad) REFERENCES dbo.DimRangoTiempoRespuesta (drtId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimRangoTiempoRespuestaGestion FOREIGN KEY (fnpDimRangoTiempoRespuestaGestion) REFERENCES dbo.DimRangoTiempoRespuesta (drtId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimEstadoPersona FOREIGN KEY (fnpDimEstadoPersona) REFERENCES dbo.DimEstadoPersona (depId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimTipoBeneficiario FOREIGN KEY (fnpDimTipoBeneficiario) REFERENCES dbo.DimTipoBeneficiario (dtbId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimEstadoEmpleador FOREIGN KEY (fnpDimEstadoEmpleador) REFERENCES dbo.DimEstadoEmpleador (deeId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimEstadoBeneficiario FOREIGN KEY (fnpDimEstadoBeneficiario) REFERENCES dbo.DimEstadoBeneficiario (debId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpDimNaturalezaPersona FOREIGN KEY (fnpDimNaturalezaPersona) REFERENCES dbo.DimNaturalezaPersona (dnpId);
ALTER TABLE dbo.FactNovedadPersona ADD CONSTRAINT FK_FactNovedadPersona_fnpSolicitud FOREIGN KEY (fnpSolicitud) REFERENCES dbo.Solicitud (solId);

CREATE TABLE FactNovedadEmpleador
(
  fneId BIGINT NOT NULL IDENTITY,
  fneDimEmpleador BIGINT NULL,
  fneDimPeriodo INT NOT NULL,
  fneDimCanal TINYINT NULL,
  fneDimSede INT NULL,
  fneDimEstadoNovedad TINYINT NULL,
  fneDimEstadoEmpleador TINYINT NULL,
  fneDimTipoNovedadEmpleador TINYINT NULL,
  fneDimRangoTiempoRespuestaNovedad TINYINT NULL,
  fneDimRangoTiempoRespuestaGestion TINYINT NULL,
  fneSolicitud BIGINT NOT NULL,
  CONSTRAINT PK_FactNovedadEmpleador_fnpeId PRIMARY KEY CLUSTERED 
  (
    fneId ASC
  )
);

ALTER TABLE dbo.FactNovedadEmpleador ADD CONSTRAINT FK_FactNovedadEmpleador_fneDimEmpleador FOREIGN KEY (fneDimEmpleador) REFERENCES dbo.DimEmpleador (demId);
ALTER TABLE dbo.FactNovedadEmpleador ADD CONSTRAINT FK_FactNovedadEmpleador_fneDimPeriodo FOREIGN KEY (fneDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactNovedadEmpleador ADD CONSTRAINT FK_FactNovedadEmpleador_fneDimCanal FOREIGN KEY (fneDimCanal) REFERENCES dbo.DimCanal (dicId);
ALTER TABLE dbo.FactNovedadEmpleador ADD CONSTRAINT FK_FactNovedadEmpleador_fneDimSede FOREIGN KEY (fneDimSede) REFERENCES dbo.DimSede (disId);
ALTER TABLE dbo.FactNovedadEmpleador ADD CONSTRAINT FK_FactNovedadEmpleador_fneDimEstadoNovedad FOREIGN KEY (fneDimEstadoNovedad) REFERENCES dbo.DimEstadoNovedad (denId);
ALTER TABLE dbo.FactNovedadEmpleador ADD CONSTRAINT FK_FactNovedadEmpleador_fneDimTipoNovedadEmpleador FOREIGN KEY (fneDimTipoNovedadEmpleador) REFERENCES dbo.DimTipoNovedadEmpleador (dneId);
ALTER TABLE dbo.FactNovedadEmpleador ADD CONSTRAINT FK_FactNovedadEmpleador_fneDimRangoTiempoRespuestaNovedad FOREIGN KEY (fneDimRangoTiempoRespuestaNovedad) REFERENCES dbo.DimRangoTiempoRespuesta (drtId);
ALTER TABLE dbo.FactNovedadEmpleador ADD CONSTRAINT FK_FactNovedadEmpleador_fneDimRangoTiempoRespuestaGestion FOREIGN KEY (fneDimRangoTiempoRespuestaGestion) REFERENCES dbo.DimRangoTiempoRespuesta (drtId);
ALTER TABLE dbo.FactNovedadEmpleador ADD CONSTRAINT FK_FactNovedadEmpleador_fneDimEstadoEmpleador FOREIGN KEY (fneDimEstadoEmpleador) REFERENCES dbo.DimEstadoEmpleador (deeId);
ALTER TABLE dbo.FactNovedadEmpleador ADD CONSTRAINT FK_FactNovedadEmpleador_fneSolicitud FOREIGN KEY (fneSolicitud) REFERENCES dbo.Solicitud (solId);

--changeset dsuesca:8
--comment: 
CREATE NONCLUSTERED INDEX nci_wi_RolAfiliado_163362A5D429548BA03A787CFCEF1C3A ON dbo.RolAfiliado
(
  roaEmpleador ASC,
  roaFechaRetiro ASC
)
INCLUDE (   roaAfiliado,
  roaEstadoAfiliado,
  roaFechaAfiliacion) WITH (STATISTICS_NORECOMPUTE = OFF, DROP_EXISTING = OFF, ONLINE = OFF);

CREATE NONCLUSTERED INDEX nci_wi_RolAfiliado_3BB9E351438A12725130451E3C43D2A3 ON dbo.RolAfiliado
(
  roaEstadoAfiliado ASC,
  roaEmpleador ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, DROP_EXISTING = OFF, ONLINE = OFF);

--changeset dsuesca:9
--comment: 
CREATE NONCLUSTERED INDEX nci_wi_RolAfiliado_pba ON dbo.RolAfiliado
(
  roaAfiliado ASC,
  roaEstadoAfiliado ASC,
  roaTipoAfiliado ASC
)
INCLUDE (   roaCanalReingreso,
  roaCargo,
  roaClaseIndependiente,
  roaClaseTrabajador,
  roaDiaHabilVencimientoAporte,
  roaEmpleador,
  roaEnviadoAFiscalizacion,
  roaEstadoEnEntidadPagadora,
  roaEstadoEnEntidadPagadoraPension,
  roaFechaAfiliacion,
  roaFechaFinContrato,
  roaFechaFinPagadorAportes,
  roaFechaFinPagadorPension,
  roaFechaFiscalizacion,
  roaFechaIngreso,
  roaFechaRetiro,
  roaHorasLaboradasMes,
  roaIdentificadorAnteEntidadPagadora,
  roaMarcaExpulsion,
  roaMotivoDesafiliacion,
  roaMotivoFiscalizacion,
  roaOportunidadPago,
  roaPagadorAportes,
  roaPagadorPension,
  roaPorcentajePagoAportes,
  roaReferenciaAporteReingreso,
  roaReferenciaSolicitudReingreso,
  roaSucursalEmpleador,
  roaSustitucionPatronal,
  roaTipoContrato,
  roaTipoSalario,
  roaValorSalarioMesadaIngresos) WITH (STATISTICS_NORECOMPUTE = OFF, DROP_EXISTING = OFF, ONLINE = OFF);

--changeset dsuesca:10
--comment: 
ALTER TABLE dbo.Afiliado ADD  CONSTRAINT UK_Afiliado_afiPersona UNIQUE NONCLUSTERED 
(
  afiPersona ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF);


--changeset dsuesca:11
--comment: 
CREATE TABLE dbo.ConvenioPago (
  copId bigint NOT NULL,
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
  CONSTRAINT PK_ConvenioPago_copId PRIMARY KEY (copId)  
) 


--changeset dsuesca:12
--comment: 
CREATE TABLE dbo.DetalleSolicitudGestionCobro (
  dsgId bigint NOT NULL,
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
  CONSTRAINT PK_DetalleSolicitudGestionCobro_dsgId PRIMARY KEY (dsgId),
 );


--changeset dsuesca:13
--comment: 
CREATE TABLE dbo.BitacoraCartera (
  bcaId bigint NOT NULL,
  bcaFecha date NULL,
  bcaActividad varchar(50) NULL,
  bcaMedio varchar(20) NULL,
  bcaResultado varchar(40) NULL,
  bcaUsuario varchar(255) NULL,
  bcaPersona bigint NOT NULL,
  bcaTipoSolicitante varchar(20) NULL,
  bcaNumeroOperacion varchar(12) NULL,
  CONSTRAINT PK_BitacoraCartera_bcaId PRIMARY KEY (bcaId)
)

--changeset dsuesca:14
--comment: 
ALTER TABLE MedioTarjeta ALTER COLUMN mtrSolicitudTarjeta VARCHAR(30);
ALTER TABLE aud.MedioTarjeta_aud ALTER COLUMN mtrSolicitudTarjeta VARCHAR(30);

--changeset dsuesca:15
--comment:
IF NOT EXISTS (select 1 from sys.types where name = 'fechasCorteType')
CREATE TYPE fechasCorteType AS TABLE (fechaCorte DATE);

--changeset dsuesca:16
--comment: 
CREATE TABLE dbo.UbicacionEmpresa (
  ubeId bigint NOT NULL,
  ubeEmpresa bigint NULL,
  ubeUbicacion bigint NULL,
  ubeTipoUbicacion varchar(30) NULL,
  CONSTRAINT PK_UbicacionEmpresa_ubeId PRIMARY KEY (ubeId)
);

CREATE TABLE dbo.DevolucionAporteDetalle (
  dadId bigint NOT NULL,
  dadIncluyeAporteObligatorio bit NULL,
  dadIncluyeMoraCotizante bit NULL,
  dadComentarioHistorico varchar(255) NULL,
  dadComentarioNovedades varchar(255) NULL,
  dadComentarioAportes varchar(255) NULL,
  dadUsuario varchar(255) NULL,
  dadFechaGestion datetime NULL,
  dadDevolucionAporte bigint NOT NULL,
  dadMovimientoAporte bigint NULL,
  CONSTRAINT PK_DevolucionAporteDetalle_dadId PRIMARY KEY (dadId)
 );

CREATE TABLE dbo.DevolucionAporte (
  dapId bigint NOT NULL,
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
  dapOtraCaja varchar(255) NULL,
  dapOtroMotivo varchar(255) NULL,
  CONSTRAINT PK_DevolucionAporte_dapId PRIMARY KEY (dapId)
 );

CREATE TABLE dbo.SolicitudDevolucionAporte (
  sdaId bigint NOT NULL,
  sdaEstadoSolicitud varchar(25) NULL,
  sdaTipoSolicitante varchar(13) NULL,
  sdaPersona bigint NULL,
  sdaObservacionAnalista varchar(255) NULL,
  sdaObservacionSupervisor varchar(255) NULL,
  sdaResultadoAnalista varchar(10) NULL,
  sdaResultadoSupervisor varchar(10) NULL,
  sdaDevolucionAporte bigint NULL,
  sdaSolicitudGlobal bigint NULL,
  CONSTRAINT PK_SolicitudDevolucionAporte_sdaId PRIMARY KEY (sdaId)
 );

CREATE TABLE dbo.SolicitudLiquidacionSubsidio (
  slsId bigint NOT NULL,
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
  slsFechaEvaluacionPrimerNivel datetime NULL,
  slsFechaEvaluacionSegundoNivel datetime NULL,
  slsCodigoReclamo varchar(50) NULL,
  slsComentarioReclamo varchar(250) NULL,
  slsFechaDispersion datetime NULL,
  slsConsideracionAporteDesembolso bit NULL,
  slsTipoDesembolso varchar(40) NULL,
  CONSTRAINT PK_SolicitudLiquidacionSubsidio_slsId PRIMARY KEY (slsId)
);

CREATE TABLE dbo.CuentaAdministradorSubsidio (
  casId bigint NOT NULL,
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
  casSolicitudLiquidacionSubsidio bigint NULL,
  casCondicionPersonaAdmin bigint NULL,
  CONSTRAINT PK_CuentaAdministradorSubsidio_casId PRIMARY KEY (casId)
 );

CREATE TABLE dbo.ProyectoSolucionVivienda (
  psvId bigint NOT NULL,
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
  psvRegistrado bit NULL,
  psvDisponeCuentaBancaria bit NULL,
  psvComparteCuentaOferente bit NULL,
  CONSTRAINT PK_ProyectoSolucionVivienda_psvId PRIMARY KEY (psvId)
  );

CREATE TABLE dbo.Oferente (
  ofeId bigint NOT NULL,
  ofePersona bigint NOT NULL,
  ofeEmpresa bigint NULL,
  ofeEstado varchar(30) NULL,
  ofeCuentaBancaria bit NULL,
  ofeBanco bigint NULL,
  ofeTipoCuenta varchar(30) NULL,
  ofeNumeroCuenta varchar(30) NULL,
  ofeTipoIdentificacionTitular varchar(20) NULL,
  ofeNumeroIdentificacionTitular varchar(16) NULL,
  ofeDigitoVerificacionTitular smallint NULL,
  ofeNombreTitularCuenta varchar(200) NULL,
  CONSTRAINT PK_Oferente_ofeId PRIMARY KEY (ofeId)
);

CREATE TABLE dbo.AhorroPrevio (
  ahpId bigint NOT NULL,
  ahpNombreAhorro varchar(65) NULL,
  ahpEntidad varchar(50) NULL,
  ahpFechaInicial date NULL,
  ahpValor numeric(19,5) NULL,
  ahpFechaInmovilizacion date NULL,
  ahpFechaAdquisicion date NULL,
  ahpPostulacionFOVIS bigint NOT NULL,
  ahpAhorroMovilizado bit NULL,
  CONSTRAINT PK_AhorroPrevio_ahpId PRIMARY KEY (ahpId)  
);

CREATE TABLE dbo.RecursoComplementario (
  recId bigint NOT NULL,
  recNombre varchar(26) NULL,
  recEntidad varchar(50) NULL,
  recFecha date NULL,
  recOtroRecurso varchar(255) NULL,
  recValor numeric(19,5) NULL,
  recPostulacionFOVIS bigint NOT NULL,
  CONSTRAINT PK_RecursoComplementario_recId PRIMARY KEY (recId) 
);

CREATE TABLE dbo.ActaAsignacionFovis (
  aafId bigint NOT NULL,
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
  CONSTRAINT PK_ActaAsignacionFovis_aafId PRIMARY KEY (aafId)  
);

--changeset dsuesca:17
--comment: 
ALTER TABLE IntentoAfiliacion ADD iafTipoIdentificacion VARCHAR(20) NULL;
ALTER TABLE IntentoAfiliacion ADD iafNumeroIdentificacion VARCHAR(16) NULL;
ALTER TABLE aud.IntentoAfiliacion_aud ADD iafTipoIdentificacion VARCHAR(20) NULL;
ALTER TABLE aud.IntentoAfiliacion_aud ADD iafNumeroIdentificacion VARCHAR(16) NULL;

--changeset dsuesca:18
--comment: 
ALTER TABLE Parametro ADD prmTipoDato VARCHAR(17);
ALTER TABLE Constante ADD cnsTipoDato VARCHAR(17);
ALTER TABLE aud.Parametro_aud ADD prmTipoDato VARCHAR(17);
ALTER TABLE aud.Constante_aud ADD cnsTipoDato VARCHAR(17);

--changeset dsuesca:19
--comment: 
ALTER TABLE CuentaAdministradorSubsidio ADD casEmpleador bigint;
ALTER TABLE CuentaAdministradorSubsidio ADD casAfiliadoPrincipal bigint;
ALTER TABLE CuentaAdministradorSubsidio ADD casBeneficiarioDetalle bigint;
ALTER TABLE CuentaAdministradorSubsidio ADD casGrupoFamiliar bigint;
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ADD casEmpleador bigint;
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ADD casAfiliadoPrincipal bigint;
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ADD casBeneficiarioDetalle bigint;
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ADD casGrupoFamiliar bigint;

--changeset clmarin:01
--comment:
ALTER TABLE ActividadCartera ADD acrObservaciones varchar(400) NULL;
ALTER TABLE aud.ActividadCartera_aud ADD acrObservaciones varchar(400) NULL;

--changeset mamonroy:02
--comment:
ALTER TABLE BeneficiarioDetalle ADD bedTipoUnionConyugal VARCHAR(11);
ALTER TABLE aud.BeneficiarioDetalle_aud ADD bedTipoUnionConyugal VARCHAR(11);

--changeset mamonroy:01
--comment:
ALTER TABLE PostulacionFovis ADD pofMatriculaInmobiliariaInmueble VARCHAR(50);
ALTER TABLE PostulacionFovis ADD pofFechaRegistroEscritura DATE;
ALTER TABLE PostulacionFovis ADD pofLoteUrbanizado BIT;
ALTER TABLE PostulacionFovis ADD pofPoseedorOcupanteVivienda VARCHAR(50);
ALTER TABLE PostulacionFovis ADD pofNumeroEscritura VARCHAR(20);
ALTER TABLE PostulacionFovis ADD pofFechaEscritura DATE;
ALTER TABLE PostulacionFovis ADD pofUbicacionIgualProyecto BIT;
ALTER TABLE PostulacionFovis ADD pofUbicacionVivienda BIGINT;
ALTER TABLE PostulacionFovis ADD CONSTRAINT FK_PostulacionFovis_pofUbicacionVivienda FOREIGN KEY (pofUbicacionVivienda) REFERENCES Ubicacion(ubiId);

ALTER TABLE aud.PostulacionFovis_aud ADD pofMatriculaInmobiliariaInmueble VARCHAR(50);
ALTER TABLE aud.PostulacionFovis_aud ADD pofFechaRegistroEscritura DATE;
ALTER TABLE aud.PostulacionFovis_aud ADD pofLoteUrbanizado BIT;
ALTER TABLE aud.PostulacionFovis_aud ADD pofPoseedorOcupanteVivienda VARCHAR(50);
ALTER TABLE aud.PostulacionFovis_aud ADD pofNumeroEscritura VARCHAR(20);
ALTER TABLE aud.PostulacionFovis_aud ADD pofFechaEscritura DATE;
ALTER TABLE aud.PostulacionFovis_aud ADD pofUbicacionIgualProyecto BIT;
ALTER TABLE aud.PostulacionFovis_aud ADD pofUbicacionVivienda BIGINT;

--MIGRACION DE DATOS ENTRE TABLAS
UPDATE pof
SET pof.pofMatriculaInmobiliariaInmueble = psv.psvMatriculaInmobiliariaInmueble
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofFechaRegistroEscritura = psv.psvFechaRegistroEscritura
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofLoteUrbanizado = psv.psvLoteUrbanizado
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofPoseedorOcupanteVivienda = psv.psvPoseedorOcupanteVivienda
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofNumeroEscritura = psv.psvNumeroEscritura
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofFechaEscritura = psv.psvFechaEscritura
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofUbicacionIgualProyecto = psv.psvUbicacionIgualProyecto
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofUbicacionVivienda = psv.psvUbicacionVivienda
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;


--ELIMINACION COLUMNAS PROYECTO SOLUCION VIVIENDA
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvMatriculaInmobiliariaInmueble;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvFechaRegistroEscritura;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvLoteUrbanizado;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvPoseedorOcupanteVivienda;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvNumeroEscritura;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvFechaEscritura;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvUbicacionIgualProyecto;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvUbicacionVivienda;

ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvMatriculaInmobiliariaInmueble;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvFechaRegistroEscritura;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvLoteUrbanizado;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvPoseedorOcupanteVivienda;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvNumeroEscritura;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvFechaEscritura;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvUbicacionIgualProyecto;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvUbicacionVivienda;

--changeset mamonroy:02f
--comment:
UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.matriculaInmobiliariaInmueble',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.fechaRegistroEscritura',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.loteUrbanizado',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.loteUrbanizado'));


UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.poseedorOcupanteVivienda',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.numeroEscritura',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.numeroEscritura'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.fechaEscritura',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaEscritura'));

--UBICACION

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.autorizacionEnvioEmail',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.autorizacionEnvioEmail'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.codigoPostal',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.codigoPostal'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.descripcionIndicacion',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.descripcionIndicacion'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.direccionFisica',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.direccionFisica'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.email',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.email'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.idMunicipio',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idMunicipio'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.idUbicacion',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idUbicacion'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.indicativoTelFijo',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.indicativoTelFijo'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.telefonoCelular',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoCelular'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.telefonoFijo',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoFijo'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionViviendaMismaProyecto',
  JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto'));

--DELETE

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble',NULL);

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura',NULL);

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.loteUrbanizado',NULL)

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda',NULL)

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.numeroEscritura',NULL)

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaEscritura',NULL)

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto',NULL)

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda',NULL)

--changeset mamonroy:03
--comment:
UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.matriculaInmobiliariaInmueble',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.matriculaInmobiliariaInmueble'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.fechaRegistroEscritura',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.fechaRegistroEscritura'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;


UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.loteUrbanizado',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.loteUrbanizado'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.poseedorOcupanteVivienda',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.poseedorOcupanteVivienda'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.numeroEscritura',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.numeroEscritura'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.fechaEscritura',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.fechaEscritura'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--UBICACION

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.autorizacionEnvioEmail',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.autorizacionEnvioEmail'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.codigoPostal',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.codigoPostal'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.descripcionIndicacion',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.descripcionIndicacion'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.direccionFisica',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.direccionFisica'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.email',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.email'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.idMunicipio',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.idMunicipio'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.idUbicacion',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.idUbicacion'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.indicativoTelFijo',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.indicativoTelFijo'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.telefonoCelular',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.telefonoCelular'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.telefonoFijo',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.telefonoFijo'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionViviendaMismaProyecto',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionViviendaMismaProyecto'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--DELETE

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.matriculaInmobiliariaInmueble',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.fechaRegistroEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.loteUrbanizado',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.poseedorOcupanteVivienda',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.numeroEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.fechaEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionViviendaMismaProyecto',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--changeset mamonroy:04
--comment:
UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.matriculaInmobiliariaInmueble',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.fechaRegistroEscritura',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;


UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.loteUrbanizado',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.loteUrbanizado'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.poseedorOcupanteVivienda',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.numeroEscritura',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.numeroEscritura'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.fechaEscritura',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.fechaEscritura'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--UBICACION

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.autorizacionEnvioEmail',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.autorizacionEnvioEmail'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.codigoPostal',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.codigoPostal'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.descripcionIndicacion',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.descripcionIndicacion'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.direccionFisica',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.direccionFisica'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.email',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.email'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.idMunicipio',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idMunicipio'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.idUbicacion',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idUbicacion'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.indicativoTelFijo',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.indicativoTelFijo'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.telefonoCelular',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoCelular'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.telefonoFijo',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoFijo'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionViviendaMismaProyecto',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--DELETE

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;


UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.loteUrbanizado',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.numeroEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.fechaEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--changeset mamonroy:05
--comment:
UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.matriculaInmobiliariaInmueble',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.fechaRegistroEscritura',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.loteUrbanizado',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.loteUrbanizado'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.poseedorOcupanteVivienda',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.numeroEscritura',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.numeroEscritura'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.fechaEscritura',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.fechaEscritura'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--UBICACION

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.autorizacionEnvioEmail',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.autorizacionEnvioEmail'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.codigoPostal',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.codigoPostal'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.descripcionIndicacion',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.descripcionIndicacion'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.direccionFisica',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.direccionFisica'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.email',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.email'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.idMunicipio',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idMunicipio'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.idUbicacion',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idUbicacion'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.indicativoTelFijo',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.indicativoTelFijo'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.telefonoCelular',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoCelular'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.telefonoFijo',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoFijo'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionViviendaMismaProyecto',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--DELETE

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.loteUrbanizado',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.numeroEscritura',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.fechaEscritura',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--changeset mamonroy:06
--comment:
UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.matriculaInmobiliariaInmueble',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.fechaRegistroEscritura',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.loteUrbanizado',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.loteUrbanizado'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.poseedorOcupanteVivienda',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.numeroEscritura',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.numeroEscritura'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.fechaEscritura',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaEscritura'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

--UBICACION

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.autorizacionEnvioEmail',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.autorizacionEnvioEmail'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.codigoPostal',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.codigoPostal'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.descripcionIndicacion',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.descripcionIndicacion'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.direccionFisica',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.direccionFisica'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.email',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.email'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.idMunicipio',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idMunicipio'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.idUbicacion',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idUbicacion'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.indicativoTelFijo',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.indicativoTelFijo'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.telefonoCelular',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoCelular'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.telefonoFijo',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoFijo'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionViviendaMismaProyecto',
  JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

--DELETE

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;


UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.loteUrbanizado',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.numeroEscritura',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaEscritura',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

--changeset mamonroy:07
--comment:
UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.matriculaInmobiliariaInmueble',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.fechaRegistroEscritura',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.loteUrbanizado',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.loteUrbanizado'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.poseedorOcupanteVivienda',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.numeroEscritura',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.numeroEscritura'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.fechaEscritura',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaEscritura'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

--UBICACION

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.autorizacionEnvioEmail',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.autorizacionEnvioEmail'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.codigoPostal',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.codigoPostal'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.descripcionIndicacion',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.descripcionIndicacion'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.direccionFisica',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.direccionFisica'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.email',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.email'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.idMunicipio',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idMunicipio'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.idUbicacion',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idUbicacion'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.indicativoTelFijo',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.indicativoTelFijo'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.telefonoCelular',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoCelular'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.telefonoFijo',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoFijo'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionViviendaMismaProyecto',
  JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

--DELETE

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.loteUrbanizado',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.numeroEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

--

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE ParametrizacionNovedad
SET novPuntoResolucion = 'BACK'
WHERE novTipoTransaccion IN ('ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL','ACTUALIZACION_TIPO_UNION_CONYUGE_WEB');

--changeset mamonroy:08
--comment:
ALTER TABLE BeneficioEmpleador ADD bemPerteneceDepartamento bit;
ALTER TABLE aud.BeneficioEmpleador_aud ADD bemPerteneceDepartamento bit;

--changeset jocorrea:09
--comment:
ALTER TABLE Solicitud ALTER COLUMN solNumeroRadicacion varchar(20);
ALTER TABLE aud.Solicitud_aud ALTER COLUMN solNumeroRadicacion varchar(20);

--changeset jocorrea:10
--comment:
ALTER TABLE PostulacionFOVIS ALTER COLUMN pofPuntaje numeric(8,2);
ALTER TABLE aud.PostulacionFOVIS_aud ALTER COLUMN pofPuntaje numeric(8,2);