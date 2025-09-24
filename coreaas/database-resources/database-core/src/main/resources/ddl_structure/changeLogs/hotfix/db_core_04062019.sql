--liquibase formatted sql

--changeset jocorrea:01
--comment: 
ALTER TABLE PostulacionFOVIS ALTER COLUMN pofSalarioAsignacion NUMERIC(19,5);
ALTER TABLE aud.PostulacionFOVIS_aud ALTER COLUMN pofSalarioAsignacion NUMERIC(19,5);