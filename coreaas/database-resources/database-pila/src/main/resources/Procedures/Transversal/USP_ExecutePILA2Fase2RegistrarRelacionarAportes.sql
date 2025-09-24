-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de ejecutar
-- la fase 2 (Aportes) de PILA Mundo2 por Id de Transaccion
-- =============================================
CREATE PROCEDURE USP_ExecutePILA2Fase2RegistrarRelacionarAportes
	@IdTransaccion Bigint,
	@EsSimulado Bit = 0,
	@EsProcesoManual BIT = 0

AS
BEGIN
SET NOCOUNT ON;
DECLARE @regId INT

DECLARE @ErrorMessage NVARCHAR(4000);
DECLARE @ErrorSeverity INT;
DECLARE @ErrorState INT;

BEGIN TRY

	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	DECLARE @AportesTransaccionCursor AS CURSOR

	UPDATE staging.RegistroGeneral
	SET regOUTEnProceso = 1,
		regDateTimeUpdate = dbo.getLocalDate()
	WHERE regTransaccion = @IdTransaccion
	AND (regOUTEstadoArchivo='PROCESADO_VS_BD' OR regOUTEstadoArchivo='PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES') --Aptos para bloque de Aportes

	
	DECLARE @RegistroGeneralTMP AS TABLE (
		regId BIGINT 
	)
	
	INSERT INTO @RegistroGeneralTMP (regId)
	SELECT regId
	FROM staging.RegistroGeneral
	WHERE regTransaccion = @IdTransaccion
	AND (regOUTEstadoArchivo='PROCESADO_VS_BD' OR regOUTEstadoArchivo='PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES') --Aptos para bloque de Aportes

	SET @AportesTransaccionCursor = CURSOR FAST_FORWARD FOR
	SELECT regId
	FROM @RegistroGeneralTMP
	
	OPEN @AportesTransaccionCursor
	FETCH NEXT FROM @AportesTransaccionCursor INTO	@regId

	WHILE @@FETCH_STATUS = 0
	BEGIN
	
		EXEC USP_ExecuteRegistrarRelacionarAportesRegistro @regId, @EsSimulado, @EsProcesoManual
	
		FETCH NEXT FROM @AportesTransaccionCursor INTO	@regId
	END
	CLOSE @AportesTransaccionCursor;
	DEALLOCATE @AportesTransaccionCursor;

END TRY
BEGIN CATCH

    SELECT @ErrorMessage = '[dbo].[USP_ExecutePILA2Fase2RegistrarRelacionarAportes]|@regId=' + CAST(ISNULL(@regId,0) AS VARCHAR(20)) + '|' + ERROR_MESSAGE(),
           @ErrorSeverity = ERROR_SEVERITY(),
           @ErrorState = ERROR_STATE();

    RAISERROR (@ErrorMessage, -- Message text.
               @ErrorSeverity, -- Severity.
               @ErrorState -- State.
               );  
    
END CATCH;
END;