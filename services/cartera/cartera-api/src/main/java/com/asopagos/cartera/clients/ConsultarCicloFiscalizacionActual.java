package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.CicloCarteraModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarCicloFiscalizacionActual
 */
public class ConsultarCicloFiscalizacionActual extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CicloCarteraModeloDTO result;
  
 	public ConsultarCicloFiscalizacionActual (){
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
		this.result = (CicloCarteraModeloDTO) response.readEntity(CicloCarteraModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CicloCarteraModeloDTO getResult() {
		return result;
	}

 
  
}