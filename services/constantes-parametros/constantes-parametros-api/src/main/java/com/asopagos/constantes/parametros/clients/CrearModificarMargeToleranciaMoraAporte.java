package com.asopagos.constantes.parametros.clients;

import com.asopagos.entidades.ccf.general.Parametro;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/constantesparametros/crearModificarMargeToleranciaMoraAporte
 */
public class CrearModificarMargeToleranciaMoraAporte extends ServiceClient { 
    	private Parametro parametro;
  
  
 	public CrearModificarMargeToleranciaMoraAporte (Parametro parametro){
 		super();
		this.parametro=parametro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametro == null ? null : Entity.json(parametro));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametro (Parametro parametro){
 		this.parametro=parametro;
 	}
 	
 	public Parametro getParametro (){
 		return parametro;
 	}
  
}