--liquibase formatted sql

--changeset jvelandia:01
--comment: Se cambia tamanio de campo
ALTER TABLE PLANTILLACOMUNICADO ALTER COLUMN pcoCuerpo VARCHAR(8000);
ALTER TABLE PLANTILLACOMUNICADO ALTER COLUMN pcoMensaje VARCHAR(8000);

--changeset jvelandia:02
--comment: Se cambia tamanio de campo
ALTER TABLE aud.PlantillaComunicado_aud ALTER COLUMN pcoCuerpo VARCHAR(8000);
ALTER TABLE aud.PlantillaComunicado_aud ALTER COLUMN pcoMensaje VARCHAR(8000);