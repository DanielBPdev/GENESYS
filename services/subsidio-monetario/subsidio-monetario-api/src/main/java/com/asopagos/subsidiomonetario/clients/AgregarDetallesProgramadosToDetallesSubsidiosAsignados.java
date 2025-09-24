package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/subsidioMonetario/agregar/detallesProgramadosToDetallesSubsidiosAsignados
 */
public class AgregarDetallesProgramadosToDetallesSubsidiosAsignados extends ServiceClient { 
    
  
 	public AgregarDetallesProgramadosToDetallesSubsidiosAsignados (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  
}