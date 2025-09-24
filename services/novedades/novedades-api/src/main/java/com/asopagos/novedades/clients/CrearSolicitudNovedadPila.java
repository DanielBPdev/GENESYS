package com.asopagos.novedades.clients;

import com.asopagos.dto.modelo.SolicitudNovedadPilaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/crearSolicitudNovedadPila
 */
public class CrearSolicitudNovedadPila extends ServiceClient { 
    	private SolicitudNovedadPilaModeloDTO solicitudNovedadPila;
  
  
 	public CrearSolicitudNovedadPila (SolicitudNovedadPilaModeloDTO solicitudNovedadPila){
 		super();
		this.solicitudNovedadPila=solicitudNovedadPila;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudNovedadPila == null ? null : Entity.json(solicitudNovedadPila));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setSolicitudNovedadPila (SolicitudNovedadPilaModeloDTO solicitudNovedadPila){
 		this.solicitudNovedadPila=solicitudNovedadPila;
 	}
 	
 	public SolicitudNovedadPilaModeloDTO getSolicitudNovedadPila (){
 		return solicitudNovedadPila;
 	}
  
}