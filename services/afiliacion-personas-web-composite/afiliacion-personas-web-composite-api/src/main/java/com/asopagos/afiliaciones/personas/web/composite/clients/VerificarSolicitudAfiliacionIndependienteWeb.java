package com.asopagos.afiliaciones.personas.web.composite.clients;

import com.asopagos.afiliaciones.personas.web.composite.dto.VerificarResultadoSolicitudAfiliacionPersonaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/verficarSolicitudAfiliacionIndependienteWeb
 */
public class VerificarSolicitudAfiliacionIndependienteWeb extends ServiceClient { 
    	private VerificarResultadoSolicitudAfiliacionPersonaDTO inDTO;
  
  
 	public VerificarSolicitudAfiliacionIndependienteWeb (VerificarResultadoSolicitudAfiliacionPersonaDTO inDTO){
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
	

 
  
  	public void setInDTO (VerificarResultadoSolicitudAfiliacionPersonaDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public VerificarResultadoSolicitudAfiliacionPersonaDTO getInDTO (){
 		return inDTO;
 	}
  
}