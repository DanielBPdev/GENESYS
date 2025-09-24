package com.asopagos.bandejainconsistencias.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de las
 * NamedQueries del servicio<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 392 <br/>
 * 
 * @author <a href="mailto:anbuitrago@heinsohn.com.co">Andres Felipe Buitrago
 *         F.</a>
 */
public class NamedQueriesConstants {
    // busqueda HU-392
    public static final String CONSULTAR_BANDEJA_I = "PilaBandejaService.PilaErrorValidacionLog.BusquedaI";
    public static final String CONSULTAR_BANDEJA_I_SIN_BLQ5 = "PilaBandejaService.PilaErrorValidacionLog.BusquedaI.SinBlq5";
    public static final String CONSULTAR_BANDEJA_F = "PilaBandejaService.PilaErrorValidacionLog.BusquedaF";
    
//    public static final String BUSCAR_CON_ARGUMENTOS_I_PLANILLA_IDENTIFICACION = "PilaBandejaService.PilaErrorValidacionLog.BusquedaConArgumentosIPlanillaIdentificacion";

    // consulta para establecer pestañas
    public static final String CONSULTAR_PESTANAS_INCONSISTENCIAS = "PilaBandejaService.PilaErrorValidacionLog.PestanasDetalle";
    public static final String CONSULTAR_PESTANAS_INCONSISTENCIAS_F = "PilaBandejaService.PilaErrorValidacionLog.PestanasDetalleF";

    // consulta para cada pestaña del detalle de inconsistencias
    public static final String CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE = "PilaBandejaService.PilaErrorValidacionLog.PestañasInconsistenciasDetalle";
    public static final String CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE_TOTAL = "PilaBandejaService.PilaErrorValidacionLog.PestañasInconsistenciasDetalle.total";
    public static final String CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE_F = "PilaBandejaService.PilaErrorValidacionLog.PestañasInconsistenciasDetalleF";
    public static final String CONSULTAR_PESTAÑAS_INCONSISTENCIAS_DETALLE_F_TOTAL = "PilaBandejaService.PilaErrorValidacionLog.PestañasInconsistenciasDetalleF.total";
    // Consulta para botones de accion de las pantallas
    public static final String CONSULTAR_ID_INDICES_PLANILLAS = "PilaBandejaService.PilaErrorValidacionLog.ObtenerIdPlanillas";
    public static final String CONSULTAR_ID_INDICES_PLANILLAS_OPERADOR_INFORMACION = "PilaBandejaService.PilaErrorValidacionLog.ObtenerIdPlanillas.por.operadorInformacion";
    public static final String OBTENER_INDICE_PLANILLAS = "PilaBandejaService.PilaErrorValidacionLog.ObtenerIndicePlanillas";
    public static final String CONSULTAR_ID_INDICES_PLANILLAS_OF = "PilaBandejaService.PilaArchivoFRegistro6.ObtenerIdPlanillasOF";
    public static final String CONSULTAR_CANTIDAD_REGISTRO6_ARCHIVOS_F = "PilaBandejaService.PilaArchivoFRegistro6.ObtenerTotalRegistro6ArchivoF";
    public static final String OBTENER_INDICE_PLANILLAS_OF = "PilaBandejaService.PilaErrorValidacionLog.ObtenerIndicePlanillasOF";
    public static final String BUSCAR_INDICES_BLOQUE_0 = "PilaBandejaService.PilaErrorValidacionLog.BuscarEnBloque0";
    public static final String BUSCAR_PAREJA_ARCHIVO = "PilaBandejaService.PilaErrorValidacionLog.BuscarParejaArchivo";
    public static final String ELIMINAR_VARIABLES_DE_PASO = "PilaBandejaService.PilaIndicePlanilla.EliminarVariablesDePaso";
    public static final String ANULAR_INDICE_PLANILLA = "PilaBandejaService.PilaIndicePlanilla.AnularIndicePlanilla";
    public static final String ACTUALIZAR_ERROR_VALIDACION = "PilaBandejaService.PilaErrorValidacionLog.ActualizarErrorValidacionLog";
    public static final String ACTUALIZAR_ERROR_VALIDACION_OF = "PilaBandejaService.PilaErrorValidacionLog.ActualizarErrorValidacionLogOF";
    public static final String CONSULTAR_IDS_REG_PLANILLA_OF = "PilaBandejaService.PilaArchivoFRegistro6.ConsultarRegistros6";

