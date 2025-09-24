create table [masivos].[MasivosDvGenerarRechazos2](
	[numeroRegistro] bigint,
	[fechaHoraDeCarga] date,
	[nombreArchivo] varchar(200),
	[tipoIdentificacionAportante] varchar(100),
	[numeroIdentificacionAportante] varchar(100),
	[razonSocial] varchar(200),
	[periodoPago] varchar(7),
	[tipoAportante] varchar(40),
	[tipoIdentificacionCotizante] varchar(100),
	[numeroIdentificacionCotizante] varchar(100),
	[tipoCotizante] varchar(40),
	[aporteObligatorio] numeric(19, 5),
	[valorIntereses] numeric(19, 5),
	[totalAporte] numeric(19, 5),
	[error] varchar(300),
	[numeroRadicado] varchar(100)
	)

	CREATE CLUSTERED INDEX IX_MasivosDvGenerarRechazos ON masivos.MasivosDvGenerarRechazos2 ([numeroRadicado]);