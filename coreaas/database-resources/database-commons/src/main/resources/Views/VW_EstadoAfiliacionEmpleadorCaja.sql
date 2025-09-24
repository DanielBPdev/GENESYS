-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/04/10
-- Description:	Calculo de estado de afiliacion de Empleador frente a la Caja
-- =============================================
CREATE OR ALTER VIEW VW_EstadoAfiliacionEmpleadorCaja
AS

	WITH cteEstados
	AS
	(
		SELECT estado, prioridad
		FROM (VALUES
		('ACTIVO',1),
		('INACTIVO', 2),
		('NO_FORMALIZADO_RETIRADO_CON_APORTES',3),
		('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES',4),
		('NO_FORMALIZADO_CON_INFORMACION',5),
		(NULL,99)) AS T (estado, prioridad)		
	)

	SELECT perId,perTipoIdentificacion,perNumeroIdentificacion,
			CASE WHEN Empresa.empId IS NOT NULL THEN 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' END empEstadoEmpleador 
	FROM Persona
	INNER JOIN Empresa ON Persona.perId = Empresa.empPersona
	LEFT JOIN  Empleador ON Empresa.empId = Empleador.empEmpresa AND Empleador.empEstadoEmpleador IS NOT NULL
	WHERE Empleador.EmpId IS NULL
	AND EXISTS (	SELECT 1
					FROM AporteGeneral 
					WHERE AporteGeneral.apgEmpresa = Empresa.EmpId
				)
	
	UNION ALL

	SELECT perId,perTipoIdentificacion,perNumeroIdentificacion,
			CASE WHEN Empresa.empId IS NOT NULL THEN 'NO_FORMALIZADO_CON_INFORMACION' END empEstadoEmpleador
	FROM Persona
	INNER JOIN Empresa ON Persona.perId = Empresa.empPersona
	LEFT JOIN  Empleador ON Empresa.empId = Empleador.empEmpresa AND Empleador.empEstadoEmpleador IS NOT NULL
	WHERE Empleador.EmpId IS NULL
	AND NOT EXISTS (SELECT 1
					FROM AporteGeneral 
					WHERE AporteGeneral.apgEmpresa = Empresa.EmpId
				)
	
	UNION ALL
	
	SELECT perId, perTipoIdentificacion, perNumeroIdentificacion, cte.estado roaEstadoAfiliado
	FROM (
		SELECT perId,perTipoIdentificacion,perNumeroIdentificacion, MIN(ISNULL(cte.prioridad,99)) prioridad
		FROM Persona
		INNER JOIN Empresa ON Persona.perId = Empresa.empPersona
		INNER JOIN Empleador ON Empresa.empId = Empleador.empEmpresa AND Empleador.empEstadoEmpleador IS NOT NULL
		LEFT JOIN (SELECT AporteGeneral.apgEmpresa, MAX(CONVERT(DATE, apgPeriodoAporte+'-01', 121)) apgPeriodoAporte 
					FROM AporteGeneral
					GROUP BY AporteGeneral.apgEmpresa) apg ON apg.apgEmpresa = Empresa.EmpId
		LEFT JOIN cteEstados cte ON ISNULL((CASE WHEN apg.apgEmpresa IS NOT NULL AND empEstadoEmpleador = 'INACTIVO' 
					THEN (CASE WHEN empFechaRetiro < apg.apgPeriodoAporte THEN 'NO_FORMALIZADO_RETIRADO_CON_APORTES' ELSE empEstadoEmpleador END)
					ELSE empEstadoEmpleador END),'') = ISNULL(cte.estado,'')
		GROUP BY perId,perTipoIdentificacion,perNumeroIdentificacion 
	) T
	INNER JOIN cteEstados cte ON T.prioridad = cte.prioridad
	
;
