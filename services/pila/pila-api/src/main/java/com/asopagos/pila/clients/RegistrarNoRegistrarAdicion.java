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
 * /rest/pila/registrarNoRegistrarAdicion
 */
public class RegistrarNoRegistrarAdicion extends ServiceClient { 
    	private RegistrarCorreccionAdicionDTO registrarAdicionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public RegistrarNoRegistrarAdicion (RegistrarCorreccionAdicionDTO registrarAdicionDTO){
 		super();
		this.registrarAdicionDTO=registrarAdicionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registrarAdicionDTO == null ? null : Entity.json(registrarAdicionDTO));
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

 
  
  	public void setRegistrarAdicionDTO (RegistrarCorreccionAdicionDTO registrarAdicionDTO){
 		this.registrarAdicionDTO=registrarAdicionDTO;
 	}
 	
 	public RegistrarCorreccionAdicionDTO getRegistrarAdicionDTO (){
 		return registrarAdicionDTO;
 	}
  
}