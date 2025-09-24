package com.asopagos.usuarios.clients;

import java.lang.Integer;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/autenticacion/tiempoToken
 */
public class ActualizarTiempoTokenWeb extends ServiceClient { 
   	private Integer tiempo;
   
  
 	public ActualizarTiempoTokenWeb (Integer tiempo){
 		super();
		this.tiempo=tiempo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tiempo", tiempo)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setTiempo (Integer tiempo){
 		this.tiempo=tiempo;
 	}
 	
 	public Integer getTiempo (){
 		return tiempo;
 	}
  
  
}