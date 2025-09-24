-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/06/29
-- Description:	Carga la tabla de hechos FactArchivoPILA para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactArchivoPILA
AS
	WITH cteMax AS
	(
		SELECT MAX(ppl.pplId) as pplId, ppl.pplTipoIdentificacionAportante, ppl.pplNumeroIdentificacionAportante
		FROM PlanillaPILA ppl
		INNER JOIN EstadoArchivoPILA epp ON ppl.pplIndicePlanilla = epp.eppIndicePlanilla
		WHERE epp.eppEnColaProceso = 1
		GROUP BY ppl.pplTipoIdentificacionAportante, ppl.pplNumeroIdentificacionAportante
	)	
	,cteAportante AS
	(
		SELECT ppl.pplTipoIdentificacionAportante AS dapTipoIdentificacion, ppl.pplNumeroIdentificacionAportante AS dapNumeroIdentificacion, ppl.pplDigitoVerificacion AS dapDigitoVerificacion, pplRazonSocial AS dapRazonSocial
		FROM PlanillaPILA ppl
		INNER JOIN cteMax ON ppl.pplId = cteMax.pplId		
	)

	MERGE DimAportante AS T
	USING cteAportante AS S
	ON (	T.dapTipoIdentificacion = S.dapTipoIdentificacion AND
			T.dapNumeroIdentificacion = S.dapNumeroIdentificacion
		)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (dapTipoIdentificacion,dapNumeroIdentificacion,dapDigitoVerificacion,dapRazonSocial)
			VALUES (S.dapTipoIdentificacion,S.dapNumeroIdentificacion,S.dapDigitoVerificacion,S.dapRazonSocial)
	WHEN MATCHED
		THEN UPDATE SET T.dapDigitoVerificacion = S.dapDigitoVerificacion, T.dapRazonSocial = S.dapRazonSocial;


	--FactArchivoPILA
	WITH 
	ctePlanilla AS
	(
		SELECT pplIndicePlanilla, MAX(pplId) pplId
		FROM PlanillaPILA
		GROUP BY pplIndicePlanilla
	),
	cteArchivoPILA AS
	(
		SELECT 
			ppl.pplTipoIdentificacionAportante AS TipoIdentificacion,
			ppl.pplNumeroIdentificacionAportante AS NumeroIdentificacion,
			ppl.pplPeriodo AS Periodo,
			eec.eecEstadoAfiliacion AS EstadoAfiliacion,
			ISNULL(empNumeroTotalTrabajadores,0) AS NumeroTotalTrabajadores,
			dbo.GetDiasPago('EMPLEADOR',ppl.pplTipoCotizantePILA,ppl.pplNumeroIdentificacionAportante,ppl.pplFechaRecaudo,ppl.pplPeriodo,ISNULL(empNumeroTotalTrabajadores,0)) AS DiasPago,
			'EMPLEADOR' AS TipoAportante,
			ppl.pplFechaRecibo AS FechaRecibo, 
			ppl.pplFechaRecaudo AS FechaRecaudo,
			ppl.pplEstadoArchivo AS EstadoArchivo,
			ppl.pplIdPlanilla AS IdPlanilla,
			ppl.pplValorAporte AS ValorAporte,
			ppl.pplIndicePlanilla AS IndicePlanilla
		FROM PlanillaPILA ppl
		INNER JOIN ctePlanilla cte ON ppl.pplId = cte.pplId
		INNER JOIN EstadoArchivoPILA epp ON ppl.pplIndicePlanilla = epp.eppIndicePlanilla	
		LEFT JOIN Persona per ON per.perTipoIdentificacion = ppl.pplTipoIdentificacionAportante AND per.perNumeroIdentificacion = ppl.pplNumeroIdentificacionAportante
		LEFT JOIN Empresa emp ON per.perId = emp.empPersona
		LEFT JOIN Empleador empl ON emp.empId = empl.empEmpresa
		LEFT JOIN (
			SELECT eecPersona, perNumeroIdentificacion, perTipoIdentificacion, MIN(eecFechaCambioEstado) AS eecFechaCambioEstado
			FROM EstadoAfiliacionEmpleadorCaja eec
			INNER JOIN Persona per ON eec.eecPersona = per.perId
			GROUP BY eecPersona, perNumeroIdentificacion, perTipoIdentificacion
		) eec1 ON eec1.perTipoIdentificacion = ppl.pplTipoIdentificacionAportante AND eec1.perNumeroIdentificacion = ppl.pplNumeroIdentificacionAportante AND eec1.eecFechaCambioEstado >= ppl.pplFechaRecibo
		LEFT JOIN EstadoAfiliacionEmpleadorCaja eec ON eec1.eecPersona = eec.eecPersona AND eec1.eecFechaCambioEstado = eec.eecFechaCambioEstado
		WHERE pplTipoPlanilla = 'EMPRESA'
		AND eppEnColaProceso = 1

		UNION ALL

		SELECT 
			ppl.pplTipoIdentificacionCotizante AS TipoIdentificacion,
			ppl.pplNumeroIdentificacionCotizante AS NumeroIdentificacion,
			ppl.pplPeriodo AS Periodo,
			eac.eacEstadoAfiliacion AS EstadoAfiliacion,
			0 AS NumeroTotalTrabajadores,
			dbo.GetDiasPago('INDEPENDIENTE',ppl.pplTipoCotizantePILA,ppl.pplNumeroIdentificacionAportante,ppl.pplFechaRecaudo,ppl.pplPeriodo,0) AS DiasPago,
			'INDEPENDIENTE' AS TipoAportante,
			ppl.pplFechaRecibo AS FechaRecibo, 
			ppl.pplFechaRecaudo AS FechaRecaudo,
			ppl.pplEstadoArchivo AS EstadoArchivo,
			ppl.pplIdPlanilla AS IdPlanilla,
			ppl.pplValorAporte AS ValorAporte,
			ppl.pplIndicePlanilla AS IndicePlanilla
		FROM PlanillaPILA ppl
		INNER JOIN ctePlanilla cte ON ppl.pplId = cte.pplId
		INNER JOIN EstadoArchivoPILA epp ON ppl.pplIndicePlanilla = epp.eppIndicePlanilla	
		LEFT JOIN Persona per ON per.perTipoIdentificacion = ppl.pplTipoIdentificacionCotizante AND per.perNumeroIdentificacion = ppl.pplNumeroIdentificacionCotizante	
		LEFT JOIN (
			SELECT eacPersona, perNumeroIdentificacion, perTipoIdentificacion, MIN(eacFechaCambioEstado) AS eacFechaCambioEstado
			FROM EstadoAfiliacionPersonaCaja eac
			INNER JOIN Persona per ON eac.eacPersona = per.perId
			GROUP BY eacPersona, perNumeroIdentificacion, perTipoIdentificacion
		) eac1 ON eac1.perTipoIdentificacion = ppl.pplTipoIdentificacionAportante AND eac1.perNumeroIdentificacion = ppl.pplNumeroIdentificacionAportante AND eac1.eacFechaCambioEstado >= ppl.pplFechaRecibo
		LEFT JOIN EstadoAfiliacionPersonaCaja eac ON eac1.eacPersona = eac.eacPersona AND eac1.eacFechaCambioEstado = eac.eacFechaCambioEstado
		WHERE ppl.pplTipoPlanilla = 'INDEPENDIENTE'
		AND eppEnColaProceso = 1
	),
	cteDimEstadoAportante AS
	(
		SELECT estadoCore, deaId
		FROM (
		VALUES
			(NULL,4),
			('ACTIVO',1),
			('INACTIVO', 2),
			('NO_FORMALIZADO_RETIRADO_CON_APORTES',3),
			('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES',3),
			('NO_FORMALIZADO_CON_INFORMACION',3)
		) est(estadoCore, deaId) 
	),
	cteFactArchivoPILA AS
	(
		SELECT
			(SELECT dipId FROM dbo.DimPeriodo dip WHERE dip.dipAnio = YEAR(cte.FechaRecibo) AND dip.dipMes = MONTH(cte.FechaRecibo)) AS fapDimPeriodo,
			(SELECT dapId FROM dbo.DimAportante dap WHERE dap.dapTipoIdentificacion = cte.TipoIdentificacion AND dap.dapNumeroIdentificacion = cte.NumeroIdentificacion) AS fapDimAportante,
			(SELECT deaId FROM cteDimEstadoAportante WHERE ISNULL(estadoCore,'') = ISNULL(cte.EstadoAfiliacion,'')) AS fapDimEstadoAportante,
			CASE WHEN cte.TipoAportante = 'EMPLEADOR' THEN
				CASE WHEN cte.NumeroTotalTrabajadores >= 200 THEN 2 ELSE 3 END
				ELSE 1 END AS fapDimTipoAportante,
			CASE WHEN cte.DiasPago IS NOT NULL THEN 
				CASE WHEN cte.DiasPago  <= -10 THEN 1
					 WHEN cte.DiasPago BETWEEN -9 AND -5 THEN 2
					 WHEN cte.DiasPago BETWEEN -4 AND 0 THEN 3
					 WHEN cte.DiasPago BETWEEN 1 AND 5 THEN 4
					 WHEN cte.DiasPago BETWEEN 6 AND 10 THEN 5
					 WHEN cte.DiasPago >= 11 THEN 6
				END
			END AS fapDimRangoTiempoAporte,
			CASE WHEN cte.EstadoArchivo = 'RECAUDO_NOTIFICADO' THEN  1 ELSE 2 END AS fapDimEstadoRecaudo,
			cte.FechaRecibo AS fapFechaRecibo,
			cte.FechaRecaudo AS fapFechaRecaudo,
			cte.Periodo AS fapPeriodoAporte,
			cte.IdPlanilla AS fapIdPlanilla,
			cte.IndicePlanilla AS fapIndicePlanilla,
			cte.ValorAporte AS fapValorAporte
		FROM cteArchivoPILA cte
	), cteFactArchivoPILAD AS (SELECT DISTINCT * FROM cteFactArchivoPILA)
	
	
	MERGE dbo.FactArchivoPILA AS T
	USING cteFactArchivoPILAD AS S
	ON (T.fapIndicePlanilla = S.fapIndicePlanilla AND
		T.fapDimPeriodo = S.fapDimPeriodo AND
		T.fapDimAportante = S.fapDimAportante)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fapDimPeriodo,fapDimAportante,fapDimEstadoRecaudo,fapDimEstadoAportante,fapDimTipoAportante,fapDimRangoTiempoAporte,fapIndicePlanilla,fapIdPlanilla,fapFechaRecibo,fapPeriodoAporte,fapFechaRecaudo,fapValorAporte)
			VALUES (S.fapDimPeriodo,S.fapDimAportante,S.fapDimEstadoRecaudo,S.fapDimEstadoAportante,S.fapDimTipoAportante,S.fapDimRangoTiempoAporte,S.fapIndicePlanilla,S.fapIdPlanilla,S.fapFechaRecibo,S.fapPeriodoAporte,S.fapFechaRecaudo,S.fapValorAporte)
	WHEN MATCHED
		THEN UPDATE SET T.fapDimEstadoRecaudo = S.fapDimEstadoRecaudo,T.fapDimEstadoAportante=S.fapDimEstadoAportante,T.fapDimRangoTiempoAporte=S.fapDimRangoTiempoAporte,T.fapFechaRecaudo=S.fapFechaRecaudo;

;