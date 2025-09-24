--liquibase formatted sql

--changeset dsuesca:01
--comment: ajuste reunion Alfonso
UPDATE VALIDATORPARAMVALUE
SET value = 'TIPO_2'
WHERE id = 2110790;