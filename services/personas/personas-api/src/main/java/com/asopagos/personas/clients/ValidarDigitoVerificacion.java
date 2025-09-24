package com.asopagos.personas.clients;

import java.lang.Boolean;
import com.asopagos.entidades.ccf.personas.Persona;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/validarDigitoVerificacion
 */
public class ValidarDigitoVerificacion extends ServiceClient { 
    	private Persona persona;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ValidarDigitoVerificacion (Persona persona){
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
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  
  	public void setPersona (Persona persona){
 		this.persona=persona;
 	}
 	
 	public Persona getPersona (){
 		return persona;
 	}
  
}