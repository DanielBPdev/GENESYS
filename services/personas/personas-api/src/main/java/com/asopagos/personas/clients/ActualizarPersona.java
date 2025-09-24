package com.asopagos.personas.clients;

import com.asopagos.entidades.ccf.personas.Persona;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas
 */
public class ActualizarPersona extends ServiceClient { 
    	private Persona persona;
  
  
 	public ActualizarPersona (Persona persona){
 		super();
		this.persona=persona;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(persona == null ? null : Entity.json(persona));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setPersona (Persona persona){
 		this.persona=persona;
 	}
 	
 	public Persona getPersona (){
 		return persona;
 	}
  
}