package com.asopagos.process.schedule.service.ejb;

import java.util.Calendar;
import java.util.Date;
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
import com.asopagos.subsidiomonetario.clients.EjecutarOrquestadorStagin;
import com.asopagos.subsidiomonetario.clients.GenerarNuevoPeriodo;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */
@Singleton
@Startup
public class ScheduleOrquestacionSubsidioStaging extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleOrquestacionSubsidioStaging.class);

    /**
     * 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.EJECUTAR_ORQUESTADOR_STAGING_SUBSIDIO;

    @Override
    @PostConstruct
    public void init() {
        start();
    }

    @Timeout
    @Override
    public void run(Timer timer) {
        logger.debug("Inicio de método ScheduleOrquestacionSubsidioStaging.run(Timer timer)");
        try {
            logger.info("Iniciando servicio staging subsidio");
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            // Buscar si el periodo actual existe, creandolo de no ser asi.
            GenerarNuevoPeriodo gnp = new GenerarNuevoPeriodo();
            gnp.execute();

            // Llamar al SP de OrquestacionStagin con la fecha actual
            EjecutarOrquestadorStagin eos = new EjecutarOrquestadorStagin(new Date().getTime());
            eos.execute();

            //Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
            logger.debug("Fin de método ScheduleEjecutarLiquidacionSubsidioMonetario.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleOrquestacionSubsidioStaging.run(Timer timer)", e);
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
        }

    }

    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }

}
