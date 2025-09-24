package com.asopagos.bandejainconsistencias.clients;

import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/actualizarEmpleadoresGestionadosBandejaCero
 */
public class ActualizarEmpleadoresGestionadosBandejaCero extends ServiceClient { 
    	private List<Long> idEmpleadores;
  
  
 	public ActualizarEmpleadoresGestionadosBandejaCero (List<Long> idEmpleadores){
 		super();
		this.idEmpleadores=idEmpleadores;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idEmpleadores == null ? null : Entity.json(idEmpleadores));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIdEmpleadores (List<Long> idEmpleadores){
 		this.idEmpleadores=idEmpleadores;
 	}
 	
 	public List<Long> getIdEmpleadores (){
 		return idEmpleadores;
 	}
  
}