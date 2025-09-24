--liquibase formatted sql

--changeset jocampo:01
--comment: Se modifica el campo de la tabla Beneficiario_aud
ALTER TABLE Beneficiario_aud ALTER COLUMN benRolAfiliado BIGINT NULL;