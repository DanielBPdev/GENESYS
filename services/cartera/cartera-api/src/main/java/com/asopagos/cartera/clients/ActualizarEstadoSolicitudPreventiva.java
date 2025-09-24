package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.EstadoSolicitudPreventivaEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/actualizarEstadoSolicitudPreventiva/{numeroRadicacion}
 */
public class ActualizarEstadoSolicitudPreventiva extends ServiceClient { 
  	private String numeroRadicacion;
   	private EstadoSolicitudPreventivaEnum estadoSolicitud;
   
  
 	public ActualizarEstadoSolicitudPreventiva (String numeroRadicacion,EstadoSolicitudPreventivaEnum estadoSolicitud){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.estadoSolicitud=estadoSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.queryParam("estadoSolicitud", estadoSolicitud)
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
  
  	public void setEstadoSolicitud (EstadoSolicitudPreventivaEnum estadoSolicitud){
 		this.estadoSolicitud=estadoSolicitud;
 	}
 	
 	public EstadoSolicitudPreventivaEnum getEstadoSolicitud (){
 		return estadoSolicitud;
 	}
  
  
}