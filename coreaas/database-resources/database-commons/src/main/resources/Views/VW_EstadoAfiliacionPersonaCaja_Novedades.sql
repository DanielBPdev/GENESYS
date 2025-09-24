-- =============================================
-- Author: Robinson Castillo Capera
-- Create date: 2022/02/18
-- Description:	Calculo de estados de afiliacion de Persona frente a la Caja, se agrega el rolId.
-- =============================================
CREATE VIEW [dbo].[VW_EstadoAfiliacionPersonaCaja_Novedades]
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
	,'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' roaEstadoAfiliado, NULL roaFechaRetiro, 4 prioridad, null as roaId
	FROM Persona
	WHERE NOT EXISTS (SELECT 1
					FROM Afiliado
					INNER JOIN RolAfiliado ON Afiliado.afiId = RolAfiliado.roaAfiliado
					WHERE Afiliado.afiPersona =  Persona.perId
					AND roaEstadoAfiliado IS NOT NULL)
	AND EXISTS (	SELECT 1
					FROM AporteGeneral apg
					INNER JOIN AporteDetallado apd ON apg.apgId = apd.apdAporteGeneral
					WHERE apd.apdPersona = Persona.PerId
				)

	UNION ALL

	SELECT
	Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion
	,'NO_FORMALIZADO_CON_INFORMACION' roaEstadoAfiliado, NULL roaFechaRetiro, 5 prioridad, null as roaId
	FROM Persona
	WHERE NOT EXISTS (SELECT 1
					FROM Afiliado
					INNER JOIN RolAfiliado ON Afiliado.afiId = RolAfiliado.roaAfiliado
					WHERE Afiliado.afiPersona =  Persona.perId
					AND roaEstadoAfiliado IS NOT NULL)
	AND NOT EXISTS (	SELECT 1
					FROM AporteGeneral apg
					INNER JOIN AporteDetallado apd ON apg.apgId = apd.apdAporteGeneral
					WHERE apd.apdPersona = Persona.PerId
				)

	UNION ALL

	SELECT perId, perTipoIdentificacion, perNumeroIdentificacion, cte.estado roaEstadoAfiliado, roaFechaRetiro, cte.prioridad prioridad, roaId
	FROM (
		SELECT 
		Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion
		,MIN(ISNULL(cte.prioridad,99)) prioridad, CASE WHEN MIN(ISNULL(cte.prioridad,99)) >= 2 THEN MAX(RolAfiliado.roaFechaRetiro) ELSE NULL END roaFechaRetiro
		,roaId
		FROM Persona
		INNER JOIN Afiliado ON Persona.perId = Afiliado.afiPersona
		INNER JOIN RolAfiliado ON Afiliado.afiId = RolAfiliado.roaAfiliado AND roaEstadoAfiliado IS NOT NULL
		LEFT JOIN (SELECT apd.apdPersona, MAX(CONVERT(DATE, apg.apgPeriodoAporte + '-01', 121)) apgPeriodoAporte
					FROM AporteGeneral apg
					INNER JOIN AporteDetallado apd ON apg.apgId = apd.apdAporteGeneral
					GROUP BY apd.apdPersona) apg ON apg.apdPersona = Persona.PerId		
		LEFT JOIN cteEstados cte ON ISNULL(
			(CASE 
				WHEN apg.apdPersona IS NOT NULL AND ISNULL(roaEstadoAfiliado, 'INACTIVO') = 'INACTIVO' 
				THEN (
					CASE
						WHEN roaFechaRetiro < apg.apgPeriodoAporte 
						THEN 'NO_FORMALIZADO_RETIRADO_CON_APORTES'
						ELSE ISNULL(CAST(roaEstadoAfiliado AS VARCHAR(50)), 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES') 
					END)
				ELSE ISNULL(CAST(roaEstadoAfiliado AS VARCHAR(50)), 
					CASE WHEN apg.apdPersona IS NOT NULL THEN 'NO_FORMALIZADO_RETIRADO_CON_APORTES' ELSE 'NO_FORMALIZADO_CON_INFORMACION' END
				) 
			END),''
		) = ISNULL(cte.estado,'')
		GROUP BY Persona.perId,Persona.perTipoIdentificacion,Persona.perNumeroIdentificacion,roaId
	) T
	INNER JOIN cteEstados cte ON T.prioridad = cte.prioridad
;