--liquibase formatted sql

--changeset rcastillo:01
--comment:Ajuste planillasN
if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'aporteDetalladoRegistroControlN' and TABLE_SCHEMA = 'dbo')
begin
CREATE TABLE [dbo].[aporteDetalladoRegistroControlN](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[redNumeroIdentificacionCotizante] [varchar](20) NULL,
	[apdId] [bigint] NULL,
	[apdAporteGeneral] [bigint] NULL,
	[redDiasCotizados] [smallint] NULL,
	[apdDiasCotizados] [smallint] NULL,
	[diasCotDif] [smallint] NULL,
	[redValorIBC] [numeric](19, 5) NULL,
	[apdValorIBC] [numeric](19, 5) NULL,
	[diferenciaIBC] [numeric](19, 5) NULL,
	[redTarifa] [numeric](19, 5) NULL,
	[apdTarifa] [numeric](19, 5) NULL,
	[diferenciaTarifa] [numeric](19, 5) NULL,
	[redAporteObligatorio] [numeric](19, 5) NULL,
	[apdAporteObligatorio] [numeric](19, 5) NULL,
	[diferenciaAporteCot] [numeric](19, 5) NULL,
	[ActualizarAporteDetalldo] [smallint] NULL,
	[registroGeneralNuevo] [bigint] NULL,
	[pipId] [bigint] NULL,
	[diferenciaMora] [numeric](19, 5) NULL,
	[fechaProcesamiento] [datetime] NULL)
end