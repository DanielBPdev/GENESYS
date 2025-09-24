--liquibase formatted sql

--changeset mamonroy:01
--comment:
UPDATE ValidacionProceso
SET vapInversa = 0
WHERE vapBloque like '%_PILA'
AND vapInversa = 1
AND vapValidacion  IN ('VALIDACION_PERSONA_DEPENDIENTE_INACTIVO','VALIDACION_PERSONA_INDEPENDIENTE_INACTIVO','VALIDACION_PERSONA_PENSIONADO_INACTIVO');
