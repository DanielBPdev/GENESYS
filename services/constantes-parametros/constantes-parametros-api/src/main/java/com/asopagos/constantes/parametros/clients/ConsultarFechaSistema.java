package com.asopagos.constantes.parametros.clients;

import java.util.Date;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/constantesparametros/consultarFechaSistema
 */
public class ConsultarFechaSistema extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Date result;
  
 	public ConsultarFechaSistema (){
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
		this.result = (Date) response.readEntity(Date.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Date getResult() {
		return result;
	}

 
  
}