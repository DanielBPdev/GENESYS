package com.asopagos.pila.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/aprobarDetallesNoRegistrados
 */
public class AprobarDetallesNoRegistrados extends ServiceClient { 
   	private Long idPlanillaCorrecion;
   
  
 	public AprobarDetallesNoRegistrados (Long idPlanillaCorrecion){
 		super();
		this.idPlanillaCorrecion=idPlanillaCorrecion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idPlanillaCorrecion", idPlanillaCorrecion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdPlanillaCorrecion (Long idPlanillaCorrecion){
 		this.idPlanillaCorrecion=idPlanillaCorrecion;
 	}
 	
 	public Long getIdPlanillaCorrecion (){
 		return idPlanillaCorrecion;
 	}
  
  
}