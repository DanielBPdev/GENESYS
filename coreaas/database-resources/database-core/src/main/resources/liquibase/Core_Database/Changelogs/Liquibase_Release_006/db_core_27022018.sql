--liquibase formatted sql

--changeset borozco:01
--comment:Creacion de las tablas SolicitudDesafiliacion, DesafiliacionAportante y DocumentoDesafiliacion
CREATE TABLE SolicitudDesafiliacion(
	sodId BIGINT NOT NULL IDENTITY(1,1),
	sodComentarioAnalista VARCHAR(9) NULL,
	sodEstadoSolicitud INT NOT NULL,
	sodSolicitudGlobal BIGINT NOT NULL,
CONSTRAINT PK_SolicitudDesafiliacion_sodId PRIMARY KEY (sodId)
);
ALTER TABLE SolicitudDesafiliacion ADD CONSTRAINT FK_SolicitudDesafiliacion_sodSolicitudGlobal FOREIGN KEY (sodSolicitudGlobal) REFERENCES Solicitud(solId);

CREATE TABLE DesafiliacionAportante(
	deaId BIGINT NOT NULL IDENTITY(1,1),
	deaPersona BIGINT NULL,
	deaSolicitudDesafiliacion BIGINT NULL,
	deaTipoSolicitante VARCHAR(13) NULL,
CONSTRAINT PK_DesafiliacionAportante_deaId PRIMARY KEY (deaId)
);
ALTER TABLE DesafiliacionAportante ADD CONSTRAINT FK_DesafiliacionAportante_deaSolicitudDesafiliacion FOREIGN KEY (deaSolicitudDesafiliacion) REFERENCES SolicitudDesafiliacion(sodId);

CREATE TABLE DocumentoDesafiliacion(
	dodId BIGINT NOT NULL IDENTITY(1,1),
	dodDocumentoSoporte BIGINT NULL,
	dodSolicitudDesafiliacion BIGINT NULL,
CONSTRAINT PK_DocumentoDesafiliacion_dodId PRIMARY KEY (dodId) 
);
ALTER TABLE DocumentoDesafiliacion ADD CONSTRAINT FK_DocumentoDesafiliacion_dodSolicitudDesafiliacion FOREIGN KEY (dodSolicitudDesafiliacion) REFERENCES SolicitudDesafiliacion(sodId)
ALTER TABLE DocumentoDesafiliacion ADD CONSTRAINT FK_DocumentoDesafiliacion_dodDocumentoSoporte FOREIGN KEY (dodDocumentoSoporte) REFERENCES DocumentoSoporte(dosId)

--changeset squintero:02
--comment:Se adiciona campos a la tabla Empresa 
ALTER TABLE Empresa ADD empEnviadoAFiscalizacion BIT NULL;
ALTER TABLE Empresa ADD empMotivoFiscalizacion VARCHAR(30) NULL;

--changeset abaquero:03
--comment:Creacion de la tabla 
CREATE TABLE EstadoCondicionValidacionSubsidio(
	ecvId BIGINT NOT NULL IDENTITY(1,1), 
	ecvAplicacionValidacionSubsidio BIGINT NOT NULL, 
	ecvActivo BIT NULL, 
	ecvInactivo BIT NULL, 
	ecvNFRetiradoConAportes BIT NULL, 
	ecvNFConAporteSinAfiliacion BIT NULL, 
	ecvNFConInformacion BIT NULL, 
	ecvOk BIT NULL, 
	ecvNoOk BIT NULL, 
	ecvNoValidadoBD BIT NULL, 
CONSTRAINT PK_EstadoCondicionValidacionSubsidio_ecvId PRIMARY KEY (ecvId)
);
ALTER TABLE EstadoCondicionValidacionSubsidio ADD CONSTRAINT FK_EstadoCondicionValidacionSubsidio_ecvAplicacionValidacionSubsidio FOREIGN KEY (ecvAplicacionValidacionSubsidio) REFERENCES AplicacionValidacionSubsidio (avsId);
