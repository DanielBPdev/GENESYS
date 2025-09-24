package com.asopagos.pila.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de las
 * NamedQueries del servicio<br>
 * <b>Módulo:</b> PilaService HU-211-401 y HU-211-410 <br/>
 * 
 * @author <a href="mailto:hhernandez@heinsohn.com.co">Ricardo Hernandez Cediel</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
public class NamedQueriesConstants {

    /* ----------------------------------------- INICIO CONTANTES CONSULTAS MODELO PILA ------------------------------------- */
      public static final String CONTEO_INCONSISTENCIAS_ERROR_TOTAL_PILA = "PilaBandejaService.PilaErrorValidacionLog.ConteoInconsistenciasTotal.Pila";

    public static final String PENDIENTES_POR_PROCESAR_OI = "PilaService.PilaIndicePlanilla.PendientesPorProcesarInformacion";

    public static final String PROCESO_FINALIZADO = "PilaService.PilaIndicePlanilla.ProcesoFinalizado";

    public static final String REGISTROS_BANDEJA_INCONSISTENTES = "PilaService.PilaIndicePlanilla.ArchivosInconsistentesOI";

    public static final String REGISTROS_BANDEJA_GESTION = "PilaService.PilaIndicePlanilla.ArchivosBandejaGestionOI";

    public static final String PENDIENTES_POR_PROCESAR_MANUAL = "PilaService.PilaIndicePlanilla.ArchivosPendientesProcesoManualOI";

    public static final String PROCESO_FINALIZADO_MANUAL = "PilaService.PilaIndicePlanilla.ProcesoFinalizadoManual";
    
    public static final String PROCESO_FINALIZADO_MANUAL_TOTAL = "PilaService.PilaIndicePlanilla.ProcesoFinalizadoManualTotal";

    public static final String ARCHIVOS_CARGADOS_CONTROL = "PilaService.PilaIndicePlanilla.ArchivosCargadosControl";

    public static final String ARCHIVOS_EN_PROCESO_CONTROL = "PilaService.PilaIndicePlanilla.ArchivosEnProcesoControl";

    public static final String REGISTROS_BANDEJA_INCONSISTENTES_CONTROL = "PilaService.PilaIndicePlanilla.ArchivosInconsistentesOIControl";

    public static final String REGISTROS_BANDEJA_GESTION_CONTROL = "PilaService.PilaIndicePlanilla.ArchivosBandejaGestionOIControl";

    public static final String PENDIENTES_POR_PROCESAR_MANUAL_CONTROL = "PilaService.PilaIndicePlanilla.ArchivosPendientesProcesoManualOIControl";

    public static final String TOTAL_APORTES_REGISTRADOS = "PilaService.AporteDetallado.consultarTotalAportesRegistrados";

    public static final String TOTAL_APORTES_RELACIONADOS = "PilaService.AporteDetallado.consultarTotalAportesRelacionados";

    public static final String DETALLE_APORTES_ASOCIADOS_A_PLANILLA = "PilaService.AporteDetallado.ConsultarDetalleAportesPorPlanilla";
    public static final String DETALLE_APORTES_PESTANA = "PilaService.AporteDetallado.ConsultarPestanaAportes";

    public static final String OBTENER_ID_REGISTRO_GENERAL = "PilaService.RegistroGeneral.obtenerIdRegistroGeneral";

    public static final String OBTENER_REGISTRO_DETALLADO = "PilaService.RegistroGeneral.obtenerRegistroDetallado";

    public static final String CONSULTAR_NOVEDADES = "PilaService.NovedadesDetallePila.ConsultarNovedades";
    public static final String CONSULTAR_TODAS_NOVEDADES = "PilaService.NovedadesDetallePila.ConsultarTodasNovedades";

    public static final String OBTENER_PARAMETROS_INDEPENDIENTE = "PilaService.StagingParametros.ObtenerParametrosIndependiente";

