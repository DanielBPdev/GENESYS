package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.cartera.composite.clients.EjecutarProcesoAutomaticoGestionCobro;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripcion:</b> Bean que ejecuta el proceso automático de asignación de
 * la acción de cobro 2F <br/>
 * <b>Módulo:</b> Asopagos - HU 164<br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */
@Singleton
@Startup
public class ScheduleAsignacionAccion2F extends ScheduleAbstract {

	/**
	 * Proceso actual
	 */
	private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.ASIGNACION_ACCION_2F;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.process.schedule.api.ScheduleAbstract#init()
	 */
	@Override
	@PostConstruct
	public void init() {
		start();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.process.schedule.api.ScheduleAbstract#run(javax.ejb.Timer)
	 */
	@Override
	@Timeout
	public void run(Timer timer) {
		try {
			logger.debug("Inicio de método ScheduleAsignacionAccion2F.run(Timer timer)");
			// Se indica el usuario al contexto
			initContextUsuarioCore();
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.F2);
			logger.debug("Fin de método ScheduleAsignacionAccion2F.run(Timer timer)");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en ScheduleAsignacionAccion2F.run(Timer timer)", e);
			this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
		}
	}

	/**
	 * Método encargado invocar el servicio de asignación de acciones de cobro
	 * 
	 * @param accionCobro
	 *            Tipo de acción de cobro
	 */
	private void ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum accionCobro) {
		logger.debug("Inicio de método ejecutarProcesoAutomaticoGestionCobro");
		EjecutarProcesoAutomaticoGestionCobro service = new EjecutarProcesoAutomaticoGestionCobro(accionCobro);
		service.execute();
		logger.debug("Fin de método ejecutarProcesoAutomaticoGestionCobro");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.process.schedule.api.ScheduleAbstract#getCurrentProcess()
	 */
	@Override
	public ProcesoAutomaticoEnum getCurrentProcess() {
		return CURRENT_PROCESS;
	}
}
