IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'catTarifaUVTPersona' AND TABLE_NAME = 'Categoria_aud' 
)
BEGIN
    ALTER TABLE Categoria_aud ADD catTarifaUVTPersona VARCHAR(50) NULL
END