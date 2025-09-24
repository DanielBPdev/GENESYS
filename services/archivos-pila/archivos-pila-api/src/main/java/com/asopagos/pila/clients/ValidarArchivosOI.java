package com.asopagos.pila.clients;

import java.util.List;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivosPILA/validarArchivosOI
 */
public class ValidarArchivosOI extends ServiceClient { 
    	private List<IndicePlanilla> indices;
  
  
 	public ValidarArchivosOI (List<IndicePlanilla> indices){
 		super();
		this.indices=indices;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(indices == null ? null : Entity.json(indices));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIndices (List<IndicePlanilla> indices){
 		this.indices=indices;
 	}
 	
 	public List<IndicePlanilla> getIndices (){
 		return indices;
 	}
  
}