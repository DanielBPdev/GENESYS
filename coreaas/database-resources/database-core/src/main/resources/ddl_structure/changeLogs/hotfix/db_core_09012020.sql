--liquibase formatted sql

--changeset cmarin:01
--comment:
update ValidatorParamValue
set value = 'archivoIregistro2campo8'
where id = 2110272