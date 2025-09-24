--liquibase formatted sql

--changeset flopez:01
--comment: Cambio de tipo de dato en la tabla ParametrizacionEjecucionProgramada y ResultadoEjecucionProgramada
DROP TABLE ParametrizacionEjecucionProgramada;
DROP TABLE ResultadoEjecucionProgramada;

CREATE TABLE ParametrizacionEjecucionProgramada (
pepId bigint NOT NULL IDENTITY PRIMARY KEY, 
pepProceso varchar(100) NOT NULL,
pepHoras varchar(20),
pepMinutos varchar(20),
pepSegundos varchar(20),
pepDiaSemana varchar(50), 
pepDiaMes varchar(50), 
pepMes varchar(50), 
pepAnio varchar(50), 
pepFechaInicio date,
pepFechaFin date,
pepFrecuencia varchar(50) NOT NULL
);

CREATE TABLE ResultadoEjecucionProgramada (
repId bigint NOT NULL IDENTITY PRIMARY KEY, 
repProceso varchar(100) NOT NULL,
repFechaEjecucion datetime,
repResultadoEjecucion varchar(50)
);


