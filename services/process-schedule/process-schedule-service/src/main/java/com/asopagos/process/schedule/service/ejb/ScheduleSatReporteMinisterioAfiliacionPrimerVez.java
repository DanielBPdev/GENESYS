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
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadMasiva;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.process.schedule.api.ScheduleAbstract;
import com.asopagos.sat.clients.ConsultarSolicitudAfiliacionaSATPrimeravez;




@Singleton
@Startup
public class ScheduleSatReporteMinisterioAfiliacionPrimerVez extends ScheduleAbstract {

    private final static ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.EJECUCION_SAT_REPORTE_MINISTERIO_AFILIACION_PRIMER_VEZ;

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
        logger.info("Inicio de método consultarSolicitudAfiliacionaSATPrimeravez.run(Timer timer)");
        
        try {            
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
    
            consultarSolicitudAfiliacionaSATPrimeravez(); //fase2

            // Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
            logger.info("Fin de método consultarSolicitudAfiliacionaSATPrimeravez.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarSolicitudAfiliacionaSATPrimeravez.run(Timer timer)", e);
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
    private void consultarSolicitudAfiliacionaSATPrimeravez() {
		logger.info("Inicio de método ejecutarProcesoAutomaticoExclusion");
		ConsultarSolicitudAfiliacionaSATPrimeravez service = new ConsultarSolicitudAfiliacionaSATPrimeravez();
		service.execute();
		logger.info("Fin de método ejecutarProcesoAutomaticoExclusion");
	}
    
}