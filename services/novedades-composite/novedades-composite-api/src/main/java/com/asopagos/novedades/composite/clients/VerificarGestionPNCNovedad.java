package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.composite.dto.VerificarGestionPNCNovedadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/verificarGestionPNCNovedad
 */
public class VerificarGestionPNCNovedad extends ServiceClient { 
    	private VerificarGestionPNCNovedadDTO entrada;
  
  
 	public VerificarGestionPNCNovedad (VerificarGestionPNCNovedadDTO entrada){
 		super();
		this.entrada=entrada;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(entrada == null ? null : Entity.json(entrada));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEntrada (VerificarGestionPNCNovedadDTO entrada){
 		this.entrada=entrada;
 	}
 	
 	public VerificarGestionPNCNovedadDTO getEntrada (){
 		return entrada;
 	}
  
}