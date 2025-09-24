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

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de
 * Cambiar automáticamente la categoría “Z” para beneficiarios que cumplan “X”
 * años de edad
 * 
 * <b>Historia de Usuario:</b> HU 496
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
@Singleton
@Startup
public class ScheduleCambiarCategoriaBeneficiarioXEdad extends ScheduleAbstract {

    /**
     * 
     */
    private final static ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.CAMBIAR_AUTOMATICAMENTE_CATEGORIA_Z_BENEFICIARIOS_CUMPLEN_X_EDAD;

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
        logger.debug("Inicio de método ScheduleRetirarBeneficiarioMayorEdadSinCedula.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            // Se invoca el cliente para radicar el retiro de beneficiarios de
            // 18 años y mayores de 18 años que no tengan cédula deciudadanía
            SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();
            solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.CAMBIAR_AUTOMATICAMENTE_CATEGORIA_Z_BENEFICIARIOS_CUMPLEN_X_EDAD);
            RadicarSolicitudNovedadMasiva radicarSolicitudNovedadMasiva = new RadicarSolicitudNovedadMasiva(solNovedadDTO);
            radicarSolicitudNovedadMasiva.execute();

            // Guarda Log exitoso del proceso automático.
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);

            logger.debug("Fin de método ScheduleRetirarBeneficiarioMayorEdadSinCedula.run(Timer timer)");
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
