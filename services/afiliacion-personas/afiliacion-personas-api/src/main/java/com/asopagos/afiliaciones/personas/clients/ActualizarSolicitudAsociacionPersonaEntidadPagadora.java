package com.asopagos.afiliaciones.personas.clients;

import com.asopagos.dto.SolicitudAsociacionPersonaEntidadPagadoraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliaciones/solicitudAsociacionPersona/actualiza
 */
public class ActualizarSolicitudAsociacionPersonaEntidadPagadora extends ServiceClient { 
    	private SolicitudAsociacionPersonaEntidadPagadoraDTO inDTO;
  
  
 	public ActualizarSolicitudAsociacionPersonaEntidadPagadora (SolicitudAsociacionPersonaEntidadPagadoraDTO inDTO){
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
	

 
  
  	public void setInDTO (SolicitudAsociacionPersonaEntidadPagadoraDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public SolicitudAsociacionPersonaEntidadPagadoraDTO getInDTO (){
 		return inDTO;
 	}
  
}