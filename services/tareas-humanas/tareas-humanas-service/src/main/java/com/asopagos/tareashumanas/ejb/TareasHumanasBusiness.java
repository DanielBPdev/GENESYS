package com.asopagos.tareashumanas.ejb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.jbpm.services.task.impl.model.xml.JaxbContent;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.TaskSummary;
import org.kie.remote.client.jaxb.JaxbTaskSummaryListResponse;
import org.kie.remote.jaxb.gen.Task;
import org.kie.services.client.serialization.jaxb.impl.task.JaxbTaskSummary;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.core.EstadoTareaEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.BPMSExecutionException;
import com.asopagos.rest.exception.RecursoNoAutorizadoException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.tareashumanas.client.JBPMClient;
import com.asopagos.tareashumanas.client.JBPMClientFactory;
import com.asopagos.tareashumanas.constants.NamedQueriesConstants;
import com.asopagos.tareashumanas.constants.TaskOperationsEnum;
import com.asopagos.tareashumanas.dto.TareaDTO;
import com.asopagos.tareashumanas.service.TareasHumanasService;
import com.asopagos.tareashumanas.util.EstadoTareaConverter;
import com.asopagos.novedades.composite.clients.InsercionMonitoreoLogs;


/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de requisitos para la afiliación de empleadores <b>Historia de
 * Usuario:</b> Tranversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Stateless
public class TareasHumanasBusiness implements TareasHumanasService {

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(TareasHumanasBusiness.class);
    
	/**
	 * Referencia al logger
	 */    
	private static final int TAMANIO_PAGINA_BPMS = 1000;
	
	/**
	 * Constante que representa el nombre de la tarea asociada a la gestión de la segunda remisión de comunicados en cartera
	 * @see Proceso BPM gestion_cartera_fisica_detallada
	 */
	private static final String GESTIONAR_SEGUNDA_REMISION = "Gestionar segunda remisión de cobro tipo";
	
	/**
	 * Constante que representa el nombre de la tarea asociada a la gestión de resultados de entrega de la segunda remisión de comunicados en cartera
	 * @see Proceso BPM gestion_cartera_fisica_detallada
	 */
	private static final String GESTIONAR_RESULTADO_SEGUNDA_REMISION = "Gestionar resultado segunda remisión de comunicado cobro tipo";
	
	@PersistenceContext(unitName = "bpms_PU")
    private EntityManager entityManager;

