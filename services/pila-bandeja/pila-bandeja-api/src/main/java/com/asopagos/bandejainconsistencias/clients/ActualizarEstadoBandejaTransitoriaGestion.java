package com.asopagos.bandejainconsistencias.clients;

import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/actualizarEstadoBandejaTransitoriaGestion
 */
public class ActualizarEstadoBandejaTransitoriaGestion extends ServiceClient { 
   	private Long indicePlanilla;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ActualizarEstadoBandejaTransitoriaGestion (Long indicePlanilla){
 		super();
		this.indicePlanilla=indicePlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("indicePlanilla", indicePlanilla)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  	public void setIndicePlanilla (Long indicePlanilla){
 		this.indicePlanilla=indicePlanilla;
 	}
 	
 	public Long getIndicePlanilla (){
 		return indicePlanilla;
 	}
  
  
}