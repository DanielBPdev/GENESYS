package com.asopagos.archivos.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class NamedQueriesConstants {

    public static final String ARCHIVOS_CONSULTAR_PROPIETARIO_POR_TIPO_ID_NUMERO_ID_TIPO_PROPIETARIO = "PropietarioArchivo.buscarPorTipoIdNumeroIdTipoPropietario";
    
    public static final String ARCHIVOS_CONSULTAR_VERSION_ARCHIVO = "VersionArchivo.buscarVersionArchivoParaDocumento";

	public static final String ARCHIVOS_CONSULTAR_VERSION_ARCHIVO_ID_ARCHIVO = "VersionArchivo.consultarVersionArchivoPorIdArchivoAlmacenado";
    
    public static final String ELIMINAR_VERSION_ARCHIVO_ID = "VersionArchivo.eliminarVersionArchivoPorIdentificador";

	public static final String ELIMINAR_DOCUMENTO_SOPORTE_POR_IDENTIFICADOR = "VersionArchivo.eliminarDocumentoSoportePorIdentificadorVersion";
    
    public static final String ELIMINAR_VERSION_ARCHIVO_ID_VERSION = "VersionArchivo.eliminarVersionArchivoPorIdentificadorVersion";
    
    public static final String ARCHIVOS_ELIMINAR_ARCHIVO = "VersionArchivo.eliminarArchivoAlmacenado";
    
    public static final String CONSULTAR_ARCHIVO_POR_IDENTIFICADOR = "VersionArchivo.consultarArchivoPorIdentificador";
    
    /**
     * Consulta que obtiene una solicitud global por idSolicitud
     */
	public static final String BUSCAR_SOLICITUD_POR_ID_SOLICITUD = "Solicitudes.buscarSolicitud.porIdSolicitud";
	
	/**
     * Consulta que obtiene una Persona por id
     */
	public static final String BUSCAR_PERSONA_POR_ID = "Solicitudes.buscarPersona.porId";
	
	/**
     * Consulta que obtiene un requisito documental en base a su id solicitud, id requisito y id persona
     */
	public static final String BUSCAR_REQUISITO_DOCUMENTAL_POR_ID_SOLICITUD_ID_REQUISITO_ID_PERSONA = 
			"Archivos.buscarRequisitoDocumental.PorSolicitudRequisitoPersona";
	
	/**
	 * Consultas nombradas que permiten obtener la solicitud especifica del proceso 
	 * y los datos del propietario del documento para escaneo masivo  
	 */
	public static final String BUSCAR_SOLICITUD_POSTULACION_FOVIS_PRESENCIAL_POR_ID_SOLICITUD = 
		"Solicitudes.buscarSolicitud.postulacionFOVIS.presencial.porIdSolicitud";
	
	public static final String BUSCAR_SOLICITUD_NOVEDADES_EMPRESAS_PRESENCIAL_POR_ID_SOLICITUD = 
		"Solicitudes.buscarSolicitud.novedadesEmpresas.presencial.porIdSolicitud";
	
	public static final String BUSCAR_SOLICITUD_NOVEDADES_PERSONAS_PRESENCIAL_POR_ID_SOLICITUD = 
		"Solicitudes.buscarSolicitud.novedadesPersonas.presencial.porIdSolicitud";
	
	public static final String BUSCAR_SOLICITUD_AFILIACION_EMPRESAS_PRESENCIAL_POR_ID_SOLICITUD = 
		"Solicitudes.buscarSolicitud.solicitudAfiliacionEmpresas.presencial.porIdSolicitud";
	
	public static final String BUSCAR_SOLICITUD_AFILIACION_PERSONAS_PRESENCIAL_POR_ID_SOLICITUD = 
		"Solicitudes.buscarSolicitud.solicitudAfiliacionPersonas.presencial.porIdSolicitud";
	
	public static final String BUSCAR_SOLICITUD_LEGALIZACION_DESEMBOLSO_FOVIS_POR_ID_SOLICITUD = 
			"Solicitudes.buscarSolicitud.legalizacionDesembolsoFOVIS.porIdSolicitud";
	
	public static final String BUSCAR_SOLICITUD_NOVEDAD_FOVIS_REGULAR_ESPECIAL_POR_ID_SOLICITUD = 
			"Solicitudes.buscarSolicitud.novedadesFOVIS.regularEspecial.porIdSolicitud";
			
	/**
	 * Constante que representa la consulta de la metadata restante del archivo para ser enviado a folium
	 */
	public static final String CONSULTAR_METADATA_RESTANTE_ARCHIVO = "Archivos.consultar.metadataRestanteDelArchivo";
	
	/**
	 * Constante que representa la consulta que persiste la respuesta del ECM externo. 
	 */
	public static final String REGISTRAR_RESPUESTA_ECM_EXTERNO = "Archivos.registrar.respuestaECMExterno";

	//Cambio 20/01/2023
	/**
	 * Constante que representa la consulta de la metadata restante del archivo para ser enviado a folium
	 */
	public static final String CONSULTAR_METADATA_NUMERO_RADICACION = "Archivos.consultar.metadataNumeroRadicacion";
	
	public static final String CONSULTAR_METADATA_NUMERO_RADICACION_INSTANCIA_PROCESO = "Archivos.consultar.metadataNumeroRadicacion.instanciaProceso";

	public static final String CONSULTAR_PERSONA_NUMERO_RADICACION_AFLAFE = "Archivos.consultar.persona.numeroRadicacion.aflafe";

	public static final String CONSULTAR_PERSONA_NUMERO_RADICACION_PFOVIS = "Archivos.consultar.persona.numeroRadicacion.pfovis";

	public static final String CONSULTAR_PERSONA_NUMERO_RADICACION_AFLTRA = "Archivos.consultar.persona.numeroRadicacion.afltra";

	public static final String CONSULTAR_PERSONA_NUMERO_RADICACION_NFOVIS = "Archivos.consultar.persona.numeroRadicacion.nfovis";

	public static final String CONSULTAR_PERSONA_NUMERO_RADICACION_AFLNVE = "Archivos.consultar.persona.numeroRadicacion.aflnve";

	public static final String CONSULTAR_PERSONA_NUMERO_RADICACION_AFLNIP = "Archivos.consultar.persona.numeroRadicacion.aflnip";

}
