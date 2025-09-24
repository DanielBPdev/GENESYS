-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/12/20
-- Description:	Carga la tabla de hechos FactSolicitudAfiliacionPersonaV2 para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactSolicitudAfiliacionPersonaV2
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
		IF NOT EXISTS (SELECT 1 FROM FactSolicitudAfiliacionPersonaV2 WHERE fspDimPeriodo = @DimPeriodoId)
		BEGIN
			WITH cteSolicitudesEnProcesoPeriodoAnterior AS (
				SELECT fspSolicitudAfiliacionPersona,@DimPeriodoId AS fspDimPeriodo,'false' AS fspAccionSolicitudRadicada,'false' AS fspAccionSolicitudAprobada,'false' AS fspAccionSolicitudRechazada,'false' AS fspAccionSolicitudCerrada,fspFechaRadicacion,NULL AS fspFechaSolicitudCerrada,0 AS fspDiasProcesamientoAfiliacion,fspDimSede,fspDimCanal,fspDimTipoTransaccion				
				FROM FactSolicitudAfiliacionPersonaV2 fae			
				WHERE fae.fspDimPeriodo = @DimPeriodoAnteriorId
				AND fae.fspAccionSolicitudCerrada = 'false'
			)
			INSERT INTO FactSolicitudAfiliacionPersonaV2 (fspSolicitudAfiliacionPersona,fspDimPeriodo,fspAccionSolicitudRadicada,fspAccionSolicitudAprobada,fspAccionSolicitudRechazada,fspAccionSolicitudCerrada,fspFechaRadicacion,fspFechaSolicitudCerrada,fspDiasProcesamientoAfiliacion,fspDimSede,fspDimCanal,fspDimTipoTransaccion)
			SELECT fspSolicitudAfiliacionPersona,fspDimPeriodo,fspAccionSolicitudRadicada,fspAccionSolicitudAprobada,fspAccionSolicitudRechazada,fspAccionSolicitudCerrada,fspFechaRadicacion,fspFechaSolicitudCerrada,fspDiasProcesamientoAfiliacion,fspDimSede,fspDimCanal,fspDimTipoTransaccion
			FROM cteSolicitudesEnProcesoPeriodoAnterior
		END
	END;

	WITH cteWaterMarkedRows
	AS
	(
		SELECT wmr.wmrKeyRowValue, wmr.wmrFirstRevisionDateTime, wmr.wmrLastRevisionDateTime, wmr.wmrJsonNewValue
		FROM WaterMarkedRows wmr
		WHERE 1=1
		AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		AND  wmr.wmrTable = 'SolicitudAfiliacionPersona'
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
		SELECT	solCanalRecepcion, fspDimCanal 
		FROM (
			VALUES
				('PRESENCIAL', 1),
				('WEB', 2),
				('PILA', 3)
			) AS T (solCanalRecepcion, fspDimCanal)
	),
	
	cteEventosSolicitudes
	AS
	(
		SELECT	wmr.wmrKeyRowValue AS sapId,			
			sol.solCanalRecepcion,
			roa.roaCanalReingreso,
			sol.solSedeDestinatario,
			sol.solFechaRadicacion AS fechaRadicacion,
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.sapEstadoSolicitud') = 'APROBADA'
			) AS fechaSolicitudAprobada,
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.sapEstadoSolicitud') = 'RECHAZADA'
			) AS fechasolicitudRechazada,
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.sapEstadoSolicitud') = 'CERRADA'
			) AS fechasolicitudCerrada
			
		FROM #wmrJSONUnified wmr
		INNER JOIN SolicitudAfiliacionPersona sap ON wmr.wmrKeyRowValue = sap.sapId
		INNER JOIN Solicitud sol ON sap.sapSolicitudGlobal = sol.solId
		INNER JOIN RolAfiliado roa ON sap.sapRolAfiliado = roa.roaId
	),

	cteAccionesSolicitudes
	AS
	(
		SELECT sapId,solSedeDestinatario,solCanalRecepcion,roaCanalReingreso,
			CASE WHEN S.fechaRadicacion BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudRadicada,
			CASE WHEN S.fechaSolicitudAprobada BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudAprobada,
			CASE WHEN S.fechasolicitudRechazada BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudRechazada,
			CASE WHEN S.fechasolicitudCerrada BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudCerrada,
			fechaRadicacion,fechaSolicitudCerrada
		FROM cteEventosSolicitudes S
	),

	cteAttachDimension
	AS
	(
		SELECT 
			S.sapId AS fspSolicitudAfiliacionPersona,
			@DimPeriodoId AS fspDimPeriodo,
			S.solSedeDestinatario AS fspDimSede,
			ISNULL(dicR.fspDimCanal, dicI.fspDimCanal) AS fspDimCanal,
			CASE WHEN dicR.fspDimCanal IS NULL THEN 1 ELSE 2 END AS fspDimTipoTransaccion,			
			S.accionSolicitudRadicada AS fspAccionSolicitudRadicada,
			S.accionSolicitudAprobada AS fspAccionSolicitudAprobada,
			S.accionSolicitudRechazada AS fspAccionSolicitudRechazada,
			S.accionSolicitudCerrada AS fspAccionSolicitudCerrada,
			S.fechaRadicacion AS fspFechaRadicacion,
			S.fechaSolicitudCerrada AS fspFechaSolicitudCerrada,
			DATEDIFF(dd,fechaRadicacion,fechaSolicitudCerrada) AS fspDiasProcesamientoAfiliacion
		FROM cteAccionesSolicitudes S	
		INNER JOIN cteDimCanal dicI ON dicI.solCanalRecepcion = S.solCanalRecepcion
		LEFT JOIN cteDimCanal dicR ON dicR.solCanalRecepcion = S.roaCanalReingreso		
		WHERE (
				S.accionSolicitudRadicada = 'true' OR
				S.accionSolicitudAprobada = 'true' OR
				S.accionSolicitudRechazada = 'true' OR
				S.accionSolicitudCerrada = 'true'
			)
	)

	MERGE FactSolicitudAfiliacionPersonaV2 AS T
	USING cteAttachDimension AS S
	ON (T.fspDimPeriodo = S.fspDimPeriodo AND T.fspSolicitudAfiliacionPersona = S.fspSolicitudAfiliacionPersona)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fspSolicitudAfiliacionPersona,fspDimPeriodo,fspAccionSolicitudRadicada,fspAccionSolicitudAprobada,fspAccionSolicitudRechazada,fspAccionSolicitudCerrada,fspFechaRadicacion,fspFechaSolicitudCerrada,fspDiasProcesamientoAfiliacion,fspDimSede,fspDimCanal,fspDimTipoTransaccion)
		VALUES (S.fspSolicitudAfiliacionPersona,S.fspDimPeriodo,S.fspAccionSolicitudRadicada,S.fspAccionSolicitudAprobada,S.fspAccionSolicitudRechazada,S.fspAccionSolicitudCerrada,S.fspFechaRadicacion,S.fspFechaSolicitudCerrada,S.fspDiasProcesamientoAfiliacion,S.fspDimSede,S.fspDimCanal,S.fspDimTipoTransaccion)
	WHEN MATCHED
		THEN UPDATE SET 
			T.fspAccionSolicitudRadicada = CASE WHEN T.fspAccionSolicitudRadicada = 'false' THEN S.fspAccionSolicitudRadicada ELSE T.fspAccionSolicitudRadicada END,
			T.fspAccionSolicitudAprobada = CASE WHEN T.fspAccionSolicitudAprobada = 'false' THEN S.fspAccionSolicitudAprobada ELSE T.fspAccionSolicitudAprobada END,
			T.fspAccionSolicitudRechazada = CASE WHEN T.fspAccionSolicitudRechazada = 'false' THEN S.fspAccionSolicitudRechazada ELSE T.fspAccionSolicitudRechazada END,
			T.fspAccionSolicitudCerrada = CASE WHEN T.fspAccionSolicitudCerrada = 'false' THEN S.fspAccionSolicitudCerrada ELSE T.fspAccionSolicitudCerrada END,
			T.fspFechaRadicacion = CASE WHEN T.fspFechaRadicacion IS NULL THEN S.fspFechaRadicacion ELSE T.fspFechaRadicacion END,
			T.fspFechaSolicitudCerrada = CASE WHEN T.fspFechaSolicitudCerrada IS NULL THEN S.fspFechaSolicitudCerrada ELSE T.fspFechaSolicitudCerrada END,
			T.fspDiasProcesamientoAfiliacion = CASE WHEN T.fspDiasProcesamientoAfiliacion IS NULL THEN S.fspDiasProcesamientoAfiliacion ELSE T.fspDiasProcesamientoAfiliacion END,
			T.fspDimTipoTransaccion = S.fspDimTipoTransaccion,
			T.fspDimCanal = S.fspDimCanal

;
