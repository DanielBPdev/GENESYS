--liquibase formatted sql

--changeset jroa:01
--comment:
CREATE TABLE HistoricoUbicacion(
	hubId  BIGINT IDENTITY(1,1) NOT NULL,
	hubTelefonoFijo VARCHAR(10),
	hubTelefonoCelular VARCHAR(10),
	hubEmail VARCHAR(255),
	hubPersona BIGINT NOT NULL,
	CONSTRAINT PK_HistoricoUbicacion_hubId PRIMARY KEY (hubId)
)