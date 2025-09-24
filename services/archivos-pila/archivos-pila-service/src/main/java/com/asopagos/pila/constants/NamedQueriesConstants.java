package com.asopagos.pila.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de las
 * NamedQueries del servicio<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 386, 387, 388, 390, 391, 407, 393
 * <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
public class NamedQueriesConstants {

    public final static String CONSULTAR_ARCHIVOS_CARGADOS_Y_EJECUTADOS_POR_PLANILLA = "ArchivosPilaService.PilaIndicePlanilla.porPlanilla.cargadosEjecutados";

    public final static String CONSULTAR_ARCHIVOS_CARGADOS_Y_EJECUTADOS_POR_PLANILLA_MIGRADAS = "ArchivosPilaService.PilaIndicePlanilla.porPlanilla.cargadosEjecutados.migradas";

    public final static String CAMBIAR_ESTADO_INDICE_PLANILLA = "ArchivosPilaService.PilaIndicePlanilla.cambiar.estado";

    // Sentencia para actualizar una entrada de proceso PILA
    public final static String ACTUALIZAR_PROCESO_PILA = "ArchivosPilaService.PilaProceso.actualizarProceso";

    // Consulta de un proceso de PILA de acuerdo a su ID
    public final static String CONSULTAR_PROCESO_PILA_ID = "ArchivosPilaService.PilaProceso.consultarPorId";


    // Consulta los códigos de departamento
    public static final String CONSULTAR_CODIGOS_DEPARTAMENTO = "ArchivosPilaService.PilaIndicePlanilla.ConsultarDepartamentos";

    // Consulta los códigos de municipio
    public static final String CONSULTAR_CODIGOS_MUNICIPIO = "ArchivosPilaService.PilaIndicePlanilla.ConsultarMunicipios";

    // Consulta de empresa por tipo y número de ID
    public static final String CONSULTAR_EMPRESA_POR_TIPO_NUMERO_ID = "ArchivosPilaService.Empresa.ConsultarEmpresa";

    // Consulta de entidad pagadora por tipo y número de ID
    public static final String CONSULTAR_ENTIDAD_PAGADORA_POR_TIPO_NUMERO_ID = "ArchivosPilaService.EntidadPagadora.ConsultarEntidadPagadora";

    // Consulta de entidad pagadora por tipo y número de ID
    public static final String CONSULTAR_PERSONA_POR_TIPO_NUMERO_ID = "ArchivosPilaService.Persona.ConsultarPersona";

    // Consulta de los códigos de actividad económica
    public static final String CONSULTAR_CODIGOS_CIIU = "ArchivosPilaService.PilaIndicePlanilla.ConsultarCodigosCIIU";

    // Consulta de parametrización de beneficio de ley
    public static final String CONSULTAR_BENEFICIO_LEY = "ArchivosPilaService.Beneficio.ConsultarDatosBeneficio";

    // Consulta de la definición de un campo en el componente de lectura de archivos
    public static final String CONSULTAR_DEFINICION_CAMPO = "ArchivosPilaService.FieldDefinitionLoad.ConsultarDefinicionCampo";

    // Consulta de una planilla en el índice de archivos OI de acuerdo a su estado
    public static final String CONSULTAR_INDICE_OI_ESTADO = "ArchivosPilaService.PilaIndicePlanilla.ConsultarIndiceEstadoOI";

    // Consulta de una planilla en el índice de archivos OI de acuerdo a un grupo de estados y tipos de carga
    public static final String CONSULTAR_INDICE_OI_ESTADO_MULTIPLE = "ArchivosPilaService.PilaIndicePlanilla.ConsultarIndiceEstadoMultipleOI";

    // Consulta de una planilla en el índice de archivos de un OI específico y de acuerdo a un grupo de estados y tipos de carga
    public static final String CONSULTAR_INDICE_OI_ESPECIFICO_ESTADO_MULTIPLE = "ArchivosPilaService.PilaIndicePlanilla.ConsultarIndiceEstadoMultipleOIEspecifico";

    // Consulta de una planilla en el índice de archivos OF de acuerdo a su estado
    public static final String CONSULTAR_INDICE_OF_ESTADO = "ArchivosPilaService.PilaIndicePlanilla.ConsultarIndiceEstadoOF";

