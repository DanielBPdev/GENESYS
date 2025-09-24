package com.asopagos.personas.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/crearPersona
 */
public class CrearPersona extends ServiceClient { 
    	private PersonaModeloDTO personaModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearPersona (PersonaModeloDTO personaModeloDTO){
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
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setPersonaModeloDTO (PersonaModeloDTO personaModeloDTO){
 		this.personaModeloDTO=personaModeloDTO;
 	}
 	
 	public PersonaModeloDTO getPersonaModeloDTO (){
 		return personaModeloDTO;
 	}
  
}