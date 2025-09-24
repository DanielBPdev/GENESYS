package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.dto.ArchivoSupervivenciaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/verificarEstructuraArchivoSupervivencia
 */
public class VerificarEstructuraArchivoSupervivencia extends ServiceClient { 
    	private ArchivoSupervivenciaDTO archivoSuperVivenciaDTO;
  
  
 	public VerificarEstructuraArchivoSupervivencia (ArchivoSupervivenciaDTO archivoSuperVivenciaDTO){
 		super();
		this.archivoSuperVivenciaDTO=archivoSuperVivenciaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(archivoSuperVivenciaDTO == null ? null : Entity.json(archivoSuperVivenciaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setArchivoSuperVivenciaDTO (ArchivoSupervivenciaDTO archivoSuperVivenciaDTO){
 		this.archivoSuperVivenciaDTO=archivoSuperVivenciaDTO;
 	}
 	
 	public ArchivoSupervivenciaDTO getArchivoSuperVivenciaDTO (){
 		return archivoSuperVivenciaDTO;
 	}
  
}