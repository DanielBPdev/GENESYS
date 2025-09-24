package com.asopagos.aportes.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * Clase que contiene las constantes con los nombres de los NamedQueries del
 * servicio <b>Módulo:</b> Asopagos - HU <br/>
 * Transversal
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
/**
 * @author squintero
 *
 */
public class NamedQueriesConstants {
    /**
     * Constante con el nombre de la query para consultar una solicitud de
     * aporte por id de solicitud global.
     */
    public static final String CONSULTAR_SOLICITUD_APORTE_ID = "Consultar.SolicitudAporte.Id";

    /**
     * Constante con el nombre del query para consultar un aporte detallado por
     * el identificador de aporte detallado
     */
    public static final String CONSULTAR_APORTE_DETALLADO = "AporteDetallado.Consultar.aporte.aporte.detallado";

    /**
     * Constante con el nombre del query para consultar por el identificador de
     * solicitud la información faltante del aportantes.
     */
    public static final String CONSULTAR_INFORMACION_FALTANTE_ID = "Aportes.InformacionFaltanteAportante.buscarPorIdSolicitud";

    /**
     * Constante que consulta todos los departamentos
     */
    public static final String CONSULTAR_DEPARTAMENTOS = "Aportes.consulta.departamentos";

    /**
     * Constante que consulta todos los municipio
     */
    public static final String CONSULTAR_MUNICIPIOS = "Aportes.consulta.municipios";

    /**
     * Procedimiento almacenado que realiza la simulación de aportes.
     */
    public static final String PROCEDURE_ASP_VALIDAR_PROCESADO_NOVEDADES = "Aportes.StoredProcedures.ASP_ValidarProcesadoNovedades";

       /**
     * Procedimiento almacenado que realiza la simulación de aportes.
     */
    public static final String PROCEDURE_ASP_PROCESADO_NOVEDADES = "Aportes.StoredProcedures.ASP_ProcesadoNovedades";

    /**
     * Constante que consulta un solicitante cuando este es independiente o un pensionado.
     */
    public static final String CONSULTAR_SOLICITANTE_INDEPENDIENTE_PENSIONADO = "Consultar.Solicitante.Independientes.Pensionados";

    /**
     * Constante que consulta un solicitante
     */
    public static final String CONSULTAR_SOLICITANTE_EMPLEADOR = "Consultar.Solicitante.Empleador";

    /**
     * Constante que contiene el nombre de la consulta: buscar registro general
     * por id de solicitud..
     */
    public static final String CONSULTAR_REGISTRO_GENERAL_POR_ID_SOLICITUD = "Aportes.RegistroGeneral.consultarPorIdSolicitud";
    
    /**
     * Constante que contiene el nombre de la consulta: buscar registro general
     * por id de solicitud..
     */
    public static final String CONSULTAR_REGISTRO_GENERAL_POR_ID_SOLICITUD_ID_REG_GEN = "Aportes.RegistroGeneral.consultarPorIdSolicitudIdRegistroGeneral";
    
    /**
     * Constante que contiene el nombre de la consulta: buscar registro
     * detallado por id registro general.
     */
    public static final String CONSULTAR_REGISTRO_DETALLADO_POR_ID_REGISTRO_GENERAL = "Aportes.RegistroGeneral.consultarPorIdRegistroGeneral";
    /**
     * Constante que contiene el nombre de la consulta: buscar registro
     * detallado por id registro general y tipo de afiliado.
     */
    public static final String CONSULTAR_REGISTRO_DETALLADO_POR_ID_REGISTRO_GENERAL_TIPO_AFILIADO = "Aportes.RegistroGeneral.consultarPorIdRegistroGeneral.tipoAfiliado";

    /**
     * Constante que contiene el nombre del procedimiento almacenado para la creación del staging.
     */
    public static final String PROCEDURE_USP_EXECUTE_BLOQUE_STAGING = "Aportes.StoredProcedures.USP_ExecuteBloqueStaging";

    /**
     * Constante que contiene el nombre del procedimiento almacenado para el
     * borrado staging.
     */
    public static final String PROCEDURE_USP_DELETE_BLOQUE_STAGING = "Aportes.StoredProcedures.USP_DeleteBloqueStaging";

    /**
     * Constante encargada de consultar los nomrbes de los campos parametrizados
     */
    public static final String BUSCAR_CAMPOS_ARCHIVO = "Aportes.buscar.file.nombreCampos";

    /**
     * Constante que consulta la planilla
     */
    // public static final String CONSULTAR_PLANILLA = "Consultar.planilla";

    /**
     * constante encargada de consultar la información completa de la planilla (cuenta)
     */
    public static final String CONSULTAR_PLANILLA_COMPLETA_CUENTA = "Consultar.datos.completos.planilla.cuenta";

    /**
     * constante encargada de consultar la información completa de la planilla
     */
    public static final String CONSULTAR_PLANILLA_COMPLETA = "Consultar.datos.completos.planilla";

    /**
     * constante que contiene el nombre del procedimiento almacenado para
     * obtener la información relevente al comunicado post aporte PILA para
     * DEPENDIENTE
     */
    public static final String NOTIFICACION_APORTES_DEP = "Aportes.StoredProcedures.USP_GetNotificacionesRegistro";

    /**
     * constante que contiene el nombre del procedimiento almacenado para
     * obtener la información relevente al comunicado post aporte PILA para
     * INDEPENDIENTE y PENSIONADO
     */
    public static final String NOTIFICACION_APORTES_INDEP_PENS = "Aportes.StoredProcedures.USP_GetNotificacionesRegistroEspecial";

    /**
     * Constante con el nombre de la query para consultar una solicitud de
     * aporte por tipo y número de identificación, obtentiendo así la
     * información necesaria para saber si existe una solicitud de aporte para
     * un aportante que aún no esté cerrada.
     */
    public static final String CONSULTAR_SOLICITUD_APORTE_POR_TIPO_IDENTI_Y_NUM_IDENTI = "Consultar.SolicitudAporte.TipoIdenti.NumIdenti";

    /**
     * 
     */
    public static final String CONSULTAR_OPERADOR_INFORMACION_POR_CODIGO = "consultar.operador.informacion.por.codigo";

    /**
     * Consulta de operador de información por listado de códigos 
     */
    public static final String CONSULTAR_OPERADOR_INFORMACION_POR_CODIGO_MASIVO = "consultar.operador.informacion.por.codigo.masivo";

    /**
     * Procedimiento almacenado que realiza la simulación de aportes.
     */
    public static final String PROCEDURE_USP_EXECUTE_PILA_2_FASE_1_VALIDACION = "Aportes.StoredProcedures.USP_ExecutePILA2Fase1Validacion";

    /**
     * Procedimiento almacenado que registra o relaciona un aporte.
     */
    public static final String PROCEDURE_USP_EXECUTE_PILA_2_FASE_2 = "Aportes.StoredProcedures.USP_ExecutePILA2Fase2RegistrarRelacionarAportes";

    /**
     * Procedimiento almacenado que registra o relaciona las novedades.
     */
    public static final String PROCEDURE_USP_EXECUTE_PILA_2_FASE_3 = "Aportes.StoredProcedures.USP_ExecutePILA2Fase3RegistrarRelacionarNovedades";

    /**
     * Procedimiento almacenado que registra o relaciona las novedades.
     */
    public static final String PROCEDURE_USP_ValidarNovedadesEmpleadorActivoSUCURSALES= "Aportes.StoredProcedures.USP_ValidarNovedadesEmpleadorActivoSUCURSALES";

    /**
     * Consulta que obtiene el historico de novedades.
     */
    public static final String CONSULTAR_HISTORICO_NOVEDADES = "Aportes.Novedad.consultarHistorico";

    /**
     * Consulta que obtiene el historico de novedades de retiro.
     */
    public static final String CONSULTAR_HISTORICO_NOVEDADES_RETIRO = "Aportes.Novedad.consultarHistoricoRetiro";

    /**
     * Se consulta la persona con su tipo de solicitante relacionada al aporte
     * general
     */
    public static final String CONSULTAR_PERSONA_SOLICITANTE_APORTE_GENERAL = "Aportes.aporte.general.consultar.persona.solicitante";

    /**
     * Se consulta la persona con su tipo de solicitante relacionada al porte
     * general y a la empresa
     */
    public static final String CONSULTAR_PERSONA_EMPRESA_SOLICITANTE_APORTE_GENERAL = "Aportes.aporte.general.consultar.persona.empresa.solicitante";

    /**
     * Se consulta el aporte en la temporal por el id de transacción.
     */
    public static final String CONSULTAR_APORTE_TEMPORAL = "Aportes.consultarAporteTemporal";
    /**
     * Se consulta el registro general por su identificador
     */
    public static final String CONSULTAR_REGISTRO_GENERAL_POR_ID = "Aportes.Registro.General.Id";

    /**
     * Se consulta el indice planilla por número de planilla.
     */
    public static final String CONSULTAR_INDICE_PLANILLA = "Aportes.consultar.indice.planilla";

    public static final String CONSULTAR_INDICE_PLANILLA_NUMERO_APORTANTE = "Aportes.consultar.indice.planilla.aportante";


