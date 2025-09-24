--liquibase formatted sql
--changeset ovega:01
/****** Object:  Table [rno].[HistoricoMaestroAfiliados]    Script Date: 05/12/2022 9:59:03 a. m. ******/
IF EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
				WHERE TABLE_SCHEMA = 'rno' and TABLE_NAME = 'HistoricoMaestroAfiliados')

DROP TABLE [rno].[HistoricoMaestroAfiliados]
 

/****** Object:  Table [rno].[HistoricoInconsistenciasUGPP]    Script Date: 05/12/2022 9:59:03 a. m. ******/
IF EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
				WHERE TABLE_SCHEMA = 'rno' and TABLE_NAME = 'HistoricoInconsistenciasUGPP')

DROP TABLE [rno].[HistoricoInconsistenciasUGPP]
 

/****** Object:  Table [rno].[HistoricoAsignacionEntregaReintegroMicrodatoFOVIS]    Script Date: 05/12/2022 9:59:03 a. m. ******/
IF EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
				WHERE TABLE_SCHEMA = 'rno' and TABLE_NAME = 'HistoricoAsignacionEntregaReintegroMicrodatoFOVIS')

DROP TABLE [rno].[HistoricoAsignacionEntregaReintegroMicrodatoFOVIS]
 

/****** Object:  Table [rno].[HistoricoAsignacionEntregaReintegroFOVIS]    Script Date: 05/12/2022 9:59:03 a. m. ******/
IF EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
				WHERE TABLE_SCHEMA = 'rno' and TABLE_NAME = 'HistoricoAsignacionEntregaReintegroFOVIS')

DROP TABLE [rno].[HistoricoAsignacionEntregaReintegroFOVIS]
 

/****** Object:  Table [rno].[HistoricoAfiliadosACargo]    Script Date: 05/12/2022 9:59:03 a. m. ******/
IF EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
				WHERE TABLE_SCHEMA = 'rno' and TABLE_NAME = 'HistoricoAfiliadosACargo')

DROP TABLE [rno].[HistoricoAfiliadosACargo]
 

/****** Object:  Table [rno].[HistoricoAfiliados]    Script Date: 05/12/2022 9:59:03 a. m. ******/
IF EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
				WHERE TABLE_SCHEMA = 'rno' and TABLE_NAME = 'HistoricoAfiliados')

DROP TABLE [rno].[HistoricoAfiliados]
 

/****** Object:  Table [rno].[HistoricoAfiliados]    Script Date: 05/12/2022 9:59:03 a. m. ******/
 

CREATE TABLE [rno].[HistoricoAfiliados](
	[hraId] [bigint] IDENTITY(1,1) NOT NULL,
	[hraFechaHistorico] [date] NOT NULL,
	[hraTipoIdentificacionEmpresa] [varchar](1) NULL,
	[hraNumeroIdentificacion] [varchar](16) NULL,
	[hraTipoIdentificacionAfiliado] [varchar](1) NULL,
	[hraNumeroIdentificacionAfiliado] [varchar](16) NULL,
	[hraPerPrimerNombre] [varchar](50) NULL,
	[hraPerSegundoNombre] [varchar](50) NULL,
	[hraPerPrimerApellido] [varchar](50) NULL,
	[hraPerSegundoApellido] [varchar](50) NULL,
	[hraPedFechaNacimiento] [varchar](30) NULL,
	[hraGenero] [varchar](1) NULL,
	[hraCodigoMunicipio] [varchar](5) NULL,
	[hraAreaGeografica] [varchar](1) NULL,
	[hraSalarioBasico] [bigint] NULL,
	[hraTipoAfiliado] [varchar](2) NULL,
	[hraCategoria] [varchar](1) NULL,
	[hraBeneficiarioCuota] [varchar](1) NULL,
	[hraFechaInicialReporte] [date] NULL,
	[hraFechaFinalReporte] [date] NULL,
	[hraOrientacionSexual] [varchar](30) NULL,
	[hraNivelEducativo] [varchar](50) NULL,
	[hraOcupacionProfesional] [varchar](4) NULL,
	[hraFactorVulnerabilidad] [varchar](60) NULL,
	[hraEstadoCivil] [varchar](20) NULL,
	[hraPertenenciaEtnica] [varchar](70) NULL,
	[hraPaisResidencia] [varchar](70) NULL)

