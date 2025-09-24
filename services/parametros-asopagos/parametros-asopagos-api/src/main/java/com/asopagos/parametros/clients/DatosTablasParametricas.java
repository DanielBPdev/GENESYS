package com.asopagos.parametros.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/parametros/datosTablasParametricas
 */
public class DatosTablasParametricas extends ServiceClient {
 
  
  	private String nombreTabla;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Object> result;
  
 	public DatosTablasParametricas (String nombreTabla){
 		super();
		this.nombreTabla=nombreTabla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("nombreTabla", nombreTabla)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<Object>) response.readEntity(new GenericType<List<Object>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<Object> getResult() {
		return result;
	}

 
  	public void setNombreTabla (String nombreTabla){
 		this.nombreTabla=nombreTabla;
 	}
 	
 	public String getNombreTabla (){
 		return nombreTabla;
 	}
  
}