    /**
     * Consulta para obtener <i><b>un cotizante</i></b> cuando se busca por
     * idPersona, <i><b>con al menos un parámetro de búsqueda</b></i> durante un
     * proceso de devolución de aportes.
     */
    public static final String CONSULTAR_COTIZANTES_POR_ID_APORTE = "Consultar.Cotizantes.Por.idAporte";
    /**
     * Consulta para obtener <i><b>un cotizante</i></b> cuando se busca por
     * idPersona, <i><b>con al menos un parámetro de búsqueda</b></i> durante un
     * proceso de devolución de aportes.
     */
    public static final String CONSULTAR_COTIZANTES_POR_ID_APORTE_EMPRESA = "Consultar.Cotizantes.Por.idAporte.empresa";
    /**
     * Consulta para obtener <i><b>los cotizantes</i></b> cuando <i><b>no se
     * ingresan parámetros de búsqueda</b></i> durante un proceso de devolución
     * de aportes.
     */
    public static final String CONSULTAR_COTIZANTES_SIN_PARAMETROS = "Consultar.Cotizantes.Sin.Parametros";
    /**
     * Consulta para obtene los cotizantes cuando no se ingresan parámetros de
     * búsqueda durante un proceso de devolución de aportes y su aportante sea
     * una empresa.
     */
    public static final String CONSULTAR_COTIZANTES_SIN_PARAMETROS_EMPRESA = "Consultar.Cotizantes.Sin.Parametros.Empresa";

    /**
     * Constante con el nombre de la consulta que obtiene un registro de la
     * tabla <code>PilaIndicePlanilla</code>, por número de planilla
     */
    public static final String CONSULTAR_INDICE_PLANILLA_NUMERO = "Aportes.Consultar.IndicePlanilla";

    /**
     * Constante con el nombre de la consulta que obtiene un registro de la
     * tabla <code>SolicitudAporte</code>, por identificador del aporte general
     */
    public static final String CONSULTAR_SOLICITUD_APORTE_ID_APORTE = "Aportes.Consultar.SolicitudAporte.IdAporteGeneral";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de novedades,
     * aplicadas y no aplicadas, para un cotizante por identificador del aporte
     */
    public static final String CONSULTAR_NOVEDADES_COTIZANTE_APORTE = "Aportes.Consultar.Novedades.Cotizante.Aporte";

    /**
     * Constante con el nombre de la consulta que obtiene una solicitud de
     * devolución de aportes, por identificador de la solicitud global asociada
     */
    public static final String CONSULTAR_SOLICITUD_DEVOLUCION_APORTE_ID = "Aportes.Consultar.SolicitudDevolucionAporte.IdSolicitudGlobal";

    /**
     * Se consulta el movimiento de un aporte por su identificador
     */
    public static final String CONSULTAR_MOVIMIENTO_APORTE_ID = "Aportes.consultar.movimiento.aporte.id";

    /**
     * Constante con el nombre de la consulta que obtiene la información de una
     * solicitud global por identificador
     */
    public static final String CONSULTAR_SOLICITUD = "Aportes.Consultar.Solicitud";

    /**
     * Consulta las cuentas de aportes relacionadas con ids de Aporte general
     */
    public static final String CONSULTAR_CUENTA_APORTE = "Aportes.consultar.cuenta.aporte";

    public static final String CONSULTAR_CUENTA_APORTE_BULDER = "Aportes.consultar.cuenta.aporte.builder";
    
    /**
     * Constante con el nombre de la consulta que obtiene el empleador
     */
    public static final String CONSULTAR_EMPLEADOR_TIPO_NUMERO_IDENTIFICACION = "Aportes.Consultar.Empleador.tipo.numero.identificacion";
    /**
     * Constante con el nombre de la consulta que obtiene los aportes detallados
     * para un empleador que se encuentra con un estado especifico
     */
    public static final String CONSULTAR_APORTE_DETALLADO_POR_PERSONA_ROLAFILIADO_ESTADO = "Aportes.Consultar.Aporte.Detallado.persona.estado.rolAfiliado";
    /**
     * Constante con el nombre de la consulta que obtiene los aportes de una
     * persona
     */
    public static final String CONSULTAR_APORTES_PERSONA_TIPO_NUMERO_IDENTIFICACION = "Aportes.Consultar.persona.tipo.numero.identificacion";
    //	/**
    //	 * Constante con el nombre de la actualizacion masiva del estado aporte
    //	 * Detallado
    //	 */
    //	public static final String ACTUALIZAR_APORTES_FORMA_MASIVA = "Aportes.Actualizar.Aporte.Detallado";

    /**
     * Constante con el nombre de la consulta para obtener los aportes por
     * persona y verificar si cuentan con empleador
     */
    public static final String CONSULTAR_APORTES_PERSONA_TIPO_NUMERO_IDENTIFICACION_Y_EMPLEADOR = "Aportes.Consultar.persona.tipo.numero.identificacion.empleador";

    /**
     * Constante con el nombre de la consulta para obtener un aporte general por
     * id de registro general.
     */
    public static final String CONSULTAR_APORTE_GENERAL_POR_REGISTRO = "Aportes.AporteGeneral.idRegistroGeneral";

    /**
     * Constante con el nombre de la consulta para obtener los aportes generales de un id de registro general.
     */
    public static final String CONSULTAR_APORTES_GENERALES_POR_REGISTRO = "Aportes.AporteGeneral.idRegistroGeneral.multiple";

    /**
     * Constante con el nombre de la consulta para obtener un aporte general por un listado de 
     * id de registro general.
     */
    public static final String CONSULTAR_APORTE_GENERAL_POR_REGISTRO_MASIVO = "Aportes.AporteGeneral.idRegistroGeneral.masivo";

    /**
     * Constante con el nombre de la consulta para obtener un aporte general por un listado de 
     * id de aporte general.
     */
    public static final String CONSULTAR_APORTE_GENERAL_POR_ID_MASIVO = "Aportes.AporteGeneral.idAporteGeneral.masivo";

    /**
     * Constante con el nombre de la consulta para obtener un aporte general por modalidad de recaudo
     */
    public static final String CONSULTAR_APORTE_GENERAL_POR_MODALIDAD_RECAUDO = "Aportes.AporteGeneral.modalidadRecaudo.masivo";

    /**
     * Constante con el nombre de la consulta para obtener los ID de TemAporteProcesado por un listado de id de registro general.
     */
    public static final String CONSULTAR_APORTE_PROCESADO_POR_REGISTRO_MASIVO = "Aportes.TemAporteProcesado.idRegistroGeneral.masivo";

    /**
     * Constante que contiene el nombre de la consulta que obtiene un registro
     * de <code>RegsitroDetallado</code>, por id
     */
    public static final String CONSULTAR_REGISTRO_DETALLADO = "Aportes.Consultar.RegistroDetallado";

    /**
     * Constante que contiene el nombre de la consulta que obtiene un registro
     * de <code>AporteGeneral</code>, por id
     */
    public static final String CONSULTAR_APORTE_GENERAL = "Aportes.Consultar.AporteGeneral";

    /**
     * Consultas los movimientos de aportes detallados relacionados a un
     * movimiento de aporte general
     */
    public static final String CONSULTAR_MOVIMIENTO_APORTE_DETALLADO = "Aportes.consultar.movimiento.detallado";

    /**
     * Constante con el nombre de la consulta que obtiene una solicitud de
     * corrección de aportes, por identificador de la solicitud global asociada
     */
    public static final String CONSULTAR_SOLICITUD_CORRECCION = "Aportes.Consultar.SolicitudCorreccionAporte.IdSolicitudGlobal";

    /**
     * Consulta para obtener <i><b>un cotizante</i></b> cuando se busca por
     * idPersona, <i><b>con al menos un parámetro de búsqueda</b></i> durante un
     * proceso de cuenta de aportes.
     */
    public static final String CONSULTAR_COTIZANTES_POR_PERSONA = "Consultar.Cotizantes.por.persona";

    /**
     * Constante que representa la consulta para obtener el o los movimientos históricos de ingresos <b>por persona</b>.
     */
    public static final String CONSULTAR_MOVIMIENTO_HISTORICO = "Consultar.Movimiento.Historico";

    /**
     * Constante que representa la consulta para obtener el o los movimientos históricos de ingresos <b>por empresa</b>.
     */
    public static final String CONSULTAR_MOVIMIENTO_HISTORICO_EMPRESA = "Consultar.Movimiento.Historico.Empresa";

    /**
     * Consulta de un aporte general por el id de una persona
     */
    public static final String CONSULTAR_APORTE_GENERAL_ID_PERSONA_DETALLE = "Consultar.aporte.general.id.persona";

    /**
     * Consulta de una Novedad temporal por el id de regitro general
     */
    public static final String CONSULTAR_NOVEDAD_TEMPORAL = "Consultar.Novedad.Temporal";

    /**
     * Consulta la persona relacionada a un aporte general
     */
    public static final String CONSULTAR_APORTE_GENERAL_PERSONA = "Consultar.persona.aporte.general";

    /**
     * Consulta la persona relacionada a un aporte general
     */
    public static final String CONSULTAR_APORTE_GENERAL_EMPRESA_PERSONA = "Consultar.empresa.persona.aporte.general";

    /**
     * Constante con el nombre de la consulta que obtiene los registros de la tabla <code>TemAporte</code>, por identificador de registro
     * general
     */
    public static final String CONSULTAR_TEMAPORTE = "Aportes.Consultar.TemAporte";

    /**
     * Constante con el nombre de la consulta que borra registros de la tabla <code>TemNovedad</code>, por identfiicador de transacción
     */
    public static final String DELETE_TEMNOVEDAD = "Aportes.Borrar.TemNovedad";

    /**
     * Constante con el nombre de la consulta que borra registros de la tabla <code>TemNovedad</code>, por identificador de registro detallado novedad
     */
    public static final String DELETE_TEMNOVEDAD_DETALLE = "Aportes.Borrar.TemNovedad.detalle";

    /**
     * Constante con el nombre de la consulta que borra registros de la tabla <code>TemAporte</code>, por identfiicador de transacción
     */
    public static final String DELETE_TEMAPORTE = "Aportes.Borrar.TemAporte";

    /**
     * Constante con el nombre de la consulta que borra registros de la tabla <code>TemAportante</code>, por identfiicador de transacción
     */
    public static final String DELETE_TEMAPORTANTE = "Aportes.Borrar.TemAportante";

    /**
     * Constante con el nombre de la consulta que borra registros de la tabla <code>TemCotizante</code>, por identfiicador de transacción
     */
    public static final String DELETE_TEMCOTIZANTE = "Aportes.Borrar.TemCotizante";

