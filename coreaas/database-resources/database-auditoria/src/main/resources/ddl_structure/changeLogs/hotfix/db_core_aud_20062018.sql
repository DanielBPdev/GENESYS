--liquibase formatted sql

--changeset fvasquez:01
--comment: Cambio tipo de dato columna dosVersionDocumento
ALTER TABLE DocumentoSoporte_aud ALTER COLUMN dosVersionDocumento varchar(10) NOT NULL;
