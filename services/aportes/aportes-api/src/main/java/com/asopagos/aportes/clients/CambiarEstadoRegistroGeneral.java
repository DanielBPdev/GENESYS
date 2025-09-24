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
 * /rest/aportes/cambiarEstadoRegistroGeneral
 */
public class CambiarEstadoRegistroGeneral extends ServiceClient { 
    	private List<Long> idRegistroGeneral;
  
  
 	public CambiarEstadoRegistroGeneral (List<Long> idRegistroGeneral){
 		super();
		this.idRegistroGeneral=idRegistroGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idRegistroGeneral == null ? null : Entity.json(idRegistroGeneral));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIdRegistroGeneral (List<Long> idRegistroGeneral){
 		this.idRegistroGeneral=idRegistroGeneral;
 	}
 	
 	public List<Long> getIdRegistroGeneral (){
 		return idRegistroGeneral;
 	}
  
}