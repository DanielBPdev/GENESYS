--liquibase formatted sql

--changeset jocorrea:01
--comment: Se crea la tabla EjecucionProcesoAsincrono_aud y se adicionan campos en la tabla Cruce_aud y CruceDetalle_aud
CREATE TABLE EjecucionProcesoAsincrono_aud(
	epsId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	epsFechaInicio DATETIME NOT NULL,
	epsFechaFin DATETIME NULL,
	epsRevisado BIT NOT NULL,
	epsTipoProceso VARCHAR(18) NULL,
);
ALTER TABLE EjecucionProcesoAsincrono_aud ADD CONSTRAINT FK_EjecucionProcesoAsincrono_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

ALTER TABLE Cruce_aud ADD cruEjecucionProcesoAsincrono BIGINT NULL;

ALTER TABLE CruceDetalle_aud ADD crdResultadoValidacion VARCHAR(255) NOT NULL;
ALTER TABLE CruceDetalle_aud ADD crdClasificacion VARCHAR(30) NOT NULL;

ALTER TABLE SolicitudGestionCruce_aud ADD sgcTipoCruce VARCHAR(8) NULL;

--changeset ecastano:02
--comment: Se crean las tablas SolicitudAsignacio_aud, ActaAsignacionFovis_aud y se adicionan campo en la tabla PostulacionFOVIS_aud
CREATE TABLE SolicitudAsignacion_aud(
	safId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	safSolicitudGlobal BIGINT NOT NULL,
	safFechaAceptacion datetime NULL,
	safUsuario VARCHAR (50) NULL,
	safValorSFVAsignado NUMERIC(19,5) NULL,	
	safEstadoSolicitudAsignacion VARCHAR(50) NULL,
	safComentarios VARCHAR(500) NULL,	
	safCicloAsignacion BIGINT NOT NULL,
 );
ALTER TABLE SolicitudAsignacion_aud ADD CONSTRAINT FK_SolicitudAsignacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE ActaAsignacionFovis_aud(
	aafId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	aafSolicitudAsignacion BIGINT NOT NULL,
	aafIdentificadorDocumentoActa VARCHAR(255) NULL,
	aafIdentificadorDocumentoConsolidado VARCHAR(255) NULL,
	aafNumeroResolucion BIGINT NULL,
	aafNumeroOficio BIGINT NULL,
	aafAnoResolucion VARCHAR(4) NULL,
	aafFechaResolucion DATETIME NULL,
	aafFechaOficio DATETIME NULL,
	aafNombreResponsable1 VARCHAR(50) NULL,
	aafCargoResponsable1 VARCHAR(50) NULL,
	aafNombreResponsable2 VARCHAR(50) NULL,
	aafCargoResponsable2 VARCHAR(50) NULL,	
	aafNombreResponsable3 VARCHAR(50) NULL,	
	aafCargoResponsable3 VARCHAR(50) NULL,	
	aafFechaConfirmacion DATETIME NULL,
	aafFechaCorte DATETIME NULL,
	aafInicioVigencia DATETIME NULL,
	aafFinVigencia DATETIME NULL,	
);
ALTER TABLE ActaAsignacionFovis_aud ADD CONSTRAINT FK_ActaAsignacionFovis_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

ALTER TABLE PostulacionFOVIS_aud ADD pofSolicitudAsignacion BIGINT NULL;
ALTER TABLE PostulacionFOVIS_aud ADD pofResultadoAsignacion VARCHAR(50) NULL;
ALTER TABLE PostulacionFOVIS_aud ADD pofValorAsignadoSFV NUMERIC(19,5) NULL;
ALTER TABLE PostulacionFOVIS_aud ADD pofIdentificardorDocumentoActaAsignacion VARCHAR(255) NULL;
ALTER TABLE PostulacionFOVIS_aud ADD pofPuntaje NUMERIC(5,2) NULL;

--changeset jocorrea:03
--comment:Se modifica campo en la tabla EjecucionProcesoAsincrono_aud 
ALTER TABLE EjecucionProcesoAsincrono_aud ALTER COLUMN epsTipoProceso VARCHAR(20) NULL;

--changeset mosanchez:03
--comment:Se adiciona campo en la tabla CondicionEspecialPersona
ALTER TABLE CondicionEspecialPersona_aud ADD cepNombreCondicion VARCHAR(28) NULL;

