package com.asopagos.aportes.clients;

import com.asopagos.entidades.ccf.aportes.PilaEstadoTransitorio;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportesPila/crearPilaEstadoTransitorio
 */
public class CrearPilaEstadoTransitorio extends ServiceClient { 
    	private PilaEstadoTransitorio pilaEstadoTransitorio;
  
  
 	public CrearPilaEstadoTransitorio (PilaEstadoTransitorio pilaEstadoTransitorio){
 		super();
		this.pilaEstadoTransitorio=pilaEstadoTransitorio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {

		System.out.println("Aportes CrearPilaEstadoTransitorio Path "+path);
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(pilaEstadoTransitorio == null ? null : Entity.json(pilaEstadoTransitorio));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setPilaEstadoTransitorio (PilaEstadoTransitorio pilaEstadoTransitorio){
 		this.pilaEstadoTransitorio=pilaEstadoTransitorio;
 	}
 	
 	public PilaEstadoTransitorio getPilaEstadoTransitorio (){
 		return pilaEstadoTransitorio;
 	}
  
}