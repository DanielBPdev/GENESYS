package com.asopagos.empleadores.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.ccf.personas.RolContactoEmpleador;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/empleadores/{idEmpleador}/rolesContacto
 */
public class ActualizarRolContactoEmpleador extends ServiceClient { 
  	private Long idEmpleador;
    	private List<RolContactoEmpleador> rolesContacto;
  
  
 	public ActualizarRolContactoEmpleador (Long idEmpleador,List<RolContactoEmpleador> rolesContacto){
 		super();
		this.idEmpleador=idEmpleador;
		this.rolesContacto=rolesContacto;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.put(rolesContacto == null ? null : Entity.json(rolesContacto));
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
  
  
  	public void setRolesContacto (List<RolContactoEmpleador> rolesContacto){
 		this.rolesContacto=rolesContacto;
 	}
 	
 	public List<RolContactoEmpleador> getRolesContacto (){
 		return rolesContacto;
 	}
  
}