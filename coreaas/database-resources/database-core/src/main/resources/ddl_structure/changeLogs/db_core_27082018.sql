--liquibase formatted sql

--changeset fvasquez:01
--comment: Se agrega columna cadAgregadoManual tabla CarteraDependiente
ALTER TABLE CarteraDependiente ADD cadAgregadoManual BIGINT;
ALTER TABLE aud.CarteraDependiente_aud ADD cadAgregadoManual BIGINT;