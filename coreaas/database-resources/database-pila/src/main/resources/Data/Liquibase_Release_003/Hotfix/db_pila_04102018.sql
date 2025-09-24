--liquibase formatted sql

--changeset abaquero:01
--comment: Se adiciona tabla para llevar el historial de cambio de estados por bloque de validación
CREATE TABLE dbo.HistorialEstadoBloque (
	hebId bigint identity NOT NULL,
	hebEstado varchar(75) NOT NULL,
	hebAccion varchar(75) NOT NULL,
	hebBloque varchar(11) NOT NULL,
	hebFechaEstado datetime,
	hebIdIndicePlanilla bigint,
	hebIdIndicePlanillaOF bigint,
	CONSTRAINT PK_HistorialEstadoBloque PRIMARY KEY (hebId)
)

ALTER TABLE dbo.HistorialEstadoBloque ADD CONSTRAINT FK_HistorialEstadoBloque_hebIdIndicePlanilla FOREIGN KEY (hebIdIndicePlanilla) REFERENCES dbo.PilaIndicePlanilla(pipId)
ALTER TABLE dbo.HistorialEstadoBloque ADD CONSTRAINT FK_HistorialEstadoBloque_hebIdIndicePlanillaOF FOREIGN KEY (hebIdIndicePlanillaOF) REFERENCES dbo.PilaIndicePlanillaOF(pioId)