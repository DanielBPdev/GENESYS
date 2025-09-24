-- =============================================
-- Author:		Juan Diego Ocampo Q.
-- Create date: 2019/11/21
-- Description:	Script encargado de reestablecer el estado de una planilla para reiniciar su ejecución
-- =============================================
DECLARE @PilaIndicePlanilla bigint
DECLARE @motivoProcesoManual varchar(20)
DECLARE @planillas AS TABLE (numeroPlanilla bigint)
DECLARE @PilaIndicePlanillaTMP AS TABLE (pipId bigint, pipMotivoProcesoManual varchar(20))
DECLARE @timestamp BIGINT

SELECT @timestamp = CAST(replace(convert(varchar, getdate(),23),'-','') + replace(convert(varchar, getdate(),108),':','') AS BIGINT)
SELECT @timestamp

SELECT pipEstadoArchivo, pipTipoArchivo, COUNT(1) FROM [dbo].[PilaIndicePlanilla] GROUP BY pipEstadoArchivo, pipTipoArchivo ORDER BY pipEstadoArchivo, pipTipoArchivo
------------------------------------------------------------------------------------------------
-- MUNDO 2
-- Estado de las planillas que se quieren reiniciar
------------------------------------------------------------------------------------------------
INSERT INTO  @planillas (numeroPlanilla)  -- pipIdPlanilla
-- VALUES (00000000) -- en caso que se desee reiniciar una planilla en particular se debe realizar el insert especifico
SELECT pipIdPlanilla 
FROM  [dbo].[PilaIndicePlanilla] 
WHERE pipEstadoArchivo IN(
	'PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD',
	'PROCESADO_VS_BD',
	'RECAUDO_CONCILIADO'
)

SELECT COUNT(1) FROM @planillas
INSERT INTO @PilaIndicePlanillaTMP (pipId, pipMotivoProcesoManual)
SELECT pipId, pipMotivoProcesoManual 
FROM PilaIndicePlanilla 
INNER JOIN @planillas ON pipIdPlanilla = numeroPlanilla 

-- registro de control
DELETE FROM staging.ControlEjecucionPlanillas 
WHERE cepIdPlanilla IN (
	SELECT pipId FROM [dbo].[PilaIndicePlanilla] INNER JOIN @planillas ON numeroPlanilla = pipIdPlanilla
)

INSERT INTO staging.ControlEjecucionPlanillas (cepId, cepIdPaquete, cepIdPlanilla, cepEjecutado)
SELECT NEXT VALUE FOR Sec_ControlEjecucionPlanillas, @timestamp, pipId, 0 FROM @PilaIndicePlanillaTMP

WHILE EXISTS (SELECT TOP 1 * FROM @PilaIndicePlanillaTMP)
BEGIN

	SELECT TOP 1
		@PilaIndicePlanilla = pipId, 
		@motivoProcesoManual = pipMotivoProcesoManual
	FROM @PilaIndicePlanillaTMP

	EXEC [dbo].[USP_ReiniciarProcesamiento] @PilaIndicePlanilla, @motivoProcesoManual, 0

	DELETE FROM @PilaIndicePlanillaTMP WHERE pipId = @PilaIndicePlanilla

END


SELECT pipEstadoArchivo, pipTipoArchivo, COUNT(1) FROM [dbo].[PilaIndicePlanilla] GROUP BY pipEstadoArchivo, pipTipoArchivo ORDER BY pipEstadoArchivo, pipTipoArchivo

EXEC [dbo].[USP_ExecutePILA2] 0, 'SIN_PARAMETRO', 0, 'SISTEMA', @timestamp