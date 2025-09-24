--liquibase formatted sql

--changeset fvasquez:01
--comment: Adición de campos acrCartera y agrCartera
ALTER TABLE BeneficiarioDetalle_aud DROP COLUMN bedFechaRecepcionCertificadoEscolar;
ALTER TABLE BeneficiarioDetalle_aud DROP COLUMN bedFechaVencimientoCertificadoEscolar;