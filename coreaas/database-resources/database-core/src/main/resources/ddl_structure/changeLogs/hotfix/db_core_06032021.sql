--liquibase formatted sql

--changeset dsuesca:01
--comment: 
  CREATE TABLE TransaccionNovedadPilaCompleta (
	tnpId BIGINT NOT NULL PRIMARY KEY,
	tnpRegistroDetallado BIGINT not null,
	tnpFechaEjecucion DATETIME not null
)

--changeset dsuesca:02
--comment: 
CREATE TABLE ExcepcionNovedadPila (
	enpId BIGINT PRIMARY KEY IDENTITY(1,1),
	enpIdTempNovedad BIGINT NOT NULL,
	enpExcepcion VARCHAR(MAX),
	enpFecha DATETIME
)