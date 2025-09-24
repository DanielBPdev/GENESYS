package com.asopagos.process.schedule.service.ejb;

import com.asopagos.cartera.composite.clients.EjecutarProcesoAutomaticoActaLiquidacion;
import com.asopagos.cartera.composite.clients.EjecutarProcesoAutomaticoFirmezaTitulo;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;

import javax.annotation.PostConstruct;
import javax.ejb.Timeout;
import javax.ejb.Timer;

import javax.ejb.Singleton;
import javax.ejb.Startup;
@Singleton
@Startup
public class ScheduleActaLiquidacionAportes extends ScheduleAbstract {

    /**
     * Proceso actual
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.ACTA_LIQUIDACION_APORTES;


    @PostConstruct
    @Override
    public void init() {
        start();
    }

    @Timeout
    @Override
    public void run(Timer timer) {
        try {
            logger.info("Inicio de método ScheduleActaLiquidacionAportes.run(Timer timer)");
            // Se indica el usuario al contexto
            initContextUsuarioCore();
            ejecutarProcesoAutomaticoActaLiquidacion();
            logger.info("Fin de método ScheduleActaLiquidacionAportes.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleFirmezaTitulo.run(Timer timer)", e);
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
        }
    }

    /**
     * Método encargado invocar el servicio de guardarNotificacion
     *
     */
    private void ejecutarProcesoAutomaticoActaLiquidacion() {
        logger.info("Inicio de método ejecutarProcesoAutomaticoActaLiquidacion");
        EjecutarProcesoAutomaticoActaLiquidacion service = new EjecutarProcesoAutomaticoActaLiquidacion();
        service.execute();
        logger.info("Fin de método ejecutarProcesoAutomaticoActaLiquidacion");
    }
    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }
}
