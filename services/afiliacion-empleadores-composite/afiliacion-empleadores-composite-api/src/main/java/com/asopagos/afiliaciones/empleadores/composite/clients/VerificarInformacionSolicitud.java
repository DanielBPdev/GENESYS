package com.asopagos.afiliaciones.empleadores.composite.clients;

import com.asopagos.afiliaciones.empleadores.composite.dto.VerificarInformacionSolicitudDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador/verificarInformacionSolicitud
 */
public class VerificarInformacionSolicitud extends ServiceClient { 
    	private VerificarInformacionSolicitudDTO inDTO;
  
  
 	public VerificarInformacionSolicitud (VerificarInformacionSolicitudDTO inDTO){
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
	

 
  
  	public void setInDTO (VerificarInformacionSolicitudDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public VerificarInformacionSolicitudDTO getInDTO (){
 		return inDTO;
 	}
  
}