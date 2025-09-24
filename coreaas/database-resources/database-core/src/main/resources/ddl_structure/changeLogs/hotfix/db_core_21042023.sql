--liquibase formatted sql

IF  exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE COLUMN_NAME = 'comunicado' AND TABLE_NAME = 'AccionCobro2F' and TABLE_SCHEMA = 'dbo'
)
BEGIN
ALTER TABLE dbo.AccionCobro1D  ALTER COLUMN acdComunicado VARCHAR(50)
END

IF  exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE COLUMN_NAME = 'comunicado' AND TABLE_NAME = 'AccionCobro2F_aud' and TABLE_SCHEMA = 'aud'
)
BEGIN
ALTER TABLE aud.AccionCobro1D_aud ADD acdComunicado VARCHAR(50)
END
