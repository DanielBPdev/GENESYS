package com.asopagos.process.schedule.service.ejb;

import com.asopagos.cartera.composite.clients.EjecutarProcesoAutomaticoPrescribirCartera;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;


@Singleton
@Startup
public class SchedulePrescribirPeriodosAportesEnCartera_X_Tiempo extends ScheduleAbstract {
    /**
     * Proceso actual
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.PRESCRIBIR_PERIODOS_APORTES_MORA_EN_CARTERA_X_TIEMPO;


    @PostConstruct
    @Override
    public void init() {
        start();
    }

    @Timeout
    @Override
    public void run(Timer timer) {
        try {
            logger.info("Inicio de método SchedulePrescribirPeriodosAportesEnCartera_X_Tiempo.run(Timer timer)");
            // Se indica el usuario al contexto
            initContextUsuarioCore();
            ejecutarProcesoAutomaticoPrescribirCartera();
            logger.info("Fin de método SchedulePrescribirPeriodosAportesEnCartera_X_Tiempo.run(Timer timer)");
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en SchedulePrescribirCartera.run(Timer timer)", e);
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
        }
    }


    /**
     * Método encargado invocar el servicio de prescribir cartera
     */
    private void ejecutarProcesoAutomaticoPrescribirCartera() {
        logger.info("Inicio de método ejecutarProcesoAutomaticoPrescribirCartera");
        EjecutarProcesoAutomaticoPrescribirCartera service = new EjecutarProcesoAutomaticoPrescribirCartera();
        service.execute();
        logger.info("Fin de método ejecutarProcesoAutomaticoPrescribirCartera");
    }

    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }
}
