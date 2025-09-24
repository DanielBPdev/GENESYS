--liquibase formatted sql

--changeset abaquero:01
--comment: Ampliación de los campos de la BitacoraCartera_aud
ALTER TABLE BitacoraCartera_aud ALTER COLUMN bcaActividad VARCHAR(50)
ALTER TABLE BitacoraCartera_aud ALTER COLUMN bcaResultado VARCHAR(40)
ALTER TABLE BitacoraCartera_aud ALTER COLUMN bcaMedio VARCHAR(20)
ALTER TABLE BitacoraCartera_aud ALTER COLUMN bcaTipoSolicitante VARCHAR(20)