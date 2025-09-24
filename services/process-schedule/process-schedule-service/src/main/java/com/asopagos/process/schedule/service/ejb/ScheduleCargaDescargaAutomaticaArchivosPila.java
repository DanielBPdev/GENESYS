package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.clients.EjecutarProcesoAutomatico;
import com.asopagos.pila.composite.clients.CargarArchivosPilaFtp;
import com.asopagos.pila.dto.OperadorInformacionDTO;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripcion:</b> Clase que permite realizar el proceso batch de recorrer los operadores de
 * informacion para realizar la descarga automatica de archivos PILA<br/>
 * <b>Módulo:</b> Asopagos - HU - TRANSVERSAL<br/>
 * 
 * <b>Historia de Usuario:</b> PT-INGE-035-211-387
 * <b>proceso</b> 1.2.1
 * 
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 */
@Singleton
@Startup
public class ScheduleCargaDescargaAutomaticaArchivosPila extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleCargaDescargaAutomaticaArchivosPila.class);

    /**
     * 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.PILA_CARGA_DESCARGA_AUTOMATICA_ARCHIVOS_OI;

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
        logger.debug("Inicio de método ScheduleCargaDescargaAutomaticaArchivosPila.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            //Ejecuta el proceso automatico para todos los Operadores de informacion que se encuentren asociados a la CCF
            OperadorInformacionDTO operadorInformacionDTO = new OperadorInformacionDTO();
            EjecutarProcesoAutomatico ejecutarProcesoAutomaticoService = new EjecutarProcesoAutomatico(operadorInformacionDTO);
            //CargarArchivosPilaFtp ejecutarProcesoAutomaticoService = new CargarArchivosPilaFtp(null);
            ejecutarProcesoAutomaticoService.execute();

            //Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);

            logger.debug("Fin de método ScheduleCargaDescargaAutomaticaArchivosPila.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleCargaDescargaAutomaticaArchivosPila.run(Timer timer)", e);
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