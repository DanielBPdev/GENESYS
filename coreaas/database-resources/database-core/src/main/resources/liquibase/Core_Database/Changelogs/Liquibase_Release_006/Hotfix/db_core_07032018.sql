--liquibase formatted sql

--changeset atoro:01
--comment:Insercion de registros en la tabla ParametrizacionEjecucionProgramada
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepFrecuencia,pepEstado) VALUES ('FINALIZACION_CICLO_FISCALIZACION','00','00','00','DIARIO','ACTIVO');
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepFrecuencia,pepEstado) VALUES ('REGISTRO_NOVEDAD_FUTURA','00','00','00','DIARIO','ACTIVO');

--changeset jocorrea:02
--comment:Adicion de campo en la tabla RegistroNovedadFutura
ALTER TABLE RegistroNovedadFutura ADD rnfProcesada BIT NULL;