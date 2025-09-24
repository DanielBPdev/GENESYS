package com.asopagos.constantes.parametros.clients;

import com.asopagos.constantes.parametros.dto.ConstantesValorUVTDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/constantesparametros/constantesCaja
 */
public class CrearValorUVT extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ConstantesValorUVTDTO result;
  
 	public CrearValorUVT (){
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
		this.result = (ConstantesValorUVTDTO) response.readEntity(ConstantesValorUVTDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ConstantesValorUVTDTO getResult() {
		return result;
	}

 
  
}