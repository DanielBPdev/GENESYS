package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.VariablesGestionFOVISDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisComposite/consultarVariablesGestionFOVIS
 */
public class ConsultarVariablesGestionFOVIS extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private VariablesGestionFOVISDTO result;
  
 	public ConsultarVariablesGestionFOVIS (){
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
		this.result = (VariablesGestionFOVISDTO) response.readEntity(VariablesGestionFOVISDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public VariablesGestionFOVISDTO getResult() {
		return result;
	}

 
  
}