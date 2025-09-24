-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2019/02/06
-- Description:	Elimina un rango de revisiones de RevisionEntidad y Revision
-- =============================================
CREATE PROCEDURE dbo.USP_AUD_DELETE_Revision
(
	@iREVIni INT,
	@iREVFin INT
)
AS
	DELETE ree
	FROM RevisionEntidad ree
	INNER JOIN Revision rev ON ree.reeRevision = rev.revId
	WHERE rev.revId BETWEEN @iREVIni AND @iREVFin

	
	DELETE rev
	FROM Revision rev
	WHERE rev.revId BETWEEN @iREVIni AND @iREVFin
;