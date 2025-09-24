-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/12/05
-- Description:	Carga la tabla de hechos FactAsignacionFOVISV2 para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactAsignacionFOVISV2
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
		INNER JOIN PostulacionFOVIS pof ON wmr.wmrKeyRowValue = pof.pofId
		INNER JOIN SolicitudAsignacion saf ON pof.pofSolicitudAsignacion = saf.safId
		WHERE 1=1
		AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		AND  wmr.wmrTable = 'PostulacionFOVIS'
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
		SELECT	solCanalRecepcion, fafDimCanal 
		FROM (
			VALUES
				('PRESENCIAL', 1),
				('WEB', 2)
			) AS T (solCanalRecepcion, fafDimCanal)
	), 
	cteDimModalidad
	AS 
	(
		SELECT	pofModalidad, fafDimModalidad 
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
			) AS T (pofModalidad, fafDimModalidad)
	),
	cteEventosSolicitudes
	AS
	(
		SELECT	saf.safId,
			pof.pofJefeHogar,
			pof.pofCicloAsignacion,
			sol.solSedeDestinatario,
			sol.solCanalRecepcion,
			pof.pofModalidad,
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.pofEstadoHogar') = 'ASIGNADO_SIN_PRORROGA'
			) AS fechaAsignadoSinProrroga,			
			pof.pofValorAsignadoSFV AS pofValorAsignadoSFV,
			pof.pofEstadoHogar
		FROM #wmrJSONUnified wmr
		INNER JOIN PostulacionFOVIS pof ON wmr.wmrKeyRowValue = pof.pofId
		INNER JOIN JefeHogar jeh ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN SolicitudAsignacion saf ON pof.pofSolicitudAsignacion = saf.safId
		INNER JOIN Solicitud sol ON saf.safSolicitudGlobal = sol.solId		
		INNER JOIN CicloAsignacion cia ON pof.pofCicloAsignacion = cia.ciaId
	),

	cteAccionesSolicitudes
	AS
	(
		SELECT safId,pofJefeHogar,pofCicloAsignacion,solSedeDestinatario,solCanalRecepcion,pofModalidad,fechaAsignadoSinProrroga,pofValorAsignadoSFV,pofEstadoHogar,
			CASE WHEN S.fechaAsignadoSinProrroga BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionAsignadoSinProrroga
		FROM cteEventosSolicitudes S
	),

	cteAttachDimension
	AS
	(
		SELECT 
			S.safId AS fafSolicitudAsignacion,
			S.pofJefeHogar AS fafJefeHogar,
			@DimPeriodoId AS fafDimPeriodo,
			S.pofCicloAsignacion AS fafDimCicloAsignacion,
			S.solSedeDestinatario AS fafDimSede,
			dic.fafDimCanal AS fafDimCanal,
			dmo.fafDimModalidad AS fafDimModalidad,			
			S.accionAsignadoSinProrroga AS fafAccionAsignadoSinProrroga,
			S.pofValorAsignadoSFV AS fafValorAsignadoSFV
		FROM cteAccionesSolicitudes S	
		INNER JOIN cteDimCanal dic ON dic.solCanalRecepcion = S.solCanalRecepcion
		INNER JOIN cteDimModalidad dmo ON dmo.pofModalidad = S.pofModalidad
		WHERE (
				S.accionAsignadoSinProrroga = 'true'
			)
	)

	--SELECT * FROM cteAttachDimension

	MERGE FactAsignacionFOVISV2 AS T
	USING cteAttachDimension AS S
	ON (T.fafDimPeriodo = S.fafDimPeriodo AND T.fafSolicitudAsignacion = S.fafSolicitudAsignacion AND T.fafJefeHogar = S.fafJefeHogar)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fafSolicitudAsignacion,fafJefeHogar,fafDimPeriodo,fafDimCicloAsignacion,fafDimSede,fafDimCanal,fafDimModalidad,fafAccionAsignadoSinProrroga,fafValorAsignadoSFV)
		VALUES (S.fafSolicitudAsignacion,S.fafJefeHogar,S.fafDimPeriodo,S.fafDimCicloAsignacion,S.fafDimSede,S.fafDimCanal,S.fafDimModalidad,S.fafAccionAsignadoSinProrroga,S.fafValorAsignadoSFV)
	WHEN MATCHED
		THEN UPDATE SET 
			T.fafAccionAsignadoSinProrroga = CASE WHEN T.fafAccionAsignadoSinProrroga = 'false' THEN S.fafAccionAsignadoSinProrroga ELSE T.fafAccionAsignadoSinProrroga END, 
			T.fafValorAsignadoSFV = S.fafValorAsignadoSFV
;
