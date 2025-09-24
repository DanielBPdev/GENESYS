-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de ejecutar la fase 1 (HU395, HU396 y HU480) de  
-- PILA 2 por Id de transaccion
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecutePILA2Fase1Validacion]
	@IdTransaccion Bigint,
    @sFase VARCHAR(100) = 'SIN_PARAMETRO'
AS
BEGIN
SET NOCOUNT ON;

DECLARE @regId INT
	
DECLARE @ErrorMessage NVARCHAR(4000);
DECLARE @ErrorSeverity INT;
DECLARE @ErrorState INT;

BEGIN TRY
	DECLARE @estadoArchivo VARCHAR(75), 
			@accionArchivo VARCHAR(75)
	DECLARE @EsPensionado INT
	DECLARE @EjecucionBloque BIT = 0

	UPDATE staging.RegistroGeneral
	SET regOUTEnProceso = 1,
		regDateTimeUpdate = dbo.getLocalDate()
	WHERE regTransaccion = @IdTransaccion
	AND (regOUTEstadoArchivo='RECAUDO_CONCILIADO' OR regOUTEstadoArchivo='RECAUDO_VALOR_CERO_CONCILIADO' OR regOUTEstadoArchivo='PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD' ) --Aptos para bloque de Validaciones

	DECLARE @RegistroGeneralTmp AS TABLE (
		regIdTmp bigint NULL,
		regEsAportePensionados bit NULL
	)
	
	INSERT INTO @RegistroGeneralTMP (regIdTmp, regEsAportePensionados)
	SELECT regId, regEsAportePensionados
	FROM staging.RegistroGeneral WITH (NOLOCK)
	WHERE regTransaccion = @IdTransaccion
	AND (regOUTEstadoArchivo='RECAUDO_CONCILIADO' OR regOUTEstadoArchivo='RECAUDO_VALOR_CERO_CONCILIADO' OR regOUTEstadoArchivo='PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD' ) --Aptos para bloque de Validaciones
	

	DECLARE @AportesTransaccionCursor AS CURSOR
	SET @AportesTransaccionCursor = CURSOR FAST_FORWARD FOR
	SELECT regIdTmp, regEsAportePensionados
	FROM @RegistroGeneralTMP

	OPEN @AportesTransaccionCursor
	FETCH NEXT FROM @AportesTransaccionCursor INTO	@regId, @EsPensionado

	WHILE @@FETCH_STATUS = 0
	BEGIN

		IF @sFase IN ('SIN_PARAMETRO', 'PILA2_FASE_1', 'PILA2_FASE_1_VAL_APORTES_VS_BD')
		BEGIN
			--print 'VerificarCondicionesValidarAportes'
			EXEC USP_ExecutePILA2Fase1VerificarCondicionesValidarAportes @regId
		END

		IF @sFase IN ('SIN_PARAMETRO', 'PILA2_FASE_1', 'PILA2_FASE_1_VAL_REG_PILA_VS_BD_CON_ANALISIS_INTEGRAL')
		BEGIN
			--print 'ValidarRegistrosPILAvsBD' 
			EXEC USP_ExecutePILA2Fase1ValidarRegistrosPILAvsBD @regId
		END
		FETCH NEXT FROM @AportesTransaccionCursor INTO	@regId, @EsPensionado
	END
	CLOSE @AportesTransaccionCursor;
	DEALLOCATE @AportesTransaccionCursor;	
	
	IF @sFase IN ('SIN_PARAMETRO', 'PILA2_FASE_1', 'PILA2_FASE_1_VAL_REG_PILA_VS_BD_CON_ANALISIS_INTEGRAL')
	BEGIN
		--print 'EjecutarPILAIntegralmente'
		EXEC USP_ExecutePILA2Fase1EjecutarPILAIntegralmente @IdTransaccion	
	END

END TRY
BEGIN CATCH

    SELECT @ErrorMessage = '[dbo].[USP_ExecutePILA2Fase1Validacion]|@regId=' + CAST(ISNULL(@regId,0) AS VARCHAR(20)) + '|' + ERROR_MESSAGE(),
           @ErrorSeverity = ERROR_SEVERITY(),
           @ErrorState = ERROR_STATE();

	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.GetLocalDate(), '@IdTransaccion=' + CAST(@IdTransaccion AS VARCHAR(20)) + ';@sFase= ' + @sFase, @ErrorMessage);

    RAISERROR (@ErrorMessage, -- Message text.
               @ErrorSeverity, -- Severity.
               @ErrorState -- State.
               );  
    
END CATCH;
END;