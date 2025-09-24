--liquibase formatted sql

--changeset clmarin:01
--comment: cambio tipo de dato campo sciResumen
ALTER TABLE SolicitudCierreRecaudo_aud ALTER COLUMN sciResumen text;