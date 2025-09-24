--liquibase formatted sql

--changeset rcastillo:01
--comment: Quitar bloqueo en FOVIS. 
ALTER TABLE dbo.SolicitudAnalisisNovedadFovis DROP CONSTRAINT IF EXISTS FK_SolicitudAnalisisNovedadFovis_sanSolicitudGlobal
ALTER TABLE dbo.SolicitudAnalisisNovedadFovis DROP CONSTRAINT IF EXISTS FK_SolicitudAnalisisNovedadFovis_sanSolicitudNovedad