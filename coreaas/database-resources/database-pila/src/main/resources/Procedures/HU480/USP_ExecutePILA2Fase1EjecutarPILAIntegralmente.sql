-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de Ejecutar los  
-- pasos de la HU480 
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecutePILA2Fase1EjecutarPILAIntegralmente]
	@IdTransaccion Bigint
AS
BEGIN
SET NOCOUNT ON;	
DECLARE @ErrorMessage NVARCHAR(4000);
DECLARE @ErrorSeverity INT;
DECLARE @ErrorState INT;

BEGIN TRY
	
	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
	
	CREATE TABLE #EstadosArchivoPostFase1 (estado VARCHAR(60))

	INSERT INTO #EstadosArchivoPostFase1 (estado)
	VALUES	('PROCESADO_VS_BD'),
			('REGISTRADO_O_RELACIONADO_LOS_APORTES'),
			('PROCESADO_NOVEDADES'),
			('PROCESADO_SIN_NOVEDADES'),
			('RECAUDO_NOTIFICADO'),
			('RECAUDO_NOTIFICADO_MANUAL'),
			('NOTIFICACION_RECAUDO_FALLIDO')	
	
	SELECT regAf.regId raiRegistroGeneralAfectado, rai.raiRegistroDetalladoAfectado, 
		CASE WHEN reg.regRegistroControl IS NOT NULL THEN 0 ELSE 1 END AS marcaManual, 
		reg.regId modificador
	INTO #redAfectados
	FROM staging.RegistroAfectacionAnalisisIntegral rai with (nolock)
	INNER JOIN staging.RegistroGeneral reg with (nolock) ON rai.raiRegistroGeneral = reg.regId
	INNER JOIN staging.RegistroDetallado red with (nolock) ON rai.raiRegistroDetalladoAfectado = red.redId
	INNER JOIN staging.RegistroGeneral regAf with (nolock) ON red.redRegistroGeneral = regAf.regId
	WHERE reg.regTransaccion = @IdTransaccion
	AND reg.regEsSimulado = 0 --Solo aplica para No simulaciones
	AND red.redOUTEstadoValidacionV0 = 'CUMPLE' AND redOUTEstadoValidacionV2 IN ('OK', 'NO_APLICA')
	AND red.redOUTEstadoValidacionV3 = 'NO_OK'

	
	--print 'Actualiza estado registrodetalle 480'

	UPDATE red 
	SET
		redDateTimeUpdate = @redDateTimeUpdate,
		redOUTEstadoValidacionV3 = 'OK', redOUTEstadoRegistroAporte = 'OK', redOUTAnalisisIntegral = 1
	FROM #redAfectados rai with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON rai.raiRegistroDetalladoAfectado = red.redId

	--print 'Registra temporal de actualizaci�n de aporte en Core'

	INSERT INTO dbo.TemAporteActualizado (taaRegistroDetallado, taaEstadoRegistroAporte, taaMarcaAporteManual, taaRegistroModificador)
	SELECT rai.raiRegistroDetalladoAfectado, 'OK', rai.marcaManual, rai.modificador
	FROM #redAfectados rai with (nolock)
	INNER JOIN staging.RegistroDetallado red ON rai.raiRegistroDetalladoAfectado = red.redId
	LEFT JOIN dbo.TemAporteActualizado taa ON red.redId = taa.taaRegistroDetallado
	WHERE taa.taaId IS NULL

	--print 'Actualiza estado registrogeneral 480'

	UPDATE reg SET regOUTEstadoArchivo = 'PROCESADO_VS_BD', regTransaccion = @IdTransaccion,regDateTimeUpdate = dbo.getLocalDate()
	FROM staging.RegistroGeneral reg with (nolock)
	INNER JOIN #redAfectados rai with (nolock) ON reg.regId = rai.raiRegistroGeneralAfectado
	WHERE NOT EXISTS (
					SELECT TOP 1 1 
					FROM staging.RegistroDetallado red with (nolock)
					WHERE (ISNULL(redOUTEstadoRegistroAporte, 'NO_OK') = 'NO_OK' OR ISNULL(redOUTMarcaValRegistroAporte,'NO_VALIDADO_BD') = 'NO_VALIDADO_BD')
					AND reg.regId = red.redRegistroGeneral
					)
	AND reg.regOUTEstadoArchivo NOT IN (SELECT estado FROM #EstadosArchivoPostFase1 with (nolock))
END TRY
BEGIN CATCH

    SELECT @ErrorMessage = '[dbo].[USP_ExecutePILA2Fase1EjecutarPILAIntegralmente]|@IdTransaccion=' + CAST(ISNULL(@IdTransaccion,0) AS VARCHAR(20)) + '|' + ERROR_MESSAGE(),
           @ErrorSeverity = ERROR_SEVERITY(),
           @ErrorState = ERROR_STATE();

	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.GetLocalDate(), '@IdTransaccion=' + CAST(@IdTransaccion AS VARCHAR(20)), @ErrorMessage);

    RAISERROR (@ErrorMessage, -- Message text.
               @ErrorSeverity, -- Severity.
               @ErrorState -- State.
               );  
END CATCH;
END;