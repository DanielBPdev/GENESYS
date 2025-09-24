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
import com.asopagos.afiliados.clients.CorreccionCategoriaAfiliadoBeneficiario;


@Singleton
@Startup
public class ScheduleCorreccionCategoriasAf_Ben extends ScheduleAbstract {

    private final static ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.CORRECCION_CATEGORIAS_AFILIADOS_BENEFICIARIOS;

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
        logger.info("Inicio de método correccion Categorias.run(Timer timer)");
        
        try {            
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
    
            correccionCategoriasAfiliadosybeneficiarios(); //fase2

            // Guarda Log exitoso del proceso automático.
            logger.info("Fin de método correccion Categorias.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado encorreccion Categorias(Timer timer)", e);
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
    private void correccionCategoriasAfiliadosybeneficiarios() {
		logger.debug("Inicio de método ejecutarProcesoAutomaticoExclusion");
		CorreccionCategoriaAfiliadoBeneficiario service = new CorreccionCategoriaAfiliadoBeneficiario();
		service.execute();
		logger.debug("Fin de método ejecutarProcesoAutomaticoExclusion");
	}
    
}