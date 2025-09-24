package com.asopagos.usuarios.clients;

import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/autenticacion
 */
public class ValidarCredencialesUsuario extends ServiceClient { 
   	private String userName;
  	private String password;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ValidarCredencialesUsuario (String userName,String password){
 		super();
		this.userName=userName;
		this.password=password;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("userName", userName)
			.queryParam("password", password)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
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

 
  	public void setUserName (String userName){
 		this.userName=userName;
 	}
 	
 	public String getUserName (){
 		return userName;
 	}
  	public void setPassword (String password){
 		this.password=password;
 	}
 	
 	public String getPassword (){
 		return password;
 	}
  
  
}