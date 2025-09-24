-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/04/10
-- Description:	Calculo de estados de afiliacion de Persona como pensionado
-- =============================================
CREATE VIEW VW_EstadoAfiliacionPersonaPensionado
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
	,'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' roaEstadoAfiliado
	FROM Persona
	WHERE NOT EXISTS (SELECT 1
					FROM Afiliado
					INNER JOIN RolAfiliado ON Afiliado.afiId = RolAfiliado.roaAfiliado AND RolAfiliado.roaTipoAfiliado = 'PENSIONADO'
					WHERE Afiliado.afiPersona =  Persona.perId
					AND roaEstadoAfiliado IS NOT NULL)
	AND EXISTS (	SELECT 1
					FROM AporteGeneral apg
					INNER JOIN AporteDetallado apd ON apg.apgId = apd.apdAporteGeneral
					WHERE apd.apdPersona = Persona.PerId
					AND apg.apgTipoSolicitante = 'PENSIONADO'
				)

	UNION ALL

	SELECT
	Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion
	,'NO_FORMALIZADO_CON_INFORMACION' roaEstadoAfiliado
	FROM Persona
	WHERE NOT EXISTS (SELECT 1
					FROM Afiliado
					INNER JOIN RolAfiliado ON Afiliado.afiId = RolAfiliado.roaAfiliado AND RolAfiliado.roaTipoAfiliado = 'PENSIONADO'
					WHERE Afiliado.afiPersona =  Persona.perId
					AND roaEstadoAfiliado IS NOT NULL)
	AND NOT EXISTS (	SELECT 1
					FROM AporteGeneral apg
					INNER JOIN AporteDetallado apd ON apg.apgId = apd.apdAporteGeneral
					WHERE apd.apdPersona = Persona.PerId
					AND apg.apgTipoSolicitante = 'PENSIONADO'
				)

	UNION ALL

	SELECT perId, perTipoIdentificacion, perNumeroIdentificacion, cte.estado roaEstadoAfiliado
	FROM (
		SELECT
		Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion, MIN(ISNULL(cte.prioridad,99)) prioridad
		FROM Persona
		INNER JOIN Afiliado ON Persona.perId = Afiliado.afiPersona
		INNER JOIN RolAfiliado ON Afiliado.afiId = RolAfiliado.roaAfiliado 
			AND RolAfiliado.roaTipoAfiliado = 'PENSIONADO'
			AND roaEstadoAfiliado IS NOT NULL
		LEFT JOIN (SELECT apd.apdPersona, MAX(CONVERT(DATE, apg.apgPeriodoAporte + '-01', 121)) apgPeriodoAporte
					FROM AporteGeneral apg
					INNER JOIN AporteDetallado apd ON apg.apgId = apd.apdAporteGeneral
					WHERE apg.apgTipoSolicitante = 'PENSIONADO'
					GROUP BY apd.apdPersona) apg ON apg.apdPersona = Persona.PerId		
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
		GROUP BY Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion 
	) T
	INNER JOIN cteEstados cte ON T.prioridad = cte.prioridad
;
