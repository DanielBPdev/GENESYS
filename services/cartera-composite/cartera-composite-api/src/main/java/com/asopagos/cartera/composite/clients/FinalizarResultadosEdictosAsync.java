package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.RegistroRemisionAportantesDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraAsincronoComposite/finalizarResultadosEdictos
 */
public class FinalizarResultadosEdictosAsync extends ServiceClient { 
    	private RegistroRemisionAportantesDTO registroDTO;
  
  
 	public FinalizarResultadosEdictosAsync (RegistroRemisionAportantesDTO registroDTO){
 		super();
		this.registroDTO=registroDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registroDTO == null ? null : Entity.json(registroDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRegistroDTO (RegistroRemisionAportantesDTO registroDTO){
 		this.registroDTO=registroDTO;
 	}
 	
 	public RegistroRemisionAportantesDTO getRegistroDTO (){
 		return registroDTO;
 	}
  
}