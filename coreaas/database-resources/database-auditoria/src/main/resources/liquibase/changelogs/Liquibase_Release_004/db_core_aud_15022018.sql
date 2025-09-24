--liquibase formatted sql

--changeset fvasquez:01
--comment:Se modifica tamaño campo de la tabla DocumentoSoporte_aud
ALTER TABLE DocumentoSoporte_aud ALTER COLUMN dosVersionDocumento VARCHAR(6) NOT NULL;