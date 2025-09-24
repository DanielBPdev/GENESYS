package com.asopagos.fovis.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/validarAfiliadosPostulacion
 */
public class ValidarAfiliadosPostulacion extends ServiceClient { 
    	private PersonaModeloDTO personaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PersonaModeloDTO> result;
  
 	public ValidarAfiliadosPostulacion (PersonaModeloDTO personaDTO){
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
		result = (List<PersonaModeloDTO>) response.readEntity(new GenericType<List<PersonaModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<PersonaModeloDTO> getResult() {
		return result;
	}

 
  
  	public void setPersonaDTO (PersonaModeloDTO personaDTO){
 		this.personaDTO=personaDTO;
 	}
 	
 	public PersonaModeloDTO getPersonaDTO (){
 		return personaDTO;
 	}
  
}