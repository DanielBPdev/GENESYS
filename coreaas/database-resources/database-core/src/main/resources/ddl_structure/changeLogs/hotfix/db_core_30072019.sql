--liquibase formatted sql

--changeset jroa:01
--comment:
INSERT INTO parametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepDiaSemana, pepDiaMes, pepMes, pepAnio, pepFechaInicio, pepFechaFin, pepFrecuencia, pepEstado)
VALUES ('PLANILLAS_PILA', null, null, '180000', null, null, null, null, null, null, 'INTERVALO', 'ACTIVO');