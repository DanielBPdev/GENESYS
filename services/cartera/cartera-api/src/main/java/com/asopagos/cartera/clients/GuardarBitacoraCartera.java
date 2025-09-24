package com.asopagos.cartera.clients;

import com.asopagos.cartera.dto.BitacoraCarteraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarBitacoraCartera
 */
public class GuardarBitacoraCartera extends ServiceClient { 
    	private BitacoraCarteraDTO bitacoraCartera;
		private Long result;
  
 	public GuardarBitacoraCartera (BitacoraCarteraDTO bitacoraCartera){
 		super();
		this.bitacoraCartera=bitacoraCartera;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(bitacoraCartera == null ? null : Entity.json(bitacoraCartera));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Long) response.readEntity(Long.class);
	}

	public Long getResult() {
		return this.result;
	}

	public void setResult(Long result) {
		this.result = result;
	}
 
  
  	public void setBitacoraCartera (BitacoraCarteraDTO bitacoraCartera){
 		this.bitacoraCartera=bitacoraCartera;
 	}
 	
 	public BitacoraCarteraDTO getBitacoraCartera (){
 		return bitacoraCartera;
 	}
  
}