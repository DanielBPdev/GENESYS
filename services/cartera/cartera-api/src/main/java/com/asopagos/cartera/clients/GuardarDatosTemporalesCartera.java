package com.asopagos.cartera.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarDatosTemporalesCartera
 */
public class GuardarDatosTemporalesCartera extends ServiceClient { 
   	private Long numeroOperacion;
   	private String jsonPayload;
  
  
 	public GuardarDatosTemporalesCartera (Long numeroOperacion,String jsonPayload){
 		super();
		this.numeroOperacion=numeroOperacion;
		this.jsonPayload=jsonPayload;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("numeroOperacion", numeroOperacion)
			.request(MediaType.APPLICATION_JSON)
			.post(jsonPayload == null ? null : Entity.json(jsonPayload));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setNumeroOperacion (Long numeroOperacion){
 		this.numeroOperacion=numeroOperacion;
 	}
 	
 	public Long getNumeroOperacion (){
 		return numeroOperacion;
 	}
  
  	public void setJsonPayload (String jsonPayload){
 		this.jsonPayload=jsonPayload;
 	}
 	
 	public String getJsonPayload (){
 		return jsonPayload;
 	}
  
}