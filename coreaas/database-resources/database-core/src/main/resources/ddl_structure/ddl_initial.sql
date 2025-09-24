--liquibase formatted sql

--changeset Heinsohn:01
--comment: structural creation base line mar 2018

CREATE SCHEMA [pila]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro1C](
	[pgcId] [bigint] NOT NULL,
	[accDiasLiquidacion] [bigint] NULL,
	[accHoraEjecucion] [datetime] NULL,
	[accLimiteEnvioDocumento] [bigint] NULL,
	[accVariableCalculo] [varchar](16) NULL,
	[accCantidadPeriodos] [bigint] NULL,
 CONSTRAINT [PK_AccionCobro1C_pgcId] PRIMARY KEY CLUSTERED 
(
	[pgcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro1D1E](
	[pgcId] [bigint] NOT NULL,
	[acdInicioDiasConteo] [varchar](13) NULL,
	[acdDiasTranscurridos] [bigint] NULL,
	[acdHoraEjecucion] [datetime] NULL,
	[acdLimiteEnvio] [bigint] NULL,
	[acdTipoCobro] [varchar](12) NULL,
 CONSTRAINT [PK_AccionCobro1D1E_pgcId] PRIMARY KEY CLUSTERED 
(
	[pgcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro1F](
	[pgcId] [bigint] NOT NULL,
	[abfAccionCobro1F] [bit] NULL,
	[abfDiasParametrizados] [smallint] NULL,
	[abfSiguienteAccion] [varchar](29) NULL,
 CONSTRAINT [PK_AccionCobro1F_pgcId] PRIMARY KEY CLUSTERED 
(
	[pgcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro2C](
	[pgcId] [bigint] NOT NULL,
	[aocAnexoLiquidacion] [bit] NULL,
	[aocDiasEjecucion] [bigint] NULL,
	[aocHoraEjecucion] [datetime] NULL,
 CONSTRAINT [PK_AccionCobro2C_pgcId] PRIMARY KEY CLUSTERED 
(
	[pgcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro2D](
	[pgcId] [bigint] NOT NULL,
	[aodInicioDiasConteo] [varchar](13) NULL,
	[aodDiasTranscurridos] [bigint] NULL,
	[aodHoraEjecucion] [datetime] NULL,
 CONSTRAINT [PK_AccionCobro2D_pgcId] PRIMARY KEY CLUSTERED 
(
	[pgcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro2E](
	[aceId] [bigint] IDENTITY(1,1) NOT NULL,
	[aceInicioDiasConteo] [varchar](13) NULL,
	[aceDiasTranscurridos] [bigint] NULL,
	[aceHoraEjecucion] [datetime] NULL,
 CONSTRAINT [PK_AccionCobro2E_aceId] PRIMARY KEY CLUSTERED 
(
	[aceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro2F2G](
	[pgcId] [bigint] NOT NULL,
	[aofInicioDiasConteo] [varchar](13) NULL,
	[aofDiasTranscurridos] [bigint] NULL,
	[aofHoraEjecucion] [datetime] NULL,
	[aofLimiteEnvio] [bigint] NULL,
	[aofTipoCobro] [varchar](12) NULL,
 CONSTRAINT [PK_AccionCobro2F2G_pgcId] PRIMARY KEY CLUSTERED 
(
	[pgcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro2H](
	[pgcId] [bigint] NOT NULL,
	[achAccionCobro2H] [bit] NULL,
	[achDiasRegistro] [bigint] NULL,
	[achDiasParametrizados] [bigint] NULL,
	[achSiguienteAccion] [varchar](29) NULL,
 CONSTRAINT [PK_AccionCobro2H_pgcId] PRIMARY KEY CLUSTERED 
(
	[pgcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobroA](
	[pgcId] [bigint] NOT NULL,
	[acaSuspensionAutomatica] [bit] NULL,
	[acaDiasLimitePago] [bigint] NULL,
	[acaFechaHoraEjecucion] [datetime] NULL,
	[acaDiasLimiteEnvioComunicado] [bigint] NULL,
	[acaMetodo] [varchar](8) NULL,
 CONSTRAINT [PK_AccionCobroA_pgcId] PRIMARY KEY CLUSTERED 
(
	[pgcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobroB](
	[pgcId] [bigint] NOT NULL,
	[acbDiasGeneracionAviso] [bigint] NULL,
	[acbHoraEjecucion] [datetime] NULL,
	[acbLimiteEnvioComunicado] [bigint] NULL,
	[acbMetodo] [varchar](8) NULL,
 CONSTRAINT [PK_AccionCobroB_pgcId] PRIMARY KEY CLUSTERED 
(
	[pgcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ActaAsignacionFovis](
	[aafId] [bigint] IDENTITY(1,1) NOT NULL,
	[aafSolicitudAsignacion] [bigint] NOT NULL,
	[aafIdentificadorDocumentoActa] [varchar](255) NULL,
	[aafIdentificadorDocumentoConsolidado] [varchar](255) NULL,
	[aafNumeroResolucion] [varchar](20) NULL,
	[aafNumeroOficio] [varchar](20) NULL,
	[aafAnoResolucion] [varchar](4) NULL,
	[aafFechaResolucion] [datetime] NULL,
	[aafFechaOficio] [datetime] NULL,
	[aafNombreResponsable1] [varchar](50) NULL,
	[aafCargoResponsable1] [varchar](50) NULL,
	[aafNombreResponsable2] [varchar](50) NULL,
	[aafCargoResponsable2] [varchar](50) NULL,
	[aafNombreResponsable3] [varchar](50) NULL,
	[aafCargoResponsable3] [varchar](50) NULL,
	[aafFechaConfirmacion] [datetime] NULL,
	[aafFechaCorte] [datetime] NULL,
	[aafInicioVigencia] [datetime] NULL,
	[aafFinVigencia] [datetime] NULL,
	[aafFechaActaAsignacionFovis] [datetime] NULL,
	[aafFechaPublicacion] [datetime] NULL,
 CONSTRAINT [PK_ActaAsignacionFovis_aafId] PRIMARY KEY CLUSTERED 
(
	[aafId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ActividadCartera](
	[acrId] [bigint] IDENTITY(1,1) NOT NULL,
	[acrActividadCartera] [varchar](42) NOT NULL,
	[acrResultadoCartera] [varchar](33) NULL,
	[acrComentarios] [varchar](500) NULL,
	[acrCicloAportante] [bigint] NOT NULL,
	[acrFecha] [datetime] NOT NULL,
	[acrFechaCompromiso] [date] NULL,
 CONSTRAINT [PK_ActividadCartera_acrId] PRIMARY KEY CLUSTERED 
(
	[acrId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ActividadDocumento](
	[adoId] [bigint] IDENTITY(1,1) NOT NULL,
	[adoIdentificadorDocumento] [varchar](255) NOT NULL,
	[adoTipoDocumento] [varchar](12) NOT NULL,
	[adoActividadCartera] [bigint] NOT NULL,
	[adoDocumentoSoporte] [bigint] NULL,
 CONSTRAINT [PK_ActividadDocumento_adoId] PRIMARY KEY CLUSTERED 
(
	[adoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AdministradorSubsidio](
	[asuId] [bigint] IDENTITY(1,1) NOT NULL,
	[asuPersona] [bigint] NOT NULL,
 CONSTRAINT [PK_AdministradorSubsidio_asuId] PRIMARY KEY CLUSTERED 
(
	[asuId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AdminSubsidioGrupo](
	[asgId] [bigint] IDENTITY(1,1) NOT NULL,
	[asgGrupoFamiliar] [bigint] NOT NULL,
	[asgAdministradorSubsidio] [bigint] NOT NULL,
	[asgMedioDePago] [bigint] NULL,
	[asgAfiliadoEsAdminSubsidio] [bit] NOT NULL,
	[asgMedioPagoActivo] [bit] NOT NULL,
	[asgRelacionGrupoFamiliar] [smallint] NULL,
 CONSTRAINT [PK_AdminSubsidioGrupo_asgId] PRIMARY KEY CLUSTERED 
(
	[asgId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Afiliado](
	[afiId] [bigint] IDENTITY(1,1) NOT NULL,
	[afiPersona] [bigint] NULL,
	[afiPignoracionSubsidio] [bit] NULL,
	[afiCesionSubsidio] [bit] NULL,
	[afiRetencionSubsidio] [bit] NULL,
	[afiServicioSinAfiliacion] [bit] NULL,
	[afiCausaSinAfiliacion] [varchar](20) NULL,
	[afiFechaInicioServiciosSinAfiliacion] [date] NULL,
	[afifechaFinServicioSinAfiliacion] [date] NULL,
 CONSTRAINT [PK_Afiliado_afiId] PRIMARY KEY CLUSTERED 
(
	[afiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_Afiliado_afiPersona] UNIQUE NONCLUSTERED 
(
	[afiPersona] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AFP](
	[afpId] [smallint] IDENTITY(1,1) NOT NULL,
	[afpNombre] [varchar](150) NOT NULL,
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AgendaCartera](
	[agrId] [bigint] IDENTITY(1,1) NOT NULL,
	[agrVisitaAgenda] [varchar](13) NULL,
	[agrFecha] [date] NOT NULL,
	[agrHorario] [datetime] NOT NULL,
	[agrContacto] [varchar](255) NOT NULL,
	[agrTelefono] [varchar](255) NULL,
	[agrCicloAportante] [bigint] NOT NULL,
 CONSTRAINT [PK_AgendaCartera_agrId] PRIMARY KEY CLUSTERED 
(
	[agrId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AhorroPrevio](
	[ahpId] [bigint] IDENTITY(1,1) NOT NULL,
	[ahpNombreAhorro] [varchar](65) NULL,
	[ahpEntidad] [varchar](50) NULL,
	[ahpFechaInicial] [date] NULL,
	[ahpValor] [numeric](19, 5) NULL,
	[ahpFechaInmovilizacion] [date] NULL,
	[ahpFechaAdquisicion] [date] NULL,
	[ahpPostulacionFOVIS] [bigint] NOT NULL,
	[ahpAhorroMovilizado] [bit] NULL,
 CONSTRAINT [PK_AhorroPrevio_ahpId] PRIMARY KEY CLUSTERED 
(
	[ahpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AplicacionValidacionSubsidio](
	[avsId] [bigint] IDENTITY(1,1) NOT NULL,
	[avsConjuntoValidacionSubsidio] [bigint] NOT NULL,
	[avsSolicitudLiquidacionSubsidio] [bigint] NOT NULL,
	[avsEsValidable] [bit] NOT NULL,
 CONSTRAINT [PK_AplicacionValidacionSubsidio_avsId] PRIMARY KEY CLUSTERED 
(
	[avsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AporteDetallado](
	[apdId] [bigint] IDENTITY(1,1) NOT NULL,
	[apdAporteGeneral] [bigint] NULL,
	[apdDiasCotizados] [smallint] NULL,
	[apdHorasLaboradas] [smallint] NULL,
	[apdSalarioBasico] [numeric](19, 5) NULL,
	[apdValorIBC] [numeric](19, 5) NULL,
	[apdValorIntMora] [numeric](19, 5) NULL,
	[apdTarifa] [numeric](5, 5) NULL,
	[apdAporteObligatorio] [numeric](19, 5) NULL,
	[apdValorSaldoAporte] [numeric](19, 5) NULL,
	[apdCorrecciones] [varchar](400) NULL,
	[apdEstadoAporteRecaudo] [varchar](50) NULL,
	[apdEstadoAporteAjuste] [varchar](50) NULL,
	[apdEstadoRegistroAporte] [varchar](50) NULL,
	[apdSalarioIntegral] [bit] NULL,
	[apdMunicipioLaboral] [smallint] NULL,
	[apdDepartamentoLaboral] [smallint] NULL,
	[apdRegistroDetallado] [bigint] NOT NULL,
	[apdTipoCotizante] [varchar](100) NULL,
	[apdEstadoCotizante] [varchar](60) NULL,
	[apdEstadoAporteCotizante] [varchar](50) NULL,
	[apdEstadoRegistroAporteCotizante] [varchar](40) NULL,
	[apdPersona] [bigint] NULL,
	[apdUsuarioAprobadorAporte] [varchar](50) NOT NULL,
	[apdEstadoRegistroAporteArchivo] [varchar](60) NOT NULL,
	[apdCodSucursal] [varchar](10) NULL,
	[apdNomSucursal] [varchar](100) NULL,
	[apdFechaMovimiento] [date] NULL,
	[apdFechaCreacion] [date] NULL,
	[apdFormaReconocimientoAporte] [varchar](75) NULL,
 CONSTRAINT [PK_AporteDetallado_apdId] PRIMARY KEY CLUSTERED 
(
	[apdId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AporteGeneral](
	[apgId] [bigint] IDENTITY(1,1) NOT NULL,
	[apgPeriodoAporte] [varchar](7) NULL,
	[apgValTotalApoObligatorio] [numeric](19, 5) NULL,
	[apgValorIntMora] [numeric](19, 5) NULL,
	[apgFechaRecaudo] [date] NULL,
	[apgFechaProcesamiento] [datetime] NULL,
	[apgCodEntidadFinanciera] [smallint] NULL,
	[apgOperadorInformacion] [bigint] NULL,
	[apgModalidadPlanilla] [varchar](40) NULL,
	[apgModalidadRecaudoAporte] [varchar](40) NULL,
	[apgApoConDetalle] [bit] NULL,
	[apgNumeroCuenta] [varchar](17) NULL,
	[apgRegistroGeneral] [bigint] NOT NULL,
	[apgPersona] [bigint] NULL,
	[apgEmpresa] [bigint] NULL,
	[apgSucursalEmpresa] [bigint] NULL,
	[apgEstadoAportante] [varchar](50) NULL,
	[apgEstadoAporteAportante] [varchar](40) NULL,
	[apgEstadoRegistroAporteAportante] [varchar](30) NULL,
	[apgPagadorPorTerceros] [bit] NULL,
	[apgTipoSolicitante] [varchar](13) NULL,
	[apgOrigenAporte] [varchar](26) NULL,
	[apgCajaCompensacion] [int] NULL,
	[apgEmailAportante] [varchar](255) NULL,
	[apgEmpresaTramitadoraAporte] [bigint] NULL,
	[apgFechaReconocimiento] [datetime] NULL,
	[apgFormaReconocimientoAporte] [varchar](75) NULL,
 CONSTRAINT [PK_AporteGeneral_apgId] PRIMARY KEY CLUSTERED 
(
	[apgId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ArchivoConsumosAnibol](
	[acnId] [bigint] IDENTITY(1,1) NOT NULL,
	[acnNombreArchivo] [varchar](15) NOT NULL,
	[acnIdentificadorDocumento] [varchar](255) NULL,
	[acnVersionDocumento] [varchar](4) NULL,
	[acnFechaHoraCargue] [datetime] NOT NULL,
	[acnUsuarioCargue] [varchar](50) NOT NULL,
	[acnFechaHoraProcesamiento] [datetime] NULL,
	[acnUsuarioProcesamiento] [varchar](50) NULL,
	[acnTipoCargue] [varchar](10) NOT NULL,
	[acnEstadoArchivo] [varchar](29) NULL,
	[acnResultadoValidacionEstructura] [varchar](20) NULL,
	[acnResultadoValidacionContenido] [varchar](20) NULL,
	[acnTipoInconsistenciaArchivo] [varchar](40) NULL,
	[acnArchivoNotificado] [smallint] NULL,
 CONSTRAINT [PK_ArchivoConsumosAnibol_acnId] PRIMARY KEY CLUSTERED 
(
	[acnId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ArchivoLiquidacionSubsidio](
	[alsId] [bigint] IDENTITY(1,1) NOT NULL,
	[alsIdentificadorECMLiquidacion] [varchar](255) NULL,
	[alsIdentificadorECMPersonasSinDerecho] [varchar](255) NULL,
	[alsIdentificadorECMConsignacionesBancos] [varchar](255) NULL,
	[alsIdentificadorECMPagosJudiciales] [varchar](255) NULL,
	[alsSolicitudLiquidacionSubsidio] [bigint] NOT NULL,
 CONSTRAINT [PK_ArchivoLiquidacionSubsidio_alsId] PRIMARY KEY CLUSTERED 
(
	[alsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ArchivoRetiroTerceroPagador](
	[arrId] [bigint] IDENTITY(1,1) NOT NULL,
	[arrIdentificadorDocumento ] [varchar](255) NOT NULL,
	[arrVersionDocumento] [varchar](4) NOT NULL,
	[arrNombreDocumento] [varchar](255) NOT NULL,
	[arrFechaHoraProcesamiento] [datetime] NOT NULL,
	[arrUsuarioProcesamiento] [varchar](50) NOT NULL,
	[arrEstado] [varchar](30) NULL,
 CONSTRAINT [PK_ArchivoRetiroTerceroPagador_arrId] PRIMARY KEY CLUSTERED 
(
	[arrId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ARL](
	[arlId] [smallint] IDENTITY(1,1) NOT NULL,
	[arlNombre] [varchar](25) NOT NULL,
 CONSTRAINT [PK_ARL_arlId] PRIMARY KEY CLUSTERED 
(
	[arlId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Banco](
	[banId] [bigint] IDENTITY(1,1) NOT NULL,
	[banCodigoPILA] [varchar](4) NOT NULL,
	[banNombre] [varchar](255) NOT NULL,
	[banMedioPago] [bit] NULL,
	[banCodigo] [varchar](6) NULL CONSTRAINT [DF_Banco_banCodigo]  DEFAULT ('000000'),
 CONSTRAINT [PK_Banco_banId] PRIMARY KEY CLUSTERED 
(
	[banId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Beneficiario](
	[benId] [bigint] IDENTITY(1,1) NOT NULL,
	[benEstadoBeneficiarioAfiliado] [varchar](20) NULL,
	[benEstudianteTrabajoDesarrolloHumano] [bit] NULL,
	[benFechaAfiliacion] [date] NULL,
	[benTipoBeneficiario] [varchar](30) NOT NULL,
	[benGrupoFamiliar] [bigint] NULL,
	[benPersona] [bigint] NOT NULL,
	[benAfiliado] [bigint] NOT NULL,
	[benGradoAcademico] [smallint] NULL,
	[benMotivoDesafiliacion] [varchar](70) NULL,
	[benFechaRetiro] [date] NULL,
	[benFechaInicioSociedadConyugal] [date] NULL,
	[benFechaFinSociedadConyugal] [date] NULL,
	[benRolAfiliado] [bigint] NULL,
	[benBeneficiarioDetalle] [bigint] NULL,
 CONSTRAINT [PK_Beneficiario_benId] PRIMARY KEY CLUSTERED 
(
	[benId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [BeneficiarioDetalle](
	[bedId] [bigint] IDENTITY(1,1) NOT NULL,
	[bedSalarioMensual] [numeric](19, 5) NULL,
	[bedLabora] [bit] NULL,
	[bedPersonaDetalle] [bigint] NOT NULL,
	[bedCertificadoEscolaridad] [bit] NULL,
	[bedFechaRecepcionCertificadoEscolar] [date] NULL,
	[bedFechaVencimientoCertificadoEscolar] [date] NULL,
 CONSTRAINT [PK_BeneficiarioDetalle_bedId] PRIMARY KEY CLUSTERED 
(
	[bedId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Beneficio](
	[befId] [bigint] IDENTITY(1,1) NOT NULL,
	[befTipoBeneficio] [varchar](16) NOT NULL,
	[befVigenciaFiscal] [bit] NOT NULL,
	[befFechaVigenciaInicio] [date] NULL,
	[befFechaVigenciaFin] [date] NULL,
 CONSTRAINT [PK_Beneficio_befId] PRIMARY KEY CLUSTERED 
(
	[befId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [BeneficioEmpleador](
	[bemId] [bigint] IDENTITY(1,1) NOT NULL,
	[bemBeneficioActivo] [bit] NULL,
	[bemFechaVinculacion] [date] NULL,
	[bemFechaDesvinculacion] [date] NULL,
	[bemEmpleador] [bigint] NOT NULL,
	[bemBeneficio] [bigint] NOT NULL,
	[bemMotivoInactivacion] [varchar](50) NULL,
 CONSTRAINT [PK_Beneficio_bemId] PRIMARY KEY CLUSTERED 
(
	[bemId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [BitacoraCartera](
	[bcaId] [bigint] IDENTITY(1,1) NOT NULL,
	[bcaFecha] [date] NULL,
	[bcaActividad] [varchar](22) NULL,
	[bcaMedio] [varchar](16) NULL,
	[bcaResultado] [varchar](33) NULL,
	[bcaUsuario] [varchar](255) NULL,
	[bcaPersona] [bigint] NOT NULL,
	[bcaTipoSolicitante] [varchar](13) NOT NULL,
 CONSTRAINT [PK_BitacoraCartera_bcaId] PRIMARY KEY CLUSTERED 
(
	[bcaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CajaCompensacion](
	[ccfId] [int] IDENTITY(1,1) NOT NULL,
	[ccfHabilitado] [bit] NOT NULL,
	[ccfMetodoGeneracionEtiquetas] [varchar](150) NOT NULL,
	[ccfNombre] [varchar](100) NOT NULL,
	[ccfSocioAsopagos] [bit] NOT NULL,
	[ccfDepartamento] [smallint] NOT NULL,
	[ccfCodigo] [varchar](5) NOT NULL,
	[ccfCodigoRedeban] [int] NULL,
 CONSTRAINT [PK_CajaCompensacion_ccfId] PRIMARY KEY CLUSTERED 
(
	[ccfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CampoArchivoConsumosAnibol](
	[caaId] [bigint] IDENTITY(1,1) NOT NULL,
	[caaRegistroArchivoConsumosAnibol] [bigint] NOT NULL,
	[caaInconsistenciaContenidoDetectada] [varchar](30) NOT NULL,
	[caaValorCampoArchivo] [varchar](30) NOT NULL,
 CONSTRAINT [PK_CampoArchivoConsumosAnibol_caaId] PRIMARY KEY CLUSTERED 
(
	[caaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CampoArchivoRetiroTerceroPagador](
	[carId] [bigint] IDENTITY(1,1) NOT NULL,
	[carDescripcionCampo] [varchar](50) NOT NULL,
	[carValorCampoArchivo] [varchar](50) NOT NULL,
	[carValorCampoCuentaAdminSubsidio] [varchar](50) NOT NULL,
	[carInconsistencia] [varchar](80) NOT NULL,
	[carRegistroArchivoRetiroTerceroPagador] [bigint] NOT NULL,
 CONSTRAINT [PK_CampoCargueArchivoRetiroTerceroPagador_carId] PRIMARY KEY CLUSTERED 
(
	[carId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoActualizacion](
	[caaId] [bigint] IDENTITY(1,1) NOT NULL,
	[caaNombreArchivo] [varchar](50) NOT NULL,
	[caaFechaProcesamiento] [datetime] NULL,
	[caaCodigoIdentificadorECM] [varchar](255) NOT NULL,
	[caaEstado] [varchar](40) NOT NULL,
	[caaFechaAceptacion] [datetime] NULL,
 CONSTRAINT [PK_CargueArchivoActualizacion_caaId] PRIMARY KEY CLUSTERED 
(
	[caaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovis](
	[cacId] [bigint] IDENTITY(1,1) NOT NULL,
	[cacCodigoIdentificadorECM] [varchar](255) NOT NULL,
	[cacNombreArchivo] [varchar](50) NOT NULL,
	[cacFechaCargue] [datetime] NOT NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovis_cacId] PRIMARY KEY CLUSTERED 
(
	[cacId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovisAfiliado](
	[cfaId] [bigint] NOT NULL,
	[cfaCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfaNitEntidad] [varchar](16) NULL,
	[cfaNombreEntidad] [varchar](100) NULL,
	[cfaIdentificacion] [varchar](16) NULL,
	[cfaApellidos] [varchar](100) NULL,
	[cfaNombres] [varchar](100) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisAfiliado_cfaId] PRIMARY KEY CLUSTERED 
(
	[cfaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovisBeneficiario](
	[cfbId] [bigint] NOT NULL,
	[cfbCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfbNitEntidad] [varchar](16) NULL,
	[cfbNombreEntidad] [varchar](100) NULL,
	[cfbIdentificacion] [varchar](16) NULL,
	[cfbApellidos] [varchar](100) NULL,
	[cfbNombres] [varchar](100) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisBeneficiario_cfbId] PRIMARY KEY CLUSTERED 
(
	[cfbId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovisCatAnt](
	[cfnId] [bigint] NOT NULL,
	[cfnCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfnNitEntidad] [varchar](16) NULL,
	[cfnNombreEntidad] [varchar](100) NULL,
	[cfnIdentificacion] [varchar](16) NULL,
	[cfnApellidos] [varchar](100) NULL,
	[cfnNombres] [varchar](100) NULL,
	[cfnCedulaCatastral] [varchar](50) NULL,
	[cfnDireccionInmueble] [varchar](300) NULL,
	[cfnMatriculaInmobiliaria] [varchar](50) NULL,
	[cfnDepartamento] [varchar](100) NULL,
	[cfnMunicipio] [varchar](100) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisCatAnt_cfnId] PRIMARY KEY CLUSTERED 
(
	[cfnId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovisCatBog](
	[cfoId] [bigint] NOT NULL,
	[cfoCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfoNitEntidad] [varchar](16) NULL,
	[cfoNombreEntidad] [varchar](100) NULL,
	[cfoIdentificacion] [varchar](16) NULL,
	[cfoCedulaCatastral] [varchar](50) NULL,
	[cfoDireccion] [varchar](300) NULL,
	[cfoMatriculaInmobiliaria] [varchar](50) NULL,
	[cfoDepartamento] [varchar](100) NULL,
	[cfoMunicipio] [varchar](100) NULL,
	[cfoApellidosNombres] [varchar](200) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisBeneficiario_cfoId] PRIMARY KEY CLUSTERED 
(
	[cfoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovisCatCali](
	[cflId] [bigint] NOT NULL,
	[cflCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cflNitEntidad] [varchar](16) NULL,
	[cflNombreEntidad] [varchar](100) NULL,
	[cflIdentificacion] [varchar](16) NULL,
	[cflApellidosNombres] [varchar](200) NULL,
	[cflMatriculaInmobiliaria] [varchar](50) NULL,
	[cflDepartamento] [varchar](100) NULL,
	[cflMunicipio] [varchar](100) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisCatCali_cflId] PRIMARY KEY CLUSTERED 
(
	[cflId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovisCatMed](
	[cfmId] [bigint] NOT NULL,
	[cfmCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfmNombreEntidad] [varchar](100) NULL,
	[cfmIdentificacion] [varchar](16) NULL,
	[cfmApellidosNombres] [varchar](200) NULL,
	[cfmDireccion] [varchar](300) NULL,
	[cfmDepartamento] [varchar](100) NULL,
	[cfmMunicipio] [varchar](100) NULL,
	[cfmMatriculaInmobiliaria] [varchar](50) NULL,
	[cfmCedulaCatastral] [varchar](50) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisCatMed_cfmId] PRIMARY KEY CLUSTERED 
(
	[cfmId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovisCedula](
	[cfcId] [bigint] NOT NULL,
	[cfcCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfcNroCedula] [varchar](16) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisCedula_cfcId] PRIMARY KEY CLUSTERED 
(
	[cfcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovisFechasCorte](
	[cffId] [bigint] NOT NULL,
	[cffCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cffNitEntidad] [varchar](16) NULL,
	[cffNombreEntidad] [varchar](100) NULL,
	[cffTipoInformacion] [varchar](20) NULL,
	[cffFechaCorte] [date] NULL,
	[cffFechaActualizacion] [date] NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisFechasCorte_cffId] PRIMARY KEY CLUSTERED 
(
	[cffId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovisIGAC](
	[cfgId] [bigint] NOT NULL,
	[cfgCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfgNitEntidad] [varchar](16) NULL,
	[cfgNombreEntidad] [varchar](100) NULL,
	[cfgIdentificacion] [varchar](16) NULL,
	[cfgApellidosNombres] [varchar](200) NULL,
	[cfgCedulaCatastral] [varchar](50) NULL,
	[cfgDireccion] [varchar](300) NULL,
	[cfgMatriculaInmobiliaria] [varchar](50) NULL,
	[cfgDepartamento] [varchar](100) NULL,
	[cfgMunicipio] [varchar](100) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisIGAC_cfgId] PRIMARY KEY CLUSTERED 
(
	[cfgId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovisNuevoHogar](
	[cfhId] [bigint] NOT NULL,
	[cfhCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfhIdentificacion] [varchar](16) NULL,
	[cfhApellidosNombres] [varchar](200) NULL,
	[cfhFechaSolicitud] [date] NULL,
	[cfhEntidadOrtogante] [varchar](30) NULL,
	[cfhCajaCompensacion] [varchar](30) NULL,
	[cfhAsignadoPosteriorReporte] [varchar](30) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisNuevoHogar_cfhId] PRIMARY KEY CLUSTERED 
(
	[cfhId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovisReunidos](
	[cfrId] [bigint] NOT NULL,
	[cfrCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfrDocumento] [varchar](16) NULL,
	[cfrTipoDocumento] [varchar](30) NULL,
	[cfrApellidosNombres] [varchar](200) NULL,
	[cfrMunicipio] [varchar](100) NULL,
	[cfrDepartamento] [varchar](100) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisReunidos_cfrId] PRIMARY KEY CLUSTERED 
(
	[cfrId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovisSisben](
	[cfsId] [bigint] NOT NULL,
	[cfsCargueArchivoCruceFovis] [bigint] NOT NULL,
	[cfsIdentificacion] [varchar](16) NULL,
	[cfsApellidosNombres] [varchar](200) NULL,
	[cfsPuntaje] [varchar](10) NULL,
	[cfsSexo] [varchar](20) NULL,
	[cfsZona] [varchar](30) NULL,
	[cfsParantesco] [varchar](100) NULL,
 CONSTRAINT [PK_CargueArchivoCruceFovisSisben_cfsId] PRIMARY KEY CLUSTERED 
(
	[cfsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovisUnidos](
	[cfuId] [bigint] NOT NULL,
	[cfuCargueArchivoCruceFovis] [bigint] NOT NULL,
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueMultiple](
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueMultipleSupervivencia](
	[cmsId] [bigint] IDENTITY(1,1) NOT NULL,
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Cartera](
	[carId] [bigint] IDENTITY(1,1) NOT NULL,
	[carDeudaPresunta] [numeric](19, 5) NULL,
	[carEstadoCartera] [varchar](6) NOT NULL,
	[carEstadoOperacion] [varchar](10) NOT NULL,
	[carFechaCreacion] [datetime] NOT NULL,
	[carPersona] [bigint] NOT NULL,
	[carMetodo] [varchar](8) NULL,
	[carPeriodoDeuda] [date] NOT NULL,
	[carRiesgoIncobrabilidad] [varchar](48) NULL,
	[carTipoAccionCobro] [varchar](4) NULL,
	[carTipoDeuda] [varchar](11) NULL,
	[carTipoLineaCobro] [varchar](3) NULL,
	[carTipoSolicitante] [varchar](13) NULL,
	[carFechaAsignacionAccion] [datetime] NULL,
	[carUsuarioTraspaso] [varchar](255) NULL,
 CONSTRAINT [PK_Cartera_carId] PRIMARY KEY CLUSTERED 
(
	[carId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CarteraDependiente](
	[cadId] [bigint] IDENTITY(1,1) NOT NULL,
	[cadDeudaPresunta] [numeric](19, 5) NULL,
	[cadEstadoOperacion] [varchar](10) NOT NULL,
	[cadCartera] [bigint] NOT NULL,
	[cadPersona] [bigint] NOT NULL,
 CONSTRAINT [PK_CarteraDependiente_cadId] PRIMARY KEY CLUSTERED 
(
	[cadId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Categoria](
	[catId] [bigint] IDENTITY(1,1) NOT NULL,
	[catTipoAfiliado] [varchar](30) NOT NULL,
	[catCategoriaPersona] [varchar](50) NOT NULL,
	[catTipoBeneficiario] [varchar](30) NULL,
	[catClasificacion] [varchar](48) NOT NULL,
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CicloAportante](
	[capId] [bigint] IDENTITY(1,1) NOT NULL,
	[capPersona] [bigint] NULL,
	[capTipoSolicitante] [varchar](14) NULL,
	[capCicloCartera] [bigint] NULL,
 CONSTRAINT [PK_CicloAportante_capId] PRIMARY KEY CLUSTERED 
(
	[capId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CicloAsignacion](
	[ciaId] [bigint] IDENTITY(1,1) NOT NULL,
	[ciaNombre] [varchar](50) NULL,
	[ciaFechaInicio] [date] NULL,
	[ciaFechaFin] [date] NULL,
	[ciaCicloPredecesor] [bigint] NULL,
	[ciaEstadoCicloAsignacion] [varchar](30) NULL,
	[ciaCicloActivo] [bit] NULL,
	[ciaValorDisponible] [numeric](19, 5) NULL,
 CONSTRAINT [PK_CicloAsignacion_ciaId] PRIMARY KEY CLUSTERED 
(
	[ciaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CicloCartera](
	[ccrId] [bigint] IDENTITY(1,1) NOT NULL,
	[ccrEstadoCiclo] [varchar](10) NOT NULL,
	[ccrFechaInicio] [date] NOT NULL,
	[ccrFechaFin] [date] NOT NULL,
	[ccrFechaCreacion] [datetime] NOT NULL,
	[ccrTipoCiclo] [varchar](14) NULL,
 CONSTRAINT [PK_CicloCartera_ccrId] PRIMARY KEY CLUSTERED 
(
	[ccrId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CicloModalidad](
	[cmoId] [bigint] IDENTITY(1,1) NOT NULL,
	[cmoCicloAsignacion] [bigint] NOT NULL,
	[cmoModalidad] [varchar](50) NOT NULL,
 CONSTRAINT [PK_CicloModalidad_cmoId] PRIMARY KEY CLUSTERED 
(
	[cmoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CodigoCIIU](
	[ciiId] [smallint] IDENTITY(1,1) NOT NULL,
	[ciiCodigo] [varchar](4) NOT NULL,
	[ciiDescripcion] [varchar](255) NOT NULL,
	[ciiCodigoSeccion] [varchar](1) NULL,
	[ciiDescripcionSeccion] [varchar](200) NULL,
	[ciiCodigoDivision] [varchar](2) NULL,
	[ciiDescripcionDivision] [varchar](250) NULL,
	[ciiCodigoGrupo] [varchar](3) NULL,
	[ciiDescripcionGrupo] [varchar](200) NULL,
 CONSTRAINT [PK_CodigoCIIU_ciiId] PRIMARY KEY CLUSTERED 
(
	[ciiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CondicionEspecialPersona](
	[cepId] [bigint] IDENTITY(1,1) NOT NULL,
	[cepPersona] [bigint] NOT NULL,
	[cepNombreCondicion] [varchar](28) NOT NULL,
	[cepActiva] [bit] NULL,
 CONSTRAINT [PK_CondicionEspecialPersona_cepId] PRIMARY KEY CLUSTERED 
(
	[cepId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CondicionInvalidez](
	[coiId] [bigint] IDENTITY(1,1) NOT NULL,
	[coiPersona] [bigint] NOT NULL,
	[coiInvalidez] [bit] NULL,
	[coiFechaReporteInvalidez] [date] NULL,
	[coiComentarioInvalidez] [varchar](500) NULL,
 CONSTRAINT [PK_CondicionInvalidez_coiId] PRIMARY KEY CLUSTERED 
(
	[coiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_CondicionInvalidez_coiPersona] UNIQUE NONCLUSTERED 
(
	[coiPersona] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CondicionVisita](
	[covId] [bigint] IDENTITY(1,1) NOT NULL,
	[covCondicion] [varchar](42) NOT NULL,
	[covCumple] [bit] NOT NULL,
	[covObservacion] [varchar](250) NULL,
	[covVisita] [bigint] NOT NULL,
 CONSTRAINT [PK_CondicionVisita_covId] PRIMARY KEY CLUSTERED 
(
	[covId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_CondicionVisita_covVisita_covCondicion] UNIQUE NONCLUSTERED 
(
	[covVisita] ASC,
	[covCondicion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ConjuntoValidacionSubsidio](
	[cvsId] [bigint] NOT NULL,
	[cvsTipoValidacion] [varchar](50) NOT NULL,
 CONSTRAINT [PK_ConjuntoValidacionSubsidio_cvsId] PRIMARY KEY CLUSTERED 
(
	[cvsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ConsolaEstadoCargueMasivo](
	[cecId] [bigint] IDENTITY(1,1) NOT NULL,
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Constante](
	[cnsId] [int] IDENTITY(1,1) NOT NULL,
	[cnsNombre] [varchar](100) NULL,
	[cnsValor] [varchar](150) NULL,
	[cnsDescripcion] [varchar](250) NOT NULL,
 CONSTRAINT [PK_Constante_cnsId] PRIMARY KEY CLUSTERED 
(
	[cnsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_Constante_cnsNombre] UNIQUE NONCLUSTERED 
(
	[cnsNombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ConvenioPago](
	[copId] [bigint] IDENTITY(1,1) NOT NULL,
	[copPersona] [bigint] NOT NULL,
	[copTipoSolicitante] [varchar](13) NOT NULL,
	[copDeudaPresuntaRegistrada] [numeric](19, 5) NOT NULL,
	[copDeudaRealRegistrada] [numeric](19, 5) NULL,
	[copCuotasPorPagar] [smallint] NOT NULL,
	[copEstadoConvenioPago] [varchar](30) NOT NULL,
	[copMotivoAnulacion] [varchar](30) NULL,
	[copUsuarioAnulacion] [varchar](255) NULL,
	[copFechaAnulacion] [datetime] NULL,
	[copFechaRegistro] [datetime] NOT NULL,
	[copUsuarioCreacion] [varchar](255) NULL,
 CONSTRAINT [PK_ConvenioPago_copId] PRIMARY KEY CLUSTERED 
(
	[copId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ConvenioPagoDependiente](
	[cpdId] [bigint] IDENTITY(1,1) NOT NULL,
	[cpdPagoPeriodoConvenio] [bigint] NULL,
	[cpdPersona] [bigint] NULL,
 CONSTRAINT [PK_ConvenioPagoDependiente_cpdId] PRIMARY KEY CLUSTERED 
(
	[cpdId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ConvenioTerceroPagador](
	[conId] [bigint] IDENTITY(1,1) NOT NULL,
	[conEmpresa] [bigint] NOT NULL,
	[conNombre] [varchar](250) NOT NULL,
	[conEstado] [varchar](10) NOT NULL,
	[conMedioDePago] [varchar](20) NOT NULL,
	[conUsuarioGenesys] [varchar](200) NOT NULL,
	[conNombreCompletoContacto] [varchar](30) NULL,
	[conCargoContacto] [varchar](30) NULL,
	[conIndicativoTelFijoContacto] [varchar](2) NULL,
	[conTelefonoFijoContacto] [varchar](7) NULL,
	[conTelefonoCelularContacto] [varchar](10) NULL,
	[conEmailContacto] [varchar](255) NULL,
	[conAcuerdoDeFacturacion] [varchar](255) NOT NULL,
 CONSTRAINT [PK_ConvenioTerceroPagador_conId] PRIMARY KEY CLUSTERED 
(
	[conId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Correccion](
	[corId] [bigint] IDENTITY(1,1) NOT NULL,
	[corAporteDetallado] [bigint] NULL,
	[corAporteGeneral] [bigint] NULL,
	[corSolicitudCorreccionAporte] [bigint] NULL,
 CONSTRAINT [PK_Correccion_scaId] PRIMARY KEY CLUSTERED 
(
	[corId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Cruce](
	[cruId] [bigint] NOT NULL,
	[cruCargueArchivoCruceFovis] [bigint] NULL,
	[cruNumeroPostulacion] [varchar](12) NULL,
	[cruPersona] [bigint] NULL,
	[cruEstadoCruce] [varchar](22) NOT NULL,
	[cruSolicitudGestionCruce] [bigint] NULL,
	[cruResultadoCodigoIdentificadorECM] [varchar](255) NULL,
	[cruObservacionResultado] [varchar](500) NULL,
	[cruEjecucionProcesoAsincrono] [bigint] NULL,
	[cruFechaRegistro] [datetime] NOT NULL,
 CONSTRAINT [PK_Cruce_cruId] PRIMARY KEY CLUSTERED 
(
	[cruId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CruceDetalle](
	[crdId] [bigint] NOT NULL,
	[crdCruce] [bigint] NOT NULL,
	[crdCausalCruce] [varchar](30) NULL,
	[crdNitEntidad] [varchar](16) NULL,
	[crdNombreEntidad] [varchar](100) NULL,
	[crdNumeroIdentificacion] [varchar](16) NULL,
	[crdApellidos] [varchar](100) NULL,
	[crdNombres] [varchar](100) NULL,
	[crdCedulaCatastral] [varchar](50) NULL,
	[crdDireccionInmueble] [varchar](300) NULL,
	[crdMatriculaInmobiliaria] [varchar](50) NULL,
	[crdDepartamento] [varchar](100) NULL,
	[crdMunicipio] [varchar](50) NULL,
	[crdFechaActualizacionMinisterio] [date] NULL,
	[crdFechaCorteEntidad] [date] NULL,
	[crdApellidosNombres] [varchar](200) NULL,
	[crdPuntaje] [varchar](10) NULL,
	[crdSexo] [varchar](20) NULL,
	[crdZona] [varchar](30) NULL,
	[crdParentesco] [varchar](30) NULL,
	[crdFolio] [varchar](30) NULL,
	[crdTipodocumento] [varchar](30) NULL,
	[crdFechaSolicitud] [date] NULL,
	[crdEntidadOtorgante] [varchar](30) NULL,
	[crdCajaCompensacion] [varchar](30) NULL,
	[crdAsignadoPosterior] [varchar](30) NULL,
	[crdTipo] [varchar](15) NULL,
	[crdResultadoValidacion] [varchar](255) NULL,
	[crdClasificacion] [varchar](30) NULL,
 CONSTRAINT [PK_CruceDetalle_crdId] PRIMARY KEY CLUSTERED 
(
	[crdId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CuentaAdministradorSubsidio](
	[casId] [bigint] IDENTITY(1,1) NOT NULL,
	[casFechaHoraCreacionRegistro] [datetime] NOT NULL,
	[casUsuarioCreacionRegistro] [varchar](200) NOT NULL,
	[casTipoTransaccionSubsidio] [varchar](40) NOT NULL,
	[casEstadoTransaccionSubsidio] [varchar](25) NULL,
	[casEstadoLiquidacionSubsidio] [varchar](25) NULL,
	[casOrigenTransaccion] [varchar](30) NOT NULL,
	[casMedioDePagoTransaccion] [varchar](13) NOT NULL,
	[casNumeroTarjetaAdmonSubsidio] [varchar](50) NULL,
	[casCodigoBanco] [varchar](4) NULL,
	[casNombreBanco] [varchar](200) NULL,
	[casTipoCuentaAdmonSubsidio] [varchar](30) NULL,
	[casNumeroCuentaAdmonSubsidio] [varchar](30) NULL,
	[casTipoIdentificacionTitularCuentaAdmonSubsidio] [varchar](20) NULL,
	[casNumeroIdentificacionTitularCuentaAdmonSubsidio] [varchar](20) NULL,
	[casNombreTitularCuentaAdmonSubsidio] [varchar](200) NULL,
	[casFechaHoraTransaccion] [datetime] NOT NULL,
	[casUsuarioTransaccion] [varchar](200) NOT NULL,
	[casValorOriginalTransaccion] [numeric](19, 5) NOT NULL,
	[casValorRealTransaccion] [numeric](19, 5) NOT NULL,
	[casIdTransaccionOriginal] [bigint] NULL,
	[casIdRemisionDatosTerceroPagador] [varchar](200) NULL,
	[casIdTransaccionTerceroPagador] [varchar](200) NULL,
	[casNombreTerceroPagado] [varchar](200) NULL,
	[casIdCuentaAdmonSubsidioRelacionado] [bigint] NULL,
	[casFechaHoraUltimaModificacion] [datetime] NULL,
	[casUsuarioUltimaModificacion] [varchar](200) NULL,
	[casAdministradorSubsidio] [bigint] NOT NULL,
	[casSitioDePago] [bigint] NULL,
	[casSitioDeCobro] [bigint] NULL,
	[casMedioDePago] [bigint] NOT NULL,
	[casSolicitudLiquidacionSubsidio] [bigint] NULL,
	[casCondicionPersonaAdmin] [bigint] NULL,
 CONSTRAINT [PK_CuentaAdministradorSubsidio_casId] PRIMARY KEY CLUSTERED 
(
	[casId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DatoTemporalComunicado](
	[dtcId] [bigint] IDENTITY(1,1) NOT NULL,
	[dtcIdTarea] [bigint] NULL,
	[dtcJsonPayload] [text] NULL,
 CONSTRAINT [PK_DatoTemporalComunicado_dtcId] PRIMARY KEY CLUSTERED 
(
	[dtcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DatoTemporalParametrizacion](
	[dtpId] [bigint] IDENTITY(1,1) NOT NULL,
	[dtpParametrizacion] [varchar](30) NULL,
	[dtpJsonPayload] [text] NULL,
 CONSTRAINT [PK_DatoTemporalParametrizacion] PRIMARY KEY CLUSTERED 
(
	[dtpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DatoTemporalSolicitud](
	[dtsId] [bigint] IDENTITY(1,1) NOT NULL,
	[dtsSolicitud] [bigint] NULL,
	[dtsJsonPayload] [text] NULL,
	[dtsTipoIdentificacion] [varchar](20) NULL,
	[dtsNumeroIdentificacion] [varchar](16) NULL,
 CONSTRAINT [PK_DatoTemporalSolicitud_dtsId] PRIMARY KEY CLUSTERED 
(
	[dtsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Departamento](
	[depId] [smallint] IDENTITY(1,1) NOT NULL,
	[depCodigo] [varchar](2) NOT NULL,
	[depIndicativoTelefoniaFija] [varchar](2) NOT NULL,
	[depNombre] [varchar](100) NOT NULL,
	[depExcepcionAplicaFOVIS] [bit] NULL,
 CONSTRAINT [PK_Departamento_depId] PRIMARY KEY CLUSTERED 
(
	[depId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DesafiliacionAportante](
	[deaId] [bigint] IDENTITY(1,1) NOT NULL,
	[deaPersona] [bigint] NULL,
	[deaSolicitudDesafiliacion] [bigint] NULL,
	[deaTipoSolicitante] [varchar](13) NULL,
	[deaTipoLineaCobro] [varchar](3) NOT NULL,
 CONSTRAINT [PK_DesafiliacionAportante_deaId] PRIMARY KEY CLUSTERED 
(
	[deaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DescuentosSubsidioAsignado](
	[desId] [bigint] IDENTITY(1,1) NOT NULL,
	[desDetalleSubsidioAsignado] [bigint] NOT NULL,
	[desEntidadDescuento] [bigint] NOT NULL,
	[desMontoDescontado] [numeric](19, 5) NULL,
	[desNombreEntidadDescuento] [varchar](250) NULL,
 CONSTRAINT [PK_DescuentosSubsidioAsignado_desId] PRIMARY KEY CLUSTERED 
(
	[desId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DestinatarioComunicado](
	[dcoId] [bigint] IDENTITY(1,1) NOT NULL,
	[dcoProceso] [varchar](150) NOT NULL,
	[dcoEtiquetaPlantilla] [varchar](150) NOT NULL,
 CONSTRAINT [PK_DestinatarioComunicado_dcoId] PRIMARY KEY CLUSTERED 
(
	[dcoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_DestinatarioComunicado_dcoProceso_dcoEtiquetaPlantilla] UNIQUE NONCLUSTERED 
(
	[dcoProceso] ASC,
	[dcoEtiquetaPlantilla] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DestinatarioGrupo](
	[dgrId] [bigint] IDENTITY(1,1) NOT NULL,
	[dgrGrupoPrioridad] [bigint] NOT NULL,
	[dgrRolContacto] [varchar](60) NOT NULL,
 CONSTRAINT [PK_DestinatarioGrupo_dgrId] PRIMARY KEY CLUSTERED 
(
	[dgrId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DetalleComunicadoEnviado](
	[dceId] [bigint] IDENTITY(1,1) NOT NULL,
	[dceComunicado] [bigint] NOT NULL,
	[dceIdentificador] [bigint] NULL,
	[dceTipoTransaccion] [varchar](100) NOT NULL,
 CONSTRAINT [PK_DetalleComunicadoEnviado_dceId] PRIMARY KEY CLUSTERED 
(
	[dceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DetalleDatosRegistroValidacion](
	[ddrIdDato] [bigint] NOT NULL,
	[ddrValorDetalle] [varchar](150) NULL,
	[ddrIdDetalle] [varchar](50) NOT NULL,
 CONSTRAINT [PK_DetalleDatosRegistroValidacion_ddrvIdDato_ddrIdDetalle] PRIMARY KEY CLUSTERED 
(
	[ddrIdDato] ASC,
	[ddrIdDetalle] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DetalleSolicitudAnulacionSubsidioCobrado](
	[dssId] [bigint] IDENTITY(1,1) NOT NULL,
	[dssSolicitudAnulacionSubsidioCobrado] [bigint] NOT NULL,
	[dssCuentaAdministradorSubisdio] [bigint] NOT NULL,
	[dssDetalleAnulacion] [varchar](250) NOT NULL,
 CONSTRAINT [PK_DetalleSolicitudAnulacionSubsidioCobrado_dssId] PRIMARY KEY CLUSTERED 
(
	[dssId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DetalleSolicitudGestionCobro](
	[dsgId] [bigint] IDENTITY(1,1) NOT NULL,
	[dsgEnviarPrimeraRemision] [bit] NULL,
	[dsgEnviarSegundaRemision] [bit] NULL,
	[dsgEstado] [varchar](52) NULL,
	[dsgFechaPrimeraRemision] [datetime] NULL,
	[dsgFechaSegundaRemision] [datetime] NULL,
	[dsgCartera] [bigint] NOT NULL,
	[dsgObservacionPrimeraEntrega] [varchar](255) NULL,
	[dsgObservacionPrimeraRemision] [varchar](255) NULL,
	[dsgObservacionSegundaEntrega] [varchar](255) NULL,
	[dsgObservacionSegundaRemision] [varchar](255) NULL,
	[dsgSolicitudPrimeraRemision] [bigint] NULL,
	[dsgSolicitudSegundaRemision] [bigint] NULL,
	[dsgResultadoPrimeraEntrega] [varchar](18) NULL,
	[dsgResultadoSegundaEntrega] [varchar](18) NULL,
	[dsgDocumentoPrimeraRemision] [bigint] NULL,
	[dsgDocumentoSegundaRemision] [bigint] NULL,
 CONSTRAINT [PK_DetalleSolicitudGestionCobro_dsgId] PRIMARY KEY CLUSTERED 
(
	[dsgId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DetalleSubsidioAsignado](
	[dsaId] [bigint] IDENTITY(1,1) NOT NULL,
	[dsaUsuarioCreador] [varchar](200) NOT NULL,
	[dsaFechaHoraCreacion] [datetime] NOT NULL,
	[dsaEstado] [varchar](20) NOT NULL,
	[dsaMotivoAnulacion] [varchar](40) NULL,
	[dsaDetalleAnulacion] [varchar](250) NULL,
	[dsaOrigenRegistroSubsidio] [varchar](30) NOT NULL,
	[dsaTipoliquidacionSubsidio] [varchar](60) NOT NULL,
	[dsaTipoCuotaSubsidio] [varchar](80) NOT NULL,
	[dsaValorSubsidioMonetario] [numeric](19, 5) NOT NULL,
	[dsaValorDescuento] [numeric](19, 5) NOT NULL,
	[dsaValorOriginalAbonado] [numeric](19, 5) NOT NULL,
	[dsaValorTotal] [numeric](19, 5) NOT NULL,
	[dsaFechaTransaccionRetiro] [date] NULL,
	[dsaUsuarioTransaccionRetiro] [varchar](200) NULL,
	[dsaFechaTransaccionAnulacion] [date] NULL,
	[dsaUsuarioTransaccionAnulacion] [varchar](200) NULL,
	[dsaFechaHoraUltimaModificacion] [datetime] NULL,
	[dsaUsuarioUltimaModificacion] [varchar](200) NULL,
	[dsaSolicitudLiquidacionSubsidio] [bigint] NOT NULL,
	[dsaEmpleador] [bigint] NOT NULL,
	[dsaAfiliadoPrincipal] [bigint] NOT NULL,
	[dsaGrupoFamiliar] [bigint] NOT NULL,
	[dsaAdministradorSubsidio] [bigint] NOT NULL,
	[dsaIdRegistroOriginalRelacionado] [bigint] NULL,
	[dsaCuentaAdministradorSubsidio] [bigint] NOT NULL,
	[dsaBeneficiarioDetalle] [bigint] NOT NULL,
	[dsaPeriodoLiquidado] [date] NOT NULL,
	[dsaResultadoValidacionLiquidacion] [bigint] NOT NULL,
	[dsaCondicionPersonaBeneficiario] [bigint] NULL,
	[dsaCondicionPersonaAfiliado] [bigint] NULL,
	[dsaCondicionPersonaEmpleador] [bigint] NULL,
 CONSTRAINT [PK_DetalleSubsidioAsignado_dsaId] PRIMARY KEY CLUSTERED 
(
	[dsaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DevolucionAporte](
	[dapId] [bigint] IDENTITY(1,1) NOT NULL,
	[dapFechaRecepcion] [datetime] NULL,
	[dapMotivoPeticion] [varchar](28) NULL,
	[dapDestinatarioDevolucion] [varchar](13) NULL,
	[dapCajaCompensacion] [int] NULL,
	[dapOtroDestinatario] [varchar](255) NULL,
	[dapMontoAportes] [numeric](19, 5) NULL,
	[dapMontoIntereses] [numeric](19, 5) NULL,
	[dapPeriodoReclamado] [varchar](255) NULL,
	[dapMedioPago] [bigint] NULL,
	[dapSedeCajaCompensacion] [bigint] NULL,
	[dapDescuentoGestionPagoOI] [numeric](19, 5) NULL,
	[dapDescuentoGestionFinanciera] [numeric](19, 5) NULL,
	[dapDescuentoOtro] [numeric](19, 5) NULL,
	[dapOtraCaja] [varchar](255) NULL,
	[dapOtroMotivo] [varchar](255) NULL,
 CONSTRAINT [PK_DevolucionAporte_dapId] PRIMARY KEY CLUSTERED 
(
	[dapId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DevolucionAporteDetalle](
	[dadId] [bigint] IDENTITY(1,1) NOT NULL,
	[dadIncluyeAporteObligatorio] [bit] NULL,
	[dadIncluyeMoraCotizante] [bit] NULL,
	[dadComentarioHistorico] [varchar](255) NULL,
	[dadComentarioNovedades] [varchar](255) NULL,
	[dadComentarioAportes] [varchar](255) NULL,
	[dadUsuario] [varchar](255) NULL,
	[dadFechaGestion] [datetime] NULL,
	[dadDevolucionAporte] [bigint] NOT NULL,
	[dadMovimientoAporte] [bigint] NULL,
 CONSTRAINT [PK_DevolucionAporteDetalle_dadId] PRIMARY KEY CLUSTERED 
(
	[dadId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DiasFestivos](
	[pifId] [bigint] IDENTITY(1,1) NOT NULL,
	[pifConcepto] [varchar](150) NOT NULL,
	[pifFecha] [date] NOT NULL,
 CONSTRAINT [PK_PilaDiasFestivos_pifId] PRIMARY KEY CLUSTERED 
(
	[pifId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DiferenciasCargueActualizacion](
	[dicId] [bigint] IDENTITY(1,1) NOT NULL,
	[dicTipoTransaccion] [varchar](100) NULL,
	[dicJsonPayload] [text] NULL,
	[dicCargueArchivoActualizacion] [bigint] NOT NULL,
 CONSTRAINT [PK_DiferenciasCargueActualizacion_dicId] PRIMARY KEY CLUSTERED 
(
	[dicId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoAdministracionEstadoSolicitud](
	[daeId] [bigint] IDENTITY(1,1) NOT NULL,
	[daeSolicitud] [bigint] NULL,
	[daeDocumentoSoporteCambioEstado] [varchar](100) NULL,
	[daeNombreDocumento] [varchar](100) NULL,
	[daeTipoDocumentoAdjunto] [varchar](22) NULL,
	[daeActividad] [varchar](29) NULL,
 CONSTRAINT [PK_DocumentoAdministracionEstadoSolicitud_daeId] PRIMARY KEY CLUSTERED 
(
	[daeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoBitacora](
	[dbiId] [bigint] IDENTITY(1,1) NOT NULL,
	[dbiBitacoraCartera] [bigint] NOT NULL,
	[dbiDocumentoSoporte] [bigint] NOT NULL,
 CONSTRAINT [PK_DocumentoBitacora_dbiId] PRIMARY KEY CLUSTERED 
(
	[dbiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoCartera](
	[dcaId] [bigint] IDENTITY(1,1) NOT NULL,
	[dcaCartera] [bigint] NOT NULL,
	[dcaDocumentoSoporte] [bigint] NOT NULL,
	[dcaAccionCobro] [varchar](4) NULL,
	[dcaConsecutivoLiquidacion] [varchar](10) NULL,
 CONSTRAINT [PK_DocumentoCartera_dcaId] PRIMARY KEY CLUSTERED 
(
	[dcaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoDesafiliacion](
	[dodId] [bigint] IDENTITY(1,1) NOT NULL,
	[dodDocumentoSoporte] [bigint] NULL,
	[dodSolicitudDesafiliacion] [bigint] NULL,
 CONSTRAINT [PK_DocumentoDesafiliacion_dodId] PRIMARY KEY CLUSTERED 
(
	[dodId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoSoporte](
	[dosId] [bigint] IDENTITY(1,1) NOT NULL,
	[dosNombreDocumento] [varchar](255) NOT NULL,
	[dosDescripcionComentarios] [varchar](255) NOT NULL,
	[dosIdentificacionDocumento] [varchar](255) NOT NULL,
	[dosVersionDocumento] [varchar](6) NOT NULL,
	[dosFechaHoraCargue] [datetime] NOT NULL,
	[dosTipoDocumento] [varchar](24) NULL,
 CONSTRAINT [PK_DocumentoSoporte_dsId] PRIMARY KEY CLUSTERED 
(
	[dosId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoSoporteConvenio](
	[dscId] [bigint] IDENTITY(1,1) NOT NULL,
	[dscConvenioTerceroPagador] [bigint] NOT NULL,
	[dscDocumentoSoporte] [bigint] NOT NULL,
 CONSTRAINT [PK_DocumentoSoporteConvenio_dscId] PRIMARY KEY CLUSTERED 
(
	[dscId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoSoporteOferente](
	[dsoId] [bigint] IDENTITY(1,1) NOT NULL,
	[dsoOferente] [bigint] NOT NULL,
	[dsoDocumentoSoporte] [bigint] NOT NULL,
 CONSTRAINT [PK_DocumentoSoporteOferente_dsoId] PRIMARY KEY CLUSTERED 
(
	[dsoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoSoporteProyectoVivienda](
	[dspId] [bigint] IDENTITY(1,1) NOT NULL,
	[dspProyectoSolucionVivienda] [bigint] NOT NULL,
	[dspDocumentoSoporte] [bigint] NOT NULL,
 CONSTRAINT [PK_DocumentoSoporteProyectoVivienda_dspId] PRIMARY KEY CLUSTERED 
(
	[dspId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [EjecucionProcesoAsincrono](
	[epsId] [bigint] IDENTITY(1,1) NOT NULL,
	[epsFechaInicio] [datetime] NOT NULL,
	[epsFechaFin] [datetime] NULL,
	[epsRevisado] [bit] NOT NULL,
	[epsTipoProceso] [varchar](20) NULL,
 CONSTRAINT [PK_EjecucionProcesoAsincrono_epsId] PRIMARY KEY CLUSTERED 
(
	[epsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [EjecucionProgramada](
	[ejpId] [bigint] IDENTITY(1,1) NOT NULL,
	[ejpFechaDefinicion] [datetime] NULL,
	[ejpUsuario] [varchar](255) NULL,
	[ejpFrecuencia] [varchar](50) NULL,
	[ejpHoraInicio] [varchar](5) NULL,
	[ejpFechaInicioVigencia] [datetime] NULL,
	[ejpFechaFinVigencia] [datetime] NULL,
	[ejpCajaCompensacion] [int] NULL,
 CONSTRAINT [PK_EjecucionProgramada_ejpId] PRIMARY KEY CLUSTERED 
(
	[ejpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ElementoDireccion](
	[eldId] [bigint] IDENTITY(1,1) NOT NULL,
	[eldNombre] [varchar](20) NOT NULL,
 CONSTRAINT [PK_ElementoDireccion_eldId] PRIMARY KEY CLUSTERED 
(
	[eldId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Empleador](
	[empId] [bigint] IDENTITY(1,1) NOT NULL,
	[empEstadoEmpleador] [varchar](8) NULL,
	[empExpulsionSubsanada] [bit] NULL,
	[empFechaCambioEstadoAfiliacion] [datetime2](7) NULL,
	[empMotivoDesafiliacion] [varchar](100) NULL,
	[empNumeroTotalTrabajadores] [int] NULL,
	[empPeriodoUltimaNomina] [date] NULL,
	[empValorTotalUltimaNomina] [numeric](19, 0) NULL,
	[empEmpresa] [bigint] NULL,
	[empFechaRetiro] [datetime] NULL,
	[empFechaSubsanacionExpulsion] [date] NULL,
	[empMotivoSubsanacionExpulsion] [varchar](200) NULL,
	[empCantIngresoBandejaCeroTrabajadores] [smallint] NULL,
	[empFechaRetiroTotalTrabajadores] [date] NULL,
	[empFechaGestionDesafiliacion] [date] NULL,
	[empMedioPagoSubsidioMonetario] [varchar](30) NULL,
	[empValidarSucursalPila] [bit] NULL,
	[empDiaHabilVencimientoAporte] [smallint] NULL,
	[empMarcaExpulsion] [varchar](22) NULL,
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
	[empCreadoPorPila] [bit] NULL,
	[empEnviadoAFiscalizacion] [bit] NULL,
	[empMotivoFiscalizacion] [varchar](30) NULL,
	[empFechaFiscalizacion] [date] NULL,
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [EntidadDescuento](
	[endId] [bigint] IDENTITY(1,1) NOT NULL,
	[endCodigo] [bigint] NOT NULL,
	[endTipo] [varchar](10) NOT NULL,
	[endEmpresa] [bigint] NULL,
	[endPrioridad] [int] NOT NULL,
	[endEstado] [varchar](10) NOT NULL,
	[endNombreContacto] [varchar](250) NULL,
	[endObservaciones] [varchar](250) NULL,
	[endFechaCreacion] [date] NOT NULL,
	[endNombre] [varchar](250) NULL,
	[endNumeroCelular] [varchar](10) NULL,
 CONSTRAINT [PK_EntidadDescuento_endId] PRIMARY KEY CLUSTERED 
(
	[endId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_EntidadDescuento_endCodigo] UNIQUE NONCLUSTERED 
(
	[endCodigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_EntidadDescuento_endPrioridad] UNIQUE NONCLUSTERED 
(
	[endPrioridad] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
	[epaCargoContacto] [varchar](20) NULL,
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
	[esoTipoAnalistaFOVIS] [varchar](22) NULL,
 CONSTRAINT [PK_EscalamientoSolicitud_esoId] PRIMARY KEY CLUSTERED 
(
	[esoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [EstadoCondicionValidacionSubsidio](
	[ecvId] [bigint] IDENTITY(1,1) NOT NULL,
	[ecvAplicacionValidacionSubsidio] [bigint] NOT NULL,
	[ecvActivo] [bit] NULL,
	[ecvInactivo] [bit] NULL,
	[ecvNFRetiradoConAportes] [bit] NULL,
	[ecvNFConAporteSinAfiliacion] [bit] NULL,
	[ecvNFConInformacion] [bit] NULL,
	[ecvOk] [bit] NULL,
	[ecvNoOk] [bit] NULL,
	[ecvNoValidadoBD] [bit] NULL,
 CONSTRAINT [PK_EstadoCondicionValidacionSubsidio_ecvId] PRIMARY KEY CLUSTERED 
(
	[ecvId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ExclusionCartera](
	[excId] [bigint] IDENTITY(1,1) NOT NULL,
	[excPersona] [bigint] NOT NULL,
	[excTipoSolicitante] [varchar](15) NOT NULL,
	[excEstadoExclusionCartera] [varchar](10) NOT NULL,
	[excFechaInicio] [date] NOT NULL,
	[excFechaFin] [date] NULL,
	[excFechaRegistro] [date] NOT NULL,
	[excFechaMovimiento] [date] NULL,
	[excObservacion] [varchar](400) NULL,
	[excTipoExclusionCartera] [varchar](25) NOT NULL,
	[excEstadoAntesExclusion] [varchar](45) NOT NULL,
	[excNumeroOperacionMora] [bigint] NULL,
	[excUsuarioRegistro] [varchar](400) NULL,
	[excResultado] [varchar](11) NULL,
	[excObservacionCambioResultado] [varchar](400) NULL,
 CONSTRAINT [PK_ExclusionCartera_excId] PRIMARY KEY CLUSTERED 
(
	[excId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ExpulsionSubsanada](
	[exsId] [bigint] IDENTITY(1,1) NOT NULL,
	[exsExpulsionSubsanada] [bit] NULL,
	[exsFechaSubsanacionExpulsion] [date] NULL,
	[exsMotivoSubsanacionExpulsion] [varchar](200) NOT NULL,
	[exsEmpleador] [bigint] NULL,
	[exsRolAfiliado] [bigint] NULL,
 CONSTRAINT [PK_ExpulsionSubsanada_exsId] PRIMARY KEY CLUSTERED 
(
	[exsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [FormaPagoModalidad](
	[fpmId] [bigint] IDENTITY(1,1) NOT NULL,
	[fpmFormaPago] [varchar](34) NOT NULL,
	[fpmModalidad] [varchar](50) NOT NULL,
 CONSTRAINT [PK_FormaPagoModalidad_fpmId] PRIMARY KEY CLUSTERED 
(
	[fpmId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GestionNotiNoEnviadas](
	[gneId] [bigint] IDENTITY(1,1) NOT NULL,
	[gneEmpresa] [bigint] NOT NULL,
	[gneTipoInconsistencia] [varchar](20) NOT NULL,
	[gneCanalContacto] [varchar](20) NOT NULL,
	[gneFechaIngreso] [datetime] NOT NULL,
	[gneEstadoGestion] [varchar](25) NULL,
	[gneObservaciones] [varchar](60) NULL,
	[gneFechaRespuesta] [datetime] NULL,
 CONSTRAINT [PK_GestionNotiNoEnviadas_gneId] PRIMARY KEY CLUSTERED 
(
	[gneId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GradoAcademico](
	[graId] [smallint] IDENTITY(1,1) NOT NULL,
	[graNombre] [varchar](20) NOT NULL,
	[graNivelEducativo] [varchar](43) NOT NULL,
 CONSTRAINT [PK_GradoAcademico_graId] PRIMARY KEY CLUSTERED 
(
	[graId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GroupFC_FieldGC](
	[fieldGenericCatalogs_id] [bigint] NOT NULL,
	[groupFieldCatalogs_id] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GrupoFamiliar](
	[grfId] [bigint] IDENTITY(1,1) NOT NULL,
	[grfNumero] [smallint] NOT NULL,
	[grfObservaciones] [varchar](500) NULL,
	[grfAfiliado] [bigint] NOT NULL,
	[grfUbicacion] [bigint] NULL,
	[grfInembargable] [bit] NULL,
 CONSTRAINT [PK_GrupoFamiliar_grfId] PRIMARY KEY CLUSTERED 
(
	[grfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GrupoPrioridad](
	[gprId] [bigint] IDENTITY(1,1) NOT NULL,
	[gprNombre] [varchar](150) NULL,
 CONSTRAINT [PK_GrupoPrioridad_gprId] PRIMARY KEY CLUSTERED 
(
	[gprId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GrupoRequisito](
	[grqId] [bigint] IDENTITY(1,1) NOT NULL,
	[grqRequisitoCajaClasificacion] [bigint] NULL,
	[grqGrupoUsuario] [varchar](60) NULL,
 CONSTRAINT [PK_GrupoRequisito_grqId] PRIMARY KEY CLUSTERED 
(
	[grqId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [HistoriaResultadoValidacion](
	[hrvId] [bigint] IDENTITY(1,1) NOT NULL,
	[hrvDetalle] [varchar](400) NULL,
	[hrvResultado] [varchar](20) NULL,
	[hrvValidacion] [varchar](100) NULL,
	[hrvIdDatosRegistro] [bigint] NULL,
	[hrvTipoExepcion] [varchar](30) NULL,
 CONSTRAINT [PK_HistoriaResultadoValidacion_hrvId] PRIMARY KEY CLUSTERED 
(
	[hrvId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [InformacionFaltanteAportante](
	[ifaId] [bigint] IDENTITY(1,1) NOT NULL,
	[ifaSolicitud] [bigint] NOT NULL,
	[ifaTipoGestion] [varchar](22) NULL,
	[ifaResponsableInformacion] [varchar](18) NULL,
	[ifaTipoDocumentoGestion] [varchar](10) NULL,
	[ifaMedioComunicacion] [varchar](19) NULL,
	[ifaObservaciones] [varchar](500) NULL,
	[ifaFechaGestion] [date] NULL,
	[ifaFechaRegistro] [date] NULL,
	[ifaUsuario] [varchar](255) NULL,
 CONSTRAINT [PK_InformacionFaltanteAportante_ifaId] PRIMARY KEY CLUSTERED 
(
	[ifaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Infraestructura](
	[infId] [bigint] IDENTITY(1,1) NOT NULL,
	[infCodigoParaSSF] [varchar](13) NOT NULL,
	[infCodigoParaCCF] [varchar](12) NOT NULL,
	[infConsecutivoInfraestructura] [smallint] NOT NULL,
	[infNombre] [varchar](255) NOT NULL,
	[infTipoInfraestructura] [bigint] NOT NULL,
	[infZona] [varchar](255) NOT NULL,
	[infDireccion] [varchar](300) NOT NULL,
	[infAreaGeografica] [varchar](6) NOT NULL,
	[infMunicipio] [bigint] NOT NULL,
	[infTipoTenencia] [bigint] NOT NULL,
	[infLatitud] [numeric](9, 6) NOT NULL,
	[infLongitud] [numeric](9, 6) NOT NULL,
	[infActivo] [bit] NOT NULL,
	[infCapacidadEstimada] [numeric](7, 2) NULL,
 CONSTRAINT [PK_Infraestructura_infId] PRIMARY KEY CLUSTERED 
(
	[infId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [InhabilidadSubsidioFovis](
	[isfId] [bigint] IDENTITY(1,1) NOT NULL,
	[isfJefeHogar] [bigint] NULL,
	[isfIntegranteHogar] [bigint] NULL,
	[isfFechaInicio] [datetime] NULL,
	[isfFechaFin] [datetime] NULL,
	[isfInhabilitadoParaSubsidio] [bit] NULL,
 CONSTRAINT [PK_InhabilidadSubsidioFovis_isfId] PRIMARY KEY CLUSTERED 
(
	[isfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [InicioTarea](
	[itaId] [bigint] IDENTITY(1,1) NOT NULL,
	[itaFecha] [bigint] NULL,
	[itaProceso] [bigint] NULL,
	[itaTarea] [bigint] NULL,
	[itaUsuario] [varchar](255) NULL,
 CONSTRAINT [PK_InicioTarea_idaId] PRIMARY KEY CLUSTERED 
(
	[itaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntegranteHogar](
	[inhId] [bigint] IDENTITY(1,1) NOT NULL,
	[inhJefeHogar] [bigint] NOT NULL,
	[inhPersona] [bigint] NOT NULL,
	[inhIntegranteReemplazaJefeHogar] [bit] NULL,
	[inhTipoIntegrante] [varchar](32) NOT NULL,
	[inhEstadoHogar] [varchar](10) NULL,
	[inhIntegranteValido] [bit] NULL,
	[inhSalarioMensual] [numeric](19, 5) NULL,
 CONSTRAINT [PK_IntegranteHogar_inhId] PRIMARY KEY CLUSTERED 
(
	[inhId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoLegalizacionDesembolso](
	[ildId] [bigint] IDENTITY(1,1) NOT NULL,
	[ildCausaIntentoFallido] [varchar](50) NULL,
	[ildFechaCreacion] [datetime] NULL,
	[ildSedeCajaCompensacion] [varchar](2) NULL,
	[ildUsuarioCreacion] [varchar](255) NULL,
	[ildSolicitud] [bigint] NULL,
	[ildProceso] [varchar](32) NULL,
	[ildTipoSolicitante] [varchar](5) NULL,
	[ildModalidad] [varchar](33) NULL,
 CONSTRAINT [PK_IntentoLegalizacionDesembolso_ildId] PRIMARY KEY CLUSTERED 
(
	[ildId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoLegalizacionDesembolsoRequisito](
	[ilrId] [bigint] IDENTITY(1,1) NOT NULL,
	[ilrIntentoLegalizacionDesembolso] [bigint] NULL,
	[ilrRequisito] [bigint] NULL,
 CONSTRAINT [PK_IntentoLegalizacionDesembolsoRequisito_ilrId] PRIMARY KEY CLUSTERED 
(
	[ilrId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoNovedad](
	[inoId] [bigint] IDENTITY(1,1) NOT NULL,
	[inoCausaIntentoFallido] [varchar](255) NULL,
	[inoFechaInicioProceso] [datetime2](7) NULL,
	[inoSolicitud] [bigint] NOT NULL,
	[inoTipoTransaccion] [varchar](100) NULL,
 CONSTRAINT [PK_IntentoNovedad_inoId] PRIMARY KEY CLUSTERED 
(
	[inoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoPostulacion](
	[ipoId] [bigint] IDENTITY(1,1) NOT NULL,
	[ipoCausaIntentoFallido] [varchar](50) NULL,
	[ipoFechaCreacion] [datetime] NULL,
	[ipoFechaInicioProceso] [datetime] NULL,
	[ipoSedeCajaCompensacion] [varchar](2) NULL,
	[ipoTipoTransaccion] [varchar](100) NULL,
	[ipoUsuarioCreacion] [varchar](255) NULL,
	[ipoSolicitud] [bigint] NULL,
	[ipoProceso] [varchar](32) NULL,
	[ipoTipoSolicitante] [varchar](5) NULL,
	[ipoModalidad] [varchar](33) NULL,
 CONSTRAINT [PK_IntentoPostulacion_ipoId] PRIMARY KEY CLUSTERED 
(
	[ipoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoPostulacionRequisito](
	[iprId] [bigint] IDENTITY(1,1) NOT NULL,
	[iprIntentoPostulacion] [bigint] NULL,
	[iprRequisito] [bigint] NULL,
 CONSTRAINT [PK_IntentoPostulacionRequisito_iprId] PRIMARY KEY CLUSTERED 
(
	[iprId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [JefeHogar](
	[jehId] [bigint] IDENTITY(1,1) NOT NULL,
	[jehAfiliado] [bigint] NOT NULL,
	[jehEstadoHogar] [varchar](10) NULL,
 CONSTRAINT [PK_JefeHogar_jehId] PRIMARY KEY CLUSTERED 
(
	[jehId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [LegalizacionDesembolso](
	[lgdId] [bigint] IDENTITY(1,1) NOT NULL,
	[lgdFormaPago] [varchar](50) NULL,
	[lgdValorDesembolsar] [numeric](19, 5) NULL,
	[lgdFechaLimitePago] [datetime] NULL,
	[lgdVisita] [bigint] NULL,
	[lgdSubsidioDesembolsado] [bit] NULL,
	[lgdTipoMedioPago] [varchar](30) NULL,
 CONSTRAINT [PK_LegalizacionDesembolso_legId] PRIMARY KEY CLUSTERED 
(
	[lgdId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Licencia](
	[licId] [bigint] IDENTITY(1,1) NOT NULL,
	[licEntidadExpide] [varchar](21) NULL,
	[licNumeroLicencia] [varchar](50) NULL,
	[licMatriculaInmobiliaria] [varchar](50) NULL,
	[licProyectoSolucionVivienda] [bigint] NOT NULL,
	[licTipoLicencia] [varchar](21) NULL,
 CONSTRAINT [PK_Licencia_licId] PRIMARY KEY CLUSTERED 
(
	[licId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [LicenciaDetalle](
	[lidId] [bigint] IDENTITY(1,1) NOT NULL,
	[lidNumeroResolucion] [varchar](50) NULL,
	[lidFechaInicio] [date] NULL,
	[lidFechaFin] [date] NULL,
	[lidLicencia] [bigint] NOT NULL,
	[lidClasificacionLicencia] [varchar](33) NULL,
	[lidEstadoLicencia] [bit] NULL,
 CONSTRAINT [PK_LicenciaDetalle_lidId] PRIMARY KEY CLUSTERED 
(
	[lidId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [LineaCobro](
	[pgcId] [bigint] NOT NULL,
	[lcoHabilitarAccionCobroA] [bit] NULL,
	[lcoDiasLimitePago] [bigint] NULL,
	[lcoDiasParametrizados] [bigint] NULL,
	[lcoHabilitarAccionCobroB] [bit] NULL,
	[lcoTipoLineaCobro] [varchar](3) NULL,
 CONSTRAINT [PK_LineaCobro_pgcId] PRIMARY KEY CLUSTERED 
(
	[pgcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioCheque](
	[mdpId] [bigint] NOT NULL,
	[mecTipoIdentificacionTitular] [varchar](20) NULL,
	[mecNumeroIdentificacionTitular] [varchar](16) NULL,
	[mecDigitoVerificacionTitular] [smallint] NULL,
	[mecNombreTitularCuenta] [varchar](200) NULL,
 CONSTRAINT [PK_MedioCheque_mdpId] PRIMARY KEY CLUSTERED 
(
	[mdpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioConsignacion](
	[mdpId] [bigint] NOT NULL,
	[mcoBanco] [bigint] NOT NULL,
	[mcoTipoCuenta] [varchar](30) NOT NULL,
	[mcoNumeroCuenta] [varchar](30) NOT NULL,
	[mcoTipoIdentificacionTitular] [varchar](20) NULL,
	[mcoNumeroIdentificacionTitular] [varchar](16) NULL,
	[mcoDigitoVerificacionTitular] [smallint] NULL,
	[mcoNombreTitularCuenta] [varchar](200) NULL,
 CONSTRAINT [PK_MedioConsignacion_mdpId] PRIMARY KEY CLUSTERED 
(
	[mdpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioDePago](
	[mdpId] [bigint] IDENTITY(1,1) NOT NULL,
	[mdpTipo] [varchar](30) NOT NULL,
 CONSTRAINT [PK_MedioDePago_mdpId] PRIMARY KEY CLUSTERED 
(
	[mdpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioEfectivo](
	[mdpId] [bigint] NOT NULL,
	[mefEfectivo] [bit] NOT NULL,
	[mefSitioPago] [bigint] NULL,
	[mefSedeCajaCompensacion] [bigint] NULL,
 CONSTRAINT [PK_MedioEfectivo_mdpId] PRIMARY KEY CLUSTERED 
(
	[mdpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioPagoPersona](
	[mppId] [bigint] IDENTITY(1,1) NOT NULL,
	[mppMedioPago] [bigint] NOT NULL,
	[mppPersona] [bigint] NOT NULL,
	[mppMedioActivo] [bit] NOT NULL,
 CONSTRAINT [PK_MedioPagoPersona_mppId] PRIMARY KEY CLUSTERED 
(
	[mppId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioPagoProyectoVivienda](
	[mprId] [bigint] IDENTITY(1,1) NOT NULL,
	[mprProyectoSolucionVivienda] [bigint] NOT NULL,
	[mprMedioDePago] [bigint] NOT NULL,
	[mprActivo] [bit] NULL,
 CONSTRAINT [PK_MedioPagoProyectoVivienda_mprId] PRIMARY KEY CLUSTERED 
(
	[mprId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MediosPagoCCF](
	[mpcId] [bigint] IDENTITY(1,1) NOT NULL,
	[mpcCajaCompensacion] [int] NOT NULL,
	[mpcMedioPago] [varchar](30) NOT NULL,
	[mpcMedioPreferente] [bit] NOT NULL,
	[mpcAplicaFOVIS] [bit] NULL,
 CONSTRAINT [PK_MediosPagoCCF_mpcId] PRIMARY KEY CLUSTERED 
(
	[mpcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioTarjeta](
	[mdpId] [bigint] NOT NULL,
	[mtrNumeroTarjeta] [varchar](50) NOT NULL,
	[mtrDisponeTarjeta] [bit] NOT NULL,
	[mtrEstadoTarjetaMultiservicios] [varchar](30) NOT NULL,
	[mtrSolicitudTarjeta] [varchar](30) NOT NULL,
 CONSTRAINT [PK_MedioTarjeta_mdpId] PRIMARY KEY CLUSTERED 
(
	[mdpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioTransferencia](
	[mdpId] [bigint] NOT NULL,
	[metBanco] [bigint] NOT NULL,
	[metTipoCuenta] [varchar](30) NOT NULL,
	[metNumeroCuenta] [varchar](30) NOT NULL,
	[metTipoIdentificacionTitular] [varchar](20) NULL,
	[metNumeroIdentificacionTitular] [varchar](16) NULL,
	[metDigitoVerificacionTitular] [smallint] NULL,
	[metNombreTitularCuenta] [varchar](200) NULL,
 CONSTRAINT [PK_MedioTransferencia_mdpId] PRIMARY KEY CLUSTERED 
(
	[mdpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MotivoNoGestionCobro](
	[mgcId] [bigint] IDENTITY(1,1) NOT NULL,
	[mgcCartera] [bigint] NOT NULL,
	[mgcTipo] [varchar](36) NULL,
 CONSTRAINT [PK_MotivoNoGestionCobro_mgcId] PRIMARY KEY CLUSTERED 
(
	[mgcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MovimientoAjusteAporte](
	[maaId] [bigint] IDENTITY(1,1) NOT NULL,
	[maaTipoMovimientoRecaudoAporte] [varchar](40) NULL,
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MovimientoAporte](
	[moaId] [bigint] IDENTITY(1,1) NOT NULL,
	[moaTipoAjuste] [varchar](20) NULL,
	[moaTipoMovimiento] [varchar](23) NULL,
	[moaEstadoAporte] [varchar](22) NULL,
	[moaValorAporte] [numeric](19, 5) NULL,
	[moaValorInteres] [numeric](19, 5) NULL,
	[moaFechaActualizacionEstado] [datetime] NULL,
	[moaFechaCreacion] [datetime] NULL,
	[moaAporteDetallado] [bigint] NULL,
	[moaAporteGeneral] [bigint] NOT NULL,
 CONSTRAINT [PK_MovimientoAporte_moaId] PRIMARY KEY CLUSTERED 
(
	[moaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Municipio](
	[munId] [smallint] IDENTITY(1,1) NOT NULL,
	[munCodigo] [varchar](5) NOT NULL,
	[munNombre] [varchar](50) NOT NULL,
	[munDepartamento] [smallint] NOT NULL,
 CONSTRAINT [PK_Municipio_munId] PRIMARY KEY CLUSTERED 
(
	[munId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [NotificacionPersonal](
	[ntpId] [bigint] IDENTITY(1,1) NOT NULL,
	[ntpActividad] [varchar](41) NOT NULL,
	[ntpPersona] [bigint] NOT NULL,
	[ntpTipoSolicitante] [varchar](13) NOT NULL,
	[ntpComentario] [varchar](250) NULL,
 CONSTRAINT [PK_NotificacionPersonal_ntpId] PRIMARY KEY CLUSTERED 
(
	[ntpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [NotificacionPersonalDocumento](
	[ntdId] [bigint] IDENTITY(1,1) NOT NULL,
	[ntdDocumentoSoporte] [bigint] NOT NULL,
	[ntdNotificacionPersonal] [bigint] NOT NULL,
 CONSTRAINT [PK_NotificacionPersonalDocumento_ntdId] PRIMARY KEY CLUSTERED 
(
	[ntdId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [NovedadDetalle](
	[nopId] [bigint] IDENTITY(1,1) NOT NULL,
	[nopFechaInicio] [date] NULL,
	[nopFechaFin] [date] NULL,
	[nopVigente] [bit] NULL,
	[nopSolicitudNovedad] [bigint] NULL,
 CONSTRAINT [PK_NovedadPila_nopId] PRIMARY KEY CLUSTERED 
(
	[nopId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [OcupacionProfesion](
	[ocuId] [int] IDENTITY(1,1) NOT NULL,
	[ocuNombre] [varchar](100) NOT NULL,
 CONSTRAINT [PK_OcupacionProfesion_ocuId] PRIMARY KEY CLUSTERED 
(
	[ocuId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Oferente](
	[ofeId] [bigint] IDENTITY(1,1) NOT NULL,
	[ofePersona] [bigint] NOT NULL,
	[ofeEmpresa] [bigint] NULL,
	[ofeEstado] [varchar](30) NULL,
	[ofeCuentaBancaria] [bit] NULL,
	[ofeBanco] [bigint] NULL,
	[ofeTipoCuenta] [varchar](30) NULL,
	[ofeNumeroCuenta] [varchar](30) NULL,
	[ofeTipoIdentificacionTitular] [varchar](20) NULL,
	[ofeNumeroIdentificacionTitular] [varchar](16) NULL,
	[ofeDigitoVerificacionTitular] [smallint] NULL,
	[ofeNombreTitularCuenta] [varchar](200) NULL,
 CONSTRAINT [PK_Oferente_ofeId] PRIMARY KEY CLUSTERED 
(
	[ofeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PagoPeriodoConvenio](
	[ppcId] [bigint] IDENTITY(1,1) NOT NULL,
	[ppcConvenioPago] [bigint] NOT NULL,
	[ppcFechaPago] [date] NOT NULL,
	[ppcValorCuota] [numeric](19, 5) NOT NULL,
	[ppcPeriodo] [date] NOT NULL,
 CONSTRAINT [PK_PagoPeriodoConvenio_ppcId] PRIMARY KEY CLUSTERED 
(
	[ppcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionCartera](
	[pacId] [bigint] IDENTITY(1,1) NOT NULL,
	[pacAplicar] [bit] NULL,
	[pacIncluirIndependientes] [bit] NULL,
	[pacIncluirPensionados] [bit] NULL,
	[pacEstadoCartera] [varchar](7) NULL,
	[pacValorPromedioAportes] [varchar](16) NULL,
	[pacCantidadPeriodos] [smallint] NULL,
	[pacTrabajadoresActivos] [varchar](18) NULL,
	[pacPeriodosMorosos] [varchar](21) NULL,
	[pacMayorValorPromedio] [smallint] NULL,
	[pacMayorTrabajadoresActivos] [smallint] NULL,
	[pacMayorVecesMoroso] [smallint] NULL,
	[pacTipoParametrizacion] [varchar](31) NULL,
	[pacFechaActualizacion] [datetime] NULL,
 CONSTRAINT [PK_ParametrizacionCartera_pacId] PRIMARY KEY CLUSTERED 
(
	[pacId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionCondicionesSubsidio](
	[pcsId] [bigint] IDENTITY(1,1) NOT NULL,
	[pcsAnioVigenciaParametrizacion] [int] NOT NULL,
	[pcsPeriodoInicio] [date] NOT NULL,
	[pcsPeriodoFin] [date] NOT NULL,
	[pcsValorCuotaAnualBase] [numeric](19, 5) NOT NULL,
	[pcsValorCuotaAnualAgraria] [numeric](19, 5) NOT NULL,
	[pcsProgramaDecreto4904] [bit] NOT NULL,
	[pcsRetroactivoNovInvalidez] [bit] NOT NULL,
	[pcsRetroactivoReingresoEmpleadores] [bit] NOT NULL,
	[pcsCantidadSubsidiosLiquidados] [int] NULL,
	[pcsMontoSubsidiosLiquidados] [numeric](10, 0) NULL,
	[pcsCantidadSubsidiosLiquidadosInvalidez] [int] NULL,
	[pcsCantidadPeriodosRetroactivosMes] [int] NULL,
	[pcsDiasNovedadFallecimiento] [int] NULL,
	[pcsCodigoCajaCompensacion] [varchar](5) NULL,
 CONSTRAINT [PK_ParametrizacionCondicionesSubsidio_pcsId] PRIMARY KEY CLUSTERED 
(
	[pcsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionConveniosPago](
	[pcpId] [bigint] IDENTITY(1,1) NOT NULL,
	[pcpCantidadPeriodos] [smallint] NULL,
	[pcpNumeroConveniosPermitido] [smallint] NULL,
 CONSTRAINT [PK_ParametrizacionConveniosPago_pcpId] PRIMARY KEY CLUSTERED 
(
	[pcpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionCriterioGestionCobro](
	[pacId] [bigint] NOT NULL,
	[pcrLineaCobro] [varchar](3) NULL,
	[pcrActiva] [bit] NULL,
	[pcrMetodo] [varchar](10) NULL,
	[pcrAccion] [varchar](10) NULL,
	[pcrCorteEntidad] [bigint] NULL,
 CONSTRAINT [PK_ParametrizacionCriterioGestionCobro_pacId] PRIMARY KEY CLUSTERED 
(
	[pacId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionDesafiliacion](
	[pdeId] [bigint] IDENTITY(1,1) NOT NULL,
	[pdeLineaCobro] [varchar](3) NOT NULL,
	[pdeProgramacionEjecucion] [varchar](8) NULL,
	[pdeMontoMoraInexactitud] [numeric](19, 5) NULL,
	[pdePeriodosMora] [bigint] NULL,
	[pdeMetodoEnvioComunicado] [varchar](11) NULL,
	[pdeOficinaPrincipalFisico] [bit] NULL,
	[pdeCorrespondenciaFisico] [bit] NULL,
	[pdeNotificacionJudicialFisico] [bit] NULL,
	[pdeOficinaPrincipalElectronico] [bit] NULL,
	[pdeRepresentanteLegalElectronico] [bit] NULL,
	[pdeResponsableAportesElectronico] [bit] NULL,
	[pdeSiguienteAccion] [varchar](29) NULL,
	[pdeHabilitado] [bit] NOT NULL,
 CONSTRAINT [PK_ParametrizacionDesafiliacion_pdeId] PRIMARY KEY CLUSTERED 
(
	[pdeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
	[pepEstado] [varchar](8) NULL,
 CONSTRAINT [PK_ParametrizacionEjecucionProgramada_pepID] PRIMARY KEY CLUSTERED 
(
	[pepId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionExclusiones](
	[pexId] [bigint] IDENTITY(1,1) NOT NULL,
	[pexExclusionNegocios] [bit] NULL,
	[pexImposicionRecurso] [bit] NULL,
	[pexConvenioPago] [bit] NULL,
	[pexAclaracionMora] [bit] NULL,
	[pexRiesgoIncobrabilidad] [bit] NULL,
 CONSTRAINT [PK_ParametrizacionExclusiones_pexId] PRIMARY KEY CLUSTERED 
(
	[pexId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionFiscalizacion](
	[pacId] [bigint] NOT NULL,
	[pfiAlertaValidacionPila] [bit] NULL,
	[pfiEstadoAporteNoOk] [bit] NULL,
	[pfiIbcMenorUltimo] [bit] NULL,
	[pfiNovedadRetiro] [bit] NULL,
	[pfiPeriodosRetroactivos] [smallint] NULL,
	[pfiSalarioMenorUltimo] [bit] NULL,
	[pfiCorteEntidades] [bigint] NULL,
	[pfiGestionPreventiva] [bit] NULL,
 CONSTRAINT [PK_ParametrizacionFiscalizacion_pacId] PRIMARY KEY CLUSTERED 
(
	[pacId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionFOVIS](
	[pafId] [bigint] IDENTITY(1,1) NOT NULL,
	[pafNombre] [varchar](50) NOT NULL,
	[pafValor] [bit] NULL,
	[pafValorNumerico] [numeric](4, 1) NULL,
	[pafPlazoVencimiento] [varchar](15) NULL,
	[pafPlazoAdicional] [varchar](15) NULL,
	[pafValorAdicional] [numeric](4, 1) NULL,
	[pafValorString] [varchar](30) NULL,
 CONSTRAINT [PK_ParametrizacionFOVIS_pafId] PRIMARY KEY CLUSTERED 
(
	[pafId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_ParametrizacionFOVIS_pafNombre] UNIQUE NONCLUSTERED 
(
	[pafNombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionGestionCobro](
	[pgcId] [bigint] IDENTITY(1,1) NOT NULL,
	[pgcOficinaPrincipalFisico] [bit] NULL,
	[pgcCorrespondenciaFisico] [bit] NULL,
	[pgcNotificacionJudicialFisico] [bit] NULL,
	[pgcOficinaPrincipalElectronico] [bit] NULL,
	[pgcRepresentanteLegalElectronico] [bit] NULL,
	[pgcResponsableAportesElectronico] [bit] NULL,
	[pgcMetodoEnvioComunicado] [varchar](11) NULL,
	[pgcTipoParametrizacion] [varchar](18) NULL,
 CONSTRAINT [PK_ParametrizacionGestionCobro_pgcId] PRIMARY KEY CLUSTERED 
(
	[pgcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionLiquidacionSubsidio](
	[plsId] [bigint] IDENTITY(1,1) NOT NULL,
	[plsAnioVigenciaParametrizacion] [int] NOT NULL,
	[plsPeriodoInicio] [date] NOT NULL,
	[plsPeriodoFin] [date] NOT NULL,
	[plsFactorCuotaInvalidez] [numeric](19, 5) NOT NULL,
	[plsFactorPorDefuncion] [numeric](19, 5) NOT NULL,
	[plsHorasTrabajadas] [int] NOT NULL,
	[plsSMLMV] [numeric](19, 5) NULL,
 CONSTRAINT [PK_ParametrizacionLiquidacionSubsidio_psuId] PRIMARY KEY CLUSTERED 
(
	[plsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionMetodoAsignacion](
	[pmaId] [bigint] IDENTITY(1,1) NOT NULL,
	[pmaSedeCajaCompensacion] [bigint] NOT NULL,
	[pmaProceso] [varchar](100) NOT NULL,
	[pmaMetodoAsignacion] [varchar](20) NULL,
	[pmaUsuario] [varchar](255) NULL,
	[pmaGrupo] [varchar](50) NULL,
	[pmaSedeCajaDestino] [bigint] NULL,
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionModalidad](
	[pmoId] [bigint] IDENTITY(1,1) NOT NULL,
	[pmoNombre] [varchar](50) NULL,
	[pmoEstado] [bit] NULL,
	[pmoTopeSMLMV] [numeric](4, 1) NULL,
	[pmoTopeAvaluoCatastral] [numeric](4, 1) NULL,
 CONSTRAINT [PK_ParametrizacionModalidad_pmoId] PRIMARY KEY CLUSTERED 
(
	[pmoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionNovedad](
	[novId] [bigint] IDENTITY(1,1) NOT NULL,
	[novTipoTransaccion] [varchar](100) NULL,
	[novPuntoResolucion] [varchar](255) NULL,
	[novRutaCualificada] [varchar](255) NULL,
	[novTipoNovedad] [varchar](255) NULL,
	[novProceso] [varchar](50) NULL,
	[novAplicaTodosRoles] [bit] NULL,
 CONSTRAINT [PK_Novedad_novId] PRIMARY KEY CLUSTERED 
(
	[novId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionPreventiva](
	[pacId] [bigint] NOT NULL,
	[pprDiasHabilesPrevios] [smallint] NULL,
	[pprHoraEjecucion] [datetime] NULL,
	[pprEjecucionAutomatica] [bit] NULL,
 CONSTRAINT [PK_ParametrizacionPreventiva_pacId] PRIMARY KEY CLUSTERED 
(
	[pacId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionSubsidioAjuste](
	[psaId] [bigint] IDENTITY(1,1) NOT NULL,
	[psaPeriodoLiquidacion] [bigint] NOT NULL,
	[psaValorCuotaAjuste] [numeric](19, 5) NOT NULL,
	[psaValorCuotaAgrariaAjuste] [numeric](19, 5) NOT NULL,
 CONSTRAINT [PK_ParametrizacionSubsidioAjuste_psaId] PRIMARY KEY CLUSTERED 
(
	[psaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Parametro](
	[prmId] [int] IDENTITY(1,1) NOT NULL,
	[prmNombre] [varchar](100) NULL,
	[prmValor] [varchar](150) NULL,
	[prmCargaInicio] [bit] NULL,
	[prmSubCategoriaParametro] [varchar](23) NULL,
	[prmDescripcion] [varchar](250) NOT NULL,
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Periodo](
	[priId] [bigint] IDENTITY(1,1) NOT NULL,
	[priPeriodo] [date] NOT NULL,
 CONSTRAINT [PK_Periodo_priId] PRIMARY KEY CLUSTERED 
(
	[priId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_Periodo_priPeriodo] UNIQUE NONCLUSTERED 
(
	[priPeriodo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PeriodoBeneficio](
	[pbeId] [bigint] IDENTITY(1,1) NOT NULL,
	[pbePeriodo] [smallint] NOT NULL,
	[pbePorcentaje] [numeric](5, 5) NULL,
	[pbeBeneficio] [bigint] NOT NULL,
 CONSTRAINT [PK_PeriodoBeneficio_pbeId] PRIMARY KEY CLUSTERED 
(
	[pbeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PeriodoExclusionMora](
	[pemId] [bigint] IDENTITY(1,1) NOT NULL,
	[pemPeriodo] [date] NOT NULL,
	[pemExclusionCartera] [bigint] NOT NULL,
	[pemEstadoPeriodo] [varchar](10) NOT NULL,
 CONSTRAINT [PK_PeriodoExclusionMora_pemId] PRIMARY KEY CLUSTERED 
(
	[pemId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PeriodoLiquidacion](
	[pelId] [bigint] IDENTITY(1,1) NOT NULL,
	[pelSolicitudLiquidacionSubsidio] [bigint] NOT NULL,
	[pelPeriodo] [bigint] NOT NULL,
	[pelTipoPeriodo] [varchar](10) NOT NULL,
 CONSTRAINT [PK_PeriodoLiquidacion_pelId] PRIMARY KEY CLUSTERED 
(
	[pelId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
	[perCreadoPorPila] [bit] NULL,
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PersonaDetalle](
	[pedId] [bigint] IDENTITY(1,1) NOT NULL,
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
	[pedEstadoCivil] [varchar](20) NULL,
	[pedHabitaCasaPropia] [bit] NULL,
	[pedFallecido] [bit] NULL,
	[pedFechaFallecido] [date] NULL,
	[pedBeneficiarioSubsidio] [bit] NULL,
	[pedEstudianteTrabajoDesarrolloHumano] [bit] NULL,
 CONSTRAINT [PK_PersonaDetalle_pedId] PRIMARY KEY CLUSTERED 
(
	[pedId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_PersonaDetalle_pedPersona] UNIQUE NONCLUSTERED 
(
	[pedPersona] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PersonaLiquidacionEspecifica](
	[pleId] [bigint] IDENTITY(1,1) NOT NULL,
	[pleSolicitudLiquidacionSubsidio] [bigint] NOT NULL,
	[pleEmpleador] [bigint] NOT NULL,
	[pleAfiliadoPrincipal] [bigint] NULL,
	[pleBeneficiarioDetalle] [bigint] NULL,
	[pleGrupoFamiliar] [bigint] NULL,
 CONSTRAINT [PK_PersonaLiquidacionEspecifica_pleId] PRIMARY KEY CLUSTERED 
(
	[pleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PlantillaComunicado](
	[pcoId] [bigint] IDENTITY(1,1) NOT NULL,
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_PlantillaComunicado_pcoNombre_pcoEtiqueta] UNIQUE NONCLUSTERED 
(
	[pcoNombre] ASC,
	[pcoEtiqueta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PostulacionFOVIS](
	[pofId] [bigint] IDENTITY(1,1) NOT NULL,
	[pofCicloAsignacion] [bigint] NULL,
	[pofJefeHogar] [bigint] NULL,
	[pofEstadoHogar] [varchar](58) NULL,
	[pofCondicionHogar] [varchar](44) NULL,
	[pofHogarPerdioSubsidioNoPago] [bit] NULL,
	[pofCantidadFolios] [smallint] NULL,
	[pofValorSFVSolicitado] [numeric](19, 5) NULL,
	[pofProyectoSolucionVivienda] [bigint] NULL,
	[pofModalidad] [varchar](50) NULL,
	[pofSolicitudAsignacion] [bigint] NULL,
	[pofResultadoAsignacion] [varchar](50) NULL,
	[pofValorAsignadoSFV] [numeric](19, 5) NULL,
	[pofIdentificardorDocumentoActaAsignacion] [varchar](255) NULL,
	[pofPuntaje] [numeric](5, 2) NULL,
	[pofFechaCalificacion] [datetime] NULL,
	[pofPrioridadAsignacion] [varchar](11) NULL,
	[pofValorCalculadoSFV] [numeric](19, 5) NULL,
	[pofValorProyectoVivienda] [numeric](19, 5) NULL,
	[pofMotivoDesistimiento] [varchar](29) NULL,
	[pofMotivoRechazo] [varchar](51) NULL,
	[pofMotivoHabilitacion] [varchar](38) NULL,
	[pofRestituidoConSancion] [bit] NULL,
	[pofTiempoSancion] [varchar](10) NULL,
	[pofMotivoRestitucion] [varchar](45) NULL,
	[pofMotivoEnajenacion] [varchar](40) NULL,
	[pofValorAjusteIPCSFV] [numeric](19, 5) NULL,
 CONSTRAINT [PK_PostulacionFOVIS_pofId] PRIMARY KEY CLUSTERED 
(
	[pofId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PrioridadDestinatario](
	[prdId] [bigint] IDENTITY(1,1) NOT NULL,
	[prdDestinatarioComunicado] [bigint] NOT NULL,
	[prdGrupoPrioridad] [bigint] NOT NULL,
	[prdPrioridad] [smallint] NULL,
 CONSTRAINT [PK_PrioridadDestinatario_prdId] PRIMARY KEY CLUSTERED 
(
	[prdId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
	[pncValorCampoBack] [varchar](300) NULL,
	[pncValorCampoFront] [varchar](300) NULL,
	[pncSolicitud] [bigint] NULL,
	[pncBeneficiario] [bigint] NULL,
	[pncNombreInconsistencia] [varchar](50) NULL,
	[pncDescripcionInconsistencia] [varchar](150) NULL,
	[pncClasificacionTipoProducto] [varchar](15) NULL,
 CONSTRAINT [PK_ProductoNoConforme_pncId] PRIMARY KEY CLUSTERED 
(
	[pncId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ProyectoSolucionVivienda](
	[psvId] [bigint] IDENTITY(1,1) NOT NULL,
	[psvNombreProyecto] [varchar](250) NULL,
	[psvMatriculaInmobiliariaInmueble] [varchar](50) NULL,
	[psvLoteUrbanizado] [bit] NULL,
	[psvFechaRegistroEscritura] [date] NULL,
	[psvNumeroEscritura] [varchar](20) NULL,
	[psvFechaEscritura] [date] NULL,
	[psvAvaluoCatastral] [bigint] NULL,
	[psvObservaciones] [varchar](500) NULL,
	[psvOferente] [bigint] NULL,
	[psvUbicacionProyecto] [bigint] NULL,
	[psvUbicacionIgualProyecto] [bit] NULL,
	[psvUbicacionVivienda] [bigint] NULL,
	[psvNumeroDocumentoElegibilidad] [varchar](50) NULL,
	[psvCodigoProyectoElegibilidad] [varchar](50) NULL,
	[psvNombreEntidadElegibilidad] [varchar](50) NULL,
	[psvFechaElegibilidad] [date] NULL,
	[psvNumeroViviendaElegibilidad] [int] NULL,
	[psvTipoInmuebleElegibilidad] [varchar](50) NULL,
	[psvComentariosElegibilidad] [varchar](500) NULL,
	[psvModalidad] [varchar](50) NULL,
	[psvPoseedorOcupanteVivienda] [varchar](50) NULL,
	[psvRegistrado] [bit] NULL,
	[psvDisponeCuentaBancaria] [bit] NULL,
	[psvComparteCuentaOferente] [bit] NULL,
 CONSTRAINT [PK_ProyectoSolucionVivienda_psvId] PRIMARY KEY CLUSTERED 
(
	[psvId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RangoTopeValorSFV](
	[rtvId] [bigint] IDENTITY(1,1) NOT NULL,
	[rtvNombre] [varchar](50) NOT NULL,
	[rtvOperadorValorMinimo] [varchar](13) NOT NULL,
	[rtvValorMinimo] [numeric](3, 1) NOT NULL,
	[rtvOperadorValorMaximo] [varchar](13) NOT NULL,
	[rtvValorMaximo] [numeric](3, 1) NOT NULL,
	[rtvTopeSMLMV] [numeric](4, 1) NOT NULL,
	[rtvModalidad] [varchar](50) NOT NULL,
 CONSTRAINT [PK_FormaPagoModalidad_rtvId] PRIMARY KEY CLUSTERED 
(
	[rtvId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RecursoComplementario](
	[recId] [bigint] IDENTITY(1,1) NOT NULL,
	[recNombre] [varchar](26) NULL,
	[recEntidad] [varchar](50) NULL,
	[recFecha] [date] NULL,
	[recOtroRecurso] [varchar](255) NULL,
	[recValor] [numeric](19, 5) NULL,
	[recPostulacionFOVIS] [bigint] NOT NULL,
 CONSTRAINT [PK_RecursoComplementario_recId] PRIMARY KEY CLUSTERED 
(
	[recId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RegistroArchivoConsumosAnibol](
	[racId] [bigint] IDENTITY(1,1) NOT NULL,
	[racArchivoConsumosAnibol] [bigint] NOT NULL,
	[racTipoRegistroArchivoConsumo] [varchar](16) NOT NULL,
	[racCuentaAdministradorSubsidio] [bigint] NULL,
	[racFechaHoraCreacion] [datetime] NOT NULL,
	[racFechaHoraValidacion] [datetime] NOT NULL,
	[racBinCCF] [varchar](9) NOT NULL,
	[racNumeroTarjeta] [varchar](19) NOT NULL,
	[racNitEmpresa] [varchar](15) NOT NULL,
	[racCuentaRelacionada] [varchar](19) NOT NULL,
	[racDispositivoOrigen] [varchar](2) NOT NULL,
	[racDescripcionCobroSubsidio] [varchar](30) NOT NULL,
	[racDescripcionTransaccion] [varchar](30) NOT NULL,
	[racValorTransaccion] [numeric](17, 2) NOT NULL,
	[racValorDispensando] [numeric](17, 2) NOT NULL,
	[racFechaTransaccion] [varchar](8) NOT NULL,
	[racValorACobrar] [numeric](17, 2) NOT NULL,
	[racValorImpuestos] [numeric](17, 2) NOT NULL,
	[racTotalACobrar] [numeric](17, 2) NOT NULL,
	[racImpuestoEmergenciaEconomica] [numeric](17, 2) NOT NULL,
	[racIndicadorReverso] [char](1) NULL,
	[racRespuestaAutorizador] [varchar](2) NOT NULL,
	[racDescripcionRespuesta] [varchar](30) NOT NULL,
	[racCodigoAutorizacion] [varchar](6) NOT NULL,
	[racSubtipo] [varchar](3) NOT NULL,
	[racFechaAutorizador] [varchar](8) NOT NULL,
	[racHoraAutorizador] [varchar](9) NOT NULL,
	[racHoraDispositivo] [varchar](6) NOT NULL,
	[racNumeroReferencia] [varchar](12) NOT NULL,
	[racRed] [varchar](4) NOT NULL,
	[racNumeroDispositivo] [varchar](16) NOT NULL,
	[racCodigoEstablecimiento] [varchar](10) NOT NULL,
	[racCodigoCuentaBolsillo] [varchar](2) NOT NULL,
	[racEstadoRegistro] [varchar](30) NULL,
	[racTipoInconsistenciaResultadoValidacion] [varchar](34) NULL,
 CONSTRAINT [PK_RegistroArchivoConsumosAnibol_racId] PRIMARY KEY CLUSTERED 
(
	[racId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RegistroArchivoRetiroTerceroPagador](
	[rarId] [bigint] IDENTITY(1,1) NOT NULL,
	[rarCuentaAdministradorSubsidio] [bigint] NULL,
	[rarArchivoRetiroTerceroPagador] [bigint] NOT NULL,
	[rarEstado] [varchar](35) NULL,
	[rarIdTransaccionTerceroPagador] [varchar](200) NOT NULL,
	[rarTipoIdentificacionAdminSubsidio] [varchar](20) NOT NULL,
	[rarNumeroIdentificacionAdminSubsidio] [varchar](16) NOT NULL,
	[rarValorRealTransaccion] [numeric](19, 5) NOT NULL,
	[rarFechaTransaccion] [varchar](50) NOT NULL,
	[rarHoraTransaccion] [varchar](50) NOT NULL,
	[rarCodigoDepartamento] [varchar](2) NOT NULL,
	[rarCodigoMunicipio] [varchar](6) NOT NULL,
 CONSTRAINT [PK_RegistroArchivoRetiroTerceroPagador_rarId] PRIMARY KEY CLUSTERED 
(
	[rarId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RegistroEstadoAporte](
	[reaId] [bigint] IDENTITY(1,1) NOT NULL,
	[reaSolicitud] [bigint] NOT NULL,
	[reaActividad] [varchar](29) NULL,
	[reaEstadoSolicitud] [varchar](25) NULL,
	[reaFecha] [datetime] NULL,
	[reaComunicado] [bigint] NULL,
	[reaUsuario] [varchar](255) NULL,
 CONSTRAINT [PK_RegistroEstadoAporte_raeId] PRIMARY KEY CLUSTERED 
(
	[reaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RegistroNovedadFutura](
	[rnfId] [bigint] IDENTITY(1,1) NOT NULL,
	[rnfFechaInicio] [date] NULL,
	[rnfFechaFin] [date] NULL,
	[rnfTipoTransaccion] [varchar](90) NULL,
	[rnfCanalRecepcion] [varchar](30) NULL,
	[rnfComentarios] [varchar](250) NULL,
	[rnfPersona] [bigint] NULL,
	[rnfRegistroDetallado] [bigint] NULL,
	[rnfClasificacion] [varchar](48) NULL,
	[rnfEmpleador] [bigint] NULL,
	[rnfProcesada] [bit] NULL,
 CONSTRAINT [PK_RegistroNovedadFutura_coiId] PRIMARY KEY CLUSTERED 
(
	[rnfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RegistroOperacionTransaccionSubsidio](
	[rotId] [bigint] IDENTITY(1,1) NOT NULL,
	[rotAdministradorSubsidio] [bigint] NULL,
	[rotTipoOperacion] [varchar](65) NULL,
	[rotFechaHoraOperacionTransaccion] [datetime] NOT NULL,
	[rotUsuarioOperacionTransaccion] [varchar](255) NOT NULL,
	[rotParametrosIn] [varchar](500) NOT NULL,
	[rotParametrosOut] [varchar](255) NULL,
	[rotTipoTransaccion] [varchar](60) NULL,
 CONSTRAINT [PK_RegistroOperacionTransaccionSubsidio_rotId] PRIMARY KEY CLUSTERED 
(
	[rotId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RegistroPersonaInconsistente](
	[rpiId] [bigint] IDENTITY(1,1) NOT NULL,
	[rpiCargueMultipleSupervivencia] [bigint] NULL,
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Requisito](
	[reqId] [bigint] IDENTITY(1,1) NOT NULL,
	[reqDescripcion] [varchar](200) NOT NULL,
	[reqEstado] [varchar](20) NOT NULL,
 CONSTRAINT [PK_Requisito_reqId] PRIMARY KEY CLUSTERED 
(
	[reqId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
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

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ResultadoEjecucionProgramada](
	[repId] [bigint] IDENTITY(1,1) NOT NULL,
	[repProceso] [varchar](100) NOT NULL,
	[repFechaEjecucion] [datetime] NULL,
	[repResultadoEjecucion] [varchar](50) NULL,
	[repTipoResultadoProceso] [varchar](10) NULL,
 CONSTRAINT [PK_ResultadoEjecucionProgramada_repId] PRIMARY KEY CLUSTERED 
(
	[repId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RetiroPersonaAutorizadaCobroSubsidio](
	[rpaId] [bigint] IDENTITY(1,1) NOT NULL,
	[rpaPersonaAutorizada] [bigint] NOT NULL,
	[rpaCuentaAdministradorSubsidio] [bigint] NOT NULL,
	[rpaDocumentoSoporte] [bigint] NOT NULL,
 CONSTRAINT [PK_RetiroPersonaAutorizadaCobroSubsidio_rpaId] PRIMARY KEY CLUSTERED 
(
	[rpaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RolAfiliado](
	[roaId] [bigint] IDENTITY(1,1) NOT NULL,
	[roaCargo] [varchar](200) NULL,
	[roaClaseIndependiente] [varchar](50) NULL,
	[roaClaseTrabajador] [varchar](20) NULL,
	[roaEstadoAfiliado] [varchar](8) NULL,
	[roaEstadoEnEntidadPagadora] [varchar](20) NULL,
	[roaFechaIngreso] [date] NULL,
	[roaFechaRetiro] [datetime] NULL,
	[roaHorasLaboradasMes] [smallint] NULL,
	[roaIdentificadorAnteEntidadPagadora] [varchar](15) NULL,
	[roaPorcentajePagoAportes] [numeric](5, 5) NULL,
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
	[roaEstadoEnEntidadPagadoraPension] [varchar](20) NULL,
	[roaDiaHabilVencimientoAporte] [smallint] NULL,
	[roaMarcaExpulsion] [varchar](22) NULL,
 CONSTRAINT [PK_RolAfiliado_roaId] PRIMARY KEY CLUSTERED 
(
	[roaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SitioPago](
	[sipId] [bigint] IDENTITY(1,1) NOT NULL,
	[sipCodigo] [varchar](3) NOT NULL,
	[sipNombre] [varchar](255) NOT NULL,
	[sipInfraestructura] [bigint] NOT NULL,
	[sipActivo] [bit] NOT NULL,
 CONSTRAINT [PK_SitioPago_sipId] PRIMARY KEY CLUSTERED 
(
	[sipId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Solicitud](
	[solId] [bigint] IDENTITY(1,1) NOT NULL,
	[solCanalRecepcion] [varchar](21) NULL,
	[solFechaRadicacion] [datetime2](7) NULL,
	[solInstanciaProceso] [varchar](255) NULL,
	[solNumeroRadicacion] [varchar](12) NULL,
	[solUsuarioRadicacion] [varchar](255) NULL,
	[solCajaCorrespondencia] [bigint] NULL,
	[solTipoTransaccion] [varchar](100) NULL,
	[solCiudadUsuarioRadicacion] [varchar](255) NULL,
	[solEstadoDocumentacion] [varchar](50) NULL,
	[solMetodoEnvio] [varchar](20) NULL,
	[solClasificacion] [varchar](48) NULL,
	[solTipoRadicacion] [varchar](20) NULL,
	[solFechaCreacion] [datetime2](7) NULL,
	[solDestinatario] [varchar](255) NULL,
	[solSedeDestinatario] [varchar](2) NULL,
	[solObservacion] [varchar](500) NULL,
	[solCargaAfiliacionMultipleEmpleador] [bigint] NULL,
	[solResultadoProceso] [varchar](22) NULL,
	[solDiferenciasCargueActualizacion] [bigint] NULL,
	[solAnulada] [bit] NULL,
 CONSTRAINT [PK_Solicitud_solId] PRIMARY KEY CLUSTERED 
(
	[solId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudAnalisisNovedadFovis](
	[sanId] [bigint] NOT NULL,
	[sanSolicitudGlobal] [bigint] NOT NULL,
	[sanSolicitudNovedad] [bigint] NOT NULL,
	[sanPersona] [bigint] NOT NULL,
	[sanEstadoSolicitud] [varchar](9) NOT NULL,
	[sanPostulacionFovis] [bigint] NOT NULL,
	[sanObservaciones] [varchar](500) NULL,
 CONSTRAINT [PK_SolicitudAnalisisNovedadFovis_sanId] PRIMARY KEY CLUSTERED 
(
	[sanId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudAnulacionSubsidioCobrado](
	[sasId] [bigint] IDENTITY(1,1) NOT NULL,
	[sasFechaHoraCreacionSolicitud] [datetime] NOT NULL,
	[sasEstadoSolicitud] [varchar](20) NOT NULL,
	[sasMotivoAnulacion] [varchar](35) NOT NULL,
	[sasSolicitudGlobal] [bigint] NOT NULL,
	[sasObservaciones] [varchar](500) NULL,
 CONSTRAINT [PK_SolicitudAnulacionSubsidio_sasId] PRIMARY KEY CLUSTERED 
(
	[sasId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudAporte](
	[soaId] [bigint] IDENTITY(1,1) NOT NULL,
	[soaSolicitudGlobal] [bigint] NOT NULL,
	[soaEstadoSolicitud] [varchar](30) NULL,
	[soaAporteGeneral] [bigint] NULL,
	[soaNumeroIdentificacion] [varchar](16) NULL,
	[soaTipoIdentificacion] [varchar](20) NULL,
	[soaNombreAportante] [varchar](200) NULL,
	[soaPeriodoAporte] [varchar](7) NULL,
	[soaTipoSolicitante] [varchar](13) NULL,
 CONSTRAINT [PK_SolicitudAporte_soaId] PRIMARY KEY CLUSTERED 
(
	[soaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_SolicitudAporte_soaSolicitudGlobal] UNIQUE NONCLUSTERED 
(
	[soaSolicitudGlobal] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudAsignacion](
	[safId] [bigint] IDENTITY(1,1) NOT NULL,
	[safSolicitudGlobal] [bigint] NOT NULL,
	[safFechaAceptacion] [datetime] NULL,
	[safUsuario] [varchar](50) NULL,
	[safValorSFVAsignado] [numeric](19, 5) NULL,
	[safEstadoSolicitudAsignacion] [varchar](50) NULL,
	[safComentarios] [varchar](500) NULL,
	[safCicloAsignacion] [bigint] NOT NULL,
	[safComentarioControlInterno] [varchar](500) NULL,
 CONSTRAINT [PK_SolicitudAsignacion_safId] PRIMARY KEY CLUSTERED 
(
	[safId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudCorreccionAporte](
	[scaId] [bigint] IDENTITY(1,1) NOT NULL,
	[scaEstadoSolicitud] [varchar](25) NULL,
	[scaTipoSolicitante] [varchar](25) NULL,
	[scaObservacionSupervisor] [varchar](255) NULL,
	[scaResultadoSupervisor] [varchar](10) NULL,
	[scaSolicitudGlobal] [bigint] NULL,
	[scaPersona] [bigint] NULL,
	[scaAporteGeneral] [bigint] NULL,
 CONSTRAINT [PK_SolicitudCorreccionAporte_scaId] PRIMARY KEY CLUSTERED 
(
	[scaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudDesafiliacion](
	[sodId] [bigint] IDENTITY(1,1) NOT NULL,
	[sodComentarioCoordinador] [varchar](500) NULL,
	[sodEstadoSolicitud] [varchar](9) NULL,
	[sodSolicitudGlobal] [bigint] NOT NULL,
 CONSTRAINT [PK_SolicitudDesafiliacion_sodId] PRIMARY KEY CLUSTERED 
(
	[sodId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudDevolucionAporte](
	[sdaId] [bigint] IDENTITY(1,1) NOT NULL,
	[sdaEstadoSolicitud] [varchar](25) NULL,
	[sdaTipoSolicitante] [varchar](13) NULL,
	[sdaPersona] [bigint] NULL,
	[sdaObservacionAnalista] [varchar](255) NULL,
	[sdaObservacionSupervisor] [varchar](255) NULL,
	[sdaResultadoAnalista] [varchar](10) NULL,
	[sdaResultadoSupervisor] [varchar](10) NULL,
	[sdaDevolucionAporte] [bigint] NULL,
	[sdaSolicitudGlobal] [bigint] NULL,
 CONSTRAINT [PK_SolicitudDevolucionAporte_sdaId] PRIMARY KEY CLUSTERED 
(
	[sdaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudFiscalizacion](
	[sfiId] [bigint] IDENTITY(1,1) NOT NULL,
	[sfiEstadoFiscalizacion] [varchar](11) NULL,
	[sfiSolicitudGlobal] [bigint] NOT NULL,
	[sfiCicloAportante] [bigint] NOT NULL,
 CONSTRAINT [PK_SolicitudFiscalizacion_sfiId] PRIMARY KEY CLUSTERED 
(
	[sfiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudGestionCobroElectronico](
	[sgeId] [bigint] IDENTITY(1,1) NOT NULL,
	[sgeEstado] [varchar](52) NOT NULL,
	[sgeCartera] [bigint] NOT NULL,
	[sgeTipoAccionCobro] [varchar](4) NOT NULL,
	[sgeSolicitud] [bigint] NOT NULL,
 CONSTRAINT [PK_SolicitudGestionCobroElectronico_sgeId] PRIMARY KEY CLUSTERED 
(
	[sgeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudGestionCobroFisico](
	[sgfId] [bigint] IDENTITY(1,1) NOT NULL,
	[sgfDocumentoSoporte] [bigint] NULL,
	[sgfEstado] [varchar](52) NULL,
	[sgfFechaRemision] [datetime] NULL,
	[sgfObservacionRemision] [varchar](255) NULL,
	[sgfTipoAccionCobro] [varchar](4) NOT NULL,
	[sgfSolicitud] [bigint] NOT NULL,
 CONSTRAINT [PK_SolicitudGestionCobroFisico_sgfId] PRIMARY KEY CLUSTERED 
(
	[sgfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudGestionCobroManual](
	[scmId] [bigint] IDENTITY(1,1) NOT NULL,
	[scmCicloAportante] [bigint] NOT NULL,
	[scmEstadoSolicitud] [varchar](25) NULL,
	[scmSolicitudGlobal] [bigint] NOT NULL,
	[scmLineaCobro] [varchar](3) NOT NULL,
 CONSTRAINT [PK_SolicitudGestionCobroManual_scmId] PRIMARY KEY CLUSTERED 
(
	[scmId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudGestionCruce](
	[sgcId] [bigint] NOT NULL,
	[sgcSolicitudPostulacion] [bigint] NOT NULL,
	[sgcEstadoCruceHogar] [varchar](53) NULL,
	[sgcTipoCruce] [varchar](8) NULL,
	[sgcEstado] [varchar](32) NULL,
 CONSTRAINT [PK_SolicitudGestionCruce_sgcId] PRIMARY KEY CLUSTERED 
(
	[sgcId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudLegalizacionDesembolso](
	[sldId] [bigint] IDENTITY(1,1) NOT NULL,
	[sldSolicitudGlobal] [bigint] NOT NULL,
	[sldPostulacionFOVIS] [bigint] NULL,
	[sldEstadoSolicitud] [varchar](48) NULL,
	[sldLegalizacionDesembolso] [bigint] NULL,
	[sldObservaciones] [varchar](500) NULL,
	[sldFechaOperacion] [datetime] NULL,
	[sldJsonPostulacion] [text] NULL,
 CONSTRAINT [PK_SolicitudLegalizacionDesembolso_sldId] PRIMARY KEY CLUSTERED 
(
	[sldId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudLiquidacionSubsidio](
	[slsId] [bigint] IDENTITY(1,1) NOT NULL,
	[slsSolicitudGlobal] [bigint] NOT NULL,
	[slsFechaCorteAporte] [datetime] NULL,
	[slsFechaInicio] [datetime] NULL,
	[slsFechaFin] [datetime] NULL,
	[slsTipoLiquidacion] [varchar](33) NOT NULL,
	[slsTipoLiquidacionEspecifica] [varchar](32) NULL,
	[slsEstadoLiquidacion] [varchar](25) NOT NULL,
	[slsTipoEjecucionProceso] [varchar](10) NOT NULL,
	[slsFechaEjecucionProgramada] [datetime] NULL,
	[slsUsuarioEvaluacionPrimerNivel] [varchar](50) NULL,
	[slsObservacionesPrimerNivel] [varchar](250) NULL,
	[slsUsuarioEvaluacionSegundoNivel] [varchar](50) NULL,
	[slsObservacionesSegundoNivel] [varchar](250) NULL,
	[slsRazonRechazoLiquidacion] [varchar](250) NULL,
	[slsObservacionesProceso] [varchar](250) NULL,
	[slsFechaEvaluacionPrimerNivel] [datetime] NULL,
	[slsFechaEvaluacionSegundoNivel] [datetime] NULL,
	[slsCodigoReclamo] [varchar](50) NULL,
	[slsComentarioReclamo] [varchar](250) NULL,
	[slsFechaDispersion] [datetime] NULL,
	[slsConsideracionAporteDesembolso] [bit] NULL,
	[slsTipoDesembolso] [varchar](40) NULL,
 CONSTRAINT [PK_SolicitudLiquidacionSubsidio_slsId] PRIMARY KEY CLUSTERED 
(
	[slsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedad](
	[snoId] [bigint] IDENTITY(1,1) NOT NULL,
	[snoEstadoSolicitud] [varchar](50) NULL,
	[snoNovedad] [bigint] NULL,
	[snoSolicitudGlobal] [bigint] NULL,
	[snoObservaciones] [varchar](200) NULL,
	[snoCargaMultiple] [bit] NULL,
 CONSTRAINT [PK_SolicitudNovedad_snoId] PRIMARY KEY CLUSTERED 
(
	[snoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_SolicitudNovedad_snoNovedad_snoSolicitudGlobal] UNIQUE NONCLUSTERED 
(
	[snoNovedad] ASC,
	[snoSolicitudGlobal] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
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

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedadFovis](
	[snfId] [bigint] IDENTITY(1,1) NOT NULL,
	[snfSolicitudGlobal] [bigint] NOT NULL,
	[snfEstadoSolicitud] [varchar](38) NOT NULL,
	[snfParametrizacionNovedad] [bigint] NOT NULL,
	[snfObservaciones] [varchar](200) NULL,
 CONSTRAINT [PK_SolicitudNovedadFovis_snfId] PRIMARY KEY CLUSTERED 
(
	[snfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedadPersona](
	[snpId] [bigint] NOT NULL,
	[snpPersona] [bigint] NOT NULL,
	[snpSolicitudNovedad] [bigint] NOT NULL,
	[snpRolAfiliado] [bigint] NULL,
	[snpBeneficiario] [bigint] NULL,
 CONSTRAINT [PK_SolicitudNovedadPersona_snpId] PRIMARY KEY CLUSTERED 
(
	[snpId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedadPersonaFovis](
	[spfId] [bigint] NOT NULL,
	[spfPersona] [bigint] NULL,
	[spfSolicitudNovedadFovis] [bigint] NOT NULL,
	[spfPostulacionFovis] [bigint] NOT NULL,
 CONSTRAINT [PK_SolicitudNovedadPersonaFovis_spfId] PRIMARY KEY CLUSTERED 
(
	[spfId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedadPila](
	[spiId] [bigint] IDENTITY(1,1) NOT NULL,
	[spiSolicitudNovedad] [bigint] NOT NULL,
	[spiRegistroDetallado] [bigint] NOT NULL,
 CONSTRAINT [PK_SolicitudNovedadPila_spiId] PRIMARY KEY CLUSTERED 
(
	[spiId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudPostulacion](
	[spoId] [bigint] IDENTITY(1,1) NOT NULL,
	[spoSolicitudGlobal] [bigint] NOT NULL,
	[spoPostulacionFOVIS] [bigint] NULL,
	[spoEstadoSolicitud] [varchar](42) NULL,
	[spoObservaciones] [varchar](500) NULL,
	[spoObservacionesWeb] [varchar](500) NULL,
 CONSTRAINT [PK_SolicitudPostulacion_spoId] PRIMARY KEY CLUSTERED 
(
	[spoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudPreventiva](
	[sprId] [bigint] IDENTITY(1,1) NOT NULL,
	[sprActualizacionEfectiva] [bit] NULL,
	[sprBackActualizacion] [varchar](255) NULL,
	[sprContactoEfectivo] [bit] NULL,
	[sprEstadoSolicitudPreventiva] [varchar](34) NULL,
	[sprPersona] [bigint] NOT NULL,
	[sprRequiereFiscalizacion] [bit] NULL,
	[sprTipoSolicitanteMovimientoAporte] [varchar](14) NOT NULL,
	[sprSolicitudGlobal] [bigint] NOT NULL,
	[sprTipoGestionCartera] [varchar](10) NULL,
	[sprFechaFiscalizacion] [date] NULL,
 CONSTRAINT [PK_SolicitudPreventiva_sprId] PRIMARY KEY CLUSTERED 
(
	[sprId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SucursalEmpresa](
	[sueId] [bigint] IDENTITY(1,1) NOT NULL,
	[sueCodigo] [varchar](10) NULL,
	[sueNombre] [varchar](100) NULL,
	[sueCodigoCIIU] [smallint] NULL,
	[sueEmpresa] [bigint] NULL,
	[sueUbicacion] [bigint] NULL,
	[sueEstadoSucursal] [varchar](25) NULL,
	[sueCoindicirCodigoPila] [bit] NULL,
	[sueMedioPagoSubsidioMonetario] [varchar](30) NULL,
	[sueSucursalPrincipal] [bit] NULL,
 CONSTRAINT [PK_SucursalEmpresa_sueId] PRIMARY KEY CLUSTERED 
(
	[sueId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [TasasInteresMora](
	[timId] [bigint] IDENTITY(1,1) NOT NULL,
	[timFechaInicioTasa] [date] NOT NULL,
	[timFechaFinTasa] [date] NOT NULL,
	[timNumeroPeriodoTasa] [smallint] NOT NULL,
	[timPorcentajeTasa] [numeric](4, 4) NOT NULL,
	[timNormativa] [varchar](100) NOT NULL,
	[timTipoInteres] [varchar](20) NOT NULL,
 CONSTRAINT [PK_TasasInteresMora_timId] PRIMARY KEY CLUSTERED 
(
	[timId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [TipoInfraestructura](
	[tifId] [bigint] IDENTITY(1,1) NOT NULL,
	[tifCodigo] [varchar](3) NOT NULL,
	[tifNombre] [varchar](255) NOT NULL,
	[tifMedidaCapacidad] [varchar](40) NOT NULL,
	[tifActivo] [bit] NOT NULL,
 CONSTRAINT [PK_TipoInfraestructura_tifId] PRIMARY KEY CLUSTERED 
(
	[tifId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [TipoTenencia](
	[tenId] [bigint] IDENTITY(1,1) NOT NULL,
	[tenCodigo] [smallint] NOT NULL,
	[tenNombre] [varchar](255) NOT NULL,
	[tenActivo] [bit] NOT NULL,
 CONSTRAINT [PK_TipoTenencia_tenId] PRIMARY KEY CLUSTERED 
(
	[tenId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [TipoVia](
	[tviId] [bigint] IDENTITY(1,1) NOT NULL,
	[tviNombreVia] [varchar](20) NOT NULL,
 CONSTRAINT [PK_TipoVia_tviId] PRIMARY KEY CLUSTERED 
(
	[tviId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [TransaccionesFallidasSubsidio](
	[tfsId] [bigint] IDENTITY(1,1) NOT NULL,
	[tfsFechaHoraRegistro] [datetime] NOT NULL,
	[tfsCanal] [varchar](50) NOT NULL,
	[tfsEstadoResolucion] [varchar](9) NOT NULL,
	[tfsResultadoGestion] [varchar](11) NULL,
	[tfsAccionesRealizadaParaResolverCaso] [varchar](200) NULL,
	[tfsTipoTransaccionPagoSubsidio] [varchar](60) NOT NULL,
	[tfsCuentaAdministradorSubsidio] [bigint] NULL,
 CONSTRAINT [PK_TransaccionesFallidasSubsidio_tfsId] PRIMARY KEY CLUSTERED 
(
	[tfsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [TransaccionFallidaOperacTransacSubsidio](
	[tfoId] [bigint] IDENTITY(1,1) NOT NULL,
	[tfoTransaccionesFallidasSubsidio] [bigint] NOT NULL,
	[tfoRegistroOperacionTransaccionSubsidio] [bigint] NOT NULL,
 CONSTRAINT [PK_TransaccionFallidaOperacTransacSubsidio_tfoId] PRIMARY KEY CLUSTERED 
(
	[tfoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Ubicacion](
	[ubiId] [bigint] IDENTITY(1,1) NOT NULL,
	[ubiAutorizacionEnvioEmail] [bit] NULL,
	[ubiCodigoPostal] [varchar](10) NULL,
	[ubiDireccionFisica] [varchar](300) NULL,
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ValidacionProceso](
	[vapId] [bigint] IDENTITY(1,1) NOT NULL,
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_ValidacionProceso_vapProceso_vapBloque_vapValidacion_vapObjetoValidacion] UNIQUE NONCLUSTERED 
(
	[vapProceso] ASC,
	[vapBloque] ASC,
	[vapValidacion] ASC,
	[vapObjetoValidacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [VariableComunicado](
	[vcoId] [bigint] IDENTITY(1,1) NOT NULL,
	[vcoClave] [varchar](55) NULL,
	[vcoDescripcion] [varchar](200) NULL,
	[vcoNombre] [varchar](50) NULL,
	[vcoPlantillaComunicado] [bigint] NOT NULL,
	[vcoNombreConstante] [varchar](100) NULL,
	[vcoTipoVariableComunicado] [varchar](20) NULL,
	[vcoOrden] [varchar](3) NULL,
 CONSTRAINT [PK_VariableComunicado_vcoId] PRIMARY KEY CLUSTERED 
(
	[vcoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Visita](
	[visId] [bigint] IDENTITY(1,1) NOT NULL,
	[visFecha] [date] NOT NULL,
	[visNombresEncargado] [varchar](50) NOT NULL,
	[visCodigoIdentificadorECM] [varchar](255) NOT NULL,
 CONSTRAINT [PK_Visita_visId] PRIMARY KEY CLUSTERED 
(
	[visId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [pila].[Aporte](
	[appPeriodoAporte] [date] NOT NULL,
	[appPeriodoAporteYYYYMM] [varchar](7) NOT NULL,
	[appTipoIdentificacionAportante] [varchar](20) NOT NULL,
	[appNumeroIdentificacionAportante] [varchar](16) NOT NULL,
	[appTipoIdentificacionCotizante] [varchar](20) NOT NULL,
	[appNumeroIdentificacionCotizante] [varchar](16) NOT NULL,
	[appTipoPlanilla] [varchar](15) NOT NULL,
	[appTipoCotizante] [varchar](30) NOT NULL,
	[appTransaccion] [bigint] NOT NULL
) ON [PRIMARY]

CREATE SEQUENCE SEC_consecutivoSnp START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CargueArchivoCruceFovisCedula START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CargueArchivoCruceFovisAfiliado START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CargueArchivoCruceFovisBeneficiario START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CargueArchivoCruceFovisCatAnt START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CargueArchivoCruceFovisCatBog START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CargueArchivoCruceFovisCatCali START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CargueArchivoCruceFovisCatMed START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CargueArchivoCruceFovisIGAC START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CargueArchivoCruceFovisNuevoHogar START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CargueArchivoCruceFovisFechasCorte START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CargueArchivoCruceFovisSisben START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CargueArchivoCruceFovisUnidos START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CargueArchivoCruceFovisReunidos START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_Cruce START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_CruceDetalle START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_SolicitudGestionCruce START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_consecutivoSne START WITH 1 INCREMENT BY 1 ;
CREATE SEQUENCE SEC_consecutivoSan START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEC_consecutivoSpf START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE [hibernate_sequence] START WITH 1 INCREMENT BY 1;