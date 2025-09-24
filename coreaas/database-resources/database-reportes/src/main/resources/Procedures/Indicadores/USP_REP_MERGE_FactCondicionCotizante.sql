-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/07/02
-- Description:	Carga la tabla de hechos FactCondicionCotizante para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactCondicionCotizante
AS

	WITH cteMax AS
	(
		SELECT MAX(ppl.pplId) as pplId, ppl.pplTipoIdentificacionCotizante, ppl.pplNumeroIdentificacionCotizante
		FROM PlanillaPILA ppl
		INNER JOIN EstadoArchivoPILA epp ON ppl.pplIndicePlanilla = epp.eppIndicePlanilla
		WHERE epp.eppEnColaProceso = 1
		GROUP BY ppl.pplTipoIdentificacionCotizante, ppl.pplNumeroIdentificacionCotizante
	)	
	,cteCotizante AS
	(
		SELECT ppl.pplTipoIdentificacionCotizante AS dcoTipoIdentificacion, ppl.pplNumeroIdentificacionCotizante AS dcoNumeroIdentificacion, 
				ppl.pplPrimerApellido AS dcoPrimerApellido, pplSegundoApellido AS dcoSegundoApellido, ppl.pplPrimerNombre AS dcoPrimerNombre, pplSegundoNombre AS dcoSegundoNombre
		FROM PlanillaPILA ppl
		INNER JOIN cteMax ON ppl.pplId = cteMax.pplId		
	)

	MERGE DimCotizante AS T
	USING cteCotizante AS S
	ON (	T.dcoTipoIdentificacion = S.dcoTipoIdentificacion AND
			T.dcoNumeroIdentificacion = S.dcoNumeroIdentificacion
		)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (dcoTipoIdentificacion,dcoNumeroIdentificacion,dcoPrimerApellido,dcoSegundoApellido,dcoPrimerNombre,dcoSegundoNombre)
			VALUES (S.dcoTipoIdentificacion,S.dcoNumeroIdentificacion,S.dcoPrimerApellido,S.dcoSegundoApellido,S.dcoPrimerNombre,S.dcoSegundoNombre)
	WHEN MATCHED
		THEN UPDATE SET T.dcoPrimerApellido = S.dcoPrimerApellido, T.dcoSegundoApellido = S.dcoSegundoApellido, T.dcoPrimerNombre = S.dcoPrimerNombre, T.dcoSegundoNombre = S.dcoSegundoNombre;

	--FactCondicionCotizante
	WITH cteCondicionCotizante AS
	(
		SELECT  
			ppl.pplTipoIdentificacionAportante AS TipoIdentificacionAportante,
			ppl.pplNumeroIdentificacionAportante AS NumeroIdentificacionAportante,
			ppl.pplTipoIdentificacionCotizante AS TipoIdentificacionCotizante,
			ppl.pplNumeroIdentificacionCotizante AS NumeroIdentificacionCotizante,
			CASE WHEN ppl.pplEstadoArchivo = 'RECAUDO_NOTIFICADO' THEN 'Exitoso' ELSE 'Inconsistente' END AS EstadoArchivo,
			'EMPLEADOR' AS TipoAportante,
			YEAR(ppl.pplFechaRecibo) AS AnioRecibo, 
			MONTH(ppl.pplFechaRecibo) AS MesRecibo,
			CASE WHEN MONTH(roa.roaFechaAfiliacion) = MONTH(ppl.pplFechaRecibo) AND YEAR(roa.roaFechaAfiliacion) = YEAR(ppl.pplFechaRecibo)  THEN 1 ELSE 0 END AS NovedadIngreso,
			CASE WHEN MONTH(roa.roaFechaRetiro) = MONTH(ppl.pplFechaRecibo) AND YEAR(roa.roaFechaRetiro) = YEAR(ppl.pplFechaRecibo)  THEN 1 ELSE 0 END AS NovedadRetiro
		FROM PlanillaPILA ppl
		INNER JOIN EstadoArchivoPILA epp ON ppl.pplIndicePlanilla = epp.eppIndicePlanilla	
		LEFT JOIN Persona per ON per.perTipoIdentificacion = ppl.pplTipoIdentificacionAportante AND per.perNumeroIdentificacion = ppl.pplNumeroIdentificacionAportante
		LEFT JOIN Empresa emp ON per.perId = emp.empPersona
		LEFT JOIN Empleador empl ON emp.empId = empl.empEmpresa		
		LEFT JOIN Persona perC ON perC.perTipoIdentificacion = ppl.pplTipoIdentificacionCotizante AND perC.perNumeroIdentificacion = ppl.pplNumeroIdentificacionCotizante		
		LEFT JOIN Afiliado afi ON afi.afiPersona = perC.perId
		LEFT JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId AND empl.empId = roa.roaEmpleador
		WHERE pplTipoPlanilla = 'EMPRESA' AND pplTipoCotizante = 'DEPENDIENTE'
		AND eppEnColaProceso = 1

		UNION ALL

		SELECT  
			ppl.pplTipoIdentificacionCotizante AS TipoIdentificacionAportante,
			ppl.pplNumeroIdentificacionCotizante AS NumeroIdentificacionAportante,
			ppl.pplTipoIdentificacionCotizante AS TipoIdentificacionCotizante,
			ppl.pplNumeroIdentificacionCotizante AS NumeroIdentificacionCotizante,
			CASE WHEN ppl.pplEstadoArchivo = 'RECAUDO_NOTIFICADO' THEN 'Exitoso' ELSE 'Inconsistente' END AS EstadoArchivo,
			'INDEPENDIENTE' AS TipoAportante,
			YEAR(ppl.pplFechaRecibo) AS AnioRecibo, 
			MONTH(ppl.pplFechaRecibo) AS MesRecibo,
			CASE WHEN MONTH(roa.roaFechaAfiliacion) = MONTH(ppl.pplFechaRecibo) AND YEAR(roa.roaFechaAfiliacion) = YEAR(ppl.pplFechaRecibo)  THEN 1 ELSE 0 END AS NovedadIngreso,
			CASE WHEN MONTH(roa.roaFechaRetiro) = MONTH(ppl.pplFechaRecibo) AND YEAR(roa.roaFechaRetiro) = YEAR(ppl.pplFechaRecibo)  THEN 1 ELSE 0 END AS NovedadRetiro
		FROM PlanillaPILA ppl
		INNER JOIN EstadoArchivoPILA epp ON ppl.pplIndicePlanilla = epp.eppIndicePlanilla	
		LEFT JOIN Persona perC ON perC.perTipoIdentificacion = ppl.pplTipoIdentificacionCotizante AND perC.perNumeroIdentificacion = ppl.pplNumeroIdentificacionCotizante		
		LEFT JOIN Afiliado afi ON afi.afiPersona = perC.perId
		LEFT JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId AND roa.roaEmpleador IS NULL
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
	cteFactCondicionCotizanteArchivoExitoso AS
	(
		SELECT TipoIdentificacionAportante, NumeroIdentificacionAportante, TipoIdentificacionCotizante, NumeroIdentificacionCotizante, EstadoArchivo, TipoAportante, AnioRecibo, MesRecibo, MAX(NovedadIngreso) NovedadIngreso, MAX(NovedadRetiro) NovedadRetiro
		FROM cteCondicionCotizante cte
		WHERE EstadoArchivo = 'Exitoso'
		GROUP BY TipoIdentificacionAportante, NumeroIdentificacionAportante, TipoIdentificacionCotizante, NumeroIdentificacionCotizante, EstadoArchivo, TipoAportante, AnioRecibo, MesRecibo
	),
	cteFactCondicionCotizanteUnique AS
	(
		SELECT TipoIdentificacionAportante, NumeroIdentificacionAportante, TipoIdentificacionCotizante, NumeroIdentificacionCotizante, EstadoArchivo, TipoAportante, AnioRecibo, MesRecibo, NovedadIngreso, NovedadRetiro
		FROM cteFactCondicionCotizanteArchivoExitoso
		UNION ALL
		SELECT TipoIdentificacionAportante, NumeroIdentificacionAportante, TipoIdentificacionCotizante, NumeroIdentificacionCotizante, EstadoArchivo, TipoAportante, AnioRecibo, MesRecibo, MAX(NovedadIngreso) NovedadIngreso, MAX(NovedadRetiro) NovedadRetiro
		FROM cteCondicionCotizante cte
		WHERE EstadoArchivo = 'Inconsistente'
		AND NOT EXISTS (
			SELECT 1 
			FROM cteFactCondicionCotizanteArchivoExitoso cte1 
			WHERE cte.TipoIdentificacionAportante = cte1.TipoIdentificacionAportante AND cte.NumeroIdentificacionAportante = cte1.NumeroIdentificacionAportante
			AND cte.TipoIdentificacionAportante = cte1.TipoIdentificacionAportante AND cte.NumeroIdentificacionAportante = cte1.NumeroIdentificacionAportante
			)
		GROUP BY TipoIdentificacionAportante, NumeroIdentificacionAportante, TipoIdentificacionCotizante, NumeroIdentificacionCotizante, EstadoArchivo, TipoAportante, AnioRecibo, MesRecibo
	),
	cteFactCondicionCotizante AS
	(
		SELECT DISTINCT
			(SELECT dipId FROM dbo.DimPeriodo dip WHERE dip.dipAnio = cte.AnioRecibo AND dip.dipMes = cte.MesRecibo) AS fccDimPeriodo,
			(SELECT dapId FROM dbo.DimAportante dap WHERE dap.dapTipoIdentificacion = cte.TipoIdentificacionAportante AND dap.dapNumeroIdentificacion = cte.NumeroIdentificacionAportante) AS fccDimAportante,
			(SELECT dcoId FROM dbo.DimCotizante dco WHERE dco.dcoTipoIdentificacion = cte.TipoIdentificacionCotizante AND dco.dcoNumeroIdentificacion = cte.NumeroIdentificacionCotizante) AS fccDimCotizante,
			(SELECT derId FROM dbo.DimEstadoRecaudo der WHERE der.derDescripcion = cte.EstadoArchivo) AS fccDimEstadoRecaudo,
			CASE WHEN cte.NovedadRetiro = 1 OR cte.NovedadIngreso = 1 THEN
				CASE WHEN cte.NovedadRetiro = 1 THEN 2 ELSE 1 END
			END AS fccDimTipoNovedad
		FROM cteFactCondicionCotizanteUnique cte
	)

	--drop table #cteFactCondicionCotizante
	SELECT *
	INTO #cteFactCondicionCotizante
	FROM cteFactCondicionCotizante	
	;
	
	
	MERGE dbo.FactCondicionCotizante AS T
	USING #cteFactCondicionCotizante AS S
	ON (S.fccDimPeriodo = T.fccDimPeriodo AND 
	S.fccDimAportante = T.fccDimAportante AND 
	S.fccDimCotizante = T.fccDimCotizante AND
	S.fccDimEstadoRecaudo = T.fccDimEstadoRecaudo AND
	S.fccDimTipoNovedad = T.fccDimTipoNovedad )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fccDimPeriodo,fccDimAportante,fccDimCotizante,fccDimEstadoRecaudo,fccDimTipoNovedad)
			VALUES (S.fccDimPeriodo,S.fccDimAportante,S.fccDimCotizante,S.fccDimEstadoRecaudo,S.fccDimTipoNovedad)
	WHEN MATCHED
		THEN UPDATE SET T.fccDimEstadoRecaudo = S.fccDimEstadoRecaudo,T.fccDimTipoNovedad=S.fccDimTipoNovedad;

;