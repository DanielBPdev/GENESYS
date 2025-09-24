--liquibase formatted sql

--changeset arocha:01
--comment:Creacion modelo de tablas de historico de estados

CREATE TABLE dbo.EstadoAfiliacionEmpleadorCaja
(
	eecId bigint IDENTITY NOT NULL,
	eecPersona bigint NOT NULL,
	eecEstadoAfiliacion varchar(50) NULL,
	eecFechaCambioEstado DATETIME NOT NULL,
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

--changeset dsuesca:02
--comment: Se agregan campos eecMotivoDesafiliacion y eecNumeroTrabajadores
ALTER TABLE EstadoAfiliacionEmpleadorCaja ADD eecMotivoDesafiliacion VARCHAR(100);
ALTER TABLE EstadoAfiliacionEmpleadorCaja ADD eecNumeroTrabajadores INT;

--changeset arocha:03
--comment: Se crean tablas para categorias
CREATE TABLE ParametrizacionCategoria
(
       pctId BIGINT IDENTITY NOT NULL,
       pctTipoAfiliado VARCHAR(30) NOT NULL,
       pctClasificacion VARCHAR(48),
       pctSMMLVDesde INT,
       pctSMMLVHasta INT,
       pctCategoria VARCHAR(50) NOT NULL,
       CONSTRAINT PK_ParametrizacionCategoria_pctId PRIMARY KEY (pctId)
);

INSERT INTO ParametrizacionCategoria (pctTipoAfiliado,pctClasificacion,pctSMMLVDesde,pctSMMLVHasta,pctCategoria)
VALUES
('TRABAJADOR_DEPENDIENTE',NULL,0,2,'A'),
('TRABAJADOR_DEPENDIENTE',NULL,2,4,'B'),
('TRABAJADOR_DEPENDIENTE',NULL,4,NULL,'C'),
('TRABAJADOR_DEPENDIENTE',NULL,0,2,'A'),
('PENSIONADO','FIDELIDAD_25_ANIOS',NULL,NULL,'A'),
('PENSIONADO','MENOS_1_5_SM_0_POR_CIENTO',NULL,NULL,'A'),
('PENSIONADO','MENOS_1_5_SM_2_POR_CIENTO',NULL,NULL,'A'),
('PENSIONADO','MENOS_1_5_SM_0_6_POR_CIENTO',NULL,NULL,'A'),
('PENSIONADO','MENOS_1_5_SM_0_POR_CIENTO',NULL,NULL,'A'),
('PENSIONADO','MAS_1_5_SM_0_6_POR_CIENTO',0,2,'A'),
('PENSIONADO','MAS_1_5_SM_0_6_POR_CIENTO',2,4,'B'),
('PENSIONADO','MAS_1_5_SM_0_6_POR_CIENTO',4,NULL,'C'),
('PENSIONADO','MAS_1_5_SM_2_POR_CIENTO',0,2,'A'),
('PENSIONADO','MAS_1_5_SM_2_POR_CIENTO',2,4,'B'),
('PENSIONADO','MAS_1_5_SM_2_POR_CIENTO',4,NULL,'C'),
('TRABAJADOR_INDEPENDIENTE','TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO',0,4,'B'),
('TRABAJADOR_INDEPENDIENTE','TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO',4,NULL,'C'),
('TRABAJADOR_INDEPENDIENTE','TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO',0,4,'B'),
('TRABAJADOR_INDEPENDIENTE','TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO',4,NULL,'C');
 

CREATE TABLE aud.Parametro_aud(
       prmId int NOT NULL,
       REV bigint NOT NULL,
       REVTYPE smallint NULL,
       prmNombre varchar(100) NULL,
       prmValor varchar(150) NULL,
       prmCargaInicio bit NULL,
       prmSubCategoriaParametro varchar(23) NULL,
       prmDescripcion varchar(250) NULL
);

CREATE TABLE dbo.Parametro(
       prmId int IDENTITY(1,1) NOT NULL,
       prmNombre varchar(100) NULL,
       prmValor varchar(150) NULL,
       prmCargaInicio bit NULL,
       prmSubCategoriaParametro varchar(23) NULL,
       prmDescripcion varchar(250) NOT NULL,
CONSTRAINT PK_Parametro_prmId PRIMARY KEY CLUSTERED
(
       prmId ASC
),

CONSTRAINT UK_Parametro_prmNombre UNIQUE NONCLUSTERED
(
       prmNombre ASC
)
);

CREATE TABLE CategoriaAfiliado
(
       ctaId BIGINT IDENTITY NOT NULL,
       ctaAfiliado BIGINT NOT NULL,
       ctaTipoAfiliado VARCHAR(30) NOT NULL,
       ctaClasificacion VARCHAR(48) NULL,
       ctaTotalIngresoMesada NUMERIC(19,5) NULL,
       ctaEstadoAfiliacion VARCHAR(8) NOT NULL,
       ctaFechaFinServicioSinAfiliacion DATETIME NULL,
       ctaCategoria VARCHAR(50) NOT NULL,
       ctaMotivoCambioCategoria VARCHAR(50) NULL,
       ctaFechaCambioCategoria DATETIME NOT NULL
       CONSTRAINT PK_CategoriaAfiliado_ctaId PRIMARY KEY CLUSTERED
(
       ctaId ASC
)
);

ALTER TABLE CategoriaAfiliado ADD CONSTRAINT FK_CategoriaAfiliado_ctaAfiliado FOREIGN KEY (ctaAfiliado) REFERENCES dbo.Afiliado (afiId);

CREATE TABLE CategoriaBeneficiario
(
       ctbId BIGINT IDENTITY NOT NULL,
       ctbBeneficiarioDetalle BIGINT NOT NULL,
       ctbTipoBeneficiario VARCHAR(30) NOT NULL,
       ctbCategoriaAfiliado BIGINT NOT NULL
       CONSTRAINT PK_CategoriaBeneficiario_ctbId PRIMARY KEY CLUSTERED
(
       ctbId ASC
)
);

ALTER TABLE CategoriaBeneficiario ADD CONSTRAINT FK_CategoriaBeneficiario_ctbBeneficiarioDetalle FOREIGN KEY (ctbBeneficiarioDetalle) REFERENCES dbo.BeneficiarioDetalle (bedId);
ALTER TABLE CategoriaBeneficiario ADD CONSTRAINT FK_CategoriaBeneficiario_ctbCategoriaAfiliado FOREIGN KEY (ctbCategoriaAfiliado) REFERENCES dbo.CategoriaAfiliado (ctaId);

--changeset arocha:04
--comment: Adiciona campo revTime
ALTER TABLE aud.Parametro_aud ADD revTime DATETIME;

--changeset arocha:05
--comment: Adiciona campo revTime
DROP TABLE Parametro;

CREATE TABLE dbo.Parametro(
       prmId int NOT NULL,
       prmNombre varchar(100) NULL,
       prmValor varchar(150) NULL,
       prmCargaInicio bit NULL,
       prmSubCategoriaParametro varchar(23) NULL,
       prmDescripcion varchar(250) NOT NULL
);

--changeset dsuesca:06
--comment: Se agregan campo eaiClaseIndependiente
ALTER TABLE EstadoAfiliacionPersonaIndependiente ADD eaiClaseIndependiente VARCHAR(50);