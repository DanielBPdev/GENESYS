--liquibase formatted sql

--changeset jocorrea:01
--comment: Se crean las tablas CargueArchivoActualizacion, DiferenciasCargueActualizacion y se agrega campo a la tabla Solicitud
CREATE TABLE CargueArchivoActualizacion(
caaId BIGINT NOT NULL IDENTITY(1,1),
caaNombreArchivo VARCHAR(50) NOT NULL,
caaFechaProcesamiento DATETIME NULL,
caaCodigoIdentificadorECM VARCHAR(255) NOT NULL,
caaEstado VARCHAR (40) NOT NULL,
caaFechaAceptacion DATETIME NULL,
CONSTRAINT PK_CargueArchivoActualizacion_caaId PRIMARY KEY (caaId)
);

CREATE TABLE DiferenciasCargueActualizacion(
dicId BIGINT NOT NULL IDENTITY(1,1),
dicTipoTransaccion VARCHAR(100) NULL,
dicJsonPayload text NULL,
dicCargueArchivoActualizacion BIGINT NOT NULL,
CONSTRAINT PK_DiferenciasCargueActualizacion_dicId PRIMARY KEY (dicId)
);
ALTER TABLE DiferenciasCargueActualizacion ADD CONSTRAINT FK_DiferenciasCargueActualizacion_dicCargueArchivoActualizacion FOREIGN KEY (dicCargueArchivoActualizacion) REFERENCES CargueArchivoActualizacion(caaId);

ALTER TABLE Solicitud ADD solDiferenciasCargueActualizacion BIGINT NULL;
ALTER TABLE Solicitud ADD CONSTRAINT FK_Solicitud_solDiferenciasCargueActualizacion FOREIGN KEY (solDiferenciasCargueActualizacion) REFERENCES DiferenciasCargueActualizacion(dicId);
