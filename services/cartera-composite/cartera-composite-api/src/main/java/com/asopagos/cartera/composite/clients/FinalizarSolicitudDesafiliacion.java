package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.modelo.DesafiliacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/finalizarSolicitudDesafiliacion
 */
public class FinalizarSolicitudDesafiliacion extends ServiceClient { 
    	private DesafiliacionDTO desafiliacionDTO;
  
  
 	public FinalizarSolicitudDesafiliacion (DesafiliacionDTO desafiliacionDTO){
 		super();
		this.desafiliacionDTO=desafiliacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(desafiliacionDTO == null ? null : Entity.json(desafiliacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDesafiliacionDTO (DesafiliacionDTO desafiliacionDTO){
 		this.desafiliacionDTO=desafiliacionDTO;
 	}
 	
 	public DesafiliacionDTO getDesafiliacionDTO (){
 		return desafiliacionDTO;
 	}
  
}