package com.asopagos.pila.constants;

/**
 * <b>Descripcion:</b> Clase que almacena la infomacion de constantes del contexto <br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 391, 407, 393<br/>
 *
 * @author <a href="mailto:jpiraban@heinsohn.com.co"> jpiraban</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */

public class ConstantesContexto {
    /** constructor privado para ocultar el constructor por defecto*/
    private ConstantesContexto(){}
    
    /** Constante que almacena la llave para el índice de planilla */
    public static final String INDICE_PLANILLA = "indicePlanilla";

    /** Constante que almacena el valor de la variable */
    public static final String BLOQUE = "bloque";

    /** Constante que almacena el valor de la variable */
    public static final String CODIGO_CCF = "codigoCCF";

    /** Constante que almacena el valor de la variable */
    public static final String SALARIO_MINIMO = "salarioMinimo";

    /** Constante que almacena el valor de la variable */
    public static final String DEPARTAMENTOS = "departamentos";

    /** Constante que almacena el valor de la variable */
    public static final String MUNICIPIOS = "municipios";

    /** Constante que almacena el valor de la variable */
    public static final String DEPARTAMENTOS_MUNICIPIOS = "departamentosMunicipios";

    /** Constante que almacena el valor de la variable */
    public static final String OPERADORES_INFORMACION = "operadoresInformacion";
    /** Constante que almacena el valor de la variable */
    public static final String CODIGOS_CIIU = "codigosCIIU";

    /** Constante que almacena el valor de la variable */
    public static final String FESTIVOS = "festivos";

    /** Constante que almacena el valor de la variable */
    public static final String TOLERANCIA_VALOR_MORA = "toleranciaValorMora";

    /** Constante que almacena el valor de la variable */
    public static final String PASO_VARIABLES = "pasoVariables";

    /** Constante que almacena el valor de la variable */
    public static final String NORMATIVIDAD = "normatividad";

    /** Constante que almacena el valor de la variable */
    public static final String OPORTUNIDAD_VENCIMIENTO = "oportunidadVencimiento";

    /** Constante que almacena el valor de la variable */
    public static final String TASAS_INTERES = "tasasInteres";

    /** Constante que almacena el valor de la variable */
    public static final String VARIABLE_BLOQUE2 = "variablesBloque2";

    /** Constante que almacena el valor de la variable */
    public static final String MINIMO_DIAS_PAGO = "minimoDiasPago";

    /** Constante que almacena el valor de la variable */
    public static final String REINTENTOS = "reintentos";

    /** Constante que almacena el valor de la variable */
    public static final String SUMATORIA_NUMERO_REGISTROS = "sumatoriaNumeroRegistros";

    /** Constante que almacena el valor de la variable */
    public static final String SUMATORIA_VALOR_PLANILLA = "sumatoriaValorPlanilla";

    /** Constante que almacena el valor de la variable */
    public static final String SUMATORIA_CANTIDAD_PLANILLAS = "sumatoriaCantidadPlanillas";

    /** Constante que almacena el valor de la variable */
    public static final String CONTADOR_REGISTROS_6 = "contadorRegistrosTipo6";

    /** Constante que almacena el valor de la variable */
    public static final String SUMATORIA_TOTAL_NUMERO_REGISTROS = "sumatoriaTotalNumeroRegistros";

    /** Constante que almacena el valor de la variable */
    public static final String SUMATORIA_TOTAL_VALOR_PLANILLA = "sumatoriaTotalValorPlanilla";

    /** Constante que almacena el campo Fecha Pago en nombre de archivo */
    public static final String NOMBRE_FECHA_PAGO = "fechaPago";

    /** Constante que almacena el campo Modalidad en nombre de archivo */
    public static final String NOMBRE_MODALIDAD = "modalidad";

    /** Constante que almacena el campo Número Planilla en nombre de archivo */
    public static final String NOMBRE_NUMERO_PLANILLA = "numeroPlanilla";

    /** Constante que almacena el campo Tipo Documento en nombre de archivo */
    public static final String NOMBRE_TIPO_DOCUMENTO = "tipoDocumento";

