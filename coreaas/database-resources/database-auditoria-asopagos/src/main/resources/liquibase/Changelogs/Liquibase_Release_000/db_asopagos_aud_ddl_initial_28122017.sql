--liquibase formatted sql

--changeset Heinsohn:01
--comment: Creacion de las tablas de la base de datos asopagos_aud
--Creacion de la tabla Revision
CREATE TABLE Revision(
	revId int IDENTITY(1,1) NOT NULL,
	revIp varchar(255) NULL,
	revNombreUsuario varchar(255) NULL,
	revRequestId varchar(255) NULL,
	revTimeStamp bigint NULL,
CONSTRAINT PK_Revision_revId PRIMARY KEY (revId)
);

-- Creación de tabla RevisionEntidad  
CREATE TABLE RevisionEntidad(
	reeId int IDENTITY(1,1) NOT NULL,
	reeEntityClassName varchar(255) NULL,
	reeRevisionType int NULL,
	reeTimeStamp bigint NULL,
	reeRevision int NOT NULL,
CONSTRAINT PK_RevisionEntidad_reeId PRIMARY KEY (reeId)
);
ALTER TABLE RevisionEntidad ADD CONSTRAINT FK_RevisionEntidad_reeRevision FOREIGN KEY(reeRevision) REFERENCES Revision (revId);

--Creacion de la tabla AFP_aud
CREATE TABLE AFP_aud(
	afpId smallint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	afpNombre varchar(150) NOT NULL,
	afpCodigoPila varchar(10) NULL,
);
ALTER TABLE AFP_aud ADD CONSTRAINT FK_AFP_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla ARL_aud    
CREATE TABLE ARL_aud(
	arlId smallint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	arlNombre varchar(25) NOT NULL,
);
ALTER TABLE ARL_aud ADD CONSTRAINT FK_ARL_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla Departamento_aud    
CREATE TABLE Departamento_aud(
	depId smallint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	depCodigo varchar(2) NOT NULL,
	depIndicativoTelefoniaFija varchar(2) NOT NULL,
	depNombre varchar(100) NOT NULL,
	depExcepcionAplicaFOVIS bit NULL,
);
ALTER TABLE Departamento_aud ADD CONSTRAINT FK_Departamento_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla Municipio_aud    
CREATE TABLE Municipio_aud(
	munId smallint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	munCodigo varchar(6) NOT NULL,
	munNombre varchar(50) NOT NULL,
	munDepartamento smallint NOT NULL,
);
ALTER TABLE Municipio_aud ADD CONSTRAINT FK_Municipio_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla CodigoCIIU_aud    
CREATE TABLE CodigoCIIU_aud(
	ciiId smallint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ciiCodigo varchar(4) NOT NULL,
	ciiDescripcion varchar(255) NOT NULL,
	ciiCodigoSeccion varchar(1) NULL,
	ciiDescripcionSeccion varchar(200) NULL,
	ciiCodigoDivision varchar(2) NULL,
	ciiDescripcionDivision varchar(250) NULL,
	ciiCodigoGrupo varchar(3) NULL,
	ciiDescripcionGrupo varchar(200) NULL,
);
ALTER TABLE CodigoCIIU_aud ADD CONSTRAINT FK_CodigoCIIU_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla DiasFestivos_aud    
CREATE TABLE DiasFestivos_aud(
	pifId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pifConcepto varchar(100) NOT NULL,
	pifFecha date NOT NULL,
);
ALTER TABLE DiasFestivos_aud ADD CONSTRAINT FK_DiasFestivos_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla GradoAcademico_aud    
CREATE TABLE GradoAcademico_aud(
	graId smallint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	graNombre varchar(20) NOT NULL,
	graNivelEducativo varchar(43) NOT NULL,
);
ALTER TABLE GradoAcademico_aud ADD CONSTRAINT FK_GradoAcademico_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla OcupacionProfesion_aud   
CREATE TABLE OcupacionProfesion_aud(
	ocuId int NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ocuNombre varchar(100) NOT NULL,
);
ALTER TABLE OcupacionProfesion_aud ADD CONSTRAINT FK_OcupacionProfesion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla CajaCompensacion_aud    
CREATE TABLE CajaCompensacion_aud(
	ccfId int NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ccfHabilitado bit NOT NULL,
	ccfMetodoGeneracionEtiquetas varchar(20) NOT NULL,
	ccfNombre varchar(100) NOT NULL,
	ccfSocioAsopagos bit NOT NULL,
	ccfDepartamento smallint NOT NULL,
	ccfCodigo varchar(5) NOT NULL,
	ccfCodigoRedeban int NULL,
);
ALTER TABLE CajaCompensacion_aud ADD CONSTRAINT FK_CajaCompensacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla Requisito_aud   
CREATE TABLE Requisito_aud(
	reqId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	reqDescripcion varchar(200) NOT NULL,
	reqEstado varchar(20) NOT NULL,
);
ALTER TABLE Requisito_aud ADD CONSTRAINT FK_Requisito_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla ElementoDireccion_aud    
CREATE TABLE ElementoDireccion_aud(
	eldId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	eldNombre varchar(20) NOT NULL,
);
ALTER TABLE ElementoDireccion_aud ADD CONSTRAINT FK_ElementoDireccion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla TipoVia_aud    
CREATE TABLE TipoVia_aud(
	tviId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	tviNombreVia varchar(20) NOT NULL,
);
ALTER TABLE TipoVia_aud ADD CONSTRAINT FK_TipoVia_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla Beneficio_aud    
CREATE TABLE Beneficio_aud(
	befId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	befTipoBeneficio varchar(16) NOT NULL,
	befVigenciaFiscal bit NOT NULL,
	befFechaVigenciaInicio date NULL,
	befFechaVigenciaFin date NULL,
);
ALTER TABLE Beneficio_aud ADD CONSTRAINT FK_Beneficio_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
 
