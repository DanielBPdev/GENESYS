package com.asopagos.consola.estado.cargue.procesos.constants;

/**
 * <b>Descripción:</b> NamedQueriesConstants para realizar las consultas
 * necesarias al momento de realizar la creacion de la consola de estados de
 * cargue procesos <b>Historia de Usuario:</b> TRA-368
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
public class NamedQueriesConstants {

	/**
	 * Consulta encargada de buscar FileLoadedLog por el load id
	 */
	public static final String CONSULTAR_FILE_LOAD_LOG_POR_LOAD_ID = "consola.estado.consultar.file.load.log.por.load.id";

	/**
	 * Constante encargada de consultar los nomrbes de los campos parametrizados
	 */
	public static final String BUSCAR_CAMPOS_ARCHIVO = "afiliacion.buscar.file.nombreCampos";
	
	/**
	 * Constante encargada de consultar ConsolaEstadoCargueMasivo de los campos parametrizados
	 */
	public static final String CONSULTAR_CONSOLA_ESTADO_CARGUE_POR_CARGA_ID_TIPO_PROCESO= "consola.estado.consultar.consola.estado.por.carga.id.tipo.proceso";

    /**
     * Constante que representa la consulta a la consola con filtros y paginada
     */
    public static final String CONSULTAR_CONSOLA_POR_FILTROS_PAGINADA = "consultar.consola.cargue.por.filtro.paginada";

    /**
     * Constante que representa la consulta del log de un registro de consola por identificador
     */
    public static final String CONSULTAR_LOG_CONSOLA_ID = "consultar.log.consola.cargue.id";

	public static final String CONSULTAR_CONSOLA_POR_NOMBRE = "consultar.consola.cargue.por.nombre";

	public static final String CONSULTAR_CONSOLA_ESTADO_CARGUE_UNICO_TIPO_PROCESO= "consola.estado.consultar.consola.unico.por.carga.tipo.proceso";

	public static final String CONSULTAR_PROCESO_POR_FILTROS_PAGINADA = "consultar.consola.procesos.por.filtro.paginada";

	public static final String CONSULTAR_CONSOLA_ESTADO_PROCESO_POR_CARGA_ID_TIPO_PROCESO= "consola.estado.consultar.consola.estado.por.proceso.id.tipo.proceso";

}
