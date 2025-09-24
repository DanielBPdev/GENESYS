--liquibase formatted sql

IF  exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE COLUMN_NAME = 'comunicado' AND TABLE_NAME = 'AccionCobro2F' and TABLE_SCHEMA = 'dbo'
)
BEGIN
ALTER TABLE dbo.AccionCobro2F  ALTER COLUMN comunicado VARCHAR(50)
END

IF  exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE COLUMN_NAME = 'comunicado' AND TABLE_NAME = 'AccionCobro2F_aud' and TABLE_SCHEMA = 'aud'
)
BEGIN
ALTER TABLE aud.AccionCobro2F_aud ADD comunicado VARCHAR(50)
END
