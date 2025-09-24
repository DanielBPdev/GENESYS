--liquibase formatted sql

--changeset ecastano:01
--comment:Se adiciona campo en la tabla ActaAsignacionFOVIS_aud
ALTER TABLE ActaAsignacionFOVIS_aud ADD aafFechaPublicacion DATETIME NULL;