    public static final String BUSCAR_ESTADOS_POR_BLOQUE_ID_PLANILLA = "PilaBandejaService.PilaEstadoBloque.BuscarEstadoBloqueIdPlanilla";

    // consultas para HU392-HU411
    public static final String CONSULTAR_ARCHIVOS_ASOCIADOS_INFORMACION = "PilaBandejaService.PilaErrorValidacionLog.ListarArchivosAsociados";
    public static final String CONSULTAR_ARCHIVOS_ASOCIADOS_INFORMACION_2 = "PilaBandejaService.PilaErrorValidacionLog.ListarArchivosAsociados.2";
    public static final String CONSULTAR_ARCHIVOS_ASOCIADOS_FINANCIERO = "PilaBandejaService.PilaErrorValidacionLog.ListarArchivosAsociadosOF";
    public static final String CONSULTAR_ARCHIVOS_ASOCIADOS_INFORMACION_NOMBRE = "PilaBandejaService.PilaErrorValidacionLog.ListarArchivosAsociadosNombreA";
    public static final String BUSQUEDA_INDICE_PLANILLA_AP = "PilaBandejaService.PilaArchivoAPRegistro1.BusquedaConIdPlanillaAP";
    public static final String BUSQUEDA_INDICE_PLANILLA_A = "PilaBandejaService.PilaArchivoARegistro1.BusquedaConIdPlanillaA";
    public static final String BUSQUEDA_INDICE_PLANILLA_IP = "PilaBandejaService.PilaArchivoIPRegistro1.BusquedaConIdPlanillaIP";
    public static final String BUSQUEDA_INDICE_PLANILLA_I = "PilaBandejaService.PilaArchivoIRegistro1.BusquedaConIdPlanillaI";
    public static final String BUSQUEDA_INDICE_PLANILLA_F = "PilaBandejaService.PilaArchivoFRegistro6.BusquedaConIdPlanillaF";
    public static final String BUSQUEDA_INDICE_PLANILLA_CON_NUMERO_PLANILLA_F = "PilaBandejaService.PilaArchivoFRegistro6.BusquedaConPlanillaF";

    public static final String BUSQUEDA_BLOQUE_INDICE = "PilaBandejaService.PilaEstadoBloque.BusquedaBloqueIndice";
    public static final String BUSQUEDA_BLOQUE_INDICE_OF = "PilaBandejaService.PilaEstadoBloqueOF.BusquedaBloqueIndiceOF";

    public static final String BUSQUEDA_ERROR_VALIDACION = "PilaBandejaService.PilaErrorValidacionLog.BusquedaErrorValidacion";
    public static final String CONTEO_REPROCESOS_PLANILLA = "PilaBandejaService.SolicitudCambioNumIdentAportante.ConteoReprocesos";

    // busqueda de registros de los aportantes
    public static final String BUSQUEDA_REGISTRO_A = "PilaBandejaService.PilaArchivoARegistro1.BusquedaRegistroA";
    public static final String BUSQUEDA_REGISTRO_AP = "PilaBandejaService.PilaArchivoARegistro1.BusquedaRegistroAP";

    /** Buscar el id de la pareja de un archivo a partir del número de planilla y el tipo de archivo deseado */
    public static final String BUSCAR_ARCHIVO_I_ID = "PilaBandejaService.CrearAportante.BusquedaIdArchivoI";

    /** Buscar el error log validacion asociado al id de planilla */
    public static final String BUSCAR_ERROR_VALIDACION_LOG_ID_PLANILLA = "PilaBandejaService.CrearAportante.BuscarErrorValidacionLogPlanilla";

