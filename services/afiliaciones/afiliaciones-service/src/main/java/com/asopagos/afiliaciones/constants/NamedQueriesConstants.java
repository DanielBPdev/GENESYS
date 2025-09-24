package com.asopagos.afiliaciones.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * Clase que contiene las constantes con los nombres de los NamedQueries del
 * servicio <b>Módulo:</b> Asopagos - HU <br/>
 * Transversal
 *
 * @author Josuè Nicoàs Pinzòn Villamil
 *         <a href="mailto:jopinzon@heinsohn.com.co"> jopinzon@heinsohn.com.co
 *         </a>
 */
public class NamedQueriesConstants {

    /**
     * Buscar asignacion a ser escalada
     */
    public static final String BUSCAR_ASIGNACION_ESCALADA = "Afiliaciones.escalar.buscarAsignacion";

    /**
     * Buscar productos no conformes
     */
    public static final String BUSCAR_NO_CONFORME_ESTADO = "Afiliaciones.noConforme.buscarEstado";

    /**
     * Buscar productos no conformes
     */
    public static final String BUSCAR_NO_CONFORME = "Afiliaciones.noConforme.buscarAll";

    /**
     * Buscar productos no conformes
     */
    public static final String BUSCAR_ETIQUETA = "Afiliaciones.Etiqueta.ExisteEtiqueta";

    /**
     * Buscar personas
     */
    public static final String BUSCAR_EMPLEADOR = "Afiliaciones.solictud.empleador";

    /**
     * Buscar identificador de solicitud
     */
    public static final String BUSCAR_EMPLEADOR_ID = "Afiliaciones.solictud.empleadorID";

    /**
     * Buscar solicitud por persona
     */
    public static final String BUSCAR_SOLICTUD = "Afiliaciones.afiliacion.buscarSolicitud";

    // /**
    // * NamedQuery para actualizar el estado de la solicitud de afiliación del
    // * empleador
    // */
    // public static final String ACTUALIZAR_ESTADO_SOLICITUD_AFILIACION_EMPLEADOR =
    // "Afiliaciones.empleadores.solicitud.actualizarEstado";

    // /**
    // * NamedQuery para actualizar el estado de la documentación de afiliación
    // * del empleador
    // */
    // public static final String
    // ACTUALIZAR_ESTADO_DOCUMENTACION_AFILIACION_EMPLEADOR =
    // "Afiliaciones.empleadores.solicitud.actualizarDocumentacion";

    // TRA-428
    /**
     * Buscar una etiqueta pre impresa disponible y por tipo dado
     */
    public static final String BUSCAR_CODIGO_PRE_IMPRESO_POR_TIPO = "Afiliaciones.etiquetaPreimpresa.disponible.porTipo";

    /**
     * Buscar solicitud por estado estado de documentos y rango de fechas
     */
    public static final String BUSCAR_ULTIMO_CODIGO_PRE_IMPRESO_POR_TIPO = "Afiliaciones.etiquetaPreimpresa.ultimaGenerada.porTipo";

    // /**
    // * Buscar una etiqueta pre impresa disponible y por tipo dado
    // */
    // public static final String ACTUALIZAR_ASIGNADO_CODIGO_PRE_IMPRESO =
    // "Afiliaciones.etiquetaPreimpresa.actualizar.estado";

    /**
     * Buscar una etiqueta pre impresa disponible y por tipo dado
     */
    public static final String CONSULTAR_ACTUALIZAR_ASIGNADO_CODIGO_PRE_IMPRESO = "Afiliaciones.etiquetaPreimpresa.consultar.actualizar.estado";

    /**
     * Radica la solicitud actualizando el número de radicado y la fecha de
     * radicado
     */
    // public static final String RADICAR_SOLICITUD_GLOBAL =
    // "Afiliaciones.radicar.solicitud";

    // TRA-087
    /**
     * Buscar solicitud por número de radicado
     */
    public static final String BUSCAR_SOLICTUD_POR_NUMERO_RADICADO = "Afiliaciones.afiliacionEmpleador.buscarSolicitud.porNumeroRadicado";

    // /**
    // * NamedQuery para buscar las solicitudes pendientes de aprobación de
    // * consejo por un rango de fechas
    // */
    // public static final String ELIMINAR_PRODUCTO_NO_CONFORME =
    // "Afiliaciones.productoNoConforme.eliminar";

    /**
     * NamedQuery encargado de buscar un empleador por empresa persona y número
     * de identificación
     */
    public static final String BUSCAR_EMPLEADOR_EMPRESA_PERSONA_TIPO_Y_NUMERO_IDENTIFICACION = "Afiliaciones.empleador.empresa.persona.tipoIdentificacion.numeroIdentificacion";

    /**
     * NamedQuery encargado de buscar las solicitudes de una persona dado el rol de
     * afiliación ordenadas por fecha de radicación descendente
     */
    public static final String BUSCAR_SOLICITUDES_AFILIACION_PERSONA = "Afiliaciones.empleador.persona.idRolAfiliado.solicitudesAfiliacionPersona";

    /**
     * NamedQuery encargado de buscar las solicitudes de una persona dado el rol de
     * afiliación ordenadas por fecha de radicación descendente
     */
    public static final String BUSCAR_SOLICTUD_NOVEDAD_POR_ID_SOLICITUD = "Afiliaciones.solicitudNovedad.buscarSolicitud.idSolicitudGlobal";

    /**
     * NamedQuery encargado de buscar las solicitudes de una persona dado el rol de
     * afiliación ordenadas por fecha de radicación descendente
     */
    public static final String BUSCAR_SOLICTUD_EMPLEADOR_POR_ID_SOLICITUD = "Afiliaciones.solicitudEmpleador.buscarSolicitud.idSolicitudGlobal";

    /**
     * NamedQuery encargado de buscar las solicitudes de una persona dado el rol de
     * afiliación ordenadas por fecha de radicación descendente
     */
    public static final String BUSCAR_SOLICTUD_PERSONA_POR_ID_SOLICITUD = "Afiliaciones.solicitudPersona.buscarSolicitud.idSolicitudGlobal";
    /**
     * NamedQuery encargado de buscar las solicitudes de postulación por Id de
     * Solicitud Global
     */
    public static final String BUSCAR_SOLICTUD_POSTULACION_POR_ID_SOLICITUD = "Afiliaciones.solicitudPostulacion.buscarSolicitud.idSolicitudGlobal";

    /**
     * NamedQuery encargado de buscar el intento de afiliacion fallido de una
     * solicitud global
     */
    public static final String BUSCAR_INTENTO_AFILIACION_POR_ID_SOLICITUD = "Afiliaciones.solicitud.buscarIntentoAfiliacion.idSolicitudGlobal";

    /**
     * NamedQuery encargado de buscar el municipio dado su código
     */
    public static final String BUSCAR_MUNICIPIO_UBICACION_PERSONA = "Afiliaciones.Municipio.buscarMunicipioPorCodigo";

    /**
     * NamedQuery encargado de buscar el id de una solicitud global dado un id de
     * rol afiliado
     */
    public static final String BUSCAR_ID_SOLICITUD_GLOBAL = "Afiliaciones.solicitud.buscarIdSolicitudGlobal";

    /**
     * NamedQuery encargado de buscar las solicitudes de una persona dado el rol de
     * afiliación ordenadas por fecha de radicación descendente
     */
    public static final String BUSCAR_SOLICTUD_APORTES_POR_ID_SOLICITUD = "Afiliaciones.solicitudAportes.buscarSolicitud.idSolicitudGlobal";

    /**
     * NamedQuery encargado de buscar las solicitudes de una persona dado el rol de
     * afiliación ordenadas por fecha de radicación descendente
     */
    public static final String BUSCAR_SOLICTUD_CORRECION_APORTE_POR_ID_SOLICITUD = "Afiliaciones.solicitudAportesCorrecion.buscarSolicitud.idSolicitudGlobal";

    /**
     * NamedQuery encargado de buscar las solicitudes de una persona dado el rol de
     * afiliación ordenadas por fecha de radicación descendente
     */
    public static final String BUSCAR_SOLICTUD_DEVOLUCION_APORTE_POR_ID_SOLICITUD = "Afiliaciones.solicitudDevolucionAporte.buscarSolicitud.idSolicitudGlobal";

    /**
     * NamedQuery encargado de buscar las solicitudes de una persona dado el rol de
     * afiliación ordenadas por fecha de radicación descendente
     */
    public static final String BUSCAR_SOLICTUD_ENTIDAD_PAGADORA_POR_ID_SOLICITUD = "Afiliaciones.solicitudEntidadPagadora.buscarSolicitud.idSolicitudGlobal";

    /**
     * NamedQuery encargado de buscar las solicitudes de una persona dado el rol de
     * afiliación ordenadas por fecha de radicación descendente
     */
    public static final String BUSCAR_SOLICTUD_PREVENTIVA_POR_ID_SOLICITUD = "Afiliaciones.solicitudPreventiva.buscarSolicitud.idSolicitudGlobal";

