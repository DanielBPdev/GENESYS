--liquibase formatted sql

--changeset mamonroy:01
--comment:
DELETE FROM DatoTemporalComunicado
WHERE  (ISJSON(CAST(dtcJsonPayload AS NVARCHAR(MAX))) > 0) 
AND JSON_VALUE(CAST(dtcJsonPayload AS NVARCHAR(MAX)),'lax$.HU331[0].invocaciones[0].peticion.url') LIKE '%/NovedadesService/rest/novedades/52838/estadoSolicitud?estadoSolicitud=CERRADA';