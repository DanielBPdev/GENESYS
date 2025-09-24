--liquibase formatted sql

--changeset jamartinez:01
--comment: Ajustes constrains tabla CruceDetalle  

IF  EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_NAME='CK_CruceDetalle_crdTipo')
	ALTER TABLE [dbo].[CruceDetalle] DROP CONSTRAINT [CK_CruceDetalle_crdTipo]

IF  EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_NAME='CK_CruceDetalle_crdClasificacion')
	ALTER TABLE [dbo].[CruceDetalle] DROP CONSTRAINT [CK_CruceDetalle_crdClasificacion]

IF  EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_NAME='CK_CruceDetalle_crdCausalCruce')
	ALTER TABLE [dbo].[CruceDetalle] DROP CONSTRAINT [CK_CruceDetalle_crdCausalCruce]

ALTER TABLE [dbo].[CruceDetalle]  WITH CHECK ADD  CONSTRAINT [CK_CruceDetalle_crdCausalCruce] CHECK  (([crdCausalCruce]='REPORTE_REUNIDOS' OR [crdCausalCruce]='REPORTE_UNIDOS' OR [crdCausalCruce]='REPORTE_SISBEN' OR [crdCausalCruce]='CONFORMO_NUEVO_HOGAR' OR [crdCausalCruce]='REGISTRO_PROPIEDAD_IGAC' OR [crdCausalCruce]='REGISTRO_PROPIEDAD_MED' OR [crdCausalCruce]='REGISTRO_PROPIEDAD_CALI' OR [crdCausalCruce]='REGISTRO_PROPIEDAD_BOG' OR [crdCausalCruce]='REGISTRO_PROPIEDAD_ANT' OR [crdCausalCruce]='BENEFICIO_SUBSIDIO_RECIBIDO' OR [crdCausalCruce]='AFILIACION_CAJA' OR [crdCausalCruce]='REGISTRO_PROPIEDAD' OR [crdCausalCruce]='BENEFICIO_SUBSIDIO_RECIBIDO_AR'))
ALTER TABLE [dbo].[CruceDetalle] CHECK CONSTRAINT [CK_CruceDetalle_crdCausalCruce]
ALTER TABLE [dbo].[CruceDetalle]  WITH CHECK ADD  CONSTRAINT [CK_CruceDetalle_crdClasificacion] CHECK  (([crdClasificacion]='YERNO_HOGAR' OR [crdClasificacion]='TIO_HOGAR' OR [crdClasificacion]='SUEGRO_HOGAR' OR [crdClasificacion]='SOBRINO_HOGAR' OR [crdClasificacion]='NIETO_HOGAR' OR [crdClasificacion]='PADRE_HOGAR' OR [crdClasificacion]='PADRE_MADRE_ADOPTANTE_HOGAR' OR [crdClasificacion]='MADRE_HOGAR' OR [crdClasificacion]='HERMANO_HOGAR' OR [crdClasificacion]='HIJO_ADOPTIVO_HOGAR' OR [crdClasificacion]='HIJASTRO_HOGAR' OR [crdClasificacion]='HIJO_BIOLOGICO_HOGAR' OR [crdClasificacion]='CUNIADO_HOGAR' OR [crdClasificacion]='BISNIETO_HOGAR' OR [crdClasificacion]='BISABUELO_HOGAR' OR [crdClasificacion]='ABUELO_HOGAR' OR [crdClasificacion]='CONYUGE_HOGAR' OR [crdClasificacion]='JEFE_HOGAR' OR [crdClasificacion]='HOGAR' OR [crdClasificacion]='GRUPO_FAMILIAR' OR [crdClasificacion]='BENEFICIARIO_EN_CUSTODIA' OR [crdClasificacion]='HERMANO_HUERFANO_DE_PADRES' OR [crdClasificacion]='HIJASTRO' OR [crdClasificacion]='HIJO_ADOPTIVO' OR [crdClasificacion]='HIJO_BIOLOGICO' OR [crdClasificacion]='MADRE' OR [crdClasificacion]='PADRE' OR [crdClasificacion]='CONYUGE' OR [crdClasificacion]='PENSION_FAMILIAR' OR [crdClasificacion]='MAS_1_5_SM_2_POR_CIENTO' OR [crdClasificacion]='MAS_1_5_SM_0_6_POR_CIENTO' OR [crdClasificacion]='MENOS_1_5_SM_2_POR_CIENTO' OR [crdClasificacion]='MENOS_1_5_SM_0_6_POR_CIENTO' OR [crdClasificacion]='MENOS_1_5_SM_0_POR_CIENTO' OR [crdClasificacion]='FIDELIDAD_25_ANIOS' OR [crdClasificacion]='TRABAJADOR_DEPENDIENTE' OR [crdClasificacion]='TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO' OR [crdClasificacion]='TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO' OR [crdClasificacion]='PERSONA' OR [crdClasificacion]='ORGANIZACION_RELIGIOSA_O_PARROQUIA' OR [crdClasificacion]='ENTIDAD_SIN_ANIMO_DE_LUCRO' OR [crdClasificacion]='COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO' OR [crdClasificacion]='PROPIEDAD_HORIZONTAL' OR [crdClasificacion]='EMPLEADOR_DE_SERVICIO_DOMESTICO' OR [crdClasificacion]='PERSONA_NATURAL' OR [crdClasificacion]='PERSONA_JURIDICA'))
ALTER TABLE [dbo].[CruceDetalle] CHECK CONSTRAINT [CK_CruceDetalle_crdClasificacion]
ALTER TABLE [dbo].[CruceDetalle]  WITH CHECK ADD  CONSTRAINT [CK_CruceDetalle_crdTipo] CHECK  (([crdTipo]='REUNIDOS' OR [crdTipo]='UNIDOS' OR [crdTipo]='SISBEN' OR [crdTipo]='NUEVO_HOGAR' OR [crdTipo]='IGAC' OR [crdTipo]='CATASTRO_MED' OR [crdTipo]='CATASTRO_CALI' OR [crdTipo]='CATASTRO_BOG' OR [crdTipo]='CATASTRO_ANT' OR [crdTipo]='BENEFICIARIOS' OR [crdTipo]='AFILIADOS' OR [crdTipo]='CATASTROS' OR [crdTipo]='BENEFICIARIO_AR'))
ALTER TABLE [dbo].[CruceDetalle] CHECK CONSTRAINT [CK_CruceDetalle_crdTipo]