	/**
	 * Método que retorna las tareas que están pendientes por asignación y que
	 * por lo tanto son susceptibles de ser asignadas al usuario identificado
	 * por <code>nombreUsuario</code>}
	 * 
	 * @see com.asopagos.tareashumanas.service.TareasHumanasService#obtenerTareasPendientes(java.util.List,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TareaDTO> obtenerTareasPendientes(List<EstadoTareaEnum> estados, UserDTO userDTO) {
		try {
			JBPMClient client = JBPMClientFactory.getJBPMClient();
			List<Status> statusList = new ArrayList<>();
			
			for (EstadoTareaEnum estado : estados) {
				statusList.add(EstadoTareaConverter.toTaskStatus(estado));
			}
			
			JaxbTaskSummaryListResponse tasksSummaryList = client.queryTask(statusList, null,
					userDTO.getNombreUsuario());
			List<TareaDTO> tareas = new ArrayList<>();

			for (JaxbTaskSummary taskSummary : tasksSummaryList.getList()) {
				tareas.add(toTareaDTO(taskSummary,userDTO));
			}
			
			return tareas;
		} catch (NotAuthorizedException e) {
			logger.error(e);
			throw new RecursoNoAutorizadoException(e);
		} catch (Exception e) {
			logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Método que retorna las tareas que están asignadas al usuario identificado
	 * por <code>nombreUsuario</code>
	 * 
	 * @see com.asopagos.tareashumanas.service.TareasHumanasService#obtenerTareasAsignadas(java.util.List,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TareaDTO> obtenerTareasAsignadas(List<EstadoTareaEnum> estados, UserDTO userDTO) {
		try {
			JBPMClient client = JBPMClientFactory.getJBPMClient();
			List<Status> statusList = new ArrayList<>();
			for (EstadoTareaEnum estado : estados) {
				statusList.add(EstadoTareaConverter.toTaskStatus(estado));
			}

			List<String> nombresUsuario = new ArrayList<>();
			nombresUsuario.add(userDTO.getNombreUsuario());
	        boolean continuar = true;
	        int pagina = 0;
	        List<TareaDTO> tareas = new ArrayList<>();
			System.out.println("Tareas- Humanas obtenerTareasAsignadas" ); 
	        while (continuar) {
	            JaxbTaskSummaryListResponse tasksSummaryList = client.queryTask(
	                    statusList, nombresUsuario, TAMANIO_PAGINA_BPMS, ++pagina);
	            for (JaxbTaskSummary taskSummary : tasksSummaryList.getList()) {
	                tareas.add(toTareaDTO(taskSummary, userDTO));
	            }
	            //Si la página actual está llena se debe consultar la siguiente página
	            continuar = tasksSummaryList.getList().size() == TAMANIO_PAGINA_BPMS;
	        }
			System.out.println("Finaliza Tareas- Humanas obtenerTareasAsignadas" ); 
			return tareas;
		} catch (NotAuthorizedException e) {
			logger.error(e);
			throw new RecursoNoAutorizadoException(e);
		} catch (Exception e) {
			logger.error(e);
			throw new TechnicalException(e);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.tareashumanas.service.TareasHumanasService#obtenerTareasAsignadasUsuario(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TareaDTO> obtenerTareasAsignadasUsuario(String nombreUsuario, UserDTO userDTO) {
		try {
			JBPMClient client = JBPMClientFactory.getJBPMClient();
			List<Status> statusList = new ArrayList<>();

			List<EstadoTareaEnum> estadoTarea = new ArrayList<>();
			estadoTarea.add(EstadoTareaEnum.RESERVADA);
			estadoTarea.add(EstadoTareaEnum.EN_PROGRESO);

			for (EstadoTareaEnum estado : estadoTarea) {
				statusList.add(EstadoTareaConverter.toTaskStatus(estado));
			}

			JaxbTaskSummaryListResponse tasksSummaryList = client.queryTask(statusList, null, nombreUsuario);
			
			List<TareaDTO> tareas = new ArrayList<>();

			for (JaxbTaskSummary taskSummary : tasksSummaryList.getList()) {
				tareas.add(toTareaDTO(taskSummary,userDTO));
			}
			return tareas;
		} catch (Exception e) {
			logger.error(e);
			throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
		}
	}

	/**
	 * Método que retorna las tareas que están pendientes por asignación y
	 * también las que están asignadas al usuario identificado por
	 * <code>nombreUsuario</code>
	 * 
	 * @see com.asopagos.tareashumanas.service.TareasHumanasService#obtenerTareasPendientesOAsignadas(java.util.List,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TareaDTO> obtenerTareasPendientesOAsignadas(List<EstadoTareaEnum> estados, UserDTO userDTO) {
		return obtenerTareasAsignadas(estados, userDTO);
	}

	/**
	 * Método que obtiene el objeto TareaDTO a partir del TaskSummary recibido
	 * por parámetro
	 */
	private TareaDTO toTareaDTO(TaskSummary taskSummary, UserDTO user) {
		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setId(taskSummary.getId());
		tareaDTO.setIdInstanciaProceso(taskSummary.getProcessInstanceId());
		ProcesoEnum processDef = buscarDefinicionProceso(taskSummary.getProcessId());
		
		if(processDef != null){
		    tareaDTO.setDeploymentParamName(processDef.getDeploymentParamName());
		    tareaDTO.setNombreProceso(processDef.getDescripcion());
		}else{
		    tareaDTO.setNombreProceso(taskSummary.getProcessId());
		}
		//Se comenta debido a fuertes demoras al concatenar el detalle
		/*Si se trata de un proceso de Cartera se añade al nombre del proces la variable accion cobro
		 * dado que para cualquier accion de cobro se usaba el mismo BPM lo único que cambiaba era el nombre de las tareas*/
		// if(taskSummary.getProcessId().equals(ProcesoEnum.GESTION_CARTERA_FISICA_GENERAL.getNombreProcesoBPM()) ||taskSummary.getProcessId().equals(ProcesoEnum.GESTION_CARTERA_FISICA_DETALLADA.getNombreProcesoBPM()) ||taskSummary.getProcessId().equals(ProcesoEnum.GESTION_COBRO_ELECTRONICO.getNombreProcesoBPM())){
		//     Map<String, Object> variables = obtenerDetalleTarea(taskSummary.getId(), user);		    
		//     tareaDTO.setNombre(taskSummary.getName()+" "+(variables.get("accionCobro")!= null?(String)variables.get("accionCobro"): ""));
		    
		//     // En caso de ser el proceso GESTION_CARTERA_FISICA_DETALLADA, se diferencia el tipo de tarea y se asocia el nombre correspondiente
		//     if(tareaDTO.getNombreProceso().equals(ProcesoEnum.GESTION_CARTERA_FISICA_DETALLADA.getDescripcion()) && 
		//     		(taskSummary.getName().equals(GESTIONAR_SEGUNDA_REMISION) || taskSummary.getName().equals(GESTIONAR_RESULTADO_SEGUNDA_REMISION))){
		//     	tareaDTO.setNombreProceso(ProcesoEnum.GESTION_CARTERA_FISICA_GENERAL.getDescripcion());
		//     }
		// }else{
		//     tareaDTO.setNombre(taskSummary.getName());
		// }

		tareaDTO.setNombre(taskSummary.getName());
		tareaDTO.setDescripcion(taskSummary.getDescription());
		tareaDTO.setEstado(EstadoTareaConverter.toEstadoTareaEnum(taskSummary.getStatus()));
		tareaDTO.setPropietario(taskSummary.getActualOwnerId());
		tareaDTO.setFechaCreacion(taskSummary.getCreatedOn());
		tareaDTO.setFechaExpiracion(taskSummary.getExpirationTime());
		return tareaDTO;
	}

