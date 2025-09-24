package com.asopagos.usuarios.clients;

import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/autenticacion/validarTokenAccesoTemporal/{sessionId}
 */
public class ValidarTokenAccesoTemporal extends ServiceClient {
 
  	private String sessionId;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ValidarTokenAccesoTemporal (String sessionId){
 		super();
		this.sessionId=sessionId;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("sessionId", sessionId)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Boolean getResult() {
		return result;
	}

 	public void setSessionId (String sessionId){
 		this.sessionId=sessionId;
 	}
 	
 	public String getSessionId (){
 		return sessionId;
 	}
  
  
}