package com.asopagos.fovis.constants;

public class QueryConstants {

    public static final StringBuilder CONSULTA_SOLICITUDES_FOVIS_BASE = new StringBuilder();

    public static final String CONDICION_NRO_RADICADO = " soles.solNumeroRadicacion = :numeroRadicado ";
    
    public static final String CONDICION_ESTADO_NOVEDAD = " soles.estadoNovedad = :estadoSolicitud ";
    
    public static final String CONDICION_ESTADO_LEGALIZACION = " soles.estadoLegalizacion = :estadoSolicitud ";
    
    public static final String CONDICION_ESTADO_POSTULACION = " soles.estadoPostulacion = :estadoSolicitud ";
    
    public static final String CONDICION_TIPO_SOLICITUD = " soles.tipo = :tipoSolicitud ";
    
    public static final String CONDICION_FECHA_RADICADO_EXACTA = " CAST(soles.solFechaRadicacion AS DATE) = CAST(:fechaExactaRadicacion AS DATE) ";
    
    public static final String CONDICION_FECHA_RADICADO_ENTRE = " soles.solFechaRadicacion BETWEEN :fechaInicio AND :fechaFin ";
    
    public static final String CONDICION_ID_POSTULACION = " soles.idPostulacion = :idPostulacion ";
    
    static {
        CONSULTA_SOLICITUDES_FOVIS_BASE.append(" SELECT soles.solId, soles.solNumeroRadicacion, ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    soles.solFechaRadicacion, soles.tipo, soles.idSolicitudEspecifico, ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    soles.estadoNovedad, soles.estadoLegalizacion, soles.estadoPostulacion, ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    com.comIdentificaArchivoComunicado, soles.estadoSolicitud, soles.idPostulacion  ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append(" FROM ( ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    SELECT solId, solNumeroRadicacion, solFechaRadicacion, 'NOVEDAD_FOVIS' AS tipo, ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("      snfId AS idSolicitudEspecifico, snfEstadoSolicitud AS estadoNovedad, ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("      null AS estadoLegalizacion, null AS estadoPostulacion,  ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("      ISNULL(solResultadoProceso, snfEstadoSolicitud) AS estadoSolicitud, spfPostulacionFovis AS idPostulacion ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    FROM SolicitudNovedadFovis JOIN Solicitud  ON (snfSolicitudGlobal = solId) ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    JOIN SolicitudNovedadPersonaFovis ON (spfSolicitudNovedadFovis = snfId) ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append(" UNION ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    SELECT solId, solNumeroRadicacion, solFechaRadicacion, 'NOVEDAD_FOVIS', ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("      inoId, snfEstadoSolicitud, null, null, ISNULL(solResultadoProceso, snfEstadoSolicitud), spfPostulacionFovis ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    FROM IntentoNovedad JOIN Solicitud ON (inoSolicitud = solId) ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    JOIN SolicitudNovedadFovis ON (snfSolicitudGlobal = solId) ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    JOIN SolicitudNovedadPersonaFovis ON (spfSolicitudNovedadFovis = snfId) ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append(" UNION ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    SELECT solId, solNumeroRadicacion, solFechaRadicacion, 'LEGALIZACION_FOVIS', ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("      sldId, null, sldEstadoSolicitud, null, ISNULL(solResultadoProceso, sldEstadoSolicitud), sldPostulacionFovis ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    FROM SolicitudLegalizacionDesembolso JOIN Solicitud ON (sldSolicitudGlobal = solId) ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append(" UNION ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    SELECT solId, solNumeroRadicacion, solFechaRadicacion, 'LEGALIZACION_FOVIS', ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("      ildId, null, sldEstadoSolicitud, null, ISNULL(solResultadoProceso, sldEstadoSolicitud), sldPostulacionFovis ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    FROM IntentoLegalizacionDesembolso JOIN Solicitud ON (ildSolicitud = solId) ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    JOIN SolicitudLegalizacionDesembolso ON (sldSolicitudGlobal = solId)");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append(" UNION ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    SELECT solId, solNumeroRadicacion, solFechaRadicacion, 'POSTULACION_FOVIS', ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("      spoId, null, null, spoEstadoSolicitud, ISNULL(solResultadoProceso, spoEstadoSolicitud), spoPostulacionFovis ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    FROM SolicitudPostulacion JOIN Solicitud ON (spoSolicitudGlobal = solId) ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append(" UNION ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    SELECT solId, solNumeroRadicacion, solFechaRadicacion, 'POSTULACION_FOVIS', ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("      ipoId, null, null, spoEstadoSolicitud, ISNULL(solResultadoProceso, spoEstadoSolicitud), spoPostulacionFovis ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    FROM IntentoPostulacion JOIN Solicitud ON (ipoSolicitud = solId) ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("    JOIN SolicitudPostulacion ON (spoSolicitudGlobal = solId) ) soles ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append(" LEFT JOIN Comunicado com ON (com.comSolicitud = soles.solId ");
        CONSULTA_SOLICITUDES_FOVIS_BASE.append("   AND com.comId  = (SELECT MAX(coc.comId) FROM Comunicado coc WHERE coc.comSolicitud = soles.solId)) ");
    }
}

