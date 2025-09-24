--liquibase formatted sql

--changeset eprocess:GLPI_49367
--comment: Se crea una nueva tabla para la administración de establecimientos

CREATE TABLE [dbo].[EstablecimientosMediosPago](
	estId [int] IDENTITY(1,1) NOT NULL,
	estCodigo [varchar](12) NOT NULL,
	estNombre [varchar](120) NOT NULL,
	CONSTRAINT UK_establecimiento_estCodigo UNIQUE (estCodigo)
);
