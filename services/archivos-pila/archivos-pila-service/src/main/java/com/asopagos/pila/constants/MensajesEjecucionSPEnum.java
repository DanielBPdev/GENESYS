package com.asopagos.pila.constants;

import com.asopagos.util.Interpolator;

/**
 * <b>Descripcion:</b> Enumeración que contiene los mensajes derivados de la ejecución de procedimientos almacenados <br/>
 * <b>Módulo:</b> ArchivosPILAService - HU-211-395, HU-211-396, HU-211-480, HU-211-397, HU-211-398<br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public enum MensajesEjecucionSPEnum {
    ERROR_TIEMPO_EJECUCION_CONSULTA (1, "Error en la ejecución del procedimiento almacenado {0} - {1}");

    /** Código de la entrada de enumeración */
    private final Integer codigo;
    
    /** Descripción de la entrada de enumeración */
    private final String descripcion;
    
    /**
     * Constructor de la enumeración
     * */
    private MensajesEjecucionSPEnum(Integer codigo, String descripcion){
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el mensaje asosicado al valor de la enumeración, reemplezando las variables específicas.
     * @param params
     *        Array de strings para interpolar en el mensaje
     * @return String
     *         Con la cadena interpolada a partir de los varargs
     */
    public String getReadableMessage(String... params) {
        return codigo + ": " + Interpolator.interpolate(getDescripcion(), params);
    }

    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
}
