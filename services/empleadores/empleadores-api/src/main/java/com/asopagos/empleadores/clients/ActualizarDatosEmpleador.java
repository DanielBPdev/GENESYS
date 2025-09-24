package com.asopagos.empleadores.clients;

import com.asopagos.entidades.ccf.personas.Empleador;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/actualizarDatosEmpleador
 */
public class ActualizarDatosEmpleador extends ServiceClient { 
    	private Empleador empleador;
  
  
 	public ActualizarDatosEmpleador (Empleador empleador){
 		super();
		this.empleador=empleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(empleador == null ? null : Entity.json(empleador));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEmpleador (Empleador empleador){
 		this.empleador=empleador;
 	}
 	
 	public Empleador getEmpleador (){
 		return empleador;
 	}
  
}