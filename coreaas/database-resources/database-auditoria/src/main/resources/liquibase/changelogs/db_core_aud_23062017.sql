--liquibase formatted sql

--changeset atoro:01
--comment: Se crea tabla SolicitudAporte_aud
CREATE TABLE dbo.SolicitudAporte_aud (
	soaId bigint IDENTITY(1,1) NOT NULL,
	soaSolicitudGlobal bigint NOT NULL,
	soaEstadoSolicitud varchar(20) NULL,
	soaAporteGeneral bigint NOT NULL,
); 