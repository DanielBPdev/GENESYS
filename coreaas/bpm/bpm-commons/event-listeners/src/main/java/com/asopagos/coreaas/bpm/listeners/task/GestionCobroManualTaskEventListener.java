package com.asopagos.coreaas.bpm.listeners.task;

import java.util.Map;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.api.task.model.Task;

public class GestionCobroManualTaskEventListener extends CoreTaskEventListener {

	public GestionCobroManualTaskEventListener(RuntimeManager runtimeManager) {
		super(runtimeManager);
	}

	@Override
	protected void crearContexto(Task task, Map<String, Object> context) {
		// se obtiene el identificador de la solicitud
		WorkflowProcessInstance instance = (WorkflowProcessInstance) ksession
				.getProcessInstance(task.getTaskData().getProcessInstanceId());
		Object numeroSolicitud = instance.getVariable("idSolicitud");
		context.put("idSolicitud", numeroSolicitud);
	}

}
