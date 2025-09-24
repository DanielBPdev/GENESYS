IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.TABLES
    WHERE TABLE_NAME = 'ParametrizacionValorUVT' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    CREATE TABLE ParametrizacionValorUVT (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    anio DATE,
    valoruvt INT
);
END

IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'catTarifaUVTPersona' AND TABLE_NAME = 'Categoria' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    ALTER TABLE Categoria ADD catTarifaUVTPersona VARCHAR(50) NULL
END

IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'ctaTarifaUVT' AND TABLE_NAME = 'CategoriaAfiliado' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    ALTER TABLE CategoriaAfiliado ADD ctaTarifaUVT VARCHAR(50) NULL
END

IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'catTarifaUVTPersona' AND TABLE_NAME = 'Categoria_aud' and TABLE_SCHEMA = 'aud'
)
BEGIN
    ALTER TABLE aud.Categoria_aud ADD catTarifaUVTPersona VARCHAR(50) NULL
END