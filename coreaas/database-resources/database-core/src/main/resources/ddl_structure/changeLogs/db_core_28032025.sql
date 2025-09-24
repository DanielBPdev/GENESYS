-- FIRMA_DIRECTOR_ADMINISTRATIVO_PRINCIPAL
INSERT INTO [dbo].[VariableComunicado] (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
SELECT TOP 1
    '${FirmaDirectorAdminppalCCF}',
    'Imagen de la firma del Director Administrativo principal CCF del envío del comunicado en la caja',
    'Firma Director Administrativo principal CCF',
    4,
    'FIRMA_DIRECTOR_ADMINISTRATIVO_PRINCIPAL',
    'CONSTANTE',
    0
FROM PlantillaComunicado pc
WHERE pc.pcoId = 4 
AND NOT EXISTS (
    SELECT 1 FROM [dbo].[VariableComunicado]
    WHERE vcoClave = '${FirmaDirectorAdminppalCCF}' 
    AND vcoPlantillaComunicado = 4
);

-- FIRMA_DIRECTOR_ADMINISTRATIVO_SEGUNDO_SUPLENTE
INSERT INTO [dbo].[VariableComunicado] (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
SELECT TOP 1
    '${FirmaDirectorAdminsplCCF}',
    'Imagen de la firma del Director Administrativo suplente CCF del envío del comunicado en la caja',
    'Firma Director Administrativo suplente CCF',
    4,
    'FIRMA_DIRECTOR_ADMINISTRATIVO_SEGUNDO_SUPLENTE',
    'CONSTANTE',
    0
FROM PlantillaComunicado pc
WHERE pc.pcoId = 4
AND NOT EXISTS (
    SELECT 1 FROM [dbo].[VariableComunicado]
    WHERE vcoClave = '${FirmaDirectorAdminsplCCF}' 
    AND vcoPlantillaComunicado = 4
);



--QUERIES PARA LA TABLA Parametro--
--FIRMA_DIRECTOR_ADMINISTRATIVO_PRINCIPAL--
IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('FIRMA_DIRECTOR_ADMINISTRATIVO_PRINCIPAL'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato]
           ,[prmVisualizarPantalla])
     VALUES
           ('FIRMA_DIRECTOR_ADMINISTRATIVO_PRINCIPAL',
           ''
           ,0
           ,'CAJA_COMPENSACION'
           ,'Imagen de la firma del Director Administrativo principal CCF del envío del comunicado en la caja'
           ,'FILE', 0)

--FIRMA_DIRECTOR_ADMINISTRATIVO_SEGUNDO_SUPLENTE--
IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('FIRMA_DIRECTOR_ADMINISTRATIVO_SEGUNDO_SUPLENTE'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato]
           ,[prmVisualizarPantalla])
     VALUES
           ('FIRMA_DIRECTOR_ADMINISTRATIVO_SEGUNDO_SUPLENTE',
           ''
           ,0
           ,'CAJA_COMPENSACION'
           ,'Imagen de la firma del Director Administrativo suplente CCF del envío del comunicado en la caja'
           ,'FILE', 0)