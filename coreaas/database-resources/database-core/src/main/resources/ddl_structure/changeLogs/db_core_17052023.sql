IF OBJECT_ID('aud.DocumentosSeguimientoGestion_aud', 'U') IS NOT NULL
	drop table [aud].[DocumentosSeguimientoGestion_aud]

CREATE TABLE [aud].[DocumentosSeguimientoGestion_aud](
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


ALTER TABLE [aud].[DocumentosSeguimientoGestion_aud]  WITH CHECK ADD  CONSTRAINT [FK_DocumentosSeguimientoGestion_aud_REV] FOREIGN KEY([REV])
	REFERENCES [aud].[Revision] ([revId])


ALTER TABLE [aud].[DocumentosSeguimientoGestion_aud] CHECK CONSTRAINT [FK_DocumentosSeguimientoGestion_aud_REV]



IF OBJECT_ID('aud.DocumentosSeguimientoConveniosPago_aud', 'U') IS NOT NULL
	drop table [aud].[DocumentosSeguimientoConveniosPago_aud]

CREATE TABLE [aud].[DocumentosSeguimientoConveniosPago_aud](
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


ALTER TABLE [aud].[DocumentosSeguimientoConveniosPago_aud]  WITH CHECK ADD  CONSTRAINT [FK_DocumentosSeguimientoConveniosPago_aud_REV] FOREIGN KEY([REV])
	REFERENCES [aud].[Revision] ([revId])


ALTER TABLE [aud].[DocumentosSeguimientoConveniosPago_aud] CHECK CONSTRAINT [FK_DocumentosSeguimientoConveniosPago_aud_REV]



IF OBJECT_ID('aud.DocumentosSeguimientoNovedades_aud', 'U') IS NOT NULL
	drop table [aud].[DocumentosSeguimientoNovedades_aud]

CREATE TABLE [aud].[DocumentosSeguimientoNovedades_aud](
	                                                       [REV] [bigint] NOT NULL,
	                                                       [REVTYPE] [smallint] NULL,
	                                                       [dsnId] [bigint] NOT NULL,
	                                                       [dsnNrOperacion] [bigint] NULL,
	                                                       [dsnFecha] [date] NULL,
	                                                       [dsnNovedad] [varchar](500) NULL,
	                                                       [dsnFechaRegistroNovedad] [date] NULL,
	                                                       [dsnEstado] [varchar](50) NULL,

) ON [PRIMARY]


ALTER TABLE [aud].[DocumentosSeguimientoNovedades_aud]  WITH CHECK ADD  CONSTRAINT [FK_DocumentosSeguimientoNovedades_aud_REV] FOREIGN KEY([REV])
	REFERENCES [aud].[Revision] ([revId])


ALTER TABLE [aud].[DocumentosSeguimientoNovedades_aud] CHECK CONSTRAINT [FK_DocumentosSeguimientoNovedades_aud_REV]










IF OBJECT_ID('dbo.DocumentosSeguimientoConveniosPago', 'U') IS NOT NULL
	DROP TABLE dbo.DocumentosSeguimientoConveniosPago;

CREATE TABLE [dbo].[DocumentosSeguimientoConveniosPago](
	                                                       [dscpId] [bigint] IDENTITY(1,1) NOT NULL,
	                                                       [dscpNrOperacion] [bigint] NULL,
	                                                       [dscpFecha] [date] NULL,
	                                                       [dscpValorDeuda] [varchar](255) NULL,
	                                                       [dscpPeriodo] [date] NULL,
	                                                       [dscpNrCuotas] [bigint] NULL,
	                                                       [dscpFechaInicial] [date] NULL,
	                                                       [dscpFechaFinal] [date] NULL,
	                                                       [dscpResultado] [varchar](255) NULL,
	                                                       [dscpSoporteDocumental] [varchar](255) NULL,
	                                                       [dscpNorConvenio] [varchar](500) NULL,
	                                                       [dscpUsuario] [varchar](255) NULL,
	                                                       CONSTRAINT [PK_DocumentosSeguimientoConveniosPago_dscpId] PRIMARY KEY CLUSTERED
		                                                       ([dscpId] ASC)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]


IF OBJECT_ID('dbo.DocumentosSeguimientoGestion', 'U') IS NOT NULL
	DROP TABLE dbo.DocumentosSeguimientoGestion;

CREATE TABLE [dbo].[DocumentosSeguimientoGestion](
	                                                 [dsgId] [bigint] IDENTITY(1,1) NOT NULL,
	                                                 [dsgNrOperacion] [bigint] NULL,
	                                                 [dsgFecha] [date] NULL,
	                                                 [dsgActividad] [varchar](50) NULL,
	                                                 [dsgMedio] [varchar](50) NULL,
	                                                 [dsgResultado] [varchar](50) NULL,
	                                                 [dsgUsuario] [varchar](255) NULL,
	                                                 [dsgDocumento] [varchar](255) NULL,
	                                                 CONSTRAINT [PK_DocumentosSeguimientoGestion_dsgId] PRIMARY KEY CLUSTERED
		                                                 (	[dsgId] ASC)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]

IF OBJECT_ID('DocumentosSeguimientoNovedades', 'U') IS NOT NULL
	DROP TABLE DocumentosSeguimientoNovedades;

CREATE TABLE [dbo].[DocumentosSeguimientoNovedades](
	                                                   [dsnId] [bigint] IDENTITY(1,1) NOT NULL,
	                                                   [dsnNrOperacion] [bigint] NULL,
	                                                   [dsnFecha] [date] NULL,
	                                                   [dsnNovedad] [varchar](500) NULL,
	                                                   [dsnFechaRegistroNovedad] [date] NULL,
	                                                   [dsnEstado] [varchar](50) NULL,
	                                                   CONSTRAINT [PK_DocumentosSeguimientoNovedades_dsnId] PRIMARY KEY CLUSTERED
		                                               ([dsnId] ASC)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]) ON [PRIMARY]



IF NOT EXISTS (
		select * from sys.all_objects t inner join sys.all_columns c on  c.object_id = t.object_id
		where t.name = 'bitacoracartera' and c.name = 'bcaComentarios'
	)
	BEGIN
		-- La columna no existe, crearla
		ALTER TABLE [dbo].[BitacoraCartera]
		ADD bcaComentarios varchar(400) NULL;
	END
ELSE
	BEGIN
		-- La columna existe, actualizarla si es necesario
		ALTER TABLE [dbo].[BitacoraCartera]
		ALTER COLUMN bcaComentarios varchar(400) NULL;
	END




IF NOT EXISTS (
		select * from sys.all_objects t inner join sys.all_columns c on  c.object_id = t.object_id
		where t.name = 'bitacoracartera_aud' and c.name = 'bcaComentarios'
	)
	BEGIN
		-- La columna no existe, crearla
		ALTER TABLE [aud].[BitacoraCartera_aud]
		ADD bcaComentarios varchar(400) NULL;
	END
ELSE
	BEGIN
		-- La columna existe, actualizarla si es necesario
		ALTER TABLE [aud].[BitacoraCartera_aud]
		ALTER COLUMN bcaComentarios varchar(400) NULL;
	END
