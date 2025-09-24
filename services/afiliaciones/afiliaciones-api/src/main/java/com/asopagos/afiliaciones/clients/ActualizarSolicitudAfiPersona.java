package com.asopagos.afiliaciones.clients;

import com.asopagos.dto.modelo.SolicitudAfiliacionPersonaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliaciones/actualizarSolicitudAfiliacionPersona
 */
public class ActualizarSolicitudAfiPersona extends ServiceClient { 
    	private SolicitudAfiliacionPersonaModeloDTO solicitudAfiliacionDTO;
  
  
 	public ActualizarSolicitudAfiPersona (SolicitudAfiliacionPersonaModeloDTO solicitudAfiliacionDTO){
 		super();
		this.solicitudAfiliacionDTO=solicitudAfiliacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudAfiliacionDTO == null ? null : Entity.json(solicitudAfiliacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setSolicitudAfiliacionDTO (SolicitudAfiliacionPersonaModeloDTO solicitudAfiliacionDTO){
 		this.solicitudAfiliacionDTO=solicitudAfiliacionDTO;
 	}
 	
 	public SolicitudAfiliacionPersonaModeloDTO getSolicitudAfiliacionDTO (){
 		return solicitudAfiliacionDTO;
 	}
  
}