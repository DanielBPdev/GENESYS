IF NOT EXISTS (
    SELECT 1
    FROM VariableComunicado vco
    INNER JOIN PlantillaComunicado pco ON vco.vcoPlantillaComunicado = pco.pcoId
    WHERE vco.vcoClave IN (
        '${cotizantesNoOK}'
    )
    AND pco.pcoEtiqueta = 'NTF_REC_APT_PLA_DEP3'
)
BEGIN
    INSERT INTO VariableComunicado (
        vcoClave,
        vcoDescripcion,
        vcoNombre,
        vcoPlantillaComunicado,
        vcoNombreConstante,
        vcoTipoVariableComunicado,
        vcoOrden
    )
    SELECT 
        '${cotizantesNoOK}',
        'Tipo, numero, nombre, incidencias del cotizante como lista',
        'Tipo, numero, nombre, incidencias del cotizante como lista',
        pco.pcoId,
        '',
        'VARIABLE',
        ''
    FROM PlantillaComunicado pco
    WHERE pco.pcoEtiqueta = 'NTF_REC_APT_PLA_DEP3'

END;