package com.asopagos.personas.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * Clase que contiene las constantes con los nombres de parametros y consultas del servicio <b>Módulo:</b> Asopagos - HU <br/>
 * Transversal
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class QueryConstants {

    /**
     * Consulta de personas beneficiarios
     */
    public static final String SQL_QUERY_PERSONA_ESTADO = "SELECT * "
            + "FROM ( "
            + "    SELECT *, CONCAT(dato.perTipoIdentificacion,'|',dato.perNumeroIdentificacion, '|', dato.perRazonSocial, '|', dato.estado) AS filtroTexto "
            + "    FROM ( "
            + "        SELECT per.perId, "
            + "            per.perDigitoVerificacion, "
            + "            per.perNumeroIdentificacion, "
            + "            CASE WHEN per.perPrimerNombre IS NOT NULL "
            + "                THEN RTrim(Coalesce(per.perPrimerNombre + ' ', '') "
            + "                    + Coalesce(per.perSegundoNombre + ' ', '') "
            + "                    + Coalesce(per.perPrimerApellido + ' ', '') "
            + "                    + Coalesce(per.perSegundoApellido, '')) "
            + "                ELSE per.perRazonSocial END perRazonSocial, "
            + "            per.perTipoIdentificacion, "
            + "            per.perPrimerNombre, "
            + "            per.perSegundoNombre, "
            + "            per.perPrimerApellido, "
            + "            per.perSegundoApellido, "
            + "            est.roaEstadoAfiliado AS estado "
            + "        FROM Persona per "
            + "        JOIN dbo.VW_EstadoAfiliacionPersonaCaja est ON (per.perId = est.perId) "
            + "    ) dato "
            + ") per ";
    
    /**
     * Condicion para el tipo de identificacion persona
     */
    public static final String SQL_PERSONA_ESTADO_CONDICION_TIPO_IDENTIFICACION = " per.perTipoIdentificacion = :tipoIdentificacion ";
    /**
     * Condicion para el nro de identificacion persona
     */
    public static final String SQL_PERSONA_ESTADO_CONDICION_NRO_IDENTIFICACION = " per.perNumeroIdentificacion = :numeroIdentificacion ";
    /**
     * Condicion para el primer nombre persona
     */
    public static final String SQL_PERSONA_ESTADO_CONDICION_PRIMER_NOMBRE = " per.perPrimerNombre = :primerNombre ";
    /**
     * Condicion para el segundo nombre persona
     */
    public static final String SQL_PERSONA_ESTADO_CONDICION_SEGUNDO_NOMBRE = " per.perSegundoNombre = :segundoNombre ";
    /**
     * Condicion para el primer apellido persona
     */
    public static final String SQL_PERSONA_ESTADO_CONDICION_PRIMER_APELLIDO = " per.perPrimerApellido = :primerApellido ";
    /**
     * Condicion para el segundo apellido persona
     */
    public static final String SQL_PERSONA_ESTADO_CONDICION_SEGUNDO_APELLIDO = " per.perSegundoApellido = :segundoApellido ";
    /**
     * Condicion para el estado dela persona ante la caja
     */
    public static final String SQL_PERSONA_ESTADO_CONDICION_ESTADO_CAJA = " per.estado = :estadoCaja ";
    /**
     * Condicion para el filtro texto
     */
    public static final String SQL_PERSONA_FILTRO_TEXTO = " per.filtroTexto LIKE :textoFiltro ";
    
    /**
     * Constructor de clase
     */
    private QueryConstants() {

    }

}
