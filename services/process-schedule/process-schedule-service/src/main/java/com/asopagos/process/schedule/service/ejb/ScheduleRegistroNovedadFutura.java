package com.asopagos.process.schedule.service.ejb;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.aportes.clients.ValidarProcesamientoNovedadFutura;
import com.asopagos.aportes.composite.clients.ProcesarNovedadesFuturasProcessSchedule;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadFutura;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de novedades futuras
 * reportadas desde PILA y/o APORTES MANUALES.
 * 
 * @author José Arley Correa<jocorrea@heinsohn.com.co>
 */
@Singleton
@Startup
public class ScheduleRegistroNovedadFutura extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleRegistroNovedadFutura.class);

    /**
     * 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.REGISTRO_NOVEDAD_FUTURA;

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
        logger.debug("Inicio de método ScheduleRegistroNovedadFutura.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            // se toma la fecha del sistema para el proceso
            Date fechaActual = new Date();
            //Date fechaActual = new Date(1606798800000L);//Test mes diciembre
                        
            // Se ejecuta el proceso de validación de aplicación de novedades futuras
            ValidarProcesamientoNovedadFutura validarProcesamientoNovedadFutura = new ValidarProcesamientoNovedadFutura(
                    fechaActual.getTime());
            validarProcesamientoNovedadFutura.execute();
            
            
            //Se ejecutan las novedades que se validaron anteriormente
            ProcesarNovedadesFuturasProcessSchedule procesarNovedadesFuturas = new ProcesarNovedadesFuturasProcessSchedule();
            procesarNovedadesFuturas.execute();

            //Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);

            logger.debug("Fin de método ScheduleRegistroNovedadFutura.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleRegistroNovedadFutura.run(Timer timer)", e);
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
}
