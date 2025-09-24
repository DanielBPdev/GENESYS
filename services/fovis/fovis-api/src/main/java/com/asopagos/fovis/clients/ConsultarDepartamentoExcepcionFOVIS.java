package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.DepartamentoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarDepartamentoExcepcionFOVIS
 */
public class ConsultarDepartamentoExcepcionFOVIS extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DepartamentoModeloDTO result;
  
 	public ConsultarDepartamentoExcepcionFOVIS (){
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
		this.result = (DepartamentoModeloDTO) response.readEntity(DepartamentoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DepartamentoModeloDTO getResult() {
		return result;
	}

 
  
}