--changeset jamartinez:02
--comment: Ajustes tablas cruces y creación de tablas para BeneficiariosArriendo y Catastros 

ALTER TABLE [dbo].[CargueArchivoCruceFovisUnidos] DROP CONSTRAINT [FK_CargueArchivoCruceFovisUnidos_cfuCargueArchivoCruceFovis]
ALTER TABLE [dbo].[CargueArchivoCruceFovisSisben] DROP CONSTRAINT [FK_CargueArchivoCruceFovisSisben_cfsCargueArchivoCruceFovis]
ALTER TABLE [dbo].[CargueArchivoCruceFovisReunidos] DROP CONSTRAINT [FK_CargueArchivoCruceFovisReunidos_cfrCargueArchivoCruceFovis]
ALTER TABLE [dbo].[CargueArchivoCruceFovisBeneficiario] DROP CONSTRAINT [FK_CargueArchivoCruceFovisBeneficiario_cfbCargueArchivoCruceFovis]
ALTER TABLE [dbo].[CargueArchivoCruceFovisAfiliado] DROP CONSTRAINT [FK_CargueArchivoCruceFovisAfiliado_cfaCargueArchivoCruceFovis]

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CargueArchivoCruceFovisUnidos]') AND type in (N'U'))
DROP TABLE [dbo].[CargueArchivoCruceFovisUnidos]

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CargueArchivoCruceFovisSisben]') AND type in (N'U'))
DROP TABLE [dbo].[CargueArchivoCruceFovisSisben]

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CargueArchivoCruceFovisReunidos]') AND type in (N'U'))
DROP TABLE [dbo].[CargueArchivoCruceFovisReunidos]

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CargueArchivoCruceFovisCatastros]') AND type in (N'U'))
ALTER TABLE [dbo].[CargueArchivoCruceFovisCatastros] DROP CONSTRAINT [FK_CargueArchivoCruceFovisCatastros_cfdCargueArchivoCruceFovis]

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CargueArchivoCruceFovisCatastros]') AND type in (N'U'))
DROP TABLE [dbo].[CargueArchivoCruceFovisCatastros]

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CargueArchivoCruceFovisBeneficiarioArriendo]') AND type in (N'U'))
ALTER TABLE [dbo].[CargueArchivoCruceFovisBeneficiarioArriendo] DROP CONSTRAINT [FK_CargueArchivoCruceFovisBeneficiarioArriendo_cfeCargueArchivoCruceFovis]

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CargueArchivoCruceFovisBeneficiarioArriendo]') AND type in (N'U'))
DROP TABLE [dbo].[CargueArchivoCruceFovisBeneficiarioArriendo]

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CargueArchivoCruceFovisBeneficiario]') AND type in (N'U'))
DROP TABLE [dbo].[CargueArchivoCruceFovisBeneficiario]

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CargueArchivoCruceFovisAfiliado]') AND type in (N'U'))
DROP TABLE [dbo].[CargueArchivoCruceFovisAfiliado]

