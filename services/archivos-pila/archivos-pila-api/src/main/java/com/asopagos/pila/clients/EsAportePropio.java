package com.asopagos.pila.clients;

import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/archivosPILA/esAportePropio
 */
public class EsAportePropio extends ServiceClient {
 
  
  	private Long id;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public EsAportePropio (Long id){
 		super();
		this.id=id;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("id", id)
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

 
  	public void setId (Long id){
 		this.id=id;
 	}
 	
 	public Long getId (){
 		return id;
 	}
  
}