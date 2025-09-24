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
import com.asopagos.subsidiomonetario.clients.AgregarDetallesProgramadosToDetallesSubsidiosAsignados;

/**
 * <b>Descripcion:</b> EJB Singleton que ejecuta el proceso automático de
 * para la inserción de los detalles programados de la tabla DetalleSubsidioAsignadoProgramado
 * a la tabla DetalleSubsidioAsignado. <br/>
 * <b>Módulo:</b> Asopagos - HU - 317 <br/>
 * 
 * <b>Historia de Usuario:</b> HU 317 - 508
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@Singleton
@Startup
public class ScheduleAlmacenarDetallesProgramadosToDetallesAsignados extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleCargarArchivosTarjetaAnibol.class);

    /**
     * 
     */
    private final static ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.EJECUTAR_INSERCION_DETALLESPROGRAMADOS_A_DETALLESSUBSIDIOS;

    /**
     * (non-Javadoc)
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
    @Timeout
    @Override
    public void run(Timer timer) {
        logger.debug("Inicio de método ScheduleAlmacenarDetallesProgramadosToDetallesAsignados.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            //se invoca el cliente para ejecutar la inserción automatica de los detalles programados a la tabla detalle de subsidios asignados
            //si es el día indicado en el periodo.
            AgregarDetallesProgramadosToDetallesSubsidiosAsignados detalleProgramado = new AgregarDetallesProgramadosToDetallesSubsidiosAsignados();
            detalleProgramado.execute();
            // Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);

            logger.debug("Fin de método ScheduleAlmacenarDetallesProgramadosToDetallesAsignados.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleAlmacenarDetallesProgramadosToDetallesAsignados.run(Timer timer)", e);
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
        }

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.process.schedule.api.ScheduleAbstract#getCurrentProcess()
     */
    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }

}
