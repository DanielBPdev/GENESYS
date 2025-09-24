--liquibase formatted sql

--changeset Heinsohn:01
--comment: structural creation base line mar 2018

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro1C_aud](
	[pgcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[accDiasLiquidacion] [bigint] NULL,
	[accHoraEjecucion] [datetime] NULL,
	[accLimiteEnvioDocumento] [bigint] NULL,
	[accVariableCalculo] [varchar](16) NULL,
	[accCantidadPeriodos] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro1D1E_aud](
	[pgcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[acdInicioDiasConteo] [varchar](13) NULL,
	[acdDiasTranscurridos] [bigint] NULL,
	[acdHoraEjecucion] [datetime] NULL,
	[acdLimiteEnvio] [bigint] NULL,
	[acdTipoCobro] [varchar](12) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro1F_aud](
	[pgcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[abfAccionCobro1F] [bit] NULL,
	[abfDiasParametrizados] [smallint] NULL,
	[abfSiguienteAccion] [varchar](29) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro2C_aud](
	[pgcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[aocAnexoLiquidacion] [bit] NULL,
	[aocDiasEjecucion] [bigint] NULL,
	[aocHoraEjecucion] [datetime] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro2D_aud](
	[pgcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[aodInicioDiasConteo] [varchar](13) NULL,
	[aodDiasTranscurridos] [bigint] NULL,
	[aodHoraEjecucion] [datetime] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro2E_aud](
	[aceId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[aceInicioDiasConteo] [varchar](13) NULL,
	[aceDiasTranscurridos] [bigint] NULL,
	[aceHoraEjecucion] [datetime] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro2F2G_aud](
	[pgcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[aofInicioDiasConteo] [varchar](13) NULL,
	[aofDiasTranscurridos] [bigint] NULL,
	[aofHoraEjecucion] [datetime] NULL,
	[aofLimiteEnvio] [bigint] NULL,
	[aofTipoCobro] [varchar](12) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobro2H_aud](
	[pgcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[achAccionCobro2H] [bit] NULL,
	[achDiasRegistro] [bigint] NULL,
	[achDiasParametrizados] [bigint] NULL,
	[achSiguienteAccion] [varchar](29) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobroA_aud](
	[pgcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[acaSuspensionAutomatica] [bit] NULL,
	[acaDiasLimitePago] [bigint] NULL,
	[acaFechaHoraEjecucion] [datetime] NULL,
	[acaDiasLimiteEnvioComunicado] [bigint] NULL,
	[acaMetodo] [varchar](8) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AccionCobroB_aud](
	[pgcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[acbDiasGeneracionAviso] [bigint] NULL,
	[acbHoraEjecucion] [datetime] NULL,
	[acbLimiteEnvioComunicado] [bigint] NULL,
	[acbMetodo] [varchar](8) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ActaAsignacionFovis_aud](
	[aafId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[aafFechaPublicacion] [datetime] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ActividadCartera_aud](
	[acrId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[acrActividadCartera] [varchar](42) NOT NULL,
	[acrResultadoCartera] [varchar](33) NULL,
	[acrComentarios] [varchar](500) NULL,
	[acrCicloAportante] [bigint] NOT NULL,
	[acrFecha] [datetime] NOT NULL,
	[acrFechaCompromiso] [date] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ActividadDocumento_aud](
	[adoId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[adoIdentificadorDocumento] [varchar](255) NOT NULL,
	[adoTipoDocumento] [varchar](12) NOT NULL,
	[adoActividadCartera] [bigint] NOT NULL,
	[adoDocumentoSoporte] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AdministradorSubsidio_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[asuPersona] [bigint] NOT NULL,
	[asuId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AdminSubsidioGrupo_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[asgGrupoFamiliar] [bigint] NOT NULL,
	[asgAdministradorSubsidio] [bigint] NOT NULL,
	[asgMedioDePago] [bigint] NULL,
	[asgAfiliadoEsAdminSubsidio] [bit] NOT NULL,
	[asgMedioPagoActivo] [bit] NOT NULL,
	[asgRelacionGrupoFamiliar] [smallint] NULL,
	[asgId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Afiliado_aud](
	[afiId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[afiPersona] [bigint] NULL,
	[afiPignoracionSubsidio] [bit] NULL,
	[afiCesionSubsidio] [bit] NULL,
	[afiRetencionSubsidio] [bit] NULL,
	[afiServicioSinAfiliacion] [bit] NULL,
	[afiCausaSinAfiliacion] [varchar](20) NULL,
	[afiFechaInicioServiciosSinAfiliacion] [date] NULL,
	[afifechaFinServicioSinAfiliacion] [date] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AFP_aud](
	[afpId] [smallint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[afpNombre] [varchar](150) NOT NULL,
	[afpCodigoPila] [varchar](10) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AgendaCartera_aud](
	[agrId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[agrVisitaAgenda] [varchar](13) NULL,
	[agrFecha] [date] NOT NULL,
	[agrHorario] [datetime] NOT NULL,
	[agrContacto] [varchar](255) NOT NULL,
	[agrTelefono] [varchar](255) NULL,
	[agrCicloAportante] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AhorroPrevio_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ahpNombreAhorro] [varchar](65) NULL,
	[ahpEntidad] [varchar](50) NULL,
	[ahpFechaInicial] [date] NULL,
	[ahpValor] [numeric](19, 5) NULL,
	[ahpFechaInmovilizacion] [date] NULL,
	[ahpFechaAdquisicion] [date] NULL,
	[ahpPostulacionFOVIS] [bigint] NOT NULL,
	[ahpId] [bigint] NOT NULL,
	[ahpAhorroMovilizado] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AporteDetallado_aud](
	[apdId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[apdFormaReconocimientoAporte] [varchar](75) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AporteGeneral_aud](
	[apgId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[apgOrigenAporte] [varchar](26) NULL,
	[apgCajaCompensacion] [int] NULL,
	[apgTipoSolicitante] [varchar](13) NULL,
	[apgEmailAportante] [varchar](255) NULL,
	[apgEmpresaTramitadoraAporte] [bigint] NULL,
	[apgFechaReconocimiento] [datetime] NULL,
	[apgFormaReconocimientoAporte] [varchar](75) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AreaCajaCompensacion_aud](
	[arcId] [smallint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[arcNombre] [varchar](30) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ARL_aud](
	[arlId] [smallint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[arlNombre] [varchar](25) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AsesorResponsableEmpleador_aud](
	[areId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[areNombreUsuario] [varchar](255) NULL,
	[arePrimario] [bit] NULL,
	[areEmpleador] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Banco_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[banCodigoPILA] [varchar](4) NOT NULL,
	[banNombre] [varchar](255) NOT NULL,
	[banMedioPago] [bit] NULL,
	[banId] [bigint] NOT NULL,
	[banCodigo] [varchar](6) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Beneficiario_aud](
	[benId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[benCertificadoEscolaridad] [bit] NULL,
	[benEstadoBeneficiarioAfiliado] [varchar](20) NULL,
	[benEstadoBeneficiarioCaja] [varchar](20) NULL,
	[benEstudianteTrabajoDesarrolloHumano] [bit] NULL,
	[benFechaAfiliacion] [date] NULL,
	[benFechaRecepcionCertificadoEscolar] [date] NULL,
	[benFechaVencimientoCertificadoEscolar] [date] NULL,
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
	[benRolAfiliado] [bigint] NULL,
	[benBeneficiarioDetalle] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [BeneficiarioDetalle_aud](
	[bedId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[bedSalarioMensual] [numeric](19, 5) NULL,
	[bedLabora] [bit] NULL,
	[bedPersonaDetalle] [bigint] NOT NULL,
	[bedCertificadoEscolaridad] [bit] NULL,
	[bedFechaRecepcionCertificadoEscolar] [date] NULL,
	[bedFechaVencimientoCertificadoEscolar] [date] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Beneficio_aud](
	[befTipoBeneficio] [varchar](16) NOT NULL,
	[befVigenciaFiscal] [bit] NOT NULL,
	[befFechaVigenciaInicio] [date] NULL,
	[befFechaVigenciaFin] [date] NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[befId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [BeneficioEmpleador_aud](
	[bemId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[bemBeneficioActivo] [bit] NULL,
	[bemFechaVinculacion] [date] NULL,
	[bemFechaDesvinculacion] [date] NULL,
	[bemEmpleador] [bigint] NOT NULL,
	[bemBeneficio] [bigint] NOT NULL,
	[bemMotivoInactivacion] [varchar](50) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [BitacoraCartera_aud](
	[bcaId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[bcaFecha] [date] NULL,
	[bcaActividad] [varchar](22) NULL,
	[bcaMedio] [varchar](16) NULL,
	[bcaResultado] [varchar](33) NULL,
	[bcaUsuario] [varchar](255) NULL,
	[bcaPersona] [bigint] NOT NULL,
	[bcaTipoSolicitante] [varchar](13) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CajaCompensacion_aud](
	[ccfId] [int] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ccfHabilitado] [bit] NOT NULL,
	[ccfMetodoGeneracionEtiquetas] [varchar](150) NOT NULL,
	[ccfNombre] [varchar](100) NOT NULL,
	[ccfSocioAsopagos] [bit] NOT NULL,
	[ccfDepartamento] [smallint] NOT NULL,
	[ccfCodigo] [varchar](5) NOT NULL,
	[ccfCodigoRedeban] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CajaCorrespondencia_aud](
	[ccoId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[ccoUsuarioRecepcion] [varchar](255) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoActualizacion_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[caaNombreArchivo] [varchar](50) NOT NULL,
	[caaFechaProcesamiento] [datetime] NULL,
	[caaCodigoIdentificadorECM] [varchar](255) NOT NULL,
	[caaEstado] [varchar](40) NOT NULL,
	[caaFechaAceptacion] [datetime] NULL,
	[caaId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueArchivoCruceFovis_aud](
	[cacId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[cacCodigoIdentificadorECM] [varchar](255) NOT NULL,
	[cacNombreArchivo] [varchar](50) NOT NULL,
	[cacFechaCargue] [datetime] NOT NULL,
	[cacInfoArchivoJsonPayload] [text] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueMultiple_aud](
	[camId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[camCodigoIdentificacionECM] [varchar](255) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CargueMultipleSupervivencia_aud](
	[cmsId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[cmsFechaIngreso] [date] NOT NULL,
	[cmsUsuario] [varchar](255) NOT NULL,
	[cmsPeriodo] [date] NULL,
	[cmsIdentificacionECM] [varchar](255) NOT NULL,
	[cmsEstadoCargueSupervivencia] [varchar](255) NOT NULL,
	[cmsNombreArchivo] [varchar](30) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Cartera_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[carId] [bigint] NOT NULL,
	[carUsuarioTraspaso] [varchar](255) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CarteraDependiente_aud](
	[cadId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[cadDeudaPresunta] [numeric](19, 5) NULL,
	[cadEstadoOperacion] [varchar](10) NOT NULL,
	[cadCartera] [bigint] NOT NULL,
	[cadPersona] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Categoria_aud](
	[catId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[catTipoAfiliado] [varchar](30) NOT NULL,
	[catCategoriaPersona] [varchar](50) NOT NULL,
	[catTipoBeneficiario] [varchar](30) NULL,
	[catClasificacion] [varchar](48) NOT NULL,
	[catTotalIngresoMesada] [numeric](19, 0) NOT NULL,
	[catFechaCambioCategoria] [date] NOT NULL,
	[catMotivoCambioCategoria] [varchar](50) NOT NULL,
	[catAfiliadoPrincipal] [bit] NOT NULL,
	[catIdAfiliado] [bigint] NOT NULL,
	[catIdBeneficiario] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CicloAportante_aud](
	[capId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[capPersona] [bigint] NULL,
	[capTipoSolicitante] [varchar](14) NULL,
	[capCicloCartera] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CicloAsignacion_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ciaNombre] [varchar](50) NULL,
	[ciaFechaInicio] [date] NULL,
	[ciaFechaFin] [date] NULL,
	[ciaCicloPredecesor] [bigint] NULL,
	[ciaEstadoCicloAsignacion] [varchar](30) NULL,
	[ciaCicloActivo] [bit] NULL,
	[ciaId] [bigint] NOT NULL,
	[ciaValorDisponible] [numeric](19, 5) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CicloCartera_aud](
	[ccrId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ccrEstadoCiclo] [varchar](10) NOT NULL,
	[ccrFechaInicio] [date] NOT NULL,
	[ccrFechaFin] [date] NOT NULL,
	[ccrFechaCreacion] [datetime] NOT NULL,
	[ccrTipoCiclo] [varchar](14) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CicloModalidad_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[cmoCicloAsignacion] [bigint] NOT NULL,
	[cmoModalidad] [varchar](50) NOT NULL,
	[cmoId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CodigoCIIU_aud](
	[ciiId] [smallint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ciiCodigo] [varchar](4) NOT NULL,
	[ciiDescripcion] [varchar](255) NOT NULL,
	[ciiCodigoSeccion] [varchar](1) NULL,
	[ciiDescripcionSeccion] [varchar](200) NULL,
	[ciiCodigoDivision] [varchar](2) NULL,
	[ciiDescripcionDivision] [varchar](250) NULL,
	[ciiCodigoGrupo] [varchar](3) NULL,
	[ciiDescripcionGrupo] [varchar](200) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Comunicado_aud](
	[comId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[comSolicitud] [bigint] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CondicionEspecialPersona_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[cepPersona] [bigint] NOT NULL,
	[cepId] [bigint] NOT NULL,
	[cepNombreCondicion] [varchar](28) NOT NULL,
	[cepActiva] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CondicionInvalidez_aud](
	[coiId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[coiPersona] [bigint] NOT NULL,
	[coiInvalidez] [bit] NULL,
	[coiFechaReporteInvalidez] [date] NULL,
	[coiComentarioInvalidez] [varchar](500) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CondicionVisita_aud](
	[covId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[covCondicion] [varchar](42) NOT NULL,
	[covCumple] [bit] NOT NULL,
	[covObservacion] [varchar](250) NULL,
	[covVisita] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ConexionOperadorInformacion_aud](
	[coiId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[coiOperadorInformacionCcf] [bigint] NULL,
	[coiProtocolo] [varchar](10) NULL,
	[coiUrl] [varchar](255) NULL,
	[coiPuerto] [smallint] NULL,
	[coiHost] [varchar](75) NULL,
	[coiUsuario] [varchar](255) NULL,
	[coiContrasena] [varchar](32) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ConsolaEstadoCargueMasivo_aud](
	[cecId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[cecGradoAvance] [numeric](6, 3) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Constante_aud](
	[cnsId] [int] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[cnsNombre] [varchar](100) NULL,
	[cnsValor] [varchar](150) NULL,
	[cnsDescripcion] [varchar](250) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ConvenioPago_aud](
	[copId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[copUsuarioCreacion] [varchar](255) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ConvenioPagoDependiente_aud](
	[cpdId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[cpdPagoPeriodoConvenio] [bigint] NULL,
	[cpdPersona] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Correccion_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[corAporteDetallado] [bigint] NULL,
	[corAporteGeneral] [bigint] NULL,
	[corSolicitudCorreccionAporte] [bigint] NULL,
	[corId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Cruce_aud](
	[cruId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[cruCargueArchivoCruceFovis] [bigint] NULL,
	[cruNumeroPostulacion] [varchar](12) NULL,
	[cruPersona] [bigint] NULL,
	[cruEstadoCruce] [varchar](22) NOT NULL,
	[cruSolicitudGestionCruce] [bigint] NULL,
	[cruResultadoCodigoIdentificadorECM] [varchar](255) NULL,
	[cruObservacionResultado] [varchar](500) NULL,
	[cruEjecucionProcesoAsincrono] [bigint] NULL,
	[cruFechaRegistro] [datetime] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CruceDetalle_aud](
	[crdId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[crdClasificacion] [varchar](30) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CuentaAdministradorSubsidio_aud](
	[casId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[casCondicionPersonaAdmin] [bigint] NULL
) ON [PRIMARY]

GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Departamento_aud](
	[depId] [smallint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[depCodigo] [varchar](2) NOT NULL,
	[depIndicativoTelefoniaFija] [varchar](2) NOT NULL,
	[depNombre] [varchar](100) NOT NULL,
	[depExcepcionAplicaFOVIS] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DesafiliacionAportante_aud](
	[deaId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[deaPersona] [bigint] NULL,
	[deaSolicitudDesafiliacion] [bigint] NULL,
	[deaTipoSolicitante] [varchar](13) NULL,
	[deaTipoLineaCobro] [varchar](3) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DestinatarioComunicado_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dcoProceso] [varchar](150) NOT NULL,
	[dcoEtiquetaPlantilla] [varchar](150) NOT NULL,
	[dcoId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DestinatarioGrupo_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dgrGrupoPrioridad] [bigint] NOT NULL,
	[dgrRolContacto] [varchar](60) NOT NULL,
	[dgrId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DetalleComunicadoEnviado_aud](
	[dceId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dceComunicado] [bigint] NOT NULL,
	[dceIdentificador] [bigint] NULL,
	[dceTipoTransaccion] [varchar](100) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DetalleSolicitudGestionCobro_aud](
	[dsgId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[dsgDocumentoSegundaRemision] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DetalleSubsidioAsignado_aud](
	[dsaId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[dsaCondicionPersonaEmpleador] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DevolucionAporte_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[dapId] [bigint] NOT NULL,
	[dapOtraCaja] [varchar](255) NULL,
	[dapOtroMotivo] [varchar](255) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DevolucionAporteDetalle_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dadIncluyeAporteObligatorio] [bit] NULL,
	[dadIncluyeMoraCotizante] [bit] NULL,
	[dadComentarioHistorico] [varchar](255) NULL,
	[dadComentarioNovedades] [varchar](255) NULL,
	[dadComentarioAportes] [varchar](255) NULL,
	[dadUsuario] [varchar](255) NULL,
	[dadFechaGestion] [datetime] NULL,
	[dadDevolucionAporte] [bigint] NOT NULL,
	[dadMovimientoAporte] [bigint] NULL,
	[dadId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DiasFestivos_aud](
	[pifId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pifConcepto] [varchar](150) NOT NULL,
	[pifFecha] [date] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DiferenciasCargueActualizacion_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dicTipoTransaccion] [varchar](100) NULL,
	[dicJsonPayload] [text] NULL,
	[dicCargueArchivoActualizacion] [bigint] NOT NULL,
	[dicId] [bigint] NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoAdministracionEstadoSolicitud_aud](
	[daeId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[daeSolicitud] [bigint] NULL,
	[daeDocumentoSoporteCambioEstado] [varchar](100) NULL,
	[daeNombreDocumento] [varchar](100) NULL,
	[daeTipoDocumentoAdjunto] [varchar](22) NULL,
	[daeActividad] [varchar](29) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoBitacora_aud](
	[dbiId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dbiBitacoraCartera] [bigint] NOT NULL,
	[dbiDocumentoSoporte] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoCartera_aud](
	[dcaId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dcaCartera] [bigint] NOT NULL,
	[dcaDocumentoSoporte] [bigint] NOT NULL,
	[dcaAccionCobro] [varchar](4) NULL,
	[dcaConsecutivoLiquidacion] [varchar](10) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoDesafiliacion_aud](
	[dodId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dodDocumentoSoporte] [bigint] NULL,
	[dodSolicitudDesafiliacion] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoEntidadPagadora_aud](
	[dpgId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dpgEntidadPagadora] [bigint] NULL,
	[dpgIdentificadorDocumento] [varchar](255) NULL,
	[dpgTipoDocumento] [varchar](50) NULL,
	[dpgNombreDocumento] [varchar](60) NULL,
	[dpgVersionDocumento] [smallint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoSoporte_aud](
	[dosId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dosNombreDocumento] [varchar](255) NOT NULL,
	[dosDescripcionComentarios] [varchar](255) NOT NULL,
	[dosIdentificacionDocumento] [varchar](255) NOT NULL,
	[dosVersionDocumento] [varchar](6) NOT NULL,
	[dosFechaHoraCargue] [datetime] NOT NULL,
	[dosTipoDocumento] [varchar](24) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoSoporteOferente_aud](
	[dsoId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dsoOferente] [bigint] NOT NULL,
	[dsoDocumentoSoporte] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DocumentoSoporteProyectoVivienda_aud](
	[dspId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[dspProyectoSolucionVivienda] [bigint] NOT NULL,
	[dspDocumentoSoporte] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [EjecucionProcesoAsincrono_aud](
	[epsId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[epsFechaInicio] [datetime] NOT NULL,
	[epsFechaFin] [datetime] NULL,
	[epsRevisado] [bit] NOT NULL,
	[epsTipoProceso] [varchar](20) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [EjecucionProgramada_aud](
	[ejpId] [bigint] IDENTITY(1,1) NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ejpFechaDefinicion] [datetime] NULL,
	[ejpUsuario] [varchar](255) NULL,
	[ejpFrecuencia] [varchar](50) NULL,
	[ejpHoraInicio] [varchar](5) NULL,
	[ejpFechaInicioVigencia] [datetime] NULL,
	[ejpFechaFinVigencia] [datetime] NULL,
	[ejpCajaCompensacion] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ElementoDireccion_aud](
	[eldId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[eldNombre] [varchar](20) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Empleador_aud](
	[empId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[empMarcaExpulsion] [varchar](22) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Empresa_aud](
	[empId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[empCreadoPorPila] [bit] NULL,
	[empEnviadoAFiscalizacion] [bit] NULL,
	[empMotivoFiscalizacion] [varchar](30) NULL,
	[empFechaFiscalizacion] [date] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [EntidadDescuento_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[endId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [EntidadPagadora_aud](
	[epaId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[epaCargoContacto] [varchar](20) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [EscalamientoSolicitud_aud](
	[esoId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[esoTipoAnalistaFOVIS] [varchar](22) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [EtiquetaCorrespondenciaRadicado_aud](
	[eprId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[eprAsignada] [bit] NULL,
	[eprCodigo] [varchar](12) NULL,
	[eprTipoEtiqueta] [varchar](50) NULL,
	[eprProcedenciaEtiqueta] [varchar](20) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ExclusionCartera_aud](
	[excId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[excObservacionCambioResultado] [varchar](400) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ExpulsionSubsanada_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[exsExpulsionSubsanada] [bit] NULL,
	[exsFechaSubsanacionExpulsion] [date] NULL,
	[exsMotivoSubsanacionExpulsion] [varchar](200) NOT NULL,
	[exsEmpleador] [bigint] NULL,
	[exsRolAfiliado] [bigint] NULL,
	[exsId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [FormaPagoModalidad_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[fpmFormaPago] [varchar](34) NOT NULL,
	[fpmModalidad] [varchar](50) NOT NULL,
	[fpmId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GestionNotiNoEnviadas_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[gneEmpresa] [bigint] NOT NULL,
	[gneTipoInconsistencia] [varchar](20) NOT NULL,
	[gneCanalContacto] [varchar](20) NOT NULL,
	[gneFechaIngreso] [datetime] NOT NULL,
	[gneEstadoGestion] [varchar](25) NULL,
	[gneObservaciones] [varchar](60) NULL,
	[gneFechaRespuesta] [datetime] NULL,
	[gneId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GlosaComentarioNovedad_aud](
	[gcnId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[gcnNombreGlosaNovedad] [varchar](60) NULL,
	[gcnDescripcionGlosaNovedad] [varchar](400) NULL,
	[gcnEstadoGlosaNovedad] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GradoAcademico_aud](
	[graId] [smallint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[graNombre] [varchar](20) NOT NULL,
	[graNivelEducativo] [varchar](43) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GrupoFamiliar_aud](
	[grfId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[grfNumero] [smallint] NOT NULL,
	[grfObservaciones] [varchar](500) NULL,
	[grfAfiliado] [bigint] NOT NULL,
	[grfUbicacion] [bigint] NULL,
	[grfInembargable] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GrupoPrioridad_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[gprNombre] [varchar](150) NULL,
	[gprId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [GrupoRequisito_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[grqRequisitoCajaClasificacion] [bigint] NULL,
	[grqGrupoUsuario] [varchar](60) NULL,
	[grqId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [HistoriaResultadoValidacion_aud](
	[hrvId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[hrvDetalle] [varchar](400) NULL,
	[hrvResultado] [varchar](20) NULL,
	[hrvValidacion] [varchar](100) NULL,
	[hrvIdDatosRegistro] [bigint] NULL,
	[hrvTipoExepcion] [varchar](30) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [InformacionFaltanteAportante_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ifaSolicitud] [bigint] NOT NULL,
	[ifaTipoGestion] [varchar](22) NULL,
	[ifaResponsableInformacion] [varchar](18) NULL,
	[ifaTipoDocumentoGestion] [varchar](10) NULL,
	[ifaMedioComunicacion] [varchar](19) NULL,
	[ifaObservaciones] [varchar](500) NULL,
	[ifaFechaGestion] [date] NULL,
	[ifaFechaRegistro] [date] NULL,
	[ifaUsuario] [varchar](255) NULL,
	[ifaId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Infraestructura_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[infId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [InhabilidadSubsidioFovis_aud](
	[isfId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[isfJefeHogar] [bigint] NULL,
	[isfIntegranteHogar] [bigint] NULL,
	[isfFechaInicio] [datetime] NULL,
	[isfFechaFin] [datetime] NULL,
	[isfInhabilitadoParaSubsidio] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntegranteHogar_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[inhJefeHogar] [bigint] NOT NULL,
	[inhPersona] [bigint] NOT NULL,
	[inhIntegranteReemplazaJefeHogar] [bit] NULL,
	[inhTipoIntegrante] [varchar](32) NOT NULL,
	[inhEstadoHogar] [varchar](10) NULL,
	[inhIntegranteValido] [bit] NULL,
	[inhSalarioMensual] [numeric](19, 5) NULL,
	[inhId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoAfiliacion_aud](
	[iafId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[iafCausaIntentoFallido] [varchar](50) NULL,
	[iafFechaCreacion] [datetime2](7) NULL,
	[iafFechaInicioProceso] [datetime2](7) NULL,
	[iafSedeCajaCompensacion] [varchar](2) NULL,
	[iafTipoTransaccion] [varchar](100) NULL,
	[iafUsuarioCreacion] [varchar](255) NULL,
	[iafSolicitud] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoAfiliRequisito_aud](
	[iarId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[iarIntentoAfiliacion] [bigint] NULL,
	[iarRequisito] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoLegalizacionDesembolso_aud](
	[ildId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ildCausaIntentoFallido] [varchar](50) NULL,
	[ildFechaCreacion] [datetime] NULL,
	[ildSedeCajaCompensacion] [varchar](2) NULL,
	[ildUsuarioCreacion] [varchar](255) NULL,
	[ildSolicitud] [bigint] NULL,
	[ildProceso] [varchar](32) NULL,
	[ildTipoSolicitante] [varchar](5) NULL,
	[ildModalidad] [varchar](33) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoLegalizacionDesembolsoRequisito_aud](
	[ilrId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ilrIntentoLegalizacionDesembolso] [bigint] NULL,
	[ilrRequisito] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoNovedad_aud](
	[inoId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[inoCausaIntentoFallido] [varchar](255) NULL,
	[inoFechaInicioProceso] [datetime2](7) NULL,
	[inoSolicitud] [bigint] NULL,
	[inoTipoTransaccion] [varchar](100) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoNoveRequisito_aud](
	[inrId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[inrRequisito] [bigint] NULL,
	[inrIntentoNovedad] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoPostulacion_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[ipoId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [IntentoPostulacionRequisito_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[iprIntentoPostulacion] [bigint] NULL,
	[iprRequisito] [bigint] NULL,
	[iprId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ItemChequeo_aud](
	[ichId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[ichIdentificadorDocumentoPrevio] [varchar](255) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [JefeHogar_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[jehAfiliado] [bigint] NOT NULL,
	[jehEstadoHogar] [varchar](10) NULL,
	[jehId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [LegalizacionDesembolso_aud](
	[lgdId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[lgdFormaPago] [varchar](50) NULL,
	[lgdValorDesembolsar] [numeric](19, 5) NULL,
	[lgdFechaLimitePago] [datetime] NULL,
	[lgdVisita] [bigint] NULL,
	[lgdSubsidioDesembolsado] [bit] NULL,
	[lgdTipoMedioPago] [varchar](30) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Licencia_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[licEntidadExpide] [varchar](21) NULL,
	[licNumeroLicencia] [varchar](50) NULL,
	[licMatriculaInmobiliaria] [varchar](50) NULL,
	[licProyectoSolucionVivienda] [bigint] NOT NULL,
	[licId] [bigint] NOT NULL,
	[licTipoLicencia] [varchar](21) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [LicenciaDetalle_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[lidNumeroResolucion] [varchar](50) NULL,
	[lidFechaInicio] [date] NULL,
	[lidFechaFin] [date] NULL,
	[lidLicencia] [bigint] NOT NULL,
	[lidId] [bigint] NOT NULL,
	[lidClasificacionLicencia] [varchar](33) NULL,
	[lidEstadoLicencia] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [LineaCobro_aud](
	[pgcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[lcoHabilitarAccionCobroA] [bit] NULL,
	[lcoDiasLimitePago] [bigint] NULL,
	[lcoDiasParametrizados] [bigint] NULL,
	[lcoHabilitarAccionCobroB] [bit] NULL,
	[lcoTipoLineaCobro] [varchar](3) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ListaEspecialRevision_aud](
	[lerId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[lerComentario] [varchar](255) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioCheque_aud](
	[mdpId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[mecTipoIdentificacionTitular] [varchar](20) NULL,
	[mecNumeroIdentificacionTitular] [varchar](16) NULL,
	[mecDigitoVerificacionTitular] [smallint] NULL,
	[mecNombreTitularCuenta] [varchar](200) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioConsignacion_aud](
	[mdpId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[mcoBanco] [bigint] NOT NULL,
	[mcoTipoCuenta] [varchar](30) NOT NULL,
	[mcoNumeroCuenta] [varchar](30) NOT NULL,
	[mcoTipoIdentificacionTitular] [varchar](20) NULL,
	[mcoNumeroIdentificacionTitular] [varchar](16) NULL,
	[mcoDigitoVerificacionTitular] [smallint] NULL,
	[mcoNombreTitularCuenta] [varchar](200) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioDePago_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[mdpTipo] [varchar](100) NOT NULL,
	[mdpId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioEfectivo_aud](
	[mdpId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[mefEfectivo] [bit] NOT NULL,
	[mefSitioPago] [bigint] NULL,
	[mefSedeCajaCompensacion] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioPagoPersona_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[mppMedioPago] [bigint] NOT NULL,
	[mppPersona] [bigint] NOT NULL,
	[mppMedioActivo] [bit] NOT NULL,
	[mppId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioPagoProyectoVivienda_aud](
	[mprId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[mprProyectoSolucionVivienda] [bigint] NOT NULL,
	[mprMedioDePago] [bigint] NOT NULL,
	[mprActivo] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MediosPagoCCF_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[mpcCajaCompensacion] [int] NOT NULL,
	[mpcMedioPago] [varchar](30) NOT NULL,
	[mpcMedioPreferente] [bit] NOT NULL,
	[mpcAplicaFOVIS] [bit] NULL,
	[mpcId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioTarjeta_aud](
	[mdpId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[mtrNumeroTarjeta] [varchar](50) NOT NULL,
	[mtrDisponeTarjeta] [bit] NOT NULL,
	[mtrEstadoTarjetaMultiservicios] [varchar](30) NOT NULL,
	[mtrSolicitudTarjeta] [varchar](30) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MedioTransferencia_aud](
	[mdpId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[metBanco] [bigint] NOT NULL,
	[metTipoCuenta] [varchar](30) NOT NULL,
	[metNumeroCuenta] [varchar](30) NOT NULL,
	[metTipoIdentificacionTitular] [varchar](20) NULL,
	[metNumeroIdentificacionTitular] [varchar](16) NULL,
	[metDigitoVerificacionTitular] [smallint] NULL,
	[metNombreTitularCuenta] [varchar](200) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MotivoNoGestionCobro_aud](
	[mgcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[mgcCartera] [bigint] NOT NULL,
	[mgcTipo] [varchar](36) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MovimientoAjusteAporte_aud](
	[maaId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[maaEstadoAjusteRegAporte] [varchar](40) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [MovimientoAporte_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[moaTipoAjuste] [varchar](20) NULL,
	[moaTipoMovimiento] [varchar](23) NULL,
	[moaEstadoAporte] [varchar](22) NULL,
	[moaValorAporte] [numeric](19, 5) NULL,
	[moaValorInteres] [numeric](19, 5) NULL,
	[moaFechaActualizacionEstado] [datetime] NULL,
	[moaFechaCreacion] [datetime] NULL,
	[moaAporteDetallado] [bigint] NULL,
	[moaAporteGeneral] [bigint] NOT NULL,
	[moaId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Municipio_aud](
	[munId] [smallint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[munCodigo] [varchar](5) NOT NULL,
	[munNombre] [varchar](50) NOT NULL,
	[munDepartamento] [smallint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [NotificacionDestinatario_aud](
	[nodId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[nodNotEnviada] [bigint] NULL,
	[nodDestintatario] [varchar](255) NULL,
	[nodTipoDestintatario] [varchar](3) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [NotificacionEnviada_aud](
	[noeId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[noeFechaEnvio] [datetime2](7) NULL,
	[noeRemitente] [varchar](255) NULL,
	[noeSccfId] [bigint] NULL,
	[noeProcesoEvento] [varchar](255) NULL,
	[noeEstadoEnvioNot] [varchar](20) NULL,
	[noeError] [varchar](4000) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [NotificacionPersonal_aud](
	[ntpId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ntpActividad] [varchar](41) NOT NULL,
	[ntpPersona] [bigint] NOT NULL,
	[ntpTipoSolicitante] [varchar](13) NOT NULL,
	[ntpComentario] [varchar](250) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [NotificacionPersonalDocumento_aud](
	[ntdId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ntdDocumentoSoporte] [bigint] NOT NULL,
	[ntdNotificacionPersonal] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [NovedadDetalle_aud](
	[nopId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[nopFechaInicio] [date] NULL,
	[nopFechaFin] [date] NULL,
	[nopVigente] [bit] NULL,
	[nopSolicitudNovedad] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [OcupacionProfesion_aud](
	[ocuId] [int] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ocuNombre] [varchar](100) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Oferente_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ofePersona] [bigint] NOT NULL,
	[ofeEmpresa] [bigint] NULL,
	[ofeEstado] [varchar](30) NULL,
	[ofeId] [bigint] NOT NULL,
	[ofeCuentaBancaria] [bit] NULL,
	[ofeBanco] [bigint] NULL,
	[ofeTipoCuenta] [varchar](30) NULL,
	[ofeNumeroCuenta] [varchar](30) NULL,
	[ofeTipoIdentificacionTitular] [varchar](20) NULL,
	[ofeNumeroIdentificacionTitular] [varchar](16) NULL,
	[ofeDigitoVerificacionTitular] [smallint] NULL,
	[ofeNombreTitularCuenta] [varchar](200) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [OperadorInformacion_aud](
	[oinId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[oinCodigo] [varchar](2) NOT NULL,
	[oinNombre] [varchar](75) NOT NULL,
	[oinOperadorActivo] [bit] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [OperadorInformacionCcf_aud](
	[oicId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[oicOperadorInformacion] [bigint] NULL,
	[oicCajaCompensacion] [int] NULL,
	[oicEstado] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PagoPeriodoConvenio_aud](
	[ppcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ppcConvenioPago] [bigint] NOT NULL,
	[ppcFechaPago] [date] NOT NULL,
	[ppcValorCuota] [numeric](19, 5) NOT NULL,
	[ppcPeriodo] [date] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionCartera_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[pacTipoParametrizacion] [varchar](74) NULL,
	[pacId] [bigint] NOT NULL,
	[pacFechaActualizacion] [datetime] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionCondicionesSubsidio_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pcsAnioVigenciaParametrizacion] [int] NOT NULL,
	[pcsPeriodoInicio] [date] NOT NULL,
	[pcsPeriodoFin] [date] NOT NULL,
	[pcsValorCuotaAnualBase] [numeric](19, 5) NOT NULL,
	[pcsValorCuotaAnualAgraria] [numeric](19, 5) NOT NULL,
	[pcsProgramaDecreto4904] [bit] NOT NULL,
	[pcsRetroactivoNovInvalidez] [bit] NOT NULL,
	[pcsRetroactivoReingresoEmpleadores] [bit] NOT NULL,
	[pcsId] [bigint] NOT NULL,
	[pcsCantidadSubsidiosLiquidados] [int] NULL,
	[pcsMontoSubsidiosLiquidados] [numeric](10, 0) NULL,
	[pcsCantidadSubsidiosLiquidadosInvalidez] [int] NULL,
	[pcsCantidadPeriodosRetroactivosMes] [int] NULL,
	[pcsCodigoCajaCompensacion] [varchar](5) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionConveniosPago_aud](
	[pcpId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pcpCantidadPeriodos] [smallint] NULL,
	[pcpNumeroConveniosPermitido] [smallint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionCriterioGestionCobro_aud](
	[pacId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pcrLineaCobro] [varchar](3) NULL,
	[pcrActiva] [bit] NULL,
	[pcrMetodo] [varchar](10) NULL,
	[pcrAccion] [varchar](10) NULL,
	[pcrCorteEntidad] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionDesafiliacion_aud](
	[pdeId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[pdeHabilitado] [bit] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionEjecucionProgramada_aud](
	[pepId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[pepEstado] [varchar](8) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionExclusiones_aud](
	[pexId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pexExclusionNegocios] [bit] NULL,
	[pexImposicionRecurso] [bit] NULL,
	[pexConvenioPago] [bit] NULL,
	[pexAclaracionMora] [bit] NULL,
	[pexRiesgoIncobrabilidad] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionFiscalizacion_aud](
	[pacId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pfiAlertaValidacionPila] [bit] NULL,
	[pfiEstadoAporteNoOk] [bit] NULL,
	[pfiIbcMenorUltimo] [bit] NULL,
	[pfiNovedadRetiro] [bit] NULL,
	[pfiPeriodosRetroactivos] [smallint] NULL,
	[pfiSalarioMenorUltimo] [bit] NULL,
	[pfiCorteEntidades] [bigint] NULL,
	[pfiGestionPreventiva] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionFOVIS_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pafNombre] [varchar](50) NOT NULL,
	[pafValor] [bit] NULL,
	[pafValorNumerico] [numeric](4, 1) NULL,
	[pafPlazoVencimiento] [varchar](15) NULL,
	[pafPlazoAdicional] [varchar](15) NULL,
	[pafValorAdicional] [numeric](4, 1) NULL,
	[pafValorString] [varchar](30) NULL,
	[pafId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionGestionCobro_aud](
	[pgcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pgcOficinaPrincipalFisico] [bit] NULL,
	[pgcCorrespondenciaFisico] [bit] NULL,
	[pgcNotificacionJudicialFisico] [bit] NULL,
	[pgcOficinaPrincipalElectronico] [bit] NULL,
	[pgcRepresentanteLegalElectronico] [bit] NULL,
	[pgcResponsableAportesElectronico] [bit] NULL,
	[pgcMetodoEnvioComunicado] [varchar](11) NULL,
	[pgcTipoParametrizacion] [varchar](55) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionLiquidacionSubsidio_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[plsAnioVigenciaParametrizacion] [int] NOT NULL,
	[plsPeriodoInicio] [date] NOT NULL,
	[plsPeriodoFin] [date] NOT NULL,
	[plsFactorCuotaInvalidez] [numeric](19, 5) NOT NULL,
	[plsFactorPorDefuncion] [numeric](19, 5) NOT NULL,
	[plsHorasTrabajadas] [int] NOT NULL,
	[plsSMLMV] [numeric](19, 5) NULL,
	[plsId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionMetodoAsignacion_aud](
	[pmaId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pmaSedeCajaCompensacion] [bigint] NOT NULL,
	[pmaProceso] [varchar](100) NOT NULL,
	[pmaMetodoAsignacion] [varchar](20) NULL,
	[pmaUsuario] [varchar](255) NULL,
	[pmaGrupo] [varchar](50) NULL,
	[pmaSedeCajaDestino] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionModalidad_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pmoNombre] [varchar](50) NULL,
	[pmoEstado] [bit] NULL,
	[pmoTopeSMLMV] [numeric](4, 1) NULL,
	[pmoTopeAvaluoCatastral] [numeric](4, 1) NULL,
	[pmoId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionNovedad_aud](
	[novId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[novTipoTransaccion] [varchar](100) NULL,
	[novPuntoResolucion] [varchar](255) NULL,
	[novRutaCualificada] [varchar](255) NULL,
	[novTipoNovedad] [varchar](255) NULL,
	[novProceso] [varchar](50) NULL,
	[novAplicaTodosRoles] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionPreventiva_aud](
	[pacId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pprDiasHabilesPrevios] [smallint] NULL,
	[pprHoraEjecucion] [datetime] NULL,
	[pprEjecucionAutomatica] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizacionTablaAuditable](
	[ptaId] [int] IDENTITY(1,1) NOT NULL,
	[ptaActualizar] [bit] NULL,
	[ptaConsultar] [bit] NULL,
	[ptaCrear] [bit] NULL,
	[ptaEntityClassName] [varchar](255) NULL,
	[ptaNombreTabla] [varchar](255) NULL,
 CONSTRAINT [PK_ParametrizacionTablaAuditable_ptaId] PRIMARY KEY CLUSTERED 
(
	[ptaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ParametrizaEnvioComunicado_aud](
	[pecId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pecProceso] [varchar](100) NULL,
	[pecTipoCorreo] [varchar](20) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Parametro_aud](
	[prmId] [int] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[prmNombre] [varchar](100) NULL,
	[prmValor] [varchar](150) NULL,
	[prmCargaInicio] [bit] NULL,
	[prmSubCategoriaParametro] [varchar](23) NULL,
	[prmDescripcion] [varchar](250) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Periodo_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[priPeriodo] [date] NOT NULL,
	[priId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PeriodoBeneficio_aud](
	[pbePeriodo] [smallint] NOT NULL,
	[pbePorcentaje] [numeric](5, 5) NULL,
	[pbeBeneficio] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pbeId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PeriodoExclusionMora_aud](
	[pemId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pemPeriodo] [date] NOT NULL,
	[pemExclusionCartera] [bigint] NOT NULL,
	[pemEstadoPeriodo] [varchar](10) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PeriodoLiquidacion_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pelSolicitudLiquidacionSubsidio] [bigint] NOT NULL,
	[pelPeriodo] [bigint] NOT NULL,
	[pelTipoPeriodo] [varchar](10) NOT NULL,
	[pelId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Persona_aud](
	[perId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[perCreadoPorPila] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PersonaDetalle_aud](
	[pedId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[pedEstadoCivil] [varchar](20) NULL,
	[pedHabitaCasaPropia] [bit] NULL,
	[pedFallecido] [bit] NULL,
	[pedFechaFallecido] [date] NULL,
	[pedBeneficiarioSubsidio] [bit] NULL,
	[pedEstudianteTrabajoDesarrolloHumano] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PlantillaComunicado_aud](
	[pcoId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pcoAsunto] [varchar](150) NULL,
	[pcoCuerpo] [varchar](5000) NULL,
	[pcoEncabezado] [varchar](500) NULL,
	[pcoIdentificadorImagenPie] [varchar](255) NULL,
	[pcoMensaje] [varchar](5000) NULL,
	[pcoNombre] [varchar](150) NULL,
	[pcoPie] [varchar](500) NULL,
	[pcoEtiqueta] [varchar](150) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PostulacionFOVIS_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[pofCicloAsignacion] [bigint] NULL,
	[pofJefeHogar] [bigint] NULL,
	[pofEstadoHogar] [varchar](58) NULL,
	[pofCondicionHogar] [varchar](44) NULL,
	[pofHogarPerdioSubsidioNoPago] [bit] NULL,
	[pofCantidadFolios] [smallint] NULL,
	[pofValorSFVSolicitado] [numeric](19, 5) NULL,
	[pofProyectoSolucionVivienda] [bigint] NULL,
	[pofModalidad] [varchar](50) NULL,
	[pofId] [bigint] NOT NULL,
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
	[pofValorAjusteIPCSFV] [numeric](19, 5) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PrioridadDestinatario_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[prdDestinatarioComunicado] [bigint] NOT NULL,
	[prdGrupoPrioridad] [bigint] NOT NULL,
	[prdPrioridad] [smallint] NULL,
	[prdId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ProductoNoConforme_aud](
	[pncId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[pncValorCampoBack] [varchar](300) NULL,
	[pncValorCampoFront] [varchar](300) NULL,
	[pncSolicitud] [bigint] NULL,
	[pncBeneficiario] [bigint] NULL,
	[pncNombreInconsistencia] [varchar](50) NULL,
	[pncDescripcionInconsistencia] [varchar](150) NULL,
	[pncClasificacionTipoProducto] [varchar](15) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ProyectoSolucionVivienda_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[psvId] [bigint] NOT NULL,
	[psvRegistrado] [bit] NULL,
	[psvDisponeCuentaBancaria] [bit] NULL,
	[psvComparteCuentaOferente] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RangoTopeValorSFV_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[rtvNombre] [varchar](50) NOT NULL,
	[rtvOperadorValorMinimo] [varchar](13) NOT NULL,
	[rtvValorMinimo] [numeric](3, 1) NOT NULL,
	[rtvOperadorValorMaximo] [varchar](13) NOT NULL,
	[rtvValorMaximo] [numeric](3, 1) NOT NULL,
	[rtvTopeSMLMV] [numeric](4, 1) NOT NULL,
	[rtvModalidad] [varchar](50) NOT NULL,
	[rtvId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RecursoComplementario_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[recNombre] [varchar](26) NULL,
	[recEntidad] [varchar](50) NULL,
	[recFecha] [date] NULL,
	[recOtroRecurso] [varchar](255) NULL,
	[recValor] [numeric](19, 5) NULL,
	[recPostulacionFOVIS] [bigint] NOT NULL,
	[recId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RegistroEstadoAporte_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[reaSolicitud] [bigint] NOT NULL,
	[reaActividad] [varchar](29) NULL,
	[reaEstadoSolicitud] [varchar](25) NULL,
	[reaFecha] [datetime] NULL,
	[reaComunicado] [bigint] NULL,
	[reaUsuario] [varchar](255) NULL,
	[reaId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RegistroNovedadFutura_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[rnfFechaInicio] [date] NULL,
	[rnfFechaFin] [date] NULL,
	[rnfTipoTransaccion] [varchar](90) NULL,
	[rnfCanalRecepcion] [varchar](30) NULL,
	[rnfComentarios] [varchar](250) NULL,
	[rnfPersona] [bigint] NULL,
	[rnfRegistroDetallado] [bigint] NULL,
	[rnfId] [bigint] NOT NULL,
	[rnfClasificacion] [varchar](48) NULL,
	[rnfEmpleador] [bigint] NULL,
	[rnfProcesada] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RegistroPersonaInconsistente_aud](
	[rpiId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[rpiCargueMultipleSupervivencia] [bigint] NULL,
	[rpiPersona] [bigint] NOT NULL,
	[rpiCanalContacto] [varchar](15) NULL,
	[rpiFechaIngreso] [date] NULL,
	[rpiEstadoGestion] [varchar](20) NULL,
	[rpiObservaciones] [varchar](255) NULL,
	[rpiTipoInconsistencia] [varchar](42) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RelacionGrupoFamiliar_aud](
	[rgfId] [smallint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[rgfNombre] [varchar](15) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Requisito_aud](
	[reqId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[reqDescripcion] [varchar](200) NOT NULL,
	[reqEstado] [varchar](20) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RequisitoCajaClasificacion_aud](
	[rtsId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[rtsEstado] [varchar](20) NULL,
	[rtsRequisito] [bigint] NULL,
	[rtsClasificacion] [varchar](100) NULL,
	[rtsTipoTransaccion] [varchar](100) NULL,
	[rtsCajaCompensacion] [int] NULL,
	[rtsTextoAyuda] [varchar](1500) NULL,
	[rtsTipoRequisito] [varchar](30) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ResultadoEjecucionProgramada_aud](
	[repId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[repProceso] [varchar](100) NOT NULL,
	[repFechaEjecucion] [datetime] NULL,
	[repResultadoEjecucion] [varchar](50) NULL,
	[repTipoResultadoProceso] [varchar](10) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Revision](
	[revId] [bigint] IDENTITY(1,1) NOT NULL,
	[revIp] [varchar](255) NULL,
	[revNombreUsuario] [varchar](255) NULL,
	[revRequestId] [varchar](255) NULL,
	[revTimeStamp] [bigint] NULL,
 CONSTRAINT [PK_Revision_revId] PRIMARY KEY CLUSTERED 
(
	[revId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RevisionEntidad](
	[reeId] [bigint] IDENTITY(1,1) NOT NULL,
	[reeEntityClassName] [varchar](255) NULL,
	[reeRevisionType] [int] NULL,
	[reeTimeStamp] [bigint] NULL,
	[reeRevision] [bigint] NOT NULL,
 CONSTRAINT [PK_RevisionEntidad_reeId] PRIMARY KEY CLUSTERED 
(
	[reeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RolAfiliado_aud](
	[roaId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[roaCargo] [varchar](200) NULL,
	[roaClaseIndependiente] [varchar](50) NULL,
	[roaClaseTrabajador] [varchar](20) NULL,
	[roaEstadoAfiliado] [varchar](8) NULL,
	[roaEstadoEnEntidadPagadora] [varchar](20) NULL,
	[roaFechaIngreso] [date] NULL,
	[roaFechaRetiro] [datetime] NULL,
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
	[roaEstadoEnEntidadPagadoraPension] [varchar](20) NULL,
	[roaDiaHabilVencimientoAporte] [smallint] NULL,
	[roaMarcaExpulsion] [varchar](22) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [RolContactoEmpleador_aud](
	[rceId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[rceTipoRolContactoEmpleador] [varchar](50) NULL,
	[rceEmpleador] [bigint] NULL,
	[rcePersona] [bigint] NULL,
	[rceCargo] [varchar](100) NULL,
	[rcetoken] [varchar](50) NULL,
	[rceCorreoEnviado] [bit] NULL,
	[rceUbicacion] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SedeCajaCompensacion_aud](
	[sccfId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sccfNombre] [varchar](100) NULL,
	[sccfVirtual] [bit] NULL,
	[sccCodigo] [varchar](2) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SitioPago_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sipCodigo] [varchar](3) NOT NULL,
	[sipNombre] [varchar](255) NOT NULL,
	[sipInfraestructura] [bigint] NOT NULL,
	[sipActivo] [bit] NOT NULL,
	[sipId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SocioEmpleador_aud](
	[semId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[semExistenCapitulaciones] [bit] NULL,
	[semIdentifiDocumCapitulaciones] [varchar](255) NULL,
	[semConyugue] [bigint] NULL,
	[semEmpleador] [bigint] NULL,
	[semPersona] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Solicitud_aud](
	[solId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[solAnulada] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudAfiliaciEmpleador_aud](
	[saeId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[saeCodigoEtiquetaPreimpresa] [varchar](12) NULL,
	[saeEstadoSolicitud] [varchar](50) NULL,
	[saeFechaAprobacionConsejo] [datetime2](7) NULL,
	[saeNumeroActoAdministrativo] [varchar](50) NULL,
	[saeNumeroCustodiaFisica] [varchar](12) NULL,
	[saeEmpleador] [bigint] NULL,
	[saeSolicitudGlobal] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudAfiliacionPersona_aud](
	[sapId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sapEstadoSolicitud] [varchar](50) NULL,
	[sapRolAfiliado] [bigint] NULL,
	[sapSolicitudGlobal] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudAnalisisNovedadFovis_aud](
	[sanId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sanSolicitudGlobal] [bigint] NOT NULL,
	[sanSolicitudNovedad] [bigint] NOT NULL,
	[sanPersona] [bigint] NOT NULL,
	[sanEstadoSolicitud] [varchar](9) NOT NULL,
	[sanPostulacionFovis] [bigint] NOT NULL,
	[sanObservaciones] [varchar](500) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudAporte_aud](
	[soaSolicitudGlobal] [bigint] NOT NULL,
	[soaEstadoSolicitud] [varchar](30) NULL,
	[soaAporteGeneral] [bigint] NULL,
	[soaNumeroIdentificacion] [varchar](16) NULL,
	[soaTipoIdentificacion] [varchar](20) NULL,
	[soaNombreAportante] [varchar](200) NULL,
	[soaPeriodoAporte] [varchar](7) NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[soaTipoSolicitante] [varchar](13) NULL,
	[soaId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudAsignacion_aud](
	[safId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[safSolicitudGlobal] [bigint] NOT NULL,
	[safFechaAceptacion] [datetime] NULL,
	[safUsuario] [varchar](50) NULL,
	[safValorSFVAsignado] [numeric](19, 5) NULL,
	[safEstadoSolicitudAsignacion] [varchar](50) NULL,
	[safComentarios] [varchar](500) NULL,
	[safCicloAsignacion] [bigint] NOT NULL,
	[safComentarioControlInterno] [varchar](500) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudAsociacionPersonaEntidadPagadora_aud](
	[soaId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
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
	[soaIdentificadorCartaResultadoGestion] [varchar](255) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudCorreccionAporte_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[scaEstadoSolicitud] [varchar](25) NULL,
	[scaTipoSolicitante] [varchar](25) NULL,
	[scaObservacionSupervisor] [varchar](255) NULL,
	[scaResultadoSupervisor] [varchar](10) NULL,
	[scaSolicitudGlobal] [bigint] NULL,
	[scaPersona] [bigint] NULL,
	[scaAporteGeneral] [bigint] NULL,
	[scaId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudDesafiliacion_aud](
	[sodId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sodComentarioCoordinador] [varchar](500) NULL,
	[sodEstadoSolicitud] [varchar](9) NULL,
	[sodSolicitudGlobal] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudDevolucionAporte_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sdaEstadoSolicitud] [varchar](25) NULL,
	[sdaTipoSolicitante] [varchar](13) NULL,
	[sdaPersona] [bigint] NULL,
	[sdaObservacionAnalista] [varchar](255) NULL,
	[sdaObservacionSupervisor] [varchar](255) NULL,
	[sdaResultadoAnalista] [varchar](10) NULL,
	[sdaResultadoSupervisor] [varchar](10) NULL,
	[sdaDevolucionAporte] [bigint] NULL,
	[sdaSolicitudGlobal] [bigint] NULL,
	[sdaId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudFiscalizacion_aud](
	[sfiId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sfiEstadoFiscalizacion] [varchar](11) NULL,
	[sfiSolicitudGlobal] [bigint] NOT NULL,
	[sfiCicloAportante] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudGestionCobroElectronico_aud](
	[sgeId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sgeEstado] [varchar](33) NOT NULL,
	[sgeCartera] [bigint] NOT NULL,
	[sgeTipoAccionCobro] [varchar](4) NOT NULL,
	[sgeSolicitud] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudGestionCobroFisico_aud](
	[sgfId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sgfDocumentoSoporte] [bigint] NULL,
	[sgfEstado] [varchar](52) NULL,
	[sgfFechaRemision] [datetime] NULL,
	[sgfObservacionRemision] [varchar](255) NULL,
	[sgfTipoAccionCobro] [varchar](4) NOT NULL,
	[sgfSolicitud] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudGestionCobroManual_aud](
	[scmId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[scmCicloAportante] [bigint] NOT NULL,
	[scmEstadoSolicitud] [varchar](25) NULL,
	[scmSolicitudGlobal] [bigint] NOT NULL,
	[scmLineaCobro] [varchar](3) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudGestionCruce_aud](
	[sgcId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sgcSolicitudPostulacion] [bigint] NOT NULL,
	[sgcEstadoCruceHogar] [varchar](53) NULL,
	[sgcTipoCruce] [varchar](8) NULL,
	[sgcEstado] [varchar](32) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudLegalizacionDesembolso_aud](
	[sldId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sldSolicitudGlobal] [bigint] NOT NULL,
	[sldPostulacionFOVIS] [bigint] NULL,
	[sldEstadoSolicitud] [varchar](48) NULL,
	[sldLegalizacionDesembolso] [bigint] NULL,
	[sldObservaciones] [varchar](500) NULL,
	[sldFechaOperacion] [datetime] NULL,
	[sldJsonPostulacion] [text] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudLiquidacionSubsidio_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
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
	[slsId] [bigint] NOT NULL,
	[slsFechaEvaluacionPrimerNivel] [datetime] NULL,
	[slsFechaEvaluacionSegundoNivel] [datetime] NULL,
	[slsCodigoReclamo] [varchar](50) NULL,
	[slsComentarioReclamo] [varchar](250) NULL,
	[slsFechaDispersion] [datetime] NULL,
	[slsConsideracionAporteDesembolso] [bit] NULL,
	[slsTipoDesembolso] [varchar](40) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedad_aud](
	[snoId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[snoEstadoSolicitud] [varchar](50) NULL,
	[snoNovedad] [bigint] NULL,
	[snoSolicitudGlobal] [bigint] NULL,
	[snoObservaciones] [varchar](200) NULL,
	[snoCargaMultiple] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedadEmpleador_aud](
	[sneId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sneIdEmpleador] [bigint] NULL,
	[sneIdSolicitudNovedad] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedadFovis_aud](
	[snfId] [bigint] IDENTITY(1,1) NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[snfSolicitudGlobal] [bigint] NOT NULL,
	[snfEstadoSolicitud] [varchar](38) NOT NULL,
	[snfParametrizacionNovedad] [bigint] NOT NULL,
	[snfObservaciones] [varchar](200) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedadPersona_aud](
	[snpId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[snpPersona] [bigint] NOT NULL,
	[snpSolicitudNovedad] [bigint] NOT NULL,
	[snpRolAfiliado] [bigint] NULL,
	[snpBeneficiario] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedadPersonaFovis_aud](
	[spfId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[spfPersona] [bigint] NULL,
	[spfSolicitudNovedadFovis] [bigint] NOT NULL,
	[spfPostulacionFovis] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudNovedadPila_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[spiSolicitudNovedad] [bigint] NOT NULL,
	[spiRegistroDetallado] [bigint] NOT NULL,
	[spiId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudPostulacion_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[spoSolicitudGlobal] [bigint] NOT NULL,
	[spoPostulacionFOVIS] [bigint] NULL,
	[spoEstadoSolicitud] [varchar](42) NULL,
	[spoObservaciones] [varchar](500) NULL,
	[spoId] [bigint] NOT NULL,
	[spoObservacionesWeb] [varchar](500) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SolicitudPreventiva_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sprActualizacionEfectiva] [bit] NULL,
	[sprBackActualizacion] [varchar](255) NULL,
	[sprContactoEfectivo] [bit] NULL,
	[sprEstadoSolicitudPreventiva] [varchar](34) NULL,
	[sprPersona] [bigint] NOT NULL,
	[sprRequiereFiscalizacion] [bit] NULL,
	[sprTipoSolicitanteMovimientoAporte] [varchar](14) NOT NULL,
	[sprSolicitudGlobal] [bigint] NOT NULL,
	[sprTipoGestionCartera] [varchar](10) NULL,
	[sprId] [bigint] NOT NULL,
	[sprFechaFiscalizacion] [date] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SucursalEmpresa_aud](
	[sueId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[sueCodigo] [varchar](10) NULL,
	[sueNombre] [varchar](100) NULL,
	[sueCodigoCIIU] [smallint] NULL,
	[sueEmpresa] [bigint] NULL,
	[sueUbicacion] [bigint] NULL,
	[sueEstadoSucursal] [varchar](25) NULL,
	[sueCoindicirCodigoPila] [bit] NULL,
	[sueMedioPagoSubsidioMonetario] [varchar](30) NULL,
	[sueSucursalPrincipal] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [SucursaRolContactEmpleador_aud](
	[srcId] [bigint] NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[srcRolContactoEmpleador] [bigint] NULL,
	[srcSucursalEmpleador] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Tarjeta_aud](
	[tarId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[tarEstadoTarjeta] [varchar](20) NULL,
	[tarNumeroTarjeta] [varchar](20) NULL,
	[afiPersona] [bigint] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [TasasInteresMora_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[timFechaInicioTasa] [date] NOT NULL,
	[timFechaFinTasa] [date] NOT NULL,
	[timNumeroPeriodoTasa] [smallint] NOT NULL,
	[timPorcentajeTasa] [numeric](4, 4) NOT NULL,
	[timNormativa] [varchar](100) NOT NULL,
	[timTipoInteres] [varchar](20) NOT NULL,
	[timId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [TipoInfraestructura_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[tifCodigo] [varchar](3) NOT NULL,
	[tifNombre] [varchar](255) NOT NULL,
	[tifMedidaCapacidad] [varchar](40) NOT NULL,
	[tifActivo] [bit] NOT NULL,
	[tifId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [TipoTenencia_aud](
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[tenCodigo] [smallint] NOT NULL,
	[tenNombre] [varchar](255) NOT NULL,
	[tenActivo] [bit] NOT NULL,
	[tenId] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [TipoVia_aud](
	[tviId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[tviNombreVia] [varchar](20) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Ubicacion_aud](
	[ubiId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ubiAutorizacionEnvioEmail] [bit] NULL,
	[ubiCodigoPostal] [varchar](10) NULL,
	[ubiDireccionFisica] [varchar](300) NULL,
	[ubiEmail] [varchar](255) NULL,
	[ubiIndicativoTelFijo] [varchar](2) NULL,
	[ubiTelefonoCelular] [varchar](10) NULL,
	[ubiTelefonoFijo] [varchar](7) NULL,
	[ubiMunicipio] [smallint] NULL,
	[ubiDescripcionIndicacion] [varchar](100) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [UbicacionEmpresa_aud](
	[ubeId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[ubeEmpresa] [bigint] NULL,
	[ubeUbicacion] [bigint] NULL,
	[ubeTipoUbicacion] [varchar](30) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ValidacionProceso_aud](
	[vapId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[vapBloque] [varchar](150) NULL,
	[vapValidacion] [varchar](100) NULL,
	[vapProceso] [varchar](100) NULL,
	[vapEstadoProceso] [varchar](20) NULL,
	[vapOrden] [int] NULL,
	[vapObjetoValidacion] [varchar](60) NULL,
	[vapInversa] [bit] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [VariableComunicado_aud](
	[vcoId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[vcoClave] [varchar](55) NULL,
	[vcoDescripcion] [varchar](200) NULL,
	[vcoNombre] [varchar](50) NULL,
	[vcoPlantillaComunicado] [bigint] NOT NULL,
	[vcoNombreConstante] [varchar](100) NULL,
	[vcoTipoVariableComunicado] [varchar](20) NULL,
	[vcoOrden] [varchar](3) NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Visita_aud](
	[visId] [bigint] NOT NULL,
	[REV] [bigint] NOT NULL,
	[REVTYPE] [smallint] NULL,
	[visFecha] [date] NOT NULL,
	[visNombresEncargado] [varchar](50) NOT NULL,
	[visCodigoIdentificadorECM] [varchar](255) NOT NULL
) ON [PRIMARY]
