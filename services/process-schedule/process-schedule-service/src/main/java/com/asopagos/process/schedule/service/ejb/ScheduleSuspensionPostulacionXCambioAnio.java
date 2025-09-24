package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.composite.clients.RadicarSolicitudNovedadAutomaticaFovis;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de Suspensión automática de postulación por cambio de año calendario.
 * 
 * proceso 3.2.1
 * 
 * @author Andrés Valbuena <anvalbuena@heinsohn.com.co>
 */
@Singleton
@Startup
public class ScheduleSuspensionPostulacionXCambioAnio extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleSuspensionPostulacionXCambioAnio.class);

    /**
     * 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.SUSPENSION_AUTOMATICA_POSTULACION_X_CAMBIO_ANIO;

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
        logger.debug("Inicio de método ScheduleSuspensionPostulacionXCambioAnio.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            //Se invoca el cliente para radicar la novedad automatica para rechazo o suspencion de postulaciones por campo de año
            SolicitudNovedadFovisDTO solNovedadDTO = new SolicitudNovedadFovisDTO();
            solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.SUSPENSION_CAMBIO_ANIO_CALENDARIO_AUTOMATICA);
            RadicarSolicitudNovedadAutomaticaFovis radicarSolicitudNovedadAutomaticaFovis = new RadicarSolicitudNovedadAutomaticaFovis(
                    solNovedadDTO);
            radicarSolicitudNovedadAutomaticaFovis.execute();

            //Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);

            logger.debug("Fin de método ScheduleSuspensionPostulacionXCambioAnio.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleSuspensionPostulacionXCambioAnio.run(Timer timer)", e);
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
