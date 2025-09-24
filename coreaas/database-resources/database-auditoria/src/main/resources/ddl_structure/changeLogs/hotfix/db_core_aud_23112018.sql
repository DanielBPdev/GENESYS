--liquibase formatted sql

--changeset fvasquez:01
--comment: 
ALTER TABLE bitacoracartera_aud ALTER column bcaActividad VARCHAR(36) NULL;
ALTER TABLE bitacoracartera_aud ALTER column bcaResultado VARCHAR(33) NULL;
