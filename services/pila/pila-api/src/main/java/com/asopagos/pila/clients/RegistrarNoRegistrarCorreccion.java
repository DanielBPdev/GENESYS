package com.asopagos.pila.clients;

import java.util.Map;
import java.lang.String;
import com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/registrarNoRegistrarCorreccion
 */
public class RegistrarNoRegistrarCorreccion extends ServiceClient { 
    	private RegistrarCorreccionAdicionDTO registrarCorreccionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public RegistrarNoRegistrarCorreccion (RegistrarCorreccionAdicionDTO registrarCorreccionDTO){
 		super();
		this.registrarCorreccionDTO=registrarCorreccionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registrarCorreccionDTO == null ? null : Entity.json(registrarCorreccionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,String> getResult() {
		return result;
	}

 
  
  	public void setRegistrarCorreccionDTO (RegistrarCorreccionAdicionDTO registrarCorreccionDTO){
 		this.registrarCorreccionDTO=registrarCorreccionDTO;
 	}
 	
 	public RegistrarCorreccionAdicionDTO getRegistrarCorreccionDTO (){
 		return registrarCorreccionDTO;
 	}
  
}