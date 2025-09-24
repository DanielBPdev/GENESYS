--liquibase formatted sql

--changeset clmarin:01
--comment: Creacion de tablas 

-- Creacion de tabla Destinatario
CREATE TABLE Destinatario(
	desId bigint IDENTITY(1,1) NOT NULL,
	desNombre varchar(50) NULL,
	desRolContacto varchar(150) NULL
CONSTRAINT PK_Destinatario_desId PRIMARY KEY (desId)
);

-- Creacion de tabla DestinatarioComunicado
CREATE TABLE DestinatarioComunicado(
	dcoId bigint IDENTITY(1,1) NOT NULL,
	dcoProceso varchar(150) NOT NULL,
	dcoEtiquetaPlantilla varchar(150) NOT NULL
CONSTRAINT PK_DestinatarioComunicado_dcoId PRIMARY KEY (dcoId)
);

ALTER TABLE DestinatarioComunicado ADD CONSTRAINT UK_DestinatarioComunicado_dcoProceso_dcoEtiquetaPlantilla UNIQUE (dcoProceso,dcoEtiquetaPlantilla);

-- Creacion de tabla GrupoPrioridad
CREATE TABLE GrupoPrioridad(
	gprId bigint IDENTITY(1,1) NOT NULL,
	gprNombre varchar(150) NULL
CONSTRAINT PK_GrupoPrioridad_gprId PRIMARY KEY (gprId)
);

-- Creacion de tabla DestinatarioGrupo
CREATE TABLE DestinatarioGrupo(
	dgrId bigint IDENTITY(1,1) NOT NULL,
	dgrDestinatario bigint NOT NULL,
	dgrGrupoPrioridad bigint NOT NULL
CONSTRAINT PK_DestinatarioGrupo_dgrId PRIMARY KEY (dgrId)
);

ALTER TABLE DestinatarioGrupo WITH CHECK ADD CONSTRAINT FK_DestinatarioGrupo_dgrDestinatario FOREIGN KEY (dgrDestinatario) REFERENCES Destinatario (desId);
ALTER TABLE DestinatarioGrupo WITH CHECK ADD CONSTRAINT FK_DestinatarioGrupo_dgrGrupoPrioridad FOREIGN KEY (dgrGrupoPrioridad) REFERENCES GrupoPrioridad (gprId);

-- Creacion de tabla PrioridadDestinatario
CREATE TABLE PrioridadDestinatario(
	prdId bigint IDENTITY(1,1) NOT NULL,
	prdDestinatarioComunicado bigint NOT NULL,
	prdGrupoPrioridad bigint NOT NULL,
	prdPrioridad smallint NULL
CONSTRAINT PK_PrioridadDestinatario_prdId PRIMARY KEY (prdId)
);

ALTER TABLE PrioridadDestinatario WITH CHECK ADD CONSTRAINT FK_PrioridadDestinatario_prdDestinatarioComunicado FOREIGN KEY (prdDestinatarioComunicado) REFERENCES DestinatarioComunicado (dcoId);
ALTER TABLE PrioridadDestinatario WITH CHECK ADD CONSTRAINT FK_PrioridadDestinatario_prdGrupoPrioridad FOREIGN KEY (prdGrupoPrioridad) REFERENCES GrupoPrioridad (gprId);


