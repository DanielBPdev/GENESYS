--liquibase formatted sql

--changeset jrico:01
--comment: Tabla para almacenar las plantillas comunicados resueltas de consolidado cartera

CREATE TABLE [PlantillaComunicadoResuelta](
	[pcrAsunto] [varchar](500) NULL,
	[pcrCuerpo] [varchar](8000) NULL,
	[pcrEncabezado] [varchar](1000) NULL,
	[pcrMensaje] [varchar](8000) NULL,
	[pcrNombre] [varchar](150) NULL,
	[pcrPie] [varchar](2000) NULL,
	[pcrEtiqueta] [varchar](150) NULL,
	[pcrSolicitud] [bigint] NOT NULL,
	[pcrTipoIdentificacion] [varchar](20) NOT NULL,
	[pcrNumeroIdentificacion] [varchar](16) NOT NULL,
	[pcrId] [bigint] NOT NULL,
	[pcrIdCartera] [bigint] NOT NULL
);
CREATE NONCLUSTERED INDEX IDX_PlantillaComunicadoResuelta_pcrSolicitud ON dbo.PlantillaComunicadoResuelta (  pcrSolicitud ASC  );
CREATE UNIQUE INDEX UK_PlantillaComunicadoResuelta_NumeroIdentificacion_Solicitud_Id ON core.dbo.PlantillaComunicadoResuelta (pcrNumeroIdentificacion,pcrSolicitud,pcrId);

