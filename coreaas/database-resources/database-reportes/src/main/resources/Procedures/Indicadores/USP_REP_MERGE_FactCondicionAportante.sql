-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/06/29
-- Description:	Carga la tabla de hechos FactCondicionAportante para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactCondicionAportante
AS

	--FactCondicionAportante

	--Se cargan Empleadores de PILA
	WITH cteMax AS
	(
		SELECT MAX(ppl.pplId) as pplId, ppl.pplTipoIdentificacionAportante, ppl.pplNumeroIdentificacionAportante
		FROM PlanillaPILA ppl
		INNER JOIN EstadoArchivoPILA epp ON ppl.pplIndicePlanilla = epp.eppIndicePlanilla
		WHERE ppl.pplTipoPlanilla = 'EMPRESA'
		AND ppl.pplTipoCotizante = 'DEPENDIENTE'
		AND epp.eppEnColaProceso = 1
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

	--Se cargan Independientes de PILA
	WITH cteMax AS
	(
		SELECT MAX(ppl.pplId) as pplId, ppl.pplTipoIdentificacionCotizante, ppl.pplNumeroIdentificacionCotizante
		FROM PlanillaPILA ppl
		INNER JOIN EstadoArchivoPILA epp ON ppl.pplIndicePlanilla = epp.eppIndicePlanilla
		WHERE ((ppl.pplTipoPlanilla = 'EMPRESA' AND ppl.pplTipoCotizante = 'INDEPENDIENTE') OR ppl.pplTipoPlanilla = 'INDEPENDIENTE')
		AND epp.eppEnColaProceso = 1
		GROUP BY ppl.pplTipoIdentificacionCotizante, ppl.pplNumeroIdentificacionCotizante
	)	
	,cteAportante AS
	(
		SELECT ppl.pplTipoIdentificacionCotizante AS dapTipoIdentificacion, ppl.pplNumeroIdentificacionCotizante AS dapNumeroIdentificacion, 
				ppl.pplPrimerApellido AS dapPrimerApellido, pplSegundoApellido AS dapSegundoApellido, ppl.pplPrimerNombre AS dapPrimerNombre, pplSegundoNombre AS dapSegundoNombre
		FROM PlanillaPILA ppl
		INNER JOIN cteMax ON ppl.pplId = cteMax.pplId		
	)

	MERGE DimAportante AS T
	USING cteAportante AS S
	ON (	T.dapTipoIdentificacion = S.dapTipoIdentificacion AND
			T.dapNumeroIdentificacion = S.dapNumeroIdentificacion
		)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (dapTipoIdentificacion,dapNumeroIdentificacion,dapPrimerApellido,dapSegundoApellido,dapPrimerNombre,dapSegundoNombre)
			VALUES (S.dapTipoIdentificacion,S.dapNumeroIdentificacion,S.dapPrimerApellido,S.dapSegundoApellido,S.dapPrimerNombre,S.dapSegundoNombre)
	WHEN MATCHED
		THEN UPDATE SET T.dapPrimerApellido = S.dapPrimerApellido, T.dapSegundoApellido = S.dapSegundoApellido, T.dapPrimerNombre = S.dapPrimerNombre, T.dapSegundoNombre = S.dapSegundoNombre;


	WITH 
	ctePlanilla AS
	(
		SELECT pplIndicePlanilla, MAX(pplId) pplId
		FROM PlanillaPILA
		GROUP BY pplIndicePlanilla
	),
	cteCondicionAportante AS
	(
		SELECT 
			ppl.pplTipoIdentificacionAportante AS TipoIdentificacion,
			ppl.pplNumeroIdentificacionAportante AS NumeroIdentificacion,
			ppl.pplPeriodo AS Periodo,
			eec.eecEstadoAfiliacion AS EstadoAfiliacion,
			ISNULL(empNumeroTotalTrabajadores,0) AS NumeroTotalTrabajadores,
			dbo.GetDiasPago('EMPLEADOR',ppl.pplTipoCotizantePILA,ppl.pplNumeroIdentificacionAportante,ppl.pplFechaRecaudo,ppl.pplPeriodo,ISNULL(empNumeroTotalTrabajadores,0)) AS DiasPago,
			'EMPLEADOR' AS TipoAportante,
			'Jurídica' AS TipoPersona,
			YEAR(ppl.pplFechaRecibo) AS AnioRecibo, 
			MONTH(ppl.pplFechaRecibo) AS MesRecibo
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
		) eec1 ON eec1.perTipoIdentificacion = ppl.pplTipoIdentificacionAportante AND eec1.perNumeroIdentificacion = ppl.pplNumeroIdentificacionAportante AND eec1.eecFechaCambioEstado >= EOMONTH(ppl.pplFechaRecibo)
		LEFT JOIN EstadoAfiliacionEmpleadorCaja eec ON eec1.eecPersona = eec.eecPersona AND eec1.eecFechaCambioEstado = eec.eecFechaCambioEstado
		WHERE pplTipoPlanilla = 'EMPRESA' AND pplTipoCotizante = 'DEPENDIENTE'
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
			'Natural' AS TipoPersona,
			YEAR(ppl.pplFechaRecibo) AS AnioRecibo, 
			MONTH(ppl.pplFechaRecibo) AS MesRecibo
		FROM PlanillaPILA ppl
		INNER JOIN ctePlanilla cte ON ppl.pplId = cte.pplId
		INNER JOIN EstadoArchivoPILA epp ON ppl.pplIndicePlanilla = epp.eppIndicePlanilla	
		LEFT JOIN Persona per ON per.perTipoIdentificacion = ppl.pplTipoIdentificacionCotizante AND per.perNumeroIdentificacion = ppl.pplNumeroIdentificacionCotizante	
		LEFT JOIN (
			SELECT eacPersona, perNumeroIdentificacion, perTipoIdentificacion, MIN(eacFechaCambioEstado) AS eacFechaCambioEstado
			FROM EstadoAfiliacionPersonaCaja eac
			INNER JOIN Persona per ON eac.eacPersona = per.perId
			GROUP BY eacPersona, perNumeroIdentificacion, perTipoIdentificacion
		) eac1 ON eac1.perTipoIdentificacion = ppl.pplTipoIdentificacionAportante AND eac1.perNumeroIdentificacion = ppl.pplNumeroIdentificacionAportante AND eac1.eacFechaCambioEstado >= EOMONTH(ppl.pplFechaRecibo)
		LEFT JOIN EstadoAfiliacionPersonaCaja eac ON eac1.eacPersona = eac.eacPersona AND eac1.eacFechaCambioEstado = eac.eacFechaCambioEstado
		WHERE ((ppl.pplTipoPlanilla = 'EMPRESA' AND ppl.pplTipoCotizante = 'INDEPENDIENTE') OR ppl.pplTipoPlanilla = 'INDEPENDIENTE')
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
	cteCondicionAportanteAVG AS
	(
		SELECT cte.TipoIdentificacion, cte.NumeroIdentificacion, cte.TipoAportante, cte.NumeroTotalTrabajadores, cte.AnioRecibo, cte.MesRecibo, cte.EstadoAfiliacion, cte.TipoPersona, AVG(cte.DiasPago) AS DiasPago
		FROM cteCondicionAportante cte
		GROUP BY cte.TipoIdentificacion, cte.NumeroIdentificacion, cte.TipoAportante, cte.NumeroTotalTrabajadores, cte.AnioRecibo, cte.MesRecibo, cte.EstadoAfiliacion, cte.TipoPersona
	),
	cteFactCondicionAportante AS
	(
		SELECT
			(SELECT dipId FROM dbo.DimPeriodo dip WHERE dip.dipAnio = cte.AnioRecibo AND dip.dipMes = cte.MesRecibo) AS fcaDimPeriodo,
			(SELECT dapId FROM dbo.DimAportante dap WHERE dap.dapTipoIdentificacion = cte.TipoIdentificacion AND dap.dapNumeroIdentificacion = cte.NumeroIdentificacion) AS fcaDimAportante,
			(SELECT deaId FROM cteDimEstadoAportante WHERE ISNULL(estadoCore,'') = ISNULL(cte.EstadoAfiliacion,'')) AS fcaDimEstadoAportante,
			(SELECT dtpId FROM dbo.DimTipoPersona WHERE dtpDescripcion = cte.TipoPersona) AS fcaDimTipoPersona,
			CASE WHEN cte.TipoAportante = 'EMPLEADOR' THEN
				CASE WHEN cte.NumeroTotalTrabajadores >= 200 THEN 2 ELSE 3 END
				ELSE 1 END AS fcaDimTipoAportante,
			CASE WHEN cte.DiasPago IS NOT NULL THEN 
				CASE WHEN cte.DiasPago  <= -10 THEN 1
					 WHEN cte.DiasPago BETWEEN -9 AND -5 THEN 2
					 WHEN cte.DiasPago BETWEEN -4 AND 0 THEN 3
					 WHEN cte.DiasPago BETWEEN 1 AND 5 THEN 4
					 WHEN cte.DiasPago BETWEEN 6 AND 10 THEN 5
					 WHEN cte.DiasPago >= 11 THEN 6
				END
			END AS fcaDimRangoTiempoAporte
		FROM cteCondicionAportanteAVG cte
	)

	MERGE dbo.FactCondicionAportante AS T
	USING cteFactCondicionAportante AS S
	ON (	T.fcaDimAportante = S.fcaDimAportante AND
			T.fcaDimPeriodo = S.fcaDimPeriodo AND
			T.fcaDimTipoAportante = S.fcaDimTipoAportante AND
			T.fcaDimEstadoAportante =  S.fcaDimEstadoAportante AND
			T.fcaDimTipoPersona = S.fcaDimTipoPersona AND
			T.fcaDimRangoTiempoAporte = S.fcaDimRangoTiempoAporte
		)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fcaDimPeriodo,fcaDimAportante,fcaDimEstadoAportante,fcaDimTipoAportante,fcaDimRangoTiempoAporte,fcaDimTipoPersona)
			VALUES (S.fcaDimPeriodo,S.fcaDimAportante,S.fcaDimEstadoAportante,S.fcaDimTipoAportante,S.fcaDimRangoTiempoAporte,fcaDimTipoPersona)
	WHEN MATCHED
		THEN UPDATE SET T.fcaDimEstadoAportante = S.fcaDimEstadoAportante, T.fcaDimRangoTiempoAporte = S.fcaDimRangoTiempoAporte;
;