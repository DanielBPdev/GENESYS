package com.asopagos.pila.composite.clients;

import com.asopagos.pila.dto.PlanillaGestionManualDTO;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PilaComposite/crearAportantesPlanillasMundoUno
 */
public class CrearAportantesPlanillasMundoUno extends ServiceClient { 
    	private List<PlanillaGestionManualDTO> indicesPlanilla;
  
  
 	public CrearAportantesPlanillasMundoUno (List<PlanillaGestionManualDTO> indicesPlanilla){
 		super();
		this.indicesPlanilla=indicesPlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(indicesPlanilla == null ? null : Entity.json(indicesPlanilla));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIndicesPlanilla (List<PlanillaGestionManualDTO> indicesPlanilla){
 		this.indicesPlanilla=indicesPlanilla;
 	}
 	
 	public List<PlanillaGestionManualDTO> getIndicesPlanilla (){
 		return indicesPlanilla;
 	}
  
}