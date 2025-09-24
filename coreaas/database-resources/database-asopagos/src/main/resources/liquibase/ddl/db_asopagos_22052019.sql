--liquibase formatted sql

--changeset jroa:01
--comment: Se agrega campo pcsCalendarioPagoFallecimiento
ALTER TABLE ParametrizacionCondicionesSubsidio ADD pcsCalendarioPagoFallecimiento SMALLINT;