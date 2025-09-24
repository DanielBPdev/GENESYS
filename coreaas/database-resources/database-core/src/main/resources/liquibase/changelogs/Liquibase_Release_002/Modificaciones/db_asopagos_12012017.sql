--liquibase formatted sql

--changeset  mgiraldo:01 
--comment: cambio tipo de dato reqDescripcion Requisito
ALTER TABLE Requisito ALTER COLUMN reqDescripcion VARCHAR(170);