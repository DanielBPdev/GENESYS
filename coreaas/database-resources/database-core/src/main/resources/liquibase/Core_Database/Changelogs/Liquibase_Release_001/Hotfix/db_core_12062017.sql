--liquibase formatted sql

--changeset atoro:01
--comment: Se resuelve incidencia # 0224638
DELETE FROM ValidacionProceso WHERE vapvalidacion='VALIDACION_DESACTIVACION_BENEFICIARIO';