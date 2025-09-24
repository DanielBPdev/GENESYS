package com.asopagos.coreaas.bpm.listeners.task;

import java.util.Map;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.api.task.model.Task;

/**
 * Esta clase adiciona al contexto de las tareas las variables de negocio para el 
 * proceso de cierre recaudo
 * @author squintero
 *
 */
public class CierreRecaudoTaskEventListener extends CoreTaskEventListener {

    /**
     * Constructor por defecto de la clase
     * @param runtimeManager
     */
    public CierreRecaudoTaskEventListener(RuntimeManager runtimeManager) {
        super(runtimeManager);
    }

    
    @Override
    protected void crearContexto(Task task, Map<String, Object> context) {
        // se obtiene el identificador de la solicitud
        WorkflowProcessInstance instance = (WorkflowProcessInstance) ksession
                .getProcessInstance(task.getTaskData().getProcessInstanceId());
        Object numeroRadicado = instance.getVariable("numeroRadicado");
        Object idSolicitud = instance.getVariable("idSolicitud");
        context.put("numeroRadicado", numeroRadicado);
        context.put("idSolicitud", idSolicitud);
    }
}
