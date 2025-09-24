package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/actualizarSolicitudAporte
 */
public class ActualizarSolicitudAporte extends ServiceClient { 
    	private SolicitudAporteModeloDTO solicitudAporteDTO;
  
  
 	public ActualizarSolicitudAporte (SolicitudAporteModeloDTO solicitudAporteDTO){
 		super();
		this.solicitudAporteDTO=solicitudAporteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudAporteDTO == null ? null : Entity.json(solicitudAporteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setSolicitudAporteDTO (SolicitudAporteModeloDTO solicitudAporteDTO){
 		this.solicitudAporteDTO=solicitudAporteDTO;
 	}
 	
 	public SolicitudAporteModeloDTO getSolicitudAporteDTO (){
 		return solicitudAporteDTO;
 	}
  
}