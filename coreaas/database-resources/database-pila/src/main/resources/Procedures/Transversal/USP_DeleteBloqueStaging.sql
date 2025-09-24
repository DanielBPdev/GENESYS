-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de eliminar las tablas del esquema Staging  
-- por Id de Transaccion
-- =============================================
CREATE PROCEDURE [dbo].[USP_DeleteBloqueStaging]
       @IdTransaccion Bigint
AS
BEGIN
SET NOCOUNT ON;	
	--DELETE FROM [staging].[RegistroDetallado] WHERE redRegistroGeneral IN (SELECT reg.regId FROM [staging].[RegistroGeneral] reg WHERE reg.[regTransaccion] = @IdTransaccion)
	--DELETE FROM [staging].[RegistroGeneral] WHERE [regTransaccion] = @IdTransaccion

	UPDATE [staging].[RegistroGeneral] 
	SET [regTransaccion] = NULL,
		regDateTimeUpdate = dbo.getLocalDate()
	WHERE [regTransaccion] = @IdTransaccion;

	DELETE FROM [staging].[Aportante] WHERE [apoTransaccion] = @IdTransaccion;
	DELETE FROM [staging].[Cotizante] WHERE [cotTransaccion] = @IdTransaccion;
	DELETE FROM [staging].[AportePeriodo] WHERE [appTransaccion] = @IdTransaccion;
	DELETE FROM [staging].[Novedad] WHERE [novTransaccion] = @IdTransaccion;
	DELETE FROM [staging].[SucursalEmpresa] WHERE [sueTransaccion] = @IdTransaccion;
	DELETE FROM [staging].[NovedadSituacionPrimaria] WHERE [nspTransaccion] = @IdTransaccion;
	DELETE FROM [staging].[BeneficioEmpresaPeriodo] WHERE [bepTransaccion] = @IdTransaccion;
	DELETE FROM [staging].[Transaccion] WHERE [traId] = @IdTransaccion;

	EXEC sp_execute_remote CoreReferenceData, N'DELETE FROM pila.Aporte WHERE appTransaccion = @IdTransaccion',N'@IdTransaccion BIGINT', @IdTransaccion = @IdTransaccion;
END;