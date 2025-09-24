--liquibase formatted sql

--changeset fvasquez:01
--comment: Se agrega columna cadDeudaReal a tabla CarteraDependiente_aud
ALTER TABLE CarteraDependiente_aud add cadDeudaReal NUMERIC(19,5);