    /**
     * Constante con el nombre de la consulta que borra registros de la tabla <code>TemAporteProcesado</code>, por identficador de RegistroGeneral
     */
    public static final String DELETE_TEMAPORTEPROCESADO = "Aportes.Borrar.TemAporteProcesado";

    /**
     * Constante con el nombre de la consulta que borra registros de la tabla <code>TemAporteProcesado</code>, por grupo de identficadores de RegistroGeneral
     */
    public static final String DELETE_TEMAPORTEPROCESADO_MASIVO = "Aportes.Borrar.TemAporteProcesado.masivo";

    /**
     * Constante con el nombre de la consulta que borra registros de la tabla <code>TemAporteActualizado</code>, por identfiicador de registro detalaldo
     */
    public static final String DELETE_TEMAPORTEACTUALIZADO = "Aportes.Borrar.TemAporteActualizado";

    /**
     * Consulta el aporte general relacionado a un movimiento donde su tipo de ajuste es DEVOLUCIÓN o CORRECCION_A_LA_BAJA
     */
    public static final String CONSULTAR_APORTE_GENERAL_Y_MOVIMIENTO = "Consultar.movimiento.aporte.general";
    public static final String CONSULTAR_APORTE_GENERAL_Y_MOVIMIENTO_NATIVA = "Consultar.movimiento.aporte.general.native";

    /**
     * Constante que representa la consulta del estado de los registros en aporte general
     */
    public static final String CONSULTAR_ESTADO_APORTE_GENERAL = "Consultar.estado.aporte.general";

    /**
     * Constante que representa la consulta del estado de los registros en aporte detallado
     */
    public static final String CONSULTAR_ESTADO_APORTE_DETALLADO = "Consultar.estado.aporte.detallado";

    /**
     * Constante con el nombre de la query para consultar una solicitud de corrección
     * aporte por tipo y número de identificación, obtentiendo así la
     * información necesaria para saber si existe una solicitud de corrección de aporte para
     * un aportante que aún no esté cerrada.
     */
    public static final String CONSULTAR_SOLICITUD_CORRECCION_APORTE_POR_TIPO_IDENTI_Y_NUM_IDENTI = "Consultar.SolicitudCorreccionAporte.TipoIdenti.NumIdenti";

    /**
     * Constante con el nombre de la query para consultar una solicitud de devolución de
     * aporte por tipo y número de identificación, obtentiendo así la
     * información necesaria para saber si existe una solicitud de devolución de aporte para
     * un aportante que aún no esté cerrada.
     */
    public static final String CONSULTAR_SOLICITUD_DEVOLUCION_APORTE_POR_TIPO_IDENTI_Y_NUM_IDENTI = "Consultar.SolicitudDevolucionAporte.TipoIdenti.NumIdenti";

    /**
     * Consulta el id de un Municipio por el codigo del mismo Municipio
     */
    public static final String CONSULTAR_MUNICIPIO_CODIGO = "Aportes.Municipio.buscarMunicipioCodigo";

    /**
     * Consulta de Municipios por listado de códigos
     */
    public static final String CONSULTAR_MUNICIPIO_CODIGO_MASIVO = "Aportes.Municipio.buscarMunicipioCodigo.masivo";

    /**
     * Consulta el cotizante por el IdPersona
     */
    public static final String CONSULTAR_COTIZANTE = "Aportes.Cotizante.Id.Persona";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de tipos de transacción de solicitudes de novedad en estado RECHAZADA,
     * para un cotizante
     */
    public static final String CONSULTAR_NOVEDADES_RETIRO_COTIZANTE = "Aportes.Consultar.Novedades.Retiro.Cotizante";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de novedades rechazadas, para un cotizante
     */
    public static final String CONSULTAR_NOVEDADESRECHAZADAS_COTIZANTE_APORTE = "Aportes.Consultar.NovedadesRechazadas.Cotizante.Aporte";

    /**
     * Constante que representa la actualización del estado del registro detallado
     */
    public static final String ACTUALIZAR_ESTADO_REGISTRO_DETALLADO_NO_OK = "Aportes.actualizar.estado.registro.detallado.no.ok";

    /**
     * Constante que representa la actualización del estado del registro detallado
     */
    public static final String ACTUALIZAR_ESTADO_REGISTRO_DETALLADO_NO_VALIDADO = "Aportes.actualizar.estado.registro.detallado.no.validado";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de tipos de transacción de solicitudes de novedad en estado RECHAZADA,
     * para un cotizante
     */
    public static final String CONSULTAR_TIPOTRANSACCION_NOVEDADESRECHAZADAS_COTIZANTE = "Aportes.Consultar.TipoTransaccion.NovedadesRechazadas.Cotizante";

    /**
     * Constante con el nombre de la consulta que obtiene los cotizantes de acuerdo a una lista de roles.
     */
    public static final String CONSULTAR_COTIZANTES_ROL_AFILIADO = "Aportes.Consultar.Cotizantes.RolAfiliado";

    /**
     * Constante con el nombre de la consulta de empleadores que no cuentan con un día de venciemiento de aportes
     */
    public static final String CONSULTAR_APORTANTE_EMPRESA_SIN_DIA_VENCIMIENTO = "Aportes.Consultar.empleador.sin.dia.vencimiento.aportes";

    /**
     * Constante con el nombre de la consulta de independiente o pensionados que no cuentan con un día de venciemiento de aportes
     */
    public static final String CONSULTAR_APORTANTE_PERSONA_SIN_DIA_VENCIMIENTO = "Aportes.Consultar.personas.sin.dia.vencimiento.aportes";

    /**
     * Constante con el nombre de la consulta de empleadores de acuerdo a listado de IDs de registro
     */
    public static final String CONSULTAR_EMPLEADOR_ID_REGISTRO = "Aportes.Consultar.empleadores.por.listado.id";

    /**
     * Constante con el nombre de la consulta de independientes y pensionados de acuerdo a listado de IDs de registro
     */
    public static final String CONSULTAR_ROLAFILIADO_ID_REGISTRO = "Aportes.Consultar.independientesPensionados.por.listado.id";

    /**
     * Constante con el nombre de la sentencia para actualizar el día de vencimiento de aportes en empleadores
     */
    public static final String ACTUALIZAR_DIA_VENCIMIENTO_EMPLEADOR = "Aportes.Actualizar.empleadores.dia.vencimiento";

    /**
     * Constante con el nombre de la sentencia para actualizar el día de vencimiento de aportes en independientes y pensionados
     */
    public static final String ACTUALIZAR_DIA_VENCIMIENTO_INDEPENDIENTES_PENSIONADOS = "Aportes.Actualizar.indPen.dia.vencimiento";

    /**
     * Constante con el nombre de la consulta de novedades rechazadas
     */
    public static final String CONSULTAR_NOVEDADES_RECHAZADAS = "Aportes.consultar.novedades.rechazadas";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de aportes generales por empleador
     */
    public static final String CONSULTAR_APORTE_GENERAL_EMPLEADOR = "Aportes.Consultar.AporteGeneral.Empleador";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de aportes detallados, de acuerdo a los ids de aporte general
     */
    public static final String CONSULTAR_APORTE_DETALLADO_IDS_GENERAL = "Aportes.Consultar.AporteDetallado.IdsGeneral";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de aportes generales asociados a un pensionado o independiente
     */
    public static final String CONSULTAR_APORTE_GENERAL_PERSONA_IDENPENDIENTE_PENSIONADO = "Aportes.Consultar.AporteGeneral.Persona";

    /**
     * Constante con el nombre de la consulta que obtiene una lista de aportes detallados, para un pensionado o independiente
     */
    public static final String CONSULTAR_APORTE_DETALLADO_IDS_GENERAL_PERSONA = "Aportes.Consultar.AporteDetallado.IdsGeneral.Persona";

    /**
     * Constante con el nombre de la consulta que obtiene una lista de aportes detallados asociados a un aportes general
     */
    public static final String CONSULTAR_APORTE_DETALLADO_ID_GENERAL = "Aportes.Consultar.AporteDetallado.IdGeneral";

    /**
     * Constante con el nombre de la consulta de cuentas de aportes para aportantes registrados sin detalle
     */
    public static final String CONSULTAR_CUENTA_APORTE_SIN_DETALLE = "Aportes.consultar.cuenta.aporte.sin.detalle";

    public static final String CONSULTAR_CUENTA_APORTE_SIN_DETALLE_BUILDER = "Aportes.consultar.cuenta.aporte.sin.detalle.builder";

    /**
     * Constante que representa la actualización del estado del registro general
     */
    public static final String ACTUALIZAR_ESTADO_REGISTRO_GENERAL_GESTIONAR_ERROR_VALIDACION_VS_BD = "Aportes.actualizar.estado.registro.general.validacion.vs.bd";

    /**
     * Constante con el nombre de la consulta que obtiene los datos de una persona asociada a una empresa
     */
    public static final String CONSULTAR_PERSONA_EMPRESA = "Aportes.Consultar.Persona.Empresa";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de aportes relacionados de la tabla AporteGeneral, por persona
     */
    public static final String CONSULTAR_APORTES_RELACIONADOS = "Aportes.Consultar.Aportes.Relacionados";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de aportes relacionados de la tabla AporteGeneral, por empresa
     */
    public static final String CONSULTAR_APORTES_RELACIONADOS_EMPRESA = "Aportes.Consultar.Aportes.Relacionados.Empresa";

    /**
     * Consulta las cuentas de aportes relacionadas con ids de Aporte general y a el cotizante
     */
    public static final String CONSULTAR_CUENTA_APORTE_COTIZANTE = "Aportes.consultar.cuenta.aporte.cotizante";

    public static final String CONSULTAR_CUENTA_APORTE_COTIZANTE_BUILDER = "Aportes.consultar.cuenta.aporte.cotizante.builder";
    

