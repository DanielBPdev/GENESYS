package fovis.constants;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio de FOVIS <br/>
 * <b>Módulo: FOVIS</b> Asopagos - HU-3.2 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class NamedQueriesConstants {

    /**
     * Constante de la consulta que obtiene las solicitudes de postulacion por el número de radicado y/o los datos del jefe del hogar.
     */
    public static final String CONSULTAR_SOLICITUD_POSTULACION_POR_NUMERO_RADICADO_Y_O_JEFE_HOGAR = "Fovis.SolicitudPostulacion.consultarPorNumeroRadicadoYODatosJefeHogar";

    /**
     * Consulta que se encarga de buscar una solicitud de legalización y desembolso por el id de la solicitud global
     */
    public static final String CONSULTAR_SOLICITUD_POR_ID_SOLICITUD_GLOBAL = "FOVIS.consultar.solicitudLegalizacionDesembolso.idSolicitudGlobal";
    
    /**
     * Consulta que se encarga de buscar una legalización por ID
     */
    public static final String CONSULTAR_LEGALIZACION_POR_ID = "FOVIS.consultar.legalizacionPorId";

    /**
     * Consulta consulta las legalizaciones desembolsadas para una Postulación Fovis
     */
    public static final String CONSULTAR_LEGALIZACIONES_DESEMBOLSADAS_POR_POSTULACION = "FOVIS.consultar.LegalizacionDesembolsada.idPostulacion";

    /**
     * Consulta el oferente asociado a una postulacion Fovis
     */
    public static final String CONSULTAR_OFERENTE_POSTULACION = "FOVIS.consultarOferente.Postulacion";

    /**
     * Consulta el proyecto de solucion de vivienda a partir del id de la postulacion Fovis
     */
    public static final String CONSULTAR_PROYECTO_SOLUCION_VIVIENDA = "FOVIS.Consultar.ProyectoSolucionVivienda";

    /**
     * Consulta la licencia asociada a un proyecto de solucion de vivienda
     */
    public static final String CONSULTAR_LICENCIA = "FOVIS.Consultar.licencia";

    /**
     * Consulta el id de la postulacion Fovis a partir del id de la solicitud global
     */
    public static final String CONSULTAR_ID_POSTULACION_FOVIS_SOLICITUD = "FOVIS.consultar.idPostulacionFovis.Solicitud";

    /**
     * Consulta el jefe de hogar asociado a una postulacion
     */
    public static final String CONSULTAR_JEFE_HOGAR_POSTULACION = "FOVIS.consultarJefeHogar.Postulacion";

    /**
     * Consulta el detalle de la licencia
     */
    public static final String CONSULTAR_DETALLE_LICENCIA = "FOVIS.Consultar.DetalleLicencia";

    /**
     * Consulta la visita a partir de su id
     */
    public static final String CONSULTAR_VISITA = "FOVIS.Consultar.Visita";

    /**
     * Consulta las condiciones asociadas a una visita
     */
    public static final String CONSULTAR_CONDICIONES_VISITA = "FOVIS.Consultar.ConidicionesVisita";

    /**
     * Constante con el nombre de la consulta que valida si el oferente tiene un proyecto de vivienda con el nombre ingresado
     */
    public static final String CONSULTAR_PROYECTO_POR_OFERENTE_NOMBRE_PROYECTO = "Fovis.Consultar.ProyectoSolucionVivienda.OferenteNombreProyecto";

    /**
     * Constante con el nombre de la consulta que valida si el oferente tiene un proyecto de vivienda con el nombre ingresado
     */
    public static final String CONSULTAR_DOCUMENTOS_SOPORTE_POR_OFERENTE = "Fovis.Consultar.DocumentosSoporteOferente.PorIdentificadorOferente";

    
    /**
     * Constante con el nombre de la consulta que valida si el oferente tiene un proyecto de vivienda con el nombre ingresado
     */
    public static final String CONSULTAR_DOCUMENTOS_SOPORTE_POR_PROVEEDOR = "Fovis.Consultar.DocumentosSoporteProveedor.PorIdentificadorProveedor";

    
    /**
     * Constante con el nombre de la consulta que valida si el oferente tiene un proyecto de vivienda con el nombre ingresado
     */
    public static final String CONSULTAR_DOCUMENTOS_SOPORTE_PROYECTO_VIVIENDA_POR_PROYECTO = "Fovis.Consultar.DocumentosSoporteProyectoVivienda.PorIdentificadorProyecto";

    /**
     * Constante con el nombre de la consulta que obtiene el oferente
     */
    public static final String CONSULTAR_OFERENTE_POR_ID = "Fovis.Consultar.Oferente.PorID";

    /**
     * Constante con el nombre de la consulta que obtiene la empresa por id
     */
    public static final String CONSULTAR_EMPRESA_ID = "Empleador.empresa.id";
    
    /**
     * Consulta el json guardado en la primera solicitud de legalización y desembolso por el número de radicado de la solicitud de
     * postulación
     */
    public static final String CONSULTAR_JSON_DATOS_INICIALES_POSTULACION_POR_RADICADO_SOLICITUD = "FOVIS.Consultar.jsonPostulacion.porNumeroRadicadoSolicitudPostulacion";
    
    /**
     * COnsulta los tipos de ahorro previo asociados a una postulacion Fovis
     */
	public static final String CONSULTAR_TIPOS_AHORRO_POSTULACION = "FOVIS.Consultar.TiposAhorro.Postulacion";

	/**
	 * COnsulta los tipos de recursos asociados a una postulacion
	 */
	public static final String CONSULTAR_TIPOS_RECURSOS_POSTULACION = "FOVIS.Consultar.TiposRecursos.Postulacion";
	
	/**
	 * 
	 */
	public static final String CONSULTAR_RECURSO_COMPLEMENTARIO = "FOVIS.Consultar.RecursoComplementario";
	
	/**
	 * Consulta los ahorros asociados a una postulacion
	 */
	public static final String CONSULTAR_LISTA_AHORROS_PREVIOS = "FOVIS.Consultar.ListaAhorrosPrevios";
	
	/**
	 * Consulta los recursos complementarios asociados a una postulacion
	 */
	public static final String CONSULTAR_LISTA_RECURSOS_COMPLEMENTARIOS = "FOVIS.Consultar.ListaRecursosComplementarios";
	
	/**
     * Consulta los recursos complementarios asociados a una postulacion
     */
    public static final String CONSULTAR_PERSONA_DETALLE = "FOVIS.consultarPersonaDetalle.idPersona";
    
    /**
     * Consulta si una solicitud de legalización se encuentra en proceso.
     */
    public static final String CONSULTAR_SOLICITUD_PROCESO = "Fovis.consultarSolicitudEnProceso.idPostulacion";
    
    
    /**
     * Consulta que se encarga de buscar las condiciones de visita por Id de visita.
     */
    public static final String CONSULTAR_CONDICIONES_VISITA_ID = "FOVIS.consultar.condiciones.visitaID";
    
    /**
     * Consulta la Licencia por Matrícula Inmobiliaria.
     */
    public static final String CONSULTAR_LICENCIA_POR_MATRICULA = "FOVIS.Consultar.LicenciaPorMatricula";
    
    /**
     * Consulta resultados de existencia y habitabilidad
     */
    public static final String CONSULTAR_RESULTADOS_EXISTENCIA_HABITABILIDAD = "FOVIS.Consultar.ResultadosExistenciaHabitabilidad";
    
    /**
     * Consulta historico desembolsos por tipo y numero identificacion
     */
    public static final String CONSULTAR_HISTORICO_DESEMBOLSOS_POR_TIPO_NUMERO_ID= "FOVIS.consultar.historico.desembolso";
    
    /**
     * Consulta historico solicitud legalizacion
     */
    public static final String CONSULTAR_HISTORICO_SOLICITUD_LEGALIZACION= "fovis.consultarHistoricoSolicitudesNovedadesFovis";
    
    /**
     * Consulta historico solicitud legalizacion por rango de fechas
     */
    public static final String CONSULTAR_HISTORICO_SOLICITUD_LEGALIZACION_RANGO_FECHAS= "fovis.consultarHistoricoSolicitudesNovedadesFovisRangoFechas";
    
    /**
     * Consulta historico solicitud legalizacion por rango de fechas
     */
    public static final String CONSULTAR_SUBSIDIOS_FOVIS_LEGALIZADOS_DESEMBOLSADOS= "fovis.consultar.SubsidiosFOVISLegalizadosDesembolsados";
    
    /**
     * Consulta historico solicitud legalizacion por rango de fechas
     */
    public static final String CONSULTAR_SUBSIDIOS_FOVIS_LEGALIZADOS_DESEMBOLSADOS_RANGO_FECHAS= "fovis.consultar.SubsidiosFOVISLegalizadosDesembolsados.RangoFechas";

    /**
     * Consulta el historico de legalizaciones asociadas a la postulacion del jefe hogar enviado por parametro de tipo y numero de
     * identificacion
     */
    public static final String CONSULTAR_HISTORICO_LEGALIZACIONES_POSTULACION_JEFE_HOGAR_TIPO_NRO_DOC = "fovis.legalizacion.desembolso.historico.jefe.hogar.tipo.numero.identificacion";

    /**
     * Consulta el historico de legalizaciones asociadas a la postulacion del jefe hogar enviado por parametro de tipo y numero de
     * identificacion
     */
    public static final String CONSULTAR_POSTULACION_PARA_LEGALIAZACION_DESEMBOLSO = "stored.procedure.consultar.postulacion.legalizacion.desembolso";

}
