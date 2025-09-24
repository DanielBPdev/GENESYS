--changeset mperilla:10
--comment: Se agrega el nuevo reporte numero 36 Accion de Cobro

-- Accion de Cobro 
INSERT INTO [dbo].[ParametrizacionReportesNormativos]
    ([prnNumero], [prnNombre], [prnNormaAsociada], [prnEntidad], [prnNFrecuencia], [prnModulo], [prnFormatos])
VALUES
	(36, 'Accion de Cobro', 'Circular 1702 de 2021', 'UGPP', 'Mensual', 'Cartera', 'txt,xlsx');


