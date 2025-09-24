package com.asopagos.constants;

/**
 *
 * @author sbrinez
 */
public class ExpresionesRegularesConstants {

	/**
	 * Expresion regular para validación de números
	 */
	public static final String UNO_O_MAS_DIGITOS = "^\\d+$";

	/** 
	 * Expresión regular para validación de correos electrónicos
	 */
	public static final String EXPRESION_REGULAR_VALIDA_EMAIL = "(^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_\\-][a-z0-9_\\-]*(\\.[a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$)|^$";
	public static final String ALFABETICO_SIN_ESPACIOS = "^([a-z\\u00E0-\\u00FC]{0,20})$";
	public static final String ALFABETICO_CON_ESPACIOS = "^([a-z\\u00E0-\\u00FC\\s]{0,20})$";
	public static final String PRIMER_NOMBRE =    "^([a-z\\u00E0-\\u00FC]{0,30})$";
	public static final String SEGUNDO_NOMBRE =   "^([a-z\\u00E0-\\u00FC ]{0,30})$";
	public static final String PRIMER_APELLIDO =  "^([a-z\\u00E0-\\u00FC ]{0,30})$";
	public static final String SEGUNDO_APELLIDO = "^([a-z\\u00E0-\\u00FC ]{0,30})$";
	public static final String ALFANUMERICO = "^[a-zA-Z0-9_]*$";
	public static final String ALFANUMERICO2 = "^[a-zA-Z0-9]*$";
	/**
	 * Expresiones regulares para verificar si es NIT
	 */
	public static final String NIT = "^\\d{1,10}$"; 
	/**
	 * Expresiones regulares para verificar si es cedula de ciudadania
	 */
	public static final String CEDULA_CIUDADANIA = "^(\\d{1,10})$";
	/**
	 * ^(\\d{3,5}|\\d{8}|\\d{10})$ Expresiones regulares para verificar si es
	 * pasaporte
	 */
	public static final String PASAPORTE = "^[\\da-zA-Z]{1,16}$";
	/**
	 * Expresiones regulares para verificar si es registro civil
	 */
	public static final String REGISTRO_CIVIL = "^(.{8}|.{10}|.{11})$";
	/**
	 * Expresiones regulares para verificar si es tarjeta de identidad
	 */
	public static final String TARJETA_IDENTIDAD = "^(\\d{10,11})$";
	/**
	 * ^(\\d{10,11})$ Expresiones regulares para verificar si es cedula
	 * extranjeria
	 */
	public static final String CEDULA_EXTRANJERIA = "^([\\da-zA-Z]{1,16})$";

	/**
	 * ^(\\d{1,7})$ Expresiones regulares para verificar si es Permiso por protección
	 * temporal
	 */
	public static final String PERM_PROT_TEMPORAL = "^[\\da-zA-Z]{1,8}$";

	public static final String PERM_ESP_PERMANENCIA = "^[\\da-zA-Z]{15}$";

	/**
	 * Expresiones regulares para verificar si es NIT
	 */
	public static final String CARNE_DIPLOMATICO = "^[\\da-zA-Z]{1,15}$";
	public static final String CARNE_DIPLOMATICO_PAGOS = "^\\d{1,15}$";
	public static final String PERM_PROT_TEMPORAL_PAGOS = "^\\d{1,8}$";
	public static final String ALFANUMERICO_DE_16_CARACTERES_PAGOS = "^[\\da-zA-Z]{1,16}$";
	public static final String ALFANUMERICO_DE_15_CARACTERES_PAGOS = "^[\\da-zA-Z]{1,15}$";
	public static final String ALFANUMERICO_DE_20_CARACTERES_PAGOS = "^[\\da-zA-Z]{1,20}$";
	public static final String SI_NO = "SI|NO";
	public static final String REGEX_UNO_CERO = "[0-1]";
	public static final String SI_NO_IGNORE_CASE = "(?i)SI|NO";
	public static final String TELEFONO_CELULAR = "(^(?!(1{10}|2{10}|3{10}|4{10}|5{10}|6{10}|7{10}|8{10}|9{10}|10{10}|1234567890|1{7}|2{7}|3{7}|4{7}|5{7}|6{7}|7{7}|8{7}|9{7}|10{7}|1234567|2345678|3456789))(((310|315|314|311|300|301|350|313|319|320|321|311|312|318|316|317|322|304|302|324|323|305)\\d{7})|\\d{7})$)|^$";
	public static final String TELEFONO_FIJO = "(^(?!(1{7}|2{7}|3{7}|4{7}|5{7}|6{7}|7{7}|8{7}|9{7}|1234567|2345678|3456789|0000000))\\d{7}$)|^$";
	/**
	 * Expresion regular perteneciente a codigo postal
	 */
	public static final String CODIGO_POSTAL = "(^([1-9]{2}|[0-9][1-9]|[1-9][0-9])([1-9]{2}|[0-9][1-9]|[1-9][0-9])([1-9]{2}|[0-9][1-9]|[1-9][0-9])$)|^$";
	/**
	 * Expresion que se encarga de verificar si es X o vacio
	 */
	public static final String X_O_VACIO = "X|";
	
