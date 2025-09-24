package com.asopagos.afiliaciones.personas.web.composite.clients;

import com.asopagos.afiliaciones.personas.web.composite.dto.CancelacionSolicitudPersonasDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/cancelarSolicitudTimeout
 */
public class CancelarSolicitudPersonasWebTimeout extends ServiceClient { 
    	private CancelacionSolicitudPersonasDTO cancelacion;
  
  
 	public CancelarSolicitudPersonasWebTimeout (CancelacionSolicitudPersonasDTO cancelacion){
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
	

 
  
  	public void setCancelacion (CancelacionSolicitudPersonasDTO cancelacion){
 		this.cancelacion=cancelacion;
 	}
 	
 	public CancelacionSolicitudPersonasDTO getCancelacion (){
 		return cancelacion;
 	}
  
}