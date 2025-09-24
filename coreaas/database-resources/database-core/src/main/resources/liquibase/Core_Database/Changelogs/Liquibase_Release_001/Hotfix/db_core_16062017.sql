--liquibase formatted sql

--changeset squintero:01
--comment: Se resuelve incidencia # 0224316
UPDATE ValidacionProceso SET vapInversa = 1 WHERE vapProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' AND vapBloque = 'CONYUGE_LABORA_PRESENCIAL' AND vapValidacion = 'VALIDACION_AFILIADO_CON_CONYUGE_ACTIVO' AND vapObjetoValidacion = 'CONYUGE';
UPDATE ValidacionProceso SET vapInversa = 1 WHERE vapProceso = 'NOVEDADES_PERSONAS_WEB' AND vapBloque = 'CONYUGE_LABORA_WEB' AND vapValidacion = 'VALIDACION_AFILIADO_CON_CONYUGE_ACTIVO' AND vapObjetoValidacion = 'CONYUGE';
UPDATE ValidacionProceso SET vapInversa = 1 WHERE vapProceso = 'NOVEDADES_DEPENDIENTE_WEB' AND vapBloque = 'CONYUGE_LABORA_DEPWEB' AND vapValidacion = 'VALIDACION_AFILIADO_CON_CONYUGE_ACTIVO' AND vapObjetoValidacion = 'CONYUGE';
