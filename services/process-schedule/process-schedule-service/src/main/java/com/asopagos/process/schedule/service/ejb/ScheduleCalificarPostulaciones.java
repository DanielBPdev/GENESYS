package com.asopagos.process.schedule.service.ejb;

import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import com.asopagos.entidades.ccf.general.ResultadoEjecucionProgramada;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoCicloAsignacionEnum;
import com.asopagos.fovis.clients.ConsultarCiclosAsignacion;
import com.asopagos.fovis.clients.ConsultarCiclosAsignacionPorEstado;
import com.asopagos.fovis.clients.GuardarActualizarCicloAsignacion;
import com.asopagos.fovis.composite.clients.CalcularGuardarCalificacionHogaresCiclo;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.composite.clients.RadicarSolicitudNovedadAutomaticaFovis;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.process.schedule.api.ScheduleAbstract;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático de Suspensión automática de postulación por cambio de año calendario.
 *
 * proceso 3.2.1
 *
 * @author Andrés Valbuena <anvalbuena@heinsohn.com.co>
 */
@Singleton
@Startup
public class ScheduleCalificarPostulaciones extends ScheduleAbstract {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ScheduleCalificarPostulaciones.class);

    /**
     *
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.EJECUCION_PRE_CALIFICACION_PARA_ASIGNACION_FOVIS;

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
        logger.info("Inicio de método ScheduleCalcularPuntajePostulaciones.run(Timer timer)");
        try {
            // Se indica el usuario al contexto
            initContextUsuarioSistema();
            this.crearLogEjecucionProceso(null, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
            List<ResultadoEjecucionProgramada> resultadoEjecucionProgramada = consultarParametrizacionEjecucionProgramada(null, null, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
            logger.info("resultadoEjecucionProgramada 2 " + resultadoEjecucionProgramada.get(0).getIdResultadoEjecucion());
            Date fecha = new Date();

            logger.info("fecha Casteada" + fecha);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            logger.info("fecha " + calendar.getTime()); // Resta un día
            Date fechaMenosUnDia = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = sdf.format(fechaMenosUnDia);
            logger.info("fechaFormateada  "+ fechaFormateada);

            List<CicloAsignacionModeloDTO> ciclos = consultarCiclosVigentes(EstadoCicloAsignacionEnum.CERRADO, fechaFormateada);
            logger.info("ciclos " + ciclos.size());
            for(CicloAsignacionModeloDTO ciclo : ciclos){
                calcularCalificacionHogaresCiclo(ciclo.getIdCicloAsignacion(), null);
                ciclo.setEjecucionProgramada(resultadoEjecucionProgramada.get(0).getIdResultadoEjecucion());
                logger.info("ciclo ejecutada " + ciclo.getEjecucionProgramada());
                logger.info("ciclo nombre " + ciclo.getNombre());
                guardarActualizarCicloAsignacion(ciclo);
                logger.info("ciclo 2.0.0" + ciclo.getEjecucionProgramada());
            }
            resultadoEjecucionProgramada = consultarParametrizacionEjecucionProgramada(resultadoEjecucionProgramada, ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(), TipoResultadoProcesoEnum.EJECUCION);
            logger.info("resultadoEjecucionProgramada 3 " + resultadoEjecucionProgramada.get(0).getIdResultadoEjecucion());
            logger.info("Fin de método ScheduleCalcularPuntajePostulaciones.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleCalcularPuntajePostulaciones.run(Timer timer)", e);
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

    private List<CicloAsignacionModeloDTO> consultarCiclosVigentes(EstadoCicloAsignacionEnum estadoCicloAsignacion, String fechaAsig) {
        logger.info("Inicia el método consultarCiclosVigentes");
        logger.info("estadoCicloAsignacion" + estadoCicloAsignacion + "fecha "+ fechaAsig);
        ConsultarCiclosAsignacion service = new ConsultarCiclosAsignacion(estadoCicloAsignacion, fechaAsig);
        service.execute();
        logger.info("Finaliza el método consultarCiclosVigentes");
        return service.getResult();
    }

    private void calcularCalificacionHogaresCiclo(Long idCicloAsignacion, BigDecimal valorDisponible) {
        logger.info("Inicia el método calcularCalificacionHogaresCiclo");
        CalcularGuardarCalificacionHogaresCiclo service = new CalcularGuardarCalificacionHogaresCiclo(valorDisponible, idCicloAsignacion);
        service.execute();
        logger.info("Finaliza el método calcularCalificacionHogaresCiclo");

    }

    private CicloAsignacionModeloDTO guardarActualizarCicloAsignacion(CicloAsignacionModeloDTO cicloAsignacionModeloDTO) {
        logger.info("Inicia el método guardarActualizarCicloAsignacion");
        logger.info("cicloAsignacionModeloDTO " + cicloAsignacionModeloDTO.getEjecucionProgramada());
        GuardarActualizarCicloAsignacion service = new GuardarActualizarCicloAsignacion(cicloAsignacionModeloDTO);
        service.execute();
        logger.info("Finaliza el método guardarActualizarCicloAsignacion");
        return service.getResult();
    }


}
