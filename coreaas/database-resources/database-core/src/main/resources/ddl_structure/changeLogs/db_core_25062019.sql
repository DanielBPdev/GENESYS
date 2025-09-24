--liquibase formatted sql

--changeset jroa:01
--comment:
ALTER TABLE RegistroSolicitudAnibol ADD rsaNumeroRadicacion VARCHAR(20);
