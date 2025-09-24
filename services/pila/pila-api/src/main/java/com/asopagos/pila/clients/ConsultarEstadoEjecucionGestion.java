package com.asopagos.pila.clients;

import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/consultarEstadoEjecucionGestion
 */
public class ConsultarEstadoEjecucionGestion extends ServiceClient {
 
  
  	private String proceso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ConsultarEstadoEjecucionGestion (String proceso){
 		super();
		this.proceso=proceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("proceso", proceso)
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

 
  	public void setProceso (String proceso){
 		this.proceso=proceso;
 	}
 	
 	public String getProceso (){
 		return proceso;
 	}
  
}