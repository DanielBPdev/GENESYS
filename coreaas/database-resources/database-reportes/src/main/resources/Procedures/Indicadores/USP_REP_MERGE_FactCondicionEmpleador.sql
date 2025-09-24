-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/04/14
-- Description:	Carga la tabla de hechos FactCondicionEmpleador para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactCondicionEmpleador
AS

	--FactCondicionEmpleador
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

	DECLARE @HomologEstados AS TABLE (estadoCore VARCHAR(50), deeId TINYINT)
	DECLARE @HomologNaturalezaJuridica AS TABLE (naturalezaJuridica VARCHAR(100), deeId TINYINT)
	
--	drop table #CondicionEmpleador
	CREATE TABLE #CondicionEmpleador
	(
		TipoIdentificacion VARCHAR(20) COLLATE Latin1_General_CI_AI, 
		NumeroIdentificacion VARCHAR(16) COLLATE Latin1_General_CI_AI,
		DigitoVerificacion SMALLINT NULL,
		RazonSocial VARCHAR(250)  COLLATE Latin1_General_CI_AI,
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
		NumeroTotalTrabajadores INT,
		NaturalezaJuridica VARCHAR(100) COLLATE Latin1_General_CI_AI,
		SedeDestinatario VARCHAR(2) COLLATE Latin1_General_CI_AI

	);
	
	IF @DimPeriodoAnteriorId IS NOT NULL
	BEGIN
		IF NOT EXISTS (SELECT 1 FROM FactCondicionEmpleador WHERE fceDimPeriodo = @DimPeriodoId AND fceActivoPeriodoAnterior = 'true')
		BEGIN
			WITH cteEmpleadorsActivasPeriodoAnterior AS (
				SELECT fceDimEmpleador,@DimPeriodoId AS fceDimPeriodo,fceDimCanal,fceDimSede,fceDimTipoAfiliacion,fceDimTamanioEmpleador,fceDimNaturalezaJuridica,fceDimEstadoEmpleador,'false' AS fceAccionActivacion,'true' AS fceActivoPeriodoAnterior
				FROM FactCondicionEmpleador fce			
				WHERE fce.fceDimPeriodo = @DimPeriodoAnteriorId
				AND fce.fceDimEstadoEmpleador = 1
			)
			INSERT INTO FactCondicionEmpleador (fceDimEmpleador,fceDimPeriodo,fceDimCanal,fceDimSede,fceDimTipoAfiliacion,fceDimTamanioEmpleador,fceDimNaturalezaJuridica,fceDimEstadoEmpleador,fceAccionActivacion,fceActivoPeriodoAnterior)
			SELECT fceDimEmpleador,fceDimPeriodo,fceDimCanal,fceDimSede,fceDimTipoAfiliacion,fceDimTamanioEmpleador,fceDimNaturalezaJuridica,fceDimEstadoEmpleador,fceAccionActivacion,fceActivoPeriodoAnterior
			FROM cteEmpleadorsActivasPeriodoAnterior
		END
	END;

	--Empleadores que cambiaron su estado de afiliación durante el incremental de auditoria

	WITH cteEmpleador AS (
		SELECT per.perTipoIdentificacion AS TipoIdentificacion, per.perNumeroIdentificacion AS NumeroIdentificacion, eec.eecEstadoAfiliacion AS EstadoAfiliacion, eec.eecFechaCambioEstado AS FechaCambioEstadoAfiliacion, eec1.eecFechaCambioEstado AS FechaPrimeraRevision, emp.empCreadoPorPila AS CreadoPorPila
		FROM Persona per
		INNER JOIN Empresa emp ON per.perId = emp.empPersona
		INNER JOIN (SELECT eec.eecPersona, MAX(eecId) eecId, MIN(eec.eecFechaCambioEstado) eecFechaCambioEstado
					FROM EstadoAfiliacionEmpleadorCaja eec 
					WHERE eec.eecFechaCambioEstado BETWEEN @FechaInicioRevision AND @FechaFinRevision
					GROUP BY eec.eecPersona) eec1 ON per.perId = eec1.eecPersona
		INNER JOIN EstadoAfiliacionEmpleadorCaja eec ON eec1.eecId = eec.eecId
	)
	
	MERGE #CondicionEmpleador AS T
	USING cteEmpleador AS S
	ON (	T.TipoIdentificacion = S.TipoIdentificacion AND
			T.NumeroIdentificacion = S.NumeroIdentificacion
		)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (TipoIdentificacion,NumeroIdentificacion,EstadoAfiliacion, FechaCambioEstadoAfiliacion, FechaPrimeraRevision, CreadoPorPila)
			VALUES (S.TipoIdentificacion, S.NumeroIdentificacion, S.EstadoAfiliacion, S.FechaCambioEstadoAfiliacion, S.FechaPrimeraRevision, S.CreadoPorPila)
	WHEN MATCHED
		THEN UPDATE SET T.FechaCambioEstadoAfiliacion = S.FechaCambioEstadoAfiliacion, T.FechaPrimeraRevision = S.FechaPrimeraRevision;


	--Empresas con Aportes registrados durante el incremental de auditoria

	WITH cteAportes AS
	(
		SELECT per.perTipoIdentificacion AS TipoIdentificacion, per.perNumeroIdentificacion AS NumeroIdentificacion, MAX(wmrAporteGeneral.wmrLastRevisionDateTime) FechaAportes 
		FROM Persona per
		INNER JOIN Empresa emp ON per.perId = emp.empPersona
		INNER JOIN AporteGeneral apg ON  emp.empId = apg.apgEmpresa
		INNER JOIN WaterMarkedRows wmrAporteGeneral ON wmrAporteGeneral.wmrTable = 'AporteGeneral' AND wmrAporteGeneral.wmrKeyRowValue = apg.apgId AND wmrAporteGeneral.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		GROUP BY per.perTipoIdentificacion, per.perNumeroIdentificacion
	)

	MERGE #CondicionEmpleador AS T
	USING cteAportes AS S
	ON (	T.TipoIdentificacion = S.TipoIdentificacion AND
			T.NumeroIdentificacion = S.NumeroIdentificacion )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (TipoIdentificacion,NumeroIdentificacion,Aportes,FechaAportes)
			VALUES (S.TipoIdentificacion,S.NumeroIdentificacion,'true',S.FechaAportes)
	WHEN MATCHED
		THEN UPDATE SET T.Aportes = 'true', T.FechaAportes = S.FechaAportes;

	-- Se halla el canal de la ultima solicitud modificada cronologicamente

	WITH cteCanal AS
	(
		SELECT 
			per.perTipoIdentificacion AS TipoIdentificacion, 
			per.perNumeroIdentificacion AS NumeroIdentificacion,
			sol.solCanalRecepcion AS CanalRecepcion,
			wmrSolicitud.wmrLastRevisionDateTime AS FechaUltimoCanalRecepcion,
			sol.solResultadoProceso AS UltimoResultadoProceso,
			sae.saeEstadoSolicitud AS UltimoEstadoSolicitud,
			solSedeDestinatario AS SedeDestinatario
		FROM Solicitud sol
		INNER JOIN SolicitudAfiliaciEmpleador sae ON sol.solId = sae.saeSolicitudGlobal		
		INNER JOIN (
			SELECT 
				sae.saeEmpleador, MAX(wmr.wmrId) wmrId
			FROM Solicitud sol
			INNER JOIN SolicitudAfiliaciEmpleador sae ON sol.solId = sae.saeSolicitudGlobal
			INNER JOIN (SELECT wmrKeyRowValue, MAX(wmrId) wmrId
						FROM WaterMarkedRows
						WHERE 1=1 
						AND wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
						AND  wmrTable = 'Solicitud'
						GROUP BY wmrKeyRowValue) wmr ON wmr.wmrKeyRowValue = sol.solId
			GROUP BY sae.saeEmpleador
		) wmr ON wmr.saeEmpleador = sae.saeEmpleador
		INNER JOIN WaterMarkedRows wmrSolicitud ON wmrSolicitud.wmrId = wmr.wmrId AND sol.solId = wmrSolicitud.wmrKeyRowValue
		INNER JOIN Empleador empl ON sae.saeEmpleador = empl.empId
		INNER JOIN Empresa emp ON empl.empEmpresa = emp.empId
		INNER JOIN Persona per ON empPersona = per.perId
	)	
	
	MERGE #CondicionEmpleador AS T
	USING cteCanal AS S
	ON (	T.TipoIdentificacion = S.TipoIdentificacion AND
			T.NumeroIdentificacion = S.NumeroIdentificacion )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (TipoIdentificacion,NumeroIdentificacion,CanalRecepcion,FechaUltimoCanalRecepcion,UltimoResultadoProceso,UltimoEstadoSolicitud,SedeDestinatario)
			VALUES (S.TipoIdentificacion,S.NumeroIdentificacion,S.CanalRecepcion,S.FechaUltimoCanalRecepcion,S.UltimoResultadoProceso,S.UltimoEstadoSolicitud,S.SedeDestinatario)
	WHEN MATCHED
		THEN UPDATE SET T.CanalRecepcion = S.CanalRecepcion, T.FechaUltimoCanalRecepcion = S.FechaUltimoCanalRecepcion, T.UltimoResultadoProceso = S.UltimoResultadoProceso, T.UltimoEstadoSolicitud = S.UltimoEstadoSolicitud, T.SedeDestinatario = S.SedeDestinatario;

	/*
	--solicitudes con solo datotemporal sin asociacion a empleador
	SELECT 
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.tipoIdentificacion') ,
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.numeroIdentificacion') ,
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.primerNombre') ,
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.segundoNombre') ,
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.primerApellido') ,
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.segundoApellido') ,
		JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)), '$.dto.empleador.persona.razonSocial') 
	FROM Solicitud sol
	INNER JOIN DatoTemporalSolicitud dts ON dts.dtsSolicitud = sol.solId
	INNER JOIN (
		SELECT 
			sol.solId, 
			MIN(wmrFirstRevisionDateTime) AS FechaPrimeraRevision,
			MAX(wmrLastRevisionDateTime) AS FechaCambioEstadoAfiliacion
		FROM Solicitud sol
		INNER JOIN SolicitudAfiliaciEmpleador sae ON sol.solId = sae.saeSolicitudGlobal
		INNER JOIN DatoTemporalSolicitud dts ON dts.dtsSolicitud = sol.solId
		INNER JOIN WaterMarkedRows wmrSolicitud ON wmrSolicitud.wmrTable = 'Solicitud' AND wmrSolicitud.wmrKeyRowValue = sol.solId AND wmrSolicitud.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision
		WHERE sae.saeEmpleador IS NULL
	) wmr ON wmr.solId = wmr.solId
	*/

	
	UPDATE e
	SET 
		RazonSocial = per.perRazonSocial,
		DigitoVerificacion = per.perDigitoVerificacion,
		PrimerNombre = per.perPrimerNombre,
		SegundoNombre = per.perSegundoNombre,
		PrimerApellido = per.perPrimerApellido,
		SegundoApellido = per.perSegundoApellido,
		NumeroTotalTrabajadores = ISNULL(empl.empNumeroTotalTrabajadores, e.NumeroTotalTrabajadores),
		NaturalezaJuridica = emp.empNaturalezaJuridica
	FROM #CondicionEmpleador e
	INNER JOIN Persona per ON e.TipoIdentificacion = per.perTipoIdentificacion AND e.NumeroIdentificacion = per.perNumeroIdentificacion
	INNER JOIN Empresa emp ON emp.empPersona = per.perId
	LEFT JOIN Empleador empl ON emp.empId = empl.empEmpresa

	MERGE DimEmpleador AS T
	USING #CondicionEmpleador AS S
	ON (	T.demTipoIdentificacion = S.TipoIdentificacion AND
			T.demNumeroIdentificacion = S.NumeroIdentificacion )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (demTipoIdentificacion,demNumeroIdentificacion,demDigitoVerificacion,demRazonSocial,demPrimerNombre,demSegundoNombre,demPrimerApellido,demSegundoApellido)
			VALUES (S.TipoIdentificacion,S.NumeroIdentificacion,S.DigitoVerificacion,S.RazonSocial,S.PrimerNombre,S.SegundoNombre,S.PrimerApellido,S.SegundoApellido)
	WHEN MATCHED
		THEN UPDATE SET demDigitoVerificacion = S.DigitoVerificacion, demRazonSocial = S.RazonSocial,demPrimerNombre = S.PrimerNombre, demSegundoNombre = S.SegundoNombre, demPrimerApellido = S.PrimerApellido, demSegundoApellido = S.SegundoApellido;


	INSERT INTO @HomologEstados
	VALUES
		(NULL,4),
		('ACTIVO',1),
		('INACTIVO', 2),
		('NO_FORMALIZADO_RETIRADO_CON_APORTES',3),
		('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES',3),
		('NO_FORMALIZADO_CON_INFORMACION',3)

	
	INSERT INTO @HomologNaturalezaJuridica
	VALUES
		('PUBLICA',1),
		('PRIVADA', 2),
		('MIXTA',3),
		('ORGANISMOS_MULTILATERALES',4),
		('ENTIDADES_DERECHO_PUBLICO_NO_SOMETIDAS',5);

	WITH cteCondicionEmpleador AS
	(
		SELECT 
			dem.demId AS fceDimEmpleador,
			@DimPeriodoId AS fceDimPeriodo,
			
			--Fecha de la ultima modificacion de Canal de recepcion
			ISNULL(e.FechaUltimoCanalRecepcion, fce.fceFechaUltimoCanalRecepcion) AS fceFechaUltimoCanalRecepcion,
			ISNULL(e.FechaPrimeraRevision, fce.fceFechaPrimeraRevision) AS fceFechaPrimeraRevision,
			ISNULL(e.FechaCambioEstadoAfiliacion, fce.fceFechaCambioEstadoAfiliacion) AS fceFechaCambioEstadoAfiliacion,
			ISNULL(e.FechaAportes, fce.fceFechaAportes) AS fceFechaAportes,
			
			CASE WHEN e.FechaUltimoCanalRecepcion IS NOT NULL THEN dic.dicId ELSE 
				CASE WHEN e.CreadoPorPila = 'true' THEN 3 ELSE fce.fceDimCanal END
				END AS fceDimCanal,
			CAST(e.SedeDestinatario AS INT) AS fceDimSede,
			
			ISNULL(CASE WHEN e.EstadoAfiliacion = 'ACTIVO'
					THEN CASE WHEN fce1.fceId IS NULL THEN 1 ELSE 2 END
				END, fce.fceDimTipoAfiliacion) AS fceDimTipoAfiliacion,

			CASE WHEN ISNULL(e.NumeroTotalTrabajadores,0) BETWEEN 0 AND 10 THEN 1
				 WHEN ISNULL(e.NumeroTotalTrabajadores,0) BETWEEN 11 AND 50 THEN 2
				 WHEN ISNULL(e.NumeroTotalTrabajadores,0) BETWEEN 51 AND 200 THEN 3
				 WHEN ISNULL(e.NumeroTotalTrabajadores,0) >= 201 THEN 4
				 END AS fceDimTamanioEmpleador,

			(SELECT deeId FROM @HomologNaturalezaJuridica din WHERE ISNULL(din.naturalezaJuridica,'') = ISNULL(e.NaturalezaJuridica,'')) AS fceDimNaturalezaJuridica,
			
			CASE WHEN e.EstadoAfiliacion IS NOT NULL THEN
				(SELECT deeId FROM @HomologEstados dee WHERE ISNULL(dee.estadoCore,'') = e.EstadoAfiliacion)
				ELSE ISNULL(fce.fceDimEstadoEmpleador, 4) END AS fceDimEstadoEmpleador,

			CASE WHEN ISNULL(fce.fceDimPagoAportes,2) = 2  THEN --No pago aportes ultima coincidencia
				CASE WHEN e.Aportes = 'true' THEN 1 ELSE 2 END
			ELSE fce.fceDimPagoAportes END AS fceDimPagoAportes,

			CAST((CASE WHEN e.EstadoAfiliacion = 'ACTIVO' AND ISNULL(fce.fceDimEstadoEmpleador,0) <> 1 THEN 'true' ELSE 'false' END) AS BIT) AS fceAccionActivacion

		FROM #CondicionEmpleador e
		INNER JOIN DimEmpleador dem ON dem.demTipoIdentificacion = e.TipoIdentificacion AND dem.demNumeroIdentificacion = e.NumeroIdentificacion
		LEFT JOIN DimCanal dic ON e.CanalRecepcion = dic.dicDescripcion
		LEFT JOIN --Ultimo periodo Activo de diferente Periodo
				( SELECT fceDimEmpleador, MAX(fceId) fceId
				FROM FactCondicionEmpleador fce
				WHERE fce.fceDimPeriodo <> @DimPeriodoId
				AND fce.fceDimEstadoEmpleador = 1 --Activo
				GROUP BY fce.fceDimEmpleador
				) fce1
				ON dem.demId = fce1.fceDimEmpleador	
		LEFT JOIN --Ultimo periodo registrado
				(
					( SELECT fceDimEmpleador, MAX(fceId) fceId
					FROM FactCondicionEmpleador fce
					GROUP BY fce.fceDimEmpleador
					) fce2
					INNER JOIN FactCondicionEmpleador fce ON fce2.fceId = fce.fceId						
				) ON dem.demId = fce.fceDimEmpleador	
		
		
	)
	
	MERGE FactCondicionEmpleador AS T
	USING cteCondicionEmpleador AS S
	ON (T.fceDimEmpleador = S.fceDimEmpleador AND T.fceDimPeriodo = S.fceDimPeriodo)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fceDimEmpleador,fceDimPeriodo,fceDimCanal,fceDimSede,fceDimTipoAfiliacion,fceDimTamanioEmpleador,fceDimNaturalezaJuridica,fceDimEstadoEmpleador,fceDimPagoAportes,fceFechaCambioEstadoAfiliacion,fceFechaPrimeraRevision,fceFechaUltimoCanalRecepcion,fceFechaAportes,fceAccionActivacion)
		VALUES (S.fceDimEmpleador,S.fceDimPeriodo,S.fceDimCanal,S.fceDimSede,S.fceDimTipoAfiliacion,S.fceDimTamanioEmpleador,S.fceDimNaturalezaJuridica,S.fceDimEstadoEmpleador,S.fceDimPagoAportes,S.fceFechaCambioEstadoAfiliacion,S.fceFechaPrimeraRevision,S.fceFechaUltimoCanalRecepcion,S.fceFechaAportes,S.fceAccionActivacion)
	WHEN MATCHED
		THEN UPDATE SET T.fceDimCanal = S.fceDimCanal, T.fceDimSede = S.fceDimSede, T.fceDimTipoAfiliacion = S.fceDimTipoAfiliacion,T.fceDimTamanioEmpleador = S.fceDimTamanioEmpleador,T.fceDimNaturalezaJuridica = S.fceDimNaturalezaJuridica,T.fceDimEstadoEmpleador = S.fceDimEstadoEmpleador,T.fceDimPagoAportes = S.fceDimPagoAportes, T.fceFechaCambioEstadoAfiliacion = S.fceFechaCambioEstadoAfiliacion, T.fceFechaPrimeraRevision = S.fceFechaPrimeraRevision, T.fceFechaUltimoCanalRecepcion = S.fceFechaUltimoCanalRecepcion, T.fceFechaAportes = S.fceFechaAportes, T.fceAccionActivacion = S.fceAccionActivacion
;