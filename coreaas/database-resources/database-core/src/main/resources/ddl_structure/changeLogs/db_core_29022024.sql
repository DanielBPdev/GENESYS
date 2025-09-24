
if not exists (select 1 from  information_schema.columns  where table_schema = 'dbo'and table_name = 'auditoriaRecaudosYPagos')
CREATE TABLE [dbo].[auditoriaRecaudosYPagos](
	[arpId] [bigint] IDENTITY(1,1) NOT NULL,
	[arpPartnerId] [bigint] NULL,
	[arpOriginId] [varchar](100) NULL,
	[arpClientId] [bigint] NULL,
	[arpTransactionId] [bigint] NULL,
	[arpSecuenceId] [varchar](200) NULL,
	[arpCurrencyId] [bigint] NULL,
	[arpAmounthTran] [numeric](18, 0) NULL,
	[arpTransmisionDate] [varchar](100) NULL,
	[arpTransactionType] [varchar](100) NULL,
	[arpBusiness] [varchar](100) NULL,
	[arpState] [varchar](100) NULL,
	[arpCity] [varchar](100) NULL,
	[arpTipoIdentificacion] [varchar](100) NULL,
	[arpNumeroIdentificacion] [varchar](100) NULL,
	[arpUser] [varchar](100) NULL,
	[arpPass] [varchar](100) NULL,
	[arpNumeroAutorizacion] [varchar](100) NULL,
	[arpCampoAdicional] [varchar](100) NULL,
	[arpExitoso] [varchar](20) NULL,
	[arpMensajeError] [varchar](100) NULL
) 