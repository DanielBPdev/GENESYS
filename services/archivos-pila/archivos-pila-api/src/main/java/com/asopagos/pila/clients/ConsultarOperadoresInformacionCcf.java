package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.pila.dto.OperadorInformacionDTO;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/archivosPILA/consultarOperadoresInformacionCcf
 */
public class ConsultarOperadoresInformacionCcf extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<OperadorInformacionDTO> result;
  
 	public ConsultarOperadoresInformacionCcf (String codigoCcf){
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
		this.result = (List<OperadorInformacionDTO>) response.readEntity(new GenericType<List<OperadorInformacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<OperadorInformacionDTO> getResult() {
		return result;
	}

 
  
}