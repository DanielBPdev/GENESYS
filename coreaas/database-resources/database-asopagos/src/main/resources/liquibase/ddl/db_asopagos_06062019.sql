--liquibase formatted sql

--changeset jroa:01
--comment: Creacion tabla RequisitoCajaClasificacion
CREATE TABLE [RequisitoCajaClasificacion](
	[rtsId] [bigint] IDENTITY(1,1) NOT NULL,
	[rtsEstado] [varchar](20) NULL,
	[rtsRequisito] [bigint] NULL,
	[rtsClasificacion] [varchar](100) NULL,
	[rtsTipoTransaccion] [varchar](100) NULL,
	[rtsCajaCompensacion] [int] NULL,
	[rtsTextoAyuda] [varchar](1500) NULL,
	[rtsTipoRequisito] [varchar](30) NULL,
 CONSTRAINT [PK_RequisitoTipoSolicitante_rtsId] PRIMARY KEY CLUSTERED 
(
	[rtsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_RequisitoCajaClasificacion_rtsRequisito_rtsClasificacion_rtsTipoTransaccion_rtsCajaCompensacion] UNIQUE NONCLUSTERED 
(
	[rtsRequisito] ASC,
	[rtsClasificacion] ASC,
	[rtsTipoTransaccion] ASC,
	[rtsCajaCompensacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

--changeset jroa:02
--comment: Creacion tabla GrupoRequisito
CREATE TABLE [GrupoRequisito](
	[grqId] [bigint] IDENTITY(1,1) NOT NULL,
	[grqRequisitoCajaClasificacion] [bigint] NULL,
	[grqGrupoUsuario] [varchar](60) NULL,
 CONSTRAINT [PK_GrupoRequisito_grqId] PRIMARY KEY CLUSTERED 
(
	[grqId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

