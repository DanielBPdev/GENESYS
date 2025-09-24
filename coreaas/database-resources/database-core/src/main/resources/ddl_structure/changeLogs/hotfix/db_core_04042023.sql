--liquibase formatted sql

IF not exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE COLUMN_NAME = 'acdComunicado' AND TABLE_NAME = 'AccionCobro1D' and TABLE_SCHEMA = 'dbo'
)
BEGIN
ALTER TABLE dbo.AccionCobro1D ADD acdComunicado VARCHAR(20)
END

IF not exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE COLUMN_NAME = 'acdComunicado' AND TABLE_NAME = 'AccionCobro1D_aud' and TABLE_SCHEMA = 'aud'
)
BEGIN
ALTER TABLE aud.AccionCobro1D_aud ADD acdComunicado VARCHAR(20)
END
