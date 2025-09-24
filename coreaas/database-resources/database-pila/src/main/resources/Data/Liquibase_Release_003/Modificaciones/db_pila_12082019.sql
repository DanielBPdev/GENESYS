--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de campo para la peridicidad de pago de aportes en el RegistroDetallado y Cotizante
ALTER TABLE staging.Cotizante ADD cotPeriodicidad VARCHAR(11)
ALTER TABLE staging.RegistroDetallado ADD redOUTPeriodicidad VARCHAR(11)