    // Consulta de una planilla en el índice de archivos OI de acuerdo a un grupo de estados
    public static final String CONSULTAR_INDICE_OF_ESTADO_MULTIPLE = "ArchivosPilaService.PilaIndicePlanilla.ConsultarIndiceEstadoMultipleOF";

    // Sentencia para inactivar una planilla en el índice de archivos OI de acuerdo a su estado
    public static final String INACTIVAR_INDICE_OI = "ArchivosPilaService.PilaIndicePlanilla.EliminarIndiceEstadoOI";

    // Sentencia para inactivar una planilla en el índice de archivos OF de acuerdo a su estado
    public static final String INACTIVAR_INDICE_OF = "ArchivosPilaService.PilaIndicePlanilla.EliminarIndiceEstadoOF";

    // Consulta de una planilla en el índice de archivos a partir de su ID de índice
    public static final String CONSULTAR_ARCHIVO_PILA_EN_INDICE_POR_ID = "ArchivosPilaService.PilaIndicePlanilla.ConsultarArchivoEnIndicePorId";

    // Consulta de una planilla en el índice de archivos a partir de su nombre y fecha de modificación
    public static final String CONSULTAR_ARCHIVO_PILA_EN_INDICE_POR_FECHA = "ArchivosPilaService.PilaIndicePlanilla.ConsultarArchivoEnIndicePorFecha";

    // Consulta de una planilla en el índice de archivos a partir de su número y tipo
    public static final String CONSULTAR_ARCHIVO_PILA_EN_INDICE_POR_TIPO = "ArchivosPilaService.PilaIndicePlanilla.ConsultarArchivoEnIndicePorTipo";

    // Actualización de un registro de índice de planilla
    public static final String ACTUALIZAR_REGISTRO_INDICE = "ArchivosPilaService.PilaIndicePlanilla.ActualizarRegistroIndiceCorreccion";

    // Consulta del estado de los archivos de una planila
    public static final String CONSULTAR_ESTADO_GENERAL = "ArchivosPilaService.PilaIndicePlanilla.ConsultarEstadoPlanilla";

    // Consulta del estado de un archivo por estado de bloque
    public static final String CONSULTAR_ESTADO_PLANILLA_TIPO = "ArchivosPilaService.PilaIndicePlanilla.ConsultarEstadoPlanillaTipo";

    // Actualización de un registro de estados por bloque
    public static final String ACTUALIZAR_ESTADO_POR_BLOQUE = "ArchivosPilaService.PilaIndicePlanilla.ActualizarRegistroEstado";

    // Consulta de valores de variables de paso entre bloques
    public static final String CONSULTAR_VARIABLES_DE_PASO = "ArchivosPilaService.PilaIndicePlanilla.ConsultarVariablesDePaso";

    // Consulta de índices de planilla con estados por bloque
    public static final String CONSULTAR_INDICE_CON_ESTADO = "ArchivosPilaService.PilaEstadoBloque.ConsultarIndicesConEstado";

    // Sentencia para el borrado de variables de paso de contexto
    public static final String ELIMINAR_VARIABLES_DE_PASO = "ArchivosPilaService.PilaIndicePlanilla.EliminarVariablesDePaso";

    // Sentencia para eliminar una entrada específica del paso de valores
    public static final String ELIMINAR_VARIABLE_DE_PASO_ESPECIFICA = "ArchivosPilaService.PilaIndicePlanilla.EliminarVariableEspecifica";

    // Consulta para realizar rollback a la persistencia del archivo A registro 1
    public static final String ROLLBACK_ARCHIVO_A_REGISTRO_1 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoARegistro1";

    // Consulta para realizar rollback a la persistencia del archivo AP registro 1
    public static final String ROLLBACK_ARCHIVO_AP_REGISTRO_1 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoAPRegistro1";

    // Consulta para realizar rollback a la persistencia del archivo I registro 1
    public static final String ROLLBACK_ARCHIVO_I_REGISTRO_1 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoIRegistro1";

    // Consulta para realizar rollback a la persistencia del archivo I registro 2
    public static final String ROLLBACK_ARCHIVO_I_REGISTRO_2 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoIRegistro2";

    // Consulta para realizar rollback a la persistencia del archivo I registro 3
    public static final String ROLLBACK_ARCHIVO_I_REGISTRO_3 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoIRegistro3";

