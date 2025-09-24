--liquibase formatted sql

--changeset jocorrea:01
--comment: Se modifica campo en la tabla Solicitud_aud
ALTER TABLE Solicitud_aud ALTER COLUMN solCanalRecepcion VARCHAR (21) NULL;