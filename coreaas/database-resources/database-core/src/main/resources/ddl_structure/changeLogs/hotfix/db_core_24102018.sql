--liquibase formatted sql

--changeset abaquero:01
--comment: Cambio de tipo de inconsistencia para validador de actividad económica por CC 0244548
UPDATE dbo.ValidatorParamValue SET value = 'TIPO_0' WHERE id = 2110739