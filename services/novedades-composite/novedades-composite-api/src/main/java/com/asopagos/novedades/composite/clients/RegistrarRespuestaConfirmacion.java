package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.composite.dto.RegistrarRespuestaConfirmacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/registrarRespuestaConfirmacion
 */
public class RegistrarRespuestaConfirmacion extends ServiceClient { 
    	private RegistrarRespuestaConfirmacionDTO entrada;
  
  
 	public RegistrarRespuestaConfirmacion (RegistrarRespuestaConfirmacionDTO entrada){
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
	

 
  
  	public void setEntrada (RegistrarRespuestaConfirmacionDTO entrada){
 		this.entrada=entrada;
 	}
 	
 	public RegistrarRespuestaConfirmacionDTO getEntrada (){
 		return entrada;
 	}
  
}