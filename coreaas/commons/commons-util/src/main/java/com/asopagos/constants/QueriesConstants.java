package com.asopagos.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * Clase que contiene las constantes con los nombres de parametros y consultas del servicio <b>Módulo:</b> Asopagos - HU <br/>
 * Transversal
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class QueriesConstants {

    /**
     * Constante para el parametro de consula tipoIdentificacion
     */
    public static final String TIPO_IDENTIFICACION = "tipoIdentificacion";
    /**
     * Constante para el parametro de consula numeroIdentificacion
     */
    public static final String NRO_IDENTIFICACION = "numeroIdentificacion";
    /**
     * Constante para el parametro de consula primerNombre
     */
    public static final String PRIMER_NOMBRE = "primerNombre";
    /**
     * Constante para el parametro de consula segundoNombre
     */
    public static final String SEGUNDO_NOMBRE = "segundoNombre";
    /**
     * Constante para el parametro de consula primerApellido
     */
    public static final String PRIMER_APELLIDO = "primerApellido";
    /**
     * Constante para el parametro de consula segundoApellido
     */
    public static final String SEGUNDO_APELLIDO = "segundoApellido";
    /**
     * Constante para el parametro de consula estadoBeneficiario
     */
    public static final String ESTADO_BENEFICIARIO = "estadoBeneficiario";
    /**
     * Constante para el parametro de consulta textoFiltro
     */
    public static final String FILTRO_TEXTO = "textoFiltro";
    /**
     * Constante para el parametro de consula listClasificacion
     */
    public static final String LISTA_CLASIFICACION = "listClasificacion";
    /**
     * Constante para el parametro de consula fechaVencimientoCertEsc
     */
    public static final String FECHA_VENCIMIENTO_CERTIFICADO_ESCOLAR = "fechaVencimientoCertEsc";
    /**
     * Constante para el parametro de consula idPersona
     */
    public static final String ID_PERSONA = "idPersona";
    /**
     * Constante para el parametro de consula estadoCaja
     */
    public static final String ESTADO_CAJA = "estadoCaja";
    
    public static final String NUMERO_RADICADO ="numeroRadicado";
    
    public static final String ESTADO_SOLICITUD ="estadoSolicitud";
    
    public static final String TIPO_SOLICITUD ="tipoSolicitud";
    
    public static final String FECHA_EXACTA_RADICACION ="fechaExactaRadicacion";
    
    public static final String FECHA_INICIO ="fechaInicio";
    
    public static final String FECHA_FIN ="fechaFin";
    
    public static final String ID_POSTULACION="idPostulacion";
    
    /**
     * Cláusula WHERE
     */
    public static final String WHERE_CLAUSE = " WHERE ";
    /**
     * Cláusula AND
     */
    public static final String AND_CLAUSE = " AND ";

    /**
     * Cláusula INSERT INTO
     */
    public static final String INSERT_INTO_CLAUSE = " INSERT INTO ";

    /**
     * Cláusula VALUES
     */
    public static final String VALUES_CLAUSE = " VALUES ";

    /**
     * Cláusula NEXT VALUE FOR <br>
     * Usada para obtener el valor de secuencia
     */
    public static final String NEXT_VALUE_FOR_CLAUSE = " NEXT VALUE FOR ";

    /**
     * Símbolo (
     */
    public static final String LEFT_PARENTHESIS_SYMBOL = " ( ";

    /**
     * Símbolo )
     */
    public static final String RIGHT_PARENTHESIS_SYMBOL = " ) ";

    /**
     * Símbolo ?
     */
    public static final String INTERROGATION_SYMBOL = " ? ";

    /**
     * Símbolo ,
     */
    public static final String COMMA_SYMBOL = " , ";

    /**
     * Constructor de clase
     */
    private QueriesConstants() {

    }

}
