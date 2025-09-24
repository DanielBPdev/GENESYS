--liquibase formatted sql

--changeset fvasquez:01
--comment: Insercion tabla ParametrizacionEjecucionProgramada
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepFrecuencia,pepEstado) VALUES('CIERRE_AUTOMATICO_EXTEMPORANEIDAD_PREVENTIVA','00','10','00','DIARIO','INACTIVO');

--changeset abaquero:02
--comment: Atualizacion tabla ValidatorParamValue 
UPDATE ValidatorParamValue SET value='IRP' WHERE id=2110561;
UPDATE ValidatorParamValue SET value='IRP' WHERE id=2110569;