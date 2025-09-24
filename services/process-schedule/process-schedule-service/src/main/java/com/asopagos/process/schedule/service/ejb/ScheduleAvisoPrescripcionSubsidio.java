package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;

import com.asopagos.cartera.composite.clients.EjecutarProcesoAutomaticoGestionCobro;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;
import com.asopagos.subsidiomonetario.pagos.composite.clients.EjecucionAvisoPrescripcionSubsidio;

/**
 * <b>Descripción: </b> Bean que ejecuta el proceso automático de asignación de
 * la acción de cobro LC5A <br/>
 * <b>Historia de Usuario: </b> HU-164
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@Singleton
@Startup
public class ScheduleAvisoPrescripcionSubsidio extends ScheduleAbstract {

	/**
	 * Proceso actual
	 */
	private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.AVISIO_PRESCRIPCION_SUBSIDIO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.process.schedule.api.ScheduleAbstract#init()
	 */
	@PostConstruct
	@Override
	public void init() {
		start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.process.schedule.api.ScheduleAbstract#run(javax.ejb.Timer)
	 */
	@Timeout
	@Override
	public void run(Timer timer) {
		try {
			logger.debug("Inicio de método ScheduleAvisoPrescripcionSubsidio.run(Timer timer)");
			// Se indica el usuario al contexto
			initContextUsuarioCore();
			ejecutarProcesoAutomaticoAvisoPrescripcionSubsidio();
			logger.debug("Fin de método ScheduleAvisoPrescripcionSubsidio.run(Timer timer)");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en ScheduleAvisoPrescripcionSubsidio.run(Timer timer)", e);
			this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
		}
	}

	/**
	 * Método encargado invocar el servicio de asignación de acciones de cobro
	 * 
	 * @param accionCobro
	 *            Tipo de acción de cobro
	 */
	private void ejecutarProcesoAutomaticoAvisoPrescripcionSubsidio() {
		logger.debug("Inicio de método ejecutarProcesoAutomaticoAvisoPrescripcionSubsidio");
		EjecucionAvisoPrescripcionSubsidio service = new EjecucionAvisoPrescripcionSubsidio();
		service.execute();
		logger.debug("Fin de método ejecutarProcesoAutomaticoAvisoPrescripcionSubsidio");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.process.schedule.api.ScheduleAbstract#getCurrentProcess()
	 */
	@Override
	public ProcesoAutomaticoEnum getCurrentProcess() {
		return CURRENT_PROCESS;
	}
}
