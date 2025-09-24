-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/12/11
-- Description:	Carga la tabla de hechos USP_REP_MERGE_FactNovedadFOVISV2 para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactNovedadFOVISV2
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
	
	
	--Morosos período anterior
	IF @DimPeriodoAnteriorId IS NOT NULL
	BEGIN
		IF NOT EXISTS (SELECT 1 FROM FactNovedadFOVISV2 WHERE fnfDimPeriodo = @DimPeriodoId)
		BEGIN
			WITH cteCarteraPeriodoAnterior AS (
				SELECT fnfSolicitudNovedadFovis,fnfJefeHogar,fnfDimPeriodo,fnfDimCicloAsignacion,fnfDimSede,fnfDimCanal,fnfDimModalidad,fnfDimTipoNovedadFOVIS,fnfDimMetodoEnvioDocumentacion,fnfAccionSolicitudRadicada,fnfAccionSolicitudCerrada,fnfAccionSolicitudRechazada,fnfAccionSolicitudEnProceso,fnfFechaRadicacion,fnfFechaSolicitudCerrada
				FROM FactNovedadFOVISV2 fnf
				WHERE fnf.fnfDimPeriodo = @DimPeriodoAnteriorId
				AND fnf.fnfAccionSolicitudCerrada <> 'true'
			)
			INSERT INTO FactNovedadFOVISV2 (fnfSolicitudNovedadFovis,fnfJefeHogar,fnfDimPeriodo,fnfDimCicloAsignacion,fnfDimSede,fnfDimCanal,fnfDimModalidad,fnfDimTipoNovedadFOVIS,fnfDimMetodoEnvioDocumentacion,fnfAccionSolicitudRadicada,fnfAccionSolicitudCerrada,fnfAccionSolicitudRechazada,fnfAccionSolicitudEnProceso,fnfFechaRadicacion,fnfFechaSolicitudCerrada)
			SELECT fnfSolicitudNovedadFovis,fnfJefeHogar,fnfDimPeriodo,fnfDimCicloAsignacion,fnfDimSede,fnfDimCanal,fnfDimModalidad,fnfDimTipoNovedadFOVIS,fnfDimMetodoEnvioDocumentacion,'false' AS fnfAccionSolicitudRadicada,'false' AS fnfAccionSolicitudCerrada,'false' AS fnfAccionSolicitudRechazada,'true' AS fnfAccionSolicitudEnProceso,fnfFechaRadicacion,fnfFechaSolicitudCerrada
			FROM cteCarteraPeriodoAnterior
		END
	END;
	
	WITH cteWaterMarkedRows
	AS
	(
		SELECT wmr.wmrKeyRowValue, wmr.wmrFirstRevisionDateTime, wmr.wmrLastRevisionDateTime, wmr.wmrJsonNewValue
		FROM WaterMarkedRows wmr
		WHERE 1=1
		AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		AND  wmr.wmrTable = 'SolicitudNovedadFovis'
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
		SELECT	solCanalRecepcion, fnfDimCanal 
		FROM (
			VALUES
				('PRESENCIAL', 1),
				('WEB', 2)
			) AS T (solCanalRecepcion, fnfDimCanal)
	), 
	cteDimModalidad
	AS 
	(
		SELECT	pofModalidad, fnfDimModalidad 
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
			) AS T (pofModalidad, fnfDimModalidad)
	),
	cteDimMetodoEnvioDocumentacion
	AS 
	(
		SELECT	solMetodoEnvio, fnfDimMetodoEnvioDocumentacion 
		FROM (
			VALUES
				('FISICO', 1),
				('ELECTRONICO', 2)
			) AS T (solMetodoEnvio, fnfDimMetodoEnvioDocumentacion)
	),
	cteDimTipoNovedadFOVIS
	AS 
	(
		SELECT	novProceso, novPuntoResolucion, fnfDimTipoNovedadFOVIS 
		FROM (
			VALUES
				('NOVEDADES_FOVIS_ESPECIAL', 'BACK', 1),
				('NOVEDADES_FOVIS_REGULAR', 'BACK', 2),
				('NOVEDADES_FOVIS_REGULAR', 'SISTEMA_AUTOMATICO', 3)
			) AS T (novProceso, novPuntoResolucion, fnfDimTipoNovedadFOVIS)
	),
	cteEventosSolicitudes
	AS
	(
		SELECT	snf.snfId,
			pof.pofJefeHogar,
			cia.ciaId AS pofCicloAsignacion,
			sol.solSedeDestinatario,
			sol.solCanalRecepcion,
			pof.pofModalidad,
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.snfEstadoSolicitud') = 'NOV_FOVIS_CERRADA'
			) AS fechaNovedadCerrada,
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.snfEstadoSolicitud') = 'NOV_FOVIS_RECHAZADA'
			) AS fechaNovedadRechazada,
			snf.snfEstadoSolicitud,
			sol.solFechaRadicacion AS fechaRadicacion,
			sol.solMetodoEnvio,
			nov.novProceso, 
			nov.novPuntoResolucion,
			CASE WHEN ino.inoId IS NOT NULL THEN 'true' ELSE 'false' END AS intentoNovedad,
			pof.pofEstadoHogar
		FROM #wmrJSONUnified wmr
		INNER JOIN SolicitudNovedadFovis snf ON wmr.wmrKeyRowValue = snf.snfId
		INNER JOIN SolicitudNovedadPersonaFovis spf ON spf.spfSolicitudNovedadFovis = snf.snfId
		INNER JOIN PostulacionFOVIS pof ON spf.spfPostulacionFOVIS = pof.pofId
		INNER JOIN JefeHogar jeh ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN Solicitud sol ON snf.snfSolicitudGlobal = sol.solId
		INNER JOIN ParametrizacionNovedad  nov ON snf.snfParametrizacionNovedad = nov.novId
		LEFT JOIN CicloAsignacion cia ON pof.pofCicloAsignacion = cia.ciaId
		LEFT JOIN IntentoNovedad ino ON sol.solId = ino.inoSolicitud
	),

	cteAccionesSolicitudes
	AS
	(
		SELECT snfId,pofJefeHogar,pofCicloAsignacion,solSedeDestinatario,solCanalRecepcion,pofModalidad,fechaRadicacion,
			snfEstadoSolicitud, fechaNovedadCerrada,solMetodoEnvio,novProceso,novPuntoResolucion,
			CASE WHEN S.fechaRadicacion BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudRadicada,
			CASE WHEN S.fechaNovedadCerrada BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudCerrada,
			CASE WHEN S.fechaNovedadRechazada BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudRechazada,
			CASE WHEN S.snfEstadoSolicitud <> 'NOV_FOVIS_CERRADA' THEN 'true' ELSE 'false' END AS accionSolicitudEnProceso
		FROM cteEventosSolicitudes S
	),

	cteAttachDimension
	AS
	(
		SELECT 
			S.snfId AS fnfSolicitudNovedadFovis,
			S.pofJefeHogar AS fnfJefeHogar,
			@DimPeriodoId AS fnfDimPeriodo,
			S.pofCicloAsignacion AS fnfDimCicloAsignacion,
			S.solSedeDestinatario AS fnfDimSede,
			dic.fnfDimCanal,
			dmo.fnfDimModalidad,
			ded.fnfDimMetodoEnvioDocumentacion,
			dnf.fnfDimTipoNovedadFOVIS,
			S.accionSolicitudRadicada AS fnfAccionSolicitudRadicada,
			S.accionSolicitudCerrada AS fnfAccionSolicitudCerrada,
			S.accionSolicitudRechazada AS fnfAccionSolicitudRechazada,
			S.accionSolicitudEnProceso AS fnfAccionSolicitudEnProceso,
			S.fechaRadicacion AS fnfFechaRadicacion,
			S.fechaNovedadCerrada AS fnfFechaSolicitudCerrada
		FROM cteAccionesSolicitudes S	
		INNER JOIN cteDimCanal dic ON dic.solCanalRecepcion = S.solCanalRecepcion
		INNER JOIN cteDimModalidad dmo ON dmo.pofModalidad = S.pofModalidad
		INNER JOIN cteDimTipoNovedadFOVIS dnf ON dnf.novProceso = S.novProceso AND dnf.novPuntoResolucion = S.novPuntoResolucion
		LEFT JOIN cteDimMetodoEnvioDocumentacion ded ON S.solMetodoEnvio = ded.solMetodoEnvio
		WHERE (
				S.accionSolicitudRadicada = 'true'
				OR S.accionSolicitudCerrada = 'true'
				OR S.accionSolicitudRechazada = 'true'
				OR S.accionSolicitudEnProceso = 'true'
			)
	)

	--SELECT * FROM cteAttachDimension

	MERGE FactNovedadFOVISV2 AS T
	USING cteAttachDimension AS S
	ON (T.fnfDimPeriodo = S.fnfDimPeriodo AND T.fnfSolicitudNovedadFovis = S.fnfSolicitudNovedadFovis)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fnfSolicitudNovedadFovis,fnfJefeHogar,fnfDimPeriodo,fnfDimCicloAsignacion,fnfDimSede,fnfDimCanal,fnfDimModalidad,fnfDimTipoNovedadFOVIS,fnfDimMetodoEnvioDocumentacion,fnfAccionSolicitudRadicada,fnfAccionSolicitudCerrada,fnfAccionSolicitudRechazada,fnfAccionSolicitudEnProceso,fnfFechaRadicacion,fnfFechaSolicitudCerrada)
		VALUES (S.fnfSolicitudNovedadFovis,S.fnfJefeHogar,S.fnfDimPeriodo,S.fnfDimCicloAsignacion,S.fnfDimSede,S.fnfDimCanal,S.fnfDimModalidad,S.fnfDimTipoNovedadFOVIS,S.fnfDimMetodoEnvioDocumentacion,S.fnfAccionSolicitudRadicada,S.fnfAccionSolicitudCerrada,S.fnfAccionSolicitudRechazada,S.fnfAccionSolicitudEnProceso,S.fnfFechaRadicacion,S.fnfFechaSolicitudCerrada)
	WHEN MATCHED
		THEN UPDATE SET 
			T.fnfJefeHogar = S.fnfJefeHogar,
			T.fnfDimMetodoEnvioDocumentacion = S.fnfDimMetodoEnvioDocumentacion,
			T.fnfAccionSolicitudRadicada = CASE WHEN T.fnfAccionSolicitudRadicada = 'false' THEN S.fnfAccionSolicitudRadicada ELSE T.fnfAccionSolicitudRadicada END, 
			T.fnfAccionSolicitudCerrada = CASE WHEN T.fnfAccionSolicitudCerrada = 'false' THEN S.fnfAccionSolicitudCerrada ELSE T.fnfAccionSolicitudCerrada END, 
			T.fnfAccionSolicitudRechazada = CASE WHEN T.fnfAccionSolicitudRechazada = 'false' THEN S.fnfAccionSolicitudRechazada ELSE T.fnfAccionSolicitudRechazada END, 
			T.fnfAccionSolicitudEnProceso = CASE WHEN T.fnfAccionSolicitudEnProceso = 'true' THEN S.fnfAccionSolicitudEnProceso ELSE T.fnfAccionSolicitudEnProceso END, 
			T.fnfFechaRadicacion = CASE WHEN T.fnfFechaRadicacion IS NULL THEN S.fnfFechaRadicacion ELSE T.fnfFechaRadicacion END,
			T.fnfFechaSolicitudCerrada = CASE WHEN T.fnfFechaSolicitudCerrada IS NULL THEN S.fnfFechaSolicitudCerrada ELSE T.fnfFechaSolicitudCerrada END
;
