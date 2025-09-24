package com.asopagos.novedades.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudNovedadModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/novedades/actualizarSolicitudNovedad
 */
public class ActualizarSolicitudNovedad extends ServiceClient { 
   	private Long idSolicitudNovedad;
   	private SolicitudNovedadModeloDTO solicitudNovedad;
  
  
 	public ActualizarSolicitudNovedad (Long idSolicitudNovedad,SolicitudNovedadModeloDTO solicitudNovedad){
 		super();
		this.idSolicitudNovedad=idSolicitudNovedad;
		this.solicitudNovedad=solicitudNovedad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudNovedad", idSolicitudNovedad)
			.request(MediaType.APPLICATION_JSON)
			.put(solicitudNovedad == null ? null : Entity.json(solicitudNovedad));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdSolicitudNovedad (Long idSolicitudNovedad){
 		this.idSolicitudNovedad=idSolicitudNovedad;
 	}
 	
 	public Long getIdSolicitudNovedad (){
 		return idSolicitudNovedad;
 	}
  
  	public void setSolicitudNovedad (SolicitudNovedadModeloDTO solicitudNovedad){
 		this.solicitudNovedad=solicitudNovedad;
 	}
 	
 	public SolicitudNovedadModeloDTO getSolicitudNovedad (){
 		return solicitudNovedad;
 	}
  
}