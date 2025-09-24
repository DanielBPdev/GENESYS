package com.asopagos.novedades.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.RegistroNovedadFuturaModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/consultarNovedadesFuturas
 */
public class ConsultarNovedadesFuturas extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RegistroNovedadFuturaModeloDTO> result;
  
 	public ConsultarNovedadesFuturas (){
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
		this.result = (List<RegistroNovedadFuturaModeloDTO>) response.readEntity(new GenericType<List<RegistroNovedadFuturaModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RegistroNovedadFuturaModeloDTO> getResult() {
		return result;
	}

 
  
}