package com.asopagos.afiliaciones.clients;

import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/actualizarFechaCreacionCertificadoEscolar
 */
public class ActualizarFechaCreacionCertificadoEscolar extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Integer result;
  
 	public ActualizarFechaCreacionCertificadoEscolar (){
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
		this.result = (Integer) response.readEntity(Integer.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Integer getResult() {
		return result;
	}

 
  
}