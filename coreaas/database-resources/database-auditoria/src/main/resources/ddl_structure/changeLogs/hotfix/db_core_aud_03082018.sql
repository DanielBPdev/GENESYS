--liquibase formatted sql

--changeset jocorrea:01
--comment: Se agrega campo epsProcesoCancelado
ALTER TABLE EjecucionProcesoAsincrono_aud ADD epsProcesoCancelado BIT;