--Creacion de la tabla Banco_aud    
CREATE TABLE Banco_aud(
	banId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	banCodigoPILA varchar(4) NOT NULL,
	banNombre varchar(255) NOT NULL,
	banMedioPago bit NOT NULL,
);
ALTER TABLE Banco_aud ADD CONSTRAINT FK_Banco_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla OperadorInformacion_aud    
CREATE TABLE OperadorInformacion_aud(
	oinId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	oinCodigo varchar(2) NOT NULL,
	oinNombre varchar(75) NOT NULL,
	oinOperadorActivo bit NOT NULL,
);
ALTER TABLE OperadorInformacion_aud ADD CONSTRAINT FK_OperadorInformacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla TipoTenencia_aud    
CREATE TABLE TipoTenencia_aud(
	tenId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	tenCodigo smallint NOT NULL,
	tenNombre varchar(255) NOT NULL,
	tenActivo bit NOT NULL,
);
ALTER TABLE TipoTenencia_aud ADD CONSTRAINT FK_TipoTenencia_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
 
--Creacion de la tabla TipoInfraestructura_aud    
CREATE TABLE TipoInfraestructura_aud(
	tifId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	tifCodigo varchar(3) NOT NULL,
	tifNombre varchar(255) NOT NULL,
	tifMedidaCapacidad varchar(40) NOT NULL,
	tifActivo bit NOT NULL,
);
ALTER TABLE TipoInfraestructura_aud ADD CONSTRAINT FK_TipoInfraestructura_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla Infraestructura_aud    
CREATE TABLE Infraestructura_aud(
	infId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	infCodigoParaSSF varchar(13) NOT NULL,
	infCodigoParaCCF varchar(12) NOT NULL,
	infConsecutivoInfraestructura smallint NOT NULL,
	infNombre varchar(255) NOT NULL,
	infTipoInfraestructura bigint NOT NULL,
	infZona varchar(255) NOT NULL,
	infDireccion varchar(300) NOT NULL,
	infAreaGeografica varchar(6) NOT NULL,
	infMunicipio bigint NOT NULL,
	infTipoTenencia bigint NOT NULL,
	infLatitud numeric(9, 6) NOT NULL,
	infLongitud numeric(9, 6) NOT NULL,
	infActivo bit NOT NULL,
	infCapacidadEstimada numeric(7, 2) NULL,
);
ALTER TABLE Infraestructura_aud ADD CONSTRAINT FK_Infraestructura_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
