--liquibase formatted sql

--changeset squintero:01
--comment: Creacion de la tabla DatoTemporalComunicado
CREATE TABLE DatoTemporalComunicado_aud(
	dtcId BIGINT NOT NULL IDENTITY(1,1), 
	REV int NOT NULL,
	REVTYPE smallint NULL,
	dtcIdTarea BIGINT NULL,
	dtcJsonPayload TEXT NULL,             
);
ALTER TABLE DatoTemporalComunicado_aud ADD CONSTRAINT FK_DatoTemporalComunicado_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);