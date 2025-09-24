IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'roaFechaFinCondicionVeterano' AND TABLE_NAME = 'RolAfiliado_aud' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    ALTER TABLE dbo.RolAfiliado_aud ADD roaFechaFinCondicionVeterano DATETIME 
END

IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'roaFechaInicioCondicionVeterano' AND TABLE_NAME = 'RolAfiliado_aud' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    ALTER TABLE dbo.RolAfiliado_aud ADD roaFechaInicioCondicionVeterano DATETIME 
END

IF exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'roaClaseTrabajador' AND TABLE_NAME = 'RolAfiliado_aud' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    alter table dbo.RolAfiliado_aud alter column roaClaseTrabajador varchar(25);
END
