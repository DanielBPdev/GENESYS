-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/08/14
-- Description:	Procedimiento almacenado encargado actualizar 
-- el estado del aporte en Core con la nueva evaluación realizada
-- =============================================
CREATE PROCEDURE [dbo].[USP_ActualizarAporte]
	@iIdRegistroDetallado BIGINT, 
	@sNuevoEstadoAporte VARCHAR (60)
AS

BEGIN
	print 'Inicia USP_ActualizarAporte'

	UPDATE dbo.AporteDetallado
	SET apdEstadoRegistroAporteArchivo = @sNuevoEstadoAporte
	WHERE apdRegistroDetallado = @iIdRegistroDetallado

	print 'Finaliza USP_ActualizarAporte'
END;