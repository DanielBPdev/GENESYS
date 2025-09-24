--liquibase formatted sql

--changeset fvasquez:01
--comment:Se modifica tamaño campo de la tabla DocumentoSoporte
ALTER TABLE DocumentoSoporte ALTER COLUMN dosVersionDocumento VARCHAR(6) NOT NULL;