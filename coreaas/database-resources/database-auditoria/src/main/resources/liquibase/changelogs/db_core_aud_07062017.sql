--liquibase formatted sql

--changeset atoro:01
--comment: Se resuelve incidencia	
ALTER TABLE SolicitudNovedadPersona_aud ADD snpRolAfiliado bigint NULL;
ALTER TABLE SolicitudNovedadPersona_aud ADD snpBeneficiario bigint NULL;

--changeset jusanchez:02
--comment: Se agrega nuevo campo en la tabla Parametro
ALTER TABLE Parametro_aud ADD prmEstado bit NULL;

--changeset jusanchez:03
--comment: Se agregan nuevos campos en la tabla Parametro
ALTER TABLE Parametro_aud ADD prmCargaInicio bit 
ALTER TABLE Parametro_aud ADD prmSubCategoriaParametro varchar(23)

--changeset clmarin:04
--comment: Creacion de tablas 
--Creacion de tabla Destinatario
CREATE TABLE Destinatario_aud(
	desId bigint IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	desNombre varchar(50) NULL,
	desRolContacto varchar(150) NULL
CONSTRAINT PK_Destinatario_desId PRIMARY KEY (desId)
);

-- Creacion de tabla DestinatarioComunicado_aud
CREATE TABLE DestinatarioComunicado_aud(
	dcoId bigint IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	dcoProceso varchar(150) NOT NULL,
	dcoEtiquetaPlantilla varchar(150) NOT NULL
CONSTRAINT PK_DestinatarioComunicado_dcoId PRIMARY KEY (dcoId)
);

-- Creacion de tabla GrupoPrioridad_aud
CREATE TABLE GrupoPrioridad_aud(
	gprId bigint IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	gprNombre varchar(150) NULL
CONSTRAINT PK_GrupoPrioridad_gprId PRIMARY KEY (gprId)
);

-- Creacion de tabla DestinatarioGrupo_aud
CREATE TABLE DestinatarioGrupo_aud(
	dgrId bigint IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	dgrDestinatario bigint NOT NULL,
	dgrGrupoPrioridad bigint NOT NULL
CONSTRAINT PK_DestinatarioGrupo_dgrId PRIMARY KEY (dgrId)
);

-- Creacion de tabla PrioridadDestinatario_aud
CREATE TABLE PrioridadDestinatario_aud(
	prdId bigint IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	prdDestinatarioComunicado bigint NOT NULL,
	prdGrupoPrioridad bigint NOT NULL,
	prdPrioridad smallint NULL
CONSTRAINT PK_PrioridadDestinatario_prdId PRIMARY KEY (prdId)
);

ALTER TABLE Destinatario_aud WITH CHECK ADD CONSTRAINT FK_Destinatario_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE DestinatarioComunicado_aud WITH CHECK ADD CONSTRAINT FK_DestinatarioComunicado_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE GrupoPrioridad_aud WITH CHECK ADD CONSTRAINT FK_GrupoPrioridad_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE DestinatarioGrupo_aud WITH CHECK ADD CONSTRAINT FK_DestinatarioGrupo_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE PrioridadDestinatario_aud WITH CHECK ADD CONSTRAINT FK_PrioridadDestinatario_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

