-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de obtener la información
-- detallada de cotizantes inactivos para el proceso de Notificaciones HU400
-- =============================================
CREATE PROCEDURE USP_GetNotificacionesRegistroDetallado
(
	@IdRegistroGeneral BIGINT
)

AS

BEGIN
SET NOCOUNT ON;

SELECT	red.redTipoIdentificacionCotizante AS TipoIdentificacionCotizante,
		red.redNumeroIdentificacionCotizante AS NumeroIdentificacionCotizante,
		redPrimerApellido AS PrimerApellid,
		redSegundoApellido AS SegundoApellido,
		redPrimerNombre AS PrimerNombre,
		redSegundoNombre AS SegundoNombre
FROM staging.RegistroDetallado red with (nolock)
WHERE redOUTEstadoSolicitante <> 'ACTIVO'
AND redRegistroGeneral = @IdRegistroGeneral;

END;