    // Consulta para realizar rollback a la persistencia del archivo I registro 4
    public static final String ROLLBACK_ARCHIVO_I_REGISTRO_4 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoIRegistro4";

    // Consulta para realizar rollback a la persistencia del archivo IP registro 1
    public static final String ROLLBACK_ARCHIVO_IP_REGISTRO_1 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoIPRegistro1";

    // Consulta para realizar rollback a la persistencia del archivo IP registro 2
    public static final String ROLLBACK_ARCHIVO_IP_REGISTRO_2 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoIPRegistro2";

    // Consulta para realizar rollback a la persistencia del archivo IP registro 3
    public static final String ROLLBACK_ARCHIVO_IP_REGISTRO_3 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoIPRegistro3";

    // Consulta para realizar rollback a la persistencia del archivo F registro 1
    public static final String ROLLBACK_ARCHIVO_F_REGISTRO_1 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoFRegistro1";

    // Consulta para realizar rollback a la persistencia del archivo F registro 1
    public static final String ROLLBACK_ARCHIVO_F_REGISTRO_5 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoFRegistro5";

    // Consulta para realizar rollback a la persistencia del archivo F registro 1
    public static final String ROLLBACK_ARCHIVO_F_REGISTRO_6 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoFRegistro6";

    // Consulta para realizar rollback a la persistencia del archivo F registro 1
    public static final String ROLLBACK_ARCHIVO_F_REGISTRO_8 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoFRegistro8";

    // Consulta para realizar rollback a la persistencia del archivo F registro 1
    public static final String ROLLBACK_ARCHIVO_F_REGISTRO_9 = "ArchivosPilaService.PilaIndicePlanilla.RollBackArchivoFRegistro9";

    // Consulta de un registro tipo 1 para archivo I a partir del ID del índice de planilla
    public static final String CONSULTAR_ARCHIVO_I_REGISTRO_1 = "ArchivosPilaService.PilaIndicePlanilla.ConsultarArchivoIRegistro1";

    // Consulta de un registro tipo 2 para archivo I a partir del ID del índice de planilla
    public static final String CONSULTAR_ARCHIVO_I_REGISTRO_2 = "ArchivosPilaService.PilaArchivoIRegistro2.ConsultarArchivoIRegistro2";

    // Consulta de un registro tipo 3 para archivo I a partir del ID del índice de planilla
    public static final String CONSULTAR_ARCHIVO_I_REGISTRO_3 = "ArchivosPilaService.PilaIndicePlanilla.ConsultarArchivoIRegistro3";

    // Sentencia para actualizar la información del registro 3 de una archivo I
    public static final String ACTUALIZAR_ARCHIVO_I_REGISTRO_3 = "ArchivosPilaService.PilaIndicePlanilla.ActualizarArchivoIRegistro3";

    // Consulta de un registro tipo 1 para archivo IP a partir del ID del índice de planilla
    public static final String CONSULTAR_ARCHIVO_IP_REGISTRO_1 = "ArchivosPilaService.PilaIndicePlanilla.ConsultarArchivoIPRegistro1";

    // Consulta de un registro tipo 3 para archivo IP a partir del ID del índice de planilla
    public static final String CONSULTAR_ARCHIVO_IP_REGISTRO_3 = "ArchivosPilaService.PilaIndicePlanilla.ConsultarArchivoIPRegistro3";

    // Consulta de Contenido del índice de archivos
    public static final String CONSULTAR_INDICE_OF = "ArchivosPilaService.PilaIndicePlanilla.ConsultarIndiceCompletoOF";

    // Consulta de una planilla en el índice de archivos a partir de su ID de índice
    public static final String CONSULTAR_ARCHIVO_PILA_EN_INDICE_POR_ID_OF = "ArchivosPilaService.PilaIndicePlanilla.ConsultarArchivoEnIndicePorIdOF";

    // Consulta del índice de archivos del OF de acuerdo a la fecha del recaudo y el código del banco
    public static final String CONSULTAR_INDICE_ARCHIVO_OF = "ArchivosPilaService.PilaIndicePlanilla.ConsultaIndiceArchivoOF";

    // Actualización de un registro de índice de planilla
    public static final String ACTUALIZAR_REGISTRO_INDICE_OF = "ArchivosPilaService.PilaIndicePlanilla.ActualizarRegistroIndiceOF";

