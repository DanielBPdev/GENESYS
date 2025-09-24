package com.asopagos.aportes.composite.clients;

import com.asopagos.aportes.composite.dto.EvaluacionAnalistaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/finalizarEvaluacionAnalista
 */
public class FinalizarEvaluacionAnalista extends ServiceClient { 
    	private EvaluacionAnalistaDTO evaluacionAnalista;
  
  
 	public FinalizarEvaluacionAnalista (EvaluacionAnalistaDTO evaluacionAnalista){
 		super();
		this.evaluacionAnalista=evaluacionAnalista;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(evaluacionAnalista == null ? null : Entity.json(evaluacionAnalista));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEvaluacionAnalista (EvaluacionAnalistaDTO evaluacionAnalista){
 		this.evaluacionAnalista=evaluacionAnalista;
 	}
 	
 	public EvaluacionAnalistaDTO getEvaluacionAnalista (){
 		return evaluacionAnalista;
 	}
  
}