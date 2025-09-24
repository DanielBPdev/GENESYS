--liquibase formatted sql

--changeset dsuesca:01
--comment: Se elimina contraint FK_Certificado_aud_REV
ALTER TABLE Certificado_aud DROP CONSTRAINT FK_Certificado_aud_REV;