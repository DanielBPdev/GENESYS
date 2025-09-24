package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.RegistroRemisionAportantesDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/guardarSegundaRemision
 */
public class GuardarSegundaRemision extends ServiceClient { 
    	private RegistroRemisionAportantesDTO registroRemision;
  
  
 	public GuardarSegundaRemision (RegistroRemisionAportantesDTO registroRemision){
 		super();
		this.registroRemision=registroRemision;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registroRemision == null ? null : Entity.json(registroRemision));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRegistroRemision (RegistroRemisionAportantesDTO registroRemision){
 		this.registroRemision=registroRemision;
 	}
 	
 	public RegistroRemisionAportantesDTO getRegistroRemision (){
 		return registroRemision;
 	}
  
}