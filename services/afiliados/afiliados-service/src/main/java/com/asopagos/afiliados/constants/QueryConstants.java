package com.asopagos.afiliados.constants;

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
    public static final String SQL_QUERY_PERSONA_BENEFICIARIO = "SELECT * FROM (SELECT *, CONCAT(per.perTipoIdentificacion,'|', per.perNumeroIdentificacion, '|', CONCAT (per.perPrimerNombre, CASE WHEN per.perSegundoNombre IS NOT NULL THEN CONCAT(' ', per.perSegundoNombre) ELSE '' END, ' ', per.perPrimerApellido, CASE WHEN per.perSegundoApellido IS NOT NULL THEN CONCAT(' ', per.perSegundoApellido) ELSE '' END), '|', ben.benEstadoBeneficiarioAfiliado, '|', ceb.cebFechaVencimiento) AS texto FROM Beneficiario ben JOIN Persona per ON (ben.benPersona = per.perId) LEFT JOIN CondicionInvalidez coi ON (per.perId = coi.coiPersona) LEFT JOIN PersonaDetalle ped ON (per.perId = ped.pedPersona) LEFT JOIN BeneficiarioDetalle bed ON (ben.benBeneficiarioDetalle = bed.bedId) LEFT JOIN (SELECT cebBeneficiarioDetalle AS cebmBeneficiarioDetalle, MAX(cebId) AS cebmId FROM CertificadoEscolarBeneficiario GROUP BY cebBeneficiarioDetalle) cebm ON (cebm.cebmBeneficiarioDetalle = bed.bedId) LEFT JOIN CertificadoEscolarBeneficiario ceb ON (ceb.cebId = cebm.cebmId)) AS info ";
    /**
     * Condicion para el tipo de identificacion persona
     */
    public static final String SQL_CONDICION_TIPO_IDENTIFICACION = " info.pertipoIdentificacion = :tipoIdentificacion ";
    /**
     * Condicion para el nro de identificacion persona
     */
    public static final String SQL_CONDICION_NRO_IDENTIFICACION = " info.pernumeroIdentificacion = :numeroIdentificacion ";
    /**
     * Condicion para el primer nombre persona
     */
    public static final String SQL_CONDICION_PRIMER_NOMBRE = " info.perprimerNombre = :primerNombre ";
    /**
     * Condicion para el segundo nombre persona
     */
    public static final String SQL_CONDICION_SEGUNDO_NOMBRE = " info.persegundoNombre = :segundoNombre ";
    /**
     * Condicion para el primer apellido persona
     */
    public static final String SQL_CONDICION_PRIMER_APELLIDO = " info.perprimerApellido = :primerApellido ";
    /**
     * Condicion para el segundo apellido persona
     */
    public static final String SQL_CONDICION_SEGUNDO_APELLIDO = " info.persegundoApellido = :segundoApellido ";
    /**
     * Condicion para el estado del beneficiario con respecto al afiliado principal
     */
    public static final String SQL_CONDICION_ESTADO_RESP_AFILIADO = " info.benestadoBeneficiarioAfiliado = :estadoBeneficiario ";
    /**
     * Condicion para el tipo de beneficiario (Clasificacion)
     */
    public static final String SQL_CONDICION_TIPO_BENEFICIARIO = " info.bentipoBeneficiario IN (:listClasificacion) ";
    /**
     * Condicion para los campos de la tabla
     */
    public static final String SQL_CONDICION_TEXTO_TABLA = " info.texto LIKE :textoFiltro ";

    /**
     * Constructor de clase
     */
    private QueryConstants() {

    }

}
