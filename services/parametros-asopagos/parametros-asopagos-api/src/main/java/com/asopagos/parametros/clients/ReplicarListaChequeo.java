package com.asopagos.parametros.clients;

import com.asopagos.entidades.transversal.core.Requisito;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/parametros/replicarListaChequeo
 */
public class ReplicarListaChequeo extends ServiceClient { 
    	private Requisito requisito;
  
  
 	public ReplicarListaChequeo (Requisito requisito){
 		super();
		this.requisito=requisito;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(requisito == null ? null : Entity.json(requisito));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRequisito (Requisito requisito){
 		this.requisito=requisito;
 	}
 	
 	public Requisito getRequisito (){
 		return requisito;
 	}
  
}