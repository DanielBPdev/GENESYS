-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2019/02/06
-- Description:	Elimina un rango de revisiones de auditoría para la tabla dada
-- =============================================
CREATE PROCEDURE dbo.USP_AUD_DELETE_AudTableREV
(
	@sTabla VARCHAR(100),
	@iREVIni INT,
	@iREVFin INT
)
AS
	DECLARE @sql NVARCHAR(MAX)

	SET @sql = 'DELETE FROM ' + @sTabla + ' WHERE REV BETWEEN ' + CAST(@iREVIni AS VARCHAR(20)) + ' AND ' + CAST(@iREVFin AS VARCHAR(20))

	EXEC (@sql)
;