--liquibase formatted sql

--changeset squintero:01
--comment:
INSERT INTO parametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepDiaSemana, pepDiaMes, pepMes, pepAnio, pepFechaInicio, pepFechaFin, pepFrecuencia, pepEstado)
VALUES ('DISPERSION_PAGOS_ORIGEN_CAMBIO_MEDIO_PAGO', null, null, '1800000', null, null, null, null, null, null, 'INTERVALO', 'ACTIVO');