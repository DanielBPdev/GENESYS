-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2021/03/10
-- Description:	Procedimiento almacenado que elimina
-- la información preliminar a la persistencia
-- =============================================
CREATE PROCEDURE USP_EliminarPreliminarArchivoPila (
	@IndicePlanilla BIGINT
)
AS
	DELETE 
	FROM [staging].[PreliminarArchivoPila]
	WHERE papIndicePlanilla = @IndicePlanilla
;