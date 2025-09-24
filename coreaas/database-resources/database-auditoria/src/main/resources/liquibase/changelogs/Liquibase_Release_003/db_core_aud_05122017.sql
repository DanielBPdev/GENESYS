--liquibase formatted sql

--changeset jzambrano:01
--comment:Se eliminan campos en la tabla Beneficiario_aud
ALTER TABLE Beneficiario_aud DROP COLUMN benFechaReporteInvalidez;
ALTER TABLE Beneficiario_aud DROP COLUMN benInvalidez;
ALTER TABLE Beneficiario_aud DROP COLUMN benComentariosInvalidez;