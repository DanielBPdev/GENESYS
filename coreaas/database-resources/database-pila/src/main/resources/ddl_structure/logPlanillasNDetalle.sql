--- Tabla de logDealle para las planillas N
if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'logPlanillasNDetalle' and TABLE_SCHEMA = 'dbo')
begin

CREATE TABLE [dbo].[logPlanillasNDetalle](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[pipIdPlanilla] [bigint] NOT NULL,
	[pi2Id] [bigint] NULL,
	[redTipoCotizante] [int] NOT NULL,
	[pi2PrimerApellido] [int] NOT NULL,
	[pi2SegundoApellido] [int] NOT NULL,
	[pi2PrimerNombre] [int] NOT NULL,
	[pi2SegundoNombre] [int] NOT NULL,
	[pi2NovIngreso] [int] NOT NULL,
	[pi2NovRetiro] [int] NOT NULL,
	[pi2NovVSP] [int] NOT NULL,
	[pi2NovVST] [int] NOT NULL,
	[pi2NovSLN] [int] NOT NULL,
	[pi2NovIGE] [int] NOT NULL,
	[pi2NovLMA] [int] NOT NULL,
	[pi2NovVACLR] [int] NOT NULL,
	[pi2DiasIRL] [int] NOT NULL,
	[pi2DiasCotizados] [int] NOT NULL,
	[pi2SalarioBasico] [int] NOT NULL,
	[pi2ValorIBC] [int] NOT NULL,
	[pi2Tarifa] [int] NOT NULL,
	[pi2AporteObligatorio] [int] NOT NULL,
	[pi2SalarioIntegral] [int] NOT NULL,
	[pi2FechaIngreso] [int] NOT NULL,
	[pi2FechaRetiro] [int] NOT NULL,
	[pi2FechaInicioVSP] [int] NOT NULL,
	[pi2FechaInicioSLN] [int] NOT NULL,
	[pi2FechaFinSLN] [int] NOT NULL,
	[pi2FechaInicioIGE] [int] NOT NULL,
	[pi2FechaFinIGE] [int] NOT NULL,
	[pi2FechaInicioLMA] [int] NOT NULL,
	[pi2FechaFinLMA] [int] NOT NULL,
	[pi2FechaInicioVACLR] [int] NOT NULL,
	[pi2FechaFinVACLR] [int] NOT NULL,
	[pi2FechaInicioVCT] [int] NOT NULL,
	[pi2FechaFinVCT] [int] NOT NULL,
	[pi2FechaInicioIRL] [int] NOT NULL,
	[pi2FechaFinIRL] [int] NOT NULL,
	[registroDetalladoOriginal] [bigint] NULL,
	[fechaProceso] [datetime] NULL
)

end