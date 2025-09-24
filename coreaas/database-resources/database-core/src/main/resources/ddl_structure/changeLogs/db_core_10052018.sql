--liquibase formatted sql

--changeset aquintero:01
--comment: Correcion validacion
UPDATE ValidacionProceso SET vapInversa = 1 WHERE vapObjetoValidacion = 'HOGAR' AND vapProceso = 'NOVEDADES_FOVIS_REGULAR' AND vapValidacion = 'VALIDACION_PERSONA_INHABILITADA_SUBSIDIO_FOVIS' AND vapBloque = 'NOVEDAD_LEVANTAR_INHABILIDAD_SANCION_HOGAR';