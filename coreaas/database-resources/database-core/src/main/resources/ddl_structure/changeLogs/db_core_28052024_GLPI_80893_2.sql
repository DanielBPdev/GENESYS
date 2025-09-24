-- Verifica si la columna existe
IF EXISTS (
    SELECT *
    FROM sys.columns
    WHERE object_id = OBJECT_ID('parametro')
    AND name = 'prmVisualizarPantalla'
)
BEGIN
    -- Actualiza todos los registros para que el valor sea 1
    UPDATE parametro
    SET prmVisualizarPantalla = 1;

    PRINT 'Todos los registros actualizados a 1 en prmVisualizarPantalla.'

    -- Establece prmVisualizarPantalla a 0 solo donde prmNombre = 'NIT_CONFA' y prmValor <> '890806490'
    UPDATE parametro
    SET prmVisualizarPantalla = 0
    WHERE prmNombre = 'NIT_CONFA' AND prmValor <> '890806490';

    PRINT 'Registros específicos actualizados a 0 en prmVisualizarPantalla.'
END
ELSE
BEGIN
    PRINT 'La columna prmVisualizarPantalla no existe.'
END