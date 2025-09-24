package com.asopagos.novedades.clients;

import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/TransaccionNovedadPilaCompleta
 */
public class TransaccionNovedadPilaCompleta extends ServiceClient {
 
  
  	private Long tempId;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public TransaccionNovedadPilaCompleta (Long tempId){
 		super();
		this.tempId=tempId;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tempId", tempId)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Long getResult() {
		return result;
	}

 
  	public void setTempId (Long tempId){
 		this.tempId=tempId;
 	}
 	
 	public Long getTempId (){
 		return tempId;
 	}
  
}