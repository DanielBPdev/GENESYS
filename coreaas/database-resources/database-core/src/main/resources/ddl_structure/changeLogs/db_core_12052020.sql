--liquibase formatted sql

--changeset jocampo:01
--comment: creacion de tablas externas 
CREATE EXTERNAL TABLE [pila].[TemAporte](
	[temId] [bigint] NOT NULL,
	[temIdTransaccion] [bigint] NULL,
	[temRegistroGeneral] [bigint] NULL,
	[temPeriodoAporte] [varchar](7) NULL,
	[temValTotalApoObligatorio] [numeric](19, 5) NULL,
	[temValorIntMoraGeneral] [numeric](19, 5) NULL,
	[temFechaRecaudo] [date] NULL,
	[temTipoIdAportante] [varchar](20) NULL,
	[temNumeroIdAportante] [varchar](16) NULL,
	[temCodEntidadFinanciera] [smallint] NULL,
	[temOperadorInformacion] [bigint] NULL,
	[temModalidadPlanilla] [varchar](40) NULL,
	[temModalidadRecaudoAporte] [varchar](40) NULL,
	[temApoConDetalle] [bit] NULL,
	[temFormaReconocimientoAporte] [varchar](50) NULL,
	[temNumeroCuenta] [varchar](60) NULL,
	[temFechaProcesamiento] [datetime] NULL,
	[temEstadoAporteRecaudo] [varchar](50) NULL,
	[temEstadoRegistroAporte] [varchar](50) NULL,
	[temNumeroPlanillaManual] [varchar](10) NULL,
	[temMarcaAporteSimulado] [bit] NULL,
	[temMarcaAporteManual] [bit] NULL,
	[temRegistroDetallado] [bigint] NULL,
	[temTipoIdCotizante] [varchar](20) NULL,
	[temNumeroIdCotizante] [varchar](16) NULL,
	[temDiasCotizados] [smallint] NULL,
	[temHorasLaboradas] [smallint] NULL,
	[temSalarioBasico] [numeric](19, 5) NULL,
	[temMunicipioLaboral] [varchar](6) NULL,
	[temDepartamentoLaboral] [varchar](2) NULL,
	[temValorIBC] [numeric](19, 5) NULL,
	[temTarifa] [numeric](5, 5) NULL,
	[temSalarioIntegral] [bit] NULL,
	[temAporteObligatorio] [numeric](19, 5) NULL,
	[temValorSaldoAporte] [numeric](19, 5) NULL,
	[temValorIntMoraDetalle] [numeric](19, 5) NULL,
	[temCorrecciones] [varchar](400) NULL,
	[temEstadoAporteAjuste] [varchar](50) NULL,
	[temMarcaValRegistroAporte] [varchar](50) NOT NULL,
	[temEstadoValRegistroAporte] [varchar](60) NOT NULL,
	[temUsuarioAprobadorAporte] [varchar](50) NOT NULL,
	[temPresentaNovedad] [bit] NULL,
	[temEnProceso] [bit] NULL
)
WITH
(
 DATA_SOURCE = PilaReferenceData,
 SCHEMA_NAME = 'dbo',
 OBJECT_NAME = 'TemAporte'
);

CREATE EXTERNAL TABLE [pila].[TemAportante](
	[tapId] [bigint] NOT NULL,
	[tapIdTransaccion] [bigint] NOT NULL,
	[tapEmail] [varchar](60) NULL,
	[tapTipoSolicitud] [varchar](100) NULL,
	[tapTipoDocTramitador] [varchar](20) NULL,
	[tapIdTramitador] [varchar](16) NULL,
	[tapTipoDocAportante] [varchar](20) NULL,
	[tapIdAportante] [varchar](16) NULL,

	[tapRazonSocial] [varchar](200) NULL,
	[tapDigVerAportante] [smallint] NULL,
	[tapDireccion] [varchar](40) NULL,
	[tapCodCiudad] [varchar](3) NULL,
	[tapCodDepartamento] [varchar](2) NULL,
	[tapTelefono] [bigint] NULL,
	[tapFax] [bigint] NULL,
	[tapFechaMatricula] [date] NULL,
	[tapNaturalezaJuridica] [smallint] NULL,
	[tapFechaHoraSolicitud] [datetime] NULL,
	[tapMarcaCreacion] [varchar](100) NULL,
	[tapMarcaSucursal] [bit] NULL,
	[tapDigVerTramitador] [smallint] NULL,
	[tapNombreTramitador] [varchar](200) NULL,
	[tapEsEmpleadorReintegrable] [bit] NULL,
	[tapEnviadoAFiscalizacion] [bit] NULL,
	[tapMotivoFiscalizacion] [varchar](30) NULL,
	[tapPrimerNombreAportante] [varchar](20) NULL,
	[tapSegundoNombreAportante] [varchar](30) NULL,
	[tapPrimerApellidoAportante] [varchar](20) NULL,
	[tapSegundoApellidoAportante] [varchar](30) NULL
)
WITH
(
 DATA_SOURCE = PilaReferenceData,
 SCHEMA_NAME = 'dbo',
 OBJECT_NAME = 'TemAportante'
);

CREATE EXTERNAL TABLE [pila].[TemCotizante](
	[tctId] [bigint] NOT NULL,
	[tctIdTransaccion] [bigint] NOT NULL,
	[tctTipoCotizante] [varchar](50) NULL,
	[tctCodSucursal] [varchar](10) NULL,
	[tctNomSucursal] [varchar](40) NULL,
	[tctCodSucursalPILA] [varchar](10) NULL,
	[tctNomSucursalPILA] [varchar](40) NULL,
	[tctTipoIdCotizante] [varchar](20) NULL,
	[tctIdCotizante] [varchar](16) NULL,
	[tctPrimerApellido] [varchar](20) NULL,
	[tctSegundoApellido] [varchar](30) NULL,
	[tctPrimerNombre] [varchar](20) NULL,
	[tctSegundoNombre] [varchar](30) NULL,
	[tctCodigoDepartamento] [varchar](2) NULL,
	[tctCodigoMunicipio] [varchar](6) NULL,
	[tctTipoIdEmpleador] [varchar](20) NULL,
	[tctIdEmpleador] [varchar](16) NULL,
	[tctFechaHoraSolicitud] [datetime] NULL,
	[tctMarcaCreacion] [varchar](100) NULL,
	[tctTipoSolicitud] [varchar](100) NULL,
	[tctEsFallecido] [bit] NULL,
	[tctEsTrabajadorReintegrable] [bit] NULL,
	[tctGrupoFamiliarReintegrable] [bit] NULL,
)
WITH
(
 DATA_SOURCE = PilaReferenceData,
 SCHEMA_NAME = 'dbo',
 OBJECT_NAME = 'TemCotizante'
);