    /**
     * NamedQuery encargado de buscar una solicitud de fiscalización por
     * identificador de la misma
     */
    public static final String BUSCAR_SOLICITUD_FISCALIZACION_POR_ID_SOLICITUD = "Afiliaciones.solicitudFiscalizacion.buscarSolicitud.idSolicitudGlobal";

    /**
     * NamedQuery encargado de buscar la clasificación de la persona
     */
    public static final String BUSCAR_CLASIFICACION_AFILIACION_PERSONA = "Afiliaciones.persona.tipo.numero.identificacion.solicitudesAfiliacionPersona";

    /**
     * NamedQuery para la consulta de solicitudes por los identificadores de la
     * misma
     */
    public static final String BUSCAR_SOLICITUDES_POR_IDS = "afiliaciones.find.solicitudes.by.id";

    /**
     * NamedQuery encargado de buscar la solicitud de novedad fovis por Id de
     * Solicitud Global
     */
    public static final String BUSCAR_SOLICTUD_NOVEDAD_FOVIS_POR_ID_SOLICITUD = "Afiliaciones.solicitudNovedadFovis.buscarSolicitud.idSolicitudGlobal";

    /**
     * NamedQuery encargado de buscar las solicitudes de una persona dado el rol de
     * afiliación ordenadas por fecha de radicación descendente
     * NamedQuery encargado de buscar la solicitud de desafiliacion por Id de
     * Solicitud Global
     */
    public static final String BUSCAR_ULTIMA_SOLICITUD_AFILIACION_PERSONA = "Afiliaciones.empleador.persona.idRolAfiliado.ultimaSolicitudAprobada";

    public static final String BUSCAR_SOLICTUD_DESAFILIACION_POR_ID_SOLICITUD = "Afiliaciones.solicitudDesafiliacion.buscarSolicitud.idSolicitudGlobal";
    /**
     * NamedQuery encargado de buscar la solicitud de gestion cobro electronico por
     * Id de Solicitud Global
     */
    public static final String BUSCAR_SOLICTUD_GESTION_COBRO_ELECTRONICO_POR_ID_SOLICITUD = "Afiliaciones.solicitudGestionCobroElectronico.buscarSolicitud.idSolicitudGlobal";
    /**
     * NamedQuery encargado de buscar la solicitud de gestion cobro fisico por Id de
     * Solicitud Global
     */
    public static final String BUSCAR_SOLICTUD_GESTION_COBRO_FISICO_POR_ID_SOLICITUD = "Afiliaciones.solicitudGestionCobroFisico.buscarSolicitud.idSolicitudGlobal";
    /**
     * NamedQuery encargado de buscar la solicitud de gestion cobro manual por Id de
     * Solicitud Global
     */
    public static final String BUSCAR_SOLICTUD_GESTION_COBRO_MANUAL_POR_ID_SOLICITUD = "Afiliaciones.solicitudGestionCobroManual.buscarSolicitud.idSolicitudGlobal";

    /**
     * Constante para la busqueda de la solicitud de asignacion fovis
     */
    public static final String BUSCAR_SOLICTUD_ASIGNACION_FOVIS_POR_ID_SOLICITUD = "Afiliaciones.solicitudAsignacionFOVIS.buscarSolicitud.idSolicitudGlobal";

    /**
     * Constante para la busqueda de la solicitud de legalizacion fovis
     */
    public static final String BUSCAR_SOLICTUD_LEGALIZACION_DESEMBOLSO_FOVIS_POR_ID_SOLICITUD = "Afiliaciones.solicitudLegalizacionDesembolsoFOVIS.buscarSolicitud.idSolicitudGlobal";

    /**
     * Constante para la buqueda de la solicicutd de analisis de novedad de persona
     * en fovis
     */
    public static final String BUSCAR_SOLICITUD_NOVEDAD_FOVIS_ANALISIS_NOVEDAD_PERSONA = "Afiliaciones.solicitudAnalisisNovedadFovis.buscarSolicitud.idSolicitudGlobal";

    /**
     * Constante para la busqueda de la solicicutd de verificacion fovis
     */
    public static final String BUSCAR_SOLICITUD_VERIFICACION_FOVIS = "Afiliaciones.solicitudVerificacionFovis.buscarSolicitud.idSolicitudGlobal";

    /**
     * Constante para la busqueda de la solicicutd de gestion de cruces internos
     * fovis
     */
    public static final String BUSCAR_SOLICITUD_GESTION_CRUCE_FOVIS_POR_ID_SOLICITUD = "Afiliaciones.solicitudGestionCruceFovis.buscarSolicitud.idSolicitudGlobal";

