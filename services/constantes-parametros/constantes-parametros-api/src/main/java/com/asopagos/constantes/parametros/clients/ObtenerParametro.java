package com.asopagos.constantes.parametros.clients;

import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/constantesparametros/obtenerParametro/{dato}
 */
public class ObtenerParametro extends ServiceClient {
 
  	private String dato;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public ObtenerParametro (String dato){
 		super();
		this.dato=dato;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("dato", dato)
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

 	public void setDato (String dato){
 		this.dato=dato;
 	}
 	
 	public String getDato (){
 		return dato;
 	}
  
  
}