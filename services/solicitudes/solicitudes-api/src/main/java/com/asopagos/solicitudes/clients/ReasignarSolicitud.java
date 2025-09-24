package com.asopagos.solicitudes.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudes/{idProceso}/reasignar
 */
public class ReasignarSolicitud extends ServiceClient { 
  	private Long idProceso;
   	private String usuarioNuevo;
  	private String usuarioActual;
   
  
 	public ReasignarSolicitud (Long idProceso,String usuarioNuevo,String usuarioActual){
 		super();
		this.idProceso=idProceso;
		this.usuarioNuevo=usuarioNuevo;
		this.usuarioActual=usuarioActual;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
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