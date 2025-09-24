--liquibase formatted sql

--changeset rlopez:01
--comment: Se adicionan campos en la tabla ParametrizacionCondicionesSubsidio
ALTER TABLE ParametrizacionCondicionesSubsidio ADD pcsCantidadSubsidiosLiquidados INT NULL;
ALTER TABLE ParametrizacionCondicionesSubsidio ADD pcsMontoSubsidiosLiquidados NUMERIC(10,0) NULL;
ALTER TABLE ParametrizacionCondicionesSubsidio ADD pcsCantidadSubsidiosLiquidadosInvalidez INT NULL;
ALTER TABLE ParametrizacionCondicionesSubsidio ADD pcsCantidadPeriodosRetroactivosMes INT NULL;

--changeset rlopez:02
--comment: Insercion de registros en la tabla ParametrizacionEjecucionProgramada
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,pepFechaFin,pepFrecuencia,pepEstado) VALUES ('CARGA_AUTOMATICA_ARCHIVOS_DESCUENTO','00','00','00',NULL,NULL,NULL,NULL,NULL,NULL,'DIARIO','ACTIVO');
