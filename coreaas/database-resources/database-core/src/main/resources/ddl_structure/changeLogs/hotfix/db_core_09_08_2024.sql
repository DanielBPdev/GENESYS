IF NOT EXISTS ( SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = 'dtsCreoArchivo' AND TABLE_NAME = 'DatoTemporalSolicitud' AND TABLE_SCHEMA = 'dbo')
BEGIN 
ALTER TABLE dbo.DatoTemporalSolicitud ADD dtsCreoArchivo smallint DEFAULT 0 CHECK (dtsCreoArchivo BETWEEN 0 AND 1);
END 