    /* Constante para la consulta de la de planillas marcadas para la gestión manual de PILA 2 */
    public static final String CONSULTAR_CANTIDAD_PLANILLAS_PENDIENTES_GESTION_MANUAL = "PilaService.PilaEstadoBloque.ConsultarCantidadPlanillaPendienteGestionManual";

    /* Constante para la consulta de planillas marcadas para la gestión manual de PILA 2 */
    public static final String CONSULTAR_PLANILLAS_PENDIENTES_GESTION_MANUAL = "PilaService.PilaEstadoBloque.ConsultarPlanillaPendienteGestionManual";

    /* Constante para la consulta de una planilla y sus estados por bloque con base en el ID de índice de planilla */
    public static final String ACTUALIZACION_ESTADO_PLANILLA = "PilaService.PilaEstadoBloque.ConsultarEstadoPlanilla";

    /* Constante para la consulta del número de planilla asociado a una planilla de corrección */
    public static final String CONSULTAR_NUMERO_PLANILLA_ORIGINAL = "PilaService.PilaArchivoIRegsitro1.ConsultarNumeroPlanillaAsociada";

    /* Constante para la consulta de un código de subtipo de cotizante en registro 2 */
    public static final String CONSULTAR_SUBTIPO_COTIZANTE = "PilaService.PilaArchivoIRegsitro2.ConsultarSubtipoCotizante";

    /* Constante para la consulta de la fecha de procesamiento de un índice de planilla */
    public static final String CONSULTAR_FECHA_PROCESO_PLANILLA = "PilaService.PilaIndicePlanilla.ConsultarFechaProcesamientoPlanilla";

    /* Constante para la consulta de un aporte simulado relacionado a un registro detallado específico */
    public static final String CONSULTAR_APORTE_SIMULADO_POR_REGISTRO_DETALLADO = "PilaService.TemAporte.ConsultarAporteSimuladoPorRegistroSimulado";

    /* Consulta el aporte temporal (simulado) */
    public static final String CONSULTAR_APORTE_TEMPORAL = "PilaService.AporteTemporal.consultaAporteSimulado";

    /* Constante para la sentencia para el borrado de una entrada de TemAporte a partir del ID de su registro detallado */
    public static final String BORRAR_APORTE_TEMPORAL = "PilaService.TemAporte.eliminarAporteTemporal";

    /* Constante para la sentencia para el borrado de una entrada de TemAportante a partir del ID de su registro detallado */
    public static final String BORRAR_APORTANTE_TEMPORAL = "PilaService.TemAportante.eliminarAportanteTemporal";

    /* Constante para la sentencia para el borrado de una entrada de TemCotizante a partir del ID de su registro detallado */
    public static final String BORRAR_COTIZANTE_TEMPORAL = "PilaService.TemCotizante.eliminarCotizanteTemporal";

    /* Constante para la consulta de archivos OF procesados finalizados */
    public static final String CONSULTAR_ARCHIVOS_OF_PROCESADOS_FINALIZADOS = "PilaService.PilaIndicePlanillaOF.ProcesadosFinalizados";

    /* Constante para la consulta de archivos OF con inconsistencias */
    public static final String CONSULTAR_ARCHIVOS_OF_CON_INCONSISTENCIAS = "PilaService.PilaIndicePlanillaOF.ArchivosConInconsistencias";

    /* Constante para la consulta de archivos OF en bandejas de gestion */
    public static final String CONSULTAR_ARCHIVOS_OF_BANDEJAS_GESTION = "PilaService.PilaIndicePlanillaOF.EnBandejasDeGestion";

    /* Constantes para la consulta de archivos con carga manual */
    public static final String CONSULTAR_ARCHIVOS_OF_PENDIENTES_PROCESAR_MANUAL = "PilaService.PilaIndicePlanillaOF.PendientesProcesarManual";

