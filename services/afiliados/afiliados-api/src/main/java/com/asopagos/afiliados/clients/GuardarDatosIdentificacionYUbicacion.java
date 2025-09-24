package com.asopagos.afiliados.clients;

import com.asopagos.dto.IdentificacionUbicacionPersonaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/datosIdentificacionYUbicacion
 */
public class GuardarDatosIdentificacionYUbicacion extends ServiceClient { 
    	private IdentificacionUbicacionPersonaDTO inDTO;
  
  
 	public GuardarDatosIdentificacionYUbicacion (IdentificacionUbicacionPersonaDTO inDTO){
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
	

 
  
  	public void setInDTO (IdentificacionUbicacionPersonaDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public IdentificacionUbicacionPersonaDTO getInDTO (){
 		return inDTO;
 	}
  
}