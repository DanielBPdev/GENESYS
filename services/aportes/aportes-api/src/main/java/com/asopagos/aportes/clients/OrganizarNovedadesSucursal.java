package com.asopagos.aportes.clients;

import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/{idTransaccion}/organizarNovedadesSucursal
 */
public class OrganizarNovedadesSucursal extends ServiceClient { 
  	private Long idTransaccion;
   
  
 	public OrganizarNovedadesSucursal (Long idTransaccion){
 		super();
		this.idTransaccion=idTransaccion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idTransaccion", idTransaccion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdTransaccion (Long idTransaccion){
 		this.idTransaccion=idTransaccion;
 	}
 	
 	public Long getIdTransaccion (){
 		return idTransaccion;
 	}
  
  
}