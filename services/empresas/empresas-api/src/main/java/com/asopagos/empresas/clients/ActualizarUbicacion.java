package com.asopagos.empresas.clients;

import com.asopagos.entidades.ccf.core.Ubicacion;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empresas/actualizarUbicacion
 */
public class ActualizarUbicacion extends ServiceClient { 
    	private Ubicacion ubicacion;
  
  
 	public ActualizarUbicacion (Ubicacion ubicacion){
 		super();
		this.ubicacion=ubicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(ubicacion == null ? null : Entity.json(ubicacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setUbicacion (Ubicacion ubicacion){
 		this.ubicacion=ubicacion;
 	}
 	
 	public Ubicacion getUbicacion (){
 		return ubicacion;
 	}
  
}