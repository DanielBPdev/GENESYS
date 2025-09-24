IF EXISTS (
    SELECT 1
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'ContraladorCarteraAportes'
      AND COLUMN_NAME = 'ccaFechaRegistro'
)
BEGIN
    EXEC sp_rename 'dbo.ContraladorCarteraAportes.ccaFechaRegistro', 'ccaFechaCreacionRegistro', 'COLUMN';
END;

IF EXISTS (
    SELECT 1
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'ContraladorCarteraAportes'
      AND COLUMN_NAME = 'ccaPlanilla'
)
BEGIN
    EXEC sp_rename 'dbo.ContraladorCarteraAportes.ccaPlanilla', 'ccaIndicePlanilla', 'COLUMN';
END;

IF EXISTS (
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE 
        TABLE_NAME = 'ContraladorCarteraAportes'
        AND TABLE_SCHEMA = 'dbo'
        AND COLUMN_NAME = 'ccaFechaCreacionRegistro'
        AND DATA_TYPE != 'datetime2'
)
BEGIN
    ALTER TABLE dbo.ContraladorCarteraAportes
    ALTER COLUMN ccaFechaCreacionRegistro DATETIME2;
END