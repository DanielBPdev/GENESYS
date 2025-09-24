--liquibase formatted sql

--changeset abaquero:01
--comment: Actualizacion tabla ValidatorParamValue
UPDATE ValidatorParamValue SET value='S' WHERE id=2111652;
