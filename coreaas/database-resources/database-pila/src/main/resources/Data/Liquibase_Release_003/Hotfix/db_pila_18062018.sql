--liquibase formatted sql

--changeset abaquero:01
--comment:Se cambia tamanio de campos pipVersionDocumento y pioVersionDocumento
ALTER TABLE PilaIndicePlanilla ALTER COLUMN pipVersionDocumento VARCHAR(20);
ALTER TABLE PilaIndicePlanillaOF ALTER COLUMN pioVersionDocumento VARCHAR(20);