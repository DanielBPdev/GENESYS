/**
  * 55414 se añade la columna de acdComunicado a la tabla AccionCobro1D
 */
IF not exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE COLUMN_NAME = 'acdComunicado' AND TABLE_NAME = 'AccionCobro1D' and TABLE_SCHEMA = 'dbo'
)
BEGIN
  ALTER TABLE dbo.AccionCobro1D ADD acdComunicado BIGINT
END

IF not exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE COLUMN_NAME = 'acdComunicado' AND TABLE_NAME = 'AccionCobro1D_aud' and TABLE_SCHEMA = 'aud'
)
BEGIN
  ALTER TABLE aud.AccionCobro1D_aud ADD acdComunicado BIGINT
END