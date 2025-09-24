package com.asopagos.tareashumanas.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class NamedQueriesConstants {
    
    public static final String OBTENER_TAREAS_PENDIENTES = "tareasHumanas.obtenerTareasFinalizadas";
    
    /**
     * Constante para la consulta de las tareas en BPM en estado CREATED sin propietario
     */
    public static final String OBTENER_TAREAS_CREADAS_SIN_PROPIETARIO = "tareasHumanas.obtenerTareasCreadasSinPropietario";

}
