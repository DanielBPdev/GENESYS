--liquibase formatted sql

--changeset jusanchez:01
--comment: Se cambia de nombramiento de tablas y campos de tablas asociadas a Cartera
--Eliminacion de llaves primarias y foraneas
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'FK_CicloAportante_capCicloFiscalizacion')) ALTER TABLE CicloAportante DROP CONSTRAINT FK_CicloAportante_capCicloFiscalizacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'PK_CicloFiscalizacion_cfiId')) ALTER TABLE CicloFiscalizacion DROP CONSTRAINT PK_CicloFiscalizacion_cfiId;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'PK_AgendaFiscalizacion_afsId')) ALTER TABLE AgendaFiscalizacion DROP CONSTRAINT PK_AgendaFiscalizacion_afsId;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'FK_ActividadDocumento_adoActividadFiscalizacion')) ALTER TABLE ActividadDocumento DROP CONSTRAINT FK_ActividadDocumento_adoActividadFiscalizacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'PK_ActividadFiscalizacion_acfId')) ALTER TABLE ActividadFiscalizacion DROP CONSTRAINT PK_ActividadFiscalizacion_acfId;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'FK_AgendaFiscalizacion_afsCicloAportante')) ALTER TABLE AgendaFiscalizacion DROP CONSTRAINT FK_AgendaFiscalizacion_afsCicloAportante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'FK_ActividadFiscalizacion_acfCicloAportante')) ALTER TABLE ActividadFiscalizacion DROP CONSTRAINT FK_ActividadFiscalizacion_acfCicloAportante;

--TABLA CicloCartera
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CicloFiscalizacion_cfiEstadoCicloFiscalizacion')) ALTER TABLE CicloFiscalizacion DROP CONSTRAINT CK_CicloFiscalizacion_cfiEstadoCicloFiscalizacion;
EXEC sp_rename 'CicloFiscalizacion', 'CicloCartera';
EXEC sp_rename 'CicloCartera.cfiId', 'ccrId', 'COLUMN';
EXEC sp_rename 'CicloCartera.cfiEstadoCicloFiscalizacion', 'ccrEstadoCiclo', 'COLUMN';
EXEC sp_rename 'CicloCartera.cfiFechaInicio', 'ccrFechaInicio', 'COLUMN';
EXEC sp_rename 'CicloCartera.cfiFechaFin', 'ccrFechaFin', 'COLUMN';
EXEC sp_rename 'CicloCartera.cfiFechaCreacion', 'ccrFechaCreacion', 'COLUMN';
ALTER TABLE CicloCartera ADD CONSTRAINT PK_CicloCartera_ccrId PRIMARY KEY (ccrId);
ALTER TABLE CicloCartera ADD ccrTipoCiclo VARCHAR(14)NULL;

--TABLA CicloAportante>
EXEC sp_rename 'CicloAportante.capCicloFiscalizacion', 'capCicloCartera', 'COLUMN';
ALTER TABLE CicloAportante ADD CONSTRAINT FK_CicloAportante_capCicloCartera FOREIGN KEY (capCicloCartera) REFERENCES CicloCartera(ccrId);

--TABLA AgendaCartera
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AgendaFiscalizacion_afsVisitaAgenda')) ALTER TABLE AgendaFiscalizacion DROP CONSTRAINT CK_AgendaFiscalizacion_afsVisitaAgenda;
EXEC sp_rename 'AgendaFiscalizacion', 'AgendaCartera'; 
EXEC sp_rename 'AgendaCartera.afsId', 'agrId', 'COLUMN';
EXEC sp_rename 'AgendaCartera.afsVisitaAgenda', 'agrVisitaAgenda', 'COLUMN';
EXEC sp_rename 'AgendaCartera.afsFecha', 'agrFecha', 'COLUMN';
EXEC sp_rename 'AgendaCartera.afsHorario', 'agrHorario', 'COLUMN';
EXEC sp_rename 'AgendaCartera.afsContacto', 'agrContacto', 'COLUMN';
EXEC sp_rename 'AgendaCartera.afsTelefono', 'agrTelefono', 'COLUMN';
EXEC sp_rename 'AgendaCartera.afsCicloAportante', 'agrCicloAportante', 'COLUMN';
ALTER TABLE AgendaCartera ADD CONSTRAINT PK_AgendaCartera_agrId PRIMARY KEY (agrId);
ALTER TABLE AgendaCartera ADD CONSTRAINT FK_AgendaCartera_agrCicloAportante FOREIGN KEY (agrCicloAportante) REFERENCES CicloAportante(capId);

