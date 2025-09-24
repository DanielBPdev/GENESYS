package com.asopagos.cartera.clients;

import java.lang.String;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudDesafiliacionEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/{numeroRadicacion}/actualizarEstadoSolicitudDesafiliacion
 */
public class ActualizarEstadoSolicitudDesafiliacion extends ServiceClient { 
  	private String numeroRadicacion;
   	private EstadoSolicitudDesafiliacionEnum estadoDesafiliacion;
   
  
 	public ActualizarEstadoSolicitudDesafiliacion (String numeroRadicacion,EstadoSolicitudDesafiliacionEnum estadoDesafiliacion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.estadoDesafiliacion=estadoDesafiliacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.queryParam("estadoDesafiliacion", estadoDesafiliacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  	public void setEstadoDesafiliacion (EstadoSolicitudDesafiliacionEnum estadoDesafiliacion){
 		this.estadoDesafiliacion=estadoDesafiliacion;
 	}
 	
 	public EstadoSolicitudDesafiliacionEnum getEstadoDesafiliacion (){
 		return estadoDesafiliacion;
 	}
  
  
}