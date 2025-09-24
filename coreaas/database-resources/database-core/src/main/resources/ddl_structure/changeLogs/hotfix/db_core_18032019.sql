--liquibase formatted sql

--changeset jocorrea:01
--comment: 
ALTER TABLE EscalamientoSolicitud ADD esoOrigen VARCHAR(5);
ALTER TABLE aud.EscalamientoSolicitud_aud ADD esoOrigen VARCHAR(5);