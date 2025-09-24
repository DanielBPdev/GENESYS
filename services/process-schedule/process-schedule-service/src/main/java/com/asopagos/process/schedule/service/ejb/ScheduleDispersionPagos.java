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
import com.asopagos.subsidiomonetario.composite.clients.DispersarPagos;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de la dispersión de pagos.
 */
@Singleton
@Startup
public class ScheduleDispersionPagos extends ScheduleAbstract {

    /**
     * Proceso actual. 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.DISPERSION_PAGOS;
    
    
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
            logger.debug("Inicio de método ScheduleDispersionPagos.run(Timer timer)");
            initContextUsuarioSistema();
            DispersarPagos dispersarPagos = new DispersarPagos();
            dispersarPagos.execute();
            logger.debug("Fin de método ScheduleDispersionPagos.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleDispersionPagos.run(Timer timer)", e);
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