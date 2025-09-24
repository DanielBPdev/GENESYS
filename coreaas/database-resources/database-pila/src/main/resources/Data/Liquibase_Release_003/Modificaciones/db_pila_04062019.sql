--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de valor modificado de tarifa para planillas de corrección
ALTER TABLE staging.RegistroDetallado ADD redOUTTarifaMod NUMERIC(5, 5)