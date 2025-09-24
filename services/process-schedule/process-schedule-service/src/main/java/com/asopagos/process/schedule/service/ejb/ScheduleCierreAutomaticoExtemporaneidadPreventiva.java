package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.cartera.composite.clients.CierreMasivoSolicitudPreventivaAgrupadora;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripcion:</b> Clase que diariamente verifica si todas las
 * solicitudes individuales de una tarea agrupadora estan cerradas
 * para luego esta ser cerrada<br/>
 * <b>Módulo:</b> Asopagos - HU 161<br/>
 *
 * @author  <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */
@Singleton
@Startup
public class ScheduleCierreAutomaticoExtemporaneidadPreventiva extends ScheduleAbstract {
    
    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleCierreAutomaticoExtemporaneidadPreventiva.class);
    
    /**
     * 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.CIERRE_AUTOMATICO_EXTEMPORANEIDAD_PREVENTIVA;

    /** (non-Javadoc)
     * @see com.asopagos.process.schedule.api.ScheduleAbstract#init()
     */
    @Override
    @PostConstruct
    public void init() {
        start();
    }

    /** (non-Javadoc)
     * @see com.asopagos.process.schedule.api.ScheduleAbstract#run(javax.ejb.Timer)
     */
    @Timeout
    @Override
    public void run(Timer timer) {
        logger.debug("Inicio de método ScheduleCierreAutomaticoExtemporaneidadPreventiva.run(Timer timer)");
        try{
             // Se indica el usuario al contexto
            logger.error("0Cierre Masivo info antes:");
            initContextUsuarioCore();
            logger.error("0Cierre Masivo info despuest context:");
            CierreMasivoSolicitudPreventivaAgrupadora cierreMasivoSolicitudPreventivaAgrupadora = new CierreMasivoSolicitudPreventivaAgrupadora();
            cierreMasivoSolicitudPreventivaAgrupadora.execute();
            logger.error("0Cierre Masivo info cierreMasivoSolicitudPreventivaAgrupadora");
            //Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
        logger.debug("Fin de método ScheduleCierreAutomaticoExtemporaneidadPreventiva.run(Timer timer)");
        }catch(Exception e){
            logger.error("Ocurrió un error inesperado en ScheduleCierreAutomaticoExtemporaneidadPreventiva.run(Timer timer)", e);
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
        }
    }

    /** (non-Javadoc)
     * @see com.asopagos.process.schedule.api.ScheduleAbstract#getCurrentProcess()
     */
    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }

}
