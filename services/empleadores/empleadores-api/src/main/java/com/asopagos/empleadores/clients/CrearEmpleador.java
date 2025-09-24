package com.asopagos.empleadores.clients;

import java.lang.Long;
import com.asopagos.entidades.ccf.personas.Empleador;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores
 */
public class CrearEmpleador extends ServiceClient { 
    	private Empleador empleador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearEmpleador (Empleador empleador){
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
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setEmpleador (Empleador empleador){
 		this.empleador=empleador;
 	}
 	
 	public Empleador getEmpleador (){
 		return empleador;
 	}
  
}