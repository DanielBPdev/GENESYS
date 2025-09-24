package com.asopagos.cartera.composite.clients;

import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/carteraComposite/asignarAccionesCobro
 */
public class AsignarAccionesCobro extends ServiceClient {
 
  
  	private TipoAccionCobroEnum accionCobro;
  
  
 	public AsignarAccionesCobro (TipoAccionCobroEnum accionCobro){
 		super();
		this.accionCobro=accionCobro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("accionCobro", accionCobro)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setAccionCobro (TipoAccionCobroEnum accionCobro){
 		this.accionCobro=accionCobro;
 	}
 	
 	public TipoAccionCobroEnum getAccionCobro (){
 		return accionCobro;
 	}
  
}