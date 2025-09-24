IF NOT EXISTS (select * from INFORMATION_SCHEMA.TABLES T WHERE TABLE_NAME = 'RegistroConfirmacionAbonos')
BEGIN
CREATE TABLE [dbo].[RegistroConfirmacionAbonos](
	[rcaId] [bigint] IDENTITY(1,1) NOT NULL,
	[rcaCuentaAdministradorSubsidio] [bigint] NULL,
	[rcaTipoCuentaAdminSubsidio] [varchar](16) NULL,
	[rcaNumeroIdentificacion] [varchar](16) NULL,
	[rcaTipoIdentificacion] [varchar](20) NULL,
	[rcaNombreArchivo] [varchar](200) NOT NULL,
	[rcaNumeroLinea] [bigint] NULL,
	[rcaNumeroCuentaAdmon] [varchar](30) NULL,
	[rcaValorTransferencia] [varchar](32) NULL,
	[rcaResultadoAbono] [varchar](100) NULL,
	[rcaMotivoRechazoAbono] [varchar](300) NULL,
	[rcaFechaConfirmacionAbono] [varchar](20) NULL,
	[rcaErrorValidacion3] [bit] NULL,
	[rcaErrorValidacion4] [bit] NULL,
	[rcaErrorValidacion5] [bit] NULL,
	[rcaErrorValidacion6] [bit] NULL,
	[rcaErrorValidacion7] [bit] NULL,
	[rcaErrorValidacion8] [bit] NULL,
	[rcaErrorValidacion9] [bit] NULL,
	[rcaErrorValidacion10] [bit] NULL
	PRIMARY KEY CLUSTERED 
	(
		[rcaId] ASC
	)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
	) ON [PRIMARY]

	IF EXISTS
	(
		SELECT * FROM sys.triggers
		WHERE name = N'TRG_AF_INS_RegistroConfirmacionAbonos' AND parent_class_desc = N'OBJECT_OR_COLUMN'
	) DROP TRIGGER [dbo].[TRG_AF_INS_RegistroConfirmacionAbonos]

END