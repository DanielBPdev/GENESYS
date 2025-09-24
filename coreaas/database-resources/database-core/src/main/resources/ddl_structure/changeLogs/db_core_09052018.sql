--liquibase formatted sql

--changeset ecastano:01
--comment: Creacion campos tabla LegalizacionDesembolso
ALTER TABLE LegalizacionDesembolso ADD lgdDocumentoSoporte varchar (50) NULL;
ALTER TABLE LegalizacionDesembolso ADD lgdFechaTrasnferencia datetime2(7) NULL;
ALTER TABLE LegalizacionDesembolso ADD lgdObservaciones varchar(500) NULL;    
ALTER TABLE LegalizacionDesembolso ADD lgdMontoDesembolsado numeric(19, 5) NULL;
ALTER TABLE LegalizacionDesembolso ADD lgdObservacionesBack varchar(500) NULL;    
ALTER TABLE LegalizacionDesembolso ADD lgdDocumentoSoporteBack varchar (50) NULL;

--changeset ecastano:02
--comment: Insert tabla Constante
INSERT INTO Constante (cnsNombre, cnsValor, cnsDescripcion)
VALUES ('BPMS_PROCESS_GESTION_CRUCES_FOVIS_DEPLOYMENTID','com.asopagos.coreaas.bpm.postulacion_fovis_gestion_cruces:postulacion_fovis_gestion_cruces:0.0.2-SNAPSHOT', 'Identificador version proceso BPM para la gestion de cruces internos de la postulacion fovis');

--changeset ecastano:03
--comment: Creacion campo sgcSolicitudGlobal tabla SolicitudGestionCruce
ALTER TABLE SolicitudGestionCruce ADD sgcSolicitudGlobal bigint NULL;