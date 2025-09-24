-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de ejecutar
-- la fase 3 (Novedades) de PILA Mundo2 por Id de Transaccion
-- =============================================
CREATE PROCEDURE USP_ExecutePILA2Fase3RegistrarRelacionarNovedades
(
	@IdTransaccion BIGINT,
	@EsSimulado Bit = 0,
	@EsRegistroManual BIT = 0,
	@FechaReferenciaNovedadFutura DATE = NULL
)
AS
BEGIN
SET NOCOUNT ON;
DECLARE @regId INT
	
DECLARE @ErrorMessage NVARCHAR(4000);
DECLARE @ErrorSeverity INT;
DECLARE @ErrorState INT;

BEGIN TRY

	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED

	DECLARE @NovedadesTransaccionCursor AS CURSOR
		

	DECLARE @Tablaids AS TABLE (regId bigint)
	
	
	
	
	INSERT INTO @Tablaids (regId)
	SELECT regId
	FROM staging.RegistroGeneral
	WHERE regTransaccion = @IdTransaccion
	AND (
		regOUTEstadoArchivo='REGISTRADO_O_RELACIONADO_LOS_APORTES'
		OR (regNovedadFutura = 1 AND regOUTNovedadFuturaProcesada = 0)
	) --Aptos para bloque de Novedades

	ORDER BY regTipoIdentificacionAportante, regNumeroIdentificacionAportante, regPeriodoAporte DESC

	SET @NovedadesTransaccionCursor = CURSOR FAST_FORWARD FOR
	SELECT regId
	FROM @Tablaids
	
	OPEN @NovedadesTransaccionCursor
	FETCH NEXT FROM @NovedadesTransaccionCursor INTO	@regId

	IF @FechaReferenciaNovedadFutura IS NULL 
	BEGIN
		SET @FechaReferenciaNovedadFutura = dbo.GetLocalDate()
	END

	WHILE @@FETCH_STATUS = 0
	BEGIN
	
		EXEC USP_ExecuteRegistrarRelacionarNovedadesRegistro @regId, @EsSimulado, @EsRegistroManual, @FechaReferenciaNovedadFutura
	
		FETCH NEXT FROM @NovedadesTransaccionCursor INTO	@regId
	END
	CLOSE @NovedadesTransaccionCursor;
	DEALLOCATE @NovedadesTransaccionCursor;
	
END TRY
BEGIN CATCH

    SELECT @ErrorMessage = '[dbo].[USP_ExecutePILA2Fase3RegistrarRelacionarNovedades]|@regId=' + CAST(ISNULL(@regId,0) AS VARCHAR(20)) + '|' + ERROR_MESSAGE(),
           @ErrorSeverity = ERROR_SEVERITY(),
           @ErrorState = ERROR_STATE();

    RAISERROR (@ErrorMessage, -- Message text.
               @ErrorSeverity, -- Severity.
               @ErrorState -- State.
               );  
    
END CATCH;
END;