    /** Buscar el estado por bloque para un archivo */
    public static final String BUSCAR_ESTADO_BLOQUE_PLANILLA = "PilaBandejaService.CrearAportante.BuscarEstadoBloquePlanilla";

    // busquedas de la ban
    public static final String BUSQUEDA_SOLICITUD_FECHA_INICIO_FIN_PLANILLA = "PilaBandejaService.SolicitudCambioNumIdentAportante.BusquedaFechaInicioFinPlanilla";
    public static final String BUSQUEDA_SOLICITUD_FECHA_INICIO_FIN = "PilaBandejaService.SolicitudCambioNumIdentAportante.BusquedaFechaInicioFin";
    public static final String BUSQUEDA_SOLICITUD_FECHA_FIN_PLANILLA = "PilaBandejaService.SolicitudCambioNumIdentAportante.BusquedaFechaFinPlanilla";
    public static final String BUSQUEDA_SOLICITUD_FECHA_FIN = "PilaBandejaService.SolicitudCambioNumIdentAportante.BusquedaFechaFin";
    public static final String BUSQUEDA_SOLICITUD_PLANILLA = "PilaBandejaService.SolicitudCambioNumIdentAportante.BusquedaPlanilla";

    // HU-399 Busqueda de planillas con inconsistencias
    public static final String CONTAR_PLANILLAS_CON_INCONSISTENCIAS = "PilaBandejaService.PilaIndicePlanilla.contarPlanillasConInconsistencias";
    public static final String CONSULTAR_REGISTRO_APORTE_CON_INCONSISTENCIA = "PilaBandejaService.RegistroDetallado.consultarRegistroAporte";
    public static final String CONSULTAR_REGISTROS_APORTE_PLANILLAS_CON_INCONSISTENCIAS_SIN_FILTRO = "PilaBandejaService.RegistroDetallado.consultarRegistrosAportePlanillasConInconsistenciasSinFiltro";
    public static final String CONSULTAR_REGISTROS_APORTE_PLANILLAS_CON_INCONSISTENCIAS = "PilaBandejaService.RegistroDetallado.consultarRegistrosAportePlanillasConInconsistencias";
    public static final String CONSULTAR_REGISTROS_APORTE_PLANILLAS_CON_INCONSISTENCIAS_SIN_DV = "PilaBandejaService.RegistroDetallado.consultarRegistrosAportePlanillasConInconsistenciasSinDv";
    public static final String CONSULTAR_REGISTROS_APORTE_PLANILLAS_CON_INCONSISTENCIAS_POR_GESTIONAR_SIN_FILTRO = "PilaBandejaService.RegistroDetallado.consultarRegistrosAportePlanillasConInconsistenciasPorGestionarSinFiltro";
    public static final String CONSULTAR_REGISTROS_APORTE_PLANILLAS_CON_INCONSISTENCIAS_POR_GESTIONAR = "PilaBandejaService.RegistroDetallado.consultarRegistrosAportePlanillasConInconsistenciasPorGestionar";
    public static final String CONSULTAR_REGISTROS_APORTE_PLANILLAS_CON_INCONSISTENCIAS_POR_GESTIONAR_SIN_DV = "PilaBandejaService.RegistroDetallado.consultarRegistrosAportePlanillasConInconsistenciasPorGestionarSinDv";
    public static final String CONSULTAR_REGISTROS_DETALLADO_APORTE_POR_REGISTRO_GENERAL = "PilaBandejaService.RegistroDetallado.listarRegistrosAportesPorPlanilla";
    public static final String CONSULTAR_REGISTRO_GENERAL_POR_ID = "PilaBandejaService.RegistroGeneral.consultarRegistroAporteGeneral";
    public static final String CONSULTAR_ESTADO_BLOQUE_PLANILLA_POR_ID_PLANILLA = "PilaBandejaService.EstadoArchivoPorBloque.consultarEstadoArchivoPorBloqueConIdPlanilla";

