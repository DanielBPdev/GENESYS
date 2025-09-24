package com.asopagos.coreaas.bpm.aportes.correcciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.drools.core.command.runtime.process.SetProcessInstanceVariablesCommand;
import org.jbpm.process.audit.VariableInstanceLog;
import org.jbpm.process.audit.command.FindVariableInstancesCommand;
import org.jbpm.ruleflow.instance.RuleFlowProcessInstance;
import org.jbpm.services.task.events.DefaultTaskEventListener;
import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.api.task.TaskEvent;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.TaskSummary;
import org.kie.api.task.model.User;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

public class CorreccionesAportesTest extends JbpmJUnitBaseTestCase {
    
	private final ILogger logger = LogManager.getLogger(CorreccionesAportesTest.class);
    
    private static final String PROCESO_CORRECCIONES_APORTES = "com/asopagos/coreaas/bpm/correcciones_aportes/correcciones_aportes.bpmn2";
    
    private static final String PROCESO_CORRECCIONES_APORTES_ID = "com.asopagos.coreaas.bpm.correcciones.correcciones_aportes";
    
    public CorreccionesAportesTest(){
        super(true,true);
    }
    KieSession ksession;
    @Test
    public void solicitudCorreccionRechazadaConTareaReasignada(){
//        logger.info("solicitudCorreccionRechazadaConTareaReasignada");
//        addTaskEventListener(new DefaultTaskEventListener() {
//            @Override
//            public void beforeTaskDelegatedEvent(TaskEvent event) {
//                logger.info("Usuario antes de reasignar: " + event.getTask().getTaskData().getActualOwner());
//                User actualUser = event.getTask().getTaskData().getActualOwner();
//                Map <String, Object> variablesProceso = obtenerVariablesProceso(event.getTask().getTaskData().getProcessInstanceId());
//                cambioValorVariable(event, variablesProceso, actualUser.getId(), "CONSTANTE_PARA_CAMBIAR");
//            }
//            
//            @Override
//            public void afterTaskDelegatedEvent(TaskEvent event) {
//                logger.info("Usuario despues de reasingar: " + event.getTask().getTaskData().getActualOwner());
//                User actualUser = event.getTask().getTaskData().getActualOwner();
//                Map <String, Object> variablesProceso = obtenerVariablesProceso(event.getTask().getTaskData().getProcessInstanceId());
//                cambioValorVariable(event, variablesProceso, "CONSTANTE_PARA_CAMBIAR", actualUser.getId());
//                
//            }
//        });
//        RuntimeManager runtimeManager = createRuntimeManager(PROCESO_CORRECCIONES_APORTES);
//        RuntimeEngine runtimeEngine = getRuntimeEngine(ProcessInstanceIdContext.get());
//        ksession = runtimeEngine.getKieSession();
//        TaskService taskService = runtimeEngine.getTaskService();
//        runtimeManager.disposeRuntimeEngine(runtimeEngine);
//        Map<String, Object> params = parametrosIniciales();
//        ProcessInstance processInstance = ksession.startProcess(PROCESO_CORRECCIONES_APORTES_ID, params);
//        long ksessionID = ksession.getIdentifier();
//        imprimirVariables(processInstance.getId());
//        assertProcessInstanceActive(processInstance.getId(), ksession);
//        assertNodeTriggered(processInstance.getId(), "StartProcess", "Analizar solicitud de corrección");
//        
//        assertEquals(ksessionID, ksession.getIdentifier());
//        
//        TaskSummary task = buscarTarea(taskService, "john");
//        
//        logger.info("tarea: " + task);
//        taskService.delegate(task.getId(), "john", "mary");
//        taskService.start(task.getId(), "mary");
//        params = new HashMap<>();
//        params.put("resultadoAnalisis", "2");
//        
//        
//        imprimirVariables(processInstance.getId());
//        taskService.complete(task.getId(), "mary", params);
//        assertNodeTriggered(processInstance.getId(), "StartProcess", "Analizar solicitud de corrección", "Estado", "End Event 1");
    }

    @Test
    public void solicitudCorreccionRechazada(){
//        logger.info("solicitudCorreccionRechazada");
//        createRuntimeManager(Strategy.SINGLETON, "manager", PROCESO_CORRECCIONES_APORTES);
//        RuntimeEngine runtimeEngine = getRuntimeEngine(null);
//        ksession = runtimeEngine.getKieSession();
//        TaskService taskService = runtimeEngine.getTaskService();
//        
//        Map<String, Object> params = parametrosIniciales();
//        ProcessInstance processInstance = ksession.startProcess(PROCESO_CORRECCIONES_APORTES_ID, params);
//        long ksessionID = ksession.getIdentifier();
//        
//        assertProcessInstanceActive(processInstance.getId(), ksession);
//        assertNodeTriggered(processInstance.getId(), "StartProcess", "Analizar solicitud de corrección");
//        
//        assertEquals(ksessionID, ksession.getIdentifier());
//        
//        TaskSummary task = buscarTarea(taskService, "john");
//        
//        logger.info("tarea " + task);
//        RuleFlowProcessInstance rfpi = (RuleFlowProcessInstance)processInstance;
//        logger.info("Variables: " + rfpi.getVariables());
//
//        taskService.start(task.getId(), "john");
//        params = new HashMap<>();
//        params.put("resultadoAnalisis", "2");
//        taskService.complete(task.getId(), "john", params);
//        imprimirVariables(processInstance.getId());
//        assertNodeTriggered(processInstance.getId(), "StartProcess", "Analizar solicitud de corrección", "Estado", "End Event 1");
    }
    
