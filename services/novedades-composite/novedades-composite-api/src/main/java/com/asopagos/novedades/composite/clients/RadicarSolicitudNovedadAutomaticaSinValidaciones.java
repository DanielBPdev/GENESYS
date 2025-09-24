package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/radicarSolicitudNovedadAutomaticaSinValidaciones
 */
public class RadicarSolicitudNovedadAutomaticaSinValidaciones extends ServiceClient { 
    	private SolicitudNovedadDTO solNovedadDTO;
  
  
 	public RadicarSolicitudNovedadAutomaticaSinValidaciones (SolicitudNovedadDTO solNovedadDTO){
 		super();
		this.solNovedadDTO=solNovedadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solNovedadDTO == null ? null : Entity.json(solNovedadDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setSolNovedadDTO (SolicitudNovedadDTO solNovedadDTO){
 		this.solNovedadDTO=solNovedadDTO;
 	}
 	
 	public SolicitudNovedadDTO getSolNovedadDTO (){
 		return solNovedadDTO;
 	}
  
}