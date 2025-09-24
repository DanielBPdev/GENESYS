package com.asopagos.afiliados.clients;

import java.util.List;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/actualizarRolesAfiliado
 */
public class ActualizarRolesAfiliado extends ServiceClient { 
    	private List<RolAfiliadoModeloDTO> rolesDTO;
  
  
 	public ActualizarRolesAfiliado (List<RolAfiliadoModeloDTO> rolesDTO){
 		super();
		this.rolesDTO=rolesDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(rolesDTO == null ? null : Entity.json(rolesDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRolesDTO (List<RolAfiliadoModeloDTO> rolesDTO){
 		this.rolesDTO=rolesDTO;
 	}
 	
 	public List<RolAfiliadoModeloDTO> getRolesDTO (){
 		return rolesDTO;
 	}
  
}