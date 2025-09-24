package com.asopagos.aportes.composite.service.constants;

import com.asopagos.util.Interpolator;

/**
 * <b>Descripcion:</b> Enumeración en la que se listan los prototipos de mensaje de error que se generan en el módulo<br/>
 * <b>Módulo:</b> Asopagos - HU-211-397, HU-211-403, HU-211-404, HU-211-405, HU-211-399, HU-211-392<br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public enum MensajesErrorEnum {
    ERROR_CONSULTA_SIN_RESULTADO (1, "La consulta de {0} no arrojó resultados"), 
    ERROR_GENERAL_CONSULTA (2, "Se ha presentado un error en la consulta de {0} - {1}"), 
    ERROR_TIPO_ARCHIVO_NO_VALIDO (3, "El tipo de archivo '{0}', no es válido para esta operación"), 
    ERROR_CONDICIONES_INICIALES_397 (4, "El archivo '{0}', no cumple las condiciones iniciales para iniciar el proceso de registro o relación de aportes");
    
    /** Código del mensaje */
    private final Integer codigo;
    
    /** Cuerpo del mensaje */
    private final String mensaje;
    
    /**
     * Contructor de la enumeración
     * */
    private MensajesErrorEnum(Integer codigo, String mensaje){
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    /**
     * Obtiene el mensaje asosicado al valor de la enumeración, reemplezando las variables específicas.
     * @param params
     *        Array de strings para interpolar en el mensaje
     * @return String
     *         Con la cadena interpolada a partir de los varargs
     */
    public String getReadableMessage(String... params) {
        return codigo + ": " + Interpolator.interpolate(getMensaje(), params);
    }

    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }
}
