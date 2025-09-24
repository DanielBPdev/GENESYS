package com.asopagos.novedades.clients;

import com.asopagos.dto.modelo.PersonaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/consultarRegistroPersonaInconsistente
 */
public class ConsultarRegistroPersonaInconsistente extends ServiceClient { 
    	private PersonaModeloDTO persona;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private boolean result;
  
 	public ConsultarRegistroPersonaInconsistente (PersonaModeloDTO persona){
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
		result = (boolean) response.readEntity(boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public boolean getResult() {
		return result;
	}

 
  
  	public void setPersona (PersonaModeloDTO persona){
 		this.persona=persona;
 	}
 	
 	public PersonaModeloDTO getPersona (){
 		return persona;
 	}
  
}