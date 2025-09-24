--liquibase formatted sql

--changeset erhernandez:01
--comment:

update dbo.ValidatorParamValue set value='05' where id='2110012';