package com.asopagos.fovis.clients;

import java.util.List;
import com.asopagos.dto.fovis.ParametrizacionMedioPagoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/actualizarMediosDePago
 */
public class ActualizarMediosDePago extends ServiceClient { 
    	private List<ParametrizacionMedioPagoDTO> parametrizacionesMedioPagoDTO;
  
  
 	public ActualizarMediosDePago (List<ParametrizacionMedioPagoDTO> parametrizacionesMedioPagoDTO){
 		super();
		this.parametrizacionesMedioPagoDTO=parametrizacionesMedioPagoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionesMedioPagoDTO == null ? null : Entity.json(parametrizacionesMedioPagoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametrizacionesMedioPagoDTO (List<ParametrizacionMedioPagoDTO> parametrizacionesMedioPagoDTO){
 		this.parametrizacionesMedioPagoDTO=parametrizacionesMedioPagoDTO;
 	}
 	
 	public List<ParametrizacionMedioPagoDTO> getParametrizacionesMedioPagoDTO (){
 		return parametrizacionesMedioPagoDTO;
 	}
  
}