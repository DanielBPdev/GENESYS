package com.asopagos.bandejainconsistencias.clients;

import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/actualizarEstadoPlanillaAportanteNuevo
 */
public class ActualizarEstadoPlanillaNuevoAportante extends ServiceClient { 
    	private IndicePlanilla indicePlanilla;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private IndicePlanilla result;
  
 	public ActualizarEstadoPlanillaNuevoAportante (IndicePlanilla indicePlanilla){
 		super();
		this.indicePlanilla=indicePlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(indicePlanilla == null ? null : Entity.json(indicePlanilla));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (IndicePlanilla) response.readEntity(IndicePlanilla.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public IndicePlanilla getResult() {
		return result;
	}

 
  
  	public void setIndicePlanilla (IndicePlanilla indicePlanilla){
 		this.indicePlanilla=indicePlanilla;
 	}
 	
 	public IndicePlanilla getIndicePlanilla (){
 		return indicePlanilla;
 	}
  
}