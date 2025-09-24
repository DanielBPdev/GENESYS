package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timer;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.process.schedule.api.ScheduleAbstract;
import com.asopagos.subsidiomonetario.clients.EjecutarLiquidacionMasiva;
import com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO;

/**
 * <b>Descripcion:</b> EJB Singleton que ejecuta el proceso automático de liquidacion
 * de subsidio monetario <br/>
 * <b>Módulo:</b> Asopagos - HU-311-434 <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */
@Singleton
@Startup
public class ScheduleEjecutarLiquidacionSubsidioMonetario extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleEjecutarLiquidacionSubsidioMonetario.class);

    /**
     * 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.EJECUTAR_LIQUIDACION_SUBSIDIO_MONETARIO_PROGRAMADO;

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

    /**
     * (non-Javadoc)
     * @see com.asopagos.process.schedule.api.ScheduleAbstract#run(javax.ejb.Timer)
     */
    @Override
    public void run(Timer timer) {
        logger.debug("Inicio de método ScheduleEjecutarLiquidacionSubsidioMonetario.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            //Se invoca el cliente para ejecutar el proceso masivo
            SolicitudLiquidacionSubsidioModeloDTO slm = new SolicitudLiquidacionSubsidioModeloDTO();
            EjecutarLiquidacionMasiva ejecutarLiquidacionMasiva = new EjecutarLiquidacionMasiva(new Long(1), slm);
            ejecutarLiquidacionMasiva.execute();

            //Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
            logger.debug("Fin de método ScheduleEjecutarLiquidacionSubsidioMonetario.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleEjecutarLiquidacionSubsidioMonetario.run(Timer timer)", e);
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
