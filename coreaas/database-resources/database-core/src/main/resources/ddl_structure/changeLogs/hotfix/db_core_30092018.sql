--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de marcas de trazabilidad para reintegro por aportes
ALTER TABLE dbo.Empleador ADD empCanalReingreso varchar(21)
ALTER TABLE dbo.Empleador ADD empReferenciaAporteReingreso bigint