CREATE TABLE [dbo].[CargueArchivoCruceFovisAfiliado](
	[cfaId] [bigint] NOT NULL,
	[cfaCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfaNitEntidad] [varchar](16) NULL,
	[cfaNombreEntidad] [varchar](100) NULL,
	[cfaTipoIdentificacion] [varchar](100) NULL,
	[cfaIdentificacion] [varchar](16) NULL,
	[cfaApellidos] [varchar](100) NULL,
	[cfaNombres] [varchar](100) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisAfiliado_cfaId] PRIMARY KEY CLUSTERED 
(
	[cfaId] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[CargueArchivoCruceFovisBeneficiario](
	[cfbId] [bigint] NOT NULL,
	[cfbCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfbNitEntidad] [varchar](16) NULL,
	[cfbNombreEntidad] [varchar](100) NULL,
	[cfbTipoIdentificacion] [varchar](100) NULL,
	[cfbIdentificacion] [varchar](16) NULL,
	[cfbApellidos] [varchar](100) NULL,
	[cfbNombres] [varchar](100) NULL,
	[cfbFechaAsignacion] [datetime] NULL,
	[cfbValorAsignado] [varchar](100) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisBeneficiario_cfbId] PRIMARY KEY CLUSTERED 
(
	[cfbId] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[CargueArchivoCruceFovisBeneficiarioArriendo](
	[cfeId] [bigint] NOT NULL,
	[cfeCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfeNitEntidad] [varchar](16) NULL,
	[cfeNombreEntidad] [varchar](100) NULL,
	[cfeTipoIdentificacion] [varchar](100) NULL,
	[cfeIdentificacion] [varchar](16) NULL,
	[cfeApellidos] [varchar](100) NULL,
	[cfeNombres] [varchar](100) NULL,
	[cfeFechaAsignacion] [datetime] NULL,
	[cfeValorAsignado] [varchar](100) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisBeneficiarioArriendo_cfeId] PRIMARY KEY CLUSTERED 
(
	[cfeId] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[CargueArchivoCruceFovisCatastros](
	[cfdId] [bigint] NOT NULL,
	[cfdCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfdNitEntidad] [varchar](16) NULL,
	[cfdNombreEntidad] [varchar](100) NULL,
	[cfdTipoIdentificacion] [varchar](100) NULL,
	[cfdIdentificacion] [varchar](16) NULL,
	[cfdApellidosNombres] [varchar](200) NULL,
	[cfdCedulaCatastral] [varchar](50) NULL,
	[cfdDireccion] [varchar](300) NULL,
	[cfdMatriculaInmobiliaria] [varchar](50) NULL,
	[cfdDepartamento] [varchar](100) NULL,
	[cfdMunicipio] [varchar](100) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisCatastros_cfdId] PRIMARY KEY CLUSTERED 
(
	[cfdId] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[CargueArchivoCruceFovisReunidos](
	[cfrId] [bigint] NOT NULL,
	[cfrCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfrTipoIdentificacion] [varchar](100) NULL,
	[cfrDocumento] [varchar](16) NULL,
	[cfrTipoDocumento] [varchar](30) NULL,
	[cfrApellidosNombres] [varchar](200) NULL,
	[cfrMunicipio] [varchar](100) NULL,
	[cfrDepartamento] [varchar](100) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisReunidos_cfrId] PRIMARY KEY CLUSTERED 
(
	[cfrId] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[CargueArchivoCruceFovisSisben](
	[cfsId] [bigint] NOT NULL,
	[cfsCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfsTipoIdentificacion] [varchar](100) NULL,
	[cfsIdentificacion] [varchar](16) NULL,
	[cfsApellidosNombres] [varchar](200) NULL,
	[cfsPuntaje] [varchar](10) NULL,
	[cfsSexo] [varchar](20) NULL,
	[cfsZona] [varchar](30) NULL,
	[cfsParantesco] [varchar](100) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisSisben_cfsId] PRIMARY KEY CLUSTERED 
(
	[cfsId] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[CargueArchivoCruceFovisUnidos](
	[cfuId] [bigint] NOT NULL,
	[cfuCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfuTipoIdentificacion] [varchar](100) NULL,
	[cfuIdentificacion] [varchar](16) NULL,
	[cfuApellidosNombres] [varchar](200) NULL,
	[cfuFolio] [varchar](30) NULL,
	[cfuSexo] [varchar](20) NULL,
	[cfuParantesco] [varchar](100) NULL,
	[cfuDepartamento] [varchar](100) NULL,
	[cfuMunicipio] [varchar](50) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisUnidos_cfuId] PRIMARY KEY CLUSTERED 
(
	[cfuId] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

ALTER TABLE [dbo].[CargueArchivoCruceFovisAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisAfiliado_cfaCargueArchivoCruceFovis] FOREIGN KEY([cfaCargueArchivoCruceFovis])
REFERENCES [dbo].[CargueArchivoCruceFovis] ([cacId])

ALTER TABLE [dbo].[CargueArchivoCruceFovisAfiliado] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisAfiliado_cfaCargueArchivoCruceFovis]

ALTER TABLE [dbo].[CargueArchivoCruceFovisBeneficiario]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisBeneficiario_cfbCargueArchivoCruceFovis] FOREIGN KEY([cfbCargueArchivoCruceFovis])
REFERENCES [dbo].[CargueArchivoCruceFovis] ([cacId])

ALTER TABLE [dbo].[CargueArchivoCruceFovisBeneficiario] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisBeneficiario_cfbCargueArchivoCruceFovis]

ALTER TABLE [dbo].[CargueArchivoCruceFovisBeneficiarioArriendo]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisBeneficiarioArriendo_cfeCargueArchivoCruceFovis] FOREIGN KEY([cfeCargueArchivoCruceFovis])
REFERENCES [dbo].[CargueArchivoCruceFovis] ([cacId])

ALTER TABLE [dbo].[CargueArchivoCruceFovisBeneficiarioArriendo] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisBeneficiarioArriendo_cfeCargueArchivoCruceFovis]

ALTER TABLE [dbo].[CargueArchivoCruceFovisCatastros]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisCatastros_cfdCargueArchivoCruceFovis] FOREIGN KEY([cfdCargueArchivoCruceFovis])
REFERENCES [dbo].[CargueArchivoCruceFovis] ([cacId])

ALTER TABLE [dbo].[CargueArchivoCruceFovisCatastros] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisCatastros_cfdCargueArchivoCruceFovis]

