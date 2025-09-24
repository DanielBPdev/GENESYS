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
 * /rest/aporteManual/{idSolicitud}/actualizarComunicadoTrazabilidadAporte
 */
public class ActualizarComunicadoTrazabilidadAporte extends ServiceClient { 
  	private Long idSolicitud;
   	private ActividadEnum actividad;
  	private Long idComunicado;
   
  
 	public ActualizarComunicadoTrazabilidadAporte (Long idSolicitud,ActividadEnum actividad,Long idComunicado){
 		super();
		this.idSolicitud=idSolicitud;
		this.actividad=actividad;
		this.idComunicado=idComunicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitud", idSolicitud)
			.queryParam("actividad", actividad)
			.queryParam("idComunicado", idComunicado)
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
  	public void setIdComunicado (Long idComunicado){
 		this.idComunicado=idComunicado;
 	}
 	
 	public Long getIdComunicado (){
 		return idComunicado;
 	}
  
  
}