package com.asopagos.afiliados.constants;

/**
 * <b>Descripcion:</b> Clase que contiene las constantes relacionadas con el procesamiento de sitios de pago e infrestructuras<br/>
 * <b>Módulo:</b> Asopagos - Control de Cambios 0227134 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ConstantesProcesoSitiosPago {

    /** Constructor privado para ocultar el constructor por defecto de Java */
    private ConstantesProcesoSitiosPago(){}

    /** Constante para el parámetro de consulta por id interno de tipos de tenencia e infraestructura */
    public static final String ID_INTERNO = "idInterno";
    
    /** Constante para parámetro de consulta ID de registro */
    public static final String ID_INF = "idInfraestructura";
    
    /** Constante para parámetro de consulta ID de la SSF */
    public static final String CODIGO_INF_SSF = "idSSF";
    
    /** Constante para parámetro de consulta por nombre */
    public static final String NOMBRE_INF = "nombre";
    
    /** Constante para parámetro de consulta tipo de infraestructura */
    public static final String TIPO_INF = "tipoInfraestructura";
    
    /** Constante para parámetro de consulta por zona */
    public static final String ZONA_INF = "zona";

    /** Constante para parámetro de consulta por área geográfica */
    public static final String AREA_GEOGRAFICA = "areaGeografica";
    
    /** Constante para parámetro de consulta ID de municipio */
    public static final String MUNICIPIO_INF = "municipio";
    
    /** Constante para parámetro de consulta por tipo de tenencia */
    public static final String TENENCIA_INF = "tenencia";
    
    /** Constante para parámetro de consulta para busqueda de registros activos */
    public static final String ESTADO = "estado";

    /** Constante para parámetro de consulta por departamento de infraestructura */
    public static final String DEPARTAMENTO_INF = "departamento";
    
    /** Constante para parámetro de consulta ID de registro */
    public static final String ID_SITIO = "idSitioPago";
    
    /** Constante para parámetro de consulta ID de la SSF */
    public static final String CODIGO_SITIO = "codigoSitio";
    
    /** Constante para parámetro de consulta por nombre */
    public static final String NOMBRE_SITIO = "nombreSitio";
    
    /** Constante para parámetro de consulta de sitio pago principal */
    public static final String SITIO_PAGO_PRINCIPAL = "principal";
    
    /** Constante para mensaje de tipo de Infraestructura */
    public static final String TIPO_INFRAESTRUCTURA = "Tipo de Infraestructura";
    
    /** Constante para mensaje de Infraestructura */
    public static final String INFRAESTRUCTURA = "Infraestructura";
    
    /** Constante para mensaje de Sitio de Pago */
    public static final String SITIO_PAGO = "Sitio de Pago";

    /** Constante para mensaje de Tipo de Tenencia */
    public static final String TIPO_TENENCIA = "Tipo de Tenencia";

    /** Constante para mensaje de campo de capacidad estimada de infraestructura */
    public static final String CAPACIDAD_ESTIMADA_INFRAESTRUCTURA = "Capacidad estimada para la nueva infraestructura";
}
