--liquibase formatted sql

--changeset jocorrea:01
--comment:Se eliminan registros en la tabla ValidacionProceso
DELETE FROM ValidacionProceso WHERE vapBloque IN ('NOVEDAD_CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_PRESENCIAL', 'NOVEDAD_CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_WEB') AND vapObjetoValidacion IN ('EMPLEADOR_DE_SERVICIO_DOMESTICO', 'PROPIEDAD_HORIZONTAL')
