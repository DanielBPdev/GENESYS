--liquibase formatted sql

--changeset oocampo:01
--comment: tabla para almacenar resultado de generacion de archivo de transaccion y detalles
CREATE TABLE [dbo].[ArchivoTransDetaSubsidio](
	[atdsId] [bigint] IDENTITY(1,1) NOT NULL,
	[atdsIdentificadorECM] [varchar](255) NULL,
	[atdsNombreArchivoECM] [varchar](255) NULL,
	[atdsFechaGeneracion] [datetime] NULL,
	[atdsPorcentajeAvance] [int] NULL,
	[atdsEstado] [varchar](255) NULL,
	[atdsTipoArchivo] [varchar](255) NULL,
	[atdsLog] [varchar](500) NULL,
 CONSTRAINT [PK_ArchivoTransDetaSubsidio_atdsId] PRIMARY KEY CLUSTERED 
(
	[atdsId] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO