package com.asopagos.coreaas.bpm.listeners.task;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.ruleflow.instance.RuleFlowProcessInstance;
import org.jbpm.services.task.events.DefaultTaskEventListener;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.task.TaskEvent;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.User;
import org.kie.internal.task.api.InternalTaskService;

/**
 * Esta clase es notificada cuando ocurre un evento relacionada con las tareas de los
 * procesos de negocio. Extiende de org.jbpm.services.task.events.DefaultTaskEventListener
 * y solamente implementa los métodos relacionados con los eventos que le interesan.
 *
 */
public abstract class CoreTaskEventListener extends DefaultTaskEventListener {

	/**
	 * Este campo es inyectado por el bpm en el momento de crear el event listener
	 * conforme a lo que se definio en el kie-deployment-descriptor.xml del proceso
	 */
	protected RuntimeManager runtimeManager;


	/**
	 * Este campo es usado para obtener acceso a 
	 * los servicios de runtime y de tareas del bpms
	 */
	protected RuntimeEngine engine;


	/**
	 * Proporciona acceso a los servicios de runtime del bpms.
	 */
	protected KieSession ksession;
	

	/**
	 * Proporciona acceso a los servicios de tareas humanas 
	 * del bpms. 
	 */
	protected TaskService taskService;
	
	/**
	 * Corresponde a la regla de navegación por defecto cuando
	 * no se ha especificado en el modelo bpmn una regla de navegación para 
	 * una tarea
	 */
	private static final String DEFAULT_RULE_NAV="blanco";
	
	/**
	 * Constructor por defecto
	 * @param runtimeManager
	 */
	public CoreTaskEventListener(RuntimeManager runtimeManager){
		this.runtimeManager=runtimeManager;	
		engine = runtimeManager.getRuntimeEngine(null);	
		ksession = engine.getKieSession();
		taskService=engine.getTaskService();
	}


	/**
	 * Crea el contexto con variables generales para todas las tareas.
	 * @param taskStart Tarea Humana creada
	 * @return El contexto inicial con las variables generales.
	 */
    protected Map<String, Object> crearContextoInicial(Task taskStart){
    	Map<String, Object> context = new HashMap<>();
    
		//se setea la regla de navegación
		Map<String, Object> content=taskService.getTaskContent(taskStart.getId());
		String ruleNav=(String)content.get("Content");
    	if(ruleNav==null){
    		//si no existe una regla de navegación definida para la tarea se coloca la regla por defecto
    		context.put("ruleNav", DEFAULT_RULE_NAV);
    	}else{
    		context.put("ruleNav", ruleNav);
    	}
    	//se setea el identificador de la tarea
		context.put("idTarea", taskStart.getId());
		
		//se setea la instancia de proceso asociada a la tarea
		context.put("idInstanciaProceso",taskStart.getTaskData().getProcessInstanceId());
		
		if(taskStart.getDescription() != null){
			context.put("numeroRadicado", taskStart.getDescription());
		}
		
		context.put("fechaActivacion", taskStart.getTaskData().getActivationTime());

    	return context;
    }
    
    /**
     * Cada TaskEventListener tiene que implementar este método colocando en el contexto
     * las variables de negocio que se necesitan 
     * incluir particularmente dentro de las tareas humanas del proceso
     * @param task Tarea Humana que se esta creando
     * @param context Contexto de negocio de las tareas
     */
    abstract protected void crearContexto(Task task, Map<String, Object> context);
	

	@Override
	public void afterTaskAddedEvent(TaskEvent event) {
		Task taskStart=event.getTask();
		
		
		//se crea el contexto inicial que tienen todos los task event listeners
		Map<String, Object> context= crearContextoInicial(taskStart);
		
		//se crea el cntexto particular para cada event listener
		crearContexto(taskStart,context);
		
		//se adiciona el contexto al contenido de la tarea
		((InternalTaskService)taskService).addContent(taskStart.getId(), context);
	}

	@Override
    public void beforeTaskDelegatedEvent(TaskEvent event) {
		//se obtiene el usuario que tiene la tarea antes de ser reasignada.
        User actualUser = event.getTask().getTaskData().getActualOwner();
        
        //se buscan las variables con sus respectivos valores, para el proceso al que pertenece la tarea.
        Map <String, Object> variablesProceso = obtenerVariablesProceso(event.getTask().getTaskData().getProcessInstanceId());
        
        //se hace el cambio de la variable que contiene el valor del dueño de la tarea por una bandera.
        cambiarValorVariable(event, variablesProceso, actualUser.getId(), "CONSTANTE_PARA_CAMBIAR");
    }
    
    @Override
    public void afterTaskDelegatedEvent(TaskEvent event) {
    	//se obtiene el usuario que tiene la tarea luego de ser reasignada.
        User actualUser = event.getTask().getTaskData().getActualOwner();
        
        //se buscan las variables con sus respectivos valores, para el proceso al que pertenece la tarea.
        Map <String, Object> variablesProceso = obtenerVariablesProceso(event.getTask().getTaskData().getProcessInstanceId());
        
        //se hace el cambio de la variable que tiene la bandera del before por el valor del usuario que actualmente tiene la tarea.
        cambiarValorVariable(event, variablesProceso, "CONSTANTE_PARA_CAMBIAR", actualUser.getId());
    }
    
	
	/**
	 * Método encargado de obtener las variables de una instancia de proceso dada.
	 * 
	 * @param processInstance
	 * 			la instancia del proceso de la cual se quiere obtener las variables.
	 * 
	 * @return Map<String, Object> con los nombres y valores de las variables del proceso.
	 */
	private Map<String, Object> obtenerVariablesProceso(Long processInstance)
    {
    	RuleFlowProcessInstance instance = (RuleFlowProcessInstance) ksession
                .getProcessInstance(processInstance);
        return instance.getVariables();
    }
	
    /**
     * Método encargado de realizar el cambio del valor en una variable del proceso.
     * 
	 * @param event
	 * 			evento sobre la tarea.
	 * 
	 * @param variablesProceso
	 * 			listado de variables del proceso con sus respectivos valores.
	 * 
	 * @param valorActual
	 * 			id del usuario que actualmente tiene asignada la tarea.
	 * 
	 * @param valorCambio
	 * 			valor que será reemplazado en la variable 
	 */
	private void cambiarValorVariable(TaskEvent event, Map<String, Object> variablesProceso,
			String valorActual, String valorCambio) {

        for (Map.Entry<String, Object> entry : variablesProceso.entrySet()){
        	if(entry.getValue().equals(valorActual) && !entry.getKey().equals("initiator")){
        		setVariableProceso(event.getTask().getTaskData().getProcessInstanceId(), entry.getKey(), valorCambio);
        	}
        }
	}

    /**
     * Método encargado de cambiar el valor de una variable en una instancia de proceso dada.
     * 
     * @param processInstance
     * 			instancia del proceso en el cual se va a realziar el cambio.
     * 
     * @param nombreVariable
     * 			nombre de la variable que se quiere cambiar.
     * 
     * @param valor
     * 			valor por el cual se desea cambiar.
     */
    private void setVariableProceso(Long processInstance, String nombreVariable, String valor){
    	
    	RuleFlowProcessInstance instance = (RuleFlowProcessInstance) ksession
                .getProcessInstance(processInstance);
        instance.setVariable(nombreVariable, valor);
    }
}