/****** Object:  Table [rno].[HistoricoAfiliadosACargo]    Script Date: 05/12/2022 9:59:04 a. m. ******/
 

CREATE TABLE [rno].[HistoricoAfiliadosACargo](
	[hacId] [bigint] IDENTITY(1,1) NOT NULL,
	[hacFechaHistorico] [date] NOT NULL,
	[hacTipoIdentificacionEmpresa] [varchar](20) NULL,
	[hacNumeroIdentificacionEmpresa] [varchar](16) NULL,
	[hacTipoIdentificacionAfiliado] [varchar](20) NULL,
	[hacNumeroIdentificacionAfiliado] [varchar](16) NULL,
	[hacTipoIdentificacionPersonaACargo] [varchar](20) NULL,
	[hacNumeroIdentificacionPersonaACargo] [varchar](16) NULL,
	[hacPrimerNombrePersonaACargo] [varchar](50) NULL,
	[hacSegundoNombrePersonaACargo] [varchar](50) NULL,
	[hacPrimerApellidoPersonaACargo] [varchar](50) NULL,
	[hacSegundoApellidoPersonaACargo] [varchar](50) NULL,
	[hacFechaNacimientoPersonaACargo] [varchar](30) NULL,
	[hacGeneroPersonaACargo] [int] NULL,
	[hacBenParentesco] [int] NULL,
	[hacCondicionDiscapacidad] [int] NULL,
	[hacTipoCuotaMonetariaPagadoPersonaCargo] [int] NULL,
	[hacNumeroCuotasPagadas] [int] NULL,
	[hacNumeroPeriodosPagados] [int] NULL,
	[hacFechaInicialReporte] [date] NULL,
	[hacFechaFinalReporte] [date] NULL,
	[hacValorTotal] [bigint] NULL,
	[hacMunicipioResidencia] [varchar](25) NULL,
	[hacAreaGeografica] [varchar](1) NULL
)

/****** Object:  Table [rno].[HistoricoAsignacionEntregaReintegroFOVIS]    Script Date: 05/12/2022 9:59:04 a. m. ******/
 

CREATE TABLE [rno].[HistoricoAsignacionEntregaReintegroFOVIS](
	[hapId] [bigint] IDENTITY(1,1) NOT NULL,
	[hapFechaHistorico] [date] NOT NULL,
	[hapFuenteFinanciamiento] [varchar](1) NULL,
	[hapTipoPlanVivienda] [int] NULL,
	[hapCodigoMunicipio] [varchar](5) NULL,
	[hapGenero] [int] NULL,
	[hapRangoEdad] [int] NULL,
	[hapNivelIngreso] [int] NULL,
	[hapComponente] [int] NULL,
	[hapEstadoSubsidio] [int] NULL,
	[hapAnioVigencia] [int] NULL,
	[hapCantidadSubsidios] [int] NULL,
	[hapValorSubsidios] [bigint] NULL,
	[hapFechaInicialReporte] [date] NULL,
	[hapFechaFinalReporte] [date] NULL,
	[hapTipoIdIntegrante] [varchar](1) NULL,
	[hapNumeroIdIntegrante] [varchar](16) NULL 
 )
 

/****** Object:  Table [rno].[HistoricoAsignacionEntregaReintegroMicrodatoFOVIS]    Script Date: 05/12/2022 9:59:04 a. m. ******/
 

CREATE TABLE [rno].[HistoricoAsignacionEntregaReintegroMicrodatoFOVIS](
	[hamId] [bigint] IDENTITY(1,1) NOT NULL,
	[hamFechaHistorico] [date] NULL,
	[hamTipoIdentificacion] [varchar](1) NULL,
	[hamNumeroIdentificacion] [varchar](16) NULL,
	[hamComponenteHogar] [varchar](1) NULL,
	[hamTipoIdIntegrante] [varchar](1) NULL,
	[hamNumeroIdIntegrante] [varchar](16) NULL,
	[hamAfiliadoACaja] [varchar](1) NULL,
	[hamPrimerNombre] [varchar](50) NULL,
	[hamSegundoNombre] [varchar](50) NULL,
	[hamPrimerApellido] [varchar](50) NULL,
	[hamSegundoApellido] [varchar](50) NULL,
	[hamParentezcoIntegrante] [varchar](2) NULL,
	[hamIngresosIntegrante] [numeric](19, 5) NULL,
	[hamNivelIngreso] [int] NULL,
	[hamComponente] [int] NULL,
	[hamAnioVigenciaAsignacionSubsidio] [int] NULL,
	[hamEstadoSubsidio] [int] NULL,
	[hamValorSubsidios] [bigint] NULL,
	[hamCodigoTipoPlanVivienda] [int] NULL,
	[hamFuenteFinanciamiento] [int] NULL,
	[hamFechaInicialReporte] [date] NULL,
	[hamFechaFinalReporte] [date] NULL,
	[hamPofid] [bigint] NULL 
)
 

