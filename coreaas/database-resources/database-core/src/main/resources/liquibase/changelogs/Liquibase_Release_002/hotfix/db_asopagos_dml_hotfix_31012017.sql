--liquibase formatted sql

--changeset jcamargo:01 
--comment: hotfix en la HU de reintegros en la validaciones que se realizan
delete from ValidacionProceso where vapBloque = 'REINTEGRO' and vapValidacion='VALIDACION_EMPLEADOR_BD_CORE';