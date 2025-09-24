package com.asopagos.constants;

import java.util.concurrent.TimeUnit;

/**
 * <b>Descripcion:</b> Clase que contiene valores de constantes útiles a nivel transversal<br/>
 * <b>Módulo:</b> Asopagos - Transversal <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ConstantesComunes {
    /**
     * Constructor privado para ocultar el constructor por defecto de clase
     * */
    private ConstantesComunes(){}
    
    /**
     * Constantes para mensaje de inicio y fin de logger en métodos
     * */
    public static final String INICIO_LOGGER = "Inicia servicio ";
    public static final String FIN_LOGGER = "Finaliza servicio ";
    public static final String FIN_LOGGER_ERROR = "Ocurrió un error inesperado en el servicio ";
    public static final String EJECUCION_LOGGER = "Ejecuta el método ";
    public static final String TIEMPO_EJECUCION = " tiempo ejecución en segundos: ";
    /**
     * Constante que representa el nombre del parametro personas
     */
    public static final String PERSONAS = "personas";
    
    /**
     * Constate que representa el nombre del parametro de empleadores
     */
    public static final String EMPLEADORES = "empleadores";
    
    /**
     * Constate que representa el nombre del parametro de empleadoresId
     */
    public static final String EMPLEADORES_ID = "empleadoresId";
    
    /**
     * Constate que representa el nombre del parametro de tipoRol
     */
    public static final String TIPO_ROL = "tipoRol";
    
    /**
     * Constante que representa el nombre del parametro beneficiario
     */
    public static final String BENEFICIARIOS = "beneficiarios";
    
    /**
     * Constante que representa el nombre de la llave del id de tarea
     */
    public static final String ID_TAREA = "idTarea";
    
    /**
     * Constante que representa el nombre de la llave del id de la instancia de proceso
     */
    public static final String ID_INSTANCIA_PROCESO = "idInstancia";
    
    /**
     * Constante que representa el nombre de la llave del id de una solicitud
     */
    public static final String ID_SOLICITUD = "idSolicitud";
    
    /**
     * Constante que representa el nombre de la llave del json del dato temporal del comunicado
     */
    public static final String JSON_DATO_TEMPORAL_COMUNICADO = "jsonPayloadDatoTemporalComunicado";
    
    /**
     * Constante que reprensenta la URL de la página de pendientes
     */
    public static final String URL_PENDIENTES = "pendientes";
    
    /**
     * Constante que representa el identificador único
     */
    public static final String UUID = "uuid";
    
    /**
     * Constante que representa el nombre de la llave del contexto del dato temporal del comunicado
     */
    public static final String CONTEXTO = "contexto";
    
    /**
     * Constante que representa el nombre de la llave del objeto que contiene las plantillas del del dato temporal del comunicado
     * 
     */
    public static final String HU331 = "HU331";
    
    /**
     * Constante que representa el nombre de la llave una plantilla del dato temporal del comunicado
     */
    public static final String PLANTILLA = "plantilla"; 
    
    /**
     * Constante que representa el nombre de la llave del proceso de una plantilla del dato temporal del comunicado
     */
    public static final String NOMBRE_PROCESO = "processName";
    
    /**
     * Constante que representa el nombre de la llave de la url a redirigir de una plantilla del dato temporal del comunicado
     */   
    public static final String URL_REDIRECCION = "urlRedirect";
    
    /**
     * Constante que representa el nombre de la llave de avanzar tarea de una plantilla del dato temporal del comunicado
     */  
    public static final String AVANZAR_TAREA = "avanzarTarea";
}
