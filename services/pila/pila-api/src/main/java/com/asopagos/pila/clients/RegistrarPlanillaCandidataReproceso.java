package com.asopagos.pila.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/registrarPlanillaCandidataReproceso
 */
public class RegistrarPlanillaCandidataReproceso extends ServiceClient { 
   	private String motivoBloqueo;
  	private Long idRegistroGeneral;
   
  
 	public RegistrarPlanillaCandidataReproceso (String motivoBloqueo,Long idRegistroGeneral){
 		super();
		this.motivoBloqueo=motivoBloqueo;
		this.idRegistroGeneral=idRegistroGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("motivoBloqueo", motivoBloqueo)
			.queryParam("idRegistroGeneral", idRegistroGeneral)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setMotivoBloqueo (String motivoBloqueo){
 		this.motivoBloqueo=motivoBloqueo;
 	}
 	
 	public String getMotivoBloqueo (){
 		return motivoBloqueo;
 	}
  	public void setIdRegistroGeneral (Long idRegistroGeneral){
 		this.idRegistroGeneral=idRegistroGeneral;
 	}
 	
 	public Long getIdRegistroGeneral (){
 		return idRegistroGeneral;
 	}
  
  
}