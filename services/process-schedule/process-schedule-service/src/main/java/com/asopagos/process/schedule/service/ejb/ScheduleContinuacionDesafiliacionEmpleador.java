package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;
import com.asopagos.novedades.clients.EjecutarDesafiliacionTrabajadoresEmpledorMasivo;
import com.asopagos.novedades.clients.EmpleadoresProcesar;
import com.asopagos.novedades.clients.ObtenerEmpleadoresProcesar; 
import java.util.List;
import com.asopagos.enumeraciones.core.EstadoDesafiliacionMasivaEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.novedades.composite.clients.DesafiliarEmpleadoresAutomatico;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import java.lang.Long;
import java.util.ArrayList;

@Singleton
@Startup
public class ScheduleContinuacionDesafiliacionEmpleador extends ScheduleAbstract{

    private final static ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.CONTINUACION_EXPULSION_MORA_EMPLEADORES;
    
    @Override
    @PostConstruct
    public void init() {
        start();
    }

    @Timeout
    @Override
    public void run(Timer timer) {
        logger.info("Inicio de método ScheduleSat.run(Timer timer)");
        
        try {            
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            List<Object[]> empleadoresaProcesar =  obtenerEmpleadoresProcesar();
            List<Long> idEmpleadores = new ArrayList<>();
            if(empleadoresaProcesar.size() >1 ){
                for(Object[] empleadoraProcesar : empleadoresaProcesar){
                    int intentos = Integer.valueOf(empleadoraProcesar[4].toString())+1;
                    desafiliarTrabajadoresEmpleadorMasivo(empleadoraProcesar[2].toString(),empleadoraProcesar[1].toString());
                    idEmpleadores.add(Long.parseLong(empleadoraProcesar[2].toString()));
                }
                DatosNovedadAutomaticaDTO datosEmpleadorNovedad = new DatosNovedadAutomaticaDTO();
                datosEmpleadorNovedad.setMotivoDesafiliacion(MotivoDesafiliacionEnum.EXPULSION_POR_MOROSIDAD);
                datosEmpleadorNovedad.setIdEmpleadores(idEmpleadores);
                DesafiliarEmpleadoresAutomatico desafiliarEmpleadoresAutomatico = new DesafiliarEmpleadoresAutomatico(datosEmpleadorNovedad);
                desafiliarEmpleadoresAutomatico.execute();
            }
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
            logger.info("Fin de método ScheduleSat.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleSat.run(Timer timer)", e);
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
        }
        
    }

    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }
    private void desafiliarTrabajadoresEmpleadorMasivo(String idEmpleador, String numeroRadicado) {
		logger.debug("Inicio de método ejecutarProcesoAutomaticoExclusion");
		EjecutarDesafiliacionTrabajadoresEmpledorMasivo service = new EjecutarDesafiliacionTrabajadoresEmpledorMasivo(numeroRadicado,Long.valueOf(idEmpleador));
		service.execute();
		logger.debug("Fin de método ejecutarProcesoAutomaticoExclusion");
	}

    private List<Object[]> obtenerEmpleadoresProcesar(){
        ObtenerEmpleadoresProcesar service = new ObtenerEmpleadoresProcesar();
        service.execute();
        return service.getResult();
    }
}