package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.VerificacionGestionControlInterno;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/registrarResultadoControlInterno
 */
public class RegistrarResultadoControlInterno extends ServiceClient { 
    	private VerificacionGestionControlInterno gestionControlInterno;
  
  
 	public RegistrarResultadoControlInterno (VerificacionGestionControlInterno gestionControlInterno){
 		super();
		this.gestionControlInterno=gestionControlInterno;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(gestionControlInterno == null ? null : Entity.json(gestionControlInterno));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setGestionControlInterno (VerificacionGestionControlInterno gestionControlInterno){
 		this.gestionControlInterno=gestionControlInterno;
 	}
 	
 	public VerificacionGestionControlInterno getGestionControlInterno (){
 		return gestionControlInterno;
 	}
  
}