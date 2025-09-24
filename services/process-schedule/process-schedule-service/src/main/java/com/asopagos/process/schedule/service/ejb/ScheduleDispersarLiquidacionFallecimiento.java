package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;

import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;
import com.asopagos.subsidiomonetario.pagos.composite.clients.DispersarPagosLiquidacionFallecimiento;

@Singleton
@Startup
public class ScheduleDispersarLiquidacionFallecimiento extends ScheduleAbstract {

	
	/**
     * Proceso actual. 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.DISPERSION_LIQUIDACION_FALLECIMIENTO;
    
    
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
            logger.debug("Inicio de método ScheduleDispersarLiquidacionFallecimiento.run(Timer timer)");
            initContextUsuarioSistema();
            DispersarPagosLiquidacionFallecimiento dispersarPagos = new DispersarPagosLiquidacionFallecimiento();
            dispersarPagos.execute();
            logger.debug("Fin de método ScheduleDispersarLiquidacionFallecimiento.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleDispersarLiquidacionFallecimiento.run(Timer timer)", e);
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
