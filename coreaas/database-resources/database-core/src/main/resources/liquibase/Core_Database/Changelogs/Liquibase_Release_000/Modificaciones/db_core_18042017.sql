--liquibase formatted sql

--changeset jzambrano:01 
--comment: Creacion de tablas 

--Creacion de [GraphicFeature] 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF OBJECT_ID('GraphicFeatureDefinition', 'U') IS NOT NULL DROP TABLE GraphicFeatureDefinition;
IF OBJECT_ID('GraphicFeature', 'U') IS NOT NULL DROP TABLE GraphicFeature; 
CREATE TABLE [GraphicFeature](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[dataType] [varchar](255) NOT NULL,
	[defaultValue] [varchar](255) NOT NULL,
	[description] [varchar](255) NULL,
	[fileFormat] [varchar](255) NOT NULL,
	[name] [varchar](100) NOT NULL,
	[restrictions] [varchar](255) NULL,
 CONSTRAINT [PK_GraphicFeature_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

--Creacion de [GraphicFeatureDefinition]    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

CREATE TABLE [GraphicFeatureDefinition](
	[id] [bigint] NOT NULL,
	[detail] [bit] NULL,
	[footer] [bit] NULL,
	[header] [bit] NULL,
	[value] [varchar](255) NULL,
	[fieldDefinition_id] [bigint] NULL,
	[fileDefinition_id] [bigint] NULL,
	[graphicFeature_id] [bigint] NULL,
	[lineDefinition_id] [bigint] NULL,
 CONSTRAINT [PK_GraphicFeatureDefinition_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

--Creacion de FK
ALTER TABLE [GraphicFeatureDefinition]  WITH CHECK ADD  CONSTRAINT [FK_GraphicFeatureDefinition_fieldDefinition_id] FOREIGN KEY([fieldDefinition_id])
REFERENCES [FieldDefinition] ([id])
GO
ALTER TABLE [GraphicFeatureDefinition] CHECK CONSTRAINT [FK_GraphicFeatureDefinition_fieldDefinition_id]
GO
ALTER TABLE [GraphicFeatureDefinition]  WITH CHECK ADD  CONSTRAINT [FK_GraphicFeatureDefinition_fileDefinition_id] FOREIGN KEY([fileDefinition_id])
REFERENCES [FileDefinition] ([id])
GO
ALTER TABLE [GraphicFeatureDefinition] CHECK CONSTRAINT [FK_GraphicFeatureDefinition_fileDefinition_id]
GO
ALTER TABLE [GraphicFeatureDefinition]  WITH CHECK ADD  CONSTRAINT [FK_GraphicFeatureDefinition_graphicFeature_id] FOREIGN KEY([graphicFeature_id])
REFERENCES [GraphicFeature] ([id])
GO
ALTER TABLE [GraphicFeatureDefinition] CHECK CONSTRAINT [FK_GraphicFeatureDefinition_graphicFeature_id]
GO
ALTER TABLE [GraphicFeatureDefinition]  WITH CHECK ADD  CONSTRAINT [FK_GraphicFeatureDefinition_lineDefinition_id] FOREIGN KEY([lineDefinition_id])
REFERENCES [LineDefinition] ([id])
GO
ALTER TABLE [GraphicFeatureDefinition] CHECK CONSTRAINT [FK_GraphicFeatureDefinition_lineDefinition_id]
GO

--changeset atoro:02
--comment: Borrado de FK en la tabla GrupoFamiliar y tamaño del campo
ALTER TABLE GrupoFamiliar DROP CONSTRAINT FK_GrupoFamiliar_grfAdministradorSubsidio;
ALTER TABLE GrupoFamiliar ALTER COLUMN grfAdministradorSubsidio varchar(16); 

--changeset atoro:03
--comment: Borrado de FK en la tabla GrupoFamiliar y tamaño del campo
ALTER TABLE GrupoFamiliar ALTER COLUMN grfAdministradorSubsidio bigint;
ALTER TABLE GrupoFamiliar WITH CHECK ADD CONSTRAINT FK_GrupoFamiliar_grfAdministradorSubsidio FOREIGN KEY (grfAdministradorSubsidio) REFERENCES Persona (perId);
ALTER TABLE GrupoFamiliar DROP CONSTRAINT FK_GrupoFamiliar_grfRelacionGrupoFamiliar;
ALTER TABLE GrupoFamiliar ALTER COLUMN grfRelacionGrupoFamiliar varchar(16); 

--changeset ogiral:04
--comment: Borrado de los registros de la tabla VariableComunicado
DELETE FROM VariableComunicado

--changeset atoro:05
--comment: Se agregan nuevos campos en la tabla RolAfiliado y en Afiliado
ALTER TABLE RolAfiliado ADD roaEstadoEnEntidadPagadoraPension varchar(20) NULL;
ALTER TABLE Afiliado ADD afiFechaInicioServiciosSinAfiliacion date NULL;
ALTER TABLE Afiliado ADD afifechaFinServicioSinAfiliacion date NULL;

--changeset jusanchez:06
--comment: Creacion de la tabla CargueMultipleSupervivencia
   CREATE TABLE CargueMultipleSupervivencia (
   cmsId bigint IDENTITY(1,1) NOT NULL,
   cmsFechaIngreso date NOT NULL,
   cmsUsuario varchar(255) NOT NULL,
   cmsPeriodo date NULL,
   cmsIdentificacionECM varchar(255) NOT NULL,
   cmsEstadoCargueSupervivencia varchar(255) NOT NULL
   CONSTRAINT PK_CargueMultipleSupervivencia_cmsId PRIMARY KEY (cmsId)
   );

 