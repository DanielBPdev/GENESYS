--liquibase formatted sql

--changeset arocha:01
--comment:Creacion modelo de reportes

--Rollback
/*
DROP TABLE FactGestionCarteraV2
DROP TABLE FactCondicionSolicitanteCarteraV2
DROP TABLE DimNaturalezaJuridicaV2
DROP TABLE DimEstadoSolicitanteV2
DROP TABLE DimTamanioEmpleadorV2
DROP TABLE DimClasificacionV2
DROP TABLE DimTipoSolicitanteV2
*/

CREATE TABLE DimTipoSolicitanteV2

(
	dtaId TINYINT NOT NULL,
	dtaDescripcion VARCHAR(20) NOT NULL, 
	CONSTRAINT PK_DimTipoSolicitanteV2_dtaId PRIMARY KEY CLUSTERED 
	(
		dtaId ASC
	)
);

INSERT INTO dbo.DimTipoSolicitanteV2 VALUES (1,'Empleador'),(2,'Independiente'),(3,'Pensionado');

CREATE TABLE DimTamanioEmpleadorV2
(
	dteId TINYINT NOT NULL,
	dteDescripcion VARCHAR(50) NOT NULL,
	CONSTRAINT PK_DimTamanioEmpleadorV2_dteId PRIMARY KEY CLUSTERED 
	(
		dteId ASC
	)
);
INSERT INTO dbo.DimTamanioEmpleadorV2 VALUES (1,'Microempresa (0 a 10 trabajadores)'),(2,'Empresa pequeña (11 a 50 trabajadores)'),(3,'Empresa mediana (51 a 200 trabajadores)'),(4,'Empresa grande (Más de 200 trabajadores)');


CREATE TABLE DimClasificacionV2

(
	dclId TINYINT NOT NULL,
	dclDescripcion VARCHAR(50) NOT NULL,
	CONSTRAINT PK_DimClasificacionV2_dclId PRIMARY KEY CLUSTERED 
	(
		dclId ASC
	)
);

INSERT INTO dbo.DimClasificacionV2 VALUES (1,'Cooperativa o pre-cooperativa de trabajo asociado'),(2,'Empleador de servicio doméstico'),(3,'Entidad sin ánimo de lucro'),(4,'Organización religiosa o parroquia'),(5,'Persona jurídica'),(6,'Persona natural'),(7,'Propiedad horizontal');

CREATE TABLE DimEstadoSolicitanteV2
(
	deaId TINYINT NOT NULL,
	deaDescripcion VARCHAR(50) NOT NULL,
	CONSTRAINT PK_DimEstadoSolicitanteV2_deaId PRIMARY KEY CLUSTERED 
	(
		deaId ASC
	)
);
INSERT INTO dbo.DimEstadoSolicitanteV2 VALUES (1,'Activo'),(2,'Inactivo'),(3,'No Formalizado retirado con aportes'),(4,'No formalizado con información'),(5,'No formalizado sin afiliación con aportes');

CREATE TABLE DimNaturalezaJuridicaV2
(
	dinId TINYINT NOT NULL,
	dinDescripcion VARCHAR(100) NOT NULL,
	CONSTRAINT PK_DimNaturalezaJuridicaV2_dinId PRIMARY KEY CLUSTERED 
	(
		dinId ASC
	)
);
INSERT INTO dbo.DimNaturalezaJuridicaV2 VALUES (1,'Pública'),(2,'Privada'),(3,'Mixta'),(4,'Organismos multilaterales'),(5,'Entidades de derecho público no sometidas a la legislación colombiana');

CREATE TABLE FactCondicionSolicitanteCarteraV2
(
	fcaId BIGINT NOT NULL IDENTITY,
	fcaDimPeriodo INT NOT NULL,
	fcaPersona BIGINT NOT NULL,
	fcaDimTipoSolicitante TINYINT NOT NULL,	
	fcaDimEstadoSolicitante TINYINT,
	fcaDimTamanioEmpleador TINYINT,
	fcaDimNaturalezaJuridica TINYINT,
	fcaDimClasificacion TINYINT,
	fcaFechaAfiliacion DATETIME,
	fcaFechaDesafiliacion DATETIME,
	fcaMoroso BIT,
	fcaMorosoPeriodoAnterior BIT,
	fcaMontoRecaudoAportes NUMERIC(19,5),
	CONSTRAINT PK_FactCondicionSolicitanteCarteraV2_fcaId PRIMARY KEY CLUSTERED 
	(
		fcaId ASC
	)
)

