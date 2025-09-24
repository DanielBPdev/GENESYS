--liquibase formatted sql

--changeset clmarin:01
--comment:Creacion de las tablas NotificacionPersonal_aud y NotificacionPersonalDocumento_aud
CREATE TABLE NotificacionPersonal_aud(
	ntpId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ntpActividad VARCHAR (41) NOT NULL,
	ntpPersona BIGINT NOT NULL,
	ntpTipoSolicitante VARCHAR (13) NOT NULL,
	ntpComentario VARCHAR (250) NULL,
);
ALTER TABLE NotificacionPersonal_aud ADD CONSTRAINT FK_NotificacionPersonal_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE NotificacionPersonalDocumento_aud(
	ntdId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ntdDocumentoSoporte BIGINT NOT NULL,
);
ALTER TABLE NotificacionPersonalDocumento_aud ADD CONSTRAINT FK_NotificacionPersonalDocumento_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset clmarin:02
--comment:Se adiciona campo en la tabla NotificacionPersonalDocumento_aud
ALTER TABLE NotificacionPersonalDocumento_aud ADD ntdNotificacionPersonal BIGINT NOT NULL;
