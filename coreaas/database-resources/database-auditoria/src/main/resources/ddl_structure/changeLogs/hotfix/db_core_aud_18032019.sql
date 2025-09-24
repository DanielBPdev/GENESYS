--liquibase formatted sql

--changeset jocorrea:01
--comment: Se agrega campo esoOrigen
ALTER TABLE EscalamientoSolicitud_aud ADD esoOrigen VARCHAR(5);