--liquibase formatted sql

--changeset jvelandia:01
--comment: Se agrega campo cadAgregadoManual tabla CarteraDependiente_aud
ALTER TABLE CarteraDependiente_aud ADD cadAgregadoManual BIGINT;