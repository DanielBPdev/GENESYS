--liquibase formatted sql

--changeset abaquero:01
--comment: cambio tipo de dato campo pevMensajeError
ALTER TABLE PilaErrorValidacionLog ALTER COLUMN pevMensajeError varchar(2000);