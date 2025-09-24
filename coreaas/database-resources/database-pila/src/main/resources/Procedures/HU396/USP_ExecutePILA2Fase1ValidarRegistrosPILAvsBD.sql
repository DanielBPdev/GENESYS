-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de Ejecutar los  
-- pasos de la HU396 para la planilla dada
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecutePILA2Fase1ValidarRegistrosPILAvsBD]
	@IdRegistroGeneral Bigint
AS
BEGIN
SET NOCOUNT ON;
BEGIN TRY
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
	
	UPDATE staging.RegistroDetallado
	SET redOUTEstadoValidacionV0 = NULL,
		redOUTEstadoValidacionV1 = NULL,
		redOUTEstadoValidacionV2 = NULL,
		redOUTEstadoValidacionV3 = NULL,
		redDateTimeUpdate = @redDateTimeUpdate 
	WHERE redRegistroGeneral = @IdRegistroGeneral
	AND redOUTEstadoRegistroAporte = 'NO_OK'

	--print '	VerificarDependienteIndependientes'
	
	IF EXISTS (		
		SELECT TOP 1 1 FROM staging.RegistroGeneral WITH (NOLOCK)
		WHERE regEsAportePensionados = 'False' 
		AND regId = @IdRegistroGeneral
	)
	BEGIN

		--print '		ValidarRegistrosPILAvsBD_Dependientes'
		EXEC dbo.USP_ValidateHU396V1Dependientes @IdRegistroGeneral --nuevo
		
		--print '		ValidarEstadoArchivoIndependientes'
		EXEC dbo.USP_ValidateEstadoArchivoDependientes @IdRegistroGeneral --nuevo		

		--print '		ValidarRegistrosPILAvsBD_Independientes'
		EXEC dbo.USP_ValidateHU396V1Independientes @IdRegistroGeneral

		--print '		ValidarEstadoArchivoInDependientes'
		EXEC dbo.USP_ValidateEstadoArchivoInDependiente @IdRegistroGeneral
	END

	--print '	ValidarRegistrosPILAvsBD_Pensionados'
	IF EXISTS (
		SELECT TOP 1 1 FROM staging.RegistroGeneral WITH (NOLOCK)
		WHERE regEsAportePensionados = 'True' 
		AND regId = @IdRegistroGeneral
	)
	BEGIN
		--print '		ValidarRegistrosPILAvsBD_Pensionados'
		EXEC dbo.USP_ValidateHU396V1Pensionados @IdRegistroGeneral

		--print '		ValidarEstadoArchivoPensionados'
		EXEC dbo.USP_ValidateEstadoArchivoPensionado @IdRegistroGeneral
	END;

	UPDATE reg
	SET reg.regOUTEnProceso = 0,
		reg.regDateTimeUpdate = dbo.getLocalDate()
	FROM staging.RegistroGeneral reg WITH (NOLOCK)
	INNER JOIN staging.RegistroDetallado red WITH (NOLOCK) ON red.redRegistroGeneral = reg.regId
	WHERE reg.regId = @IdRegistroGeneral
	AND red.redOUTEstadoRegistroAporte = 'NO_OK'
END TRY
BEGIN CATCH

	DECLARE @ErrorMessage NVARCHAR(4000);
	DECLARE @ErrorSeverity INT;
	DECLARE @ErrorState INT;

    SELECT @ErrorMessage = '[dbo].[USP_ExecutePILA2Fase1ValidarRegistrosPILAvsBD]|@IdRegistroGeneral=' + CAST(ISNULL(@IdRegistroGeneral,0) AS VARCHAR(20)) + '|' + ERROR_MESSAGE(),
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
