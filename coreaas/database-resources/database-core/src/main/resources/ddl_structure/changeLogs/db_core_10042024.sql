---Correccion vcoNombreConstante Gerente financiera ---
UPDATE VariableComunicado
SET vcoNombreConstante = 'GERENTE_FINANCIERA'
WHERE vcoClave = '${gerentefinanciera}';

------Nueva columna base de datos en la tabla PlantillaComunicado --------
IF NOT EXISTS 
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'pcoArchivoAdjunto' AND TABLE_NAME = 'PlantillaComunicado' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    ALTER TABLE PlantillaComunicado ADD pcoArchivoAdjunto BIT DEFAULT 1;
END

------ Nueva columna base de datos en la tabla aud.PlantillaComunicado_aud--------
IF NOT EXISTS 
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'pcoArchivoAdjunto' AND TABLE_NAME = 'PlantillaComunicado_aud' and TABLE_SCHEMA = 'aud'
)
BEGIN
    ALTER TABLE aud.PlantillaComunicado_aud ADD pcoArchivoAdjunto BIT
END