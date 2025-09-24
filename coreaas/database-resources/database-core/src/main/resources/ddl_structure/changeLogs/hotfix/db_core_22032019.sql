--liquibase formatted sql

--changeset jocorrea:01
--comment: 
ALTER TABLE MedioTarjeta ALTER COLUMN mtrSolicitudTarjeta VARCHAR(30);

--changeset jocorrea:02
--comment: 
ALTER TABLE aud.MedioTarjeta_aud ALTER COLUMN mtrSolicitudTarjeta VARCHAR(30);