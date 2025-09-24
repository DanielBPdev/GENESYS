package com.asopagos.process.schedule.service.ejb;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.afiliaciones.service.*;
import java.util.*;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.RecursoNoAutorizadoException;
import com.asopagos.rest.exception.ServicioNoEncontradoException;
import com.asopagos.solicitudes.clients.DesistirSolicitudesAutomatico;

@Singleton
@Startup
 public class ScheduleDesistirSolicitudAfiliacion extends ScheduleAbstract{

    private static final ILogger logger = LogManager.getLogger(ScheduleDesistirSolicitudAfiliacion.class);
 	private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.DESISTIR_SOLICITUD_AFILIACION;

    @PostConstruct
    @Override
    public void init() {
        start();
    }

    @Timeout
    @Override
    public void run(Timer timer) { 

        logger.debug("Inicio proceso controlado: [EJB:ScheduleDesistirSolicitudAfiliacion]");
        System.out.println("Inicio proceso controlado: [EJB:ScheduleDesistirSolicitudAfiliacion]");

        try {
            logger.info("Esta en el schedule");
            initContextUsuarioCore();
            DesistirSolicitudesAutomatico desistirAfiliacion = new DesistirSolicitudesAutomatico();
            desistirAfiliacion.execute();
        } catch (Exception e) {
           logger.error("Ocurrió un error inesperado en ScheduleDesistirSolicitudAfiliacion.run(Timer timer)", e);
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
        }
    }

    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }
}