    // Consulta del estado de validación de un archivo de OF
    public static final String CONSULTAR_ESTADO_ARCHIVO_OF = "ArchivosPilaService.PilaIndicePlanilla.ConsultarEstadoArchivoOF";

    // Actualización de un registro de estados por bloque archivo OF
    public static final String ACTUALIZAR_ESTADO_POR_BLOQUE_OF = "ArchivosPilaService.PilaIndicePlanilla.ActualizarRegistroEstadoOF";

    // Consulta de registro tipo 6 de archivo OF por # de planilla, período y operador de información
    public static final String CONSULTAR_ARCHIVO_F_REGISTRO_6 = "ArchivosPilaService.PilaIndicePlanilla.ConsultarArchivoFRegistro6";

    // Actualización del estado de conciliación de registro tipo 6 en archivo OF al cargar un archivo F
    public static final String CONSULTAR_ARCHIVO_F_REGISTRO_6_AL_CARGAR_F = "ArchivosPilaService.PilaIndicePlanilla.ConsultarArchivoFRegistro6.alCargarF";

    // Actualización del estado de conciliación de registro tipo 6 en archivo OF
    public static final String ACTUALIZAR_ESTADO_ARCHIVO_F_REGISTRO_6 = "ArchivosPilaService.PilaIndicePlanilla.ActualizarEstadoArchivoFRegistro6";

    // Consulta del ID de registro 5 con base en índice de planilla OF y número de lote
    public static final String CONSULTAR_ARCHIVO_F_ID_REGISTRO_5 = "ArchivosPilaService.PilaArchivoFRegistro5.ConsultarIDPorLote";

    // Consulta de los estados presentes en los registros tipo 6 de un archivo OF
    public static final String CONSULTAR_ESTADOS_REGISTRO_6_OF = "ArchivosPilaService.PilaArchivoFRegistro6.ConsultarEstadosR6";

    // Consulta de los días festivos
    public static final String CONSULTAR_DIAS_FESTIVOS = "ArchivosPilaService.PilaIndicePlanilla.ConsultarFestivosAnio";

    // Consulta de reglas de normatividad para cálculo de días de mora de acuerdo a un período
    public static final String CONSULTAR_NORMATIVIDAD_FECHA_VENCIMIENTO = "ArchivosPilaService.PilaIndicePlanilla.ConsultaNormatividadFechaVencimiento";

    // Consulta de la oportunidad en presentación de la planilla de acuerdo al tipo de planilla
    public static final String CONSULTAR_OPORTUNIDAD_PRESENTACION = "ArchivosPilaService.PilaIndicePlanilla.ConsultaOportunidadPresentacionPILA";

    // Consulta de períodos de tasa de interés
    public static final String CONSULTAR_PERIODO_INTERES = "ArchivosPilaService.PilaIndicePlanilla.ConsultarPeriodoTasaInteres";

    // Consulta de períodos de tasa de interés en rango de fechas de validez
    public static final String CONSULTAR_PERIODO_INTERES_RANGO = "ArchivosPilaService.PilaIndicePlanilla.ConsultarPeriodoTasaInteresRango";

    // Consulta de condiciones parametrizadas para otorgar descuento en mora de aportes
    public static final String CONSULTAR_CASOS_DESCUENTO_MORA = "ArchivosPilaService.DescuentoInteresMora.ConsultarCasosDescuento";

    // Consulta de operadores de información activos
    public static final String CONSULTAR_OPERADORES_INFORMACION_ACTIVOS = "ArchivosPilaService.PilaOperadorInformacion.ConsultarOperadoresinformacionActivos";

    // Consulta de operadores financieros
    public static final String CONSULTAR_OPERADORES_FINANCIEROS = "ArchivosPilaService.PilaOperadorInformacion.ConsultarOperadoresFinancieros";

    // Consulta de operadores de información activos para una CCF
    public static final String CONSULTAR_OPERADORES_INFORMACION_ACTIVOS_CCF = "ArchivosPilaService.PilaOperadorInformacionCcf.ConsultarOperadoresinformacionActivosCcf";

    // Consulta de procesos de validación por estado
    public static final String CONSULTAR_PROCESO_POR_ESTADO = "ArchivosPilaService.PilaProceso.ConsultarProcesos";

