package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.process.schedule.api.ScheduleAbstract;
import com.asopagos.subsidiomonetario.composite.clients.GestionarColaEjecucionLiquidacion;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de Suspensión automática de postulación por cambio de año calendario.
 * 
 * proceso 3.2.1
 * 
 * @author Andrés Valbuena <anvalbuena@heinsohn.com.co>
 */
@Singleton
@Startup
public class ScheduleGestionLiquidacionesParalelasSubsidio extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleGestionLiquidacionesParalelasSubsidio.class);

    /**
     * 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.GESTION_LIQUIDACIONES_PARALELAS_SUBSIDIO;

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
        logger.debug("Inicio de método GestionarColaEjecucionLiquidacion.run(Timer timer)");
        logger.info("Inicio de método GestionarColaEjecucionLiquidacion.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            //Se invoca el cliente para radicar la novedad automatica para procesar el vencimiento de subsidios asignados
            GestionarColaEjecucionLiquidacion gestionLiqui = new GestionarColaEjecucionLiquidacion();
            gestionLiqui.execute();

            //Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);

            logger.debug("Fin de método GestionarColaEjecucionLiquidacion.run(Timer timer)");
            logger.info("Fin de método GestionarColaEjecucionLiquidacion.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en GestionarColaEjecucionLiquidacion.run(Timer timer)", e);
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
