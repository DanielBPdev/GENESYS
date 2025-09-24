-- =============================================
-- Author:		Mauricio Sanchez Castro 
-- Create date: 2017/04/16
-- Description:	Procedimiento almacenado encargado de verificar si un registro del 
-- archivo IP de PILA, debe ser evaluado como pensionado.
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ValidateHU395CondicionesAportesPensionados]
	@IdRegistroGeneral Bigint
AS
BEGIN
SET NOCOUNT ON;
BEGIN TRY
	
	CREATE TABLE #RegistroDetalladoTmp (
		redIdTmp bigint NULL,
		redOUTMarcaValRegistroAporteTmp varchar(50) NULL,
		redOUTEstadoRegistroAporteTmp varchar(60) NULL,
		redOUTFechaProcesamientoValidRegAporteTmp datetime NULL
		INDEX IX_redIdTmp (redIdTmp)
	)
	
	INSERT INTO #RegistroDetalladoTmp (
		redIdTmp
		,redOUTMarcaValRegistroAporteTmp
		,redOUTEstadoRegistroAporteTmp
		,redOUTFechaProcesamientoValidRegAporteTmp
	)
	SELECT
		redId
		,CASE WHEN ISNULL(staging.RegistroDetallado.redOUTEstadoSolicitante, '') IN ('ACTIVO') THEN 'VALIDAR_COMO_PENSIONADO' ELSE 'NO_VALIDADO_BD' END
		,CASE WHEN ISNULL(staging.RegistroDetallado.redOUTEstadoSolicitante, '') NOT IN ('ACTIVO') THEN 'NO_VALIDADO_BD_APROBADO' END
		,dbo.GetLocalDate()
	FROM staging.RegistroGeneral WITH(NOLOCK) JOIN staging.RegistroDetallado WITH(NOLOCK) ON staging.RegistroGeneral.regId = staging.RegistroDetallado.redRegistroGeneral
	WHERE staging.RegistroGeneral.regId = @IdRegistroGeneral
	AND ISNULL(redOUTEstadoRegistroAporte,'NO_VALIDADO_BD') = 'NO_VALIDADO_BD'
	
	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
	UPDATE red
	SET redOUTMarcaValRegistroAporte = redOUTMarcaValRegistroAporteTmp
		,redOUTEstadoRegistroAporte = redOUTEstadoRegistroAporteTmp 
		,redOUTFechaProcesamientoValidRegAporte = redOUTFechaProcesamientoValidRegAporteTmp
		,redDateTimeUpdate = @redDateTimeUpdate 
	FROM staging.RegistroDetallado red WITH(NOLOCK)
	INNER JOIN #RegistroDetalladoTmp WITH(NOLOCK) ON redIdTmp = redId
	
END TRY
BEGIN CATCH

	DECLARE @ErrorMessage NVARCHAR(4000);
	DECLARE @ErrorSeverity INT;
	DECLARE @ErrorState INT;
	
    SELECT @ErrorMessage = '[dbo].[USP_ValidateHU395CondicionesAportesPensionados]|@IdRegistroGeneral=' + CAST(ISNULL(@IdRegistroGeneral,0) AS VARCHAR(20)) + '|' + ERROR_MESSAGE(),
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