-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/08/14
-- Description:	Procedimiento almacenado encargado de re-validar la fase 1
-- de PILA 2, para establecer sí el estado del aporte ha cambiado
-- =============================================
CREATE PROCEDURE [dbo].[USP_GET_RevalidarPila2Fase1]
	@iIdRegistroGeneral BIGINT
AS

BEGIN
SET NOCOUNT ON;
	
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	BEGIN TRY

		--print 'Inicia USP_GET_RevalidarPila2Fase1'

		DECLARE @iTransaccion BIGINT,
			@sEstadoArchivoOriginal VARCHAR(60)

		-- se consulta el número de transacción y estado del archivo del registro general
		SELECT @iTransaccion = reg.regTransaccion,
			@sEstadoArchivoOriginal = reg.regOUTEstadoArchivo
		FROM staging.RegistroGeneral reg WITH(NOLOCK)
		WHERE reg.regId = @iIdRegistroGeneral

		-- se crea una tabla temporal en la que se almacenen los estados de aporte originales de los registros detallados 
		CREATE TABLE #estadosAporteDetalle (redId BIGINT, redOUTEstadoRegistroAporte VARCHAR(60))
		INSERT INTO #estadosAporteDetalle (redId, redOUTEstadoRegistroAporte)
		SELECT red.redId, red.redOUTEstadoRegistroAporte 
		FROM staging.RegistroGeneral reg WITH(NOLOCK)
		INNER JOIN staging.RegistroDetallado red WITH(NOLOCK) ON red.redRegistroGeneral = reg.regId
		WHERE reg.regId = @iIdRegistroGeneral

		-- sí el registro general aún tiene transacción, se debe invocar un borrado de staging
		IF @iTransaccion IS NOT NULL
		BEGIN
			EXEC USP_DeleteBloqueStaging @iTransaccion
		END

		-- se crea un nuevo número de transacción
		INSERT INTO [staging].[Transaccion] (traFechaTransaccion)
		VALUES (dbo.GetLocalDate())

		SET @iTransaccion = @@IDENTITY

		-- se asigna el nuevo número de transacción al registro general
		UPDATE staging.RegistroGeneral
		SET regTransaccion = @iTransaccion,
			regDateTimeUpdate = dbo.getLocalDate()
		WHERE regId = @iIdRegistroGeneral

		-- se solicita una nueva creación de staging para actualizar los datos del registro general
		DECLARE @bSuccess BIT = 0

		EXEC [dbo].[USP_ExecuteBloqueStaging] @iTransaccion, @bSuccess = @bSuccess OUTPUT
    
		IF @bSuccess = 0
		BEGIN
			--print 'Fallo en bloque staging'
			RAISERROR ( 'Falló la ejecución del bloque staging', 11, 1);
		END

		-- se valida nuevamente por HU-211-395
		EXEC USP_ExecutePILA2Fase1VerificarCondicionesValidarAportes @iIdRegistroGeneral

		-- se valida nuevamente por HU-211-396 y HU-211-480
		EXEC USP_ExecutePILA2Fase1ValidarRegistrosPILAvsBD @iIdRegistroGeneral

		-- se reestablece el valor del campo regOUTEstadoArchivo para el resgistro general
		UPDATE staging.RegistroGeneral
		SET regOUTEstadoArchivo = @sEstadoArchivoOriginal,
			regDateTimeUpdate = dbo.getLocalDate()
		WHERE regId = @iIdRegistroGeneral

		-- se recorren los registros detallados consultados para reestablecer sus valores en el campo redOUTEstadoRegistroAporte
		DECLARE @iIdRegistroDetallado BIGINT,
			@sEstadoRegistroAporte VARCHAR(60),
			@sNuevoEstadoRegistroAporte VARCHAR(60),
			@sql NVARCHAR(4000)

		DECLARE @DBNAME VARCHAR(255)
		SET @DBNAME = dbo.FN_GET_CORE_DBNAME(SUSER_SNAME())

		
		DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
		
		DECLARE @cursorRegistrosDetallados AS CURSOR
		SET @cursorRegistrosDetallados = CURSOR FAST_FORWARD FOR
			SELECT redId, redOUTEstadoRegistroAporte FROM #estadosAporteDetalle WITH(NOLOCK)

		OPEN @cursorRegistrosDetallados

		FETCH NEXT FROM @cursorRegistrosDetallados INTO @iIdRegistroDetallado, @sEstadoRegistroAporte

		
		WHILE @@FETCH_STATUS = 0
		BEGIN
			-- se consulta el estado ajustado antes de volverlo a su valor original
			SELECT @sNuevoEstadoRegistroAporte = redOUTEstadoRegistroAporte
			FROM staging.RegistroDetallado WITH(NOLOCK)
			WHERE redId = @iIdRegistroDetallado

			--print '@sNuevoEstadoRegistroAporte'
			--print @sNuevoEstadoRegistroAporte

			-- se registra el dato para posterior actualización en core
			INSERT INTO dbo.TemAporteActualizado (taaEstadoRegistroAporte, taaRegistroDetallado)
			VALUES (@sNuevoEstadoRegistroAporte, @iIdRegistroDetallado)

			UPDATE staging.RegistroDetallado
			SET redOUTEstadoRegistroAporte = @sEstadoRegistroAporte
			,redDateTimeUpdate = @redDateTimeUpdate
			WHERE redId = @iIdRegistroDetallado

			FETCH NEXT FROM @cursorRegistrosDetallados INTO @iIdRegistroDetallado, @sEstadoRegistroAporte
		END
		CLOSE @cursorRegistrosDetallados;
		DEALLOCATE @cursorRegistrosDetallados;
	END TRY
	BEGIN CATCH
	--	print 'Finaliza USP_GET_RevalidarPila2Fase1'

		DECLARE @ErrorMessage NVARCHAR(4000);
		DECLARE @ErrorSeverity INT;
		DECLARE @ErrorState INT;

		SELECT @ErrorMessage = '[dbo].[USP_GET_RevalidarPila2Fase1]|' + ERROR_MESSAGE(),
			   @ErrorSeverity = ERROR_SEVERITY(),
			   @ErrorState = ERROR_STATE();
			   
		--print @ErrorMessage;

		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.GetLocalDate(), '@iIdRegistroGeneral=' + CAST(@iIdRegistroGeneral AS VARCHAR(20)), @ErrorMessage);

		RAISERROR (@ErrorMessage, -- Message text.
				   @ErrorSeverity, -- Severity.
				   @ErrorState -- State.
				   );  
    
	END CATCH

	--print 'Finaliza USP_GET_RevalidarPila2Fase1'
END