    // con los 3 argumentos
    public static final String OBTENER_PLANILLAS_EMPLEADOR_I_TODOS_ARGUMENTOS = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIEmpleadorPeriodoPlanilla";
    public static final String OBTENER_PLANILLAS_EMPLEADOR_IP_TODOS_ARGUMENTOS = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIPEmpleadorPeriodoPlanilla";

    // con los numero de planilla y empleador
    public static final String OBTENER_PLANILLAS_EMPLEADOR_I_PLANILLA = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIEmpleadorPlanilla";
    public static final String OBTENER_PLANILLAS_EMPLEADOR_IP_PLANILLA = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIPEmpleadorPlanilla";

    // con periodo aporte y empleador
    public static final String OBTENER_PLANILLAS_EMPLEADOR_I_PERIODO = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIEmpleadorPeriodo";
    public static final String OBTENER_PLANILLAS_EMPLEADOR_IP_PERIODO = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIPEmpleadorPeriodo";

    // con periodo aporte y numero de planilla
    public static final String OBTENER_PLANILLAS_I_PERIODO_PLANILLA = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIPeriodoPlanilla";
    public static final String OBTENER_PLANILLAS_IP_PERIODO_PLANILLA = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIPPeriodoPlanilla";

    // con periodo aporte y numero de planilla
    public static final String OBTENER_PLANILLAS_I_PLANILLA = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIPlanilla";
    public static final String OBTENER_PLANILLAS_IP_PLANILLA = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIPPlanilla";
    /**
     * busqueda de los aportes de una persoinas con los 3 argumentos
     */
    //
    public static final String OBTENER_PLANILLAS_PERSONA_I_TODOS_ARGUMENTOS = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIPersonaPeriodoPlanilla";
    public static final String OBTENER_PLANILLAS_PERSONA_IP_TODOS_ARGUMENTOS = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIPPersonaPeriodoPlanilla";

    // con los numero de planilla y empleador
    public static final String OBTENER_PLANILLAS_PERSONA_I_PLANILLA = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIPersonaPlanilla";
    public static final String OBTENER_PLANILLAS_PERSONA_IP_PLANILLA = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIPPersonaPlanilla";

    // con periodo aporte y empleador
    public static final String OBTENER_PLANILLAS_PERSONA_I_PERIODO = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIPersonaPeriodo";
    public static final String OBTENER_PLANILLAS_PERSONA_IP_PERIODO = "PilaBandejaService.PilaIndicePlanilla.BusquedaConArgumentosIPPersonaPeriodo";

    // consultas para HU 403 y 404
    /**
     * Consultas 403: Buscar empleadores con estado No formalizado – sin afiliación con aportes
     * y No formalizado – retirado con aportes
     */
    public static final String BUSQUEDA_APORTANTES_RETIRADOS_CON_APORTES = "PilaBandejaService.Empleador.BusquedaEmpleadorRetiradoConAportes";
    public static final String BUSQUEDA_APORTANTES_NO_AFILIADOS_CON_APORTES = "PilaBandejaService.Empleador.BusquedaEmpleadorSinAfiliacionConAportes";
    /**
     * Consultas 404: Bandeja empleador cero trabajadores activos
     */
    // Cuando no llegan parametros
    public static final String BUSQUEDA_EMPLEADOR_CERO_TRABAJADORES_ACTIVOS = "PilaBandejaService.Empleador.BusquedaEmpleadorCeroTrabajadoresActivos";
    // Busqueda de los RolAfiliado que han sido retirados por PILA
    public static final String BUSQUEDA_ROL_AFILIADO_RETIRADO_POR_PILA = "PilaBandejaService.Empleador.BusquedaRolAfiliadoRetiradoPorPila";
    // Actualizar la fecha de gestion del empleador
    public static final String ACTUALIZAR_FECHA_GESTION_EMPLEADOR = "PilaBandejaService.Empleador.ActualizarFechaGestionEmpleador";
    // Obtener lista de empleadores a partir de su ID
    public static final String CONSULTAR_EMPLEADORES_LISTA_BANDEJA_GESTIONADA = "PilaBandejaService.Empleador.ConsultarEmpleadoresBandejaGestionada";

