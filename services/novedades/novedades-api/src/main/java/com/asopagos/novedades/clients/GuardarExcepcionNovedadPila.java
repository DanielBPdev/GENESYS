package com.asopagos.novedades.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/novedades/GuardarExcepcionNovedadPila
 */
public class GuardarExcepcionNovedadPila extends ServiceClient { 
   	private Long tempNovedadId;
  	private String excepcion;
   
  
 	public GuardarExcepcionNovedadPila (Long tempNovedadId,String excepcion){
 		super();
		this.tempNovedadId=tempNovedadId;
		this.excepcion=excepcion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tempNovedadId", tempNovedadId)
			.queryParam("excepcion", excepcion)
			.request(MediaType.APPLICATION_JSON)
			.put(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setTempNovedadId (Long tempNovedadId){
 		this.tempNovedadId=tempNovedadId;
 	}
 	
 	public Long getTempNovedadId (){
 		return tempNovedadId;
 	}
  	public void setExcepcion (String excepcion){
 		this.excepcion=excepcion;
 	}
 	
 	public String getExcepcion (){
 		return excepcion;
 	}
  
  
}