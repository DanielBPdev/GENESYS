--liquibase formatted sql

--changeset jocampo:01
--comment: Se modifica el campo de la tabla Beneficiario
ALTER TABLE Beneficiario ALTER COLUMN benRolAfiliado BIGINT NULL;