CREATE PROCEDURE USP_AUD_UTIL_TableList
(
	@iRevIni BIGINT, --@{activity('ObtenerRangoRevisionesCoreLookup').output.firstRow.REVINI}
	@iRevFin BIGINT  --@{activity('ObtenerRangoRevisionesCoreLookup').output.firstRow.REVFIN}
)
AS

	DECLARE @tablasAud AS TABLE (tabla VARCHAR(100), columnas NVARCHAR(MAX))
	DECLARE @tabla AS VARCHAR(100)
	DECLARE @tabla_aud AS VARCHAR(100)
	DECLARE @columns NVARCHAR(MAX)
	DECLARE @sql NVARCHAR(MAX)
	DECLARE @start BIGINT 
	DECLARE @fin BIGINT
	DECLARE @cuenta BIGINT

	INSERT INTO @TablasAud (tabla) 
	SELECT TABLE_NAME tabla
	FROM INFORMATION_SCHEMA.TABLES
	WHERE TABLE_NAME LIKE '%_aud'
	AND TABLE_SCHEMA = 'aud'

	DECLARE @TablasAudCursor AS CURSOR
	SET @TablasAudCursor = CURSOR FAST_FORWARD FOR
		SELECT tabla FROM @TablasAud
	OPEN @TablasAudCursor
	FETCH NEXT FROM @TablasAudCursor INTO @tabla

	WHILE @@FETCH_STATUS = 0
	BEGIN
		SET @columns = NULL

		SET @sql = '
		SELECT @cuenta = COUNT(1)
		FROM aud.' + @tabla + '
		WHERE REV >= @iRevIni AND REV <= @iRevFin'
		
		EXEC sp_executesql @sql, 
		N'@iRevIni INT, @iRevFin INT, @cuenta AS INT OUTPUT', @iRevIni = @iRevIni, @iRevFin = @iRevFin, @cuenta = @cuenta OUTPUT	
			
		IF @Cuenta > 0
		BEGIN
			SELECT @columns = COALESCE(@columns + ',','') + COLUMN_NAME
			FROM INFORMATION_SCHEMA.COLUMNS
			WHERE TABLE_NAME = @tabla
			AND TABLE_SCHEMA = 'aud'

			UPDATE @TablasAud SET columnas = @columns WHERE tabla = @tabla
		END
		ELSE
		BEGIN
			DELETE FROM @TablasAud WHERE tabla = @tabla
		END
	
		FETCH NEXT FROM @TablasAudCursor INTO	@tabla
	END
	CLOSE @TablasAudCursor;
	DEALLOCATE @TablasAudCursor;

	SELECT tabla, columnas
	FROM @tablasAud
;
CREATE PROCEDURE USP_AUD_DELETE_BufferAuditoria
(
	@iRevIni BIGINT, --@{activity('ObtenerRangoRevisionesCoreLookup').output.firstRow.REVINI}
	@iRevFin BIGINT  --@{activity('ObtenerRangoRevisionesCoreLookup').output.firstRow.REVFIN}
)
AS
	DECLARE @tabla AS VARCHAR(100)
	DECLARE @sql NVARCHAR(MAX)

	

	DECLARE @TablasAudCursor AS CURSOR
	SET @TablasAudCursor = CURSOR FAST_FORWARD FOR
		SELECT TABLE_NAME tabla
		FROM INFORMATION_SCHEMA.TABLES
		WHERE TABLE_NAME LIKE '%_aud'
		AND TABLE_SCHEMA = 'aud'
	OPEN @TablasAudCursor
	FETCH NEXT FROM @TablasAudCursor INTO @tabla

	WHILE @@FETCH_STATUS = 0
	BEGIN
		
		SET @sql = 'DELETE FROM aud.' + @tabla + ' WHERE REV >= @iRevIni AND  REV <= @iRevFin'		
		EXEC sp_executesql @sql, N'@iRevIni INT, @iRevFin INT', @iRevIni = @iRevIni, @iRevFin = @iRevFin
			
		FETCH NEXT FROM @TablasAudCursor INTO	@tabla
	END
	CLOSE @TablasAudCursor;
	DEALLOCATE @TablasAudCursor;

	DELETE FROM aud.RevisionEntidad WHERE reeRevision >= @iRevIni AND reeRevision <= @iRevFin
	DELETE FROM aud.Revision WHERE revId >= @iRevIni AND  revId <= @iRevFin
;