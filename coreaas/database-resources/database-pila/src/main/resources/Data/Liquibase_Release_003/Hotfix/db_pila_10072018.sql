--liquibase formatted sql

--changeset alquintero:01
--comment:Se cambia longitud campo pevMensajeError
ALTER TABLE PilaErrorValidacionLog ALTER COLUMN pevMensajeError varchar(4000);