    /* COnstantes para la consulta de archivos OF procesado finalizado manual */
    public static final String CONSULTAR_ARCHIVOS_OF_PROCESADO_FINALIZADO_MANUAL = "PilaService.PilaIndicePlanillaOF.ProcesadosFinalizadosManual";

    /* Constante para la consulta de los archivos OF cargados exitosamente */
    public static final String CONSULTAR_ARCHIVOS_OF_CARGADOS_EXITOSAMENTE = "PilaService.PilaIndicePlanillaOF.ArchivosCargadosExitosamente";

    /* Constante para la consulta de los archivos OF con estado consistente */
    public static final String CONSULTASR_ARCHIVOS_OF_EN_PROCESO = "PilaService.PilaIndicePlanillaOF.ArchivoConsistente";

    /* Constante para la consultas de los registros F cargados */
    public static final String CONSULTAR_REGISTROS_F_CARGADOS = "PilaService.PilaArchivoFRegistro6.RegistrosFcargados";

    /* Constante para consultar Registros F en proceso */
    public static final String CONSULTAR_REGISTROS_F_EN_PROCESO = "PilaService.PilaArchivoFRegistro6.RegistrosFEnProceso";

    /* Constante para consultar Registros F procesados */
    public static final String CONSULTAR_REGISTROS_F_PROCESADOS = "PilaService.PilaArchivoFRegistro6.RegistrosFProcesados";

    /* Constante para consultar Registros F con inconsistencias */
    public static final String CONSULTAR_REGISTROS_F_INCONSISTENCIAS = "PilaService.PilaArchivoFRegistro6.RegistrosFInconsistencias";

    public static final String CONSULTAR_REGISTROS_F_EN_ESTADO_ARCHIVO = "PilaService.PilaArchivoFRegistro6.RegistrosFEnEstadoArchivo";

    /* Constante para la consulta de los archivos procesados finalizados aplicando criterios de busqueda */
    public static final String CONSULTAR_ARCHIVOS_PROCESADOS_FINALIZADOS_CRITERIOS = "PilaService.PilaIndicePlanilla.ArchivosProcesadosFinalizadosCriterios";

    /* Constante para la consulta de los archivos procesados finalizados aplicando criterios de busqueda (Operador Financiero) */
    public static final String CONSULTAR_ARCHIVOS_PROCESADOS_FINALIZADOS_CRITERIOS_OF = "PilaService.PilaIndicePlanilla.ArchivosProcesadosFinalizadosCriteriosOF";
    

    public static final String VER_ARCHIVOS_PROCESADOS_FINALIZADOS_OI = "PilaService.PilaIndicePlanilla.VerArchivosProcesadosFinalizadosOI";

    /* Constante para la consulta de los archivos procesados finalizados aplicando criterios de busqueda */
    public static final String CONSULTAR_ARCHIVOS_PROCESADOS_FINALIZADOS_MANUAL_CRITERIOS = "PilaService.PilaIndicePlanilla.ArchivosProcesadosFinalizadosManualCriterios";

    /* Constante para la consulta de los archivos sin aplicar ningun criterio */
    public static final String VER_ARCHIVOS_PROCESADOS_FINALIZADOS = "PilaService.PilaIndicePlanilla.VerArchivosProcesadosFinalizados";

    /* Constante para la consulta del detalle de los bloques de validacion para un archivo */
    public static final String VER_DETALLE_BLOQUE_ARCHIVO = "PilaService.PilaIndicePlanilla.VerDetalleBloqueArchivo";

    /* Constante para la consulta del detalle de los bloques de validacion para un archivo OF */
    public static final String VER_DETALLE_BLOQUE_ARCHIVO_OF = "PilaService.PilaIndicePlanilla.VerDetalleBloqueArchivoOF";

    /* Constante para la consulta de la cabecera del detalle del archivo */
    public static final String VER_CABECERA_DETALLE_ARCHIVO = "PilaService.PilaIndicePlanilla.VerCabeceraDetalleArchivo";

