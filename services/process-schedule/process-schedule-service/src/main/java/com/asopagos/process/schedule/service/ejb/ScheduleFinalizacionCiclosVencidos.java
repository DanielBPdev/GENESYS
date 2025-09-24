package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.cartera.composite.clients.FinalizarCiclosVencidos;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadMasiva;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de finalización del ciclo de fiscalización.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
@Singleton
@Startup
public class ScheduleFinalizacionCiclosVencidos extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleFinalizacionCiclosVencidos.class);

    /**
     * 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.FINALIZACION_CICLOS_VENCIDOS;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.process.schedule.api.ScheduleAbstract#init()
     */
    @Override
    @PostConstruct
    public void init() {
        start();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.process.schedule.api.ScheduleAbstract#run(javax.ejb.Timer)
     */
    @Timeout
    @Override
    public void run(Timer timer) {
        logger.debug("Inicio de método ScheduleFinalizacionCiclosVencidos.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            FinalizarCiclosVencidos finalizarCicloService = new FinalizarCiclosVencidos();
            finalizarCicloService.execute();
            //Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);

            logger.debug("Fin de método ScheduleFinalizacionCiclosVencidos.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleFinalizacionCiclosVencidos.run(Timer timer)", e);
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
        }
    }

    /**
     * 
     * @return
     */
    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }
}
