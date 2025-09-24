------------Cargo Gerente Financiera-----------------
UPDATE [dbo].[VariableComunicado]
SET
    vcoDescripcion = 'Corresponde al cargo que tiene la gerente financiera de fovis',
    vcoNombre = 'Cargo Gerente Financiera',
    vcoNombreConstante = 'CARGO_GERENTE_FINANCIERA',
    vcoTipoVariableComunicado = 'CONSTANTE',
    vcoOrden = ''
WHERE
    vcoClave = '${cargogerentefinanciera}'
AND EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${cargogerentefinanciera}');

INSERT INTO [dbo].[VariableComunicado] (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
SELECT
    '${cargogerentefinanciera}',
    'Corresponde al cargo que tiene la gerente financiera de fovis',
    'Cargo Gerente Financiera',
    pc.pcoId,
    'CARGO_GERENTE_FINANCIERA',
    'CONSTANTE',
    ''
FROM
    PlantillaComunicado pc
WHERE
    pc.pcoId NOT IN (SELECT vcoPlantillaComunicado FROM [dbo].[VariableComunicado] WHERE vcoClave = '${cargogerentefinanciera}')
AND NOT EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${cargogerentefinanciera}' AND vcoPlantillaComunicado = pc.pcoId);
------------Firma Gerente Financiera-----------------
UPDATE [dbo].[VariableComunicado]
SET
    vcoDescripcion = 'Firma del gerente financiero',
    vcoNombre = 'Firma Gerente Financiera',
    vcoNombreConstante = 'FIRMA_GERENTE_FINANCIERA',
    vcoTipoVariableComunicado = 'CONSTANTE',
    vcoOrden = ''
WHERE
    vcoClave = '${firmagerentefinanciera}'
AND EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${firmagerentefinanciera}');

INSERT INTO [dbo].[VariableComunicado] (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
SELECT
    '${firmagerentefinanciera}',
    'Firma del gerente financiero',
    'Firma Gerente Financiera',
    pc.pcoId,
    'FIRMA_GERENTE_FINANCIERA',
    'CONSTANTE',
    ''
FROM
    PlantillaComunicado pc
WHERE
    pc.pcoId NOT IN (SELECT vcoPlantillaComunicado FROM [dbo].[VariableComunicado] WHERE vcoClave = '${firmagerentefinanciera}')
AND NOT EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${firmagerentefinanciera}' AND vcoPlantillaComunicado = pc.pcoId);
------------Gerente Financiera-----------------------
UPDATE [dbo].[VariableComunicado]
SET
    vcoDescripcion = 'Gerente financiero',
    vcoNombre = 'Gerente Financiera',
    vcoNombreConstante = 'GERENTE_FINANCIERA',
    vcoTipoVariableComunicado = 'CONSTANTE',
    vcoOrden = ''
WHERE
    vcoClave = '${gerentefinanciera}'
AND EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${gerentefinanciera}');

INSERT INTO [dbo].[VariableComunicado] (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
SELECT
    '${gerentefinanciera}',
    'Gerente financiero',
    'Gerente Financiera',
    pc.pcoId,
    'GERENTE_FINANCIERA',
    'CONSTANTE',
    ''
FROM
    PlantillaComunicado pc
WHERE
    pc.pcoId NOT IN (SELECT vcoPlantillaComunicado FROM [dbo].[VariableComunicado] WHERE vcoClave = '${gerentefinanciera}')
AND NOT EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${gerentefinanciera}' AND vcoPlantillaComunicado = pc.pcoId);

------------Cargo Gerente Comercial-----------------
UPDATE [dbo].[VariableComunicado]
SET
    vcoDescripcion = 'Corresponde al cargo que tiene el gerente comercial de el proceso de afiliación empresa',
    vcoNombre = 'Cargo Gerente Comercial',
    vcoNombreConstante = 'CARGO_GERENTE_COMERCIAL',
    vcoTipoVariableComunicado = 'CONSTANTE',
    vcoOrden = ''
WHERE
    vcoClave = '${cargogerentecomercial}'
AND EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${cargogerentecomercial}');

INSERT INTO [dbo].[VariableComunicado] (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
SELECT
    '${cargogerentecomercial}',
    'Corresponde al cargo que tiene el gerente comercial de el proceso de afiliación empresa',
    'Cargo Gerente Comercial',
    pc.pcoId,
    'CARGO_GERENTE_COMERCIAL',
    'CONSTANTE',
    ''
FROM
    PlantillaComunicado pc
WHERE
    pc.pcoId NOT IN (SELECT vcoPlantillaComunicado FROM [dbo].[VariableComunicado] WHERE vcoClave = '${cargogerentecomercial}')
AND NOT EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${cargogerentecomercial}' AND vcoPlantillaComunicado = pc.pcoId);
------------Firma Gerente Comercial-----------------
UPDATE [dbo].[VariableComunicado]
SET
    vcoDescripcion = 'Firma de gerente comercial',
    vcoNombre = 'Firma Gerente Comercial',
    vcoNombreConstante = 'FIRMA_GERENTE_COMERCIAL',
    vcoTipoVariableComunicado = 'CONSTANTE',
    vcoOrden = ''
WHERE
    vcoClave = '${firmagerentecomercial}'
AND EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${firmagerentecomercial}');

INSERT INTO [dbo].[VariableComunicado] (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
SELECT
    '${firmagerentecomercial}',
    'Firma de gerente comercial',
    'Firma Gerente Comercial',
    pc.pcoId,
    'FIRMA_GERENTE_COMERCIAL',
    'CONSTANTE',
    ''
FROM
    PlantillaComunicado pc
WHERE
    pc.pcoId NOT IN (SELECT vcoPlantillaComunicado FROM [dbo].[VariableComunicado] WHERE vcoClave = '${firmagerentecomercial}')
AND NOT EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${firmagerentecomercial}' AND vcoPlantillaComunicado = pc.pcoId);
------------Gerente Comercial-----------------------
UPDATE [dbo].[VariableComunicado]
SET
    vcoDescripcion = 'Gerente Comercial',
    vcoNombre = 'Gerente Comercial',
    vcoNombreConstante = 'GERENTE_COMERCIAL',
    vcoTipoVariableComunicado = 'CONSTANTE',
    vcoOrden = ''
WHERE
    vcoClave = '${gerentecomercial}'
AND EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${gerentecomercial}');

INSERT INTO [dbo].[VariableComunicado] (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
SELECT
    '${gerentecomercial}',
    'Gerente Comercial',
    'Gerente Comercial',
    pc.pcoId,
    'GERENTE_COMERCIAL',
    'CONSTANTE',
    ''
FROM
    PlantillaComunicado pc
WHERE
    pc.pcoId NOT IN (SELECT vcoPlantillaComunicado FROM [dbo].[VariableComunicado] WHERE vcoClave = '${gerentecomercial}')
AND NOT EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${gerentecomercial}' AND vcoPlantillaComunicado = pc.pcoId);

------------Cargo Secretaria General-----------------
UPDATE [dbo].[VariableComunicado]
SET
    vcoDescripcion = 'Corresponde al cargo que tiene la secretaria general en cartera',
    vcoNombre = 'Cargo Secretaria General',
    vcoNombreConstante = 'CARGO_SECRETARIA_GENERAL',
    vcoTipoVariableComunicado = 'CONSTANTE',
    vcoOrden = ''
WHERE
    vcoClave = '${cargosecretariageneral}'
AND EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${cargosecretariageneral}');

INSERT INTO [dbo].[VariableComunicado] (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
SELECT
    '${cargosecretariageneral}',
    'Corresponde al cargo que tiene la secretaria general en cartera',
    'Cargo Secretaria General',
    pc.pcoId,
    'CARGO_SECRETARIA_GENERAL',
    'CONSTANTE',
    ''
FROM
    PlantillaComunicado pc
WHERE
    pc.pcoId NOT IN (SELECT vcoPlantillaComunicado FROM [dbo].[VariableComunicado] WHERE vcoClave = '${cargosecretariageneral}')
AND NOT EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${cargosecretariageneral}' AND vcoPlantillaComunicado = pc.pcoId);
------------Firma Secretaria General-----------------
UPDATE [dbo].[VariableComunicado]
SET
    vcoDescripcion = 'Firma secretaria general',
    vcoNombre = 'Firma Secretaria General',
    vcoNombreConstante = 'FIRMA_SECRETARIA_GENERAL',
    vcoTipoVariableComunicado = 'CONSTANTE',
    vcoOrden = ''
WHERE
    vcoClave = '${firmasecretariageneral}'
AND EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${firmasecretariageneral}');

INSERT INTO [dbo].[VariableComunicado] (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
SELECT
    '${firmasecretariageneral}',
    'Firma secretaria general',
    'Firma Secretaria General',
    pc.pcoId,
    'FIRMA_SECRETARIA_GENERAL',
    'CONSTANTE',
    ''
FROM
    PlantillaComunicado pc
WHERE
    pc.pcoId NOT IN (SELECT vcoPlantillaComunicado FROM [dbo].[VariableComunicado] WHERE vcoClave = '${firmasecretariageneral}')
AND NOT EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${firmasecretariageneral}' AND vcoPlantillaComunicado = pc.pcoId);
------------Secretaria General-----------------
UPDATE [dbo].[VariableComunicado]
SET
    vcoDescripcion = 'Secretaria General',
    vcoNombre = 'Secretaria General',
    vcoNombreConstante = 'SECRETARIA_GENERAL',
    vcoTipoVariableComunicado = 'CONSTANTE',
    vcoOrden = ''
WHERE
    vcoClave = '${secretariageneral}'
AND EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${secretariageneral}');

INSERT INTO [dbo].[VariableComunicado] (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
SELECT
    '${secretariageneral}',
    'Secretaria General',
    'Secretaria General',
    pc.pcoId,
    'SECRETARIA_GENERAL',
    'CONSTANTE',
    ''
FROM
    PlantillaComunicado pc
WHERE
    pc.pcoId NOT IN (SELECT vcoPlantillaComunicado FROM [dbo].[VariableComunicado] WHERE vcoClave = '${secretariageneral}')
AND NOT EXISTS (SELECT 1 FROM [dbo].[VariableComunicado] WHERE vcoClave = '${secretariageneral}' AND vcoPlantillaComunicado = pc.pcoId);
