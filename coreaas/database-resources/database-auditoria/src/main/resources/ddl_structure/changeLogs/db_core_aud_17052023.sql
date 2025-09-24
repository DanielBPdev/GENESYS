IF OBJECT_ID('dbo.DocumentosSeguimientoGestion_aud', 'U') IS NOT NULL
	drop table [dbo].[DocumentosSeguimientoGestion_aud]

CREATE TABLE [dbo].[DocumentosSeguimientoGestion_aud](
	                                                     [REV] [bigint] NOT NULL,
	                                                     [REVTYPE] [smallint] NULL,
	                                                     [dsgId] [bigint] NOT NULL,
	                                                     [dsgNrOperacion] [bigint] NULL,
	                                                     [dsgFecha] [date] NULL,
	                                                     [dsgActividad] [varchar](50) NULL,
	                                                     [dsgMedio] [varchar](50) NULL,
	                                                     [dsgResultado] [varchar](50) NULL,
	                                                     [dsgUsuario] [varchar](255) NULL,
	                                                     [dsgDocumento] [varchar](500) NULL,
) ON [PRIMARY]

IF OBJECT_ID('dbo.DocumentosSeguimientoConveniosPago_aud', 'U') IS NOT NULL
	drop table [dbo].[DocumentosSeguimientoConveniosPago_aud]

CREATE TABLE [dbo].[DocumentosSeguimientoConveniosPago_aud](
	                                                           [REV] [bigint] NOT NULL,
	                                                           [REVTYPE] [smallint] NULL,
	                                                           [dscpId] [bigint] NOT NULL,
	                                                           [dscpNrOperacion] [bigint] NULL,
	                                                           [dscpFecha] [date] NULL,
	                                                           [dscpValorDeuda] [varchar](255) NULL,
	                                                           [dscpPeriodo] [date] NULL,
	                                                           [dscpNrCuotas] [bigint] NULL,
	                                                           [dscpFechaInicial] [date] NULL,
	                                                           [dscpFechaFinal] [date] NULL,
	                                                           [dscpResultado] [varchar](255) NULL,
	                                                           [dscpSoporteDocumental] [varchar](500) NULL,
	                                                           [dscpNorConvenio] [bigint] NULL,
	                                                           [dscpUsuario] [varchar](255) NULL,
) ON [PRIMARY]

IF OBJECT_ID('dbo.DocumentosSeguimientoNovedades_aud', 'U') IS NOT NULL
	drop table [dbo].[DocumentosSeguimientoNovedades_aud]

CREATE TABLE [dbo].[DocumentosSeguimientoNovedades_aud](
	                                                       [REV] [bigint] NOT NULL,
	                                                       [REVTYPE] [smallint] NULL,
	                                                       [dsnId] [bigint] NOT NULL,
	                                                       [dsnNrOperacion] [bigint] NULL,
	                                                       [dsnFecha] [date] NULL,
	                                                       [dsnNovedad] [varchar](500) NULL,
	                                                       [dsnFechaRegistroNovedad] [date] NULL,
	                                                       [dsnEstado] [varchar](50) NULL,

) ON [PRIMARY]


IF NOT EXISTS (
		select * from sys.all_objects t inner join sys.all_columns c on  c.object_id = t.object_id
		where t.name = 'bitacoracartera_aud' and c.name = 'bcaComentarios'
	)
	BEGIN
		-- La columna no existe, crearla
		ALTER TABLE [dbo].[BitacoraCartera_aud]
			ADD bcaComentarios varchar(400) NULL;
	END
ELSE
	BEGIN
		-- La columna existe, actualizarla si es necesario
		ALTER TABLE [dbo].[BitacoraCartera_aud]
			ALTER COLUMN bcaComentarios varchar(400) NULL;
	END