    /**
     * Se consulta el solicitante con el identificador de la persona relacionada al aporte
     * general
     */
    public static final String CONSULTAR_SOLICITANTE_APORTE_GENERAL = "Aportes.aporte.general.consultar.solicitante";

    /**
     * Se consulta el solicitante con el identificador de la persona relacionada al aporte
     * general
     */
    public static final String CONSULTAR_SOLICITANTE_APORTE_GENERAL_EMPRESA = "Aportes.aporte.general.consultar.solicitante.empresa";

    /**
     * Se consulta la persona por tipo y numero de identificacion
     */
    public static final String CONSULTAR_PERSONA_TIPO_NUMERO_IDENTIFICACION = "Aportes.persona.tipo.numero.identificacion";

    /**
     * Se consulta la empresa por el identificador de la persona
     */
    public static final String CONSULTAR_EMPRESA_ID_PERSONA = "Aportes.empresa.id.persona";

    /**
     * Constante con el nombre de la consulta que obtiene una solicitud de
     * corrección de aportes, por identificador de la solicitud global asociada
     */
    public static final String CONSULTAR_SOLICITUD_CORRECCION_APORTE_GENERAL = "Aportes.Consultar.SolicitudCorreccionAporte.idAporteGeneral";

    /**
     * Constante con el nombre de la consulta que obtiene una solicitud de
     * cierre de recaudo por el número de radicación.
     */
    public static final String CONSULTAR_SOLICITUD_CIERRE_RECAUDO_NUMERO_RADICACION = "Aportes.Consultar.SolicitudCierreRecaudo.numeroRadicacion";

    /**
     * Constante con el nombre de la consulta que obtiene una solicitud de
     * cierre de recaudo comprendida entre fechas
     */
    public static final String CONSULTAR_SOLICITUD_CIERRE_RECAUDO_FECHAS_ESTADO_RESULTADO = "Aportes.Consultar.SolicitudCierreRecaudo.fechas.resultado";

    /**
     * Constante para la ejecución del SP que valida la aplicación de novedades futuras
     */
    public static final String PROCEDURE_USP_SOLICITAR_EVALUACION_NOVEDAD_FUTURA = "Aportes.StoredProcedures.USP_SolicitarEvaluacionNovedadFutura";

    /**
     * Constante para consultar el historico del cierre de recaudo por fecha inicio, fecha fin y número de radicación
     */
    public static final String CONSULTAR_FECHA_INICIO_FIN_NUMERO_RADICACION_CIERRE_RECAUDO = "Aportes.Consultar.SolicitudCierreRecaudo.historico.fechas.numeroRadicacion";

    /**
     * Constante para consultar el historico del cierre de recaudo por fecha inicio y fecha fin
     */
    public static final String CONSULTAR_FECHA_INICIO_FIN_CIERRE_RECAUDO = "Aportes.Consultar.SolicitudCierreRecaudo.historico.fechas";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de aportes detallados, de acuerdo a los ids de aporte general para
     * la vista 360
     */
    public static final String CONSULTAR_APORTE_DETALLADO_IDS_GENERAL_VISTA_360 = "Aportes.Consultar.AporteDetallado.IdsGeneral.Vista.360";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de devoluciones dados a los ids de aporte general y aporte detallado para
     * la vista 360 de aportes
     */
    public static final String CONSULTAR_SOLICITUD_DEVOLUCION_VISTA_360_APORTE_POR_ID_APORTE_ID_MOVIMIENTO = "Aportes.Consultar.Solicitud.Devolucion.Vista360.Por.IdAporte.IdMovimiento";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de devoluciones dados a los ids de aporte general la vista 360 de aportes
     */
    public static final String CONSULTAR_SOLICITUD_DEVOLUCION_VISTA_360_APORTE_POR_ID_APORTE = "Aportes.Consultar.Solicitud.Devolucion.Vista360.Por.IdAporte";

    /**
     * Constante con el nombre de la consulta que obtiene detalle de la devolucion dado el id de aporte general la vista 360 de aportes
     */
    public static final String CONSULTAR_DETALLE_DEVOLUCION_VISTA_360_APORTE_POR_ID_APORTE = "Aportes.Consultar.Detalle.Devolucion.Vista360.Por.IdAporte";

    /**
     * Constante con el nombre de la consulta que obtiene detalle de la correcion del cotizante por id aporte general tipo identificacion y
     * numero identificacion
     */
    public static final String CONSULTAR_DETALLE_CORRECCION_COTIZANTE_VISTA_360 = "Aportes.Consultar.Detalle.Correccion.Cotizante.Vista360";

    /**
     * Constante con el nombre de la consulta que obtiene detalle de la correcion del aportante por tipo identificacion y numero
     * identificacion
     */
    public static final String CONSULTAR_DETALLE_CORRECCION_APORTANTE_VISTA_360 = "Aportes.Consultar.Detalle.Correccion.Aportante.Vista360";

    /**
     * Constante con el nombre de la consulta que obtiene detalle de la correcion del cotizante nuevo por tipo identificacion y numero
     * identificacion
     */
    public static final String CONSULTAR_DETALLE_CORRECCION_COTIZANTE_NUEVO_VISTA_360 = "Aportes.Consultar.Detalle.Correccion.Aportante.Nuevo.Vista360";
    public static final String CONSULTAR_DETALLE_CORRECCION_COTIZANTE_NUEVO_VISTA_360_NATIVA = "Consultar.detalle.correccion.cotizante.native";

    /**
     * Constante con el nombre de la consulta que obtiene el id del aporte general por medio del id del cotizante
     */
    public static final String CONSULTAR_IDENTIFICADOR_APORTE_GENERAL_POR_ID_COTIZANTE_VISTA_360_PERSONA = "Consultar.Identificador.Aporte.General.Por.Id.Cotizante.Vista360.Persona";

    /**
     * Constante con el nombre de la consulta que consulta la solicitud de correcion por el id del aporte
     */
    public static final String CONSULTAR_SOLICITUD_CORRECION_VISTA_360_APORTE_POR_ID_APORTE = "Aportes.Consultar.Solicitud.Correcion.Vista360.Por.IdAporte";

    /**
     * Constante con el nombre de la consulta que consulta la solicitud de aporte por el id del aporte
     */
    public static final String CONSULTAR_SOLICITUD_APORTE_VISTA_360_APORTE_POR_ID_APORTE = "Aportes.Consultar.Solicitud.Aporte.Vista360.Por.IdAporte";

    /**
     * Constante con el nombre que consulta el registro general por medio del id de registro general del aporte
     */
    public static final String CONSULTAR_REGISTRO_GENERAL_VISTA_360_POR_ID_REGISTRO_GENERAL_APORTE = "Consultar.Registrado.General.Por.Id.Registro.General.Aporte";

    /**
     * Constante para consultar el historico del cierre de recaudo por número de radicación
     */
    public static final String CONSULTAR_NUMERO_RADICACION_CIERRE_RECAUDO = "Aportes.Consultar.SolicitudCierreRecaudo.historico.numeroRadicacion";

    /**
     * Constante para consultar el historico del cierre de recaudo por fecha inicio, fecha fin sinel numero de radicacion pero si validando
     * el estado
     */
    public static final String CONSULTAR_FECHA_INICIO_FIN_SIN_NUMERO_RADICACION_CIERRE_RECAUDO = "Aportes.Consultar.SolicitudCierreRecaudo.historico.fechas.sin.numeroRadicacion";

    /**
     * Constante que representa la consulta de los aportes que se encuentran registrados
     */
    public static final String CONSULTA_APORTES_REGISTRADOS = "Aportes.consultar.aportes.registrados";

    /**
     * Constante que representa la consulta de los aportes que se encuentran relacionados
     */
    public static final String CONSULTA_APORTES_RELACIONADOS = "Aportes.consultar.aportes.relacionados.215";

    /**
     * Constante que representa la consulta de las devoluciones que se encuentran registradas
     */
    public static final String CONSULTA_DEVOLUCIONES_REGISTRADOS = "Aportes.consultar.devoluciones.registrados";

    /**
     * Constante que representa la consulta de las devoluciones que se encuentran relacionadas
     */
    public static final String CONSULTA_DEVOLUCIONES_RELACIONADAS = "Aportes.consultar.devoluciones.relacionados";

    /**
     * Constante que representa la consulta de los correcciones que se encuentran registrados
     */
    public static final String CONSULTA_CORRECCIONES_REGISTRADOS = "Aportes.consultar.correcciones.registrados";

    /**
     * Constante que representa la consulta de los correcciones que se encuentran relacionados
     */
    public static final String CONSULTA_CORRECCIONES_RELACIONADOS = "Aportes.consultar.correcciones.relacionados";

    /**
     * Constante que representa la consulta de los aportes que se encuentran registrados legalizados
     */
    public static final String CONSULTA_REGISTRADOS_LEGALIZADOS = "Aportes.consultar.registrados.legalizados";

    /**
     * Constante que representa la consulta delperiodo que se ingresa desde afiliacion para independientes o pensionados
     */
    public static final String CONSULTAR_PERIODO_PAGO_AFILIACION = "Aportes.periodo.pago.afiliacion";

    /**
     * Constante que representa la consulta de la persona cotizante que se encuentra en el registro del aporte detallado
     */
    public static final String CONSULTAR_PERSONA_APORTE_DETALLADO = "Aportes.consultar.persona.id.aporte.detallado";

    /**
     * Constante que representa la consulta de los recaudos realizados a los cotizantes que tienen novedades procesadas aplicadas
     */
    public static final String CONSULTAR_RECAUDO_COTIZANTE_NOVEDADES_PROCESADAS = "Aportes.consultar.recaudo.cotizantes.novedades.procesadas";

