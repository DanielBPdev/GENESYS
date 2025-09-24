--liquibase formatted sql

--changeset fhoyos:01
--comment: 
ALTER TABLE [dbo].[DatoTemporalComunicado] ADD dtcIdInstanciaProceso VARCHAR(255) NULL, dtcIdSolicitud BIGINT NULL;