    /** Constante que almacena el campo Id Aportante en nombre de archivo */
    public static final String NOMBRE_ID_APORTANTE = "idAportante";

    /** Constante que almacena el campo Código Entidad en nombre de archivo */
    public static final String NOMBRE_CODIGO_ENTIDAD = "codigoEntidad";

    /** Constante que almacena el campo Código Operador Información en nombre de archivo */
    public static final String NOMBRE_CODIGO_OPERADOR = "codigoOperador";

    /** Constante que almacena el campo Código del banco en nombre de archivo OF */
    public static final String NOMBRE_CODIGO_BANCO = "codigoBanco";

    /** Constante que almacena el campo Tipo de Archivo en nombre de archivo */
    public static final String NOMBRE_TIPO_ARCHIVO = "tipoArchivo";

    /** Constante que almacena el campo Período de Pago en nombre de archivo */
    public static final String NOMBRE_PERIODO_PAGO = "periodoPago";

    /** Constante que almacena el contador de registros tipo 2 */
    public static final String CONTADOR_REGISTROS_2 = "contadorRegistrosTipo2";

    /** Constante que almacena todas las tarifas en cero presentan una novedad SNL, IGE, LMA o IRL */
    public static final String TARIFA_CERO_NOVEDAD = "tarifaCeroConNovedad";

    /** Constante que almacena la sumatoria de IBC general */
    public static final String SUMATORIA_IBC_GENERAL = "sumatoriaIBCGeneral";

    /** Constante que almacena la sumatoria de IBC para registros A */
    public static final String SUMATORIA_IBC_A = "sumatoriaIBCplanillaNcorrecionA";

    /** Constante que almacena la sumatoria de IBC para registros C */
    public static final String SUMATORIA_IBC_C = "sumatoriaIBCplanillaNcorrecionC";

    /** Constante que almacena la sumatoria del Aporte Obligatorio general */
    public static final String SUMATORIA_AO_GENERAL = "sumatoriaAOGeneral";

    /** Constante que almacena la sumatoria del Aporte Obligatorio para registros A */
    public static final String SUMATORIA_AO_A = "sumatoriaAOplanillaNcorrecionA";

    /** Constante que almacena la sumatoria del Aporte Obligatorio para registros C */
    public static final String SUMATORIA_AO_C = "sumatoriaAOplanillaNcorrecionC";

    /** Constante que almacena el último número de secuencia registro tipo 2 */
    public static final String ULTIMA_SECUENCIA_REGISTRO_2 = "ultimaSecuenciaTipo2";

    /** Constante que almacena el control de secuencia de registro tipo 2 */
    public static final String CONTROL_SECUENCIA_REGISTRO_2 = "valorSecuenciaEsperado";

    /** Constante que almacena la sumatoria de las mesadas pensionales */
    public static final String SUMATORIA_MESADAS = "sumatoriaMesadasPensionales";

    /** Constante que almacena el listado de pensionados leídos para control de cantidad de pensionados */
    public static final String LISTA_PENSIONADOS = "pensionados";

    /** Patrón de expresión regular para la validación del período en el nombre de archivo de OI */
    public static final String PATRON_PERIODO_NOMBRE = "^[0-9]{4}-[0-1][0-9]\\.txt$";

    /** Constante que almacena la llave del campo que indica la presencia de independiente que obliguen evaluación mes actual */
    public static final String TIENE_INDEPENDIENTE_MES_ACTUAL = "independienteMesActual";

    /** Constante que almacena la llave del campo que indica la presencia de independiente que obliguen evaluación mes vencido */
    public static final String TIENE_INDEPENDIENTE_MES_VENCIDO = "independienteMesVencido";

    /** Constante que almacena la llave del tipo de planilla */
    public static final String TIPO_PLANILLA = "tipoPlanilla";

    /** Constante que almacena la llave del registro 1 */
    public static final String REGISTRO_1 = "registro1";

