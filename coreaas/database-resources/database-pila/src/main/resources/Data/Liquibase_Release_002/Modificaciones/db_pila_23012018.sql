--liquibase formatted sql

--changeset abaquero:01
--comment:Se adiciona campo en la tablas staging.Cotizante, staging.RegistroDetallado
ALTER TABLE staging.Cotizante ADD cotFechaRetiro date
ALTER TABLE staging.RegistroDetallado ADD redOUTFechaRetiroCotizante date