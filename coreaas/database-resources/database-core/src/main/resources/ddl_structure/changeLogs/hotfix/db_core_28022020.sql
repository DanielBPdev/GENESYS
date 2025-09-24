--liquibase formatted sql

--changeset jocampo:01
--comment:
INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepSegundos, pepFrecuencia, pepEstado)
VALUES ('CONCILIAR_PLANILLAS_PILA', 600, 'INTERVALO', 'ACTIVO');
