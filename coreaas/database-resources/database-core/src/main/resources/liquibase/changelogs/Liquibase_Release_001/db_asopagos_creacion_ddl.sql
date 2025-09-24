--liquibase formatted sql

--changeset Heinsohn:1 stripComments:true

--CREACIÓN DE TABLAS
/****** Object:  Table [dbo].[ARL]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ARL](
	[arlId] [smallint] IDENTITY(1,1) NOT NULL,
	[arlNombre] [varchar](255) NULL,
 CONSTRAINT [PK_ARL_arlId] PRIMARY KEY CLUSTERED 
(
	[arlId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[AsesorResponsableEmpleador]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AsesorResponsableEmpleador](
	[areId] [bigint] IDENTITY(1,1) NOT NULL,
	[areNombreUsuario] [varchar](20) NULL,
	[arePrimario] [bit] NULL,
	[areEmpleador] [bigint] NULL,
 CONSTRAINT [PK_AsesorResponsableEmpleador_areId] PRIMARY KEY CLUSTERED 
(
	[areId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CajaCompensacion]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CajaCompensacion](
	[ccfId] [int] IDENTITY(1,1) NOT NULL,
	[ccfHabilitado] [bit] NULL,
	[ccfMetodoGeneracionEtiquetas] [varchar](20) NULL,
	[ccfNombre] [varchar](255) NULL,
	[ccfSocioAsopagos] [bit] NULL,
	[ccfDepartamento] [smallint] NULL,
 CONSTRAINT [PK_CajaCompensacion_ccfId] PRIMARY KEY CLUSTERED 
(
	[ccfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CajaCorrespondencia]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CajaCorrespondencia](
	[ccoId] [bigint] IDENTITY(1,1) NOT NULL,
	[ccoCodigoEtiqueta] [varchar](12) NULL,
	[ccoConsecutivo] [bigint] NULL,
	[ccoDestinatario] [varchar](255) NULL,
	[ccoEstado] [varchar](20) NULL,
	[ccoFechaFin] [datetime2](7) NULL,
	[ccoFechaInicio] [datetime2](7) NULL,
	[ccoFechaRecepcion] [datetime2](7) NULL,
	[ccoRemitente] [varchar](255) NULL,
	[ccoSedeDestinatario] [varchar](2) NULL,
	[ccoSedeRemitente] [varchar](2) NULL,
	[ccoUsuarioRecepcion] [varchar](255) NULL,
 CONSTRAINT [PK_CajaCorrespondencia_ccoId] PRIMARY KEY CLUSTERED 
(
	[ccoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CodigoCIIU]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CodigoCIIU](
	[ciiId] [smallint] IDENTITY(1,1) NOT NULL,
	[ciiCodigo] [varchar](4) NULL,
	[ciiDescripcion] [varchar](255) NULL,
 CONSTRAINT [PK_CodigoCIIU_ciiId] PRIMARY KEY CLUSTERED 
(
	[ciiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Comunicado]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Comunicado](
	[comId] [bigint] IDENTITY(1,1) NOT NULL,
	[comEmail] [varchar](255) NULL,
	[comIdentificaArchivoComunicado] [varchar](255) NULL,
	[comTextoAdicionar] [varchar](500) NULL,
	[comPlantillaComunicado] [bigint] NULL,
 CONSTRAINT [PK_Comunicado_comId] PRIMARY KEY CLUSTERED 
(
	[comId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ComunicadoEnviado]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ComunicadoEnviado](
	[coeId] [bigint] IDENTITY(1,1) NOT NULL,
	[coeDestinatario] [varchar](255) NULL,
	[coeEstadoEnvio] [varchar](20) NULL,
	[coeFechaEnvio] [datetime2](7) NULL,
	[coeMensajeEnvio] [varchar](255) NULL,
	[coeNumeroCorreoMasivo] [bigint] NULL,
	[coeRemitente] [varchar](255) NULL,
	[coeSedeCajaCompensacion] [varchar](2) NULL,
	[coeComunicado] [bigint] NULL,
 CONSTRAINT [PK_ComunicadoEnviado_coeId] PRIMARY KEY CLUSTERED 
(
	[coeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ComunicadoImpreso]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ComunicadoImpreso](
	[coiId] [bigint] IDENTITY(1,1) NOT NULL,
	[coiFechaImpresion] [datetime2](7) NULL,
	[coiRemitente] [varchar](255) NULL,
	[coiSedeCajaCompensacion] [varchar](2) NULL,
	[coiComunicado] [bigint] NULL,
 CONSTRAINT [PK_ComunicadoImpreso_coiId] PRIMARY KEY CLUSTERED 
(
	[coiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Constante]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Constante](
	[cnsId] [bigint] IDENTITY(1,1) NOT NULL,
	[cnsNombre] [varchar](255) NULL,
	[cnsValor] [varchar](255) NULL,
 CONSTRAINT [PK_Constante_cnsId] PRIMARY KEY CLUSTERED 
(
	[cnsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Departamento]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Departamento](
	[depId] [smallint] IDENTITY(1,1) NOT NULL,
	[depCodigo] [varchar](2) NULL,
	[depIndicativoTelefoniaFija] [varchar](2) NULL,
	[depNombre] [varchar](100) NULL,
 CONSTRAINT [PK_Departamento_depId] PRIMARY KEY CLUSTERED 
(
	[depId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Empleador]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Empleador](
	[empId] [bigint] IDENTITY(1,1) NOT NULL,
	[empEspecialRevision] [bit] NULL,
	[empEstadoAportesEmpleador] [varchar](20) NULL,
	[empEstadoEmpleador] [varchar](20) NULL,
	[empExpulsionSubsanada] [bit] NULL,
	[empFechaCambioEstadoAfiliacion] [datetime2](7) NULL,
	[empFechaConstitucion] [date] NULL,
	[empMotivoDesafiliacion] [varchar](100) NULL,
	[empNaturalezaJuridica] [varchar](100) NULL,
	[empNombreComercial] [varchar](250) NULL,
	[empNumeroTotalTrabajadores] [int] NULL,
	[empPaginaWeb] [varchar](256) NULL,
	[empPeriodoUltimaNomina] [varchar](7) NULL,
	[empValorTotalUltimaNomina] [numeric](19, 2) NULL,
	[empArl] [smallint] NULL,
	[empCodigoCIIU] [smallint] NULL,
	[empRepresentanteLegal] [bigint] NULL,
	[empRepresentanteLegalSuplente] [bigint] NULL,
	[empUltimaCajaCompensacion] [int] NULL,
	[empMedioPagoSubsidioMonetario] [smallint] NULL,
	[empPersona] [bigint] NULL,
 CONSTRAINT [PK_Empleador_empId] PRIMARY KEY CLUSTERED 
(
	[empId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[EscalaSoliciAfiliEmpleador]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[EscalaSoliciAfiliEmpleador](
	[eaeId] [bigint] IDENTITY(1,1) NOT NULL,
	[eaeAsunto] [varchar](255) NULL,
	[eaeComentarioAnalista] [varchar](255) NULL,
	[eaeDescripcion] [varchar](255) NULL,
	[eaeDestinatario] [varchar](255) NULL,
	[eaeFechaCreacion] [datetime2](7) NULL,
	[eaeIdentifDocumSoporteAnalista] [varchar](255) NULL,
	[eaeResultadoAnalista] [varchar](255) NULL,
	[eaeUsuarioCreacion] [varchar](255) NULL,
	[eaeSolicitudAfiliacion] [bigint] NULL,
 CONSTRAINT [PK_EscalaSoliciAfiliEmpleador_eaeId] PRIMARY KEY CLUSTERED 
(
	[eaeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[EtiquetaCorrespondenciaRadicado]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[EtiquetaCorrespondenciaRadicado](
	[eprId] [bigint] IDENTITY(1,1) NOT NULL,
	[eprAsignada] [bit] NULL,
	[eprCodigo] [varchar](12) NULL,
	[eprTipoEtiqueta] [varchar](50) NULL,
	[eprProcedenciaEtiqueta] [varchar](20) NULL,
 CONSTRAINT [PK_EtiquetaPreimpresa_eprId] PRIMARY KEY CLUSTERED 
(
	[eprId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[IntentoAfiliacion]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[IntentoAfiliacion](
	[iafId] [bigint] IDENTITY(1,1) NOT NULL,
	[iafCausaIntentoFalido] [varchar](50) NULL,
	[iafFechaCreacion] [datetime2](7) NULL,
	[iafFechaInicioProceso] [datetime2](7) NULL,
	[iafSedeCajaCompensacion] [varchar](2) NULL,
	[iafTipoTransaccion] [varchar](100) NULL,
	[iafUsuarioCreacion] [varchar](255) NULL,
	[iafSolicitud] [bigint] NULL,
 CONSTRAINT [PK_IntentoAfiliacion_iafId] PRIMARY KEY CLUSTERED 
(
	[iafId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[IntentoAfiliRequisito]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[IntentoAfiliRequisito](
	[iarId] [bigint] IDENTITY(1,1) NOT NULL,
	[iarIntentoAfiliacion] [bigint] NULL,
	[iarRequisito] [bigint] NULL,
 CONSTRAINT [PK_IntentoAfiliRequisito_iarId] PRIMARY KEY CLUSTERED 
(
	[iarId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ItemChequeoAfiliEmpleador]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ItemChequeoAfiliEmpleador](
	[ichId] [bigint] IDENTITY(1,1) NOT NULL,
	[ichComentarios] [varchar](255) NULL,
	[ichComentariosBack] [varchar](255) NULL,
	[ichCumpleRequisito] [bit] NULL,
	[ichCumpleRequisitoBack] [bit] NULL,
	[ichEstadoRequisito] [varchar](255) NULL,
	[ichFormatoEntregaDocumento] [varchar](255) NULL,
	[ichPrecargado] [bit] NULL,
	[ichSolicitudAfiliaciEmpleador] [bigint] NULL,
	[ichRequisitoAfiliaciEmpleador] [bigint] NULL,
	[ichNombreRequisito] [varchar](255) NULL,
 CONSTRAINT [PK_ItemChequeoAfiliEmpleador_ichId] PRIMARY KEY CLUSTERED 
(
	[ichId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Municipio]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Municipio](
	[munId] [smallint] IDENTITY(1,1) NOT NULL,
	[munCodigo] [varchar](6) NULL,
	[munNombre] [varchar](50) NULL,
	[munDepartamento] [smallint] NULL,
 CONSTRAINT [PK_Municipio_munId] PRIMARY KEY CLUSTERED 
(
	[munId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[NotDestinatario]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[NotDestinatario](
	[nodId] [bigint] IDENTITY(1,1) NOT NULL,
	[nodNotEnviada] [bigint] NULL,
	[nodDestintatario] [varchar](255) NULL,
 CONSTRAINT [PK_NotDestinatario_nodId] PRIMARY KEY CLUSTERED 
(
	[nodId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[NotEnviada]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[NotEnviada](
	[noeId] [bigint] IDENTITY(1,1) NOT NULL,
	[noeFechaEnvio] [datetime2](7) NULL,
	[noeRemitente] [varchar](255) NULL,
	[noeSccfId] [bigint] NULL,
	[noeProcesoEvento] [varchar](255) NULL,
	[noeEstadoEnvioNot] [varchar](255) NULL,
	[noeError] [varchar](4000) NULL,
 CONSTRAINT [PK_NotEnviada_noeId] PRIMARY KEY CLUSTERED 
(
	[noeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PaqueteEnvioBack]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PaqueteEnvioBack](
	[pebId] [bigint] IDENTITY(1,1) NOT NULL,
	[pebCodigoEtiquetaPaqueteEnvio] [varchar](12) NULL,
	[pebFechaFinalSolicitudes] [datetime2](7) NULL,
	[pebFechaInicialSolicitudes] [datetime2](7) NULL,
 CONSTRAINT [PK_PaqueteEnvioBack_pebId] PRIMARY KEY CLUSTERED 
(
	[pebId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ParametrizacionMedioDePago]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ParametrizacionMedioDePago](
	[pmpId] [smallint] IDENTITY(1,1) NOT NULL,
	[pmpMedioPago] [varchar](50) NULL,
 CONSTRAINT [PK_ParametrizacionMedioDePago_pmpId] PRIMARY KEY CLUSTERED 
(
	[pmpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ParametrizacionMetodoAsignacion]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ParametrizacionMetodoAsignacion](
	[pmaSedeCajaCompensacion] [bigint] NOT NULL,
	[pmaProceso] [varchar](100) NULL,
	[pmaMetodoAsignacion] [varchar](20) NULL,
	[pmaUsuario] [varchar](255) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ParametrizaEnvioComunicado]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ParametrizaEnvioComunicado](
	[pecId] [bigint] IDENTITY(1,1) NOT NULL,
	[pecProceso] [varchar](100) NULL,
	[pecTipoCorreo] [varchar](20) NULL,
 CONSTRAINT [PK_ParametrizaEnvioComunicado_pecId] PRIMARY KEY CLUSTERED 
(
	[pecId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Parametro]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Parametro](
	[prmId] [bigint] IDENTITY(1,1) NOT NULL,
	[prmNombre] [varchar](255) NULL,
	[prmValor] [varchar](255) NULL,
 CONSTRAINT [PK_Parametro_prmId] PRIMARY KEY CLUSTERED 
(
	[prmId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Persona]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Persona](
	[perId] [bigint] IDENTITY(1,1) NOT NULL,
	[perApellidos] [varchar](255) NULL,
	[perDigitoVerificacion] [tinyint] NULL,
	[perNombres] [varchar](255) NULL,
	[perNumeroIdentificacion] [varchar](16) NULL,
	[perPrecargado] [bit] NULL,
	[perRazonSocial] [varchar](250) NULL,
	[perTipoIdentificacion] [varchar](20) NULL,
	[perUbicacionPrincipal] [bigint] NULL,
 CONSTRAINT [PK_Persona_perId] PRIMARY KEY CLUSTERED 
(
	[perId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_Persona_TipoIdentificacion_NumeroIdentificacion] UNIQUE NONCLUSTERED 
(
	[perTipoIdentificacion] ASC,
	[perNumeroIdentificacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PlantillaComunicado]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PlantillaComunicado](
	[pcoId] [bigint] IDENTITY(1,1) NOT NULL,
	[pcoAsunto] [varchar](100) NULL,
	[pcoCuerpo] [varchar](1000) NULL,
	[pcoEncabezado] [varchar](500) NULL,
	[pcoIdentificadorImagenPie] [varchar](255) NULL,
	[pcoMensaje] [varchar](500) NULL,
	[pcoNombre] [varchar](150) NULL,
	[pcoPie] [varchar](500) NULL,
	[pcoEtiqueta] [varchar](150) NULL,
 CONSTRAINT [PK_PlantillaComunicado_pcoId] PRIMARY KEY CLUSTERED 
(
	[pcoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ProductoNoConforme]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ProductoNoConforme](
	[pncId] [bigint] IDENTITY(1,1) NOT NULL,
	[pncCampo] [varchar](150) NULL,
	[pncCampoObligatorio] [bit] NULL,
	[pncComentariosBack] [varchar](255) NULL,
	[pncComentariosBack2] [varchar](255) NULL,
	[pncComentariosFront] [varchar](255) NULL,
	[pncDescripcion] [varchar](255) NULL,
	[pncFechaCreacion] [datetime2](7) NULL,
	[pncGrupoCampos] [varchar](150) NULL,
	[pncDocumentoSoporteGestion] [varchar](255) NULL,
	[pncNumeroConsecutivo] [smallint] NULL,
	[pncResultadoGestion] [varchar](20) NULL,
	[pncResultadoRevisionBack] [varchar](20) NULL,
	[pncResultadoRevisionBack2] [varchar](20) NULL,
	[pncSeccion] [varchar](150) NULL,
	[pncSubsanable] [bit] NULL,
	[pncTipoError] [varchar](80) NULL,
	[pncTipoProductoNoConforme] [varchar](20) NULL,
	[pncUsuarioBack] [varchar](255) NULL,
	[pncUsuarioFront] [varchar](255) NULL,
	[pncValorCampoBack] [varchar](255) NULL,
	[pncValorCampoFront] [varchar](255) NULL,
	[pncSolicitudAfiliacion] [bigint] NULL,
 CONSTRAINT [PK_ProductoNoConforme_pncId] PRIMARY KEY CLUSTERED 
(
	[pncId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Requisito]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Requisito](
	[reqId] [bigint] IDENTITY(1,1) NOT NULL,
	[reqDescripcion] [varchar](255) NULL,
	[reqTipoTransaccion] [varchar](100) NULL,
 CONSTRAINT [PK_Requisito_reqId] PRIMARY KEY CLUSTERED 
(
	[reqId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[RequisitoAfiliaciEmpleador]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[RequisitoAfiliaciEmpleador](
	[raeId] [bigint] IDENTITY(1,1) NOT NULL,
	[raeIdentificaDocumentoAdjunto] [varchar](255) NULL,
	[raeEmpleador] [bigint] NULL,
	[raeRequisito] [bigint] NULL,
 CONSTRAINT [PK_RequisitoAfiliaciEmpleador_raeId] PRIMARY KEY CLUSTERED 
(
	[raeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[RequisitoCajaCompensacion]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[RequisitoCajaCompensacion](
	[rccId] [bigint] IDENTITY(1,1) NOT NULL,
	[rccEstado] [varchar](20) NULL,
	[rccCajaCompensacion] [int] NULL,
	[rccRequisito] [bigint] NULL,
 CONSTRAINT [PK_RequisitoCajaCompensacion_rccId] PRIMARY KEY CLUSTERED 
(
	[rccId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_RequisitoCajaCompensacion_rccRequisito_rccCajaCompensacion] UNIQUE NONCLUSTERED 
(
	[rccRequisito] ASC,
	[rccCajaCompensacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[RequisitoTipoSolicitante]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[RequisitoTipoSolicitante](
	[rtsId] [bigint] IDENTITY(1,1) NOT NULL,
	[rtsEstado] [varchar](20) NULL,
	[rtsRequisito] [bigint] NULL,
	[rtsTipoSolicitante] [int] NULL,
 CONSTRAINT [PK_RequisitoTipoSolicitante_rtsId] PRIMARY KEY CLUSTERED 
(
	[rtsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_RequisitoTipoSolicitante_rtsRequisito_rtsTipoSolicitante] UNIQUE NONCLUSTERED 
(
	[rtsRequisito] ASC,
	[rtsTipoSolicitante] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[RolContactoEmpleador]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[RolContactoEmpleador](
	[rceId] [bigint] IDENTITY(1,1) NOT NULL,
	[rceTipoRolContactoEmpleador] [varchar](50) NULL,
	[rceEmpleador] [bigint] NULL,
	[rcePersona] [bigint] NULL,
 CONSTRAINT [PK_RolContactoEmpleador_rceId] PRIMARY KEY CLUSTERED 
(
	[rceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SedeCajaCompensacion]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SedeCajaCompensacion](
	[sccfId] [bigint] IDENTITY(1,1) NOT NULL,
	[sccfNombre] [varchar](255) NULL,
	[sccfVirtual] [bit] NULL,
	[sccCodigo] [varchar](2) NULL,
 CONSTRAINT [PK_SedeCajaCompensacion_sccfId] PRIMARY KEY CLUSTERED 
(
	[sccfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SocioEmpleador]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SocioEmpleador](
	[semId] [bigint] IDENTITY(1,1) NOT NULL,
	[semExistenCapitulaciones] [bit] NULL,
	[semIdentifiDocumCapitulaciones] [varchar](255) NULL,
	[semConyugue] [bigint] NULL,
	[semEmpleador] [bigint] NULL,
	[semPersona] [bigint] NULL,
 CONSTRAINT [PK_SocioEmpleador_semId] PRIMARY KEY CLUSTERED 
(
	[semId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Solicitud]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Solicitud](
	[solId] [bigint] IDENTITY(1,1) NOT NULL,
	[solCanalRecepcion] [varchar](10) NULL,
	[solFechaRadicacion] [datetime2](7) NULL,
	[solInstanciaProceso] [varchar](255) NULL,
	[solNumeroRadicacion] [varchar](255) NULL,
	[solUsuarioRadicacion] [varchar](255) NULL,
	[solCajaCorrespondencia] [bigint] NULL,
	[solTipoSolicitante] [int] NULL,
	[solTipoTransaccion] [varchar](100) NULL,
	[solCiudadUsuarioRadicacion] [varchar](255) NULL,
 CONSTRAINT [PK_Solicitud_solId] PRIMARY KEY CLUSTERED 
(
	[solId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SolicitudAfiliaciEmpleador]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SolicitudAfiliaciEmpleador](
	[saeId] [bigint] IDENTITY(1,1) NOT NULL,
	[saeCodigoEtiquetaPreimpresa] [varchar](12) NULL,
	[saeDestinatario] [varchar](255) NULL,
	[saeEstadoDocumentacion] [varchar](20) NULL,
	[saeEstadoSolicitud] [varchar](50) NULL,
	[saeFechaAprobacionConsejo] [datetime2](7) NULL,
	[saeFechaCreacion] [datetime2](7) NULL,
	[saeMetodoEnvio] [varchar](20) NULL,
	[saeNumeroActoAdministrativo] [varchar](50) NULL,
	[saeNumeroCustodiaFisica] [varchar](12) NULL,
	[saeObservacion] [varchar](500) NULL,
	[saeEmpleador] [bigint] NULL,
	[saePaqueteEnvioPack] [bigint] NULL,
	[saeSolicitudGlobal] [bigint] NULL,
	[saeSedeDestinatario] [varchar](3) NULL,
 CONSTRAINT [PK_SolicitudAfiliaciEmpleador_saeId] PRIMARY KEY CLUSTERED 
(
	[saeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SucursalEmpleador]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SucursalEmpleador](
	[sueId] [bigint] IDENTITY(1,1) NOT NULL,
	[sueCodigo] [varchar](3) NULL,
	[sueNombre] [varchar](255) NULL,
	[sueCodigoCIIU] [smallint] NULL,
	[sueEmpleador] [bigint] NULL,
	[sueMedioPagoSubsidioMonetario] [smallint] NULL,
	[sueUbicacion] [bigint] NULL,
 CONSTRAINT [PK_SucursalEmpleador_sueId] PRIMARY KEY CLUSTERED 
(
	[sueId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SucursaRolContactEmpleador]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SucursaRolContactEmpleador](
	[srcId] [bigint] IDENTITY(1,1) NOT NULL,
	[srcRolContactoEmpleador] [bigint] NULL,
	[srcSucursalEmpleador] [bigint] NULL,
 CONSTRAINT [PK_SucursaRolContactEmpleador_srcId] PRIMARY KEY CLUSTERED 
(
	[srcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[TipoSoliciCajaCompensacion]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TipoSoliciCajaCompensacion](
	[tscId] [bigint] IDENTITY(1,1) NOT NULL,
	[tscEstado] [varchar](20) NULL,
	[tscCajaCompensacion] [int] NULL,
	[tscTipoSolicitante] [int] NULL,
 CONSTRAINT [PK_TipoSoliciCajaCompensacion_tscId] PRIMARY KEY CLUSTERED 
(
	[tscId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_TipoSoliciCajaCompensacion_tscTipoSolicitante_tscCajaCompensacion] UNIQUE NONCLUSTERED 
(
	[tscTipoSolicitante] ASC,
	[tscCajaCompensacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[TipoSolicitante]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TipoSolicitante](
	[tsoId] [int] IDENTITY(1,1) NOT NULL,
	[tsoDescripcion] [varchar](255) NULL,
	[tsoTipoTipoSolicitante] [varchar](10) NULL,
 CONSTRAINT [PK_TipoSolicitante_tsoId] PRIMARY KEY CLUSTERED 
(
	[tsoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Ubicacion]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Ubicacion](
	[ubiId] [bigint] IDENTITY(1,1) NOT NULL,
	[ubiAutorizacionEnvioEmail] [bit] NULL,
	[ubiCodigoPostal] [varchar](10) NULL,
	[ubiDireccionFisica] [varchar](255) NULL,
	[ubiEmail] [varchar](255) NULL,
	[ubiIndicativoTelFijo] [varchar](2) NULL,
	[ubiTelefonoCelular] [varchar](10) NULL,
	[ubiTelefonoFijo] [varchar](7) NULL,
	[ubiMunicipio] [smallint] NULL,
 CONSTRAINT [PK_Ubicacion_ubiId] PRIMARY KEY CLUSTERED 
(
	[ubiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[UbicacionEmpleador]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UbicacionEmpleador](
	[ubeId] [bigint] IDENTITY(1,1) NOT NULL,
	[ubeTipoUbicacion] [varchar](50) NULL,
	[ubeEmpleador] [bigint] NULL,
	[ubeUbicacion] [bigint] NULL,
 CONSTRAINT [PK_UbicacionEmpleador_ubeId] PRIMARY KEY CLUSTERED 
(
	[ubeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[VariableComunicado]    Script Date: 21/09/2016 02:04 p.m ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[VariableComunicado](
	[vcoId] [bigint] IDENTITY(1,1) NOT NULL,
	[vcoClave] [varchar](100) NULL,
	[vcoDescripcion] [varchar](150) NULL,
	[vcoNombre] [varchar](100) NULL,
	[vcoPlantillaComunicado] [bigint] NULL,
	[vcoNombreConstante] [varchar](255) NULL,
 CONSTRAINT [PK_VariableComunicado_vcoId] PRIMARY KEY CLUSTERED 
(
	[vcoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[AsesorResponsableEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_AsesorResponsableEmpleador_areEmpleador] FOREIGN KEY([areEmpleador])
REFERENCES [dbo].[Empleador] ([empId])
GO
ALTER TABLE [dbo].[AsesorResponsableEmpleador] CHECK CONSTRAINT [FK_AsesorResponsableEmpleador_areEmpleador]
GO
ALTER TABLE [dbo].[CajaCompensacion]  WITH CHECK ADD  CONSTRAINT [FK_CajaCompensacion_ccfDepartamento] FOREIGN KEY([ccfDepartamento])
REFERENCES [dbo].[Departamento] ([depId])
GO
ALTER TABLE [dbo].[CajaCompensacion] CHECK CONSTRAINT [FK_CajaCompensacion_ccfDepartamento]
GO
ALTER TABLE [dbo].[Comunicado]  WITH CHECK ADD  CONSTRAINT [FK_Comunicado_comPlantillaComunicado] FOREIGN KEY([comPlantillaComunicado])
REFERENCES [dbo].[PlantillaComunicado] ([pcoId])
GO
ALTER TABLE [dbo].[Comunicado] CHECK CONSTRAINT [FK_Comunicado_comPlantillaComunicado]
GO
ALTER TABLE [dbo].[ComunicadoEnviado]  WITH CHECK ADD  CONSTRAINT [FK_ComunicadoEnviado_coeComunicado] FOREIGN KEY([coeComunicado])
REFERENCES [dbo].[Comunicado] ([comId])
GO
ALTER TABLE [dbo].[ComunicadoEnviado] CHECK CONSTRAINT [FK_ComunicadoEnviado_coeComunicado]
GO
ALTER TABLE [dbo].[ComunicadoImpreso]  WITH CHECK ADD  CONSTRAINT [FK_ComunicadoImpreso_coiComunicado] FOREIGN KEY([coiComunicado])
REFERENCES [dbo].[Comunicado] ([comId])
GO
ALTER TABLE [dbo].[ComunicadoImpreso] CHECK CONSTRAINT [FK_ComunicadoImpreso_coiComunicado]
GO
ALTER TABLE [dbo].[Empleador]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_empArl] FOREIGN KEY([empArl])
REFERENCES [dbo].[ARL] ([arlId])
GO
ALTER TABLE [dbo].[Empleador] CHECK CONSTRAINT [FK_Empleador_empArl]
GO
ALTER TABLE [dbo].[Empleador]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_empCodigoCIIU] FOREIGN KEY([empCodigoCIIU])
REFERENCES [dbo].[CodigoCIIU] ([ciiId])
GO
ALTER TABLE [dbo].[Empleador] CHECK CONSTRAINT [FK_Empleador_empCodigoCIIU]
GO
ALTER TABLE [dbo].[Empleador]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_empMedioPagoSubsidioMonetario] FOREIGN KEY([empMedioPagoSubsidioMonetario])
REFERENCES [dbo].[ParametrizacionMedioDePago] ([pmpId])
GO
ALTER TABLE [dbo].[Empleador] CHECK CONSTRAINT [FK_Empleador_empMedioPagoSubsidioMonetario]
GO
ALTER TABLE [dbo].[Empleador]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_empPersona] FOREIGN KEY([empPersona])
REFERENCES [dbo].[Persona] ([perId])
GO
ALTER TABLE [dbo].[Empleador] CHECK CONSTRAINT [FK_Empleador_empPersona]
GO
ALTER TABLE [dbo].[Empleador]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_empRepresentanteLegal] FOREIGN KEY([empRepresentanteLegal])
REFERENCES [dbo].[Persona] ([perId])
GO
ALTER TABLE [dbo].[Empleador] CHECK CONSTRAINT [FK_Empleador_empRepresentanteLegal]
GO
ALTER TABLE [dbo].[Empleador]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_empRepresentanteLegalSuplente] FOREIGN KEY([empRepresentanteLegalSuplente])
REFERENCES [dbo].[Persona] ([perId])
GO
ALTER TABLE [dbo].[Empleador] CHECK CONSTRAINT [FK_Empleador_empRepresentanteLegalSuplente]
GO
ALTER TABLE [dbo].[Empleador]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_empUltimaCajaCompensacion] FOREIGN KEY([empUltimaCajaCompensacion])
REFERENCES [dbo].[CajaCompensacion] ([ccfId])
GO
ALTER TABLE [dbo].[Empleador] CHECK CONSTRAINT [FK_Empleador_empUltimaCajaCompensacion]
GO
ALTER TABLE [dbo].[EscalaSoliciAfiliEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_EscalaSoliciAfiliEmpleador_eaeSolicitudAfiliacion] FOREIGN KEY([eaeSolicitudAfiliacion])
REFERENCES [dbo].[SolicitudAfiliaciEmpleador] ([saeId])
GO
ALTER TABLE [dbo].[EscalaSoliciAfiliEmpleador] CHECK CONSTRAINT [FK_EscalaSoliciAfiliEmpleador_eaeSolicitudAfiliacion]
GO
ALTER TABLE [dbo].[IntentoAfiliacion]  WITH CHECK ADD  CONSTRAINT [FK_IntentoAfiliacion_iafSolicitud] FOREIGN KEY([iafSolicitud])
REFERENCES [dbo].[Solicitud] ([solId])
GO
ALTER TABLE [dbo].[IntentoAfiliacion] CHECK CONSTRAINT [FK_IntentoAfiliacion_iafSolicitud]
GO
ALTER TABLE [dbo].[IntentoAfiliRequisito]  WITH CHECK ADD  CONSTRAINT [FK_IntentoAfiliRequisito_iarIntentoAfiliacion] FOREIGN KEY([iarIntentoAfiliacion])
REFERENCES [dbo].[IntentoAfiliacion] ([iafId])
GO
ALTER TABLE [dbo].[IntentoAfiliRequisito] CHECK CONSTRAINT [FK_IntentoAfiliRequisito_iarIntentoAfiliacion]
GO
ALTER TABLE [dbo].[IntentoAfiliRequisito]  WITH CHECK ADD  CONSTRAINT [FK_IntentoAfiliRequisito_iarRequisito] FOREIGN KEY([iarRequisito])
REFERENCES [dbo].[Requisito] ([reqId])
GO
ALTER TABLE [dbo].[IntentoAfiliRequisito] CHECK CONSTRAINT [FK_IntentoAfiliRequisito_iarRequisito]
GO
ALTER TABLE [dbo].[ItemChequeoAfiliEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_ItemChequeoAfiliEmpleador_ichRequisitoAfiliaciEmpleador] FOREIGN KEY([ichRequisitoAfiliaciEmpleador])
REFERENCES [dbo].[RequisitoAfiliaciEmpleador] ([raeId])
GO
ALTER TABLE [dbo].[ItemChequeoAfiliEmpleador] CHECK CONSTRAINT [FK_ItemChequeoAfiliEmpleador_ichRequisitoAfiliaciEmpleador]
GO
ALTER TABLE [dbo].[ItemChequeoAfiliEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_ItemChequeoAfiliEmpleador_ichSolicitudAfiliaciEmpleador] FOREIGN KEY([ichSolicitudAfiliaciEmpleador])
REFERENCES [dbo].[SolicitudAfiliaciEmpleador] ([saeId])
GO
ALTER TABLE [dbo].[ItemChequeoAfiliEmpleador] CHECK CONSTRAINT [FK_ItemChequeoAfiliEmpleador_ichSolicitudAfiliaciEmpleador]
GO
ALTER TABLE [dbo].[Municipio]  WITH CHECK ADD  CONSTRAINT [FK_Municipio_munDepartamento] FOREIGN KEY([munDepartamento])
REFERENCES [dbo].[Departamento] ([depId])
GO
ALTER TABLE [dbo].[Municipio] CHECK CONSTRAINT [FK_Municipio_munDepartamento]
GO
ALTER TABLE [dbo].[NotDestinatario]  WITH CHECK ADD  CONSTRAINT [FK_NotDestinatario_nodNotEnviada] FOREIGN KEY([nodNotEnviada])
REFERENCES [dbo].[NotEnviada] ([noeId])
GO
ALTER TABLE [dbo].[NotDestinatario] CHECK CONSTRAINT [FK_NotDestinatario_nodNotEnviada]
GO
ALTER TABLE [dbo].[ParametrizacionMetodoAsignacion]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionMetodoAsignacion_pmaSedeCajaCompensacion] FOREIGN KEY([pmaSedeCajaCompensacion])
REFERENCES [dbo].[SedeCajaCompensacion] ([sccfId])
GO
ALTER TABLE [dbo].[ParametrizacionMetodoAsignacion] CHECK CONSTRAINT [FK_ParametrizacionMetodoAsignacion_pmaSedeCajaCompensacion]
GO
ALTER TABLE [dbo].[Persona]  WITH CHECK ADD  CONSTRAINT [FK_Persona_perUbicacionPrincipal] FOREIGN KEY([perUbicacionPrincipal])
REFERENCES [dbo].[Ubicacion] ([ubiId])
GO
ALTER TABLE [dbo].[Persona] CHECK CONSTRAINT [FK_Persona_perUbicacionPrincipal]
GO
ALTER TABLE [dbo].[ProductoNoConforme]  WITH CHECK ADD  CONSTRAINT [FK_ProductoNoConforme_pncSolicitudAfiliacion] FOREIGN KEY([pncSolicitudAfiliacion])
REFERENCES [dbo].[SolicitudAfiliaciEmpleador] ([saeId])
GO
ALTER TABLE [dbo].[ProductoNoConforme] CHECK CONSTRAINT [FK_ProductoNoConforme_pncSolicitudAfiliacion]
GO
ALTER TABLE [dbo].[RequisitoAfiliaciEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_RequisitoAfiliaciEmpleador_raeEmpleador] FOREIGN KEY([raeEmpleador])
REFERENCES [dbo].[Empleador] ([empId])
GO
ALTER TABLE [dbo].[RequisitoAfiliaciEmpleador] CHECK CONSTRAINT [FK_RequisitoAfiliaciEmpleador_raeEmpleador]
GO
ALTER TABLE [dbo].[RequisitoAfiliaciEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_RequisitoAfiliaciEmpleador_raeRequisito] FOREIGN KEY([raeRequisito])
REFERENCES [dbo].[Requisito] ([reqId])
GO
ALTER TABLE [dbo].[RequisitoAfiliaciEmpleador] CHECK CONSTRAINT [FK_RequisitoAfiliaciEmpleador_raeRequisito]
GO
ALTER TABLE [dbo].[RequisitoCajaCompensacion]  WITH CHECK ADD  CONSTRAINT [FK_RequisitoCajaCompensacion_rccCajaCompensacion] FOREIGN KEY([rccCajaCompensacion])
REFERENCES [dbo].[CajaCompensacion] ([ccfId])
GO
ALTER TABLE [dbo].[RequisitoCajaCompensacion] CHECK CONSTRAINT [FK_RequisitoCajaCompensacion_rccCajaCompensacion]
GO
ALTER TABLE [dbo].[RequisitoCajaCompensacion]  WITH CHECK ADD  CONSTRAINT [FK_RequisitoCajaCompensacion_rccRequisito] FOREIGN KEY([rccRequisito])
REFERENCES [dbo].[Requisito] ([reqId])
GO
ALTER TABLE [dbo].[RequisitoCajaCompensacion] CHECK CONSTRAINT [FK_RequisitoCajaCompensacion_rccRequisito]
GO
ALTER TABLE [dbo].[RequisitoTipoSolicitante]  WITH CHECK ADD  CONSTRAINT [FK_RequisitoTipoSolicitante_rtsRequisito] FOREIGN KEY([rtsRequisito])
REFERENCES [dbo].[Requisito] ([reqId])
GO
ALTER TABLE [dbo].[RequisitoTipoSolicitante] CHECK CONSTRAINT [FK_RequisitoTipoSolicitante_rtsRequisito]
GO
ALTER TABLE [dbo].[RequisitoTipoSolicitante]  WITH CHECK ADD  CONSTRAINT [FK_RequisitoTipoSolicitante_rtsTipoSolicitante] FOREIGN KEY([rtsTipoSolicitante])
REFERENCES [dbo].[TipoSolicitante] ([tsoId])
GO
ALTER TABLE [dbo].[RequisitoTipoSolicitante] CHECK CONSTRAINT [FK_RequisitoTipoSolicitante_rtsTipoSolicitante]
GO
ALTER TABLE [dbo].[RolContactoEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_RolContactoEmpleador_rceEmpleador] FOREIGN KEY([rceEmpleador])
REFERENCES [dbo].[Empleador] ([empId])
GO
ALTER TABLE [dbo].[RolContactoEmpleador] CHECK CONSTRAINT [FK_RolContactoEmpleador_rceEmpleador]
GO
ALTER TABLE [dbo].[RolContactoEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_RolContactoEmpleador_rcePersona] FOREIGN KEY([rcePersona])
REFERENCES [dbo].[Persona] ([perId])
GO
ALTER TABLE [dbo].[RolContactoEmpleador] CHECK CONSTRAINT [FK_RolContactoEmpleador_rcePersona]
GO
ALTER TABLE [dbo].[SocioEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SocioEmpleador_semConyugue] FOREIGN KEY([semConyugue])
REFERENCES [dbo].[Persona] ([perId])
GO
ALTER TABLE [dbo].[SocioEmpleador] CHECK CONSTRAINT [FK_SocioEmpleador_semConyugue]
GO
ALTER TABLE [dbo].[SocioEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SocioEmpleador_semEmpleador] FOREIGN KEY([semEmpleador])
REFERENCES [dbo].[Empleador] ([empId])
GO
ALTER TABLE [dbo].[SocioEmpleador] CHECK CONSTRAINT [FK_SocioEmpleador_semEmpleador]
GO
ALTER TABLE [dbo].[SocioEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SocioEmpleador_semPersona] FOREIGN KEY([semPersona])
REFERENCES [dbo].[Persona] ([perId])
GO
ALTER TABLE [dbo].[SocioEmpleador] CHECK CONSTRAINT [FK_SocioEmpleador_semPersona]
GO
ALTER TABLE [dbo].[Solicitud]  WITH CHECK ADD  CONSTRAINT [FK_Solicitud_solCajaCorrespondencia] FOREIGN KEY([solCajaCorrespondencia])
REFERENCES [dbo].[CajaCorrespondencia] ([ccoId])
GO
ALTER TABLE [dbo].[Solicitud] CHECK CONSTRAINT [FK_Solicitud_solCajaCorrespondencia]
GO
ALTER TABLE [dbo].[Solicitud]  WITH CHECK ADD  CONSTRAINT [FK_Solicitud_solTipoSolicitante] FOREIGN KEY([solTipoSolicitante])
REFERENCES [dbo].[TipoSolicitante] ([tsoId])
GO
ALTER TABLE [dbo].[Solicitud] CHECK CONSTRAINT [FK_Solicitud_solTipoSolicitante]
GO
ALTER TABLE [dbo].[SolicitudAfiliaciEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saeEmpleador] FOREIGN KEY([saeEmpleador])
REFERENCES [dbo].[Empleador] ([empId])
GO
ALTER TABLE [dbo].[SolicitudAfiliaciEmpleador] CHECK CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saeEmpleador]
GO
ALTER TABLE [dbo].[SolicitudAfiliaciEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saePaqueteEnvioPack] FOREIGN KEY([saePaqueteEnvioPack])
REFERENCES [dbo].[PaqueteEnvioBack] ([pebId])
GO
ALTER TABLE [dbo].[SolicitudAfiliaciEmpleador] CHECK CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saePaqueteEnvioPack]
GO
ALTER TABLE [dbo].[SolicitudAfiliaciEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saeSolicitudGlobal] FOREIGN KEY([saeSolicitudGlobal])
REFERENCES [dbo].[Solicitud] ([solId])
GO
ALTER TABLE [dbo].[SolicitudAfiliaciEmpleador] CHECK CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saeSolicitudGlobal]
GO
ALTER TABLE [dbo].[SucursalEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpleador_sueCodigoCIIU] FOREIGN KEY([sueCodigoCIIU])
REFERENCES [dbo].[CodigoCIIU] ([ciiId])
GO
ALTER TABLE [dbo].[SucursalEmpleador] CHECK CONSTRAINT [FK_SucursalEmpleador_sueCodigoCIIU]
GO
ALTER TABLE [dbo].[SucursalEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpleador_sueEmpleador] FOREIGN KEY([sueEmpleador])
REFERENCES [dbo].[Empleador] ([empId])
GO
ALTER TABLE [dbo].[SucursalEmpleador] CHECK CONSTRAINT [FK_SucursalEmpleador_sueEmpleador]
GO
ALTER TABLE [dbo].[SucursalEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpleador_sueMedioPagoSubsidioMonetario] FOREIGN KEY([sueMedioPagoSubsidioMonetario])
REFERENCES [dbo].[ParametrizacionMedioDePago] ([pmpId])
GO
ALTER TABLE [dbo].[SucursalEmpleador] CHECK CONSTRAINT [FK_SucursalEmpleador_sueMedioPagoSubsidioMonetario]
GO
ALTER TABLE [dbo].[SucursalEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpleador_sueUbicacion] FOREIGN KEY([sueUbicacion])
REFERENCES [dbo].[Ubicacion] ([ubiId])
GO
ALTER TABLE [dbo].[SucursalEmpleador] CHECK CONSTRAINT [FK_SucursalEmpleador_sueUbicacion]
GO
ALTER TABLE [dbo].[SucursaRolContactEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SucursaRolContactEmpleador_srcRolContactoEmpleador] FOREIGN KEY([srcRolContactoEmpleador])
REFERENCES [dbo].[RolContactoEmpleador] ([rceId])
GO
ALTER TABLE [dbo].[SucursaRolContactEmpleador] CHECK CONSTRAINT [FK_SucursaRolContactEmpleador_srcRolContactoEmpleador]
GO
ALTER TABLE [dbo].[SucursaRolContactEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SucursaRolContactEmpleador_srcSucursalEmpleador] FOREIGN KEY([srcSucursalEmpleador])
REFERENCES [dbo].[SucursalEmpleador] ([sueId])
GO
ALTER TABLE [dbo].[SucursaRolContactEmpleador] CHECK CONSTRAINT [FK_SucursaRolContactEmpleador_srcSucursalEmpleador]
GO
ALTER TABLE [dbo].[TipoSoliciCajaCompensacion]  WITH CHECK ADD  CONSTRAINT [FK_TipoSoliciCajaCompensacion_tscCajaCompensacion] FOREIGN KEY([tscCajaCompensacion])
REFERENCES [dbo].[CajaCompensacion] ([ccfId])
GO
ALTER TABLE [dbo].[TipoSoliciCajaCompensacion] CHECK CONSTRAINT [FK_TipoSoliciCajaCompensacion_tscCajaCompensacion]
GO
ALTER TABLE [dbo].[TipoSoliciCajaCompensacion]  WITH CHECK ADD  CONSTRAINT [FK_TipoSoliciCajaCompensacion_tscTipoSolicitante] FOREIGN KEY([tscTipoSolicitante])
REFERENCES [dbo].[TipoSolicitante] ([tsoId])
GO
ALTER TABLE [dbo].[TipoSoliciCajaCompensacion] CHECK CONSTRAINT [FK_TipoSoliciCajaCompensacion_tscTipoSolicitante]
GO
ALTER TABLE [dbo].[Ubicacion]  WITH CHECK ADD  CONSTRAINT [FK_Ubicacion_ubiMunicipio] FOREIGN KEY([ubiMunicipio])
REFERENCES [dbo].[Municipio] ([munId])
GO
ALTER TABLE [dbo].[Ubicacion] CHECK CONSTRAINT [FK_Ubicacion_ubiMunicipio]
GO
ALTER TABLE [dbo].[UbicacionEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpleador_ubeEmpleador] FOREIGN KEY([ubeEmpleador])
REFERENCES [dbo].[Empleador] ([empId])
GO
ALTER TABLE [dbo].[UbicacionEmpleador] CHECK CONSTRAINT [FK_UbicacionEmpleador_ubeEmpleador]
GO
ALTER TABLE [dbo].[UbicacionEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpleador_ubeUbicacion] FOREIGN KEY([ubeUbicacion])
REFERENCES [dbo].[Ubicacion] ([ubiId])
GO
ALTER TABLE [dbo].[UbicacionEmpleador] CHECK CONSTRAINT [FK_UbicacionEmpleador_ubeUbicacion]
GO
ALTER TABLE [dbo].[VariableComunicado]  WITH CHECK ADD  CONSTRAINT [FK_VariableComunicado_vcoPlantillaComunicado] FOREIGN KEY([vcoPlantillaComunicado])
REFERENCES [dbo].[PlantillaComunicado] ([pcoId])
GO
ALTER TABLE [dbo].[VariableComunicado] CHECK CONSTRAINT [FK_VariableComunicado_vcoPlantillaComunicado]
GO