    // Busqueda para HU -405
    public static final String BUSQUEDA_ACTUALIZACION_EMPLEADOR_DV_FECHAS = "PilaBandejaService.ActualizacionDatosEmpleador.BusquedaTodosArgumentos";

    public static final String BUSQUEDA_ACTUALIZACION_EMPLEADOR_ID = "PilaBandejaService.ActualizacionDatosEmpleador.BusquedaPorId";

    public static final String CONTEO_INCONSISTENCIAS_ERROR = "PilaBandejaService.PilaErrorValidacionLog.ConteoInconsistencias";
    
    public static final String CONTEO_INCONSISTENCIAS_ERROR_TOTAL = "PilaBandejaService.PilaErrorValidacionLog.ConteoInconsistenciasTotal";
    
    public static final String CONTEO_INCONSISTENCIAS_IDENTIFICACION = "PilaBandejaService.SolicitudCambioNumIdentAportante.ConteoInconsistenciasIdentificacion";

    public static final String VALIDAR_EXISTENCIA_RESPUESTA_SOLICITUD_CAMBIO_ID = "PilaBandejaService.PilaErrorValidacionLog.ValidarRespuestaCambioId";

    //HU-392
    public static final String BUSQUEDA_ID_ERRORES_POR_INDICE = "PilaBandejaService.PilaErrorValidacionLog.BusquedaPorIdIndice";

    public static final String BUSQUEDA_ID_ERRORES_POR_INDICE_OF = "PilaBandejaService.PilaErrorValidacionLog.BusquedaPorIdIndiceOF";

    public static final String CONSULTAR_PERSONA = "Empleador.Persona.consultarPersona";

    public static final String CONSULTAR_MUNICIPIO = "Ubicacion.Municipio.consultarMunicipio";

    public static final String CONSULTAR_DEPARTAMENTO = "Ubicacion.Municipio.consultarDepartamento";

    /**
     * Consultar si hay empresas creadas con una persona en particular
     */
    public static final String BUSCAR_EMPRESA_PERSONA = "PilaBandejaService.CrearAportante.BuscarEmpresaPersona";

    public static final String BUSCAR_EMPRESA = "Empleador.Empresa.consultarEmpresa";

    // HU-389
    /**
     * Consulta con multicriterio para la busqueda por persona (Planillas I)
     */
    public static final String BUSCAR_POR_PERSONA_CRITERIOS = "PilaBandejaService.PilaStaging.BuscarPorPersonaCriterios";

    /**
     * Consulta con multicriterio para la busqueda por persona pensionados (Planillas IP)
     */
    public static final String BUSCAR_POR_PERSONA_PENSIONADO_CRITERIOS = "PilaBandejaService.PilaStaging.BuscarPorPersonaPensionadoCriterios";

    /**
     * Consulta con multicriterio para la busqueda por aportante (Planillas I)
     */
    public static final String BUSCAR_POR_APORTANTE_CRITERIOS = "PilaBandejaService.PilaStaging.BuscarPorAportanteCriterios";
    
    /**
     * Consulta el número total de registros asociados a la consulta 
     */
    public static final String BUSCAR_POR_APORTANTE_CRITERIOS_TOTAL = "PilaBandejaService.PilaStaging.BuscarPorAportanteCriteriosTotal";

    /**
     * Consulta con multicriterio para la busqueda por aportante pensionado (Planillas IP)
     */
    public static final String BUSCAR_POR_APORTANTE_PENSIONADO_CRITERIOS = "PilaBandejaService.PilaStaging.BuscarPorAportantePensionadoCriterios";
    
    /**
     * Consultar el nombre del aportante dado el id de la planilla para archivos I o IR
     */
    public static final String CONSULTAR_NOMBRE_APORTANTE_ARCHIVO_I = "PilaBandejaService.ConsultarAportanteI.PilaArchivoIRegistro1";
    
