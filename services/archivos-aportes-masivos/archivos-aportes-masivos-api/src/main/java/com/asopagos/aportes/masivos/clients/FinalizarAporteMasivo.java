package com.asopagos.aportes.masivos.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/FinalizarAporteMasivo
 */
public class FinalizarAporteMasivo extends ServiceClient { 
   	private String nombreArchivo;
   
  
 	public FinalizarAporteMasivo (String nombreArchivo){
 		super();
		this.nombreArchivo=nombreArchivo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("nombreArchivo", nombreArchivo)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setNombreArchivo (String nombreArchivo){
 		this.nombreArchivo=nombreArchivo;
 	}
 	
 	public String getNombreArchivo (){
 		return nombreArchivo;
 	}
  
  
}