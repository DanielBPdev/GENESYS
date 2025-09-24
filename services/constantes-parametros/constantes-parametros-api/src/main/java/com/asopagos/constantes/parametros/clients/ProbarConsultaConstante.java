package com.asopagos.constantes.parametros.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/constantesparametros/probarConsultaConstante/{iteraciones}
 */
public class ProbarConsultaConstante extends ServiceClient {
 
  	private Long iteraciones;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public ProbarConsultaConstante (Long iteraciones){
 		super();
		this.iteraciones=iteraciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("iteraciones", iteraciones)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public String getResult() {
		return result;
	}

 	public void setIteraciones (Long iteraciones){
 		this.iteraciones=iteraciones;
 	}
 	
 	public Long getIteraciones (){
 		return iteraciones;
 	}
  
  
}