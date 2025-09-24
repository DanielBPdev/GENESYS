--liquibase formatted sql

--changeset jzambrano:01
--comment: Se borran campos de la tabla Beneficiario
ALTER TABLE Beneficiario DROP COLUMN benSalarioMensualBeneficiario;
ALTER TABLE Beneficiario DROP COLUMN benLabora;
ALTER TABLE Beneficiario DROP COLUMN benFechaVencimientoCertificadoEscolar;
ALTER TABLE Beneficiario DROP COLUMN benFechaRecepcionCertificadoEscolar;
ALTER TABLE Beneficiario DROP COLUMN benCertificadoEscolaridad;
