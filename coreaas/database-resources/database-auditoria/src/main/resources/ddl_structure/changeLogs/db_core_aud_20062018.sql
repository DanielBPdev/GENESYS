--liquibase formatted sql

--changeset dsuesca:01
--comment: Ajuste tamanio columna bcaActividad
ALTER TABLE BitacoraCartera_aud ALTER COLUMN bcaActividad varchar(27);