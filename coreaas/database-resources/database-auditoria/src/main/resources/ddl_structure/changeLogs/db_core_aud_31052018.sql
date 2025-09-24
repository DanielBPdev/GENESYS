--liquibase formatted sql

--changeset jocorrea:01
--comment: Se agrega campo epsIdProceso
ALTER TABLE EjecucionProcesoAsincrono_aud ADD epsIdProceso bigint;