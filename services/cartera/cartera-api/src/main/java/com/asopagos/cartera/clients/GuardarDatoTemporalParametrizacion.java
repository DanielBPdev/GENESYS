package com.asopagos.cartera.clients;

import java.lang.String;
import com.asopagos.enumeraciones.cartera.ParametrizacionEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/parametrizacion/guardarParametrizacion
 */
public class GuardarDatoTemporalParametrizacion extends ServiceClient { 
   	private ParametrizacionEnum parametrizacion;
   	private String jsonPayload;
  
  
 	public GuardarDatoTemporalParametrizacion (ParametrizacionEnum parametrizacion,String jsonPayload){
 		super();
		this.parametrizacion=parametrizacion;
		this.jsonPayload=jsonPayload;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("parametrizacion", parametrizacion)
			.request(MediaType.APPLICATION_JSON)
			.post(jsonPayload == null ? null : Entity.json(jsonPayload));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setParametrizacion (ParametrizacionEnum parametrizacion){
 		this.parametrizacion=parametrizacion;
 	}
 	
 	public ParametrizacionEnum getParametrizacion (){
 		return parametrizacion;
 	}
  
  	public void setJsonPayload (String jsonPayload){
 		this.jsonPayload=jsonPayload;
 	}
 	
 	public String getJsonPayload (){
 		return jsonPayload;
 	}
  
}