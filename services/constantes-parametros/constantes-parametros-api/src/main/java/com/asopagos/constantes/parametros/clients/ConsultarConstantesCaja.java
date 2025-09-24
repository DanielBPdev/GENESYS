package com.asopagos.constantes.parametros.clients;

import com.asopagos.constantes.parametros.dto.ConstantesCajaCompensacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/constantesparametros/constantesCaja
 */
public class ConsultarConstantesCaja extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ConstantesCajaCompensacionDTO result;
  
 	public ConsultarConstantesCaja (){
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
		this.result = (ConstantesCajaCompensacionDTO) response.readEntity(ConstantesCajaCompensacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ConstantesCajaCompensacionDTO getResult() {
		return result;
	}

 
  
}