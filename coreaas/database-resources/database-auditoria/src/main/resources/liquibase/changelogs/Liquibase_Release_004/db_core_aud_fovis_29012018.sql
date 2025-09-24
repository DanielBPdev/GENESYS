--liquibase formatted sql

--changeset mamonroy:01
--comment:Creacion de las tablas Visita_aud y CondicionVisita_aud
CREATE TABLE Visita_aud(
	visId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	visFecha DATE NOT NULL,
	visNombresEncargado VARCHAR(50) NOT NULL,
	visCodigoIdentificadorECM VARCHAR(255) NOT NULL,
);
ALTER TABLE Visita_aud ADD CONSTRAINT FK_Visita_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE CondicionVisita_aud(
	covId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	covCondicion VARCHAR(42) NOT NULL,
	covCumple BIT NOT NULL,
	covObservacion VARCHAR(250) NULL,
	covVisita BIGINT NOT NULL,
);
ALTER TABLE CondicionVisita_aud ADD CONSTRAINT FK_CondicionVisita_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset alquintero:02
--comment:Creación de las tablas Legalizacion_aud, SolicitudLegalizacionDesembolso_aud, IntentoLegalizacionDesembolso_aud y IntentoLegalizacionDesembolsoRequisito_aud
CREATE TABLE LegalizacionDesembolso_aud(
	lgdId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	lgdFormaPago VARCHAR(50) NULL,
	lgdMedioDePago BIGINT NULL,
	lgdValorDesembolsar NUMERIC(19,5) NULL,
	lgdFechaLimitePago DATETIME NULL,
	lgdVisita BIGINT NULL,
	lgdSubsidioDesembolsado BIT NULL,
);
ALTER TABLE LegalizacionDesembolso_aud ADD CONSTRAINT FK_LegalizacionDesembolso_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE SolicitudLegalizacionDesembolso_aud(
	sldId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	sldSolicitudGlobal BIGINT NOT NULL,
	sldPostulacionFOVIS BIGINT NULL,
	sldEstadoSolicitud VARCHAR(42) NULL,
	sldLegalizacionDesembolso BIGINT NULL,
	sldObservaciones VARCHAR(500) NULL,
);
ALTER TABLE SolicitudLegalizacionDesembolso_aud ADD CONSTRAINT FK_SolicitudLegalizacionDesembolso_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE IntentoLegalizacionDesembolso_aud(
	ildId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ildCausaIntentoFallido VARCHAR(50) NULL,
	ildFechaCreacion DATETIME NULL,
	ildSedeCajaCompensacion VARCHAR(2) NULL,
	ildUsuarioCreacion VARCHAR(255) NULL,
	ildSolicitud BIGINT NULL,
	ildProceso VARCHAR(32) NULL,
	ildTipoSolicitante VARCHAR(5) NULL,
	ildModalidad VARCHAR(33) NULL,
);
ALTER TABLE IntentoLegalizacionDesembolso_aud ADD CONSTRAINT FK_IntentoLegalizacionDesembolso_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE IntentoLegalizacionDesembolsoRequisito_aud(
	ilrId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ilrIntentoLegalizacionDesembolso BIGINT NULL,
	ilrRequisito BIGINT NULL,
);
ALTER TABLE IntentoLegalizacionDesembolsoRequisito_aud ADD CONSTRAINT FK_IntentoLegalizacionDesembolsoRequisito_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset flopez:03
--comment:Creacion de la tabla DocumentoSoporte
CREATE TABLE DocumentoSoporte_aud(
	dosId BIGINT NOT NULL,
	REV INT NOT NULL,
	REVTYPE SMALLINT NULL,
	dosNombreDocumento VARCHAR (255) NOT NULL,
	dosDescripcionComentarios VARCHAR (255) NOT NULL,
	dosIdentificacionDocumento VARCHAR(255) NOT NULL,
	dosVersionDocumento VARCHAR(3) NOT NULL,               
	dosFechaHoraCargue DATETIME NOT NULL
);
ALTER TABLE DocumentoSoporte_aud ADD CONSTRAINT FK_DocumentoSoporte_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset ecastano:04
--comment:Creacion de la tabla DocumentoSoporteProyectoVivienda_aud y DocumentoSoporteOferente_aud y adicion de campos en las tabla Licencia_aud,LicenciaDetalle_aud,Oferente_aud y SolicitudLegalizacionDesembolso_aud
CREATE TABLE DocumentoSoporteProyectoVivienda_aud(
	dspId BIGINT NOT NULL,
	REV INT NOT NULL,
	REVTYPE SMALLINT NULL,
	dspProyectoSolucionVivienda BIGINT NOT NULL,
	dspDocumentoSoporte  BIGINT NOT NULL,
);
ALTER TABLE DocumentoSoporteProyectoVivienda_aud ADD CONSTRAINT FK_DocumentoSoporteProyectoVivienda_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE DocumentoSoporteOferente_aud(
	dsoId BIGINT NOT NULL,
	REV INT NOT NULL,
	REVTYPE SMALLINT NULL,
	dsoOferente BIGINT NOT NULL,
	dsoDocumentoSoporte  BIGINT NOT NULL,
);
ALTER TABLE DocumentoSoporteOferente_aud ADD CONSTRAINT FK_DocumentoSoporteOferente_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

