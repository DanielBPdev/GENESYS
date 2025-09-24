--liquibase formatted sql

--changeSET cmarin:01
--comment: CC Relajar validaciones PILA
UPDATE ValidatorParamValue
SET value = 'TIPO_1'
WHERE id = 2111134;

UPDATE ValidatorParamValue
SET value = 'TIPO_1'
WHERE id =2110643;

UPDATE ValidatorParamValue
SET value = 'CC,CE,TI,RC,PA,CD,SC,PE'
WHERE id = 2110268;

UPDATE ValidatorParamValue
SET value = 'CE,PA,CD,SC,PE'
WHERE id = 2110262;