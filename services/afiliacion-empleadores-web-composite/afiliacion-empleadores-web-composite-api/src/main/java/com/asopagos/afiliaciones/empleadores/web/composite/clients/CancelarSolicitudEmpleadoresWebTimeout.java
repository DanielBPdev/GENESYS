package com.asopagos.afiliaciones.empleadores.web.composite.clients;

import com.asopagos.afiliaciones.empleadores.web.composite.dto.CancelacionSolicitudDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador/cancelarSolicitudTimeout
 */
public class CancelarSolicitudEmpleadoresWebTimeout extends ServiceClient { 
    	private CancelacionSolicitudDTO cancelacion;
  
  
 	public CancelarSolicitudEmpleadoresWebTimeout (CancelacionSolicitudDTO cancelacion){
 		super();
		this.cancelacion=cancelacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(cancelacion == null ? null : Entity.json(cancelacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setCancelacion (CancelacionSolicitudDTO cancelacion){
 		this.cancelacion=cancelacion;
 	}
 	
 	public CancelacionSolicitudDTO getCancelacion (){
 		return cancelacion;
 	}
  
}