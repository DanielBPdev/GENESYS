--liquibase formatted sql

--changeset  halzate:01
--comment: 02/11/2016-halzate-Creación de tablas para el framework Lion
/****** Object:  Table [dbo].[FieldCatalog]    Script Date: 26/10/2016 12:07:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FieldCatalog](
	[id] [bigint] NOT NULL,
	[dataType] [varchar](255) NOT NULL,
	[description] [varchar](255) NULL,
	[maxDecimalSize] [bigint] NULL,
	[minDecimalSize] [bigint] NULL,
	[name] [varchar](70) NOT NULL,
	[label] [varchar](255) NULL,
	[paginated] [bit] NULL,
	[query] [varchar](255) NULL,
	[roundMode] [bigint] NULL,
	[fieldGCatalog_id] [bigint] NULL,
	[idLineCatalog] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FieldDefinition]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FieldDefinition](
	[id] [bigint] NOT NULL,
	[finalPosition] [bigint] NULL,
	[formatDate] [varchar](40) NULL,
	[initialPosition] [bigint] NULL,
	[label] [varchar](70) NULL,
	[fieldOrder] [bigint] NULL,
	[footerOperation] [varchar](255) NULL,
	[generateEvidence] [bit] NULL,
	[fieldCatalog_id] [bigint] NULL,
	[lineDefinition_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FieldGenericCatalog]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FieldGenericCatalog](
	[id] [bigint] NOT NULL,
	[dataType] [int] NULL,
	[description] [varchar](255) NULL,
	[label] [varchar](255) NULL,
	[maxDecimalSize] [bigint] NULL,
	[minDecimalSize] [bigint] NULL,
	[name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FileDefinition]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FileDefinition](
	[id] [bigint] NOT NULL,
	[decimalSeparator] [char](1) NOT NULL,
	[nombre] [varchar](70) NULL,
	[tenantId] [bigint] NULL,
	[thousandsSeparator] [char](1) NULL,
	[activeHistoric] [bit] NULL,
	[compressAll] [bit] NULL,
	[compressEachFile] [bit] NULL,
	[creationDate] [datetime2](7) NULL,
	[encryptedFileExtension] [varchar](150) NULL,
	[encrypterClass] [varchar](150) NULL,
	[finalConditionsClass] [varchar](150) NULL,
	[nextLineSeparator] [varchar](20) NULL,
	[registersBlockSize] [bigint] NULL,
	[signedFileExtension] [varchar](150) NULL,
	[signerClass] [varchar](150) NULL,
	[fileDefinitionType_id] [bigint] NULL,
	[fileLocation_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FileDefinitionType]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FileDefinitionType](
	[id] [bigint] NOT NULL,
	[description] [varchar](255) NULL,
	[name] [varchar](70) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FileGenerated]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FileGenerated](
	[id] [bigint] NOT NULL,
	[finalDate] [datetime2](7) NULL,
	[initialDate] [datetime2](7) NULL,
	[formats] [varchar](255) NULL,
	[registersGenerated] [bigint] NULL,
	[state] [varchar](255) NULL,
	[userName] [varchar](255) NULL,
	[fileDefinition_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FileGeneratedLog]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FileGeneratedLog](
	[id] [bigint] NOT NULL,
	[lineNumber] [bigint] NULL,
	[message] [varchar](max) NULL,
	[fileGenerated_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FileLocationCommon]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FileLocationCommon](
	[id] [bigint] NOT NULL,
	[contrasena] [varchar](70) NULL,
	[host] [varchar](50) NULL,
	[localPath] [varchar](100) NULL,
	[multiplesFiles] [bit] NULL,
	[overwriteFile] [bit] NULL,
	[protocolo] [int] NULL,
	[puerto] [varchar](10) NULL,
	[remotePath] [varchar](100) NULL,
	[tempLocalPath] [varchar](255) NULL,
	[usuario] [varchar](70) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GraphicFeature]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GraphicFeature](
	[id] [bigint] NOT NULL,
	[dataType] [varchar](255) NOT NULL,
	[defaultValue] [varchar](255) NOT NULL,
	[description] [varchar](255) NULL,
	[fileFormat] [varchar](255) NOT NULL,
	[name] [varchar](100) NOT NULL,
	[restrictions] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GraphicFeatureDefinition]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GraphicFeatureDefinition](
	[id] [bigint] NOT NULL,
	[detail] [bit] NULL,
	[footer] [bit] NULL,
	[header] [bit] NULL,
	[value] [varchar](255) NULL,
	[fieldDefinition_id] [bigint] NULL,
	[fileDefinition_id] [bigint] NULL,
	[graphicFeature_id] [bigint] NULL,
	[lineDefinition_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GroupFieldCatalogs]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GroupFieldCatalogs](
	[id] [bigint] NOT NULL,
	[description] [varchar](255) NULL,
	[name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LineCatalog]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LineCatalog](
	[id] [bigint] NOT NULL,
	[className] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[name] [varchar](70) NOT NULL,
	[tenantId] [bigint] NULL,
	[paginated] [bit] NOT NULL,
	[query] [varchar](max) NULL,
	[queryType] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LineDefinition]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LineDefinition](
	[id] [bigint] NOT NULL,
	[alternateDetail] [bit] NULL,
	[generateLineFooter] [bit] NULL,
	[generaterHeaderLine] [bit] NULL,
	[lineOrder] [bigint] NULL,
	[masterField] [varchar](255) NULL,
	[masterFieldReference] [varchar](255) NULL,
	[masterLine] [bit] NULL,
	[numberGroup] [bigint] NULL,
	[parentLine] [bigint] NULL,
	[query] [varchar](255) NULL,
	[required] [bit] NULL,
	[fileDefinition_id] [bigint] NULL,
	[lineCatalog_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ProcessorCatalog]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ProcessorCatalog](
	[id] [bigint] NOT NULL,
	[className] [varchar](100) NULL,
	[description] [varchar](255) NULL,
	[name] [varchar](70) NULL,
	[scope] [varchar](255) NULL,
	[tenantId] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ProcessorDefinition]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProcessorDefinition](
	[id] [bigint] NOT NULL,
	[fieldDefinitionId] [bigint] NULL,
	[fieldDefinitionLoadId] [bigint] NULL,
	[lineDefinitionId] [bigint] NULL,
	[lineDefinitionLoadId] [bigint] NULL,
	[processorOrder] [bigint] NULL,
	[processorCatalog_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ProcessorParameter]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ProcessorParameter](
	[id] [bigint] NOT NULL,
	[dataType] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[mask] [varchar](100) NULL,
	[name] [varchar](70) NULL,
	[processorCatalog_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ProcessorParamValue]    Script Date: 26/10/2016 12:07:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ProcessorParamValue](
	[id] [bigint] NOT NULL,
	[value] [varchar](100) NULL,
	[processorDefinition_id] [bigint] NULL,
	[processorParameter_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[FieldCatalog]  WITH CHECK ADD  CONSTRAINT [FK9x09l23psfgb5nx64hb6bnrnj] FOREIGN KEY([fieldGCatalog_id])
REFERENCES [dbo].[FieldGenericCatalog] ([id])
GO
ALTER TABLE [dbo].[FieldCatalog] CHECK CONSTRAINT [FK9x09l23psfgb5nx64hb6bnrnj]
GO
ALTER TABLE [dbo].[FieldCatalog]  WITH CHECK ADD  CONSTRAINT [FKifk7ca5onvs5tabqsarnougt4] FOREIGN KEY([idLineCatalog])
REFERENCES [dbo].[LineCatalog] ([id])
GO
ALTER TABLE [dbo].[FieldCatalog] CHECK CONSTRAINT [FKifk7ca5onvs5tabqsarnougt4]
GO
ALTER TABLE [dbo].[FieldDefinition]  WITH CHECK ADD  CONSTRAINT [FK9icd6f6rd5q1ypq6l6fk3obdo] FOREIGN KEY([lineDefinition_id])
REFERENCES [dbo].[LineDefinition] ([id])
GO
ALTER TABLE [dbo].[FieldDefinition] CHECK CONSTRAINT [FK9icd6f6rd5q1ypq6l6fk3obdo]
GO
ALTER TABLE [dbo].[FieldDefinition]  WITH CHECK ADD  CONSTRAINT [FK9sfnsi9p2knem27cygpno78hm] FOREIGN KEY([fieldCatalog_id])
REFERENCES [dbo].[FieldCatalog] ([id])
GO
ALTER TABLE [dbo].[FieldDefinition] CHECK CONSTRAINT [FK9sfnsi9p2knem27cygpno78hm]
GO
ALTER TABLE [dbo].[FileDefinition]  WITH CHECK ADD  CONSTRAINT [FK7d5bgelfa9yqkupm47pm4v282] FOREIGN KEY([fileDefinitionType_id])
REFERENCES [dbo].[FileDefinitionType] ([id])
GO
ALTER TABLE [dbo].[FileDefinition] CHECK CONSTRAINT [FK7d5bgelfa9yqkupm47pm4v282]
GO
ALTER TABLE [dbo].[FileDefinition]  WITH CHECK ADD  CONSTRAINT [FKsec52q33c5ocgga6xjhoniy2q] FOREIGN KEY([fileLocation_id])
REFERENCES [dbo].[FileLocationCommon] ([id])
GO
ALTER TABLE [dbo].[FileDefinition] CHECK CONSTRAINT [FKsec52q33c5ocgga6xjhoniy2q]
GO
ALTER TABLE [dbo].[FileGenerated]  WITH CHECK ADD  CONSTRAINT [FKdyb4nqogc64lup89xhni6jwaw] FOREIGN KEY([fileDefinition_id])
REFERENCES [dbo].[FileDefinition] ([id])
GO
ALTER TABLE [dbo].[FileGenerated] CHECK CONSTRAINT [FKdyb4nqogc64lup89xhni6jwaw]
GO
ALTER TABLE [dbo].[FileGeneratedLog]  WITH CHECK ADD  CONSTRAINT [FKseyp6vql8y2w7pjcgoxhnb46r] FOREIGN KEY([fileGenerated_id])
REFERENCES [dbo].[FileGenerated] ([id])
GO
ALTER TABLE [dbo].[FileGeneratedLog] CHECK CONSTRAINT [FKseyp6vql8y2w7pjcgoxhnb46r]
GO
ALTER TABLE [dbo].[GraphicFeatureDefinition]  WITH CHECK ADD  CONSTRAINT [FKjqcwbyr5pvikv0xwdt9gv9kyq] FOREIGN KEY([lineDefinition_id])
REFERENCES [dbo].[LineDefinition] ([id])
GO
ALTER TABLE [dbo].[GraphicFeatureDefinition] CHECK CONSTRAINT [FKjqcwbyr5pvikv0xwdt9gv9kyq]
GO
ALTER TABLE [dbo].[GraphicFeatureDefinition]  WITH CHECK ADD  CONSTRAINT [FKo9pb92o3ipmjkyb1pb6p6owbl] FOREIGN KEY([fieldDefinition_id])
REFERENCES [dbo].[FieldDefinition] ([id])
GO
ALTER TABLE [dbo].[GraphicFeatureDefinition] CHECK CONSTRAINT [FKo9pb92o3ipmjkyb1pb6p6owbl]
GO
ALTER TABLE [dbo].[GraphicFeatureDefinition]  WITH CHECK ADD  CONSTRAINT [FKtih62k3if4mlvrtuvoua2mfu0] FOREIGN KEY([graphicFeature_id])
REFERENCES [dbo].[GraphicFeature] ([id])
GO
ALTER TABLE [dbo].[GraphicFeatureDefinition] CHECK CONSTRAINT [FKtih62k3if4mlvrtuvoua2mfu0]
GO
ALTER TABLE [dbo].[GraphicFeatureDefinition]  WITH CHECK ADD  CONSTRAINT [FKtqa9kdy81au31nhyh39tv5j5x] FOREIGN KEY([fileDefinition_id])
REFERENCES [dbo].[FileDefinition] ([id])
GO
ALTER TABLE [dbo].[GraphicFeatureDefinition] CHECK CONSTRAINT [FKtqa9kdy81au31nhyh39tv5j5x]
GO
ALTER TABLE [dbo].[LineDefinition]  WITH CHECK ADD  CONSTRAINT [FK7b9199swgogisrth6watqix82] FOREIGN KEY([fileDefinition_id])
REFERENCES [dbo].[FileDefinition] ([id])
GO
ALTER TABLE [dbo].[LineDefinition] CHECK CONSTRAINT [FK7b9199swgogisrth6watqix82]
GO
ALTER TABLE [dbo].[LineDefinition]  WITH CHECK ADD  CONSTRAINT [FK7he7vvf12wtgunxuxv69cjch3] FOREIGN KEY([lineCatalog_id])
REFERENCES [dbo].[LineCatalog] ([id])
GO
ALTER TABLE [dbo].[LineDefinition] CHECK CONSTRAINT [FK7he7vvf12wtgunxuxv69cjch3]
GO
ALTER TABLE [dbo].[ProcessorDefinition]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorDefinition_processorCatalog_id] FOREIGN KEY([processorCatalog_id])
REFERENCES [dbo].[ProcessorCatalog] ([id])
GO
ALTER TABLE [dbo].[ProcessorDefinition] CHECK CONSTRAINT [FK_ProcessorDefinition_processorCatalog_id]
GO
ALTER TABLE [dbo].[ProcessorDefinition]  WITH CHECK ADD  CONSTRAINT [FK6pt77ur0s9812dyulxpkwr23s] FOREIGN KEY([processorCatalog_id])
REFERENCES [dbo].[ProcessorCatalog] ([id])
GO
ALTER TABLE [dbo].[ProcessorDefinition] CHECK CONSTRAINT [FK6pt77ur0s9812dyulxpkwr23s]
GO
ALTER TABLE [dbo].[ProcessorParameter]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorParameter_processorCatalog_id] FOREIGN KEY([processorCatalog_id])
REFERENCES [dbo].[ProcessorCatalog] ([id])
GO
ALTER TABLE [dbo].[ProcessorParameter] CHECK CONSTRAINT [FK_ProcessorParameter_processorCatalog_id]
GO
ALTER TABLE [dbo].[ProcessorParameter]  WITH CHECK ADD  CONSTRAINT [FKe3csec2fub8fu6l0ie3y5pub] FOREIGN KEY([processorCatalog_id])
REFERENCES [dbo].[ProcessorCatalog] ([id])
GO
ALTER TABLE [dbo].[ProcessorParameter] CHECK CONSTRAINT [FKe3csec2fub8fu6l0ie3y5pub]
GO
ALTER TABLE [dbo].[ProcessorParamValue]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorParamValue_processorDefinition_id] FOREIGN KEY([processorDefinition_id])
REFERENCES [dbo].[ProcessorDefinition] ([id])
GO
ALTER TABLE [dbo].[ProcessorParamValue] CHECK CONSTRAINT [FK_ProcessorParamValue_processorDefinition_id]
GO
ALTER TABLE [dbo].[ProcessorParamValue]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorParamValue_processorParameter_id] FOREIGN KEY([processorParameter_id])
REFERENCES [dbo].[ProcessorParameter] ([id])
GO
ALTER TABLE [dbo].[ProcessorParamValue] CHECK CONSTRAINT [FK_ProcessorParamValue_processorParameter_id]
GO
ALTER TABLE [dbo].[ProcessorParamValue]  WITH CHECK ADD  CONSTRAINT [FK3iq3cfb1t75gk0t8gly1yh176] FOREIGN KEY([processorParameter_id])
REFERENCES [dbo].[ProcessorParameter] ([id])
GO
ALTER TABLE [dbo].[ProcessorParamValue] CHECK CONSTRAINT [FK3iq3cfb1t75gk0t8gly1yh176]
GO
ALTER TABLE [dbo].[ProcessorParamValue]  WITH CHECK ADD  CONSTRAINT [FK9il68rsjftao6v6l3p1fp22lo] FOREIGN KEY([processorDefinition_id])
REFERENCES [dbo].[ProcessorDefinition] ([id])
GO
ALTER TABLE [dbo].[ProcessorParamValue] CHECK CONSTRAINT [FK9il68rsjftao6v6l3p1fp22lo]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GroupFC_FieldGC](
	[fieldGenericCatalogs_id] [bigint] NOT NULL,
	[groupFieldCatalogs_id] [bigint] NOT NULL
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[GroupFC_FieldGC]  WITH CHECK ADD  CONSTRAINT [FK14p6ljxtgj7ocpfno5ilqt0cb] FOREIGN KEY([groupFieldCatalogs_id])
REFERENCES [dbo].[GroupFieldCatalogs] ([id])
GO
ALTER TABLE [dbo].[GroupFC_FieldGC] CHECK CONSTRAINT [FK14p6ljxtgj7ocpfno5ilqt0cb]
GO
ALTER TABLE [dbo].[GroupFC_FieldGC]  WITH CHECK ADD  CONSTRAINT [FKghg21q8ypa1ccagxpea1c0qyr] FOREIGN KEY([fieldGenericCatalogs_id])
REFERENCES [dbo].[FieldGenericCatalog] ([id])
GO
ALTER TABLE [dbo].[GroupFC_FieldGC] CHECK CONSTRAINT [FKghg21q8ypa1ccagxpea1c0qyr]
GO
CREATE SEQUENCE [hibernate_sequence] START WITH 1 INCREMENT BY 1 ;




Insert into FILEDEFINITIONTYPE (ID,DESCRIPTION,NAME) 
values ('1','Estructura de los registros está dada para el convenio de la caja de compensación Confa y Colpensiones.','Estructura Solicitud de Alta');
Insert into FILEDEFINITION (ID,DECIMALSEPARATOR,NOMBRE,TENANTID,THOUSANDSSEPARATOR,COMPRESSALL,COMPRESSEACHFILE,ENCRYPTEDFILEEXTENSION,ENCRYPTERCLASS,FINALCONDITIONSCLASS,NEXTLINESEPARATOR,SIGNEDFILEEXTENSION,SIGNERCLASS,FILEDEFINITIONTYPE_ID,FILELOCATION_ID) 
values ('1',',',null,null,'.','0','0',null,null,null,'\r\n',null,null,'1',null);

Insert into LINECATALOG (ID,CLASSNAME,DESCRIPTION,NAME,TENANTID,PAGINATED,QUERY,QUERYTYPE) 
values ('1','com.asopagos.entidades.pagadoras.dto.EstructuraSolicitudAltaRetiroDataLine','Estructura Solicitud de Alta','Estructura Solicitud de Alta',null,'0',null,null);
Insert into LINEDEFINITION (ID,GENERATELINEFOOTER,GENERATERHEADERLINE,LINEORDER,NUMBERGROUP,REQUIRED,FILEDEFINITION_ID,LINECATALOG_ID) 
values ('1','0','0','1',null,'1','1','1');


Insert into FIELDCATALOG (ID,DATATYPE,DESCRIPTION,MAXDECIMALSIZE,MINDECIMALSIZE,NAME,PAGINATED,QUERY,ROUNDMODE,IDLINECATALOG) 
values ('1','STRING','No. Afiliación',null,null,'numeroAfiliacion',null,null,null,'1');
Insert into FIELDCATALOG (ID,DATATYPE,DESCRIPTION,MAXDECIMALSIZE,MINDECIMALSIZE,NAME,PAGINATED,QUERY,ROUNDMODE,IDLINECATALOG) 
values ('2','STRING','No. de Documento',null,null,'nroDocumento',null,null,null,'1');
Insert into FIELDCATALOG (ID,DATATYPE,DESCRIPTION,MAXDECIMALSIZE,MINDECIMALSIZE,NAME,PAGINATED,QUERY,ROUNDMODE,IDLINECATALOG) 
values ('3','STRING','NIT. de Entidad',null,null,'nitDeEntidad',null,null,null,'1');
Insert into FIELDCATALOG (ID,DATATYPE,DESCRIPTION,MAXDECIMALSIZE,MINDECIMALSIZE,NAME,PAGINATED,QUERY,ROUNDMODE,IDLINECATALOG) 
values ('4','STRING','Tipo Novedad',null,null,'tipoNovedad',null,null,null,'1');


Insert into FIELDDEFINITION (ID,INITIALPOSITION,FINALPOSITION,LABEL,FIELDORDER,FORMATDATE,FOOTEROPERATION,GENERATEEVIDENCE,FIELDCATALOG_ID,LINEDEFINITION_ID) 
values ('1',1,13,'No. Afiliación','1',null,'NONE','0','1','1');
Insert into FIELDDEFINITION (ID,INITIALPOSITION,FINALPOSITION,LABEL,FIELDORDER,FORMATDATE,FOOTEROPERATION,GENERATEEVIDENCE,FIELDCATALOG_ID,LINEDEFINITION_ID) 
values ('2',13,25,'No. de Documento','2',null,'NONE','0','2','1');
Insert into FIELDDEFINITION (ID,INITIALPOSITION,FINALPOSITION,LABEL,FIELDORDER,FORMATDATE,FOOTEROPERATION,GENERATEEVIDENCE,FIELDCATALOG_ID,LINEDEFINITION_ID) 
values ('3',25,34,'NIT. de Entidad','3',null,'NONE','0','3','1');
Insert into FIELDDEFINITION (ID,INITIALPOSITION,FINALPOSITION,LABEL,FIELDORDER,FORMATDATE,FOOTEROPERATION,GENERATEEVIDENCE,FIELDCATALOG_ID,LINEDEFINITION_ID) 
values ('4',34,35,'Tipo Novedad','4',null,'NONE','0','4','1');