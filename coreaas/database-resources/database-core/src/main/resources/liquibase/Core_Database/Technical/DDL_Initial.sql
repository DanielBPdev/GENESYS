--liquibase formatted sql

--changeset Heinsohn:01
--comment: Creación de tablas

-- Creación de tabla [Afiliado]  
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [Afiliado](
	[afiId] [bigint] IDENTITY(1,1) NOT NULL,
	[afiEstadoAfiliadoCaja] [varchar](20) NOT NULL,
	[afiPersona] [bigint] NULL,
 CONSTRAINT [PK_Afiliado_afiId] PRIMARY KEY CLUSTERED 
(
	[afiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [AFP]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AFP](
	[afpId] [smallint] IDENTITY(1,1) NOT NULL,
	[afpNombre] [varchar](150) NULL,
	[afpCodigoPila] [varchar](10) NULL,
 CONSTRAINT [PK_AFP_afpId] PRIMARY KEY CLUSTERED 
(
	[afpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_AFP_afpCodigoPila] UNIQUE NONCLUSTERED 
(
	[afpCodigoPila] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [Aportante]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Aportante](
	[apoId] [bigint] IDENTITY(1,1) NOT NULL,
	[apoEmpleador] [bigint] NULL,
	[apoEntidadPagadora] [bigint] NULL,
	[apoPersona] [bigint] NULL,
	[apoEstadoAportanteInicial] [varchar](50) NULL,
	[apoEstadoAportanteFinal] [varchar](60) NULL,
	[apoEstadoAporteAportante] [varchar](40) NULL,
	[apoValorMoraAportante] [numeric](30, 2) NULL,
	[apoEstadoRegistroAporteAportante] [varchar](30) NULL,
 CONSTRAINT [PK_Aportante_apoId] PRIMARY KEY CLUSTERED 
(
	[apoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [AporteDetallado]  
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AporteDetallado](
	[apdId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla [AporteDetalladoSimulado] 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AporteDetalladoSimulado](
	[adsId] [bigint] IDENTITY(1,1) NOT NULL,
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
	[adsValorTotalAporteObligatorio] [bigint] NULL,
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

-- Creación de tabla [AporteGeneral] 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AporteGeneral](
	[apgId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla [AporteGeneralSimulado] 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AporteGeneralSimulado](
	[agsId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [AreaCajaCompensacion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AreaCajaCompensacion](
	[arcId] [smallint] IDENTITY(1,1) NOT NULL,
	[arcNombre] [varchar](30) NULL,
 CONSTRAINT [PK_AreaCajaCompensacion_arcId] PRIMARY KEY CLUSTERED 
(
	[arcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [ARL]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ARL](
	[arlId] [smallint] IDENTITY(1,1) NOT NULL,
	[arlNombre] [varchar](25) NULL,
 CONSTRAINT [PK_ARL_arlId] PRIMARY KEY CLUSTERED 
(
	[arlId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [AsesorResponsableEmpleador]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [AsesorResponsableEmpleador](
	[areId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [Beneficiario]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [Beneficiario](
	[benId] [bigint] IDENTITY(1,1) NOT NULL,
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
 CONSTRAINT [PK_Beneficiario_benId] PRIMARY KEY CLUSTERED 
(
	[benId] ASC

)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [BeneficioEmpleador]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [BeneficioEmpleador](
	[bemId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [CajaCompensacion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [CajaCompensacion](
	[ccfId] [int] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [CajaCorrespondencia]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [CajaCorrespondencia](
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

-- Creación de tabla  [CargueAfiliacionMultiple]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [CargueAfiliacionMultiple](
	[camId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [Categoria]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Categoria](
	[catId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [CodigoCIIU]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [CodigoCIIU](
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

-- Creación de tabla  [Comunicado]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Comunicado](
	[comId] [bigint] IDENTITY(1,1) NOT NULL,
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
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [ConexionOperadorInformacion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ConexionOperadorInformacion](
	[coiId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [ConsolaEstadoCargueMasivo]
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ConsolaEstadoCargueMasivo](
	[cecId] [bigint] IDENTITY(1,1) NOT NULL,
	[cecCcf] [varchar](5) NULL,
	[cecTipoProcesoMasivo] [varchar](40) NOT NULL,
	[cecUsuario] [varchar](20) NULL,
	[cecFechaInicio] [date] NULL,
	[cecFechaFin] [date] NULL,
	[cecNumRegistroObjetivo] [bigint] NOT NULL,
	[cecNumRegistroProcesado] [bigint] NOT NULL,
	[cecNumRegistroConErrores] [bigint] NULL,
	[cecNumRegistroValidos] [bigint] NULL,
	[cecEstadoCargueMasivo] [varchar](15) NOT NULL,
	[cecCargueId] [bigint] NULL,
	[cecFileLoadedId] [bigint] NULL,
	[cecIdentificacionECM] [varchar](255) NOT NULL,
	[cecGradoAvance] [decimal](3,3) NOT NULL,
 CONSTRAINT [PK_ConsolaEstadoCargueMasivo_cecId] PRIMARY KEY CLUSTERED 
(
	[cecId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [Constante]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Constante](
	[cnsId] [int] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla [Cotizante]  
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Cotizante](
	[cotId] [bigint] IDENTITY(1,1) NOT NULL,
	[cotTipoCotizante] [varchar](100) NULL,
	[cotAfiliadoCotizante] [bigint] NULL,
	[cotEstadoCotizanteInicial] [varchar](60) NULL,
	[cotEstadoCotizanteFinal] [varchar](60) NULL,
	[cotValorMoraCotizante] [numeric](30, 2) NULL,
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

-- Creación de tabla  [DatosRegistroValidacion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DatosRegistroValidacion](
	[drvId] [bigint] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK_DatosRegistroValidaciono_drvId] PRIMARY KEY CLUSTERED 
(
	[drvId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

-- Creación de tabla  [DatoTemporalSolicitud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DatoTemporalSolicitud](
	[dtsId] [bigint] IDENTITY(1,1) NOT NULL,
	[dtsSolicitud] [bigint] NULL,
	[dtsJsonPayload] [text] NULL,
 CONSTRAINT [PK_DatoTemporalSolicitud_dtsId] PRIMARY KEY CLUSTERED 
(
	[dtsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

-- Creación de tabla  [Departamento]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Departamento](
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

-- Creación de tabla  [DetalleDatosRegistroValidacion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [DetalleDatosRegistroValidacion](
	[ddrIdDato] [bigint] NOT NULL,
	[ddrValorDetalle] [varchar](50) NULL,
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

-- Creación de tabla  [DiasFestivos]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [DiasFestivos](
	[pifId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [DocumentoEntidadPagadora]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [DocumentoEntidadPagadora](
	[dpgId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [ElementoDireccion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ElementoDireccion](
	[eldId] [bigint] IDENTITY(1,1) NOT NULL,
	[eldNombre] [varchar](20) NULL,
 CONSTRAINT [PK_ElementoDireccion_eldId] PRIMARY KEY CLUSTERED 
(
	[eldId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [Empleador]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Empleador](
	[empId] [bigint] IDENTITY(1,1) NOT NULL,
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_Empleador_empEmpresa] UNIQUE NONCLUSTERED 
(
	[empEmpresa] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [Empresa]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Empresa](
	[empId] [bigint] IDENTITY(1,1) NOT NULL,
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_Empresa_empPersona] UNIQUE NONCLUSTERED 
(
	[empPersona] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [EntidadPagadora]   
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [EntidadPagadora](
	[epaId] [bigint] IDENTITY(1,1) NOT NULL,
	[epaAportante] [bit] NULL,
	[epaCanalComunicacion] [varchar](20) NULL,
	[epaEmailComunicacion] [varchar](255) NULL,
	[epaEstadoEntidadPagadora] [varchar](20) NULL,
	[epaMedioComunicacion] [varchar](50) NULL,
	[epaNombreContacto] [varchar](50) NULL,
	[epaEmpresa] [bigint] NULL,
	[epaSucursalPagadora] [bigint] NULL,
	[epaTipoAfiliacion] [varchar](50) NULL,
	[epaCargoContacto] [varchar](20) NULL
 CONSTRAINT [PK_Empresa_epaId] PRIMARY KEY CLUSTERED 
(
	[epaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_EntidadPagadora_epaEmpresa] UNIQUE NONCLUSTERED 
(
	[epaEmpresa] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [EscalamientoSolicitud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [EscalamientoSolicitud](
	[esoId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [EtiquetaCorrespondenciaRadicado]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [EtiquetaCorrespondenciaRadicado](
	[eprId] [bigint] IDENTITY(1,1) NOT NULL,
	[eprAsignada] [bit] NULL,
	[eprCodigo] [varchar](12) NULL,
	[eprTipoEtiqueta] [varchar](50) NULL,
	[eprProcedenciaEtiqueta] [varchar](20) NULL,
 CONSTRAINT [PK_EtiquetaPreimpresa_eprId] PRIMARY KEY CLUSTERED 
(
	[eprId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_EtiquetaCorrespondenciaRadicado_eprCodigo] UNIQUE NONCLUSTERED 
(
	[eprCodigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [FieldCatalog]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FieldCatalog](
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
 CONSTRAINT [PK_FieldCatalog_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [FieldDefinition]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FieldDefinition](
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
 CONSTRAINT [PK_FieldDefinition_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [FieldDefinitionLoad]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FieldDefinitionLoad](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [FieldGenericCatalog]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FieldGenericCatalog](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [FieldLoadCatalog]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FieldLoadCatalog](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [FileDefinition]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileDefinition](
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
 CONSTRAINT [PK_FileDefinition_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [FileDefinitionLoad]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileDefinitionLoad](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [FileDefinitionLoadType]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileDefinitionLoadType](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [FileDefinitionType]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileDefinitionType](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [FileGenerated]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileGenerated](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [FileGeneratedLog]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileGeneratedLog](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [FileLoaded]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileLoaded](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [FileLoadedLog]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileLoadedLog](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [FileLocationCommon]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [FileLocationCommon](
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
 CONSTRAINT [PK_FileLocationCommon_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla [GlosaComentarioNovedad]  
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [GlosaComentarioNovedad](
	[gcnId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [GradoAcademico]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [GradoAcademico](
	[graId] [smallint] IDENTITY(1,1) NOT NULL,
	[graNombre] [varchar](20) NULL,
 CONSTRAINT [PK_GradoAcademico_graId] PRIMARY KEY CLUSTERED 
(
	[graId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [GraphicFeature]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [GraphicFeature](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [GraphicFeatureDefinition]   
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

-- Creación de tabla  [GroupFC_FieldGC]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GroupFC_FieldGC](
	[fieldGenericCatalogs_id] [bigint] NOT NULL,
	[groupFieldCatalogs_id] [bigint] NOT NULL
) ON [PRIMARY]

GO

-- Creación de tabla  [GroupFieldCatalogs]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [GroupFieldCatalogs](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [GrupoFamiliar]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [GrupoFamiliar](
	[grfId] [bigint] IDENTITY(1,1) NOT NULL,
	[grfParametrizacionMedioPago] [smallint] NULL,
	[grfNumero] [smallint] NOT NULL,
	[grfObservaciones] [varchar](500) NULL,
	[grfAdministradorSubsidio] [bigint] NULL,
	[grfAfiliado] [bigint] NOT NULL,
	[grfUbicacion] [bigint] NULL,
	[grfRelacionGrupoFamiliar] [smallint] NULL,
 CONSTRAINT [PK_GrupoFamiliar_grfId] PRIMARY KEY CLUSTERED 
(
	[grfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [HistoriaResultadoValidacion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [HistoriaResultadoValidacion](
	[hrvId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [IntentoAfiliacion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [IntentoAfiliacion](
	[iafId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [IntentoAfiliRequisito]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoAfiliRequisito](
	[iarId] [bigint] IDENTITY(1,1) NOT NULL,
	[iarIntentoAfiliacion] [bigint] NULL,
	[iarRequisito] [bigint] NULL,
 CONSTRAINT [PK_IntentoAfiliRequisito_iarId] PRIMARY KEY CLUSTERED 
(
	[iarId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
-- Creación de tabla  [IntentoNoveRequisito]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoNoveRequisito](
	[inrId] [bigint] IDENTITY(1,1) NOT NULL,
	[inrRequisito] [bigint] NULL,
	[inrIntentoNovedad] [bigint] NULL,
 CONSTRAINT [PK_IntentoNoveRequisito_id] PRIMARY KEY CLUSTERED 
(
	[inrId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

-- Creación de tabla  [ItemChequeo]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [ItemChequeo](
	[ichId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [IntentoNovedad]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [IntentoNovedad](
	[inoId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [LineCatalog]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [LineCatalog](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [LineDefinition]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [LineDefinition](
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
 CONSTRAINT [PK_LineDefinition_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [LineDefinitionLoad]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [LineDefinitionLoad](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [LineLoadCatalog]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [LineLoadCatalog](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [ListaEspecialRevision]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ListaEspecialRevision](
	[lerId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla [MovimientoAjusteAporte]  
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [MovimientoAjusteAporte](
	[maaId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [Municipio]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Municipio](
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

-- Creación de tabla  [NotificacionDestinatario]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [NotificacionDestinatario](
	[nodId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [NotificacionEnviada]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [NotificacionEnviada](
	[noeId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [Novedad]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Novedad](
	[novId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [OcupacionProfesion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [OcupacionProfesion](
	[ocuId] [int] IDENTITY(1,1) NOT NULL,
	[ocuNombre] [varchar](100) NULL,
 CONSTRAINT [PK_OcupacionProfesion_ocuId] PRIMARY KEY CLUSTERED 
(
	[ocuId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [OperadorInformacion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [OperadorInformacion](
	[oinId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [OperadorInformacionCcf]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [OperadorInformacionCcf](
	[oicId] [bigint] IDENTITY(1,1) NOT NULL,
	[oicOperadorInformacion] [bigint] NULL,
	[oicCajaCompensacion] [int] NULL,
	[oicEstado] [bit] NULL,
 CONSTRAINT [PK_PilaOperadorInformacionCcf_oicId] PRIMARY KEY CLUSTERED 
(
	[oicId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

-- Creación de tabla  [parameter]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [parameter](
	[id] [bigint] NOT NULL,
	[description] [varchar](100) NULL,
	[name] [varchar](20) NOT NULL,
	[type] [varchar](50) NULL,
 CONSTRAINT [PK_parameter_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_parameter_name] UNIQUE NONCLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [ParametrizacionEjecucionProgramada]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ParametrizacionEjecucionProgramada](
	[pepId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [ParametrizacionMedioDePago]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ParametrizacionMedioDePago](
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
 
-- Creación de tabla  [ParametrizacionMetodoAsignacion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ParametrizacionMetodoAsignacion](
	[pmaId] [bigint] IDENTITY(1,1) NOT NULL,
	[pmaSedeCajaCompensacion] [bigint] NOT NULL,
	[pmaProceso] [varchar](100) NOT NULL,
	[pmaMetodoAsignacion] [varchar](20) NULL,
	[pmaUsuario] [varchar](255) NULL,
	[pmaGrupo] [varchar](50) NULL,
 CONSTRAINT [PK_ParametrizacionMetodoAsignacion_pmaId] PRIMARY KEY CLUSTERED
 (
	[pmaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_ParametrizacionMetodoAsignacion_pmaSedeCajaCompensacion_pmaProceso] UNIQUE NONCLUSTERED 
(
	[pmaSedeCajaCompensacion] ASC,
	[pmaProceso] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [ParametrizaEnvioComunicado]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ParametrizaEnvioComunicado](
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

-- Creación de tabla  [Parametro]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Parametro](
	[prmId] [int] IDENTITY(1,1) NOT NULL,
	[prmNombre] [varchar](100) NULL,
	[prmValor] [varchar](150) NULL,
 CONSTRAINT [PK_Parametro_prmId] PRIMARY KEY CLUSTERED 
(
	[prmId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_Parametro_prmNombre] UNIQUE NONCLUSTERED 
(
	[prmNombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [ParamValue]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ParamValue](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [Persona]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Persona](
	[perId] [bigint] IDENTITY(1,1) NOT NULL,
	[perDigitoVerificacion] [smallint] NULL,
	[perNumeroIdentificacion] [varchar](16) NULL,
	[perRazonSocial] [varchar](250) NULL,
	[perTipoIdentificacion] [varchar](20) NULL,
	[perUbicacionPrincipal] [bigint] NULL,
	[perPrimerNombre] [varchar](50) NULL,
	[perSegundoNombre] [varchar](50) NULL,
	[perPrimerApellido] [varchar](50) NULL,
	[perSegundoApellido] [varchar](50) NULL,
	[perFechaNacimiento] [date] NULL,
	[perFechaExpedicionDocumento] [date] NULL,
	[perGenero] [varchar](10) NULL,
	[perOcupacionProfesion] [int] NULL,
	[perNivelEducativo] [varchar](100) NULL,
	[perCabezaHogar] [bit] NULL,
	[perAutorizaUsoDatosPersonales] [bit] NULL,
	[perResideSectorRural] [bit] NULL,
	[perMedioPago] [smallint] NULL,
	[perEstadoCivil] [varchar](20) NULL,
	[perHabitaCasaPropia] [bit] NULL,
	[perFallecido] [bit] NULL,
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

-- Creación de tabla  [PilaArchivoAPRegistro1]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoAPRegistro1](
	[ap1Id] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaArchivoARegistro1]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoARegistro1](
	[pa1Id] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaArchivoFRegistro1]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoFRegistro1](
	[pf1Id] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaArchivoFRegistro5]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoFRegistro5](
	[pf5Id] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaArchivoFRegistro6]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoFRegistro6](
	[pf6Id] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaArchivoFRegistro8]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PilaArchivoFRegistro8](
	[pf8Id] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaArchivoFRegistro9]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PilaArchivoFRegistro9](
	[pf9Id] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaArchivoIPRegistro1]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoIPRegistro1](
	[ip1Id] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaArchivoIPRegistro2]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoIPRegistro2](
	[ip2Id] [bigint] IDENTITY(1,1) NOT NULL,
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
	[ip2MarcaValRegistroAporte] varchar(50) NULL,
	[ip2EstadoRegistroAporte] varchar(60) NULL,
	[ip2FechaProcesamientoValidRegAporte] datetime NULL,
	[ip2EstadoValidacionV1] varchar(30) NULL,
 CONSTRAINT [PK_PilaArchivoIPRegistro2_ip2Id] PRIMARY KEY CLUSTERED 
(
	[ip2Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [PilaArchivoIPRegistro3]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PilaArchivoIPRegistro3](
	[ip3Id] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaArchivoIRegistro1]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoIRegistro1](
	[pi1Id] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaArchivoIRegistro2]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaArchivoIRegistro2](
	[pi2Id] [bigint] IDENTITY(1,1) NOT NULL,
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
	[pi2MarcaValRegistroAporte] varchar(50) NULL,
	[pi2EstadoRegistroAporte] varchar(60) NULL,
	[pi2FechaProcesamientoValidRegAporte] datetime NULL,
	[pi2EstadoValidacionV0] varchar(30) NULL,
	[pi2EstadoValidacionV1] varchar(30) NULL,
	[pi2EstadoValidacionV2] varchar(30) NULL,
	[pi2EstadoValidacionV3] varchar(30) NULL,
 CONSTRAINT [PK_PilaArchivoIRegistro2_pi2Id] PRIMARY KEY CLUSTERED 
(
	[pi2Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [PilaArchivoIRegistro3]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PilaArchivoIRegistro3](
	[pi3Id] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaClasificacionAportante]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaClasificacionAportante](
	[pcaId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaEjecucionProgramada]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaEjecucionProgramada](
	[pepId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaErrorValidacionLog]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaErrorValidacionLog](
	[pevId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaEstadoBloque]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaEstadoBloque](
	[pebId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaEstadoBloqueOF]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaEstadoBloqueOF](
	[peoId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaIndiceCorreccionPlanilla]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaIndiceCorreccionPlanilla](
	[picId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaIndicePlanilla]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaIndicePlanilla](
	[pipId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaIndicePlanillaOF]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaIndicePlanillaOF](
	[pioId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaNormatividadFechaVencimiento]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaNormatividadFechaVencimiento](
	[pfvId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaOportunidadPresentacionPlanilla]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaOportunidadPresentacionPlanilla](
	[popId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaPasoValores]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaPasoValores](
	[ppvId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaProceso]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaProceso](
	[pprId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PilaTasasInteresMora]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PilaTasasInteresMora](
	[ptiId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [PlantillaComunicado]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [PlantillaComunicado](
	[pcoId] [bigint] IDENTITY(1,1) NOT NULL,
	[pcoAsunto] [varchar](100) NULL,
	[pcoCuerpo] [varchar](5000) NULL,
	[pcoEncabezado] [varchar](500) NULL,
	[pcoIdentificadorImagenPie] [varchar](255) NULL,
	[pcoMensaje] [varchar](5000) NULL,
	[pcoNombre] [varchar](150) NULL,
	[pcoPie] [varchar](500) NULL,
	[pcoEtiqueta] [varchar](150)  NULL,
 CONSTRAINT [PK_PlantillaComunicado_pcoId] PRIMARY KEY CLUSTERED 
(
	[pcoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_PlantillaComunicado_pcoNombre_pcoEtiqueta] UNIQUE NONCLUSTERED 
(
	[pcoNombre] ASC,
	[pcoEtiqueta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [ProcessorCatalog]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ProcessorCatalog](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [ProcessorDefinition]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ProcessorDefinition](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [ProcessorParameter]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ProcessorParameter](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [ProcessorParamValue]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ProcessorParamValue](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [ProductoNoConforme]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ProductoNoConforme](
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

-- Creación de tabla [RegistroPilaNovedad]  
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [RegistroPilaNovedad](
	[rpnId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [RelacionGrupoFamiliar]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [RelacionGrupoFamiliar](
	[rgfId] [smallint] IDENTITY(1,1) NOT NULL,
	[rgfNombre] [varchar](15) NULL,
 CONSTRAINT [PK_RelacionGrupoFamiliar_rgfId] PRIMARY KEY CLUSTERED 
(
	[rgfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [Requisito]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Requisito](
	[reqId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [RequisitoCajaClasificacion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [RequisitoCajaClasificacion](
	[rtsId] [bigint] IDENTITY(1,1) NOT NULL,
	[rtsEstado] [varchar](20) NULL,
	[rtsRequisito] [bigint] NULL,
	[rtsClasificacion] [varchar](100) NULL,
	[rtsTipoTransaccion] [varchar](100) NULL,
	[rtsCajaCompensacion] [int] NULL,
	[rtsTextoAyuda] [varchar](1500) NULL,
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

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [ResultadoEjecucionProgramada]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ResultadoEjecucionProgramada](
	[repId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [RolAfiliado]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [RolAfiliado](
	[roaId] [bigint] IDENTITY(1,1) NOT NULL,
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
 CONSTRAINT [PK_RolAfiliado_roaId] PRIMARY KEY CLUSTERED 
(
	[roaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [RolContactoEmpleador]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [RolContactoEmpleador](
	[rceId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [SedeCajaCompensacion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SedeCajaCompensacion](
	[sccfId] [bigint] IDENTITY(1,1) NOT NULL,
	[sccfNombre] [varchar](100) NULL,
	[sccfVirtual] [bit] NULL,
	[sccCodigo] [varchar](2) NULL,
 CONSTRAINT [PK_SedeCajaCompensacion_sccfId] PRIMARY KEY CLUSTERED 
(
	[sccfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_SedeCajaCompensacion_sccCodigo] UNIQUE NONCLUSTERED 
(
	[sccCodigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [SocioEmpleador]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SocioEmpleador](
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

-- Creación de tabla  [Solicitud]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Solicitud](
	[solId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [SolicitudAfiliaciEmpleador]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SolicitudAfiliaciEmpleador](
	[saeId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [SolicitudAfiliacionPersona]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SolicitudAfiliacionPersona](
	[sapId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [SolicitudAsociacionPersonaEntidadPagadora]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SolicitudAsociacionPersonaEntidadPagadora](
	[soaId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [SolicitudNovedad]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SolicitudNovedad](
	[snoId] [bigint] IDENTITY(1,1) NOT NULL,
	[snoEstadoSolicitud] [varchar](50) NULL,
	[snoNovedad] [bigint] NULL,
	[snoSolicitudGlobal] [bigint] NULL,
 CONSTRAINT [PK_SolicitudNovedad_snoId] PRIMARY KEY CLUSTERED 
(
	[snoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [SolicitudNovedadAfiliado]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedadAfiliado](
	[snaId] [bigint] NOT NULL,
	[snaIdAfiliado] [bigint] NULL,
	[snaIdSolicitudNovedad] [bigint] NULL,
 CONSTRAINT [PK_SolicitudNovedadAfiliado_snaId] PRIMARY KEY CLUSTERED 
(
	[snaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
 CREATE SEQUENCE SEC_consecutivoSna START WITH 1 INCREMENT BY 1 ;
GO

-- Creación de tabla  [SolicitudNovedadEmpleador]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedadEmpleador](
	[sneId] [bigint] NOT NULL,
	[sneIdEmpleador] [bigint] NULL,
	[sneIdSolicitudNovedad] [bigint] NULL,
 CONSTRAINT [PK_SolicitudNovedadEmpleador_sneId] PRIMARY KEY CLUSTERED 
(
	[sneId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
 CREATE SEQUENCE SEC_consecutivoSne START WITH 1 INCREMENT BY 1 ;
 
GO

-- Creación de tabla  [SucursalEmpresa]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SucursalEmpresa](
	[sueId] [bigint] IDENTITY(1,1) NOT NULL,
	[sueCodigo] [varchar](3) NULL,
	[sueNombre] [varchar](40) NULL,
	[sueCodigoCIIU] [smallint] NULL,
	[sueEmpresa] [bigint] NULL,
	[sueMedioPagoSubsidioMonetario] [smallint] NULL,
	[sueUbicacion] [bigint] NULL,
	[sueEstadoSucursal] [varchar](25) NULL,
 CONSTRAINT [PK_SucursalEmpresa_sueId] PRIMARY KEY CLUSTERED 
(
	[sueId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [SucursaRolContactEmpleador]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SucursaRolContactEmpleador](
	[srcId] [bigint] IDENTITY(1,1) NOT NULL,
	[srcRolContactoEmpleador] [bigint] NULL,
	[srcSucursalEmpleador] [bigint] NULL,
 CONSTRAINT [PK_SucursaRolContactEmpleador_srcId] PRIMARY KEY CLUSTERED 
(
	[srcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

-- Creación de tabla  [Tarjeta]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [Tarjeta](
	[tarId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [TipoVia]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [TipoVia](
	[tviId] [bigint] IDENTITY(1,1) NOT NULL,
	[tviNombreVia] [varchar](20) NULL,
 CONSTRAINT [PK_TipoVia_tviId] PRIMARY KEY CLUSTERED 
(
	[tviId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [Ubicacion]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Ubicacion](
	[ubiId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [UbicacionEmpresa]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [UbicacionEmpresa](
	[ubeId] [bigint] IDENTITY(1,1) NOT NULL,
	[ubeEmpresa] [bigint] NULL,
	[ubeUbicacion] [bigint] NULL,
	[ubeTipoUbicacion] [varchar](30) NULL,
 CONSTRAINT [PK_UbicacionEmpresa_ubeId] PRIMARY KEY CLUSTERED 
(
	[ubeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_UbicacionEmpresa_ubeEmpresa_ubeTipoUbicacion] UNIQUE NONCLUSTERED 
(
	[ubeEmpresa] ASC,
	[ubeTipoUbicacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [ValidacionProceso]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ValidacionProceso](
	[vapId] [bigint] IDENTITY(1,1) NOT NULL,
	[vapBloque] [varchar](65) NULL,
	[vapValidacion] [varchar](100) NULL,
	[vapProceso] [varchar](100) NULL,
	[vapEstadoProceso] [varchar](20) NULL,
	[vapOrden] [int] NULL,
	[vapObjetoValidacion] [varchar](60) NULL,
 CONSTRAINT [PK_ValidacionProceso_vapId] PRIMARY KEY CLUSTERED 
(
	[vapId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

-- Creación de tabla  [ValidatorCatalog]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ValidatorCatalog](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [ValidatorDefinition]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ValidatorDefinition](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [ValidatorParameter]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ValidatorParameter](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [ValidatorParamValue]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ValidatorParamValue](
	[id] [bigint] NOT NULL,
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

-- Creación de tabla  [VariableComunicado]   
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [VariableComunicado](
	[vcoId] [bigint] IDENTITY(1,1) NOT NULL,
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

-- Creación de tabla  [DocumentoAdministracionEstadoSolicitud]    
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoAdministracionEstadoSolicitud](
	[daeId] [bigint] IDENTITY(1,1) NOT NULL,
	[daeSolicitud] [bigint] NULL,
	[daeDocumentoSoporteCambioEstado] [varchar](100) NULL,
	[daeNombreDocumento] [varchar](100) NULL,
 CONSTRAINT [PK_DocumentoAdministracionEstadoSolicitud_daeId] PRIMARY KEY CLUSTERED 
(
	[daeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO 


-- Creación de FK de las tablas

ALTER TABLE [Afiliado]  WITH CHECK ADD  CONSTRAINT [FK_Afliado_afiPersona] FOREIGN KEY([afiPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Afiliado] CHECK CONSTRAINT [FK_Afliado_afiPersona]
GO
ALTER TABLE [Aportante]  WITH CHECK ADD  CONSTRAINT [FK_Aportante_apoEmpleador] FOREIGN KEY([apoEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [Aportante] CHECK CONSTRAINT [FK_Aportante_apoEmpleador]
GO
ALTER TABLE [Aportante]  WITH CHECK ADD  CONSTRAINT [FK_Aportante_apoEntidadPagadora] FOREIGN KEY([apoEntidadPagadora])
REFERENCES [EntidadPagadora] ([epaId])
GO
ALTER TABLE [Aportante] CHECK CONSTRAINT [FK_Aportante_apoEntidadPagadora]
GO
ALTER TABLE [Aportante]  WITH CHECK ADD  CONSTRAINT [FK_Aportante_apoPersona] FOREIGN KEY([apoPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Aportante] CHECK CONSTRAINT [FK_Aportante_apoPersona]
GO
ALTER TABLE [AporteDetallado]  WITH CHECK ADD  CONSTRAINT [FK_AporteDetallado_apdAporteGeneral] FOREIGN KEY([apdAporteGeneral])
REFERENCES [AporteGeneral] ([apgId])
GO
ALTER TABLE [AporteDetallado] CHECK CONSTRAINT [FK_AporteDetallado_apdAporteGeneral]
GO
ALTER TABLE [AporteDetallado]  WITH CHECK ADD  CONSTRAINT [FK_AporteDetallado_apdArchivoIPRegistro2] FOREIGN KEY([apdArchivoIPRegistro2])
REFERENCES [PilaArchivoIPRegistro2] ([ip2Id])
GO
ALTER TABLE [AporteDetallado] CHECK CONSTRAINT [FK_AporteDetallado_apdArchivoIPRegistro2]
GO
ALTER TABLE [AporteDetallado]  WITH CHECK ADD  CONSTRAINT [FK_AporteDetallado_apdArchivoIRegistro2] FOREIGN KEY([apdArchivoIRegistro2])
REFERENCES [PilaArchivoIRegistro2] ([pi2Id])
GO
ALTER TABLE [AporteDetallado] CHECK CONSTRAINT [FK_AporteDetallado_apdArchivoIRegistro2]
GO
ALTER TABLE [AporteDetallado]  WITH CHECK ADD  CONSTRAINT [FK_AporteDetallado_apdCotizante] FOREIGN KEY([apdCotizante])
REFERENCES [Cotizante] ([cotId])
GO
ALTER TABLE [AporteDetallado] CHECK CONSTRAINT [FK_AporteDetallado_apdCotizante]
GO
ALTER TABLE [AporteDetallado]  WITH CHECK ADD  CONSTRAINT [FK_AporteDetallado_apdUbicacionLaboral] FOREIGN KEY([apdUbicacionLaboral])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [AporteDetallado] CHECK CONSTRAINT [FK_AporteDetallado_apdUbicacionLaboral]
GO
ALTER TABLE [AporteGeneral]  WITH CHECK ADD  CONSTRAINT [FK_AporteGeneral_apgAportante] FOREIGN KEY([apgAportante])
REFERENCES [Aportante] ([apoId])
GO
ALTER TABLE [AporteGeneral] CHECK CONSTRAINT [FK_AporteGeneral_apgAportante]
GO
ALTER TABLE [AporteGeneral]  WITH CHECK ADD  CONSTRAINT [FK_AporteGeneral_apgIndicePlanilla] FOREIGN KEY([apgIndicePlanilla])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [AporteGeneral] CHECK CONSTRAINT [FK_AporteGeneral_apgIndicePlanilla]
GO
ALTER TABLE [AporteGeneral]  WITH CHECK ADD  CONSTRAINT [FK_AporteGeneral_apgOperadorInformacion] FOREIGN KEY([apgOperadorInformacion])
REFERENCES [OperadorInformacion] ([oinId])
GO
ALTER TABLE [AporteGeneral] CHECK CONSTRAINT [FK_AporteGeneral_apgOperadorInformacion]
GO
ALTER TABLE [AsesorResponsableEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_AsesorResponsableEmpleador_areEmpleador] FOREIGN KEY([areEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [AsesorResponsableEmpleador] CHECK CONSTRAINT [FK_AsesorResponsableEmpleador_areEmpleador]
GO
ALTER TABLE [Beneficiario]  WITH CHECK ADD  CONSTRAINT [FK_Beneficiario_benAfiliado] FOREIGN KEY([benAfiliado])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [Beneficiario] CHECK CONSTRAINT [FK_Beneficiario_benAfiliado]
GO
ALTER TABLE [Beneficiario]  WITH CHECK ADD  CONSTRAINT [FK_Beneficiario_benGradoAcademico] FOREIGN KEY([benGradoAcademico])
REFERENCES [GradoAcademico] ([graId])
GO
ALTER TABLE [Beneficiario] CHECK CONSTRAINT [FK_Beneficiario_benGradoAcademico]
GO
ALTER TABLE [Beneficiario]  WITH CHECK ADD  CONSTRAINT [FK_Beneficiario_benGrupoFamiliar] FOREIGN KEY([benGrupoFamiliar])
REFERENCES [GrupoFamiliar] ([grfId])
GO
ALTER TABLE [Beneficiario] CHECK CONSTRAINT [FK_Beneficiario_benGrupoFamiliar]
GO
ALTER TABLE [Beneficiario]  WITH CHECK ADD  CONSTRAINT [FK_Beneficiario_benPersona] FOREIGN KEY([benPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Beneficiario] CHECK CONSTRAINT [FK_Beneficiario_benPersona]
GO
ALTER TABLE [BeneficioEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_BeneficioEmpleador_bemEmpleador] FOREIGN KEY([bemEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [BeneficioEmpleador] CHECK CONSTRAINT [FK_BeneficioEmpleador_bemEmpleador]
GO
ALTER TABLE [CajaCompensacion]  WITH CHECK ADD  CONSTRAINT [FK_CajaCompensacion_ccfDepartamento] FOREIGN KEY([ccfDepartamento])
REFERENCES [Departamento] ([depId])
GO
ALTER TABLE [CajaCompensacion] CHECK CONSTRAINT [FK_CajaCompensacion_ccfDepartamento]
GO
ALTER TABLE [CargueAfiliacionMultiple]  WITH CHECK ADD  CONSTRAINT [FK_CargueAfiliacionMultiple_camIdSucursalEmpleador] FOREIGN KEY([camIdSucursalEmpleador])
REFERENCES [SucursalEmpresa] ([sueId])
GO
ALTER TABLE [CargueAfiliacionMultiple] CHECK CONSTRAINT [FK_CargueAfiliacionMultiple_camIdSucursalEmpleador]
GO
ALTER TABLE [CargueAfiliacionMultiple]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpresa_camIdEmpleador] FOREIGN KEY([camIdEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [CargueAfiliacionMultiple] CHECK CONSTRAINT [FK_SucursalEmpresa_camIdEmpleador]
GO
ALTER TABLE [Categoria]  WITH CHECK ADD  CONSTRAINT [FK_Categoria_catIdAfiliado] FOREIGN KEY([catIdAfiliado])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [Categoria] CHECK CONSTRAINT [FK_Categoria_catIdAfiliado]
GO
ALTER TABLE [Categoria]  WITH CHECK ADD  CONSTRAINT [FK_Categoria_catIdBeneficiario] FOREIGN KEY([catIdBeneficiario])
REFERENCES [Beneficiario] ([benId])
GO
ALTER TABLE [Categoria] CHECK CONSTRAINT [FK_Categoria_catIdBeneficiario]
GO
ALTER TABLE [Comunicado]  WITH CHECK ADD  CONSTRAINT [FK_Comunicado_comPlantillaComunicado] FOREIGN KEY([comPlantillaComunicado])
REFERENCES [PlantillaComunicado] ([pcoId])
GO
ALTER TABLE [Comunicado] CHECK CONSTRAINT [FK_Comunicado_comPlantillaComunicado]
GO
ALTER TABLE [Comunicado]  WITH CHECK ADD  CONSTRAINT [FK_Comunicado_comSolicitud] FOREIGN KEY([comSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [Comunicado] CHECK CONSTRAINT [FK_Comunicado_comSolicitud]
GO
ALTER TABLE [ConexionOperadorInformacion]  WITH CHECK ADD  CONSTRAINT [FK_ConexionOperadorInformacion_coiOperadorInformacionCcf] FOREIGN KEY([coiOperadorInformacionCcf])
REFERENCES [OperadorInformacionCcf] ([oicId])
GO
ALTER TABLE [ConexionOperadorInformacion] CHECK CONSTRAINT [FK_ConexionOperadorInformacion_coiOperadorInformacionCcf]
GO
ALTER TABLE [Cotizante]  WITH CHECK ADD  CONSTRAINT [FK_Cotizante_cotAfiliadoCotizante] FOREIGN KEY([cotAfiliadoCotizante])
REFERENCES [RolAfiliado] ([roaId])
GO
ALTER TABLE [Cotizante] CHECK CONSTRAINT [FK_Cotizante_cotAfiliadoCotizante]
GO
ALTER TABLE [DetalleDatosRegistroValidacion]  WITH CHECK ADD  CONSTRAINT [FK_DetalleDatosRegistroValidacion_ddrvIdDato] FOREIGN KEY([ddrIdDato])
REFERENCES [DatosRegistroValidacion] ([drvId])
GO
ALTER TABLE [DetalleDatosRegistroValidacion] CHECK CONSTRAINT [FK_DetalleDatosRegistroValidacion_ddrvIdDato]
GO
ALTER TABLE [DocumentoEntidadPagadora]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoEntidadPagadora_dpgEntidadPagadora] FOREIGN KEY([dpgEntidadPagadora])
REFERENCES [EntidadPagadora] ([epaId])
GO
ALTER TABLE [DocumentoEntidadPagadora] CHECK CONSTRAINT [FK_DocumentoEntidadPagadora_dpgEntidadPagadora]
GO
ALTER TABLE [Empleador]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_empEmpresa] FOREIGN KEY([empEmpresa])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [Empleador] CHECK CONSTRAINT [FK_Empleador_empEmpresa]
GO
ALTER TABLE [Empleador]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_empMedioPagoSubsidioMonetario] FOREIGN KEY([empMedioPagoSubsidioMonetario])
REFERENCES [ParametrizacionMedioDePago] ([pmpId])
GO
ALTER TABLE [Empleador] CHECK CONSTRAINT [FK_Empleador_empMedioPagoSubsidioMonetario]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_Empresa_empUbicacionRepresentanteLegal] FOREIGN KEY([empUbicacionRepresentanteLegal])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_Empresa_empUbicacionRepresentanteLegal]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_Empresa_empUbicacionRepresentanteLegalSuplente] FOREIGN KEY([empUbicacionRepresentanteLegalSuplente])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_Empresa_empUbicacionRepresentanteLegalSuplente]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_empresa_empUltimaCajaCompensacion] FOREIGN KEY([empUltimaCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_empresa_empUltimaCajaCompensacion]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_empArl] FOREIGN KEY([empArl])
REFERENCES [ARL] ([arlId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_empArl]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_empCodigoCIIU] FOREIGN KEY([empCodigoCIIU])
REFERENCES [CodigoCIIU] ([ciiId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_empCodigoCIIU]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_empPersona] FOREIGN KEY([empPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_empPersona]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_empRepresentanteLegal] FOREIGN KEY([empRepresentanteLegal])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_empRepresentanteLegal]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_empRepresentanteLegalSuplente] FOREIGN KEY([empRepresentanteLegalSuplente])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_empRepresentanteLegalSuplente]
GO
ALTER TABLE [EntidadPagadora]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_epaEmpresa] FOREIGN KEY([epaEmpresa])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [EntidadPagadora] CHECK CONSTRAINT [FK_Empleador_epaEmpresa]
GO
ALTER TABLE [EntidadPagadora]  WITH CHECK ADD  CONSTRAINT [FK_EntidadPagadora_epaSucursalPagadora] FOREIGN KEY([epaSucursalPagadora])
REFERENCES [SucursalEmpresa] ([sueId])
GO
ALTER TABLE [EntidadPagadora] CHECK CONSTRAINT [FK_EntidadPagadora_epaSucursalPagadora]
GO
ALTER TABLE [EscalamientoSolicitud]  WITH CHECK ADD  CONSTRAINT [FK_EscalamientoSolicitud_esoSolicitud] FOREIGN KEY([esoSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [EscalamientoSolicitud] CHECK CONSTRAINT [FK_EscalamientoSolicitud_esoSolicitud]
GO
ALTER TABLE [FieldCatalog]  WITH CHECK ADD  CONSTRAINT [FK_FieldCatalog_fieldGCatalog_id] FOREIGN KEY([fieldGCatalog_id])
REFERENCES [FieldGenericCatalog] ([id])
GO
ALTER TABLE [FieldCatalog] CHECK CONSTRAINT [FK_FieldCatalog_fieldGCatalog_id]
GO
ALTER TABLE [FieldCatalog]  WITH CHECK ADD  CONSTRAINT [FK_FieldCatalog_idLineCatalog] FOREIGN KEY([idLineCatalog])
REFERENCES [LineCatalog] ([id])
GO
ALTER TABLE [FieldCatalog] CHECK CONSTRAINT [FK_FieldCatalog_idLineCatalog]
GO
ALTER TABLE [FieldDefinition]  WITH CHECK ADD  CONSTRAINT [FK_FieldDefinition_fieldCatalog_id] FOREIGN KEY([fieldCatalog_id])
REFERENCES [FieldCatalog] ([id])
GO
ALTER TABLE [FieldDefinition] CHECK CONSTRAINT [FK_FieldDefinition_fieldCatalog_id]
GO
ALTER TABLE [FieldDefinition]  WITH CHECK ADD  CONSTRAINT [FK_FieldDefinition_lineDefinition_id] FOREIGN KEY([lineDefinition_id])
REFERENCES [LineDefinition] ([id])
GO
ALTER TABLE [FieldDefinition] CHECK CONSTRAINT [FK_FieldDefinition_lineDefinition_id]
GO
ALTER TABLE [FieldDefinitionLoad]  WITH CHECK ADD  CONSTRAINT [FK_FieldDefinitionLoad_fieldLoadCatalog_id] FOREIGN KEY([fieldLoadCatalog_id])
REFERENCES [FieldLoadCatalog] ([id])
GO
ALTER TABLE [FieldDefinitionLoad] CHECK CONSTRAINT [FK_FieldDefinitionLoad_fieldLoadCatalog_id]
GO
ALTER TABLE [FieldDefinitionLoad]  WITH CHECK ADD  CONSTRAINT [FK_FieldDefinitionLoad_lineDefinition_id] FOREIGN KEY([lineDefinition_id])
REFERENCES [LineDefinitionLoad] ([id])
GO
ALTER TABLE [FieldDefinitionLoad] CHECK CONSTRAINT [FK_FieldDefinitionLoad_lineDefinition_id]
GO
ALTER TABLE [FileDefinition]  WITH CHECK ADD  CONSTRAINT [FK_FileDefinition_fileDefinitionType_id] FOREIGN KEY([fileDefinitionType_id])
REFERENCES [FileDefinitionType] ([id])
GO
ALTER TABLE [FileDefinition] CHECK CONSTRAINT [FK_FileDefinition_fileDefinitionType_id]
GO
ALTER TABLE [FileDefinition]  WITH CHECK ADD  CONSTRAINT [FK_FileDefinition_fileLocation_id] FOREIGN KEY([fileLocation_id])
REFERENCES [FileLocationCommon] ([id])
GO
ALTER TABLE [FileDefinition] CHECK CONSTRAINT [FK_FileDefinition_fileLocation_id]
GO
ALTER TABLE [FileDefinitionLoad]  WITH CHECK ADD  CONSTRAINT [FK_FileDefinitionLoad_fileDefinitionType_id] FOREIGN KEY([fileDefinitionType_id])
REFERENCES [FileDefinitionLoadType] ([id])
GO
ALTER TABLE [FileDefinitionLoad] CHECK CONSTRAINT [FK_FileDefinitionLoad_fileDefinitionType_id]
GO
ALTER TABLE [FileGenerated]  WITH CHECK ADD  CONSTRAINT [FK_FileGenerated_fileDefinition_id] FOREIGN KEY([fileDefinition_id])
REFERENCES [FileDefinition] ([id])
GO
ALTER TABLE [FileGenerated] CHECK CONSTRAINT [FK_FileGenerated_fileDefinition_id]
GO
ALTER TABLE [FileGeneratedLog]  WITH CHECK ADD  CONSTRAINT [FK_FileGeneratedLog_fileGenerated_id] FOREIGN KEY([fileGenerated_id])
REFERENCES [FileGenerated] ([id])
GO
ALTER TABLE [FileGeneratedLog] CHECK CONSTRAINT [FK_FileGeneratedLog_fileGenerated_id]
GO
ALTER TABLE [FileLoaded]  WITH CHECK ADD  CONSTRAINT [FK_FileLoaded_fileDefinition_id] FOREIGN KEY([fileDefinition_id])
REFERENCES [FileDefinitionLoad] ([id])
GO
ALTER TABLE [FileLoaded] CHECK CONSTRAINT [FK_FileLoaded_fileDefinition_id]
GO
ALTER TABLE [FileLoadedLog]  WITH CHECK ADD  CONSTRAINT [FK_FileLoadedLog_fileLoaded_id] FOREIGN KEY([fileLoaded_id])
REFERENCES [FileLoaded] ([id])
GO
ALTER TABLE [FileLoadedLog] CHECK CONSTRAINT [FK_FileLoadedLog_fileLoaded_id]
GO
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
ALTER TABLE [GroupFC_FieldGC]  WITH CHECK ADD  CONSTRAINT [FK_GroupFC_FieldGC_fieldGenericCatalogs_id] FOREIGN KEY([fieldGenericCatalogs_id])
REFERENCES [FieldGenericCatalog] ([id])
GO
ALTER TABLE [GroupFC_FieldGC] CHECK CONSTRAINT [FK_GroupFC_FieldGC_fieldGenericCatalogs_id]
GO
ALTER TABLE [GroupFC_FieldGC]  WITH CHECK ADD  CONSTRAINT [FK_GroupFC_FieldGC_groupFieldCatalogs_id] FOREIGN KEY([groupFieldCatalogs_id])
REFERENCES [GroupFieldCatalogs] ([id])
GO
ALTER TABLE [GroupFC_FieldGC] CHECK CONSTRAINT [FK_GroupFC_FieldGC_groupFieldCatalogs_id]
GO
ALTER TABLE [GrupoFamiliar]  WITH CHECK ADD  CONSTRAINT [FK_GrupoFamiliar_grfAdministradorSubsidio] FOREIGN KEY([grfAdministradorSubsidio])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [GrupoFamiliar] CHECK CONSTRAINT [FK_GrupoFamiliar_grfAdministradorSubsidio]
GO
ALTER TABLE [GrupoFamiliar]  WITH CHECK ADD  CONSTRAINT [FK_GrupoFamiliar_grfAfiliado] FOREIGN KEY([grfAfiliado])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [GrupoFamiliar] CHECK CONSTRAINT [FK_GrupoFamiliar_grfAfiliado]
GO
ALTER TABLE [GrupoFamiliar]  WITH CHECK ADD  CONSTRAINT [FK_GrupoFamiliar_grfParametrizacionMedioPago] FOREIGN KEY([grfParametrizacionMedioPago])
REFERENCES [ParametrizacionMedioDePago] ([pmpId])
GO
ALTER TABLE [GrupoFamiliar] CHECK CONSTRAINT [FK_GrupoFamiliar_grfParametrizacionMedioPago]
GO
ALTER TABLE [GrupoFamiliar]  WITH CHECK ADD  CONSTRAINT [FK_GrupoFamiliar_grfRelacionGrupoFamiliar] FOREIGN KEY([grfRelacionGrupoFamiliar])
REFERENCES [RelacionGrupoFamiliar] ([rgfId])
GO
ALTER TABLE [GrupoFamiliar] CHECK CONSTRAINT [FK_GrupoFamiliar_grfRelacionGrupoFamiliar]
GO
ALTER TABLE [GrupoFamiliar]  WITH CHECK ADD  CONSTRAINT [FK_GrupoFamiliar_grfUbicacion] FOREIGN KEY([grfUbicacion])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [GrupoFamiliar] CHECK CONSTRAINT [FK_GrupoFamiliar_grfUbicacion]
GO
ALTER TABLE [HistoriaResultadoValidacion]  WITH CHECK ADD  CONSTRAINT [FK_HistoriaResultadoValidacion_hrvIdDatosRegistro] FOREIGN KEY([hrvIdDatosRegistro])
REFERENCES [DatosRegistroValidacion] ([drvId])
GO
ALTER TABLE [HistoriaResultadoValidacion] CHECK CONSTRAINT [FK_HistoriaResultadoValidacion_hrvIdDatosRegistro]
GO
ALTER TABLE [IntentoAfiliacion]  WITH CHECK ADD  CONSTRAINT [FK_IntentoAfiliacion_iafSolicitud] FOREIGN KEY([iafSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [IntentoAfiliacion] CHECK CONSTRAINT [FK_IntentoAfiliacion_iafSolicitud]
GO
ALTER TABLE [IntentoAfiliRequisito]  WITH CHECK ADD  CONSTRAINT [FK_IntentoAfiliRequisito_iarIntentoAfiliacion] FOREIGN KEY([iarIntentoAfiliacion])
REFERENCES [IntentoAfiliacion] ([iafId])
GO
ALTER TABLE [IntentoAfiliRequisito] CHECK CONSTRAINT [FK_IntentoAfiliRequisito_iarIntentoAfiliacion]
GO
ALTER TABLE [IntentoAfiliRequisito]  WITH CHECK ADD  CONSTRAINT [FK_IntentoAfiliRequisito_iarRequisito] FOREIGN KEY([iarRequisito])
REFERENCES [Requisito] ([reqId])
GO
ALTER TABLE [IntentoAfiliRequisito] CHECK CONSTRAINT [FK_IntentoAfiliRequisito_iarRequisito]
GO
ALTER TABLE [IntentoNoveRequisito]  WITH CHECK ADD  CONSTRAINT [FK_IntentoNoveRequisito_inrIntentoNovedad] FOREIGN KEY([inrIntentoNovedad])
REFERENCES [IntentoNovedad] ([inoId])
GO
ALTER TABLE [IntentoNoveRequisito] CHECK CONSTRAINT [FK_IntentoNoveRequisito_inrIntentoNovedad]
GO
ALTER TABLE [ItemChequeo]  WITH CHECK ADD  CONSTRAINT [FK_ItemChequeo_ichPersona] FOREIGN KEY([ichPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [ItemChequeo] CHECK CONSTRAINT [FK_ItemChequeo_ichPersona]
GO
ALTER TABLE [ItemChequeo]  WITH CHECK ADD  CONSTRAINT [FK_ItemChequeo_ichRequisito] FOREIGN KEY([ichRequisito])
REFERENCES [Requisito] ([reqId])
GO
ALTER TABLE [ItemChequeo] CHECK CONSTRAINT [FK_ItemChequeo_ichRequisito]
GO
ALTER TABLE [ItemChequeo]  WITH CHECK ADD  CONSTRAINT [FK_ItemChequeo_ichSolicitud] FOREIGN KEY([ichSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [ItemChequeo] CHECK CONSTRAINT [FK_ItemChequeo_ichSolicitud]
GO
ALTER TABLE [LineDefinition]  WITH CHECK ADD  CONSTRAINT [FK_LineDefinition_fileDefinition_id] FOREIGN KEY([fileDefinition_id])
REFERENCES [FileDefinition] ([id])
GO
ALTER TABLE [LineDefinition] CHECK CONSTRAINT [FK_LineDefinition_fileDefinition_id]
GO
ALTER TABLE [LineDefinition]  WITH CHECK ADD  CONSTRAINT [FK_LineDefinition_lineCatalog_id] FOREIGN KEY([lineCatalog_id])
REFERENCES [LineCatalog] ([id])
GO
ALTER TABLE [LineDefinition] CHECK CONSTRAINT [FK_LineDefinition_lineCatalog_id]
GO
ALTER TABLE [LineDefinitionLoad]  WITH CHECK ADD  CONSTRAINT [FK_LineDefinitionLoad_fileDefinition_id] FOREIGN KEY([fileDefinition_id])
REFERENCES [FileDefinitionLoad] ([id])
GO
ALTER TABLE [LineDefinitionLoad] CHECK CONSTRAINT [FK_LineDefinitionLoad_fileDefinition_id]
GO
ALTER TABLE [LineDefinitionLoad]  WITH CHECK ADD  CONSTRAINT [FK_LineDefinitionLoad_lineLoadCatalog_id] FOREIGN KEY([lineLoadCatalog_id])
REFERENCES [LineLoadCatalog] ([id])
GO
ALTER TABLE [LineDefinitionLoad] CHECK CONSTRAINT [FK_LineDefinitionLoad_lineLoadCatalog_id]
GO
ALTER TABLE [ListaEspecialRevision]  WITH CHECK ADD  CONSTRAINT [FK_ListaEspecialRevision_lerCajaCompensacion] FOREIGN KEY([lerCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [ListaEspecialRevision] CHECK CONSTRAINT [FK_ListaEspecialRevision_lerCajaCompensacion]
GO
ALTER TABLE [MovimientoAjusteAporte]  WITH CHECK ADD  CONSTRAINT [FK_MovimientoAjusteAporte_maaAporteDetalladoCorregido] FOREIGN KEY([maaAporteDetalladoCorregido])
REFERENCES [AporteDetallado] ([apdId])
GO
ALTER TABLE [MovimientoAjusteAporte] CHECK CONSTRAINT [FK_MovimientoAjusteAporte_maaAporteDetalladoCorregido]
GO
ALTER TABLE [MovimientoAjusteAporte]  WITH CHECK ADD  CONSTRAINT [FK_MovimientoAjusteAporte_maaAporteDetalladoOriginal] FOREIGN KEY([maaAporteDetalladoOriginal])
REFERENCES [AporteDetallado] ([apdId])
GO
ALTER TABLE [MovimientoAjusteAporte] CHECK CONSTRAINT [FK_MovimientoAjusteAporte_maaAporteDetalladoOriginal]
GO
ALTER TABLE [MovimientoAjusteAporte]  WITH CHECK ADD  CONSTRAINT [FK_MovimientoAjusteAporte_maaIndicePlanillaCorregida] FOREIGN KEY([maaIndicePlanillaCorregida])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [MovimientoAjusteAporte] CHECK CONSTRAINT [FK_MovimientoAjusteAporte_maaIndicePlanillaCorregida]
GO
ALTER TABLE [MovimientoAjusteAporte]  WITH CHECK ADD  CONSTRAINT [FK_MovimientoAjusteAporte_maaIndicePlanillaOriginal] FOREIGN KEY([maaIndicePlanillaOriginal])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [MovimientoAjusteAporte] CHECK CONSTRAINT [FK_MovimientoAjusteAporte_maaIndicePlanillaOriginal]
GO
ALTER TABLE [Municipio]  WITH CHECK ADD  CONSTRAINT [FK_Municipio_munDepartamento] FOREIGN KEY([munDepartamento])
REFERENCES [Departamento] ([depId])
GO
ALTER TABLE [Municipio] CHECK CONSTRAINT [FK_Municipio_munDepartamento]
GO
ALTER TABLE [NotificacionDestinatario]  WITH CHECK ADD  CONSTRAINT [FK_NotDestinatario_nodNotEnviada] FOREIGN KEY([nodNotEnviada])
REFERENCES [NotificacionEnviada] ([noeId])
GO
ALTER TABLE [NotificacionDestinatario] CHECK CONSTRAINT [FK_NotDestinatario_nodNotEnviada]
GO
ALTER TABLE [OperadorInformacionCcf]  WITH CHECK ADD  CONSTRAINT [FK_OperadorInformacionCcf_oicCajaCompensacion] FOREIGN KEY([oicCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [OperadorInformacionCcf] CHECK CONSTRAINT [FK_OperadorInformacionCcf_oicCajaCompensacion]
GO
ALTER TABLE [OperadorInformacionCcf]  WITH CHECK ADD  CONSTRAINT [FK_OperadorInformacionCcf_oicOperadorInformacion] FOREIGN KEY([oicOperadorInformacion])
REFERENCES [OperadorInformacion] ([oinId])
GO
ALTER TABLE [OperadorInformacionCcf] CHECK CONSTRAINT [FK_OperadorInformacionCcf_oicOperadorInformacion]
GO
ALTER TABLE [ParametrizacionMetodoAsignacion]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionMetodoAsignacion_pmaSedeCajaCompensacion] FOREIGN KEY([pmaSedeCajaCompensacion])
REFERENCES [SedeCajaCompensacion] ([sccfId])
GO
ALTER TABLE [ParametrizacionMetodoAsignacion] CHECK CONSTRAINT [FK_ParametrizacionMetodoAsignacion_pmaSedeCajaCompensacion]
GO
ALTER TABLE [ParamValue]  WITH CHECK ADD  CONSTRAINT [FK_ParamValue_parameter_id] FOREIGN KEY([parameter_id])
REFERENCES [parameter] ([id])
GO
ALTER TABLE [ParamValue] CHECK CONSTRAINT [FK_ParamValue_parameter_id]
GO
ALTER TABLE [Persona]  WITH CHECK ADD  CONSTRAINT [FK_Persona_perMedioPago] FOREIGN KEY([perMedioPago])
REFERENCES [ParametrizacionMedioDePago] ([pmpId])
GO
ALTER TABLE [Persona] CHECK CONSTRAINT [FK_Persona_perMedioPago]
GO
ALTER TABLE [Persona]  WITH CHECK ADD  CONSTRAINT [FK_Persona_perOcupacionProfesion] FOREIGN KEY([perOcupacionProfesion])
REFERENCES [OcupacionProfesion] ([ocuId])
GO
ALTER TABLE [Persona] CHECK CONSTRAINT [FK_Persona_perOcupacionProfesion]
GO
ALTER TABLE [Persona]  WITH CHECK ADD  CONSTRAINT [FK_Persona_perUbicacionPrincipal] FOREIGN KEY([perUbicacionPrincipal])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [Persona] CHECK CONSTRAINT [FK_Persona_perUbicacionPrincipal]
GO
ALTER TABLE [PilaArchivoAPRegistro1]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoAPRegistro1_ap1IndicePlanilla] FOREIGN KEY([ap1IndicePlanilla])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [PilaArchivoAPRegistro1] CHECK CONSTRAINT [FK_PilaArchivoAPRegistro1_ap1IndicePlanilla]
GO
ALTER TABLE [PilaArchivoARegistro1]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoARegistro1_pa1IndicePlanilla] FOREIGN KEY([pa1IndicePlanilla])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [PilaArchivoARegistro1] CHECK CONSTRAINT [FK_PilaArchivoARegistro1_pa1IndicePlanilla]
GO
ALTER TABLE [PilaArchivoFRegistro1]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoFRegistro1_pf1IndicePlanillaOF] FOREIGN KEY([pf1IndicePlanillaOF])
REFERENCES [PilaIndicePlanillaOF] ([pioId])
GO
ALTER TABLE [PilaArchivoFRegistro1] CHECK CONSTRAINT [FK_PilaArchivoFRegistro1_pf1IndicePlanillaOF]
GO
ALTER TABLE [PilaArchivoFRegistro5]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoFRegistro5_pf5IndicePlanillaOF] FOREIGN KEY([pf5IndicePlanillaOF])
REFERENCES [PilaIndicePlanillaOF] ([pioId])
GO
ALTER TABLE [PilaArchivoFRegistro5] CHECK CONSTRAINT [FK_PilaArchivoFRegistro5_pf5IndicePlanillaOF]
GO
ALTER TABLE [PilaArchivoFRegistro6]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoFRegistro6_pf6IndicePlanillaOF] FOREIGN KEY([pf6IndicePlanillaOF])
REFERENCES [PilaIndicePlanillaOF] ([pioId])
GO
ALTER TABLE [PilaArchivoFRegistro6] CHECK CONSTRAINT [FK_PilaArchivoFRegistro6_pf6IndicePlanillaOF]
GO
ALTER TABLE [PilaArchivoFRegistro8]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoFRegistro8_pf8IndicePlanillaOF] FOREIGN KEY([pf8IndicePlanillaOF])
REFERENCES [PilaIndicePlanillaOF] ([pioId])
GO
ALTER TABLE [PilaArchivoFRegistro8] CHECK CONSTRAINT [FK_PilaArchivoFRegistro8_pf8IndicePlanillaOF]
GO
ALTER TABLE [PilaArchivoFRegistro9]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoFRegistro9_pf9IndicePlanillaOF] FOREIGN KEY([pf9IndicePlanillaOF])
REFERENCES [PilaIndicePlanillaOF] ([pioId])
GO
ALTER TABLE [PilaArchivoFRegistro9] CHECK CONSTRAINT [FK_PilaArchivoFRegistro9_pf9IndicePlanillaOF]
GO
ALTER TABLE [PilaArchivoIPRegistro1]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoIPRegistro1_ip1IndicePlanilla] FOREIGN KEY([ip1IndicePlanilla])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [PilaArchivoIPRegistro1] CHECK CONSTRAINT [FK_PilaArchivoIPRegistro1_ip1IndicePlanilla]
GO
ALTER TABLE [PilaArchivoIPRegistro2]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoIPRegistro2_ip2IndicePlanilla] FOREIGN KEY([ip2IndicePlanilla])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [PilaArchivoIPRegistro2] CHECK CONSTRAINT [FK_PilaArchivoIPRegistro2_ip2IndicePlanilla]
GO
ALTER TABLE [PilaArchivoIPRegistro3]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoIPRegistro3_ip3IndicePlanilla] FOREIGN KEY([ip3IndicePlanilla])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [PilaArchivoIPRegistro3] CHECK CONSTRAINT [FK_PilaArchivoIPRegistro3_ip3IndicePlanilla]
GO
ALTER TABLE [PilaArchivoIRegistro1]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoIRegistro1_pi1IndicePlanilla] FOREIGN KEY([pi1IndicePlanilla])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [PilaArchivoIRegistro1] CHECK CONSTRAINT [FK_PilaArchivoIRegistro1_pi1IndicePlanilla]
GO
ALTER TABLE [PilaArchivoIRegistro2]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoIRegistro2_pi2IndicePlanilla] FOREIGN KEY([pi2IndicePlanilla])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [PilaArchivoIRegistro2] CHECK CONSTRAINT [FK_PilaArchivoIRegistro2_pi2IndicePlanilla]
GO
ALTER TABLE [PilaArchivoIRegistro3]  WITH CHECK ADD  CONSTRAINT [FK_PilaArchivoIRegistro3_pi3IndicePlanilla] FOREIGN KEY([pi3IndicePlanilla])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [PilaArchivoIRegistro3] CHECK CONSTRAINT [FK_PilaArchivoIRegistro3_pi3IndicePlanilla]
GO
ALTER TABLE [PilaEjecucionProgramada]  WITH CHECK ADD  CONSTRAINT [FK_PilaEjecucionProgramada_pepCajaCompensacion] FOREIGN KEY([pepCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [PilaEjecucionProgramada] CHECK CONSTRAINT [FK_PilaEjecucionProgramada_pepCajaCompensacion]
GO
ALTER TABLE [PilaEstadoBloque]  WITH CHECK ADD  CONSTRAINT [FK_PilaEstadoBloque_pebIndicePlanilla] FOREIGN KEY([pebIndicePlanilla])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [PilaEstadoBloque] CHECK CONSTRAINT [FK_PilaEstadoBloque_pebIndicePlanilla]
GO
ALTER TABLE [PilaEstadoBloqueOF]  WITH CHECK ADD  CONSTRAINT [FK_PilaEstadoBloqueOF_peoIndicePlanillaOF] FOREIGN KEY([peoIndicePlanillaOF])
REFERENCES [PilaIndicePlanillaOF] ([pioId])
GO
ALTER TABLE [PilaEstadoBloqueOF] CHECK CONSTRAINT [FK_PilaEstadoBloqueOF_peoIndicePlanillaOF]
GO
ALTER TABLE [PilaIndiceCorreccionPlanilla]  WITH CHECK ADD  CONSTRAINT [FK_PilaIndiceCorreccionPlanilla_picIndicePlanillaAfectada] FOREIGN KEY([picIndicePlanillaAfectada])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [PilaIndiceCorreccionPlanilla] CHECK CONSTRAINT [FK_PilaIndiceCorreccionPlanilla_picIndicePlanillaAfectada]
GO
ALTER TABLE [PilaIndiceCorreccionPlanilla]  WITH CHECK ADD  CONSTRAINT [FK_PilaIndiceCorreccionPlanilla_picIndicePlanillaCargado] FOREIGN KEY([picIndicePlanillaCargado])
REFERENCES [PilaIndicePlanilla] ([pipId])
GO
ALTER TABLE [PilaIndiceCorreccionPlanilla] CHECK CONSTRAINT [FK_PilaIndiceCorreccionPlanilla_picIndicePlanillaCargado]
GO
ALTER TABLE [PilaNormatividadFechaVencimiento]  WITH CHECK ADD  CONSTRAINT [FK_PilaNormatividadFechaVencimiento_pfvClasificacionAportante] FOREIGN KEY([pfvClasificacionAportante])
REFERENCES [PilaClasificacionAportante] ([pcaId])
GO
ALTER TABLE [PilaNormatividadFechaVencimiento] CHECK CONSTRAINT [FK_PilaNormatividadFechaVencimiento_pfvClasificacionAportante]
GO
ALTER TABLE [ProcessorDefinition]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorDefinition_processorCatalog_id] FOREIGN KEY([processorCatalog_id])
REFERENCES [ProcessorCatalog] ([id])
GO
ALTER TABLE [ProcessorDefinition] CHECK CONSTRAINT [FK_ProcessorDefinition_processorCatalog_id]
GO
ALTER TABLE [ProcessorParameter]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorParameter_processorCatalog_id] FOREIGN KEY([processorCatalog_id])
REFERENCES [ProcessorCatalog] ([id])
GO
ALTER TABLE [ProcessorParameter] CHECK CONSTRAINT [FK_ProcessorParameter_processorCatalog_id]
GO
ALTER TABLE [ProcessorParamValue]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorParamValue_processorDefinition_id] FOREIGN KEY([processorDefinition_id])
REFERENCES [ProcessorDefinition] ([id])
GO
ALTER TABLE [ProcessorParamValue] CHECK CONSTRAINT [FK_ProcessorParamValue_processorDefinition_id]
GO
ALTER TABLE [ProcessorParamValue]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorParamValue_processorParameter_id] FOREIGN KEY([processorParameter_id])
REFERENCES [ProcessorParameter] ([id])
GO
ALTER TABLE [ProcessorParamValue] CHECK CONSTRAINT [FK_ProcessorParamValue_processorParameter_id]
GO
ALTER TABLE [ProductoNoConforme]  WITH CHECK ADD  CONSTRAINT [FK_ProductoNoConforme_pncBeneficiario] FOREIGN KEY([pncBeneficiario])
REFERENCES [Beneficiario] ([benId])
GO
ALTER TABLE [ProductoNoConforme] CHECK CONSTRAINT [FK_ProductoNoConforme_pncBeneficiario]
GO
ALTER TABLE [ProductoNoConforme]  WITH CHECK ADD  CONSTRAINT [FK_ProductoNoConforme_pncSolicitud] FOREIGN KEY([pncSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [ProductoNoConforme] CHECK CONSTRAINT [FK_ProductoNoConforme_pncSolicitud]
GO
ALTER TABLE [RegistroPilaNovedad]  WITH CHECK ADD  CONSTRAINT [FK_RegistroPilaNovedad_rpnGlosaNovedad] FOREIGN KEY([rpnGlosaNovedad])
REFERENCES [GlosaComentarioNovedad] ([gcnId])
GO
ALTER TABLE [RegistroPilaNovedad] CHECK CONSTRAINT [FK_RegistroPilaNovedad_rpnGlosaNovedad]
GO
ALTER TABLE [RegistroPilaNovedad]  WITH CHECK ADD  CONSTRAINT [FK_RegistroPilaNovedad_rpnRegistroAporteDetallado] FOREIGN KEY([rpnRegistroAporteDetallado])
REFERENCES [AporteDetallado] ([apdId])
GO
ALTER TABLE [RegistroPilaNovedad] CHECK CONSTRAINT [FK_RegistroPilaNovedad_rpnRegistroAporteDetallado]
GO
ALTER TABLE [RequisitoCajaClasificacion]  WITH CHECK ADD  CONSTRAINT [FK_RequisitoCajaClasificacion_rtsCajaCompensacion] FOREIGN KEY([rtsCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [RequisitoCajaClasificacion] CHECK CONSTRAINT [FK_RequisitoCajaClasificacion_rtsCajaCompensacion]
GO
ALTER TABLE [RequisitoCajaClasificacion]  WITH CHECK ADD  CONSTRAINT [FK_RequisitoTipoSolicitante_rtsRequisito] FOREIGN KEY([rtsRequisito])
REFERENCES [Requisito] ([reqId])
GO
ALTER TABLE [RequisitoCajaClasificacion] CHECK CONSTRAINT [FK_RequisitoTipoSolicitante_rtsRequisito]
GO
ALTER TABLE [RolAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_RolAfiliado_roaAfiliado] FOREIGN KEY([roaAfiliado])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [RolAfiliado] CHECK CONSTRAINT [FK_RolAfiliado_roaAfiliado]
GO
ALTER TABLE [RolAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_RolAfiliado_roaEmpleador] FOREIGN KEY([roaEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [RolAfiliado] CHECK CONSTRAINT [FK_RolAfiliado_roaEmpleador]
GO
ALTER TABLE [RolAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_RolAfiliado_roaPagadorAportes] FOREIGN KEY([roaPagadorAportes])
REFERENCES [EntidadPagadora] ([epaId])
GO
ALTER TABLE [RolAfiliado] CHECK CONSTRAINT [FK_RolAfiliado_roaPagadorAportes]
GO
ALTER TABLE [RolAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_RolAfiliado_roaPagadorPension] FOREIGN KEY([roaPagadorPension])
REFERENCES [AFP] ([afpId])
GO
ALTER TABLE [RolAfiliado] CHECK CONSTRAINT [FK_RolAfiliado_roaPagadorPension]
GO
ALTER TABLE [RolAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_RolAfiliado_roaSucursalEmpleador] FOREIGN KEY([roaSucursalEmpleador])
REFERENCES [SucursalEmpresa] ([sueId])
GO
ALTER TABLE [RolAfiliado] CHECK CONSTRAINT [FK_RolAfiliado_roaSucursalEmpleador]
GO
ALTER TABLE [RolContactoEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_RolContactoEmpleador_rceEmpleador] FOREIGN KEY([rceEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [RolContactoEmpleador] CHECK CONSTRAINT [FK_RolContactoEmpleador_rceEmpleador]
GO
ALTER TABLE [RolContactoEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_RolContactoEmpleador_rcePersona] FOREIGN KEY([rcePersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [RolContactoEmpleador] CHECK CONSTRAINT [FK_RolContactoEmpleador_rcePersona]
GO
ALTER TABLE [RolContactoEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_RolContactoEmpleador_rceUbicacion] FOREIGN KEY([rceUbicacion])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [RolContactoEmpleador] CHECK CONSTRAINT [FK_RolContactoEmpleador_rceUbicacion]
GO
ALTER TABLE [SocioEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SocioEmpleador_semConyugue] FOREIGN KEY([semConyugue])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [SocioEmpleador] CHECK CONSTRAINT [FK_SocioEmpleador_semConyugue]
GO
ALTER TABLE [SocioEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SocioEmpleador_semEmpleador] FOREIGN KEY([semEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [SocioEmpleador] CHECK CONSTRAINT [FK_SocioEmpleador_semEmpleador]
GO
ALTER TABLE [SocioEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SocioEmpleador_semPersona] FOREIGN KEY([semPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [SocioEmpleador] CHECK CONSTRAINT [FK_SocioEmpleador_semPersona]
GO
ALTER TABLE [Solicitud]  WITH CHECK ADD  CONSTRAINT [FK_Solicitud_solCajaCorrespondencia] FOREIGN KEY([solCajaCorrespondencia])
REFERENCES [CajaCorrespondencia] ([ccoId])
GO
ALTER TABLE [Solicitud] CHECK CONSTRAINT [FK_Solicitud_solCajaCorrespondencia]
GO
ALTER TABLE [Solicitud]  WITH CHECK ADD  CONSTRAINT [FK_Solicitud_solCargaAfiliacionMultipleEmpleador] FOREIGN KEY([solCargaAfiliacionMultipleEmpleador])
REFERENCES [CargueAfiliacionMultiple] ([camId])
GO
ALTER TABLE [Solicitud] CHECK CONSTRAINT [FK_Solicitud_solCargaAfiliacionMultipleEmpleador]
GO
ALTER TABLE [SolicitudAfiliaciEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saeEmpleador] FOREIGN KEY([saeEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [SolicitudAfiliaciEmpleador] CHECK CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saeEmpleador]
GO
ALTER TABLE [SolicitudAfiliaciEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saeSolicitudGlobal] FOREIGN KEY([saeSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudAfiliaciEmpleador] CHECK CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saeSolicitudGlobal]
GO
ALTER TABLE [SolicitudAfiliacionPersona]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliacionPersona_sapRolAfiliado] FOREIGN KEY([sapRolAfiliado])
REFERENCES [RolAfiliado] ([roaId])
GO
ALTER TABLE [SolicitudAfiliacionPersona] CHECK CONSTRAINT [FK_SolicitudAfiliacionPersona_sapRolAfiliado]
GO
ALTER TABLE [SolicitudAfiliacionPersona]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliacionPersona_sapSolicitudGlobal] FOREIGN KEY([sapSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudAfiliacionPersona] CHECK CONSTRAINT [FK_SolicitudAfiliacionPersona_sapSolicitudGlobal]
GO
ALTER TABLE [SolicitudAsociacionPersonaEntidadPagadora]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAsociacionPersonaEntidadPagadora_soaRolAfiliado] FOREIGN KEY([soaRolAfiliado])
REFERENCES [RolAfiliado] ([roaId])
GO
ALTER TABLE [SolicitudAsociacionPersonaEntidadPagadora] CHECK CONSTRAINT [FK_SolicitudAsociacionPersonaEntidadPagadora_soaRolAfiliado]
GO
ALTER TABLE [SolicitudAsociacionPersonaEntidadPagadora]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAsociacionPersonaEntidadPagadora_soaSolicitudGlobal] FOREIGN KEY([soaSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudAsociacionPersonaEntidadPagadora] CHECK CONSTRAINT [FK_SolicitudAsociacionPersonaEntidadPagadora_soaSolicitudGlobal]
GO
ALTER TABLE [SolicitudNovedad]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedad_snoNovedad] FOREIGN KEY([snoNovedad])
REFERENCES [Novedad] ([novId])
GO
ALTER TABLE [SolicitudNovedad] CHECK CONSTRAINT [FK_SolicitudNovedad_snoNovedad]
GO
ALTER TABLE [SolicitudNovedad]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedad_snoSolicitudGlobal] FOREIGN KEY([snoSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudNovedad] CHECK CONSTRAINT [FK_SolicitudNovedad_snoSolicitudGlobal]
GO
ALTER TABLE [SolicitudNovedadAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadAfiliado_snaIdAfiliado] FOREIGN KEY([snaIdAfiliado])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [SolicitudNovedadAfiliado] CHECK CONSTRAINT [FK_SolicitudNovedadAfiliado_snaIdAfiliado]
GO
ALTER TABLE [SolicitudNovedadAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadAfiliado_snaIdSolicitudNovedad] FOREIGN KEY([snaIdSolicitudNovedad])
REFERENCES [SolicitudNovedad] ([snoId])
GO
ALTER TABLE [SolicitudNovedadAfiliado] CHECK CONSTRAINT [FK_SolicitudNovedadAfiliado_snaIdSolicitudNovedad]
GO
ALTER TABLE [SolicitudNovedadEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadEmpleador_sneIdEmpleador] FOREIGN KEY([sneIdEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [SolicitudNovedadEmpleador] CHECK CONSTRAINT [FK_SolicitudNovedadEmpleador_sneIdEmpleador]
GO
ALTER TABLE [SolicitudNovedadEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadEmpleador_sneIdSolicitudNovedad] FOREIGN KEY([sneIdSolicitudNovedad])
REFERENCES [SolicitudNovedad] ([snoId])
GO
ALTER TABLE [SolicitudNovedadEmpleador] CHECK CONSTRAINT [FK_SolicitudNovedadEmpleador_sneIdSolicitudNovedad]
GO
ALTER TABLE [SucursalEmpresa]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpresa_sueCodigoCIIU] FOREIGN KEY([sueCodigoCIIU])
REFERENCES [CodigoCIIU] ([ciiId])
GO
ALTER TABLE [SucursalEmpresa] CHECK CONSTRAINT [FK_SucursalEmpresa_sueCodigoCIIU]
GO
ALTER TABLE [SucursalEmpresa]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpresa_sueEmpresa] FOREIGN KEY([sueEmpresa])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [SucursalEmpresa] CHECK CONSTRAINT [FK_SucursalEmpresa_sueEmpresa]
GO
ALTER TABLE [SucursalEmpresa]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpresa_sueMedioPagoSubsidioMonetario] FOREIGN KEY([sueMedioPagoSubsidioMonetario])
REFERENCES [ParametrizacionMedioDePago] ([pmpId])
GO
ALTER TABLE [SucursalEmpresa] CHECK CONSTRAINT [FK_SucursalEmpresa_sueMedioPagoSubsidioMonetario]
GO
ALTER TABLE [SucursalEmpresa]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpresa_sueUbicacion] FOREIGN KEY([sueUbicacion])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [SucursalEmpresa] CHECK CONSTRAINT [FK_SucursalEmpresa_sueUbicacion]
GO
ALTER TABLE [SucursaRolContactEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SucursaRolContactEmpleador_srcRolContactoEmpleador] FOREIGN KEY([srcRolContactoEmpleador])
REFERENCES [RolContactoEmpleador] ([rceId])
GO
ALTER TABLE [SucursaRolContactEmpleador] CHECK CONSTRAINT [FK_SucursaRolContactEmpleador_srcRolContactoEmpleador]
GO
ALTER TABLE [SucursaRolContactEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SucursaRolContactEmpleador_srcSucursalEmpleador] FOREIGN KEY([srcSucursalEmpleador])
REFERENCES [SucursalEmpresa] ([sueId])
GO
ALTER TABLE [SucursaRolContactEmpleador] CHECK CONSTRAINT [FK_SucursaRolContactEmpleador_srcSucursalEmpleador]
GO
ALTER TABLE [Tarjeta]  WITH CHECK ADD  CONSTRAINT [FK_Tarjeta_afiPersona] FOREIGN KEY([afiPersona])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [Tarjeta] CHECK CONSTRAINT [FK_Tarjeta_afiPersona]
GO
ALTER TABLE [Ubicacion]  WITH CHECK ADD  CONSTRAINT [FK_Ubicacion_ubiMunicipio] FOREIGN KEY([ubiMunicipio])
REFERENCES [Municipio] ([munId])
GO
ALTER TABLE [Ubicacion] CHECK CONSTRAINT [FK_Ubicacion_ubiMunicipio]
GO
ALTER TABLE [UbicacionEmpresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_ubeEmpresa] FOREIGN KEY([ubeEmpresa])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [UbicacionEmpresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_ubeEmpresa]
GO
ALTER TABLE [UbicacionEmpresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_ubeUbicacion] FOREIGN KEY([ubeUbicacion])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [UbicacionEmpresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_ubeUbicacion]
GO
ALTER TABLE [ValidatorDefinition]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorDefinition_fieldDefinition_id] FOREIGN KEY([fieldDefinition_id])
REFERENCES [FieldDefinitionLoad] ([id])
GO
ALTER TABLE [ValidatorDefinition] CHECK CONSTRAINT [FK_ValidatorDefinition_fieldDefinition_id]
GO
ALTER TABLE [ValidatorDefinition]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorDefinition_fileDefinitionLoad_id] FOREIGN KEY([fileDefinitionLoad_id])
REFERENCES [FileDefinitionLoad] ([id])
GO
ALTER TABLE [ValidatorDefinition] CHECK CONSTRAINT [FK_ValidatorDefinition_fileDefinitionLoad_id]
GO
ALTER TABLE [ValidatorDefinition]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorDefinition_lineDefinition_id] FOREIGN KEY([lineDefinition_id])
REFERENCES [LineDefinitionLoad] ([id])
GO
ALTER TABLE [ValidatorDefinition] CHECK CONSTRAINT [FK_ValidatorDefinition_lineDefinition_id]
GO
ALTER TABLE [ValidatorDefinition]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorDefinition_validatorCatalog_id] FOREIGN KEY([validatorCatalog_id])
REFERENCES [ValidatorCatalog] ([id])
GO
ALTER TABLE [ValidatorDefinition] CHECK CONSTRAINT [FK_ValidatorDefinition_validatorCatalog_id]
GO
ALTER TABLE [ValidatorParameter]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorParameter_validatorCatalog_id] FOREIGN KEY([validatorCatalog_id])
REFERENCES [ValidatorCatalog] ([id])
GO
ALTER TABLE [ValidatorParameter] CHECK CONSTRAINT [FK_ValidatorParameter_validatorCatalog_id]
GO
ALTER TABLE [ValidatorParamValue]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorParamValue_validatorDefinition_id] FOREIGN KEY([validatorDefinition_id])
REFERENCES [ValidatorDefinition] ([id])
GO
ALTER TABLE [ValidatorParamValue] CHECK CONSTRAINT [FK_ValidatorParamValue_validatorDefinition_id]
GO
ALTER TABLE [ValidatorParamValue]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorParamValue_validatorParameter_id] FOREIGN KEY([validatorParameter_id])
REFERENCES [ValidatorParameter] ([id])
GO
ALTER TABLE [ValidatorParamValue] CHECK CONSTRAINT [FK_ValidatorParamValue_validatorParameter_id]
GO
ALTER TABLE [VariableComunicado]  WITH CHECK ADD  CONSTRAINT [FK_VariableComunicado_vcoPlantillaComunicado] FOREIGN KEY([vcoPlantillaComunicado])
REFERENCES [PlantillaComunicado] ([pcoId])
GO
ALTER TABLE [VariableComunicado] CHECK CONSTRAINT [FK_VariableComunicado_vcoPlantillaComunicado]

CREATE SEQUENCE [hibernate_sequence] START WITH 1 INCREMENT BY 1 ;
GO
