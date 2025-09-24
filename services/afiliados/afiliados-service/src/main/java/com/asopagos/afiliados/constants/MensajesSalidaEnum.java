/**
 * 
 */
package com.asopagos.afiliados.constants;

import com.asopagos.util.Interpolator;

/**
 * <b>Descripcion:</b> Enumeración que contiene los prototipos de mensaje de salida del servicio<br/>
 * <b>Módulo:</b> Asopagos - Control de Cambios 0227134 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
public enum MensajesSalidaEnum {
    ERROR_REGISTRO_GENERAL ("Se presentó un error inesperado durante el registro de {0} :: {1}."),
    ERROR_CONSULTA ("Se presentó un error inesperado durante la consulta de {0} :: {1}."),
    ERROR_CONSULTA_RESPUESTA_MULTIPLE ("La consulta de {0}, retornó un resultado múltiple cuando se esperaba una respuesta única."),
    ERROR_REGISTRO_EXISTENTE ("No se pudo completar el registro de {0}, el registro ya existe en el sistema."),
    ERROR_REGISTRO_INCOMPLETO ("No se pudo completar el registro de {0}, no se cuenta con el dato obligatorio {1}."),
    ERROR_ACTUALIZACION_SIMPLE ("Se presentó un error durante la actualización del registro de {0}"),
    ERROR_ACTUALIZACION_COMPLETO (ERROR_ACTUALIZACION_SIMPLE.getMensaje() + " :: {1}"),
    REGISTRO_EXITOSO ("El registro de {0} se ha completado con éxito. El código para el nuevo registro es {1}."),
    ACTUALIZACION_EXITOSA ("La actualización del registro de {0} se ha completado con éxito."),
    CONSULTA_SIN_RESULTADOS ("La consulta de {0} no arrojó resultados.");

    /** Mensaje de error */
    private final String mensaje;

    /**
     * Constructor de la enumeración
     */
    private MensajesSalidaEnum(String mensaje) {
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
