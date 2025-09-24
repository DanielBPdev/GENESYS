--liquibase formatted sql

--changeset tuestrella:01
--comment: mantis 266246

DROP INDEX dbo.PlantillaComunicadoResuelta.UK_PlantillaComunicadoResuelta_NumeroIdentificacion_Solicitud_Id;

CREATE UNIQUE INDEX UK_PlantillaComunicadoResuelta_NumeroIdentificacion_TipoIdentificacion_Solicitud_Id 
ON dbo.PlantillaComunicadoResuelta (pcrNumeroIdentificacion, pcrTipoIdentificacion, pcrSolicitud);