package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.CriteriosParametrizacionTemporalDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/carteraComposite/consultarCriteriosParametrizacion
 */
public class ConsultarCriteriosParametrizacion extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CriteriosParametrizacionTemporalDTO result;
  
 	public ConsultarCriteriosParametrizacion (){
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
		this.result = (CriteriosParametrizacionTemporalDTO) response.readEntity(CriteriosParametrizacionTemporalDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CriteriosParametrizacionTemporalDTO getResult() {
		return result;
	}

 
  
}