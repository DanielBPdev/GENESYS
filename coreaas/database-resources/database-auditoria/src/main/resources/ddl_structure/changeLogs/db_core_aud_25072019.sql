--liquibase formatted sql

--changeset jocorrea:01
--comment:
CREATE TABLE TipoDocumentoRequisito_aud (
tdrId BIGINT NOT NULL,
REV bigint NOT NULL,
REVTYPE smallint NULL,
tdrRequisito BIGINT NOT NULL,
tdrTipoDocumento VARCHAR(26) NOT NULL
);
