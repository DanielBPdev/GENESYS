package com.asopagos.pila.constants;

import com.asopagos.util.Interpolator;

/**
 * <b>Descripcion:</b> Enumeración que contiene los mensajes derivados de la persistencia de datos de validación<br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 386, 387, 388, 390, 391, 407, 393<br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
public enum MensajesPersistenciaDatosEnum {
    ERROR_ACTUALIZACION_PROCESO (1, "Error en la actualización del proceso - {0}"),
    ERROR_ACTUALIZACION_INDICE (2, "Error en la escritura en el índice de planillas - {0}"),
    ERROR_LECTURA_PROCESO (3, "Error en lectura del listado de procesos - {0}"),
    ERROR_LECTURA_INDICE (4, "Error en lectura de índice de planillas - {0}"),
    ERROR_ACTUALIZACION_PASO_VALORES (5, "Error en la escritura de la información - {0}"),
    ERROR_LECTURA_REGISTRO (6, "Error en la lectura del registro {0} del archivo \"{1}\" - {2}"),
    ERROR_LECTURA_OPERADOR_INFORMACION (7, "Error en la lectura de datos de Operadores de Información - {0}"),
    ERROR_LECTURA_PARAMETROS_PROGRAMACION (8, "Error en la lectura de la configuración de ejecución programada - {0}"), 
    ERROR_ACTUALIZACION_INCONSISTENCIA (9, "Error en la actualización del log de inconsistencias de validación - {0}"),
	ERROR_LECTURA_OPERADOR_FINANCIERO (10, "Error en la lectura de datos de Operadores Financieros - {0}"),
    ERROR_LECTURA_BENEFICIO_LEY (10, "Error en la lectura de datos de Beneficio de Ley - {0}"),
    ERROR_LECTURA_CASOS_DESCUENTO_MORA (11, "Error en la consulta de parametrización de casos de descuento de valor de mora.");
    
    /** Código del mensaje */
    private Integer codigo;
    
    /** Cuerpo del mensaje */
    private String descripcion;
    
    /**
     * Constructor de la enumeración
     * */
    private MensajesPersistenciaDatosEnum(Integer codigo, String descripcion){
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
