--liquibase formatted sql

--changeset jvelandia:01
--comment: Se cambia tamanio de campo
ALTER TABLE PlantillaComunicado_aud ALTER COLUMN pcoCuerpo VARCHAR(8000);
ALTER TABLE PlantillaComunicado_aud ALTER COLUMN pcoMensaje VARCHAR(8000);