ALTER TABLE SolicitudLegalizacionDesembolso_aud ADD sldFechaOperacion DATETIME NULL;

ALTER TABLE Licencia_aud ADD licTipoLicencia VARCHAR(12) NULL;

ALTER TABLE LicenciaDetalle_aud ADD lidClasificacionLicencia VARCHAR(33) NULL;
ALTER TABLE LicenciaDetalle_aud ADD lidEstadoLicencia BIT NULL;

ALTER TABLE Oferente_aud ADD ofeCuentaBancaria BIT NULL;
ALTER TABLE Oferente_aud ADD ofeBanco BIGINT NULL;
ALTER TABLE Oferente_aud ADD ofeTipoCuenta VARCHAR(30) NULL;
ALTER TABLE Oferente_aud ADD ofeNumeroCuenta VARCHAR(30) NULL;
ALTER TABLE Oferente_aud ADD ofeTipoIdentificacionTitular VARCHAR(20) NULL;
ALTER TABLE Oferente_aud ADD ofeNumeroIdentificacionTitular VARCHAR(16) NULL;
ALTER TABLE Oferente_aud ADD ofeDigitoVerificacionTitular SMALLINT NULL;
ALTER TABLE Oferente_aud ADD ofeNombreTitularCuenta VARCHAR(200) NULL;

--changeset alquintero:05
--comment:Se adiciona campo en la tabla SolicitudLegalizacionDesembolso_aud
ALTER TABLE SolicitudLegalizacionDesembolso_aud ADD sldJsonPostulacion TEXT NULL;

--changeset ecastano:06
--comment:Se elimina columna de la tabla Oferente_aud
ALTER TABLE Oferente_aud DROP COLUMN ofeMedioDePago;

--changeset ecastano:07
--comment: Se adiciona campo en la tabla DocumentoSoporte
ALTER TABLE DocumentoSoporte_aud ADD dosTipoDocumento VARCHAR(22) NULL;

--changeset alquintero:08
--comment:Eliminacion y creacion de campo en la tabla LegalizacionDesembolso
ALTER TABLE LegalizacionDesembolso_aud DROP COLUMN lgdMedioDePago;
ALTER TABLE LegalizacionDesembolso_aud ADD lgdMediosPagoCCF BIGINT NULL;

--changeset flopez:09
--comment:Se adiciona campo en la tabla ProyectoSolucionVivienda_aud
CREATE TABLE MedioPagoProyectoVivienda_aud(
	mprId BIGINT NOT NULL,
	REV INT NOT NULL,
	REVTYPE SMALLINT NULL,
	mprProyectoSolucionVivienda BIGINT NOT NULL,
	mprMedioDePago BIGINT NOT NULL,
	mprActivo BIT,
);
ALTER TABLE MedioPagoProyectoVivienda_aud ADD CONSTRAINT FK_MedioPagoProyectoVivienda_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE ProyectoSolucionVivienda_aud DROP COLUMN psvMedioDePago;
ALTER TABLE ProyectoSolucionVivienda_aud ADD psvRegistrado BIT NULL;

--changeset alquintero:10
--comment:Se elimina y adiciona campo en la tabla LegalizacionDesembolso
ALTER TABLE LegalizacionDesembolso_aud DROP COLUMN lgdMediosPagoCCF;
ALTER TABLE LegalizacionDesembolso_aud ADD lgdTipoMedioPago VARCHAR (30) NULL;

--changeset flopez:11
--comment:Se adiciona campo en la tabla 
ALTER TABLE PostulacionFOVIS_aud ADD pofValorProyectoVivienda NUMERIC (19,5) NULL;