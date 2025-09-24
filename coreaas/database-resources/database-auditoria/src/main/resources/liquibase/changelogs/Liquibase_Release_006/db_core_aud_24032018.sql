--liquibase formatted sql

--changeset borozco:01
--comment:Se modifica campo en la tabla BitacoraCartera_aud
ALTER TABLE BitacoraCartera_aud ALTER COLUMN bcaActividad VARCHAR(22) NULL;
