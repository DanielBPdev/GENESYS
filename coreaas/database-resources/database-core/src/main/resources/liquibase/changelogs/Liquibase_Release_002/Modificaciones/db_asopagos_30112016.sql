--liquibase formatted sql

--changeset halzate:02
--comment: Adición de campos vcoDescripcion a la tabla VariableComunicado
ALTER TABLE VariableComunicado ALTER COLUMN vcoDescripcion VARCHAR(200);