----Correccion formato clave para la clave ${ciudadSolicitud}---------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'CONSTANTE'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave IN ('${ciudadSolicitud}');