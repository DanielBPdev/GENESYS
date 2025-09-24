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
import com.asopagos.novedades.clients.ActualizarSolicitudesNovedadDesistimiento;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de anulación
 * de un proceso de registro de novedad múltiple.
 * 
 * <b>Historia de Usuario:</b> HU-135-449
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Singleton
@Startup
public class ScheduleRegistroNovedadMultipleNoFinalizada extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleRegistroNovedadMultipleNoFinalizada.class);

    /**
     * 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.REGISTRO_NOVEDAD_MULTIPLE_NO_FINALIZADA;

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
        logger.debug("Inicio de método ScheduleRegistroNovedadMultipleNoFinalizada.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            /*
             * Se invoca el cliente para Desistir y Cerrar la solicitud por Registro de Novedad Múltiple
             * No finalizada
             */
            ActualizarSolicitudesNovedadDesistimiento actualizarSolicitudes = new ActualizarSolicitudesNovedadDesistimiento();
            actualizarSolicitudes.execute();

            //Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);

            logger.debug("Fin de método ScheduleRegistroNovedadMultipleNoFinalizada.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleRegistroNovedadMultipleNoFinalizada.run(Timer timer)", e);
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
