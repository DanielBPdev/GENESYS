--liquibase formatted sql

--changeset jocorrea:01
--comment: 
ALTER TABLE MedioTarjeta_aud ALTER COLUMN mtrSolicitudTarjeta VARCHAR(30);