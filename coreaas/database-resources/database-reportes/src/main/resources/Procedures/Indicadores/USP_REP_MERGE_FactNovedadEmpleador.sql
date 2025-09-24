-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/07/11
-- Description:	Carga la tabla de hechos FactNovedadEmpleador para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactNovedadEmpleador
AS

	--FactNovedadEmpleador
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

	CREATE TABLE #TmpNovedadEmpleador(
		perIdEmpleador bigint,
		TipoIdentificacionEmpleador varchar(20) COLLATE Latin1_General_CI_AI,
		NumeroIdentificacionEmpleador varchar(16) COLLATE Latin1_General_CI_AI,
		CanalRecepcion varchar(21) COLLATE Latin1_General_CI_AI,
		SedeDestinatario varchar(2) COLLATE Latin1_General_CI_AI,
		EstadoNovedad varchar(9) COLLATE Latin1_General_CI_AI,
		TipoNovedad varchar(78) COLLATE Latin1_General_CI_AI,
		FechaRadicacion datetime,
		FechaModificacionSolicitud datetime,
		ResultadoProceso varchar(22) COLLATE Latin1_General_CI_AI,
		FechaModificacionNovedad datetime,
		EstadoSolicitudNovedad varchar(50) COLLATE Latin1_General_CI_AI,
		EstadoAfiliacionEmpleador varchar(50) COLLATE Latin1_General_CI_AI,
		NaturalezaPersona varchar(30) COLLATE Latin1_General_CI_AI,
		Solicitud bigint
	);

	WITH cteTipoNovedad
	AS
	(	SELECT * 
		FROM (VALUES 
		--'Activar', 
		('Activar', 'ACTIVAR_INACTIVAR_CODIGO_SUCURSAL_DEBE_COINCIDIR_CON_PILA'),
		('Activar', 'ACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL'),
		('Activar', 'ACTIVAR_BENEFICIOS_LEY_1429_2010_WEB'),
		('Activar', 'ACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL'),
		('Activar', 'ACTIVAR_BENEFICIOS_LEY_590_2000_WEB'),
		--'Actualizar', 
		('Actualizar', 'ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_WEB'),
		('Actualizar', 'ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL'),
		('Actualizar', 'ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL'),
		('Actualizar', 'ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_WEB'),
		--'Agregar', 
		('Agregar', 'AGREGAR_SUCURSAL'),
		--Anular
	
		--'Cambiar', 
		('Cambiar', 'CAMBIO_TIPO_NUMERO_DOCUMENTO'),
		('Cambiar', 'CAMBIO_RAZON_SOCIAL_NOMBRE'),
		('Cambiar', 'CAMBIO_NATURALEZA_JURIDICA'),
		('Cambiar', 'CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_PRESENCIAL'),
		('Cambiar', 'CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_WEB'),
		('Cambiar', 'CAMBIOS_OTROS_DATOS_IDENTIFICACION_EMPLEADOR'),
		('Cambiar', 'CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_PRESENCIAL'),
		('Cambiar', 'CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_WEB'),
		('Cambiar', 'CAMBIO_MEDIO_PAGO_EMPLEADOR_PRESENCIAL'),
		('Cambiar', 'CAMBIO_MEDIO_PAGO_EMPLEADOR_WEB'),
		('Cambiar', 'CAMBIO_CODIGO_NOMBRE_SUCURSAL_WEB'),
		('Cambiar', 'CAMBIO_CODIGO_NOMBRE_SUCURSAL'),
		('Cambiar', 'CAMBIO_DATOS_SUCURSAL_PRESENCIAL'),
		('Cambiar', 'CAMBIO_DATOS_SUCURSAL_WEB'),
		('Cambiar', 'CAMBIO_ACTIVIDAD_ECONOMICA_SUCURSAL_PRESENCIAL'),
		('Cambiar', 'CAMBIO_ACTIVIDAD_ECONOMICA_SUCURSAL_WEB'),
		('Cambiar', 'CAMBIO_MEDIO_PAGO_SUCURSAL_PRESENCIAL'),
		('Cambiar', 'CAMBIO_MEDIO_PAGO_SUCURSAL_WEB'),
		('Cambiar', 'CAMBIOS_ROLES_CONTACTO_PRESENCIAL'),
		('Cambiar', 'CAMBIOS_ROLES_CONTACTO_WEB'),
		('Cambiar', 'CAMBIO_RESPONSABLE_CONTACTOS_CFF'),
		--'Desafiliar', 
		('Desafiliar', 'DESAFILIACION'),
		--'Inactivar', 
		('Inactivar', 'INACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL'),
		('Inactivar', 'INACTIVAR_BENEFICIOS_LEY_1429_2010_WEB'),
		('Inactivar', 'INACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL'),
		('Inactivar', 'INACTIVAR_BENEFICIOS_LEY_590_2000_WEB'),
		('Inactivar', 'INACTIVAR_SUCURSAL'),
		--'Sustituir', 
		('Sustituir', 'SUSTITUCION_PATRONAL'),
		--'Trasladar', 
		('Trasladar', 'TRASLADO_TRABAJADORES_ENTRE_SUCURSALES')
		) param (TipoNovedad, TipoTransaccion)	
	),
	cteEstadoNovedad AS (
		SELECT *
		FROM (VALUES
		('RADICADA', 'Radicada'),
		('APROBADA', 'Aprobado'),
		('CERRADA', 'Cerrada'),
		('RECHAZADA', 'Rechazada')
		) EN(EstadoCore, EstadoNovedad)
	),
	cteNovedadEmpleador AS
	(
		SELECT 
			perE.perId AS perIdEmpleador,
			perE.perTipoIdentificacion AS TipoIdentificacionEmpleador,
			perE.perNumeroIdentificacion AS NumeroIdentificacionEmpleador,
			sol.solCanalRecepcion AS CanalRecepcion,
			sol.solSedeDestinatario AS SedeDestinatario,
			cen.EstadoNovedad,
			ctn.TipoNovedad,
			sol.solFechaRadicacion AS FechaRadicacion,
			wmrSolicitud.wmrLastRevisionDateTime AS FechaModificacionSolicitud,
			sol.solResultadoProceso AS ResultadoProceso,
			wmrSolicitudNovedad.wmrLastRevisionDateTime AS FechaModificacionNovedad,
			sno.snoEstadoSolicitud AS EstadoSolicitudNovedad,
			(SELECT TOP 1 eecEstadoAfiliacion FROM EstadoAfiliacionEmpleadorCaja WHERE eecPersona = perE.perId AND eecFechaCambioEstado <= wmrSolicitud.wmrLastRevisionDateTime ORDER BY eecId DESC) AS EstadoAfiliacionEmpleador,
			sol.solId AS Solicitud
		FROM Solicitud sol
		INNER JOIN SolicitudNovedad sno ON sol.solId = sno.snoSolicitudGlobal
		INNER JOIN SolicitudNovedadEmpleador sne ON sno.snoId = sne.sneIdSolicitudNovedad
		INNER JOIN Empleador empl ON sne.sneIdEmpleador = empl.empId
		INNER JOIN Empresa emp ON empl.empEmpresa = emp.empId
		INNER JOIN Persona perE ON emp.empPersona = perE.perId
		INNER JOIN (
					SELECT wmrKeyRowValue, MAX(wmrId) wmrId
					FROM WaterMarkedRows
					WHERE 1=1 
					AND wmrTable = 'Solicitud'
					GROUP BY wmrKeyRowValue) wmrS ON wmrS.wmrKeyRowValue = sol.solId
		INNER JOIN WaterMarkedRows wmrSolicitud ON wmrSolicitud.wmrId = wmrS.wmrId AND sol.solId = wmrSolicitud.wmrKeyRowValue		
		INNER JOIN (
					SELECT wmrKeyRowValue, MAX(wmrId) wmrId
					FROM WaterMarkedRows
					WHERE 1=1 
					AND wmrTable = 'SolicitudNovedad'
					GROUP BY wmrKeyRowValue) wmrSN ON wmrSN.wmrKeyRowValue = sno.snoId
		INNER JOIN WaterMarkedRows wmrSolicitudNovedad ON wmrSolicitudNovedad.wmrId = wmrSN.wmrId AND sno.snoId = wmrSolicitudNovedad.wmrKeyRowValue		
		LEFT JOIN cteTipoNovedad ctn ON ctn.TipoTransaccion = sol.solTipoTransaccion
		LEFT JOIN cteEstadoNovedad cen ON cen.EstadoCore = ISNULL(solResultadoProceso, snoEstadoSolicitud)
		WHERE 
		EXISTS (SELECT 1 FROM WaterMarkedRows wmr WHERE wmr.wmrTable = 'Solicitud' AND wmr.wmrKeyRowValue = sol.solId AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision)
		OR EXISTS (SELECT 1 FROM WaterMarkedRows wmr WHERE wmr.wmrTable = 'SolicitudNovedad' AND wmr.wmrKeyRowValue = sno.snoId AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision)
		OR EXISTS (SELECT 1 FROM WaterMarkedRows wmr WHERE wmr.wmrTable = 'SolicitudNovedadEmpleador' AND wmr.wmrKeyRowValue = sne.sneId AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision)
	)

	INSERT INTO #TmpNovedadEmpleador (perIdEmpleador,TipoIdentificacionEmpleador,NumeroIdentificacionEmpleador,CanalRecepcion,SedeDestinatario,EstadoNovedad,TipoNovedad,FechaRadicacion,FechaModificacionSolicitud,ResultadoProceso,FechaModificacionNovedad,EstadoSolicitudNovedad,EstadoAfiliacionEmpleador,Solicitud)
	SELECT perIdEmpleador,TipoIdentificacionEmpleador,NumeroIdentificacionEmpleador,CanalRecepcion,SedeDestinatario,EstadoNovedad,TipoNovedad,FechaRadicacion,FechaModificacionSolicitud,ResultadoProceso,FechaModificacionNovedad,EstadoSolicitudNovedad,EstadoAfiliacionEmpleador,Solicitud
	FROM cteNovedadEmpleador;

		
	WITH cteHomologEstados AS
	(
		SELECT *
		FROM (
		VALUES
			(NULL,4),
			('ACTIVO',1),
			('INACTIVO', 2),
			('NO_FORMALIZADO_RETIRADO_CON_APORTES',3),
			('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES',3),
			('NO_FORMALIZADO_CON_INFORMACION',3)
		) h (estadoCore, depId)
	),
	
	cteFactNovedadEmpleador AS
	(
		SELECT 
			dem.demId AS fneDimEmpleador,
			@DimPeriodoId AS fneDimPeriodo,
			dic.dicId AS fneDimCanal,
			CAST(t.SedeDestinatario AS INT) AS fneDimSede,
			den.denId AS fneDimEstadoNovedad,
			dne.dneId AS fneDimTipoNovedadEmpleador,						
			CASE WHEN t.FechaRadicacion IS NOT NULL AND t.ResultadoProceso IN ('APROBADA','RECHAZADA') THEN
				CASE DATEDIFF(HH,FechaRadicacion, t.FechaModificacionSolicitud) / 24
				WHEN 0 THEN 1
				WHEN 1 THEN 2
				WHEN 2 THEN 3
				ELSE 4
				END		
			END	AS fneDimRangoTiempoRespuestaGestion,
			CASE WHEN t.FechaRadicacion IS NOT NULL AND t.EstadoSolicitudNovedad = 'CERRADA' THEN
				CASE DATEDIFF(HH,FechaRadicacion, t.FechaModificacionNovedad) / 24
				WHEN 0 THEN 1
				WHEN 1 THEN 2
				WHEN 2 THEN 3
				ELSE 4
				END		
			END	AS fneDimRangoTiempoRespuestaNovedad,
			(SELECT cte.depId FROM cteHomologEstados cte WHERE cte.estadoCore = t.EstadoAfiliacionEmpleador) AS fneDimEstadoEmpleador,
			t.Solicitud AS fneSolicitud
		FROM #TmpNovedadEmpleador t	
		INNER JOIN DimEmpleador dem ON t.TipoIdentificacionEmpleador = dem.demTipoIdentificacion AND t.NumeroIdentificacionEmpleador = dem.demNumeroIdentificacion
		LEFT JOIN DimCanal dic ON t.CanalRecepcion = dic.dicDescripcion
		LEFT JOIN DimEstadoNovedad den ON t.EstadoNovedad = den.denDescripcion
		LEFT JOIN DimTipoNovedadEmpleador dne ON t.TipoNovedad = dne.dneDescripcion
	),
	cteFactNovedadEmpleadorD AS (SELECT DISTINCT * FROM cteFactNovedadEmpleador)
	
	MERGE FactNovedadEmpleador AS T
	USING cteFactNovedadEmpleadorD AS S
	ON (T.fneSolicitud = S.fneSolicitud AND
		T.fneDimEmpleador = S.fneDimEmpleador AND
		T.fneDimPeriodo = S.fneDimPeriodo)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fneDimEmpleador,fneDimPeriodo,fneDimCanal,fneDimSede,fneDimEstadoNovedad,fneDimTipoNovedadEmpleador,fneDimRangoTiempoRespuestaNovedad,fneDimRangoTiempoRespuestaGestion,fneDimEstadoEmpleador,fneSolicitud)
		VALUES (S.fneDimEmpleador,S.fneDimPeriodo,S.fneDimCanal,S.fneDimSede,S.fneDimEstadoNovedad,S.fneDimTipoNovedadEmpleador,S.fneDimRangoTiempoRespuestaNovedad,S.fneDimRangoTiempoRespuestaGestion,S.fneDimEstadoEmpleador,S.fneSolicitud)
	WHEN MATCHED
		THEN UPDATE SET T.fneDimEstadoNovedad = S.fneDimEstadoNovedad, T.fneDimRangoTiempoRespuestaNovedad = S.fneDimRangoTiempoRespuestaNovedad, T.fneDimRangoTiempoRespuestaGestion = S.fneDimRangoTiempoRespuestaGestion,T.fneDimEstadoEmpleador = S.fneDimEstadoEmpleador;
		
;