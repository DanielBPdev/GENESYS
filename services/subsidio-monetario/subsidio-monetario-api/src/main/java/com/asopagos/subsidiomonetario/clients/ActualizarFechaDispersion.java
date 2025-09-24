package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/subsidioMonetario/actualizarFechaDispersion
 */
public class ActualizarFechaDispersion extends ServiceClient { 
   	private Long idSolicitudLiquidacion;
   
  
 	public ActualizarFechaDispersion (Long idSolicitudLiquidacion){
 		super();
		this.idSolicitudLiquidacion=idSolicitudLiquidacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudLiquidacion", idSolicitudLiquidacion)
			.request(MediaType.APPLICATION_JSON)
			.put(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdSolicitudLiquidacion (Long idSolicitudLiquidacion){
 		this.idSolicitudLiquidacion=idSolicitudLiquidacion;
 	}
 	
 	public Long getIdSolicitudLiquidacion (){
 		return idSolicitudLiquidacion;
 	}
  
  
}