package com.asopagos.empleadores.clients;

import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/guardarDatosEmpleador
 */
public class GuardarDatosEmpleador extends ServiceClient { 
    	private EmpleadorModeloDTO empleadorDTO;
  
  
 	public GuardarDatosEmpleador (EmpleadorModeloDTO empleadorDTO){
 		super();
		this.empleadorDTO=empleadorDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(empleadorDTO == null ? null : Entity.json(empleadorDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEmpleadorDTO (EmpleadorModeloDTO empleadorDTO){
 		this.empleadorDTO=empleadorDTO;
 	}
 	
 	public EmpleadorModeloDTO getEmpleadorDTO (){
 		return empleadorDTO;
 	}
  
}