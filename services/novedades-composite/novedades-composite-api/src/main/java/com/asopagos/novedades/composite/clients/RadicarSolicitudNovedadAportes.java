package com.asopagos.novedades.composite.clients;

import com.asopagos.dto.aportes.NovedadAportesDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/radicarSolicitudNovedadAportes
 */
public class RadicarSolicitudNovedadAportes extends ServiceClient { 
    	private NovedadAportesDTO novedadAportesDTO;
  
  
 	public RadicarSolicitudNovedadAportes (NovedadAportesDTO novedadAportesDTO){
 		super();
		this.novedadAportesDTO=novedadAportesDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(novedadAportesDTO == null ? null : Entity.json(novedadAportesDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setNovedadAportesDTO (NovedadAportesDTO novedadAportesDTO){
 		this.novedadAportesDTO=novedadAportesDTO;
 	}
 	
 	public NovedadAportesDTO getNovedadAportesDTO (){
 		return novedadAportesDTO;
 	}
  
}