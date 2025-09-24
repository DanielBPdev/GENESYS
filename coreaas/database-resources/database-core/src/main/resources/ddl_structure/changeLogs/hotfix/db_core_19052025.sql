IF EXISTS (
    SELECT 1
    FROM INFORMATION_SCHEMA.TABLES
    WHERE TABLE_NAME = 'ContraladorCarteraAportes'
      AND TABLE_SCHEMA = 'dbo'
)
BEGIN
    EXEC sp_rename 'dbo.ContraladorCarteraAportes', 'ControladorCarteraAportes', 'OBJECT';
END
