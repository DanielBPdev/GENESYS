--liquibase formatted sql

--changeset borozco:01
--comment:Actualizacion de registros de la tabla ValidacionProceso
UPDATE ValidacionProceso SET vapInversa = 1 WHERE vapBloque ='ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL' and vapValidacion='VALIDACION_PERSONA_DEPENDIENTE_ACTIVO' and vapProceso='NOVEDADES_PERSONAS_PRESENCIAL';
UPDATE ValidacionProceso SET vapInversa = 1 WHERE vapBloque ='ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL' and vapValidacion='VALIDACION_PERSONA_INDEPENDIENTE_ACTIVO' and vapProceso='NOVEDADES_PERSONAS_PRESENCIAL';
