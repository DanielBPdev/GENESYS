package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.VerificacionGestionControlInterno;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/verificarCorreccionesControlInterno
 */
public class VerificarCorreccionesControlInterno extends ServiceClient { 
    	private VerificacionGestionControlInterno correccionHallazgos;
  
  
 	public VerificarCorreccionesControlInterno (VerificacionGestionControlInterno correccionHallazgos){
 		super();
		this.correccionHallazgos=correccionHallazgos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(correccionHallazgos == null ? null : Entity.json(correccionHallazgos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setCorreccionHallazgos (VerificacionGestionControlInterno correccionHallazgos){
 		this.correccionHallazgos=correccionHallazgos;
 	}
 	
 	public VerificacionGestionControlInterno getCorreccionHallazgos (){
 		return correccionHallazgos;
 	}
  
}