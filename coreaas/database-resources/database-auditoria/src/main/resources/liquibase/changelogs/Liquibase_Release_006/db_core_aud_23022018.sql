--liquibase formatted sql

--changeset jocorrea:01
--comment:
CREATE TABLE SolicitudAnalisisNovedadFovis_aud(
	sanId BIGINT NOT NULL ,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	sanSolicitudGlobal BIGINT NOT NULL,
	sanSolicitudNovedad BIGINT NOT NULL,
	sanPersona BIGINT NOT NULL,
	sanEstadoSolicitud VARCHAR(9) NOT NULL,
	sanPostulacionFovis BIGINT NOT NULL,
	sanObservaciones VARCHAR(500) NULL,
);
ALTER TABLE SolicitudAnalisisNovedadFovis_aud ADD CONSTRAINT FK_SolicitudAnalisisNovedadFovis_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE SolicitudNovedadFovis_aud(
	snfId BIGINT NOT NULL IDENTITY (1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	snfSolicitudGlobal BIGINT NOT NULL,
	snfEstadoSolicitud VARCHAR(38) NOT NULL,
	snfParametrizacionNovedad BIGINT NOT NULL,
	snfObservaciones VARCHAR(200) NULL,
);
ALTER TABLE SolicitudNovedadFovis_aud ADD CONSTRAINT FK_SolicitudNovedadFovis_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE SolicitudNovedadPersonaFovis_aud(
	spfId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	spfPersona BIGINT NULL,
	spfSolicitudNovedadFovis BIGINT NOT NULL,
	spfPostulacionFovis BIGINT NOT NULL,
);
ALTER TABLE SolicitudNovedadPersonaFovis_aud ADD CONSTRAINT FK_SolicitudNovedadPersonaFovis_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
