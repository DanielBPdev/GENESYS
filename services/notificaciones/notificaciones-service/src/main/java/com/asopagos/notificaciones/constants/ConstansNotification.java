package com.asopagos.notificaciones.constants;

/**
 * Constantes usadas por el servicio de notificaciones
 * 
 * @author Juan Diego Ocampo Q. <jocampo@heinsohn.com.co>
 */
public class ConstansNotification {

	/**
	 * Constante que contiene la etiqueta html para establecer una imagen como
	 * contenido de una Notificación.
	 */
	public static final String ETIQUETA_IMG = "<img src=\"cid:Content";

	/**
	 * Constante que contiene la etiqueta html para establecer el titulo de una
	 * imagen que se encuentra embebida en el cuerpo de una notitificación.
	 */
	public static final String ETIQUETA_TITLE = "\" title=\"";

	/**
	 * Constante que indica el caracter punto.
	 */
	public static final String POINT = ".";

	/**
	 * Constante que contiene los caracteres para cerrar una etiqueta de tipo
	 * html.
	 */
	public static final String ETIQUETA_CLOSE = "\">";

	/**
	 * Constante que indica la cadena Content-ID.
	 */
	public static final String CONTENT_ID = "Content-ID";

	/**
	 * Constantes que indica el identificador CID de una etiqueta de tipo
	 * Imagen.
	 */
	public static final String CONTENT_OPEN = "<Content";

	/**
	 * Constante que indica el caracter mayor que.
	 */
	public static final String MAYOR_QUE = ">";

	public static final String MESSAGE_ADITIONAL = "No se pudo adjuntar el(los) archivo(s) porque tienen un tama\u00F1o mayor a {0} MB y su tama\u00F1o actual es {1} MB. Los archivos est\u00E1n disponibles en la ruta {2} y se llaman {3}";
	
	/**
	 * Constante que indica el caracter mayor que.
	 */
	public static final String JAVAX_JSM_QUEUE = "javax.jms.Queue";
	
	/**
	 * Constante que indica el caracter mayor que.
	 */
	public static final String QUEUE_MAILS = "java:/jms/queue/MailsQueue";
	
	/**
     * Constante que indica el caracter mayor que.
     */
	public static final String QUEUE_MAILS_NAME = "${property.mdb.mails.queue.name:MailsQueue}";
	
	/**
	 * nombre parametro de notificación enviado a la cola dentro del mapa
	 */
	public static final String NOTIFICACION_PARAM = "notificacion";
	
	/**
	 * nombre parametro de usuario enviado a la cola dentro del mapa
	 */
	public static final String USER_PARAM = "userDTO";
	
	
	/**
     * Tipo de notificación si es tru es parametrizada de lo contrario simple
     */
    public static final String TIPO_NOTIFICACION_PARAMETRIZADA = "tipoNotificacionParametrizada";
}
