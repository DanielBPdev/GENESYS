--liquibase formatted sql

--changeset mosorio:01
--comment: Se cambia tipo de datos de campo rpaDocumentoSoporte  
ALTER TABLE RetiroPersonaAutorizadaCobroSubsidio ALTER COLUMN rpaDocumentoSoporte BIGINT;