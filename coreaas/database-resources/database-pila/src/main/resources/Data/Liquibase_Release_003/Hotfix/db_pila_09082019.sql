--liquibase formatted sql

--changeset abaquero:01
--comment: Se agregan campos de control en temporales para actualización por HU-211-480
ALTER TABLE TemAporteActualizado ADD taaMarcaAporteManual BIT
ALTER TABLE TemAporteActualizado ADD taaRegistroModificador BIGINT