    /**
     * Constante para la busqueda del historico de intentos de afiliación de un
     * empleador
     */
    public static final String CONSULTAR_INTENTOS_AFILIACION_EMPLEADOR = "Afiliaciones.consultar.intentosAfiliacionEmpleador";

    /**
     * Constante para la busqueda d ela solicitud de cierre del recaudo
     */
    public static final String BUSCAR_SOLICTUD_CIERRE_POR_ID_SOLICITUD = "Afiliaciones.solicitudCierre.buscarSolicitud.idSolicitudGlobal";

    /**
     * Constante para la consulta de busqueda de solicitudes por numero de radicado
     * estado documento y medio de envio
     */
    public static final String BUSCAR_SOLICITUD_POR_NRO_RADICADO_ESTADO_DOCUMENTO = "buscar.solicitud.by.nro.radicado.metodoEnvio.estadoDocumento";

    /**
     * Constante que representa la consulta del histórico de afiliación de una
     * persona como dependiente
     */
    public static final String CONSULTAR_HISTORICO_AFI_DEP = "consultar.Afiliacion.historicoAfiliacionDependiente";

    /**
     * Constante que representa la consulta del histórico de afiliación de una
     * persona como independiente
     */
    public static final String CONSULTAR_HISTORICO_AFI_INDEP = "consultar.Afiliacion.historicoAfiliacionIndependiente";

    /**
     * Constante que representa la consulta del histórico de afiliación de una
     * persona como pensionado
     */
    public static final String CONSULTAR_HISTORICO_AFI_PEN = "consultar.Afiliacion.historicoAfiliacionPensionado";

    /**
     * Constante que representa la consulta de un registro en la lista de especial
     * revision
     */
    public static final String BUSCAR_REGISTRO_LISTA_ESPECIAL_REVISION = "consultar.Afiliacion.registroEnListaDeEspecialRevision";

    /////
    // inicio NamedQueriesConstants para servicios del catálogo de integración
    /////

    /**
     * Constante para la consulta de la información básica de la persona respecto a
     * la afiliación por afiliado y beneficiarios
     */
    public static final String OBTENER_INFO_BASICA_PERSONA = "Afiliados.Obtener.infoBasicaPersona";

    /**
     * 
     */
    public static final String CONSULTAR_AFILIADOS_PRINCIPALES_BENEFICIARIO_SRV_TRA = "Afiliados.transversal.consultarAfiliadosPrincipalesBeneficiarioSrvTra";

    /**
     * Constante para la consulta de la información detallada de la persona respecto
     * a la afiliación por afiliado y beneficiarios
     */
    public static final String OBTENER_INFO_TOTAL_PERSONA = "Afiliados.Obtener.infoTotalPersona";

    /**
     * 
     */
    public static final String CONSULTAR_AFILIADOS_PRINCIPALES_POR_BENEFICIARIO = "Afiliados.Principales.Obtener.Por.Beneficiario";


    /**
     * Constante para la consulta de la información detallada restante de la persona
     * respecto a la afiliación por afiliado y beneficiarios
     * que no puede ser obtenida con la consulta
     * com.asopagos.afiliados.constants.NamedQueriesConstants.OBTENER_INFO_TOTAL_PERSONA
     */
    public static final String OBTENER_INFO_TOTAL_RESTANTE_PERSONA = "Afiliados.Obtener.infoTotalRestantePersona";

    /**
     * consulta la categoria actual de una persona para los servicios de integración
     */
    public static final String CONSULTAR_CATEGORIA_ACTUAL_PERSONA_TRA = "Afiliados.transversal.obtenerCategoriaActualPersonaTra";

    /**
     * constante que define la consulta que define el tipo de rol que tiene la
     * persona a consultar
     */
    public static final String DEFINIR_PERSONA_CONSULTA_ESTADO_CATEGORIA_SRV_TRA = "Afiliados.transversal.definirPersonaConsultaEstadoCategoriaSrvTra";

    /**
     * 
     */
    public static final String CONSULTAR_DATOS_PERSONA_COMO_AFILIADO_SRV_TRA = "Afiliados.transversal.consultarDatosPersonaComoAfiliadoSrvTra";

    /**
     * 
     */
    public static final String CONSULTAR_CATEGORIAS_AFILIADO_SRV_TRA = "Afiliados.transversal.consultarCategoriasAfiliadoSrvTra";

    /**
     * 
     */
    public static final String CONSULTAR_BENEFICIARIOS_AFILIADO_SRV_TRA = "Afiliados.transversal.consultarBeneficiariosAfiliadoSrvTra";

