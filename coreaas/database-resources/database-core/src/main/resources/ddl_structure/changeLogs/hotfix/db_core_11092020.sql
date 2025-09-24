--liquibase formatted sql

--changeset mamonroy:01
--comment:
ALTER TABLE DatosRegistroValidacion ADD drvFechaValidacion DATETIME2;