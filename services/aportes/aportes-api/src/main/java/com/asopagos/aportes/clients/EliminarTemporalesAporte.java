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
 * /rest/aportes/eliminarTemporalesAporte
 */
public class EliminarTemporalesAporte extends ServiceClient { 
    	private List<Long> idsDetalle;
  
  
 	public EliminarTemporalesAporte (List<Long> idsDetalle){
 		super();
		this.idsDetalle=idsDetalle;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idsDetalle == null ? null : Entity.json(idsDetalle));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIdsDetalle (List<Long> idsDetalle){
 		this.idsDetalle=idsDetalle;
 	}
 	
 	public List<Long> getIdsDetalle (){
 		return idsDetalle;
 	}
  
}