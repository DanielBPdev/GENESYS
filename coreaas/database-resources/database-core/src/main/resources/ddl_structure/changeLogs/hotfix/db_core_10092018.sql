--liquibase formatted sql

--changeset jocorrea:01
--comment: Actualizacion tabla ValidacionProceso
UPDATE ValidacionProceso SET vapValidacion = 'VALIDACION_BENEFICIARIO_ACTIVO_DISTINTO_AFILIADO_47_FOVIS' WHERE vapBloque = 'NOVEDAD_CAMBIO_OTROS_DATOS' AND vapValidacion = 'VALIDACION_BENEFICIARIO_ACTIVO';
