package com.asopagos.coreaas.bpm.listeners.task;

import java.util.Map;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.api.task.model.Task;

/**
 * Esta clase adiciona al contexto de las tareas las variables de negocio para el 
 * proceso de Afiliación Empresas Presencial.
 * @author alopez
 *
 */
public class AfiliacionEmpresasPresencialTaskEventListener extends CoreTaskEventListener {

	/**
	 * Constructor por defecto de la clase
	 * @param runtimeManager
	 */
	public AfiliacionEmpresasPresencialTaskEventListener(RuntimeManager runtimeManager) {
		super(runtimeManager);
	}

	/**
	 * Adiciona al contexto del proceso el identificador de la solicitud
	 * de afiliación.
	 */
	@Override
	protected void crearContexto(Task taskStart,Map<String, Object> context) {
		//se obtiene el identificador de la solicitud
		WorkflowProcessInstance instance = (WorkflowProcessInstance) ksession.getProcessInstance(
				taskStart.getTaskData().getProcessInstanceId());
		Object idSolicitud=instance.getVariable("idSolicitud");
		Object usuarioRadicador=instance.getVariable("usuarioRadicador");
		context.put("idSolicitud", idSolicitud);
		context.put("usuarioRadicador", usuarioRadicador);
	}

}
