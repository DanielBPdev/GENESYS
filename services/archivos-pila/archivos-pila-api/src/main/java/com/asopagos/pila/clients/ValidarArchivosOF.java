package com.asopagos.pila.clients;

import java.util.List;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivosPILA/validarArchivosOF
 */
public class ValidarArchivosOF extends ServiceClient { 
    	private List<IndicePlanillaOF> indices;
  
  
 	public ValidarArchivosOF (List<IndicePlanillaOF> indices){
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
	

 
  
  	public void setIndices (List<IndicePlanillaOF> indices){
 		this.indices=indices;
 	}
 	
 	public List<IndicePlanillaOF> getIndices (){
 		return indices;
 	}
  
}