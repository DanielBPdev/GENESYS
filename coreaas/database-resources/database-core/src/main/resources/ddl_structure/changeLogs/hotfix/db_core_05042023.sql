--liquibase formatted sql

IF not exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE COLUMN_NAME = 'comunicado' AND TABLE_NAME = 'AccionCobro2F' and TABLE_SCHEMA = 'dbo'
)
BEGIN
ALTER TABLE dbo.AccionCobro2F ADD acdComunicado VARCHAR(20)
END

IF not exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE COLUMN_NAME = 'comunicado' AND TABLE_NAME = 'AccionCobro1D_aud' and TABLE_SCHEMA = 'aud'
)
BEGIN
ALTER TABLE aud.AccionCobro2F_aud ADD comunicado VARCHAR(20)
END
