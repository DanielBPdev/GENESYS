package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.GestionPNCPostulacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/gestionarPNCPostulacionWeb
 */
public class GestionarPNCPostulacionWeb extends ServiceClient { 
    	private GestionPNCPostulacionDTO entrada;
  
  
 	public GestionarPNCPostulacionWeb (GestionPNCPostulacionDTO entrada){
 		super();
		this.entrada=entrada;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(entrada == null ? null : Entity.json(entrada));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEntrada (GestionPNCPostulacionDTO entrada){
 		this.entrada=entrada;
 	}
 	
 	public GestionPNCPostulacionDTO getEntrada (){
 		return entrada;
 	}
  
}