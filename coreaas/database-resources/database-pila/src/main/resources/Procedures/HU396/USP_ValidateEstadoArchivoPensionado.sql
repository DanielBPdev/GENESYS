-- =============================================
-- Author:		Mauricio Sanchez Castro
-- Create date: 2017/06/01
-- Description:	Procedimiento almacenado encargado de calcular el estado del archivo PILA de Pensionados, después de 
-- haber pasado por todos los procesos de validacion de la fase 1 de pila.
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ValidateEstadoArchivoPensionado]
@IdRegistroGeneral Bigint 
AS

BEGIN
SET NOCOUNT ON;
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	IF((SELECT COUNT(1) FROM staging.RegistroGeneral WITH (NOLOCK)
	INNER JOIN staging.RegistroDetallado WITH (NOLOCK) ON staging.RegistroGeneral.regId = staging.RegistroDetallado.redRegistroGeneral
	WHERE regEsAportePensionados = 'True'
	AND redRegistroGeneral = @IdRegistroGeneral 
	AND (redOUTEstadoRegistroAporte = 'NO_OK' OR redOUTEstadoRegistroAporte = 'NO_VALIDADO_BD' )) > 0)
	BEGIN
		UPDATE staging.RegistroGeneral SET regOUTEstadoArchivo = 'PROCESADO_VS_BD',
											regDateTimeUpdate = dbo.getLocalDate()
		WHERE regId = @IdRegistroGeneral
		AND regEsSimulado = 0 --Actualiza el estado únicamente si no es una simulación
	END
	ELSE
	BEGIN
		UPDATE staging.RegistroGeneral SET regOUTEstadoArchivo = 'PROCESADO_VS_BD',
		regDateTimeUpdate = dbo.getLocalDate()
		WHERE regId = @IdRegistroGeneral
		AND regEsSimulado = 0 --Actualiza el estado únicamente si no es una simulación
	END
END;