--liquibase formatted sql

--changeset jocorrea:01
--comment: Se eliminan campos bedFechaRecepcionCertificadoEscolar y bedFechaVencimientoCertificadoEscolar
ALTER TABLE BeneficiarioDetalle DROP COLUMN bedFechaRecepcionCertificadoEscolar;
ALTER TABLE BeneficiarioDetalle DROP COLUMN bedFechaVencimientoCertificadoEscolar;
ALTER TABLE aud.BeneficiarioDetalle_aud DROP COLUMN bedFechaRecepcionCertificadoEscolar;
ALTER TABLE aud.BeneficiarioDetalle_aud DROP COLUMN bedFechaVencimientoCertificadoEscolar;