    /**
     * 
     */
    public static final String CONSULTAR_CATEGORIAS_BENEFICIARIOS_TIPO_AFI_SRV_TRA = "Afiliados.transversal.consultarCategoriasBeneficiarioPorTipoAfiliadoSrvTra";

    /**
     * 
     */
    public static final String CONSULTAR_AFILIADO_COMO_BENEFICIARIO_SRV_TRA = "Afiliados.transvesal.consultarAfiliadoComoBeneficiarioSrvTra";

    /**
     * 
     */
    public static final String CONSULTAR_CATEGORIAS_BENEFICIARIO_SRV_TRA = "Afiliados.transversal.consultarCategoriasBeneficiarioSrvTra";

    /**
     * 
     */
    public static final String CONSULTAR_AFILIADOS_PRINCIPALES_GRUPO_BENEFICIARIO_SRV_TRA = "Afiliados.transversal.consultarAfiliadosPrincipalesGrupoBeneficiarioSrvTra";

    /**
     * consulta y obtiene los datos de una persona como afiliado pricipal
     */
    public static final String OBTENER_INFO_AFILIADO_PRINCIPAL_SRV_TRA = "Afiliados.transversal.obtenerInfoAfiliadoPrincipal";

    /**
     * consulta y obtiene la información general de los grupos familiares donde la
     * persona parece como afiliado principal
     */
    public static final String CONSULTAR_INFO_GENERAL_GRUPOS_FAMILIARES_ST = "Afiliados.transversal.consultarInfoGeneralGruposFamiliares";

    /**
     * consulta y obtiene la información de los beneficiarios de un grupo familiar
     * determinado
     */
    public static final String CONSULTAR_BENEFICIARIOS_GRF_ST = "Afiliados.transversal.consultarBeneficiariosGrupoFamiliar";

    /**
     * consulta y obtiene la información basica de un empleador determinado.
     */
    public static final String OBTENER_INFO_BASICA_EMPLEADOR_SRV_TRA = "Afiliados.transversal.obtenerInfoBasicaEmpleador";

    /**
     * 
     */
    public static final String CONSULTAR_EMPLEADORES_AFILIADO_SRV_TRA = "Afiliados.transversal.consultarEmpleadoresAfiliadoSrvTra";

    /**
     * consulta y obtiene la información total de un empleador determinado.
     */
    public static final String OBTENER_INFO_TOTAL_EMPLEADOR_SRV_TRA = "Afiliados.transversal.obtenerInfoTotalEmpleador";

    /**
     * consulta y obtiene los datos del representante legal de un empleador dado.
     */
    public static final String OBTENER_INFO_REPRESENTANTE_LEGAL_EMPLEADOR_SRV_TRA = "Afiliados.transversal.obtenerInfoRepLegalEmpleador";

    /**
     * 
     */
    public static final String CONSULTAR_SUCURSALES_EMPRESA_SRV_TRA = "Afiliados.transversal.consultarSucursalesEmpresaSrvTra";

    /**
     * consulta y obtiene los datos generales de un empleador dado.
     */
    public static final String OBTENER_INFO_EMPLEADOR_PARA_CONTACTOS_SRV_TRA = "Afiliados.transversal.obtenerInfoEmpleadorParaContacto";

    /**
     * 
     */
    public static final String CONSULTAR_SUCURSALES_EMPLEADOR_POR_CODIGO_SRV_TRA = "Afiliados.transversal.consultarSucursalesEmpleadorPorCodigoSrvTra";

    /**
     * consulta los datos de contacto de un empleador dado.
     */
    public static final String OBTENER_DATOS_CONTACTO_EMPLEADOR_SRV_TRA_PPL = "Afiliados.transversal.obtenerDatosContactoEmpleadorPpl";

    /**
     * consulta los datos de contacto de un empleador dado.
     */
    public static final String OBTENER_DATOS_CONTACTO_EMPLEADOR_SRV_TRA = "Afiliados.transversal.obtenerDatosContactoEmpleador";

    /**
     * consulta la información de los grupos familiares de un afiliado para el
     * método obtenerInfoAfiliado
     * del servicio Cajas Sin Fronteras
     */
    public static final String OBTENER_INFO_AFILIADO_CSF_SRV_TRA = "Afiliados.transversal.obtenerInfoAfiliadoCSF";

    /**
     * 
     */
    public static final String CONSULTAR_BENEFICIARIOS_AFILIADO_PRINCIPAL_SRV_TRA = "Afiliados.transversal.obtenerBeneficiariosAfiliadoPrincipalSrvTra";

