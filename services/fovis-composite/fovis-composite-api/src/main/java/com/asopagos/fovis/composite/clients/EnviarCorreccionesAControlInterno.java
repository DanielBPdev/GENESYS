package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.VerificacionCorreccionHallazgos;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/enviarCorreccionesAControlInterno
 */
public class EnviarCorreccionesAControlInterno extends ServiceClient { 
    	private VerificacionCorreccionHallazgos correccionHallazgos;
  
  
 	public EnviarCorreccionesAControlInterno (VerificacionCorreccionHallazgos correccionHallazgos){
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
	

 
  
  	public void setCorreccionHallazgos (VerificacionCorreccionHallazgos correccionHallazgos){
 		this.correccionHallazgos=correccionHallazgos;
 	}
 	
 	public VerificacionCorreccionHallazgos getCorreccionHallazgos (){
 		return correccionHallazgos;
 	}
  
}