--changeset mosanchez:04
--comment:Se actualizan registros en la tabla CondicionEspecialPersona
UPDATE CondicionEspecialPersona_aud SET cepNombreCondicion = 'CONDICION_INVALIDEZ' WHERE cepCondicionEspecial = 1;
UPDATE CondicionEspecialPersona_aud SET cepNombreCondicion = 'DAMNIFICADO_DESASTRE_NATURAL' WHERE cepCondicionEspecial = 2;
UPDATE CondicionEspecialPersona_aud SET cepNombreCondicion = 'INDIGENA' WHERE cepCondicionEspEcial = 3;
UPDATE CondicionEspecialPersona_aud SET cepNombreCondicion = 'MADRE_COMUNITARIA_ICBF' WHERE cepCondicionEspecial = 4;
UPDATE CondicionEspecialPersona_aud SET cepNombreCondicion = 'MIEMBRO_HOGAR_AFROCOLOMBIANO' WHERE cepCondicionEspecial = 5;
UPDATE CondicionEspecialPersona_aud SET cepNombreCondicion = 'MUJER_HOMBRE_CABEZA_FAMILIA' WHERE cepCondicionEspecial = 6;

--changeset fvasquez:04
--comment:Se elimina campo en la tabla CondicionEspecialPersona y se elimina la tabla CondicionEspecial_aud
ALTER TABLE CondicionEspecialPersona_aud ALTER COLUMN cepNombreCondicion VARCHAR(28) NOT NULL;

--changeset fvasquez:05
--comment:Se elimina campo en la tabla CondicionEspecialPersona y se elimina la tabla CondicionEspecial_aud
ALTER TABLE CondicionEspecialPersona_aud DROP CONSTRAINT FK_CondicionEspecialPersona_aud_REV;
ALTER TABLE CondicionEspecialPersona_aud DROP COLUMN cepCondicionEspecial;
DROP TABLE CondicionEspecial_aud;

--changeset jocorrea:06
--comment:Se adiciona campo en la tabla Cruce_aud y se modifican campos en la tabla CruceDetalle_aud
ALTER TABLE Cruce_aud ADD cruFechaRegistro DATETIME NOT NULL;
ALTER TABLE CruceDetalle_aud ALTER COLUMN crdCausalCruce VARCHAR (30) NULL;
ALTER TABLE CruceDetalle_aud ALTER COLUMN crdTipo VARCHAR (15) NULL;
ALTER TABLE CruceDetalle_aud ALTER COLUMN crdResultadoValidacion VARCHAR (255) NULL;
ALTER TABLE CruceDetalle_aud ALTER COLUMN crdClasificacion VARCHAR (30) NULL;

--changeset jocorrea:07
--comment:Se modifica campo en la tabla CruceDetalle_aud
ALTER TABLE CruceDetalle_aud ALTER COLUMN crdSexo VARCHAR(20) NULL;

--changeset flopez:07
--comment:Se modifican campos en las tablas RecursoComplementario y AhorroPrevio
ALTER TABLE RecursoComplementario_aud ALTER COLUMN recNombre VARCHAR(26) NULL;
ALTER TABLE AhorroPrevio_aud ALTER COLUMN ahpNombreAhorro VARCHAR(65) NULL;

--changeset flopez:08
--comment:Se adiciona campo en la tabla CondicionEspecialPersona_aud
ALTER TABLE CondicionEspecialPersona_aud ADD cepActiva BIT NULL;

--changeset fvasquez:31
--comment:Se adiciona campo a la tabla ActaAsignacionFovis_aud
ALTER TABLE ActaAsignacionFovis_aud ADD aafFechaActaAsignacionFovis DATETIME NULL;

--changeset ecastaño:32
--comment:Se adiciona campo a la tabla ActaAsignacionFovis
ALTER TABLE PostulacionFOVIS_aud ADD pofFechaCalificacion DATETIME NULL;
ALTER TABLE PostulacionFOVIS_aud ADD pofPrioridadAsignacion VARCHAR(11) NULL;

--changeset jocorrea:09
--comment:Se eliminan y crean tablas de Cruce_aud, CruceDetalle_aud y SolicitudGestionCruce_aud
ALTER TABLE CruceDetalle_aud DROP CONSTRAINT FK_CruceDetalle_aud_REV;
DROP TABLE CruceDetalle_aud;

ALTER TABLE Cruce_aud DROP CONSTRAINT FK_Cruce_aud_REV;
DROP TABLE Cruce_aud;

ALTER TABLE SolicitudGestionCruce_aud DROP CONSTRAINT FK_SolicitudGestionCruce_aud_REV;
DROP TABLE SolicitudGestionCruce_aud;

