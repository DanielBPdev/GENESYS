package com.asopagos.afiliaciones.personas.clients;

import com.asopagos.dto.SolicitudDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliaciones/solicitudAfiliacionPersona/{idSolicitudGlobal}
 */
public class ActualizarSolicitudAfiliacionPersona extends ServiceClient { 
  	private Long idSolicitudGlobal;
    	private SolicitudDTO solicitudDTO;
  
  
 	public ActualizarSolicitudAfiliacionPersona (Long idSolicitudGlobal,SolicitudDTO solicitudDTO){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
		this.solicitudDTO=solicitudDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudGlobal", idSolicitudGlobal)
			.request(MediaType.APPLICATION_JSON)
			.put(solicitudDTO == null ? null : Entity.json(solicitudDTO));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
  
  	public void setSolicitudDTO (SolicitudDTO solicitudDTO){
 		this.solicitudDTO=solicitudDTO;
 	}
 	
 	public SolicitudDTO getSolicitudDTO (){
 		return solicitudDTO;
 	}
  
}