	private TareaDTO toTareaDTOBandejaGestion(TaskSummary taskSummary, UserDTO user) {
		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setId(taskSummary.getId());
		tareaDTO.setIdInstanciaProceso(taskSummary.getProcessInstanceId());
		ProcesoEnum processDef = buscarDefinicionProceso(taskSummary.getProcessId());
		
		if(processDef != null){
		    tareaDTO.setDeploymentParamName(processDef.getDeploymentParamName());
		    tareaDTO.setNombreProceso(processDef.getDescripcion());
		}else{
		    tareaDTO.setNombreProceso(taskSummary.getProcessId());
		}
		//Se comenta debido a fuertes demoras al concatenar el detalle
		/*Si se trata de un proceso de Cartera se añade al nombre del proces la variable accion cobro
		 * dado que para cualquier accion de cobro se usaba el mismo BPM lo único que cambiaba era el nombre de las tareas*/
		 if(taskSummary.getProcessId().equals(ProcesoEnum.GESTION_CARTERA_FISICA_GENERAL.getNombreProcesoBPM()) ||taskSummary.getProcessId().equals(ProcesoEnum.GESTION_CARTERA_FISICA_DETALLADA.getNombreProcesoBPM()) ||taskSummary.getProcessId().equals(ProcesoEnum.GESTION_COBRO_ELECTRONICO.getNombreProcesoBPM())){
		     Map<String, Object> variables = obtenerDetalleTarea(taskSummary.getId(), user);		    
		     tareaDTO.setNombre(taskSummary.getName()+" "+(variables.get("accionCobro")!= null?(String)variables.get("accionCobro"): ""));
		  
		     // En caso de ser el proceso GESTION_CARTERA_FISICA_DETALLADA, se diferencia el tipo de tarea y se asocia el nombre correspondiente
		     if(tareaDTO.getNombreProceso().equals(ProcesoEnum.GESTION_CARTERA_FISICA_DETALLADA.getDescripcion()) && 
		     		(taskSummary.getName().equals(GESTIONAR_SEGUNDA_REMISION) || taskSummary.getName().equals(GESTIONAR_RESULTADO_SEGUNDA_REMISION))){
		     	tareaDTO.setNombreProceso(ProcesoEnum.GESTION_CARTERA_FISICA_GENERAL.getDescripcion());
		     }
		 }else{
		     tareaDTO.setNombre(taskSummary.getName());
		 }
		 System.out.println("Tareas- Humanas toTareaDTOBandejaGestion" ); 
		tareaDTO.setDescripcion(taskSummary.getDescription());
		tareaDTO.setEstado(EstadoTareaConverter.toEstadoTareaEnum(taskSummary.getStatus()));
		tareaDTO.setPropietario(taskSummary.getActualOwnerId());
		tareaDTO.setFechaCreacion(taskSummary.getCreatedOn());
		tareaDTO.setFechaExpiracion(taskSummary.getExpirationTime());
		return tareaDTO;
	}

	
	/**
	 * Método que busca una definición de proceso en el enum de procesos
	 * 
	 * @param idProceso
	 *            Identificador de proceso a buscar
	 * @return ProcesoEnum
	 */
	private ProcesoEnum buscarDefinicionProceso(String idProceso) {
		for (ProcesoEnum p : ProcesoEnum.values()) {
			if (p.getNombreProcesoBPM().equals(idProceso)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Método que permite reclamar la tarea para el usuario recibido
	 * 
	 * @see com.asopagos.tareashumanas.service.TareasHumanasService#iniciarTarea(java.lang.Long,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<String, Object> iniciarTarea(Long idTarea, UserDTO userDTO) {
		try {
			JBPMClient jbpm = JBPMClientFactory.getJBPMClient();
			Map<String, Object> context = obtenerDetalleTarea(idTarea, userDTO);
			jbpm.doTaskOperation(idTarea, TaskOperationsEnum.START.getOperationName());
			return context;
		} catch (NotAuthorizedException e) {
			logger.error(e);
			throw new RecursoNoAutorizadoException(e);
		} catch (Exception e) {
			logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Método que permite reclamar la tarea para el usuario recibido
	 * 
	 * @see com.asopagos.tareashumanas.service.ITareasHumanasService#reclamarTarea(java.lang.String,
	 *      java.lang.Long)
	 * @param idTarea
	 * @param userDTO
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<String, Object> reclamarTarea(Long idTarea, UserDTO userDTO) {
		try {
			JBPMClient jbpm = JBPMClientFactory.getJBPMClient();
			Map<String, Object> context = obtenerDetalleTarea(idTarea, userDTO);
			jbpm.doTaskOperation(idTarea, TaskOperationsEnum.CLAIM.getOperationName());
			return context;
		} catch (NotAuthorizedException e) {
			logger.error(e);
			throw new RecursoNoAutorizadoException(e);
		} catch (Exception e) {
			logger.error(e);
			throw new TechnicalException(e);
		}

	}

	/**
	 * Método que permite obtener el detalle de una tarea
	 * 
	 * @see com.asopagos.tareashumanas.service.ITareasHumanasService#obtenerDetalleTarea(java.lang.Long)
	 * @param idTarea
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<String, Object> obtenerDetalleTarea(Long idTarea, UserDTO user) {
		try {
			Map<String, Object> variables = null;
			JBPMClient jbpm = JBPMClientFactory.getJBPMClient();
			Task task = jbpm.getTask(idTarea);
			if (task != null) {
				JaxbContent content = jbpm.getContent(task.getTaskData().getOutputContentId());
				if (content != null) {
					variables = content.getContentMap();
				}
				logger.trace(variables);
			}
			return variables;
		} catch (NotAuthorizedException e) {
			logger.error(e);
			throw new RecursoNoAutorizadoException(e);
		} catch (Exception e) {
			logger.error(e);
			throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
		}

	}

	/**
	 * Método que permite obtener el id de la tarea que se encuentra activa para
	 * una instancia de proceso
	 * 
	 * @param idInstanciaProceso
     * @param user
	 * @return
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<String, Object> obtenerTareaActiva(Long idInstanciaProceso, UserDTO user) {
		JaxbTaskSummaryListResponse tareas = null;
		try {
			logger.info("Obteniendo tarea activa para la instancia de proceso: " + idInstanciaProceso + " del usuario: " + user.getNombreUsuario());
			JBPMClient client = JBPMClientFactory.getJBPMClient();
			tareas = client.queryTask(Arrays.asList(Status.Ready, Status.Reserved), idInstanciaProceso,
					user.getNombreUsuario());
		} catch (NotAuthorizedException e) {
			InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( "UserDTO: " + user.getNombreUsuario()
			+ " idInstanciaProceso: "+ idInstanciaProceso
			,"TareasHumanas-obtenerTareaActiva");
			insercionMonitoreoLogs.execute();
			logger.error(e);
			throw new RecursoNoAutorizadoException(e);
		} catch (Exception e) {
			InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( "UserDTO: " + user.getNombreUsuario()
		+ " idInstanciaProceso: "+ idInstanciaProceso
		,"TareasHumanas-obtenerTareaActiva");
			insercionMonitoreoLogs.execute();
			logger.error(e);
			throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
		}

		if (tareas.getList() != null && !tareas.getList().isEmpty()) {
			Long idTarea = tareas.getList().get(0).getId();
			return obtenerDetalleTarea(idTarea, user);
		} else {
		    return null;
		}
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<String, Object> obtenerTareaActivaSat(Long idInstanciaProceso, String nombreUsuario,UserDTO user) {
		JaxbTaskSummaryListResponse tareas = null;
		user.setNombreUsuario(nombreUsuario);
		try {
			logger.info("Obteniendo tarea activa para la instancia de proceso: " + idInstanciaProceso + " del usuario: " + nombreUsuario);
			JBPMClient client = JBPMClientFactory.getJBPMClient();
			tareas = client.queryTask(Arrays.asList(Status.Ready, Status.Reserved), idInstanciaProceso,
					nombreUsuario);
		} catch (NotAuthorizedException e) {
			InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( "UserDTO: " + nombreUsuario
			+ " idInstanciaProceso: "+ idInstanciaProceso
			,"TareasHumanas-obtenerTareaActiva");
			insercionMonitoreoLogs.execute();
			logger.error(e);
			throw new RecursoNoAutorizadoException(e);
		} catch (Exception e) {
			InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( "UserDTO: " + nombreUsuario
		+ " idInstanciaProceso: "+ idInstanciaProceso
		,"TareasHumanas-obtenerTareaActiva");
			insercionMonitoreoLogs.execute();
			logger.error(e);
			throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
		}

		if (tareas.getList() != null && !tareas.getList().isEmpty()) {
			Long idTarea = tareas.getList().get(0).getId();
			return obtenerDetalleTarea(idTarea, user);
		} else {
		    return null;
		}
	}

	/**
	 * Método genérico para la terminación de tareas
	 * 
	 * @param idTarea
	 * @param params
	 * @param userDTO
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void terminarTarea(Long idTarea, Map<String, Object> params, UserDTO userDTO) {
		logger.info("Terminando La Tarea: " + idTarea);
		logger.info("params: " + params);
		logger.info("userDTO: " + userDTO);
		// try {
			JBPMClient client = obtenerClienteBPM(params);
			logger.info("client: " + client);
			JBPMClient singleClient = JBPMClientFactory.getJBPMClient();
			logger.info("singleClient: " + singleClient);

			Task tarea = singleClient.getTask(idTarea);
			logger.info("tarea: " + tarea);
			
			//[Temporal] revision del estado de la tarea previo su cierre.
			if (tarea != null && tarea.getTaskData() != null && tarea.getTaskData().getStatus() != null) {
                logger.info("El estado de la tarea es " + tarea.getTaskData().getStatus().name());
            }
			if (tarea.getTaskData().getStatus().equals(Status.Ready)
					|| tarea.getTaskData().getStatus().equals(Status.Reserved)) {
				// Primero se debe iniciar la tarea para poder terminarla
				singleClient.doTaskOperation(idTarea, TaskOperationsEnum.START.getOperationName());
			}
			client.doTaskOperation(idTarea, TaskOperationsEnum.COMPLETE.getOperationName());
			logger.info("Fin Terminando La Tarea: " + idTarea);
			
		// } catch (NotAuthorizedException e) {
			// InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( "UserDTO: " + userDTO.getNombreUsuario()
            //                     + " parametros: "+ params
            // 					,"TareasHumanas-terminarTarea");
			// insercionMonitoreoLogs.execute();
		// 	logger.error(e);
		// 	throw new RecursoNoAutorizadoException(e);
		// } catch (Exception e) {
		// 	// InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( "UserDTO: " + userDTO.getNombreUsuario()
        //     //                     + " parametros: "+ params
        //     // 					,"TareasHumanas-terminarTarea");
		// 	// insercionMonitoreoLogs.execute();
		// 	logger.error(e);
		// 	throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
		// }
	}
	
	/**
	 * Obtiene el cliente para interactuar con el BPM
	 * @param params
	 * @return
	 */
    private JBPMClient obtenerClienteBPM(Map<String, Object> params) {
        JBPMClient client = JBPMClientFactory.getJBPMClient();
        MultivaluedMap<String, Object> taskParams = null;
        if (params != null && !params.isEmpty()) {
        	taskParams = new MultivaluedHashMap<>();
        	for (Map.Entry<String, Object> entry : params.entrySet()) {
        	    Object param = entry.getValue();
        	    if(entry.getValue() instanceof Integer){
        	        param = entry.getValue() + "i";
        	    }else if (entry.getValue() instanceof String){
        	        param = "\"" + entry.getValue() + "\"";
        	    }
        		taskParams.add("map_" + entry.getKey(), param); 
        	}
        	client = JBPMClientFactory.getJBPMClient(taskParams);
        }
        return client;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.tareashumanas.service.TareasHumanasService#reasignarTarea(
	 * java.lang.Long, java.lang.String, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
    public void reasignarTarea(Long idTarea, String usuario, UserDTO userDTO) {
        try {
            JBPMClient jbpm = JBPMClientFactory.getJBPMClient();
            jbpm.reassignTask(idTarea, usuario);
        } catch (NotAuthorizedException e) {
			InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( "UserDTO: " + userDTO.getNombreUsuario()
                                + " idInstanciaProceso: "+ idTarea
								+ "Usuario: "+ usuario
            					,"TareasHumanas-reasignarTarea");
			insercionMonitoreoLogs.execute();
            logger.error(e);
            throw new RecursoNoAutorizadoException(e);
        } catch (Exception e) {
			InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( "UserDTO: " + userDTO.getNombreUsuario()
			+ " idInstanciaProceso: "+ idTarea
			+ "Usuario: "+ usuario
			,"TareasHumanas-reasignarTarea");
	insercionMonitoreoLogs.execute();
            logger.error(e);
            throw new TechnicalException(e);
        }
    }

	/*
	 * (non-Javadoc)
	 * @see com.asopagos.tareashumanas.service.TareasHumanasService#obtenerUltimaTareaTerminada()
	 */
	@Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TareaDTO obtenerUltimaTareaTerminada(Long idProceso, UserDTO userDTO) {
		JaxbTaskSummaryListResponse tareas = null;
		try {
			JBPMClient client = JBPMClientFactory.getJBPMClient();
			tareas = client.queryTask(Arrays.asList(Status.Completed), idProceso);
		} catch (NotAuthorizedException e) {
			logger.error(e);
			throw new RecursoNoAutorizadoException(e);
		} catch (Exception e) {
			logger.error(e);
			throw new TechnicalException(e);
		}

		if (!tareas.getList().isEmpty()) {
			Collections.sort(tareas.getList(), new Comparator<JaxbTaskSummary>(){

				@Override
				public int compare(JaxbTaskSummary arg0, JaxbTaskSummary arg1) {
					if(arg0.getCreatedOn().after(arg1.getCreatedOn()))
						return 1;
					return -1;
				}
			});
			return toTareaDTO(tareas.getList().get(0),userDTO);
		} else {
			return null;
		}
	}

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<TareaDTO> obtenerTareasAsignadasUsuarios(List<String> nombresUsuario, UserDTO userDTO) {
        InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( "UserDTO: " + userDTO.getNombreUsuario()
            					,"TareasHumanas-obtenerTareasAsignadasUsuarios");
			insercionMonitoreoLogs.execute();
        List<Status> statusList = new ArrayList<>();
        statusList.add(EstadoTareaConverter.toTaskStatus(EstadoTareaEnum.EN_PROGRESO));
        statusList.add(EstadoTareaConverter.toTaskStatus(EstadoTareaEnum.RESERVADA));
        
        List<TareaDTO> tareas = new ArrayList<>();
        JBPMClient client = JBPMClientFactory.getJBPMClient();
        boolean continuar = true;
        int pagina = 0;
        while (continuar) {
            JaxbTaskSummaryListResponse tasksSummaryList = client.queryTask(
                    statusList, nombresUsuario, TAMANIO_PAGINA_BPMS, ++pagina);
            for (JaxbTaskSummary taskSummary : tasksSummaryList.getList()) {
                tareas.add(toTareaDTO(taskSummary, userDTO));
            }
            //Si la página actual está llena se debe consultar la siguiente página
            continuar = tasksSummaryList.getList().size() == TAMANIO_PAGINA_BPMS;
        }
        return tareas;
    }
    
    /*
     * (non-Javadoc)
     * @see com.asopagos.tareashumanas.service.TareasHumanasService#obtenerUltimaTareaTerminada()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public TareaDTO obtenerTareaActivaInstancia(Long idProceso, UserDTO userDTO) {
        JaxbTaskSummaryListResponse tareas = null;
        try {
            JBPMClient client = JBPMClientFactory.getJBPMClient();
            List<Status> statusList = new ArrayList<>();
            statusList.add(EstadoTareaConverter.toTaskStatus(EstadoTareaEnum.EN_PROGRESO));
            statusList.add(EstadoTareaConverter.toTaskStatus(EstadoTareaEnum.RESERVADA));
            statusList.add(EstadoTareaConverter.toTaskStatus(EstadoTareaEnum.CREADA));
            tareas = client.queryTask(statusList, idProceso);
        } catch (NotAuthorizedException e) {
            logger.error(e);
            throw new RecursoNoAutorizadoException(e);
        } catch (Exception e) {
            logger.error(e);
            throw new TechnicalException(e);
        }

        if (!tareas.getList().isEmpty()) {
            return toTareaDTO(tareas.getList().get(0),userDTO);
        } else {
            return null;
        }
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<TareaDTO> obtenerTareasFinalizadas(UserDTO userDTO) {
        logger.debug("Obteniendo la lista de tareas finalizadas para el usuario " + userDTO.getNombreUsuario());
        List<TareaDTO> tareasFinalizadas = new ArrayList<>();
        List<Object[]> tareas = entityManager.createNamedQuery(
                NamedQueriesConstants.OBTENER_TAREAS_PENDIENTES)
                .setParameter("username", userDTO.getNombreUsuario())
                .getResultList();
        
        for (Object[] tarea : tareas) {
            TareaDTO tareaDTO = new TareaDTO();
            tareaDTO.setId(((BigInteger)tarea[0]).longValue());
            tareaDTO.setIdInstanciaProceso(((BigInteger)tarea[1]).longValue());
            tareaDTO.setDescripcion((String)tarea[2]);
            tareaDTO.setFechaCreacion((Date)tarea[3]);
            tareaDTO.setNombre((String)tarea[4]);
            tareaDTO.setNombreProceso(buscarDefinicionProceso((String)tarea[5]).getDescripcion());
            tareasFinalizadas.add(tareaDTO);
        }
        return tareasFinalizadas;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void suspenderTarea(Long idTarea, Map<String, Object> params, UserDTO userDTO) {
        logger.info("Suspendiendo la Tarea: " + idTarea);
        try {
            JBPMClient client = obtenerClienteBPM(params);
            JBPMClient singleClient = JBPMClientFactory.getJBPMClient();
            Task tarea = singleClient.getTask(idTarea);
            
            if (tarea.getTaskData().getStatus().equals(Status.Ready)
                    || tarea.getTaskData().getStatus().equals(Status.Reserved)
                    || tarea.getTaskData().getStatus().equals(Status.InProgress)) {
                // Primero se debe iniciar la tarea para poder terminarla
                client.doTaskOperation(idTarea, TaskOperationsEnum.SUSPEND.getOperationName());
                logger.debug("Se suspendió la Tarea: " + idTarea);
            }
        } catch (NotAuthorizedException e) {
			InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( "UserDTO: " + userDTO.getNombreUsuario()
                                + " parmetros: "+ params
            					,"TareasHumanas-suspenderTarea");
			insercionMonitoreoLogs.execute();
            logger.error(e);
            throw new RecursoNoAutorizadoException(e);
        } catch (Exception e) {
			InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( "UserDTO: " + userDTO.getNombreUsuario()
                                + " parmetros: "+ params
            					,"TareasHumanas-suspenderTarea");
			insercionMonitoreoLogs.execute();
            logger.error(e);
            throw new TechnicalException(e);
        }
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void retomarTarea(Long idTarea, Map<String, Object> params, UserDTO userDTO) {
        logger.info("Retomando la Tarea: " + idTarea);
        try {
            JBPMClient client = obtenerClienteBPM(params);
            JBPMClient singleClient = JBPMClientFactory.getJBPMClient();
            Task tarea = singleClient.getTask(idTarea);

            if (tarea.getTaskData().getStatus().equals(Status.Suspended)) {
                // Primero se debe iniciar la tarea para poder terminarla
                client.doTaskOperation(idTarea, TaskOperationsEnum.RESUME.getOperationName());
                logger.debug("Se retomó la Tarea: " + idTarea);
            }
        } catch (NotAuthorizedException e) {
            logger.error(e);
            throw new RecursoNoAutorizadoException(e);
        } catch (Exception e) {
            logger.error(e);
            throw new TechnicalException(e);
        }
    }
    
    @Override
    public String consultarEstadoTarea(Long idProceso, UserDTO user){
        logger.info("Consultando estado de la tarea asociada al proceso : " + idProceso);
        
        TareaDTO tareaDTO = obtenerTareaActivaInstancia(idProceso, user);
        String estado = "";
        
        if (tareaDTO != null && tareaDTO.getId() != null) {
            
            JBPMClient jbpm = JBPMClientFactory.getJBPMClient();
            Task task = jbpm.getTask(tareaDTO.getId());
            estado =task.getTaskData().getStatus().name(); 
            
            logger.info("La tarea " + tareaDTO.getId() + " se encuentra en estado " + estado);
        }else{
            logger.info("El " + idProceso + " no tiene tarea asociada");
            return "No se encontró una tarea asociada al proceso " + idProceso;
        }
        logger.info("Se consultó el estado de la tarea asociada al proceso : " + idProceso);  
        return estado;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void activarTarea(Long idTarea, Map<String, Object> params, UserDTO userDTO) {
        logger.info("Activando la Tarea: " + idTarea);
        JBPMClient client = obtenerClienteBPM(params);
        JBPMClient singleClient = JBPMClientFactory.getJBPMClient();
        Task tarea = singleClient.getTask(idTarea);

        if (tarea.getTaskData().getStatus().equals(Status.Created)) {
            client.doTaskOperation(idTarea, TaskOperationsEnum.ACTIVATE.getOperationName());
            this.iniciarTarea(idTarea, userDTO);
            logger.debug("Se activó la Tarea: " + idTarea);
        }
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<TareaDTO> obtenerTareasCreadasSinPropietario(UserDTO userDTO) {
        logger.debug("Obteniendo la lista de tareas CREATED sin propietarios");
        
        List<Object[]> tareas = new ArrayList<>();
        List<TareaDTO> tareasCreadas = new ArrayList<>();
        tareas = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_TAREAS_CREADAS_SIN_PROPIETARIO)
                .getResultList();
        
        for (Object[] tarea : tareas) {
            TareaDTO tareaDTO = new TareaDTO();
            tareaDTO.setId(((BigInteger)tarea[0]).longValue());
            tareaDTO.setIdInstanciaProceso(((BigInteger)tarea[1]).longValue());
            tareaDTO.setDescripcion((String)tarea[2]);
            tareasCreadas.add(tareaDTO);
        }
        return tareasCreadas;
    }
}

