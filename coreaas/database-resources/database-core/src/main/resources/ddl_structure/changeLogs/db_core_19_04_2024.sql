IF exists
(
    SELECT 1
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'pcoArchivoAdjunto' AND TABLE_NAME = 'PlantillaComunicado' and TABLE_SCHEMA = 'dbo'
)
BEGIN

   	update PlantillaComunicado
    set pcoArchivoAdjunto = 1
    where pcoArchivoAdjunto is null
    
END