    /**
     * obtiene la información completa de una persona como beneficiario de un
     * afiliado de la caja
     */
    public static final String OBTENER_INFO_TOTAL_BENEFICIARIO_SRV_TRA = "Afiliados.transversal.obtenerInfoTotalBeneficiario";

    /**
     * obtiene la información de los certificados de escolaridad del beneficiario
     */
    public static final String OBTENER_INFO_CERTIFICADOS_ESCOLARIDAD_BENEFICIARIO_SRV_TRA = "Afiliados.transversal.obtenerInfoCertificadosEscolaridadBenefciarioSrvTra";

    /**
     * obtiene la información de si el afiliado está activo como dependiente,
     * indepenediente o pensionado
     */
    public static final String CONSULTAR_INFO_AFILIACION_AFI_PRINCIPAL_ST = "Afiliados.transversal.consultarInfoAfiliacionAfiliadoPrincipalST";

    /**
     * obtiene las sucursales del empleador
     */
    public static final String CONSULTAR_SUCURSALES_EMPLEADOR_SRV_TRA = "Afiliados.transversal.consultarSucrsalesEmpleadorSrvTra";

    /**
     * obtiene los datos del afiliado
     */
    public static final String BUSCAR_DATOS_AFILIADO_SRV_TRA = "Afiliados.transversal.buscarDatosAfiliadosSrvTra";

    /**
     * obtiene las categorias del afiliado principal
     */
    public static final String BUSCAR_CATEGORIAS_AFI_PPAL_SRV_TRA = "Afiliados.transversal.BuscarCategoriasAfiliadoPrincipalSrvTra";

    /**
     * obtiene los afiliados de los cuales es beneficiaria la persona
     */
    public static final String OBTENER_INFO_AFILIADOS_PPALES_BENEFICIARIO_SRV_TRA = "Afiliados.tansversal.obtenerInfoAfiliadosPrincipalesBeneficiarioSrvTra";

    /**
     * constante que representa la consulta que busca la información de una ciudad
     * dado su código
     */
    public static final String BUSCAR_INFO_CIUDAD_SRV_TRA = "Afiliados.transversal.buscarInfoCiudadSrvTra";

    /**
     * constante que representa la consulta que busca la información de una ciudad
     * dado su código y el departamento al cual pertenece
     */
    public static final String BUSCAR_INFO_CIUDAD_CON_DEPARATMENTO_SRV_TRA = "Afiliados.transversal.buscarInfoCiudadConDepartamentoSrvTra";

    /**
     * constante que representa la consulta que busca la información del último
     * salario del afiliado
     */
    public static final String BUSCAR_ULTIMO_SALARIO_AFILIADO_SRV_TRA = "Afiliados.transversal.buscarUltimoSalarioAfiliadoSrvTra";

    public static final String BUSCAR_HISTORICO_VALOR_SALARIO_MINIMO_EN_PERIODO = "Afiliados.transversal.aud.buscarValorSalarioMinimoEnPeriodo";

    public static final String BUSCAR_SMMLV_EN_PERIODO = "Afiliados.transversal.aud.buscarSMMLVEnPeriodo";

    public static final String BUSCAR_DATOS_PADRE_BIOLOGICO = "Afiliados.transversal.BuscarDatosPadreBiologico";

    public static final String BUSCAR_DATOS_MADRE_BIOLOGICA = "Afiliados.transversal.BuscarDatosMadreBiologica";

    public static final String BUSCAR_DATOS_PERSONAS_EN_RELACION_HIJOS= "Afiliados.Consultar.ObtenerPersonasEnRelacionHijos";

    public static final String BUSCAR_DATOS_PERSONAS_EN_RELACION_HIJOS2= "Afiliados.Consultar.ObtenerPersonasEnRelacionHijos2";

    public static final String CONSULTAR_DATOS_HISTORICOS_AFILIADO = "Afiliados.transversal.ConsultarDatosHistoricosAfiliado";

    public static final String CONSULTAR_CATEGORIA_BENEFICIARIOS_AFILIADO_SRV = "Afiliados.transversal.ConsultarCategoriaBefeciariosAfiliado";
    /////
    // final NamedQueriesConstants para servicios del catálogo de integración
    /////

    /**
     * Representa la consulta que retorna la entidad IntentoAfiliacion asociada a un
     * id
     */
    public static final String CONSULTAR_INTENTO_AFILIACION_BY_ID = "Afiliaciones.IntentoAfiliacion.ConsultarById";

    // Temporales utilitario liquidacion
    public static final String CONSULTAR_HISTORICO_ESTADOS_BENEFICIARIO = "Historicos.Consultar.EstadosBeneficiario";

