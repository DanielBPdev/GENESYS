--liquibase formatted sql

--changeset dsuesca:01
--comment: 
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