ALTER TABLE [dbo].[CargueArchivoCruceFovisReunidos]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisReunidos_cfrCargueArchivoCruceFovis] FOREIGN KEY([cfrCargueArchivoCruceFovis])
REFERENCES [dbo].[CargueArchivoCruceFovis] ([cacId])

ALTER TABLE [dbo].[CargueArchivoCruceFovisReunidos] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisReunidos_cfrCargueArchivoCruceFovis]

ALTER TABLE [dbo].[CargueArchivoCruceFovisSisben]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisSisben_cfsCargueArchivoCruceFovis] FOREIGN KEY([cfsCargueArchivoCruceFovis])
REFERENCES [dbo].[CargueArchivoCruceFovis] ([cacId])

ALTER TABLE [dbo].[CargueArchivoCruceFovisSisben] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisSisben_cfsCargueArchivoCruceFovis]

ALTER TABLE [dbo].[CargueArchivoCruceFovisUnidos]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisUnidos_cfuCargueArchivoCruceFovis] FOREIGN KEY([cfuCargueArchivoCruceFovis])
REFERENCES [dbo].[CargueArchivoCruceFovis] ([cacId])

ALTER TABLE [dbo].[CargueArchivoCruceFovisUnidos] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisUnidos_cfuCargueArchivoCruceFovis]
