package com.asopagos.listaschequeo.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class NamedQueriesConstants {

	// HU TRA-061
	public static final String CONSULTAR_CAJAS_COMPENSACION = "listasChequeo.cajaCompensacion.buscarTodos";

	// HU 111-59
	public static final String CONSULTAR_TIPO_SOLICITANTE_POR_CAJA = "listasChequeo.tipoSolicitante.buscarPorCajaCompensacion";

	// HU 111-59
	public static final String CONSULTAR_LISTA_CHEQUEO = "listasChequeo.listasChequeo.buscarPorClasificacionCajaCompensacion";
	public static final String CONSULTAR_LISTA_CHEQUEO_PERSONA = "listasChequeo.listasChequeo.buscarPorClasificacionCajaCompensacionPersona";

	// HU TRA-061
	public static final String CONSULTAR_PERSONA_TIPO_NUMERO_IDENTIFICACION = "listasChequeo.persona.consultarPorTipoNumeroIdentificacion";

	// HU TRA-061
	public static final String CONSULTAR_LISTA_CHEQUEO_POR_SOLICITUD = "listasChequeo.listasChequeo.buscarListaChequeoPorSolicitud";

	// HU TRA-061
	public static final String CONSULTAR_LISTA_CHEQUEO_POR_SOLICITUD_PERSONA = "listasChequeo.listasChequeo.buscarListaChequeoPorSolicitudPersona";

	// HU 061 Constante para consultar todo el listado de Requisitos
	public static final String CONSULTAR_LISTA_REQUISITOS = "listasChequeo.requisitos.consultarRequisitos";

	// HU 061 Constante para consultar el listado de Requisitos filtrando con
	// nombre
	public static final String CONSULTAR_LISTA_REQUISITOS_NOMBRE = "listasChequeo.requisitos.consultarRequisitos.nombre";

	// HU 061 Constante para consultar todos los registros de la caja de
	// compensación familiar
	public static final String CONSULTAR_REQUISITOS_CAJA_COMPENSACION = "listasChequeo.requisitos.consultar.consultarRequisitosCajasCompensacion";

	// HU 061 Constante para consultar todos los registros de la tabla
	// RequisitoCajaClasificacion
	public static final String CONSULTAR_REQUISITOS_CAJA_CLASIFICACION = "listasChequeo.requisitos.consultar.consultarRequisitosClasificaciones";

	// HU 061 Constante para actualizar un registro de la tabla Requisito
	// public static final String ACTUALIZAR_REQUISITO =
	// "listasChequeo.requisitos.actualizar.requisito";

	// HU 061 Constante para consultar la lista de checueo
	public static final String CONSULTAR_LISTA_CHEQUEO_POR_SOLICITUD_Y_REQUISITO = "listasChequeo.listasChequeo.buscarListaChequeoPorSolicitudYRequisito";

	// HU 061 Constante para consultar la lista de checueo
	public static final String CONSULTAR_LISTA_CHEQUEO_POR_SOLICITUD_REQUISITO_PERSONA = "listasChequeo.listasChequeo.buscarListaChequeoPorSolicitudRequisitoYPersona";

	/**
	 * Consulta el grupo usuario por un id de requisito caja clasificación
	 */
	public static final String CONSULTAR_GRUPO_USUARIO = "listasChequeo.buscarGrupoUsuario.idRequisitoCajaClasificacion";
	/**
	 * Consulta los requisitos por un id
	 */
	public static final String CONSULTAR_REQUISITOS_ID = "listasChequeo.consultarRequisitos.idRequisito";
	/**
	 * Consulta una caja de compensacion por su id
	 */
	public static final String CONSULTAR_CAJA_COMPENSACION_ID = "listasChequeo.consultarCajaCompensacion.id";
	/**
	 * Consulta un grupo usuario por el id de Requisito Caja Clasificación donde
	 * el requisito sea el mismo en Requisito Caja Clasificacion y en Item
	 * Chequeo
	 */
	public static final String CONSULTAR_GRUPO_USUARIO_ITEM_CHEQUEO = "listasChequeo.grupoUsuario.itemChequeo";
	/**
	 * Consulta los id de rcc para un item chequeo donde su documento previo sea
	 * permitido visualizarse por el grupo
	 */
	public static final String CONSULTAR_LISTA_CHEQUEO_POR_SOLICITUD_PERSONA_PREVIO = "listasChequeo.listasChequeo.buscarListaChequeoPorSolicitudPersonaConPrevio";
	/**
	 * Consulta los ids de RCC para un item chequeo donde su documento previo
	 * sea permitido visualizarse por el grupo
	 */
	public static final String CONSULTAR_LISTA_CHEQUEO_POR_SOLICITUD_PREVIO = "listasChequeo.listasChequeo.buscarListaChequeoPorSolicitudConPrevio";
	/**
	 * Cuenta la cantidad de grupos de un usuario por id de requisito caja
	 * clasificacion si se encuentra en la lista de grupos pasada por parametro
	 */
	public static final String CONSULTAR_GRUPO_USUARIO_ID_RCC = "listasChequeo.consultar.grupoUsuario.requisitoCajaClasificacion.listaGrupos";
	/**
	 * Elimina los grupos de usuario por un id de RequisitoCajaClasificacion
	 */
	public static final String ELIMINAR_GRUPO_USUARIO_ID_REQUISITO_CAJA_CLASIFICACION = "listasChequeo.GrupoUsuario.id.requisitoCajaClasificacion";
	/**
	 * Elimina los grupos de usuario por un id de RequisitoCajaClasificacion y
	 * por una lista de ids de grupos de usuarios
	 */
	public static final String ELIMINAR_GRUPOS_USUARIOS_NO_PRESENTES = "listasChequeo.GrupoUsuario.id.grupoUsuarios";
	/**
	 * Consulta las listas de cheaqueo por la clasificacion
	 */
	public static final String CONSULTAR_LISTA_CHEQUEO_POR_CLASIFICACION = "listasChequeo.listasChequeo.buscarListaChequeoPorClasificacion";

	/**
	 * Consulta las listas de chequeo asociadas a una SolicitudPostulacion
	 */
	public static final String CONSULTAR_LISTA_CHEQUEO_SOLICITUD_POSTULACION = "listasChequeo.consultarListaChequeo.SolicituPostulacion";
	
	public static final String CONSULTAR_ITEMS_CHEQUEO_SOLICITUD="listasChequeo.consultarItemsChequeo.Solicitudes";

    /**
     * Constante que representa la consulta de documentos requisito de una persona
     */
    public static final String CONSULTAR_DOCUMENTO_REQUISITO_PERSONA = "consultar.documento.requisito.persona";
    
    public static final String CONSULTAR_REQUISITO_CAJA_CLASIFICACION = "listasChequeo.consultarRequisitoCajaClasificacion";
}
