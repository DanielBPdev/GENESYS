--liquibase formatted sql

--changeset Heinsohn:01
--comment: Creación de tablas LogAuditoria


-- Creación de tabla [Revision]
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Revision](
	[revId] [int] IDENTITY(1,1) NOT NULL,
	[revIp] [varchar](255) NULL,
	[revNombreUsuario] [varchar](255) NULL,
	[revRequestId] [varchar](255) NULL,
	[revTimeStamp] [bigint] NULL,
CONSTRAINT [PK_Revision_revId] PRIMARY KEY CLUSTERED 
(
	[revId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [LogAuditoriaConsulta]    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LogAuditoriaConsulta](
	[lacId] [bigint] IDENTITY(1,1) NOT NULL,
	[lacEntityClassName] [varchar](255) NULL,
	[lacIp] [varchar](255) NULL,
	[lacJson] [text] NULL,
	[lacNameEntity] [varchar](255) NULL,
	[lacNombreUsuario] [varchar](255) NULL,
	[lacRequestId] [varchar](255) NULL,
	[lacTimeStamp] [bigint] NULL,
 CONSTRAINT [PK_LogAuditoriaConsulta_lacId] PRIMARY KEY CLUSTERED 
(
	[lacId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [RevisionEntidad]  
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[RevisionEntidad](
	[reeId] [int] IDENTITY(1,1) NOT NULL,
	[reeEntityClassName] [varchar](255) NULL,
	[reeRevisionType] [int] NULL,
	[reeTimeStamp] [bigint] NULL,
	[reeRevision] [int] NOT NULL,
CONSTRAINT [PK_RevisionEntidad_reeId] PRIMARY KEY CLUSTERED 
(
	[reeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [ParametrizacionTablaAuditable]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ParametrizacionTablaAuditable](
	[ptaId] [int] IDENTITY(1,1) NOT NULL,
	[ptaActualizar] [bit] NULL,
	[ptaConsultar] [bit] NULL,
	[ptaCrear] [bit] NULL,
	[ptaEntityClassName] [varchar](255) NULL,
	[ptaNombreTabla] [varchar](255) NULL,
CONSTRAINT [PK_ParametrizacionTablaAuditable_ptaId] PRIMARY KEY CLUSTERED 
(
	[ptaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO


ALTER TABLE [RevisionEntidad]  WITH CHECK ADD  CONSTRAINT [FK_RevisionEntidad_reeRevision] FOREIGN KEY([reeRevision])
REFERENCES [Revision] ([revId])
GO

ALTER TABLE [RevisionEntidad] CHECK CONSTRAINT [FK_RevisionEntidad_reeRevision]
GO

