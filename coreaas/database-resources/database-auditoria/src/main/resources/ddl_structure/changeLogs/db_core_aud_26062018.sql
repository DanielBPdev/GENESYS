--liquibase formatted sql

--changeset clmarin:01
--comment: cambio de tipo de dato campo Solicitud_aud
ALTER TABLE Solicitud_aud ALTER COLUMN solResultadoProceso VARCHAR(30);