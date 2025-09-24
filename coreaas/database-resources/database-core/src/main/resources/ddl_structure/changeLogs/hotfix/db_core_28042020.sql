--liquibase formatted sql

--changeset cmarin:01
--comment:

UPDATE ValidatorParamValue
set value = null
from ValidatorParamValue vpv
where vpv.id in (2111559,2111581,2111593)