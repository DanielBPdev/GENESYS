package com.asopagos.process.schedule.service.ejb;

import com.asopagos.cartera.composite.clients.EjecutarProcesoAutomaticoGestionCobro;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
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
public class ScheduleEnvioComunicadoH2C2ToF1C6 extends ScheduleAbstract {

    /**
     * Proceso actual
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.ENVIO_COMUNICADO_H2C2_F1C6;

    @PostConstruct
    @Override
    public void init() {
        start();
    }

    @Timeout
    @Override
    public void run(Timer timer) {
        try {
            logger.info("Inicio de método ScheduleDesafiliacionC6.run(Timer timer)");
            // Se indica el usuario al contexto
            initContextUsuarioCore();
            ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.F1_C6);
            ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum.H2_C6);
            logger.info("Fin de método ScheduleDesafiliacionC6.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleFirmezaTitulo.run(Timer timer)", e);
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
        }
    }

    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }

    private void ejecutarProcesoAutomaticoGestionCobro(TipoAccionCobroEnum accionCobro) {
        logger.debug("Inicio de método ejecutarProcesoAutomaticoGestionCobro");
        EjecutarProcesoAutomaticoGestionCobro service = new EjecutarProcesoAutomaticoGestionCobro(accionCobro);
        service.execute();
        logger.debug("Fin de método ejecutarProcesoAutomaticoGestionCobro");
    }

}