    public static final String BUSCAR_BENEFICIARIOS_SIN_FECHA_AFILIACION = "Historicos.Consultar.BeneficiariosSinFechaAfiliacion";

    public static final String BUSCAR_BENEFICIARIO = "Historicos.ConsultarBeneficiario";
    // Fin temporales utilitario liquidacion

    /**
     * CC vistas 360
     */
    public static final String BUSCAR_DATOS_PADRE_BIOLOGICO_PERSONA = "Afiliados.transversal.BuscarDatosPadreBiologico.Persona";

    /**
     * CC vistas 360
     */
    public static final String BUSCAR_DATOS_MADRE_BIOLOGICA_PERSONA = "Afiliados.transversal.BuscarDatosMadreBiologica.Persona";

    /**
     * CC vistas 360
     */
    public static final String BUSCAR_DATOS_HIJO_BIOLOGICO_PERSONA = "Afiliados.transversal.BuscarDatosHijoBiologico.Persona";

    /**
     * CC vistas 360
     */
    public static final String BUSCAR_CERTIFICADOS_ESCOLARES = "Afiliados.Consultar.CertificadosEscolares";

    // Temporales fecha creación certificado escolar
    public static final String BUSCAR_CERTIFICADOS_FECHA_CREACION_NULL = "Afiliados.ConsultarCertificadosEscolaresFechaCreacionNull";

    public static final String CONSULTAR_CERTIFICADO_ESCOLAR = "Afiliados.consultarCertificadoEscolar";

    public static final String CONSULTAR_FECHA_CREACION_CERFICADO_AUD = "Historicos.consultarFechaCreacionCertificadoEscolar";
    // Fin temporales fecha creación certificado escolar

    // utilitario trabajadores activos con empleadores inactivos
    public static final String CONSULTAR_TRABAJADORES_ACTIVOS_EMPLEADORES_INACTIVOS = "Afiliaciones.Consultar.TrabajadoresActivosConEmpleadoresInactivos";

    public static final String CONSULTAR_ROLES_AFILIADO_AUDITORIA = "Afiliaciones.Consultar.RolesAfiliados.Auditoria";

    public static final String CONSULTAR_AFILIADOS_NO_RETIRADOS_PILA = "Afiliaciones.Consultar.afiliadosNoRetiradosPila";

    public static final String MARCAR_PROCESO_REGISTRO_AFILIADO_NO_RETIRADOS_PILA = "Afiliaciones.marcar.afiliadosNoRetiradosPila";

    public static final String STORED_PROCEDURE_WEB_CONSULTAR_INFORMACION_COMPLETA_AFILIADO_CONFA = "Afiliaciones.sp.consultar.informacion.completa.afiliado.confa";

    public static final String STORED_PROCEDURE_WEB_CONSULTAR_INFORMACION_COMPLETA_BENEFICIARIO_CONFA = "Afiliaciones.sp.consultar.informacion.completa.beneficiario.confa";

    public static final String CONSULTAR_CARGUE_MULTIPLE_SUPERVIVENCIA_POR_ID = "Afiliaciones.consultar.cargue.multiple.supervivencia.por.id";

    public static final String  BUSCAR_DATOS_AFILIADO_EROLAMIENTO_RECAUDOS_PAGOS = "Afiliaciones.consultar.afiliado.enrolamiento.recaudos.recaudos.pagos";

    public static final String CLASIFICACION_CATEGORIA_AFILIADO = "clasificacion.categoria.afiliado";

    public static final String BUSCAR_DEPARTAMENTO_ID_MUNICIPIO = "Afiliaciones.Departamento.buscarDepartamentoPorCodigo";

    public static final String PROCEDURE_SCHEDULE_DESISTIR_SOLICITUD_AFILIACION = "Afiliaciones.Personas.Desistir.Solicitud.Afiliacion";

    public static final String CONSULTAR_ARCHIVO_TRASLADOS_CCF = "Afiliaciones.Personas.Consultar.Archivos.Traslados";

    public static final String CANCELAR_ARCHIVOS_TRASLADOS_CCF_A_CARGO = "Afiliaciones.Personas.Cancelar.Archivos.Traslados.A.Cargo";

    public static final String CANCELAR_ARCHIVOS_TRASLADOS_CCF = "Afiliaciones.Personas.Cancelar.Archivos.Traslados";

    public static final String CONSULTAR_INFO_TOTAL_AFILIADO = "Afiliaciones.Consultar.Infototal.Afiliado";
    
    public static final String CONSULTAR_INFO_TOTAL_BENEFICIARIO = "Afiliaciones.Consultar.Infototal.Beneficiario";

