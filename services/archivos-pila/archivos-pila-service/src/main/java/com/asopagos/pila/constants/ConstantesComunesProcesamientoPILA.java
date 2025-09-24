package com.asopagos.pila.constants;

/**
 * <b>Descripcion:</b> Clase que maneja constantes comunes al servicio <br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 391, 407 y 393<br/>
 *
 * @author <a href="mailto:jpiraban@heinsohn.com.co"> Jhon Angel Piraban Castellanos</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ConstantesComunesProcesamientoPILA {
    /**
     * Constructor privado para ocultar el constructor por defecto de clase
     */
    private ConstantesComunesProcesamientoPILA() {
    }

    /** Expresión regular para el formato de fecha sin guiones */
    public static final String PATRON_FORMATO_FECHA = "^[0-9]{4}[0-1][0-9][0-3][0-9]$";

    /** Expresión regular para el formato de fecha con guiones */
    public static final String PATRON_FORMATO_FECHA_GUION = "^[0-9]{4}-[0-1][0-9]-[0-3][0-9]$";

    /** Expresión regular para el formato de etiqueta de campo */
    public static final String PATRON_ETIQUETA_CAMPO = "((archivo)([IAF]|AP|IP)([R]?)(registro)([12345689])(-?)([1-3])?(campo)([1-9])([0-9]?))";

    /** Expresión regular para el valor de un campo en un mensaje de error de estructura por tipo de dato */
    public static final String PATRON_VALOR_CAMPO_ERROR_ESTRUCTURA_TIPO = "(.*)(El valor \\\")(.*)(\\\" del campo)(.*)";

    /** Expresión regular para el valor de un campo en un mensaje de error de estructura por longitud de la línea */
    public static final String PATRON_VALOR_CAMPO_ERROR_ESTRUCTURA_LONGITUD = "error al tratar de acceder a las posiciones";

    /** Expresión regular para el valor de un campo en un mensaje de error de estructura por longitud de la línea */
    public static final String PATRON_VALOR_CAMPO_ERROR_ESTRUCTURA_LINEA_FALTANTE = "(Error: La estructura de la línea con nombre)(\\s*)(%%)(.*)(%%)";
    
    /** Expresión regular para líneas vacías en un mensaje de error de estructura por línea vacía */
    public static final String PATRON_LINEA_VACIA = "(Error leyendo la línea)(.*)";

    /** Expresión regular para líneas vacías en un mensaje de error de estructura por línea vacía sin identificador */
    public static final String PATRON_LINEA_VACIA_SIN_IDENTIFICADOR = "(Identificador asociado a la línea)(.*)";

    /** Expresión regular para líneas vacías en un mensaje de error de estructura por línea vacía ya reportada */
    public static final String PATRON_LINEA_VACIA_YA_REPORTADA = "(No existe parametrizada)(.*)";

    /** Expresión regular para el valor de un campo en un mensaje de error de estructura por número de campos */
    public static final String PATRON_VALOR_CAMPO_ERROR_ESTRUCTURA_LINEA_CAMPOS_FALTANTE = "Error de estructura, el número de campos de la línea";

    /** Expresión regular para el formato del código de la CCF en mayúscula */
    public static final String PATRON_FORMATO_CODIGO_CCF_MAYUSCULA = "^[A-Z]*[0-9]*$";

    /** Esta constante contiene el valor de la entidad indicePlanilla */
    public static final String INDICE_PLANILLA = "indicePlanilla";

    /** Esta constante contiene el valor de la columna idPlanilla */
    public static final String ID_PLANILLA = "idPlanilla";
    /** Esta constante contiene el valor de la columna numeroPlanilla */
    public static final String NUMERO_PLANILLA = "numeroPlanilla";
    /** Esta constante contiene el valor de tipo archivo */
    public static final String TIPO_ARCHIVO = "tipoArchivo";

    /** Esta constante contiene el valor de la lista listaId */
    public static final String LISTA_ID = "listaId";

    /** Esta constante contiene el valor de la lista operadorInformacion */
    public static final String OPERADOR_INFORMACION = "operadorInformacion";
    /** Esta constante contiene el valor de id */
    public static final String ID = "id";

    /** Esta constante contiene el valor de estado */
    public static final String ESTADO = "estado";
    /** Esta constante contiene el valor de la columna periodo de pago */
    public static final String PERIODO_PAGO = "periodoPago";

    /** Esta constante contiene el valor de la columna pip id */
    public static final String PIP_ID = "pipId";

    /** Esta constante contiene el valor de la columna pip id */
    public static final String PIP_ESTADO_ARCHIVO = "pipEstadoArchivo";

    /** Esta constante contiene el valor de la columna fecha de pago */
    public static final String PIP_FECHA_PAGO = "pipofFechaPago";

    /** Esta constante contiene el valor de la columna codigo banco recaudador */
    public static final String PIP_COD_BANCO_RECA = "pipofCodigoBancoRecaudador";

    /** Esta constante contiene el valor de la columna tipo identificacion */
    public static final String TIPO_IDENTIFICACION = "tipoIdentificacion";

    /** Esta constante contiene el valor de la columna numero identificacion */
    public static final String NUMERO_IDENTIFICACION = "numeroIdentificacion";

    /** Esta constante contiene el valor de la columna numero identificacion */
    public static final String INDICE_PLANILLA_OF = "indicePlanillaOF";

    /** Esta constante contiene el valor de registro 6 */
    public static final String REGISTRO6F = "registro6F";

    /** Esta constante contiene el valor de registro */
    public static final String PIP_TIPO_ARCHIVO = "pipTipoArchivo";

    /** Esta constante contiene el valor de registro */
    public static final String PIP_ID_PLANILLA = "pipIdPlanilla";
    /** Esta constante contiene el valor de registro */
    public static final String ID_INDICE_PLANILLA = "idIndicePlanilla";

    /** Esta constante contiene el valor de registro */
    public static final String BLOQUE = "bloque";

    /** Esta constante contiene el valor tipo de secuencia */
    public static final Integer TIPO_SECUENCIA_1 = 1;
    /** Esta constante contiene el valor tipo de correciones */
    public static final String CORRECCIONES_A = "A";
    /** Esta constante contiene el valor tipo de correciones */
    public static final String CORRECCIONES_C = "C";

    /** Esta constante contiene el separador de las partes de un mensaje de error */
    public static final String SEPARADOR_MENSAJE_ERROR = "¬¬";
    /** Esta constante contiene el separador de las partes de un mensaje de error */
    public static final String SEPARADOR_CAMPOS_ERROR = "@@";

    /** Esta constante contiene el valor de extención válida para un archivo */
    public static final String EXTENSION_ARCHIVO_VALIDA = ".txt";

    /** Constantes con los nombres de los campos del nombre de archivo */

    public static final String NOMBRE_ARCHIVO = "Nombre del archivo";

    public static final String NOMBRE_ARCHIVO_FECHA_RECAUDO = "Fecha de recaudo en el nombre del archivo";

    public static final String NOMBRE_ARCHIVO_TIPO_ARCHIVO = "Tipo de archivo en el nombre del archivo";

    public static final String NOMBRE_ARCHIVO_MODALIDAD_PLANILLA = "Modalidad de la planilla en el nombre del archivo";

    public static final String NOMBRE_ARCHIVO_TIPO_DOCUMENTO_APORTANTE = "Tipo de documento del aportante en el nombre del archivo";

    public static final String NOMBRE_ARCHIVO_ENTIDAD_ADMINISTRADORA = "Código de la entidad administradora en el nombre del archivo";

    public static final String NOMBRE_ARCHIVO_CODIGO_OI = "Código del Operador de Información en el nombre del archivo";

    public static final String NOMBRE_ARCHIVO_PERIODO_APORTE = "Período del aporte en el nombre del archivo";

    public static final String NOMBRE_ARCHIVO_NUMERO_PLANILLA = "Número de la planilla en el nombre del archivo";

    public static final String NOMBRE_ARCHIVO_NUMERO_IDENTIFICACION_APORTANTE = "Número de identificación del aportante en el nombre del archivo";

    /** Constantes para los "nombre de campo" de validaciones de carga */
    public static final String TAMANO_ARCHIVO = "Tamaño del archivo";

    public static final String EXTENSION_ARCHIVO = "Extensión del archivo";

    public static final String DUPLICIDAD_ARCHIVOS = "Duplicidad de archivos";

    public static final String COMBINATORIA_ARCHIVOS = "Combinatoria de archivos";

    /** Constante con el nombre del campo para mensaje de error en validación de correcciones de novedades ING y RET */
    public static final String NOMBRE_CAMPO_MENSAJE_ERROR_CORRECCION_NOVEDAD = "Archivo Tipo I - Registro 2 - Campo 29: Correcciones";

    public static final Integer DIAS_HABILES_CASO2_CORRECCION_NOVEDADES = 5;

    /** Constantes para el manejo de valores en los mensajes de error por falta de parámetros */
    public static final String CODIGO_FALTA_PARAMETRO = "7";
    public static final String VALOR_FALTA_PARAMETRO = "FALLO LECTURA VALOR";
    public static final String VALOR_FALTA_PARAMETRO_MULTIPLE = VALOR_FALTA_PARAMETRO + SEPARADOR_CAMPOS_ERROR + VALOR_FALTA_PARAMETRO;

    /** Constante para identificar la primera línea del archivo */
    public static final Long PRIMERA_LINEA = 1L;

    /** Constantes para mensajes de error en cálculo de la fecha de vencimiento del aporte */
    public static final String RANGO_DIGITO_FINAL_ID = "Rango últimos dígitos ID";
    public static final String PERIODO_MAYOR = "comparación de período mayor";
    public static final String FECHA_VENCIMIENTO = "Fecha de Vencimiento";

    /** Constante para establecer la longitud máxima del mensaje de inconsistencia de validación */
    public static final Integer LONGITUD_MAX_MENSAJE_ERROR_INCONSISTENCIA = 4000;

    /** Constante que contiene el código del mensaje de error relacionado con una fila requerida faltante */
    public static final String CODIGO_ERROR_LINEA_FALTANTE = "197";

    /** COnstantes con los nombres de las secuencias de las tablas de persistencia de reg 2 para I-IP y 6 para F*/
    public static final String SEC_PILA_ARCHIVO_I_R2 = "dbo.Sec_PilaArchivoIRegistro2";
    public static final String SEC_PILA_ARCHIVO_IP_R2 = "dbo.Sec_PilaArchivoIPRegistro2";
    public static final String SEC_PILA_ARCHIVO_F_R6 = "dbo.Sec_PilaArchivoFRegistro6";
}