/****** Object:  Table [rno].[HistoricoInconsistenciasUGPP]    Script Date: 05/12/2022 9:59:04 a. m. ******/
 

CREATE TABLE [rno].[HistoricoInconsistenciasUGPP](
	[hinId] [bigint] IDENTITY(1,1) NOT NULL,
	[hinFechaHistorico] [date] NOT NULL,
	[hinTipoAdmin] [varchar](3) NULL,
	[hinCodAdmin] [varchar](150) NULL,
	[hinNomAdmin] [varchar](100) NULL,
	[hinTipoIdAportante] [varchar](2) NULL,
	[hinNumIdAportante] [varchar](16) NULL,
	[hinRazonSocial] [varchar](250) NULL,
	[hinCodigoDep] [varchar](2) NULL,
	[hinCod] [varchar](5) NULL,
	[hinDireccion] [varchar](300) NULL,
	[hinTipoIdCotizante] [varchar](2) NULL,
	[hinNumIdCotizante] [varchar](16) NULL,
	[hinConcepto] [varchar](11) NULL,
	[hinAnioInicio] [int] NULL,
	[hinMesInicio] [int] NULL,
	[hinAnioFin] [int] NULL,
	[hinMesFin] [int] NULL,
	[hinDeuda] [numeric](9, 0) NULL,
	[hinUltimaAccion] [varchar](7) NULL,
	[hinFechaAccion] [date] NULL,
	[hinObservaciones] [int] NULL,
	[hinFechaInicialReporte] [date] NULL,
	[hinFechaFinalReporte] [date] NULL )
 

/****** Object:  Table [rno].[HistoricoMaestroAfiliados]    Script Date: 05/12/2022 9:59:05 a. m. ******/
 

CREATE TABLE [rno].[HistoricoMaestroAfiliados](
	[hmaId] [bigint] IDENTITY(1,1) NOT NULL,
	[hmaFechaHistorico] [date] NOT NULL,
	[hmaTipoRegistro] [varchar](1) NULL,
	[hmaTipoIdentificacionAfiliado] [varchar](2) NULL,
	[hmaNumeroIdentificacionAfiliado] [varchar](16) NULL,
	[hmaTipoIdentificacionPoblacion] [varchar](2) NULL,
	[hmaNumeroIdentificacionPoblacion] [varchar](16) NULL,
	[hmaCodigoGenero] [varchar](1) NULL,
	[hmaFechaNacimiento] [date] NULL,
	[hmaPrimerApellido] [varchar](50) NULL,
	[hmaSegundoApellido] [varchar](50) NULL,
	[hmaPrimerNombre] [varchar](50) NULL,
	[hmaSegundoNombre] [varchar](50) NULL,
	[hmaDepartamentoResidencia] [varchar](2) NULL,
	[hmaMunicipioResidencia] [varchar](5) NULL,
	[hmaFechaAfiliacion] [date] NULL,
	[hmaCodigoCaja] [varchar](150) NULL,
	[hmaCodTipoAfiliado] [varchar](1) NULL,
	[hmaTipoIdentificacionAportante] [varchar](2) NULL,
	[hmaNumeroIdentificacionAportante] [varchar](16) NULL,
	[hmaDigitoVerficacionAportante] [smallint] NULL,
	[hmaRazonSocialAportante] [varchar](250) NULL,
	[hmaFechaVinculacion] [date] NULL,
	[hmaDepartamentroLaboral] [varchar](2) NULL,
	[hmaMunicipioLaboral] [varchar](5) NULL,
	[hmaAlDia] [varchar](1) NULL,
	[hmaCodigoTipoMiembro] [varchar](1) NULL,
	[hmaTipoRelacionConAfiliado] [varchar](1) NULL,
	[hmaFechaInicialReporte] [date] NULL,
	[hmaFechaFinalReporte] [date] NULL,
	[hmaCondicionBeneficiario] [varchar](1) NULL
	)
 
 
 
