--liquibase formatted sql

--changeset squintero:01
--comment: Creacion de la tabla DatoTemporalComunicado
CREATE TABLE DatoTemporalComunicado(
	dtcId BIGINT NOT NULL IDENTITY(1,1), 
	dtcIdTarea BIGINT NULL,
	dtcJsonPayload TEXT NULL,             
CONSTRAINT PK_DatoTemporalComunicado_dtcId PRIMARY KEY (dtcId)
);

