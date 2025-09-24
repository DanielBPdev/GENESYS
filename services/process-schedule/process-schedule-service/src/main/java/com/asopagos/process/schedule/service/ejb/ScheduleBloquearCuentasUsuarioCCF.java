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
import com.asopagos.process.schedule.api.ScheduleAbstract;
import com.asopagos.usuarios.clients.BloquearCuentasUsuarioCCF;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de Bloquear Cuentas de Usuarios Caja de Compensacion Familiar
 * 
 * <b>Historia de Usuario:</b> HU-TRA
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@Singleton
@Startup
public class ScheduleBloquearCuentasUsuarioCCF extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleBloquearCuentasUsuarioCCF.class);

    /**
     * 
     */
    private final static ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.BLOQUEAR_CUENTAS_USUARIO_CCF;

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
        logger.debug("Inicio de método ScheduleBloquearCuentasUsuarioCCF.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            BloquearCuentasUsuarioCCF bloquear = new BloquearCuentasUsuarioCCF();
            bloquear.execute();
            //Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
            logger.debug("Fin de método ScheduleBloquearCuentasUsuarioCCF.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleBloquearCuentasUsuarioCCF.run(Timer timer)", e);
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
