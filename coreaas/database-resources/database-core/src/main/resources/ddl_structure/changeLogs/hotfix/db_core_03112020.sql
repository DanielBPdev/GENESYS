--liquibase formatted sql

--changeset flopez:01
--comment: Creación de Tabla Histórica de Parametrización ejecución Programada.
DROP TABLE IF EXISTS HistoricoParametrizacionEjecucionProgramada;
CREATE TABLE HistoricoParametrizacionEjecucionProgramada
(
	hpeId BIGINT IDENTITY(1,1) NOT NULL,
	hpeProceso varchar(100) NOT NULL,
	hpeHoras varchar(20),
	hpeMinutos varchar(20),
	hpeSegundos varchar(20),
	hpeDiaSemana varchar(50),
	hpeDiaMes varchar(50),
	hpeMes varchar(50),
	hpeAnio varchar(50),
	hpeFechaInicio date,
	hpeFechaFin date,
	hpeFrecuencia varchar(50) NOT NULL,
	hpeEstado varchar(8),
	hpeFechaActualizacion datetime,
	hpeIsActualizacion bit,
	CONSTRAINT PK_HistoricoParametrizacionEjecucionProgramada_hpeId PRIMARY KEY (hpeId)
);