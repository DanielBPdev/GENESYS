package com.asopagos.process.schedule.service.ejb;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;

import com.asopagos.aportes.clients.ActualizarCategoriaAfiliadosAporteFuturo;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;

@Singleton
@Startup
public class ScheduleCambioCategoriaPorAportesFuturos  extends ScheduleAbstract {
	
	/**
     * Proceso actual. 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.CAMBIO_CATEGORIA_POR_APORTES_FUTUROS;
    
    
    /* (non-Javadoc)
     * @see com.asopagos.process.schedule.api.ScheduleAbstract#init()
     */
    @PostConstruct
    @Override
    public void init() {
        start();
    }
    
    /* (non-Javadoc)
     * @see com.asopagos.process.schedule.api.ScheduleAbstract#run(javax.ejb.Timer)
     */
    @Timeout
    @Override
    public void run(Timer timer){
        try {
            logger.debug("Inicio de método ScheduleCambioCategoriaPorAportesFuturos.run(Timer timer)");
            initContextUsuarioSistema();

            ActualizarCategoriaAfiliadosAporteFuturo actualizarCategoria = new ActualizarCategoriaAfiliadosAporteFuturo(LocalDate.now().toString());
            actualizarCategoria.execute();
            logger.debug("Fin de método ScheduleCambioCategoriaPorAportesFuturos.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleCambioCategoriaPorAportesFuturos.run(Timer timer)", e);
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, 
                    getCurrentProcess(),
                    TipoResultadoProcesoEnum.EJECUCION);
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