ALTER TABLE dbo.FactCondicionSolicitanteCarteraV2 ADD CONSTRAINT FK_FactCondicionSolicitanteCarteraV2_fcaDimPeriodo FOREIGN KEY (fcaDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactCondicionSolicitanteCarteraV2 ADD CONSTRAINT FK_FactCondicionSolicitanteCarteraV2_fcaDimTamanioEmpleador FOREIGN KEY (fcaDimTamanioEmpleador) REFERENCES dbo.DimTamanioEmpleadorV2 (dteId);
ALTER TABLE dbo.FactCondicionSolicitanteCarteraV2 ADD CONSTRAINT FK_FactCondicionSolicitanteCarteraV2_fcaDimNaturalezaJuridica FOREIGN KEY (fcaDimNaturalezaJuridica) REFERENCES dbo.DimNaturalezaJuridicaV2 (dinId);
ALTER TABLE dbo.FactCondicionSolicitanteCarteraV2 ADD CONSTRAINT FK_FactCondicionSolicitanteCarteraV2_fcaDimEstadoSolicitante FOREIGN KEY (fcaDimEstadoSolicitante) REFERENCES dbo.DimEstadoSolicitanteV2 (deaId);
ALTER TABLE dbo.FactCondicionSolicitanteCarteraV2 ADD CONSTRAINT FK_FactCondicionSolicitanteCarteraV2_fcaDimClasificacion FOREIGN KEY (fcaDimClasificacion) REFERENCES dbo.DimClasificacionV2 (dclId);
ALTER TABLE dbo.FactCondicionSolicitanteCarteraV2 ADD CONSTRAINT FK_FactCondicionSolicitanteCarteraV2_fcaPersona FOREIGN KEY (fcaPersona) REFERENCES dbo.Persona (perId);

CREATE TABLE FactGestionCarteraV2
(
	fgcId BIGINT NOT NULL IDENTITY,
	fgcCartera BIGINT NOT NULL,
	fgcPersona BIGINT NOT NULL,
	fgcDimPeriodo INT NOT NULL,
	fgcDimPeriodoRetroactivo INT NOT NULL,
	fgcDimTipoSolicitante TINYINT NOT NULL,
	fgcValorMontoMora NUMERIC(19,5) NULL,
	fgcFechaEstadoMoroso DATETIME NULL,
	fgcFechaAlDia DATETIME NULL,
	fgcMoroso BIT NULL,
	CONSTRAINT PK_FactGestionCarteraV2_fgcId PRIMARY KEY CLUSTERED 
	(
		fgcId ASC
	)
);

ALTER TABLE dbo.FactGestionCarteraV2 ADD CONSTRAINT FK_FactGestionCarteraV2_fgcDimPeriodo FOREIGN KEY (fgcDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactGestionCarteraV2 ADD CONSTRAINT FK_FactGestionCarteraV2_fgcDimPeriodoRetroactivo FOREIGN KEY (fgcDimPeriodoRetroactivo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactGestionCarteraV2 ADD CONSTRAINT FK_FactGestionCarteraV2_fgcDimTipoSolicitante FOREIGN KEY (fgcDimTipoSolicitante) REFERENCES dbo.DimTipoSolicitanteV2 (dtaId);
ALTER TABLE dbo.FactGestionCarteraV2 ADD CONSTRAINT FK_FactGestionCarteraV2_fgcPersona FOREIGN KEY (fgcPersona) REFERENCES dbo.Persona (perId);
ALTER TABLE dbo.FactGestionCarteraV2 ADD CONSTRAINT FK_FactGestionCarteraV2_fgcCartera FOREIGN KEY (fgcCartera) REFERENCES dbo.Cartera (carId);

--changeset arocha:02
--comment:Creacion modelo de reportes de FOVIS

--Rollback
/*
DROP TABLE FactNovedadFOVISV2
DROP TABLE FactLegalizacionDesembolsoFOVISV2
DROP TABLE FactAsignacionFOVISV2
DROP TABLE FactPostulacionFOVISV2
DROP TABLE DimCicloAsignacionV2
DROP TABLE DimCanalV2
DROP TABLE DimModalidadV2
DROP TABLE DimEstadoProcesamientoV2
DROP TABLE DimMetodoEnvioDocumentacionV2
DROP TABLE DimTipoNovedadFOVISV2
*/

CREATE TABLE DimCicloAsignacionV2
(
	dciId BIGINT NOT NULL,
	dciDescripcion VARCHAR(50) NOT NULL, 
	dciAnio INT NOT NULL,
	CONSTRAINT PK_DimCicloAsignacionV2_dciId PRIMARY KEY CLUSTERED 
	(
		dciId ASC
	)
);

CREATE TABLE DimCanalV2
(
	dicId TINYINT NOT NULL,
	dicDescripcion VARCHAR(20) NOT NULL, 
	dicIncluyePILA BIT NOT NULL,
	CONSTRAINT PK_DimCanalV2_dicId PRIMARY KEY CLUSTERED 
	(
		dicId ASC
	)
);
INSERT INTO dbo.DimCanalV2 VALUES (1,'Presencial',0),(2,'Web',0),(3,'PILA',1);


CREATE TABLE DimModalidadV2
(
	dmoId TINYINT NOT NULL,
	dmoDescripcion VARCHAR(40) NOT NULL
	CONSTRAINT PK_DimCanalV2_dmoId PRIMARY KEY CLUSTERED 
	(
		dmoId ASC
	)
);

INSERT INTO dbo.DimModalidadV2 VALUES (1,'Adquisición de vivienda nueva rural'),(2,'Adquisición de vivienda nueva urbana'),(3,'Adquisición de vivienda usada rural'),
(4,'Adquisición de vivienda usada urbana'),(5,'Construcción en sitio propio rural'),(6,'Construcción en sitio propio urbano'),(7,'Mejoramiento de vivienda urbana'),
(8,'Mejoramiento de vivienda rural'),(9,'Mejoramiento de vivienda saludable');


CREATE TABLE DimEstadoProcesamientoV2 
(
	dieId TINYINT NOT NULL,
	dieDescripcion VARCHAR(30) NOT NULL
	CONSTRAINT PK_DimEstadoProcesamientoV2_dieId PRIMARY KEY CLUSTERED 
	(
		dieId ASC
	)
);

INSERT INTO dbo.DimEstadoProcesamientoV2 VALUES (1,'Solicitudes aprobadas'),(2,'Solicitudes rechazadas'),(3,'Solicitudes en proceso');

CREATE TABLE FactPostulacionFOVISV2
(
	fpfId BIGINT NOT NULL IDENTITY,
	fpfPostulacionFOVIS BIGINT NOT NULL,
	fpfJefeHogar BIGINT NOT NULL,
	fpfDimPeriodo INT NOT NULL,
	fpfDimCicloAsignacion BIGINT NULL,
	fpfDimSede INT NULL,
	fpfDimCanal TINYINT NOT NULL,
	fpfDimModalidad TINYINT NOT NULL,
	fpfDimEstadoProcesamiento TINYINT NULL,
	fpfAccionSolicitudHabil BIT NULL,
	fpfAccionSolicitudRechazada BIT NULL,
	fpfAccionSolicitudEnProceso BIT NULL,
	fpfAccionSolicitudRadicada BIT NULL,
	fpfAccionSolicitudCerrada BIT NULL,
	fpfAccionRenuncioSubsidioAsignado BIT NULL,
	fpfAccionSubsidioReembolsado BIT NULL,
	fpfAccionRestituido BIT NULL,
	fpfFechaRadicacionSolicitud DATETIME NULL,	
	fpfFechaCierreSolicitud DATETIME NULL,
	fpfValorAsignadoSFV NUMERIC (18,0) NULL,
	fpfValorAjusteIPCSFV NUMERIC (18,0) NULL,
	CONSTRAINT PK_FactPostulacionFOVISV2_fpfId PRIMARY KEY CLUSTERED 
	(
		fpfId ASC
	)
);

ALTER TABLE dbo.FactPostulacionFOVISV2 ADD CONSTRAINT FK_FactPostulacionFOVISV2_fpfDimPeriodo FOREIGN KEY (fpfDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactPostulacionFOVISV2 ADD CONSTRAINT FK_FactPostulacionFOVISV2_fpfDimCicloAsignacion FOREIGN KEY (fpfDimCicloAsignacion) REFERENCES dbo.DimCicloAsignacionV2 (dciId);
ALTER TABLE dbo.FactPostulacionFOVISV2 ADD CONSTRAINT FK_FactPostulacionFOVISV2_fpfDimSede FOREIGN KEY (fpfDimSede) REFERENCES dbo.DimSede (disId);
ALTER TABLE dbo.FactPostulacionFOVISV2 ADD CONSTRAINT FK_FactPostulacionFOVISV2_fpfDimCanal FOREIGN KEY (fpfDimCanal) REFERENCES dbo.DimCanalV2 (dicId);
ALTER TABLE dbo.FactPostulacionFOVISV2 ADD CONSTRAINT FK_FactPostulacionFOVISV2_fpfDimEstadoProcesamiento FOREIGN KEY (fpfDimEstadoProcesamiento) REFERENCES dbo.DimEstadoProcesamientoV2 (dieId);
ALTER TABLE dbo.FactPostulacionFOVISV2 ADD CONSTRAINT FK_FactPostulacionFOVISV2_fpfPostulacionFOVIS FOREIGN KEY (fpfPostulacionFOVIS) REFERENCES dbo.PostulacionFOVIS (pofId);
ALTER TABLE dbo.FactPostulacionFOVISV2 ADD CONSTRAINT FK_FactPostulacionFOVISV2_fpfJefeHogar FOREIGN KEY (fpfJefeHogar) REFERENCES dbo.JefeHogar (jehId);


CREATE TABLE FactAsignacionFOVISV2
(
	fafId BIGINT NOT NULL IDENTITY,
	fafSolicitudAsignacion BIGINT NOT NULL,
	fafJefeHogar BIGINT NOT NULL,
	fafDimPeriodo INT NOT NULL,
	fafDimCicloAsignacion BIGINT NOT NULL,
	fafDimSede INT NULL,
	fafDimCanal TINYINT NOT NULL,
	fafDimModalidad TINYINT NOT NULL,
	fafAccionAsignadoSinProrroga BIT NULL,
	fafValorAsignadoSFV NUMERIC(18,0) NULL
	CONSTRAINT PK_FactAsignacionFOVISV2_fafId PRIMARY KEY CLUSTERED 
	(
		fafId ASC
	)
);

ALTER TABLE dbo.FactAsignacionFOVISV2 ADD CONSTRAINT FK_FactAsignacionFOVISV2_fafDimPeriodo FOREIGN KEY (fafDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactAsignacionFOVISV2 ADD CONSTRAINT FK_FactAsignacionFOVISV2_fafDimCicloAsignacion FOREIGN KEY (fafDimCicloAsignacion) REFERENCES dbo.DimCicloAsignacionV2 (dciId);
ALTER TABLE dbo.FactAsignacionFOVISV2 ADD CONSTRAINT FK_FactAsignacionFOVISV2_fafDimSede FOREIGN KEY (fafDimSede) REFERENCES dbo.DimSede (disId);
ALTER TABLE dbo.FactAsignacionFOVISV2 ADD CONSTRAINT FK_FactAsignacionFOVISV2_fafDimCanal FOREIGN KEY (fafDimCanal) REFERENCES dbo.DimCanalV2 (dicId);
ALTER TABLE dbo.FactAsignacionFOVISV2 ADD CONSTRAINT FK_FactAsignacionFOVISV2_fafSolicitudAsignacion FOREIGN KEY (fafSolicitudAsignacion) REFERENCES dbo.SolicitudAsignacion (safId);
ALTER TABLE dbo.FactAsignacionFOVISV2 ADD CONSTRAINT FK_FactAsignacionFOVISV2_fafJefeHogar FOREIGN KEY (fafJefeHogar) REFERENCES dbo.JefeHogar (jehId);


CREATE TABLE FactLegalizacionDesembolsoFOVISV2
(
	fldId BIGINT NOT NULL IDENTITY,
	fldSolicitudLegalizacionDesembolso BIGINT NOT NULL,
	fldJefeHogar BIGINT NOT NULL,
	fldDimPeriodo INT NOT NULL,
	fldDimCicloAsignacion BIGINT NOT NULL,
	fldDimSede INT NULL,
	fldDimCanal TINYINT NOT NULL,
	fldDimModalidad TINYINT NOT NULL,
	fldAccionSolicitudRadicada BIT NULL,
	fldAccionLegalizacionDesembolsoCerrado BIT NULL,
	fldAccionDesembolsoExitoso BIT NULL,
	fldAccionReintentoDesembolsoExitoso BIT NULL,
	fldFechaRadicacion DATETIME NULL,
	fldFechaLegalizacionDesembolsoCerrado DATETIME NULL,
	fldMontoDesembolsado NUMERIC(18,0) NULL
	CONSTRAINT PK_FactLegalizacionDesembolsoFOVISV2_fldId PRIMARY KEY CLUSTERED 
	(
		fldId ASC
	)
);

ALTER TABLE dbo.FactLegalizacionDesembolsoFOVISV2 ADD CONSTRAINT FK_FactLegalizacionDesembolsoFOVISV2_fldDimPeriodo FOREIGN KEY (fldDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactLegalizacionDesembolsoFOVISV2 ADD CONSTRAINT FK_FactLegalizacionDesembolsoFOVISV2_fldDimCicloAsignacion FOREIGN KEY (fldDimCicloAsignacion) REFERENCES dbo.DimCicloAsignacionV2 (dciId);
ALTER TABLE dbo.FactLegalizacionDesembolsoFOVISV2 ADD CONSTRAINT FK_FactLegalizacionDesembolsoFOVISV2_fldDimSede FOREIGN KEY (fldDimSede) REFERENCES dbo.DimSede (disId);
ALTER TABLE dbo.FactLegalizacionDesembolsoFOVISV2 ADD CONSTRAINT FK_FactLegalizacionDesembolsoFOVISV2_fldDimCanal FOREIGN KEY (fldDimCanal) REFERENCES dbo.DimCanalV2 (dicId);
ALTER TABLE dbo.FactLegalizacionDesembolsoFOVISV2 ADD CONSTRAINT FK_FactLegalizacionDesembolsoFOVISV2_fldSolicitudLegalizacionDesembolso FOREIGN KEY (fldSolicitudLegalizacionDesembolso) REFERENCES dbo.SolicitudLegalizacionDesembolso (sldId);
ALTER TABLE dbo.FactLegalizacionDesembolsoFOVISV2 ADD CONSTRAINT FK_FactLegalizacionDesembolsoFOVISV2_fldJefeHogar FOREIGN KEY (fldJefeHogar) REFERENCES dbo.JefeHogar (jehId);

CREATE TABLE DimTipoNovedadFOVISV2 
(
	dnfId TINYINT NOT NULL,
	dnfDescripcion VARCHAR(30) NOT NULL,
	dnfIncluyeAutomatica BIT NOT NULL,
	CONSTRAINT PK_DimTipoNovedadFOVISV2_dnfId PRIMARY KEY CLUSTERED 
	(
		dnfId ASC
	)
);

INSERT INTO dbo.DimTipoNovedadFOVISV2 VALUES (1,'Especial',0),(2,'Regular',0),(3,'Automática',1);

CREATE TABLE DimMetodoEnvioDocumentacionV2 
(
	dedId TINYINT NOT NULL,
	dedDescripcion VARCHAR(30) NOT NULL
	CONSTRAINT PK_DimMetodoEnvioDocumentacionV2_dedId PRIMARY KEY CLUSTERED 
	(
		dedId ASC
	)
);

INSERT INTO dbo.DimMetodoEnvioDocumentacionV2 VALUES (1,'Físico'),(2,'Electrónico');

CREATE TABLE FactNovedadFOVISV2
(
	fnfId BIGINT NOT NULL IDENTITY,
	fnfSolicitudNovedadFovis BIGINT NOT NULL,
	fnfJefeHogar BIGINT NOT NULL,
	fnfDimPeriodo INT NOT NULL,
	fnfDimCicloAsignacion BIGINT NULL,
	fnfDimSede INT NULL,
	fnfDimCanal TINYINT NOT NULL,
	fnfDimModalidad TINYINT NOT NULL,
	fnfDimTipoNovedadFOVIS TINYINT NOT NULL,
	fnfDimMetodoEnvioDocumentacion TINYINT NULL,
	fnfAccionSolicitudRadicada BIT NULL,
	fnfAccionSolicitudCerrada BIT NULL,
	fnfAccionSolicitudRechazada BIT NULL,
	fnfAccionSolicitudEnProceso BIT NULL,
	fnfFechaRadicacion DATETIME NULL,
	fnfFechaSolicitudCerrada DATETIME NULL,
	CONSTRAINT PK_FactNovedadFOVISV2_fnfId PRIMARY KEY CLUSTERED 
	(
		fnfId ASC
	)
);

ALTER TABLE dbo.FactNovedadFOVISV2 ADD CONSTRAINT FK_FactNovedadFOVISV2_fnfDimPeriodo FOREIGN KEY (fnfDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactNovedadFOVISV2 ADD CONSTRAINT FK_FactNovedadFOVISV2_fnfDimCicloAsignacion FOREIGN KEY (fnfDimCicloAsignacion) REFERENCES dbo.DimCicloAsignacionV2 (dciId);
ALTER TABLE dbo.FactNovedadFOVISV2 ADD CONSTRAINT FK_FactNovedadFOVISV2_fnfDimSede FOREIGN KEY (fnfDimSede) REFERENCES dbo.DimSede (disId);
ALTER TABLE dbo.FactNovedadFOVISV2 ADD CONSTRAINT FK_FactNovedadFOVISV2_fnfDimCanal FOREIGN KEY (fnfDimCanal) REFERENCES dbo.DimCanalV2 (dicId);

ALTER TABLE dbo.FactNovedadFOVISV2 ADD CONSTRAINT FK_FactNovedadFOVISV2_fnfDimTipoNovedadFOVIS FOREIGN KEY (fnfDimTipoNovedadFOVIS) REFERENCES dbo.DimTipoNovedadFOVISV2 (dnfId);
ALTER TABLE dbo.FactNovedadFOVISV2 ADD CONSTRAINT FK_FactNovedadFOVISV2_fnfDimMetodoEnvioDocumentacion FOREIGN KEY (fnfDimMetodoEnvioDocumentacion) REFERENCES dbo.DimMetodoEnvioDocumentacionV2 (dedId);

ALTER TABLE dbo.FactNovedadFOVISV2 ADD CONSTRAINT FK_FactNovedadFOVISV2_fnfSolicitudNovedadFovis FOREIGN KEY (fnfSolicitudNovedadFovis) REFERENCES dbo.SolicitudNovedadFovis (snfId);
ALTER TABLE dbo.FactNovedadFOVISV2 ADD CONSTRAINT FK_FactNovedadFOVISV2_fnfJefeHogar FOREIGN KEY (fnfJefeHogar) REFERENCES dbo.JefeHogar (jehId);

--changeset arocha:03
--comment:Creacion modelo de reportes de Afiliaciones

--Rollback
/*
DROP TABLE FactSolicitudNovedadPersonaV2
DROP TABLE FactSolicitudNovedadEmpleadorV2
DROP TABLE FactAfiliadoV2
DROP TABLE FactAfiliacionPersonaV2
DROP TABLE FactSolicitudAfiliacionPersonaV2
DROP TABLE FactSolicitudAfiliacionEmpleadorV2
DROP TABLE FactAfiliacionEmpleadorV2
DROP TABLE DimTipoTransaccionV2
DROP TABLE DimRangoTiempoRespuestaV2
DROP TABLE DimTipoAfiliadoV2
*/

CREATE TABLE DimTipoTransaccionV2 
(
	dttId TINYINT NOT NULL,
	dttDescripcion VARCHAR(30) NOT NULL
	CONSTRAINT PK_DimTipoTransaccionV2_dttId PRIMARY KEY CLUSTERED 
	(
		dttId ASC
	)
);

INSERT INTO dbo.DimTipoTransaccionV2 VALUES (1,'Nueva Afiliación'),(2,'Reintegro');

CREATE TABLE FactAfiliacionEmpleadorV2
(
	faeId BIGINT NOT NULL IDENTITY,
	faeEmpleador BIGINT NOT NULL,
	faeDimPeriodo INT NOT NULL,
	faeActivo BIT NOT NULL,
	faeActivoPeriodoAnterior BIT NOT NULL,
	faeFechaActivo DATETIME NULL,
	faeFechaInactivo DATETIME NULL,
	faeDimTipoTransaccion TINYINT NULL,
	faeDimSede INT NULL,
	faeDimCanal TINYINT NULL,
	CONSTRAINT PK_FactAfiliacionEmpleadorV2_faeId PRIMARY KEY CLUSTERED 
	(
		faeId ASC
	)
);

ALTER TABLE dbo.FactAfiliacionEmpleadorV2 ADD CONSTRAINT FK_FactAfiliacionEmpleadorV2_faeDimPeriodo FOREIGN KEY (faeDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactAfiliacionEmpleadorV2 ADD CONSTRAINT FK_FactAfiliacionEmpleadorV2_faeDimSede FOREIGN KEY (faeDimSede) REFERENCES dbo.DimSede (disId);
ALTER TABLE dbo.FactAfiliacionEmpleadorV2 ADD CONSTRAINT FK_FactAfiliacionEmpleadorV2_faeDimCanal FOREIGN KEY (faeDimCanal) REFERENCES dbo.DimCanalV2 (dicId);
ALTER TABLE dbo.FactAfiliacionEmpleadorV2 ADD CONSTRAINT FK_FactAfiliacionEmpleadorV2_faeDimTipoTransaccion FOREIGN KEY (faeDimTipoTransaccion) REFERENCES dbo.DimTipoTransaccionV2 (dttId);
ALTER TABLE dbo.FactAfiliacionEmpleadorV2 ADD CONSTRAINT FK_FactAfiliacionEmpleadorV2_faeEmpleador FOREIGN KEY (faeEmpleador) REFERENCES dbo.Empleador (empId);

CREATE TABLE DimRangoTiempoRespuestaV2 
(
	dtrId TINYINT NOT NULL,
	dtrDescripcion VARCHAR(30) NOT NULL
	CONSTRAINT PK_DimRangoTiempoRespuestaV2_dtrId PRIMARY KEY CLUSTERED 
	(
		dtrId ASC
	)
);

INSERT INTO dbo.DimRangoTiempoRespuestaV2 VALUES (1,'0 a 1 día'),(2,'1 a 2 días'), (3,'2 a 3 días'), (4,'3 días o más');

CREATE TABLE FactSolicitudAfiliacionEmpleadorV2
(
	fseId BIGINT NOT NULL IDENTITY,
	fseSolicitudAfiliacionEmpleador BIGINT NOT NULL,
	fseDimPeriodo INT NOT NULL,
	fseAccionPrimerComunicado BIT NULL,
	fseFechaRadicacion DATETIME NOT NULL,
	fseFechaPrimerComunicado DATETIME NULL,
	fseDimRangoTiempoRespuesta TINYINT NULL,	
	fseDimSede INT NULL,
	fseDimCanal TINYINT NULL,
	CONSTRAINT PK_FactSolicitudAfiliacionEmpleadorV2_fseId PRIMARY KEY CLUSTERED 
	(
		fseId ASC
	)
);

ALTER TABLE dbo.FactSolicitudAfiliacionEmpleadorV2 ADD CONSTRAINT FK_FactSolicitudAfiliacionEmpleadorV2_fseDimPeriodo FOREIGN KEY (fseDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactSolicitudAfiliacionEmpleadorV2 ADD CONSTRAINT FK_FactSolicitudAfiliacionEmpleadorV2_fseDimSede FOREIGN KEY (fseDimSede) REFERENCES dbo.DimSede (disId);
ALTER TABLE dbo.FactSolicitudAfiliacionEmpleadorV2 ADD CONSTRAINT FK_FactSolicitudAfiliacionEmpleadorV2_fseDimCanal FOREIGN KEY (fseDimCanal) REFERENCES dbo.DimCanalV2 (dicId);
ALTER TABLE dbo.FactSolicitudAfiliacionEmpleadorV2 ADD CONSTRAINT FK_FactSolicitudAfiliacionEmpleadorV2_fseSolicitudAfiliacionEmpleador FOREIGN KEY (fseSolicitudAfiliacionEmpleador) REFERENCES dbo.SolicitudAfiliaciEmpleador (saeId);


CREATE TABLE FactSolicitudAfiliacionPersonaV2
(
	fspId BIGINT NOT NULL IDENTITY,
	fspSolicitudAfiliacionPersona BIGINT NOT NULL,
	fspDimPeriodo INT NOT NULL,
	fspAccionSolicitudRadicada BIT NULL,
	fspAccionSolicitudAprobada BIT NULL,
	fspAccionSolicitudRechazada BIT NULL,
	fspAccionSolicitudCerrada BIT NULL,
	fspFechaRadicacion DATETIME NULL,
	fspFechaSolicitudCerrada DATETIME NULL,
	fspDiasProcesamientoAfiliacion SMALLINT NULL,
	fspDimSede INT NULL,
	fspDimCanal TINYINT NULL,
	fspDimTipoTransaccion TINYINT NULL,
	CONSTRAINT PK_FactSolicitudAfiliacionPersonaV2_fspId PRIMARY KEY CLUSTERED 
	(
		fspId ASC
	)
);

ALTER TABLE dbo.FactSolicitudAfiliacionPersonaV2 ADD CONSTRAINT FK_FactSolicitudAfiliacionPersonaV2_fspDimPeriodo FOREIGN KEY (fspDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactSolicitudAfiliacionPersonaV2 ADD CONSTRAINT FK_FactSolicitudAfiliacionPersonaV2_fspDimSede FOREIGN KEY (fspDimSede) REFERENCES dbo.DimSede (disId);
ALTER TABLE dbo.FactSolicitudAfiliacionPersonaV2 ADD CONSTRAINT FK_FactSolicitudAfiliacionPersonaV2_fspDimCanal FOREIGN KEY (fspDimCanal) REFERENCES dbo.DimCanalV2 (dicId);
ALTER TABLE dbo.FactSolicitudAfiliacionPersonaV2 ADD CONSTRAINT FK_FactSolicitudAfiliacionPersonaV2_fspDimTipoTransaccion FOREIGN KEY (fspDimTipoTransaccion) REFERENCES dbo.DimTipoTransaccionV2 (dttId);
ALTER TABLE dbo.FactSolicitudAfiliacionPersonaV2 ADD CONSTRAINT FK_FactSolicitudAfiliacionPersonaV2_fspSolicitudAfiliacionPersona FOREIGN KEY (fspSolicitudAfiliacionPersona) REFERENCES dbo.SolicitudAfiliacionPersona (sapId);

CREATE TABLE DimTipoAfiliadoV2 
(
	dtaId TINYINT NOT NULL,
	dtaDescripcion VARCHAR(30) NOT NULL
	CONSTRAINT PK_DimTipoAfiliadoV2_dtaId PRIMARY KEY CLUSTERED 
	(
		dtaId ASC
	)
);

INSERT INTO dbo.DimTipoAfiliadoV2 VALUES (1,'Dependiente'),(2,'Independiente'), (3,'Pensionado');

CREATE TABLE FactAfiliacionPersonaV2
(
	fapId BIGINT NOT NULL IDENTITY,
	fapAfiliado BIGINT NOT NULL,
	fapDimTipoAfiliado TINYINT NOT NULL,
	fapDimPeriodo INT NOT NULL,
	fapActivo BIT NULL,
	fapActivoPeriodoAnterior BIT NULL,
	fapDimSede INT NULL,
	fapDimCanal TINYINT NULL,
	fapDimTipoTransaccion TINYINT NULL,
	CONSTRAINT PK_FactAfiliacionPersonaV2_fapId PRIMARY KEY CLUSTERED 
	(
		fapId ASC
	)
);

ALTER TABLE dbo.FactAfiliacionPersonaV2 ADD CONSTRAINT FK_FactAfiliacionPersonaV2_fapDimPeriodo FOREIGN KEY (fapDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactAfiliacionPersonaV2 ADD CONSTRAINT FK_FactAfiliacionPersonaV2_fapDimSede FOREIGN KEY (fapDimSede) REFERENCES dbo.DimSede (disId);
ALTER TABLE dbo.FactAfiliacionPersonaV2 ADD CONSTRAINT FK_FactAfiliacionPersonaV2_fapDimCanal FOREIGN KEY (fapDimCanal) REFERENCES dbo.DimCanalV2 (dicId);
ALTER TABLE dbo.FactAfiliacionPersonaV2 ADD CONSTRAINT FK_FactAfiliacionPersonaV2_fapDimTipoAfiliado FOREIGN KEY (fapDimTipoAfiliado) REFERENCES dbo.DimTipoAfiliadoV2 (dtaId);
ALTER TABLE dbo.FactAfiliacionPersonaV2 ADD CONSTRAINT FK_FactAfiliacionPersonaV2_fapAfiliado FOREIGN KEY (fapAfiliado) REFERENCES dbo.Afiliado (afiId);

CREATE TABLE FactAfiliadoV2
(
	fafId BIGINT NOT NULL IDENTITY,
	fafAfiliado BIGINT NOT NULL,
	fafActivo BIT NOT NULL,
	fafDimPeriodo INT NOT NULL,
	fafCantidadPersonasACargo TINYINT NOT NULL
	CONSTRAINT PK_FactAfiliadoV2_fafId PRIMARY KEY CLUSTERED 
	(
		fafId ASC
	)
);

ALTER TABLE dbo.FactAfiliadoV2 ADD CONSTRAINT FK_FactAfiliadoV2_fafDimPeriodo FOREIGN KEY (fafDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactAfiliadoV2 ADD CONSTRAINT FK_FactAfiliadoV2_fafAfiliado FOREIGN KEY (fafAfiliado) REFERENCES dbo.Afiliado (afiId);

CREATE TABLE FactSolicitudNovedadEmpleadorV2
(
	fneId BIGINT NOT NULL IDENTITY,
	fneSolicitudNovedadEmpleador BIGINT NOT NULL,
	fneEmpleador BIGINT NOT NULL,
	fneDimPeriodo INT NOT NULL,
	fneAccionRadicacion BIT NULL,
	fneAccionSolicitudCerrada BIT NULL,
	fneFechaRadicacion DATETIME NOT NULL,
	fneFechaSolicitudCerrada DATETIME NULL,
	fneEsAutomatica BIT NULL,
	fneDimSede INT NULL,
	fneDimCanal TINYINT NULL,
	CONSTRAINT PK_FactSolicitudNovedadEmpleadorV2_fneId PRIMARY KEY CLUSTERED 
	(
		fneId ASC
	)
);

ALTER TABLE dbo.FactSolicitudNovedadEmpleadorV2 ADD CONSTRAINT FK_FactSolicitudNovedadEmpleadorV2_fneDimPeriodo FOREIGN KEY (fneDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactSolicitudNovedadEmpleadorV2 ADD CONSTRAINT FK_FactSolicitudNovedadEmpleadorV2_fneDimSede FOREIGN KEY (fneDimSede) REFERENCES dbo.DimSede (disId);
ALTER TABLE dbo.FactSolicitudNovedadEmpleadorV2 ADD CONSTRAINT FK_FactSolicitudNovedadEmpleadorV2_fneDimCanal FOREIGN KEY (fneDimCanal) REFERENCES dbo.DimCanalV2 (dicId);
ALTER TABLE dbo.FactSolicitudNovedadEmpleadorV2 ADD CONSTRAINT FK_FactSolicitudNovedadEmpleadorV2_fneSolicitudNovedadEmpleador FOREIGN KEY (fneSolicitudNovedadEmpleador) REFERENCES dbo.SolicitudNovedadEmpleador (sneId);
ALTER TABLE dbo.FactSolicitudNovedadEmpleadorV2 ADD CONSTRAINT FK_FactSolicitudNovedadEmpleadorV2_fneEmpleador FOREIGN KEY (fneEmpleador) REFERENCES dbo.Empleador (empId);

CREATE TABLE FactSolicitudNovedadPersonaV2
(
	fnpId BIGINT NOT NULL IDENTITY,
	fnpSolicitudNovedadPersona BIGINT NOT NULL,
	fnpPersona BIGINT NOT NULL,
	fnpDimPeriodo INT NOT NULL,
	fnpAccionRadicacion BIT NULL,
	fnpAccionSolicitudCerrada BIT NULL,
	fnpFechaRadicacion DATETIME NOT NULL,
	fnpFechaSolicitudCerrada DATETIME NULL,
	fnpEsAutomatica BIT NULL,
	fnpDimSede INT NULL,
	fnpDimCanal TINYINT NULL,
	CONSTRAINT PK_FactSolicitudNovedadPersonaV2_fnpId PRIMARY KEY CLUSTERED 
	(
		fnpId ASC
	)
);

ALTER TABLE dbo.FactSolicitudNovedadPersonaV2 ADD CONSTRAINT FK_FactSolicitudNovedadPersonaV2_fnpDimPeriodo FOREIGN KEY (fnpDimPeriodo) REFERENCES dbo.DimPeriodo (dipId);
ALTER TABLE dbo.FactSolicitudNovedadPersonaV2 ADD CONSTRAINT FK_FactSolicitudNovedadPersonaV2_fnpDimSede FOREIGN KEY (fnpDimSede) REFERENCES dbo.DimSede (disId);
ALTER TABLE dbo.FactSolicitudNovedadPersonaV2 ADD CONSTRAINT FK_FactSolicitudNovedadPersonaV2_fnpDimCanal FOREIGN KEY (fnpDimCanal) REFERENCES dbo.DimCanalV2 (dicId);
ALTER TABLE dbo.FactSolicitudNovedadPersonaV2 ADD CONSTRAINT FK_FactSolicitudNovedadPersonaV2_fnpSolicitudNovedadPersona FOREIGN KEY (fnpSolicitudNovedadPersona) REFERENCES dbo.SolicitudNovedadPersona (snpId);
ALTER TABLE dbo.FactSolicitudNovedadPersonaV2 ADD CONSTRAINT FK_FactSolicitudNovedadPersonaV2_fnpPersona FOREIGN KEY (fnpPersona) REFERENCES dbo.Persona (perId);

--changeset dsuesca:01
--comment:
ALTER TABLE FactSolicitudAfiliacionEmpleadorV2 ALTER COLUMN fseFechaRadicacion DATETIME NULL;