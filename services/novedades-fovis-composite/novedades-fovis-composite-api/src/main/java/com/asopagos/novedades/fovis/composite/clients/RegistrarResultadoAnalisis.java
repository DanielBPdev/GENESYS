package com.asopagos.novedades.fovis.composite.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovisComposite/registrarResultadoAnalisis
 */
public class RegistrarResultadoAnalisis extends ServiceClient { 
   	private Long idTarea;
  	private Long idSolicitud;
   	private String observaciones;
  
  
 	public RegistrarResultadoAnalisis (Long idTarea,Long idSolicitud,String observaciones){
 		super();
		this.idTarea=idTarea;
		this.idSolicitud=idSolicitud;
		this.observaciones=observaciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idTarea", idTarea)
			.queryParam("idSolicitud", idSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.post(observaciones == null ? null : Entity.json(observaciones));
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
  	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  	public void setObservaciones (String observaciones){
 		this.observaciones=observaciones;
 	}
 	
 	public String getObservaciones (){
 		return observaciones;
 	}
  
}