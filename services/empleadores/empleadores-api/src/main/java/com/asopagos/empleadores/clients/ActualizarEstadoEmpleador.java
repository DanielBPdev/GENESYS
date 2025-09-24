package com.asopagos.empleadores.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/empleadores/{idEmpleador}/estado
 */
public class ActualizarEstadoEmpleador extends ServiceClient { 
  	private Long idEmpleador;
    	private EstadoEmpleadorEnum nuevoEstado;
  
  
 	public ActualizarEstadoEmpleador (Long idEmpleador,EstadoEmpleadorEnum nuevoEstado){
 		super();
		this.idEmpleador=idEmpleador;
		this.nuevoEstado=nuevoEstado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.put(nuevoEstado == null ? null : Entity.json(nuevoEstado));
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
  
  
  	public void setNuevoEstado (EstadoEmpleadorEnum nuevoEstado){
 		this.nuevoEstado=nuevoEstado;
 	}
 	
 	public EstadoEmpleadorEnum getNuevoEstado (){
 		return nuevoEstado;
 	}
  
}