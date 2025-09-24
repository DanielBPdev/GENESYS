package com.asopagos.pila.constants;

import com.asopagos.util.Interpolator;

/**
 * <b>Descripcion:</b> Enumeración que contiene los mensajes derivados de la gestión de estados de validación<br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 391, 407 y 393<br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
public enum MensajesGestionEstadosEnum {
    ERROR_CONSULTA_DATOS(1,"Error en la consulta de estados - {0}"),
    ERROR_LECTURA_ESTADO_PLANILLA(2,"Error en la lectura del estado de planillas - {0}"),
    ERROR_ACTUALIZACION_ESTADO_PLANILLA(3,"Error en la actualización del estado del archivo - {0}"),
    ERROR_ACTUALIZACION_ESTADO_REGISTRO_6(4,"Error en la actualización del estado del registro 6 en el archivo de Operador Financiero - {0}"),
    ERROR_ESTADO_NO_PROCEDENTE(5,"El cambio de estado solicitado no es procedente"),
    ERROR_HISTORIAL_ESTADO(6,"Error en el registro de la entrada de historial de estado de archivo - {0}");
    
    /** Código del mensaje */
    private Integer codigo;
    
    /** Cuerpo del mensaje */
    private String descripcion;
    
    /**
     * Constructor de la enumeración
     * */
    private MensajesGestionEstadosEnum(Integer codigo, String descripcion){
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
