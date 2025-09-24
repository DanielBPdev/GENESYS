package com.asopagos.pila.constants;

import com.asopagos.util.Interpolator;

/**
 * <b>Descripcion:</b> Enumeración que lista los mensajes de error relacionados con
 * operaciones comunes de la aplicación <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public enum MensajesErrorComunesEnum {
    ERROR_REGISTRO_DETALLADO_FALTANTE("No se encuentra el Registro detallado solicitado (ID {0})"), 
    ERROR_REGISTRO_ORIGINAL_FALTANTE("No se cuentan con el registro de la planilla original con número {0}"), 
    ERROR_APORTE_ORIGINAL_FALTANTE("No se encontró un aporte detallado asociado al registro detallado {0}"), 
    ERROR_REGISTROS_DETALLADOS_FALTANTE("No se encontraron registros detallados asociados a la planilla {0}"), 
    ERROR_APORTE_TEMPORAL_FALTANTE("No se encontró un aporte temporal asociado al registro detallado {0} y  con recaudo mayor a cero");

    /** Mensaje de error */
    private final String mensaje;

    /**
     * Constructor de la enumeración
     */
    private MensajesErrorComunesEnum(String mensaje) {
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
