--liquibase formatted sql

--changeset jocorrea:01
--comment:
ALTER TABLE Solicitud_aud ALTER COLUMN solNumeroRadicacion varchar(20);