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
 * /rest/afiliados/inactivarAfiliadosMasivo
 */
public class InactivarAfiliadosMasivo extends ServiceClient { 
    	private List<RolAfiliadoModeloDTO> rolesAfiliado;
  
  
 	public InactivarAfiliadosMasivo (List<RolAfiliadoModeloDTO> rolesAfiliado){
 		super();
		this.rolesAfiliado=rolesAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(rolesAfiliado == null ? null : Entity.json(rolesAfiliado));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRolesAfiliado (List<RolAfiliadoModeloDTO> rolesAfiliado){
 		this.rolesAfiliado=rolesAfiliado;
 	}
 	
 	public List<RolAfiliadoModeloDTO> getRolesAfiliado (){
 		return rolesAfiliado;
 	}
  
}