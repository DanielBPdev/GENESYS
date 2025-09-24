package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.entidades.subsidiomonetario.pagos.ArchivoTransDetaSubsidio;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/actualizarArchivoTransDetaSubsidio
 */
public class ActualizarArchivoTransDetaSubsidio extends ServiceClient { 
    	private ArchivoTransDetaSubsidio archivoTransDetaSubsidio;
  
  
 	public ActualizarArchivoTransDetaSubsidio (ArchivoTransDetaSubsidio archivoTransDetaSubsidio){
 		super();
		this.archivoTransDetaSubsidio=archivoTransDetaSubsidio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(archivoTransDetaSubsidio == null ? null : Entity.json(archivoTransDetaSubsidio));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setArchivoTransDetaSubsidio (ArchivoTransDetaSubsidio archivoTransDetaSubsidio){
 		this.archivoTransDetaSubsidio=archivoTransDetaSubsidio;
 	}
 	
 	public ArchivoTransDetaSubsidio getArchivoTransDetaSubsidio (){
 		return archivoTransDetaSubsidio;
 	}
  
}