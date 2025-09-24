--liquibase formatted sql

--changeset jmontana:01
--comment: Ajuste GLPI 54863, actualización slsFechaEvaluacionSegundoNivel casos migración
SELECT dsaSolicitudLiquidacionSubsidio, MIN (dsaFechaHoraCreacion) AS dsaFechaHoraCreacion
INTO #GLPI54863
FROM DetalleSubsidioAsignado
GROUP BY dsaSolicitudLiquidacionSubsidio

UPDATE a
SET a.slsFechaEvaluacionSegundoNivel = c.dsaFechaHoraCreacion
FROM SolicitudLiquidacionSubsidio a
INNER JOIN Solicitud b ON b.solId = a.slsSolicitudGlobal
LEFT JOIN #GLPI54863 c ON c.dsaSolicitudLiquidacionSubsidio = a.slsId
WHERE slsEstadoLiquidacion = 'CERRADA'
AND slsObservacionesProceso = 'MIGRACION'
AND solResultadoProceso = 'DISPERSADA'
AND slsFechaEvaluacionSegundoNivel IS NULL;