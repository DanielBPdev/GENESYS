--liquibase formatted sql

--changeset mamonroy:01
--comment: 
ALTER TABLE CertificadoEscolarBeneficiario ADD cebFechaCreacion DATE NULL;
ALTER TABLE aud.CertificadoEscolarBeneficiario_aud ADD cebFechaCreacion DATE NULL;