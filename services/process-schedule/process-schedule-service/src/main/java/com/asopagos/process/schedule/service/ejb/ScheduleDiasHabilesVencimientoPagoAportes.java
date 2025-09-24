package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.aportes.composite.clients.VerificarDiaVencimientoAportantes;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripcion:</b> Clase que invoca proceso automático encargado de actualizar los días hábiles para la fecha de vencimiento<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */
@Singleton
@Startup
public class ScheduleDiasHabilesVencimientoPagoAportes extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleDiasHabilesVencimientoPagoAportes.class);

    /**
     * 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.ACTUALIZACION_DIAS_HABILES_VENCIMIENTO;

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
        logger.debug("Inicio de método ScheduleDiasHabilesVencimiento.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioCore();
            /* Se ejecuta el servicio */
            verificarDiaVencimientoAportantes();  
            //Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
            logger.debug("Fin de método ScheduleDiasHabilesVencimiento.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleDiasHabilesVencimiento.run(Timer timer)", e);
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

    /**
     * Servicio llamar el cliente del servicio que ejecuta el proceso automatico para cartera
     */
    private void verificarDiaVencimientoAportantes() {
        logger.debug("Inicia ejecutarProcesoAutomaticoCartera()");
        VerificarDiaVencimientoAportantes vencimientoAportantes = new VerificarDiaVencimientoAportantes();
        vencimientoAportantes.execute();
        logger.debug("Finaliza ejecutarProcesoAutomaticoCartera()");
    }

}
