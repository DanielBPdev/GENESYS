-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/12/03
-- Description:	Realiza Merge de la Dimension de Ciclo de Asignacion
-- =============================================
CREATE PROCEDURE USP_REP_INSERT_DimCicloAsignacionV2
AS

	MERGE DimCicloAsignacionV2 AS T
	USING CicloAsignacion AS S
	ON (T.dciId = S.ciaId)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT(dciId, dciDescripcion, dciAnio) 
		VALUES(S.ciaId, S.ciaNombre, YEAR(S.ciaFechaInicio))
	WHEN MATCHED
		THEN UPDATE SET
			dciDescripcion = S.ciaNombre, dciAnio = YEAR(S.ciaFechaInicio);
;