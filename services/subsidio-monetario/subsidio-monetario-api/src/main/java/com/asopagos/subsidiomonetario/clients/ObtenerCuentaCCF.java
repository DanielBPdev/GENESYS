package com.asopagos.subsidiomonetario.clients;

import com.asopagos.subsidiomonetario.modelo.dto.CuentaCCFModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/obtenerCuentaCCF
 */
public class ObtenerCuentaCCF extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CuentaCCFModeloDTO result;
  
 	public ObtenerCuentaCCF (){
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
		this.result = (CuentaCCFModeloDTO) response.readEntity(CuentaCCFModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CuentaCCFModeloDTO getResult() {
		return result;
	}

 
  
}