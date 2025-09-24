--liquibase formatted sql

--changeset atoro:01
--comment: Se resuelve incidencia # 0225009
UPDATE ValidacionProceso SET vapObjetoValidacion='HIJO_ADOPTIVO' WHERE vapValidacion='VALIDACION_PERSONA_HIJO_BIOLOGICO' AND vapBloque='121-107-1';