--liquibase formatted sql

--changeset jzambrano:01
--comment: Se actualiza campo en la tabla ValidacionProceso
UPDATE ValidacionProceso SET vapOrden = 2 WHERE vapBloque='111-059-1' AND vapValidacion='VALIDACION_EMPLEADOR_BD_CORE';

--changeset jocorrea:02
--comment: Se adiciona campo en la tabla Parametro
ALTER TABLE Parametro ADD prmDescripcion varchar(100) NULL;

--changeset abaquero:03
--comment: Se actualiza registro de la tabla ValidatorParamValue
UPDATE ValidatorParamValue SET value = '6' WHERE validatorDefinition_id = 2110206 AND validatorParameter_id = 211238;

--changeset jusanchez:04
--comment: Se modifica tamaño del prmDescripcion campo en la tabla Parametro
ALTER TABLE Parametro ALTER COLUMN prmDescripcion varchar(250) NULL;