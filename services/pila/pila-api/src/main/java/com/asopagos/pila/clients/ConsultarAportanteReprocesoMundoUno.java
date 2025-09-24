package com.asopagos.pila.clients;

import com.asopagos.pila.dto.PlanillaGestionManualDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/consultarAportanteReprocesoMundoUno
 */
public class ConsultarAportanteReprocesoMundoUno extends ServiceClient { 
    	private List<PlanillaGestionManualDTO> indicesPlanilla;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PersonaModeloDTO> result;
  
 	public ConsultarAportanteReprocesoMundoUno (List<PlanillaGestionManualDTO> indicesPlanilla){
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
		result = (List<PersonaModeloDTO>) response.readEntity(new GenericType<List<PersonaModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<PersonaModeloDTO> getResult() {
		return result;
	}

 
  
  	public void setIndicesPlanilla (List<PlanillaGestionManualDTO> indicesPlanilla){
 		this.indicesPlanilla=indicesPlanilla;
 	}
 	
 	public List<PlanillaGestionManualDTO> getIndicesPlanilla (){
 		return indicesPlanilla;
 	}
  
}