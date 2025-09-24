package com.asopagos.aportes.clients;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
* ArchivosAportesMasivosService/rest/archivosAportesMasivos/cargarAutomaticamenteArchivosCrucesAportes
 */
public class CargarAutomaticamenteArchivosCrucesAportesAutomatico extends ServiceClient {
 
  
  
  
 	public CargarAutomaticamenteArchivosCrucesAportesAutomatico (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
}