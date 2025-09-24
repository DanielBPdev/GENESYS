package com.asopagos.pila.clients;

import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/copiarAgruparPlanilla
 */
public class CopiarAgruparPlanilla extends ServiceClient {
 
  
  	private Long idPlanillaOriginal;
  	private Long idPlanillaCorrecion;
  
  
 	public CopiarAgruparPlanilla (Long idPlanillaOriginal,Long idPlanillaCorrecion){
 		super();
		this.idPlanillaOriginal=idPlanillaOriginal;
		this.idPlanillaCorrecion=idPlanillaCorrecion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPlanillaOriginal", idPlanillaOriginal)
						.queryParam("idPlanillaCorrecion", idPlanillaCorrecion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdPlanillaOriginal (Long idPlanillaOriginal){
 		this.idPlanillaOriginal=idPlanillaOriginal;
 	}
 	
 	public Long getIdPlanillaOriginal (){
 		return idPlanillaOriginal;
 	}
  	public void setIdPlanillaCorrecion (Long idPlanillaCorrecion){
 		this.idPlanillaCorrecion=idPlanillaCorrecion;
 	}
 	
 	public Long getIdPlanillaCorrecion (){
 		return idPlanillaCorrecion;
 	}
  
}