CREATE TABLE Cruce_aud(
	cruId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	cruCargueArchivoCruceFovis BIGINT NULL,
	cruNumeroPostulacion VARCHAR(12) NULL,
	cruPersona BIGINT NULL,
	cruEstadoCruce VARCHAR(22) NOT NULL,
	cruSolicitudGestionCruce BIGINT NULL,
	cruResultadoCodigoIdentificadorECM VARCHAR(255) NULL,
	cruObservacionResultado VARCHAR(500) NULL,
	cruEjecucionProcesoAsincrono BIGINT NULL,
	cruFechaRegistro DATETIME NOT NULL,
);
ALTER TABLE Cruce_aud ADD CONSTRAINT FK_Cruce_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
 
CREATE TABLE CruceDetalle_aud(
	crdId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	crdCruce BIGINT NOT NULL,
	crdCausalCruce VARCHAR(30) NULL,
	crdNitEntidad VARCHAR(16) NULL,
	crdNombreEntidad VARCHAR(100) NULL,
	crdNumeroIdentificacion VARCHAR(16) NULL,
	crdApellidos VARCHAR(100) NULL,
	crdNombres VARCHAR(100) NULL,
	crdCedulaCatastral VARCHAR(50) NULL,
	crdDireccionInmueble VARCHAR(300) NULL,
	crdMatriculaInmobiliaria VARCHAR(50) NULL,
	crdDepartamento VARCHAR(100) NULL,
	crdMunicipio VARCHAR(50) NULL,
	crdFechaActualizacionMinisterio DATE NULL,
	crdFechaCorteEntidad DATE NULL,
	crdApellidosNombres VARCHAR(200) NULL,
	crdPuntaje VARCHAR(10) NULL,
	crdSexo VARCHAR(20) NULL,
	crdZona VARCHAR(30) NULL,
	crdParentesco VARCHAR(30) NULL,
	crdFolio VARCHAR(30) NULL,
	crdTipodocumento VARCHAR(30) NULL,
	crdFechaSolicitud DATE NULL,
	crdEntidadOtorgante VARCHAR(30) NULL,
	crdCajaCompensacion VARCHAR(30) NULL,
	crdAsignadoPosterior VARCHAR(30) NULL,
	crdTipo VARCHAR(15) NULL,
	crdResultadoValidacion VARCHAR(255) NULL,
	crdClasificacion VARCHAR(30) NULL,
); 
ALTER TABLE CruceDetalle_aud ADD CONSTRAINT FK_CruceDetalle_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE SolicitudGestionCruce_aud(
	sgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	sgcSolicitudPostulacion BIGINT NOT NULL,
	sgcEstadoCruceHogar VARCHAR(53) NULL,
	sgcTipoCruce VARCHAR(8) NULL,
CONSTRAINT PK_SolicitudGestionCruce_sgcId PRIMARY KEY (sgcId)
);
ALTER TABLE SolicitudGestionCruce_aud ADD CONSTRAINT FK_SolicitudGestionCruce_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset ecastano:10
--comment:Se adiciona campo en la tabla SolicitudAsignacion_aud
ALTER TABLE SolicitudAsignacion_aud ADD safComentarioControlInterno VARCHAR(500) NULL;

--changeset ecastano:11
--comment:Se modifica campo en la tabla ActaAsignacionFOVIS_aud
ALTER TABLE ActaAsignacionFOVIS_aud ALTER COLUMN aafNumeroResolucion VARCHAR(20);
ALTER TABLE ActaAsignacionFOVIS_aud ALTER COLUMN aafNumeroOficio VARCHAR(20);

--changeset mamonroy:12
--comment:Se adiciona campo en la tabla SolicitudGestionCruce
ALTER TABLE SolicitudGestionCruce_aud ADD sgcEstado VARCHAR (32) NULL;

--changeset jocorrea:13
--comment:Se elimina llave primaria de la tabla SolicitudGestionCruce
ALTER TABLE SolicitudGestionCruce_aud DROP CONSTRAINT PK_SolicitudGestionCruce_sgcId;

--changeset alquintero:14
--comment: Se adiciona campo en la tabla SolicitudPostulacion_aud
ALTER TABLE SolicitudPostulacion_aud ADD spoObservacionesWeb VARCHAR(500) NULL;

--changeset alquintero:15
--comment:Se modifica campo en la tabla ParametrizacionModalidad_aud
ALTER TABLE ParametrizacionModalidad_aud ALTER COLUMN pmoTopeSMLMV NUMERIC (4,1) NULL;

--changeset alquintero:16
--comment:Se adiciona campo en la tabla PostulacionFOVIS
ALTER TABLE PostulacionFOVIS_aud ADD pofValorCalculadoSFV NUMERIC (19,5) NULL;