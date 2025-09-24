package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/ejecutarSPGestionarColaEjecucionLiquidacion
 */
public class EjecutarSPGestionarColaEjecucionLiquidacion extends ServiceClient { 
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<String> result;
  
 	public EjecutarSPGestionarColaEjecucionLiquidacion (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<String>) response.readEntity(new GenericType<List<String>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<String> getResult() {
		return result;
	}

 
  
  
}