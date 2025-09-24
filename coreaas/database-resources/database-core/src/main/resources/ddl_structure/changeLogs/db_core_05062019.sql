--liquibase formatted sql

--changeset jocorrea:01
--comment: 
ALTER TABLE Solicitud ALTER COLUMN solNumeroRadicacion varchar(20);
ALTER TABLE aud.Solicitud_aud ALTER COLUMN solNumeroRadicacion varchar(20);
