package com.asopagos.personas.clients;

import com.asopagos.dto.modelo.PersonaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/actualizarDatosPersona
 */
public class ActualizarDatosPersona extends ServiceClient { 
    	private PersonaModeloDTO personaDTO;
  
  
 	public ActualizarDatosPersona (PersonaModeloDTO personaDTO){
 		super();
		this.personaDTO=personaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(personaDTO == null ? null : Entity.json(personaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setPersonaDTO (PersonaModeloDTO personaDTO){
 		this.personaDTO=personaDTO;
 	}
 	
 	public PersonaModeloDTO getPersonaDTO (){
 		return personaDTO;
 	}
  
}