    @Test
    public void solicitudCorreccionAprobada(){
//        logger.info("solicitudCorreccionAprobada");
//        createRuntimeManager(Strategy.SINGLETON, "manager", PROCESO_CORRECCIONES_APORTES);
//        RuntimeEngine runtimeEngine = getRuntimeEngine(null);
//        ksession = runtimeEngine.getKieSession();
//        TaskService taskService = runtimeEngine.getTaskService();
//        
//        // start process
//        Map<String, Object> params = parametrosIniciales();
//        ProcessInstance processInstance = ksession.startProcess(PROCESO_CORRECCIONES_APORTES_ID, params);
//        long ksessionID = ksession.getIdentifier();
//        
//        assertProcessInstanceActive(processInstance.getId(), ksession);
//        assertNodeTriggered(processInstance.getId(), "StartProcess", "Analizar solicitud de corrección");
//        
//        assertEquals(ksessionID, ksession.getIdentifier());
//        
//        TaskSummary task = buscarTarea(taskService, "john");
//        
//        logger.info("tarea " + task);
//
//        taskService.start(task.getId(), "john");
//        params = new HashMap<>();
//        params.put("resultadoAnalisis", "1");
//        params.put("usuarioSupervisor", "mary");
//        taskService.complete(task.getId(), "john", params);
//        assertNodeTriggered(processInstance.getId(), "StartProcess", "Analizar solicitud de corrección", "Estado", "Aprobar solicitud de corrección");
//        
//        task = buscarTarea(taskService, "mary");
//        
//        taskService.start(task.getId(), "mary");
//        params = new HashMap<>();
//        params.put("usuarioAnalista", "john");
//        taskService.complete(task.getId(), "mary", params);
//        assertNodeTriggered(processInstance.getId(), "StartProcess", "Analizar solicitud de corrección", 
//                "Estado", "Aprobar solicitud de corrección", "Consultar resultados solicitud de corrección");
//        
//        task = buscarTarea(taskService, "john");
//        taskService.start(task.getId(), "john");
//        taskService.complete(task.getId(), "john", null);
//        assertNodeTriggered(processInstance.getId(), "StartProcess", "Analizar solicitud de corrección", 
//                "Estado", "Aprobar solicitud de corrección", "Consultar resultados solicitud de corrección", "Generar comunicado");
//        
//        task = buscarTarea(taskService, "john");
//        taskService.start(task.getId(), "john");
//        taskService.complete(task.getId(), "john", null);
//        imprimirVariables(processInstance.getId());
//        assertNodeTriggered(processInstance.getId(), "StartProcess", "Analizar solicitud de corrección", 
//                "Estado", "Aprobar solicitud de corrección", "Consultar resultados solicitud de corrección", "Generar comunicado", "End Event 2");
//        
//        
    }

    private Map<String, Object> parametrosIniciales() {
        Map<String, Object> params = new HashMap<>();
        params.put("numeroRadicado", "123456");
        params.put("usuarioAnalista", "john");
        params.put("idSolicitud", "654321");
        return params;
    }

    private TaskSummary buscarTarea(TaskService taskService, String usuario) {
        List<Status> statusList = new ArrayList<>();
        statusList.add(Status.Ready);
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(usuario, "en-UK");
        TaskSummary task = list.get(0);
        return task;
    }
    
    private void imprimirVariables(Long processInstance) {
        FindVariableInstancesCommand cmd = new FindVariableInstancesCommand(
        processInstance);
        List<VariableInstanceLog> result = ksession.execute(cmd);
        logger.info("Variables: " + result);
    }

    
    private Map<String, Object> obtenerVariablesProceso(Long processInstance)
    {
    	RuleFlowProcessInstance instance = (RuleFlowProcessInstance) ksession
                .getProcessInstance(processInstance);
        return instance.getVariables();
    }
    
    private void setVariableProceso(Long processInstance, String llave, String valor){
    	
    	RuleFlowProcessInstance instance = (RuleFlowProcessInstance) ksession
                .getProcessInstance(processInstance);
        instance.setVariable(llave, valor);
    }
    
    /**
	 * @param event
	 * @param variablesProceso
	 * @param usuarioActual
	 * @param valorCambio
	 */
	private void cambioValorVariable(TaskEvent event, Map<String, Object> variablesProceso,
			String valorActual, String valorCambio) {
        for (Map.Entry<String, Object> entry : variablesProceso.entrySet()){
        	if(entry.getValue().equals(valorActual) && !entry.getKey().equals("initiator")){
        		setVariableProceso(event.getTask().getTaskData().getProcessInstanceId(), entry.getKey(), valorCambio);
        	}
        }
	}
}
