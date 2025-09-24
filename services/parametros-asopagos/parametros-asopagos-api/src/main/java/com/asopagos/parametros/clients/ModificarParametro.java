package com.asopagos.parametros.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/parametros/admon/{nombreTabla}
 */
public class ModificarParametro extends ServiceClient { 
  	private String nombreTabla;
    	private String parametro;
  
  
 	public ModificarParametro (String nombreTabla,String parametro){
 		super();
		this.nombreTabla=nombreTabla;
		this.parametro=parametro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("nombreTabla", nombreTabla)
			.request(MediaType.APPLICATION_JSON)
			.put(parametro == null ? null : Entity.json(parametro));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setNombreTabla (String nombreTabla){
 		this.nombreTabla=nombreTabla;
 	}
 	
 	public String getNombreTabla (){
 		return nombreTabla;
 	}
  
  
  	public void setParametro (String parametro){
 		this.parametro=parametro;
 	}
 	
 	public String getParametro (){
 		return parametro;
 	}
  
}