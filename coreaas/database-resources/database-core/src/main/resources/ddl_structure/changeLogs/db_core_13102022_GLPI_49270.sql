--liquibase formatted sql
--changeset eprocess:01
--comment: Crea las estructuras requeridas para el glpi 49270

-------------------------------------------- Creacion tabla proveedor------------------------------------------
IF (NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'proveedor'))
BEGIN
  CREATE TABLE [dbo].[proveedor](
	[provId] [bigint] IDENTITY(1,1) NOT NULL,
	[provPersona] [bigint] NOT NULL,
	[provEmpresa] [bigint] NULL,
	[provEstado] [varchar](30) NULL,
	[provCuentaBancaria] [bit] NULL,
	[provBanco] [bigint] NULL,
	[provTipoCuenta] [varchar](30) NULL,
	[provNumeroCuenta] [varchar](30) NULL,
	[provTipoIdentificacionTitular] [varchar](20) NULL,
	[provNumeroIdentificacionTitular] [varchar](16) NULL,
	[provDigitoVerificacionTitular] [smallint] NULL,
	[provNombreTitularCuenta] [varchar](200) NULL,
	[provConcepto] [int] NULL,
 CONSTRAINT [PK_proveedor_provId] PRIMARY KEY CLUSTERED 
(
	[provId] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END;


if not exists (select * from sys.foreign_keys where object_id = OBJECT_ID(N'FK_proveedor_provBanco') and parent_object_id = OBJECT_ID(N'dbo.proveedor'))
begin
	ALTER TABLE [dbo].[proveedor]  WITH CHECK ADD  CONSTRAINT [FK_proveedor_provBanco] FOREIGN KEY([provBanco])
	REFERENCES [dbo].[Banco] ([banId])

	ALTER TABLE [dbo].[proveedor] CHECK CONSTRAINT [FK_proveedor_provBanco]

end

if not exists (select * from sys.foreign_keys where object_id = OBJECT_ID(N'FK_proveedor_provEmpresa') and parent_object_id = OBJECT_ID(N'dbo.proveedor'))
begin

	ALTER TABLE [dbo].[proveedor]  WITH CHECK ADD  CONSTRAINT [FK_proveedor_provEmpresa] FOREIGN KEY([provEmpresa])
	REFERENCES [dbo].[Empresa] ([empId])
	
	ALTER TABLE [dbo].[proveedor] CHECK CONSTRAINT [FK_proveedor_provEmpresa]
end

if not exists (select * from sys.foreign_keys where object_id = OBJECT_ID(N'FK_proveedor_provPersona') and parent_object_id = OBJECT_ID(N'dbo.proveedor'))
begin
	ALTER TABLE [dbo].[proveedor]  WITH CHECK ADD  CONSTRAINT [FK_proveedor_provPersona] FOREIGN KEY([provPersona])
	REFERENCES [dbo].[Persona] ([perId])
	
	ALTER TABLE [dbo].[proveedor] CHECK CONSTRAINT FK_proveedor_provPersona
end

if (OBJECT_ID(N'CK_proveedor_provEstado','F') is not null)
begin
	ALTER TABLE [dbo].[proveedor]  WITH CHECK ADD  CONSTRAINT [CK_proveedor_provEstado] CHECK  (([provEstado]='INACTIVO' OR [provEstado]='ACTIVO'))
	ALTER TABLE [dbo].[proveedor] CHECK CONSTRAINT [CK_proveedor_provEstado]

end

if (OBJECT_ID(N'CK_proveedor_provTipoCuenta','F') is not null)
begin
	ALTER TABLE [dbo].[proveedor]  WITH CHECK ADD  CONSTRAINT [CK_proveedor_provTipoCuenta] CHECK  (([provTipoCuenta]='CORRIENTE' OR [provTipoCuenta]='AHORROS' OR [provTipoCuenta]='DAVIPLATA'))
	ALTER TABLE [dbo].[proveedor] CHECK CONSTRAINT [CK_proveedor_provTipoCuenta]
end

if (OBJECT_ID(N'CK_proveedor_provTipoIdentificacionTitular','F') is not null)
begin
	ALTER TABLE [dbo].[proveedor]  WITH CHECK ADD  CONSTRAINT [CK_proveedor_provTipoIdentificacionTitular] CHECK  (([provTipoIdentificacionTitular]='PERM_ESP_PERMANENCIA' OR [provTipoIdentificacionTitular]='SALVOCONDUCTO' OR [provTipoIdentificacionTitular]='NIT' OR [provTipoIdentificacionTitular]='CARNE_DIPLOMATICO' OR [provTipoIdentificacionTitular]='PASAPORTE' OR [provTipoIdentificacionTitular]='CEDULA_EXTRANJERIA' OR [provTipoIdentificacionTitular]='CEDULA_CIUDADANIA' OR [provTipoIdentificacionTitular]='TARJETA_IDENTIDAD' OR [provTipoIdentificacionTitular]='REGISTRO_CIVIL'))
	ALTER TABLE [dbo].[proveedor] CHECK CONSTRAINT [CK_proveedor_provTipoIdentificacionTitular]
end
------------------------- fin creation table proveedor -----

--- creacion table conceptos ---

IF (NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'conceptoProv'))
BEGIN
  CREATE TABLE conceptoProv (
    ID int NOT NULL PRIMARY KEY,
    nombre varchar(255) NOT NULL, 
	estado varchar(100) NULL
	)
END;

------------------------------------------------------- fin insertion records table conceptos --------------------------------------------------------------------

----------------------------- creacion table DocumentoSoporteProveedor-------------------------------------------------------------------

IF (NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'DocumentoSoporteProveedor'))
BEGIN
	CREATE TABLE [dbo].[DocumentoSoporteProveedor](
	[dspId] [bigint] IDENTITY(1,1) NOT NULL,
	[dspProveedor] [bigint] NOT NULL,
	[dspDocumentoSoporte] [bigint] NOT NULL,
	 CONSTRAINT [PK_DocumentoSoporteProveedor_dspId] PRIMARY KEY CLUSTERED 
	(
		[dspId] ASC
	)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
	) ON [PRIMARY]