    // Consulta de parámetros de conexión de un operador de información específico
    public static final String CONSULTAR_PARAMETROS_OPERADOR_INFORMACION_ACTIVO_ESPECIFICO = "ArchivosPilaService.PilaOperadorInformacion.ConsultarOperadorinformacionActivoEspecifico";

    // Consulta de parámetros de conexión de un operadores de información activos para la CCF
    public static final String CONSULTAR_PARAMETROS_OPERADOR_INFORMACION_ACTIVOS = "ArchivosPilaService.PilaOperadorInformacion.ConsultarOperadorinformacionActivo";

    // Consulta de la configuración de ejecución programada de PILA vigente a la fecha actual por CCF
    public static final String CONSULTAR_PARAMETROS_EJECUCION_ACTUAL = "ArchivosPilaService.PilaEjecucionProgramada.ConsultarEjecucionProgramadaVigenteCcf";

    // Actualización del estado de gestión de las inconsistencias asociadas a un ID de índice de planilla
    public static final String ACTUALIZAR_GESTION_INCONSISTENCIA = "ArchivosPilaService.PilaErrorValidacionLog.ActualizarEstadosGestionPorIdOI";
    // Consultar del estado de gestión de las inconsistencias asociadas a un ID de índice de planilla
    public static final String CONSULTAR_GESTION_INCONSISTENCIA = "ArchivosPilaService.PilaErrorValidacionLog.ConsultarEstadosGestionPorIdOI";

    // Actualización del estado de gestión de las inconsistencias asociadas a un ID de índice de planilla y un bloque específico
    public static final String ACTUALIZAR_GESTION_INCONSISTENCIA_BLOQUE = "ArchivosPilaService.PilaErrorValidacionLog.ActualizarEstadosGestionPorIdOIyBloque";
    // Consulta del estado de gestión de las inconsistencias asociadas a un ID de índice de planilla y un bloque específico
    public static final String CONSULTAR_GESTION_INCONSISTENCIA_BLOQUE = "ArchivosPilaService.PilaErrorValidacionLog.BuscarEstadosGestionPorIdOIyBloque";

    // Consulta de índices de planilla por conjunto de números de planilla y estados
    public static final String CONSULTAR_ARCHIVO_PILA_EN_INDICE_MULTIPLE = "ArchivosPilaService.PilaIndicePlanilla.ConsultarPorNumerosPlanillaYEstados";

    // Consulta de registros tipo 6 de OF por nombre de archivo
    public static final String CONSULTAR_REGISTROS_TIPO_6 = "ArchivosPilaService.PilaArchivoFRegistro6.ConsultarRegistro6PorNombre";

    // Consulta de las planillas que están pendientes por conciliar
    public static final String CONSULTAR_PENDIENTES_CONCILIACION = "ArchivosPilaService.PilaIndicePlanilla.ConsultarPendientesPorConciliar";

    // Consulta de las planillas activas por listado de IDs
    public static final String CONSULTAR_INDICES_LISTADO_ID = "ArchivosPilaService.PilaIndicePlanilla.ConsultarPorListaIds";

    // Consulta de un proceso de PILA de acuerdo a su ID
    public final static String CONSULTAR_INDICES_LISTADO_ID_PLANILLA = "ArchivosPilaService.PilaIndicePlanilla.ConsultarPorListaIdsPlanilla";

    public final static String CONSULTAR_INDICES_PLANILLAS_OI = "ArchivosPilaService.PilaIndicePlanilla.ConsultarPlanillasOI";

    // Consulta de las planillas activas por listado de IDs
    public static final String CONSULTAR_INDICES_LISTADO_ID_OF = "ArchivosPilaService.PilaIndicePlanillaOF.ConsultarPorListaIds";

    // Consulta de solicitud de cambio de ID asociado a una planilla
    public static final String CONSULTAR_SOLICITUD_CAMBIO_ID = "ArchivosPilaService.PilaSolicitudCambioNumIdentAportante.consultarPorId";

    // Consulta de la existencia de una archivo FTP en el índice de planillas de OI
    public static final String CONSULTAR_EXISTENCIA_FTP_OI = "ArchivosPilaService.PilaIndicePlanilla.consultarExistenciaFtpOI";

    // Consulta de la existencia de una archivo FTP en el índice de planillas de OI
    public static final String CONSULTAR_EXISTENCIA_FTP_ARCHIVO = "ArchivosPilaService.PilaIndicePlanilla.consultarExistenciaArchivo";

