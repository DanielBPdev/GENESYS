--liquibase formatted sql

--changeset cmarin:01
--comment:
UPDATE ValidatorParamValue
SET value = 'TIPO_1'
where id = 2111718;