package com.asopagos.aportes.composite.clients;

import com.asopagos.enumeraciones.aportes.ActividadEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/{idSolicitud}/actualizarComunicadoTrazabilidad
 */
public class ActualizarComunicadoTrazabilidad extends ServiceClient { 
  	private Long idSolicitud;
   	private ActividadEnum actividad;
   
  
 	public ActualizarComunicadoTrazabilidad (Long idSolicitud,ActividadEnum actividad){
 		super();
		this.idSolicitud=idSolicitud;
		this.actividad=actividad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitud", idSolicitud)
			.queryParam("actividad", actividad)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  	public void setActividad (ActividadEnum actividad){
 		this.actividad=actividad;
 	}
 	
 	public ActividadEnum getActividad (){
 		return actividad;
 	}
  
  
}