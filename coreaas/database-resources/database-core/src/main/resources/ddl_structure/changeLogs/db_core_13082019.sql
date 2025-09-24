--liquibase formatted sql

--changeset squintero:01
--comment:
UPDATE parametrizacionEjecucionProgramada SET pepSegundos = '1800000' WHERE pepProceso = 'DISPERSION_PAGOS';

INSERT INTO parametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepDiaSemana, pepDiaMes, pepMes, pepAnio, pepFechaInicio, pepFechaFin, pepFrecuencia, pepEstado)
VALUES ('DISPERSION_DESCUENTOS', null, null, '1800000', null, null, null, null, null, null, 'INTERVALO', 'ACTIVO');

--changeset clmarin:01
--comment:
ALTER TABLE agendacartera ADD agrEsVigente BIT;
ALTER TABLE actividadcartera ADD acrEsVigente BIT;
ALTER TABLE aud.agendacartera_aud ADD agrEsVigente BIT;
ALTER TABLE aud.actividadcartera_aud ADD acrEsVigente BIT;