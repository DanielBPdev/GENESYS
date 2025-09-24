package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;

import com.asopagos.cartera.composite.clients.EjecutarProcesoAutomaticoExclusion;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripción: </b> Bean que ejecuta el proceso automático de inactivación
 * de exclusiones de cartera <br/>
 * <b>Historia de Usuario: </b> HU-240 - CC-0230546
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@Singleton
@Startup
public class ScheduleExclusionCartera extends ScheduleAbstract {

	/**
	 * Proceso actual
	 */
	private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.EXCLUSION_CARTERA;

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
			logger.debug("Inicio de método ScheduleExclusionCartera.run(Timer timer)");
			// Se indica el usuario al contexto
			initContextUsuarioCore();
			ejecutarProcesoAutomaticoExclusion();
			logger.debug("Fin de método ScheduleExclusionCartera.run(Timer timer)");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en ScheduleExclusionCartera.run(Timer timer)", e);
			this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
		}
	}

	/**
	 * Método encargado invocar el servicio de actualización de exclusiones de cartera
	 */
	private void ejecutarProcesoAutomaticoExclusion() {
		logger.debug("Inicio de método ejecutarProcesoAutomaticoExclusion");
		EjecutarProcesoAutomaticoExclusion service = new EjecutarProcesoAutomaticoExclusion();
		service.execute();
		logger.debug("Fin de método ejecutarProcesoAutomaticoExclusion");
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
