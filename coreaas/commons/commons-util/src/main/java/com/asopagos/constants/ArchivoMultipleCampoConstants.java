package com.asopagos.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */
public class ArchivoMultipleCampoConstants {
    private ArchivoMultipleCampoConstants(){
        
    }

	// nombre de la variable que se espera tener en el contexto
	public static final String ID_EMPLEADOR = "idEmpleador";
	public static final String ID_CARGUE_MULTIPLE = "idCargueMultiple";
	public static final String LISTA_DEPARTAMENTO = "listaDepartamentos";
	public static final String LISTA_MUNICIPIO = "listaMunicipios";
	public static final String LISTA_CANDIDATOS = "listaCandidatos";
	public static final String LISTA_COTIZANTES = "listaCotizantes";
	public static final String LISTA_HALLAZGOS = "listaHallazgos";
	public static final String TOTAL_REGISTRO = "totalRegistro";
	public static final String TOTAL_REGISTRO_ERRORES = "totalRegistroErrores";
	public static final String TOTAL_REGISTRO_VALIDO="totalRegistroValido";

	public static final String LISTA_CODIGO_CIIU = "listaCodigoCIIU";
	public static final String LISTA_GRADOS_ACADEMICOS = "listaGradosAcademicos";
	public static final String LISTA_AFP = "listAFP";
	public static final String NOMBRE_HOJA = "nombreHoja";

	// Valores de control para determinar el valor de un booleano
	public static final String SI = "SI";
	public static final String NO = "NO";

	// Errores
	public static final String EXCEDE_LA_LONGITUD_MAXIMA_DE_CARACTERES_PERMITIDOS = "Excede la longitud máxima de caracteres permitidos ";
    public static final String REQUERIDO = " requerido. ";
	public static final String MENSAJE_ERROR_CAMPO = "El valor del campo {0} no es valido. {1}";
	public static final String MENSAJE_ERROR_ESTRUCTURA = "La línea especificada no cuenta con una estructura válida.";
	public static final String MENSAJE_ERROR_LINEA = "Error en la validacion de la línea {0}. [{1}]";
	public static final String MENSAJE_ERROR_CONTENIDO = "Debe de existir al menos un registro y no más de 50 registros";
	
	public static final String MENSAJE_ERROR_VALOR_PERMITIDO = "El valor no hace parte del dominio del campo";
	public static final String MENSAJE_ERROR_VALOR_EXCEDE_TAMANIO = "Excede la longitud máxima de caracteres permitidos  ";
	public static final String MENSAJE_ERROR_VALOR_REQUERIDO = "El campo es obligatorio";
	public static final String MENSAJE_ERROR_FORMATO_FECHA = "La fecha se encuentra en un formato no permitido. Formato valido: yyyy-MM-dd";
	public static final String MENSAJE_ERROR_DEPARTAMENTO_MUNICIPIO = "El municipio no hace parte del departamento";
	public static final String MENSAJE_ERROR_ENCABEZADO= "El nombre del encabezado no es valido";
	public static final String MENSAJE_ERROR_FILE_PROCESSING = "Se presento una excepción procesando la hoja del archivo";
	public static final String MENSAJE_DEL_AFILIADO= "No se encuentra como afiliado";
	/**
	 * Limite de registros permitidos
	 */
	public static final String LIMITE_DE_REGISTROS_PERMITIDOS_MSG = "El archivo supero el límite de registros permitidos. Máximo: ";
	
	/**
	 * Minimo de registros permitidos
	 */
	public static final String MINIMO_DE_REGISTROS_PERMITIDOS_MSG = "El archivo no cuenta con registros";
	
	/**
	 * Minimo de registros permitidos
	 */
	public static final String FORMATO_ARCHIVO_NO_VALIDO = "El formato del archivo no es valido";
	
	/**
	 * nombre archivo
	 */
	public static final String NOMBRE_ARCHIVO_NO_VALIDO = "El nombre del archivo no es valido";

	public static final int LONGITUD_REGISTROS = 500;

	// Valores de lineas numeros de lineas para lecturas
	/**
	 * indica la diferencia de edad que debe de existir - 15
	 */
	public static final int DIFERENCIA_EDAD_15 = 15;
	/**
	 * indica la diferencia de edad que debe de existir - 14
	 */
	public static final int DIFERENCIA_EDAD_14 = 14;
	/**
	 * Valor minimo del salario
	 */
	public static final Long VALOR_MINIMO_SALARIO = (long) 100000;
	/**
	 * Valor maximo del salario
	 */
	public static final Long VALOR_MAXIMO_SALARIO = (long) 500000000;
	/**
	 * Numero de linea de inicio encabezado
	 */
	public static final Long LINEA_ENCABEZADO = (long) 1;
	/**
	 * Numero de linea de inicio de registro
	 */
	public static final Long LINEA_INICIO_REGISTRO = (long) 2;
	/**
	 * Numero de linea fin de lectura de registros
	 */
	public static final Long LINEA_FIN_REGISTRO = (long) 51;

