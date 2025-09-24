-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/12/19
-- Description:	Carga la tabla de hechos FactSolicitudAfiliacionEmpleadorV2 para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactSolicitudAfiliacionEmpleadorV2
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
	


	WITH cteComunicados
	AS (
		SELECT sae.saeId,
			sol.solFechaRadicacion,
			com.comFechaComunicado,
			sol.solCanalRecepcion,
			sol.solSedeDestinatario
		FROM Comunicado com
		INNER JOIN Solicitud sol ON com.comSolicitud = sol.solId
		INNER JOIN SolicitudAfiliaciEmpleador sae ON sol.solId = sae.saeSolicitudGlobal
		WHERE NOT comFechaComunicado < @FechaInicioRevision
		AND com.comId IN ( 
					SELECT MIN(comId) comId
					FROM Comunicado
					INNER JOIN Solicitud sol ON com.comSolicitud = sol.solId
					INNER JOIN SolicitudAfiliaciEmpleador sae ON sol.solId = sae.saeSolicitudGlobal
					WHERE comFechaComunicado BETWEEN @FechaInicioRevision AND @FechaFinRevision
					GROUP BY comSolicitud)
		
	),
	
	cteDimCanal
	AS 
	(
		SELECT	solCanalRecepcion, fseDimCanal 
		FROM (
			VALUES
				('PRESENCIAL', 1),
				('WEB', 2),
				('PILA', 3)
			) AS T (solCanalRecepcion, fseDimCanal)
	),
	
	cteAttachDimension
	AS
	(
		SELECT 
			S.saeId AS fseSolicitudAfiliacionEmpleador,
			@DimPeriodoId AS fseDimPeriodo,
			S.solFechaRadicacion AS fseFechaRadicacion,
			S.comFechaComunicado AS fseFechaPrimerComunicado,
			CASE WHEN DATEDIFF(dd,S.solFechaRadicacion,S.comFechaComunicado) <= 24 THEN 1 ELSE
			CASE WHEN DATEDIFF(dd,S.solFechaRadicacion,S.comFechaComunicado) BETWEEN 25 AND 48 THEN 2 ELSE
			CASE WHEN DATEDIFF(dd,S.solFechaRadicacion,S.comFechaComunicado) BETWEEN 49 AND 72 THEN 3 ELSE 4 END END END AS fseDimRangoTiempoRespuesta,
			dic.fseDimCanal,
			S.solSedeDestinatario AS fseDimSede,
			'true' AS fseAccionPrimerComunicado
		FROM cteComunicados S	
		LEFT JOIN cteDimCanal dic ON dic.solCanalRecepcion = S.solCanalRecepcion
		
	)

	MERGE FactSolicitudAfiliacionEmpleadorV2 AS T
	USING cteAttachDimension AS S
	ON (T.fseDimPeriodo = S.fseDimPeriodo AND T.fseSolicitudAfiliacionEmpleador = S.fseSolicitudAfiliacionEmpleador)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fseSolicitudAfiliacionEmpleador,fseDimPeriodo,fseAccionPrimerComunicado,fseFechaRadicacion,fseFechaPrimerComunicado,fseDimRangoTiempoRespuesta,fseDimSede,fseDimCanal)
		VALUES (S.fseSolicitudAfiliacionEmpleador,S.fseDimPeriodo,S.fseAccionPrimerComunicado,S.fseFechaRadicacion,S.fseFechaPrimerComunicado,S.fseDimRangoTiempoRespuesta,S.fseDimSede,S.fseDimCanal)

;
