-- =============================================
-- Author:		Andrés Julián Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de Ejecutar los  
-- pasos de la HU395 para la planilla dada
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecutePILA2Fase1VerificarCondicionesValidarAportes]
	@IdRegistroGeneral Bigint

AS
BEGIN
SET NOCOUNT ON;
BEGIN TRY
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	--print '	VerificarDependienteIndependientes'
	IF EXISTS (
		SELECT TOP 1 1 FROM staging.RegistroGeneral WITH(NOLOCK)
		WHERE regId=@IdRegistroGeneral
		AND regEsAportePensionados = 0
	)
	BEGIN

		--print '		ValidarCondicionesDependientes'
		EXEC dbo.USP_ValidateHU395CondicionesAportesDependiente @IdRegistroGeneral

		--print '		ValidarCondicionesIndependientes'
		EXEC dbo.USP_ValidateHU395CondicionesAportesIndependiente @IdRegistroGeneral
	END

	--print '	VerificarPensionados'
	IF EXISTS (
		SELECT TOP 1 1 FROM staging.RegistroGeneral WITH(NOLOCK)
		WHERE regId=@IdRegistroGeneral
		AND regEsAportePensionados = 1
	)
	BEGIN
		--print '		ValidarCondicionesPensionados'
		EXEC dbo.USP_ValidateHU395CondicionesAportesPensionados @IdRegistroGeneral
	END;

	CREATE TABLE #RegistroGeneralTmp (
		regIdTmp bigint NULL
		INDEX IX_regIdTmp (regIdTmp)
	)
		
	INSERT INTO #RegistroGeneralTmp ( 
		regIdTmp
	)
	SELECT
		regId
	FROM staging.RegistroGeneral reg WITH(NOLOCK)
	INNER JOIN staging.RegistroDetallado red WITH(NOLOCK) ON red.redRegistroGeneral = reg.regId
	WHERE reg.regId = @IdRegistroGeneral
	AND red.redOUTEstadoRegistroAporte = 'NO_VALIDADO_BD';
	
	
	UPDATE reg
	SET reg.regOUTEnProceso = 0,
		reg.regDateTimeUpdate = dbo.getLocalDate()
	FROM staging.RegistroGeneral reg WITH(NOLOCK)
	INNER JOIN #RegistroGeneralTmp WITH(NOLOCK) ON regIdTmp = regId;

END TRY
BEGIN CATCH

	DECLARE @ErrorMessage NVARCHAR(4000);
	DECLARE @ErrorSeverity INT;
	DECLARE @ErrorState INT;
	
    SELECT @ErrorMessage = '[dbo].[USP_ExecutePILA2Fase1VerificarCondicionesValidarAportes]|@IdRegistroGeneral=' + CAST(ISNULL(@IdRegistroGeneral,0) AS VARCHAR(20)) + '|' + ERROR_MESSAGE(),
           @ErrorSeverity = ERROR_SEVERITY(),
           @ErrorState = ERROR_STATE();

	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.GetLocalDate(), '@IdRegistroGeneral=' + CAST(@IdRegistroGeneral AS VARCHAR(20)), @ErrorMessage);

    RAISERROR (@ErrorMessage, -- Message text.
               @ErrorSeverity, -- Severity.
               @ErrorState -- State.
               );  
    
END CATCH;
END;