    /**
     * Constante que representa la consulta de los recaudos realizados a los cotizantes que tienen novedades no procesadas guardadas
     */
    public static final String CONSULTAR_RECAUDO_COTIZANTE_NOVEDADES_NO_PROCESADAS = "Aportes.consultar.recaudo.cotizantes.novedades.no.procesadas";

    /**
     * Constante que representa la consulta de los recaudos realizados a los cotizantes que tienen personas pendientes por afiliar
     */
    public static final String CONSULTAR_RECAUDO_COTIZANTE_PENDIENTE_AFILIAR = "Aportes.consultar.recaudo.cotizantes.pendiente.afiliar";

    /**
     * Constante que representa la consulta del medio de consignación
     */
    public static final String CONSULTAR_MEDIO_CONSIGNACION = "Aportes.consultar.medio.consignacion";
    
    /**
     * Constante que representa la consulta del medio de consignación
     */
    public static final String CONSULTAR_MEDIO_TRANSFERENCIA = "Aportes.consultar.medio.transferencia";

    /* Constante para la consulta que indica que un cotizante ha generado subsidio para un período */
    public static final String HAY_SUBSIDIO_PARA_COTIZANTE = "USP_SM_GET_CotizanteConSubsidioPeriodo";

    /**
     * Constante que representa el valor de la consulta para los registros que son aportes 
     */
    public static final String CONSULTAR_DETALLE_APORTES_COTIZANTES_REGISTRADOS = "Aportes.consultar.detalle.registro.aportes.cotizantes.registrados";

    /**
     * Constante que representa el valor de la consulta para los registros que son aportes 
     */
    public static final String CONSULTAR_DETALLE_APORTES_REGISTRADOS = "Aportes.consultar.detalle.registro.aportes.registrados";

    /**
     * Numero de radicacion del aporte por registro general
     */
    public static final String CONSULTAR_RADICADO_POR_REGISTRO_GENERAL = "Aportes.consultar.numeroRadicado.idRegistroGeneral";

     /**
     * Numero de aporte general de las correciones
     */
    public static final String CONSULTAR_APORTE_GENERAL_CORRECION = "Aportes.consultar.aporteGeneral.idAporteGeneral";

    /**
     * Constante que representa el valor de la consulta para los registros que son aportes 
     */
    public static final String CONSULTAR_DETALLE_REGISTRO_GENERAL_TARIFA = "Aportes.consultar.detalle.registroGeneral.tarifa";
    public static final String CONSULTAR_DETALLE_REGISTRO_GENERAL_TARIFA_APORTANTE = "Aportes.consultar.detalle.registroGeneral.tarifa.aportante";

    public static final String CONSULTAR_DATOS_PLANILLA_PAGO_OBLIGATORIO = "Consultar.datos.planilla.pago.obligatorio";
    public static final String CONSULTAR_DATOS_PLANILLA_VALOR_MORA = "Consultar.datos.planilla.valor.mora";
    public static final String CONSULTAR_DATOS_PLANILLA_TOTAL_RECAUDO = "Consultar.datos.planilla.total.recaudo";

    public static final String CONSULTAR_DATOS_PLANILLA_PAGO_OBLIGATORIO_COTIZANTE = "Consultar.datos.planilla.pago.obligatorio.cotizante";

    public static final String CONSULTAR_DATOS_PLANILLA_PAGO_OBLIGATORIO_COTIZANTE_MORA = "Consultar.datos.planilla.pago.obligatorio.cotizante.mora";
    public static final String CONSULTAR_DATOS_PLANILLA_PAGO_OBLIGATORIO_COTIZANTE_TOTAL = "Consultar.datos.planilla.pago.obligatorio.cotizante.total";
    public static final String CONSULTAR_DATOS_PLANILLA_TARIFA_COTIZANTE= "Consultar.datos.planilla.tarifa.cotizante";
    public static final String CONSULTAR_DATOS_PLANILLA_TARIFA_APORTANTE = "Consultar.datos.planilla.tarifa.aportante";
    
    /**
     * Constante que representa el valor de la consulta para los registros que son aportes 
     */
    public static final String CONSULTAR_DETALLE_DEVOLUCIONES_COTIZANTES = "Aportes.consultar.detalle.registro.aportes.cotizantes.devoluciones";
    
    /**
     * Constante que representa el valor de la consulta para los registros que son aportes 
     */
    public static final String CONSULTAR_DETALLE_DEVOLUCIONES = "Aportes.consultar.detalle.registro.aportes.devoluciones";

    /**
     * Constante que representa el valor de la consulta para los registros que son aportes 
     */
    public static final String CONSULTAR_DETALLE_CORRECCIONES_ORIGEN_COTIZANTES = "Aportes.consultar.detalle.registro.aportes.cotizantes.correcciones.origen";
    
    /**
     * Constante que representa el valor de la consulta para los registros que son aportes 
     */
    public static final String CONSULTAR_DETALLE_CORRECCIONES_ORIGEN = "Aportes.consultar.detalle.registro.aportes.correccion.origen";

    /**
     * Constante que representa el valor de la consulta para los registros que son aportes 
     */
    public static final String CONSULTAR_DETALLE_CORRECCIONES_A_LA_ALTA_ORIGEN = "Aportes.consultar.detalle.registro.aportes.correccion.a.la.alta.origen";

    /**
     * Constante que representa el valor de la consulta para los registros que son aportes 
     */
    public static final String CONSULTAR_DETALLE_CORRECCIONES_A_LA_ALTA_ORIGEN_COTIZANTES = "Aportes.consultar.detalle.registro.aportes.cotizantes.correcciones.a.la.alta.origen";


    /**
     * Constante que representa el valor de la actualización sobre aporte general
     */
    public static final String ACTUALIZAR_MARCA_CONCILIADO_APORTE_GENERAL = "Aportes.actualizar.marca.conciliado.aporte.general";
    
    /**
     * Consulta que obtiene el historico de novedades recientes.
     */
    public static final String CONSULTAR_HISTORICO_NOVEDADES_RECIENTES = "Aportes.Novedad.consultarHistorico.Recientes";
    
    /**
     * Consulta que obtiene el historico de novedades de retiro recientes.
     */
    public static final String CONSULTAR_HISTORICO_NOVEDADES_RETIRO_RECIENTES = "Aportes.Novedad.consultarHistoricoRetiro.recientes";
    
    /**
     * Constante con el nombre de la consulta de novedades rechazadas
     */
    public static final String CONSULTAR_NOVEDADES_RECHAZADAS_RECIENTES = "Aportes.consultar.novedades.rechazadas.recientes";
    
    /**
     * Constante con el nombre de la consulta de novedades que no tienen asociado un rol de afiliado y a su vez un empleador 
     */
    public static final String CONSULTAR_HISTORICO_NOVEDADES_SIN_ROL_AFILIADO = "Aportes.Novedad.consultarHistorico.sin.rol.afiliado";
    
    /**
     * Constante con el nombre de la ejecución del procedimiento de la validación de la sucursal
     */
    public static final String VALIDAR_SUCURSAL = "USP_VerificarCumplimientoSucursal";

    /**
     * Constante con el nombre de la consulta de los identificadores d elos cotizantes para una corrección 
     */
    public static final String CONSULTAR_COTIZANTE_CORRECCIONES = "Aportes.cotizantes.correcciones";

    /**
     * Constante con el nombre de la consulta de los datos del aportante
     */
    public static final String CONSULTAR_DATOS_APORTANTE_PERSONA = "Aportes.consultar.datos.aportante.persona";
    
    /**
     * Constante con el nombre de la consulta de los datos del aportante
     */
    public static final String CONSULTAR_DATOS_APORTANTE_EMPRESA = "Aportes.consultar.datos.aportante.empresa";
    
    /**
     * Constante con el nombre de la consulta de los datos del detalle de los aportes
     */
    public static final String CONSULTAR_DETALLE_DATOS_APORTANTE = "Aportes.consultar.detalle.datos.aportante";

    /**
     * Constante con el nombre de la consulta de los datos del detalle de los cotizantes
     */
    public static final String CONSULTAR_DATOS_COTIZANTE_PERSONA = "Aportes.consultar.datos.cotizante";
    
    /**
     * Constante con el nombre de la consulta de los datos del detalle de los cotizantes
     */
    public static final String CONSULTAR_DETALLE_DATOS_COTIZANTE = "Aportes.consultar.detalle.datos.cotizante";

    /**
     * Constante con el nombre de la consulta de los datos del detalle de los aportantes con parametros de periodos
     */
    public static final String CONSULTAR_DATOS_APORTANTE_PERSONA_PERIODOS = "Aportes.consultar.datos.aportante.persona.periodo";
    
    /**
     * Constante con el nombre de la consulta de los datos del detalle de los aportantes con parametros de periodos
     */
    public static final String CONSULTAR_DATOS_APORTANTE_EMPRESA_PERIODOS = "Aportes.consultar.datos.aportante.empresa.periodo";

    /**
     * Constante con el nombre de la consulta de los datos del detalle de los cotizantes con parametros de periodos
     */
    public static final String CONSULTAR_DETALLE_DATOS_COTIZANTE_PERIODOS = "Aportes.consultar.detalle.datos.cotizante.periodos";

    /**
     * Constante con el nombre de la consulta de los datos del detalle de los cotizantes
     */
    public static final String CONSULTAR_DETALLE_DATOS_COTIZANTE_EMPRESA = "Aportes.consultar.detalle.datos.cotizante.empresa";

    /**
     * Constante con el nombre de la consulta de los datos del detalle de los cotizantes con parametros de periodos
     */
    public static final String CONSULTAR_DETALLE_DATOS_COTIZANTE_PERIODOS_EMPRESA = "Aportes.consultar.detalle.datos.cotizante.periodos.empresa";

    /**
     * Constante con el nombre de la consulta de los datos del aportante
     */
    public static final String CONSULTAR_DATOS_APORTANTE_PERSONA_COTIZANTE = "Aportes.consultar.datos.aportante.persona.cotizante";

