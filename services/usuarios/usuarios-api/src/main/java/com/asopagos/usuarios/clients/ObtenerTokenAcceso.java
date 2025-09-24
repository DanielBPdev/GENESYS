package com.asopagos.usuarios.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/autenticacion/obtenerTokenAcceso
 */
public class ObtenerTokenAcceso extends ServiceClient { 
   	private String refreshToken;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public ObtenerTokenAcceso (String refreshToken){
 		super();
		this.refreshToken=refreshToken;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("refreshToken", refreshToken)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public String getResult() {
		return result;
	}

 
  	public void setRefreshToken (String refreshToken){
 		this.refreshToken=refreshToken;
 	}
 	
 	public String getRefreshToken (){
 		return refreshToken;
 	}
  
  
}