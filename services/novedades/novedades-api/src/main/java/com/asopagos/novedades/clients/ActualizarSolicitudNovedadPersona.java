package com.asopagos.novedades.clients;

import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPersona;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/actualizarSolicitudNovedadPersona
 */
public class ActualizarSolicitudNovedadPersona extends ServiceClient { 
    	private SolicitudNovedadPersona solicitudNovedadPersona;
  
  
 	public ActualizarSolicitudNovedadPersona (SolicitudNovedadPersona solicitudNovedadPersona){
 		super();
		this.solicitudNovedadPersona=solicitudNovedadPersona;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudNovedadPersona == null ? null : Entity.json(solicitudNovedadPersona));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setSolicitudNovedadPersona (SolicitudNovedadPersona solicitudNovedadPersona){
 		this.solicitudNovedadPersona=solicitudNovedadPersona;
 	}
 	
 	public SolicitudNovedadPersona getSolicitudNovedadPersona (){
 		return solicitudNovedadPersona;
 	}
  
}