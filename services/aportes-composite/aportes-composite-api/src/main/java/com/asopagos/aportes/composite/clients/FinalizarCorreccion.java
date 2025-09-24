package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/{idSolicitud}/finalizarCorreccion
 */
public class FinalizarCorreccion extends ServiceClient { 
  	private Long idSolicitud;
   	private Long idTarea;
  	private Long instaciaProceso;
   
  
 	public FinalizarCorreccion (Long idSolicitud,Long idTarea,Long instaciaProceso){
 		super();
		this.idSolicitud=idSolicitud;
		this.idTarea=idTarea;
		this.instaciaProceso=instaciaProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitud", idSolicitud)
			.queryParam("idTarea", idTarea)
			.queryParam("instaciaProceso", instaciaProceso)
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
  
  	public void setIdTarea (Long idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public Long getIdTarea (){
 		return idTarea;
 	}
  	public void setInstaciaProceso (Long instaciaProceso){
 		this.instaciaProceso=instaciaProceso;
 	}
 	
 	public Long getInstaciaProceso (){
 		return instaciaProceso;
 	}
  
  
}