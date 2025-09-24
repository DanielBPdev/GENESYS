package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/consultarListaBlancaAportantes
 */
public class ConsultarListaBlancaAportantes extends ServiceClient { 
    	private List<PersonaModeloDTO> aportantes;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ListasBlancasAportantes> result;
  
 	public ConsultarListaBlancaAportantes (List<PersonaModeloDTO> aportantes){
 		super();
		this.aportantes=aportantes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(aportantes == null ? null : Entity.json(aportantes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ListasBlancasAportantes>) response.readEntity(new GenericType<List<ListasBlancasAportantes>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ListasBlancasAportantes> getResult() {
		return result;
	}

 
  
  	public void setAportantes (List<PersonaModeloDTO> aportantes){
 		this.aportantes=aportantes;
 	}
 	
 	public List<PersonaModeloDTO> getAportantes (){
 		return aportantes;
 	}
  
}