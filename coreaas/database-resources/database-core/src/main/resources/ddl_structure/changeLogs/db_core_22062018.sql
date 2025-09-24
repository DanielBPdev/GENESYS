--liquibase formatted sql

--changeset fvasquez:01
--comment: Ajuste tamanio columna bcaActividad
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepFrecuencia,pepEstado) VALUES ('EXCLUSION_CARTERA','00','10','00','DIARIO','ACTIVO');