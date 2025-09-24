CREATE PROCEDURE USP_AUD_DELETE_BufferAuditoria
(
	@iRevIni BIGINT, --@{activity('ObtenerRangoRevisionesCoreLookup').output.firstRow.REVINI}
	@iRevFin BIGINT  --@{activity('ObtenerRangoRevisionesCoreLookup').output.firstRow.REVFIN}
)
AS
BEGIN TRY			
	BEGIN TRAN

		DECLARE @tabla AS NVARCHAR(100)
		DECLARE @sql NVARCHAR(MAX)	
		declare @vRevIni nvarchar(30),@vRevFin nvarchar(30),@mensaje nvarchar(200);

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
			-- Se recorre cada tabla espejo y se verifica si pasaron todos los datos a core_aud
			declare @countCoreAud as table (cantidad bigint,s1 varchar(200))
			declare @countCore as table (cantidad bigint)
			-- Cantidad de registros en core_aud
			SET @sql = 'SELECT COUNT(*) FROM ' + @tabla + ' WHERE REV >= @iRevIni AND  REV <= @iRevFin';
			insert @countCoreAud (cantidad,s1)
			exec sp_execute_remote CoreAudReferenceData, @sql , N'@iRevIni BIGINT, @iRevFin BIGINT', @iRevIni = @iRevIni, @iRevFin = @iRevFin

			-- Cantidad de registros en core
			SET @sql = 'SELECT COUNT(*) FROM aud.' + @tabla + ' WHERE REV >= @iRevIni AND  REV <= @iRevFin'
			insert @countCore (cantidad)
			EXEC sp_executesql @sql, N'@iRevIni BIGINT, @iRevFin BIGINT', @iRevIni = @iRevIni, @iRevFin = @iRevFin

			IF (select cantidad from @countCoreAud)<>(select cantidad from @countCore)
			BEGIN;			
				SET @vRevIni = CAST(@iRevIni AS NVARCHAR);
				SET @vRevFin = CAST(@iRevFin AS NVARCHAR);
				SET @mensaje = N'No se puede eliminar información del buffer de core, la cantidad de registros difieren de core_aud para la tabla: ' + @tabla +N' en el rango revisiones: ' 
							+ @vRevIni + N'-' + @vRevFin;
				THROW 50005, @mensaje, 1; 
			END

			SET @sql = 'DELETE FROM aud.' + @tabla + ' WHERE REV >= @iRevIni AND  REV <= @iRevFin'
			EXEC sp_executesql @sql, N'@iRevIni BIGINT, @iRevFin BIGINT', @iRevIni = @iRevIni, @iRevFin = @iRevFin

			DELETE FROM @countCoreAud;
			DELETE FROM @countCore;
				
			FETCH NEXT FROM @TablasAudCursor INTO	@tabla
		END
		CLOSE @TablasAudCursor;
		DEALLOCATE @TablasAudCursor;
		
		-- RevisionEntidad
		SET @sql = 'SELECT COUNT(*) FROM RevisionEntidad WHERE reeRevision >= @iRevIni AND reeRevision <= @iRevFin';
		insert @countCoreAud (cantidad,s1)
		exec sp_execute_remote CoreAudReferenceData, @sql , N'@iRevIni INT, @iRevFin INT', @iRevIni = @iRevIni, @iRevFin = @iRevFin

		IF  (select cantidad from @countCoreAud)<>(SELECT COUNT(*) FROM aud.RevisionEntidad WHERE reeRevision >= @iRevIni AND reeRevision <= @iRevFin)
		BEGIN		
			SET @vRevIni = CAST(@iRevIni AS NVARCHAR);
			SET @vRevFin = CAST(@iRevFin AS NVARCHAR);
			SET @mensaje = N'No se puede eliminar información del buffer de core, la cantidad de registros difieren de core_aud para la tabla: RevisionEntidad en el rango revisiones: ' 
						+ @vRevIni + N'-' + @vRevFin;
			THROW 50005, @mensaje, 1; 
		END
		DELETE FROM @countCoreAud
		DELETE FROM aud.RevisionEntidad WHERE reeRevision >= @iRevIni AND reeRevision <= @iRevFin

		-- Revision
		SET @sql = 'SELECT COUNT(*) FROM Revision WHERE revId >= @iRevIni AND revId <= @iRevFin';
		insert @countCoreAud (cantidad,s1)
		exec sp_execute_remote CoreAudReferenceData, @sql , N'@iRevIni INT, @iRevFin INT', @iRevIni = @iRevIni, @iRevFin = @iRevFin

		IF  (select cantidad from @countCoreAud)<>(SELECT COUNT(*) FROM aud.Revision WHERE revId >= @iRevIni AND revId <= @iRevFin)
		BEGIN		
			SET @vRevIni = CAST(@iRevIni AS NVARCHAR);
			SET @vRevFin = CAST(@iRevFin AS NVARCHAR);
			SET @mensaje = N'No se puede eliminar información del buffer de core, la cantidad de registros difieren de core_aud para la tabla: Revision en el rango revisiones: ' 
						+ @vRevIni + N'-' + @vRevFin;
			THROW 50005, @mensaje, 1; 
		END
		DELETE FROM @countCoreAud
		DELETE FROM aud.Revision WHERE revId >= @iRevIni AND  revId <= @iRevFin
	COMMIT;
END TRY
BEGIN CATCH			
	ROLLBACK;
	BEGIN TRY
		INSERT RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.GetLocalDate(),'[USP_AUD_DELETE_BufferAuditoria] @@iRevIni:'
						+ ISNULL(CAST(@iRevIni AS VARCHAR(20)),'NULL')
							+ '@@iRevFin: ' + ISNULL(CAST(@iRevFin AS VARCHAR(20)),'NULL')  ,ERROR_MESSAGE());
		THROW;
	END TRY
	BEGIN CATCH
		PRINT 'Fallo RegistroLog';
		THROW;
	END CATCH
END CATCH;