--TABLA ActividadCartera
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ActividadFiscalizacion_acfActividadFiscalizacion')) ALTER TABLE ActividadFiscalizacion DROP CONSTRAINT CK_ActividadFiscalizacion_acfActividadFiscalizacion; 
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ActividadFiscalizacion_acfResultadoFiscalizacion')) ALTER TABLE ActividadFiscalizacion DROP CONSTRAINT CK_ActividadFiscalizacion_acfResultadoFiscalizacion; 
EXEC sp_rename 'ActividadFiscalizacion', 'ActividadCartera'; 
EXEC sp_rename 'ActividadCartera.acfId', 'acrId', 'COLUMN';
EXEC sp_rename 'ActividadCartera.acfActividadFiscalizacion', 'acrActividadCartera', 'COLUMN';
EXEC sp_rename 'ActividadCartera.acfResultadoFiscalizacion', 'acrResultadoCartera', 'COLUMN';
EXEC sp_rename 'ActividadCartera.acfComentarios', 'acrComentarios', 'COLUMN';
EXEC sp_rename 'ActividadCartera.acfCicloAportante', 'acrCicloAportante', 'COLUMN';
EXEC sp_rename 'ActividadCartera.acfFecha', 'acrFecha', 'COLUMN';
ALTER TABLE ActividadCartera ADD CONSTRAINT PK_ActividadCartera_acrId PRIMARY KEY (acrId);
ALTER TABLE ActividadCartera ADD CONSTRAINT FK_ActividadCartera_acrCicloAportante FOREIGN KEY (acrCicloAportante) REFERENCES CicloAportante(capId);

--TABLA SolicitudGestionCobroManual
CREATE TABLE SolicitudGestionCobroManual(
	scmId BIGINT NOT NULL IDENTITY(1,1),
	scmCicloCartera BIGINT NOT NULL,
	scmEstadoSolicitud VARCHAR(25) NULL,
	scmSolicitudGlobal BIGINT NOT NULL, 
	scmLineaCobro VARCHAR(3) NOT NULL,
CONSTRAINT PK_SolicitudGestionCobroManual_scmId	PRIMARY KEY (scmId)
);
ALTER TABLE SolicitudGestionCobroManual ADD CONSTRAINT FK_SolicitudGestionCobroManual_scmCicloCartera FOREIGN KEY (scmCicloCartera) REFERENCES CicloCartera(ccrId);
ALTER TABLE SolicitudGestionCobroManual ADD CONSTRAINT FK_SolicitudGestionCobroManual_scmSolicitudGlobal FOREIGN KEY (scmSolicitudGlobal) REFERENCES Solicitud(solId);

--TABLA ActividadDocumento
EXEC sp_rename 'ActividadDocumento.adoActividadFiscalizacion', 'adoActividadCartera', 'COLUMN';
ALTER TABLE ActividadDocumento ADD adoDocumentoSoporte BIGINT NULL; 
ALTER TABLE ActividadDocumento ADD CONSTRAINT FK_ActividadDocumento_adoActividadCartera FOREIGN KEY (adoActividadCartera) REFERENCES ActividadCartera(acrId);
ALTER TABLE ActividadDocumento ADD CONSTRAINT FK_ActividadDocumento_adoDocumentoSoporte FOREIGN KEY (adoDocumentoSoporte) REFERENCES DocumentoSoporte(dosId);

--TABLA BitacoraCartera
CREATE TABLE BitacoraCartera(
	bcaId BIGINT NOT NULL IDENTITY(1,1), 
	bcaFecha DATE NULL,
	bcaActividad VARCHAR(4) NULL, 
	bcaMedio VARCHAR(16) NULL,
	bcaResultado VARCHAR(33) NULL, 
	bcaUsuario VARCHAR(255) NULL,
	bcaPersona BIGINT NOT NULL, 
	bcaTipoSolicitante VARCHAR(13) NOT NULL,
CONSTRAINT PK_BitacoraCartera_bcaId PRIMARY KEY(bcaId)
);
ALTER TABLE BitacoraCartera ADD CONSTRAINT FK_BitacoraCartera_bcaPersona FOREIGN KEY (bcaPersona) REFERENCES Persona(perId);

--TABLA DocumentoBitacora 
CREATE TABLE DocumentoBitacora(
	dbiId BIGINT NOT NULL IDENTITY(1,1),
	dbiBitacoraCartera BIGINT NOT NULL,
	dbiDocumentoSoporte BIGINT NOT NULL,
CONSTRAINT PK_DocumentoBitacora_dbiId PRIMARY KEY(dbiId)
);
ALTER TABLE DocumentoBitacora ADD CONSTRAINT FK_DocumentoBitacora_dbiBitacoraCartera FOREIGN KEY (dbiBitacoraCartera) REFERENCES BitacoraCartera(bcaId);
ALTER TABLE DocumentoBitacora ADD CONSTRAINT FK_DocumentoBitacora_dbiDocumentoSoporte FOREIGN KEY (dbiDocumentoSoporte) REFERENCES DocumentoSoporte(dosId);

--changeset jroa:02
--comment:Insercion registro en la tabla Constante
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_GESTION_COBRO_MANUAL_DEPLOYMENTID','com.asopagos.coreaas.bpm.gestion_cobro_manual:gestion_cobro_manual:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para gestión de cobro manual');
