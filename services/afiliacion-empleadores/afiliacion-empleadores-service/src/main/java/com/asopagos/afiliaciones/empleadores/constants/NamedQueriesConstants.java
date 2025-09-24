package com.asopagos.afiliaciones.empleadores.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * Clase que contiene las constantes con los nombres de los NamedQueries del
 * servicio
 * <b>Módulo:</b> Asopagos - HU <br/>
 * Transversal
 *
 * @author Josuè Nicoàs Pinzòn Villamil
 * <a href="mailto:jopinzon@heinsohn.com.co"> jopinzon@heinsohn.com.co</a>
 */
public class NamedQueriesConstants {

    /**
     * Buscar solicitud por persona
     */
    public static final String BUSCAR_SOLICTUD = "Afiliaciones.afiliacion.buscarSolicitud";

    /**
     * Buscar personas
     */
    public static final String BUSCAR_PERSONA = "Afiliaciones.solictud.persona";

    /**
     * Buscar personas
     */
    public static final String BUSCAR_EMPLEADOR = "Afiliaciones.solictud.empleador";

    /**
     * Buscar identificador de solicitud
     */
    public static final String BUSCAR_EMPLEADOR_ID = "Afiliaciones.solictud.empleadorID";

    /**
     * Buscar solicitudes ordenadas por el número de radicación
     */
    public static final String BUSCAR_SOLICTUD_ORDENADO_POR_NUMERO_RADICACION = "Afiliaciones.solictud.ordenadoPor.NumeroRadicacion";

    /**
     * Buscar caja compensacion
     */
    public static final String BUSCAR_ID_DPTO = "Afiliaciones.id_dpto";

//  /**
//   * NamedQuery para actualizar el estado de la solicitud de afiliación del empleador
//   */
//    public static final String ACTUALIZAR_ESTADO_SOLICITUD_AFILIACION_EMPLEADOR = "Afiliaciones.empleadores.solicitud.actualizarEstado";
    /**
     * NamedQuery para actualizar el estado de la documentación de afiliación
     * del empleador
     */
    public static final String ACTUALIZAR_ESTADO_DOCUMENTACION_AFILIACION_EMPLEADOR = "Afiliaciones.empleadores.solicitud.actualizarDocumentacion";

    /**
     * NamedQuery para buscar las solicitudes pendientes de aprobación de
     * consejo por un rango de fechas
     */
    public static final String BUSCAR_LISTA_SOL_AFILI_EMP_PENDIENTES_APROBACION = "Afiliaciones.empleadores.solicitud.pendientesAprobacion";

//  /**
//   * NamedQuery para buscar las solicitudes pendientes de aprobación de consejo por un rango de fechas
//   */
//  public static final String ACTUALIZAR_SOL_AFILI_EMP_FECHA_APROBACION_CONSEJO_NUMERO_ACTO_ADMIN = "Afiliaciones.empleadores.solicitud.fechaAprobacion.numeroActoAdmin";
//    /**
//     * NamedQuery para buscar las solicitudes pendientes de aprobación de consejo por un rango de fechas
//     */
//    public static final String ELIMINAR_PRODUCTO_NO_CONFORME = "Afiliaciones.productoNoConforme.eliminar";
    /**
     * NamedQuery para buscar la solicitud afiliacion empleador
     */
    public static final String BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR = "Afiliaciones.empleadores.consultar.solicitud";

    /**
     * Consulta EmpresaDesentralizada
     */
    public static final String CONSULTA_PREREGISTRO_DESCENTRALIZADA = "Empleador.consultar.empresa.descentralizada";

    public static final String BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR_BY_EMPLEADOR = "SolicitudAfiliacionEmpleador.buscarPorEmpleador.by.empleador";

    /**
     * NamedQuery para buscar la solicitud afiliacion empleador por el número de
     * radicado dado
     */
    public static final String BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR_POR_NUMERO_RADICADO = "Afiliaciones.empleadores.consultar.solicitud.numeroRadicado";

    public static final String BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR_POR_INSTANCIA_PROCESO = "Afiliaciones.empleadores.consultar.solicitud.instanciaProceso";


    /**
     * NamedQuery para buscar la solicitud afiliacion empleador por numero de
     * identificacion del empleador sin número de radicación
     */
    public static final String BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR_NUMEROID_SIN_RADICADO = "Afiliaciones.empleadores.consultar.solicitud_numeroID_sinRadicado";

    /**
     * NamedQuery para buscar la solicitud afiliacion empleador por numero de
     * identificacion del empleador con número de radicación
     */
    public static final String BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR_NUMEROID_CON_RADICADO = "Afiliaciones.empleadores.consultar.solicitud_numeroID_numeroRadicado";

    /**
     * NamedQuery para buscar la solicitud afiliacion empleador sin número de
     * radiación
     */
    public static final String BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR_SIN_RADICADO = "Afiliaciones.empleadores.consultar.solicitud_sinRadicado";

    /**
     * Nombre del named query para buscar solicutdes de afiliación por empleador
     */
    public static final String NAMED_QUERY_SOLIC_EMPL = "SolicitudAfiliacionEmpleador.buscarPorEmpleador";

    /**
     * Nombre del named query para buscar solicutdes de afiliación por persona
     */
    public static final String NAMED_QUERY_SOLIC_EMPL_PERSONA = "SolicitudAfiliacionEmpleador.buscarPorPersona";

    public static final String BUSCAR_SOLICITUDES_AFILIACION_EMPLEADOR = "SolicitudAfiliacionEmpleador.buscar.solicitudes.afiliacion.empleador";

}
