--liquibase formatted sql

--changeset jzambrano:01
--comment:Actualizacion tabla ValidacionProceso
UPDATE ValidacionProceso SET vapProceso='AFILIACION_DEPENDIENTE_WEB' WHERE vapBloque LIKE '122-361-%';