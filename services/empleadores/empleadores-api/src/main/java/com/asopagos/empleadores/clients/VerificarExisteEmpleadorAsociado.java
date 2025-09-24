package com.asopagos.empleadores.clients;

import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/verificarExisteEmpleadorAsociado
 */
public class VerificarExisteEmpleadorAsociado extends ServiceClient { 
    	private PersonaModeloDTO personaModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EmpleadorModeloDTO result;
  
 	public VerificarExisteEmpleadorAsociado (PersonaModeloDTO personaModeloDTO){
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
		result = (EmpleadorModeloDTO) response.readEntity(EmpleadorModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public EmpleadorModeloDTO getResult() {
		return result;
	}

 
  
  	public void setPersonaModeloDTO (PersonaModeloDTO personaModeloDTO){
 		this.personaModeloDTO=personaModeloDTO;
 	}
 	
 	public PersonaModeloDTO getPersonaModeloDTO (){
 		return personaModeloDTO;
 	}
  
}