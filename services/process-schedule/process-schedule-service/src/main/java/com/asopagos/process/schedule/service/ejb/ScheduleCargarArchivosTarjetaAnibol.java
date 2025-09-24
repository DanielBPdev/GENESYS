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
import com.asopagos.subsidiomonetario.pagos.composite.clients.CargarArchivoConsumoTarjetaAnibol;

/**
 * <b>Descripcion:</b> EJB Singleton que ejecuta el proceso automático de
 * carga de archivos de consumo de los registros de tarjetas por parte de ANIBOL
 * para efectuarse el retiro correspondiente.<br/>
 * <b>Módulo:</b> Asopagos - HU - 31 <br/>
 * 
 * <b>Historia de Usuario:</b> HU 31 - Anexo-Validación y cargue archivo consumos_validado
 *
 * @author  <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@Singleton
@Startup
public class ScheduleCargarArchivosTarjetaAnibol extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleCargarArchivosTarjetaAnibol.class);

    /**
     * 
     */
    private final static ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.EJECUTAR_CARGA_ARCHIVO_CONSUMO_TARJETAS_ANIBOL;

    
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
        logger.debug("Inicio de método ScheduleCargarArchivosTarjetaAnibol.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            //Se invoca el cliente para ejecutar la carga automática de archivos de descuento
            CargarArchivoConsumoTarjetaAnibol archivoConsumoTarjetaAnibol = new CargarArchivoConsumoTarjetaAnibol();
            archivoConsumoTarjetaAnibol.execute();
            // Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);

            logger.debug("Fin de método ScheduleCargarArchivosTarjetaAnibol.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleCargarArchivosTarjetaAnibol.run(Timer timer)", e);
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
