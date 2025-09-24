package com.asopagos.afiliacion.personas.constants;

/**
 * <b>Descripción:</b> NamedQueriesConstants para realizar las consultas
 * necesarias al momento de realizar la creacion de solicitud afiliacion persona
 * <b>Historia de Usuario:</b> HU-121-104
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
public class NamedQueriesConstants {

	/**
	 * Buscar solicitud afiliacion persona por el id global de la solicitud
	 */
	public static final String BUSCAR_ID_SOLICITUD_GLOBAL = "Afiliaciones.Personas.solicitud.persona.id.global";
	/**
	 * Constante para bucar una solicitud por id
	 */
	public static final String BUSCAR_ID_SOLICITUD = "Afiliaciones.Personas.solicitud.id";
	/**
	 * Constante para buscar un afiliado por el id
	 */
	public static final String BUSCAR_ID_AFILIADO = "Afiliaciones.Personas.afiliado.id";
	/**
	 * Constante para buscar el afiliado por la identificacion y tipo de
	 * identificacion de la persona
	 */
	public static final String BUSCAR_AFILIADO_IDENTIFICACION_PERSONA = "Afiliaciones.Personas.afiliado.identificacion.persona";
	/**
	 * Constante para buscar persona por id
	 */
	public static final String BUSCAR_ID_PERSONA = "Afiliaciones.Personas.personas.id";
	/**
	 * Constante para consultar el rol del afiliado
	 */
	public static final String BUSCAR_ROL_AFILIADO_ID_AFILIADO = "Afiliaciones.Personas.rol.afiliado";
	/**
	 * Constante para buscar una persona por la identificacion
	 */
	public static final String BUSCAR_PERSONA_IDENTIFICACION = "Afiliaciones.Personas.persona.identificacion";
	/**
	 * Constante para buscar el tipo de afiliado por la identificacion de la
	 * persona
	 */
	public static final String BUSCAR_AFILIADO_TIPO_IDENTIFICACION_PERSONA = "Afiliaciones.Personas.rolAfiliado.identificacion";
	/**
	 * Constante para buscar el empleador por el id
	 */
	public static final String BUSCAR_EMPLEADOR_ID = "Afiliaciones.Persona.empleador.id";
	/**
	 * Constante para buscar la sucursal por el id del empleador
	 */
	public static final String BUSCAR_SUCURSAL_EMPLEADOR_ID = "Afiliaciones.Persona.sucursal.empleador.id";
	/**
	 * Constante para buscar el rolAfiliado por su id
	 */
	public static final String BUSCAR_ROL_AFILIADO_POR_ID = "Afiliaciones.Persona.rolAfiliado.id";

	/**
	 * Constante para busca la solicitud de afiliacion por tipo y numero
	 * documento
	 */
	public static final String BUSCAR_SOLICITUD_TIPO_NUM_IDENTIFICACION = "Afiliaciones.persona.solicitud.tipo.numero.identificacion";

	/**
	 * Constante para busca la solicitud de afiliacion por tipo y numero
	 * documento, y tipo afiliados
	 */
	public static final String BUSCAR_SOLICITUD_TIPO_NUM_IDEN_AFIL = "Afiliaciones.persona.solicitud.tipo.numero.iden.afiliado";

	/**
	 * Constante encargada de consultar la personas la numero de identificacion
	 * y por numero de indentificacion
	 */
	public static final String BUSCAR_PERSONA_NUMERO_IDENTIFICACION_TIPO_IDENTIFICACION = "Afiliaciones.personas.por.afiliado.numero.tipo.identificacion";

	/**
	 * Constante encargada de consultar las solicitudes pertenecientes a un rol
	 * afiliado
	 */
	public static final String BUSCAR_SOLICITUDES_POR_ID_ROL_AFILIADO_TIPO_SOLICITUD_CANAL = "Afiliaciones.solicitudes.por.id.rol.afiliado";
	
	/**
	 * Constante encargada de consultar las solicitudes pertenecientes a un rol
	 * afiliado
	 */
	public static final String BUSCAR_SOLICITUDES_POR_ID_ROL_AFILIADO_TIPO_ESTADO_SOLICITUD_CANAL = "Afiliaciones.solicitudes.por.id.rol.afiliado.estado";
	/**
     * Constante encargada de consultar las solicitudes pertenecientes a un rol
     * afiliado
     */
    public static final String BUSCAR_SOLICITUDES_POR_ID_ROL_AFILIADO_TIPO_SOLICITUD_CANAL_NUMERORADICACION = "Afiliaciones.solicitudes.por.id.rol.afiliado.numeroRadicacion";
    
    /**
     * Constante encargada de consultar las solicitudes pertenecientes a un rol
     * afiliado
     */
    public static final String BUSCAR_SOLICITUDES_POR_ID_ROL_AFILIADO_TIPO_ESTADO_SOLICITUD_CANAL_NUMERORADICACION = "Afiliaciones.solicitudes.por.id.rol.afiliado.estado.numeroRadicacion";
	/**
	 * Constante encargada de buscar un rol afiliado por el id del afiliado
	 */
	public static final String BUSCAR_ROL_AFILIADO_POR_ID_AFILIADO = "Afiliaciones.Personas.rol.id.afiliado";

	/**
	 * Constante encargada de buscar PersonaDetalle por el id de persona
	 */
	public static final String BUSCAR_ID_PERSONA_DETALLE = "Afiliaciones.personaDetalle.persona.id";
	
	/**
	 * Constante para la consulta de solicitudes de afiliacion sin instancia de proceso
	 */
    public static final String BUSCAR_SOLICITUDES_SIN_INSTANCIA_PROCESO = "Afiliaciones.solicitudes.buscarSolicitudesSinInstanciaProceso";

	/**
	 * Constante para la consulta de solicitudes de afiliacion sin instancia de proceso
	 */
    public static final String BUSCAR_SOLICITUDES_POR_NUMERORADICACION_CANAL = "Afiliaciones.solicitudes.por.numeroRadicacion.canal";

	public static final String CONSULTAR_CLASIFICACION_PENSIONADO = "Consultar.calsificacion.pensionado";
	/**
	 * Constante para la consulta de solicitudes de afiliacion sin instancia de proceso
	 */
    public static final String CONSULTAR_DATOS_EXISTENTE_NO_PENSIONADO = "Consultar.datos.existente.no.pensionado";

	public static final String CONSULTAR_CARGUE_PENSIONADOS = "Consultar.cargue.pensionados.25.anios";


    public static final String CONSULTAR_CARGUE_TRANSLADOS = "Consultar.cargue.traslados";
}
