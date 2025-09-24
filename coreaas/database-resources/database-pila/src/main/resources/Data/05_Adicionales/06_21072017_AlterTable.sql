--liquibase formatted sql

--changeset hhernandez:01
--comment: Se adicionan columnas a las tablas staging.Cotizante, staging.RegistroDetallado y 
ALTER TABLE staging.Cotizante ADD cotFechaFallecido date NULL;
ALTER TABLE staging.RegistroDetallado ADD redOUTFechaFallecimiento date NULL;
ALTER TABLE dbo.TemCotizante ADD tctEsFallecido bit NULL;