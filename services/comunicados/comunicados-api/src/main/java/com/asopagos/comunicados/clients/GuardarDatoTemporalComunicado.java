package com.asopagos.comunicados.clients;

import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalComunicado;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/comunicados/guardarDatoTemporalComunicado
 */
public class GuardarDatoTemporalComunicado extends ServiceClient { 
    	private DatoTemporalComunicado datoTemporalComunicado;
  
  
 	public GuardarDatoTemporalComunicado (DatoTemporalComunicado datoTemporalComunicado){
 		super();
		this.datoTemporalComunicado=datoTemporalComunicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datoTemporalComunicado == null ? null : Entity.json(datoTemporalComunicado));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDatoTemporalComunicado (DatoTemporalComunicado datoTemporalComunicado){
 		this.datoTemporalComunicado=datoTemporalComunicado;
 	}
 	
 	public DatoTemporalComunicado getDatoTemporalComunicado (){
 		return datoTemporalComunicado;
 	}
  
}