END;

if not exists (select * from sys.foreign_keys where object_id = OBJECT_ID(N'FK_DocumentoSoporteProveedor_dspDocumentoSoporte') and parent_object_id = OBJECT_ID(N'dbo.DocumentoSoporteProveedor'))
begin
	ALTER TABLE [dbo].[DocumentoSoporteProveedor]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoSoporteProveedor_dspDocumentoSoporte] FOREIGN KEY([dspDocumentoSoporte])
	REFERENCES [dbo].[DocumentoSoporte] ([dosId])

	ALTER TABLE [dbo].[DocumentoSoporteProveedor] CHECK CONSTRAINT [FK_DocumentoSoporteProveedor_dspDocumentoSoporte]

end

if not exists (select * from sys.foreign_keys where object_id = OBJECT_ID(N'FK_DocumentoSoporteProveedor_dspProveedor') and parent_object_id = OBJECT_ID(N'dbo.DocumentoSoporteProveedor'))
begin
ALTER TABLE [dbo].[DocumentoSoporteProveedor]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoSoporteProveedor_dspProveedor] FOREIGN KEY([dspProveedor])
REFERENCES [dbo].[Proveedor] ([provId])

ALTER TABLE [dbo].[DocumentoSoporteProveedor] CHECK CONSTRAINT [FK_DocumentoSoporteProveedor_dspProveedor]
end
----------------------- fin creacion table DocumentoSoporteProveedor --------------------------------------

----------------------- Email Secundario -------------------------------------
if not exists (select * from sys.columns where name = 'ubiEmailSecundario' and object_id = object_id('ubicacion')) alter table ubicacion add ubiEmailSecundario varchar(100);
if not exists (select * from sys.columns where name = 'ubiEmailSecundario' and object_id = object_id('aud.ubicacion_aud')) alter table aud.ubicacion_aud add ubiEmailSecundario varchar(100);