-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/12/17
-- Description:	Carga la tabla de hechos FactAfiliacionEmpleadorV2 para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactAfiliacionEmpleadorV2
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
		IF NOT EXISTS (SELECT 1 FROM FactAfiliacionEmpleadorV2 WHERE faeDimPeriodo = @DimPeriodoId)
		BEGIN
			WITH cteEmpleadoresActivosPeriodoAnterior AS (
				SELECT faeEmpleador,@DimPeriodoId AS faeDimPeriodo,faeActivo,'true' AS faeActivoPeriodoAnterior,faeFechaActivo,NULL AS faeFechaInactivo,faeDimTipoTransaccion,faeDimSede,faeDimCanal
				FROM FactAfiliacionEmpleadorV2 fae			
				WHERE fae.faeDimPeriodo = @DimPeriodoAnteriorId
				AND fae.faeActivo = 'true'
			)
			INSERT INTO FactAfiliacionEmpleadorV2 (faeEmpleador,faeDimPeriodo,faeActivo,faeActivoPeriodoAnterior,faeFechaActivo,faeFechaInactivo,faeDimTipoTransaccion,faeDimSede,faeDimCanal)
			SELECT faeEmpleador,faeDimPeriodo,faeActivo,faeActivoPeriodoAnterior,faeFechaActivo,faeFechaInactivo,faeDimTipoTransaccion,faeDimSede,faeDimCanal
			FROM cteEmpleadoresActivosPeriodoAnterior
		END
	END;
	
	WITH cteWaterMarkedRows
	AS
	(
		SELECT wmr.wmrKeyRowValue, wmr.wmrFirstRevisionDateTime, wmr.wmrLastRevisionDateTime, wmr.wmrJsonNewValue
		FROM WaterMarkedRows wmr
		WHERE 1=1
		AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		AND  wmr.wmrTable = 'Empleador'
	)

	SELECT *
	INTO #cteWaterMarkedRows
	FROM cteWaterMarkedRows

	CREATE TABLE #wmrJSONUnified
	(
		wmrKeyRowValue BIGINT NOT NULL,
		wmrJsonValue NVARCHAR(MAX)
	)
	
	INSERT INTO #wmrJSONUnified (wmrKeyRowValue, wmrJsonValue)
	SELECT cte.wmrKeyRowValue, cte.wmrJsonNewValue 
	FROM #cteWaterMarkedRows cte
	INNER JOIN (SELECT wmrKeyRowValue
				FROM #cteWaterMarkedRows
				GROUP BY wmrKeyRowValue
				HAVING COUNT(1)=1) cte1 ON cte.wmrKeyRowValue = cte1.wmrKeyRowValue
							  
	DECLARE @wmrCursor AS CURSOR
	DECLARE	@wmrKeyRowValue BIGINT

	SET @wmrCursor = CURSOR FAST_FORWARD FOR
		SELECT wmrKeyRowValue
		FROM #cteWaterMarkedRows
		GROUP BY wmrKeyRowValue
		HAVING COUNT(1)>1
	
	OPEN @wmrCursor
	FETCH NEXT FROM @wmrCursor INTO
	@wmrKeyRowValue
	
	WHILE @@FETCH_STATUS = 0
	BEGIN

		DECLARE @sJsonValueFull NVARCHAR(MAX)

		SELECT @sJsonValueFull = COALESCE(@sJsonValueFull,'') + ISNULL(wmr.wmrJsonNewValue,'')
		FROM #cteWaterMarkedRows wmr
		WHERE wmr.wmrKeyRowValue = @wmrKeyRowValue

		SET @sJsonValueFull = REPLACE (@sJsonValueFull,'][',',')

		INSERT INTO #wmrJSONUnified (wmrKeyRowValue, wmrJsonValue) VALUES (@wmrKeyRowValue, @sJsonValueFull)
	
		FETCH NEXT FROM @wmrCursor INTO
		@wmrKeyRowValue
	END
	CLOSE @wmrCursor;
	DEALLOCATE @wmrCursor;

	--DROP TABLE #wmrJSONUnified
	DROP TABLE #cteWaterMarkedRows;

	WITH cteDimCanal
	AS 
	(
		SELECT	canal, faeDimCanal 
		FROM (
			VALUES
				('PRESENCIAL', 1),
				('WEB', 2),
				('PILA', 3)
			) AS T (canal, faeDimCanal)
	),

	cteSolicitudAfiliacion AS
	(
		SELECT
			sae.saeEmpleador,
			sol.solCanalRecepcion,
			sol.solSedeDestinatario
		FROM Solicitud sol
		INNER JOIN SolicitudAfiliaciEmpleador sae ON sol.solId = sae.saeSolicitudGlobal		
		INNER JOIN (
			SELECT 
				sae.saeEmpleador, MAX(sae.saeId) saeId
			FROM SolicitudAfiliaciEmpleador sae
			GROUP BY sae.saeEmpleador
		) sae2 ON sae2.saeId = sae.saeId
	),

	cteEventosEmpleador
	AS
	(
		SELECT	wmr.wmrKeyRowValue AS empId,			
			csa.solCanalRecepcion,
			empl.empCanalReingreso,
			emp.empCreadoPorPila,
			csa.solSedeDestinatario,
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.empEstadoEmpleador') = 'ACTIVO'
			) AS fechaActivo,
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.empEstadoEmpleador') = 'INACTIVO'
			) AS fechaInactivo,
			empl.empEstadoEmpleador
		FROM #wmrJSONUnified wmr
		INNER JOIN Empleador empl ON wmr.wmrKeyRowValue = empl.empId
		INNER JOIN Empresa emp ON empl.empEmpresa = emp.empId
		LEFT JOIN cteSolicitudAfiliacion csa ON empl.empId = csa.saeEmpleador		
	),

	cteAccionesEmpleador
	AS
	(
		SELECT empId, solCanalRecepcion, empCanalReingreso, empCreadoPorPila, solSedeDestinatario, fechaActivo, fechaInactivo, empEstadoEmpleador,
			CASE WHEN S.fechaActivo BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionActivo,
			CASE WHEN S.fechaInactivo BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionInactivo
		FROM cteEventosEmpleador S
	),

	cteAttachDimension
	AS
	(
		SELECT 
			S.empId AS faeEmpleador,
			@DimPeriodoId AS faeDimPeriodo,
			S.fechaActivo AS faeFechaActivo,
			S.fechaInactivo AS faeFechaInactivo,
			CASE WHEN S.empEstadoEmpleador = 'ACTIVO' THEN 'true' ELSE 'false' END AS faeActivo,
			ISNULL(dicR.faeDimCanal, dicI.faeDimCanal) AS faeDimCanal,
			CASE WHEN dicR.faeDimCanal IS NULL THEN 1 ELSE 2 END AS faeDimTipoTransaccion,
			S.solSedeDestinatario AS faeDimSede
		FROM cteAccionesEmpleador S	
		LEFT JOIN cteDimCanal dicI ON dicI.canal = CASE WHEN S.empCreadoPorPila = 'true' THEN 'PILA' ELSE S.solCanalRecepcion END
		LEFT JOIN cteDimCanal dicR ON dicR.canal = S.empCanalReingreso
		WHERE (
				S.accionActivo = 'true' OR
				S.accionInactivo = 'true' 
			)
	)

	--DROP TABLE #wmrJSONUnified
	--DROP TABLE #cteWaterMarkedRows;
	--SELECT * FROM cteAttachDimension

	MERGE FactAfiliacionEmpleadorV2 AS T
	USING cteAttachDimension AS S
	ON (T.faeDimPeriodo = S.faeDimPeriodo AND T.faeEmpleador = S.faeEmpleador)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (faeEmpleador,faeDimPeriodo,faeActivo,faeActivoPeriodoAnterior,faeFechaActivo,faeFechaInactivo,faeDimTipoTransaccion,faeDimSede,faeDimCanal)
		VALUES (S.faeEmpleador,S.faeDimPeriodo,S.faeActivo,'false',S.faeFechaActivo,S.faeFechaInactivo,S.faeDimTipoTransaccion,S.faeDimSede,S.faeDimCanal)
	WHEN MATCHED
		THEN UPDATE SET 
			T.faeActivo = S.faeActivo,
			T.faeFechaActivo = CASE WHEN T.faeFechaActivo IS NULL THEN S.faeFechaActivo ELSE T.faeFechaActivo END,
			T.faeFechaInactivo = CASE WHEN T.faeFechaInactivo IS NULL THEN S.faeFechaInactivo ELSE T.faeFechaInactivo END,
			T.faeDimTipoTransaccion = CASE WHEN S.faeActivo = 'true' THEN S.faeDimTipoTransaccion ELSE T.faeDimTipoTransaccion END,
			T.faeDimCanal = S.faeDimCanal
;
