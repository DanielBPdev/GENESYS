--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de campos de canal de recepción de reintegro por aportes o afiliación
ALTER TABLE RolAfiliado_aud ADD roaCanalReingreso varchar(21)
ALTER TABLE RolAfiliado_aud ADD roaReferenciaAporteReingreso bigint
ALTER TABLE RolAfiliado_aud ADD roaReferenciaSolicitudReingreso bigint