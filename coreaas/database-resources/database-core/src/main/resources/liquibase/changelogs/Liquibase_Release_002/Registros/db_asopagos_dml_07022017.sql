--liquibase formatted sql


--changeset atoro:01
--comment: eliminacion tabla Novedad

DROP TABLE Novedad

--changeset  atoro:02
--comment:Creacion de entidad novedades

--Creación IntentoNovedadRequisito
GO
/****** Object:  Table [dbo].[IntentoNoveRequisito]    Script Date: 06/02/2017 16:56:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[IntentoNoveRequisito](
	[inrId] [bigint] IDENTITY(1,1) NOT NULL,
	[inrRequisito] [bigint] NULL,
	[inrIntentoNovedad] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[inrId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF

--Creación Intento de Novedad
GO
/****** Object:  Table [dbo].[ItentoNovedad]    Script Date: 06/02/2017 16:56:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ItentoNovedad](
	[inoId] [bigint] IDENTITY(1,1) NOT NULL,
	[inoCausaIntentoFalido] [varchar](255) NULL,
	[inoFechaInicioProceso] [datetime2](7) NULL,
	[inoSolicitud] [bigint] NULL,
	[inoTipoTransaccion] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[inoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF

--Creación Novedad
GO
/****** Object:  Table [dbo].[Novedad]    Script Date: 06/02/2017 16:56:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [dbo].[Novedad](
	[novId] [bigint] IDENTITY(1,1) NOT NULL
) ON [PRIMARY]
SET ANSI_PADDING ON
ALTER TABLE [dbo].[Novedad] ADD [novTipoTransaccion] [varchar](255) NULL
ALTER TABLE [dbo].[Novedad] ADD [novPuntoResolucion] [varchar](255) NULL
ALTER TABLE [dbo].[Novedad] ADD [novRutaCualificada] [varchar](255) NULL
ALTER TABLE [dbo].[Novedad] ADD [novTipoNovedad] [varchar](255) NULL
 CONSTRAINT [PK_Novedad_novId] PRIMARY KEY CLUSTERED 
(
	[novId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO
SET ANSI_PADDING OFF

--Creación Solicitud de novedad
GO
/****** Object:  Table [dbo].[SolicitudNovedad]    Script Date: 06/02/2017 16:56:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SolicitudNovedad](
	[snoIdSolicitudNovedad] [bigint] IDENTITY(1,1) NOT NULL,
	[snoEstadoSolicitud] [varchar](255) NULL,
	[snoNovedad] [bigint] NULL,
	[snoSolicitudGlobal] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[snoIdSolicitudNovedad] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[IntentoNoveRequisito]  WITH CHECK ADD  CONSTRAINT [FK_IntentoNoveRequisito_inrIntentoNovedad] FOREIGN KEY([inrIntentoNovedad])
REFERENCES [dbo].[ItentoNovedad] ([inoId])
GO
ALTER TABLE [dbo].[IntentoNoveRequisito] CHECK CONSTRAINT [FK_IntentoNoveRequisito_inrIntentoNovedad]
GO

ALTER TABLE [dbo].[SolicitudNovedad]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedad_snoSolicitudGlobal] FOREIGN KEY([snoSolicitudGlobal])
REFERENCES [dbo].[Solicitud] ([solId])
GO
ALTER TABLE [dbo].[SolicitudNovedad] CHECK CONSTRAINT [FK_SolicitudNovedad_snoSolicitudGlobal]

--changeset jcamargo:03
--comment: Se agregan nuevos campos a la tabla Empleador

ALTER TABLE Empleador ADD empBeneficioLey2429Activo bit;
ALTER TABLE Empleador ADD empBeneficioLey590Activo bit;
ALTER TABLE Empleador ADD empAnoInicioBeneficioLey1429 date;
ALTER TABLE Empleador ADD empAnoFinBeneficioLey1429 date;
ALTER TABLE Empleador ADD empPeriodoInicioBeneficioLey590 date;
ALTER TABLE Empleador ADD empPeriodoFinBeneficioLey590 date;
ALTER TABLE Empleador ADD empMotivoInactivacionBeneficioLey1429 varchar(100);
ALTER TABLE Empleador ADD empMotivoInactivacionBeneficioLey590 varchar(100);

--changeset juagonzalez:04
--comment: Se elimina dato con constraint que no existe

delete from RequisitoCajaClasificacion where rtsTipoTransaccion='AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO' 




