package com.asopagos.pila.clients;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/validarEstadoEjecucion410Automatico
 */
public class ValidarEstadoEjecucion410Automatico extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private boolean result;
  
 	public ValidarEstadoEjecucion410Automatico (){
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
		this.result = (boolean) response.readEntity(boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public boolean getResult() {
		return result;
	}

 
  
}