package com.asopagos.solicitud.composite.clients;

import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudComposite/cambiarEstadoSolicitudFinalizarGestion
 */
public class CambiarEstadoSolicitudFinalizarGestion extends ServiceClient { 
    	private CambiarEstadoSolicitudFinGestionDTO inDTO;
  
  
 	public CambiarEstadoSolicitudFinalizarGestion (CambiarEstadoSolicitudFinGestionDTO inDTO){
 		super();
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setInDTO (CambiarEstadoSolicitudFinGestionDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public CambiarEstadoSolicitudFinGestionDTO getInDTO (){
 		return inDTO;
 	}
  
}