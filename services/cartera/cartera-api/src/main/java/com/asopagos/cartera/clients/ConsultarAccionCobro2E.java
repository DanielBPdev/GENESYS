package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.AccionCobro2EModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarAccionCobro2E
 */
public class ConsultarAccionCobro2E extends ServiceClient { 
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private AccionCobro2EModeloDTO result;
  
 	public ConsultarAccionCobro2E (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (AccionCobro2EModeloDTO) response.readEntity(AccionCobro2EModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public AccionCobro2EModeloDTO getResult() {
		return result;
	}

 
  
  
}