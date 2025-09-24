package com.asopagos.pila.clients;

import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/cambiarMarcaHabilitacionPila2Manual
 */
public class CambiarMarcaHabilitacionPila2Manual extends ServiceClient {
 
  
  	private Long idIndicePlanilla;
  	private Boolean nuevaMarca;
  
  
 	public CambiarMarcaHabilitacionPila2Manual (Long idIndicePlanilla,Boolean nuevaMarca){
 		super();
		this.idIndicePlanilla=idIndicePlanilla;
		this.nuevaMarca=nuevaMarca;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idIndicePlanilla", idIndicePlanilla)
						.queryParam("nuevaMarca", nuevaMarca)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdIndicePlanilla (Long idIndicePlanilla){
 		this.idIndicePlanilla=idIndicePlanilla;
 	}
 	
 	public Long getIdIndicePlanilla (){
 		return idIndicePlanilla;
 	}
  	public void setNuevaMarca (Boolean nuevaMarca){
 		this.nuevaMarca=nuevaMarca;
 	}
 	
 	public Boolean getNuevaMarca (){
 		return nuevaMarca;
 	}
  
}