    /* Constante para calcular la lista de los nombres relacionados por el numero de planilla (A e I) */
    public static final String CONSULTAR_NOMBRE_ARCHIVOS_RELACIONADOS = "PilaService.PilaIndicePlanilla.ConsultarArchivosRelacionados";

    public static final String CONSULTAR_INDICE_PLANILLA_POR_ID = "PilaService.IndicePlanilla.consultarIndicePlanillaPorId";

    public static final String CONSULTAR_ESTADO_BLOQUE_PLANILLA_POR_ID = "PilaService.EstadoArchivoPorBloque.consultarEstadoArchivoPorBloquePorId";
    
    public static final String CONSULTAR_ESTADO_BLOQUE_PLANILLA_POR_NUMERO_TIPO = "PilaService.EstadoArchivoPorBloque.consultarEstadoArchivoPorBloquePorNumeroYTipo";

    public static final String CONSULTAR_APORTES_A_RELACIONAR = "PilaService.TemAporte.consultarAportesARelacionar";

    /* Constante para la consulta de novedades temporales en staging asociadas a un registro detallado */
    public static final String CONSULTAR_NOVEDADES_TEMPORALES_REGISTRO_DETALLADO = "PilaService.TemNovedad.ConsultarNovedadesTemporalesPorRegistroDetallado";

    /* Constante para la sentencia que elimina las entradas de TemNovedad de acuerdo a listado */
    public static final String ELIMINAR_NOVEDADES_TEMPORALES_ESPECIFICAS = "PilaService.TemNovedad.EliminarNovedadesTemporalesEspecificas";

    /* Constante para la consulta de los aportes temporales asociados a un registro general */
    public static final String CONSULTAR_APORTE_TEMPORAL_REGISTRO_GENERAL = "PilaService.TemAporte.ConsultarAportesTemporalesPorRegistroGeneral";

    /* Constante para la consulta de novedades temporales en staging asociadas a un registro general */
    public static final String CONSULTAR_NOVEDADES_TEMPORALES_REGISTRO_GENERAL = "PilaService.TemNovedad.ConsultarNovedadesTemporalesPorRegistroGeneral";

    /* Constante para la consulta de la existencia de procesos PILA por tipo y estado */
    public static final String CONSULTA_PROCESOS_PILA = "PilaService.PilaProceso.ConsultarExistenciaProcesosPila";

    /* Constante para la consulta del historico de estados por bloque de validación */
    public static final String CONSULTAR_HISTORIAL_ESTADOS_POR_BLOQUE = "PilaService.HistorialEstadoBloque.ConsultarHistorialPorBloque";

    /* ------------------------------------------ FIN CONTANTES CONSULTAS MODELO PILA --------------------------------------- */

    /* ----------------------------------------- INICIO CONTANTES CONSULTAS MODELO CORE ------------------------------------- */

    public static final String CONSULTAR_BANCOS_PARAMETRIZADOS = "PilaService.Banco.consultarBancosParametrizados";

    /* Constante para la consulta de las fechas de días festivos parametrizados */
    public static final String CONSULTAR_DIAS_FESTIVOS = "PilaService.DiasFestivos.ConsultarDiasFestivos";

    /* Constante para la consulta de roles de afiliado con fecha de ingreso o retiro posterior */
    public static final String CONSULTAR_ING_RET_AFILIADO_POSTERIOR = "PilaService.RolAfiliado.ConsultarPorFechaIngRetPosterior";

    /* Constante para la consulta de aportes detallados a partir de un ID de registro detallado */
    public static final String CONSULTAR_APORTE_DETALLADO_CORE_POR_REGISTRO = "PilaService.AporteDetallado.ConsultarAporteDetalladoPorRegistro";

