package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;

import com.asopagos.cartera.composite.clients.EjecutarProcesoAutomaticoCartera;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripción: </b> Bean que ejecuta el proceso automático de cálculo de
 * deuda presunta <br/>
 * <b>Historia de Usuario: </b> HU-169
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@Singleton
@Startup
public class ScheduleCalcularDeudaPresunta extends ScheduleAbstract {

	/**
	 * Proceso actual
	 */
	private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.CALCULO_DEUDA_PRESUNTA;

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
			logger.debug("Inicio de método ScheduleCalcularDeudaPresunta.run(Timer timer)");
			// Se indica el usuario al contexto
			initContextUsuarioCore();
			ejecutarProcesoAutomaticoCartera();
			logger.debug("Fin de método ScheduleCalcularDeudaPresunta.run(Timer timer)");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en ScheduleCalcularDeudaPresunta.run(Timer timer)", e);
			this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
		}
	}

	/**
	 * Método encargado invocar el servicio de cálculo y almacenamiento de la
	 * deuda presunta
	 */
	private void ejecutarProcesoAutomaticoCartera() {
		logger.debug("Inicio de método ejecutarProcesoAutomaticoCartera()");
		EjecutarProcesoAutomaticoCartera service = new EjecutarProcesoAutomaticoCartera();
		service.execute();
		logger.debug("Fin de método ejecutarProcesoAutomaticoCartera()");
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
