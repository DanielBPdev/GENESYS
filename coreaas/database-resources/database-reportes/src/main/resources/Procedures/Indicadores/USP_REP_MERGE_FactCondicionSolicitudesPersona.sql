-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/06/20
-- Description:	Carga la tabla de hechos FactCondicionSolicitudesPersona para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactCondicionSolicitudesPersona
AS

	--FactCondicionPersona
	DECLARE @FechaInicioRevision AS DATETIME
	DECLARE @FechaFinRevision AS DATETIME
	DECLARE @DimPeriodoId AS BIGINT
	DECLARE @RevisionAuditoriaId BIGINT
		
	SELECT  @FechaInicioRevision =MIN(rar.rarRevisionTimeInicio), @FechaFinRevision =MAX(rar.rarRevisionTimeFin)
	FROM RevisionAuditoriaReportes rar
	WHERE rar.rarEncolaProceso = 1
		
	SELECT @DimPeriodoId = dipId FROM DimPeriodo WHERE dipMes = MONTH(@FechaFinRevision) AND dipAnio = YEAR(@FechaFinRevision);

	CREATE TABLE #SolicitudAfiliacionPersona
	(
		TipoIdentificacion VARCHAR(20) COLLATE Latin1_General_CI_AI, 
		NumeroIdentificacion VARCHAR(16) COLLATE Latin1_General_CI_AI,
		PrimerNombre VARCHAR(50) COLLATE Latin1_General_CI_AI,
		SegundoNombre VARCHAR(50) COLLATE Latin1_General_CI_AI,
		PrimerApellido VARCHAR(50) COLLATE Latin1_General_CI_AI,
		SegundoApellido VARCHAR(50) COLLATE Latin1_General_CI_AI,
		Solicitud BIGINT NOT NULL,
		RolAfiliado BIGINT NULL,
		EsIntento BIT NULL,
		EsRechazo BIT NULL,
		FechaInicioSolicitud DATETIME NOT NULL,
		FechaCambioEstadoSolicitud DATETIME NOT NULL,
		FechaNotificacion DATETIME NULL,
		Cerrada BIT		
	)

	--Intentos/Rechazos con registro en RolAfiliado
	INSERT INTO #SolicitudAfiliacionPersona (TipoIdentificacion,NumeroIdentificacion,PrimerNombre,SegundoNombre,PrimerApellido,SegundoApellido,Solicitud,RolAfiliado,EsIntento,EsRechazo,FechaInicioSolicitud,FechaCambioEstadoSolicitud,FechaNotificacion,Cerrada)
	SELECT 
		per.perTipoIdentificacion AS TipoIdentificacion,
		per.perNumeroIdentificacion AS  NumeroIdentificacion,
		per.perPrimerNombre AS PrimerNombre,
		per.perSegundoNombre AS SegundoNombre,
		per.perPrimerApellido AS PrimerApellido,
		per.perSegundoApellido AS SegundoApellido,
		sol.solId AS Solicitud,
		roa.roaId AS RolAfiliado,
		CASE WHEN ISNULL(sol.SolResultadoProceso,'') NOT IN ('APROBADA', 'RECHAZADA') THEN 'true' ELSE 'false' END AS EsIntento,
		CASE WHEN ISNULL(sol.SolResultadoProceso,'') = 'RECHAZADA' THEN 'true' ELSE 'false' END AS EsRechazo,
		wmr.FechaPrimeraRevision AS FechaInicioSolicitud,
		wmr.FechaCambioEstadoAfiliacion AS FechaCambioEstadoSolicitud,
		com.comFechaComunicado AS FechaNotificacion,
		CASE WHEN ISNULL(sap.sapEstadoSolicitud,'') = 'CERRADA' THEN 'true' ELSE 'false' END AS Cerrada
	FROM Solicitud sol
	INNER JOIN SolicitudAfiliacionPersona sap ON sol.solId = sap.sapSolicitudGlobal
	INNER JOIN RolAfiliado roa ON roa.roaId = sap.sapRolAfiliado
	INNER JOIN Afiliado afi ON roa.roaAfiliado = afi.afiId
	INNER JOIN Persona per ON afi.afiPersona = per.perId
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
	) ON wmrC.comSolicitud = sol.solId;
	;

	WITH ctePersona
	AS
	(
		SELECT DISTINCT S.TipoIdentificacion,S.NumeroIdentificacion,S.PrimerNombre,S.SegundoNombre,S.PrimerApellido,S.SegundoApellido
		FROM #SolicitudAfiliacionPersona S
	)

	MERGE DimPersona AS T
	USING ctePersona AS S
	ON (	T.dpeTipoIdentificacion = S.TipoIdentificacion AND
			T.dpeNumeroIdentificacion = S.NumeroIdentificacion )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (dpeTipoIdentificacion,dpeNumeroIdentificacion,dpePrimerNombre,dpeSegundoNombre,dpePrimerApellido,dpeSegundoApellido)
			VALUES (S.TipoIdentificacion,S.NumeroIdentificacion,S.PrimerNombre,S.SegundoNombre,S.PrimerApellido,S.SegundoApellido);	


	--FactSolicitudAfiliacionPersona
	WITH cteSolicitudAfiliacionPersona AS
	(
		SELECT 
			dpe.dpeId AS fspDimPersona,
			ISNULL(fsp.fspDimPeriodo,@DimPeriodoId) AS fspDimPeriodo,
			
			e.Solicitud AS fspSolicitud,
			e.Rolafiliado AS fspRolAfiliado,

			ISNULL(fsp.fspFechaInicioSolicitud, e.FechaInicioSolicitud) AS fspFechaInicioSolicitud,
			e.FechaCambioEstadoSolicitud AS fspFechaCambioEstadoSolicitud,
						
			CASE WHEN e.FechaNotificacion IS NOT NULL THEN
				CASE DATEDIFF(HH,ISNULL(fsp.fspFechaInicioSolicitud, e.FechaInicioSolicitud), e.FechaNotificacion) / 24
				WHEN 0 THEN 1
				WHEN 1 THEN 2
				WHEN 2 THEN 3
				ELSE 4
				END
			ELSE fsp.fspDimRangoTiempoRespuestaNotificacion
			END	AS fspDimRangoTiempoRespuestaNotificacion,
			ISNULL(
			CASE 
				WHEN e.EsIntento = 1 THEN 1
				WHEN e.EsRechazo = 1 THEN 2
			END, fsp.fspDimResultadoSolicitud) AS fspDimResultadoSolicitud,
			ISNULL(
			CASE WHEN ISNULL(e.Cerrada, ISNULL(fsp.fspCerrada, 0)) = 1 THEN 
				CASE WHEN ISNULL(e.EsRechazo,0) = 1 THEN 1 ELSE 2 END
			END, fsp.fspDimEstadoSolicitud) AS fspDimEstadoSolicitud,

			ISNULL(e.Cerrada, fsp.fspCerrada) AS fspCerrada
		
		FROM #SolicitudAfiliacionPersona e
		INNER JOIN DimPersona dpe ON dpe.dpeTipoIdentificacion = e.TipoIdentificacion AND dpe.dpeNumeroIdentificacion = e.NumeroIdentificacion
		LEFT JOIN --Ultimo periodo registrado
				(
					( SELECT fspSolicitud, MAX(fspId) fspId
					FROM FactSolicitudAfiliacionPersona fsp
					GROUP BY fsp.fspSolicitud
					) fsp2
					INNER JOIN FactSolicitudAfiliacionPersona fsp ON fsp2.fspId = fsp.fspId
				) ON e.Solicitud = fsp.fspSolicitud		
	)
	
	MERGE FactSolicitudAfiliacionPersona AS T
	USING cteSolicitudAfiliacionPersona AS S
	ON (T.fspSolicitud = S.fspSolicitud)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fspDimPersona,fspDimPeriodo,fspSolicitud,fspRolAfiliado,fspFechaInicioSolicitud,fspFechaCambioEstadoSolicitud,fspDimRangoTiempoRespuestaNotificacion,fspDimResultadoSolicitud,fspDimEstadoSolicitud,fspCerrada)
		VALUES (S.fspDimPersona,S.fspDimPeriodo,S.fspSolicitud,S.fspRolAfiliado,S.fspFechaInicioSolicitud,S.fspFechaCambioEstadoSolicitud,S.fspDimRangoTiempoRespuestaNotificacion,S.fspDimResultadoSolicitud,S.fspDimEstadoSolicitud,S.fspCerrada)
	WHEN MATCHED
		THEN UPDATE SET T.fspRolAfiliado = S.fspRolAfiliado, T.fspFechaInicioSolicitud = S.fspFechaInicioSolicitud,T.fspFechaCambioEstadoSolicitud = S.fspFechaCambioEstadoSolicitud,T.fspDimRangoTiempoRespuestaNotificacion = S.fspDimRangoTiempoRespuestaNotificacion,T.fspDimResultadoSolicitud = S.fspDimResultadoSolicitud,T.fspDimEstadoSolicitud = S.fspDimEstadoSolicitud,T.fspCerrada = S.fspCerrada

;