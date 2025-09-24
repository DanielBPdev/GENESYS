package com.asopagos.novedades.fovis.composite.clients;

import com.asopagos.novedadesfovis.composite.dto.VerificacionNovedadFovisDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovisComposite/verificarPNCSolicitudNovedadFovis
 */
public class VerificarPNCSolicitudNovedadFovis extends ServiceClient { 
    	private VerificacionNovedadFovisDTO verificacionNovedadFovis;
  
  
 	public VerificarPNCSolicitudNovedadFovis (VerificacionNovedadFovisDTO verificacionNovedadFovis){
 		super();
		this.verificacionNovedadFovis=verificacionNovedadFovis;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(verificacionNovedadFovis == null ? null : Entity.json(verificacionNovedadFovis));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setVerificacionNovedadFovis (VerificacionNovedadFovisDTO verificacionNovedadFovis){
 		this.verificacionNovedadFovis=verificacionNovedadFovis;
 	}
 	
 	public VerificacionNovedadFovisDTO getVerificacionNovedadFovis (){
 		return verificacionNovedadFovis;
 	}
  
}