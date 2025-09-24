package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.TasasInteresMoraModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarTasasInteresMoraAportes
 */
public class ConsultarTasasInteresMoraAportes extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TasasInteresMoraModeloDTO> result;
  
 	public ConsultarTasasInteresMoraAportes (){
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
		this.result = (List<TasasInteresMoraModeloDTO>) response.readEntity(new GenericType<List<TasasInteresMoraModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<TasasInteresMoraModeloDTO> getResult() {
		return result;
	}

 
  
}