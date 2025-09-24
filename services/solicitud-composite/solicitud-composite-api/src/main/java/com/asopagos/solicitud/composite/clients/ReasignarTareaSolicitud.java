package com.asopagos.solicitud.composite.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudComposite/{idProceso}/{idTarea}/reasignar
 */
public class ReasignarTareaSolicitud extends ServiceClient { 
  	private Long idTarea;
  	private Long idProceso;
   	private String usuarioNuevo;
  	private String usuarioActual;
   
  
 	public ReasignarTareaSolicitud (Long idTarea,Long idProceso,String usuarioNuevo,String usuarioActual){
 		super();
		this.idTarea=idTarea;
		this.idProceso=idProceso;
		this.usuarioNuevo=usuarioNuevo;
		this.usuarioActual=usuarioActual;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idTarea", idTarea)
			.resolveTemplate("idProceso", idProceso)
			.queryParam("usuarioNuevo", usuarioNuevo)
			.queryParam("usuarioActual", usuarioActual)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
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
  	public void setIdProceso (Long idProceso){
 		this.idProceso=idProceso;
 	}
 	
 	public Long getIdProceso (){
 		return idProceso;
 	}
  
  	public void setUsuarioNuevo (String usuarioNuevo){
 		this.usuarioNuevo=usuarioNuevo;
 	}
 	
 	public String getUsuarioNuevo (){
 		return usuarioNuevo;
 	}
  	public void setUsuarioActual (String usuarioActual){
 		this.usuarioActual=usuarioActual;
 	}
 	
 	public String getUsuarioActual (){
 		return usuarioActual;
 	}
  
  
}