    /* Constante para la consulta de novedades vigentes para un cotizante específico */
    public static final String CONSULTAR_NOVEDADES_VIGENTES_COTIZANTE = "PilaService.NovedadDetalle.ConsultarNovedadesVigentesPorCotizante";

    /* Constante para la consulta que indica que un cotizante ha generado subsidio para un período */
    public static final String HAY_SUBSIDIO_PARA_COTIZANTE = "USP_SM_GET_CotizanteConSubsidioPeriodo";
    
    /* Constante para la consulta de personas existentes en bd */
	public static final String CONSULTAR_PERSONAS_EXISTENTES = "PilaService.consultar.personas.existentes";

    /* ------------------------------------------ FIN CONTANTES CONSULTAS MODELO CORE --------------------------------------- */

    /* ---------------------------------------- INICIO CONTANTES CONSULTAS MODELO STAGING ----------------------------------- */
    /* Constante para la consulta del resultado general de PILA 2 para un número de planilla específico */
    public static final String CONSULTAR_RESULTADO_GENERAL_IIP = "PilaService.RegistroGeneral.ConsultarEstadoGeneralPlanilla.porIdIndicePlanilla";

    /* Constante para la consulta del resultado detallado de PILA 2 para un registro general específico */
    public static final String CONSULTAR_RESULTADO_DETALLADO = "PilaService.RegistroDetallado.ConsultarEstadoDetalladoPlanilla";

    /* Constante para la consulta del resultado detallado de PILA 2 para un registro general específico (Resultado completo) */
    public static final String CONSULTAR_RESULTADO_DETALLADO_COMPLETO = "PilaService.RegistroDetallado.ConsultarEstadoDetalladoPlanillaCompleto";

    /* Constante para la consulta del resultado detallado de PILA 2 para un registro general específico (Sólo IDs) */
    public static final String CONSULTAR_RESULTADO_DETALLADO_IDS = "PilaService.RegistroDetallado.ConsultarEstadoDetalladoPlanillaIds";

    /* Constante para la consulta de un registro general por su ID */
    public static final String CONSULTAR_REGISTRO_GENERAL_POR_ID = "PilaService.RegistroGeneral.consultarRegistroGeneralPorId";

    /* Constante para la consulta de un registro detallado por su ID */
    public static final String CONSULTAR_REGISTRO_DETALLADO_POR_ID = "PilaService.RegistroDetallado.consultarRegistroDetalladoPorId";

    /* Constante para la consulta de registros detallados asociados a un ID de registro general */
    public static final String CONSULTAR_REGISTRO_DETALLADO_POR_REGISTRO_GENERAL = "PilaService.RegistroDetallado.ConsultarRegistrosDetalladosPorIdRegistroGeneral";

    /* Constante para la consulta de la información básica de registros detallados asociados a un ID de registro general */
    public static final String CONSULTAR_INFO_BASICA_REGISTRO_DETALLADO_POR_REGISTRO_GENERAL = "PilaService.RegistroDetallado.ConsultarInfoBasicaRegistrosDetalladosPorIdRegistroGeneral";
    
    /* Constante para la consulta de novedades en staging asociadas a un registro detallado */
    public static final String CONSULTAR_NOVEDADES_REGISTRO_DETALLADO = "PilaService.RegistroDetalladoNovedad.ConsultarNovedadesStagingPorRegistroDetallado";

    /* Constante para la consulta de la cantidad de novedades asociadas a cada registro de tallado de un registro general */
    public static final String CONSULTAR_CANTIDAD_NOVEDADES = "PilaService.RegistroDetalladoNovedad.ConsultarCantidadNovedadesPorRegistroDetallado";

    /* Constante para la actualización de registros detallados a marcar como registrados sin novedad */
    public static final String MARCAR_REGISTRADOS_SIN_NOVEDAD = "PilaService.RegistroDetallado.MarcarRegistroDetalladoSinNovedad";

