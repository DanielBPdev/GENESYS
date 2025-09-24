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
import com.asopagos.subsidiomonetario.pagos.composite.clients.DispersarPagosCambioMedioPago;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de la dispersión de pagos de origen anulación.
 */

@Singleton
@Startup
public class ScheduleDispersionPagosOrigenCambioMedioPago extends ScheduleAbstract {

    /**
     * Proceso actual. 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.DISPERSION_PAGOS_ORIGEN_CAMBIO_MEDIO_PAGO;
    
    
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
            logger.debug("Inicio de método ScheduleDispersionPagosOrigenCambioMedioPago.run(Timer timer)");
            initContextUsuarioSistema();
            DispersarPagosCambioMedioPago dispersarPagos = new DispersarPagosCambioMedioPago();
            dispersarPagos.execute();
            logger.debug("Fin de método ScheduleDispersionPagosOrigenCambioMedioPago.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleDispersionPagosOrigenCambioMedioPago.run(Timer timer)", e);
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
