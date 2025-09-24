--liquibase formatted sql

--changeset abaquero:01
--comment: 
UPDATE dbo.ValidatorDefinition SET state=0 WHERE id=2110301;
UPDATE dbo.ValidatorDefinition SET state=0 WHERE id=2110302;
UPDATE dbo.ValidatorDefinition SET state=0 WHERE id=2110303;
UPDATE dbo.ValidatorDefinition SET state=0 WHERE id=2110345;


