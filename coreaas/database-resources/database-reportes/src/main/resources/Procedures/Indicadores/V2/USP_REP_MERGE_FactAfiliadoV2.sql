-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/12/24
-- Description:	Carga la tabla de hechos FactAfiliadoV2 para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactAfiliadoV2
AS

	DECLARE @FechaInicioRevision AS DATETIME
	DECLARE @FechaFinRevision AS DATETIME
	DECLARE @DimPeriodoId AS BIGINT
	DECLARE @DimPeriodoAnteriorId AS BIGINT
	DECLARE @RevisionAuditoriaId BIGINT
		
	SELECT  @FechaInicioRevision =MIN(rar.rarRevisionTimeInicio), @FechaFinRevision =MAX(rar.rarRevisionTimeFin)
	FROM RevisionAuditoriaReportes rar
	WHERE rar.rarEncolaProceso = 1

	SELECT @DimPeriodoId = dipId FROM DimPeriodo WHERE dipMes = MONTH(@FechaFinRevision) AND dipAnio = YEAR(@FechaFinRevision);
	SELECT @DimPeriodoAnteriorId = dipId FROM DimPeriodo WHERE dipMes = MONTH(DATEADD(mm,-1,@FechaFinRevision)) AND dipAnio = YEAR(DATEADD(mm,-1,@FechaFinRevision));			
	
	
	IF @DimPeriodoAnteriorId IS NOT NULL
	BEGIN
		IF NOT EXISTS (SELECT 1 FROM FactAfiliadoV2 WHERE fafDimPeriodo = @DimPeriodoId)
		BEGIN
			WITH cteSolicitudesEnProcesoPeriodoAnterior AS (
				SELECT fafAfiliado,@DimPeriodoId AS fafDimPeriodo,fafActivo,fafCantidadPersonasACargo
				FROM FactAfiliadoV2 fae			
				WHERE fae.fafDimPeriodo = @DimPeriodoAnteriorId
				AND fae.fafActivo = 'true'
			)
			INSERT INTO FactAfiliadoV2 (fafAfiliado,fafActivo,fafDimPeriodo,fafCantidadPersonasACargo)
			SELECT fafAfiliado,fafActivo,fafDimPeriodo,fafCantidadPersonasACargo
			FROM cteSolicitudesEnProcesoPeriodoAnterior
		END
	END;

	WITH cteWaterMarkedRows_roa
	AS
	(
		SELECT wmr.wmrKeyRowValue
		FROM WaterMarkedRows wmr
		WHERE 1=1
		AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		AND  wmr.wmrTable = 'RolAfiliado'
	)

	SELECT DISTINCT wmrKeyRowValue
	INTO #cteWaterMarkedRows_roa
	FROM cteWaterMarkedRows_roa;

	WITH cteWaterMarkedRows_ben
	AS
	(
		SELECT wmr.wmrKeyRowValue
		FROM WaterMarkedRows wmr
		WHERE 1=1
		AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		AND  wmr.wmrTable = 'Beneficiario'
	)

	SELECT DISTINCT wmrKeyRowValue
	INTO #cteWaterMarkedRows_ben
	FROM cteWaterMarkedRows_ben;

	WITH cteAfiliadosModificadosActivos
	AS
	(
		SELECT afi.afiId
		FROM Afiliado afi		
		WHERE 
		EXISTS (SELECT 1
					FROM RolAfiliado roa
					INNER JOIN #cteWaterMarkedRows_roa wmr ON roa.roaId = wmr.wmrKeyRowValue
					WHERE afi.afiId = roa.roaAfiliado)
		AND EXISTS (SELECT 1
					FROM RolAfiliado roa1
					WHERE afi.afiId = roa1.roaAfiliado
					AND roa1.roaEstadoAfiliado = 'ACTIVO')
	),

	cteAfiliadosModificadosInactivos
	AS
	(
		SELECT afi.afiId
		FROM Afiliado afi
		WHERE 
		EXISTS (SELECT 1
					FROM RolAfiliado roa
					INNER JOIN #cteWaterMarkedRows_roa wmr ON roa.roaId = wmr.wmrKeyRowValue
					WHERE afi.afiId = roa.roaAfiliado)
		AND NOT EXISTS (SELECT 1
					FROM RolAfiliado roa1
					WHERE afi.afiId = roa1.roaAfiliado
					AND roa1.roaEstadoAfiliado = 'ACTIVO')	
	),

	cteAfiliadosActivosConBeneficiariosModificados
	AS
	(
		SELECT afiId
		FROM Afiliado afi
		WHERE EXISTS (SELECT 1
					FROM RolAfiliado roa1
					WHERE afi.afiId = roa1.roaAfiliado
					AND roa1.roaEstadoAfiliado = 'ACTIVO')
		AND EXISTS (SELECT 1
					FROM Beneficiario ben
					WHERE benId IN (SELECT wmrKeyRowValue FROM #cteWaterMarkedRows_ben)
					AND ben.benAfiliado = afi.afiId)		
	),

	cteCantidadPersonasACargo
	AS
	(
		
		SELECT	COALESCE(cte1.afiId,cte2.afiId) AS fafAfiliado,
				'true' AS fafActivo,
				@DimPeriodoId AS fafDimPeriodo,
				(SELECT COUNT(1) 
				FROM Beneficiario
				WHERE benTipoBeneficiario <> 'CONYUGE'
				AND benEstadoBeneficiarioAfiliado = 'ACTIVO'
				AND (benAfiliado = cte1.afiId OR benAfiliado = cte2.afiId)) AS fafCantidadPersonasACargo
		FROM cteAfiliadosModificadosActivos cte1
		FULL JOIN cteAfiliadosActivosConBeneficiariosModificados cte2 ON cte1.afiId = cte2.afiId

		UNION ALL

		SELECT	afiId AS fafAfiliado,
				'false' AS fafActivo,
				@DimPeriodoId AS fafDimPeriodo,
				0 AS fafCantidadPersonasACargo
		FROM cteAfiliadosModificadosInactivos
	)

	--select * from cteAfiliadosModificadosInactivos
	--drop table #cteWaterMarkedRows_roa
	--drop table #cteWaterMarkedRows_ben

	MERGE FactAfiliadoV2 AS T
	USING cteCantidadPersonasACargo AS S
	ON (T.fafDimPeriodo = S.fafDimPeriodo AND T.fafAfiliado = S.fafAfiliado)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fafAfiliado,fafDimPeriodo,fafActivo,fafCantidadPersonasACargo)
		VALUES (S.fafAfiliado,S.fafDimPeriodo,S.fafActivo,S.fafCantidadPersonasACargo)
	WHEN MATCHED
		THEN UPDATE SET
			T.fafActivo = S.fafActivo,
			T.fafCantidadPersonasACargo = S.fafCantidadPersonasACargo

;
