package com.asopagos.aportes.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/ejecutarArmadoStaging
 */
public class EjecutarArmadoStaging extends ServiceClient { 
   	private Long idTransaccion;
   
  
 	public EjecutarArmadoStaging (Long idTransaccion){
 		super();
		this.idTransaccion=idTransaccion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idTransaccion", idTransaccion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdTransaccion (Long idTransaccion){
 		this.idTransaccion=idTransaccion;
 	}
 	
 	public Long getIdTransaccion (){
 		return idTransaccion;
 	}
  
  
}