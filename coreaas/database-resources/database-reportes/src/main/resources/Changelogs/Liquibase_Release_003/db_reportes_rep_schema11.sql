--changeset mperilla:11
--comment: Se agregan los reportes 18A y 20A 
-- Ajustes en la Informacion 
INSERT INTO ParametrizacionReportesNormativos (
	[prnNumero],
	[prnNombre],
	[prnNormaAsociada],
	[prnEntidad],
	[prnNFrecuencia],
	[prnModulo],
	[prnFormatos]
) VALUES ('181', 'Ajustes en la información', '', '', 'Mensual', '', 'txt, xlsx');

-- Detalle Trabajadores Sector Agropecuario
INSERT INTO ParametrizacionReportesNormativos (
	[prnNumero],
	[prnNombre],
	[prnNormaAsociada],
	[prnEntidad],
	[prnNFrecuencia],
	[prnModulo],
	[prnFormatos]
) VALUES ('201', 'Detalle Trabajadores Sector Agropecuario', '', '', 'Mensual', '', 'xlsx');