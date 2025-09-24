package com.asopagos.usuarios.clients;

import com.asopagos.usuarios.dto.TokenDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/autenticacion/generarTokenCore
 */
public class GenerarTokenAccesoCore extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private TokenDTO result;
  
 	public GenerarTokenAccesoCore (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (TokenDTO) response.readEntity(TokenDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public TokenDTO getResult() {
		return result;
	}

 
  
}