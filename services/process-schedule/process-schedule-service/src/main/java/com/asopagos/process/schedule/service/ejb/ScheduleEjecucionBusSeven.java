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
import com.asopagos.seven.clients.EjecutarEsbSeven;

@Singleton
@Startup
public class ScheduleEjecucionBusSeven extends ScheduleAbstract{

	/**
     * Proceso actual. 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.EJECUCION_BUS_SEVEN;

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
            logger.debug("Inicio de método ScheduleEjecucionBusSeven.run(Timer timer)");
            initContextUsuarioSistema();
            EjecutarEsbSeven ejecutarEsbSeven = new EjecutarEsbSeven();
            ejecutarEsbSeven.execute();
            logger.debug("Fin de método ScheduleEjecucionBusSeven.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleEjecucionBusSeven.run(Timer timer)", e);
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
