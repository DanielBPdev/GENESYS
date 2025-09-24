--liquibase formatted sql

--changeset jocorrea:01
--comment:
ALTER TABLE PostulacionFOVIS ADD pofJsonPostulacion VARCHAR(MAX) NULL;
ALTER TABLE aud.PostulacionFOVIS_aud ADD pofJsonPostulacion VARCHAR(MAX) NULL;