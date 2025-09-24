package com.asopagos.aportes.composite.clients;

import com.asopagos.aportes.composite.dto.GestionAnalistaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/finalizarGestionAnalistaAporte
 */
public class FinalizarGestionAnalistaAporte extends ServiceClient { 
    	private GestionAnalistaDTO gestionAnalista;
  
  
 	public FinalizarGestionAnalistaAporte (GestionAnalistaDTO gestionAnalista){
 		super();
		this.gestionAnalista=gestionAnalista;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(gestionAnalista == null ? null : Entity.json(gestionAnalista));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setGestionAnalista (GestionAnalistaDTO gestionAnalista){
 		this.gestionAnalista=gestionAnalista;
 	}
 	
 	public GestionAnalistaDTO getGestionAnalista (){
 		return gestionAnalista;
 	}
  
}