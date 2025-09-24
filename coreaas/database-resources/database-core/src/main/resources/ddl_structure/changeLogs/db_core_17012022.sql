--liquibase formatted sql

insert ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,pepFechaFin,pepFrecuencia,pepEstado)
values ('LIBERACION_PLANILLAS_BLOQUE9',null,null, '60',null, null,null,null,null,null,'INTERVALO','INACTIVO')