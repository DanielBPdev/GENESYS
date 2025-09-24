-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/04/10
-- Description:	Calculo de estados de afiliacion de Beneficiarios
-- =============================================
CREATE VIEW VW_EstadoAfiliacionBeneficiario
AS

	WITH cteEstados
	AS
	(
		SELECT estado, prioridad
		FROM (VALUES
		('ACTIVO',1),
		('INACTIVO', 2),
		(NULL,99)) AS T (estado, prioridad)		
	)

	
	SELECT perId, perTipoIdentificacion, perNumeroIdentificacion, cte.estado benEstadoBeneficiarioAfiliado
	FROM (
		SELECT per.perId, per.perTipoIdentificacion, per.perNumeroIdentificacion,
				MIN(ISNULL(cte.prioridad,99)) prioridad
		FROM  persona per
		INNER JOIN beneficiario ben ON (per.perId = ben.benPersona)
		INNER JOIN cteEstados cte ON ISNULL(benEstadoBeneficiarioAfiliado,'') = ISNULL(cte.estado,'')
		GROUP BY per.perId, perId, per.perTipoIdentificacion, per.perNumeroIdentificacion
	) T
	INNER JOIN cteEstados cte ON T.prioridad = cte.prioridad
				
;