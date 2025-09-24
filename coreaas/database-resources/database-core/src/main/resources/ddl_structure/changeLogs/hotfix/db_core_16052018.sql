--liquibase formatted sql

--changeset fvasquez:01
--comment: Creacion tabla RegistroSolicitudAnibol
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepFrecuencia,pepEstado) VALUES('ASIGNACION_ACCION_LC4C','00','50','00','DIARIO','ACTIVO');
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepFrecuencia,pepEstado) VALUES('ASIGNACION_ACCION_LC5C','00','50','00','DIARIO','ACTIVO');