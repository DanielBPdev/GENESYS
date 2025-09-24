package com.asopagos.process.schedule.service.ejb;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.cartera.composite.clients.AsignarAccionesPreventivas;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de Gestión preventiva.
 * @author Angélica Toro Murillo<atoro@heinsohn.com.co>
 */
@Singleton
@Startup
public class ScheduleGestionPreventiva extends ScheduleAbstract {

    /**
     * Proceso actual. 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.GESTION_PREVENTIVA_CARTERA;
    
	
	/* (non-Javadoc)
	 * @see com.asopagos.process.schedule.api.ScheduleAbstract#init()
	 */
	@PostConstruct
	@Override
    public void init() {
		start();
    }
	
	/* (non-Javadoc)
	 * @see com.asopagos.process.schedule.api.ScheduleAbstract#run(javax.ejb.Timer)
	 */
	@Timeout
	@Override
	public void run(Timer timer){
		try {
		    logger.debug("Inicio de método ScheduleGestionPreventiva.run(Timer timer)");
		    // Se indica el usuario al contexto
		    initContextUsuarioCore();
			asignarGestionPreventiva(Boolean.TRUE);
			logger.debug("Fin de método ScheduleGestionPreventiva.run(Timer timer)");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en ScheduleGestionPreventiva.run(Timer timer)", e);
			this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, 
			        getCurrentProcess(),
					TipoResultadoProcesoEnum.EJECUCION);
		}
	}
	
	/**
	 * Método encargado de invocar el servicio de asigna la gestión preventiva.
	 * @param simulaciones a gestionar.
	 */
	private void asignarGestionPreventiva(Boolean automatico){
	    logger.debug("Inicio de método asignarGestionPreventiva(List<SimulacionDTO> simulaciones)");
	    AsignarAccionesPreventivas asignarAccionesService = new AsignarAccionesPreventivas(automatico);
	    asignarAccionesService.execute();
	    logger.debug("Fin de método asignarGestionPreventiva(List<SimulacionDTO> simulaciones)");
	}

    /**
     * @return the currentprocess
     */    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }
	
}
