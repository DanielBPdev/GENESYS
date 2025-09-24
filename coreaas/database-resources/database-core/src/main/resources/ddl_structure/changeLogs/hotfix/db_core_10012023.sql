/**
  * 55414 se añade la columna de comunicado a la tabla AccionCobro2F
 */
IF not exists
	(
		SELECT *
		FROM INFORMATION_SCHEMA.COLUMNS
		WHERE COLUMN_NAME = 'comunicado' AND TABLE_NAME = 'AccionCobro2F' and TABLE_SCHEMA = 'dbo'
	)
	BEGIN
		ALTER TABLE dbo.AccionCobro2F ADD comunicado BIGINT
	END

IF not exists
	(
		SELECT *
		FROM INFORMATION_SCHEMA.COLUMNS
		WHERE COLUMN_NAME = 'comunicado' AND TABLE_NAME = 'AccionCobro2F_aud' and TABLE_SCHEMA = 'aud'
	)
	BEGIN
		ALTER TABLE aud.AccionCobro2F_aud ADD comunicado BIGINT
	END