package com.asopagos.aportes.clients;

import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/cambiarEstadoRegistroDetallado
 */
public class CambiarEstadoRegistroDetallado extends ServiceClient { 
    	private List<Long> idCotizantes;
  
  
 	public CambiarEstadoRegistroDetallado (List<Long> idCotizantes){
 		super();
		this.idCotizantes=idCotizantes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idCotizantes == null ? null : Entity.json(idCotizantes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIdCotizantes (List<Long> idCotizantes){
 		this.idCotizantes=idCotizantes;
 	}
 	
 	public List<Long> getIdCotizantes (){
 		return idCotizantes;
 	}
  
}