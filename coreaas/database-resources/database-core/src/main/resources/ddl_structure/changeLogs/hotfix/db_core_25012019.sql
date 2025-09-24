--liquibase formatted sql

--changeset jocorrea:01
--comment: 
UPDATE DatoTemporalSolicitud
SET dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.avaluoCatastralVivienda',NULL)
WHERE ISJSON(CAST(dtsJsonPayload AS NVARCHAR(MAX))) > 0;

UPDATE DatoTemporalSolicitud
SET dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.avaluoCatastralVivienda',NULL)
WHERE ISJSON(CAST(dtsJsonPayload AS NVARCHAR(MAX))) > 0;

UPDATE DatoTemporalSolicitud
SET dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.avaluoCatastralVivienda',NULL)
WHERE ISJSON(CAST(dtsJsonPayload AS NVARCHAR(MAX))) > 0;

UPDATE DatoTemporalSolicitud
SET dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.avaluoCatastralVivienda',NULL)
WHERE ISJSON(CAST(dtsJsonPayload AS NVARCHAR(MAX))) > 0;