package com.asopagos.subsidiomonetario.pagos.composite.constants;

/**
 * <b>Descripción: Clase que contiene las constantes utilizadas en el servicio de composicion de pagos de subsidio monetario</b>
 * <b>Historia de Usuario: HU-31-227 </b>
 * 
 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
 */
public class PagosSubsidioMonetarioCompositeConstants {

    /** Variable que contiene el valor para indicar la el estado de una solcitud de anulacion de subsidio cobrado */
    public static final String ESTADO_SOLICITUD_ANULACION = "estadoSolicitudAnulacion";

    /** Variable que contiene el valor para la aprobación de una solicitud de anulacion de subsidio cobrado */
    public static final Boolean APROBACION = true;

    /** Variable que contiene el valor para el rechazo de una solicitud de anulacion de subsidio cobrado */
    public static final Boolean RECHAZO = false;

    /**
     * Mensajes de error para la gestion de una solicitud de anulación de subsidio monetario cobrado
     */
    public static final String ERROR_MESSAGE_ACTUALIZACION_SOLICITUD_ANULACION = "FALLO_ACTUALIZACION_SOLICITUD_ANULACION_SUBSIDIO_COBRADO";

    public static final String ERROR_MESSAGE_TRANSACCION_ANULACION_NO_REALIZADA = "TRANSACCION_ANULACION_CON_REEMPLAZO_NO_REALIZADA";

    public static final String ERROR_MESSAGE_FALTAN_PARAMETROS = "FALTAN_PARAMETROS_APROBACION_SOLICITUD_ANULACION_SUBSIDIO_COBRADO";

    public static final String ERROR_MESSAGE_ACTUALIZACION_SOLICTUD_NO_EXITOSO = "ACTUALIZACION_SOLICTUD_GLOBAL_NO_EXITOSO";

    public static final String ERROR_MESSAGE_INICIO_PROCESO_BPM_NO_EXITOSO = "INICIO_PROCESO_BPM_NO_EXITOSO";

    public static final String ERROR_MESSAGE_ERROR_GENERACION_NUM_RADICADO = "ERROR_GENERACION_NUMERO_RADICADO";

    public static final String ERROR_MESSAGE_GENERACION_TOKEN_ACCESO = "ERROR_GENERACION_TOKEN_ACCESO";

    public static final String ERROR_MESSAGE_REGISTRO_SOLICITUD_ANULACION_NO_EXITOSO = "PROCESO_REGISTRO_SOLICITUD_ANULACION_NO_EXITOSO";

    public static final String ERROR_MESSAGE_ANULACION_SIN_ABONOS_ASOCIADOS = "SOLICITUD_ANULACION_SIN_ABONOS_ASOCIADOS";

    public static final String PAGOS_VALOR_MINIMO_SOLICITADO = "PAGOS_VALOR_MINIMO_SOLICITADO";

    public static final String PAGOS_VALOR_MAXIMO_SOLICITADO = "PAGOS_VALOR_MAXIMO_SOLICITADO";

}
