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
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadMasiva;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de Inactivación de Beneficios Ley 1429 de 2010.
 * Novedad 29.
 * 
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Singleton
@Startup
public class ScheduleInactivacionBeneficio590 extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleInactivacionBeneficio590.class);

    /**
     * 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.INACTIVAR_AUTOMATICAMENTE_BENEFICIOS_LEY_590_2000;

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
        logger.debug("Inicio de método ScheduleInactivacionBeneficio590.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            //Se invoca el cliente para radicar la inactivación de Beneficios Ley 590 de 2000
            SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();
            solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.INACTIVAR_AUTOMATICAMENTE_BENEFICIOS_LEY_590_2000);
            RadicarSolicitudNovedadMasiva radicarSolicitudNovedadMasiva = new RadicarSolicitudNovedadMasiva(solNovedadDTO);
            radicarSolicitudNovedadMasiva.execute();

            //Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);

            logger.debug("Fin de método ScheduleInactivacionBeneficio590.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleInactivacionBeneficio590.run(Timer timer)", e);
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
