IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'catTarifaUVTPersona' AND TABLE_NAME = 'Categoria_aud' and TABLE_SCHEMA = 'aud'
)
BEGIN
    ALTER TABLE aud.Categoria_aud ADD catTarifaUVTPersona VARCHAR(50) NULL
END