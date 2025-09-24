-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/12/27
-- Description:	Carga la tabla de hechos FactSolicitudNovedadPersonaV2 para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactSolicitudNovedadPersonaV2
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
	
	WITH cteWaterMarkedRows_snp
	AS
	(
		SELECT wmr.wmrKeyRowValue
		FROM WaterMarkedRows wmr
		WHERE 1=1
		AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		AND  wmr.wmrTable = 'SolicitudNovedadPersona'
	)

	SELECT DISTINCT wmrKeyRowValue
	INTO #cteWaterMarkedRows_snp
	FROM cteWaterMarkedRows_snp;


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
		SELECT	canal, fnpDimCanal 
		FROM (
			VALUES
				('PRESENCIAL', 1),
				('WEB', 2),
				('PILA', 3)
			) AS T (canal, fnpDimCanal)
	),

	cteEventosNovedadesRadicadas AS
	(
		SELECT	wmr.wmrKeyRowValue AS snpId,			
			snp.snpPersona,
			sol.solSedeDestinatario,
			sol.solCanalRecepcion,
			sol.solFechaRadicacion,
			nov.novTipoNovedad
		FROM #cteWaterMarkedRows_snp wmr
		INNER JOIN SolicitudNovedadPersona snp ON wmr.wmrKeyRowValue = snp.snpId
		INNER JOIN SolicitudNovedad sno ON snp.snpSolicitudNovedad = sno.snoId
		INNER JOIN Solicitud sol ON sno.snoSolicitudGlobal = sol.solId
		INNER JOIN ParametrizacionNovedad nov ON sno.snoNovedad = nov.novId
		INNER JOIN Persona per ON snp.snpPersona = per.perId
	),

	cteEventosNovedadesCerradas AS
	(
		SELECT	snp.snpId,			
			snp.snpPersona,
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
		INNER JOIN SolicitudNovedadPersona snp ON sno.snoId = snp.snpSolicitudNovedad
		INNER JOIN Solicitud sol ON sno.snoSolicitudGlobal = sol.solId
		INNER JOIN ParametrizacionNovedad nov ON sno.snoNovedad = nov.novId
		INNER JOIN Persona per ON snp.snpPersona = per.perId
	),

	cteNovedadesUnion
	AS 
	(
		SELECT snpId, snpPersona, solSedeDestinatario, solCanalRecepcion, solFechaRadicacion, NULL AS fechaSolicitudCerrada, novTipoNovedad
		FROM cteEventosNovedadesRadicadas
		WHERE snpId NOT IN (SELECT snpId FROM cteEventosNovedadesCerradas)

		UNION ALL

		SELECT snpId, snpPersona, solSedeDestinatario, solCanalRecepcion, solFechaRadicacion, fechaSolicitudCerrada, novTipoNovedad
		FROM cteEventosNovedadesCerradas
	),

	cteAccionesNovedades
	AS
	(
		SELECT snpId, snpPersona, solSedeDestinatario, solCanalRecepcion, solFechaRadicacion, fechaSolicitudCerrada, novTipoNovedad,
			CASE WHEN S.solFechaRadicacion BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionRadicacion,
			CASE WHEN S.fechaSolicitudCerrada BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudCerrada
		FROM cteNovedadesUnion S
	),

	cteAttachDimension
	AS
	(
		SELECT 
			S.snpId AS fnpSolicitudNovedadPersona,
			S.snpPersona AS fnpPersona,
			@DimPeriodoId AS fnpDimPeriodo,
			S.solFechaRadicacion AS fnpFechaRadicacion,
			S.fechaSolicitudCerrada AS fnpFechaSolicitudCerrada,
			S.accionRadicacion AS fnpAccionRadicacion,
			S.accionSolicitudCerrada AS fnpAccionSolicitudCerrada,
			dic.fnpDimCanal,
			S.solSedeDestinatario AS fnpDimSede,
			CASE WHEN S.novTipoNovedad = 'AUTOMATICA' THEN 'true' ELSE 'false' END AS fnpEsAutomatica
		FROM cteAccionesNovedades S	
		INNER JOIN cteDimCanal dic ON dic.canal = S.solCanalRecepcion
		WHERE (
				S.accionRadicacion = 'true' OR
				S.accionSolicitudCerrada = 'true' 
			)
	)

	--DROP TABLE #wmrJSONUnified_sno
	--DROP TABLE #cteWaterMarkedRows_snp;
	--SELECT * FROM cteAttachDimension

	MERGE FactSolicitudNovedadPersonaV2 AS T
	USING cteAttachDimension AS S
	ON (T.fnpDimPeriodo = S.fnpDimPeriodo AND T.fnpSolicitudNovedadPersona = S.fnpSolicitudNovedadPersona)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fnpSolicitudNovedadPersona,fnpPersona,fnpDimPeriodo,fnpAccionRadicacion,fnpAccionSolicitudCerrada,fnpFechaRadicacion,fnpFechaSolicitudCerrada,fnpEsAutomatica,fnpDimSede,fnpDimCanal)
		VALUES (S.fnpSolicitudNovedadPersona,S.fnpPersona,S.fnpDimPeriodo,S.fnpAccionRadicacion,S.fnpAccionSolicitudCerrada,S.fnpFechaRadicacion,S.fnpFechaSolicitudCerrada,S.fnpEsAutomatica,S.fnpDimSede,S.fnpDimCanal)
	WHEN MATCHED
		THEN UPDATE SET
			T.fnpFechaSolicitudCerrada = CASE WHEN T.fnpFechaSolicitudCerrada IS NULL THEN S.fnpFechaSolicitudCerrada ELSE T.fnpFechaSolicitudCerrada END,
			T.fnpAccionSolicitudCerrada = CASE WHEN T.fnpAccionSolicitudCerrada = 'false' THEN S.fnpAccionSolicitudCerrada ELSE T.fnpAccionSolicitudCerrada END
;