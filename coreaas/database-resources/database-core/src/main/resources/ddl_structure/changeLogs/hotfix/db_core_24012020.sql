--liquibase formatted sql

--changeset cmarin:01
--comment:
UPDATE validatorparamvalue 
SET validatorParameter_id = 211380 
WHERE validatorDefinition_id = 2110085 
AND id = 2111769;