    /** Constante que almacena la llave del indicador de un aporte propio */
    public static final String ES_APORTE_PROPIO = "esAportePropio";

    /** Constante que almacena la llave para el estado de validación de nombre */
    public static final String ESTADO_NOMBRE = "estadoNombre";

    /** Constante que almacena la llave para la clase de aportante */
    public static final String CLASE_APORTANTE = "claseAportante";

    /** Constante que contiene la llave para el arreglo con la información para el Bloque 3 */
    public static final String INFORMACION_BLOQUE_3 = "informacionBloque3";

    /** Constante que contiene la llave para el indicador de ejecución de bloque 6 para OI */
    public static final String EJECUTAR_BLOQUE_6 = "ejecutarB6";

    /** Constante que contiene la llave del registro tipo 6 de OF */
    public static final String REGISTRO_6 = "registro6F";

    /** Constante que contiene la llave para el listado de errores detallados de componente */
    public static final String ERRORES_DETALLADOS = "erroresDetallados";

    /**
     * Constante que contiene la llave para el listado de errores, requerido en la validación
     * de correcciones sobre novedades de ingreso y/o retiro
     */
    public static final String RESULTADO_BLOQUE_VALIDACION_DTO = "resultadoBloqueValidacionDTO";

    /** Constante que contiene la llave para la fecha de vencimiento */
    public static final String FECHA_VENCIMIENTO = "fechaVencimiento";

    /** Constante que contiene la llave del valor de mora */
    public static final String VALOR_MORA = "valorMora";

    /** Constante que contiene la llave del valor de mora calculado */
    public static final String VALOR_MORA_CALCULADO = "valorMoraCalculado";

    /** Constante que almacena la llave del valor de aportes */
    public static final String VALOR_APORTES = "valorAporte";

    /** Constante que contiene la llave del listado de cotizantes no válidos para IBC = 0 */
    public static final String COTIZANTES_IBC_NO_CERO = "cotizantesIBCNoCero";

    /** Constante que contiene la llave del listado de cotizantes que admiten un valor de 0 días cotizados */
    public static final String COTIZANTES_DIAS_CERO = "cotizantesCeroDias";

    /** Constante que contiene la llave del modificador del salario integral mínimo */
    public static final String MODIFICADOR_SALARIO_INTEGRAL = "modificadorSalarioIntegral";

    /** Constante que contiene la llave del estado de bloque 6 consultado */
    public static final String ESTADO_BLOQUE_6 = "estadoB6";

    /** Constante que contiene la llave del listado de tipos de documento válidos para el nombre de archivo OI */
    public static final String TIPOS_ID_VALIDOS = "listaTiposIdNombre";

    /** Constante que contiene la llave del listado de códigos de Operador Financiero para validación */
    public static final String OPERADORES_FINANCIEROS = "operadoresFinancieros";

    /** Constante que contiene la llave del listado de los casos de correciones en novedades */
    public static final String LISTA_CASOS_CORRECCION = "listaCasosCorreccionNovedad";

    /** Constante que contiene la llave del código del departamento de la CCF */
    public static final String CODIGO_DPTO_CCF = "codigoDepCCF";

    /** Constante que contiene la llave del código del municipio de la CCF */
    public static final String CODIGO_MUNI_CCF = "codigoMunCCF";

    /** Constante que contiene la llave del listado de los casos de novedades en múltiples líneas */
    public static final String LISTA_CASOS_NOVEDAD_MULTIPLE = "listaCasosNovedadMultiple";

    /** Constante que contiene la llave del número de lote de registro tipo 5 que se está leyento */
    public static final String NUMERO_LOTE_REGISTRO_5_OF = "numeroLote";

    /** Constante que contiene la llave de la cantidad de cotizantes de una planilla */
    public static final String CANTIDAD_COTIZANTES = "cantidadCotizantes";

    /** Constante que contiene la llave de la fecha de inicio de la Ley 1429 */
    public static final String INICIO_1429 = "inicio1429";

    /** Constante que contiene la llave de la fecha de finalización de la Ley 1429 */
    public static final String FIN_1429 = "fin1429";

