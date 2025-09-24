package com.asopagos.cartera.composite.clients;

import java.util.List;
import com.asopagos.dto.modelo.DesafiliacionAportanteDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/gestionarDesafiliacion
 */
public class GestionarDesafiliacion extends ServiceClient { 
    	private List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public GestionarDesafiliacion (List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs){
 		super();
		this.desafiliacionAportanteDTOs=desafiliacionAportanteDTOs;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(desafiliacionAportanteDTOs == null ? null : Entity.json(desafiliacionAportanteDTOs));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public String getResult() {
		return result;
	}

 
  
  	public void setDesafiliacionAportanteDTOs (List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs){
 		this.desafiliacionAportanteDTOs=desafiliacionAportanteDTOs;
 	}
 	
 	public List<DesafiliacionAportanteDTO> getDesafiliacionAportanteDTOs (){
 		return desafiliacionAportanteDTOs;
 	}
  
}