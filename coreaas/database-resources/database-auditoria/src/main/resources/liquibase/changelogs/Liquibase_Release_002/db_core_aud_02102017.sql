--liquibase formatted sql

--changeset jocorrea:01
--comment: Se crean las tablas CargueArchivoActualizacion_aud, DiferenciasCargueActualizacion_aud y se agrega campo a la tabla Solicitud_aud
CREATE TABLE CargueArchivoActualizacion_aud(
caaId BIGINT NOT NULL IDENTITY(1,1),
REV int NOT NULL,
REVTYPE smallint NULL,
caaNombreArchivo VARCHAR(50) NOT NULL,
caaFechaProcesamiento DATETIME NULL,
caaCodigoIdentificadorECM VARCHAR(255) NOT NULL,
caaEstado VARCHAR (40) NOT NULL,
caaFechaAceptacion DATETIME NULL,
);
ALTER TABLE CargueArchivoActualizacion_aud ADD CONSTRAINT FK_CargueArchivoActualizacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE DiferenciasCargueActualizacion_aud(
dicId BIGINT NOT NULL IDENTITY(1,1),
REV int NOT NULL,
REVTYPE smallint NULL,
dicTipoTransaccion VARCHAR(100) NULL,
dicJsonPayload text NULL,
dicCargueArchivoActualizacion BIGINT NOT NULL,
);
ALTER TABLE DiferenciasCargueActualizacion_aud ADD CONSTRAINT FK_DiferenciasCargueActualizacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

ALTER TABLE Solicitud_aud ADD solDiferenciasCargueActualizacion BIGINT NULL;
