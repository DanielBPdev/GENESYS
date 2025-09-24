--liquibase formatted sql

--changeset abaquero:01
--comment: Se actualizan registros en la tabla ValidatorDefinition
UPDATE ValidatorDefinition SET fieldDefinition_id=NULL,lineDefinition_id=5 WHERE id=2110110;
UPDATE ValidatorDefinition SET fieldDefinition_id=NULL,lineDefinition_id=10 WHERE id=2110214;

--changeset abaquero:02
--comment: Se actualiza registro en la tabla ValidatorDefinition
UPDATE ValidatorParamValue SET value='I3_23' WHERE id=2110124;