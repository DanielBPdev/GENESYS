----Actualizacion formato clave fechaDelSistema en todos los comunicados---------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}';