-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/04/10
-- Description:	Calculo de estados de afiliacion de Persona frente a empresas
-- =============================================
CREATE VIEW VW_EstadoAfiliacionPersonaEmpresa
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
	
	SELECT DISTINCT
	Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion,
	PersonaEmpleador.perId AS perIdEmpleador,PersonaEmpleador.perTipoIdentificacion AS perTipoIdentificacionEmpleador,PersonaEmpleador.perNumeroIdentificacion AS perNumeroIdentificacionEmpleador,
	Empleador.empId AS roaEmpleador
	,'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' roaEstadoAfiliado
	FROM Persona with (nolock)
	INNER JOIN AporteDetallado apd with (nolock) ON apd.apdPersona = Persona.PerId
	INNER JOIN AporteGeneral apg with (nolock) ON apg.apgId = apd.apdAporteGeneral
	INNER JOIN Empresa with (nolock) ON apg.apgEmpresa = Empresa.EmpId
	INNER JOIN Persona PersonaEmpleador with (nolock) ON PersonaEmpleador.perId = Empresa.empPersona
	LEFT JOIN Empleador with (nolock) ON Empleador.empEmpresa = Empresa.empId
	WHERE 
		NOT EXISTS (SELECT 1
					FROM Afiliado with (nolock)
					INNER JOIN RolAfiliado with (nolock) ON Afiliado.afiId = RolAfiliado.roaAfiliado
					INNER JOIN Empleador with (nolock) ON RolAfiliado.roaEmpleador = Empleador.empId
					WHERE Afiliado.afiPersona =  Persona.perId
					AND Empresa.empId = Empleador.empEmpresa
					AND roaEstadoAfiliado IS NOT NULL)

	UNION ALL

	SELECT
	Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion,
	NULL AS perIdEmpleador,
	NULL AS perTipoIdentificacionEmpleador,
	NULL AS perNumeroIdentificacionEmpleador,
	CAST(NULL AS BIGINT) AS roaEmpleador
	,'NO_FORMALIZADO_CON_INFORMACION' roaEstadoAfiliado
	FROM Persona
	WHERE
	NOT EXISTS (SELECT 1
					FROM Afiliado with (nolock)
					INNER JOIN RolAfiliado with (nolock) ON Afiliado.afiId = RolAfiliado.roaAfiliado
					INNER JOIN Empleador with (nolock) ON RolAfiliado.roaEmpleador = Empleador.empId
					WHERE Afiliado.afiPersona =  Persona.perId
					AND roaEstadoAfiliado IS NOT NULL)
	AND NOT EXISTS (	SELECT 1
					FROM AporteGeneral apg with (nolock)
					INNER JOIN AporteDetallado apd with (nolock) ON apg.apgId = apd.apdAporteGeneral
					WHERE apd.apdPersona = Persona.PerId
				)

	UNION ALL

	SELECT perId, perTipoIdentificacion, perNumeroIdentificacion,
	perIdEmpleador, perTipoIdentificacionEmpleador, perNumeroIdentificacionEmpleador,
	roaEmpleador
	,cte.estado roaEstadoAfiliado
	FROM (
		SELECT
		Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion,
		PersonaEmpleador.perId AS perIdEmpleador,PersonaEmpleador.perTipoIdentificacion AS perTipoIdentificacionEmpleador,PersonaEmpleador.perNumeroIdentificacion AS perNumeroIdentificacionEmpleador,
		roaEmpleador,MIN(ISNULL(cte.prioridad,99)) prioridad
		FROM Persona with (nolock)
		CROSS JOIN Persona PersonaEmpleador with (nolock)
		INNER JOIN Empresa with (nolock) ON PersonaEmpleador.perId = Empresa.empPersona
		INNER JOIN Afiliado with (nolock) ON Persona.perId = Afiliado.afiPersona
		INNER JOIN RolAfiliado with (nolock) ON Afiliado.afiId = RolAfiliado.roaAfiliado AND roaEstadoAfiliado IS NOT NULL
		INNER JOIN Empleador with (nolock) ON RolAfiliado.roaEmpleador = Empleador.empId AND Empresa.empId = Empleador.empEmpresa
		LEFT JOIN (SELECT apd.apdPersona, MAX(CONVERT(DATE, apg.apgPeriodoAporte + '-01', 121)) apgPeriodoAporte, apg.apgEmpresa
					FROM AporteGeneral apg with (nolock)
					INNER JOIN AporteDetallado apd with (nolock) ON apg.apgId = apd.apdAporteGeneral
					GROUP BY apd.apdPersona, apg.apgEmpresa) apg ON apg.apdPersona = Persona.PerId  AND apg.apgEmpresa = Empresa.empId
		LEFT JOIN cteEstados cte ON ISNULL((
				CASE 
					WHEN apg.apdPersona IS NOT NULL AND ISNULL(roaEstadoAfiliado, 'INACTIVO') = 'INACTIVO' 
					THEN (
						CASE 
							WHEN roaFechaRetiro < apg.apgPeriodoAporte 
							THEN 'NO_FORMALIZADO_RETIRADO_CON_APORTES' 
							ELSE ISNULL(CAST(roaEstadoAfiliado AS VARCHAR(50)), 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES') 
						END)
					ELSE ISNULL(CAST(roaEstadoAfiliado AS VARCHAR(50)), 
						CASE WHEN apg.apdPersona IS NOT NULL THEN 'NO_FORMALIZADO_RETIRADO_CON_APORTES' ELSE 'NO_FORMALIZADO_CON_INFORMACION' END) 
				END),'') = ISNULL(cte.estado,'')
		GROUP BY Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion,
		PersonaEmpleador.perId,PersonaEmpleador.perTipoIdentificacion,PersonaEmpleador.perNumeroIdentificacion,
		roaEmpleador
	) T
	INNER JOIN cteEstados cte ON T.prioridad = cte.prioridad

;