    /** Constante que contiene la llave del valor del campo de cantidad de pensionados reportados */
    public static final String CANTIDAD_PENSIONADOS = "cantidadPensionados";

    /** Constante que contiene la llave del listado de los números de planilla leídos en un archivo OF*/
    public static final String MAPA_NUMEROS_PLANILLA_EN_OF = "listadoPlanillas";

    /** Constante que contiene la llave del último número de línea que presentó error de estructura */
    public static final String LINEA_ESTRUCTURA_ANTERIOR = "lineaEstructura";
    
    /** Constante que contiene la llave del campo Naturaleza Jurídica */
    public static final String NATURALEZA_JURIDICA = "naturalezaJuridica";
    
    /** Constante que contiene la llave del campo de número de planilla asociada */
    public static final String NUMERO_PLANILLA_ASOCIADA = "numeroPlanillaAsociada";
    
    /** Constante que contiene la llave del campo de fecha de pago de planilla asociada */
    public static final String FECHA_PAGO_PLANILLA_ASOCIADA = "fechaPagoPlanillaAsociada";

    /** Constante que contiene la llave del listado de casos parametrizados para descuento al valor de mora */
    public static final String CASOS_DESCUENTO_INTERES = "casosDecuentoInteres";

    /** Constante que contiene la llave del listado de tipos de cotizante encontrados en la planilla */
    public static final String TIPOS_COTIZANTES_ENCONTRADOS = "listadoTiposCotizante";
    
    /** Constante que contiene la llave del indicador de UGPP del registro tipo 4 */
    public static final String INDICADOR_UGPP = "indicadorUGPP";
    
    /** Constante que contiene la llave para el indicador de presencia de un registro tipo 4 */
    public static final String HAY_REGISTRO_4 = "hayRegistro4";

    /** Constantes que contienen los valores del:
     * Nombre del campo validado en validación de mora
     * Tipo de error generado al fallar la validación
     * ID del campo para ubicar su configuración
     * */
    public static final String NOMBRE_CAMPO = "nombreCampo";
    public static final String TIPO_ERROR = "tipoError";
    public static final String ID_CAMPO = "codigoCampo";

    /** Constante que contiene la llave del número de la línea en la que se encuentra el valor de la mora */
    public static final String LINEA_VALOR_MORA = "lineaValorMora";
    
    /** Constante que contiene la llave del Set con los registros requeridos no encontrados */
    public static final String LISTA_REGISTROS_FALTANTES = "listaRegistrosFaltantes";

    /** Constante que contiene la llave del listado de control de cantidad de registros léidos por tipo */
    public static final String LISTA_CONTROL_REGISTROS = "listaControlRegistros";

    /** Constante que contiene la llave del mapa de control de las sumatorias por lote en los archivos OF */
    public static final String MAPA_CONTROL_SUMATORIAS_LOTES_OF = "mapaControlSumatoriasLotesOF";

    /** Constante que contiene la llave del tipo de persona */
    public static final String TIPO_PERSONA = "tipoPersona";

    /** Constante que contiene la llave para el tamaño límite individual de archivos cargados*/
    public static final String LIMITE_TAMANO_INDIVIDUAL = "limiteTamano";

    /** Constante que contiene la llave para el listado de las líneas vacías encontradas en el archivo */
    public static final String LISTADO_LINEAS_VACIAS = "listaLineasVacias";

    /** Constante que contiene la llave para el listado de los DTO de control de subgrupos de archivos de corrección */
    public static final String LISTA_CONTROL_CORRECCIONES = "listaControlCorrecciones";
    
    /** Constante que contiene la llave de la clave anterior de tipo y número de ID de cotizante para correcciones */
    public static final String LLAVE_COTIZANTE_CORRECCION_ANTERIOR = "llaveCotizanteAnterior";
    
    /** Constante que contiene la llave del valor del campo Correcciones de la línea anterior */
    public static final String LLAVE_CORRECCION_ANTERIOR = "llaveCotizanteAnterior";
}