    /**
     * Constante con el nombre de la consulta de los datos del aportante
     */
    public static final String CONSULTAR_DATOS_APORTANTE_EMPRESA_COTIZANTE = "Aportes.consultar.datos.aportante.empresa.cotizante";

    /**
     * Constante con el nombre de la consulta de los datos del aportante
     */
    public static final String CONSULTAR_DATOS_APORTANTE_PERSONA_APO_COT = "Aportes.consultar.datos.aportante.persona.apo.cotizante";

    /**
     * Constante con el nombre de la consulta de los datos del aportante
     */
    public static final String CONSULTAR_DATOS_APORTANTE_EMPRESA_APO_COT = "Aportes.consultar.datos.aportante.empresa.apo.cotizante";

    /**
     * Constante con el nombre de la consulta de los datos del detalle de los aportantes con parametros de periodos
     */
    public static final String CONSULTAR_DATOS_APORTANTE_PERSONA_PERIODOS_APO_COT = "Aportes.consultar.datos.aportante.persona.periodo.apo.cot";

    /**
     * Constante con el nombre de la consulta de los datos del detalle de los aportantes con parametros de periodos
     */
    public static final String CONSULTAR_DATOS_APORTANTE_EMPRESA_PERIODOS_APO_COT = "Aportes.consultar.datos.aportante.empresa.periodo.apo.cot";

    /**
     * Constante con el nombre de la consulta de los datos del detalle de los aportantes con parametros de periodos
     */
    public static final String CONSULTAR_DATOS_APORTANTE_PERSONA_PERIODOS_COTIZANTE = "Aportes.consultar.datos.aportante.persona.periodo.cotizante";

    /**
     * Constante con el nombre de la consulta de los datos del detalle de los aportantes con parametros de periodos
     */
    public static final String CONSULTAR_DATOS_APORTANTE_EMPRESA_PERIODOS_COTIZANTE = "Aportes.consultar.datos.aportante.empresa.periodo.cotizante";

    /**
     * Constante con el nombre de la consulta para obtener el dia de vencimiento del aporte
     */
    public static final String CONSULTAR_DIA_VENCIMIENTO_APORTE_AFILIADO = "Aportes.dia.vencimiento.aporte.afiliado";

    /**
     * Constante con el nombre de la consulta para obtener el dia de vencimiento del aporte
     */
    public static final String CONSULTAR_DIA_VENCIMIENTO_APORTE_EMPLEADOR = "Aportes.dia.vencimiento.aporte.empleador";

    /**
     * Constante con el nombre de la consulta para obtener los datos del cotizante
     */
    public static final String CONSULTAR_DATOS_COTIZANTE_EMPRESA = "Aportes.consultar.datos.cotizante.empresa";

    /**
     * Constante con el nombre de la consulta de las peticiones de actualización de aportes 
     */
    public static final String CONSULTAR_APORTES_PARA_ACTUALIZAR = "Aportes.consultar.actualizacionAportes";

    /**
     * Constante con el nombre de la consulta de aportes detalldos asociados a un registro detallado
     */
    public static final String CONSULTAR_APORTE_DETALLADO_REGISTRO_DETALLADO = "Aportes.consultar.aporteDetallado.registroDetallado";

    /**
     * Constante con el nombre de la consulta de aportes detalldos asociados a un registro detallado
     */
    public static final String CONSULTAR_APORTE_DETALLADO_REGISTRO_DETALLADO_MASIVO = "Aportes.consultar.aporteDetallado.registroDetallado.masivo";

    /**
     * Constante con el nombre de la consulta de aportes detalldos asociados a un registro detallado
     */
    public static final String CONSULTAR_APORTE_DETALLADO_ID_APORTE_GENERAL_MASIVO = "Aportes.consultar.aporteDetallado.idAporteGeneral.masivo";

    /**
     * Constante que contiene el nombre del Sp para el cálculo de estado de servicios para independientes y pensionados
     */
    public static final String PROCEDURE_USP_GET_CALCULAR_ESTADO_SERVICIOS_INDEPENDIENTE_PENSIONADO = "Aportes.StoredProcedures.USP_GET_CalcularEstadoServiciosIndependientePensionado";

    /**
     * Constante con el nombre de la consulta para obtener los aportes que tienen correcciones
     */
    public static final String CONSULTAR_APORTES_CON_CORRECCIONES_DEVOLUCIONES = "Aportes.consultar.aportes.con.correcciones.devoluciones";

    public static final String CONSULTAR_APORTES_CON_CORRECCIONES_DEVOLUCIONES_EMPLEADOR = "Aportes.consultar.aportes.con.correcciones.devoluciones.empleador";

    /**
     * Constante con el nombre de la consulta de las novedades de un cotizante 
     */
    public static final String CONSULTAR_NOVEDADES_COTIZANTE = "Aportes.consultar.novedades.cotizante";

    /**
     * Constante con el nombre de la consulta de datos de un afiliado para cálculo de estado de servicios
     * */
    public static final String CONSULTAR_DATOS_AFILIADO_SERVICIOS = "Aportes.consultar.rolAfiliado.datosAfiliado";

    /**
     * Constante con el nombre de la consulta del tipo de cotizante más reciente en un aporte como independiente
     * */
    public static final String CONSULTAR_TIPO_COTIZANTE_APORTE_INDEPENDIENTE = "Aportes.consultar.registroDetallado.tipoCotizanteAporteInd";

    /**
     * Constante que contiene el nombre del procedimiento almacenado para el cálculo de un día calendario a partir de un día habil
     */
    public static final String PROCEDURE_USP_CALCULO_FECHA_DIA_HABIL = "Aportes.StoredProcedures.USP_calculoFechaDiaHabil";
    
    /**
     * Constante que contiene el nombre del procedimiento almacenado para el cálculo de un día calendario a partir de un día habil
     */
    public static final String PROCEDURE_USP_CalculoMasivoCategorias = "Aportes.StoredProcedures.USP_CalculoMasivoCategorias";

    /**
     * Constante con el nombre de la consulta del estado del aporte más reciente de una persona y su registro general en staging
     * */
    public static final String CONSULTAR_DATOS_APORTE_CALCULO_SERVICIOS = "Aportes.consultar.AporteDetallado.estadoAporteYRegistroGeneral";

    /**
     * Constante que contiene el nombre del procedimiento almacenado para la revalidación de PILA 2 Fase 1 para un registro general
     */
    public static final String PROCEDURE_USP_REVALIDAR_PILA2_FASE1 = "USP_GET_RevalidarPila2Fase1";

    /**
     * Constante con el nombre de la consulta del estado de afiliación de aportante como pensionado
     * */
    public static final String CONSULTAR_ESTADO_AFILIACION_PENSIONADO = "Aportes.consultar.vistaEstadoAfiliacion.pensionado";

    /**
     * Constante con el nombre de la consulta del estado de afiliación de aportante como trabajador independiente
     * */
    public static final String CONSULTAR_ESTADO_AFILIACION_INDEPENDIENTE = "Aportes.consultar.vistaEstadoAfiliacion.independiente";

    /**
     * Constante con el nombre de la consulta de la clasificación de aportante como trabajador independiente
     * */
    public static final String CONSULTAR_CLASIFICACION_INDEPENDIENTE = "Aportes.consultar.clasificación.independiente";
    
    /**
     * Constante con el nombre de la consulta de la clasificación de aportante como trabajador independiente
     * */
    public static final String CONSULTAR_PORCENTAJE_INDEPENDIENTE = "Aportes.consultar.porcentaje.independiente";
    /**
     * Constante que representa la consulta de los recaudos realizados a los cotizantes que tienen un estado OK
     */
     
    public static final String CONSULTAR_RECAUDO_COTIZANTE_APORTES = "Aportes.consultar.recaudo.cotizantes.aporte.ok";
    
    /**
     * Constante con el nombre de la consulta que obtiene la lista de devoluciones dados a los ids de aporte general y aporte detallado para
     * la vista 360 de aportes
     */
    public static final String CONSULTAR_SOLICITUD_DEVOLUCION_IDS_APORTES = "Aportes.Consultar.Solicitud.Devolucion.idsAporte";

    /**
     * Constante con el nombre del query para consultar los datos de la devolucion
     */
    public static final String CONSULTAR_DATOS_DEVOLUCION = "Aportes.datos.devolucion";

    /**
     * Constante con el nombre del query para consultar los datos de la corrección
     */
    public static final String CONSULTAR_DATOS_CORRECCION = "Aportes.datos.correccion";

    /**
     * Constante con el nombre de la sentencia para el borrado de registros detallados novedad asociados a un registro general
     * */
    public static final String ELIMINAR_REGISTRO_DETALLADO_NOVEDAD_POR_REGISTRO_GENERAL = "Aportes.eliminar.RegistroDetalladoNovedad.idRegistroGeneral";

    /**
     * Constante con el nombre de la sentencia para el borrado de registros detallados asociados a un registro general
     * */
    public static final String ELIMINAR_REGISTRO_DETALLADO_POR_REGISTRO_GENERAL = "Aportes.eliminar.RegistroDetallado.idRegistroGeneral";

    /**
     * Constante con el nombre de la sentencia para el borrado de registros detallados asociados a un registro general
     * Aportes.eliminar.RegistroGeneral.id
     * */
    public static final String ELIMINAR_REGISTRO_GENERAL_ID = "Aportes.eliminar.RegistroGeneral.id";
    
    /**
     * Constante que representa la consulta de personas por un tipo de identificación y un listado de números de identificación
     * */
    public static final String CONSULTAR_PERSONAS_TIPO_ID_LISTA_NUM_ID = "Aportes.Consultar.Persona.buscar.tipoNumId";
    
