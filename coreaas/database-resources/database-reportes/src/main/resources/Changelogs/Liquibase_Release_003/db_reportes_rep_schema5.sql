--changeset ggiraldo:05
--comment: Adición de la parametrización de los reportes normativos 30A y 30B

INSERT INTO [dbo].[ParametrizacionReportesNormativos]
        ([prnNumero], [prnNombre], [prnNormaAsociada], [prnEntidad], [prnNFrecuencia], [prnModulo], [prnFormatos])
	VALUES
		(301, 'Asignados Concurrencia', 'Circular 0013 del 2019', 'Ministerio de Vivienda',
			'Semanal', 'FOVIS', 'txt,xlsx'),
		(302, 'Novedades Concurrencia', 'Circular 0013 del 2019', 'Ministerio de Vivienda',
			'Semanal', 'FOVIS', 'txt,xlsx');