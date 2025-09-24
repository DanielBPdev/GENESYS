package com.asopagos.pila.constants;

/**
 * Mensajes de validación del proceso de carga PILA
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 *
 */
public class MessagesConstants {
    private MessagesConstants (){}
	
	public static final String CARGA_EXITOSA = "El archivo NOMBRE_ARCHIVO ha sido cargado exitosamente.";
	public static final String CARGA_EXITOSA_REPROCESO = "El archivo de reproceso NOMBRE_ARCHIVO ha sido cargado exitosamente.";
	public static final String CARGA_EXITOSA_REPROCESO_ACTUAL = "El archivo de reproceso NOMBRE_ARCHIVO ha sido cargado exitosamente al ser el más reciente.";
	
	
	public static final String ERROR_LONGITUD_ARCHIVO = "Error en la longitud del archivo debe ser mayor de 0 Bytes";
	public static final String ERROR_EXTENSION_ARCHIVO = "El archivo NOMBRE_ARCHIVO no tiene una extensión válida";
	public static final String ERROR_NOMBRE_ARCHIVO = "El archivo NOMBRE_ARCHIVO no tiene un formato de nombre válido";
	
	public static final String ERROR_ARCHIVO_DESCARGADO_CON_INCONSISTENCIA = "El archivo NOMBRE_ARCHIVO, presentó errores durante el proceso de carga.";
	public static final String ERROR_ARCHIVO_DUPLICADO = "El archivo NOMBRE_ARCHIVO ya existe en el sistema, por lo tanto no se cargará el archivo recibido.";
	public static final String ERROR_ARCHIVO_DUPLICADO_ANTERIOR = "El archivo NOMBRE_ARCHIVO ha sido cargado previamente en el sistema. El archivo que intenta cargar es más antiguo.";
	public static final String ERROR_ARCHIVO_GRUPO_NO_VALIDO = "El archivo NOMBRE_ARCHIVO, no corresponde al mismo grupo de archivos cargados previamente con el mismo número de planilla.";
	public static final String ERROR_ARCHIVO_REPROCESO_PREVIO = "El archivo NOMBRE_ARCHIVO, fue anulado luego de su carga por presencia de un reproceso cargado previamente.";
	
	public static final String ERROR_VALIDACION_LECTURA = "Error durante la lectura del archivo: ";

	public static final String ELIMINACION_EXITOSA = "El archivo NOMBRE_ARCHIVO ha sido eliminado correctamente";
	
	public static final String PROCESO_NO_INICIADO = "No fue posible dar inicio al proceso";
	public static final String PROCESO_EN_CURSO = "No ha sido posible realizar este proceso de carga porque se está ejecutando en este momento otro proceso de este tipo";
	public static final String PROCESO_INICIADO = "Ha comenzado satisfactoriamente el proceso. Recuerde que para procesar los archivos que se carguen, debe ingresar por la opción “Procesar archivos OI descargados automáticamente";
	
	public static final String ERROR_TECNICO = "Se ha presentado una falla técnica: ";
	
	//-------------------------------------------------------------------------------------------------------------

    /** Constantes para mensajes de falta de parámetros */
	public static final String FECHA_VENCIMIENTO = "no se cuenta con la fecha de vencimiento.";
	public static final String CALCULO_DIAS_PERIODO = "días en el período de tasa de interés";
	public static final String PERIODOS_INTERES = "falta de primer y/o último período de tasa de interés";
	public static final String LISTADO_PERIODOS = "listado de períodos de tasa de interés y/o tipo de archivo";
	public static final String PERIODO_APORTE = "Período del aporte";
    public static final String TIPO_PLANILLA = "Tipo de planilla válido";
    public static final String TIPO_ARCHIVO = "Tipo de archivo";
    public static final String CANTIDAD_PERSONAS = "cantidad de personas";
    public static final String NORMATIVIDAD_OPORTUNIDAD = "caso de normatividad para el cálculo ó de oportunidad en el pago de la planilla";
    public static final String DIAS_MORA = "Días Morosidad";
    public static final String FECHA_APORTE = "fecha de pago de la planilla";
    public static final String NATURALEZA_JURIDICA = "naturaleza jurídica del aportante";
    public static final String FECHA_MATRICULA = "Fecha matrícula";
    public static final String TIPO_COTIZANTE = "Tipo Cotizante";
    public static final String CLASE_APORTANTE = "clase aportante";
    public static final String SALARIO_MINIMO = "valor del salario mínimo aplicable";
    public static final String CANTIDAD_REGISTROS_2 = "cantidad de registros tipo 2";
    public static final String CONTADOR_REGISTROS_2 = "contador de registros tipo 2";
    public static final String ULTIMA_SECUENCIA_2 = "valor de última secuencia de registro tipo 2";
    public static final String VALOR_MORA_PLANILLA = "valor de mora en planilla";
    public static final String VALOR_APORTE_PLANILLA = "valor de aporte en planilla";
    public static final String VALOR_MORA_CALCULADO = "valor de mora calculado para validación";
    public static final String VALOR_TOLERANCIA_VALOR_MORA = "rango de tolerancia en diferencia en el valor de mora";
    public static final String CASOS_DESCUENTO_MORA = "listado de casos para aplicación de descuento en mora";
    public static final String TIPOS_COTIZANTES_PLANILLA = "listado de los diferentes tipos de cotizante presentes en la planilla";
    public static final String ID = "número de identificación";
    public static final String TIPO_PERSONA = "tipo de persona";
    public static final String NUMERO_LOTE = "número de lote de registro tipo 5";
    public static final String TARIFA = "tarifa";
    public static final String MESADA_PENSIONAL = "valor de la mesada pensional";
	
	//-------------------------------------------------------------------------------------------------------------
	
	/**
	 * Función para obtener un mensaje de archivo duplicado específico
	 * @param nombreArchivo
	 * 		  Nombre del archivo a incluir en el mensaje
	 * @return String
	 *        Mensaje específico
	 * */
	public static String getMensajeCargaArchivo(String nombreArchivo, String mensajeBase){
		String mensaje = null;
		mensaje = mensajeBase.replace("NOMBRE_ARCHIVO", nombreArchivo);
		
		return mensaje;
	}
	
	/**
	 * Función para detallar una mensaje de error en lectura de archivos
	 * @param detalle 
	 * 		  Mensaje detallado
	 * */
	public static String getErrorLecturaArchivos(String detalle){
		return ERROR_VALIDACION_LECTURA + detalle;
	}
	
	/**
	 * Función para generar un mensaje de error técnico
	 * @param detalle 
	 * 		  Mensaje detallado
	 * */
	public static String getErrorTecnico(String detalle){
		return ERROR_TECNICO + detalle;
	}
}
