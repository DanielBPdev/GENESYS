--changeset ggiraldo:03
--comment: Adición de columnas relacionadas al reponsable o el que solicitó la generación del reporte

ALTER TABLE [dbo].[SolicitudesReportesNormativos]
ADD [srCargoSolicitadoPor] [varchar](255) NULL;

ALTER TABLE [dbo].[SolicitudesReportesNormativos]
ADD [srCorreoSolicitadoPor] [varchar](255) NULL;

ALTER TABLE [dbo].[SolicitudesReportesNormativos]
ADD [srTelefonoSolicitadoPor] [varchar](255) NULL;

ALTER TABLE [dbo].[SolicitudesReportesNormativos]
ADD [srIndicativoTelSolicitadoPor] [varchar](255) NULL;


--changeset ggiraldo:04
--comment: Creación de la tabla que almacena la informacion de las fichas de control de los reportes generados

CREATE TABLE [dbo].[FichaControlReportesNormativos](
	[fcId] [int] IDENTITY(1,1) PRIMARY KEY,
	[fcSolicitudReporteNormativoId] [int] FOREIGN KEY REFERENCES SolicitudesReportesNormativos(srId),
	[fcNombreArchivo] [varchar](255) NULL,
	[fcFechaGeneracion] [datetime] NULL,
	[fcVersionDocumento] [varchar](255) NULL,
	[fcIdentificacionDocumento] [varchar](255) NULL
) ON [PRIMARY];