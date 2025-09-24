package com.asopagos.coreaas.bpm.listeners.task;

import java.util.Map;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.api.task.model.Task;

public class GestionCarteraFisicaDetalladaTaskEventListener extends CoreTaskEventListener {

	public GestionCarteraFisicaDetalladaTaskEventListener(RuntimeManager runtimeManager) {
		super(runtimeManager);
	}

	@Override
	protected void crearContexto(Task task, Map<String, Object> context) {
		// se obtiene el identificador de la solicitud
		WorkflowProcessInstance instance = (WorkflowProcessInstance) ksession
				.getProcessInstance(task.getTaskData().getProcessInstanceId());
		Object numeroSolicitud = instance.getVariable("numeroSolicitud");
		context.put("numeroSolicitud", numeroSolicitud);
		Object accionCobro = instance.getVariable("accionCobro");
        context.put("accionCobro", accionCobro);
	}

}
