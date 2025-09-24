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
 * <b>Descripción: </b> Bean que ejecuta el proceso automático de asignación de
 * acciones de cobro intermedias <br/>
 * <b>Historia de Usuario: </b> HU-164
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@Singleton
@Startup
public class ScheduleAsignacionAccionCierre extends ScheduleAbstract {

	/**
	 * Proceso actual
	 */
	private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.ASIGNACION_ACCION_CIERRE;

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
			logger.debug("Inicio de método ScheduleAsignacionAccionCierre.run(Timer timer)");
			// Se indica el usuario al contexto
			initContextUsuarioCore();
			
			// LC1 - Método 1
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.AB1);
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.BC1);
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.CD1);
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.DE1);
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.EF1);
			
			// LC1 - Método 2
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.AB2);
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.BC2);
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.CD2);
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.DE2);
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.EF2);
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.FG2);
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.GH2);
			
			// LC4
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.L4AC);
			
			// LC5
			ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.L5AC);
			
			logger.debug("Fin de método ScheduleAsignacionAccionCierre.run(Timer timer)");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en ScheduleAsignacionAccionCierre.run(Timer timer)", e);
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
