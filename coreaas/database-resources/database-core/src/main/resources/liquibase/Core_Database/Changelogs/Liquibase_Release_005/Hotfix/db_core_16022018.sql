--liquibase formatted sql

--changeset jocorrea:01
--comment:Eliminacion de registros en la tabla ValidacionProceso
DELETE FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_ACTUALIZACION_VACACIONES_TRABAJADOR_DEPENDIENTE';

--changeset jusanchez:02
--comment:Actualizacion de registro en la tabla FieldDefinitionLoad
UPDATE FieldDefinitionLoad set required=0 WHERE lineDefinition_id=1212 AND id IN (100000,100001,100005,100023,100024,100025,100026,100027);
