package com.asopagos.tareashumanas.clients;

import java.lang.Long;
import java.util.Map;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/tareasHumanas/{idTarea}/suspender
 */
public class SuspenderTarea extends ServiceClient { 
  	private Long idTarea;
    	private Map<String,Object> params;
  
  
 	public SuspenderTarea (Long idTarea,Map<String,Object> params){
 		super();
		this.idTarea=idTarea;
		this.params=params;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idTarea", idTarea)
			.request(MediaType.APPLICATION_JSON)
			.post(params == null ? null : Entity.json(params));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdTarea (Long idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public Long getIdTarea (){
 		return idTarea;
 	}
  
  
  	public void setParams (Map<String,Object> params){
 		this.params=params;
 	}
 	
 	public Map<String,Object> getParams (){
 		return params;
 	}
  
}