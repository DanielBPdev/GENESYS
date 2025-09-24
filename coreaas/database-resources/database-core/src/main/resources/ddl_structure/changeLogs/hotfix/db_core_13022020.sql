--liquibase formatted sql

--changeset dsuesca:01
--comment: se crea tabla para registro de errores de base de datos
CREATE TABLE RegistroLog (
	relId bigint IDENTITY(1,1) NOT NULL,
	relFecha datetime NOT NULL,
	relParametrosEjecucion varchar(6000),
	relErrorMessage varchar(MAX),
	CONSTRAINT PK_RegistroLog_relId PRIMARY KEY (relId)
)