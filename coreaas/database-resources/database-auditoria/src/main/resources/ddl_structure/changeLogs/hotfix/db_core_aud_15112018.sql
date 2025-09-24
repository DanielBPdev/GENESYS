--liquibase formatted sql

--changeset jocorrea:01
--comment: 
ALTER TABLE PostulacionFovis_aud ALTER COLUMN pofInfoAsignacion VARCHAR(MAX);