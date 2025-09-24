--liquibase formatted sql

--changeset mamonroy:01
--comment: 
ALTER TABLE CertificadoEscolarBeneficiario_aud ADD cebFechaCreacion DATE NULL;