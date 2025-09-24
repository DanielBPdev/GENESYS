package com.asopagos.cartera.clients;

import java.util.List;
import com.asopagos.dto.modelo.ConvenioPagoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/actualizarConveniosPago
 */
public class ActualizarConveniosPago extends ServiceClient { 
    	private List<ConvenioPagoModeloDTO> conveniosPago;
  
  
 	public ActualizarConveniosPago (List<ConvenioPagoModeloDTO> conveniosPago){
 		super();
		this.conveniosPago=conveniosPago;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(conveniosPago == null ? null : Entity.json(conveniosPago));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setConveniosPago (List<ConvenioPagoModeloDTO> conveniosPago){
 		this.conveniosPago=conveniosPago;
 	}
 	
 	public List<ConvenioPagoModeloDTO> getConveniosPago (){
 		return conveniosPago;
 	}
  
}