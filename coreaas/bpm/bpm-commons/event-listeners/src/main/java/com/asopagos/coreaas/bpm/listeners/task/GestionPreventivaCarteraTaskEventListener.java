package com.asopagos.coreaas.bpm.listeners.task;

import java.util.Map;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.api.task.model.Task;

public class GestionPreventivaCarteraTaskEventListener extends CoreTaskEventListener {

    public GestionPreventivaCarteraTaskEventListener(RuntimeManager runtimeManager) {
        super(runtimeManager);
    }

    @Override
    protected void crearContexto(Task task, Map<String, Object> context) {
        WorkflowProcessInstance instance = (WorkflowProcessInstance) ksession
                .getProcessInstance(task.getTaskData().getProcessInstanceId());
        Object idSolicitud = instance.getVariable("idSolicitud");
        context.put("idSolicitud", idSolicitud);
    }
    
    

}