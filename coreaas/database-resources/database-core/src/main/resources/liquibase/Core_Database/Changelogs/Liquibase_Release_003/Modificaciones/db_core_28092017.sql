--liquibase formatted sql

--changeset jocorrea:01
--comment: Se modifica campo en la tabla Solicitud
ALTER TABLE Solicitud ALTER COLUMN solCanalRecepcion VARCHAR (21) NULL;