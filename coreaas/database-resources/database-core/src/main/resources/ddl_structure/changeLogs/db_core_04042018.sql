--liquibase formatted sql

--changeset jocorrea:01
--comment:Actualizacion tabla ValidacionProceso
UPDATE ValidacionProceso SET vapBloque = 'NOVEDAD_CAMBIOS_CONDICION_ESPECIAL_HERMANO' WHERE vapBloque = 'NOVEDAD_CAMBIOS_CONDICION_ESPECIAL_HERMANO ';
