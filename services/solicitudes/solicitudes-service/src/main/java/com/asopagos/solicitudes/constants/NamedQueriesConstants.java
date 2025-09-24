package com.asopagos.solicitudes.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * Clase que contiene las constantes con los nombres de los NamedQueries del
 * servicio <b>Módulo:</b> Solicitudes <br/>
 *
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class NamedQueriesConstants {

    /**
     * Buscar asignacion a ser escalada
     */
    public static final String BUSCAR_ASIGNACION_ESCALADA = "Afiliaciones.escalar.buscarAsignacion";

    /**
     * Buscar un dato temporal para el id de solicitud especificado
     */
    public static final String SOLICITUDES_CONSULTAR_DATOS_TEMPORALES = "Solicitudes.consultar.datosTemporales";

    /**
     * Buscar un dato temporal para el id de solicitud especificado
     */
    public static final String BUSCAR_SOLICTUD_POR_NUMERO_RADICADO = "Solicitudes.buscarSolicitud.porNumeroRadicado";

    /**
     * Buscar un dato temporal para el id de instancia del proceso
     */
    public static final String BUSCAR_SOLICTUD_POR_INSTANCIA_PROCESO = "Solicitudes.buscarSolicitud.porIdInstanciaProceso";

    /**
     * Constante con el nombre de la query para consultar por el id de la
     * solicitud una trazabilidad dada.
     */
    public static final String CONSULTAR_TRAZABILIDAD_ID = "Solicitudes.Trazabilidad.buscarPorIdSolicitud";

    /**
     * Nombre del named query para buscar solicutdes de afiliación por empleador
     */
    public static final String NAMED_QUERY_SOLIC_EMPL_ESTADO_TEMPORAL = "SolicitudAfiliacionEmpleadorTemporalEstado.buscarPorPersona";

    /**
     * Nombre del named query para buscar documento por id de solicitud y
     * actividad.
     */
    public static final String BUSCAR_DOCUMENTO_ADMIN_ESTADO = "Solicitudes.DocumentoAdministracionEstado.buscarPorIdActividad";

    /**
     * Constante con el nombre de la query para consultar por el id de la
     * trazabilidad dada.
     */
    public static final String CONSULTAR_TRAZABILIDAD_POR_SOLICITUD_ACTIVIDAD = "Solicitudes.Trazabilidad.buscarPorSolicitudActividad";
    
    /**
     * Constante con el nombre de la consulta que obtiene la información de una
     * solicitud global por identificador
     */
    public static final String CONSULTAR_SOLICITUD = "Aportes.Consultar.Solicitud";
    /** 
     * Constante que contiene el nombre de la consulta de retorna la fecha de radicación 
     */
    public static final String CONSULTAR_FECHA_RADICACION_SOLICITUD="Solicitud.consultarFechaRadicacionSolicitud";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de solicitudes para la vista 360
     */
    public static final String CONSULTAR_SOLICITUD_POR_INSTANCIA_PROCESO = "Solicitudes.buscarSolicitud.porIdInstanciaProceso";
    
    /**
     * Constante con el nombre del SP que obtiene la lista de solicitudes para la vista 360
     */
    public static final String CONSULTAR_LISTA_SOLICITUDES = "Solicitud.StoredProcedures.USP_consultaListaSolicitudes360";
    
    /**
     * Constante con el nombre para la persistencia del resultado de la ejecución del utilitario del BPM
     */
    public static final String PERSISTIR_RESULTADO_EJECUCION_UTILITARIO_BPM = "Solicitud.persistir.resultadoEjecucionUtilitarioBPM";
    
    /**
     * Constante con el nombre de la consulta que obtiene el id de una solicitud global de una solicitud de afiliación de empleador
     */
    public static final String CONSULTAR_ID_SOLICITUD_GLOBAL_SOLICITUD_AFILIACION_EMPLEADOR = "Solicitud.buscarIdSolicitudGlobal.SolicitudAfiliacionEmpleador";

    public static final String SP_CALCULAR_TIEMPO_DESISITIR_SOLICITUD = "Solicitud.StoredProcedures.SP_CalcularTiempoDesistirSolicitud";

    public static final String CONSULTAR_SOLICITUDES_VIGENTES = "Solicitud.Consultar.Solicitudes.Vigentes";
}
