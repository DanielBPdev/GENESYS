-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado de calcular el estado del archivo PILA de Dependientes, despues de 
-- haber pasado por todos los procesos de validacion de la fase 1 de pila.
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ValidateEstadoArchivoDependientes]
	@IdRegistroGeneral Bigint
AS
BEGIN
SET NOCOUNT ON;
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED

	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
	
	UPDATE staging.RegistroDetallado
	SET
	redDateTimeUpdate = @redDateTimeUpdate 
	,redOUTEstadoRegistroAporte = case when (isnull(redOUTEstadoValidacionV0,'') = 'CUMPLE' AND isnull(redOUTEstadoValidacionV1,'') = 'OK' AND ISNULL(redOUTEstadoValidacionV2, '') = 'OK' AND isnull(redOUTEstadoValidacionV3,'')='OK') then 'OK'
										when (isnull(redOUTEstadoValidacionV0,'') != 'CUMPLE' AND isnull(redOUTEstadoValidacionV1,'') = 'OK' AND ISNULL(redOUTEstadoValidacionV2, '') = 'OK' AND isnull(redOUTEstadoValidacionV3,'')='OK') then 'NO_OK_APROBADO'
										when (isnull(redOUTEstadoValidacionV2, '') = 'NO_VALIDADO_BD') then 'NO_VALIDADO_BD_APROBADO'
										else 'NO_OK_APROBADO'
										end
	WHERE redOUTMarcaValRegistroAporte = 'VALIDAR_COMO_DEPENDIENTE'
	AND redRegistroGeneral = @IdRegistroGeneral
	AND ISNULL(redOUTEstadoRegistroAporte,'NO_OK') = 'NO_OK'
END;