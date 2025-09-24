package com.asopagos.subsidiomonetario.pagos.constants;

/**
 * <b>Descripción: Clase que contiene las constantes utilizadas en el servicio de composicion de pagos de subsidio monetario</b>
 * <b>Historia de Usuario: HU-31-227 </b>
 * 
 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
 */
public class PagosSubsidioMonetarioConstants {

    /**
     * Mensajes de error para la creacion de una solicitud de subsidio de anulación de subsidio cobrado
     */
    public static final String ERROR_MESSAGE_CREACION_SOLICITUD_GLOBAL = "ERROR_CREACION_SOLICITUD_GLOBAL";

    public static final String ERROR_MESSAGE_CREACION_SOLICITUD_ANULACION = "ERROR_CREACION_SOLICITUD_ANULACION_SUBSIDIO_COBRADO";

    public static final String ERROR_MESSAGE_ERROR_INTERNO = "ERROR_INTERNO";

    public static final String ERROR_MESSAGE_SOLICITUD_ANULACION_SIN_ABONOS = "SOLICITUD_ANULACION_SIN_ABONOS_ASOCIADOS";

    public static final String PAGOS_VALOR_MINIMO_SOLICITADO = "PAGOS_VALOR_MINIMO_SOLICITADO";

    public static final String PAGOS_VALOR_MAXIMO_SOLICITADO = "PAGOS_VALOR_MAXIMO_SOLICITADO";
}
