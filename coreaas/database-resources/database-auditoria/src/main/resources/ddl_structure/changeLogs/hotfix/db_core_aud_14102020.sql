--liquibase formatted sql

--changeset flopez:01
--comment: Agrega campo pofOferente a tabl PostulacionFovis_aud
IF NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name='PostulacionFOVIS_aud' AND column_name='pofOferente')
ALTER TABLE PostulacionFOVIS_aud ADD pofOferente BIGINT NULL;