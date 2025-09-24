--liquibase formatted sql

--changeset squintero:01
--comment:
INSERT INTO parametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepDiaSemana, pepDiaMes, pepMes, pepAnio, pepFechaInicio, pepFechaFin, pepFrecuencia, pepEstado)
VALUES ('DISPERSION_LIQUIDACION_FALLECIMIENTO', null, null, '1800', null, null, null, null, null, null, 'INTERVALO', 'INACTIVO');