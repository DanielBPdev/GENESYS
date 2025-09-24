--liquibase formatted sql

--changeset mamonroy:01
--comment:
ALTER TABLE DocumentoSoporte_aud ADD dosTipoDocumentoSoporteFovis bigint NULL;