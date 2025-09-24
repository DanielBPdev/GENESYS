package com.asopagos.process.schedule.service.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio de Process Schedule
 * <b>Historia de Usuario:</b>Proceso: 1.3Novedades
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
public class NamedQueriesConstants {
    
	/**
	 * Constante para invocar NamedQuery de consulta de parametrización
	 * de Ejecuciones programadas.
	 */
    public static final String PROCESSSCHEDULE_PARAMEJECUCIONPROGRAMADA_TIPOTRANSACCION = "ProcessSchedule.ParametrizacionEjecucionProgramada.buscarPorTipoTransaccion";
    
    /**
	 * Constante para invocar NamedQuery de consulta de programaciòn dada una lista de procesos
     */
	public static final String CONSULTAR_PROGRAMACION_POR_PROCESOS = "Parametrizacion.Programacion.consultarProgramacionPorProcesos";
	
	/**
	 * Constante que representa la invocación al sp de faclien para la integración con el ERP SEVEN
	 */
	public static final String PROCEDURE_SP_FACLIEN = "Aportes.ejecutar.SP.FACLIEN";
	
	/**
	 * Constante que representa la invocación al sp de popvdor para la integración con el ERP SEVEN
	 */
	public static final String PROCEDURE_SP_POPVDOR = "Aportes.ejecutar.SP.POPVDOR";
	
	/**
	 * Constante que representa la invocación al sp de gnterce-empresas para la integración con el ERP SEVEN
	 */
	public static final String PROCEDURE_SP_GNTERCE_EMPRESAS = "Aportes.ejecutar.SP.GNTERCE.EMPRESAS";
	
	/**
	 * Constante que representa la invocación al sp de gnterce-personas para la integración con el ERP SEVEN
	 */
	public static final String PROCEDURE_SP_GNTERCE_PERSONAS = "Aportes.ejecutar.SP.GNTERCE.PERSONAS";
	
	/**
	 * Constante que representa la invocación al sp de rcnmcont y rpocxpag de subsidios para la integración con el ERP SEVEN
	 */
	public static final String PROCEDURE_SP_RCNMCONT_Y_RPOCXPAG_SUBSIDIOS = "Subsidios.ejecutar.SP.RCNMCONT.RPOCXPAG.SUBSIDIOS";
	
	public static final String PROCEDURE_SP_GESTIONAR_APORTES_CORRECCIONES_ERP = "Aportes.ejecutar.GESTIONAR.APORTES.CORRECCIONES.ERP";
	public static final String PROCEDURE_SP_GESTIONAR_APORTES_DEVOLUCIONES_ERP = "Aportes.ejecutar.GESTIONAR.APORTES.DEVOLUCIONES.ERP";
	public static final String PROCEDURE_SP_GESTIONAR_APORTES_EMPRESAS_ERP = "Aportes.ejecutar.GESTIONAR.APORTES.EMPRESAS.ERP";
	public static final String PROCEDURE_SP_GESTIONAR_APORTES_ERP = "Aportes.ejecutar.GESTIONAR.APORTES.ERP";
	public static final String PROCEDURE_SP_GESTIONAR_CARTERA_ERP = "Aportes.ejecutar.GESTIONAR.CARTERA.ERP";
	public static final String PROCEDURE_SP_GESTIONAR_RCNMCONT_AFILIA_PERSONAS = "Aportes.ejecutar.GESTIONAR.RCNMCONT.AFILIA.PERSONAS";
	public static final String PROCEDURE_SP_GESTIONAR_ASIGNACION_SUBSIDIO_FOVIS_ERP = "Aportes.ejecutar.GESTIONAR.ASIGNACION.SUBSIDIO.FOVIS.ERP";
	public static final String PROCEDURE_SP_GESTIONAR_SUBSIDIO_FOVIS_ERP = "Aportes.ejecutar.GESTIONAR.SUBSIDIO.FOVIS.ERP";
	public static final String PROCEDURE_SP_GESTIONAR_APORTES_PILA_RTSCONSD_ERP = "Aportes.ejecutar.GESTIONAR.APORTES.PILA.RTSCONSD.ERP";

	public static final String PROCESSSCHEDULE_PARAMEJECUCIONPROGRAMADA_LIBERACION_BLOQUE9 = "Parametrizacion.Programacion.liberarPlanillasBloque9";

	/**
	 * Constante para invocar NamedQuery de consulta el resultado de la parametrizacion
	 */
	public static final String PROCESSSCHEDULE_RESULTADOEJECUCION_PROCESO= "ProcessSchedule.resultadoEjecucionProgramada.buscarPorProceso";

}
