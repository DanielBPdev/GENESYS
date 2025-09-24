package com.asopagos.aportes.clients;

import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/actualizarCategoriaAfiliadosAporteFuturo
 */
public class ActualizarCategoriaAfiliadosAporteFuturo extends ServiceClient {
 
  
  	private String fecha;
  
  
 	public ActualizarCategoriaAfiliadosAporteFuturo (String fecha){
 		super();
		this.fecha=fecha;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("fecha", fecha)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setFecha (String fecha){
 		this.fecha=fecha;
 	}
 	
 	public String getFecha (){
 		return fecha;
 	}
  
}