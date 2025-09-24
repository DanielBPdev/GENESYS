-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/05/29
-- Description:	Realiza Merge de la Dimension de Sede
-- =============================================
CREATE PROCEDURE USP_REP_INSERT_DimSede
AS

	MERGE DimSede AS T
	USING SedeCajaCompensacion AS S
	ON (T.disId = S.sccfId)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT(disId, disDescripcion) 
		VALUES(S.sccfId, S.sccfNombre)
	WHEN MATCHED
		THEN UPDATE SET
			disDescripcion = S.sccfNombre;
;