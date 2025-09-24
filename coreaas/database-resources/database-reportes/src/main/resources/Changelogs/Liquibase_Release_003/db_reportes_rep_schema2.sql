--liquibase formatted sql

--changeset ggiraldo:02
--comment: Adición de la tabla Parametrizacion Modalidad que no está incluida en la actualización de core a reportes

CREATE TABLE [dbo].[ParametrizacionModalidad](
	[pmoId] [bigint] IDENTITY(1,1) NOT NULL,
	[pmoNombre] [varchar](50) NULL,
	[pmoEstado] [bit] NULL,
	[pmoTopeSMLMV] [numeric](4, 1) NULL,
	[pmoTopeAvaluoCatastral] [numeric](4, 1) NULL,
CONSTRAINT [PK_ParametrizacionModalidad_pmoId] PRIMARY KEY CLUSTERED 
(
	[pmoId] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

ALTER TABLE [dbo].[ParametrizacionModalidad]  WITH CHECK ADD  CONSTRAINT [CK_ParametrizacionModalidad_pmoNombre] CHECK  (([pmoNombre]='MEJORAMIENTO_VIVIENDA_SALUDABLE' OR [pmoNombre]='MEJORAMIENTO_VIVIENDA_URBANA' OR [pmoNombre]='MEJORAMIENTO_VIVIENDA_RURAL' OR [pmoNombre]='CONSTRUCCION_SITIO_PROPIO_URBANO' OR [pmoNombre]='CONSTRUCCION_SITIO_PROPIO_RURAL' OR [pmoNombre]='ADQUISICION_VIVIENDA_USADA_URBANA' OR [pmoNombre]='ADQUISICION_VIVIENDA_USADA_RURAL' OR [pmoNombre]='ADQUISICION_VIVIENDA_NUEVA_URBANA' OR [pmoNombre]='ADQUISICION_VIVIENDA_NUEVA_RURAL'));

ALTER TABLE [dbo].[ParametrizacionModalidad] CHECK CONSTRAINT [CK_ParametrizacionModalidad_pmoNombre];

INSERT INTO [dbo].[ParametrizacionModalidad]
		([pmoNombre], [pmoEstado], [pmoTopeSMLMV], [pmoTopeAvaluoCatastral])
    VALUES ('ADQUISICION_VIVIENDA_NUEVA_RURAL', 1, 150.0, 0.0);

INSERT INTO [dbo].[ParametrizacionModalidad]
		([pmoNombre], [pmoEstado], [pmoTopeSMLMV], [pmoTopeAvaluoCatastral])
	VALUES ('ADQUISICION_VIVIENDA_NUEVA_URBANA', 1, 150.0, 0.0);

INSERT INTO [dbo].[ParametrizacionModalidad]
		([pmoNombre], [pmoEstado], [pmoTopeSMLMV], [pmoTopeAvaluoCatastral])
	VALUES ('ADQUISICION_VIVIENDA_USADA_RURAL', 1, 70.0, 0.0);

INSERT INTO [dbo].[ParametrizacionModalidad]
		([pmoNombre], [pmoEstado], [pmoTopeSMLMV], [pmoTopeAvaluoCatastral])
	VALUES ('ADQUISICION_VIVIENDA_USADA_URBANA', 1, 135.0, 0.0);

INSERT INTO [dbo].[ParametrizacionModalidad]
		([pmoNombre], [pmoEstado], [pmoTopeSMLMV], [pmoTopeAvaluoCatastral])
	VALUES ('CONSTRUCCION_SITIO_PROPIO_RURAL', 1, 70.0, 0.0);

INSERT INTO [dbo].[ParametrizacionModalidad]
		([pmoNombre], [pmoEstado], [pmoTopeSMLMV], [pmoTopeAvaluoCatastral])
	VALUES ('CONSTRUCCION_SITIO_PROPIO_URBANO', 1, 70.0, 0.0);

INSERT INTO [dbo].[ParametrizacionModalidad]
		([pmoNombre], [pmoEstado], [pmoTopeSMLMV], [pmoTopeAvaluoCatastral])
	VALUES ('MEJORAMIENTO_VIVIENDA_RURAL', 1, 70.0, 70.0);

INSERT INTO [dbo].[ParametrizacionModalidad]
		([pmoNombre], [pmoEstado], [pmoTopeSMLMV], [pmoTopeAvaluoCatastral])
	VALUES ('MEJORAMIENTO_VIVIENDA_SALUDABLE', 1, 135.0, 70.0);

INSERT INTO [dbo].[ParametrizacionModalidad]
		([pmoNombre], [pmoEstado], [pmoTopeSMLMV], [pmoTopeAvaluoCatastral])
	VALUES ('MEJORAMIENTO_VIVIENDA_URBANA', 1, 135.0, 70.0);
