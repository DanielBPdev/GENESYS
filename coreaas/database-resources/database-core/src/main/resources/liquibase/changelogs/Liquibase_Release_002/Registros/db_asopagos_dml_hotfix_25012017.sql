--liquibase formatted sql

--changeset jcamargo:01 
--comment: Eliminación dato validación proceso
delete from ValidacionProceso where vapBloque='112-110-1' and vapValidacion='VALIDACION_CUENTA_WEB';
