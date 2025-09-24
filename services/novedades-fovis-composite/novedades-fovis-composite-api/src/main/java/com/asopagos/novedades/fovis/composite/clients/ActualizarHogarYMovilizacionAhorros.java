package com.asopagos.novedades.fovis.composite.clients;

import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesConvertidorFovisComposite/actualizarHogarYMovilizacionAhorros
 */
public class ActualizarHogarYMovilizacionAhorros extends ServiceClient { 
    	private PostulacionFOVISModeloDTO hogar;
  
  
 	public ActualizarHogarYMovilizacionAhorros (PostulacionFOVISModeloDTO hogar){
 		super();
		this.hogar=hogar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(hogar == null ? null : Entity.json(hogar));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setHogar (PostulacionFOVISModeloDTO hogar){
 		this.hogar=hogar;
 	}
 	
 	public PostulacionFOVISModeloDTO getHogar (){
 		return hogar;
 	}
  
}