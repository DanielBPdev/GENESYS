--liquibase formatted sql

--changeset abaquero:01
--comment: Se adiciona campo en la tabla staging.RegistroDetallado
ALTER TABLE staging.RegistroDetallado ADD redOUTValorMoraCotizante numeric(19,5);