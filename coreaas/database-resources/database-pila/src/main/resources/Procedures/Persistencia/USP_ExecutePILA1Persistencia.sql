-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2021/03/10
-- Description:	Procedimiento almacenado que ejecuta las sentencias INSERT
-- almacenadas en la tabla dbo.PilaPersistenciaClausulasInsert para un 
-- IndicePlanilla dado
-- =============================================
CREATE PROCEDURE USP_ExecutePILA1Persistencia (
	@IndicePlanilla BIGINT
)
AS

	DECLARE @SQLFull VARCHAR(MAX), @NSQLFull NVARCHAR(MAX)

	SELECT @SQLFull = COALESCE(@SQLFull + '
	', '') + ppcInsertClause
	FROM dbo.PilaPersistenciaClausulasInsert
	
	SET @NSQLFull = CAST(@SQLFull AS NVARCHAR(MAX))

	EXECUTE sp_executesql @NSQLFull, N'@IndicePlanilla BIGINT', @IndicePlanilla = @IndicePlanilla;

	EXECUTE USP_EliminarPreliminarArchivoPila @IndicePlanilla
;