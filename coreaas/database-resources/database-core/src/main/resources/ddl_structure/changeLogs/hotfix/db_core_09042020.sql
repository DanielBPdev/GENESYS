--liquibase formatted sql

--changeset flopez:01
--comment:
UPDATE DatoTemporalSolicitud SET dtsJsonPayload = CAST(REPLACE(CAST(dtsJsonPayload as NVarchar(MAX)),'CASADO_UNION_LIBRE','CASADO') AS NText)
WHERE dtsSolicitud IN 
(SELECT sn.snoSolicitudGlobal FROM SolicitudNovedadPersona snp 
JOIN SolicitudNovedad sn ON sn.snoId = snp.snpSolicitudNovedad 
WHERE sn.snoEstadoSolicitud = 'ASIGNADA_AL_BACK')
AND dtsJsonPayload LIKE '%CASADO_UNION_LIBRE%';