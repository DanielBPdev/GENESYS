--liquibase formatted sql

--changeset jocorrea:01
--comment:Se eliminan registros de la tabla ValidacionProceso
DELETE FROM ValidacionProceso WHERE vapBloque IN ('INACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL', 'INACTIVAR_BENEFICIOS_LEY_590_2000_WEB') AND vapValidacion = 'VALIDACION_EMPLEADOR_NO_CUBIERTO_BENEFICIO_590';

--changeset jzambrano:02
--comment: Se eliminan registros de la tabla ValidacionProceso
DELETE FROM ValidacionProceso WHERE vapValidacion = 'VALIDACION_PERSONA_INCLUIDA_SOLICITUD';