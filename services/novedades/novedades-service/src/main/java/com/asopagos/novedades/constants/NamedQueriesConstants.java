package com.asopagos.novedades.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio <b>Historia de Usuario:</b> Transversal
 *
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class NamedQueriesConstants {

    /**
     * Constante con named query de consulta para consultar persona por numeroIdentificacion
     */
    public static final String CONSULTAR_PERSONAS_POR_NUMEROIDENTIFICACION = "novedades.consultar.persona.numeroIdentificacion";
    /**
     * Constante con named query de consulta para una novedad por tipo de
     * transacción.
     */
    public static final String BUSCAR_NOVEDAD_TIPO_TRANSACCION = "Novedad.tipoTransaccion";
    /**
     * Constante con named query de consulta para una novedad por tipo de
     * transacción.
     */
    public static final String BUSCAR_SOLICITUD_NOVEDAD_ID_SOLICITUD_NOVEDAD = "SolicitudNovedad.idSolicitud";
    /**
     * Constante encargada de consultar un empleador por su id
     */
    public static final String BUSCAR_EMPLEADOR_POR_ID = "novedades.buscar.empleador.por.id";

    /**
     * Constante encargada de consultar una sucursal empresa por su id
     */
    public static final String BUSCAR_SUCURSAL_EMPRESA_POR_ID = "novedades.buscar.sucursal.empresa.por.id";

    /**
     * consulta la lista de los departamentos en la base de datos
     */
    public static String CONSULTAR_DEPARTAMENTOS = "novedades.consultar.departamentos";

    /**
     * consulta la lista de los municipios en la base de datos
     */
    public static String CONSULTAR_MUNICIPIOS = "novedades.consultar.municipios";

    /**
     * Constante encargada de consultar los nomrbes de los campos parametrizados
     */
    public static final String BUSCAR_CAMPOS_ARCHIVO = "novedades.buscar.file.nombreCampos";
    /**
     * Constante que se encarga de consultar la persona por el id
     */
    public static final String BUSCAR_PERSONA_POR_ID = "novedades.buscar.persona.por.id";
    /**
     * Constante que se encarga de verificar el estado de identificador de
     * cargue multiple
     */
    public static final String ESTADO_IDENTIFICADOR_CARGUE_MULTIPLE_EMPLEADOR = "novedades.estado.identificador.cargue.multiple.empleador";
    /**
     * Constante que se encarga de consultar el estado de cargue multiple por id
     * del empleador
     */
    public static final String CONSULTAR_ESTADO_CARGUE_MULTIPLE_POR_ID_EMPLEADOR = "novedades.estado.cargue.multiple.por.id.empleador";
    /**
     * Constante que se encarga de buscar un estado de cargue multipe
     */
    public static final String BUSCAR_ESTADO_CARGUE_MULTIPLE_POR_ID = "novedades.estado.cargue.multiple.por.id";

    /**
     * consulta el departamento por ID
     */
    public static final String CONSULTAR_DEPARTAMENTO_POR_ID = "novedades.consultar.departamento.por.id";

    /**
     * consulta el numicipio por ID
     */
    public static final String CONSULTAR_MUNICIPIO_POR_ID = "novedades.consultar.municipio.por.id";

    /**
     * consulta afiliado por estado
     */
    public static final String CONSULTAR_AFILIADO_POR_ESTADO = "novedades.consultar.afiliado.por.estado";

    /**
     * Constante que se encarga de buscar un estado de cargue multipe
     */
    public static final String CONSULTAR_SOLICITUD_POR_ID_SOLICITUD_GLOBAL = "novedades.consultar.solicitud.por.idSolicitudGlobal";

    /**
     * Constante que se encarga de consultar las solicitudes de novedad
     * asociadas a una persona dado un canal de recepcion
     */
    public static final String CONSULTAR_SOLICITUDES_NOVEDAD_EMPLEADOR_CON_CANAL_RECEPCION = "novedades.consultar.solicitudes.novedad.empleador.con.canal.recepcion";

    /**
     * Constante que se encarga de consultar las solicitudes de novedad
     * asociadas a una persona sin un canal de recepcion
     */
    public static final String CONSULTAR_SOLICITUDES_NOVEDAD_EMPLEADOR_SIN_CANAL_RECEPCION = "novedades.consultar.solicitudes.novedad.empleador.sin.canal.recepcion";

    /**
     * Constante que se encarga de consultar la identificacion de la persona asociada a una solicitude de novedad
     */
    public static final String CONSULTAR_IDENTIFICACION_SOLICITUDES_NOVEDAD = "novedades.consultar.identificacion.novedad";

    /**
     * Constante que se encarga de consultar la identificacion del beneficiario asociada a una solicitude de novedad
     */
    public static final String CONSULTAR_IDENTIFICACION_SOLICITUDES_NOVEDAD_BENEFICIARIO = "novedades.beneficiario.consultar.identificacion.novedad";


    /**
     * Constante que se encarga de consultar las solicitudes de novedad
     * asociadas a una persona sin un canal de recepcion
     */
    public static final String CONSULTAR_SOLICITUDES_NOVEDAD_PERSONA_SIN_CANAL_RECEPCION = "novedades.consultar.solicitudes.novedad.persona.sin.canal.recepcion";

    /**
     * Constante que se encarga de consultar las solicitudes de novedad
     * asociadas a una persona dado un canal de recepcion
     */
    public static final String CONSULTAR_SOLICITUDES_NOVEDAD_PERSONA_CON_CANAL_RECEPCION = "novedades.consultar.solicitudes.novedad.persona.con.canal.recepcion";

    /**
     * Constante que se encarga de consultar las novedades dado un proceso
     */
    public static final String CONSULTAR_NOVEDADES = "novedades.consultar.consultarNovedades";
    /**
     * Constante que se encarga de insertar los registros de las personas inconsistentes.
     */
    public static final String INSERTAR_REGISTRO_PERSONA_INCOSISTENTE = "novedades.guardar.registros.persona.inconsistentes.supervivencia";

    /**
     * Constante que se encarga de consultar los registros de las personas
     * inconsistentes que se encontraron al momento de consultar la
     * suvervivencia de ellos
     */
    public static final String CONSULTAR_REGISTROS_PERSONAS_INCONSISTENTE_POR_TIPO_Y_NUMERO_IDENTIFICACION_FECHAINGRESO = "novedades.consultar.registros.novedad.persona.inconsistentes.tipo.numeroIdentificacion.fechaingreso";

    /**
     * constante que se encarga de consultar informacion txt generarReporteAfiSupervivenciaJson
     */
    public static final String CONSULTAR_GENERAR_REPORTE_AFILIACION_SUPERVIVENCIA = "novedades.consultar.reporte.afiliacion.supervivencia";

    /**
     * Constante que se encarga de consultar los registros de las personas
     * inconsistentes que se encontraron al momento de consultar la
     * suvervivencia de ellos
     */
    public static final String CONSULTAR_REGISTROS_PERSONAS_INCONSISTENTE_POR_TIPO_Y_NUMERO_IDENTIFICACION = "novedades.consultar.registros.novedad.persona.inconsistentes.tipo.numeroIdentificacion";

    /**
     * Constante que se encarga de consultar los registros de las personas
     * inconsistentes que se encontraron al momento de consultar la
     * suvervivencia de ellos
     */
    public static final String CONSULTAR_REGISTROS_PERSONAS_INCONSISTENTE_POR_FECHAINGRESO = "novedades.consultar.registros.novedad.persona.inconsistentes.fechaingreso";

    /**
     * Constante que se encarga de consultar los registros de las personas
     * inconsistentes que se encontraron al momento de consultar la
     * suvervivencia de ellos
     */
    public static final String CONSULTAR_REGISTROS_PERSONAS_INCONSISTENTE = "novedades.consultar.registros.novedad.persona.inconsistentes";
    /**
     * Constante que se encarga de consultar los registros de las personas
     * inconsistentes que se encontraron al momento de consultar la
     * suvervivencia de ellos
     */
    public static final String CONSULTAR_REGISTROS_PERSONAS_INCONSISTENTE_POR_ID = "novedades.consultar.registros.novedad.persona.inconsistentes.id";

    /**
     * Constante que se encarga de consultar los registros de las personas
     * inconsistentes que se encontraron al momento de consultar la
     * suvervivencia de ellos
     */
    public static final String CONSULTAR_PERSONAS_POR_TIPO_NUMEROIDENTIFICACION = "novedades.consultar.persona.tipo.numeroIdentificacion";

    public static final String CONSULTAR_PERSONAS_POR_NUMEROIDENTIFICACIONTIPO = "novedades.consultar.persona.numeroIdentificacionTipo";
    /**
     * Constante encargada de consultar un cargue multiple de supervivencia por Id
     */
    public static final String CONSULTAR_CARGUE_MULTIPLE_SUPERVIVENCIA_POR_ID = "novedades.consultar.cargue.multiple.supervivencia.por.id";

    /**
     * Consulta las solicitudes de Novedad Diferentes a CERRADA que sean Carga Multiple y se haya cumplido el plazo para Desistir.
     */
    public static final String CONSULTAR_SOLICITUDES_DESISTIR = "novedades.personas.consultarSolicitudesDesistir";

    /**
     * Consulta si existen registros de inconsistencia para una persona, caso en el cual devuelve TRUE
     */
    public static final String CONSULTAR_HAY_REGISTRO_PERSONA_INCONSISTENTE = "novedades.personas.consultarHayRegistroPersonaInconsistente";

    /**
     * Consulta los codigos CIIU que representan las actividades economicas
     */
    public static final String CONSULTAR_CODIGOS_CIIU = "novedades.consultar.codigosCIIU";

    /**
     * Consulta los grados academicos existentes en el sistema
     */
    public static final String CONSULTAR_GRADOS_ACADEMICOS = "novedades.consultar.gradosAcademicos";

    /**
     * Consulta los AFP existentes en el sistema
     */
    public static final String CONSULTAR_AFP = "novedades.consultar.AFP";

    /**
     * Consulta el cargue de archivo actualizacion por ID
     */
    public static final String CONSULTAR_CARGUE_ACTUALIZACION_BY_ID = "novedades.consultar.cargue.archivo.actualizacion.id";

    /**
     * Consulta la diferencia de cargue de archivo actualizacion por ID
     */
    public static final String CONSULTAR_DIFERENCIA_CARGUE_ACTUALIZACION_BY_ID = "novedades.consultar.diferencia.cargue.archivo.actualizacion.id";

    /**
     * Consulta la solicitud novedad persona con el identificador de la solicitud novedad previamente registrada
     */
    public static final String CONSULTAR_NOVEDAD_PERSONA_BY_ID_SOLICITUD_NOVEDAD = "consultar.novedad.persona.id.solicitud.novedad";

    /**
     * Consulta los registros de novedad futura sin procesar
     */
    public static final String CONSULTAR_NOVEDAD_FUTURA_SIN_PROCESAR = "consultar.registro.novedades.futuras";

    /**
     * Consulta los registros de novedades para el empleador
     */
    public static final String CONSULTAR_NOVEDADES_EMPLEADOR = "consultar.Novedades.Empleador";

    /**
     * Consulta las novedades Vigentes de una persona
     */
    public static final String CONSULTAR_NOVEDAD_VIGENTE_PERSONA = "novedades.fovis.consultar.NovedadesVigentesPersona";

    /**
     * Consulta Condición de Invalidez de persona
     */
    public static final String CONSULTAR_CONDICION_INVALIDEZ_PERSONA = "novedades.fovis.consultar.CondicionInvalidez";

    /**
     * Consulta Novedades Registradas a la persona.
     */
    public static final String CONSULTAR_NOVEDADES_REGISTRADAS_PERSONA = "novedades.consultar.NovedadesRegistradasPersona";

    /**
     * Consulta Novedades Registradas a los Beneficiarios de un Afiliado.
     */
    public static final String CONSULTAR_NOVEDADES_REGISTRADAS_BENEFICIARIO = "novedades.consultar.NovedadesRegistradasBeneficiario";

    /**
     * Constante para la consulta de estado de la persona por su identificador
     */
    public static final String CONSULTAR_ESTADO_APORTANTE_BY_ID_PERSONA = "consultar.estado.aportante.caja.by.id.persona";

    /**
     * Consulta el consecutivo de la novedad de cascada
     */
    public static final String CONSULTAR_CONSECUTIVO_CASCADA_RADICADO = "consultar.consecutivoNovedadCascada";

    /**
     * Guarda los datos asociados a una excepcion de novedades
     */
    public static final String GUARDAR_EXCEPCION_NOVEDAD = "guardar.ExcepcionNovedad";

    /**
     * TEMP : Consulta la data asociada a afiliados inactivos con beneficiarios activos (PILA)
     */
    public static final String CONSULTAR_DATA_AFILIADOS_INACTIVOS_BENEFICIARIOS_ACTIVOS = "consultar.dataAfiliadosInactivosConBeneficiariosActivos";

    /**
     * Consulta las novedades asociadas a una persona (Excluyendo el estado "Guardada", producto de novedades de cascada o automaticas)
     */
    public static final String CONSULTAR_NOVEDADES_REGISTRADAS_PERSONA_INT_NG = "novedades.consultar.NovedadesRegistradasPersonaIntNG";

    public static final String STORED_PROCEDURE_CONSULTAR_NOVEDADES_REGISTRADAS_PERSONA_INT_NG = "stored.procedure.novedades.consultar.NovedadesRegistradasPersonaIntNG";
    /**
     * Consulta los registros de novedades para el empleador para la vista 360 aprobadas
     */
    public static final String CONSULTAR_NOVEDADES_EMPLEADOR_VISTA_360 = "consultar.Novedades.Empleador.Vista360";

    /**
     * Consulta las solicitudes de novedad sin instancia de proceso asociada
     */
    public static final String BUSCAR_NOVEDADES_SIN_INSTANCIA_PROCESO = "novedades.consultar.solicitudesSinInstanciaProceso";

    /**
     * Consulta las novedades asociadas a una persona (Futuras sin ser registradas aun)
     */
    public static final String CONSULTAR_NOVEDADES_FUTURAS_PERSONA_INT_NG = "novedades.consultar.NovedadesFuturasPersonaIntNG";

    /**
     * Constante que se encarga de consultar los registros de las personas
     * inconsistentes que se encontraron al momento de consultar la
     * suvervivencia de ellos
     */
    public static final String TRANACCION_NOVEDAD_PILA_COMPLETA = "transaccion.novedad.pila.completa";

    /**
     * Guarda los datos asociados a una excepcion de novedades
     */
    public static final String GUARDAR_EXCEPCION_NOVEDAD_PILA = "guardar.ExcepcionNovedad.pila";

    /**
     * Consulta si la novedad es retroactiva
     */
    public static final String CONSULTAR_RETROACTIVIDAD_NOVEDAD = "consultar.novedad.retroactiva.by.RegistroDetallado";

    /**
     * Val 3: Se valida el campo No 1 Tipo Iden - No 2 Num iden del admin sub corresponda a una persona que es o fue administrador de subsidio en Genesys
     */
    public static final String VALIDACION_3_REGISTRO_CONF_ABONO = "consultar.persona.admin.sub.tipo.num";
    /**
     * Val 4: Se valida que el campo No 3 - casId corresponda a una persona que es o fue administrador de subsidio en Genesys.
     */
    public static final String VALIDACION_4_REGISTRO_CONF_ABONO = "consultar.persona.admin.sub.casId";
    /**
     * Val 5: Se valida que el campo No 3 - casId este relacionado a un “Medio de pago” igual a “TRANSFERENCIA” con estado de la transacción igual a “ENVIADO”
     */
    public static final String VALIDACION_5_REGISTRO_CONF_ABONO = "consultar.casId.medio.pago";
    /**
     * Val 6: Se valida que el campo No 1 tipo Iden - No 2 Num iden este asociado al administrador de subsidio
     */
    public static final String VALIDACION_6_REGISTRO_CONF_ABONO = "consultar.asociado.admin.sub";
    /**
     * Val 7 : Se valida que el campo No 1 Tipo iden - No 2 Num iden sea el mismo que el del administrador de subsidio
     */
    public static final String VALIDACION_7_REGISTRO_CONF_ABONO = "consultar.persona.igual.admin.sub";
    /**
     * Se valida que el campo No tipo cuenta este asociado al titular de la cuenta
     */
    public static final String VALIDACION_8_REGISTRO_CONF_ABONO = "consultar.tipo.cuenta.titular";
    /**
     * Se valida que el campo No Numero cuenta exista en Genesys y este asociado al titular de la cuenta
     */
    public static final String VALIDACION_9_REGISTRO_CONF_ABONO = "consultar.numero.cuenta.exits";
    /**
     * Se valida que el campo No Valor transferencia sea igual al “valor real de la transacción” del abono que existe en Genesys
     */
    public static final String VALIDACION_10_REGISTRO_CONF_ABONO = "consultar.valor.transaccion.genesys";

    /*SP encargado de crear solicitud,solicitudNovedad, solicitudNovedadPersona, actualizacion de estados. para retiros de 
    trabajadores masivamente en motivo desafiliaion al retirarse una empresa*/
    public static final String PROCEDURE_USP_DESAFILIACION_EMPLEADOR_TRABAJADORES_MASIVOS = "procedure.usp.desafiliacion.empleador.trabajadores.masivo";

    public static final String NUMERO_IDENTIFICACION_NIT_CAJA = "consultar.numero.identificacion.caja.nit";

    public static final String CONSULTAR_TITULAR_CUENTA_ADMINISTTRADOR_SUBSIDIO_CASID = "consultar.titular.cuenta.admonSubsidio.casid";

    /**
     * Constante con named query de consulta para consultar todos los juzgados glpi 65540
     */
    public static final String CONSULTAR_TODOS_LOS_JUZGADOS = "novedades.consultar.todosLosJuzgados";
    /**
     * Constante con named query de consulta una persona detalle por numero de identificacion
     */
    public static final String UPDATE_PERSONA_DETALLE_FALLECIDO = "novedades.update.persona.detalle.fallecido";
      /**
     * Constante con named query de consulta una persona detalle por numero de identificacion
     */
    public static final String CONSULTAR_PERSONA_EXISTE = "consultar.persona.existe";
    /**
     * Constante con named query de consulta una persona detalle por numero de identificacion
     */
    public static final String CONSULTAR_PENSIONADO_25_ANIOS = "consultar.pensionado.25.anios";
    /**
     * Constante con named query de consulta una persona detalle por numero de identificacion
     */
    public static final String CONSULTAR_TIEMPOS_MULTIAFILIACION = "procedure.usp.consultar.tiempos.multiafiliacion";

    public static final String CONSULTA_HALLAZGOS_CONFIRMACION_ABONOS = "consultar.hallazgos.confirmacion.abonos";

    public static final String CONSULTA_RESPUESTAS_CONFIRMACION_ABONOS = "consultar.respuestas.confirmacion.abonos";

    public static final String OBTENER_EMPLEADORES_POR_PROCESAR = "Obtener.empleadores.procesar"; 
    
    //82800
    public static final String CONSULTAR_DATOS_EMPLEADOR_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION = "Consultar.datos.empleador.por.numero.documento.y.tipo.documento";
    public static final String CONSULTAR_DATOS_PERSONA_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION = "Consultar.datos.persona.por.numero.documento.y.tipo.documento";

    //95241
    public static final String CONSULTAR_CORREO_EMPLEADOR_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION = "Consultar.correo.empleador.por.numero.documento.y.tipo.documento";
    public static final String CONSULTAR_CORREO_PERSONA_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION = "Consultar.correo.persona.por.numero.documento.y.tipo.documento";
    
    //96686
    public static final String CONSULTAR_PERSONA_NUMERO_EXISTE_POR_NUMERO = "Consultar.persona.numero.existe.por.numero";
    public static final String CONSULTAR_PERSONA_EXISTE_POR_NUMERO_EXCLUYENDO_ACTUAL = "Consultar.persona.numero.existe.por.numero.excluyendo.actual";
    //96686 Consultas Afiliado 
    public static final String CONSULTAR_DATOS_AFILIADO_EXISTE_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION = "Consultar.datos.afiliado.existe.por.numero.identificacion.y.tipo.identificacion";
    public static final String CONSULTAR_DATOS_AFILIADO_EXISTE_COMO_EMPLEADOR_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION = "Consultar.datos.afiliado.existe.como.empleador.por.numero.identificacion.y.tipo.identificacion";
    public static final String CONSULTAR_NOVEDADES_ACTIVAS_AFILIADO_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION = "Consultar.novedades.activas.afiliado.por.numero.identificacion.y.tipo.identificacion";
    public static final String CONSULTAR_DATOS_AFILIADO_FALLECIDO_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION = "Consultar.datos.afiliado.fallecido.por.numero.identificacion.y.tipo.identificacion";
    //96686 Consultas Beneficiario 
    public static final String CONSULTAR_DATOS_BENEFICIARIO_EXISTE_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION = "Consultar.datos.beneficiario.existe.por.numero.identificacion.y.tipo.identificacion";
    public static final String CONSULTAR_NOVEDADES_ACTIVAS_BENEFICIARIO_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION = "Consultar.novedades.activas.beneficiario.por.numero.identificacion.y.tipo.identificacion";
    public static final String CONSULTAR_DATOS_BENEFICIARIO_FALLECIDO_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION = "Consultar.datos.beneficiario.fallecido.por.numero.identificacion.y.tipo.identificacion";

    // ================= MASIVO TRANSFERENCIA
    public static final String VERIFICAR_CODIGO_BANCO = "verificar.codigo.banco.archivo.masivo";

    public static final String VALIDAR_AFILIADO_ADMINISTRADOR = "validaciones.afiliado.principal.novedadMasiva.transfeerncia";

    public static final String CONSULTAR_SOLICITUDES_NOVEDAD_EMPLEADOR_SIN_CANAL_RECEPCION_NATIVE = "novedades.consultar.solicitudes.novedad.empleador.sin.canal.recepcion.native";

    public static final String VALIDAR_RELACION_AFILIADO_CERTIFICADOS_MASIVOS = "validaciones.relacion.afiliado.cetificadosMasivos";
    
    public static final String CONSULTAR_SOLICITUDES_NOVEDADES_POR_EMPLEADOR_CERRADAS = "novedades.consultar.solicitudes.empleador.cerradas";
    
    public static final String CONSULTAR_SOLICITUDES_NOVEDADES_POR_EMPLEADOR = "novedades.consultar.solicitudes.novedad.empleador";

}
