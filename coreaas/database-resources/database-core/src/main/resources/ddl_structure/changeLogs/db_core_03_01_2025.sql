IF NOT EXISTS (	
SELECT * FROM PlantillaComunicado p
INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_DEP' AND v.vcoClave = '${numeroIdentificacionEmpleador}'
)
BEGIN
INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
SELECT DISTINCT
'${numeroIdentificacionEmpleador}',
'Número de identificación del empleador que realizó la solicitud',
'Número identificación del empleador',
pcoId,
'',
'VARIABLE',
0
FROM PlantillaComunicado
WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'
END;