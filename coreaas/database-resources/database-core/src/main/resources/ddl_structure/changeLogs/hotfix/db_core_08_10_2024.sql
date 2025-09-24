CREATE TABLE [dbo].[ArchivosTrasladosEmpresasCCF](
	[ateId] [bigint] IDENTITY(1,1) PRIMARY KEY,
	[ateIdCargue] [bigint] NULL,
	[ateIdEmpleador] [bigint] NULL,
	[ateDatosTemporales] [nvarchar](max) NULL
	)

ALTER TABLE [dbo].[ArchivosTrasladosEmpresasCCF]  ADD FOREIGN KEY([ateIdCargue])
REFERENCES [dbo].[ConsolaEstadoCargueMasivo] ([cecId])

ALTER TABLE [dbo].[ArchivosTrasladosEmpresasCCF]  ADD FOREIGN KEY([ateIdEmpleador])
REFERENCES [dbo].[Empleador] ([empId])