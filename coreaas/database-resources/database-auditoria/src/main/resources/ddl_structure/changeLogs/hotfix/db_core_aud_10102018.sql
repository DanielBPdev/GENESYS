--liquibase formatted sql

--changeset jocorrea:01
--comment: Se crea tabla CerficadoEscolarBeneficiario_aud
CREATE TABLE CerficadoEscolarBeneficiario_aud (
cebId bigint NOT NULL,
REV bigint NOT NULL,
REVTYPE smallint,
cebBeneficiarioDetalle bigint NOT NULL,
cebFechaRecepcion date NULL,
cebFechaVencimiento date NULL
);
