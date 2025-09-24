package com.asopagos.pila.constants;

import com.asopagos.util.Interpolator;

/**
 * <b>Descripcion:</b> Enumeración que lista los mensajes de error relacionados con
 * operaciones con los modelos de datos de la aplicación <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public enum MensajesErrorPersistenciaEnum {
    ERROR_CONSULTA("Error durante la consulta de {0}: {1}"), 
    ERROR_ACTUALIZACION("Error durante la actualización de {0}: {1}"),
    ERROR_EJECUCION_USP("Error durante la ejecución del procedimiento almacenado {0} - {1}: {2}"), 
    SIN_RESULTADOS("Consulta de {0} sin resultados"), 
    ERROR_BORRADO("Error durante el borrado de {0}: {1}");

    /** Mensaje de error */
    private final String mensaje;

    /**
     * Constructor de la enumeración
     */
    private MensajesErrorPersistenciaEnum(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @param indicePlanilla
     * @return
     */
    public String getReadableMessage(String... params) {
        return Interpolator.interpolate(getMensaje(), params);
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }
}
