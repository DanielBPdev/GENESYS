package com.asopagos.constants;

/**
 * <b>Descripcion:</b> Clase que contiene las constantes de nombramiento de parametros para Stored Procedures<br/>
 * <b>Módulo:</b> Asopagos - HU-211-395, HU-211-396, HU-211-397, HU-211-398, HU-211-480<br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ConstantesParametrosSp {
    private ConstantesParametrosSp(){}

    /** Identificador de índice de planilla */
    public static final String ID_INDICE_PLANILLA = "iIdIndicePlanilla";
    
    public static final String ID_REGISTRO_GENERAL = "idRegistroGeneral";
    

    /** Fase inicial de ejecución */
    public static final String FASE_INICIO = "sFase";

    /** Indica sí la ejecución es simulada para un proceso asistido */
    public static final String ES_SIMULADO = "bSimulado";
    
    /** Identificador de paquete de ejecución de PILA */
    public static final String ID_PAQUETE = "iIdentificadorPaquete";

    /** Indica el usuario que realiza la ejecución de un proceso asistido */
    public static final String USUARIO_PROCESO = "sUsuarioProceso";

    /** Indica el usuario de procesamiento por defecto para la invocación del orquestador de pila 2 staging */
    public static final String USUARIO_PROCESAMIENTO_POR_DEFECTO = "SISTEMA";

    /** Indica sí la planilla representa un aporte propio o no */
    public static final String ES_APORTE_PROPIO = "bEsAportePropio";

    /** Tipo de identificación del cotizante */
    public static final String TIPO_ID_COTIZANTE = "sTipoDocumentoCotizante";

    /** Número de identificación del cotizante */
    public static final String NUM_ID_COTIZANTE = "sNumeroIdentificacionCotizante";

    /** Período del aporte */
    public static final String PERIODO_APORTE_TEXTO = "sPeriodoAporte";

    /** Indica sí un cotizante presenta subsidio monetario */
    public static final String TIENE_SUBSIDIO = "bTieneSubsidio";

    /** Indica el nombre de la meta equivalente al enum */
    public static final String META = "sMeta";

    /** Indica la periodicidad */
    public static final String PERIODICIDAD = "sPeriodicidad";

    /** Indica el periodo */
    public static final String PERIODO = "sPeriodo";

    /** Indica la frecuencia */
    public static final String FRECUENCIA = "sFrecuencia";

    /** Hace referencia al Json de la parametrizacion de la meta a registrar */
    public static final String VALORES_PARAMETRIZACION_META = "sValores";
   
    /**
     * Parámetro "tipo de identificación" 
     */
    public static final String TIPO_IDENTIFICACION = "tipoIdentificacion";
    
    /**
     * Parámetro "número de identificación" 
     */
    public static final String NUMERO_IDENTIFICACION = "numeroIdentificacion";

    /**
     * Parámetro "tipo de aportante" 
     */
    public static final String TIPO_APORTANTE = "tipoAportante";
    
    /**
     * Parámetro "tipo de afiliado" 
     */
    public static final String TIPO_AFILIADO = "tipoAfiliado";
    
    /**
     * Parámetro "tipo de identificación del empleador" 
     */
    public static final String TIPO_IDENTIFICACION_EMPLEADOR = "tipoIdentificacionEmpleador";
    
    /**
     * Parámetro "número de identificación del empleador" 
     */
    public static final String NUMERO_IDENTIFICACION_EMPLEADOR = "numeroIdentificacionEmpleador";

    /**
     * Parámetro "fecha para buscar el estado de la persona"
     */
    public static final String FECHA_CONSULTA = "fechaConsulta";
    
    /** Indica sí la ejecución debe ejecutar el SP USP_DeleteBloqueStaging  */
    public static final String BORRAR_BLOQUE_STAGING = "bBorrarBloqueStaging";
    
    /** Parámetro identificador de la transacción */
    public static final String ID_TRANSACCION = "IdTransaccion";
    
    /** Parámetro id persona*/
    public static final String ID_PERSONA = "idPersona";
    
    public static final String REANUDAR_TRANSACCION_410 = "bReanudarTransaccion";
    
    public static final String ID_TRANSACCION_410 = "IdTransaccion";
}
