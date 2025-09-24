package com.asopagos.correspondencia.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class NamedQueriesConstants {
    
	/**
     * NamedQuery para actualizar el estado de la documentación de afiliación del empleador
     */
    public static final String BUSCAR_CAJA_ABIERTA_POR_SEDE = "Correspondencia.CajaCorrespondencia.buscarAbiertaPorSede";
    
    /**
     * NamedQuery para actualizar el estado de la documentación de afiliación del empleador
     */
    public static final String BUSCAR_SOLICITUDES_POR_CAJA = "Correspondencia.Solicitudes.buscarSolicitudesPorCajaCorrespondencia";
    
//    public static final String ACTUALIZAR_ESTADO_DOCUMENTACION_SOLICITUDES = "Correspondencia.solicitudes.actualizarEstadoDocumentacion";
    
//    /**
//     * Buscar una etiqueta pre impresa disponible y por tipo dado
//     */
//    public static final String ACTUALIZAR_ASIGNADO_CODIGO_PRE_IMPRESO = "Correspondencia.etiquetaPreimpresa.actualizar.estado";
    
    
    /**
     * Buscar una etiqueta pre impresa disponible y por tipo dado
     */
    public static final String CONSULTAR_ACTUALIZAR_ASIGNADO_CODIGO_PRE_IMPRESO = "Correspondencia.etiquetaPreimpresa.consultar.actualizar.estado";
    
    /**
     * Buscar solicitud por estado estado de documentos y rango de fechas
     */
    public static final String BUSCAR_ULTIMO_CODIGO_PRE_IMPRESO_POR_TIPO = "Correspondencia.etiquetaPreimpresa.ultimaGenerada.porTipo";
    
 // TRA-428
    /**
     * Buscar una etiqueta pre impresa disponible y por tipo dado
     */
    public static final String BUSCAR_CODIGO_PRE_IMPRESO_POR_TIPO = "Correspondencia.etiquetaPreimpresa.disponible.porTipo";
    
 // 111-086
//    /**
//     * Buscar solicitud por número de radicado
//     */
//    public static final String ASOCIAR_SOLICITUDES_A_CAJA_CORRESPONDENCIA = "Correspondencia.solicitudes.asociarACajaCorrespondencia";
    
    /**
     * NamedQuery para consultar el resumen de remisión back
     */
    public static final String CONSULTAR_REMISION = "Correspondencia.Consulta.Documento.Remision.Back";
    
    /**
     * NamedQuery para consultar el resumen de remisión back
     */
    public static final String CONSULTAR_RESUMEN_REMISION = "Correspondencia.Consulta.Resumen.Remision";
    
    /**
     * NamedQuery para consultar el resumen de remisión back
     */
    public static final String CONSULTAR_DOCUMENTO_REMISION = "Correspondencia.Consulta.Documento.Remision";
    
    /**
     * NamedQuery para consultar la solicitud que será actualizada
     */
    public static final String CONSULTAR_SOLICITUD_A_ACTUALIZAR = "Correspondencia.consultar.solicitud";
    
    /**
     * NamedQuery para consultar las solicitudes que serán actualizadas
     */
    public static final String CONSULTAR_SOLICITUDES_A_ACTUALIZAR = "Correspondencia.consultar.solicitudes";
    
    /**
     * NamedQuery para consultar Solicitud afiliación Persona por id solicitud Global
     */
    public static final String CONSULTAR_TIPO_SOLICITUD_PERSONA = "Correspondencia.consultar.solicitudPersona";
    
    /**
     * NamedQuery para consultar Solicitud afiliación Empleador por id solicitud Global
     */
    public static final String CONSULTAR_TIPO_SOLICITUD_EMPLEADOR = "Correspondencia.consultar.solicitudEmpleador";
    
    /**
     * NamedQuery para consultar Solicitud Novedad por id solicitud Global
     */
    public static final String CONSULTAR_TIPO_SOLICITUD_NOVEDAD = "Correspondencia.consultar.solicitudNovedad";
    
    /**
     * NamedQuery para consultar Solicitud Postulacion por id solicitud Global
     */
    public static final String CONSULTAR_TIPO_SOLICITUD_POSTULACION = "Correspondencia.consultar.solicitudPostulacion";
    
    /**
     * NamedQuery para consultar Solicitud Legalizacion por id solicitud Global
     */
    public static final String CONSULTAR_TIPO_SOLICITUD_LEGALIZACION = "Correspondencia.consultar.solicitudLegalizacion";
    
//    /**
//     * NamedQuery para actualizar el nro custodia de la SolicitudAfiliacionEmpleador
//     */
//    public static final String ACTUALIZAR_NRO_CUSTODIA_SOLICITUD_AFILIACION_EMPLEADOR = "Correspondencia.actualizar.nroCustodia.solicitudAfiliacionEmpleador";
    
//    /**
//     * NamedQuery para actualizar estadoDocumentacion en Solicitud 
//     */
//    public static final String ACTUALIZAR_ESTADO_DOCUMENTACION_SOLICITUD = "Correspondencia.actualizar.estadoDocumentacion.solicitud";
    
//    /**
//     * NamedQuery para actualizar el estado de la solicitud en SolicitudAfiliacionEmpleador
//     */
//    public static final String ACTUALIZAR_ESTADO_SOLICITUD = "Correspondencia.actualizar.estadoSolicitud.solicitudAfiliacionEmpleador";

//    /**
//     * NamedQuery para actualizar el estado de la solicitud en SolicitudAfiliacionPersona
//     */
//    public static final String ACTUALIZAR_ESTADO_SOLICITUD_PERSONA = "Correspondencia.actualizar.estadoSolicitud.solicitudAfiliacionPersona";
    
//    /**
//     * NamedQuery para actualizar el estado de la solicitud en SolicitudNovedad
//     */
//    public static final String ACTUALIZAR_ESTADO_SOLICITUD_NOVEDAD = "Correspondencia.actualizar.estadoSolicitud.solicitudNovedad";
    

	public static final String CONSULTAR_RESUMEN_REMISION_PERSONAS = "Correspondencia.Consulta.Resumen.Remision.Personas";

	public static final String CONSULTAR_DOCUMENTO_REMISION_PERSONAS = "Correspondencia.Consulta.Documento.Remision.Personas";
	
	public static final String CONSULTAR_RESUMEN_REMISION_NOVEDADES_PERSONA = "Correspondencia.Consulta.Resumen.Remision.NovedadesPersona";
	
	public static final String CONSULTAR_RESUMEN_REMISION_NOVEDADES_EMPLEADOR = "Correspondencia.Consulta.Resumen.Remision.NovedadesEmpleador";
	
	public static final String CONSULTAR_DOCUMENTO_REMISION_NOVEDADES = "Correspondencia.Consulta.Documento.Remision.Novedades";
	
	public static final String CONSULTAR_RESUMEN_REMISION_POSTULACION_FOVIS = "Correspondencia.Consulta.Resumen.Remision.PostulacionFOVIS";
	
	public static final String CONSULTAR_DOCUMENTO_REMISION_POSTULACIONES = "Correspondencia.Consulta.Documento.Remision.Postulaciones";
	
	public static final String CONSULTAR_RESUMEN_REMISION_LEGALIZACION_FOVIS = "Correspondencia.Consulta.Resumen.Remision.LegalizacionFOVIS";
    
    public static final String CONSULTAR_DOCUMENTO_REMISION_LEGALIZACION = "Correspondencia.Consulta.Documento.Remision.Legalizacion";

    public static final String CONSULTAR_RESUMEN_REMISION_NOVEDAD_FOVIS = "Correspondencia.Consulta.Resumen.Remision.NovedadFOVIS";
    
    public static final String CONSULTAR_DOCUMENTO_REMISION_NOVEDADES_FOVIS = "Correspondencia.Consulta.Documento.Remision.NovedadFOVIS";

    public static final String CONSULTAR_SOLICITUDES_AFIEMP_DOCREC_ASIGBACK = "Correspondencia.Consulta.Solicitudes.AfiEmp.DocRec.AsigBack";
}
