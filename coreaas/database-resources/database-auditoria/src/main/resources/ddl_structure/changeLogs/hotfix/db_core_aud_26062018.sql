--liquibase formatted sql

--changeset dsuesca:01
--comment: Se cambia tipo de datos de tabla Solicitud_aud
ALTER TABLE Solicitud_aud ALTER COLUMN solResultadoProceso VARCHAR(30);