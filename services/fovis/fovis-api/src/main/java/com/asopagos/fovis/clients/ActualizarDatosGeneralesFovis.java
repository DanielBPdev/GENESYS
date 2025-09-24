package com.asopagos.fovis.clients;

import java.util.List;
import com.asopagos.dto.modelo.ParametrizacionFOVISModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/actualizarDatosGeneralesFovis
 */
public class ActualizarDatosGeneralesFovis extends ServiceClient { 
    	private List<ParametrizacionFOVISModeloDTO> parametrizacionFOVIS;
  
  
 	public ActualizarDatosGeneralesFovis (List<ParametrizacionFOVISModeloDTO> parametrizacionFOVIS){
 		super();
		this.parametrizacionFOVIS=parametrizacionFOVIS;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionFOVIS == null ? null : Entity.json(parametrizacionFOVIS));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametrizacionFOVIS (List<ParametrizacionFOVISModeloDTO> parametrizacionFOVIS){
 		this.parametrizacionFOVIS=parametrizacionFOVIS;
 	}
 	
 	public List<ParametrizacionFOVISModeloDTO> getParametrizacionFOVIS (){
 		return parametrizacionFOVIS;
 	}
  
}