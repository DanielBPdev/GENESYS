--liquibase formatted sql

--changeset Heinsohn:01
--comment: Creación de tablas Auditoria


-- Creación de tabla  [Afiliado_aud] 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [Afiliado_aud](
	[afiId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[afiEstadoAfiliadoCaja] [varchar](20) NOT NULL,
	[afiPersona] [bigint] NULL,
	[afiPignoracionSubsidio] [bit] NULL,
	[afiCesionSubsidio] [bit] NULL,
	[afiRetencionSubsidio] [bit] NULL,
	[afiServicioSinAfiliacion] [bit] NULL,
	[afiCausaSinAfiliacion] [varchar](20) NULL,
	[afiFechaInicioServiciosSinAfiliacion] [date] NULL,
	[afifechaFinServicioSinAfiliacion] [date] NULL
 CONSTRAINT [PK_Afiliado_afiId] PRIMARY KEY CLUSTERED 
(
	[afiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  AFP_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AFP_aud](
	[afpId] [smallint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[afpNombre] [varchar](150) NULL,
	[afpCodigoPila] [varchar](10) NULL,
 CONSTRAINT [PK_AFP_afpId] PRIMARY KEY CLUSTERED 
(
	[afpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF


-- Creación de tabla  Aportante_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Aportante_aud](
	[apoId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[apoEmpleador] [bigint] NULL,
	[apoEntidadPagadora] [bigint] NULL,
	[apoPersona] [bigint] NULL,
	[apoEstadoAportanteInicial] [varchar](50) NULL,
	[apoEstadoAportanteFinal] [varchar](60) NULL,
	[apoEstadoAporteAportante] [varchar](40) NULL,
	[apoEstadoRegistroAporteAportante] [varchar](30) NULL,
 CONSTRAINT [PK_Aportante_apoId] PRIMARY KEY CLUSTERED 
(
	[apoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla  AporteDetallado_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AporteDetallado_aud](
	[apdId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[apdAporteGeneral] [bigint] NULL,
	[apdArchivoIRegistro2] [bigint] NULL,
	[apdArchivoIPRegistro2] [bigint] NULL,
	[apdCotizante] [bigint] NULL,
	[apdDiasCotizados] [smallint] NULL,
	[apdHorasLaboradas] [smallint] NULL,
	[apdSalarioBasico] [bigint] NULL,
	[apdUbicacionLaboral] [bigint] NULL,
	[apdValorIBC] [int] NULL,
	[apdValorIntMora] [int] NULL,
	[apdTarifa] [numeric](5, 5) NULL,
	[apdValorSalarioIntegral] [bigint] NULL,
	[apdAporteObligatorio] [bigint] NULL,
	[apdValorSaldoAporte] [bigint] NULL,
	[apdCorrecciones] [varchar](400) NULL,
	[apdFechaProcesamiento] [datetime] NULL,
	[apdEstadoAporteRecaudo] [varchar](50) NULL,
	[apdEstadoAporteAjuste] [varchar](50) NULL,
	[apdEstadoRegistroAporte] [varchar](50) NULL,
 CONSTRAINT [PK_AporteDetallado_apdId] PRIMARY KEY CLUSTERED 
(
	[apdId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

--Creación de tabla AporteDetalladoSimulado_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AporteDetalladoSimulado_aud](
	[adsId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[adsAporteGeneral] [bigint] NULL,
	[adsArchivoIRegistro2] [bigint] NULL,
	[adsArchivoIPRegistro2] [bigint] NULL,
	[adsCotizante] [bigint] NULL,
	[adsDiasCotizados] [smallint] NULL,
	[adsHorasLaboradas] [smallint] NULL,
	[adsSalarioBasico] [bigint] NULL,
	[adsUbicacionLaboral] [bigint] NULL,
	[adsValorIBC] [int] NULL,
	[adsValorIntMora] [int] NULL,
	[adsTarifa] [numeric](5, 5) NULL,
	[adsValorSalarioIntegral] [bigint] NULL,
	[adsAporteObligatorio] [bigint] NULL,
	[adsValorSaldoAporte] [bigint] NULL,
	[adsCorrecciones] [varchar](400) NULL,
	[adsFechaProcesamiento] [datetime] NULL,
	[adsEstadoAporteRecaudo] [varchar](50) NULL,
	[adsEstadoAporteAjuste] [varchar](50) NULL,
	[adsEstadoRegistroAporte] [varchar](50) NULL,
	[adsFechaProcesamientoValidRegAporte] [datetime] NULL,
	[adsEstadoValidacionV0] [varchar](30) NULL,
	[adsEstadoValidacionV1] [varchar](30) NULL,
	[adsEstadoValidacionV2] [varchar](30) NULL,
	[adsEstadoValidacionV3] [varchar](30) NULL,
 CONSTRAINT [PK_AporteDetallado_adsId] PRIMARY KEY CLUSTERED 
(
	[adsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creación de tabla AporteGeneral_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AporteGeneral_aud](
	[apgId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[apgIndicePlanilla] [bigint] NULL,
	[apgPeriodoAporte] [varchar](7) NULL,
	[apgValTotalApoObligatorio] [bigint] NULL,
	[apgValorIntMora] [bigint] NULL,
	[apgFechaRecaudo] [date] NULL,
	[apgFechaProcesamiento] [datetime] NULL,
	[apgAportante] [bigint] NULL,
	[apgCodEntidadFinanciera] [smallint] NULL,
	[apgOperadorInformacion] [bigint] NULL,
	[apgModalidadPlanilla] [varchar](40) NULL,
	[apgModalidadRecaudoAporte] [varchar](40) NULL,
 CONSTRAINT [PK_AporteGeneral_apgId] PRIMARY KEY CLUSTERED 
(
	[apgId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO



--Creación de tabla AporteGeneralSimulado_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AporteGeneralSimulado_aud](
	[agsId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[agsIndicePlanilla] [bigint] NULL,
	[agsPeriodoAporte] [varchar](7) NULL,
	[agsValTotalApoObligatorio] [bigint] NULL,
	[agsValorIntMora] [bigint] NULL,
	[agsFechaRecaudo] [date] NULL,
	[agsFechaProcesamiento] [datetime] NULL,
	[agsAportante] [bigint] NULL,
	[agsCodEntidadFinanciera] [smallint] NULL,
	[agsOperadorInformacion] [bigint] NULL,
	[agsModalidadPlanilla] [varchar](40) NULL,
	[agsModalidadRecaudoAporte] [varchar](40) NULL,
 CONSTRAINT [PK_AporteGeneral_agsId] PRIMARY KEY CLUSTERED 
(
	[agsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  AreaCajaCompensacion_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AreaCajaCompensacion_aud](
	[arcId] [smallint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[arcNombre] [varchar](30) NULL,
 CONSTRAINT [PK_AreaCajaCompensacion_arcId] PRIMARY KEY CLUSTERED 
(
	[arcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [ARL_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ARL_aud](
	[arlId] [smallint]  NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[arlNombre] [varchar](25) NULL,
 CONSTRAINT [PK_ARL_arlId] PRIMARY KEY CLUSTERED 
(
	[arlId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO



--Creación de tabla  AsesorResponsableEmpleador_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AsesorResponsableEmpleador_aud](
	[areId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[areNombreUsuario] [varchar](255) NULL,
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


--Creación de tabla beneficiario_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [Beneficiario_aud](
	[benId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[benCertificadoEscolaridad] [bit] NULL,
	[benComentariosInvalidez] [varchar](500) NULL,
	[benEstadoBeneficiarioAfiliado] [varchar](20) NULL,
	[benEstadoBeneficiarioCaja] [varchar](20) NULL,
	[benEstudianteTrabajoDesarrolloHumano] [bit] NULL,
	[benFechaAfiliacion] [date] NULL,
	[benFechaRecepcionCertificadoEscolar] [date] NULL,
	[benFechaReporteInvalidez] [date] NULL,
	[benFechaVencimientoCertificadoEscolar] [date] NULL,
	[benInvalidez] [bit] NULL,
	[benLabora] [bit] NULL,
	[benTipoBeneficiario] [varchar](30) NOT NULL,
	[benGrupoFamiliar] [bigint] NULL,
	[benPersona] [bigint] NOT NULL,
	[benAfiliado] [bigint] NOT NULL,
	[benSalarioMensualBeneficiario] [numeric](19, 0) NULL,
	[benGradoAcademico] [smallint] NULL,
	[benMotivoDesafiliacion] [varchar](70) NULL,
	[benFechaRetiro] [date] NULL,
	[benFechaInicioSociedadConyugal] [date] NULL,
	[benFechaFinSociedadConyugal] [date] NULL,
 CONSTRAINT [PK_Beneficiario_benId] PRIMARY KEY CLUSTERED 
(
	[benId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

--Creación de tabla BeneficioEmpleador_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [BeneficioEmpleador_aud](
	[bemId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[bemTipoBeneficio] [varchar](10) NULL,
	[bemBeneficioActivo] [bit] NULL,
	[bemFechaBeneficioInicio] [date] NULL,
	[bemFechaBeneficioFin] [date] NULL,
	[bemMotivoInactivacionBeneficioLey1429] [varchar](50) NULL,
	[bemMotivoInactivacionBeneficioLey590] [varchar](50) NULL,
	[bemEmpleador] [bigint] NOT NULL,
 CONSTRAINT [PK_Beneficio_bemId] PRIMARY KEY CLUSTERED 
(
	[bemId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

--Creación de tabla CajaCompensacion_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [CajaCompensacion_aud](
	[ccfId] [int] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ccfHabilitado] [bit] NULL,
	[ccfMetodoGeneracionEtiquetas] [varchar](20) NULL,
	[ccfNombre] [varchar](100) NULL,
	[ccfSocioAsopagos] [bit] NULL,
	[ccfDepartamento] [smallint] NULL,
	[ccfCodigo] [varchar](5) NULL,
	[ccfCodigoRedeban] [int] NULL,
 CONSTRAINT [PK_CajaCompensacion_ccfId] PRIMARY KEY CLUSTERED 
(
	[ccfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creación de tabla CajaCorrespondencia_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [CajaCorrespondencia_aud](
	[ccoId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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

--Creación de tabla CargueMultiple_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [CargueMultiple_aud](
	[camId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[camCodigoCargueMultiple] [bigint] NOT NULL,
	[camIdSucursalEmpleador] [bigint] NOT NULL,
	[camIdEmpleador] [bigint] NOT NULL,
	[camTipoSolicitante] [varchar](30) NOT NULL,
	[camClasificacion] [varchar](100) NOT NULL,
	[camTipoTransaccion] [varchar](100) NULL,
	[camProceso] [varchar](100) NOT NULL,
	[camEstado] [varchar](20) NOT NULL,
	[camFechaCarga] [date] NOT NULL,
	[camCodigoIdentificacionECM] [varchar](255) NOT NULL,
 CONSTRAINT [PK_CargueAfiliacionMultiple_camId] PRIMARY KEY CLUSTERED 
(
	[camId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creación tabla [CargueMultipleSupervivencia_aud]
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [CargueMultipleSupervivencia_aud](
	[cmsId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[cmsFechaIngreso] [date] NOT NULL,
	[cmsUsuario] [varchar](255) NOT NULL,
	[cmsPeriodo] [date] NULL,
	[cmsIdentificacionECM] [varchar](255) NOT NULL,
	[cmsEstadoCargueSupervivencia] [varchar](255) NOT NULL,
	[cmsNombreArchivo] [varchar](30) NOT NULL,
 CONSTRAINT [PK_CargueMultipleSupervivencia_cmsId] PRIMARY KEY CLUSTERED 
(
	[cmsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creación de tabla [Categoria_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Categoria_aud](
	[catId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[catTipoAfiliado] [varchar](30) NOT NULL,
	[catCategoriaPersona] [varchar](50) NOT NULL,
	[catTipoBeneficiario] [varchar](30) NULL,
	[catClasificacion] [varchar](30) NOT NULL,
	[catTotalIngresoMesada] [numeric](19, 0) NOT NULL,
	[catFechaCambioCategoria] [date] NOT NULL,
	[catMotivoCambioCategoria] [varchar](50) NOT NULL,
	[catAfiliadoPrincipal] [bit] NOT NULL,
	[catIdAfiliado] [bigint] NOT NULL,
	[catIdBeneficiario] [bigint] NULL,
 CONSTRAINT [PK_Categoria_catId] PRIMARY KEY CLUSTERED 
(
	[catId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creación de tabla CodigoCIIU_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [CodigoCIIU_aud](
	[ciiId] [smallint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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


--Creación de tabla [Comunicado_aud]    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Comunicado_aud](
	[comId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[comEmail] [varchar](255) NULL,
	[comIdentificaArchivoComunicado] [varchar](255) NULL,
	[comTextoAdicionar] [varchar](500) NULL,
	[comPlantillaComunicado] [bigint] NULL,
	[comFechaComunicado] [datetime2](7) NULL,
	[comRemitente] [varchar](255) NULL,
	[comSedeCajaCompensacion] [varchar](2) NULL,
	[comNumeroCorreoMasivo] [bigint] NULL,
	[comDestinatario] [varchar](255) NULL,
	[comEstadoEnvio] [varchar](20) NULL,
	[comMensajeEnvio] [varchar](max) NULL,
	[comMedioComunicado] [varchar](10) NULL,
	[comSolicitud] [bigint] NULL,
 CONSTRAINT [PK_Comunicado_comId] PRIMARY KEY CLUSTERED 
(
	[comId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creación de tabla CondicionInvalidez_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [CondicionInvalidez_aud](
	[coiId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[coiPersona] [bigint] NOT NULL,
	[coiInvalidez] [bit] NULL,
	[coiFechaReporteInvalidez] [date] NULL,
	[coiComentarioInvalidez] [varchar](500) NULL,
 CONSTRAINT [PK_CondicionInvalidez_coiId] PRIMARY KEY CLUSTERED 
(
	[coiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [ConexionOperadorInformacion_aud]
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ConexionOperadorInformacion_aud](
	[coiId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[coiOperadorInformacionCcf] [bigint] NULL,
	[coiProtocolo] [varchar](10) NULL,
	[coiUrl] [varchar](255) NULL,
	[coiPuerto] [smallint] NULL,
	[coiHost] [varchar](75) NULL,
	[coiUsuario] [varchar](255) NULL,
	[coiContrasena] [varchar](32) NULL,
 CONSTRAINT [PK_PilaConexionOperadorInformacion_coiId] PRIMARY KEY CLUSTERED 
(
	[coiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

--Creación de tabla ConsolaEstadoCargueMasivo_aud
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ConsolaEstadoCargueMasivo_aud](
	[cecId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[cecCcf] [varchar](5) NULL,
	[cecTipoProcesoMasivo] [varchar](40) NOT NULL,
	[cecUsuario] [varchar](255) NULL,
	[cecFechaInicio] [datetime2](7) NULL,
	[cecFechaFin] [datetime2](7) NULL,
	[cecNumRegistroObjetivo] [bigint] NOT NULL,
	[cecNumRegistroProcesado] [bigint] NOT NULL,
	[cecNumRegistroConErrores] [bigint] NULL,
	[cecNumRegistroValidos] [bigint] NULL,
	[cecEstadoCargueMasivo] [varchar](15) NOT NULL,
	[cecCargueId] [bigint] NULL,
	[cecFileLoadedId] [bigint] NULL,
	[cecIdentificacionECM] [varchar](255) NOT NULL,
	[cecGradoAvance] [numeric](6, 3) NOT NULL,
 CONSTRAINT [PK_ConsolaEstadoCargueMasivo_cecId] PRIMARY KEY CLUSTERED 
(
	[cecId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creación de tabla  [Constante_aud]    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Constante_aud](
	[cnsId] [int] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[cnsNombre] [varchar](100) NULL,
	[cnsValor] [varchar](100) NULL,
 CONSTRAINT [PK_Constante_cnsId] PRIMARY KEY CLUSTERED 
(
	[cnsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
--Creación de tabla [Cotizante_aud]    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Cotizante_aud](
	[cotId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[cotTipoCotizante] [varchar](100) NULL,
	[cotAfiliadoCotizante] [bigint] NULL,
	[cotEstadoCotizanteInicial] [varchar](60) NULL,
	[cotEstadoCotizanteFinal] [varchar](60) NULL,
	[cotEstadoAporteCotizante] [varchar](50) NULL,
	[cotEstadoRegistroAporteCotizante] [varchar](40) NULL,
 CONSTRAINT [PK_Cotizante_cotId] PRIMARY KEY CLUSTERED 
(
	[cotId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


--Creación de tabla [DatosRegistroValidacion_aud]    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DatosRegistroValidacion_aud](
	[drvId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
 CONSTRAINT [PK_DatosRegistroValidaciono_drvId] PRIMARY KEY CLUSTERED 
(
	[drvId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


--Creación de tabla [DatoTemporalSolicitud_aud]
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DatoTemporalSolicitud_aud](
	[dtsId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dtsSolicitud] [bigint] NULL,
	[dtsJsonPayload] [text] NULL,
 CONSTRAINT [PK_DatoTemporalSolicitud_dtsId] PRIMARY KEY CLUSTERED 
(
	[dtsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO


--Creación de tabla [Departamento_aud]
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Departamento_aud](
	[depId] [smallint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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


--Creación de tabla [DetalleDatosRegistroValidacion_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [DetalleDatosRegistroValidacion_aud](
	[ddrIdDato] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ddrValorDetalle] [varchar](150) NULL,
	[ddrIdDetalle] [varchar](50) NOT NULL,
 CONSTRAINT [PK_DetalleDatosRegistroValidacion_ddrvIdDato_ddrIdDetalle] PRIMARY KEY CLUSTERED 
(
	[ddrIdDato] ASC,
	[ddrIdDetalle] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla  [DiasFestivos_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [DiasFestivos_aud](
	[pifId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pifConcepto] [varchar](100) NULL,
	[pifFecha] [date] NULL,
 CONSTRAINT [PK_PilaDiasFestivos_pifId] PRIMARY KEY CLUSTERED 
(
	[pifId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [DocumentoAdministracionEstadoSolicitud_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [DocumentoAdministracionEstadoSolicitud_aud](
	[daeId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[daeSolicitud] [bigint] NULL,
	[daeDocumentoSoporteCambioEstado] [varchar](100) NULL,
	[daeNombreDocumento] [varchar](100) NULL,
 CONSTRAINT [PK_DocumentoAdministracionEstadoSolicitud_daeId] PRIMARY KEY CLUSTERED 
(
	[daeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [DocumentoEntidadPagadora_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [DocumentoEntidadPagadora_aud](
	[dpgId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dpgEntidadPagadora] [bigint] NULL,
	[dpgIdentificadorDocumento] [varchar](255) NULL,
	[dpgTipoDocumento] [varchar](50) NULL,
	[dpgNombreDocumento] [varchar](60) NULL,
	[dpgVersionDocumento] [smallint] NULL,
 CONSTRAINT [PK_DocumentoEntidadPagadora_dpgId] PRIMARY KEY CLUSTERED 
(
	[dpgId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [ElementoDireccion_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ElementoDireccion_aud](
	[eldId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[eldNombre] [varchar](20) NULL,
 CONSTRAINT [PK_ElementoDireccion_eldId] PRIMARY KEY CLUSTERED 
(
	[eldId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [Empleador_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Empleador_aud](
	[empId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[empEstadoAportesEmpleador] [varchar](20) NULL,
	[empEstadoEmpleador] [varchar](50) NULL,
	[empExpulsionSubsanada] [bit] NULL,
	[empFechaCambioEstadoAfiliacion] [datetime2](7) NULL,
	[empMotivoDesafiliacion] [varchar](100) NULL,
	[empNumeroTotalTrabajadores] [int] NULL,
	[empPeriodoUltimaNomina] [date] NULL,
	[empValorTotalUltimaNomina] [numeric](19, 0) NULL,
	[empMedioPagoSubsidioMonetario] [smallint] NULL,
	[empEmpresa] [bigint] NULL,
	[empFechaRetiro] [date] NULL,
 CONSTRAINT [PK_Empleador_empId] PRIMARY KEY CLUSTERED 
(
	[empId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [Empresa_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Empresa_aud](
	[empId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[empPersona] [bigint] NULL,
	[empNombreComercial] [varchar](250) NULL,
	[empFechaConstitucion] [date] NULL,
	[empNaturalezaJuridica] [varchar](100) NULL,
	[empCodigoCIIU] [smallint] NULL,
	[empArl] [smallint] NULL,
	[empUltimaCajaCompensacion] [int] NULL,
	[empPaginaWeb] [varchar](256) NULL,
	[empRepresentanteLegal] [bigint] NULL,
	[empRepresentanteLegalSuplente] [bigint] NULL,
	[empEspecialRevision] [bit] NULL,
	[empUbicacionRepresentanteLegal] [bigint] NULL,
	[empUbicacionRepresentanteLegalSuplente] [bigint] NULL,
 CONSTRAINT [PK_Empresa_empId] PRIMARY KEY CLUSTERED 
(
	[empId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [EntidadPagadora_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [EntidadPagadora_aud](
	[epaId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[epaAportante] [bit] NULL,
	[epaCanalComunicacion] [varchar](20) NULL,
	[epaEmailComunicacion] [varchar](255) NULL,
	[epaEstadoEntidadPagadora] [varchar](20) NULL,
	[epaMedioComunicacion] [varchar](50) NULL,
	[epaNombreContacto] [varchar](50) NULL,
	[epaEmpresa] [bigint] NULL,
	[epaSucursalPagadora] [bigint] NULL,
	[epaTipoAfiliacion] [varchar](50) NULL,
	[epaCargoContacto] [varchar](20) NULL,
 CONSTRAINT [PK_Empresa_epaId] PRIMARY KEY CLUSTERED 
(
	[epaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [EscalamientoSolicitud_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [EscalamientoSolicitud_aud](
	[esoId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[esoSolicitud] [bigint] NOT NULL,
	[esoAsunto] [varchar](100) NOT NULL,
	[esoDescripcion] [varchar](255) NOT NULL,
	[esoDestinatario] [varchar](255) NULL,
	[esoResultadoAnalista] [varchar](30) NULL,
	[esoComentarioAnalista] [varchar](255) NULL,
	[esoIdentificadorDocumentoSoporteAnalista] [varchar](255) NULL,
	[esoUsuarioCreacion] [varchar](255) NULL,
	[esoFechaCreacion] [datetime2](7) NULL,
 CONSTRAINT [PK_EscalamientoSolicitud_esoId] PRIMARY KEY CLUSTERED 
(
	[esoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
-- Creación de tabla [EtiquetaCorrespondenciaRadicado_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [EtiquetaCorrespondenciaRadicado_aud](
	[eprId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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

-- Creación de tabla [FieldCatalog_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FieldCatalog_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
 CONSTRAINT [PK_FieldCatalog_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [FieldDefinition_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FieldDefinition_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[finalPosition] [bigint] NULL,
	[formatDate] [varchar](40) NULL,
	[initialPosition] [bigint] NULL,
	[label] [varchar](70) NULL,
	[fieldOrder] [bigint] NULL,
	[footerOperation] [varchar](255) NULL,
	[generateEvidence] [bit] NULL,
	[fieldCatalog_id] [bigint] NULL,
	[lineDefinition_id] [bigint] NULL,
 CONSTRAINT [PK_FieldDefinition_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [FieldDefinitionLoad_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FieldDefinitionLoad_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[finalPosition] [bigint] NULL,
	[formatDate] [varchar](40) NULL,
	[initialPosition] [bigint] NULL,
	[label] [varchar](70) NULL,
	[identifierLine] [bit] NULL,
	[removeFormat] [bit] NULL,
	[required] [bit] NULL,
	[fieldLoadCatalog_id] [bigint] NULL,
	[lineDefinition_id] [bigint] NULL,
 CONSTRAINT [PK_FieldDefinitionLoad_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [FieldGenericCatalog_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FieldGenericCatalog_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dataType] [int] NULL,
	[description] [varchar](255) NULL,
	[label] [varchar](255) NULL,
	[maxDecimalSize] [bigint] NULL,
	[minDecimalSize] [bigint] NULL,
	[name] [varchar](255) NULL,
 CONSTRAINT [PK_FieldGenericCatalog_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [FieldLoadCatalog_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FieldLoadCatalog_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dataType] [varchar](255) NOT NULL,
	[description] [varchar](255) NULL,
	[maxDecimalSize] [bigint] NULL,
	[minDecimalSize] [bigint] NULL,
	[name] [varchar](70) NOT NULL,
	[fieldOrder] [bigint] NULL,
	[tenantId] [bigint] NULL,
 CONSTRAINT [PK_FieldLoadCatalog_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [FileDefinition_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileDefinition_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
 CONSTRAINT [PK_FileDefinition_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [FileDefinitionLoad_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileDefinitionLoad_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[decimalSeparator] [char](1) NOT NULL,
	[nombre] [varchar](70) NULL,
	[tenantId] [bigint] NULL,
	[thousandsSeparator] [char](1) NULL,
	[excludeLines] [bit] NULL,
	[registersRead] [int] NULL,
	[sheetNumberXls] [bigint] NULL,
	[useCharacters] [bit] NULL,
	[fileDefinitionType_id] [bigint] NULL,
 CONSTRAINT [PK_FileDefinitionLoad_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [FileDefinitionLoadType_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileDefinitionLoadType_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[description] [varchar](255) NULL,
	[name] [varchar](70) NOT NULL,
 CONSTRAINT [PK_FileDefinitionLoadType_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [FileDefinitionType_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileDefinitionType_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[description] [varchar](255) NULL,
	[name] [varchar](70) NOT NULL,
 CONSTRAINT [PK_FileDefinitionType_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [FileGenerated_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileGenerated_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[finalDate] [datetime2](7) NULL,
	[initialDate] [datetime2](7) NULL,
	[formats] [varchar](255) NULL,
	[registersGenerated] [bigint] NULL,
	[state] [varchar](255) NULL,
	[userName] [varchar](255) NULL,
	[fileDefinition_id] [bigint] NULL,
 CONSTRAINT [PK_FileGenerated_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [FileGeneratedLog_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileGeneratedLog_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[lineNumber] [bigint] NULL,
	[message] [varchar](max) NULL,
	[fileGenerated_id] [bigint] NULL,
 CONSTRAINT [PK_FileGeneratedLog_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [FileLoaded_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileLoaded_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[finalDate] [datetime] NULL,
	[initialDate] [datetime] NULL,
	[fileLoadedName] [varchar](255) NULL,
	[format] [varchar](255) NULL,
	[loadedRegisters] [bigint] NULL,
	[state] [varchar](255) NULL,
	[fileDefinition_id] [bigint] NULL,
	[validatorprofile] [bigint] NULL,
 CONSTRAINT [PK_FileLoaded_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [FileLoadedLog_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileLoadedLog_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[lineNumber] [bigint] NULL,
	[message] [varchar](max) NULL,
	[fileLoaded_id] [bigint] NULL,
 CONSTRAINT [PK_FileLoadedLog_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [FileLocationCommon_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileLocationCommon_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
 CONSTRAINT [PK_FileLocationCommon_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [GlosaComentarioNovedad_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [GlosaComentarioNovedad_aud](
	[gcnId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[gcnNombreGlosaNovedad] [varchar](60) NULL,
	[gcnDescripcionGlosaNovedad] [varchar](400) NULL,
	[gcnEstadoGlosaNovedad] [bit] NULL,
 CONSTRAINT [PK_GlosaComentarioNovedad_gcnId] PRIMARY KEY CLUSTERED 
(
	[gcnId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [GradoAcademico_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [GradoAcademico_aud](
	[graId] [smallint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[graNombre] [varchar](20) NULL,
	[graNivelEducativo] [varchar](43) NULL,
 CONSTRAINT [PK_GradoAcademico_graId] PRIMARY KEY CLUSTERED 
(
	[graId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [GraphicFeature_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [GraphicFeature_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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


-- Creación de tabla [GraphicFeatureDefinition]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [GraphicFeatureDefinition_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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


-- Creación de tabla [GroupFC_FieldGC_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GroupFC_FieldGC_aud](
	[fieldGenericCatalogs_id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[groupFieldCatalogs_id] [bigint] NOT NULL
) ON [PRIMARY]

GO

-- Creación de tabla [GroupFieldCatalogs_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [GroupFieldCatalogs_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[description] [varchar](255) NULL,
	[name] [varchar](255) NULL,
 CONSTRAINT [PK_GroupFieldCatalogs_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
-- Creación de tabla [GrupoFamiliar_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [GrupoFamiliar_aud](
	[grfId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[grfParametrizacionMedioPago] [smallint] NULL,
	[grfNumero] [smallint] NOT NULL,
	[grfObservaciones] [varchar](500) NULL,
	[grfAdministradorSubsidio] [bigint] NULL,
	[grfAfiliado] [bigint] NOT NULL,
	[grfUbicacion] [bigint] NULL,
	[grfRelacionGrupoFamiliar] [varchar](16) NULL,
	[grfInembargable] [bit] NULL
 CONSTRAINT [PK_GrupoFamiliar_grfId] PRIMARY KEY CLUSTERED 
(
	[grfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [HistoriaResultadoValidacion_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [HistoriaResultadoValidacion_aud](
	[hrvId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[hrvDetalle] [varchar](255) NULL,
	[hrvResultado] [varchar](20) NULL,
	[hrvValidacion] [varchar](60) NULL,
	[hrvIdDatosRegistro] [bigint] NULL,
	[hrvTipoExepcion] [varchar](30) NULL,
 CONSTRAINT [PK_HistoriaResultadoValidacion_hrvId] PRIMARY KEY CLUSTERED 
(
	[hrvId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [IntentoAfiliacion_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [IntentoAfiliacion_aud](
	[iafId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[iafCausaIntentoFallido] [varchar](50) NULL,
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


-- Creación de tabla [IntentoAfiliRequisito_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoAfiliRequisito_aud](
	[iarId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[iarIntentoAfiliacion] [bigint] NULL,
	[iarRequisito] [bigint] NULL,
 CONSTRAINT [PK_IntentoAfiliRequisito_iarId] PRIMARY KEY CLUSTERED 
(
	[iarId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


-- Creación de tabla [IntentoNovedad_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [IntentoNovedad_aud](
	[inoId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[inoCausaIntentoFallido] [varchar](255) NULL,
	[inoFechaInicioProceso] [datetime2](7) NULL,
	[inoSolicitud] [bigint] NULL,
	[inoTipoTransaccion] [varchar](100) NULL,
 CONSTRAINT [PK_IntentoNovedad_inoId] PRIMARY KEY CLUSTERED 
(
	[inoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [IntentoNoveRequisito_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoNoveRequisito_aud](
	[inrId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[inrRequisito] [bigint] NULL,
	[inrIntentoNovedad] [bigint] NULL,
 CONSTRAINT [PK_IntentoNoveRequisito_id] PRIMARY KEY CLUSTERED 
(
	[inrId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

-- Creación de tabla [ItemChequeo_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [ItemChequeo_aud](
	[ichId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ichSolicitud] [bigint] NULL,
	[ichRequisito] [bigint] NULL,
	[ichPersona] [bigint] NULL,
	[ichIdentificadorDocumento] [varchar](255) NULL,
	[ichVersionDocumento] [smallint] NULL,
	[ichEstadoRequisito] [varchar](20) NULL,
	[ichPrecargado] [bit] NULL,
	[ichCumpleRequisito] [bit] NULL,
	[ichFormatoEntregaDocumento] [varchar](20) NULL,
	[ichComentarios] [varchar](255) NULL,
	[ichCumpleRequisitoBack] [bit] NULL,
	[ichComentariosBack] [varchar](255) NULL,
	[ichIdentificadorDocumentoPrevio] [varchar](255) NULL,
 CONSTRAINT [PK_ItemChequeo_ichId] PRIMARY KEY CLUSTERED 
(
	[ichId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [LineCatalog_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [LineCatalog_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[className] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[name] [varchar](70) NOT NULL,
	[tenantId] [bigint] NULL,
	[paginated] [bit] NOT NULL,
	[query] [varchar](max) NULL,
	[queryType] [varchar](255) NULL,
 CONSTRAINT [PK_LineCatalog_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [LineDefinition_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [LineDefinition_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
 CONSTRAINT [PK_LineDefinition_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [LineDefinitionLoad_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [LineDefinitionLoad_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[identifier] [varchar](255) NULL,
	[numberGroup] [bigint] NULL,
	[required] [bit] NULL,
	[requiredInGroup] [bit] NULL,
	[rollbackOrder] [bigint] NULL,
	[fileDefinition_id] [bigint] NULL,
	[lineLoadCatalog_id] [bigint] NULL,
 CONSTRAINT [PK_LineDefinitionLoad_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [LineLoadCatalog_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [LineLoadCatalog_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[className] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[name] [varchar](70) NOT NULL,
	[tenantId] [bigint] NULL,
	[lineOrder] [bigint] NULL,
	[lineSeparator] [varchar](255) NULL,
	[targetEntity] [varchar](255) NULL,
 CONSTRAINT [PK_LineLoadCatalog_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [ListaEspecialRevision_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ListaEspecialRevision_aud](
	[lerId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[lerTipoIdentificacion] [varchar](20) NOT NULL,
	[lerNumeroIdentificacion] [varchar](16) NOT NULL,
	[lerDigitoVerificacion] [tinyint] NULL,
	[lerCajaCompensacion] [int] NULL,
	[lerNombreEmpleador] [varchar](200) NULL,
	[lerFechaInicioInclusion] [date] NULL,
	[lerFechaFinInclusion] [date] NULL,
	[lerRazonInclusion] [varchar](20) NULL,
	[lerEstado] [varchar](20) NULL,
	[lerComentario] [varchar](255) NULL,
 CONSTRAINT [PK_ListaEspecialRevision_lerId] PRIMARY KEY CLUSTERED 
(
	[lerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [MovimientoAjusteAporte_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [MovimientoAjusteAporte_aud](
	[maaId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[maaTipoMovimientoRecaudoAporte] [varchar](40) NULL,
	[maaIndicePlanillaOriginal] [bigint] NULL,
	[maaIndicePlanillaCorregida] [bigint] NULL,
	[maaAporteDetalladoOriginal] [bigint] NULL,
	[maaAporteDetalladoCorregido] [bigint] NULL,
	[maaPeriodoMovimientoAporte] [varchar](7) NULL,
	[maaFechaRegisroMovimientoAporte] [datetime] NULL,
	[maaValorAporteRegOriginal] [numeric](38, 3) NULL,
	[maaValorIntAporteRegOriginal] [numeric](38, 3) NULL,
	[maaValorTotalAporteRegOriginal] [numeric](38, 3) NULL,
	[maaTipoAjusteMovimientoAporte] [varchar](40) NULL,
	[maaValorAjusteRegAporte] [numeric](38, 3) NULL,
	[maaValorIntAjusteRegAporte] [numeric](38, 3) NULL,
	[maaValorTotalAjusteRegAporte] [numeric](38, 3) NULL,
	[maaValorFinalRegAporte] [numeric](38, 3) NULL,
	[maaValorIntFinalRegAporte] [numeric](38, 3) NULL,
	[maaValorTotalFinalRegAporte] [numeric](38, 3) NULL,
	[maaEstadoAjusteRegAporte] [varchar](40) NULL,
 CONSTRAINT [PK_MovimientoAjusteAporte_maaId] PRIMARY KEY CLUSTERED 
(
	[maaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [Municipio_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Municipio_aud](
	[munId] [smallint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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

-- Creación de tabla [NotificacionDestinatario_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [NotificacionDestinatario_aud](
	[nodId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[nodNotEnviada] [bigint] NULL,
	[nodDestintatario] [varchar](255) NULL,
	[nodTipoDestintatario] [varchar](3) NULL,
 CONSTRAINT [PK_NotDestinatario_nodId] PRIMARY KEY CLUSTERED 
(
	[nodId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [NotificacionEnviada_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [NotificacionEnviada_aud](
	[noeId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[noeFechaEnvio] [datetime2](7) NULL,
	[noeRemitente] [varchar](255) NULL,
	[noeSccfId] [bigint] NULL,
	[noeProcesoEvento] [varchar](255) NULL,
	[noeEstadoEnvioNot] [varchar](20) NULL,
	[noeError] [varchar](4000) NULL,
 CONSTRAINT [PK_NotEnviada_noeId] PRIMARY KEY CLUSTERED 
(
	[noeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [Novedad_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Novedad_aud](
	[novId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[novTipoTransaccion] [varchar](100) NULL,
	[novPuntoResolucion] [varchar](255) NULL,
	[novRutaCualificada] [varchar](255) NULL,
	[novTipoNovedad] [varchar](255) NULL,
	[novProceso] [varchar](50) NULL,
 CONSTRAINT [PK_Novedad_novId] PRIMARY KEY CLUSTERED 
(
	[novId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [NovedadPila_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [NovedadPila_aud](
	[nopId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[nopRolAfiliado] [bigint] NOT NULL,
	[nopTipoNovedadPila] [varchar](15) NOT NULL,
	[nopFechaInicio] [date] NULL,
	[nopFechaFin] [date] NULL,
	[nopVigente] [bit] NULL,
 CONSTRAINT [PK_NovedadPila_nopId] PRIMARY KEY CLUSTERED 
(
	[nopId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [OcupacionProfesion_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [OcupacionProfesion_aud](
	[ocuId] [int] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ocuNombre] [varchar](100) NULL,
 CONSTRAINT [PK_OcupacionProfesion_ocuId] PRIMARY KEY CLUSTERED 
(
	[ocuId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [OperadorInformacion_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [OperadorInformacion_aud](
	[oinId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[oinCodigo] [varchar](2) NOT NULL,
	[oinNombre] [varchar](75) NOT NULL,
	[oinOperadorActivo] [bit] NOT NULL,
 CONSTRAINT [PK_PilaOperadorInformacion_oinId] PRIMARY KEY CLUSTERED 
(
	[oinId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [OperadorInformacionCcf_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [OperadorInformacionCcf_aud](
	[oicId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[oicOperadorInformacion] [bigint] NULL,
	[oicCajaCompensacion] [int] NULL,
	[oicEstado] [bit] NULL,
 CONSTRAINT [PK_PilaOperadorInformacionCcf_oicId] PRIMARY KEY CLUSTERED 
(
	[oicId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


-- Creación de tabla [parameter_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [parameter_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[description] [varchar](100) NULL,
	[name] [varchar](20) NOT NULL,
	[type] [varchar](50) NULL,
 CONSTRAINT [PK_parameter_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [ParametrizacionEjecucionProgramada_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ParametrizacionEjecucionProgramada_aud](
	[pepId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pepProceso] [varchar](100) NOT NULL,
	[pepHoras] [varchar](20) NULL,
	[pepMinutos] [varchar](20) NULL,
	[pepSegundos] [varchar](20) NULL,
	[pepDiaSemana] [varchar](50) NULL,
	[pepDiaMes] [varchar](50) NULL,
	[pepMes] [varchar](50) NULL,
	[pepAnio] [varchar](50) NULL,
	[pepFechaInicio] [date] NULL,
	[pepFechaFin] [date] NULL,
	[pepFrecuencia] [varchar](50) NOT NULL,
 CONSTRAINT [PK_ParametrizacionEjecucionProgramada_pepID] PRIMARY KEY CLUSTERED 
(
	[pepId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [ParametrizacionMedioDePago_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ParametrizacionMedioDePago_aud](
	[pmpId] [smallint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pmpMedioPago] [varchar](50) NULL,
 CONSTRAINT [PK_ParametrizacionMedioDePago_pmpId] PRIMARY KEY CLUSTERED 
(
	[pmpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [ParametrizacionMetodoAsignacion_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ParametrizacionMetodoAsignacion_aud](
	[pmaId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pmaSedeCajaCompensacion] [bigint] NOT NULL,
	[pmaProceso] [varchar](100) NOT NULL,
	[pmaMetodoAsignacion] [varchar](20) NULL,
	[pmaUsuario] [varchar](255) NULL,
	[pmaGrupo] [varchar](50) NULL,
 CONSTRAINT [PK_ParametrizacionMetodoAsignacion_pmaId] PRIMARY KEY CLUSTERED 
(
	[pmaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [ParametrizaEnvioComunicado_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ParametrizaEnvioComunicado_aud](
	[pecId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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

-- Creación de tabla [Parametro_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Parametro_aud](
	[prmId] [int] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[prmNombre] [varchar](100) NULL,
	[prmValor] [varchar](150) NULL,
 CONSTRAINT [PK_Parametro_prmId] PRIMARY KEY CLUSTERED 
(
	[prmId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [ParamValue_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ParamValue_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[entityEnterpriseId] [bigint] NULL,
	[paramValue] [varchar](255) NULL,
	[parameter_id] [bigint] NULL,
 CONSTRAINT [PK_ParamValue_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [Persona_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Persona_aud](
	[perId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[perDigitoVerificacion] [smallint] NULL,
	[perNumeroIdentificacion] [varchar](16) NULL,
	[perRazonSocial] [varchar](250) NULL,
	[perTipoIdentificacion] [varchar](20) NULL,
	[perUbicacionPrincipal] [bigint] NULL,
	[perPrimerNombre] [varchar](50) NULL,
	[perSegundoNombre] [varchar](50) NULL,
	[perPrimerApellido] [varchar](50) NULL,
	[perSegundoApellido] [varchar](50) NULL,
 CONSTRAINT [PK_Persona_perId] PRIMARY KEY CLUSTERED 
(
	[perId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PersonaDetalle_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PersonaDetalle_aud](
	[pedId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pedPersona] [bigint] NOT NULL,
	[pedFechaNacimiento] [date] NULL,
	[pedFechaExpedicionDocumento] [date] NULL,
	[pedGenero] [varchar](10) NULL,
	[pedOcupacionProfesion] [int] NULL,
	[pedNivelEducativo] [varchar](100) NULL,
	[pedGradoAcademico] [smallint] NULL,
	[pedCabezaHogar] [bit] NULL,
	[pedAutorizaUsoDatosPersonales] [bit] NULL,
	[pedResideSectorRural] [bit] NULL,
	[pedMedioPago] [smallint] NULL,
	[pedEstadoCivil] [varchar](20) NULL,
	[pedHabitaCasaPropia] [bit] NULL,
	[pedFallecido] [bit] NULL,
	[pedFechaFallecido] [date] NULL,
 CONSTRAINT [PK_PersonaDetalle_pedId] PRIMARY KEY CLUSTERED 
(
	[pedId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PilaArchivoAPRegistro1]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoAPRegistro1_aud](
	[ap1Id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ap1IndicePlanilla] [bigint] NOT NULL,
	[ap1NombrePagador] [varchar](150) NOT NULL,
	[ap1TipoIdPagador] [varchar](2) NOT NULL,
	[ap1IdPagador] [varchar](16) NOT NULL,
	[ap1DigVerPagador] [smallint] NULL,
	[ap1CodSucursal] [varchar](10) NULL,
	[ap1NomSucursal] [varchar](40) NULL,
	[ap1ClasePagador] [varchar](1) NOT NULL,
	[ap1NaturalezaJuridica] [smallint] NOT NULL,
	[ap1TipoPersona] [varchar](1) NOT NULL,
	[ap1FormaPresentacion] [varchar](1) NOT NULL,
	[ap1Direccion] [varchar](40) NOT NULL,
	[ap1CodCiudad] [varchar](3) NOT NULL,
	[ap1CodDepartamento] [varchar](2) NOT NULL,
	[ap1CodActividadEconomica] [smallint] NOT NULL,
	[ap1Telefono] [bigint] NOT NULL,
	[ap1Fax] [bigint] NOT NULL,
	[ap1Email] [varchar](60) NOT NULL,
	[ap1IdRepresentante] [varchar](16) NOT NULL,
	[ap1DigVerRepresentante] [smallint] NULL,
	[ap1TipoIdRepresentante] [varchar](2) NOT NULL,
	[ap1PrimerApellidoRep] [varchar](20) NOT NULL,
	[ap1SegundoApellidoRep] [varchar](30) NOT NULL,
	[ap1PrimerNombreRep] [varchar](20) NOT NULL,
	[ap1SegundoNombreRep] [varchar](30) NULL,
	[ap1CodOperador] [smallint] NOT NULL,
	[ap1PeriodoAporte] [varchar](7) NOT NULL,
	[ap1TipoPagador] [smallint] NOT NULL,
 CONSTRAINT [PK_PilaArchivoAPRegistro1_ap1Id] PRIMARY KEY CLUSTERED 
(
	[ap1Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PilaArchivoARegistro1_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoARegistro1_aud](
	[pa1Id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pa1IndicePlanilla] [bigint] NOT NULL,
	[pa1NombreAportante] [varchar](200) NOT NULL,
	[pa1TipoIdAportante] [varchar](2) NOT NULL,
	[pa1IdAportante] [varchar](16) NOT NULL,
	[pa1DigVerAportante] [smallint] NULL,
	[pa1CodSucursal] [varchar](10) NULL,
	[pa1NomSucursal] [varchar](40) NULL,
	[pa1ClaseAportante] [varchar](1) NOT NULL,
	[pa1NaturalezaJuridica] [smallint] NOT NULL,
	[pa1TipoPersona] [varchar](1) NOT NULL,
	[pa1FormaPresentacion] [varchar](1) NOT NULL,
	[pa1Direccion] [varchar](40) NOT NULL,
	[pa1CodCiudad] [varchar](3) NOT NULL,
	[pa1CodDepartamento] [varchar](2) NOT NULL,
	[pa1CodActividadEconomica] [smallint] NOT NULL,
	[pa1Telefono] [bigint] NOT NULL,
	[pa1Fax] [bigint] NULL,
	[pa1Email] [varchar](60) NULL,
	[pa1IdRepresentante] [varchar](16) NOT NULL,
	[pa1DigVerRepresentante] [smallint] NULL,
	[pa1TipoIdRepresentante] [varchar](2) NOT NULL,
	[pa1PrimerApellidoRep] [varchar](20) NOT NULL,
	[pa1SegundoApellidoRep] [varchar](30) NULL,
	[pa1PrimerNombreRep] [varchar](20) NOT NULL,
	[pa1SegundoNombreRep] [varchar](30) NULL,
	[pa1FechaInicioConcordato] [date] NULL,
	[pa1TipoAccion] [smallint] NULL,
	[pa1FechaFinActividades] [date] NULL,
	[pa1CodOperador] [smallint] NULL,
	[pa1PeriodoAporte] [varchar](7) NOT NULL,
	[pa1TipoAportante] [smallint] NOT NULL,
	[pa1FechaMatricula] [date] NULL,
	[pa1CodDepartamentoBene] [varchar](2) NULL,
	[pa1AportanteExonerado] [varchar](1) NOT NULL,
	[pa1AcogeBeneficio] [varchar](1) NOT NULL,
 CONSTRAINT [PK_PilaArchivoARegistro1_pa1Id] PRIMARY KEY CLUSTERED 
(
	[pa1Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PilaArchivoFRegistro1_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoFRegistro1_aud](
	[pf1Id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pf1IndicePlanillaOF] [bigint] NOT NULL,
	[pf1FechaRecaudo] [int] NOT NULL,
	[pf1CodigoEntidadFinanciera] [smallint] NOT NULL,
	[pf1IdAdministradora] [varchar](15) NOT NULL,
	[pf1NombreAdministradora] [varchar](22) NOT NULL,
 CONSTRAINT [PK_PilaArchivoFRegistro1_pf1Id] PRIMARY KEY CLUSTERED 
(
	[pf1Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PilaArchivoFRegistro5_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoFRegistro5_aud](
	[pf5Id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pf5IndicePlanillaOF] [bigint] NOT NULL,
	[pf5NumeroCuenta] [varchar](17) NOT NULL,
	[pf5TipoCuenta] [smallint] NOT NULL,
	[pf5NumeroLote] [smallint] NOT NULL,
	[pf5SistemaPago] [varchar](2) NOT NULL,
 CONSTRAINT [PK_PilaArchivoFRegistro5_pf5Id] PRIMARY KEY CLUSTERED 
(
	[pf5Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PilaArchivoFRegistro6_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoFRegistro6_aud](
	[pf6Id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pf6IndicePlanillaOF] [bigint] NOT NULL,
	[pf6IdAportante] [varchar](16) NOT NULL,
	[pf6NombreAportante] [varchar](16) NOT NULL,
	[pf6CodBanco] [varchar](8) NOT NULL,
	[pf6NumeroPlanilla] [varchar](15) NOT NULL,
	[pf6PeriodoPago] [varchar](6) NOT NULL,
	[pf6CanalPago] [varchar](2) NOT NULL,
	[pf6CantidadRegistros] [int] NOT NULL,
	[pf6CodOperadorInformacion] [varchar](2) NOT NULL,
	[pf6ValorPlanilla] [bigint] NOT NULL,
	[pf6HoraMinuto] [varchar](4) NOT NULL,
	[pf6Secuencia] [int] NOT NULL,
	[pf6EstadoConciliacion] [varchar](64) NULL,
 CONSTRAINT [PK_PilaArchivoFRegistro6_pf6Id] PRIMARY KEY CLUSTERED 
(
	[pf6Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
-- Creación de tabla [PilaArchivoFRegistro8_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PilaArchivoFRegistro8_aud](
	[pf8Id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pf8IndicePlanillaOF] [bigint] NOT NULL,
	[pf8CantidadPlanillas] [int] NOT NULL,
	[pf8CantidadRegistros] [int] NOT NULL,
	[pf8ValorRecaudado] [bigint] NOT NULL,
 CONSTRAINT [PK_PilaArchivoFRegistro8_pf8Id] PRIMARY KEY CLUSTERED 
(
	[pf8Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
-- Creación de tabla [PilaArchivoFRegistro9]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PilaArchivoFRegistro9_aud](
	[pf9Id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pf9IndicePlanillaOF] [bigint] NOT NULL,
	[pf9CantidadTotalPlanillas] [int] NOT NULL,
	[pf9CantidadTotalRegistros] [int] NOT NULL,
	[pf9ValorTotalRecaudo] [bigint] NOT NULL,
	[pf9CantidadTotalLotes] [int] NOT NULL,
 CONSTRAINT [PK_PilaArchivoFRegistro9_pf9Id] PRIMARY KEY CLUSTERED 
(
	[pf9Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
-- Creación de tabla [PilaArchivoIPRegistro1_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoIPRegistro1_aud](
	[ip1Id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ip1IndicePlanilla] [bigint] NOT NULL,
	[ip1Secuencia] [int] NOT NULL,
	[ip1IdAdministradora] [varchar](16) NOT NULL,
	[ip1DigVerAdministradora] [smallint] NOT NULL,
	[ip1CodAdministradora] [varchar](6) NOT NULL,
	[ip1NombrePagador] [varchar](150) NOT NULL,
	[ip1TipoIdPagador] [varchar](2) NOT NULL,
	[ip1IdPagador] [varchar](16) NOT NULL,
	[ip1DigVerPagador] [smallint] NULL,
	[ip1PeriodoAporte] [varchar](7) NOT NULL,
	[ip1FechaPago] [date] NULL,
	[ip1NumPlanilla] [varchar](10) NOT NULL,
	[ip1FormaPresentacion] [varchar](1) NOT NULL,
	[ip1CodSucursal] [varchar](10) NULL,
	[ip1NomSucursal] [varchar](40) NULL,
	[ip1ValorTotalMesadas] [bigint] NOT NULL,
	[ip1CantPensionados] [int] NOT NULL,
	[ip1DiasMora] [smallint] NOT NULL,
	[ip1CodOperador] [smallint] NOT NULL,
	[ip1CantidadReg2] [int] NOT NULL,
 CONSTRAINT [PK_PilaArchivoIPRegistro1_ip1Id] PRIMARY KEY CLUSTERED 
(
	[ip1Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
-- Creación de tabla [PilaArchivoIPRegistro2_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoIPRegistro2_aud](
	[ip2Id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ip2IndicePlanilla] [bigint] NOT NULL,
	[ip2Secuencia] [int] NOT NULL,
	[ip2TipoIdPensionado] [varchar](2) NOT NULL,
	[ip2IdPensionado] [varchar](16) NOT NULL,
	[ip2PrimerApellido] [varchar](20) NOT NULL,
	[ip2SegundoApellido] [varchar](30) NULL,
	[ip2PrimerNombre] [varchar](20) NOT NULL,
	[ip2SegundoNombre] [varchar](30) NULL,
	[ip2CodDepartamento] [varchar](2) NOT NULL,
	[ip2CodMunicipio] [varchar](3) NOT NULL,
	[ip2Tarifa] [numeric](5, 5) NOT NULL,
	[ip2ValorAporte] [int] NOT NULL,
	[ip2ValorMesada] [int] NOT NULL,
	[ip2DiasCotizados] [smallint] NOT NULL,
	[ip2NovING] [varchar](1) NULL,
	[ip2NovRET] [varchar](1) NULL,
	[ip2NovVSP] [varchar](1) NULL,
	[ip2NovSUS] [varchar](1) NULL,
	[ip2FechaIngreso] [date] NULL,
	[ip2FechaRetiro] [date] NULL,
	[ip2FechaInicioVSP] [date] NULL,
	[ip2MarcaValRegistroAporte] [varchar](50) NULL,
	[ip2EstadoRegistroAporte] [varchar](60) NULL,
	[ip2FechaProcesamientoValidRegAporte] [datetime] NULL,
	[ip2EstadoValidacionV1] [varchar](30) NULL,
 CONSTRAINT [PK_PilaArchivoIPRegistro2_ip2Id] PRIMARY KEY CLUSTERED 
(
	[ip2Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [PilaArchivoIPRegistro3_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PilaArchivoIPRegistro3_aud](
	[ip3Id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ip3IndicePlanilla] [bigint] NOT NULL,
	[ip3ValorTotalAporte] [bigint] NOT NULL,
	[ip3DiasMora] [smallint] NOT NULL,
	[ip3ValorMora] [bigint] NOT NULL,
	[ip3ValorTotalPagar] [bigint] NOT NULL,
 CONSTRAINT [PK_PilaArchivoIPRegistro3_ip3Id] PRIMARY KEY CLUSTERED 
(
	[ip3Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


-- Creación de tabla [PilaArchivoIRegistro1_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoIRegistro1_aud](
	[pi1Id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pi1IndicePlanilla] [bigint] NOT NULL,
	[pi1IdCCF] [varchar](16) NOT NULL,
	[pi1DigVerCCF] [smallint] NOT NULL,
	[pi1RazonSocial] [varchar](200) NOT NULL,
	[pi1TipoDocAportante] [varchar](2) NOT NULL,
	[pi1IdAportante] [varchar](16) NOT NULL,
	[pi1DigVerAportante] [smallint] NULL,
	[pi1TipoAportante] [varchar](2) NOT NULL,
	[pi1Direccion] [varchar](40) NOT NULL,
	[pi1CodCiudad] [varchar](3) NOT NULL,
	[pi1CodDepartamento] [varchar](2) NOT NULL,
	[pi1Telefono] [bigint] NOT NULL,
	[pi1Fax] [bigint] NOT NULL,
	[pi1Email] [varchar](60) NOT NULL,
	[pi1PeriodoAporte] [varchar](7) NOT NULL,
	[pi1TipoPlanilla] [varchar](1) NOT NULL,
	[pi1FechaPagoAsociado] [date] NULL,
	[pi1FechaPago] [date] NOT NULL,
	[pi1NumPlanillaAsociada] [varchar](10) NULL,
	[pi1NumPlanilla] [varchar](10) NOT NULL,
	[pi1Presentacion] [varchar](1) NOT NULL,
	[pi1CodSucursal] [varchar](10) NULL,
	[pi1NomSucursal] [varchar](40) NULL,
	[pi1CantidadEmpleados] [int] NOT NULL,
	[pi1CantidadAfiliados] [int] NOT NULL,
	[pi1CodOperador] [smallint] NOT NULL,
	[pi1ModalidadPlanilla] [smallint] NOT NULL,
	[pi1DiasMora] [smallint] NOT NULL,
	[pi1CantidadReg2] [int] NOT NULL,
	[pi1FechaMatricula] [date] NULL,
	[pi1CodDepartamentoBeneficio] [varchar](2) NULL,
	[pi1AcogeBeneficio] [varchar](1) NOT NULL,
	[pi1ClaseAportante] [varchar](1) NOT NULL,
	[pi1NaturalezaJuridica] [smallint] NOT NULL,
	[pi1TipoPersona] [varchar](1) NOT NULL,
 CONSTRAINT [PK_PilaArchivoIRegistro1_pi1Id] PRIMARY KEY CLUSTERED 
(
	[pi1Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [PilaArchivoIRegistro2_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoIRegistro2_aud](
	[pi2Id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pi2IndicePlanilla] [bigint] NOT NULL,
	[pi2Secuencia] [int] NOT NULL,
	[pi2TipoIdCotizante] [varchar](2) NOT NULL,
	[pi2IdCotizante] [varchar](16) NOT NULL,
	[pi2TipoCotizante] [smallint] NOT NULL,
	[pi2SubTipoCotizante] [smallint] NOT NULL,
	[pi2ExtrangeroNoObligado] [varchar](1) NULL,
	[pi2ColombianoExterior] [varchar](1) NULL,
	[pi2CodDepartamento] [varchar](2) NULL,
	[pi2CodMunicipio] [varchar](3) NULL,
	[pi2PrimerApellido] [varchar](20) NOT NULL,
	[pi2SegundoApellido] [varchar](30) NULL,
	[pi2PrimerNombre] [varchar](20) NOT NULL,
	[pi2SegundoNombre] [varchar](30) NULL,
	[pi2NovIngreso] [varchar](1) NULL,
	[pi2NovRetiro] [varchar](1) NULL,
	[pi2NovVSP] [varchar](1) NULL,
	[pi2NovVST] [varchar](1) NULL,
	[pi2NovSLN] [varchar](1) NULL,
	[pi2NovIGE] [varchar](1) NULL,
	[pi2NovLMA] [varchar](1) NULL,
	[pi2NovVACLR] [varchar](1) NULL,
	[pi2DiasIRL] [varchar](2) NULL,
	[pi2DiasCotizados] [smallint] NOT NULL,
	[pi2SalarioBasico] [int] NOT NULL,
	[pi2ValorIBC] [int] NOT NULL,
	[pi2Tarifa] [numeric](5, 5) NOT NULL,
	[pi2AporteObligatorio] [int] NOT NULL,
	[pi2Correcciones] [varchar](1) NULL,
	[pi2SalarioIntegral] [varchar](1) NULL,
	[pi2FechaIngreso] [date] NULL,
	[pi2FechaRetiro] [date] NULL,
	[pi2FechaInicioVSP] [date] NULL,
	[pi2FechaInicioSLN] [date] NULL,
	[pi2FechaFinSLN] [date] NULL,
	[pi2FechaInicioIGE] [date] NULL,
	[pi2FechaFinIGE] [date] NULL,
	[pi2FechaInicioLMA] [date] NULL,
	[pi2FechaFinLMA] [date] NULL,
	[pi2FechaInicioVACLR] [date] NULL,
	[pi2FechaFinVACLR] [date] NULL,
	[pi2FechaInicioVCT] [date] NULL,
	[pi2FechaFinVCT] [date] NULL,
	[pi2FechaInicioIRL] [date] NULL,
	[pi2FechaFinIRL] [date] NULL,
	[pi2HorasLaboradas] [smallint] NULL,
	[pi2MarcaValRegistroAporte] [varchar](50) NULL,
	[pi2EstadoRegistroAporte] [varchar](60) NULL,
	[pi2FechaProcesamientoValidRegAporte] [datetime] NULL,
	[pi2EstadoValidacionV0] [varchar](30) NULL,
	[pi2EstadoValidacionV1] [varchar](30) NULL,
	[pi2EstadoValidacionV2] [varchar](30) NULL,
	[pi2EstadoValidacionV3] [varchar](30) NULL,
 CONSTRAINT [PK_PilaArchivoIRegistro2_pi2Id] PRIMARY KEY CLUSTERED 
(
	[pi2Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
-- Creación de tabla [PilaArchivoIRegistro3]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PilaArchivoIRegistro3_aud](
	[pi3Id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pi3IndicePlanilla] [bigint] NOT NULL,
	[pi3ValorTotalIBC] [bigint] NULL,
	[pi3ValorTotalAporteObligatorio] [bigint] NULL,
	[pi3DiasMora] [smallint] NULL,
	[pi3ValorMora] [bigint] NULL,
	[pi3ValorTotalAportes] [bigint] NULL,
 CONSTRAINT [PK_PilaArchivoIRegistro3_pi3Id] PRIMARY KEY CLUSTERED 
(
	[pi3Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

-- Creación de tabla [PilaClasificacionAportante_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaClasificacionAportante_aud](
	[pcaId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pcaTipoArchivo] [varchar](20) NULL,
	[pcaCampo] [smallint] NULL,
	[pcaValor] [varchar](30) NULL,
	[pcaComparacion] [varchar](20) NULL,
 CONSTRAINT [PK_PilaClasificacionAportante_pcaId] PRIMARY KEY CLUSTERED 
(
	[pcaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PilaEjecucionProgramada_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaEjecucionProgramada_aud](
	[pepId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pepFechaDefinicion] [datetime] NULL,
	[pepUsuario] [varchar](255) NULL,
	[pepFrecuencia] [varchar](50) NULL,
	[pepHoraInicio] [varchar](5) NULL,
	[pepFechaInicioVigencia] [datetime] NULL,
	[pepFechaFinVigencia] [datetime] NULL,
	[pepCajaCompensacion] [int] NULL,
 CONSTRAINT [PK_PilaEjecucionProgramada_pepId] PRIMARY KEY CLUSTERED 
(
	[pepId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PilaErrorValidacionLog_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaErrorValidacionLog_aud](
	[pevId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pevIdIndicePlanilla] [bigint] NULL,
	[pevTipoArchivo] [varchar](20) NULL,
	[pevTipoError] [varchar](20) NULL,
	[pevNumeroLinea] [smallint] NULL,
	[pevBloqueValidacion] [varchar](11) NULL,
	[pevNombreCampo] [varchar](150) NULL,
	[pevPosicionInicial] [smallint] NULL,
	[pevPosicionFinal] [smallint] NULL,
	[pevValorCampo] [varchar](200) NULL,
	[pevCodigoError] [varchar](10) NULL,
	[pevMensajeError] [varchar](255) NULL,
 CONSTRAINT [PK_PilaErrorValidacionLog_pevId] PRIMARY KEY CLUSTERED 
(
	[pevId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [PilaEstadoBloque_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaEstadoBloque_aud](
	[pebId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pebIndicePlanilla] [bigint] NOT NULL,
	[pebTipoArchivo] [varchar](20) NOT NULL,
	[pebEstadoBloque0] [varchar](75) NULL,
	[pebAccionBloque0] [varchar](75) NULL,
	[pebEstadoBloque1] [varchar](75) NULL,
	[pebAccionBloque1] [varchar](75) NULL,
	[pebEstadoBloque2] [varchar](75) NULL,
	[pebAccionBloque2] [varchar](75) NULL,
	[pebIdLogLecturaBloque2] [bigint] NULL,
	[pebEstadoBloque3] [varchar](75) NULL,
	[pebAccionBloque3] [varchar](75) NULL,
	[pebEstadoBloque4] [varchar](75) NULL,
	[pebAccionBloque4] [varchar](75) NULL,
	[pebIdLogLecturaBloque4] [bigint] NULL,
	[pebEstadoBloque5] [varchar](75) NULL,
	[pebAccionBloque5] [varchar](75) NULL,
	[pebEstadoBloque6] [varchar](75) NULL,
	[pebAccionBloque6] [varchar](75) NULL,
 CONSTRAINT [PK_PilaEstadoBloque_pebId] PRIMARY KEY CLUSTERED 
(
	[pebId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [PilaEstadoBloqueOF_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaEstadoBloqueOF_aud](
	[peoId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[peoIndicePlanillaOF] [bigint] NOT NULL,
	[peoEstadoBloque0] [varchar](75) NULL,
	[peoAccionBloque0] [varchar](75) NULL,
	[peoEstadoBloque1] [varchar](75) NULL,
	[peoAccionBloque1] [varchar](75) NULL,
	[peoIdLogLecturaBloque1] [bigint] NULL,
	[peoEstadoBloque6] [varchar](75) NULL,
	[peoAccionBloque6] [varchar](75) NULL,
 CONSTRAINT [PK_PilaEstadoBloqueOF_peoId] PRIMARY KEY CLUSTERED 
(
	[peoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PilaIndiceCorreccionPlanilla_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaIndiceCorreccionPlanilla_aud](
	[picId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[picFechaAccion] [datetime] NULL,
	[picAccion] [varchar](75) NULL,
	[picEstadoArchivoAfectado] [varchar](75) NULL,
	[picIndicePlanillaAfectada] [bigint] NOT NULL,
	[picIndicePlanillaCargado] [bigint] NOT NULL,
 CONSTRAINT [PK_PilaIndiceCorreccionPlanilla_picId] PRIMARY KEY CLUSTERED 
(
	[picId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PilaIndicePlanilla_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaIndicePlanilla_aud](
	[pipId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pipIdPlanilla] [bigint] NOT NULL,
	[pipTipoArchivo] [varchar](20) NOT NULL,
	[pipNombreArchivo] [varchar](80) NULL,
	[pipFechaRecibo] [datetime] NULL,
	[pipFechaFtp] [datetime] NULL,
	[pipCodigoOperadorInformacion] [varchar](2) NULL,
	[pipEstadoArchivo] [varchar](75) NULL,
	[pipTipoCargaArchivo] [varchar](30) NULL,
	[pipUsuario] [varchar](255) NULL,
	[pipIdentificadorDocumento] [varchar](255) NULL,
	[pipVersionDocumento] [varchar](10) NULL,
	[pipFechaProceso] [datetime] NULL,
	[pipUsuarioProceso] [varchar](255) NULL,
	[pipFechaEliminacion] [datetime] NULL,
	[pipUsuarioEliminacion] [varchar](255) NULL,
	[pipProcesar] [bit] NULL,
	[pipRegistroActivo] [bit] NULL,
	[pipEnLista] [bit] NULL,
 CONSTRAINT [PK_PilaIndicePlanilla_pipId] PRIMARY KEY CLUSTERED 
(
	[pipId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PilaIndicePlanillaOF_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaIndicePlanillaOF_aud](
	[pioId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pioFechaPago] [datetime] NULL,
	[pioNombreArchivo] [varchar](80) NULL,
	[pioFechaRecibo] [datetime] NULL,
	[pioFechaFtp] [datetime] NULL,
	[pioCodigoAdministradora] [varchar](6) NULL,
	[pioCodigoBancoRecaudador] [varchar](3) NULL,
	[pioTipoArchivo] [varchar](20) NULL,
	[pioTipoCargaArchivo] [varchar](10) NULL,
	[pioUsuario] [varchar](255) NULL,
	[pioEstadoArchivo] [varchar](75) NULL,
	[pioIdentificadorDocumento] [varchar](255) NULL,
	[pioVersionDocumento] [varchar](10) NULL,
	[pioFechaProceso] [datetime] NULL,
	[pioUsuarioProceso] [varchar](255) NULL,
	[pioFechaEliminacion] [datetime] NULL,
	[pioUsuarioEliminacion] [varchar](255) NULL,
	[pioRegistroActivo] [bit] NULL,
 CONSTRAINT [PK_PilaIndicePlanillaOF_pioId] PRIMARY KEY CLUSTERED 
(
	[pioId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [PilaNormatividadFechaVencimiento_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaNormatividadFechaVencimiento_aud](
	[pfvId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pfvPeriodoInicial] [varchar](7) NULL,
	[pfvPeriodoFinal] [varchar](7) NULL,
	[pfvUltimoDigitoId] [varchar](50) NULL,
	[pfvClasificacionAportante] [bigint] NULL,
	[pfvDiaVencimiento] [smallint] NULL,
	[pfvTipoFecha] [varchar](20) NULL,
 CONSTRAINT [PK_PilaNormatividadFechaVencimiento_pfvId] PRIMARY KEY CLUSTERED 
(
	[pfvId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PilaOportunidadPresentacionPlanilla_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaOportunidadPresentacionPlanilla_aud](
	[popId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[popTipoPlanilla] [varchar](1) NULL,
	[popOportunidad] [varchar](20) NULL,
	[popTipoCotizanteEspecifico] [varchar](2) NULL,
 CONSTRAINT [PK_PilaOportunidadPresentacionPlanilla_popId] PRIMARY KEY CLUSTERED 
(
	[popId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [PilaPasoValores_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaPasoValores_aud](
	[ppvId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ppvIdPlanilla] [bigint] NOT NULL,
	[ppvTipoPlanilla] [varchar](75) NOT NULL,
	[ppvBloque] [varchar](11) NOT NULL,
	[ppvNombreVariable] [varchar](75) NOT NULL,
	[ppvValorVariable] [varchar](200) NULL,
	[ppvCodigoCampo] [varchar](10) NULL,
 CONSTRAINT [PK_PilaPasoValores_ppvId] PRIMARY KEY CLUSTERED 
(
	[ppvId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PilaProceso_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaProceso_aud](
	[pprId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pprNumeroRadicado] [varchar](12) NOT NULL,
	[pprTipoProceso] [varchar](30) NOT NULL,
	[pprFechaInicioProceso] [datetime] NOT NULL,
	[pprFechaFinProceso] [datetime] NULL,
	[pprUsuarioProceso] [varchar](255) NOT NULL,
	[pprEstadoProceso] [varchar](75) NOT NULL,
 CONSTRAINT [PK_PilaProcesoValidacion_pprId] PRIMARY KEY CLUSTERED 
(
	[pprId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [PilaTasasInteresMora_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaTasasInteresMora_aud](
	[ptiId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ptiFechaInicioTasa] [date] NULL,
	[ptiFechaFinTasa] [date] NULL,
	[ptiNumeroPeriodoTasa] [smallint] NULL,
	[ptiPorcentajeTasa] [numeric](4, 4) NULL,
	[ptiNormativa] [varchar](100) NULL,
	[ptiTipoInteres] [varchar](20) NULL,
 CONSTRAINT [PK_PilaTasasInteresMora_ptiId] PRIMARY KEY CLUSTERED 
(
	[ptiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [PlantillaComunicado_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PlantillaComunicado_aud](
	[pcoId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pcoAsunto] [varchar](150) NULL,
	[pcoCuerpo] [varchar](5000) NULL,
	[pcoEncabezado] [varchar](500) NULL,
	[pcoIdentificadorImagenPie] [varchar](255) NULL,
	[pcoMensaje] [varchar](5000) NULL,
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


-- Creación de tabla [Pregunta_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Pregunta_aud](
	[preId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[preEstado] [varchar](255) NULL,
	[prePregunta] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[preId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [ProcessorCatalog_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ProcessorCatalog_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[className] [varchar](100) NULL,
	[description] [varchar](255) NULL,
	[name] [varchar](70) NULL,
	[scope] [varchar](255) NULL,
	[tenantId] [bigint] NULL,
 CONSTRAINT [PK_ProcessorCatalog_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [ProcessorDefinition_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ProcessorDefinition_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[fieldDefinitionId] [bigint] NULL,
	[fieldDefinitionLoadId] [bigint] NULL,
	[lineDefinitionId] [bigint] NULL,
	[lineDefinitionLoadId] [bigint] NULL,
	[processorOrder] [bigint] NULL,
	[processorCatalog_id] [bigint] NULL,
 CONSTRAINT [PK_ProcessorDefinition_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


-- Creación de tabla [ProcessorParameter_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ProcessorParameter_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dataType] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[mask] [varchar](100) NULL,
	[name] [varchar](70) NULL,
	[processorCatalog_id] [bigint] NULL,
 CONSTRAINT [PK_ProcessorParameter_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [ProcessorParamValue_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ProcessorParamValue_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[value] [varchar](100) NULL,
	[processorDefinition_id] [bigint] NULL,
	[processorParameter_id] [bigint] NULL,
 CONSTRAINT [PK_ProcessorParamValue_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [ProductoNoConforme_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ProductoNoConforme_aud](
	[pncId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[pncValorCampoBack] [varchar](150) NULL,
	[pncValorCampoFront] [varchar](150) NULL,
	[pncSolicitud] [bigint] NULL,
	[pncBeneficiario] [bigint] NULL,
	[pncNombreInconsistencia] [varchar](50) NULL,
	[pncDescripcionInconsistencia] [varchar](150) NULL,
 CONSTRAINT [PK_ProductoNoConforme_pncId] PRIMARY KEY CLUSTERED 
(
	[pncId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [ReferenciaToken_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ReferenciaToken_aud](
	[retId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[retDigitoVerificacion] [smallint] NULL,
	[retFechaExpiracion] [date] NULL,
	[retNumeroIdentificacion] [varchar](255) NULL,
	[retTipoIdentificacion] [varchar](255) NULL,
	[retToken] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[retId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [RegistroPersonaInconsistente_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [RegistroPersonaInconsistente_aud](
	[rpiId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[rpiCargueMultipleSupervivencia] [bigint] NOT NULL,
	[rpiPersona] [bigint] NOT NULL,
	[rpiCanalContacto] [varchar](15) NULL,
	[rpiFechaIngreso] [date] NULL,
	[rpiEstadoGestion] [varchar](20) NULL,
	[rpiObservaciones] [varchar](255) NULL,
	[rpiTipoInconsistencia] [varchar](42) NULL,
 CONSTRAINT [PK_RegistroPersonaInconsistente_rpiId] PRIMARY KEY CLUSTERED 
(
	[rpiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [RegistroPilaNovedad_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [RegistroPilaNovedad_aud](
	[rpnId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[rpnRegistroAporteDetallado] [bigint] NULL,
	[rpnTipoNovedadPila] [varchar](30) NULL,
	[rpnFechaRegistroNovedad] [datetime] NULL,
	[rpnTipoTransaccionNovedad] [varchar](90) NULL,
	[rpnFechaInicialNovedad] [datetime] NULL,
	[rpnFechaFinalNovedad] [datetime] NULL,
	[rpnMarcaNovedad] [varchar](40) NULL,
	[rpnFechaProcesamientoNovedad] [datetime] NULL,
	[rpnUsuarioProcesamientoNovedad] [varchar](100) NULL,
	[rpnCanalRecepcion] [varchar](30) NULL,
	[rpnEstadoNovedad] [varchar](40) NULL,
	[rpnGlosaNovedad] [bigint] NULL,
 CONSTRAINT [PK_RegistroPilaNovedad_rpnId] PRIMARY KEY CLUSTERED 
(
	[rpnId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [RelacionGrupoFamiliar_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [RelacionGrupoFamiliar_aud](
	[rgfId] [smallint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[rgfNombre] [varchar](15) NULL,
 CONSTRAINT [PK_RelacionGrupoFamiliar_rgfId] PRIMARY KEY CLUSTERED 
(
	[rgfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [Requisito_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Requisito_aud](
	[reqId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[reqDescripcion] [varchar](170) NULL,
	[reqEstado] [varchar](20) NULL,
	[reqTipoRequisito] [varchar](30) NULL,
 CONSTRAINT [PK_Requisito_reqId] PRIMARY KEY CLUSTERED 
(
	[reqId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [RequisitoCajaClasificacion_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [RequisitoCajaClasificacion_aud](
	[rtsId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[rtsEstado] [varchar](20) NULL,
	[rtsRequisito] [bigint] NULL,
	[rtsClasificacion] [varchar](100) NULL,
	[rtsTipoTransaccion] [varchar](100) NULL,
	[rtsCajaCompensacion] [int] NULL,
	[rtsTextoAyuda] [varchar](1500) NULL,
 CONSTRAINT [PK_RequisitoTipoSolicitante_rtsId] PRIMARY KEY CLUSTERED 
(
	[rtsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [ResultadoEjecucionProgramada_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ResultadoEjecucionProgramada_aud](
	[repId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[repProceso] [varchar](100) NOT NULL,
	[repFechaEjecucion] [datetime] NULL,
	[repResultadoEjecucion] [varchar](50) NULL,
 CONSTRAINT [PK_ResultadoEjecucionProgramada_repId] PRIMARY KEY CLUSTERED 
(
	[repId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [RolAfiliado_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [RolAfiliado_aud](
	[roaId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[roaCargo] [varchar](200) NULL,
	[roaClaseIndependiente] [varchar](50) NULL,
	[roaClaseTrabajador] [varchar](50) NULL,
	[roaEstadoAfiliado] [varchar](20) NOT NULL,
	[roaEstadoEnEntidadPagadora] [varchar](20) NULL,
	[roaFechaIngreso] [date] NULL,
	[roaFechaRetiro] [date] NULL,
	[roaHorasLaboradasMes] [smallint] NULL,
	[roaIdentificadorAnteEntidadPagadora] [varchar](15) NULL,
	[roaPorcentajePagoAportes] [numeric](19, 2) NULL,
	[roaTipoAfiliado] [varchar](30) NOT NULL,
	[roaTipoContrato] [varchar](20) NULL,
	[roaTipoSalario] [varchar](10) NULL,
	[roaValorSalarioMesadaIngresos] [numeric](19, 2) NULL,
	[roaAfiliado] [bigint] NOT NULL,
	[roaEmpleador] [bigint] NULL,
	[roaPagadorAportes] [bigint] NULL,
	[roaPagadorPension] [smallint] NULL,
	[roaSucursalEmpleador] [bigint] NULL,
	[roaFechaAfiliacion] [date] NULL,
	[roaMotivoDesafiliacion] [varchar](50) NULL,
	[roaSustitucionPatronal] [bit] NULL,
	[roaFechaFinPagadorAportes] [date] NULL,
	[roaFechaFinPagadorPension] [date] NULL,
	[roaEstadoEnEntidadPagadoraPension] [varchar](20) NULL
 CONSTRAINT [PK_RolAfiliado_roaId] PRIMARY KEY CLUSTERED 
(
	[roaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [RolContactoEmpleador_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [RolContactoEmpleador_aud](
	[rceId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[rceTipoRolContactoEmpleador] [varchar](50) NULL,
	[rceEmpleador] [bigint] NULL,
	[rcePersona] [bigint] NULL,
	[rceCargo] [varchar](100) NULL,
	[rcetoken] [varchar](50) NULL,
	[rceCorreoEnviado] [bit] NULL,
	[rceUbicacion] [bigint] NOT NULL,
 CONSTRAINT [PK_RolContactoEmpleador_rceId] PRIMARY KEY CLUSTERED 
(
	[rceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [SedeCajaCompensacion_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SedeCajaCompensacion_aud](
	[sccfId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sccfNombre] [varchar](100) NULL,
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


-- Creación de tabla [SocioEmpleador_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SocioEmpleador_aud](
	[semId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
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


-- Creación de tabla [Solicitud_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Solicitud_aud](
	[solId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[solCanalRecepcion] [varchar](10) NULL,
	[solFechaRadicacion] [datetime2](7) NULL,
	[solInstanciaProceso] [varchar](255) NULL,
	[solNumeroRadicacion] [varchar](12) NULL,
	[solUsuarioRadicacion] [varchar](255) NULL,
	[solCajaCorrespondencia] [bigint] NULL,
	[solTipoTransaccion] [varchar](100) NULL,
	[solCiudadUsuarioRadicacion] [varchar](255) NULL,
	[solEstadoDocumentacion] [varchar](50) NULL,
	[solMetodoEnvio] [varchar](20) NULL,
	[solClasificacion] [varchar](100) NULL,
	[solTipoRadicacion] [varchar](20) NULL,
	[solFechaCreacion] [datetime2](7) NULL,
	[solDestinatario] [varchar](255) NULL,
	[solSedeDestinatario] [varchar](2) NULL,
	[solObservacion] [varchar](500) NULL,
	[solCargaAfiliacionMultipleEmpleador] [bigint] NULL,
	[solResultadoProceso] [varchar](10) NULL,
 CONSTRAINT [PK_Solicitud_solId] PRIMARY KEY CLUSTERED 
(
	[solId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [SolicitudAfiliaciEmpleador_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SolicitudAfiliaciEmpleador_aud](
	[saeId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[saeCodigoEtiquetaPreimpresa] [varchar](12) NULL,
	[saeEstadoSolicitud] [varchar](50) NULL,
	[saeFechaAprobacionConsejo] [datetime2](7) NULL,
	[saeNumeroActoAdministrativo] [varchar](50) NULL,
	[saeNumeroCustodiaFisica] [varchar](12) NULL,
	[saeEmpleador] [bigint] NULL,
	[saeSolicitudGlobal] [bigint] NULL,
 CONSTRAINT [PK_SolicitudAfiliaciEmpleador_saeId] PRIMARY KEY CLUSTERED 
(
	[saeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [SolicitudAfiliacionPersona_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SolicitudAfiliacionPersona_aud](
	[sapId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sapEstadoSolicitud] [varchar](50) NULL,
	[sapRolAfiliado] [bigint] NULL,
	[sapSolicitudGlobal] [bigint] NULL,
 CONSTRAINT [PK_SolicitudAfiliacionPersona_sapId] PRIMARY KEY CLUSTERED 
(
	[sapId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [SolicitudAsociacionPersonaEntidadPagadora_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SolicitudAsociacionPersonaEntidadPagadora_aud](
	[soaId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[soaConsecutivo] [varchar](11) NULL,
	[soaEstado] [varchar](50) NULL,
	[soaFechaGestion] [datetime2](7) NULL,
	[soaIdentificadorArchivoCarta] [varchar](255) NULL,
	[soaIdentificadorArchivoPlano] [varchar](255) NULL,
	[soaTipoGestion] [varchar](50) NULL,
	[soaRolAfiliado] [bigint] NOT NULL,
	[soaSolicitudGlobal] [bigint] NOT NULL,
	[soaUsuarioGestion] [varchar](255) NULL,
	[soaIdentificadorCartaResultadoGestion] [varchar](255) NULL,
 CONSTRAINT [PK_SolicitudAsociacionPersonaEntidadPagadora_soaId] PRIMARY KEY CLUSTERED 
(
	[soaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [SolicitudNovedad_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SolicitudNovedad_aud](
	[snoId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[snoEstadoSolicitud] [varchar](50) NULL,
	[snoNovedad] [bigint] NULL,
	[snoSolicitudGlobal] [bigint] NULL,
	[snoObservaciones] [varchar](200) NULL,
	[snoCargaMultiple] [bit] NULL,
 CONSTRAINT [PK_SolicitudNovedad_snoId] PRIMARY KEY CLUSTERED 
(
	[snoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [SolicitudNovedadEmpleador_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedadEmpleador_aud](
	[sneId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sneIdEmpleador] [bigint] NULL,
	[sneIdSolicitudNovedad] [bigint] NULL,
 CONSTRAINT [PK_SolicitudNovedadEmpleador_sneId] PRIMARY KEY CLUSTERED 
(
	[sneId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


-- Creación de tabla [SolicitudNovedadPersona_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedadPersona_aud](
	[snpId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[snpPersona] [bigint] NOT NULL,
	[snpSolicitudNovedad] [bigint] NOT NULL,
 CONSTRAINT [PK_SolicitudNovedadPersona_snpId] PRIMARY KEY CLUSTERED 
(
	[snpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


-- Creación de tabla [SucursalEmpresa_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SucursalEmpresa_aud](
	[sueId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sueCodigo] [varchar](3) NULL,
	[sueNombre] [varchar](100) NULL,
	[sueCodigoCIIU] [smallint] NULL,
	[sueEmpresa] [bigint] NULL,
	[sueMedioPagoSubsidioMonetario] [smallint] NULL,
	[sueUbicacion] [bigint] NULL,
	[sueEstadoSucursal] [varchar](25) NULL,
	[sueCoindicirCodigoPila] [bit] NULL,
 CONSTRAINT [PK_SucursalEmpresa_sueId] PRIMARY KEY CLUSTERED 
(
	[sueId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [SucursaRolContactEmpleador_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SucursaRolContactEmpleador_aud](
	[srcId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[srcRolContactoEmpleador] [bigint] NULL,
	[srcSucursalEmpleador] [bigint] NULL,
 CONSTRAINT [PK_SucursaRolContactEmpleador_srcId] PRIMARY KEY CLUSTERED 
(
	[srcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


-- Creación de tabla [Tarjeta_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [Tarjeta_aud](
	[tarId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[tarEstadoTarjeta] [varchar](20) NULL,
	[tarNumeroTarjeta] [varchar](20) NULL,
	[afiPersona] [bigint] NULL,
 CONSTRAINT [PK_Tarjeta_tarId] PRIMARY KEY CLUSTERED 
(
	[tarId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [TipoVia_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [TipoVia_aud](
	[tviId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[tviNombreVia] [varchar](20) NULL,
 CONSTRAINT [PK_TipoVia_tviId] PRIMARY KEY CLUSTERED 
(
	[tviId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [Ubicacion_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Ubicacion_aud](
	[ubiId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ubiAutorizacionEnvioEmail] [bit] NULL,
	[ubiCodigoPostal] [varchar](10) NULL,
	[ubiDireccionFisica] [varchar](150) NULL,
	[ubiEmail] [varchar](255) NULL,
	[ubiIndicativoTelFijo] [varchar](2) NULL,
	[ubiTelefonoCelular] [varchar](10) NULL,
	[ubiTelefonoFijo] [varchar](7) NULL,
	[ubiMunicipio] [smallint] NULL,
	[ubiDescripcionIndicacion] [varchar](100) NULL,
 CONSTRAINT [PK_Ubicacion_ubiId] PRIMARY KEY CLUSTERED 
(
	[ubiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [UbicacionEmpresa_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [UbicacionEmpresa_aud](
	[ubeId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ubeEmpresa] [bigint] NULL,
	[ubeUbicacion] [bigint] NULL,
	[ubeTipoUbicacion] [varchar](30) NULL,
 CONSTRAINT [PK_UbicacionEmpresa_ubeId] PRIMARY KEY CLUSTERED 
(
	[ubeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [ValidacionProceso_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ValidacionProceso_aud](
	[vapId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[vapBloque] [varchar](150) NULL,
	[vapValidacion] [varchar](100) NULL,
	[vapProceso] [varchar](100) NULL,
	[vapEstadoProceso] [varchar](20) NULL,
	[vapOrden] [int] NULL,
	[vapObjetoValidacion] [varchar](60) NULL,
	[vapInversa] [bit] NOT NULL,
 CONSTRAINT [PK_ValidacionProceso_vapId] PRIMARY KEY CLUSTERED 
(
	[vapId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [ValidatorCatalog_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ValidatorCatalog_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[className] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[name] [varchar](255) NULL,
	[scope] [varchar](255) NULL,
	[tenantId] [bigint] NULL,
 CONSTRAINT [PK_ValidatorCatalog_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [ValidatorDefinition_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ValidatorDefinition_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[excludeLine] [bit] NULL,
	[stopProcess] [bit] NULL,
	[validatorOrder] [bigint] NULL,
	[fieldDefinition_id] [bigint] NULL,
	[fileDefinitionLoad_id] [bigint] NULL,
	[lineDefinition_id] [bigint] NULL,
	[validatorCatalog_id] [bigint] NULL,
	[validatorprofile] [bigint] NULL,
	[state] [bit] NULL,
 CONSTRAINT [PK_ValidatorDefinition_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


-- Creación de tabla [ValidatorParameter_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ValidatorParameter_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dataType] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[mask] [varchar](255) NULL,
	[name] [varchar](255) NULL,
	[validatorCatalog_id] [bigint] NULL,
 CONSTRAINT [PK_ValidatorParameter_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [ValidatorParamValue_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ValidatorParamValue_aud](
	[id] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[value] [varchar](255) NULL,
	[validatorDefinition_id] [bigint] NULL,
	[validatorParameter_id] [bigint] NULL,
 CONSTRAINT [PK_ValidatorParamValue_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO


-- Creación de tabla [VariableComunicado_aud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [VariableComunicado_aud](
	[vcoId] [bigint] NOT NULL,
	[REV] [int] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[vcoClave] [varchar](55) NULL,
	[vcoDescripcion] [varchar](200) NULL,
	[vcoNombre] [varchar](50) NULL,
	[vcoPlantillaComunicado] [bigint] NOT NULL,
	[vcoNombreConstante] [varchar](100) NULL,
	[vcoTipoVariableComunicado] [varchar](20) NULL,
 CONSTRAINT [PK_VariableComunicado_vcoId] PRIMARY KEY CLUSTERED 
(
	[vcoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

ALTER TABLE [Afiliado_aud]  WITH CHECK ADD  CONSTRAINT [FK_Afiliado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Afiliado_aud] CHECK CONSTRAINT [FK_Afiliado_aud_REV]
GO

ALTER TABLE [AFP_aud]  WITH CHECK ADD  CONSTRAINT [FK_AFP_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AFP_aud] CHECK CONSTRAINT [FK_AFP_aud_REV]
GO

ALTER TABLE [Aportante_aud]  WITH CHECK ADD  CONSTRAINT [FK_Aportante_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Aportante_aud] CHECK CONSTRAINT [FK_Aportante_aud_REV]
GO

ALTER TABLE [AporteDetallado_aud]  WITH CHECK ADD  CONSTRAINT [FK_AporteDetallado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AporteDetallado_aud] CHECK CONSTRAINT [FK_AporteDetallado_aud_REV]
GO

ALTER TABLE [AporteDetalladoSimulado_aud]  WITH CHECK ADD  CONSTRAINT [FK_AporteDetalladoSimulado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AporteDetalladoSimulado_aud] CHECK CONSTRAINT [FK_AporteDetalladoSimulado_aud_REV]
GO

ALTER TABLE [AporteGeneral_aud]  WITH CHECK ADD  CONSTRAINT [FK_AporteGeneral_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AporteGeneral_aud] CHECK CONSTRAINT [FK_AporteGeneral_aud_REV]
GO

ALTER TABLE [AporteGeneralSimulado_aud]  WITH CHECK ADD  CONSTRAINT [FK_AporteGeneralSimulado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AporteGeneralSimulado_aud] CHECK CONSTRAINT [FK_AporteGeneralSimulado_aud_REV]
GO

ALTER TABLE [AreaCajaCompensacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_AreaCajaCompensacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AreaCajaCompensacion_aud] CHECK CONSTRAINT [FK_AreaCajaCompensacion_aud_REV]
GO

ALTER TABLE [ARL_aud]  WITH CHECK ADD  CONSTRAINT [FK_ARL_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ARL_aud] CHECK CONSTRAINT [FK_ARL_aud_REV]
GO

ALTER TABLE [AsesorResponsableEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_AsesorResponsableEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AsesorResponsableEmpleador_aud] CHECK CONSTRAINT [FK_AsesorResponsableEmpleador_aud_REV]
GO

ALTER TABLE [Beneficiario_aud]  WITH CHECK ADD  CONSTRAINT [FK_Beneficiario_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Beneficiario_aud] CHECK CONSTRAINT [FK_Beneficiario_aud_REV]
GO

ALTER TABLE [BeneficioEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_BeneficioEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [BeneficioEmpleador_aud] CHECK CONSTRAINT [FK_BeneficioEmpleador_aud_REV]
GO

ALTER TABLE [CajaCompensacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_CajaCompensacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CajaCompensacion_aud] CHECK CONSTRAINT [FK_CajaCompensacion_aud_REV]
GO

ALTER TABLE [CajaCorrespondencia_aud]  WITH CHECK ADD  CONSTRAINT [FK_CajaCorrespondencia_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CajaCorrespondencia_aud] CHECK CONSTRAINT [FK_CajaCorrespondencia_aud_REV]
GO

ALTER TABLE [CargueMultiple_aud]  WITH CHECK ADD  CONSTRAINT [FK_CargueMultiple_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CargueMultiple_aud] CHECK CONSTRAINT [FK_CargueMultiple_aud_REV]
GO

ALTER TABLE [CargueMultipleSupervivencia_aud]  WITH CHECK ADD  CONSTRAINT [FK_CargueMultipleSupervivencia_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CargueMultipleSupervivencia_aud] CHECK CONSTRAINT [FK_CargueMultipleSupervivencia_aud_REV]
GO

ALTER TABLE [Categoria_aud]  WITH CHECK ADD  CONSTRAINT [FK_Categoria_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Categoria_aud] CHECK CONSTRAINT [FK_Categoria_aud_REV]
GO

ALTER TABLE [CodigoCIIU_aud]  WITH CHECK ADD  CONSTRAINT [FK_CodigoCIIU_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CodigoCIIU_aud] CHECK CONSTRAINT [FK_CodigoCIIU_aud_REV]
GO

ALTER TABLE [Comunicado_aud]  WITH CHECK ADD  CONSTRAINT [FK_Comunicado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Comunicado_aud] CHECK CONSTRAINT [FK_Comunicado_aud_REV]
GO

ALTER TABLE [CondicionInvalidez_aud]  WITH CHECK ADD  CONSTRAINT [FK_CondicionInvalidez_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CondicionInvalidez_aud] CHECK CONSTRAINT [FK_CondicionInvalidez_aud_REV]
GO

ALTER TABLE [ConexionOperadorInformacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_ConexionOperadorInformacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ConexionOperadorInformacion_aud] CHECK CONSTRAINT [FK_ConexionOperadorInformacion_aud_REV]
GO

ALTER TABLE [ConsolaEstadoCargueMasivo_aud]  WITH CHECK ADD  CONSTRAINT [FK_ConsolaEstadoCargueMasivo_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ConsolaEstadoCargueMasivo_aud] CHECK CONSTRAINT [FK_ConsolaEstadoCargueMasivo_aud_REV]
GO

ALTER TABLE [Constante_aud]  WITH CHECK ADD  CONSTRAINT [FK_Constante_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Constante_aud] CHECK CONSTRAINT [FK_Constante_aud_REV]
GO

ALTER TABLE [Cotizante_aud]  WITH CHECK ADD  CONSTRAINT [FK_Cotizante_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Cotizante_aud] CHECK CONSTRAINT [FK_Cotizante_aud_REV]
GO

ALTER TABLE [DatosRegistroValidacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_DatosRegistroValidacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DatosRegistroValidacion_aud] CHECK CONSTRAINT [FK_DatosRegistroValidacion_aud_REV]
GO

ALTER TABLE [DatoTemporalSolicitud_aud]  WITH CHECK ADD  CONSTRAINT [FK_DatoTemporalSolicitud_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DatoTemporalSolicitud_aud] CHECK CONSTRAINT [FK_DatoTemporalSolicitud_aud_REV]
GO

ALTER TABLE [Departamento_aud]  WITH CHECK ADD  CONSTRAINT [FK_Departamento_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Departamento_aud] CHECK CONSTRAINT [FK_Departamento_aud_REV]
GO

ALTER TABLE [DetalleDatosRegistroValidacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_DetalleDatosRegistroValidacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DetalleDatosRegistroValidacion_aud] CHECK CONSTRAINT [FK_DetalleDatosRegistroValidacion_aud_REV]
GO

ALTER TABLE [DiasFestivos_aud]  WITH CHECK ADD  CONSTRAINT [FK_DiasFestivos_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DiasFestivos_aud] CHECK CONSTRAINT [FK_DiasFestivos_aud_REV]
GO

ALTER TABLE [DocumentoAdministracionEstadoSolicitud_aud]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoAdministracionEstadoSolicitud_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DocumentoAdministracionEstadoSolicitud_aud] CHECK CONSTRAINT [FK_DocumentoAdministracionEstadoSolicitud_aud_REV]
GO

ALTER TABLE [DocumentoEntidadPagadora_aud]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoEntidadPagadora_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DocumentoEntidadPagadora_aud] CHECK CONSTRAINT [FK_DocumentoEntidadPagadora_aud_REV]
GO

ALTER TABLE [ElementoDireccion_aud]  WITH CHECK ADD  CONSTRAINT [FK_ElementoDireccion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ElementoDireccion_aud] CHECK CONSTRAINT [FK_ElementoDireccion_aud_REV]
GO

ALTER TABLE [Empleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Empleador_aud] CHECK CONSTRAINT [FK_Empleador_aud_REV]
GO

ALTER TABLE [Empresa_aud]  WITH CHECK ADD  CONSTRAINT [FK_Empresa_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Empresa_aud] CHECK CONSTRAINT [FK_Empresa_aud_REV]
GO

ALTER TABLE [EntidadPagadora_aud]  WITH CHECK ADD  CONSTRAINT [FK_EntidadPagadora_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [EntidadPagadora_aud] CHECK CONSTRAINT [FK_EntidadPagadora_aud_REV]
GO

ALTER TABLE [EscalamientoSolicitud_aud]  WITH CHECK ADD  CONSTRAINT [FK_EscalamientoSolicitud_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [EscalamientoSolicitud_aud] CHECK CONSTRAINT [FK_EscalamientoSolicitud_aud_REV]
GO

ALTER TABLE [EtiquetaCorrespondenciaRadicado_aud]  WITH CHECK ADD  CONSTRAINT [FK_EtiquetaCorrespondenciaRadicado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [EtiquetaCorrespondenciaRadicado_aud] CHECK CONSTRAINT [FK_EtiquetaCorrespondenciaRadicado_aud_REV]
GO

ALTER TABLE [FieldCatalog_aud]  WITH CHECK ADD  CONSTRAINT [FK_FieldCatalog_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FieldCatalog_aud] CHECK CONSTRAINT [FK_FieldCatalog_aud_REV]
GO

ALTER TABLE [FieldDefinition_aud]  WITH CHECK ADD  CONSTRAINT [FK_FieldDefinition_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FieldDefinition_aud] CHECK CONSTRAINT [FK_FieldDefinition_aud_REV]
GO

ALTER TABLE [FieldDefinitionLoad_aud]  WITH CHECK ADD  CONSTRAINT [FK_FieldDefinitionLoad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FieldDefinitionLoad_aud] CHECK CONSTRAINT [FK_FieldDefinitionLoad_aud_REV]
GO

ALTER TABLE [FieldGenericCatalog_aud]  WITH CHECK ADD  CONSTRAINT [FK_FieldGenericCatalog_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FieldGenericCatalog_aud] CHECK CONSTRAINT [FK_FieldGenericCatalog_aud_REV]
GO

ALTER TABLE [FieldLoadCatalog_aud]  WITH CHECK ADD  CONSTRAINT [FK_FieldLoadCatalog_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FieldLoadCatalog_aud] CHECK CONSTRAINT [FK_FieldLoadCatalog_aud_REV]
GO

ALTER TABLE [FileDefinition_aud]  WITH CHECK ADD  CONSTRAINT [FK_FileDefinition_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FileDefinition_aud] CHECK CONSTRAINT [FK_FileDefinition_aud_REV]
GO

ALTER TABLE [FileDefinitionLoad_aud]  WITH CHECK ADD  CONSTRAINT [FK_FileDefinitionLoad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FileDefinitionLoad_aud] CHECK CONSTRAINT [FK_FileDefinitionLoad_aud_REV]
GO

ALTER TABLE [FileDefinitionLoadType_aud]  WITH CHECK ADD  CONSTRAINT [FK_FileDefinitionLoadType_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FileDefinitionLoadType_aud] CHECK CONSTRAINT [FK_FileDefinitionLoadType_aud_REV]
GO

ALTER TABLE [FileDefinitionType_aud]  WITH CHECK ADD  CONSTRAINT [FK_FileDefinitionType_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FileDefinitionType_aud] CHECK CONSTRAINT [FK_FileDefinitionType_aud_REV]
GO

ALTER TABLE [FileGenerated_aud]  WITH CHECK ADD  CONSTRAINT [FK_FileGenerated_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FileGenerated_aud] CHECK CONSTRAINT [FK_FileGenerated_aud_REV]
GO

ALTER TABLE [FileGeneratedLog_aud]  WITH CHECK ADD  CONSTRAINT [FK_FileGeneratedLog_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FileGeneratedLog_aud] CHECK CONSTRAINT [FK_FileGeneratedLog_aud_REV]
GO

ALTER TABLE [FileLoaded_aud]  WITH CHECK ADD  CONSTRAINT [FK_FileLoaded_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FileLoaded_aud] CHECK CONSTRAINT [FK_FileLoaded_aud_REV]
GO

ALTER TABLE [FileLoadedLog_aud]  WITH CHECK ADD  CONSTRAINT [FK_FileLoadedLog_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FileLoadedLog_aud] CHECK CONSTRAINT [FK_FileLoadedLog_aud_REV]
GO

ALTER TABLE [FileLocationCommon_aud]  WITH CHECK ADD  CONSTRAINT [FK_FileLocationCommon_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FileLocationCommon_aud] CHECK CONSTRAINT [FK_FileLocationCommon_aud_REV]
GO

ALTER TABLE [GlosaComentarioNovedad_aud]  WITH CHECK ADD  CONSTRAINT [FK_GlosaComentarioNovedad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [GlosaComentarioNovedad_aud] CHECK CONSTRAINT [FK_GlosaComentarioNovedad_aud_REV]
GO

ALTER TABLE [GradoAcademico_aud]  WITH CHECK ADD  CONSTRAINT [FK_GradoAcademico_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [GradoAcademico_aud] CHECK CONSTRAINT [FK_GradoAcademico_aud_REV]
GO

ALTER TABLE [GraphicFeature_aud]  WITH CHECK ADD  CONSTRAINT [FK_GraphicFeature_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [GraphicFeature_aud] CHECK CONSTRAINT [FK_GraphicFeature_aud_REV]
GO

ALTER TABLE [GraphicFeatureDefinition_aud]  WITH CHECK ADD  CONSTRAINT [FK_GraphicFeatureDefinition_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [GraphicFeatureDefinition_aud] CHECK CONSTRAINT [FK_GraphicFeatureDefinition_aud_REV]
GO

ALTER TABLE [GroupFC_FieldGC_aud]  WITH CHECK ADD  CONSTRAINT [FK_GroupFC_FieldGC_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [GroupFC_FieldGC_aud] CHECK CONSTRAINT [FK_GroupFC_FieldGC_aud_REV]
GO

ALTER TABLE [GrupoFamiliar_aud]  WITH CHECK ADD  CONSTRAINT [FK_GrupoFamiliar_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [GrupoFamiliar_aud] CHECK CONSTRAINT [FK_GrupoFamiliar_aud_REV]
GO

ALTER TABLE [HistoriaResultadoValidacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_HistoriaResultadoValidacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [HistoriaResultadoValidacion_aud] CHECK CONSTRAINT [FK_HistoriaResultadoValidacion_aud_REV]
GO

ALTER TABLE [IntentoAfiliacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_IntentoAfiliacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [IntentoAfiliacion_aud] CHECK CONSTRAINT [FK_IntentoAfiliacion_aud_REV]
GO

ALTER TABLE [IntentoAfiliRequisito_aud]  WITH CHECK ADD  CONSTRAINT [FK_IntentoAfiliRequisito_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [IntentoAfiliRequisito_aud] CHECK CONSTRAINT [FK_IntentoAfiliRequisito_aud_REV]
GO

ALTER TABLE [IntentoNovedad_aud]  WITH CHECK ADD  CONSTRAINT [FK_IntentoNovedad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [IntentoNovedad_aud] CHECK CONSTRAINT [FK_IntentoNovedad_aud_REV]
GO

ALTER TABLE [IntentoNoveRequisito_aud]  WITH CHECK ADD  CONSTRAINT [FK_IntentoNoveRequisito_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [IntentoNoveRequisito_aud] CHECK CONSTRAINT [FK_IntentoNoveRequisito_aud_REV]
GO

ALTER TABLE [ItemChequeo_aud]  WITH CHECK ADD  CONSTRAINT [FK_ItemChequeo_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ItemChequeo_aud] CHECK CONSTRAINT [FK_ItemChequeo_aud_REV]
GO

ALTER TABLE [LineCatalog_aud]  WITH CHECK ADD  CONSTRAINT [FK_LineCatalog_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [LineCatalog_aud] CHECK CONSTRAINT [FK_LineCatalog_aud_REV]
GO

ALTER TABLE [LineDefinition_aud]  WITH CHECK ADD  CONSTRAINT [FK_LineDefinition_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [LineDefinition_aud] CHECK CONSTRAINT [FK_LineDefinition_aud_REV]
GO

ALTER TABLE [LineDefinitionLoad_aud]  WITH CHECK ADD  CONSTRAINT [FK_LineDefinitionLoad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [LineDefinitionLoad_aud] CHECK CONSTRAINT [FK_LineDefinitionLoad_aud_REV]
GO

ALTER TABLE [LineLoadCatalog_aud]  WITH CHECK ADD  CONSTRAINT [FK_LineLoadCatalog_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [LineLoadCatalog_aud] CHECK CONSTRAINT [FK_LineLoadCatalog_aud_REV]
GO

ALTER TABLE [ListaEspecialRevision_aud]  WITH CHECK ADD  CONSTRAINT [FK_ListaEspecialRevision_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ListaEspecialRevision_aud] CHECK CONSTRAINT [FK_ListaEspecialRevision_aud_REV]
GO

ALTER TABLE [MovimientoAjusteAporte_aud]  WITH CHECK ADD  CONSTRAINT [FK_MovimientoAjusteAporte_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [MovimientoAjusteAporte_aud] CHECK CONSTRAINT [FK_MovimientoAjusteAporte_aud_REV]
GO

ALTER TABLE [Municipio_aud]  WITH CHECK ADD  CONSTRAINT [FK_Municipio_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Municipio_aud] CHECK CONSTRAINT [FK_Municipio_aud_REV]
GO

ALTER TABLE [NotificacionDestinatario_aud]  WITH CHECK ADD  CONSTRAINT [FK_NotificacionDestinatario_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [NotificacionDestinatario_aud] CHECK CONSTRAINT [FK_NotificacionDestinatario_aud_REV]
GO

ALTER TABLE [NotificacionEnviada_aud]  WITH CHECK ADD  CONSTRAINT [FK_NotificacionEnviada_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [NotificacionEnviada_aud] CHECK CONSTRAINT [FK_NotificacionEnviada_aud_REV]
GO

ALTER TABLE [Novedad_aud]  WITH CHECK ADD  CONSTRAINT [FK_Novedad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Novedad_aud] CHECK CONSTRAINT [FK_Novedad_aud_REV]
GO

ALTER TABLE [NovedadPila_aud]  WITH CHECK ADD  CONSTRAINT [FK_NovedadPila_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [NovedadPila_aud] CHECK CONSTRAINT [FK_NovedadPila_aud_REV]
GO

ALTER TABLE [OcupacionProfesion_aud]  WITH CHECK ADD  CONSTRAINT [FK_OcupacionProfesion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [OcupacionProfesion_aud] CHECK CONSTRAINT [FK_OcupacionProfesion_aud_REV]
GO

ALTER TABLE [OperadorInformacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_OperadorInformacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [OperadorInformacion_aud] CHECK CONSTRAINT [FK_OperadorInformacion_aud_REV]
GO

ALTER TABLE [OperadorInformacionCcf_aud]  WITH CHECK ADD  CONSTRAINT [FK_OperadorInformacionCcf_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [OperadorInformacionCcf_aud] CHECK CONSTRAINT [FK_OperadorInformacionCcf_aud_REV]
GO

ALTER TABLE [parameter_aud]  WITH CHECK ADD  CONSTRAINT [FK_parameter_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [parameter_aud] CHECK CONSTRAINT [FK_parameter_aud_REV]
GO

ALTER TABLE [ParametrizacionEjecucionProgramada_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionEjecucionProgramada_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionEjecucionProgramada_aud] CHECK CONSTRAINT [FK_ParametrizacionEjecucionProgramada_aud_REV]
GO

ALTER TABLE [ParametrizacionMedioDePago_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionMedioDePago_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionMedioDePago_aud] CHECK CONSTRAINT [FK_ParametrizacionMedioDePago_aud_REV]
GO

ALTER TABLE [ParametrizacionMetodoAsignacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionMetodoAsignacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionMetodoAsignacion_aud] CHECK CONSTRAINT [FK_ParametrizacionMetodoAsignacion_aud_REV]
GO

ALTER TABLE [ParametrizaEnvioComunicado_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizaEnvioComunicado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizaEnvioComunicado_aud] CHECK CONSTRAINT [FK_ParametrizaEnvioComunicado_aud_REV]
GO

ALTER TABLE [Parametro_aud]  WITH CHECK ADD  CONSTRAINT [FK_Parametro_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Parametro_aud] CHECK CONSTRAINT [FK_Parametro_aud_REV]
GO

ALTER TABLE [ParamValue_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParamValue_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParamValue_aud] CHECK CONSTRAINT [FK_ParamValue_aud_REV]
GO

ALTER TABLE [Persona_aud]  WITH CHECK ADD  CONSTRAINT [FK_Persona_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Persona_aud] CHECK CONSTRAINT [FK_Persona_aud_REV]
GO

ALTER TABLE [PersonaDetalle_aud]  WITH CHECK ADD  CONSTRAINT [FK_PersonaDetalle_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PersonaDetalle_aud] CHECK CONSTRAINT [FK_PersonaDetalle_aud_REV]
GO

ALTER TABLE [PilaArchivoAPRegistro1_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoAPRegistro1_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaArchivoAPRegistro1_aud] CHECK CONSTRAINT [FK_PilaArchivoAPRegistro1_aud_REV]
GO

ALTER TABLE [PilaArchivoARegistro1_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoARegistro1_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaArchivoARegistro1_aud] CHECK CONSTRAINT [FK_PilaArchivoARegistro1_aud_REV]
GO

ALTER TABLE [PilaArchivoFRegistro1_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoFRegistro1_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaArchivoFRegistro1_aud] CHECK CONSTRAINT [FK_PilaArchivoFRegistro1_aud_REV]
GO

ALTER TABLE [PilaArchivoFRegistro5_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoFRegistro5_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaArchivoFRegistro5_aud] CHECK CONSTRAINT [FK_PilaArchivoFRegistro5_aud_REV]
GO

ALTER TABLE [PilaArchivoFRegistro6_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoFRegistro6_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaArchivoFRegistro6_aud] CHECK CONSTRAINT [FK_PilaArchivoFRegistro6_aud_REV]
GO

ALTER TABLE [PilaArchivoFRegistro8_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoFRegistro8_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaArchivoFRegistro8_aud] CHECK CONSTRAINT [FK_PilaArchivoFRegistro8_aud_REV]
GO

ALTER TABLE [PilaArchivoFRegistro9_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoFRegistro9_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaArchivoFRegistro9_aud] CHECK CONSTRAINT [FK_PilaArchivoFRegistro9_aud_REV]
GO

ALTER TABLE [PilaArchivoIPRegistro1_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoIPRegistro1_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaArchivoIPRegistro1_aud] CHECK CONSTRAINT [FK_PilaArchivoIPRegistro1_aud_REV]
GO

ALTER TABLE [PilaArchivoIPRegistro2_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoIPRegistro2_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaArchivoIPRegistro2_aud] CHECK CONSTRAINT [FK_PilaArchivoIPRegistro2_aud_REV]
GO

ALTER TABLE [PilaArchivoIPRegistro3_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoIPRegistro3_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaArchivoIPRegistro3_aud] CHECK CONSTRAINT [FK_PilaArchivoIPRegistro3_aud_REV]
GO

ALTER TABLE [PilaArchivoIRegistro1_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoIRegistro1_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaArchivoIRegistro1_aud] CHECK CONSTRAINT [FK_PilaArchivoIRegistro1_aud_REV]
GO

ALTER TABLE [PilaArchivoIRegistro2_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoIRegistro2_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaArchivoIRegistro2_aud] CHECK CONSTRAINT [FK_PilaArchivoIRegistro2_aud_REV]
GO

ALTER TABLE [PilaArchivoIRegistro3_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoIRegistro3_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaArchivoIRegistro3_aud] CHECK CONSTRAINT [FK_PilaArchivoIRegistro3_aud_REV]
GO

ALTER TABLE [PilaClasificacionAportante_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaClasificacionAportante_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaClasificacionAportante_aud] CHECK CONSTRAINT [FK_PilaClasificacionAportante_aud_REV]
GO

ALTER TABLE [PilaEjecucionProgramada_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaEjecucionProgramada_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaEjecucionProgramada_aud] CHECK CONSTRAINT [FK_PilaEjecucionProgramada_aud_REV]
GO

ALTER TABLE [PilaErrorValidacionLog_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaErrorValidacionLog_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaErrorValidacionLog_aud] CHECK CONSTRAINT [FK_PilaErrorValidacionLog_aud_REV]
GO

ALTER TABLE [PilaEstadoBloque_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaEstadoBloque_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaEstadoBloque_aud] CHECK CONSTRAINT [FK_PilaEstadoBloque_aud_REV]
GO

ALTER TABLE [PilaEstadoBloqueOF_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaEstadoBloqueOF_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaEstadoBloqueOF_aud] CHECK CONSTRAINT [FK_PilaEstadoBloqueOF_aud_REV]
GO

ALTER TABLE [PilaIndiceCorreccionPlanilla_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaIndiceCorreccionPlanilla_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaIndiceCorreccionPlanilla_aud] CHECK CONSTRAINT [FK_PilaIndiceCorreccionPlanilla_aud_REV]
GO

ALTER TABLE [PilaIndicePlanilla_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaIndicePlanilla_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaIndicePlanilla_aud] CHECK CONSTRAINT [FK_PilaIndicePlanilla_aud_REV]
GO

ALTER TABLE [PilaIndicePlanillaOF_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaIndicePlanillaOF_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaIndicePlanillaOF_aud] CHECK CONSTRAINT [FK_PilaIndicePlanillaOF_aud_REV]
GO

ALTER TABLE [PilaNormatividadFechaVencimiento_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaNormatividadFechaVencimiento_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaNormatividadFechaVencimiento_aud] CHECK CONSTRAINT [FK_PilaNormatividadFechaVencimiento_aud_REV]
GO

ALTER TABLE [PilaOportunidadPresentacionPlanilla_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaOportunidadPresentacionPlanilla_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaOportunidadPresentacionPlanilla_aud] CHECK CONSTRAINT [FK_PilaOportunidadPresentacionPlanilla_aud_REV]
GO


ALTER TABLE [PilaPasoValores_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaPasoValores_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaPasoValores_aud] CHECK CONSTRAINT [FK_PilaPasoValores_aud_REV]
GO

ALTER TABLE [PilaProceso_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaProceso_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaProceso_aud] CHECK CONSTRAINT [FK_PilaProceso_aud_REV]
GO

ALTER TABLE [PilaTasasInteresMora_aud]  WITH CHECK ADD  CONSTRAINT [FK_PilaTasasInteresMora_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PilaTasasInteresMora_aud] CHECK CONSTRAINT [FK_PilaTasasInteresMora_aud_REV]
GO

ALTER TABLE [PlantillaComunicado_aud]  WITH CHECK ADD  CONSTRAINT [FK_PlantillaComunicado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PlantillaComunicado_aud] CHECK CONSTRAINT [FK_PlantillaComunicado_aud_REV]
GO

ALTER TABLE [Pregunta_aud]  WITH CHECK ADD  CONSTRAINT [FK_Pregunta_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Pregunta_aud] CHECK CONSTRAINT [FK_Pregunta_aud_REV]
GO

ALTER TABLE [ProcessorCatalog_aud]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorCatalog_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ProcessorCatalog_aud] CHECK CONSTRAINT [FK_ProcessorCatalog_aud_REV]
GO

ALTER TABLE [ProcessorDefinition_aud]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorDefinition_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ProcessorDefinition_aud] CHECK CONSTRAINT [FK_ProcessorDefinition_aud_REV]
GO

ALTER TABLE [ProcessorParameter_aud]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorParameter_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ProcessorParameter_aud] CHECK CONSTRAINT [FK_ProcessorParameter_aud_REV]
GO

ALTER TABLE [ProcessorParamValue_aud]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorParamValue_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ProcessorParamValue_aud] CHECK CONSTRAINT [FK_ProcessorParamValue_aud_REV]
GO

ALTER TABLE [ProductoNoConforme_aud]  WITH CHECK ADD  CONSTRAINT [FK_ProductoNoConforme_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ProductoNoConforme_aud] CHECK CONSTRAINT [FK_ProductoNoConforme_aud_REV]
GO

ALTER TABLE [ReferenciaToken_aud]  WITH CHECK ADD  CONSTRAINT [FK_ReferenciaToken_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ReferenciaToken_aud] CHECK CONSTRAINT [FK_ReferenciaToken_aud_REV]
GO

ALTER TABLE [RegistroPersonaInconsistente_aud]  WITH CHECK ADD  CONSTRAINT [FK_RegistroPersonaInconsistente_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RegistroPersonaInconsistente_aud] CHECK CONSTRAINT [FK_RegistroPersonaInconsistente_aud_REV]
GO

ALTER TABLE [RegistroPilaNovedad_aud]  WITH CHECK ADD  CONSTRAINT [FK_RegistroPilaNovedad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RegistroPilaNovedad_aud] CHECK CONSTRAINT [FK_RegistroPilaNovedad_aud_REV]
GO

ALTER TABLE [RelacionGrupoFamiliar_aud]  WITH CHECK ADD  CONSTRAINT [FK_RelacionGrupoFamiliar_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RelacionGrupoFamiliar_aud] CHECK CONSTRAINT [FK_RelacionGrupoFamiliar_aud_REV]
GO

ALTER TABLE [Requisito_aud]  WITH CHECK ADD  CONSTRAINT [FK_Requisito_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Requisito_aud] CHECK CONSTRAINT [FK_Requisito_aud_REV]
GO

ALTER TABLE [RequisitoCajaClasificacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_RequisitoCajaClasificacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RequisitoCajaClasificacion_aud] CHECK CONSTRAINT [FK_RequisitoCajaClasificacion_aud_REV]
GO

ALTER TABLE [ResultadoEjecucionProgramada_aud]  WITH CHECK ADD  CONSTRAINT [FK_ResultadoEjecucionProgramada_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ResultadoEjecucionProgramada_aud] CHECK CONSTRAINT [FK_ResultadoEjecucionProgramada_aud_REV]
GO

ALTER TABLE [RolAfiliado_aud]  WITH CHECK ADD  CONSTRAINT [FK_RolAfiliado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RolAfiliado_aud] CHECK CONSTRAINT [FK_RolAfiliado_aud_REV]
GO

ALTER TABLE [RolContactoEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_RolContactoEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RolContactoEmpleador_aud] CHECK CONSTRAINT [FK_RolContactoEmpleador_aud_REV]
GO

ALTER TABLE [SedeCajaCompensacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_SedeCajaCompensacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SedeCajaCompensacion_aud] CHECK CONSTRAINT [FK_SedeCajaCompensacion_aud_REV]
GO

ALTER TABLE [SocioEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_SocioEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SocioEmpleador_aud] CHECK CONSTRAINT [FK_SocioEmpleador_aud_REV]
GO

ALTER TABLE [Solicitud_aud]  WITH CHECK ADD  CONSTRAINT [FK_Solicitud_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Solicitud_aud] CHECK CONSTRAINT [FK_Solicitud_aud_REV]
GO

ALTER TABLE [SolicitudAfiliaciEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliaciEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudAfiliaciEmpleador_aud] CHECK CONSTRAINT [FK_SolicitudAfiliaciEmpleador_aud_REV]
GO

ALTER TABLE [SolicitudAfiliacionPersona_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliacionPersona_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudAfiliacionPersona_aud] CHECK CONSTRAINT [FK_SolicitudAfiliacionPersona_aud_REV]
GO

ALTER TABLE [SolicitudAsociacionPersonaEntidadPagadora_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAsociacionPersonaEntidadPagadora_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudAsociacionPersonaEntidadPagadora_aud] CHECK CONSTRAINT [FK_SolicitudAsociacionPersonaEntidadPagadora_aud_REV]
GO

ALTER TABLE [SolicitudNovedad_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudNovedad_aud] CHECK CONSTRAINT [FK_SolicitudNovedad_aud_REV]
GO

ALTER TABLE [SolicitudNovedadEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudNovedadEmpleador_aud] CHECK CONSTRAINT [FK_SolicitudNovedadEmpleador_aud_REV]
GO

ALTER TABLE [SolicitudNovedadPersona_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadPersona_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudNovedadPersona_aud] CHECK CONSTRAINT [FK_SolicitudNovedadPersona_aud_REV]
GO

ALTER TABLE [SucursalEmpresa_aud]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpresa_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SucursalEmpresa_aud] CHECK CONSTRAINT [FK_SucursalEmpresa_aud_REV]
GO

ALTER TABLE [SucursaRolContactEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_SucursaRolContactEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SucursaRolContactEmpleador_aud] CHECK CONSTRAINT [FK_SucursaRolContactEmpleador_aud_REV]
GO

ALTER TABLE [Tarjeta_aud]  WITH CHECK ADD  CONSTRAINT [FK_Tarjeta_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Tarjeta_aud] CHECK CONSTRAINT [FK_Tarjeta_aud_REV]
GO

ALTER TABLE [TipoVia_aud]  WITH CHECK ADD  CONSTRAINT [FK_TipoVia_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [TipoVia_aud] CHECK CONSTRAINT [FK_TipoVia_aud_REV]
GO

ALTER TABLE [Ubicacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_Ubicacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Ubicacion_aud] CHECK CONSTRAINT [FK_Ubicacion_aud_REV]
GO

ALTER TABLE [UbicacionEmpresa_aud]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [UbicacionEmpresa_aud] CHECK CONSTRAINT [FK_UbicacionEmpresa_aud_REV]
GO

ALTER TABLE [ValidacionProceso_aud]  WITH CHECK ADD  CONSTRAINT [FK_ValidacionProceso_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ValidacionProceso_aud] CHECK CONSTRAINT [FK_ValidacionProceso_aud_REV]
GO

ALTER TABLE [ValidatorCatalog_aud]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorCatalog_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ValidatorCatalog_aud] CHECK CONSTRAINT [FK_ValidatorCatalog_aud_REV]
GO

ALTER TABLE [ValidatorDefinition_aud]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorDefinition_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ValidatorDefinition_aud] CHECK CONSTRAINT [FK_ValidatorDefinition_aud_REV]
GO

ALTER TABLE [ValidatorParameter_aud]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorParameter_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ValidatorParameter_aud] CHECK CONSTRAINT [FK_ValidatorParameter_aud_REV]
GO

ALTER TABLE [ValidatorParamValue_aud]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorParamValue_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ValidatorParamValue_aud] CHECK CONSTRAINT [FK_ValidatorParamValue_aud_REV]
GO

ALTER TABLE [VariableComunicado_aud]  WITH CHECK ADD  CONSTRAINT [FK_VariableComunicado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [VariableComunicado_aud] CHECK CONSTRAINT [FK_VariableComunicado_aud_REV]
GO