    // Solicitud de ejecución del SP que retorna un listado de ID para los registros tipo 2 de I-IP y 6 de F para persistencia
    public static final String EJECUTAR_USP_GET_CONJUNTO_SECUENCIAS_PERSISTENCIA_PILA_M1 = "ArchivosPilaService.StoredProcedures.USP_GET_ConjuntoSecuenciasPersistenciaPilaM1";

    /**
     * Consulta la entidad de lista blanca de pila por un número de
     * identificación dado
     */
    public static final String CONSULTAR_LISTA_BLANCA_PILA_POR_NUMERO_IDENTIFICACION = "ArchivosPilaService.ListasBlancasAportantes.porNumeroIdentificacion";

    /**
     * Consulta la lista de planillas que se encuentran detenidas
     */
    public static final String CONSULTAR_LISTA_PLANILLAS_I_DETENIDAS_PENDIENTES_CONCILIACION = "ArchivosPilaService.ProcesoAutomatico.PlanillasPendientesConciliacion.Detenidas";
    public static final String CONSULTAR_LISTA_PLANILLAS_I_SEGUN_SP_REINICIAR_PLANILLAS = "ArchivosPilaService.ProcesoAutomatico.Listar.Planillas.Segun.Reiniciar.Planillas";

    public static final String CONSULTAR_LISTA_PLANILLAS_I_DETENIDAS_PENDIENTES_VALOR_CERO = "ArchivosPilaService.ProcesoAutomatico.PlanillasPendientesValorCero.Detenidas";

    public static final String CONSULTAR_PLANILLA_ES_AUTIMATICA = "ArchivosPilaService.ProcesoAutomatico.PlanillasPendientesM2Automatica";

    /**
     * Se encarga de consulta que planillas se deben reprocesar
     */
    public static final String CONSULTAR_PLANILLA_A_REPROCESAR = "USP_ReiniciarPlanillas";

    public static final String EXECUTE_PILA1_PERSISTENCIA = "ArchivosPilaService.StoredProcedures.USP_ExecutePILA1Persistencia";

    //Consulta de Empresas descentralizada
    public static final String CONSULTAR_EMPRESAS_DESCENTRALIZADA = "ArchivosPilaService.PilaIndicePlanilla.ConsultarEmpresasDescentralizadas";

    //Consulta una empresa descentralizada con todos los atrubutos
    public static final String CONSULTAR_EMPRESA_DESCENTRALIZADA = "ArchivosPilaService.PilaIndicePlanilla.ConsultarEmpresaDescentralizada";

    //Consulta una empresa descentralizada sin tipo de documento
    public static final String CONSULTAR_EMPRESA_DESCENTRALIZADA_SIN_TIPO = "ArchivosPilaService.PilaIndicePlanilla.ConsultarEmpresaDescentralizadaSinTipo";


    //Consulta planillas F que quedaron conciliadas y su I en busca del F
    public static final String CONSULTAR_PLANILLAS_F_PENDIENTES_ACTUALIZACION = "ArchivosPilaService.PilaIndicePlanilla.ConstultarFsPendientesActualizacion";


    public static final String CONSULTAR_PLANILLAS_F_PENDIENTES_ACTUALIZACION_ESTADO_CONCILIADO = "ArchivosPilaService.PilaIndicePlanilla.ConstultarFsPendientesActualizacion.Estado.Conciliado";

    public final static String ANULAR_PLANILLAS_REPROCESO = "ArchivosPilaService.PilaIndicePlanilla.porPlanilla.anular";

    public final static String ANULAR_PLANILLAS_REPROCESO_ESTADO_BLOQUE = "ArchivosPilaService.PilaIndicePlanilla.porPlanilla.anular.estadoBloque";

    public final static String ANULAR_PLANILLAS = "ArchivosPilaService.PilaIndicePlanilla.Planilla.anular";

    public final static String ANULAR_PLANILLAS_CONDICION = "ArchivosPilaService.PilaIndicePlanilla.Planilla.anular.validar";

    /**
     * Se encarga de consulta que planillas se deben PROCESAR DE B6 A B8
     */
    public static final String CONSULTAR_PLANILLA_A_PROCESAR_DE_B6_A_B8 = "USP_ConsultarPlanillasConciliadasB6aB8";



}
