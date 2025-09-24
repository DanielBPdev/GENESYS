--liquibase formatted sql

--changeset abaquero:01
--comment: Actualizaciones tabla ValidatorParamValue
UPDATE ValidatorParamValue SET value='I17' WHERE id=2110044;
UPDATE ValidatorParamValue SET value='A12' WHERE id=2110693;
UPDATE ValidatorParamValue SET value='AP12' WHERE id=2110848;
UPDATE ValidatorDefinition SET lineDefinition_id=9 WHERE id=2110345;