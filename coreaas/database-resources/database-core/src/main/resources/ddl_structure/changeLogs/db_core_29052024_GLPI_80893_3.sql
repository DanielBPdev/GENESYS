-- Verifica si la columna no existe
IF NOT EXISTS (
    SELECT *
    FROM sys.columns
    WHERE object_id = OBJECT_ID('aud.parametro_aud')
    AND name = 'prmVisualizarPantalla'
)
BEGIN
    -- Agrega la columna si no existe
    ALTER TABLE aud.parametro_aud
    ADD prmVisualizarPantalla BIT DEFAULT 1;

    PRINT 'Columna prmVisualizarPantalla agregada con valor por defecto 1.'
END
ELSE
BEGIN
    PRINT 'La columna prmVisualizarPantalla ya existe.'
END