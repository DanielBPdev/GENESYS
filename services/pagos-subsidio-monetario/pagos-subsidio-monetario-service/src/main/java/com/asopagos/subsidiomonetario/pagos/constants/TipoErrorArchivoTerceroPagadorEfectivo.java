package com.asopagos.subsidiomonetario.pagos.constants;

/**
 * <b>Descripcion:</b> Clase que contiene las constantes para la validación del archivo de la solicitud del convenio del tercer pagador en el proceso de pagos<br/>
 * <b>Módulo:</b> Asopagos - HU -31 - 205 <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co">Miguel Angel Osorio</a>
 */
public class TipoErrorArchivoTerceroPagadorEfectivo {
	
	/**
	 * Mensajes de error archivo tercero pagador
	 */
	public static final String ERROR_ID_TRANSACCION_LONG_CARACTERES = "Identificador de transacción no tiene la longitud especificada";	
	public static final String ERROR_FORMATO_VALOR_TRANSACCION_NO_VALIDO = "Valor de la transacción no válido";
	public static final String ERROR_FORMATO_FECHA_HORA_TRANSACCION_NO_VALIDO = "Formato de fecha y hora de transacción no válido";
	public static final String ERROR_LEYENDO_DATOS_LINEA = "Error técnico leyendo linea del archivo";
}
