package com.asopagos.entidades.pagadoras.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio <b>Historia de Usuario:</b> 133
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class NamedQueriesConstants {

	// HU-133 constante para buscar una entidad pagadora por tipo y nro documento
	public static final String BUSCAR_ENTIDAD_PAGADORA_POR_DOCUMENTO = "EntidadesPagadoras.buscar.entidades.documento";

	// HU-133 constante para buscar una entidad pagadora por razón social
	public static final String BUSCAR_ENTIDAD_PAGADORA_POR_RAZON_SOCIAL = "EntidadesPagadoras.buscar.entidades.razonSocial";

	// HU-133 constante para consultar una empresa por tipo y nro documento
	public static final String BUSCAR_EMPLEADOR_POR_DOCUMENTO = "EntidadesPagadoras.buscar.empleador";

	// HU-133 constante para consultar un municipio
	public static final String CONSULTAR_MUNICIPIO = "EntidadesPagadoras.consultar.municipio";

	// HU-133 constante para consultar la ubicación de la empresa
	public static final String CONSULTAR_UBICACION_EMPRESA = "EntidadesPagadoras.consultar.ubicacionEmpresa";

	// HU-133 constante para consultar la persona
	public static final String CONSULTAR_PERSONA = "EntidadesPagadoras.consultar.persona";

	// HU-133 constante para consultar todas las personas
	public static final String CONSULTAR_PERSONAS = "EntidadesPagadoras.consultar.personas";


	// HU-133 constante para consultar todas las entidades pagadoras
	public static final String CONSULTAR_ENTIDADES_PAGADORAS = "EntidadesPagadoras.consultar.entidades.pagadoras";

	// HU-133 constante para consultar una entidad pagadora por tipo y nro
	// documento
	public static final String CONSULTAR_DOCUMENTOS_ENTIDAD_PAGADORA = "EntidadesPagadoras.consultar.documentos.entidad.pagadora";
	
	// HU-133 constante para consultar una persona en la empresa
	public static final String CONSULTAR_PERSONA_EN_EMPRESA = "EntidadesPagadoras.consultar.persona.empresa";

	// HU-109 constante para validar una entidad pagadora filtrando por tipo de
	// gestión SOLICITAR_ALTA
	public static final String VALIDAR_ENTIDAD_PAGADORA_TIPO_GESTION_SOLICITAR_ALTA = "EntidadesPagadoras.validar.entidad.tipo.gestion.solicitar.alta";

	// HU-109 constante para validar una entidad pagadora filtrando por tipo de
	// gestión SOLICITAR_RETIRO
	public static final String VALIDAR_ENTIDAD_PAGADORA_TIPO_GESTION_SOLICITAR_RETIRO = "EntidadesPagadoras.validar.entidad.tipo.gestion.solicitar.retiro";

	// HU-109 constante para validar una entidad pagadora filtrando por tipo de
	// gestión REGISTRAR_RESULTADO_SOLICITUD
	public static final String VALIDAR_ENTIDAD_PAGADORA_TIPO_GESTION_REGISTRAR_RESULTADO_SOLICITUD = "EntidadesPagadoras.validar.entidad.tipo.gestion.registrar.resultado.solicitud";

	// HU-109 constante para consultar una solicitud en la tabla Solicitud con
	// el nro de radicado
	public static final String CONSULTAR_SOLITUD_POR_NRO_RADICADO = "EntidadesPagadoras.consultar.solicitud";

	// HU-109 constante para actualizar una Gestión de Solicitudes Asociacion
	// cuando es Alta o Retiro
	public static final String ACTUALIZAR_GESTION_SOLICITUD_ASOCIACION = "EntidadesPagadoras.actualizar.gestion.solicitud.asociacion";

	// HU-109 constante para actualizar una Gestión de Solicitudes Asociacion
	// cuando es Alta o Retiro
	public static final String CONSULTAR_CONSECUTIVO = "EntidadesPagadoras.consultar.consecutivo";

	// HU-109 constante para actualizar una Gestión de Solicitudes Asociacion
	// cuando es Alta o Retiro
	public static final String ACTUALIZAR_CONSECUTIVO = "EntidadesPagadoras.actualizar.consecutivo";

	// HU-109 constante para actualizar una Gestión de Solicitudes Asociacion
	public static final String ACTUALIZAR_ROL_AFILIADO = "EntidadesPagadoras.actualizar.rol.afiliado";

	// HU-109 constante para consultar una Gestión de Solicitudes Asociacion
	public static final String CONSULTAR_CONSECTUTIVO_GESTION_SOLICITUD_ASOCIACION = "EntidadesPagadoras.consultar.consecutivo.gestion.solicitud.asociacion";

	// HU-109 constante para actulizar el identificador archivo carta en la
	// tabla SolicitudAsociacionPersonaEntidadPagadora
	public static final String ACTUALIZAR_IDENTIFICADOR_ARCHIVO_CARTA = "EntidadesPagadoras.actualizar.identificador.archivo.carta";

	// HU-109 constante para actulizar el identificador archivo en la tabla SolicitudAsociacionPersonaEntidadPagadora
	public static final String GUARDAR_IDENTIFICADOR_ARCHIVO = "EntidadesPagadoras.actualizar.identificador.archivo";

	// HU-109 constante para la consulta de los datos que iran en el archivo
	public static final String CONSULTAR_DATOS_INFORME_ESTANDAR = "EntidadesPagadoras.consultar.datos.informe.estandar";

	// HU-109 constante para actulizar el identificador carta resultado gestion
	// tabla SolicitudAsociacionPersonaEntidadPagadora
	public static final String ACTUALIZAR_IDENTIFICADOR_CARTA_RESULTADO_GESTION = "EntidadesPagadoras.actualizar.identificador.carta.resultado.gestion";

	// HU-109 constante para la consulta de los datos que iran en el archivo  
	public static final String CONSULTAR_DATOS_ARCHIVO_FORMATO_TXT = "EntidadesPagadoras.generarArchivoTXT.EstructuraSolicitudAlta";
	
	// HU-109 constante para asociar el pagador aportes 
	public static final String ASOCIAR_PAGADOR_APORTES = "EntidadesPagadoras.asociar.pagador.aportes";

    /**
     * Constante para consultar las entidades habilitadas por tipo de afiliacion
     */
    public static final String CONSULTAR_ENTIDADES_PAGADORAS_BY_TIPO_AFILIACION = "EntidadesPagadoras.consultar.habilitadas.by.tipoAfiliacion";
    public static final String CONSULTAR_ENTIDADES_PAGADORAS_BY_TIPO_AFILIACION_COMPLETA = "EntidadesPagadoras.consultar.habilitadas.by.tipoAfiliacion.completa";
    
    public static final String BUSCAR_SOLICTUD_ENTIDAD_PAGADORA_POR_ID_SOLICITUD = "EntidadesPagadoras.buscarSolicitud.idSolicitudGlobal";

    /* Consulta base para la consultar las solicitudes de personas asociadas a la entidad pagadora */
    public static final String ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_BASE = "SELECT per, rolAf, sapep, sol FROM EntidadPagadora epa, Persona per, Afiliado afi, RolAfiliado rolAf, SolicitudAsociacionPersonaEntidadPagadora sapep, Solicitud sol WHERE sapep.solicitudGlobal = sol.idSolicitud AND sapep.rolAfiliado = rolAf.idRolAfiliado AND rolAf.afiliado = afi.idAfiliado AND afi.persona = per.idPersona AND rolAf.pagadorAportes.idEntidadPagadora = epa.idEntidadPagadora ";

    /*
     * Parametro cuando el consecutivo gestion es diferente de null se agrega como condicion a la consulta solicitudes de personas asociadas
     * a la entidad pagadora
     */
    public static final String ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_PARAMETRO_CONSECUTIVO_GESTION = "AND sapep.consecutivo = :consecutivoGestion ";

    /*
     * Parametro cuando el numero radicado es diferente de null se agrega como condicion a la consulta solicitudes de personas asociadas a
     * la entidad pagadora
     */
    public static final String ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_PARAMETRO_NUMERO_RADICADO = "AND sol.numeroRadicacion = :numeroRadicado ";

    /*
     * Parametro cuando el tipo de gestion es diferente de null se agrega como condicion a la consulta solicitudes de personas asociadas a
     * la entidad pagadora
     */
    public static final String ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_PARAMETRO_ID_ENTIDAD_PAGADORA = "AND rolAf.pagadorAportes.idEntidadPagadora = :idEntidadPagadora ";

    /*
     * Parametro cuando el tipo de gestion es solicitar alta se agrega como condicion a la consulta solicitudes de personas asociadas a la
     * entidad pagadora
     */
    public static final String ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_PARAMETRO_TIPO_GESTION_SOLICITAR_ALTA = "AND sapep.tipoGestion = 'SOLICITAR_ALTA' AND sapep.estado = 'PENDIENTE_SOLICITAR_ALTA' AND afi.estadoAfiliadoCaja = 'ACTIVO' AND rolAf.estadoEnEntidadPagadora = 'INACTIVO' AND epa.estadoEntidadPagadora = 'HABILITADO' ";

    /*
     * Parametro cuando el tipo de gestion es solicitar retiro se agrega como condicion a la consulta solicitudes de personas asociadas a la
     * entidad pagadora
     */
    public static final String ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_PARAMETRO_TIPO_GESTION_SOLICITAR_RETIRO = "AND sapep.tipoGestion = 'SOLICITAR_RETIRO' AND sapep.estado = 'PENDIENTE_SOLICITAR_RETIRO' AND rolAf.estadoEnEntidadPagadora = 'ACTIVO' ";

    /*
     * Parametro cuando el tipo de gestion es registrar resultado solicitud se agrega como condicion a la consulta solicitudes de personas
     * asociadas a la entidad pagadora
     */
    public static final String ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_PARAMETRO_TIPO_GESTION_REGISTRAR_RESULTADO_SOLICITUD = "AND sapep.tipoGestion = 'REGISTRAR_RESULTADO_SOLICITUD' AND sapep.estado IN ('SOLICITADA_ALTA', 'RETIRO_SOLICITADO', 'APROBADA') ";
    
    /* Constante que sirve para ordenar por el estado de la entidad pagadora */
    public static final String ENTIDADES_PAGADORAS_CONSULTAR_SOLICITUD_PERSONA_A_ENTIDADPAGADORA_ORDER_BY = "ORDER BY rolAf.estadoEnEntidadPagadora ASC, per.primerNombre ASC ";
   
    /**
     * Consulta las solicitudes de asociacion a la entidad pagadora por id entidad pagadora
     */
    public static final String CONSULTA_SOLICITUD_ASOCIACION_ENTIDAD_PAGADORA_BY_ID = "find.solicitud.asociacion.persona.by.idEntidadPagadora";
}
