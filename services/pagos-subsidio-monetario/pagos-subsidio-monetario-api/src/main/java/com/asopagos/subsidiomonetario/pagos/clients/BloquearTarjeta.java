package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/bloquearTarjeta
 */
public class BloquearTarjeta extends ServiceClient {
 
  
  	private String numeroTarjeta;
  
  
 	public BloquearTarjeta (String numeroTarjeta){
 		super();
		this.numeroTarjeta=numeroTarjeta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroTarjeta", numeroTarjeta)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setNumeroTarjeta (String numeroTarjeta){
 		this.numeroTarjeta=numeroTarjeta;
 	}
 	
 	public String getNumeroTarjeta (){
 		return numeroTarjeta;
 	}
  
}