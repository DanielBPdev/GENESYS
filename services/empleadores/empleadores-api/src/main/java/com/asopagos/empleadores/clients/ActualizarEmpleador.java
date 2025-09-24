package com.asopagos.empleadores.clients;

import java.lang.Long;
import com.asopagos.dto.EmpleadorDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/empleadores/{idEmpleador}
 */
public class ActualizarEmpleador extends ServiceClient { 
  	private Long idEmpleador;
    	private EmpleadorDTO empleador;
  
  
 	public ActualizarEmpleador (Long idEmpleador,EmpleadorDTO empleador){
 		super();
		this.idEmpleador=idEmpleador;
		this.empleador=empleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.put(empleador == null ? null : Entity.json(empleador));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  
  	public void setEmpleador (EmpleadorDTO empleador){
 		this.empleador=empleador;
 	}
 	
 	public EmpleadorDTO getEmpleador (){
 		return empleador;
 	}
  
}