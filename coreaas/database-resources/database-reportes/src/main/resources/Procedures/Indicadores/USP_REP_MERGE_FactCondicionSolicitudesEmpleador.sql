-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/05/09
-- Description:	Carga la tabla de hechos FactCondicionSolicitudEmpleador para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactCondicionSolicitudesEmpleador
AS

	--FactCondicionEmpleador
	DECLARE @FechaInicioRevision AS DATETIME
	DECLARE @FechaFinRevision AS DATETIME
	DECLARE @DimPeriodoId AS BIGINT
	DECLARE @RevisionAuditoriaId BIGINT
		
	SELECT  @FechaInicioRevision =MIN(rar.rarRevisionTimeInicio), @FechaFinRevision =MAX(rar.rarRevisionTimeFin)
	FROM RevisionAuditoriaReportes rar
	WHERE rar.rarEncolaProceso = 1


	SELECT @DimPeriodoId = dipId FROM DimPeriodo WHERE dipMes = MONTH(@FechaFinRevision) AND dipAnio = YEAR(@FechaFinRevision);

	CREATE TABLE #SolicitudAfiliacionEmpleador
	(
		TipoIdentificacion VARCHAR(20) COLLATE Latin1_General_CI_AI, 
		NumeroIdentificacion VARCHAR(16) COLLATE Latin1_General_CI_AI,
		DigitoVerificacion SMALLINT NULL,
		RazonSocial VARCHAR(250) COLLATE Latin1_General_CI_AI,
		PrimerNombre VARCHAR(50) COLLATE Latin1_General_CI_AI,
		SegundoNombre VARCHAR(50) COLLATE Latin1_General_CI_AI,
		PrimerApellido VARCHAR(50) COLLATE Latin1_General_CI_AI,
		SegundoApellido VARCHAR(50) COLLATE Latin1_General_CI_AI,
		Solicitud BIGINT NOT NULL,
		Empleador BIGINT NULL,
		EsIntento BIT NULL,
		EsRechazo BIT NULL,
		FechaInicioSolicitud DATETIME NOT NULL,
		FechaCambioEstadoSolicitud DATETIME NOT NULL,
		FechaNotificacion DATETIME NULL,
		Cerrada BIT		
	)

	--Intentos/Rechazos sin registro en Empleador			
	INSERT INTO #SolicitudAfiliacionEmpleador (TipoIdentificacion,NumeroIdentificacion,DigitoVerificacion,RazonSocial,PrimerNombre,SegundoNombre,PrimerApellido,SegundoApellido,Solicitud,Empleador,EsIntento,EsRechazo,FechaInicioSolicitud,FechaCambioEstadoSolicitud,FechaNotificacion,Cerrada)
	SELECT 
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.tipoIdentificacion') AS TipoIdentificacion,
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.numeroIdentificacion') AS NumeroIdentificacion,
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.digitoVerificacion') AS DigitoVerificacion,
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.razonSocial') AS RazonSocial,
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.primerNombre') AS PrimerNombre,
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.segundoNombre') AS SegundoNombre,
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.primerApellido') AS PrimerApellido,
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.segundoApellido') AS SegundoApellido,
		sol.solId AS Solicitud,
		NULL AS Empleador,
		CASE WHEN ISNULL(sol.SolResultadoProceso,'') NOT IN ('APROBADA', 'RECHAZADA') THEN 'true' ELSE 'false' END AS EsIntento,
		CASE WHEN ISNULL(sol.SolResultadoProceso,'') = 'RECHAZADA' THEN 'true' ELSE 'false' END AS EsRechazo,
		wmr.FechaPrimeraRevision AS FechaInicioSolicitud,
		wmr.FechaCambioEstadoAfiliacion AS FechaCambioEstadoSolicitud,
		NULL AS FechaNotificacion,
		CASE WHEN ISNULL(sae.saeEstadoSolicitud,'') = 'CERRADA' THEN 'true' ELSE 'false' END AS Cerrada
	FROM Solicitud sol
	INNER JOIN DatoTemporalSolicitud dts ON dts.dtsSolicitud = sol.solId
	INNER JOIN SolicitudAfiliaciEmpleador sae ON sol.solId = sae.saeSolicitudGlobal
	INNER JOIN (
		SELECT 
			sol.solId, 
			MIN(wmrFirstRevisionDateTime) AS FechaPrimeraRevision,
			MAX(wmrLastRevisionDateTime) AS FechaCambioEstadoAfiliacion
		FROM Solicitud sol
		INNER JOIN WaterMarkedRows wmrSolicitud ON wmrSolicitud.wmrTable = 'Solicitud' AND wmrSolicitud.wmrKeyRowValue = sol.solId AND wmrSolicitud.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision		
		GROUP BY sol.solId
	) wmr ON wmr.solId = sol.solId
	WHERE sae.saeEmpleador IS NULL

	DELETE FROM #SolicitudAfiliacionEmpleador WHERE NumeroIdentificacion IS NULL

	--Intentos/Rechazos con registro en Empleador
	INSERT INTO #SolicitudAfiliacionEmpleador (TipoIdentificacion,NumeroIdentificacion,DigitoVerificacion,RazonSocial,PrimerNombre,SegundoNombre,PrimerApellido,SegundoApellido,Solicitud,Empleador,EsIntento,EsRechazo,FechaInicioSolicitud,FechaCambioEstadoSolicitud,FechaNotificacion,Cerrada)
	SELECT 
		per.perTipoIdentificacion AS TipoIdentificacion,
		per.perNumeroIdentificacion AS  NumeroIdentificacion,
		per.perDigitoVerificacion AS DigitoVerificacion,
		per.perRazonSocial AS RazonSocial,
		per.perPrimerNombre AS PrimerNombre,
		per.perSegundoNombre AS SegundoNombre,
		per.perPrimerApellido AS PrimerApellido,
		per.perSegundoApellido AS SegundoApellido,
		sol.solId AS Solicitud,
		empl.empId AS Empleador,
		CASE WHEN ISNULL(sol.SolResultadoProceso,'') NOT IN ('APROBADA', 'RECHAZADA') THEN 'true' ELSE 'false' END AS EsIntento,
		CASE WHEN ISNULL(sol.SolResultadoProceso,'') = 'RECHAZADA' THEN 'true' ELSE 'false' END AS EsRechazo,
		wmr.FechaPrimeraRevision AS FechaInicioSolicitud,
		wmr.FechaCambioEstadoAfiliacion AS FechaCambioEstadoSolicitud,
		com.comFechaComunicado AS FechaNotificacion,
		CASE WHEN ISNULL(sae.saeEstadoSolicitud,'') = 'CERRADA' THEN 'true' ELSE 'false' END AS Cerrada
	FROM Solicitud sol
	INNER JOIN SolicitudAfiliaciEmpleador sae ON sol.solId = sae.saeSolicitudGlobal
	INNER JOIN Empleador empl ON empl.empId = sae.saeEmpleador
	INNER JOIN Empresa emp ON empl.empEmpresa = emp.empId
	INNER JOIN Persona per ON emp.empPersona = per.perId
	INNER JOIN (
		SELECT 
			sol.solId, 
			MIN(wmrFirstRevisionDateTime) AS FechaPrimeraRevision,
			MAX(wmrLastRevisionDateTime) AS FechaCambioEstadoAfiliacion
		FROM Solicitud sol
		INNER JOIN WaterMarkedRows wmrSolicitud ON wmrSolicitud.wmrTable = 'Solicitud' AND wmrSolicitud.wmrKeyRowValue = sol.solId AND wmrSolicitud.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		GROUP BY sol.solId
	) wmr ON wmr.solId = sol.solId
	LEFT JOIN 
	(
		(
			SELECT 
				com.comSolicitud, 
				MIN(com.comId) AS comId
			FROM Comunicado com
			INNER JOIN WaterMarkedRows wmrComunicado ON wmrComunicado.wmrTable = 'Comunicado' AND wmrComunicado.wmrKeyRowValue = com.comId AND wmrComunicado.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
			GROUP BY com.comSolicitud
		) wmrC
		INNER JOIN Comunicado com ON wmrC.comId = com.comId
	) ON wmrC.comSolicitud = sol.solId
	;

	WITH cteEmpleador
	AS
	(
		SELECT DISTINCT S.TipoIdentificacion,S.NumeroIdentificacion,S.DigitoVerificacion,S.RazonSocial,S.PrimerNombre,S.SegundoNombre,S.PrimerApellido,S.SegundoApellido
		FROM #SolicitudAfiliacionEmpleador S
	)

	MERGE DimEmpleador AS T
	USING cteEmpleador AS S
	ON (	T.demTipoIdentificacion = S.TipoIdentificacion AND
			T.demNumeroIdentificacion = S.NumeroIdentificacion )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (demTipoIdentificacion,demNumeroIdentificacion,demDigitoVerificacion,demRazonSocial,demPrimerNombre,demSegundoNombre,demPrimerApellido,demSegundoApellido)
			VALUES (S.TipoIdentificacion,S.NumeroIdentificacion,S.DigitoVerificacion,S.RazonSocial,S.PrimerNombre,S.SegundoNombre,S.PrimerApellido,S.SegundoApellido);	


	--FactSolicitudAfiliacionEmpleador
	WITH cteSolicitudAfiliacionEmpleador AS
	(
		SELECT 
			dem.demId AS fseDimEmpleador,
			ISNULL(fse.fseDimPeriodo,@DimPeriodoId) AS fseDimPeriodo,
			
			e.Solicitud AS fseSolicitud,
			e.Empleador AS fseEmpleador,

			ISNULL(fse.fseFechaInicioSolicitud, e.FechaInicioSolicitud) AS fseFechaInicioSolicitud,
			e.FechaCambioEstadoSolicitud AS fseFechaCambioEstadoSolicitud,

			CASE WHEN e.FechaNotificacion IS NOT NULL THEN
				CASE DATEDIFF(HH,ISNULL(fse.fseFechaInicioSolicitud, e.FechaInicioSolicitud), e.FechaNotificacion) / 24
				WHEN 0 THEN 1
				WHEN 1 THEN 2
				WHEN 2 THEN 3
				ELSE 4
				END
			ELSE fse.fseDimRangoTiempoRespuestaNotificacion
			END AS fseDimRangoTiempoRespuestaNotificacion,
			ISNULL(
			CASE 
				WHEN e.EsIntento = 1 THEN 1
				WHEN e.EsRechazo = 1 THEN 2
			END, fse.fseDimResultadoSolicitud) AS fseDimResultadoSolicitud,
			ISNULL(e.Cerrada, fse.fseCerrada) AS fseCerrada
		
		FROM #SolicitudAfiliacionEmpleador e
		INNER JOIN DimEmpleador dem ON dem.demTipoIdentificacion = e.TipoIdentificacion AND dem.demNumeroIdentificacion = e.NumeroIdentificacion
		LEFT JOIN --Ultimo periodo registrado
				(
					( SELECT fseSolicitud, MAX(fseId) fseId
					FROM FactSolicitudAfiliacionEmpleador fse
					GROUP BY fse.fseSolicitud
					) fse2
					INNER JOIN FactSolicitudAfiliacionEmpleador fse ON fse2.fseId = fse.fseId
				) ON e.Solicitud = fse.fseSolicitud		
	)

	MERGE FactSolicitudAfiliacionEmpleador AS T
	USING cteSolicitudAfiliacionEmpleador AS S
	ON (T.fseSolicitud = S.fseSolicitud)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fseDimEmpleador,fseDimPeriodo,fseSolicitud,fseEmpleador,fseFechaInicioSolicitud,fseFechaCambioEstadoSolicitud,fseDimRangoTiempoRespuestaNotificacion,fseDimResultadoSolicitud,fseCerrada)
		VALUES (S.fseDimEmpleador,S.fseDimPeriodo,S.fseSolicitud,S.fseEmpleador,S.fseFechaInicioSolicitud,S.fseFechaCambioEstadoSolicitud,S.fseDimRangoTiempoRespuestaNotificacion,S.fseDimResultadoSolicitud,S.fseCerrada)
	WHEN MATCHED
		THEN UPDATE SET T.fseEmpleador = S.fseEmpleador, T.fseFechaInicioSolicitud = S.fseFechaInicioSolicitud,T.fseFechaCambioEstadoSolicitud = S.fseFechaCambioEstadoSolicitud,T.fseDimRangoTiempoRespuestaNotificacion = S.fseDimRangoTiempoRespuestaNotificacion,T.fseDimResultadoSolicitud = S.fseDimResultadoSolicitud,T.fseCerrada = S.fseCerrada

;