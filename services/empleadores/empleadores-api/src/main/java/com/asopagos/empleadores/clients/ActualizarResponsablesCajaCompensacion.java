package com.asopagos.empleadores.clients;

import java.util.List;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/empleadores/{idEmpleador}/responsablesCajaCompensacion
 */
public class ActualizarResponsablesCajaCompensacion extends ServiceClient { 
  	private Long idEmpleador;
    	private List<String> usuariosCajaCompensacion;
  
  
 	public ActualizarResponsablesCajaCompensacion (Long idEmpleador,List<String> usuariosCajaCompensacion){
 		super();
		this.idEmpleador=idEmpleador;
		this.usuariosCajaCompensacion=usuariosCajaCompensacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.put(usuariosCajaCompensacion == null ? null : Entity.json(usuariosCajaCompensacion));
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
  
  
  	public void setUsuariosCajaCompensacion (List<String> usuariosCajaCompensacion){
 		this.usuariosCajaCompensacion=usuariosCajaCompensacion;
 	}
 	
 	public List<String> getUsuariosCajaCompensacion (){
 		return usuariosCajaCompensacion;
 	}
  
}