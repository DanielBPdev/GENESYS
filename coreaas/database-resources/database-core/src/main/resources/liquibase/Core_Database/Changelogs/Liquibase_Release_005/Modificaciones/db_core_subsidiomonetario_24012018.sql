--liquibase formatted sql

--changeset rlopez:01
--comment: Se adicionan campos en la tabla ParametrizacionCondicionesSubsidio
ALTER TABLE ParametrizacionCondicionesSubsidio ADD pcsDiasNovedadFallecimiento INT;