----Actualizacion formato clave fechaCorte,fechaGeneracion y fechaRadicacionSolicitud en los comunicados---------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave IN ('${fechaCorte}', '${fechaGeneracion}', '${fechaRadicacionSolicitud}');