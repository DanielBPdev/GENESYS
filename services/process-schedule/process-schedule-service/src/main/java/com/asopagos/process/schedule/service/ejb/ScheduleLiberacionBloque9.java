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
import com.asopagos.aportes.composite.clients.LiberarPlanillasBloque9Process;
import com.asopagos.aportes.service.*;
import java.util.List; // import just the List interface
import java.util.ArrayList; // import just the ArrayList class
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.*;
import com.asopagos.process.schedule.api.ScheduleAbstract;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.RecursoNoAutorizadoException;
import com.asopagos.rest.exception.ServicioNoEncontradoException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.filter.AddAuditHeaders;
import com.asopagos.rest.security.filter.AddAuthenticationToken;
import com.asopagos.rest.security.filter.AddHeaderAuthenticationToken;
@Singleton
@Startup
 public class ScheduleLiberacionBloque9 extends ScheduleAbstract{

    private static final ILogger logger = LogManager.getLogger(ScheduleLiberacionBloque9.class);
 	private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.LIBERACION_PLANILLAS_BLOQUE9;


     
    @PostConstruct
    @Override
    public void init() {
        start();
    }

    @Timeout
    @Override
    public void run(Timer timer) { 

        logger.debug("Inicio Bloque 9. proceso controlado: [EJB:ScheduleLiberacionBloque9]");
        System.out.println("Inicio Bloque 9. proceso controlado: [EJB:ScheduleLiberacionBloque9]");
         List<Long> idPlanillas = new ArrayList<>();
        try {
            LiberarPlanillasBloque9Process bloque = new LiberarPlanillasBloque9Process();
            bloque.execute();
            logger.debug("Fin de método Schedule bloque 9");
        } catch (Exception e) {
            logger.debug("Ocurrió un error inesperado en servicio ejecucon bloque 9 proceso.");
            logger.error("Ocurrió un error inesperado en servicio ejecucon bloque 9 proceso.", e);
        }
    
    }

    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }
    
}
