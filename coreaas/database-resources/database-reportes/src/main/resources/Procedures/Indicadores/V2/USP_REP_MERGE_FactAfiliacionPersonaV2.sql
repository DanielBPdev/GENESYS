-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/12/24
-- Description:	Carga la tabla de hechos FactAfiliacionPersonaV2 para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactAfiliacionPersonaV2
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
		IF NOT EXISTS (SELECT 1 FROM FactAfiliacionPersonaV2 WHERE fapDimPeriodo = @DimPeriodoId)
		BEGIN
			WITH cteSolicitudesEnProcesoPeriodoAnterior AS (
				SELECT fapAfiliado,fapDimTipoAfiliado,@DimPeriodoId AS fapDimPeriodo,fapActivo,'true' AS fapActivoPeriodoAnterior,fapDimSede,fapDimCanal,fapDimTipoTransaccion
				FROM FactAfiliacionPersonaV2 fae			
				WHERE fae.fapDimPeriodo = @DimPeriodoAnteriorId
				AND fae.fapActivo = 'true'
			)
			INSERT INTO FactAfiliacionPersonaV2 (fapAfiliado,fapDimTipoAfiliado,fapDimPeriodo,fapActivo,fapActivoPeriodoAnterior,fapDimSede,fapDimCanal,fapDimTipoTransaccion)
			SELECT fapAfiliado,fapDimTipoAfiliado,fapDimPeriodo,fapActivo,fapActivoPeriodoAnterior,fapDimSede,fapDimCanal,fapDimTipoTransaccion
			FROM cteSolicitudesEnProcesoPeriodoAnterior
		END
	END;

	WITH cteWaterMarkedRows
	AS
	(
		SELECT wmr.wmrKeyRowValue
		FROM WaterMarkedRows wmr
		WHERE 1=1
		AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		AND  wmr.wmrTable = 'RolAfiliado'
	)

	SELECT *
	INTO #cteWaterMarkedRows
	FROM cteWaterMarkedRows;


	WITH cteDimCanal
	AS 
	(
		SELECT solCanalRecepcion, fapDimCanal 
		FROM (
			VALUES
				('PRESENCIAL', 1),
				('WEB', 2),
				('PILA', 3)
			) AS T (solCanalRecepcion, fapDimCanal)
	),
	cteDimTipoAfiliado
	AS 
	(
		SELECT roaTipoAfiliado, fapDimTipoAfiliado 
		FROM (
			VALUES
				('TRABAJADOR_DEPENDIENTE', 1),
				('TRABAJADOR_INDEPENDIENTE', 2),
				('PENSIONADO', 3)
			) AS T (roaTipoAfiliado, fapDimTipoAfiliado)
	),

	cteEventosAfiliaciones
	AS
	(
		--Afiliados que cambiaron su estado a inactivo que no tengan registros activos
		SELECT roaAfiliado,roaTipoAfiliado,MAX(roaId) AS roaId				
		FROM #cteWaterMarkedRows wmr
		INNER JOIN RolAfiliado roa ON wmr.wmrKeyRowValue = roa.roaId
		WHERE roaEstadoAfiliado = 'INACTIVO'
		AND NOT EXISTS (
			SELECT 1
			FROM RolAfiliado roa1
			WHERE roa1.roaAfiliado = roa.roaAfiliado
			AND roa1.roaTipoAfiliado = roa.roaTipoAfiliado
			AND roaEstadoAfiliado = 'ACTIVO'
		)
		GROUP BY roaAfiliado,roaTipoAfiliado
		
		UNION ALL

		SELECT roaAfiliado,roaTipoAfiliado,MAX(roaId) AS roaId				
		FROM #cteWaterMarkedRows wmr
		INNER JOIN RolAfiliado roa ON wmr.wmrKeyRowValue = roa.roaId
		WHERE roaEstadoAfiliado = 'ACTIVO'
		GROUP BY roaAfiliado,roaTipoAfiliado
	),

	cteInformacionComplementaria
	AS
	(
		SELECT 
			cte.roaAfiliado,
			cte.roaTipoAfiliado,
			roa.roaEstadoAfiliado,
			roa.roaCanalReingreso,
			sol.solCanalRecepcion,
			sol.solSedeDestinatario
		FROM cteEventosAfiliaciones cte
		INNER JOIN (SELECT MAX(sapId) AS sapId, sapRolAfiliado
					FROM SolicitudAfiliacionPersona sap
					WHERE sapEstadoSolicitud='CERRADA'
					GROUP BY sapRolAfiliado
					) sapU ON cte.roaId = sapU.sapRolAfiliado
		INNER JOIN SolicitudAfiliacionPersona sap ON sapU.sapId = sap.sapId
		INNER JOIN Solicitud sol ON sap.sapSolicitudGlobal = sol.solId
		INNER JOIN RolAfiliado roa ON cte.roaId = roa.roaId
		INNER JOIN Afiliado afi ON roa.roaAfiliado = afi.afiId

	),

	cteAttachDimension
	AS
	(
		SELECT 
			S.roaAfiliado AS fapAfiliado,
			dta.fapDimTipoAfiliado,
			@DimPeriodoId AS fapDimPeriodo,
			S.solSedeDestinatario AS fapDimSede,
			ISNULL(dicR.fapDimCanal, dicI.fapDimCanal) AS fapDimCanal,
			CASE WHEN dicR.fapDimCanal IS NULL THEN 1 ELSE 2 END AS fapDimTipoTransaccion,
			CASE WHEN S.roaEstadoAfiliado = 'ACTIVO' THEN 'true' ELSE 'false' END AS fapActivo
		FROM cteInformacionComplementaria S	
		INNER JOIN cteDimCanal dicI ON dicI.solCanalRecepcion = S.solCanalRecepcion
		INNER JOIN cteDimTipoAfiliado dta ON dta.roaTipoAfiliado = S.roaTipoAfiliado
		LEFT JOIN cteDimCanal dicR ON dicR.solCanalRecepcion = S.roaCanalReingreso
	)
	
	MERGE FactAfiliacionPersonaV2 AS T
	USING cteAttachDimension AS S
	ON (T.fapDimPeriodo = S.fapDimPeriodo AND T.fapAfiliado = S.fapAfiliado AND T.fapDimTipoAfiliado = S.fapDimTipoAfiliado)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fapAfiliado,fapDimTipoAfiliado,fapDimPeriodo,fapActivo,fapActivoPeriodoAnterior,fapDimSede,fapDimCanal,fapDimTipoTransaccion)
		VALUES (S.fapAfiliado,S.fapDimTipoAfiliado,S.fapDimPeriodo,S.fapActivo,'false',S.fapDimSede,S.fapDimCanal,S.fapDimTipoTransaccion)
	WHEN MATCHED
		THEN UPDATE SET 
			T.fapDimTipoTransaccion = S.fapDimTipoTransaccion,
			T.fapDimCanal = S.fapDimCanal,
			T.fapActivo = S.fapActivo

;
