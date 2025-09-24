--liquibase formatted sql

--changeset borozco:01
--comment:Creacion de las tablas SolicitudDesafiliacion_aud, DesafiliacionAportante_aud y DocumentoDesafiliacion_aud
CREATE TABLE SolicitudDesafiliacion_aud(
	sodId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	sodComentarioAnalista VARCHAR(9) NULL,
	sodEstadoSolicitud INT NOT NULL,
	sodSolicitudGlobal BIGINT NOT NULL,
);
ALTER TABLE SolicitudDesafiliacion_aud ADD CONSTRAINT FK_SolicitudDesafiliacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE DesafiliacionAportante_aud(
	deaId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	deaPersona BIGINT NULL,
	deaSolicitudDesafiliacion BIGINT NULL,
	deaTipoSolicitante VARCHAR(13) NULL,
);
ALTER TABLE DesafiliacionAportante_aud ADD CONSTRAINT FK_DesafiliacionAportante_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE DocumentoDesafiliacion_aud(
	dodId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	dodDocumentoSoporte BIGINT NULL,
	dodSolicitudDesafiliacion BIGINT NULL,
);
ALTER TABLE DocumentoDesafiliacion_aud ADD CONSTRAINT FK_DocumentoDesafiliacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset squintero:02
--comment:Se adiciona campos a la tabla Empresa_aud
ALTER TABLE Empresa_aud ADD empEnviadoAFiscalizacion BIT NULL;
ALTER TABLE Empresa_aud ADD empMotivoFiscalizacion VARCHAR(30) NULL;
