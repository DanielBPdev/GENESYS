package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.PersonaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/InsertarPersonaCartera
 */
public class InsertarPersonaCartera extends ServiceClient { 
    	private PersonaModeloDTO personaModeloDTO;
  
  
 	public InsertarPersonaCartera (PersonaModeloDTO personaModeloDTO){
 		super();
		this.personaModeloDTO=personaModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(personaModeloDTO == null ? null : Entity.json(personaModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setPersonaModeloDTO (PersonaModeloDTO personaModeloDTO){
 		this.personaModeloDTO=personaModeloDTO;
 	}
 	
 	public PersonaModeloDTO getPersonaModeloDTO (){
 		return personaModeloDTO;
 	}
  
}