--liquibase formatted sql

--changeset ggiraldo:01

CREATE TABLE [dbo].[ParametrizacionReportesNormativos](
	[prnId] [int] PRIMARY KEY IDENTITY(1,1),
	[prnNumero] [int] NULL,
	[prnNombre] [varchar](255) NULL,
	[prnNormaAsociada] [varchar](255) NULL,
	[prnEntidad] [varchar](255) NULL,
	[prnNFrecuencia] [varchar](50) NULL,
	[prnModulo] [varchar](100) NULL,
	[prnFormatos] [varchar](100) NULL
) ON [PRIMARY];

INSERT INTO [dbo].[ParametrizacionReportesNormativos]
        ([prnNumero], [prnNombre], [prnNormaAsociada], [prnEntidad], [prnNFrecuencia], [prnModulo], [prnFormatos])
	VALUES
		(12, 'Reporte Maestro de Afiliados', 'Resolucion 1056 de 2015', 'Ministerio de salud y protección social',
			'Semanal', 'Afiliación', 'txt,xlsx'),
		(14, 'Novedades de Afiliados y Subsidios', 'Resolucion 1056 de 2015', 'Ministerio de Salud',
			'Semanal', 'Trasversal', 'txt,xlsx'),
		(15, 'Novedades de Estado de la Afiliacion y al dia del aportante', 'Resolucion 1056 de 2015',
			'Ministerio de Salud', 'Semanal', 'Aportes', 'txt,xlsx'),
		(5, 'Asignación, Pago Y Reintegro De Subsidios De Viviendas – FOVIS', 'Circular 0007 del 2019',
			'Superintendencia del Subsidio Familiar', 'Mensual', 'FOVIS', 'xml,txt,xlsx'),
		(16, 'Asignacion, pago y reintegro de Subsidios de vivienda FOVIS microdato',
			'Circular 0007 del 2019', 'Superintendencia de Subsidio Familiar', 'Mensual', 'FOVIS', 'txt,xlsx'),
		(17, 'Reporte de Asignados', '', 'Superintendencia de Subsidio Familiar', 'Mensual', 'FOVIS', 'txt,xlsx'),
		(18, 'Devoluciones UGPP', 'Circular 007 del 2019', 'UGPP',
			'Mensual', 'Aportes', 'txt,xlsx'),
		(19, 'Pagos Fuera de Pila UGPP', 'Circular 0007 del 2019', 'UGPP',
			'Mensual', 'Aportes', 'txt,xlsx'),
		(23, 'Cuota Monetaria - Numero de Personas a Cargo', 'Circular 0007 del 2019',
			'Superintendencia de Subsidio Familiar', 'Mensual', 'Subsidio', 'txt,xlsx'),
		(24, 'Cuota Monetaria - Numero total ', 'Circular 0007 del 2019',
			'Superintendencia de Subsidio Familiar', 'Mensual', 'Subsidio', 'txt,xlsx'),
		(11, 'Registro Unico de Empleadores', 'Resolucion 0074',
			'Ministerio de Trabajo', 'Semestral', 'Afiliacion', 'txt,xlsx'),
		(20, 'Reporte Semestral de trabajadores sector Agropecuario', '',
			'Ministerio de Trabajo', 'Semestral', 'Aportes', 'txt,xlsx'),
		(6, 'Postulaciones Y Asignaciones – FOVIS', 'Circular 0007 del 2019',
			'Superintendencia del Subsidio Familiar', 'Anual', 'FOVIS', 'txt,xlsx,xml'),
		(7, 'Reporte de Ubicación y Contacto', 'Resolución 2082',
			'UGPP', 'Anual', 'Afiliación', 'txt,xlsx,csv'),
		(28, 'Consolidado histórico asignaciones, pagos y reingresos', 'Circular 0007 del 2019',
			'Superintendencia del Subsidio Familiar', 'Anual', 'FOVIS', 'txt,xlsx,xml'),
		(29, 'CONSOLIDADO HISTORICO ASIGNACIONES, PAGOS Y REINTEGROS (ANUAL)(Microdato) Circular 0007 del 2019', 'Resolución 1056 de 2015',
			'Superintendencia del Subsidio Familiar', 'Anual', 'FOVIS', 'txt,xlsx,xml'),
		(1, 'Reporte Empresas y aportantes', 'Circular 0007 del 2019', 'Superintendencia del Subsidio Familiar', 'Mensual', 'Aportes', 'txt,xlsx,xml'),
		(2, 'Afiliados', 'Circular 0007 del 2019', 'Superintendencia del Subsidio Familiar', 'Mensual', 'Afiliación', 'txt,xlsx,xml'),
		(3, 'Afiliados a cargo', 'Circular 0007 del 2019', 'Superintendencia del Subsidio Familiar', 'Mensual', 'Subsidio', 'txt,xlsx,xml'),
		(4, 'Empresas en Mora', 'Circular 0007 del 2019', 'Superintendencia del Subsidio Familiar', 'Mensual', '', 'txt,xlsx,xml'),
		(8, 'Reporte consolidado de cartera', 'Resolución 2082', 'UGPP', 'Mensual', 'Cartera', 'txt,xlsx'),
		(9, 'Reporte desagregado de cartera', 'Resolución 2082', 'UGPP', 'Trimestral', 'Cartera', 'txt,xlsx'),
		(10, 'Aviso de incumplimiento', 'Resolución 2082', 'UGPP', 'Mensual', 'Cartera', 'txt,xlsx'),
		(13, 'Archivo Maestro de Subsidios', 'Resolución 1056 de 2015',
			'Ministerio de salud y protección social', 'Semanal', 'Subsidio', 'txt,xlsx'),
		(20, 'Reporte Trabajadores Sector Agropecuario', '', 'Ministerio de Trabajo', 'Semestral', '', 'txt,xlsx'),
		(21, 'Reporte de Inconsistencias', 'Decreto 3033', 'UGPP', 'Mensual', 'Cartera', 'txt,xlsx'),
		(22, 'Empleadores Morosos', '', 'Superintendencia del Subsidio Familiar', 'Trimestral', 'Cartera', 'txt,xlsx,csv'),
		(27, 'Verificación y / confirmación pagos y/o novedades administradora', '', '', '', '', '');

