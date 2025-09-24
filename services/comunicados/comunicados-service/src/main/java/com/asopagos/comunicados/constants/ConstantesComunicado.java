package com.asopagos.comunicados.constants;

/**
 * <b>Descripcion:</b> Clase que contiene las constantes utilizadas en los servicios<br/>
 * <b>Módulo:</b> Asopagos - HU Transversal<br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co"> jocampo</a>
 */
public class ConstantesComunicado {

    /** Constante que contiene el valor de una cadena vacía */
    public static final String VACIO = "";

    /** contiene el valor que representa un salto de línea html */
    public static final String SALTO_LINEA = "<br /> ";

    /** carácter que representa el elemento inicial de una variable de un comunicado */
    public static final String VARIABLE_ABRIR = "${";

    /** carácter que representa el elemento final de una variable de un comunicado */
    public static final String VARIABLE_CERRAR = "}";

    /** Constante que representa el valor del campo del encabezado */
    public static final String VALUE = "Value";

    /** Constante que representa el orden del campo en que se debe mostrar en el encabezado */
    public static final String ORDER = "Order";

    /** Constante que representa el nombre del campo del encabezado */
    public static final String NAME = "Name";

    /** Constante que representa el valor de una etiqueta */
    public static final String ETIQUETA = "etiqueta";

    /** Constante que representa el identificador de una plantilla de comunicado */
    public static final String ID_PLANTILLA_COMUNICADO = "idPlantillaComunicado";

    /** Constante que repesenta el identificador del proceso de un encabezado **/
    public static final String POSTULACION_FOVIS = "postulacionFovis";

    /** Constante que representa un espacio, utilizada para separar cadenas **/
    public static final String SPACE = " ";

    /**
     * Llave para el identificar el idSolicitud
     */
    public static final String KEY_MAP_ID_SOLICITUD = "idSolicitud";

    /**
     * Llave para el identificar el mapa con atributos adicionales para la generación del comunicado
     */
    public static final String KEY_MAP_ATRIBUTOS = "atributos";

    /**
     * Llave para el identificar el campo idesSolicitud
     */
    public static final String KEY_MAP_IDES_SOLICITUD = "idesSolicitud";

    /**
     * Llave para identificar el campo Número de Identificación
     */
    public static final String KEY_MAP_NUMERO_IDENTIFICACION = "numeroIdentificacion";

    /**
     * Llave para identificar el campo tipo de identificación
     */
    public static final String KEY_MAP_TIPO_IDENTIFICACION = "tipoIdentificacion";

    /**
     * Llave para identificar la entidad Cartera
     */
    public static final String KEY_MAP_ID_CARTERA = "idCartera";

    /**
     * HU-31-202: Llave para identificar el campo id tercero pagador relacionado al retiro efectuado
     * por ventanilla para el Proceso 3.1 de Pago de subsidio Monetario
     */
    public static final String KEY_MAP_ID_TRANSACCION_TERCERO_PAGADOR = "idTransaccionTerceroPagador";

    /**
     * HU-31-202: Llave para identificar el comprobante de la transacción generada en el retiro efectuado
     * por ventanilla para el Proceso 3.1 de Pago de subsidio Monetario.
     */
    public static final String KEY_MAP_ID_RESPUESTA = "identificadorRespuesta";

    /**
     * HU-31-202: Llave para identificar el usuario que registro el retiro efectuado
     * por ventanilla para el Proceso 3.1 de Pago de subsidio Monetario.
     */
    public static final String KEY_MAP_USUARIO_TRANSACCION_PAGOS = "${usuarioRegistroTran}";

    /**
     * ANEXO-Validacion y cargue archivo consumos_validado V.2: Llave para identificar el identificador del archivo
     * de consumos cargado para el proceso 3.1 de Pago de subsidio Monetario.
     */
    public static final String KEY_MAP_ID_ARCHIVO_CONSUMO_ANIBOL = "idArchivoConsumosAnibol";

    /** constante número de Radicación */
	public static final String KEY_MAP_NUMERO_RADICACION = "numeroRadicacion";
	
	/**
     * Llave para identificar la entidad Cartera
     */
    public static final String KEY_MAP_ID_PERSONA = "idPersona";
}
