package com.asopagos.listas.clients;

import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/listasValores/pruebaCacheDos
 */
public class PruebaCacheDos extends ServiceClient {
 
  
  	private String key;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Object result;
  
 	public PruebaCacheDos (String key){
 		super();
		this.key=key;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("key", key)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Object) response.readEntity(Object.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Object getResult() {
		return result;
	}

 
  	public void setKey (String key){
 		this.key=key;
 	}
 	
 	public String getKey (){
 		return key;
 	}
  
}