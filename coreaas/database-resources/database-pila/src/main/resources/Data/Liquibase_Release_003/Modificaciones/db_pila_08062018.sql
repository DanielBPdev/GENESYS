--liquibase formatted sql

--changeset abaquero:01
--comment: INSERT tablas
ALTER TABLE staging.RegistroDetallado ADD redOUTValorMoraCotizanteMod numeric(19,5);