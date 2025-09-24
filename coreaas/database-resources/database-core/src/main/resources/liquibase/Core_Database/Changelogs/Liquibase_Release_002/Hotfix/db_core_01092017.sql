--liquibase formatted sql

--changeset abaquero:01
--comment: Actualizacion de registros de la tabla ValidatorParamValue
UPDATE ValidatorParamValue SET value = 'N,F,U,J' WHERE id = 2110080;