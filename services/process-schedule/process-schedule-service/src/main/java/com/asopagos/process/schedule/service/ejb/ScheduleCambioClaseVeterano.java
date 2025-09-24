package com.asopagos.process.schedule.service.ejb;

import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadMasiva;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.entidaddescuento.composite.clients.CargarAutomaticamenteArchivosEntidadDescuentoComposite;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.process.schedule.api.ScheduleAbstract;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

@Singleton
@Startup
public class ScheduleCambioClaseVeterano extends ScheduleAbstract{

    private final static ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_PRESENCIAL;

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
     * @see
     * com.asopagos.process.schedule.api.ScheduleAbstract#run(javax.ejb.Timer)
     */
    @Timeout
    @Override
    public void run(Timer timer) {
        logger.info("Inicio de método ScheduleCambioClaseVeterano.run(Timer timer)");
        
        try {            
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
    
            // Se invoca el servicio de novedades masivas para
            // El cambio de categoria a beneficiarios que cumplan las condiciones
    
            SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();
            solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_PRESENCIAL);
            RadicarSolicitudNovedadMasiva radicarSolicitudNovedadMasiva = new RadicarSolicitudNovedadMasiva(solNovedadDTO);
            radicarSolicitudNovedadMasiva.execute();
    
            // Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
            logger.info("Fin de método ScheduleCambioClaseVeterano.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleRetirarBeneficiarioMayorEdadSinCedula.run(Timer timer)", e);
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
        }
        
    }


    /**
     * @return the currentprocess
     */
    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }
    
}
