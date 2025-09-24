--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de ParametrizacionEjecucionProgramada 
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,pepFechaFin,pepFrecuencia) VALUES ('INACTIVAR_AUTOMATICAMENTE_BENEFICIO_LEY_1429_2010','00','00',NULL,NULL,'01','01',NULL,NULL,NULL,'ANUAL');
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,pepFechaFin,pepFrecuencia) VALUES ('INACTIVAR_AUTOMATICAMENTE_BENEFICIOS_LEY_590_2000','00','00',NULL,NULL,'01',NULL,NULL,NULL,NULL,'MENSUAL');
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,pepFechaFin,pepFrecuencia) VALUES ('INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_EMPLEADOR','00','00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'DIARIO');