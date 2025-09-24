package com.asopagos.processschedule.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.dto.modelo.ParametrizacionEjecucionProgramadaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/processSchedule/consultarProgramacion
 */
public class ConsultarProgramacion extends ServiceClient { 
    	private List<ProcesoAutomaticoEnum> procesos;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ParametrizacionEjecucionProgramadaModeloDTO> result;
  
 	public ConsultarProgramacion (List<ProcesoAutomaticoEnum> procesos){
 		super();
		this.procesos=procesos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(procesos == null ? null : Entity.json(procesos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ParametrizacionEjecucionProgramadaModeloDTO>) response.readEntity(new GenericType<List<ParametrizacionEjecucionProgramadaModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ParametrizacionEjecucionProgramadaModeloDTO> getResult() {
		return result;
	}

 
  
  	public void setProcesos (List<ProcesoAutomaticoEnum> procesos){
 		this.procesos=procesos;
 	}
 	
 	public List<ProcesoAutomaticoEnum> getProcesos (){
 		return procesos;
 	}
  
}