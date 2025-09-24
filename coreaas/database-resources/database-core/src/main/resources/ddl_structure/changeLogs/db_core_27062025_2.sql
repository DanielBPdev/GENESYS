--Clave ${numeroDeRadicacion} del comunicado NTF_RAD_NVD_PER
IF EXISTS (
    SELECT 1
    FROM VariableComunicado vco
    INNER JOIN PlantillaComunicado pco ON vco.vcoPlantillaComunicado = pco.pcoId
    WHERE vco.vcoClave = '${numeroDeRadicacion}'
      AND pco.pcoEtiqueta = 'NTF_RAD_NVD_PER'
)
BEGIN
    DELETE vco
    FROM VariableComunicado vco
    INNER JOIN PlantillaComunicado pco ON vco.vcoPlantillaComunicado = pco.pcoId
    WHERE vco.vcoClave = '${numeroDeRadicacion}'
      AND pco.pcoEtiqueta = 'NTF_RAD_NVD_PER';
END;

--Clave ${numeroDeRadicacion} del comunicado NTF_NVD_PERS
IF EXISTS (
    SELECT 1
    FROM VariableComunicado vco
    INNER JOIN PlantillaComunicado pco ON vco.vcoPlantillaComunicado = pco.pcoId
    WHERE vco.vcoClave = '${numeroDeRadicacion}'
      AND pco.pcoEtiqueta = 'NTF_NVD_PERS'
)
BEGIN
    DELETE vco
    FROM VariableComunicado vco
    INNER JOIN PlantillaComunicado pco ON vco.vcoPlantillaComunicado = pco.pcoId
    WHERE vco.vcoClave = '${numeroDeRadicacion}'
      AND pco.pcoEtiqueta = 'NTF_NVD_PERS';
END;