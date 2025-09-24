-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/12/05
-- Description:	Carga la tabla de hechos FactLegalizacionDesembolsoFOVISV2.sql para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactLegalizacionDesembolsoFOVISV2
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
	
	
	WITH cteWaterMarkedRows
	AS
	(
		SELECT wmr.wmrKeyRowValue, wmr.wmrFirstRevisionDateTime, wmr.wmrLastRevisionDateTime, wmr.wmrJsonNewValue
		FROM WaterMarkedRows wmr
		WHERE 1=1
		AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		AND  wmr.wmrTable = 'SolicitudLegalizacionDesembolso'
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
		SELECT	solCanalRecepcion, fldDimCanal 
		FROM (
			VALUES
				('PRESENCIAL', 1),
				('WEB', 2)
			) AS T (solCanalRecepcion, fldDimCanal)
	), 
	cteDimModalidad
	AS 
	(
		SELECT	pofModalidad, fldDimModalidad 
		FROM (
			VALUES
				('ADQUISICION_VIVIENDA_NUEVA_RURAL', 1),
				('ADQUISICION_VIVIENDA_NUEVA_URBANA', 2),
				('ADQUISICION_VIVIENDA_USADA_RURAL', 3),
				('ADQUISICION_VIVIENDA_USADA_URBANA', 4),
				('CONSTRUCCION_SITIO_PROPIO_RURAL', 5),
				('CONSTRUCCION_SITIO_PROPIO_URBANO', 6),
				('MEJORAMIENTO_VIVIENDA_URBANA', 7),
				('MEJORAMIENTO_VIVIENDA_RURAL', 8),
				('MEJORAMIENTO_VIVIENDA_SALUDABLE', 9)
			) AS T (pofModalidad, fldDimModalidad)
	),
	cteEventosSolicitudes
	AS
	(
		SELECT	sld.sldId,
			pof.pofJefeHogar,
			pof.pofCicloAsignacion,
			sol.solSedeDestinatario,
			sol.solCanalRecepcion,
			pof.pofModalidad,
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.sldEstadoSolicitud') = 'LEGALIZACION_Y_DESEMBOLSO_CERRADO'
			) AS fechaLegalizacionDesembolsoCerrado,
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.sldEstadoSolicitud') = 'DESEMBOLSO_EXITOSO_REPORTADO_POR_AREA_FINANCIERA'
			) AS fechaDesembolsoExitoso,
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.sldEstadoSolicitud') = 'REINTENTO_DESEMBOLSO_EXITOSO_REPORTADO_POR_AREA_FINANCIERA'
			) AS fechaReintentoDesembolsoExitoso,		
			pof.pofValorAsignadoSFV AS fldMontoDesembolsado,
			sol.solFechaRadicacion AS fechaRadicacion,
			pof.pofEstadoHogar
		FROM #wmrJSONUnified wmr
		INNER JOIN SolicitudLegalizacionDesembolso sld ON wmr.wmrKeyRowValue = sld.sldId
		INNER JOIN PostulacionFOVIS pof ON sld.sldPostulacionFOVIS = pof.pofId
		INNER JOIN JefeHogar jeh ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN Solicitud sol ON sld.sldSolicitudGlobal = sol.solId		
		INNER JOIN CicloAsignacion cia ON pof.pofCicloAsignacion = cia.ciaId
	),

	cteAccionesSolicitudes
	AS
	(
		SELECT sldId,pofJefeHogar,pofCicloAsignacion,solSedeDestinatario,solCanalRecepcion,pofModalidad,fldMontoDesembolsado,fechaRadicacion,fechaLegalizacionDesembolsoCerrado,pofEstadoHogar,
			CASE WHEN S.fechaRadicacion BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudRadicada,
			CASE WHEN S.fechaLegalizacionDesembolsoCerrado BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionLegalizacionDesembolsoCerrado,
			CASE WHEN S.fechaDesembolsoExitoso BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionDesembolsoExitoso,
			CASE WHEN S.fechaReintentoDesembolsoExitoso BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionReintentoDesembolsoExitoso
		FROM cteEventosSolicitudes S
	),

	cteAttachDimension
	AS
	(
		SELECT 
			S.sldId AS fldSolicitudLegalizacionDesembolso,
			S.pofJefeHogar AS fldJefeHogar,
			@DimPeriodoId AS fldDimPeriodo,
			S.pofCicloAsignacion AS fldDimCicloAsignacion,
			S.solSedeDestinatario AS fldDimSede,
			dic.fldDimCanal AS fldDimCanal,
			dmo.fldDimModalidad AS fldDimModalidad,
			S.accionSolicitudRadicada AS fldAccionSolicitudRadicada,
			S.accionLegalizacionDesembolsoCerrado AS fldAccionLegalizacionDesembolsoCerrado,
			S.accionDesembolsoExitoso AS fldAccionDesembolsoExitoso,
			S.accionReintentoDesembolsoExitoso AS fldAccionReintentoDesembolsoExitoso,
			S.fechaRadicacion AS fldFechaRadicacion,
			S.fechaLegalizacionDesembolsoCerrado AS fldFechaLegalizacionDesembolsoCerrado,
			S.fldMontoDesembolsado AS fldMontoDesembolsado
		FROM cteAccionesSolicitudes S	
		INNER JOIN cteDimCanal dic ON dic.solCanalRecepcion = S.solCanalRecepcion
		INNER JOIN cteDimModalidad dmo ON dmo.pofModalidad = S.pofModalidad
		WHERE (
				S.accionSolicitudRadicada = 'true'
				OR S.accionLegalizacionDesembolsoCerrado = 'true'
				OR S.accionDesembolsoExitoso = 'true'
				OR S.accionReintentoDesembolsoExitoso = 'true'
			)
	)

	--SELECT * FROM cteAttachDimension

	MERGE FactLegalizacionDesembolsoFOVISV2 AS T
	USING cteAttachDimension AS S
	ON (T.fldDimPeriodo = S.fldDimPeriodo AND T.fldSolicitudLegalizacionDesembolso = S.fldSolicitudLegalizacionDesembolso)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fldSolicitudLegalizacionDesembolso,fldJefeHogar,fldDimPeriodo,fldDimCicloAsignacion,fldDimSede,fldDimCanal,fldDimModalidad,fldAccionSolicitudRadicada,fldAccionLegalizacionDesembolsoCerrado,fldAccionDesembolsoExitoso,fldAccionReintentoDesembolsoExitoso,fldFechaRadicacion,fldFechaLegalizacionDesembolsoCerrado,fldMontoDesembolsado)
		VALUES (S.fldSolicitudLegalizacionDesembolso,S.fldJefeHogar,S.fldDimPeriodo,S.fldDimCicloAsignacion,S.fldDimSede,S.fldDimCanal,S.fldDimModalidad,S.fldAccionSolicitudRadicada,S.fldAccionLegalizacionDesembolsoCerrado,S.fldAccionDesembolsoExitoso,S.fldAccionReintentoDesembolsoExitoso,S.fldFechaRadicacion,S.fldFechaLegalizacionDesembolsoCerrado,S.fldMontoDesembolsado)
	WHEN MATCHED
		THEN UPDATE SET 
			T.fldJefeHogar = S.fldJefeHogar,
			T.fldAccionSolicitudRadicada = CASE WHEN T.fldAccionSolicitudRadicada = 'false' THEN S.fldAccionSolicitudRadicada ELSE T.fldAccionSolicitudRadicada END, 
			T.fldAccionLegalizacionDesembolsoCerrado = CASE WHEN T.fldAccionLegalizacionDesembolsoCerrado = 'false' THEN S.fldAccionLegalizacionDesembolsoCerrado ELSE T.fldAccionLegalizacionDesembolsoCerrado END, 
			T.fldAccionDesembolsoExitoso = CASE WHEN T.fldAccionDesembolsoExitoso = 'false' THEN S.fldAccionDesembolsoExitoso ELSE T.fldAccionDesembolsoExitoso END, 
			T.fldAccionReintentoDesembolsoExitoso = CASE WHEN T.fldAccionReintentoDesembolsoExitoso = 'false' THEN S.fldAccionReintentoDesembolsoExitoso ELSE T.fldAccionReintentoDesembolsoExitoso END, 
			T.fldFechaRadicacion = CASE WHEN T.fldFechaRadicacion IS NULL THEN S.fldFechaRadicacion ELSE T.fldFechaRadicacion END,
			T.fldFechaLegalizacionDesembolsoCerrado = CASE WHEN T.fldFechaLegalizacionDesembolsoCerrado IS NULL THEN S.fldFechaLegalizacionDesembolsoCerrado ELSE T.fldFechaLegalizacionDesembolsoCerrado END,
			T.fldMontoDesembolsado = S.fldMontoDesembolsado
;
