package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/registrarRetiroAutomaticoPorFallecimiento
 */
public class RegistrarRetiroAutomaticoPorFallecimiento extends ServiceClient { 
    	private SolicitudNovedadDTO solicitudNovedad;
  
  
 	public RegistrarRetiroAutomaticoPorFallecimiento (SolicitudNovedadDTO solicitudNovedad){
 		super();
		this.solicitudNovedad=solicitudNovedad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudNovedad == null ? null : Entity.json(solicitudNovedad));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setSolicitudNovedad (SolicitudNovedadDTO solicitudNovedad){
 		this.solicitudNovedad=solicitudNovedad;
 	}
 	
 	public SolicitudNovedadDTO getSolicitudNovedad (){
 		return solicitudNovedad;
 	}
  
}