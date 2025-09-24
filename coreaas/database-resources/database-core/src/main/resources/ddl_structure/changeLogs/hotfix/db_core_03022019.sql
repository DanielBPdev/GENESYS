--liquibase formatted sql

--changeset dsuesca:01
--comment: Creacion modelo de tablas de historico de estados

CREATE TABLE dbo.EstadoAfiliacionEmpleadorCaja
(
	eecId bigint IDENTITY NOT NULL,
	eecPersona bigint NOT NULL,
	eecEstadoAfiliacion varchar(50) NULL,
	eecFechaCambioEstado DATETIME NOT NULL,
	eecMotivoDesafiliacion VARCHAR(100),
	eecNumeroTrabajadores INT,
 CONSTRAINT PK_EstadoAfiliacionEmpleadorCaja_eecId PRIMARY KEY CLUSTERED 
(
	eecId ASC
)
);

CREATE TABLE dbo.EstadoAfiliacionPersonaCaja
(
	eacId bigint IDENTITY NOT NULL,
	eacPersona bigint NOT NULL,
	eacEstadoAfiliacion varchar(50) NULL,
	eacFechaCambioEstado DATETIME NOT NULL,
 CONSTRAINT PK_EstadoAfiliacionPersonaCaja_eacId PRIMARY KEY CLUSTERED 
(
	eacId ASC
)
);

CREATE TABLE dbo.EstadoAfiliacionPersonaEmpresa
(
	eaeId bigint IDENTITY NOT NULL,
	eaePersona bigint NOT NULL,
	eaeEmpleador bigint NOT NULL,
	eaeEstadoAfiliacion varchar(50) NULL,
	eaeFechaCambioEstado DATETIME NOT NULL,
 CONSTRAINT PK_EstadoAfiliacionPersonaEmpresa_eaeId PRIMARY KEY CLUSTERED 
(
	eaeId ASC
)
);

CREATE TABLE dbo.EstadoAfiliacionPersonaIndependiente
(
	eaiId bigint IDENTITY NOT NULL,
	eaiPersona bigint NOT NULL,
	eaiEstadoAfiliacion varchar(50) NULL,
	eaiFechaCambioEstado DATETIME NOT NULL,
	eaiClaseIndependiente VARCHAR(50),
 CONSTRAINT PK_EstadoAfiliacionPersonaIndependiente_eaiId PRIMARY KEY CLUSTERED 
(
	eaiId ASC
)
);

CREATE TABLE dbo.EstadoAfiliacionPersonaPensionado
(
	eapId bigint IDENTITY NOT NULL,
	eapPersona bigint NOT NULL,
	eapEstadoAfiliacion varchar(50) NULL,
	eapFechaCambioEstado DATETIME NOT NULL,
 CONSTRAINT PK_EstadoAfiliacionPersonaPensionado_eapId PRIMARY KEY CLUSTERED 
(
	eapId ASC
)
)

CREATE TABLE dbo.EstadoAfiliacionBeneficiario
(
	eabId bigint IDENTITY NOT NULL,
	eabPersona bigint NOT NULL,
	eabEstadoAfiliacion varchar(50) NULL,
	eabFechaCambioEstado DATETIME NOT NULL,
 CONSTRAINT PK_EstadoAfiliacionBeneficiario_eabId PRIMARY KEY CLUSTERED 
(
	eabId ASC
)
);