    /**
     * Constante que representa la consulta de personas por un listado de IDs de persona
     * */
    public static final String CONSULTAR_PERSONAS_ID_LISTA_ID = "Aportes.Consultar.Persona.buscar.listaId";
    
    /**
     * Constante que representa la consulta de personas por un listado de IDs de persona
     * */
    public static final String CONSULTAR_PERSONAS_ID_LISTA_ID_EMPRESA = "Aportes.Consultar.Persona.buscar.listaIdEmpresa";
    
    /**
     * Constante que representa la consulta de empresas correspondientes a un listado de ID de persona
     * */
    public static final String CONSULTAR_EMPRESAS_ID_PERSONA_MASIVO = "Aportes.Consultar.Empresa.buscar.idPersona.masivo";
    
    /**
     * Constante que representa la consulta de empleadores correspondientes a un listado de ID de empresa
     * */
    public static final String CONSULTAR_EMPLEADORES_ID_EMPRESA_MASIVO = "Aportes.Consultar.Empleador.buscar.idEmpresa.masivo";
    
    /**
     * Constante que representa la consulta de empleadores correspondientes a un listado de ID de empresa
     * */
    public static final String CONSULTAR_EMPRESAS_ID_MASIVO = "Aportes.Consultar.Empresa.buscar.id.masivo";
    
    /**
     * Constante que representa la consulta de afiliados correspondientes a un listado de ID de persona
     * */
    public static final String CONSULTAR_AFILIADOS_ID_PERSONA_MASIVO = "Aportes.Consultar.Afiliado.buscar.idPersona.masivo";
    
    /**
     * Constante que representa la consulta de roles de afiliados correspondientes a un listado de ID de afiliado
     * */
    public static final String CONSULTAR_ROLES_AFILIADOS_ID_AFILIADO_MASIVO = "Aportes.Consultar.RolAfiliado.buscar.idAfiliado.masivo";

    /**
     * Constante que representa la consulta de sucursales de empresa correspondientes a un listado de llaves
     * */
    public static final String CONSULTAR_SUCURSAL_EMPRESA_LLAVE_MASIVO = "Aportes.Consultar.SucursalEmpresa.buscar.llave.masivo";

    /**
     * Constante que representa la consulta de personas con inconsistencia correspondientes a un listado de IDs de persona
     * */
    public static final String CONSULTAR_PERSONAS_INCONSISTENTES_LISTA = "Aportes.Consultar.RegistroPersonaInconsistente.buscar.id.masivo";

    /**
     * Constante que representa la consulta de planillas que no han finalizado y tampoco cuentan con un TemAporteProcesado
     * */
    public static final String CONSULTA_PLANILLAS_SIN_PROCESO = "Aportes.Consultar.PlanillaSinTemAporteProcesado";
    
    /**
     * Constante que representa la consulta nativa de novedades por listado de ID de registro detallado
     * */
    public static final String CONSULTAR_NOVEDADES_LISTA = "Aportes.Consultar.SolicitudNovedadPila.buscar.id.masivo";

    /**
     * Constante que representa la consulta de marca de planilla PILA Manual
     * */
    public static final String CONSULTA_MARCA_PLANILLA_MANUAL = "Aportes.Consultar.RegistroGeneral.marcaPilaManula";

    /**
     * Constante que representa la consulta para establecer la presencia de novedades en un listado de registros generales 
     * */
    public static final String CONSULTA_PRESENCIA_NOVEDADES_APORTE = "Aportes.Consultar.RegistroGeneral.tieneNovedades";

    /**
     * Constante que representa la consulta del aporte detallado original de una devolución o corrección 
     * */
    public static final String CONSULTAR_APORTE_DETALLADO_ORIGINAL = "Aportes.consultar.cuenta.aporte.original.cotizante";

    /**
     * Constante que representa la consulta de datos de planilla para información de aporte original en vistas 360
     * */
    public static final String CONSULTA_DATOS_PLANILLA_APORTE_ORIGINAL = "Aportes.Consultar.RegistroGeneral.datosPlanillaAporteOriginal";
    
    /**
     * Constante que representa la consulta de datos de planilla para información de aporte original en vistas 360
     * */
    public static final String CONSULTA_DATOS_PLANILLA_APORTE = "Aportes.Consultar.RegistroGeneral.datosPlanillaAporte";

    /**
     * Constante que representa la consulta de datos para el resumen de cierre de aportes
     * */
    public static final String CONSULTA_APORTES_CIERRE_RESUMEN_APORTES = "Aportes.Consultar.AporteGeneral.datosCierre.resumen";

    public static final String CONSULTA_APORTES_CIERRE_RESUMEN = "Aportes.Consultar.AporteGeneral.datosCierre.resumen.old";

    public static final String CONSULTA_APORTES_CIERRE_RESUMEN_REGISTRADOS = "Aportes.Consultar.AporteGeneral.datosCierre.resumen.registrados";

    public static final String CONSULTA_APORTES_CIERRE_RESUMEN_N = "Aportes.Consultar.AporteGeneral.datosCierre.resumen.n";

    /**
     * Constante que representa la consulta de registros generales por listado de IDs
     * */
    public static final String CONSULTAR_REGISTRO_GENERAL_POR_LISTADO_ID = "Aporte.Consultar.RegistroGeneral.listadoIds";
    public static final String CONSULTAR_REGISTRO_GENERAL_POR_LISTADO_ID_NATIVA = "Aporte.Consultar.RegistroGeneral.listadoIds.native";

    /**
     * Constante que representa la consulta de registros generales por número de planilla
     * */
    public static final String CONSULTAR_REGISTRO_GENERAL_POR_NUM_PLANILLA = "Aportes.RegistroGeneral.consultarPorNumPlanilla";

    /**
     * Constante que representa la consulta de numero de planilla por planilla anterior y registro detallado
     * */
    public static final String CONSULTAR_NUM_PLANILLA_POR_PLANILLA_ANT_REGISTRO = "Aportes.Consultar.NumeroPlanilla.PorPlanillaAsociada.RegistroDetallado";

/**
     * Constante que representa la consulta de numero de planilla por planilla anterior y registro detallado
     * */
    public static final String CONSULTAR_NUM_PLANILLA_POR_PLANILLA_COR_MOV = "Aportes.Consultar.NumeroPlanilla.PorMovCor.MovimientoAporte";
    /**
     * Constante que representa la consulta de numero de registro por aporte detallado
     * */
    public static final String CONSULTAR_REGISTRO_DETALLADO_POR_APORTE_DETALLADO = "Aportes.consultar.registroDetallado.por.aporteDetallado";

    /**
     * Constante que representa la consulta los valores de aportes e interes por aporte detallado
     * */
    public static final String CONSULTAR_APORTE_OBLIGATORIO_INTERES_POR_APORTE_DETALLADO = "Aportes.consultar.aporteOgligatorio.interes.por.aporteDetallado";

    /**
     * Constante que representa la consulta de índice de planilla por listado de IDs
     * */
    public static final String CONSULTAR_INDICE_PLANILLA_LISTA_ID = "Aporte.Consultar.IndicePlanilla.listadoIds";

    /**
     * Constante que representa la consulta de solicitud de aporte por listado de ids de registro general
     */
    public static final String CONSULTAR_SOLICITUD_APORTE_LISTADO_ID_REGGEN = "Aporte.Consultar.SolicitudAporte.ListadoId.RegistroGeneral";

    /**
     * Constante que representa la consulta de pago de subsidio para un listado de cotizantes 
     */
    public static final String CONSULTA_PAGO_SUBSIDIO_COTIZANTES = "Aporte.Consultar.pagoSubsidio.cotizante";

    /**
     * Constante que representa la consulta de solicitud de aporte por listado de ids de aporte general
     */
    public static final String CONSULTAR_SOLICITUD_APORTE_LISTADO_ID = "Aporte.Consultar.SolicitudAporte.ListadoId";
    
    /**
     * Constante que representa la consulta de la información histórica de los aportes para un afiliado
     */
    public static final String CONSULTAR_HISTORICO_APORTES_TIPO_AFILIACION = "Aportes.Consultar.historicoAportes.tipoAfiliacion";

    /**
     * Constante que representa la consulta de aportes a registrar con su cantidad de aportes
     * */
    public static final String CONSULTAR_PLANILLA_NUM_APORTES = "Aporte.Consultar.temAporte.planillas.numAportes";
    
    /**
     * Constante que representa la consulta de aportes a registrar con su cantidad de aportes
     * */
    public static final String CONSULTAR_PLANILLA_NUM_APORTES_IDPLANILLA = "Aporte.Consultar.temAporte.planillas.numAportes.idplanilla";

    /**
     * Constante que representa la consulta de novedades a registrar con su cantidad de aportes
     * */
    public static final String CONSULTAR_PLANILLA_NUM_NOVEDADES = "Aporte.Consultar.temAporte.planillas.numNovedades";
    
    /**
     * Constante que representa la consulta de novedades a registrar con su cantidad de aportes
     * */
    public static final String CONSULTAR_PLANILLA_NUM_NOVEDADES_IDPLANILLA = "Aporte.Consultar.temAporte.planillas.numNovedades.idplanilla";
    
    /**
     * Constante que representa la consulta de planillas a registrar con su cantidad de aportes
     * */
    public static final String CONSULTAR_PLANILLA_APORTES_A_PROCESAR = "Aporte.Consultar.temAporte.planillas.procesar";
    
    /**
     * Constante que representa la consulta de planillas a registrar con su cantidad de novedades
     * */
    public static final String CONSULTAR_PLANILLA_NOVEDADES_A_PROCESAR = "Aporte.Consultar.temNovedad.planillas.procesar";
    /**
     * Constante que representa la consulta de planillas a registrar con su cantidad de novedades po SP
     * */
    public static final String STORED_PROCEDURE_CONSULTAR_PLANILLA_NOVEDADES_A_PROCESAR = "SP.consulta.Aporte.Consultar.temNovedad.planillas.procesar";
    /** 
     * Consulta de sucursales por llave compuesta de tipo y número de ID de empresa y código de sucursal 
     * */
    public static final String CONSULTAR_SUCURSALES_EMPRESA_POR_LLAVE_ID = "Aporte.Consultar.SucursalEmpresa.listadoLlaves";

