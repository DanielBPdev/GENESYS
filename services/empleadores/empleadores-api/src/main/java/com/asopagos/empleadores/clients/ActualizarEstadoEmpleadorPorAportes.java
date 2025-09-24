package com.asopagos.empleadores.clients;

import com.asopagos.dto.ActivacionEmpleadorDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/actualizarEstadoEmpleadorPorAportes
 */
public class ActualizarEstadoEmpleadorPorAportes extends ServiceClient { 
    	private ActivacionEmpleadorDTO datosReintegro;
  
  
 	public ActualizarEstadoEmpleadorPorAportes (ActivacionEmpleadorDTO datosReintegro){
 		super();
		this.datosReintegro=datosReintegro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosReintegro == null ? null : Entity.json(datosReintegro));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDatosReintegro (ActivacionEmpleadorDTO datosReintegro){
 		this.datosReintegro=datosReintegro;
 	}
 	
 	public ActivacionEmpleadorDTO getDatosReintegro (){
 		return datosReintegro;
 	}
  
}