    /**
     * Consultar el nombre del aportante para archivos IP o IPR
     */
    public static final String CONSULTAR_NOMBRE_APORTANTE_ARCHIVO_IP = "PilaBandejaService.ConsultarAportanteIP.PilaArchivoIPRegistro1";

    /**
     * Solicitud de la actualización de los registros detallados que pasan a estar en estado "_APROBADO"
     * */
    public static final String APROBAR_REGISTROS_POR_ID = "PilaBandejaService.RegistroDetallado.AprobarPorListado";

    /**
     * Solicitud de la asignación de ID de transacción para registros generales
     * */
    public static final String ASIGNAR_ID_TRANSACCION_POR_ID = "PilaBandejaService.RegistroGeneral.AsignarIdTransaccion";

    /**
     * Solicitud de la asignación de ID de transacción para registros generales (Simulación)
     * */
    public static final String ASIGNAR_ID_TRANSACCION_POR_ID_SIMLUACION = "PilaBandejaService.RegistroGeneral.AsignarIdTransaccion.Simulacion";
    
    /**
     * Consulta de registros generales por listado de IDs
     * */
    public static final String CONSULTAR_REGISTROS_GENERALES_LISTA_ID = "PilaBandejaService.RegistroGeneral.ConsultaPorLista";
    
    /**
     * Consulta que lista los datos adicionales de planilla en bandeja de inconsistencias
     * */
    public static final String CONSULTAR_DATOS_ADICIONALES_INCONSISTENCIAS = "PilaBandejaService.IndicePlanilla.datosAdicionalesInconsistencia";
    
    /**
     * Recalculo del estado de archivo de Registro General por reproceso bandeja 399 para planillas manuales
     * */
	public static final String RECALCULAR_ESTADO_ARCHIVO_RG = "PilaBandejaService.RegistroGeneral.recalcularEstadoArchivo";
	
	/**
	 * Consulta de la existencia de una persona en BD
	 * */
    public static final String BUSCAR_EXISTENCIA_PERSONA = "PilaBandejaService.Persona.existenciaPersona";
    
    // Refactor PILA fase 3
    public static final String BUSCAR_BANDEJA_TRANSITORIA_GESTION = "PilaBandejaService.BandejaTransitoriaGestion.BuscarFallido";    
    public static final String BUSCAR_LISTA_INDICE_PLANILLA = "PilaBandejaService.IndicePlanilla.Buscar";
    public static final String CONSULTAR_BANDEJA_TRANSITORIA_GESTION = "PilaBandejaService.BandejaTransitoriaGestion.consultarById";
    public static final String CONSULTAR_TEM_NOVEDAD_POR_REGISTRO_GENERAL = "PilaBandejaService.TemNovedad.ConsultarPorRegistroGeneral";
    public static final String CONSULTAR_EXCEPCION_NOVEDAD_PILA = "PilaBandejaService.ExcepcionNovedadPila.ConsultarPorIdTempNovedad";
    public static final String CONSULTAR_REGISTRO_GENERAL_POR_REGISTRO_CONTROL = "PilaBandejaService.RegistroGeneral.consultarByRegistroControl";
    public static final String ACTUALIZAR_ESTADO_BANDEJA_TRANSITORIA = "PilaBandejaService.BandejaTransitoriaGestion.actualizarEstadoBandejaTransitoria";
    public static final String ACTUALIZAR_ESTADO_EN_PROCESO_APORTES = "PilaBandejaService.BandejaTransitoriaGestion.actualizarEstadoEnProcesoAportes";
    public static final String BUCAR_ESTADO_INDICE_PLANILLA_B2 = "PilaBandejaService.BandejaTransitoriaGestion.consultarEstadoIndcePlanillaB2";
    /**
     * Constante para la consulta de bandeja transitoria de pila con parametros de busqueda
     */
    public static final String BUSCAR_BANDEJA_TRANSITORIA_GESTION_PARAM = "USP_GET_BandejaTransitoriaGestion";
}
