----Actualizacion formato clave para los ciudades---------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'LUGAR_MAYUS'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave IN ('${Ciudad}', '${ciudadSolicitud}', '${ciudadSede}', '${ciudadRepresentanteLegal}');