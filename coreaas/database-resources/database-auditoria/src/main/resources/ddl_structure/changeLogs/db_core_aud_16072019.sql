--liquibase formatted sql

--changeset jocorrea:01
--comment:
ALTER TABLE PostulacionFOVIS_aud ADD pofJsonPostulacion VARCHAR(MAX) NULL;