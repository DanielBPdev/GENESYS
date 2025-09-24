package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/actualizarRolAfiliado
 */
public class ActualizarRolAfiliado extends ServiceClient { 
    private RolAfiliadoModeloDTO rolAfiliadoModeloDTO;
  
  
 	public ActualizarRolAfiliado (RolAfiliadoModeloDTO rolAfiliadoModeloDTO){
 		super();
		this.rolAfiliadoModeloDTO=rolAfiliadoModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(rolAfiliadoModeloDTO == null ? null : Entity.json(rolAfiliadoModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRolAfiliadoModeloDTO (RolAfiliadoModeloDTO rolAfiliadoModeloDTO){
 		this.rolAfiliadoModeloDTO=rolAfiliadoModeloDTO;
 	}
 	
 	public RolAfiliadoModeloDTO getRolAfiliadoModeloDTO (){
 		return rolAfiliadoModeloDTO;
 	}
  
}