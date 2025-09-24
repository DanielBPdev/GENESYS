package com.asopagos.process.schedule.service.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.ParametrizacionEjecucionProgramadaModeloDTO;
import com.asopagos.entidades.ccf.general.ParametrizacionEjecucionProgramada;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.process.schedule.api.ScheduleAbstract;
import com.asopagos.process.schedule.service.constants.NamedQueriesConstants;
import com.asopagos.processschedule.service.ProcessScheduleService;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.aportes.composite.clients.ProcesarAportesNovedadesByIdPlanilla;
import com.asopagos.util.CalendarUtils;
import com.asopagos.usuarios.clients.ValidarCredencialesUsuario;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.entidades.auditoria.ThreadContext;



/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con el process Schedule<br/>
 *
 * @author Andres Valbuena <anvalbuena@heinsohn.com.co>
 */
@Stateless
public class ProcessScheduleBusiness implements ProcessScheduleService {

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "process_schedule_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ProcessScheduleBusiness.class);

    /**
     * Referencia al Context
     */
    private static InitialContext context;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.processschedule.service.ProcessScheduleService#consultarProgramacion(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ParametrizacionEjecucionProgramadaModeloDTO> consultarProgramacion(List<ProcesoAutomaticoEnum> procesos) {
        logger.debug("Se inicia el servicio de consultarProgramacion()");
        try {
            List<ParametrizacionEjecucionProgramada> programacionesNovedades = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PROGRAMACION_POR_PROCESOS).setParameter("procesos", procesos)
                    .getResultList();
            List<ParametrizacionEjecucionProgramadaModeloDTO> resp = new ArrayList<>();
            if (programacionesNovedades != null && !programacionesNovedades.isEmpty()) {
                for (ParametrizacionEjecucionProgramada parametrizacionEjecucionProgramada : programacionesNovedades) {
                    ParametrizacionEjecucionProgramadaModeloDTO parametrizacionDTO = new ParametrizacionEjecucionProgramadaModeloDTO();
                    parametrizacionDTO.convertToDTO(parametrizacionEjecucionProgramada);
                    resp.add(parametrizacionDTO);
                }
            }
            logger.debug("Finaliza el servicio de consultarProgramacion()");
            return resp;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio consultarProgramacion()", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.processschedule.service.ProcessScheduleService#registrarActualizarProgramacion(java.util.List)
     */
    @Override
    public void registrarActualizarProgramacionPostman(List<ParametrizacionEjecucionProgramadaModeloDTO> programaciones,String user, String pass, UserDTO userDTO) {
        System.out.println("Se inicia el servicio de registrarActualizarProgramacion");
        logger.debug("Se inicia el servicio de registrarActualizarProgramacion(List<ParamaterizacionEjecucionProgramadaModelDTO>)");
        try {
            Boolean autenticado = null;
            if(userDTO.getNombreUsuario().equals("service-account-clientes_web")){
                logger.info(userDTO.getNombreUsuario());
                userDTO.setNombreUsuario(user);
                ThreadContext contexto = ThreadContext.get();
                contexto.setUserName(user);
                // se programan las ejecuciones programadas de las novedades
                ValidarCredencialesUsuario validarCredencialesUsuario = new ValidarCredencialesUsuario(user, pass);
                validarCredencialesUsuario.execute();
                autenticado = validarCredencialesUsuario.getResult();
            }else{
                autenticado = true;
            }
            
            logger.info("Se autentico el usuario: " + user + " con resultado: " + autenticado);
            if (programaciones != null && !programaciones.isEmpty() && autenticado) {
                context = new InitialContext();
                for (ParametrizacionEjecucionProgramadaModeloDTO paramaterizacionEjecucionProgramadaModelDTO : programaciones) {
                    System.out.println("Se inicia ciclo: "+paramaterizacionEjecucionProgramadaModelDTO.getProceso().getImpl());
                    ScheduleAbstract scheduleImpl = (ScheduleAbstract) context
                            .lookup(paramaterizacionEjecucionProgramadaModelDTO.getProceso().getImpl());
                    System.out.println("Se inicia setParamaterizacionEjecucionProgramadaModelDTO: "+paramaterizacionEjecucionProgramadaModelDTO.toString());
                    scheduleImpl.setParamaterizacionEjecucionProgramadaModelDTO(paramaterizacionEjecucionProgramadaModelDTO);
                    System.out.println("Se inicia cancelarTareasProgramadas");
                    scheduleImpl.cancelarTareasProgramadas();
                    System.out.println("Se inicia setScheduleExpression ---> createSchedule"+scheduleImpl.getCurrentProcess());
                    scheduleImpl.setScheduleExpression(scheduleImpl.createSchedule(scheduleImpl.getCurrentProcess()));
                    System.out.println("Se inicia scheduleImpl.init()");
                    scheduleImpl.init();
                }
            }
            logger.debug("Finaliza el serv icio de registrarActualizarProgramacion(List<ParamaterizacionEjecucionProgramadaModelDTO>)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio "
                    + "registrarActualizarProgramacion(List<ParamaterizacionEjecucionProgramadaModelDTO>)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.processschedule.service.ProcessScheduleService#registrarActualizarProgramacion(java.util.List)
     */
    @Override
    public void registrarActualizarProgramacion(List<ParametrizacionEjecucionProgramadaModeloDTO> programaciones) {
        System.out.println("Se inicia el servicio de registrarActualizarProgramacion");
        logger.debug("Se inicia el servicio de registrarActualizarProgramacion(List<ParamaterizacionEjecucionProgramadaModelDTO>)");
        try {
            // se programan las ejecuciones programadas de las novedades
            if (programaciones != null && !programaciones.isEmpty()) {
                context = new InitialContext();
                for (ParametrizacionEjecucionProgramadaModeloDTO paramaterizacionEjecucionProgramadaModelDTO : programaciones) {
                    System.out.println("Se inicia ciclo: "+paramaterizacionEjecucionProgramadaModelDTO.getProceso().getImpl());
                    ScheduleAbstract scheduleImpl = (ScheduleAbstract) context
                            .lookup(paramaterizacionEjecucionProgramadaModelDTO.getProceso().getImpl());
                    System.out.println("Se inicia setParamaterizacionEjecucionProgramadaModelDTO: "+paramaterizacionEjecucionProgramadaModelDTO.toString());
                    scheduleImpl.setParamaterizacionEjecucionProgramadaModelDTO(paramaterizacionEjecucionProgramadaModelDTO);
                    System.out.println("Se inicia cancelarTareasProgramadas");
                    scheduleImpl.cancelarTareasProgramadas();
                    System.out.println("Se inicia setScheduleExpression ---> createSchedule"+scheduleImpl.getCurrentProcess());
                    scheduleImpl.setScheduleExpression(scheduleImpl.createSchedule(scheduleImpl.getCurrentProcess()));
                    System.out.println("Se inicia scheduleImpl.init()");
                    scheduleImpl.init();
                }
            }
            logger.debug("Finaliza el servicio de registrarActualizarProgramacion(List<ParamaterizacionEjecucionProgramadaModelDTO>)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio "
                    + "registrarActualizarProgramacion(List<ParamaterizacionEjecucionProgramadaModelDTO>)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.processschedule.service.ProcessScheduleService#CancelarProgramacionPorProceso(com.asopagos.enumeraciones.core.
     * ProcesoAutomaticoEnum)
     */
    @Override
    public void cancelarProgramacionPorProceso(ProcesoAutomaticoEnum proceso) {
        logger.debug("Se inicia el servicio de CancelarProgramacionPorProceso(ProcesoAutomaticoEnum)");
        try {
            context = new InitialContext();
            ScheduleAbstract scheduleImpl = (ScheduleAbstract) context.lookup(proceso.getImpl());
            scheduleImpl.cancelarTareasProgramadas();
            scheduleImpl.cambiarEstadoProceso(EstadoActivoInactivoEnum.INACTIVO);
            logger.debug("Finaliza el servicio de CancelarProgramacionPorProceso(ProcesoAutomaticoEnum)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio " + "CancelarProgramacionPorProceso(ProcesoAutomaticoEnum)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.processschedule.service.ProcessScheduleService#consultarProgramacion(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void liberarPlanillasBloque9 () {

         logger.debug("ProcessSchedule - liberarPlanillasBloque9 Begin");
        long timeStart = System.nanoTime();

        List<Long> idPlanillas = new ArrayList<>();
        try {
            idPlanillas  = entityManager
                    .createNamedQuery(NamedQueriesConstants.PROCESSSCHEDULE_PARAMEJECUCIONPROGRAMADA_LIBERACION_BLOQUE9)
                    .getResultList();
                    
            long timeIndicesPlanilla = System.nanoTime();
            logger.debug("La invocación al servicio IndicesPlanilla tardó: " + CalendarUtils.calcularTiempoEjecucion(timeStart, timeIndicesPlanilla));
            
            if(!idPlanillas.isEmpty()){

                for (Long  id : idPlanillas) {
                    ProcesarAportesNovedadesByIdPlanilla procesarPlanilla = new ProcesarAportesNovedadesByIdPlanilla(id);
                    procesarPlanilla.execute();
                }

                long timeProcesarAportesNovedadesByIdPlanillaSincrono = System.nanoTime();
                logger.debug("La invocación al servicio ProcesarAportesNovedadesByIdPlanillaSincrono tardó: " + CalendarUtils.calcularTiempoEjecucion(timeIndicesPlanilla, timeProcesarAportesNovedadesByIdPlanillaSincrono));
            }

            logger.debug(" ProcessSchedule - liberarPlanillasBloque9 End");
            
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio  ProcessSchedule - liberarPlanillasBloque9)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_EJECUCION_PROCEDIMIENTO_JOBS);
        }
     }
}