	/**
	 * indica la longitud requerida de 1 caracter
	 */
	public static final int LONGITUD_1_CARACTER = 1;
	/**
	 * indica la longitud requerida de 2 caracteres
	 */
	public static final int LONGITUD_2_CARACTERES = 2;
	/**
     * indica la longitud requerida de 4 caracteres
     */
    public static final int LONGITUD_4_CARACTERES = 4;
    /**
     * indica la longitud requerida de 5 caracteres
     */
    public static final int LONGITUD_5_CARACTERES = 5;
	/**
	 * indica la longitud requerida de 7 caracteres
	 */
	public static final int LONGITUD_7_CARACTERES = 7;

	/**
	 * indica la longitud requerida de 8 caracteres
	 */
	public static final int LONGITUD_8_CARACTERES = 8;

	/**
	 * indica la longitud requerida de 8 caracteres
	 */
	public static final int LONGITUD_9_CARACTERES = 9;

	/**
	 * indica la longitud requerida de 10 caracteres
	 */
	public static final int LONGITUD_10_CARACTERES = 10;
	
	/**
	 * indica la longitud requerida de 17 caracteres
	 */
	public static final int LONGITUD_17_CARACTERES = 17;

	/**
     * indica la longitud requerida de 17 caracteres
     */
    public static final int LONGITUD_20_CARACTERES = 20;

    /**
     * indica la longitud requerida de 30 caracteres
     */
    public static final int LONGITUD_30_CARACTERES = 30;

    /**
     * indica la longitud requerida de 40 caracteres
     */
    public static final int LONGITUD_40_CARACTERES = 40;

	/**
	 * indica la longitud requerida de 50 caracteres
	 */
	public static final int LONGITUD_50_CARACTERES = 50;
	
	/**
     * indica la longitud requerida de 60 caracteres
     */
    public static final int LONGITUD_60_CARACTERES = 60;

	/**
	 * indica la longitud requerida de 100 caracteres
	 */
	public static final int LONGITUD_100_CARACTERES = 100;
	
	/**
	 * indica la longitud requerida de 200 caracteres
	 */
	public static final int LONGITUD_200_CARACTERES = 200;

	/**
	 * Extension del archivo .XLS
	 */
	public static final String EXT_XLS = ".XLS";

	/**
	 * Extension del archivo .XLSX
	 */
	public static final String EXT_XLSX = ".XLSX";
	
	/**
	 * Extension del archivo txt/plain separado por comas 
	 */
	public static final String DELIMITED_TEXT_PLAIN = ".TXT";
	
	/**
     * Extension del archivo txt/plain separado por comas 
     */
    public static final String TEXT_PLAIN = "text/plain";
    
    /**
     * Numero de linea 1 del archivo de texto 
     */
	public static final Long NUMERO_1_LINEA_TXT=1L;
	/**
     * Numero de linea 2 del archivo de texto 
     */
	public static final Long NUMERO_2_LINEA_TXT=2L;
	/**
     * Numero de linea 2 del archivo de texto 
     */
	public static final Long NUMERO_3_LINEA_TXT=3L;
	/**
	 * Cantidad de registros validos
	 */
	public static final Long CANTIDAD_REGISTROS_VALIDADOS=50L;

	// =============== MASIVO TRANSFERENCIA

	public static final String TIPO_IDENTIFICACION_AFILIADO_PRINCIPAL = "tipoIdentificacionAfiliadoPrincipal";

	public static final String 	NUMERO_IDENTIFICACION_AFILIADO_PRINCIPAL = "numeroIdentificacionAfiliadoPrincipal";

	public static final String BANCO = "codigoBanco";

	public static final String TIPO_CUENTA = "tipoCuenta";

	public static final String NUMERO_CUENTA = "numeroCuenta";

	public static final String LISTA_CARGUE_NOVEDAD_MASIVA_TRANSFERENCIA = "listaCargueNovedadMasivaTransferencia";

	public static final String CODIGO_IECM_ARCHIVO = "codigoIecmArchivo";

	public static final String LISTA_CARGUE_CERTIFICADOS_MASIVOS = "listaCargueCertificadosMasivos";

	public static final String TIPO_IDENTIFICACION_AFILIADO = "tipoIdentificacionAfiliado";

	public static final String 	NUMERO_IDENTIFICACION_AFILIADO = "numeroIdentificacionAfiliado";

	public static final String ID_EMPLEADOR_CERTIFICADO_MASIVO = "idEmpleador";
	
}
