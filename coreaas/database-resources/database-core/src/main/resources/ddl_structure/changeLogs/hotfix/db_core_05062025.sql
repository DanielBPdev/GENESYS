IF NOT EXISTS (
    SELECT 1 FROM [staging].[StagingTiposCotizantes] WHERE tipoCot = 70
)
BEGIN
    INSERT INTO [staging].[StagingTiposCotizantes] 
    (tipo, tipoCot, descri, tipotra, otro)
    VALUES 
    (
        'TIPO_COTIZANTE_PROMOTOR_DEL_SERVICIO_PARA_LA_PAZ',
        70,
        'Promotor del Servicio Social para la Paz',
        'TRABAJADOR_INDEPENDIENTE',
        'INDEPENDIENTE_REGULAR'
    );
END;

IF NOT EXISTS (
    SELECT 1 FROM [staging].[StagingTiposCotizantes] WHERE tipoCot = 71
)
BEGIN
    INSERT INTO [staging].[StagingTiposCotizantes] 
    (tipo, tipoCot, descri, tipotra, otro)
    VALUES 
    (
        'TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES',
        71,
        'Ley de Segundas Oportunidades',
        'TRABAJADOR_INDEPENDIENTE',
        'INDEPENDIENTE_REGULAR'
    );
END;

IF NOT EXISTS (
    SELECT 1 FROM [staging].[StagingTiposCotizantes] WHERE tipoCot = 72
)
BEGIN
    INSERT INTO [staging].[StagingTiposCotizantes] 
    (tipo, tipoCot, descri, tipotra, otro)
    VALUES 
    (
        'TIPO_COTIZANTE_MUJER_CON_APORTE',
        72,
        'Mujeres con aporte a pensión por pago por tercero',
        'PENSIONADO',
        'REGULAR'
    );
END;
