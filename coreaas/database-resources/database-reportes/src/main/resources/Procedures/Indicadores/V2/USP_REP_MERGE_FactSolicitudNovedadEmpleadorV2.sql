-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/12/27
-- Description:	Carga la tabla de hechos FactSolicitudNovedadEmpleadorV2 para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactSolicitudNovedadEmpleadorV2
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
	
	WITH cteWaterMarkedRows_sne
	AS
	(
		SELECT wmr.wmrKeyRowValue
		FROM WaterMarkedRows wmr
		WHERE 1=1
		AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		AND  wmr.wmrTable = 'SolicitudNovedadEmpleador'
	)

	SELECT DISTINCT wmrKeyRowValue
	INTO #cteWaterMarkedRows_sne
	FROM cteWaterMarkedRows_sne;


	WITH cteWaterMarkedRows
	AS
	(
		SELECT wmr.wmrKeyRowValue, wmr.wmrFirstRevisionDateTime, wmr.wmrLastRevisionDateTime, wmr.wmrJsonNewValue
		FROM WaterMarkedRows wmr
		WHERE 1=1
		AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		AND  wmr.wmrTable = 'SolicitudNovedad'
	)

	SELECT *
	INTO #cteWaterMarkedRows_sno
	FROM cteWaterMarkedRows

	CREATE TABLE #wmrJSONUnified_sno
	(
		wmrKeyRowValue BIGINT NOT NULL,
		wmrJsonValue NVARCHAR(MAX)
	)
	
	INSERT INTO #wmrJSONUnified_sno (wmrKeyRowValue, wmrJsonValue)
	SELECT cte.wmrKeyRowValue, cte.wmrJsonNewValue 
	FROM #cteWaterMarkedRows_sno cte
	INNER JOIN (SELECT wmrKeyRowValue
				FROM #cteWaterMarkedRows_sno
				GROUP BY wmrKeyRowValue
				HAVING COUNT(1)=1) cte1 ON cte.wmrKeyRowValue = cte1.wmrKeyRowValue
							  
	DECLARE @wmrCursor AS CURSOR
	DECLARE	@wmrKeyRowValue BIGINT

	SET @wmrCursor = CURSOR FAST_FORWARD FOR
		SELECT wmrKeyRowValue
		FROM #cteWaterMarkedRows_sno
		GROUP BY wmrKeyRowValue
		HAVING COUNT(1)>1
	
	OPEN @wmrCursor
	FETCH NEXT FROM @wmrCursor INTO
	@wmrKeyRowValue
	
	WHILE @@FETCH_STATUS = 0
	BEGIN

		DECLARE @sJsonValueFull NVARCHAR(MAX)

		SELECT @sJsonValueFull = COALESCE(@sJsonValueFull,'') + ISNULL(wmr.wmrJsonNewValue,'')
		FROM #cteWaterMarkedRows_sno wmr
		WHERE wmr.wmrKeyRowValue = @wmrKeyRowValue

		SET @sJsonValueFull = REPLACE (@sJsonValueFull,'][',',')

		INSERT INTO #wmrJSONUnified_sno (wmrKeyRowValue, wmrJsonValue) VALUES (@wmrKeyRowValue, @sJsonValueFull)
	
		FETCH NEXT FROM @wmrCursor INTO
		@wmrKeyRowValue
	END
	CLOSE @wmrCursor;
	DEALLOCATE @wmrCursor;

	--DROP TABLE #wmrJSONUnified_sno
	DROP TABLE #cteWaterMarkedRows_sno;

	WITH cteDimCanal
	AS 
	(
		SELECT	canal, fneDimCanal 
		FROM (
			VALUES
				('PRESENCIAL', 1),
				('WEB', 2),
				('PILA', 3)
			) AS T (canal, fneDimCanal)
	),

	cteEventosNovedadesRadicadas AS
	(
		SELECT	wmr.wmrKeyRowValue AS sneId,			
			sne.sneIdEmpleador,
			sol.solSedeDestinatario,
			sol.solCanalRecepcion,
			sol.solFechaRadicacion,
			nov.novTipoNovedad
		FROM #cteWaterMarkedRows_sne wmr
		INNER JOIN SolicitudNovedadEmpleador sne ON wmr.wmrKeyRowValue = sne.sneId
		INNER JOIN SolicitudNovedad sno ON sne.sneIdSolicitudNovedad = sno.snoId
		INNER JOIN Solicitud sol ON sno.snoSolicitudGlobal = sol.solId
		INNER JOIN ParametrizacionNovedad nov ON sno.snoNovedad = nov.novId
		INNER JOIN Empleador emp ON sne.sneIdEmpleador = emp.empId
	),

	cteEventosNovedadesCerradas AS
	(
		SELECT	sne.sneId,			
			sne.sneIdEmpleador,
			sol.solSedeDestinatario,
			sol.solCanalRecepcion,
			sol.solFechaRadicacion,
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.snoEstadoSolicitud') = 'CERRADA'
			) AS fechaSolicitudCerrada,
			nov.novTipoNovedad
		FROM #wmrJSONUnified_sno wmr
		INNER JOIN SolicitudNovedad sno ON  wmr.wmrKeyRowValue = sno.snoId
		INNER JOIN SolicitudNovedadEmpleador sne ON sno.snoId = sne.sneIdSolicitudNovedad
		INNER JOIN Solicitud sol ON sno.snoSolicitudGlobal = sol.solId
		INNER JOIN ParametrizacionNovedad nov ON sno.snoNovedad = nov.novId
		INNER JOIN Empleador emp ON sne.sneIdEmpleador = emp.empId
	),

	cteNovedadesUnion
	AS 
	(
		SELECT sneId, sneIdEmpleador, solSedeDestinatario, solCanalRecepcion, solFechaRadicacion, NULL AS fechaSolicitudCerrada, novTipoNovedad
		FROM cteEventosNovedadesRadicadas
		WHERE sneId NOT IN (SELECT sneId FROM cteEventosNovedadesCerradas)

		UNION ALL

		SELECT sneId, sneIdEmpleador, solSedeDestinatario, solCanalRecepcion, solFechaRadicacion, fechaSolicitudCerrada, novTipoNovedad
		FROM cteEventosNovedadesCerradas
	),

	cteAccionesNovedades
	AS
	(
		SELECT sneId, sneIdEmpleador, solSedeDestinatario, solCanalRecepcion, solFechaRadicacion, fechaSolicitudCerrada, novTipoNovedad,
			CASE WHEN S.solFechaRadicacion BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionRadicacion,
			CASE WHEN S.fechaSolicitudCerrada BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudCerrada
		FROM cteNovedadesUnion S
	),

	cteAttachDimension
	AS
	(
		SELECT 
			S.sneId AS fneSolicitudNovedadEmpleador,
			S.sneIdEmpleador AS fneEmpleador,
			@DimPeriodoId AS fneDimPeriodo,
			S.solFechaRadicacion AS fneFechaRadicacion,
			S.fechaSolicitudCerrada AS fneFechaSolicitudCerrada,
			S.accionRadicacion AS fneAccionRadicacion,
			S.accionSolicitudCerrada AS fneAccionSolicitudCerrada,
			dic.fneDimCanal,
			S.solSedeDestinatario AS fneDimSede,
			CASE WHEN S.novTipoNovedad = 'AUTOMATICA' THEN 'true' ELSE 'false' END AS fneEsAutomatica
		FROM cteAccionesNovedades S	
		INNER JOIN cteDimCanal dic ON dic.canal = S.solCanalRecepcion
		WHERE (
				S.accionRadicacion = 'true' OR
				S.accionSolicitudCerrada = 'true' 
			)
	)

	--DROP TABLE #wmrJSONUnified_sno
	--DROP TABLE #cteWaterMarkedRows_sne;
	--SELECT * FROM cteAttachDimension

	MERGE FactSolicitudNovedadEmpleadorV2 AS T
	USING cteAttachDimension AS S
	ON (T.fneDimPeriodo = S.fneDimPeriodo AND T.fneSolicitudNovedadEmpleador = S.fneSolicitudNovedadEmpleador)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fneSolicitudNovedadEmpleador,fneEmpleador,fneDimPeriodo,fneAccionRadicacion,fneAccionSolicitudCerrada,fneFechaRadicacion,fneFechaSolicitudCerrada,fneEsAutomatica,fneDimSede,fneDimCanal)
		VALUES (S.fneSolicitudNovedadEmpleador,S.fneEmpleador,S.fneDimPeriodo,S.fneAccionRadicacion,S.fneAccionSolicitudCerrada,S.fneFechaRadicacion,S.fneFechaSolicitudCerrada,S.fneEsAutomatica,S.fneDimSede,S.fneDimCanal)
	WHEN MATCHED
		THEN UPDATE SET
			T.fneFechaSolicitudCerrada = CASE WHEN T.fneFechaSolicitudCerrada IS NULL THEN S.fneFechaSolicitudCerrada ELSE T.fneFechaSolicitudCerrada END,
			T.fneAccionSolicitudCerrada = CASE WHEN T.fneAccionSolicitudCerrada = 'false' THEN S.fneAccionSolicitudCerrada ELSE T.fneAccionSolicitudCerrada END
;