	/**
	 * Expresion que se encarga de validar si es una cantidad o no
	 */
	public static final String VALOR_NUMERICO = "[0-9]*\\.?[0-9]*";
	
	/**
	 * Constante para la expresión regular de validación del nombre del archivo de descuentos
	 */
	public static final String REGEX_NOMBRE_ARCHIVO_DESCUENTOS = "IN_[0-9]{4}_[0-9]{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2][0-9]|3[0-1])_[0-9]{2}.(txt|TXT)";


/**
	 * Constante para la expresión regular de validación del nombre del archivo de cruces Aportes
	 */
	public static final String REGEX_NOMBRE_ARCHIVO_CRUCES_APORTES = "CRUCE_[0-9]{2}_CCF[0-9]{2}_[0-9]{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2][0-9]|3[0-1]).(txt|TXT)";

	/**
	 * Expresión que se encarga de validar la hora en formato 24:00
	 */
	public static final String HORA =  "([0-2][0-4]|[0-1][0-9]):([0-5][0-9])$";
	
	/**
	 * Expresiones regulares para verificar si es salvoconducto
	 */
	public static final String SALVO_CONDUCTO = "^(\\d{1,16})$";
	
	/**
	 * Expresión regular que se encarga de validar el formato alfanumérico de 20 caracteres de la identificación
	 * del tercero 
	 */
	public static final String IDENTIFICACION_TERCERO_PAGADOR_SUBSIDIO_MONETARIO ="^([A-Za-z0-9]{1,20})$";
	
	/**
	 * Expresión regular que se encarga de validar el formato del nombre del archivo de consumo de retiros de Tarjetas
	 *  en el FTP donde lo deja ANIBOl en el proceso de Pagos.
	 */
	public static final String REGEX_NOMBRE_ARCHIVO_CONSUMO_TARJETA_ANIBOL = "T[0-9]{6}([1-9]|[A|B|C]{1})([0-2][1-9]|[1-3][0-1]).(txt|TXT)";
	
	/**
	 * Expresión regular que se encarga de validar el formato del nombre del archivo de consumo de retiros de Tarjetas
	 *  en el FTP donde lo deja ANIBOl en el proceso de Pagos.
	 */
	public static final String REGEX_NOMBRE_ARCHIVO_CONSUMO_TERCERO_PAGADOR_EFECTIVO = "AC_[0-9]{3}_[0-9]{8}_[0-9]{2}.(txt|TXT).(txt|TXT)";
	
	// ARCHIVO BLOQUEO
	/**
	 * Expresión regular que se encarga de validar el formato del nombre del archivo de bloqueo de subsidio monetario
	 *  en el FTP donde lo deja ANIBOl en el proceso de Pagos.
	 */
	public static final String REGEX_NOMBRE_ARCHIVO_BLOQUEO_CM = "([A-Z0-9]){6}_BLOQUEO_CUOTA_MONETARIA_[0-9]{8}_A[0-9]{3}.(txt|TXT)";

	// ARCHIVO BLOQUEO TRAJABADOR BENEFICIARIO
	/**
	 * Expresión regular que se encarga de validar el formato del nombre del archivo de bloqueo de subsidio monetario
	 * para un trabajador beneficiario en el FTP donde lo deja ANIBOl en el proceso de Pagos.
	 */
	public static final String REGEX_NOMBRE_ARCHIVO_BLOQUEO_TRABAJADOR_CM = "([A-Z0-9]){6}_BLOQUEO_TRABAJADOR_BENEFICIARIO_[0-9]{8}_A[0-9]{3}.(txt|TXT)";
	
	/**
	 * Expresión regular que se encarga de validar el tipo de documento de un beneficiario para bloqueo de subsidios.
	 */
	//agregado PE 28/09/2021 G46292
	public static final String REGEX_TIPO_DOC_BENEFICIARIO = "(CC|CE|TI|RC|PA|CD|PE|PT|SC)";
	
	/**
	 * Expresión regular que se encarga de validar el numero de documento de un beneficiario
	 */
	public static final String REGEX_NUMERO_DOC_BENEFICIARIO = "[0-9]+";

	/**
	 * Expresión regular que se encarga de validar las causales de un bloqueo de un beneficiario
	 * para un subsidio monetario
	 */
	public static final String REGEX_CAUSAL_BLOQUEO = "^(BENEFICIARIO_AFILIADO_OTRA_CCF|BENEFICIARIO_DUPLICIDAD_TIPO_IDENTIFICACION|BLOQUEO_FISCALIZACION|INVESTIGACION_TRABAJO_SOCIAL|CRUCE_GIASS|FALLECIDO_REPORTADO_ADRES|SUBSIDIO_ASIGNADO_OTRA_CCF|TRABAJADOR_LABORA_MENOS_96_HORAS)$";
	
}
