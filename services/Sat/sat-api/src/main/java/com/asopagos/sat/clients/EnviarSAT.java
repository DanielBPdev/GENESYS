package com.asopagos.sat.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.sat.dto.RespuestaEstandar;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/sat/enviarSAT
 */
public class EnviarSAT extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RespuestaEstandar> result;
  
 	public EnviarSAT (){
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
		this.result = (List<RespuestaEstandar>) response.readEntity(new GenericType<List<RespuestaEstandar>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RespuestaEstandar> getResult() {
		return result;
	}

 
  
}