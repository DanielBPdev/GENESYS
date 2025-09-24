package com.asopagos.empleadores.clients;

import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/anularSolicitudes
 */
public class AnularSolictudesEmpleador extends ServiceClient { 
    	private List<Long> idsEmpleadores;
  
  
 	public AnularSolictudesEmpleador (List<Long> idsEmpleadores){
 		super();
		this.idsEmpleadores=idsEmpleadores;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idsEmpleadores == null ? null : Entity.json(idsEmpleadores));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIdsEmpleadores (List<Long> idsEmpleadores){
 		this.idsEmpleadores=idsEmpleadores;
 	}
 	
 	public List<Long> getIdsEmpleadores (){
 		return idsEmpleadores;
 	}
  
}