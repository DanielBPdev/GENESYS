--liquibase formatted sql

--changeset jocorrea:01
--comment: Se agegan campos pedPersonaPadre y pedPersonaMadre
ALTER TABLE PersonaDetalle_aud ADD pedPersonaPadre BIGINT;
ALTER TABLE PersonaDetalle_aud ADD pedPersonaMadre BIGINT;