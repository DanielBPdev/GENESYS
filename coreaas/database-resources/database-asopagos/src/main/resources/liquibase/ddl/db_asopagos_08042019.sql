--liquibase formatted sql

--changeset jroa:01
--comment: Creacion tabla Parametro
CREATE TABLE [dbo].[Parametro](
	[prmId] [int] IDENTITY(1,1) NOT NULL,
	[prmNombre] [varchar](100) NULL,
	[prmValor] [varchar](150) NULL,
	[prmCargaInicio] [bit] NULL,
	[prmSubCategoriaParametro] [varchar](23) NULL,
	[prmDescripcion] [varchar](250) NOT NULL,
 CONSTRAINT [PK_Parametro_prmId] PRIMARY KEY CLUSTERED 
(
	[prmId] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UK_Parametro_prmNombre] UNIQUE NONCLUSTERED 
(
	[prmNombre] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

ALTER TABLE [dbo].[Parametro]  WITH CHECK ADD  CONSTRAINT [CK_Parametro_prmSubCategoriaParametro] CHECK  (([prmSubCategoriaParametro]='REPORTES' OR [prmSubCategoriaParametro]='ALMACENAMIENTO_ARCHIVOS' OR [prmSubCategoriaParametro]='FTP_ARCHIVO_TAR_ANIBOL' OR [prmSubCategoriaParametro]='FTP_ARCHIVOS_DESCUENTO' OR [prmSubCategoriaParametro]='VALOR_GLOBAL_TECNICO' OR [prmSubCategoriaParametro]='VALOR_GLOBAL_NEGOCIO' OR [prmSubCategoriaParametro]='CAJA_COMPENSACION' OR [prmSubCategoriaParametro]='KEYCLOAK' OR [prmSubCategoriaParametro]='ECM' OR [prmSubCategoriaParametro]='MAIL_SMTP' OR [prmSubCategoriaParametro]='IDM' OR [prmSubCategoriaParametro]='FILE_DEFINITION' OR [prmSubCategoriaParametro]='EJECUCION_TIMER' OR [prmSubCategoriaParametro]='BPM_PROCESS' OR [prmSubCategoriaParametro]='BPM_PROCESS_AFILIACION' OR [prmSubCategoriaParametro]='ENTITY_MANAGER' OR [prmSubCategoriaParametro]='TECNICO_TEMAS_VPN'))

ALTER TABLE [dbo].[Parametro] CHECK CONSTRAINT [CK_Parametro_prmSubCategoriaParametro]

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Entidad que representa constantes del sistema que determinan comportamientos
 específicos de la aplicación <br/>
 <b>Historia de Usuario: </b>TRA' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Parametro'

--changeset jroa:02
--comment: Creacion tabla Constante
CREATE TABLE [dbo].[Constante](
	[cnsId] [int] IDENTITY(1,1) NOT NULL,
	[cnsNombre] [varchar](100) NULL,
	[cnsValor] [varchar](150) NULL,
	[cnsDescripcion] [varchar](250) NOT NULL,
 CONSTRAINT [PK_Constante_cnsId] PRIMARY KEY CLUSTERED 
(
	[cnsId] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UK_Constante_cnsNombre] UNIQUE NONCLUSTERED 
(
	[cnsNombre] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Entidad que representa constantes del sistema (<b><i>Esta entidad solo almacena valores propios de desarrollo</i></b>) que determinan comportamientos específicos de la aplicación y no deben modificarse<br/>
 <b>Historia de Usuario: </b>TRA' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Constante'


