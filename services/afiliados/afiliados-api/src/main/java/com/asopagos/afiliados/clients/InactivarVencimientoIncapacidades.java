package com.asopagos.afiliados.clients;

import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/inactivarVencimientoIncapacidades
 */
public class InactivarVencimientoIncapacidades extends ServiceClient { 
    	private List<Long> idPersonasInactivar;
  
  
 	public InactivarVencimientoIncapacidades (List<Long> idPersonasInactivar){
 		super();
		this.idPersonasInactivar=idPersonasInactivar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idPersonasInactivar == null ? null : Entity.json(idPersonasInactivar));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIdPersonasInactivar (List<Long> idPersonasInactivar){
 		this.idPersonasInactivar=idPersonasInactivar;
 	}
 	
 	public List<Long> getIdPersonasInactivar (){
 		return idPersonasInactivar;
 	}
  
}