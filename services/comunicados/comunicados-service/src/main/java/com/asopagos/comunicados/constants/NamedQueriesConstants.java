package com.asopagos.comunicados.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class NamedQueriesConstants {

    // HU-TRA-331 Consulta las variables comunicado, entity "VariableComunicado"
    public static final String CONSULTAR_PLANTILLA_COMUNICADO = "Comunicados.buscar.plantilla";

    // HU-TRA-331 Consulta las variables comunicado, entity "VariableComunicado"
    public static final String CONSULTAR_VARIABLES_COMUNICADO = "Comunicados.buscar.variable";

    // Consulta una solicitud global por el id de la instancia de proceso
    public static final String CONSULTAR_SOLICITUD = "Comunicados.buscar.solicitud.instanciaProceso";
    /**
     * Nombre de la query que consulta un comunicado por id.
     */
    public static final String CONSULTAR_COMUNICADO_POR_ID = "Comunicados.Comunicado.buscarPorId";

    /**
     * Consultar los comunicados por proceso
     */
    public static final String CONSULTAR_COMUNICADO_POR_PROCESO = "comunicados.buscar.comunicado.por.proceso";

    /**
     * Notificación de pago de solicitud de devolución de aportes Comunicado 111
     */
    public static final String CONSULTA_COMUNICADO_SOL_DEV_APORTES = "comunicados.solicitud.devolucion.aportes";

    /**
     * Constante que representa la consulta de un comunicado cuando se hace por id de solicitud.
     */
    public static final String CONSULTAR_COMUNICADO_POR_ID_SOLICITUD = "Comunicados.Buscar.Comunicado.id.Solicitud";

    /**
     * Constante que reprensenta la consulta de los datos temporales de un comunicado dado un id de tarea
     */
    public static final String CONSULTAR_DATO_TEMPORAL_COMUNICADO = "Comunicados.consultar.datoTemporalComunicado";

    /**
     * Consulta para el comunicado 102 ACT_ASIG_FOVIS
     */
    public static final String CONSULTA_FOVIS_ACTA_ASIGNACION = "comunicados.solicitud.fovis.actaAsignacion";

    /**
     * Consulta para la tabla de cruce de informacion para el comunicado 102 ACT_ASIG_FOVIS
     */
    public static final String CONSULTA_FOVIS_ACTA_ASIGNACION_TABLA_CRUCE_INFORMACION = "comunicados.solicitud.fovis.actaAsignacion.tablaCruceInformacion";

    /**
     * Consulta para la tabla hogares asignados para el comunicado 102 ACT_ASIG_FOVIS
     */
    public static final String CONSULTA_FOVIS_ACTA_ASIGNACION_TABLA_HOGARES_ASIGNADOS = "comunicados.solicitud.fovis.actaAsignacion.tablaHogaresAsignados";

    /**
     * Consulta para la tabla hogares asignados para el comunicado 102 ACT_ASIG_FOVIS
     */
    public static final String CONSULTA_FOVIS_ACTA_ASIGNACION_TABLA_MODALIDAD = "comunicados.solicitud.fovis.actaAsignacion.tablaModalidad";

    /**
     * Consulta para el comunicado 103 CRT_ASIG_FOVIS
     */
    public static final String CONSULTA_FOVIS_CARTA_ASIGNACION = "comunicados.solicitud.fovis.cartaAsignacion";

    /**
     * Consulta para el comunicado 103 CRT_ASIG_FOVIS tabla carta de asignacion
     */
    public static final String CONSULTA_FOVIS_TABLA_CARTA_ASIGNACION = "comunicados.solicitud.fovis.actaAsignacion.tablaCartaAsignacion";

    /**
     * Consulta para generar la tabla de la carta de la entidad pagadora 1.2.1
     */
    public static final String CONSULTA_TABLA_CARTA_ENTIDAD_PAGADORA = "tabla.CartaEntidadPagadora";

    /**
     * Consulta el modelo de cartera por medio del identificador de la solicitud
     */
    public static final String CONSULTA_CARTERA = "Comunicados.cartera.solicitud";

    /**
     * Consulta los datos de la tabla para el comunicado NTF_INT_NO_CBR
     */
    public static final String CONSULTA_COMUNICADO_NTF_INT_NO_CBR = "comunicados.notificacion.interna";

    /**
     * Consulta los periodos de cartera que se encuentran en mora
     */
    public static final String CONSULTA_CARTERA_PERIODOS = "Comunicados.consulta.cartera.periodos";
        /**
     * Consultar grupo familiar 
     */
    public static final String CONSULTA_GRUPO_FAMILIAR = "Comunicados.consulta.grupo.familiar";

    public static final String OBTENER_INCONSISTENCIAS_EMPLEADOS = "Comunicados.consulta.inconsistencias.empleados";

    /**
     * Consulta el periodo y el valor de la deuda para el proceso 223 de Cartera
     */
    public static final String CONSULTA_COMUNICADO_COBRO_PERSUASIVO = "Comunicados.consulta.cobro.persuasivo";

    /**
     * Consulta la datos de la persona en la entidad cartera
     */
    public static final String CONSULTA_PERSONA_CARTERA = "Comunicados.consulta.cartera";

    /**
     * Consulta los datos para la tabla del comunicado Notificación por cancelación de publicación en edicto para gestión de cobro de
     * aportantes
     * perteneciente al proceso de cartera
     */
    public static final String CONSULTA_COMUNICADO_NTF_CCL_PUB_EDC = "comunicados.publicacion.edicto";

    /**
     * Consulta los campos del nombre del administrador del subsidio, el número de identificación, tipo de identificación y valor del retiro
     * para mostrarlo al comunicado generado en la HU-31-202 proceso de Pagos subsidio monetario.
     */
    public static final String CONSULTAR_CAMPOS_COMUNICADO_RETIRO_POR_VENTANILLA_PROCESO_DE_PAGOS = "Comunicados.consulta.pagosSubsidioMonetario.camposAdmin.retiro.202";

    /**
     * Consulta los campos de los empleadores relacionados con el retiro efectuado por ventanilla en la HU-31-202 proceso de Pagos subsidio
     * monetario.
     * en el proceso de Pagos subsidio monetario.
     */
    public static final String CONSULTAR_EMPLEADORES_COMUNICADO_RETIRO_POR_VENTANILLA_PROCESO_DE_PAGOS = "Comunicados.consulta.pagosSubsidioMonetario.empleadores.retiro.202";

    /**
     * Consulta la persona autorizada para realizar el retiro que se efectuó por ventanilla en la HU-31-202 proceso de Pagos subsidio
     * monetario.
     */
    public static final String CONSULTAR_PERSONA_AUTORIZADA_RETIRO_POR_VENTANILLA_PROCESO_DE_PAGOS = "Comunicados.consulta.pagosSubsidioMonetario.personaAutorizada.retiro.202";

    /**
     * Consulta los campos de las inconsistencias encontradas en el procesamiento de un archivo de consumos indicado en:
     * ANEXO-Validacion y cargue archivo consumos_validado V.2
     */
    public static final String CONSULTAR_DETALLE_INCONSISTENCIAS_ARCHIVO_CONSUMOS_PROCESO_DE_PAGOS = "Comunicados.consulta.pagosSubsidioMonetario.archivoConsumo.inconsistencias.detalle";

    /**
     * Consulta el listado de beneficiarios por trabajador comunicado 64
     */
    public static final String CONSULTAR_BENEFICIARIOS_NOTIFICACION_DISPERSION_PAGOS_TRABAJADOR = "Comunicados.consulta.pagosSubsidioMonetario.masivo.dispersion.pagos.trabajador";

    /**
     * Consulta el listado de beneficiarios por trabajador comunicado 65
     */
    public static final String CONSULTAR_BENEFICIARIOS_NOTIFICACION_DISPERSION_PAGOS_ADMIN = "Comunicados.consulta.pagosSubsidioMonetario.masivo.dispersion.pagos.administrador";

    /**
     * Consulta el listado de periodos y valores de pago
     */
    public static final String CONSULTAR_DISPERSION_PAGOS_FALLECIMIENTO = "Comunicados.consulta.pagosSubsidioMonetario.fallecimeinto.dispersion.pagos";
    
    public static final String CONSULTAR_NOTIFICACIONES_PERSONA = "Comunicados.consulta.notificaciones.persona";

    /**
     * Consulta los certificados generados por los filtros dados
     */
    public static final String CONSULTAR_CERTIFICADOS = "Certificados.consultar";

    /**
     * Consulta si la persona posee una afiliacion activa por el tipo de afiliación enviado
     */
    public static final String CONSULTAR_AFILIACION_ACTIVA_CAJA = "Certificados.consulta.AfiliacionActivoACaja";

    /**
     * Consulta si la persona posee una afiliacion cerrada por el tipo de afiliación enviado
     */
    public static final String CONSULTAR_AFILIACION_CERRADA_CAJA = "Certificados.consulta.AfiliacionCerradaACaja";

    /**
     * Procedimiento Almacenado que consulta si una persona o empleador estuvo activo en algun momento 
     */
    public static final String CONSULTAR_HISTORICO_AFILIACIONES = "Certificados.consulta.historico.afiliacion";
    
    /**
     * Procedimiento Almacenado que consulta las fechas de ingreso y retiro de un empleador
     */
    public static final String CONSULTA_HISTORICO_AFILIACIONES_EMPLEADOR = "Certificados.consulta.historico.afiliacion.empleador";
    
    /**
     * Procedimiento Almacenado que consulta las fechas de ingreso y retiro de un empleador
     */
    public static final String CONSULTA_HISTORICO_AFILIACIONES_PERSONA = "Certificados.consulta.historico.afiliacion.persona";
    
    /**
     * Procedimiento Almacenado que consulta las fechas de ingreso y retiro de un empleador
     */
    public static final String CONSULTA_CERTIFICADO_POR_ID = "Certificados.consultar.idCertificado";
    
    /**
     * Procedimiento Almacenado que consulta el estado de un aportante empleador
     */
    public static final String CONSULTA_CERTIFICADO_APORTANTE_EMPLEADOR = "Certificados.consultar.estado.aportante.empleador";
    
    /**
     * Procedimiento Almacenado que consulta el estado de un aportante persona
     */
    public static final String CONSULTA_CERTIFICADO_APORTANTE_PERSONA = "Certificados.consultar.estado.aportante.persona";
    
    /**
     * Constante que representa la consulta de un comunicado cuando se hace por número de radicado
     */
    public static final String CONSULTAR_COMUNICADO_POR_RADICADO = "Comunicados.Buscar.Comunicado.Radicado";
    
    /**
     * Constante que representa la consulta de un comunicado cuando se hace por etiqueta y idSolicitud
     */
    public static final String CONSULTAR_COMUNICADO_POR_ETIQUETA_IDSOLICITUD = "Comunicados.consulta.certificado";

    /**
     * Constante que representa la consulta de los historicos de afiliación para un empleador
     */
    public static final String CONSULTAR_HISTORICO_AFILIACION_EMPLEADOR = "Certificados.consultar.historicoAfiliacionEmpleador";
    
    /**
     * Constante que representa la consulta de los historicos de afiliación para un trabajador dependiente
     */
    public static final String CONSULTAR_HISTORICO_AFILIACION_DEPENDIENTE = "Certificados.consultar.historicoAfiliacionDependiente";

    /**
     * Constante que representa la consulta de los historicos de afiliación para un independiente
     */
    public static final String CONSULTAR_HISTORICO_AFILIACION_INDEPENDIENTE = "Certificados.consultar.historicoAfiliacionIndependiente";
    
    /**
     * Constante que representa la consulta de los historicos de afiliación para un pensionado
     */
    public static final String CONSULTAR_HISTORICO_AFILIACION_PENSIONADO = "Certificados.consultar.historicoAfiliacionPensionado";

    /**
     * Constante que representa la consulta de los historicos de estado para un empleador
     */
    public static final String CONSULTAR_HISTORICO_ESTADOS_EMPLEADOR = "Historicos.consultar.USP_ExecuteConsultarHistoricoEstadoAportante";


    /**
     * Constante que representa la consulta de ls datos de identificación del empleador, info necesaria en el proceso de
     * consulta de histórico de afiliación
     */
    public static final String OBTENER_DATOS_ID_EMPLEADOR = "Comunicados.Obtener.datosIdentificacionEmpleadorV360Persona";

    /**
     * Constate que representa la consulta del histórico de afiliación de un afiliado
     */
    public static final String CONSULTAR_HISTORICO_AFILIACION = "Comunicados.Consultar.USP_ExecuteConsultarHistoricoAfiliacionPersona";
    
    /**
     * Constate que representa la consulta de los certificados de una persona o empleador
     */
    public static final String CONSULTAR_CERTIFICADO_EMPLEADOR_PERSONA = "Certificados.consulta.empleador_persona";

	/**
	 * Constante con el nombre de la consulta que obtiene la deuda de un aportante, filtrando por identificador de cartera
	 */
	public static final String CONSULTA_DEUDA_CARTERA_IDENTIFICADOR = "Comunicados.consultar.deuda.cartera.identificador";
	
	/**
     * Constante que representa la consulta de un comunicado cuando se hace por etiqueta y idCertificado
     */
    public static final String CONSULTAR_COMUNICADO_POR_ETIQUETA_IDCERTIFICADO = "Comunicados.consulta.certificado.idCertificado";
    
    /**
     * Constante que representa la consulta del id de solicitud dado el numero de radicado
     */
    public static final String CONSULTAR_ID_SOLICITUD = "Comunicados.consulta.idSolicitud";
    
    /**
     * Consulta el estado de la cartera, validando el estado Moroso o Al Dia
     */
    public static final String CONSULTAR_ESTADO_CARTERA = "Certificados.consulta.estadoCartera";
    

    /**
     * Constante que representa la consulta del id de una persona
     */
    public static final String CONSULTAR_ID_PERSONA = "Certificados.consulta.idPersona";
    
    /**
     * Constante que representa la consulta de validación del estado de afiliación de la persona como dependiente en la bdat de reportes
     */
    public static final String VALIDAR_ESTADO_AFILIADO_DEPENDIENTE = "Certificados.consulta.validarEstadoAfiliadoDependiente";
    
    /**
     * Constante que representa la consulta de registro del estado de afiliación actual 
     */
    public static final String REGISTRAR_ESTADO_AFILIADO_DEPENDIENTE = "Certificados.insertar.registrarEstadoAfiliadoDependiente";
    
    /**
     * Constante que representa la consulta de validación del estado de afiliación de la persona como independiente en la bdat de reportes
     */
    public static final String VALIDAR_ESTADO_AFILIADO_INDEPENDIENTE = "Certificados.consulta.validarEstadoAfiliadoIndependiente";
    
    
    public static final String REGISTRAR_ESTADO_AFILIADO_INDEPENDIENTE = "Certificados.insertar.registrarEstadoAfiliadoIndependiente";
    
    /**
     * Constante que representa la consulta de validación del estado de afiliación de la persona como pensionado en la bdat de reportes
     */
    public static final String VALIDAR_ESTADO_AFILIADO_PENSIONADO = "Certificados.consulta.validarEstadoAfiliadoPensionado";
    
    
    public static final String REGISTRAR_ESTADO_AFILIADO_PENSIONADO = "Certificados.insertar.registrarEstadoAfiliadoPensionado";

    /**
     * constante que representa la consulta del estado actual de afiliación de una persona dependiente
     */
    public static final String CONSULTAR_ESTADO_ACTUAL_AFI_DEP_CORE = "Certificados.consulta.estadoActualAfiliadoDependienteCore";
    
    /**
     * constante que representa la consulta del estado actual de afiliación de una persona independiente
     */
    public static final String CONSULTAR_ESTADO_ACTUAL_AFI_INDEP_CORE = "Certificados.consulta.estadoActualAfiliadoIndependienteCore";
    
    /**
     * constante que representa la consulta del estado actual de afiliación de una persona pensionada
     */
    public static final String CONSULTAR_ESTADO_ACTUAL_PENSI_CORE = "Certificados.consulta.estadoActualPensionadoCore";
    
    /**
     * constante que representa la consulta del estado actual de afiliación de un empleador
     */
    public static final String CONSULTAR_ESTADO_ACTUAL_EMPLEADOR_CORE = "Ceritifcados.consulta.estadoActualEmpleadorCore";
    
    public static final String OBTENER_ESTADO_ACTUAL_EMPLEADOR_REPORTES = "Certificados.consulta.validarEstadoActualEmpleadorReportes";
    
    public static final String ACTUALIZAR_HISTORICO_ESTADO_EMPLEADOR = "Certificados.insertar.actualizarHistoricoEstadoEmpleador";
    
    public static final String CONSULTAR_HISTORICO_AFILIACION_EMPLEADOR_CORE = "Consultar.HistoricoAfiliacionEmpleadorEnCore";
    
    public static final String CONSULTAR_HISTORICO_ESTADOS_EMPLEADOR_CORE = "Consultar.HistoricoEstadosEmpleadorCore";
    /**
     * Consulta las notificaciones enviadas desde el proceso de PILA asociadas a una persona
     */
    public static final String CONSULTAR_NOTIFICACIONES_PILA_PERSONA = "Comunicados.consulta.notificaciones.pila.persona";
    
    public static final String CONSULTAR_HISTORICO_ESTADOS_EMPRESA_CORE = "Consultar.Historico.estadosEmpresaCore";
    
    public static final String CONSULTAR_HISTORICO_ESTADOS_PERSONA_CORE = "Consultar.Historico.estadosPersonaCore";

    /**
     * Consulta la etiqueta y el asunto de los comunicados de los que ha sido objeto el solicitante
     */
	public static final String CONSULTAR_ETIQUETA_COMUNICADOS_ENVIADOS_PERSONA = "Consultar.etiqueta.comunicados.enviados.persona";
	
	/**
     * Consulta la etiqueta y el asunto de los comunicados de los que ha sido objeto el solicitante
     */
	public static final String CONSULTAR_ETIQUETA_COMUNICADOS_ENVIADOS_EMPLEADOR = "Consultar.etiqueta.comunicados.enviados.empleador";
	
	/**
     * Consulta la etiqueta y el asunto de los comunicados de los que ha sido objeto el solicitante
     */
	public static final String CONSULTAR_COMUNICADOS_ENVIADOS_PERSONA = "Consultar.comunicados.enviados.persona";
	
	/**
     * Consulta la etiqueta y el asunto de los comunicados de los que ha sido objeto el solicitante
     */
	public static final String CONSULTAR_COMUNICADOS_ENVIADOS_EMPLEADOR = "Consultar.comunicados.enviados.empleador";
	
	/**
     * Consulta las notificaciones enviadas desde el proceso de PILA asociadas a un empleador
     */
    public static final String CONSULTAR_NOTIFICACIONES_PILA_EMPLEADOR = "Comunicados.consulta.notificaciones.pila.empleador";
    
    /**
     * Consulta el total de los aportes realizados por un empleador en un año determinado.
     */
    public static final String CONSULTA_TOTAL_APORTES_EMPLEADOR = "Certificados.totalAportes.empleador";
    
    /**
     * Consulta el total de los aportes realizados por una persona en un año determinado.
     */
    public static final String CONSULTA_TOTAL_APORTES_PERSONA = "Certificados.totalAportes.persona";
    
    /**
     * Consulta la dirección de correo electronico asociada el usuario que radica la solicitud.
     */
    public static final String CONSULTAR_CORREO_FRONT = "Comunicados.consulta.CorreoFront";
    
    /**
     * Consulta el estado de la solicitud de novedad
     */
    public static final String CONSULTAR_ESTADO_SOLICITUD_NOVEDAD = "Comunicados.consulta.estadoSolicitudNovedad";
    
    /**
     * Consulta el número de comunicados enviados asociados a la solicitud.
     */
    public static final String CONSULTAR_NUMERO_COMUNICADOS_SOLICITUD = "Comunicados.consulta.numeroComunicadosSolicitud";
    
    /**
     * Consulta el número de comunicados enviados asociados a la solicitud de afiliación de empleadores.
     */
    public static final String CONSULTAR_NUMERO_COMUNICADOS_SOLICITUD_AFI_EMP = "Comunicados.consulta.numeroComunicadosSolicitudAfiEmp";
    
    /**
     * Consulta el número de comunicados enviados asociados a la solicitud de novedad de empleadores.
     */
    public static final String CONSULTAR_NUMERO_COMUNICADOS_SOLICITUD_NOV_EMP = "Comunicados.consulta.numeroComunicadosSolicitudNovEmp";
    
    /**
     * Consulta el número de comunicados enviados asociados a la solicitud de novedad de personas.
     */
    public static final String CONSULTAR_NUMERO_COMUNICADOS_SOLICITUD_NOV_PER = "Comunicados.consulta.numeroComunicadosSolicitudNovPer";
    
    /**
     * Consulta el estado de la solicitud de afiliación
     */
    public static final String CONSULTAR_ESTADO_SOLICITUD_AFILIACION = "Comunicados.consulta.estadoSolicitudAfiliacion";
    
    /**
     * Consulta el estado de la solicitud de devolución
     */
    public static final String CONSULTAR_ESTADO_SOLICITUD_DEVOLUCION = "Comunicados.consulta.estadoSolicitudDevolucion";
    
    /**
     * Consulta el estado de la solicitud de correción
     */
    public static final String CONSULTAR_ESTADO_SOLICITUD_CORRECCION = "Comunicados.consulta.estadoSolicitudCorrecion";
    
    /**
     * Consulta el estado de la solicitud de correción
     */
    public static final String CONSULTAR_ESTADO_SOLICITUD_APORTE_MANUAL = "Comunicados.consulta.estadoSolicitudAporteManual";
    
    /**
     * Consulta el json asociado a la solicitud de aporte y si la misma tiene detalle
     */
    public static final String CONSULTAR_JSON_DETALLE_SOLICITUD_APORTE_MANUAL = "Comunicados.consulta.jsonDetalleSolicitudAporteManual";
    
    /**
     * Consulta la persona asociada a un Id
     */
    public static final String CONSULTAR_PERSONA = "Notificaciones.consultarPersona";
    
    /**
     * Consulta el estado del empleador
     */
    public static final String CONSULTAR_ESTADO_EMPLEADOR = "Certificados.consultar.estadoEmpleador";

    /**
     * Consulta el estado de un trabajador dependiente
     */
    public static final String CONSULTAR_ESTADO_DEPENDIENTE = "Certificados.consultar.estadoDependiente";
    
    /**
     * Consulta el estado de un trabajador independiente, pensionado
     */
    public static final String CONSULTAR_ESTADO_INDEPENDIENTE_PENSIONADO = "Certificados.consultar.estadoIndependientePensionado";
    
    /**
     * Obtiene el reporte formateado del comunicado consolidado de cartera
     */
    public static final String PROCEDURE_USP_EXECUTE_PREGENERAR_CONSOLIDADO_CARTERA = "Comunicados.USP_UTIL_PregenerarComunicadosConsolidadoCartera";
    
    /**
     * Consulta el tipo de accion de cobro asociado a una solicitud
     */
    public static final String CONSULTA_TIPO_ACCION_COBRO = "Comunicados.consulta.accionCobroSolicitud";
    
    /**
     * Consulta las planillas resueltas para el comunicado consoliddo cartera
     */
    public static final String CONSULTAR_COMUNICACION_RESUELTA_CONSOLIDADO_CARTERA = "Comunicados.consulta.comunicacionResueltaConsolidadoCartera";
    
    /**
     * Consulta el estado del empleador
     */
    public static final String CONSULTAR_ESTADO_SOLICITUD_AFILIACION_EMPLEADOR = "Comunicados.consulta.estadoSolicitudAfiliacionEmpleador";
    
    /**
     * Consulta el estado de la solicitud de novedad
     */
    public static final String CONSULTAR_ESTADO_SOLICITUD_NOVEDAD_EMPLEADOR = "Comunicados.consulta.estadoSolicitudNovedadEmpleador";
    
    /**
     * Constante que reprensenta la consulta de los datos temporales de un comunicado dado un id de tarea o un id instancia proceso
     */
    public static final String CONSULTAR_DATO_TEMPORAL_COMUNICADO_ID_TAREA_O_ID_INSTANCIA_PROCESO = "Comunicados.consultar.datoTemporalComunicado.idTarea.idInstanciaProceso";

    public static final String CONSULTAR_DATO_TEMPORAL_COMUNICADO_ID_INSTANCIA_PROCESO = "Comunicados.consultar.datoTemporalComunicado.idInstanciaProceso";

    public static final String CONSULTA_CARTERA_PERSONA_COMUNICADO = "Comunicados.consulta.cartera.persona";
	
    public static final String PROCEDURE_HISTORICO_AFILIADO = "Comunicados.SP_historicoAfiliacionPersona";
}
