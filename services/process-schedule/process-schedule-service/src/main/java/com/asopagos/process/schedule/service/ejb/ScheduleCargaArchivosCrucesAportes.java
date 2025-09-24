package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.aportes.clients.CargarAutomaticamenteArchivosCrucesAportesAutomatico;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de
 * carga de archivos de descuento enviados al FTP por las entidades de descuento
 * 
 * <b>Historia de Usuario:</b> HU 311-432
 * 
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona.</a>
 *
 */
@Singleton
@Startup
public class ScheduleCargaArchivosCrucesAportes extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleCargaArchivosCrucesAportes.class);

    /**
     * 
     */

    private final static ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.CARGA_AUTOMATICA_ARCHIVOS_CRUCES_APORTES;

    /**
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
     * 
     * @see com.asopagos.process.schedule.api.ScheduleAbstract#run(javax.ejb.Timer)
     */
    @Timeout
    @Override
    public void run(Timer timer) {
        logger.debug("Inicio de método ScheduleCargaArchivosDescuento.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            //Se invoca el cliente para ejecutar la carga automática de archivos de descuento
            CargarAutomaticamenteArchivosCrucesAportesAutomatico cargaAutomatica = new CargarAutomaticamenteArchivosCrucesAportesAutomatico();
            cargaAutomatica.execute();

            // Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);

            logger.debug("Fin de método ScheduleCargaArchivosDescuento.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleCargaArchivosDescuento.run(Timer timer)", e);
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
