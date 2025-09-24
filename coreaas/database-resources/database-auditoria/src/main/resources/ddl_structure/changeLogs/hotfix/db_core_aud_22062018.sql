--liquibase formatted sql

--changeset dsuesca:01
--comment: Se cambia tipo de datos de tablas que tengan version de documento de alfresco
ALTER TABLE DocumentoSoporte_aud ALTER COLUMN dosVersionDocumento VARCHAR(20);
