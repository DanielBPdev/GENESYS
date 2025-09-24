package com.asopagos.process.schedule.service.ejb;

import com.asopagos.cartera.composite.clients.EjecutarProcesoAutomaticoFirmezaTitulo;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;

/**
 * <b>Descripción: </b> Bean que ejecuta el proceso automático de asignación de
 * la acción de cobro 1A <br/>
 * <b>Historia de Usuario: </b> HU-164
 *
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 * Benavides</a>
 */
@Singleton
@Startup
public class ScheduleFirmezaTitulo extends ScheduleAbstract {
    /**
     * Proceso actual
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.CARTERA_1702_FIRMEZA_TITULO;

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.process.schedule.api.ScheduleAbstract#init()
     */
    @PostConstruct
    @Override
    public void init() {
        start();
    }

    @Timeout
    @Override
    public void run(Timer timer) {
        try {
            logger.info("Inicio de método ScheduleFirmezaTitulo.run(Timer timer)");
            // Se indica el usuario al contexto
            initContextUsuarioCore();
            ejecutarProcesoAutomaticoFirmezaTitulo();
            logger.info("Fin de método ScheduleFirmezaTitulo.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleFirmezaTitulo.run(Timer timer)", e);
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
        }
    }


    /**
     * Método encargado invocar el servicio de guardarNotificacion
     *
     */
    private void ejecutarProcesoAutomaticoFirmezaTitulo() {
        logger.info("Inicio de método ejecutarProcesoAutomaticoFirmezaTitulo");
        EjecutarProcesoAutomaticoFirmezaTitulo service = new EjecutarProcesoAutomaticoFirmezaTitulo();
        service.execute();
        logger.info("Fin de método ejecutarProcesoAutomaticoFirmezaTitulo");
    }

    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }
}