     /**
     * consulta y obtiene la información  de un empleador determinado.
     */
    public static final String OBTENER_INFORMACION_EMPLEADOR = "Afiliados.transversal.obtener.informacion.empleador";
    
    public static final String BUSCAR_TARJETA_AFILIADO = "Afiliaciones.Consultar.Tarjeta.Afiliado";

    public static final String CONSULTAR_DATOS_APORTANTE_PERSONA_PERIODOS_WS = "Aportes.consultar.datos.aportante.empresa.periodo.ws";

    public static final String CONSULTAR_DATOS_COTIZANTE_PERSONA_PERIODOS_WS = "Aportes.consultar.datos.cotizante.empresa.periodo.ws";

    public static final String OBTENER_INFORMACION_AFILIADO_SERVICE = "Afiliaciones.Consultar.Informacion.Afiliado.Service";

    public static final String OBTENER_MUNICIPIO_DEPARTAMENTO = "Afiliaciones.Consultar.Municipio.Departamento";

    public static final String CONSULTAR_DATOS_PERSONA_NOVEDAD = "Afiliaciones.Consultar.Datos.Persona.Novedad";

    public static final String OBTENER_PAGOS_PERSONA = "Afiliaciones.Pagos.BuscarPagosPorPersona";
    
    public static final String VALIDA_EMPRESA = "Afiliaciones.Valida.Empresa";
    
    public static final String OBTENER_NUMEROS_DOCUMENTOS_EXISTENTES = "Afiliaciones.Consultar.Numeros.Documentos.Existentes";  
    
    public static final String CONSULTA_CERTIFICADO_FOSFEC_WS = "Afiliaicones.consulta.certificado.fosfec";;

    public static final String CONSULTAR_EXISTE_PERSONA = "Afiliaciones.Consultar.Existe.Persona";

    public static final String COINSIDEN_DATOS_REGISTRADOS = "Afiliaciones.Coinciden.datos.registrados";

    public static final String CONSUTLA_BASICOS_USUARIO = "Afiliaciones.Consulta.Datos.BasiocUsuario";
    
    public static final String CONSULTAR_DIGITO_VERIFICACION_EMPRESA = "Afiliaciones.Consultar.Digito.Verificacion.Empresa";
    
    public static final String CONSULTAR_URL_AMBIENTE = "Afiliaciones.Consultar.Url.Ambiente";

    public static final String CONSULTAR_ID_EMPLEADOR_AFILIACION = "Afiliaciones.Consultar.idEmpleador";

    public static final String CONSULTAR_DATA_ROLAFILIADO = "Afiliaciones.Consutar.infoRolafiliado";

    public static final String CONSULTAR_DATA_SOLICITUD_TEMPORAL = "Afiliaciones.Consultar.datos.solicitudTemporal";

    public static final String CONSULTAR_DATOS_SOLICITUD = "Afiliaciones.Consultar.datosSolicitud";

    public static final String CONSULTAR_DATOS_EMPLEADOR_AFILIACION = "Afiliaciones.consultar.datosEmpleador";

    public static final String CONSULTAR_ESTADO_SOLICITUD_AFILIACION_EMPRESA = "Afiliaciones.consultar.estado.SolicitudEmpresa";

    public static final String CONSULTAR_HALLAZGOS_CARGUE = "Afiliaciones.consultar.hallazgos.cargue";

    public static final String CONSULTAR_ID_PAGADOR_PENSION = "Afiliaciones.consultar.idPagadorPension";   
    
    public static final String CONSULTA_COD_PAIS = "Afiliaciones.consultar.codigo.pais";

    public static final String CONSULTA_OCUPACION = "Afiliaciones.consultar.codigo.ocupacion";

    public static final String CONSULTA_COD_RESGUARDO = "Afiliaciones.consultar.codigo.resguardo";

    public static final String CONSULTA_COD_PUEBLO_INDIGENA = "Afiliaciones.consultar.codigo.puebloIndigena";

    public static final String CONSULTAR_DOCUMENTO_PREVIO = "Afiliaciones.consultar.itemChequeo.documentoPrevio";

    public static final String CONSULTAR_INFO_EMPLEADOR_NOVEDAD = "Afiliaciones.consultar.infoEmpleador";

    public static final String CONSULTAR_INFORMACION_SOLICITUDES = "Afiliaciones.consular.informacion.solicitudes";

    public static final String CONSULTAR_INFORMACION_ITEMS = "Afiliaciones.consultar.informacion.items";

    public static final String CONSULTAR_INFORMACION_SOLICITUD = "Afiliciones.consultar.informacion.solicitudes.enCurso";
}
