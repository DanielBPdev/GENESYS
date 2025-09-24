--liquibase formatted sql

--changeset jusanchez:01
--comment: Se cambia de nombramiento de Tablas y campos de Tablas asociadas a Cartera
--Tabla CicloCartera_aud
EXEC sp_rename 'CicloFiscalizacion_aud', 'CicloCartera_aud';
EXEC sp_rename 'CicloCartera_aud.cfiId', 'ccrId', 'COLUMN';
EXEC sp_rename 'CicloCartera_aud.cfiEstadoCicloFiscalizacion', 'ccrEstadoCiclo', 'COLUMN';
EXEC sp_rename 'CicloCartera_aud.cfiFechaInicio', 'ccrFechaInicio', 'COLUMN';
EXEC sp_rename 'CicloCartera_aud.cfiFechaFin', 'ccrFechaFin', 'COLUMN';
EXEC sp_rename 'CicloCartera_aud.cfiFechaCreacion', 'ccrFechaCreacion', 'COLUMN';
ALTER TABLE CicloCartera_aud ADD ccrTipoCiclo VARCHAR(14)NULL;

--Tabla CicloAportante_aud
EXEC sp_rename 'CicloAportante_aud.capCicloFiscalizacion', 'capCicloCartera', 'COLUMN';

--Tabla AgendaCartera_aud
EXEC sp_rename 'AgendaFiscalizacion_aud', 'AgendaCartera_aud'; 
EXEC sp_rename 'AgendaCartera_aud.afsId', 'agrId', 'COLUMN';
EXEC sp_rename 'AgendaCartera_aud.afsVisitaAgenda', 'agrVisitaAgenda', 'COLUMN';
EXEC sp_rename 'AgendaCartera_aud.afsFecha', 'agrFecha', 'COLUMN';
EXEC sp_rename 'AgendaCartera_aud.afsHorario', 'agrHorario', 'COLUMN';
EXEC sp_rename 'AgendaCartera_aud.afsContacto', 'agrContacto', 'COLUMN';
EXEC sp_rename 'AgendaCartera_aud.afsTelefono', 'agrTelefono', 'COLUMN';
EXEC sp_rename 'AgendaCartera_aud.afsCicloAportante', 'agrCicloAportante', 'COLUMN';

--Tabla ActividadCartera_aud
EXEC sp_rename 'ActividadFiscalizacion_aud', 'ActividadCartera_aud'; 
EXEC sp_rename 'ActividadCartera_aud.acfId', 'acrId', 'COLUMN';
EXEC sp_rename 'ActividadCartera_aud.acfActividadFiscalizacion', 'acrActividadCartera', 'COLUMN';
EXEC sp_rename 'ActividadCartera_aud.acfResultadoFiscalizacion', 'acrResultadoCartera', 'COLUMN';
EXEC sp_rename 'ActividadCartera_aud.acfComentarios', 'acrComentarios', 'COLUMN';
EXEC sp_rename 'ActividadCartera_aud.acfCicloAportante', 'acrCicloAportante', 'COLUMN';
EXEC sp_rename 'ActividadCartera_aud.acfFecha', 'acrFecha', 'COLUMN';

--Tabla SolicitudGestionCobroManual_aud
CREATE TABLE SolicitudGestionCobroManual_aud(
	scmId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	scmCicloCartera BIGINT NOT NULL,
	scmEstadoSolicitud VARCHAR(25) NULL,
	scmSolicitudGlobal BIGINT NOT NULL, 
	scmLineaCobro VARCHAR(3) NOT NULL,
);
ALTER TABLE SolicitudGestionCobroManual_aud ADD CONSTRAINT FK_SolicitudGestionCobroManual_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Tabla ActividadDocumento_aud
EXEC sp_rename 'ActividadDocumento_aud.adoActividadFiscalizacion', 'adoActividadCartera', 'COLUMN';
ALTER TABLE ActividadDocumento_aud ADD adoDocumentoSoporte BIGINT NULL; 

--Tabla BitacoraCartera_aud 
CREATE TABLE BitacoraCartera_aud(
	bcaId BIGINT NOT NULL, 
	REV int NOT NULL,
	REVTYPE smallint NULL,
	bcaFecha DATE NULL,
	bcaActividad VARCHAR(4) NULL, 
	bcaMedio VARCHAR(16) NULL,
	bcaResultado VARCHAR(33) NULL, 
	bcaUsuario VARCHAR(255) NULL,
	bcaPersona BIGINT NOT NULL, 
	bcaTipoSolicitante VARCHAR(13) NOT NULL,
);
ALTER TABLE BitacoraCartera_aud ADD CONSTRAINT FK_BitacoraCartera_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Tabla DocumentoBitacora_aud 
CREATE TABLE DocumentoBitacora_aud(
	dbiId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	dbiBitacoraCartera BIGINT NOT NULL,
	dbiDocumentoSoporte BIGINT NOT NULL,
);
ALTER TABLE DocumentoBitacora_aud ADD CONSTRAINT FK_DocumentoBitacora_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset borozco:02
--comment: Se modifica campo de la tabla Cartera_aud
ALTER TABLE Cartera_aud DROP COLUMN carId;  
ALTER TABLE Cartera_aud ADD carId BIGINT NOT NULL;
