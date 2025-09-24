package com.asopagos.empleadores.clients;

import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/modificarEmpleador
 */
public class ModificarEmpleador extends ServiceClient { 
    	private EmpleadorModeloDTO empleadorModeloDTO;
  
  
 	public ModificarEmpleador (EmpleadorModeloDTO empleadorModeloDTO){
 		super();
		this.empleadorModeloDTO=empleadorModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(empleadorModeloDTO == null ? null : Entity.json(empleadorModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEmpleadorModeloDTO (EmpleadorModeloDTO empleadorModeloDTO){
 		this.empleadorModeloDTO=empleadorModeloDTO;
 	}
 	
 	public EmpleadorModeloDTO getEmpleadorModeloDTO (){
 		return empleadorModeloDTO;
 	}
  
}