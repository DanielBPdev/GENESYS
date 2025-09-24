package com.asopagos.process.schedule.api;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.ParametrizacionEjecucionProgramadaModeloDTO;
import com.asopagos.entidades.ccf.general.ParametrizacionEjecucionProgramada;
import com.asopagos.entidades.ccf.general.ResultadoEjecucionProgramada;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.FrecuenciaEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.process.schedule.service.constants.NamedQueriesConstants;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.filter.AccessToken;
import com.asopagos.usuarios.clients.GenerarTokenAccesoCore;
import com.asopagos.usuarios.clients.GenerarTokenAccesoSystem;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.util.ContextUtil;
import com.asopagos.util.Interpolator;

/**
 * <b>Descripción:</b> Clase abstracta que implementa la definición del
 * ScheduleExpression, dependiente de la parametrización de la tabla
 * ParametrizacionEjecucionProgramada
 * 
 * Para detalles para establecer esta configuración ir a este sitio.
 * 
 * @see https://docs.oracle.com/javaee/7/tutorial/ejb-basicexamples004.htm
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public abstract class ScheduleAbstract {

    public static final String USUARIO_SISTEMA = "SISTEMA";
    /**
     * Entity manager para la clase validador.
     */
    @PersistenceContext(unitName = "process_schedule_PU")
    protected EntityManager entityManager;

    /**
     * Recurso TimerService para ejecución del proceso automático.
     */
    @Resource
    protected TimerService timerService;

    /**
     * Referencia al logger
     */
    protected ILogger logger = LogManager.getLogger(ScheduleAbstract.class);

    /**
     * Parametrización DTO que se usa para persistir o actualizar la entidad
     */
    private ParametrizacionEjecucionProgramadaModeloDTO paramaterizacionEjecucionProgramadaModelDTO;

    /**
     * expression que contiene el calendario que indica cuando ejecutar el proceso
     */
    private ScheduleExpression expression;

    /**
     * Operación abstracta que crea un ScheduleExpression
     * 
     * @param timerService2
     * 
     * @param iValidable
     *        Interfaz asociada al proceso en ejecución.
     * @return Schedule con la programación del proceso.
     */
    public ScheduleExpression createSchedule(ProcesoAutomaticoEnum proceso) {
        logger.debug("Inicio de método ScheduleAbstract.createSchedule(IValidable iValidable)");
        expression = null;
        try {
            /* Cancela las tareas asociadas anteriormente. */
            this.cancelarTareasProgramadas();
            ParametrizacionEjecucionProgramada parametrizacionEjecucionProgramada = registrarActualizarParametrizacion();

            if (parametrizacionEjecucionProgramada != null) {

                expression = new ScheduleExpression();

                /*
                 * Se verifica el estado de la programación, si es inactiva
                 * retorna null
                 */
                if (EstadoActivoInactivoEnum.INACTIVO.equals(parametrizacionEjecucionProgramada.getEstadoEjecucionProceso())) {
                    return null;
                }

                /* Se obtienen las horas */
                if (parametrizacionEjecucionProgramada.getHoras() != null && !FrecuenciaEjecucionProcesoEnum.INTERVALO
                        .equals(parametrizacionEjecucionProgramada.getFrecuenciaEjecucionProceso())) {
                    // Se asigna la hora establecida.
                    expression.hour(parametrizacionEjecucionProgramada.getHoras());
                }

                /* Se obtienen los minutos */
                if (parametrizacionEjecucionProgramada.getMinutos() != null && !FrecuenciaEjecucionProcesoEnum.INTERVALO
                        .equals(parametrizacionEjecucionProgramada.getFrecuenciaEjecucionProceso())) {
                    expression.minute(parametrizacionEjecucionProgramada.getMinutos());
                }

                /* Se obtienen los segundos (ejecución diaria) */
                if (parametrizacionEjecucionProgramada.getSegundos() != null && !FrecuenciaEjecucionProcesoEnum.INTERVALO
                        .equals(parametrizacionEjecucionProgramada.getFrecuenciaEjecucionProceso())) {
                    expression.second(parametrizacionEjecucionProgramada.getSegundos());
                }

                /* Se obtienen los segundos (ejecución por intervalo) */
                if (parametrizacionEjecucionProgramada.getSegundos() != null && FrecuenciaEjecucionProcesoEnum.INTERVALO
                        .equals(parametrizacionEjecucionProgramada.getFrecuenciaEjecucionProceso())) {
                    expression.second("*" + parametrizacionEjecucionProgramada.getSegundos()+"000");
                }

                /* se verifica si llega fecha Inicio del proceso. */
                if (parametrizacionEjecucionProgramada.getFechaInicio() != null && !FrecuenciaEjecucionProcesoEnum.INTERVALO
                        .equals(parametrizacionEjecucionProgramada.getFrecuenciaEjecucionProceso())) {
                    expression.start(parametrizacionEjecucionProgramada.getFechaInicio());
                }
                /* se verifica si llega fecha Fin del proceso. */
                if (parametrizacionEjecucionProgramada.getFechaFin() != null && !FrecuenciaEjecucionProcesoEnum.INTERVALO
                        .equals(parametrizacionEjecucionProgramada.getFrecuenciaEjecucionProceso())) {
                    expression.end(parametrizacionEjecucionProgramada.getFechaFin());
                }

                /* Si la ejecución es SEMANAL */
                if (parametrizacionEjecucionProgramada.getFrecuenciaEjecucionProceso().equals(FrecuenciaEjecucionProcesoEnum.SEMANAL)) {
                    /* Se asigna el día de la semana. */
                    expression.dayOfWeek(parametrizacionEjecucionProgramada.getDiaSemana());
                    /* Si la ejecución es MENSUAL */
                }
                else if (parametrizacionEjecucionProgramada.getFrecuenciaEjecucionProceso()
                        .equals(FrecuenciaEjecucionProcesoEnum.MENSUAL)) {
                    /* Se asigna el día del mes. */
                    expression.dayOfMonth(parametrizacionEjecucionProgramada.getDiaMes());
                    /* Si la ejecución es ANUAL */
                }
                else if (parametrizacionEjecucionProgramada.getFrecuenciaEjecucionProceso().equals(FrecuenciaEjecucionProcesoEnum.ANUAL)) {
                    /*
                     * Se asigna el mes en el que se debe ejecutar anualmente.
                     */
                    expression.month(parametrizacionEjecucionProgramada.getMes());
                    /* Se asigna el día del mes */
                    expression.dayOfMonth(parametrizacionEjecucionProgramada.getDiaMes());
                }
            }
            logger.debug("Fin de método ScheduleAbstract.createSchedule(IValidable iValidable)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado ScheduleAbstract.createSchedule(IValidable iValidable)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return expression;
    }

    /**
     * Limpia las tareas creadas en otras ejecuciones.
     * 
     * @param timerName
     */
    public void cancelarTareasProgramadas() {
        logger.debug("Inicio de método ScheduleAbstract.cancelarTareasProgramadas(TimerService timerService)");
        try {
            /*
             * Obtiene los Timer asociados al Proceso en ejecución y los
             * cancela.
             */
            if (timerService.getTimers() != null && !timerService.getTimers().isEmpty()) {
                for (Object obj : timerService.getTimers()) {
                    Timer timer = (Timer) obj;
                    timer.cancel();
                }
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
        }
        logger.debug("Fin de método ScheduleAbstract.cancelarTareasProgramadas(TimerService timerService)");
    }

    /**
     * Genera log resultado de la invocación del proceso, exitosa o fallida.
     * 
     * 
     * @param Resultado
     *        ejecución del proceso
     * @param Proceso
     *        ejecutado.
     */
    public void crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum resultadoEjecucion, ProcesoAutomaticoEnum proceso,
            TipoResultadoProcesoEnum tipoResultado) {
        logger.debug(
                "Inicio de método ScheduleAbstract.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum resultadoEjecucion, IValidable proceso)");
        try {
            /* Se almacena el registro de resultado de Ejecución del proceso */
            ResultadoEjecucionProgramada resultadoEjecucionProgramada = new ResultadoEjecucionProgramada();
            resultadoEjecucionProgramada.setFechaEjecucion(new Date());
            resultadoEjecucionProgramada.setProceso(proceso.name());
            resultadoEjecucionProgramada.setResultadoEjecucion(resultadoEjecucion);
            resultadoEjecucionProgramada.setTipoResultadoProceso(tipoResultado);
            entityManager.persist(resultadoEjecucionProgramada);
            logger.info("resultadoEjecucionProgramada 1" +resultadoEjecucionProgramada.getIdResultadoEjecucion());
            logger.debug(
                    "Fin de método ScheduleAbstract.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum resultadoEjecucion, IValidable proceso)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método encargado de refrescar el timer service de un proceso automatico,
     * con la nueva parametrización.
     * 
     * @param procesoAutomatico
     *        proceso automático.
     */
    public void actualizarFrecuencia(ProcesoAutomaticoEnum procesoAutomatico, TipoResultadoProcesoEnum tipoResultado) {
        try {
            logger.debug("Inicio de método ScheduleAbstract.refrescarTimerService(ProcesoAutomaticoEnum procesoAutomatico)");
            cancelarTareasProgramadas();
            ScheduleExpression scheduleExpression = this.createSchedule(procesoAutomatico);
            timerService.createCalendarTimer(scheduleExpression);
            /* Crea Log de programación de evento exitosa */
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, procesoAutomatico, tipoResultado);
            logger.debug("Fin de método ScheduleAbstract.refrescarTimerService(ProcesoAutomaticoEnum procesoAutomatico)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado ScheduleAbstract.refrescarTimerService(ProcesoAutomaticoEnum procesoAutomatico)", e);
            /* Crea Log de programación de evento fallida */
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, procesoAutomatico, tipoResultado);
        }
    }

    /**
     * Metodo que crea la expresion y crea el timer para ejecutar el proceso
     */
    protected void start() {
        try {
            logger.debug(Interpolator.interpolate("Inicio método {0} start", getCurrentProcess()));
            // Token offline 
            // Se crea el schedule
            ScheduleExpression scheduleExpression = this.createSchedule(getCurrentProcess());
            if (scheduleExpression != null) {
                TimerConfig timerConfig = new TimerConfig();
                timerConfig.setInfo(getCurrentProcess());

                Boolean esIntervalo = scheduleExpression.getSecond() != null && scheduleExpression.getSecond().contains("*") ? Boolean.TRUE
                        : Boolean.FALSE;

                if (!esIntervalo) {
                    timerService.createCalendarTimer(scheduleExpression, timerConfig);
                }
                else {
                    Long intervalo = null;
                    String intervaloString = scheduleExpression.getSecond();
                    //                    String intervaloString = (String) CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_INACTIVAR_EMPLEADOR);
                    intervaloString = intervaloString != null ? intervaloString.replace("*", "") : "60000";

                    intervalo = Long.parseLong(intervaloString);

                    timerService.createIntervalTimer(new Date(), intervalo, timerConfig);
                }

                /* Crea Log de programación de evento exitosa */
                this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.EXITOSO, getCurrentProcess(),
                        TipoResultadoProcesoEnum.DESPLIEGUE);
            }
            logger.debug(Interpolator.interpolate("Fin método {0} start", getCurrentProcess()));
        } catch (Exception e) {
            logger.error(Interpolator.interpolate("Ocurrió un error inesperado {0} start", getCurrentProcess()), e);
            /* Crea Log de programación de evento fallida */
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, getCurrentProcess(), TipoResultadoProcesoEnum.DESPLIEGUE);
        }
    }

    /**
     * Metodo que busca la parametrización en la base de datos, si la
     * implementacion tiene un ParametrizacionEjecucionProgramadaModeloDTO debe
     * hacer el persist o merge correspondiente
     * 
     * @param paramaterizacionEjecucionProgramadaModelDTO
     * @return ParametrizacionEjecucionProgramada retorna la parametrizacion que
     *         corresponde al proceso de la implementación, null si no lo
     *         encuentra
     */
    public ParametrizacionEjecucionProgramada registrarActualizarParametrizacion() {
        ParametrizacionEjecucionProgramadaModeloDTO parametrizacionDTO = getParamaterizacionEjecucionProgramadaModelDTO();
        ParametrizacionEjecucionProgramada parametrizacionEjecucionProgramada = null;
        boolean persist = false;
        logger.debug(Interpolator.interpolate("Inicio método {0} ParametrizacionEjecucionProgramada", getCurrentProcess()));
        try {
            /*
             * Consulta la parametrización actual para la ejecución Automática.
             */
            parametrizacionEjecucionProgramada = (ParametrizacionEjecucionProgramada) entityManager
                    .createNamedQuery(NamedQueriesConstants.PROCESSSCHEDULE_PARAMEJECUCIONPROGRAMADA_TIPOTRANSACCION)
                    .setParameter("proceso", getCurrentProcess()).getSingleResult();
        } catch (NoResultException e) {
            if (parametrizacionDTO != null) {
                // Se debe persistir la Parametrizacion
                parametrizacionDTO.setEstadoEjecucionProceso(EstadoActivoInactivoEnum.ACTIVO);
                parametrizacionEjecucionProgramada = parametrizacionDTO.convertToEntity();
                entityManager.persist(parametrizacionEjecucionProgramada);
                persist = true;
            }
        } catch (Exception e) {
            logger.error(
                    Interpolator.interpolate("Ocurrió un error inesperado {0} ParametrizacionEjecucionProgramada", getCurrentProcess()), e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        /*
         * se debe hacer un merge a la parametrizacion
         */
        if (parametrizacionDTO != null && !persist) {
            parametrizacionDTO.setIdParametrizacion(parametrizacionEjecucionProgramada.getIdParametrizacion());
            parametrizacionDTO.setEstadoEjecucionProceso(EstadoActivoInactivoEnum.ACTIVO);
            parametrizacionEjecucionProgramada = parametrizacionDTO.convertToEntity();
            entityManager.merge(parametrizacionEjecucionProgramada);
        }
        logger.debug(Interpolator.interpolate("Fin método {0} ParametrizacionEjecucionProgramada", getCurrentProcess()));
        return parametrizacionEjecucionProgramada;
    }

    /**
     * Cambia el estado de la parametrización segun el parametro de entrada
     * 
     * @param estadoProceso
     *        Nuevo estado de la parametrizacón
     */
    public void cambiarEstadoProceso(EstadoActivoInactivoEnum estadoProceso) {
        try {
            ParametrizacionEjecucionProgramada paramEjecucion = entityManager
                    .createNamedQuery(NamedQueriesConstants.PROCESSSCHEDULE_PARAMEJECUCIONPROGRAMADA_TIPOTRANSACCION,
                            ParametrizacionEjecucionProgramada.class)
                    .setParameter("proceso", getCurrentProcess()).getSingleResult();
            if (paramEjecucion != null) {
                paramEjecucion.setEstadoEjecucionProceso(estadoProceso);
                entityManager.merge(paramEjecucion);
            }
        } catch (NoResultException e) {
            logger.debug(Interpolator.interpolate("No se encontro implemtacion de este proceso {0}", getCurrentProcess()));
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Cambia el estado de la parametrización segun el parametro de entrada
     *
     * @param resultadoEjecucionProgramada
     *        Registro de resultado ejecucion programada
     * @param resultado
     *        Resultado de la ejecucion
     * @param procesoAutomatico
     *        Proceso de la ejecucion
     */
    public List<ResultadoEjecucionProgramada> consultarParametrizacionEjecucionProgramada(List<ResultadoEjecucionProgramada> resultadoEjecucionProgramada, ResultadoEjecucionProcesoEnum resultado, ProcesoAutomaticoEnum procesoAutomatico, TipoResultadoProcesoEnum tipoResultadoProcesoEnum) {
        try {
            if(resultadoEjecucionProgramada == null) {
                logger.info("viene null");
                resultadoEjecucionProgramada = entityManager
                        .createNamedQuery(NamedQueriesConstants.PROCESSSCHEDULE_RESULTADOEJECUCION_PROCESO,
                                ResultadoEjecucionProgramada.class)
                        .setParameter("proceso", procesoAutomatico.name())
                        .setParameter("resultadoProceso", tipoResultadoProcesoEnum).getResultList();
            }else{
                logger.info("viene a actualizar");
                for(ResultadoEjecucionProgramada ejecucionProgramada : resultadoEjecucionProgramada) {
                    ejecucionProgramada.setResultadoEjecucion(resultado);
                    entityManager.merge(ejecucionProgramada);
                }
            }

        } catch (NoResultException e) {
            logger.debug(Interpolator.interpolate("No se encontro implemtacion de este proceso {0}", getCurrentProcess()));
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        return (List<ResultadoEjecucionProgramada>) resultadoEjecucionProgramada;
    }

    /**
     * 
     */
    public abstract void init();

    /**
     * Ejecuta el proceso automático asociado.
     * 
     * @param timer
     */
    public abstract void run(Timer timer);

    /**
     * 
     * @return
     */
    public abstract ProcesoAutomaticoEnum getCurrentProcess();

    /**
     * @return the paramaterizacionEjecucionProgramadaModelDTO
     */
    public ParametrizacionEjecucionProgramadaModeloDTO getParamaterizacionEjecucionProgramadaModelDTO() {
        return paramaterizacionEjecucionProgramadaModelDTO;
    }

    /**
     * @param paramaterizacionEjecucionProgramadaModelDTO
     *        the paramaterizacionEjecucionProgramadaModelDTO to set
     */
    public void setParamaterizacionEjecucionProgramadaModelDTO(
            ParametrizacionEjecucionProgramadaModeloDTO paramaterizacionEjecucionProgramadaModelDTO) {
        this.paramaterizacionEjecucionProgramadaModelDTO = paramaterizacionEjecucionProgramadaModelDTO;
    }

    /**
     * @return the scheduleExpression
     */
    public ScheduleExpression getScheduleExpression() {
        return expression;
    }

    /**
     * @param scheduleExpression
     *        the scheduleExpression to set
     */
    public void setScheduleExpression(ScheduleExpression scheduleExpression) {
        this.expression = scheduleExpression;
    }

    /**
     * Provee los datos del usuario sistema al contexto para el registro de la tarea
     */
    public void initContextUsuarioSistema() {
        // Se genera el token de conexion
        GenerarTokenAccesoSystem tokenAcceso = new GenerarTokenAccesoSystem();
        tokenAcceso.execute();
        TokenDTO token = tokenAcceso.getResult();
        // Se agrega al contexto el usuario y el token
        ContextUtil.addValueContext(AccessToken.class, new AccessToken(token.getToken()));
    }
    
    /**
     * Provee los datos del usuario sistema al contexto para el registro de la tarea
     */
    public void initContextUsuarioCore() {
        // Se genera el token de conexion
        GenerarTokenAccesoCore tokenAcceso = new GenerarTokenAccesoCore();
        tokenAcceso.execute();
        TokenDTO token = tokenAcceso.getResult();
        // Se agrega al contexto el usuario y el token
        ContextUtil.addValueContext(AccessToken.class, new AccessToken(token.getToken()));
    }

}
