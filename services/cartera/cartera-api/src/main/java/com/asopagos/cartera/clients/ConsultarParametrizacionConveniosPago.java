package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.ParametrizacionConveniosPagoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarParametrizacionConvenioPago
 */
public class ConsultarParametrizacionConveniosPago extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ParametrizacionConveniosPagoModeloDTO result;
  
 	public ConsultarParametrizacionConveniosPago (){
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
		this.result = (ParametrizacionConveniosPagoModeloDTO) response.readEntity(ParametrizacionConveniosPagoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ParametrizacionConveniosPagoModeloDTO getResult() {
		return result;
	}

 
  
}