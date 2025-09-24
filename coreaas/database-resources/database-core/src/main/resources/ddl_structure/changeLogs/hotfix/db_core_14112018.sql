--liquibase formatted sql

--changeset mosorio:01
--comment: 
ALTER TABLE GeneracionReporteNormativo ALTER COLUMN gnrUsuarioGeneracion VARCHAR(200);