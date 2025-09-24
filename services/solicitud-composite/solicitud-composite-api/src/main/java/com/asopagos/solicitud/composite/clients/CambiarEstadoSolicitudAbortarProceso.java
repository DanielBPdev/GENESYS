package com.asopagos.solicitud.composite.clients;

import com.asopagos.solicitud.composite.dto.DatosAbortarSolicitudDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudComposite/cambiarEstadoSolicitudAbortarProceso
 */
public class CambiarEstadoSolicitudAbortarProceso extends ServiceClient { 
    	private DatosAbortarSolicitudDTO inDTO;
  
  
 	public CambiarEstadoSolicitudAbortarProceso (DatosAbortarSolicitudDTO inDTO){
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
	

 
  
  	public void setInDTO (DatosAbortarSolicitudDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public DatosAbortarSolicitudDTO getInDTO (){
 		return inDTO;
 	}
  
}