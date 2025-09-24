--liquibase formatted sql

--changeset jocorrea:01
--comment:
ALTER TABLE PostulacionFOVIS_aud ALTER COLUMN pofSalarioAsignacion NUMERIC(19,5);