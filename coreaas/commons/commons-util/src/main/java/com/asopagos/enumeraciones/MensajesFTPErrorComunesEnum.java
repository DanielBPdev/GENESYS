/**
 *
 *
 * 29 de nov. de 2016
 */
package com.asopagos.enumeraciones;

import com.asopagos.util.Interpolator;

/**
 * <b>Descripcion:</b> Enumeración que contiene los mensajes de error en persistencia generales<br/>
 * <b>Módulo:</b> EntidadDescuento - HU 432 <br/>
 *
 * @author <a href="mailto:jpiraban@heinsohn.com.co">Jhon Angel Piraban Castellanos</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co">Roy López Cardona.</a>
 */

public enum MensajesFTPErrorComunesEnum {

    ERROR_GENERAL_DATOS(1, "Error en la consulta - {0}"), 
    ERROR_CONSULTA_SIN_DATOS(2, "La consulta de {0} no arrojó resultados"), 
    ERROR_ARGUMENTO_COMPONENTE(3, "Error en la ejecución del componente, no se cuenta con argumento de línea o campo - {0}"),
    ERROR_DESCONEXION_SERVIDOR(4, "Error - No se puede desconectar por {0}"), 
    ERROR_LOGIN_SERVIDOR(5, "Error - Usuario o password incorrecto para realizar conexión {0}"),
    ERROR_CONEXION(6, "Se presentó un error al realizar la conexión {0} - {1}"),
    ERROR_LECTURA_ARCHIVOS(7, "Se presentó un error al intentar acceder a los archivos por {0} - {1}"),
    ERROR_DESCARGA_CONTENIDO(8, "Error al descargar el contenido del archivo remoto por {0}"),
    ERROR_PARAMETRIZACION_COMPONENTE(9, "Error al recibir datos de la configuración del componente para la presentación de "
            + "inconsistencias. La cantidad de campos y de valores en una inconsistencia de campos múltiples no coincide - {0}"), 
    ERROR_PARAMTRO_ID_LECTURA (10, "No se encontró el parámetro {0} en el sistema"),
    ERROR_INICIO_JOB_ETL (11, "No fue posible iniciar el llamado al procesamiento de la información en el motor de bases de datos"),
    ERROR_ESCRITURA_ARCHIVOS(12, "Se presentó un error al intentar enviar el archivo al servidor - {0}");
    
    private Integer codigo;

    private String descripcion;

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
     * @param codigo
     * @param descripcion
     */
    private MensajesFTPErrorComunesEnum(Integer codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
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
