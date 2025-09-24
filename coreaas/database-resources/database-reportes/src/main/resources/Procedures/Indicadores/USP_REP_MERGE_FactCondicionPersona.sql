-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/06/19
-- Description:	Carga la tabla de hechos FactCondicionPersona para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactCondicionPersona
AS

	--FactCondicionPersona
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

	DECLARE @HomologEstados AS TABLE (estadoCore VARCHAR(50), depId TINYINT)
	DECLARE @HomologNaturalezaPersona AS TABLE (naturalezaPersona VARCHAR(100), dnpId TINYINT)
	
--	drop table CondicionPersona
	CREATE TABLE #CondicionPersona
	(
		Id BIGINT IDENTITY,
		TipoIdentificacion VARCHAR(20) COLLATE Latin1_General_CI_AI, 
		NumeroIdentificacion VARCHAR(16) COLLATE Latin1_General_CI_AI,
		PrimerNombre VARCHAR(50)  COLLATE Latin1_General_CI_AI,
		SegundoNombre VARCHAR(50)  COLLATE Latin1_General_CI_AI,
		PrimerApellido VARCHAR(50)  COLLATE Latin1_General_CI_AI,
		SegundoApellido VARCHAR(50)  COLLATE Latin1_General_CI_AI,
		EstadoAfiliacion VARCHAR(50) COLLATE Latin1_General_CI_AI,
		FechaCambioEstadoAfiliacion DATETIME,
		FechaPrimeraRevision DATETIME,
		CanalRecepcion VARCHAR(21) COLLATE Latin1_General_CI_AI,
		FechaUltimoCanalRecepcion DATETIME,
		UltimoResultadoProceso VARCHAR(22) COLLATE Latin1_General_CI_AI,
		UltimoEstadoSolicitud VARCHAR(50) COLLATE Latin1_General_CI_AI,
		CreadoPorPila BIT,
		Aportes BIT,
		FechaAportes DATETIME,
		NaturalezaPersona VARCHAR(100) COLLATE Latin1_General_CI_AI

	);

	IF @DimPeriodoAnteriorId IS NOT NULL
	BEGIN
		IF NOT EXISTS (SELECT 1 FROM FactCondicionPersona WHERE fcpDimPeriodo = @DimPeriodoId AND fcpActivoPeriodoAnterior = 'true')
		BEGIN
			WITH ctePersonasActivasPeriodoAnterior AS (
				SELECT fcpDimPersona,@DimPeriodoId AS fcpDimPeriodo,fcpDimCanal,fcpDimTipoAfiliacion,fcpDimNaturalezaPersona,fcpDimEstadoPersona,'false' AS fcpAccionActivacion,'true' AS fcpActivoPeriodoAnterior
				FROM FactCondicionPersona fcp			
				WHERE fcp.fcpDimPeriodo = @DimPeriodoAnteriorId
				AND fcp.fcpDimEstadoPersona = 1
			)
			INSERT INTO FactCondicionPersona (fcpDimPersona,fcpDimPeriodo,fcpDimCanal,fcpDimTipoAfiliacion,fcpDimNaturalezaPersona,fcpDimEstadoPersona,fcpAccionActivacion,fcpActivoPeriodoAnterior)
			SELECT fcpDimPersona,fcpDimPeriodo,fcpDimCanal,fcpDimTipoAfiliacion,fcpDimNaturalezaPersona,fcpDimEstadoPersona,fcpAccionActivacion,fcpActivoPeriodoAnterior
			FROM ctePersonasActivasPeriodoAnterior
		END
	END;


	--Personas que cambiaron su estado de afiliación durante el incremental de auditoria	
	WITH ctePersonaSub AS (
		SELECT per.perId, per.perTipoIdentificacion AS TipoIdentificacion, per.perNumeroIdentificacion AS NumeroIdentificacion, eae.eaeEstadoAfiliacion AS Estadoafiliacion, eae.eaeFechaCambioEstado AS FechaCambioEstadoAfiliacion, eae1.eaeFechaCambioEstado AS FechaPrimeraRevision, 'TRABAJADOR_DEPENDIENTE' AS NaturalezaPersona, per.perCreadoPorPila AS CreadoPorPila
		FROM Persona per
		INNER JOIN (SELECT eae.eaePersona, MAX(eaeId) eaeId, MIN(eae.eaeFechaCambioEstado) eaeFechaCambioEstado
					FROM EstadoAfiliacionPersonaEmpresa eae 
					WHERE eae.eaeFechaCambioEstado BETWEEN @FechaInicioRevision AND @FechaFinRevision
					GROUP BY eae.eaePersona) eae1 ON per.perId = eae1.eaePersona
		INNER JOIN EstadoAfiliacionPersonaEmpresa eae ON eae1.eaeId = eae.eaeId
		UNION ALL
		SELECT per.perId, per.perTipoIdentificacion AS TipoIdentificacion, per.perNumeroIdentificacion AS NumeroIdentificacion, eai.eaiEstadoAfiliacion AS Estadoafiliacion, eai.eaiFechaCambioEstado AS FechaCambioEstadoAfiliacion, eai1.eaiFechaCambioEstado AS FechaPrimeraRevision, 'TRABAJADOR_INDEPENDIENTE' AS NaturalezaPersona, per.perCreadoPorPila AS CreadoPorPila
		FROM Persona per
		INNER JOIN (SELECT eai.eaiPersona, MAX(eaiId) eaiId, MIN(eai.eaiFechaCambioEstado) eaiFechaCambioEstado
					FROM EstadoAfiliacionPersonaIndependiente eai 
					WHERE eai.eaiFechaCambioEstado BETWEEN @FechaInicioRevision AND @FechaFinRevision
					GROUP BY eai.eaiPersona) eai1 ON per.perId = eai1.eaiPersona
		INNER JOIN EstadoAfiliacionPersonaIndependiente eai ON eai1.eaiId = eai.eaiId
		UNION ALL
		SELECT per.perId, per.perTipoIdentificacion AS TipoIdentificacion, per.perNumeroIdentificacion AS NumeroIdentificacion, eap.eapEstadoAfiliacion AS Estadoafiliacion, eap.eapFechaCambioEstado AS FechaCambioEstadoAfiliacion, eap1.eapFechaCambioEstado AS FechaPrimeraRevision, 'PENSIONADO' AS NaturalezaPersona, per.perCreadoPorPila AS CreadoPorPila
		FROM Persona per
		INNER JOIN (SELECT eap.eapPersona, MAX(eapId) eapId, MIN(eap.eapFechaCambioEstado) eapFechaCambioEstado
					FROM EstadoAfiliacionPersonaPensionado eap 
					WHERE eap.eapFechaCambioEstado BETWEEN @FechaInicioRevision AND @FechaFinRevision
					GROUP BY eap.eapPersona) eap1 ON per.perId = eap1.eapPersona
		INNER JOIN EstadoAfiliacionPersonaPensionado eap ON eap1.eapId = eap.eapId
	)
	
	SELECT	*,
			ROW_NUMBER() OVER(ORDER BY perId, NaturalezaPersona  DESC) AS Id
	INTO #ctePersonaId
	FROM ctePersonaSub cp;

	WITH cteEstadoPersonaCaja AS
	(	
		SELECT per.perId, per.perTipoIdentificacion AS TipoIdentificacion, per.perNumeroIdentificacion AS NumeroIdentificacion, eac.eacEstadoAfiliacion AS Estadoafiliacion, eac.eacFechaCambioEstado AS FechaCambioEstadoAfiliacion, per.perCreadoPorPila AS CreadoPorPila
		FROM Persona per
		INNER JOIN (SELECT eac.eacPersona, MAX(eacId) eacId
					FROM EstadoAfiliacionPersonaCaja eac 
					WHERE eac.eacFechaCambioEstado BETWEEN @FechaInicioRevision AND @FechaFinRevision
					GROUP BY eac.eacPersona) eac1 ON per.perId = eac1.eacPersona
		INNER JOIN EstadoAfiliacionPersonaCaja eac ON eac1.eacId = eac.eacId
	),		
	ctePersona AS (
		SELECT *
		FROM #ctePersonaId
		WHERE Id IN (
			SELECT MAX(CP.Id) Id
			FROM #ctePersonaId CP
			INNER JOIN (
				SELECT CP.TipoIdentificacion, CP.NumeroIdentificacion, MAX(CP.FechaCambioEstadoAfiliacion) Fecha
				FROM #ctePersonaId CP
				INNER JOIN cteEstadoPersonaCaja CPC ON CP.perId = CPC.perId AND CP.EstadoAfiliacion = CPC.EstadoAfiliacion
				GROUP BY CP.TipoIdentificacion, CP.NumeroIdentificacion
				) CP2 ON CP.TipoIdentificacion = CP2.TipoIdentificacion AND CP.NumeroIdentificacion = CP2.NumeroIdentificacion AND CP.FechaCambioEstadoAfiliacion = CP2.Fecha
			GROUP BY CP.TipoIdentificacion, CP.NumeroIdentificacion	
		)
	)

	
	MERGE #CondicionPersona AS T
	USING ctePersona AS S
	ON (	T.TipoIdentificacion = S.TipoIdentificacion AND
			T.NumeroIdentificacion = S.NumeroIdentificacion AND
			T.NaturalezaPersona = S.NaturalezaPersona )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (TipoIdentificacion,NumeroIdentificacion,EstadoAfiliacion, FechaCambioEstadoAfiliacion, FechaPrimeraRevision, NaturalezaPersona, CreadoPorPila)
			VALUES (S.TipoIdentificacion, S.NumeroIdentificacion, S.EstadoAfiliacion, S.FechaCambioEstadoAfiliacion, S.FechaPrimeraRevision, S.NaturalezaPersona, S.CreadoPorPila)
	WHEN MATCHED
		THEN UPDATE SET T.FechaCambioEstadoAfiliacion = S.FechaCambioEstadoAfiliacion, T.FechaPrimeraRevision = S.FechaPrimeraRevision;

	DROP TABLE #ctePersonaId;

	--Personas con Aportes registrados durante el incremental de auditoria

	WITH cteAportes AS
	(
		SELECT per.perTipoIdentificacion AS TipoIdentificacion, per.perNumeroIdentificacion AS NumeroIdentificacion, apd.apdTipoCotizante AS NaturalezaPersona, MAX(wmrAporteDetallado.wmrLastRevisionDateTime) FechaAportes 
		FROM Persona per
		INNER JOIN AporteDetallado apd ON  per.perId = apd.apdPersona
		INNER JOIN WaterMarkedRows wmrAporteDetallado ON wmrAporteDetallado.wmrTable = 'AporteDetallado' AND wmrAporteDetallado.wmrKeyRowValue = apd.apdId AND wmrAporteDetallado.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		GROUP BY per.perId, per.perTipoIdentificacion, per.perNumeroIdentificacion, apd.apdTipoCotizante
	)

	MERGE #CondicionPersona AS T
	USING cteAportes AS S
	ON (	T.TipoIdentificacion = S.TipoIdentificacion AND
			T.NumeroIdentificacion = S.NumeroIdentificacion AND
			T.NaturalezaPersona = S.NaturalezaPersona )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (TipoIdentificacion,NumeroIdentificacion,NaturalezaPersona,Aportes,FechaAportes)
			VALUES (S.TipoIdentificacion,S.NumeroIdentificacion,NaturalezaPersona,'true',S.FechaAportes)
	WHEN MATCHED
		THEN UPDATE SET T.Aportes = 'true', T.FechaAportes = S.FechaAportes;
			

	-- Se halla el canal de la ultima solicitud modificada cronologicamente	
	WITH cteCanalSub AS
	(
		SELECT 
			per.perId,
			per.perTipoIdentificacion AS TipoIdentificacion, 
			per.perNumeroIdentificacion AS NumeroIdentificacion,
			sol.solCanalRecepcion AS CanalRecepcion,
			wmrSolicitud.wmrLastRevisionDateTime AS FechaUltimoCanalRecepcion,
			sol.solResultadoProceso AS UltimoResultadoProceso,
			sap.sapEstadoSolicitud AS UltimoEstadoSolicitud,
			roaTipoAfiliado AS NaturalezaPersona
		FROM Solicitud sol
		INNER JOIN SolicitudAfiliacionPersona sap ON sol.solId = sap.sapSolicitudGlobal		
		INNER JOIN (
			SELECT 
				sap.sapRolAfiliado, MAX(wmr.wmrId) wmrId
			FROM Solicitud sol
			INNER JOIN SolicitudAfiliacionPersona sap ON sol.solId = sap.sapSolicitudGlobal
			INNER JOIN (SELECT wmrKeyRowValue, MAX(wmrId) wmrId
						FROM WaterMarkedRows
						WHERE 1=1 
						AND wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
						AND  wmrTable = 'Solicitud'
						GROUP BY wmrKeyRowValue) wmr ON wmr.wmrKeyRowValue = sol.solId
			GROUP BY sap.sapRolAfiliado
		) wmr ON wmr.sapRolAfiliado = sap.sapRolAfiliado
		INNER JOIN WaterMarkedRows wmrSolicitud ON wmrSolicitud.wmrId = wmr.wmrId AND sol.solId = wmrSolicitud.wmrKeyRowValue
		INNER JOIN RolAfiliado roa ON sap.sapRolAfiliado = roa.roaId
		INNER JOIN Afiliado afi ON roa.roaAfiliado = afi.afiId
		INNER JOIN Persona per ON afiPersona = per.perId
	)

	SELECT	*,
			ROW_NUMBER() OVER(ORDER BY perId, NaturalezaPersona  DESC) AS Id
	INTO #cteCanalId
	FROM cteCanalSub;

	WITH cteCanal AS
	(
		SELECT *
		FROM #cteCanalId
		WHERE Id IN (
			SELECT MAX(CP.Id) Id
			FROM #cteCanalId CP
			INNER JOIN (
				SELECT CP2.TipoIdentificacion, CP2.NumeroIdentificacion, MAX(CP2.FechaUltimoCanalRecepcion) Fecha
				FROM #cteCanalId CP2
				GROUP BY CP2.TipoIdentificacion, CP2.NumeroIdentificacion
				) CP1 ON CP.TipoIdentificacion = CP1.TipoIdentificacion AND CP.NumeroIdentificacion = CP1.NumeroIdentificacion AND CP.FechaUltimoCanalRecepcion = CP1.Fecha
			GROUP BY CP.TipoIdentificacion, CP.NumeroIdentificacion
		)
	)
	
	MERGE #CondicionPersona AS T
	USING cteCanal AS S
	ON (	T.TipoIdentificacion = S.TipoIdentificacion AND
			T.NumeroIdentificacion = S.NumeroIdentificacion AND
			T.NaturalezaPersona = S.NaturalezaPersona )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (TipoIdentificacion,NumeroIdentificacion,NaturalezaPersona,CanalRecepcion,FechaUltimoCanalRecepcion,UltimoResultadoProceso,UltimoEstadoSolicitud)
			VALUES (S.TipoIdentificacion,S.NumeroIdentificacion,S.NaturalezaPersona,S.CanalRecepcion,S.FechaUltimoCanalRecepcion,S.UltimoResultadoProceso,S.UltimoEstadoSolicitud)
	WHEN MATCHED
		THEN UPDATE SET T.CanalRecepcion = S.CanalRecepcion, T.FechaUltimoCanalRecepcion = S.FechaUltimoCanalRecepcion, T.UltimoResultadoProceso = S.UltimoResultadoProceso, T.UltimoEstadoSolicitud = S.UltimoEstadoSolicitud;

	DROP TABLE #cteCanalId;

	--Se eliminan duplicados y se deja solo el registro con modificacion mas reciente
	DELETE #CondicionPersona
	WHERE Id NOT IN (
		SELECT MAX(Id) Id
		FROM #CondicionPersona CP
		INNER JOIN (
			SELECT CP2.TipoIdentificacion, CP2.NumeroIdentificacion, MAX(COALESCE(CP2.FechaAportes, CP2.FechaCambioEstadoAfiliacion, CP2.FechaUltimoCanalRecepcion)) Fecha
			FROM #CondicionPersona CP2
			GROUP BY CP2.TipoIdentificacion, CP2.NumeroIdentificacion
			) CP1 ON CP.TipoIdentificacion = CP1.TipoIdentificacion AND CP.NumeroIdentificacion = CP1.NumeroIdentificacion AND COALESCE(CP.FechaAportes, CP.FechaCambioEstadoAfiliacion, CP.FechaUltimoCanalRecepcion) = CP1.Fecha
		GROUP BY CP.TipoIdentificacion, CP.NumeroIdentificacion
	)
	
	
	--Se actualizan datos personales
	UPDATE p
	SET 
		PrimerNombre = per.perPrimerNombre,
		SegundoNombre = per.perSegundoNombre,
		PrimerApellido = per.perPrimerApellido,
		SegundoApellido = per.perSegundoApellido
	FROM #CondicionPersona p
	INNER JOIN Persona per ON p.TipoIdentificacion = per.perTipoIdentificacion AND p.NumeroIdentificacion = per.perNumeroIdentificacion	

	MERGE DimPersona AS T
	USING #CondicionPersona AS S
	ON (	T.dpeTipoIdentificacion = S.TipoIdentificacion AND
			T.dpeNumeroIdentificacion = S.NumeroIdentificacion )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (dpeTipoIdentificacion,dpeNumeroIdentificacion,dpePrimerNombre,dpeSegundoNombre,dpePrimerApellido,dpeSegundoApellido)
			VALUES (S.TipoIdentificacion,S.NumeroIdentificacion,S.PrimerNombre,S.SegundoNombre,S.PrimerApellido,S.SegundoApellido)
	WHEN MATCHED
		THEN UPDATE SET dpePrimerNombre = S.PrimerNombre, dpeSegundoNombre = S.SegundoNombre, dpePrimerApellido = S.PrimerApellido, dpeSegundoApellido = S.SegundoApellido;


	INSERT INTO @HomologEstados
	VALUES
		(NULL,4),
		('ACTIVO',1),
		('INACTIVO', 2),
		('NO_FORMALIZADO_RETIRADO_CON_APORTES',3),
		('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES',3),
		('NO_FORMALIZADO_CON_INFORMACION',3)

	
	INSERT INTO @HomologNaturalezaPersona
	VALUES
		('TRABAJADOR_DEPENDIENTE',1),
		('TRABAJADOR_INDEPENDIENTE', 2),
		('PENSIONADO',3)
		
	;
	WITH cteCondicionPersona AS
	(
		SELECT 
			dpe.dpeId AS fcpDimPersona,
			@DimPeriodoId AS fcpDimPeriodo,
			
			--Fecha de la ultima modificacion de Canal de recepcion
			ISNULL(p.FechaUltimoCanalRecepcion, fcp.fcpFechaUltimoCanalRecepcion) AS fcpFechaUltimoCanalRecepcion,
			ISNULL(p.FechaPrimeraRevision, fcp.fcpFechaPrimeraRevision) AS fcpFechaPrimeraRevision,
			ISNULL(p.FechaCambioEstadoAfiliacion, fcp.fcpFechaCambioEstadoAfiliacion) AS fcpFechaCambioEstadoAfiliacion,
			ISNULL(p.FechaAportes, fcp.fcpFechaAportes) AS fcpFechaAportes,
			
			CASE WHEN p.FechaUltimoCanalRecepcion IS NOT NULL THEN dic.dicId ELSE 
				CASE WHEN p.CreadoPorPila = 'true' THEN 3 ELSE fcp.fcpDimCanal END
				END AS fcpDimCanal,
			
			ISNULL(CASE WHEN p.EstadoAfiliacion = 'ACTIVO'
					THEN CASE WHEN fcp1.fcpId IS NULL THEN 1 ELSE 2 END
				END, fcp.fcpDimTipoAfiliacion) AS fcpDimTipoAfiliacion,

			(SELECT dnpId FROM @HomologNaturalezaPersona dnp WHERE dnp.naturalezaPersona = p.NaturalezaPersona) AS fcpDimNaturalezaPersona,
			
			CASE WHEN p.EstadoAfiliacion IS NOT NULL THEN
				(SELECT depId FROM @HomologEstados dep WHERE ISNULL(dep.estadoCore,'') = p.EstadoAfiliacion)
				ELSE ISNULL(fcp.fcpDimEstadoPersona, 4) END AS fcpDimEstadoPersona,

			CASE WHEN ISNULL(fcp.fcpDimPagoAportes,2) = 2  THEN --No pago aportes ultima coincidencia
				CASE WHEN p.Aportes = 'true' THEN 1 ELSE 2 END
			ELSE fcp.fcpDimPagoAportes END AS fcpDimPagoAportes,

			CAST((CASE WHEN p.EstadoAfiliacion = 'ACTIVO' AND ISNULL(fcp.fcpDimEstadoPersona,0) <> 1 THEN 'true' ELSE 'false' END) AS BIT) AS fcpAccionActivacion

		FROM #CondicionPersona p
		INNER JOIN DimPersona dpe ON dpe.dpeTipoIdentificacion = p.TipoIdentificacion AND dpe.dpeNumeroIdentificacion = p.NumeroIdentificacion
		LEFT JOIN DimCanal dic ON p.CanalRecepcion = dic.dicDescripcion
		LEFT JOIN --Ultimo periodo Activo de diferente Periodo
				( SELECT fcpDimPersona, MAX(fcpId) fcpId
				FROM FactCondicionPersona fcp
				WHERE fcp.fcpDimPeriodo <> @DimPeriodoId
				AND fcp.fcpDimEstadoPersona = 1 --Activo
				GROUP BY fcp.fcpDimPersona
				) fcp1
				ON dpe.dpeId = fcp1.fcpDimPersona	
		LEFT JOIN --Ultimo periodo registrado
				(
					( SELECT fcpDimPersona, MAX(fcpId) fcpId
					FROM FactCondicionPersona fcp
					GROUP BY fcp.fcpDimPersona
					) fcp2
					INNER JOIN FactCondicionPersona fcp ON fcp2.fcpId = fcp.fcpId						
				) ON dpe.dpeId = fcp.fcpDimPersona	
		
		
	)
	
	MERGE FactCondicionPersona AS T
	USING cteCondicionPersona AS S
	ON (T.fcpDimPersona = S.fcpDimPersona AND T.fcpDimPeriodo = S.fcpDimPeriodo)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fcpDimPersona,fcpDimPeriodo,fcpDimCanal,fcpDimTipoAfiliacion,fcpDimNaturalezaPersona,fcpDimEstadoPersona,fcpDimPagoAportes,fcpFechaCambioEstadoAfiliacion,fcpFechaPrimeraRevision,fcpFechaUltimoCanalRecepcion,fcpFechaAportes,fcpAccionActivacion)
		VALUES (S.fcpDimPersona,S.fcpDimPeriodo,S.fcpDimCanal,S.fcpDimTipoAfiliacion,S.fcpDimNaturalezaPersona,S.fcpDimEstadoPersona,S.fcpDimPagoAportes,S.fcpFechaCambioEstadoAfiliacion,S.fcpFechaPrimeraRevision,S.fcpFechaUltimoCanalRecepcion,S.fcpFechaAportes,S.fcpAccionActivacion)
	WHEN MATCHED
		THEN UPDATE SET T.fcpDimCanal = S.fcpDimCanal, T.fcpDimTipoAfiliacion = S.fcpDimTipoAfiliacion,T.fcpDimNaturalezaPersona = S.fcpDimNaturalezaPersona,T.fcpDimEstadoPersona = S.fcpDimEstadoPersona,T.fcpDimPagoAportes = S.fcpDimPagoAportes, T.fcpFechaCambioEstadoAfiliacion = S.fcpFechaCambioEstadoAfiliacion, T.fcpFechaPrimeraRevision = S.fcpFechaPrimeraRevision, T.fcpFechaUltimoCanalRecepcion = S.fcpFechaUltimoCanalRecepcion, T.fcpFechaAportes = S.fcpFechaAportes, T.fcpAccionActivacion = S.fcpAccionActivacion	

;