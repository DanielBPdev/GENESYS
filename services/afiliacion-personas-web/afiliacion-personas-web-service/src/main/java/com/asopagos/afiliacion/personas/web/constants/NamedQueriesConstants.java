package com.asopagos.afiliacion.personas.web.constants;

/**
 * 
 * @author jusanchez
 *
 */
public class NamedQueriesConstants {

	/** consulta la lista de los departamentos en la base de datos */
	public static String CONSULTAR_DEPARTAMENTOS = "AfiliacionPersonasWeb.consultar.departamentos";

	/** consulta la lista de los municipios en la base de datos */
	public static String CONSULTAR_MUNICIPIOS = "AfiliacionPersonasWeb.consultar.municipios";
	/**
	 * Buscar solicitudes afiliacion persona por empleador
	 */
	public static final String BUSCAR_SOLICITUDES_AFILIACION_PERSONA_POR_EMPLEADOR_ESTADO = "afiliacion.buscar.solicitud.afiliacion.persona.empleador.estado";
	/**
	 * Constante que se encarga de buscar el estado del identificador de cargue
	 * multiple del empleador
	 */
	public static final String ESTADO_IDENTIFICADOR_CARGUE_MULTIPLE_EMPLEADOR = "afiliacion.estado.identificador.cargue.multiple.empleador";
	/**
	 * Constante encargado de buscar el cargue multiple por id
	 */
	public static final String BUSCAR_CARGUE_MULTIPLE_POR_ID = "afiliacion.buscar.cargue.multiple.por.id";
	/**
	 * Constante encargada de actualizar el estado del cargue multiple por id
	 */
	public static final String CONSULTAR_ESTADO_CARGUE_MULTIPLE_POR_ID_EMPLEADOR = "afiliacion.actualizar.estado.cargue.multiple.por.id.empleador";
	/**
	 * Constante encargada de consultar un empleador por su id
	 */
	public static final String BUSCAR_EMPLEADOR_POR_ID = "afiliacion.buscar.empleador.por.id";
	/**
	 * Constante encargada de consultar la carga multiple que pertenece a un
	 * empleador y estado
	 */
	public static final String BUSCAR_CARGA_MULTIPLE_POR_EMPLEADOR_ESTADO = "afiliacion.buscar.carga.multiple.por.empleador.estado";
	/**
	 * Constante encargada de consultar la solicitud por cargue multiple
	 * mediante su id cargue
	 */
	public static final String BUSCAR_SOLICITUD_CARGA_MULTIPLE_POR_ID_CARGA = "afiliacion.buscar.solicitud.carga.por.idCarga";
	/**
	 * Constante encargada de consultar el dato temporal perteneciente a una
	 * solicitud
	 */
	public static final String BUSCAR_DATO_TEMPORAL_POR_ID_SOLICITUD = "afiliacion.buscar.dato.temporal.por.id.solicitud";
	/**
	 * Constante encargada de consultar el file log definition catalog
	 */
	public static final String BUSCAR_FILE_LOG_DEFINITION_CATALOG = "afiliacion.buscar.file.log.definition.catalog";

	/**
	 * Constante encargada de consultar los nomrbes de los campos parametrizados
	 */
	public static final String BUSCAR_CAMPOS_ARCHIVO = "afiliacion.buscar.file.nombreCampos";

	/**
	 * Constante que se encarga de consultar la persona por el id
	 */
	public static final String BUSCAR_PERSONA_POR_ID = "afiliacion.buscar.persona.por.id";

	/**
	 * Buscar solicitudes afiliacion persona por empleador
	 */
	public static final String BUSCAR_SOLICITUDES_AFILIACION_PERSONA_POR_EMPLEADOR = "afiliacion.buscar.solicitud.afiliacion.persona.empleador";
			/**
	 * Buscar solicitudes afiliacion persona por empleador
	 */
	public static final String BUSCAR_SOLICITUDES_AFILIACION_PERSONA_POR_EMPLEADOR_CONSULTAR = "afiliacion.buscar.solicitud.afiliacion.persona.empleador.consultar";

	public static final String BUSCAR_SOLICITUDES_AFILIACION_PERSONA_POR_EMPLEADOR_WEB = "afiliacion.buscar.solicitud.afiliacion.persona.empleador.web";

	/**
	 * Buscar un cargue multiple por id
	 */
	public static final String BUSCAR_ESTADO_CARGUE_MULTIPLE_POR_ID = "afiliacion.estado.cargue.multiple.por.id";
	
	/**
	 * Constante encargada de consultar una sucursal por el id
	 */
	public static final String BUSCAR_SUCURSAL_EMPRESA_POR_ID = "afiliacion.buscar.sucursal.empresa.por.id";

	/**
	 * Constante encargada actualizar las solicitudes a cerradas
	 */
	public static final String ACTUALIZAR_SOLICITUDES_A_CANCELADAS = "AfiliacionPersonasWebComposite.Actualizar.Solicitudes.A.Canceladas";

	/**
	 * Buscar persona detalle por numero y tipo de identificacion de una persona
	 */
    public static final String BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION = "afiliacion.buscar.persona.detalle.tipo.numero.identificacion";

    /**
     * Busca la persona por tipo y número de identificación 
     */
    public static final String BUSCAR_PERSONA = "afiliacion.buscar.persona.tipo.numero.identificacion";

	public static final String AFILIACION_SUCURSAL_ECM = "afiliacion.sucursal.ECM";

    
    /**
     * VALIDACION PAIS DE RESIDENCIA
     */
    public static final String BUSCAR_PAIS = "afiliacion.buscar.pais";
     /**
     * VALIDACION OCUPACION POR CODIGO
     */
    public static final String BUSCAR_OCUPACION_PROFESIONAL = "afiliacion.buscar.ocupacion.profesional";
     /**
     * VALIDACION OCUPACION POR NOMBRE
     */
    public static final String BUSCAR_OCUPACION_PROFESIONAL_BY_NAME = "afiliacion.buscar.ocupacion.profesional.name";
    /**
     * VALIDACION OCUPACION
     */
    public static final String BUSCAR_MUNICIPIO = "afiliacion.buscar.municipio"; 
    
    public static final String BUSCAR_CODIGO_PAIS = "afiliacion.buscar.buscarCodigoPais";
    
    public static final String BUSCAR_ID_OCUPACION = "afiliacion.buscar.id.ocupacion";

	public static final String ASP_JsonPersona = "sp.procedure.ASP_JsonPersona";

	public static final String BUSCAR_SOLICITUD_AFILIADO = "afiliacion.solicitud.masivos";


}
