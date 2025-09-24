package com.asopagos.constantes.parametros.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.entidades.ccf.core.OperadorInformacion;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/constantesparametros/consultarOperadoresInformacionInfo
 */
public class ConsultarOperadoresInformacionInfo extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<OperadorInformacion> result;
  
 	public ConsultarOperadoresInformacionInfo (){
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
		this.result = (List<OperadorInformacion>) response.readEntity(new GenericType<List<OperadorInformacion>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<OperadorInformacion> getResult() {
		return result;
	}

 
  
}