--liquibase formatted sql

--changeset arocha:01
--comment: Se modifica el tipo de dato para dos campos de la tabla staging.Cotizante y staging.RegistroDetallado
ALTER TABLE staging.Cotizante ALTER COLUMN cotPorcentajePagoAportes numeric(5,5);
ALTER TABLE staging.RegistroDetallado ALTER COLUMN redOUTPorcentajePagoAportes numeric(5,5);
