--liquibase formatted sql

--changeset mamonroy:01
--comment:
UPDATE DatoTemporalSolicitud 
SET dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPersona.tipoUnionConyugal',NULL)
WHERE dtsSolicitud IN 
(SELECT sn.snoSolicitudGlobal FROM SolicitudNovedadPersona snp 
JOIN SolicitudNovedad sn ON sn.snoId = snp.snpSolicitudNovedad 
WHERE sn.snoEstadoSolicitud = 'ASIGNADA_AL_BACK')
AND (ISJSON(CAST(dtsJsonPayload AS NVARCHAR(MAX))) > 0);