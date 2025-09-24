------ Nueva columna base de datos en la tabla PlantillaComunicado_aud--------
IF NOT EXISTS 
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'pcoArchivoAdjunto' AND TABLE_NAME = 'PlantillaComunicado_aud' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    ALTER TABLE PlantillaComunicado_aud ADD pcoArchivoAdjunto BIT
END