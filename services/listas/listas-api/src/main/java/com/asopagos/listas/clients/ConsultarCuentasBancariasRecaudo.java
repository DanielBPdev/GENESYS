package com.asopagos.listas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.CuentasBancariasRecaudoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/listasValores/consultarCuentasBancariasRecaudo
 */
public class ConsultarCuentasBancariasRecaudo extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentasBancariasRecaudoDTO> result;
  
 	public ConsultarCuentasBancariasRecaudo (){
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
		this.result = (List<CuentasBancariasRecaudoDTO>) response.readEntity(new GenericType<List<CuentasBancariasRecaudoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CuentasBancariasRecaudoDTO> getResult() {
		return result;
	}

 
  
}