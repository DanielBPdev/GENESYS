--Clave ${cargogerentecomercial} para el comunicado CARTA DE ACEPTACIÓN DEL EMPLEADOR - CRT_ACP_EMP
IF NOT EXISTS (
    SELECT 1
    FROM VariableComunicado vco
    INNER JOIN PlantillaComunicado pco ON vco.vcoPlantillaComunicado = pco.pcoId
    WHERE vco.vcoClave = '${cargogerentecomercial}'
      AND pco.pcoEtiqueta = 'CRT_ACP_EMP'
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
        '${cargogerentecomercial}',
        'Corresponde al cargo que tiene el gerente comercial de el proceso de afiliación empresa',
        'Cargo Gerente Comercial',
        pco.pcoId,
        'CARGO_GERENTE_COMERCIAL',
        'CONSTANTE',
        '0'
    FROM PlantillaComunicado pco
    WHERE pco.pcoEtiqueta = 'CRT_ACP_EMP';
END;

--Clave ${firmagerentecomercial} para el comunicado CARTA DE ACEPTACIÓN DEL EMPLEADOR - CRT_ACP_EMP
IF NOT EXISTS (
    SELECT 1
    FROM VariableComunicado vco
    INNER JOIN PlantillaComunicado pco ON vco.vcoPlantillaComunicado = pco.pcoId
    WHERE vco.vcoClave = '${firmagerentecomercial}'
      AND pco.pcoEtiqueta = 'CRT_ACP_EMP'
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
        '${firmagerentecomercial}',
        'Firma de gerente comercial',
        'Firma Gerente Comercial',
        pco.pcoId,
        'FIRMA_GERENTE_COMERCIAL',
        'CONSTANTE',
        '0'
    FROM PlantillaComunicado pco
    WHERE pco.pcoEtiqueta = 'CRT_ACP_EMP';
END;

--Clave ${gerentecomercial} para el comunicado CARTA DE ACEPTACIÓN DEL EMPLEADOR - CRT_ACP_EMP
IF NOT EXISTS (
    SELECT 1
    FROM VariableComunicado vco
    INNER JOIN PlantillaComunicado pco ON vco.vcoPlantillaComunicado = pco.pcoId
    WHERE vco.vcoClave = '${gerentecomercial}'
      AND pco.pcoEtiqueta = 'CRT_ACP_EMP'
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
        '${gerentecomercial}',
        'Gerente Comercial',
        'Gerente Comercial',
        pco.pcoId,
        'GERENTE_COMERCIAL',
        'CONSTANTE',
        '0'
    FROM PlantillaComunicado pco
    WHERE pco.pcoEtiqueta = 'CRT_ACP_EMP';
END;