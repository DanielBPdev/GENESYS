package com.asopagos.empresas.clients;

import com.asopagos.entidades.ccf.personas.Empresa;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empresas/actualizarEmpresa
 */
public class ActualizarEmpresa extends ServiceClient { 
    	private Empresa empresa;
  
  
 	public ActualizarEmpresa (Empresa empresa){
 		super();
		this.empresa=empresa;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(empresa == null ? null : Entity.json(empresa));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEmpresa (Empresa empresa){
 		this.empresa=empresa;
 	}
 	
 	public Empresa getEmpresa (){
 		return empresa;
 	}
  
}