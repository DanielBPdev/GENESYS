package com.asopagos.aportes.composite.clients;

import com.asopagos.aportes.composite.dto.EvaluacionSupervisorDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/finalizarEvaluacionSupervisor
 */
public class FinalizarEvaluacionSupervisor extends ServiceClient { 
    	private EvaluacionSupervisorDTO evaluacionSupervisorDTO;
  
  
 	public FinalizarEvaluacionSupervisor (EvaluacionSupervisorDTO evaluacionSupervisorDTO){
 		super();
		this.evaluacionSupervisorDTO=evaluacionSupervisorDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(evaluacionSupervisorDTO == null ? null : Entity.json(evaluacionSupervisorDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEvaluacionSupervisorDTO (EvaluacionSupervisorDTO evaluacionSupervisorDTO){
 		this.evaluacionSupervisorDTO=evaluacionSupervisorDTO;
 	}
 	
 	public EvaluacionSupervisorDTO getEvaluacionSupervisorDTO (){
 		return evaluacionSupervisorDTO;
 	}
  
}