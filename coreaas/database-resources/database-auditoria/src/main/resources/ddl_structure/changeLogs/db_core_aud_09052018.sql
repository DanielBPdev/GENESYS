--liquibase formatted sql

--changeset ecastano:01
--comment: Creacion campos tabla LegalizacionDesembolso_aud_aud
ALTER TABLE LegalizacionDesembolso_aud ADD lgdDocumentoSoporte varchar (50) NULL;
ALTER TABLE LegalizacionDesembolso_aud ADD lgdFechaTrasnferencia datetime2(7) NULL;
ALTER TABLE LegalizacionDesembolso_aud ADD lgdObservaciones varchar(500) NULL;    
ALTER TABLE LegalizacionDesembolso_aud ADD lgdMontoDesembolsado numeric(19, 5) NULL;
ALTER TABLE LegalizacionDesembolso_aud ADD lgdObservacionesBack varchar(500) NULL;    
ALTER TABLE LegalizacionDesembolso_aud ADD lgdDocumentoSoporteBack varchar (50) NULL;

--changeset ecastano:02
--comment: Creacion campo sgcSolicitudGlobal tabla SolicitudGestionCruce_aud
ALTER TABLE SolicitudGestionCruce_aud ADD sgcSolicitudGlobal bigint NULL;