    /**
     * Sentencia para la actualización de los registros de temAporteProcesado
     * */
	public static final String ACTUALIZAR_TEM_APORTE_PROCESADO = "Aporte.Actualizar.TemAporteProcesado";

        /**
         * Sentencia para la actualización de los registros de temAporteProcesado
         **/
	public static final String ACTUALIZAR_TEM_APORTE_PROCESADO_IDPLANILLA = "Aporte.Actualizar.TemAporteProcesadoByIdPlanilla";
	/**
	 * Consulta de TemAporteProcesado para determinar el envío de comunicados
	 * */
	public static final String CONSULTAR_TEM_APORTE_PROCESADO = "Aporte.Consultar.TemAporteProcesado";

        /**
	 * Consulta de TemAporteProcesado para determinar el envío de comunicados
	 * */
	public static final String CONSULTAR_TEM_APORTE_PROCESADO_IDPLANILLA = "Aporte.Consultar.TemAporteProcesadoByIdPlanilla";
        
	/**
	 * Actualización de marca de proceso para aportes
	 * */
	public static final String MARCA_PROCESO_APORTES = "Aporte.Actualizar.TemAporte.marcarProceso";

	/**
	 * Actualización de marca de proceso para novedades
	 * */
	public static final String MARCA_PROCESO_NOVEDADES = "Aporte.Actualizar.TemNovedad.marcarProceso";
	
	/**
	 * Sentencia para la limpieza de registros de control de planillas terminadas
	 * */
	public static final String DELETE_CONTROL_EJECUCION_PILA = "Aportes.Eliminar.ControlEjecucionPlanillas";
	
	/**
	 * Constante que representa la consulta de los afiliados que tienen aportes futuros para un periodo dado. 
	 */
	public static final String CONSULTAR_PERSONAS_AFILIADAS_CON_APORTE_FUTURO_EN_PERIODO = "Aportes.Consultar.personasAfiliadasConAporteFuturoEnElPeriodo";
	
	/**
	 * Constante que representa la invocación al proceso almacenando USP_REP_CambioCategoriaAfiliado
	 */
	public static final String PROCEDURE_USP_REP_CAMBIO_CATEGORIA_AFILIADO = "Aportes.ejecutar.USP_REP_CambioCategoriaAfiliado";
	
	/**
	 * Constante que representa la invocación al proceso almacenando USP_SET_CalcularCategoriasAporteFuturo
	 */
	public static final String PROCEDURE_USP_REP_CAMBIO_CATEGORIA_APORTE_FUTURO = "Aportes.StoredProcedures.USP_SET_CalcularCategoriasAporteFuturo";
	

	/**
	 * Constante que representa el nombre de la consulta de un aportante ingresado en el proceso de corrección 
	 */
    public static final String CONSULTAR_APORTANTE_NUEVO_CORRECCION = "Aportes.consultar.aportante.nuevo.correccion";

    public static final String  CONSULTAR_ID_REGISTROS_DETALLADOS_FUTUROS = "Aportes.consultar.ids.aportes.futuros";

    public static final String PROCEDURE_USP_REP_CALCULO_CATEGORIAS_APORTES = "Aportes.StoredProcedures.USP_REP_CalcularCategoriaAportes";
    /**
	 * Constante que representa el nombre deL SP que copia los aportes 
	 */
    public static final String USP_COPIAR_DATOS_TEMPORALES_APORTES = "PilaService.StoredProcedures.USP_CopiarAportesDesdeTemporalPila";
    
    /**
	 * Constante que representa el nombre deL SP que copia los aportes 
	 */
    public static final String ASP_EXECUTEVALIDATENOVPILA = "PilaService.StoredProcedures.ASP_ExecuteValidateNovPila";
    
    /**
	 * Constante que representa el nombre de la consulta de las planillas de correccion con respecto a las originales
	 */
	public static final String CONSULTAR_PLANILLA_CORRECCION = "Aporte.Consultar.Planillas.Correccion";
    
    /**
     * Constante que representa la consulta de un registro de la tabla TasaInteresMora
     */
    public static final String CONSULTAR_TASA_INTERES_MORA_APORTES = "Aportes.consultar.tasaInteresMora";
    
    public static final String CONSULTAR_TASA_INTERES_MORA_APORTES_POR_PERIODO = "Aportes.consultar.tasaInteresMora.porPeriodo";

    /**
     * Constante que representa la consulta de los aportes generales de los aportes corregidos
     */
	public static final String CONSULTAR_NUEVOS_NUMERO_OPERACION_CORRECCION = "Aportes.consultar.nuevosNumerosOperacion.correccion";
	
	/**
     * Constante que representa la consulta de novedades futuras a registrar con su cantidad de aportes
     * */
    public static final String CONSULTAR_PLANILLA_NUM_NOVEDADES_FUTURAS = "Aporte.Consultar.temAporte.planillas.numNovedades.futuras";
    	/**
     * Constante PARA TRAER EL IDAFILIADO Y PODER REALIZAR EL CAMBIO CATEGORIA
     * */
    public static final String CONSULTAR_AFILIADOS_ID_PERSONA_CAMBIO_CATEGORIA = "Aporte.Consultar.idafiliado.cambio.categoria";
    

    public static final String INSERCION_LOG_MONITOREO_NOVEDADES = "novedades.composite.monitoreo.novedades";
    
 // --PRUEBA NOVEDADES PILA
    
    
    /**
     * Constante que se encarga de buscar un estado de cargue multipe
     */
    public static final String CONSULTAR_SOLICITUD_POR_ID_SOLICITUD_GLOBAL = "novedades.consultar.solicitud.por.idSolicitudGlobal";
    
    /**
     * Guarda los datos asociados a una excepcion de novedades
     */
    public static final String GUARDAR_EXCEPCION_NOVEDAD = "guardar.ExcepcionNovedad";

    /**
     * Constante con el nombre del procedimiento almacenado que actualiza en cartera las entidades que presentan impago y las saca de la
     * línea, si es el caso
     */
    public static final String PROCEDURE_USP_EXECUTE_CARTERA_ACTUALIZAR_CARTERA = "Cartera.StoredProcedures.USP_ExecuteCARTERAActualizarCartera";
    
    /**
     * Buscar un dato temporal para el id de solicitud especificado
     */
    public static final String SOLICITUDES_CONSULTAR_DATOS_TEMPORALES = "Solicitudes.consultar.datosTemporales";

    /**
     * Buscar metodo de asignacion parametrizado para proceso virtual
     */
    public static final String BUSCAR_METODO_ASIGNACION_PARAMETRIZADO_VIRTUAL = "AsignacionSolicitudes.buscar.metodoAsignacionParametrizadoVirtual";
    
    /**
     * Buscar metodo de asignacion parametrizado para proceso sede
     */
    public static final String BUSCAR_METODO_ASIGNACION_PARAMETRIZADO = "AsignacionSolicitudes.buscar.metodoAsignacionParametrizado";
    
    /**
     * NamedQuery para la consulta de solicitudes por los identificadores de la misma
     */
    public static final String BUSCAR_SOLICITUDES_POR_IDS = "afiliaciones.find.solicitudes.by.id";
    
    /** Consulta la información del aportante para procesar */
    public static final String CONSULTAR_APORTANTE_INFO_PROCESAR ="Aporte.consultar.infoAportante.procesar";
    
    /** Consulta la información de los cotizantes por crear */
    public static final String CONSULTAR_COTIZANTES_POR_CREAR ="Aporte.consultar.infoCotizante.porCrear";
    
    public static final String CONSULTAR_ESTADO_FORMALIZACION_DATOS_PLANILLA = "Aportes.consultar.PilaEstadoTransitoriaGestion";
    
    public static final String CONSULTAR_CANTIDAD_APORTES_TEMPORALES = "Aportes.consultar.CantidadAportesTemprales";

    public static final String CONSULTAR_LIBERACION_BLOQUE9 = "Aportes.consultar.liberarPlanillasBloque9";

    public static final String   CONSULTAR_CANTIDAD_NOTIFICACION_PLANILLAS_N = "Aportes.consultar.cantidad.notificacion.planillas.n";

    public static final String   CONSULTAR_MOVIMIENTO_HISTORICO_PILA = "Aportes.consultar.movimiento.historico.pila";

    public static final String CONSULTAR_MODIFICACIONES_POR_APORTE_DETALLADO = "Aportes.consultar.modificaciones.por.aporte.detallado";

    public static final String CONSULTAR_NUMERO_Y_CODIGO_CUENTA_BANCARIA = "Aportes.consultar.numero.y.codigo.cuenta.aporte.general";

    public static final String CONSULTAR_NUMERO_Y_CODIGO_CUENTA_BANCARIA_PLANILLA_N = "Aportes.consultar.numero.y.codigo.cuenta.aporte.general.planillaN";

    public static final String CONSULTAR_NUMERO_OPERACION_CORRECCION = "Aportes.consultar.numero.operacion.correccion";

    public static final String CONSULTA_APORTES_CIERRE_RESUMEN_APORTES_EXTEMPORANEOS = "Aportes.Extemporaneos.Consultar.AporteGeneral.datosCierre.resumen";

    public static final String CONSULTAR_DETALLE_APORTES_EXTEMPORANEOS_REGISTRADOS = "Aportes.consultar.detalle.registro.aportes.extemporaneos.registrados";

    public static final String CONSULTAR_DETALLE_APORTES_EXTEMPORANEOS_COTIZANTES_REGISTRADOS = "Aportes.consultar.detalle.registro.aportes.extemporaneos.cotizantes.registrados";

}