    /* Constante para la consulta de novedades de reintegro aplicadas en staging asociadas a un registro detallado */
    public static final String CONSULTAR_NOVEDADES_REINTEGRO_PILA = "PilaService.RegistroDetalladoNovedad.ConsultarNovedadesReintegro";

    /** PROCEDIMIENTOS ALMACENADOS */

    /** Procedimiento encargado de invocar la HU-211-395 Verificar condiciones validación aportes vs BD */
    public static final String PROCEDURE_EXECUTE_HU_211_395_DEPENDIENTE = "USP_ValidateHU395CondicionesAportesDependiente";
    public static final String PROCEDURE_EXECUTE_HU_211_395_INDEPENDIENTE = "USP_ValidateHU395CondicionesAportesIndependiente";
    public static final String PROCEDURE_EXECUTE_HU_211_395_PENSIONADOS = "USP_ValidateHU395CondicionesAportesPensionados";

    public static final String CONSULTAR_REGISTRO_GENERAL_POR_NUMERO_PLANILLA = "PilaService.RegistroGeneral.consultarRegistroGeneralPorNumeroPlanilla";
    /* ----------------------------------------- FIN CONTANTES CONSULTAS MODELO STAGING ------------------------------------- */
    
    
    /*---------------Inicio métodos para vistas 360---------------------*/
    /**
     * constante para la consulta del detalle de un aporte dado el id de registro detallado
     */
    public static final String CONSULTAR_DETALLE_APORTE_360 = "PilaService.Consultar.detalleAporteParaVista360";
    
    /**
     * constante para la consulta de las novedades asociadas a un aporte
     */
    public static final String CONSULTAR_NOVEDADES_APORTE = "PilaService.Consultar.novedadesAporte";
    
    /**
     * contante para la consulta de la información correspondiente al aporte
     */
    public static final String CONSULTAR_INFORMACION_APORTE = "PilaService.Consultar.informacionAporte";
    
    /**
     * contante para la consulta de la información correspondiente al aporte por anio
     */
    public static final String CONSULTAR_INFORMACION_APORTE_POR_ANIO = "Certificados.consultar.aportantes.anio";
    
    public static final String OBTENER_ID_REGISTRO_GENERAL_POR_NUMERO_PLANILLA = "PilaService.Long.ObtenerIdRegistroGeneralPorNumeroPlanilla";
    /*---------------Fin métodos para vistas 360------------------------*/
    
    /**
     * Consulta que lista los registros de aportes contenidos en una planilla
     * */
    public static final String CONSULTAR_REGISTROS_A_APORTES_PLANILLA_N = "PilaService.IndicePlanilla.datosTipoAPlanillaN";
    
    /**
     * Consulta que lista los registros de aportes contenidos en una planilla
     * */
    public static final String CONSULTAR_REGISTROS_APORTES_PLANILLA_N = "PilaService.IndicePlanilla.datosPlanillaN";
    
    /**
     * Asigna un identificador de grupo a los registros de aportes contenidos en una planilla
     * */
    public static final String AGRUPAR_REGISTROS_APORTES_PLANILLA_N = "PilaService.IndicePlanilla.agrupaRegistrosPlanillaN";
    
    /**
     * Elimina  un identificador de grupo a los registros de aportes contenidos en una planilla
     * */
    public static final String DESAGRUPAR_REGISTROS_APORTES_PLANILLA_N = "PilaService.IndicePlanilla.desagrupaRegistrosPlanillaN";

    /**
     * Consulta de ID de índice de planilla original
     * */
    public static final String CONSULTAR_INDICE_ORIGINAL = "PilaService.IndicePlanilla.consultarIdOriginal";

    /**
     * Consulta de idicador de aporte anulado por grupo de IDs de RegistroDetallado
     * */
    public static final String CONSULTAR_INDICADOR_APORTE_ANULADO = "PilaService.AporteDetallado.consultarAporteAnulado";
    
