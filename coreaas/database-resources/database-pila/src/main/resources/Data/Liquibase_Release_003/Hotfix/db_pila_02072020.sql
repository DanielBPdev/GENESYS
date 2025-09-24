--liquibase formatted sql

--changeset jocampo:01
--comment: 
CREATE EXTERNAL TABLE [core].[AporteDetallado](
	[apdId] [bigint] NOT NULL,
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
	[apdMunicipioLaboral] [varchar](5) NULL,
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
	[apdMarcaPeriodo] [varchar](19) NULL,
	[apdModalidadRecaudoAporte] [varchar](40) NULL,
	[apdMarcaCalculoCategoria] [bit] NULL,
	[apdModificadoAportesOK] [bit] NULL
) WITH
(
 DATA_SOURCE = CoreReferenceData,
 SCHEMA_NAME = 'dbo',
 OBJECT_NAME = 'AporteDetallado'
);