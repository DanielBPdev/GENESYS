package com.asopagos.cartera.clients;

import com.asopagos.entidades.ccf.cartera.TiempoProcesoCartera;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarTiempoProcesoCartera
 */
public class GuardarTiempoProcesoCartera extends ServiceClient { 
    	private TiempoProcesoCartera procesoCartera;
  
  
 	public GuardarTiempoProcesoCartera (TiempoProcesoCartera procesoCartera){
 		super();
		this.procesoCartera=procesoCartera;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(procesoCartera == null ? null : Entity.json(procesoCartera));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setProcesoCartera (TiempoProcesoCartera procesoCartera){
 		this.procesoCartera=procesoCartera;
 	}
 	
 	public TiempoProcesoCartera getProcesoCartera (){
 		return procesoCartera;
 	}
  
}