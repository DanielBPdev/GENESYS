--liquibase formatted sql

--changeset abaquero:01
--comment: Se resetea la marca de proceso de temporales de aportes y novedades
UPDATE temNovedad SET tenEnProceso = 0
UPDATE temAporte SET temEnProceso = 0