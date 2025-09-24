package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.NovedadDetalleModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/actualizarNovedadPila
 */
public class ActualizarNovedadPila extends ServiceClient { 
    	private NovedadDetalleModeloDTO novedadPilaModeloDTO;
  
  
 	public ActualizarNovedadPila (NovedadDetalleModeloDTO novedadPilaModeloDTO){
 		super();
		this.novedadPilaModeloDTO=novedadPilaModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(novedadPilaModeloDTO == null ? null : Entity.json(novedadPilaModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setNovedadPilaModeloDTO (NovedadDetalleModeloDTO novedadPilaModeloDTO){
 		this.novedadPilaModeloDTO=novedadPilaModeloDTO;
 	}
 	
 	public NovedadDetalleModeloDTO getNovedadPilaModeloDTO (){
 		return novedadPilaModeloDTO;
 	}
  
}