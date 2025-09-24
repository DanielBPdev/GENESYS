package com.asopagos.comunicados.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes asociadas a los Json
 * generados en distintas intancias de cada proceso del aplicativo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class TemporalJsonConstants {
    
    /**
     * Novedades Personas - NOTIFICACIÓN DE RADICACIÓN DE SOLICITUD DE NOVEDAD DE PERSONA (Comunicado 1 FRONT)
     */
    public static final String COMUNICADO_RADICACION_SOLICITUD_NOVEDAD = "{\"contexto\":{\"idInstancia\":%s,\"idSolicitud\":%s,\"mensajeExito\":true,\"mensaje\":\"La operación fue realizada de manera exitosa, puede continuar con el proceso.<br> Su número de radicado es: <b>%s</b>\",\"idTarea\":%s},\"HU331\":[{\"plantilla\":\"NTF_RAD_NVD_PER\",\"processName\":\"NOVEDADES_PERSONAS_PRESENCIAL\",\"idInstancia\":%s,\"idSolicitud\":%s},{\"plantilla\":\"NTF_NVD_PERS\",\"processName\":\"NOVEDADES_PERSONAS_PRESENCIAL\",\"urlRedirect\":\"134-140\",\"avanzarTarea\":true,\"invocaciones\":[],\"contexto\":{\"idSolicitud\":%s},\"idInstancia\":%s,\"idSolicitud\":%s}],\"uuid\":\"%s\"}";
    
    /**
     * Novedades Personas - NOTIFICACIÓN DE RESULTADOS DE SOLICITUD DE NOVEDAD PERSONAS (Comunicado 2 FRONT)
     */
    public static final String COMUNICADO_RESULTADOS_SOLICITUD_NOVEDAD_FRONT = "{\"HU331\":[{\"plantilla\":\"NTF_RAD_NVD_PER\",\"processName\":\"NOVEDADES_PERSONAS_PRESENCIAL\",\"idInstancia\":%s,\"idSolicitud\":%s,\"i\":0,\"esUltimo\":false,\"idECM\":{\"processName\":\"NOVEDADES_PERSONAS_PRESENCIAL\",\"docName\":\"NTF_RAD_NVD_PER_comunicado.pdf\",\"front\":false,\"fileName\":\"39351346-068c-46eb-bddd-c080647461c3.pdf\",\"fileType\":\"application/pdf\",\"identificadorDocumento\":\"39351346-068c-46eb-bddd-c080647461c3.pdf\",\"versionDocumento\":\"1576516461270901\",\"clasificable\":false}},{\"plantilla\":\"NTF_NVD_PERS\",\"processName\":\"NOVEDADES_PERSONAS_PRESENCIAL\",\"urlRedirect\":\"134-140\",\"avanzarTarea\":true,\"invocaciones\":[],\"contexto\":{\"idSolicitud\":%s},\"idInstancia\":%s,\"idSolicitud\":%s}],\"contexto\":{\"idInstancia\":%s,\"idSolicitud\":%s,\"mensajeExito\":true,\"mensaje\":\"La operación fue realizada de manera exitosa, puede continuar con el proceso.<br> Su número de radicado es: <b>%s</b>\",\"idTarea\":%s},\"uuid\":\"%s\"}";

    /**
     * Novedades Personas - NOTIFICACIÓN DE RESULTADOS DE SOLICITUD DE NOVEDAD PERSONAS (Comunicado BACK - SOLICITUD RECHAZADA)
     */
    public static final String COMUNICADO_RESULTADOS_SOLICITUD_NOVEDAD_RECHAZADA = "{\"contexto\":{\"idInstancia\":%s,\"idSolicitud\":%s,\"idTarea\":%s},\"HU331\":[{\"plantilla\":\"NTF_NVD_PERS\",\"processName\":\"NOVEDADES_PERSONAS_PRESENCIAL\",\"urlRedirect\":\"pendientes\",\"invocaciones\":[{\"id\":\"srvActualizarEstado\",\"peticion\":{\"method\":\"POST\",\"url\":\"%s/NovedadesService/rest/novedades/%s/estadoSolicitud?estadoSolicitud=CERRADA\"}}],\"avanzarTarea\":true,\"idInstancia\":%s,\"idSolicitud\":%s}],\"uuid\":\"%s\"}";
    
    /**
     * Afiliación Personas - NOTIFICACIÓN DE RESULTADOS DE SOLICITUD DE NOVEDAD PERSONAS (Comunicado BACK - SOLICITUD RECHAZADA)
     */
    public static final String COMUNICADO_RESULTADOS_SOLICITUD_AFILIACION_RECHAZADA = "{\"contexto\":{\"idInstancia\":\"%s\",\"idSolicitud\":%s,\"idTarea\":%s},\"HU331\":[{\"plantilla\":\"RCHZ_AFL_PER_POR_PROD_NSUBLE\",\"processName\":\"AFILIACION_PERSONAS_PRESENCIAL\",\"avanzarTarea\":true,\"idSolicitud\":%s}],\"uuid\":\"%s\"}";
    
    /**
     * Afiliación Personas - NOTIFICACIÓN DE RADICACIÓN DE SOLICITUD DE AFILIACIÓN PERSONAS (Comunicado 1 FRONT)
     */
    public static final String COMUNICADO_RADICACION_SOLICITUD_AFILIACION = "{\"contexto\":{\"idInstancia\":\"%s\",\"idSolicitud\":%s,\"idTarea\":%s},\"HU331\":[{\"plantilla\":\"NTF_RAD_AFL_PER\",\"processName\":\"AFILIACION_PERSONAS_PRESENCIAL\",\"urlRedirect\":\"140-asigSolicAfiPersona\",\"avanzarTarea\":true,\"idSolicitud\":%s}],\"uuid\":\"%s\"}";
    
    /**
     * Devolución aportes - NOTIFICACIÓN APROBACIÓN ANALISTA
     */
    public static final String COMUNICADO_SOLICITUD_APROBADA_ANALISTA_DEVOLUCION = "{\"contexto\":{\"idSolicitud\":%s,\"idInstancia\":\"%s\",\"idTarea\":%s},\"HU331\":[{\"plantilla\":\"NTF_APR_DVL_APT\",\"processName\":\"DEVOLUCION_APORTES\",\"avanzarTarea\":true,\"urlRedirect\":\"pendientes\",\"idSolicitud\":%s,\"idInstancia\":\"%s\"}],\"uuid\":\"%s\"}";
    
    /**
     * Correción aportes - NOTIFICACIÓN APROBACIÓN
     */
    public static final String COMUNICADO_SOLICITUD_APROBADA_ANALISTA_CORRECCION = "{\"contexto\":{\"idSolicitud\":%s,\"idInstancia\":\"%s\",\"idTarea\":%s},\"HU331\":[{\"plantilla\":\"NTF_APR_COR_APT\",\"processName\":\"CORRECCION_APORTES\",\"avanzarTarea\":true,\"idSolicitud\":%s,\"idInstancia\":\"%s\"}],\"uuid\":\"%s\"}";
    
    /**
     * Correción aportes - NOTIFICACIÓN RECHAZO
     */
    public static final String COMUNICADO_SOLICITUD_RECHAZADA_ANALISTA_CORRECCION = "{\"contexto\":{\"idSolicitud\":%s,\"idInstancia\":\"%s\",\"idTarea\":%s},\"HU331\":[{\"plantilla\":\"NTF_RCHZ_COR_APT\",\"processName\":\"CORRECCION_APORTES\",\"avanzarTarea\":true,\"idSolicitud\":%s,\"idInstancia\":\"%s\"}],\"uuid\":\"%s\"}";
    
    /**
     * Aportes Manuales - NOTIFICACIÓN APROBACIÓN
     */
    public static final String COMUNICADO_SOLICITUD_APROBADA_APORTE_MANUAL = "{\"contexto\":{\"idSolicitud\":%s,\"idInstancia\":\"%s\",\"idTarea\":%s},\"HU331\":[{\"plantilla\":\"%s\",\"processName\":\"PAGO_APORTES_MANUAL\",\"urlRedirect\":\"pendientes\",\"avanzarTarea\":true,\"historia\":482,\"idSolicitud\":%s}],\"uuid\":\"%s\"}";

    public static final String COMUNICADO_BIENVENIDA_EMPLEADOR = "{\"contexto\":{\"idInstancia\":\"%s\",\"idSolicitud\":%s,\"idTarea\":%s},\"HU331\":[{\"plantilla\":\"CRT_BVD_EMP\",\"processName\":\"AFILIACION_EMPRESAS_PRESENCIAL\",\"idSolicitud\":%s,\"idInstancia\":\"%s\"},{\"plantilla\":\"CRT_ACP_EMP\",\"processName\":\"AFILIACION_EMPRESAS_PRESENCIAL\",\"urlRedirect\":\"asigSolicAfiEmpleador\",\"avanzarTarea\":true,\"idSolicitud\":%s,\"idInstancia\":\"%s\"}],\"uuid\":\"%s\"}";

    public static final String COMUNICADO_ESPERA_CONFIRMACION = "{\"HU331\":[{\"plantilla\":\"NTF_RAD_NVD_EMP\",\"processName\":\"NOVEDADES_EMPRESAS_PRESENCIAL\",\"idSolicitud\":%s,\"idInstancia\":%s,\"i\":0,\"esUltimo\":false,\"idECM\":{\"processName\":\"NOVEDADES_EMPRESAS_PRESENCIAL\",\"docName\":\"NTF_RAD_NVD_EMP_comunicado.pdf\",\"front\":false,\"fileType\":\"application/pdf\",\"clasificable\":false}},{\"plantilla\":\"NTF_NVD_EMP\",\"processName\":\"NOVEDADES_EMPRESAS_PRESENCIAL\",\"urlRedirect\":\"131-140\",\"avanzarTarea\":true,\"invocaciones\":[],\"contexto\":{\"idSolicitud\":%s},\"idSolicitud\":%s,\"idInstancia\":%s}],\"contexto\":{\"idInstancia\":%s,\"idSolicitud\":%s,\"mensajeExito\":true,\"mensaje\":\"La operación fue realizada de manera exitosa, puede continuar con el proceso.<br> Su número de radicado es: <b>%s</b>\",\"idTarea\":%s},\"uuid\":\"%s\"}";

    public static final String COMUNICADO_CIERRE_NOVEDAD_EMPLEADOR = "{\"contexto\":{\"idInstancia\":%s,\"idSolicitud\":%s,\"idTarea\":%s},\"HU331\":[{\"plantilla\":\"NTF_NVD_EMP\",\"processName\":\"NOVEDADES_EMPRESAS_PRESENCIAL\",\"invocaciones\":[{\"id\":\"srvActualizarEstado\",\"peticion\":{\"method\":\"POST\",\"url\":\"%s/NovedadesService/rest/novedades/%s/estadoSolicitud?estadoSolicitud=CERRADA\"}}],\"avanzarTarea\":true,\"idSolicitud\":%s,\"idInstancia\":%s}],\"uuid\":\"%s\"}";
    
    public static final String COMUNICADO_INTENTO_AFILIACION_PERSONA = "{\"contexto\":{\"idInstancia\":%s,\"idSolicitud\":%s,\"contexto\":{\"tipoAfiliado\":\"%s\"},\"idTarea\":%s},\"HU331\":[{\"plantilla\":\"NTF_INT_AFL_DEP\",\"processName\":\"AFILIACION_PERSONAS_PRESENCIAL\",\"avanzarTarea\":true,\"idSolicitud\":%s}],\"uuid\":\"%s\"}";
   
    /**
     * Novedades Personas - NOTIFICACIÓN DE RADICACIÓN DE SOLICITUD DE NOVEDAD DE EMPLEADOR (Comunicado 1 FRONT)
     */
    public static final String COMUNICADO_RADICACION_SOLICITUD_NOVEDAD_EMPLEADOR = "{\"contexto\":{\"idInstancia\":%s,\"idSolicitud\":%s,\"mensajeExito\":true,\"mensaje\":\"La operación fue realizada de manera exitosa, puede continuar con el proceso.<br> Su número de radicado es: <b>%s</b>\",\"idTarea\":%s},\"HU331\":[{\"plantilla\":\"NTF_RAD_NVD_EMP\",\"processName\":\"NOVEDADES_EMPRESAS_PRESENCIAL\",\"idSolicitud\":%s,\"idInstancia\":%s},{\"plantilla\":\"NTF_NVD_EMP\",\"processName\":\"NOVEDADES_EMPRESAS_PRESENCIAL\",\"urlRedirect\":\"131-140\",\"avanzarTarea\":true,\"invocaciones\":[],\"contexto\":{\"idSolicitud\":%s},\"idSolicitud\":%s,\"idInstancia\":%s}],\"uuid\":\"%s\"}";
    
    /**
     * Novedades Personas - NOTIFICACIÓN DE RESULTADOS DE SOLICITUD DE NOVEDAD DE EMPLEADOR (Comunicado 2 FRONT)
     */
    public static final String COMUNICADO_RESULTADOS_SOLICITUD_NOVEDAD_FRONT_EMPLEADOR = "{\"HU331\":[{\"plantilla\":\"NTF_RAD_NVD_EMP\",\"processName\":\"NOVEDADES_EMPRESAS_PRESENCIAL\",\"idSolicitud\":%s,\"idInstancia\":%s,\"i\":0,\"esUltimo\":false,\"idECM\":{\"processName\":\"NOVEDADES_EMPRESAS_PRESENCIAL\",\"docName\":\"NTF_RAD_NVD_EMP_comunicado.pdf\",\"front\":false,\"fileName\":\"c66775f6-8863-4846-9980-7a2e4fde788c.pdf\",\"fileType\":\"application/pdf\",\"identificadorDocumento\":\"c66775f6-8863-4846-9980-7a2e4fde788c.pdf\",\"versionDocumento\":\"1598913243434520\",\"clasificable\":false}},{\"plantilla\":\"NTF_NVD_EMP\",\"processName\":\"NOVEDADES_EMPRESAS_PRESENCIAL\",\"urlRedirect\":\"131-140\",\"avanzarTarea\":true,\"invocaciones\":[],\"contexto\":{\"idSolicitud\":%s},\"idSolicitud\":%s,\"idInstancia\":%s}],\"contexto\":{\"idInstancia\":%s,\"idSolicitud\":%s,\"mensajeExito\":true,\"mensaje\":\"La operación fue realizada de manera exitosa, puede continuar con el proceso.<br> Su número de radicado es: <b>%s</b>\",\"idTarea\":%s},\"uuid\":\"%s\"}";

    /**
     * Novedades Dependientes - NOTIFICACIÓN DE RESULTADOS DE SOLICITUD DE NOVEDAD DE DEPENDIENTE WEB (Comunicado BACK)
     */
    public static final String COMUNICADO_RESULTADOS_SOLICITUD_NOVEDAD_DEPENDIENTE_WEB = "{\"contexto\":{\"idInstancia\":%s,\"idSolicitud\":%s,\"mensajeExito\":true,\"mensaje\":\"La operación fue realizada de manera exitosa, puede continuar con el proceso.\",\"idTarea\":%s},\"HU331\":[{\"plantilla\":\"NTF_NVD_WEB_TRB_EMP\",\"processName\":\"NOVEDADES_DEPENDIENTE_WEB\",\"urlRedirect\":\"pendientes\",\"invocaciones\":[{\"id\":\"srvActualizarEstado\",\"peticion\":{\"method\":\"POST\",\"url\":\"%s/NovedadesService/rest/novedades/%s/estadoSolicitud?estadoSolicitud=CERRADA\"}}],\"avanzarTarea\":true,\"idInstancia\":%s,\"idSolicitud\":%s}],\"uuid\":\"%s\"}";
}


