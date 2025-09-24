-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/12/03
-- Description:	Carga la tabla de hechos FactPostulacionFOVISV2 para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactPostulacionFOVISV2
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
		AND  wmr.wmrTable = 'SolicitudPostulacion'
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
		SELECT	solCanalRecepcion, fpfDimCanal 
		FROM (
			VALUES
				('PRESENCIAL', 1),
				('WEB', 2)
			) AS T (solCanalRecepcion, fpfDimCanal)
	), 
	cteDimModalidad
	AS 
	(
		SELECT	pofModalidad, fpfDimModalidad 
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
			) AS T (pofModalidad, fpfDimModalidad)
	),
	cteEventosSolicitudes
	AS
	(
		SELECT	wmr.wmrKeyRowValue AS spoId,
			pof.pofId,
			pof.pofJefeHogar,
			cia.ciaId AS pofCicloAsignacion,
			sol.solSedeDestinatario,
			sol.solCanalRecepcion,
			pof.pofModalidad,
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.spoEstadoSolicitud') = 'POSTULACION_HABIL'
			) AS fechaHabil,
			
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.spoEstadoSolicitud') = 'POSTULACION_RECHAZADA'
			) AS fechaRechazado,
			
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.spoEstadoSolicitud') = 'POSTULACION_CERRADA'
			)  AS fechaCierre,
			
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.spoEstadoSolicitud') = 'RENUNCIO_A_SUBSIDIO_ASIGNADO'
			) AS fechaRenuncioSubsidioAsignado,

			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.spoEstadoSolicitud') = 'SUBSIDIO_REEMBOLSADO'
			) AS fechaSubsidioReembolsado,
			
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.spoEstadoSolicitud') = 'RESTITUIDO_CON_SANCION'
			) AS fechaRestituidoConSancion,
			
			(	
				SELECT TOP 1 CAST(JSON_VALUE(j.[value],'$.revTime') AS DATETIME)
				FROM OPENJSON(wmr.wmrJsonValue) j
				WHERE JSON_VALUE(j.[value],'$.spoEstadoSolicitud') = 'RESTITUIDO_SIN_SANCION'
			)  AS fechaRestituidoSinSancion,
			sol.solFechaRadicacion AS fechaRadicacion,
			spo.spoEstadoSolicitud,
			pof.pofEstadoHogar,
			pof.pofValorAsignadoSFV,
			pof.pofValorAjusteIPCSFV
		FROM #wmrJSONUnified wmr
		INNER JOIN SolicitudPostulacion spo ON wmr.wmrKeyRowValue = spo.spoId
		INNER JOIN PostulacionFOVIS pof ON spo.spoPostulacionFOVIS = pof.pofId
		INNER JOIN JefeHogar jeh ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN Solicitud sol ON spo.spoSolicitudGlobal = sol.solId		
		LEFT JOIN CicloAsignacion cia ON pof.pofCicloAsignacion = cia.ciaId
	),

	cteAccionesSolicitudes
	AS
	(
		SELECT spoId,pofId,pofJefeHogar,pofCicloAsignacion,solSedeDestinatario,solCanalRecepcion,pofModalidad,fechaHabil,fechaRechazado,fechaCierre,fechaRadicacion,spoEstadoSolicitud,pofEstadoHogar,pofValorAsignadoSFV,pofValorAjusteIPCSFV,
			CASE WHEN S.fechaHabil BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudHabil,
			CASE WHEN S.fechaRechazado BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudRechazada,
			CASE WHEN S.fechaCierre BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudCerrada,
			CASE WHEN S.fechaRadicacion BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSolicitudRadicada,
			CASE WHEN S.fechaCierre IS NULL AND S.fechaHabil IS NULL AND S.fechaRechazado IS NULL THEN 'true' ELSE 'false' END AS accionSolicitudEnProceso,
			CASE WHEN S.fechaRenuncioSubsidioAsignado BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionRenuncioSubsidioAsignado,
			CASE WHEN S.fechaSubsidioReembolsado BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionSubsidioReembolsado,
			CASE WHEN S.fechaRestituidoConSancion BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionRestituidoConSancion,
			CASE WHEN S.fechaRestituidoSinSancion BETWEEN @FechaInicioRevision AND @FechaFinRevision THEN 'true' ELSE 'false' END AS accionRestituidoSinSancion
		FROM cteEventosSolicitudes S
	),

	cteAttachDimension
	AS
	(
		SELECT 
			S.pofId AS fpfPostulacionFOVIS,
			S.pofJefeHogar AS fpfJefeHogar,
			@DimPeriodoId AS fpfDimPeriodo,
			S.pofCicloAsignacion AS fpfDimCicloAsignacion,
			S.solSedeDestinatario AS fpfDimSede,
			dic.fpfDimCanal AS fpfDimCanal,
			dmo.fpfDimModalidad AS fpfDimModalidad,
			CASE WHEN S.accionSolicitudHabil = 'true' THEN 1 ELSE
			CASE WHEN S.accionSolicitudRechazada = 'true' THEN 2 ELSE
			CASE WHEN S.accionSolicitudCerrada IS NULL THEN 3 END END END AS fpfDimEstadoProcesamiento,
			S.accionSolicitudHabil AS fpfAccionSolicitudHabil,
			S.accionSolicitudRechazada AS fpfAccionSolicitudRechazada,
			S.accionSolicitudEnProceso AS fpfAccionSolicitudEnProceso,
			S.accionSolicitudRadicada AS fpfAccionSolicitudRadicada,
			S.accionSolicitudCerrada AS fpfAccionSolicitudCerrada,
			S.accionRenuncioSubsidioAsignado AS fpfAccionRenuncioSubsidioAsignado,
			S.accionSubsidioReembolsado AS fpfAccionSubsidioReembolsado,
			CASE WHEN S.accionRestituidoConSancion = 'true' OR S.accionRestituidoSinSancion = 'true' THEN 'true' ELSE 'false' END AS fpfAccionRestituido,			
			S.fechaRadicacion AS fpfFechaRadicacionSolicitud,
			S.fechacierre AS fpfFechaCierreSolicitud,
			S.pofValorAsignadoSFV AS fpfValorAsignadoSFV,
			S.pofValorAjusteIPCSFV AS fpfValorAjusteIPCSFV
		FROM cteAccionesSolicitudes S	
		INNER JOIN cteDimCanal dic ON dic.solCanalRecepcion = S.solCanalRecepcion
		INNER JOIN cteDimModalidad dmo ON dmo.pofModalidad = S.pofModalidad
		WHERE (
				S.accionSolicitudHabil = 'true' 
				OR S.accionSolicitudRechazada = 'true' 
				OR S.accionSolicitudCerrada = 'true' 
				OR S.accionSolicitudEnProceso = 'true' 
				OR S.accionSolicitudRadicada = 'true'				
				OR S.accionRenuncioSubsidioAsignado = 'true' 
				OR S.accionSubsidioReembolsado = 'true' 
				OR S.accionRestituidoConSancion = 'true' 
				OR S.accionSolicitudCerrada = 'true'
			)
	)

	--SELECT * FROM cteAttachDimension

	MERGE FactPostulacionFOVISV2 AS T
	USING cteAttachDimension AS S
	ON (T.fpfDimPeriodo = S.fpfDimPeriodo AND T.fpfPostulacionFOVIS = S.fpfPostulacionFOVIS)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fpfPostulacionFOVIS,fpfJefeHogar,fpfDimPeriodo,fpfDimCicloAsignacion,fpfDimSede,fpfDimCanal,fpfDimModalidad,fpfDimEstadoProcesamiento,fpfAccionSolicitudHabil,fpfAccionSolicitudRechazada,fpfAccionSolicitudEnProceso,fpfAccionSolicitudRadicada,fpfAccionSolicitudCerrada,fpfAccionRenuncioSubsidioAsignado,fpfAccionSubsidioReembolsado,fpfAccionRestituido,fpfFechaRadicacionSolicitud,fpfFechaCierreSolicitud,fpfValorAsignadoSFV,fpfValorAjusteIPCSFV)
		VALUES (S.fpfPostulacionFOVIS,S.fpfJefeHogar,S.fpfDimPeriodo,S.fpfDimCicloAsignacion,S.fpfDimSede,S.fpfDimCanal,S.fpfDimModalidad,S.fpfDimEstadoProcesamiento,S.fpfAccionSolicitudHabil,S.fpfAccionSolicitudRechazada,S.fpfAccionSolicitudEnProceso,S.fpfAccionSolicitudRadicada,S.fpfAccionSolicitudCerrada,S.fpfAccionRenuncioSubsidioAsignado,S.fpfAccionSubsidioReembolsado,S.fpfAccionRestituido,S.fpfFechaRadicacionSolicitud,S.fpfFechaCierreSolicitud,S.fpfValorAsignadoSFV,S.fpfValorAjusteIPCSFV)
	WHEN MATCHED
		THEN UPDATE SET 
			T.fpfJefeHogar = S.fpfJefeHogar,
			T.fpfDimCicloAsignacion = S.fpfDimCicloAsignacion,
			T.fpfDimEstadoProcesamiento = ISNULL(S.fpfDimEstadoProcesamiento, T.fpfDimEstadoProcesamiento),
			T.fpfAccionSolicitudHabil = CASE WHEN T.fpfAccionSolicitudHabil = 'false' THEN S.fpfAccionSolicitudHabil ELSE T.fpfAccionSolicitudHabil END,
			T.fpfAccionSolicitudRechazada = CASE WHEN T.fpfAccionSolicitudRechazada = 'false' THEN S.fpfAccionSolicitudRechazada ELSE T.fpfAccionSolicitudRechazada END,
			T.fpfAccionSolicitudEnProceso = CASE WHEN T.fpfAccionSolicitudEnProceso = 'true' THEN S.fpfAccionSolicitudEnProceso ELSE T.fpfAccionSolicitudEnProceso END,
			T.fpfAccionSolicitudRadicada = CASE WHEN T.fpfAccionSolicitudRadicada = 'false' THEN S.fpfAccionSolicitudRadicada ELSE T.fpfAccionSolicitudRadicada END,
			T.fpfAccionSolicitudCerrada = CASE WHEN T.fpfAccionSolicitudCerrada = 'false' THEN S.fpfAccionSolicitudCerrada ELSE T.fpfAccionSolicitudCerrada END,

			T.fpfAccionSubsidioReembolsado = CASE WHEN T.fpfAccionSubsidioReembolsado = 'false' THEN S.fpfAccionSubsidioReembolsado ELSE T.fpfAccionSubsidioReembolsado END,
			T.fpfAccionRestituido = CASE WHEN T.fpfAccionRestituido = 'false' THEN S.fpfAccionRestituido ELSE T.fpfAccionRestituido END,
			T.fpfAccionRenuncioSubsidioAsignado = CASE WHEN T.fpfAccionRenuncioSubsidioAsignado = 'false' THEN S.fpfAccionRenuncioSubsidioAsignado ELSE T.fpfAccionRenuncioSubsidioAsignado END,

			T.fpfFechaRadicacionSolicitud = CASE WHEN T.fpfFechaRadicacionSolicitud IS NULL THEN S.fpfFechaRadicacionSolicitud ELSE T.fpfFechaRadicacionSolicitud END,
			T.fpfFechaCierreSolicitud = CASE WHEN T.fpfFechaCierreSolicitud IS NULL THEN S.fpfFechaCierreSolicitud ELSE T.fpfFechaCierreSolicitud END,

			T.fpfValorAsignadoSFV = S.fpfValorAsignadoSFV,
			T.fpfValorAjusteIPCSFV = S.fpfValorAjusteIPCSFV
;