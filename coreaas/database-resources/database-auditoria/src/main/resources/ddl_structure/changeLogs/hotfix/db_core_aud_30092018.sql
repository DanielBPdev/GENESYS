--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de marcas de trazabilidad para reintegro por aportes
ALTER TABLE Empleador_aud ADD empCanalReingreso varchar(21)
ALTER TABLE Empleador_aud ADD empReferenciaAporteReingreso bigint