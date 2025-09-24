-- =============================================
-- Author:		Mauricio Sanchez Castro
-- Create date: 2017/04/16
-- Description:	Procedimiento almacenado encargado  de verificar si un registro del 
-- archivo I de PILA, debe ser evaluado como dependiente.
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidateHU395CondicionesAportesDependiente]  
	@IdRegistroGeneral Bigint
AS
BEGIN
SET NOCOUNT ON;
BEGIN TRY
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	CREATE TABLE #TablaDependientes (tipoCotizante VARCHAR(2))

	INSERT INTO #TablaDependientes
	SELECT Data FROM dbo.Split( (
			SELECT stpValorParametro FROM staging.StagingParametros WITH(NOLOCK)
			WHERE stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE') ,',')

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
		,CASE WHEN ISNULL(regOUTEstadoEmpleador, '') IN ('ACTIVO','INACTIVO', 'NO_FORMALIZADO_RETIRADO_CON_APORTES') THEN 'VALIDAR_COMO_DEPENDIENTE' ELSE 'NO_VALIDADO_BD' END
		,CASE WHEN ISNULL(regOUTEstadoEmpleador, '') NOT IN ('ACTIVO','INACTIVO', 'NO_FORMALIZADO_RETIRADO_CON_APORTES') THEN 'NO_VALIDADO_BD' END
		,dbo.GetLocalDate()
	FROM staging.RegistroGeneral reg WITH(NOLOCK)
	INNER JOIN staging.RegistroDetallado red WITH(NOLOCK) ON regId = redRegistroGeneral
	WHERE ISNULL(redTipoCotizante,'') NOT IN (Select tipoCotizante FROM #TablaDependientes WITH(NOLOCK))
	AND regId = @IdRegistroGeneral 
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
	
    SELECT @ErrorMessage = '[dbo].[USP_ValidateHU395CondicionesAportesDependiente]|@IdRegistroGeneral=' + CAST(ISNULL(@IdRegistroGeneral,0) AS VARCHAR(20)) + '|' + ERROR_MESSAGE(),
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
