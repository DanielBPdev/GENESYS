package com.asopagos.coreaas.bpm.listeners.task;

import java.util.Map;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.api.task.model.Task;

public class NovedadesArchivosActualizacionTaskEventListener  extends CoreTaskEventListener {

	public NovedadesArchivosActualizacionTaskEventListener(RuntimeManager runtimeManager) {
		super(runtimeManager);
	}

	@Override
	protected void crearContexto(Task taskStart, Map<String, Object> context) {
		//se obtiene el identificador de la solicitud
		WorkflowProcessInstance instance = (WorkflowProcessInstance) ksession.getProcessInstance(
				taskStart.getTaskData().getProcessInstanceId());
		Object idSolicitud = instance.getVariable("idSolicitud");		
		context.put("idSolicitud", idSolicitud);
	}

}
