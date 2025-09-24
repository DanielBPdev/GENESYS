package com.asopagos.archivos.clients;

import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio DELETE
 * /rest/archivos/eliminarArchivo/{identificadorArchivo}
 */
public class EliminarArchivo extends ServiceClient {
 
  	private String identificadorArchivo;
  
  
  
 	public EliminarArchivo (String identificadorArchivo){
 		super();
		this.identificadorArchivo=identificadorArchivo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("identificadorArchivo", identificadorArchivo)
									.request(MediaType.APPLICATION_JSON).delete();
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdentificadorArchivo (String identificadorArchivo){
 		this.identificadorArchivo=identificadorArchivo;
 	}
 	
 	public String getIdentificadorArchivo (){
 		return identificadorArchivo;
 	}
  
  
}