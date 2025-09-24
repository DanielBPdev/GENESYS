--liquibase formatted sql

--changeset dsuesca:01
--comment: Cambio tipo de datos diferentes de core a core_aud
ALTER TABLE Beneficiario_aud DROP COLUMN benCertificadoEscolaridad;
ALTER TABLE Beneficiario_aud DROP COLUMN benEstadoBeneficiarioCaja;
ALTER TABLE Beneficiario_aud DROP COLUMN benFechaRecepcionCertificadoEscolar;
ALTER TABLE Beneficiario_aud DROP COLUMN benFechaVencimientoCertificadoEscolar;
ALTER TABLE Beneficiario_aud DROP COLUMN benLabora;
ALTER TABLE Beneficiario_aud DROP COLUMN benSalarioMensualBeneficiario;

