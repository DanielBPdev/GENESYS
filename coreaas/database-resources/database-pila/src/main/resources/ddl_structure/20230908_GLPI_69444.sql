if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = N'TemNovedad_val_proc' and TABLE_SCHEMA = N'dbo')
	begin
		CREATE TABLE [dbo].[TemNovedad_val_proc]([canal] [varchar](30) NULL,[novedadexistenteCore] [bit] NULL,[tenId] [bigint] NOT NULL,[tenIdTransaccion] [bigint] NOT NULL,[tenMarcaNovedadSimulado] [bit] NULL,
		[tenMarcaNovedadManual] [bit] NULL,[tenRegistroGeneral] [bigint] NOT NULL,[tenRegistroDetallado] [bigint] NOT NULL,[tenTipoIdAportante] [varchar](20) NOT NULL,[tenNumeroIdAportante] [varchar](16) NOT NULL,
		[tenTipoIdCotizante] [varchar](20) NOT NULL,[tenNumeroIdCotizante] [varchar](16) NOT NULL,[tenTipoTransaccion] [varchar](100) NULL,[tenEsIngreso] [bit] NOT NULL,[tenEsRetiro] [bit] NOT NULL,
		[tenFechaInicioNovedad] [date] NULL,[tenFechaFinNovedad] [date] NULL,[tenAccionNovedad] [varchar](20) NOT NULL,[tenMensajeNovedad] [varchar](250) NULL,[tenTipoCotizante] [varchar](50) NULL,
		[tenPrimerApellido] [varchar](20) NULL,[tenSegundoApellido] [varchar](30) NULL,[tenPrimerNombre] [varchar](20) NULL,[tenSegundoNombre] [varchar](30) NULL,[tenCodigoDepartamento] [smallint] NULL,
		[tenCodigoMunicipio] [smallint] NULL,[tenModalidadRecaudoAporte] [varchar](40) NOT NULL,[tenValor] [varchar](255) NULL,[tenEsEmpleadorReintegrable] [bit] NULL,[tenEsTrabajadorReintegrable] [bit] NULL,
		[tenRegistroDetalladoNovedad] [bigint] NULL,[tenEnProceso] [bit] NULL)
	end