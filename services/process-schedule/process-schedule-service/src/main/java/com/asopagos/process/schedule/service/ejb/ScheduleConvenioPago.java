package com.asopagos.process.schedule.service.ejb;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.cartera.composite.clients.EjecutarProcesoAutomaticoConvenioPago;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de la Gestión de convenios de pago.
 * @author Angélica Toro Murillo<atoro@heinsohn.com.co>
 */
@Singleton
@Startup
public class ScheduleConvenioPago extends ScheduleAbstract {

    /**
     * Proceso actual. 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.CONVENIO_PAGO;
    
	
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
		    logger.debug("Inicio de método ScheduleConvenioPago.run(Timer timer)");
		    // Se indica el usuario al contexto
		    initContextUsuarioCore();
		    EjecutarProcesoAutomaticoConvenioPago ejecutarProcesoAutomaticoService = new EjecutarProcesoAutomaticoConvenioPago();
		    ejecutarProcesoAutomaticoService.execute();
			logger.debug("Fin de método ScheduleConvenioPago.run(Timer timer)");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en ScheduleConvenioPago.run(Timer timer)", e);
			this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, 
			        getCurrentProcess(),
					TipoResultadoProcesoEnum.EJECUCION);
		}
	}
	

    /**
     * @return the currentprocess
     */
    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }
	
}
