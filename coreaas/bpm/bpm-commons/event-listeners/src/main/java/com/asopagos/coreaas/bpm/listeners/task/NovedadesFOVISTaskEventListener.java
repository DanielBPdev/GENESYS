package com.asopagos.coreaas.bpm.listeners.task;

import java.util.Map;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.api.task.model.Task;

/**
 * Esta clase adiciona al contexto de las tareas las variables de negocio para el
 * proceso de Registro Novedad FOVIS
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class NovedadesFOVISTaskEventListener extends CoreTaskEventListener {

    /**
     * Constructor por defecto de la clase
     * @param runtimeManager
     */
    public NovedadesFOVISTaskEventListener(RuntimeManager runtimeManager) {
        super(runtimeManager);
    }

    /**
     * Adiciona al contexto del proceso el identificador de la solicitud
     * de Registro Novedad FOVIS.
     */
    @Override
    protected void crearContexto(Task taskStart, Map<String, Object> context) {
        //se obtiene el identificador de la solicitud
        WorkflowProcessInstance instance = (WorkflowProcessInstance) ksession
                .getProcessInstance(taskStart.getTaskData().getProcessInstanceId());
        Object idSolicitud = instance.getVariable("idSolicitud");
        context.put("idSolicitud", idSolicitud);
    }

}