CREATE TABLE [dbo].[historico_reportes_normativos](
	[hrnId] [varchar](100) NULL,
	[hrnParamReportesNormativosId] [int] NULL,
	[hrnPilaCaja] [varchar](100) NULL,
	[hrnPeriodo] [date] NULL,
	[hrnRutaDocumento] [varchar](100) NULL,
	[hrnGeneradoPor] [varchar](100) NULL,
	[hrnFechaGeneracion] [datetime] NULL,
	[hrnVersionXlsx] [varchar](100) NULL,
	[hrnIdentificadorDocumentoXlsx] [varchar](100) NULL,
	[hrnVersionTxt] [varchar](100) NULL,
	[hrnIdentificadorDocumentoTxt] [varchar](100) NULL,
	[hrnVersionXml] [varchar](100) NULL,
	[hrnIdentificadorDocumentoXml] [varchar](100) NULL,
	[hrnVersionCsv] [varchar](100) NULL,
	[hrnIdentificadorDocumentoCsv] [varchar](100) NULL
) ON [PRIMARY];

CREATE TABLE [dbo].[SolicitudesReportesNormativos](
	[srId] [int] IDENTITY(1,1) PRIMARY KEY,
	[srParamReportesNormativosId] [int] FOREIGN KEY REFERENCES ParametrizacionReportesNormativos(prnId),
	[srEstado] [varchar](255) NULL,
	[srDetalleEstado] [varchar](300) NULL,
	[srNombreArchivo] [varchar](255) NULL,
	[srFechaInicio] [datetime] NULL,
	[srFechaFin] [datetime] NULL,
	[srFormato] [varchar](100) NULL,
	[srOficial] [varchar](100) NULL,
	[srFechaGeneracion] [datetime] NULL,
	[srSolicitadoPor] [varchar](255) NULL,
	[srVersionDocumento] [varchar](255) NULL,
	[srIdentificacionDocumento] [varchar](255) NULL
) ON [PRIMARY];
