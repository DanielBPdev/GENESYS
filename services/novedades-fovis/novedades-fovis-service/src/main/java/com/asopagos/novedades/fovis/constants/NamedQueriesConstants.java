package com.asopagos.novedades.fovis.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio de Novedades FOVIS <b>Historia de Usuario:</b>
 * Proceso: 3.2.5 FOVIS
 * 
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 */
public class NamedQueriesConstants {

    /**
     * Constante que hace referencia a la consulta que obtiene las postulaciones para generar la novedad automatica por suspencion
     * automatica por cambio de año
     */
    public static final String CONSULTAR_POSTULACIONES_NOVEDAD_SUSPENCION_CAMBIO_ANO = "novedades.fovis.consultarPostulacionesSuspencionAutomaticaCambioAno";
    /**
     * Constante que hace referencia a la consulta que obtiene la solicitud de novedad fovis por el id de la solicitud global.
     */
    public static final String CONSULTAR_SOLICITUD_NOVEDAD_FOVIS_POR_ID_SOLICITUD_GLOBAL = "novedades.fovis.consultar.por.idSolicitudGlobal";

    /**
     * Constante que hace referencia a la consulta que obtiene la solicitud de novedad fovis por el id postulacion la transaccion y el resultado de proceso
     */
    public static final String CONSULTAR_SOLICITUD_NOVEDAD_FOVIS_POR_ID_POSTULACION_TRANSACCION_RESULTADO = "novedades.fovis.consultar.solicitud.por.idPostulacion.tipoTransaccion.resultado";

    /**
     * Constante que hace referencia a la consulta que obtiene las postulaciones para generar la novedad automatica por rechazo
     * automatico por cambio de año
     */
    public static final String CONSULTAR_POSTULACIONES_NOVEDAD_RECHAZO_CAMBIO_ANO = "novedades.fovis.consultarPostulacionesRechazoAutomaticaCambioAno";

    /**
     * Constante que hace referencia a la consulta que obtiene las postulaciones para generar la novedad automatica por Vencimiendo de
     * Subsidios
     */
    public static final String CONSULTAR_POSTULACIONES_NOVEDAD_VENCIMIENTO_SUBSIDIOS = "novedades.fovis.consultarPostulacionesVencimiendoSubsidios";

    /**
     * Constante que hace referencia a la consulta que obtiene la solicitud de analisis de novedad de persona que afecta postulacion FOVIS
     * con los datos detallados del registro
     */
    public static final String CONSULTAR_SOLICITUD_ANALISIS_NOVEDAD_AFECTA_FOVIS = "novedades.fovis.consultar.solicitud.analisis.novedad.afecta.postulacion";
    
    /**
     * Constante que hace referencia a la consulta que obtiene la solicitud 
     */
    public static final String CONSULTAR_SOLICITUD_ANALISIS_ID_SOLICITUD = "novedades.fovis.consultar.solicitud.analisis.novedad.id";

    /**
     * Constante que hace referencia a la consulta que obtiene el acto de aceptación de una novedad de prorroga Fovis
     */
    public static final String CONSULTAR_ACTO_ACEPTACION_PRORROGA_POR_SOLICITUD_NOVEDAD_FOVIS = "novedades.fovis.consultar.ActoAceptacionProrrogaFovis.SolicitudNovedadFovis";

    /**
     * Constante que hace referencia a la consulta que obtiene los datos de las novedaddes de prorroga Fovis que se mostraran en el reporte.
     */
    public static final String CONSULTAR_SOLICITIDES_NOVEDAD_FOVIS_PRORROGA_REPORTE = "novedades.fovis.consultar.SolicitudNovedadFovis.Prorroga.Reporte";
    
	/**
	 * Constante que hace referencia a la consulta que obtiene el historico de
	 * novedades del hogar
	 */
	public static final String CONSULTAR_HISTORICO_NOVEDADES_HOGAR = "fovis.novedades.historico.by.numeroPostulacion";
	
	/**
	 * Constante que hace referencia a la consulta que obtiene el historico de
	 * novedades del hogar
	 */
	public static final String CONSULTAR_HISTORICO_SOLICITUDES_NOVEDADES = "novedades.fovis.consultarHistoricoSolicitudesNovedadesFovis";

	/**
	 * Constante que hace referencia a la consulta que obtiene el historico de
	 * novedades del hogar por rango de fechas
	 */
	public static final String CONSULTAR_HISTORICO_SOLICITUDES_NOVEDADES_RANGO_FECHAS = "novedades.fovis.consultarHistoricoSolicitudesNovedadesFovisRangoFechas";

    /**
     * Constante con el nombre de la consulta que obtiene la información de las solicitudes asociadas a una postulacion
     */
    public static final String CONSULTAR_SOLICITUDES_ASOCIADAS_POSTULACION = "fovis.consultar.solicitudes.asociadas.postulacion.by.id";

    /**
     * Constante con la consulta para la inhabilidad de una persona por tipo y nro de documento  
     */
    public static final String CONSULTAR_INHABILIDAD_PERSONA_BY_TIPO_NRO = "fovis.novedades.consultar.inhabilidad.persona";
    
    /**
     * Constante con la consulta para la inhabilidad del hogar por tipo y nro de documento jefe hogar
     */
    public static final String CONSULTAR_INHABILIDAD_HOGAR_BY_TIPO_NRO_JEFE = "fovis.novedades.consultar.inhabilidad.postulacion.jefeHogar";
    
    /**
     * Representa la consulta las postulaciones fovis asociadas a prorroga de acuerdo a la lista de solicitud de novedad enviada
     */
    public static final String CONSULTAR_POSTULACION_PRORROBA_BY_SOLICITUD_NOVEDAD = "fovis.consultar.postulacion.prorroga.by.solicitudNovedad";
}
