-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/04/10
-- Description:	Calculo de estados de afiliacion de Persona como independiente
-- =============================================
CREATE VIEW VW_EstadoAfiliacionPersonaIndependiente
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

	SELECT
	Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion
	,'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' roaEstadoAfiliado, '' as roaClaseIndependiente
	FROM Persona
	WHERE NOT EXISTS (SELECT 1
					FROM Afiliado
					INNER JOIN RolAfiliado ON Afiliado.afiId = RolAfiliado.roaAfiliado AND RolAfiliado.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
					WHERE Afiliado.afiPersona =  Persona.perId)
	AND EXISTS (	SELECT 1
					FROM AporteGeneral apg
					INNER JOIN AporteDetallado apd ON apg.apgId = apd.apdAporteGeneral
					WHERE apd.apdPersona = Persona.PerId
				)

	UNION ALL

	SELECT
	Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion
	,'NO_FORMALIZADO_CON_INFORMACION' roaEstadoAfiliado,'' as roaClaseIndependiente
	FROM Persona
	WHERE NOT EXISTS (SELECT 1
					FROM Afiliado
					INNER JOIN RolAfiliado ON Afiliado.afiId = RolAfiliado.roaAfiliado AND RolAfiliado.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
					WHERE Afiliado.afiPersona =  Persona.perId)
	AND NOT EXISTS (	SELECT 1
					FROM AporteGeneral apg
					INNER JOIN AporteDetallado apd ON apg.apgId = apd.apdAporteGeneral
					WHERE apd.apdPersona = Persona.PerId
				)

	UNION ALL

	SELECT perId, perTipoIdentificacion, perNumeroIdentificacion, cte.estado roaEstadoAfiliado,roaClaseIndependiente
	FROM (
		SELECT
		Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion,roaClaseIndependiente
		,MIN(ISNULL(cte.prioridad,99)) prioridad
		FROM Persona
		INNER JOIN Afiliado ON Persona.perId = Afiliado.afiPersona
		INNER JOIN RolAfiliado ON Afiliado.afiId = RolAfiliado.roaAfiliado AND RolAfiliado.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
		LEFT JOIN (SELECT apd.apdPersona, MAX(CONVERT(DATE, apg.apgPeriodoAporte + '-01', 121)) apgPeriodoAporte
					FROM AporteGeneral apg
					INNER JOIN AporteDetallado apd ON apg.apgId = apd.apdAporteGeneral
					WHERE apg.apgTipoSolicitante = 'INDEPENDIENTE'
					GROUP BY apd.apdPersona) apg ON apg.apdPersona = Persona.PerId		
		LEFT JOIN cteEstados cte ON ISNULL((CASE WHEN apg.apdPersona IS NOT NULL AND roaEstadoAfiliado = 'INACTIVO' 
					THEN (CASE WHEN roaFechaRetiro < apg.apgPeriodoAporte THEN 'NO_FORMALIZADO_RETIRADO_CON_APORTES' ELSE roaEstadoAfiliado END)
					ELSE roaEstadoAfiliado END),'') = ISNULL(cte.estado,'')
		GROUP BY Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion,roaClaseIndependiente
	) T
	INNER JOIN cteEstados cte ON T.prioridad = cte.prioridad

;