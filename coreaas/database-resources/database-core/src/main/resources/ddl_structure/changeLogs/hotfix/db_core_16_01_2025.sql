IF NOT EXISTS (SELECT 1 
               FROM INFORMATION_SCHEMA.TABLES 
               WHERE TABLE_NAME = 'ConsolaEstadoProcesoMasivo' 
                 AND TABLE_SCHEMA = 'dbo') 
BEGIN
    CREATE TABLE ConsolaEstadoProcesoMasivo (
        cepmId BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
        cepmTipoProcesoMasivo VARCHAR(50) NOT NULL,
        cepmFechaInicio DATETIME NOT NULL,
        cepmFechaFin DATETIME NULL,
        cepmEstadoCargueMasivo VARCHAR(50) NOT NULL,
        cepmGradoAvance DECIMAL(5, 2) NULL,
        cepmError VARCHAR(50) NULL
    )
END
