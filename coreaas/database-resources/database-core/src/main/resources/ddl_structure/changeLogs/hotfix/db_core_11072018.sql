--liquibase formatted sql

--changeset abaquero:01
--comment: Actualizacion tabla ParametrizacionMetodoAsignacion
UPDATE ValidatorDefinition SET state=1 WHERE id=2110302;
UPDATE ValidatorDefinition SET state=1 WHERE id=2110303;
