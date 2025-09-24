--liquibase formatted sql

--changeset mperilla:04

INSERT INTO [dbo].[ParametrizacionReportesNormativos]
        ([prnNumero], [prnNombre], [prnNormaAsociada], [prnEntidad], [prnNFrecuencia], [prnModulo], [prnFormatos])
	VALUES
		(172, 'Reporte Afiliados y Beneficiarios - Asignados', '', '',
			'Mensual', 'Afiliaci�n', 'txt,xlsx'),
		(173, 'Reporte Afiliados y Beneficiarios - Perdida de Vivienda', '', '',
			'Mensual', 'Afiliaci�n', 'txt,xlsx');


UPDATE dbo.ParametrizacionReportesNormativos
SET
	prnNombre = 'Reporte Afiliados y Beneficiarios - Afiliados'
WHERE 
	prnNumero = 17;
