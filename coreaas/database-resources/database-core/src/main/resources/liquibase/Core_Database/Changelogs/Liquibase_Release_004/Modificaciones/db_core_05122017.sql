--liquibase formatted sql

--changeset jzambrano:01
--comment:Se eliminan campos en la tabla Beneficiario_aud
ALTER TABLE Beneficiario DROP COLUMN benFechaReporteInvalidez;
ALTER TABLE Beneficiario DROP COLUMN benInvalidez;
ALTER TABLE Beneficiario DROP COLUMN benComentariosInvalidez;