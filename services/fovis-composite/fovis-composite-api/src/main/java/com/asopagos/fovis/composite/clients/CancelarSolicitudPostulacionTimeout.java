package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.CancelacionSolicitudPostulacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/cancelarSolicitudPostulacionTimeout
 */
public class CancelarSolicitudPostulacionTimeout extends ServiceClient { 
    	private CancelacionSolicitudPostulacionDTO cancelacion;
  
  
 	public CancelarSolicitudPostulacionTimeout (CancelacionSolicitudPostulacionDTO cancelacion){
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
	

 
  
  	public void setCancelacion (CancelacionSolicitudPostulacionDTO cancelacion){
 		this.cancelacion=cancelacion;
 	}
 	
 	public CancelacionSolicitudPostulacionDTO getCancelacion (){
 		return cancelacion;
 	}
  
}