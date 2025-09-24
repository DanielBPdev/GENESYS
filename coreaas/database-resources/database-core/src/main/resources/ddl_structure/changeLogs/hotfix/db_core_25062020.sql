--liquibase formatted sql

--changeset cmarin:01
--comment: 
update FieldLoadCatalog
set [description] = 'Tipo Salario'
where id = 2110128;

update ValidatorDefinition
set validatorCatalog_id = 211003
where id = 2110090;

delete ValidatorParamValue
where id = 2110453;

update ValidatorParamValue
set validatorParameter_id = 211010
where id = 2110451;

update ValidatorParamValue
set validatorParameter_id = 211011, value = 'X,F,V'
where id = 2110452;

update ValidatorParamValue
set validatorParameter_id = 211012
where id = 2110454;

update ValidatorParamValue
set validatorParameter_id = 211013
where id = 2110455;