    /**
     * Consulta de indicador de aporte anulado por grupo de IDs de RegistroDetallado
     * */
    public static final String USP_AGRUPAR_AUTOMATICAMENTE_REGISTROS_PLANILLA_N = "USP_AgruparAutomaticamenteRegistrosPlanillaN";

    /**
     * Consulta de Registro General por ID de planilla PILA
     * */
    public static final String CONSULTAR_REGISTRO_GENERAL_POR_ID_PLANILLA = "PilaService.RegistroGeneral.consulta.id.planilla";

    /**
     * Consulta de Registro General por ID de planilla PILA
     * */
    public static final String CONSULTAR_AGRUPACIONES = "PilaService.IndicePlanilla.consultarAgrupaciones";
    
    /**
     * Sentencia para la marcación de validaciones de bloque 7 como aprobadas para propósitos de planillas de Corrección / Adición
     * */
    public static final String ACTUALIZAR_APROBACION_REGISTROS_DETALLADOS = "PilaService.RegistroDetallado.actualizar.aprobarB7";

    /**
     * Sentencia para la limpieza de las marcas de agrupación de una planilla de corrección
     * */
	public static final String LIMPIAR_AGRUPACION_ARCHIVO = "PilaService.RegistroDetallado.limpiar.agrupacion";

	/**
     * Sentencia para la actualización del estado de ejecucion gestion inconsistencias
     * */
	public static final String ACTUALIZAR_EJECUCION_GESTION_INCONSISTENCIAS = "PilaService.GestionIncosistencia.actualizarEstadoInconsistencias";

	/**
     * Sentencia para la consultar del estado de ejecucion gestion inconsistencias
     * */
	public static final String CONSULTAR_ESTADO_EJECUCION_GESTION_INCONSISTENCIAS = "PilaService.GestionIncosistencia.consultarEstadoInconsistencias";
	
	/**
	 * Sentencia para la verificación de bloqueo en el proceso de la HU 410 automatica
	 */
	public static final String VALIDAR_BLOQUEO_PROCESO_410_AUTOMATICO = "PilaService.GestionIncosistencia.validarBloqueoProceso410Automatico";
	
	
	//REFACTOR PILA MAYO 2020 INICIO

    public static final String CONSULTAR_DATOS_EMPLEADOR_BY_REGISTRO_DETALLADO = "PilaService.consultar.datos.empleador.por.registro.detallado";
    public static final String CONSULTAR_DATOS_AFILIACION_BY_REGISTRO_DETALLADO = "PilaService.consultar.datos.afiliacion.por.registro.detallado";
    
    
    
    
    /**
     * Consulta de indices de planilla por registros generales
     * */
    public static final String CONSULTAR_INDICE_REGENERAL = "PilaService.IndicePlanilla.indicePlanillaByRegGeneral";
    
    public static final String VERIFICAR_EXISTENCIA_APORTES_PENDIENTES = "PilaService.consultar.verificarAportesPendientes";
    
    public static final String VERIFICAR_EXISTENCIA_NOVEDADES_PENDIENTES = "PilaService.consultar.verificarNovedadesPendientes";
    
    public static final String REGISTRAR_PLANILLA_CANDIDATA_REPROCESO = "PilaService.staging.registrarPlanillaCandidataReproceso";
    
    public static final String CONSULTAR_LISTA_BLANCA_PILA_POR_LISTA_NUMERO_IDENTIFICACION = "PilaService.ListasBlancasAportantes.porNumeroIdentificacion";

    public static final String CONSULTAR_PLANILLAS_PENDIENTES_MUNDO_DOS = "PilaService.consultar.planillas.pendientes.mundo.dos";

    public final static String CONSULTAR_LISTAS_BLANCAS = "PilaService.ConsultarListasBlancas";

    public final static String CONSULTAR_LISTA_BLANCA_NUMERO_IDENTIFICACION = "PilaService.Existe.ListasBlancas";
}
