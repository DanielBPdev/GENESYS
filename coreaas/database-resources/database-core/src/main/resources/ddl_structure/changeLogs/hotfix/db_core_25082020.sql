--liquibase formatted sql

--changeset erhernandez:01
--comment:
UPDATE ValidatorParamValue set value='TIPO_1' where id='2111142';